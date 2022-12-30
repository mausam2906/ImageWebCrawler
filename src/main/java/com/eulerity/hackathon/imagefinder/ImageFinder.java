package com.eulerity.hackathon.imagefinder;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@WebServlet(
    name = "ImageFinder",
    urlPatterns = {"/main"}
)
public class ImageFinder extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected static final Gson GSON = new GsonBuilder().create();
	ArrayList<String> scrapedImages = new ArrayList<String>();
	public static String[] finalImages;

	@Override
	protected final void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/json");
		String path = req.getServletPath();
		String url = req.getParameter("url");
		System.out.println("Got request of: " + path + " with query param: " + url);
		
		MultiThreadManager multiThreadManager = new MultiThreadManager();
		
		try {
			scrapedImages = multiThreadManager.go(new URL(url));
		} catch (Exception e) {}		

		finalImages = new String[scrapedImages.size()];
		int i = 0;
		for(String img: scrapedImages) {
			finalImages[i] = img;
			i++;
		}

		resp.getWriter().print(GSON.toJson(finalImages));
	}
	
}
