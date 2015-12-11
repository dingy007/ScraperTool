package com.scraper.tool;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class ScraperBase {

	public String queryHTML (HttpURLConnection httpConnection) {
		String queriedHTML = "";
		InputStreamReader connectionStreamReader = null;
		BufferedReader incomingData = null;
		try {
			connectionStreamReader = new InputStreamReader( httpConnection.getInputStream()); 
			incomingData = new BufferedReader(connectionStreamReader);
			System.out.println("Connection Encoding: " + connectionStreamReader.getEncoding());
			String inputLine = "";

			while ((inputLine = incomingData.readLine()) != null){
				queriedHTML = queriedHTML + inputLine;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				connectionStreamReader.close();
				incomingData.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return queriedHTML;
	}

	public void initiateQuery(Path outputFileLocation) {
		String	queryUri = "http://sgwiki.com/wiki/Simei_MRT_Station";
		int timeout = 5*1000; //timeout for HTTP connection, in ms

		ParseHTML parsehtml = new ParseHTML();
		Map<String, String> mappedData = new HashMap<String, String>();
		Document queriedHTML = null;

		System.out.println("URI: " + queryUri);

		try {
			queriedHTML = Jsoup.connect(queryUri)
					.data("query", "Java")
					.userAgent("Mozilla/4.76")
					//						  .cookie("auth", "token")
					.timeout(timeout)
					.get();
			//				System.out.println("queried data \n" + queriedHTML.toString());
			mappedData = parsehtml.searchFoVal(queriedHTML.toString());
			System.out.println(mappedData.keySet().toString());

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//		userRequestedData = getRequireDataOnly(stockList, requiredParameters, outputFileLocation);
		//		WriteToCSV.WriteDataToCSV(outputFileLocation, userRequestedData);
	}



	public static void main(String[] args) {
		System.out.println("Starting the scraper...");
		Path outputPath = new File("/home/dineshkp/Desktop/gitlocal").toPath();
		ScraperBase scraper = new ScraperBase();
		scraper.initiateQuery(outputPath);
	}

}
