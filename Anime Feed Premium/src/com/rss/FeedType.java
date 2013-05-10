package com.rss;

/*
 * This class acts as a directory for RSS feeds
 */
public class FeedType 
{
	private String[] newsURL;
	private String[] animeURL;
	private String[] mangaURL;
	
	/*
	 * Constructor method.
	 * Arrays are used to store lists of related RSS URLs for aggregation.
	 * Note: If an array has more than one URL, ensure each item has a pubDate for sorting.
	 */
	public FeedType()
	{	
		newsURL = new String[6];
		newsURL[0] = "http://www.otakuusamagazine.com/Rss.aspx?sn=NewsRSSFeed";
		newsURL[1] = "http://www.animenewsnetwork.co.uk/news/rss.xml";
		newsURL[2] = "http://www.otakunews.com/rss/rss.xml";
		newsURL[3] = "http://feeds.feedburner.com/crunchyroll/animenews";
		newsURL[4] = "http://www.animenation.net/blog/feed/";
		
		animeURL = new String[2];
		animeURL[0] = "http://www.baka-updates.com/rss.php";
	
		mangaURL = new String[2];
		mangaURL[0] = "http://www.mangaupdates.com/rss.php";
	}
	
	/*
	 * Return the requested RSS feed URLs based on message content.
	 */
	public String[] getURL (String str)
	{
		if(str.equalsIgnoreCase("NEWS"))
		{
			return newsURL;
		}
		else if(str.equalsIgnoreCase("ANIME"))
		{
			return animeURL;
		}
		else if(str.equalsIgnoreCase("MANGA"))
		{
			return mangaURL;
		}
		else if(str.equalsIgnoreCase("MY_ANIME"))
		{
			return animeURL;
		}
		else if(str.equalsIgnoreCase("MY_MANGA"))
		{
			return mangaURL;
		}
		return null;
	}
}
