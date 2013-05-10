package com.rss;

import java.util.Collections;
import java.util.List;

import com.example.anime_feed.R;
import com.menus.MainMenuActivity;
import com.premium.UserRecordItem;
import com.premium.UserRecordReader;

import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

/*
 * Main activity for all RSS feed pages
 */
public class RssMainActivity extends Activity 
{	
	public final static String REFRESH_MESSAGE = "url";
	private static final int MENU_REFRESH = 0;
	private String rssSource;
	private RssMainActivity localObject;
	
		/*
		 * Set new Content view and add back button to action bar
		 * Receive message containing source of URLs from main menu
		 * If error, then activity created from refresh, reuse URL source
		 * Create an async task to create RSS feed from source URLs
		 */
		@Override
		public void onCreate(Bundle savedInstanceState) 
		{
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_rss_main);
			localObject = this;			
			ActionBar actionBar = getActionBar();
			actionBar.setDisplayHomeAsUpEnabled(true);
			Intent intent = getIntent();			
			try
			{
				rssSource = intent.getStringExtra(MainMenuActivity.SOURCE_MESSAGE);
			}
			catch (Exception e)
			{
				rssSource = intent.getStringExtra(RssMainActivity.REFRESH_MESSAGE);	
			}		
			GetRSSDataTask task = new GetRSSDataTask();
			FeedType feedType = new FeedType();		
			String temp[] = feedType.getURL(rssSource);
			task.execute(temp); 		
		}
		
		/*
		 * Add refresh button to action bar
		 * If room, display the title of the button
		 */
		@Override
		public boolean onCreateOptionsMenu(Menu menu) 
		{
			menu.add(Menu.NONE, MENU_REFRESH, Menu.NONE, "Refresh")
	       	.setIcon(R.drawable.ic_refresh)
	        .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);  
			return true;
		}
	
		/*
		 * Create listeners for action bar buttons
		 * If refresh is pressed, end current activity to prevent stacking obsolete refreshes
		 */
		@Override
		public boolean onOptionsItemSelected(MenuItem item) 
		{		   
			switch(item.getItemId())
			{
			case android.R.id.home:
				Intent intent = new Intent(RssMainActivity.this, MainMenuActivity.class);
				startActivity(intent);
				finish();
				break;
			case MENU_REFRESH:
				Intent intent2 = new Intent(RssMainActivity.this, RssMainActivity.class); 
				intent2.putExtra(REFRESH_MESSAGE, rssSource); 
				startActivity(intent2);
				finish();
				break;
			}
			return true;
		}
		
	/*
	 * Background task for downloading and processing the RSS feeds
	 * Implements async task to allow other user interaction with app	
	 */
	private class GetRSSDataTask extends AsyncTask<String[], Void, List<RssItem> >
	{		
		ProgressBar prog;
		
		@Override
		protected void onPreExecute() 
		{
			prog = (ProgressBar) findViewById(R.id.progressBar);
			prog.setMax(100);
		}
		
		/*
		 * All RSS URLs passed to rssReader for resolution and then merged
		 */
		@Override
		protected List<RssItem> doInBackground(String[]... urls) 
		{			
			try 
			{
				List<RssItem> finalList;
				RssReader rssReader = new RssReader(urls[0][0]);
				finalList = rssReader.getItems();					// Initialise final array with first (compulsory) reading			
				prog.setProgress(100/(urls[0].length-1)); 			//Increment by first feed as percentage of feeds read
				
				//for any other element, if not null download from url
				int i = 1;
				boolean sort = false;
				while (urls[0][i] != null)
				{
					rssReader = new RssReader(urls[0][i]);
					finalList.addAll(rssReader.getItems());
					
					i += 1;
					prog.setProgress(prog.getProgress()+(100/(urls[0].length-1))); //Increment by percentage of feeds read
					sort = true;
				}
				if (sort)								//if feeds > 1, sort them
				Collections.sort(finalList);			//sort rss feed - most recent first
		
				if (rssSource.equalsIgnoreCase("MY_MANGA"))
				{
					prog.setProgress(50);
					SharedPreferences mySharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
					String my_edittext_preference = mySharedPreferences.getString("Username_Pref", "");
					PremiumFilter(finalList, "mangalist", my_edittext_preference);
					prog.setProgress(100);
				}
				else if (rssSource.equalsIgnoreCase("MY_ANIME"))
				{
					prog.setProgress(50);
					SharedPreferences mySharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
					String my_edittext_preference = mySharedPreferences.getString("Username_Pref", "");
					PremiumFilter(finalList, "animelist", my_edittext_preference);
					prog.setProgress(100);
				}
				return finalList;
			} 
			
			catch (Exception e) 
			{
				e.getMessage();
			}
			return null;
		}
		
		/*
		 *Fixes bugs and passes finalised RSS feed to List View for output
		 */
		@Override
		protected void onPostExecute(List<RssItem> result) 
		{
			if (result.size() != 0)
			{
				for (int i = 0; i < result.size(); i++)
				{
					result.get(i).setTitle(bugFixes(result.get(i).toString()));
				}
				ListView rssList = (ListView) findViewById(R.id.listMainView);
				ArrayAdapter<RssItem> adapter = new ArrayAdapter<RssItem>(localObject,android.R.layout.simple_list_item_1, result);
				rssList.setAdapter(adapter);
				rssList.setOnItemClickListener(new ListListener(result, localObject));
			}
			else
			{
				Toast.makeText(getBaseContext(), "No recent updates found.", Toast.LENGTH_LONG).show();
			}
				prog.setVisibility(View.GONE); 
		}
	
		private List<RssItem> PremiumFilter(List<RssItem> finalList, String catagory, String user)
		{
			List<UserRecordItem> recordList;
			UserRecordReader recordReader = new UserRecordReader("http://mal-api.com/"+catagory+"/"+user+"?format=xml"); 
			try 
			{
				recordList = recordReader.getItems();	
				for (int j = 0; j < recordList.size(); j++)			//Have list only of records watching or planned
				{
					if (recordList.get(j).getWatchedStatus().equalsIgnoreCase("completed"))
					{
						recordList.remove(j);
						j-=1;
					}
					else if (recordList.get(j).getWatchedStatus().equalsIgnoreCase("dropped"))
					{
						recordList.remove(j);
						j-=1;
					}
				}	 
				for (int k = 0; k < finalList.size(); k++)
				{
					for(int l = 0; l < recordList.size(); l++)	//check each user show
					{
						if (finalList.get(k).toString().toLowerCase().contains((recordList.get(l).getTitle().toLowerCase())))		//if rss show relevant keep it
							break;
						else 
						{
							if (l + 1 == recordList.size())		//if not present, remove it
							{
								finalList.remove(k);
								k-=1;							//reduce k to counter index changes
							}
						}
					}
				}
			}
			catch(Exception e)
			{}
			return finalList;
		}
		
		/*
		 * Resolve commonly encountered bugs/presentation issues with RSS titles
		 */
		private String bugFixes(String text)
		{	
			// replace &quot; with "
			for (int i = 0; i < text.length()-5; i++)
			{
				if (text.substring(i,i+6).equalsIgnoreCase("&quot;"))
				{
					text = (text.substring(0,i)+"\""+text.substring(i+6,text.length())); //remove phrase, insert correct
					i += 6; //update counter to reflect reduced string length
				}
			}
			// clarify 'daily briefs'
			if (text.equalsIgnoreCase("Daily Briefs"))		
				text = "AnimeNewsNetwork's Daily Briefs";
			// remove leading space character if present
			if (text.charAt(0) == ' ')
				text = (text.substring(1));
			// capitalise leading char
			if (Character.isLowerCase(text.charAt(0)))
				text = (Character.toUpperCase(text.charAt(0)) + text.substring(1));
			// if appropriate, add missing leading brackets
			if (!rssSource.equalsIgnoreCase("NEWS"))
				if (text.charAt(0) != '[')
					text = "[" + text;
					
			return text;
		}
	}
}