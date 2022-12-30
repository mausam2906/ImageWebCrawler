package com.eulerity.hackathon.imagefinder;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.*;

import org.apache.commons.lang3.StringUtils;
 
public class MultiThreadManager {
	private static final long SLEEP_TIME = 1000;
 
	private Set<URL> masterUrlList = new HashSet<>();
	private Set<URL> masterImgList = new HashSet<>();
	private List<Future<PageInfo>> futurePages = new ArrayList<>();
	private ExecutorService executorService = Executors.newCachedThreadPool();
	public ArrayList<String> scrapedImgs;
	private String domainName;
 
	public MultiThreadManager() {
	}
 
	public ArrayList<String> go(URL start) throws IOException, InterruptedException {
 
		// same domain as the URL submitted by the user
		domainName = start.toString().replaceAll("(.*//.*/).*", "$1");
		submitNewURL(start);
 
		while (checkPageGrabs()) ;
		scrapedImgs = new ArrayList<String>();
		for(URL img : masterImgList){
			try {
				scrapedImgs.add(img.toString());
			}
			catch(Exception e) {}			
		}
		
		return scrapedImgs;
	}
 
	private boolean checkPageGrabs() throws InterruptedException {
		Thread.sleep(SLEEP_TIME);
		Set<PageInfo> pageSet = new HashSet<>();
		Iterator<Future<PageInfo>> iterator = futurePages.iterator();
 
		while (iterator.hasNext()) {
			Future<PageInfo> future = iterator.next();
			if (future.isDone()) {
				iterator.remove();
				try {
					pageSet.add(future.get());
				} catch (Exception e) {  // skip pages that load too slow
				} 
			}
		}
 
		for (PageInfo grabPage : pageSet) {
			addNewURLs(grabPage);
		}
		for (PageInfo grabPage : pageSet) {
			addNewImgs(grabPage);
		}
		
		return (futurePages.size() > 0);
	}

	private void addNewImgs(PageInfo grabPage) {
		for (URL img : grabPage.getImgList()) {
			if (!masterImgList.contains(img)) {
				masterImgList.add(img);
			}
		}
	}
 
	private void addNewURLs(PageInfo grabPage) {
		for (URL url : grabPage.getUrlList()) {
			submitNewURL(url);
		}
	}
 
	private void submitNewURL(URL url) {
		if (shouldVisit(url)) {
			masterUrlList.add(url);
			PageInfo grabPage = new PageInfo(url);
			Future<PageInfo> future = executorService.submit(grabPage);
			futurePages.add(future);
		}
	}
	
	private boolean shouldVisit(URL url) {
		if (masterUrlList.contains(url)) {
			return false;
		}
		if (!url.toString().startsWith(domainName)) {
			return false;
		}
		if (url.toString().endsWith(".pdf")) {
			return false;
		}
		
		return true;
	}
}
