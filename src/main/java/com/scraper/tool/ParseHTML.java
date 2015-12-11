package com.scraper.tool;


import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @author dineshkp
 *
 */
public class ParseHTML {

	/**
	 * Creates a HTML Doc from the 'String xmlValue' and extracts the required data from the doc file
	 * Uses Jsoup for HTML Parsing (treated as an xml)
	 * 
	 * @param xmlValue String input containing the HTML page
	 * @return mappedData Map of all the required Headers vs Data
	 */
	public Map<String, String> searchFoVal(String xmlValue) {
		Map<String, String> mappedData = new HashMap<String, String>();
		Document doc =Jsoup.parse(xmlValue);
		mappedData = extractReqdDataFrmWikiPg(doc);
		return mappedData;
	}

	/**
	 * Parse the HTML Document to create a map of required Headers vs Data
	 * @param doc Document (Jsoup) HTML file format
	 * @return mappedData Map of all the required Headers vs Data
	 */
	public static Map<String, String> extractReqdDataFrmWikiPg (Document doc) {
		Map<String, String> layoutData = new HashMap<String, String>();
		Integer count =0;
		System.out.println("*********************");
		System.out.println("Extracting the required data from page: ParseHTML.extractReqdDataFrmWikiPg()");
		if (doc.title().isEmpty()) {
			throw new RuntimeException("The HTML is invalid.");
		}
		System.out.println("Document title is: "+doc.title());
		//		Elements tableElement = doc.getElementsByClass("yfi_quote_summary").select("tr"); //This is the Content that we are interested in.
		Element layout = doc.getElementsByAttributeValue("Id", "Layout").first().parent();
		Element afterLayout = layout.nextElementSibling();
		System.out.println(afterLayout);

		
		//		do {
//			tr = afterLayout.select()
//		}
		//		System.out.println(tables.toString());
		/*
		if (tables.isEmpty()) {
			throw new RuntimeException("The extract table was not found. Please check the HTML page.");
		}
		for (Element tableElement : tables) { //stepping through each element of the table
			System.out.println(tables.text());
			System.out.println("Table Header: \n" + tableElement.select("th").text());  //Enable for DEBUG, this prints the table headers
			if (tableElement.hasAttr("rowspan")) {
				count =Integer.parseInt(tableElement.getElementsByAttribute("rowspan").toString());
				String lvl = tableElement.select("th").text();
				System.out.println("Level: " + lvl);
				Elements tRows = tableElement.getElementsByTag("tr");
				for (Element tRow : tRows) {
					System.out.println(tRow.text());
				}
			}*/
		//			mappedData.put(eachTableElement.select("th").text(), eachTableElement.select("td").text());
		//System.out.println("\n\t" + tableElement.select("td").text()); //Enable for DEBUG, this prints the table data
		//		}
		System.out.println("*********************");

		return layoutData;

	}

}
