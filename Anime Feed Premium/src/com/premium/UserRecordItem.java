package com.premium;

/*
 *  This code encapsulates data about an item from the user's media catalogue.
 *  Stores title and watched/read status e.g. 'Plan to Watch' or 'Completed'. 
 */
public class UserRecordItem
{
	private String title;
	private String watched_status;
	
	public String getTitle() 
	{
		return title;
	}
	
	public void setTitle(String title) 
	{
		this.title = title;
	}

	public String getWatchedStatus() 
	{
		return watched_status;
	}

	public void setWatchedStatus(String status) 
	{
		this.watched_status = status;
	}	
}