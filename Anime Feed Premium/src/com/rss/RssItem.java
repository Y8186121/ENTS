package com.rss;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/*
 *  This code encapsulates RSS item data.
 *  Stores title, publication date and URL of each item.
 */
public class RssItem implements Comparable<RssItem>
{
	private String title;
	private String link;
	private Calendar pubDate;
	
	public String getTitle() 
	{
		return title;
	}
	
	public void setTitle(String title) 
	{
		this.title = title;
	}

	public String getLink() 
	{
		return link;
	}

	public void setLink(String link) 
	{
		this.link = link;
	}
	
	public Calendar getPubDate()
	{
		return pubDate;
	}

	/*
	 * Converts the string date read from the parser into Java format.
	 * Try-Catches account for varying RSS date formats encountered.
	 * Most detailed date formats tested first, most general last.
	 */
	public void setPubDate(String date) 
	{
		try 
		{
			pubDate = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z"); //Try to get timezone
			pubDate.setTime(sdf.parse(date));
		}
		catch (ParseException e1)
		{
			try 
			{
				pubDate = Calendar.getInstance();
				SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss"); //if not present take date and time
				pubDate.setTime(sdf.parse(date));
			} 
			catch (ParseException e2) 
			{
				try 
				{ 
					SimpleDateFormat sdf2 = new SimpleDateFormat("EEE, dd MMM yyyy"); //if not present take the date
					pubDate.setTime(sdf2.parse(date));
				}
				catch (ParseException e3) 
				{ }	//Nothing worth recording	
			}
		}
	}
	
	/*
	 * Override toString method for array to listView adapter.
	 */
	@Override
	public String toString() 
	{
		return getTitle();
	}

	/*
	 * Override comparison mechanism to use publication date.
	 * Invert order of comparisons to place most recent items first in Collections.sort().
	 */
	@Override
	public int compareTo(RssItem compareItem) 
	{
		if(getPubDate().compareTo(compareItem.getPubDate()) == 0)
			return 0;
		else if(getPubDate().compareTo(compareItem.getPubDate()) < 0)
			return 1;
		else
			return -1;
	}
}