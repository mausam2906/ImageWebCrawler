package com.eulerity.hackathon.imagefinder;

// import org.*;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
 
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;
 
public class PageInfo implements Callable<PageInfo> {
	static final int TIMEOUT = 60000;
 
	private URL url;
	private Set<URL> urlList = new HashSet<>();
    private Set<URL> imgList = new HashSet<>();
 
	public PageInfo(URL url) {
		this.url = url;
	}
 
	@Override
	public PageInfo call() throws Exception {
		Document document = null;
		document = Jsoup.parse(url, TIMEOUT);
		processLinks(document.select("a[href]"));
        processImages(document.select("img[src~=(?i)\\.(png|jpe?g|gif)]"));
		return this;
	}
 
	private void processLinks(Elements links) {
		for (Element link : links) {
			String href = link.attr("href");
			if (StringUtils.isBlank(href)
			||  href.startsWith("#")) {
				continue;
			}
			try {
				URL nextUrl = new URL(url, href);
				urlList.add(nextUrl);
			} catch (MalformedURLException e) { // ignore bad urls
			}
		}
	}
    private void processImages(Elements images){
        for(Element img : images){
            String pulledImg = img.attr("abs:src");
            try {
                imgList.add(new URL(pulledImg));
            } catch (MalformedURLException e) {
            }
        }
    }
	public Set<URL> getUrlList() {
		return urlList;
	}
    public Set<URL> getImgList(){
        return imgList;
    }
}