package com.rss;

import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

//Read in RSS data from specified feed.
public class RssReader 
{
	private String rssUrl;

	// Constructor method assigning target URL.
	public RssReader(String rssUrl) 
	{
		this.rssUrl = rssUrl;
	}

	/*
	 *  Retrieve RSS data with SAXParser.
	 *  Forward the returned list of items to caller.
	 */
	public List<RssItem> getItems() throws Exception 
	{
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser saxParser = factory.newSAXParser();
		RssParseHandler handler = new RssParseHandler();
		saxParser.parse(rssUrl, handler);
		return handler.getItems();
	}
}
