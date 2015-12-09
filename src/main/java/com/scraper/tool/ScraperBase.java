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
		URI queryUri=null;
		try {
			queryUri = new URI("");
		} catch (URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		int timeout = 5*1000; //timeout for HTTP connection, in ms

		ParseHTML parsehtml = new ParseHTML();
		Map<String, String> mappedData = new HashMap<String, String>();
//		InputStreamReader connectionStreamReader = null;

			String queriedHTML = "";

			System.out.println("URI: " + queryUri.toString());
			//Ensure that all the Query Codes are set for each Stock
			//The query codes will be used to identify the <key, value> pair from the Map mappedData <String, String>()

			try {
				HttpURLConnection httpConnection = (HttpURLConnection) queryUri.toURL().openConnection();
				httpConnection.setConnectTimeout(timeout);
				httpConnection.setRequestMethod("GET");
				httpConnection.addRequestProperty("User-Agent", "Mozilla/4.76");
				
				queriedHTML = queryHTML(httpConnection);
//				System.out.println("queried data \n" + queriedHTML.toString());
				mappedData = parsehtml.searchFoVal(queriedHTML);
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
