package com.rss;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/*
 * Handles XML element tags for SAXParser
 */
public class RssParseHandler extends DefaultHandler 
{
	private List<RssItem> rssItems;
	private RssItem currentItem;
	private boolean parsingTitle;
	private boolean parsingLink;
	private boolean parsingDate;
	
	//Creates list to store multiple items.
	public RssParseHandler() 
	{
		rssItems = new ArrayList<RssItem>();
	}
	
	//Returns list of RSS items.
	public List<RssItem> getItems() 
	{
		return rssItems;
	}
	
	/*
	 * Check each element tag to see if required.
	 * If tag is 'item', create a new item as tag is root.
	 * Otherwise, check for required data content and set flags.
	 */
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException 
	{
		if ("item".equals(qName)) 
		{
			currentItem = new RssItem();
		} 
		else if ("title".equals(qName)) 
		{
			parsingTitle = true;
		} 
		else if ("link".equals(qName)) 
		{
			parsingLink = true;
		}
		else if ("pubDate".equals(qName)) 
		{
			parsingDate = true;
		}
	}
	
	/*
	 * Check each element tag to see if required.
	 * If tag is 'item', the end of the item has been reached.
	 * Otherwise, indicates that end of data string for an element has been reached.
	 */
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException 
	{
		if ("item".equals(qName)) 
		{
			rssItems.add(currentItem);
			currentItem = null;
		} 
		else if ("title".equals(qName)) 
		{
			parsingTitle = false;
		} 
		else if ("link".equals(qName))
		{
			parsingLink = false;
		}
		else if ("pubDate".equals(qName)) 
        {
			parsingDate = false;
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
		else if (parsingLink) 
		{
			if (currentItem != null) 
			{
				currentItem.setLink(new String(ch, start, length));
				parsingLink = false;
			}
		}
		else if (parsingDate) 
		{
			if (currentItem != null) 
			{
				currentItem.setPubDate(new String(ch, start, length));
				parsingDate = false;
			}
		}
	}	
}