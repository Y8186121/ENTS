package com.premium;

import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

// Read in catalogue data from website API.
public class UserRecordReader 
{
	private String recordUrl;

	// Constructor method assigning target URL.
	public UserRecordReader(String RecordUrl) 
	{
		this.recordUrl = RecordUrl;
	}

	/*
	 *  Retrieve user catalogue data with SAXParser.
	 *  Forward the returned list of items to caller.
	 */
	public List<UserRecordItem> getItems() throws Exception 
	{
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser saxParser = factory.newSAXParser();
		UserRecordParseHandler handler = new UserRecordParseHandler();
		saxParser.parse(recordUrl, handler);
		return handler.getItems();
	}
}
