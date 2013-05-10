package com.premium;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/*
 * Handles XML element tags for SAXParser
 */
public class UserRecordParseHandler extends DefaultHandler 
{
	private List<UserRecordItem> animeItems;
	private UserRecordItem currentItem;
	private boolean parsingTitle;
	private boolean parsingWatchedStatus;
	
	//Creates list to store multiple items.
	public UserRecordParseHandler() 
	{
		animeItems = new ArrayList<UserRecordItem>();
	}
	
	//Returns list of catalogued media.
	public List<UserRecordItem> getItems() 
	{
		return animeItems;
	}
	
	/*
	 * Check each element tag to see if required.
	 * If tag is 'Anime', create a new item as tag is root.
	 * Otherwise, check for required data content and set flags.
	 */
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException 
	{
		if ("anime".equals(qName)) 
		{
			currentItem = new UserRecordItem();
		} 
		else if ("title".equals(qName)) 
		{
			parsingTitle = true;
		} 
		else if ("watched_status".equals(qName)) 
		{
			parsingWatchedStatus = true;
		}
		else if ("read_status".equals(qName)) 
		{
			parsingWatchedStatus = true;
		}
	}
	
	/*
	 * Check each element tag to see if required.
	 * If tag is 'Anime', the end of the item has been reached.
	 * Otherwise, indicates that end of data string for an element has been reached.
	 */
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException 
	{
		if ("anime".equals(qName)) 
		{
			animeItems.add(currentItem);
			currentItem = null;
		} 
		else if ("title".equals(qName)) 
		{
			parsingTitle = false;
		} 
		else if ("watched_status".equals(qName))
		{
			parsingWatchedStatus = false;
		}
		else if ("read_status".equals(qName)) 
		{
			parsingWatchedStatus = true;
		}
	}
	
	/*
	 * If an element with required data has been found, read in characters.
	 * Assign the string between the open and close tags to item's variables.
	 */
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException 
	{
		if (parsingTitle) 
		{
			if (currentItem != null)
			{
				currentItem.setTitle(new String(ch, start, length));
			}
		} 
		else if (parsingWatchedStatus) 
		{
			if (currentItem != null) 
			{
				currentItem.setWatchedStatus(new String(ch, start, length));
				parsingWatchedStatus = false;
			}
		}
	}	
}