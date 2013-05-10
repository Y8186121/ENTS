package com.menus;

import com.example.anime_feed.R;
import com.rss.RssMainActivity;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

/*
 *   The Activity for the main menu.
 *	 The first activity to be loaded.
 */
public class MainMenuActivity extends Activity 
{
	public final static String SOURCE_MESSAGE = "url";
	private static boolean ONLINE = false;
	private static final int MENU_SETTINGS = 0;
	private static final int MENU_WARNING = 1;
	
	/* 
	 * Creates buttons for navigation.
	 * If navigating to RssMainActivity, provides additional message describing required feed type.
	 * If there is no Internet connection, blocks intents to RssMainActivity to avoid crash.
	 */
	@Override 
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);
		
		Button buttonAnime = (Button) findViewById(R.id.btn_anime);
		buttonAnime.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				if (ONLINE == true)
				{
				Intent intent = new Intent(MainMenuActivity.this, RssMainActivity.class);
				String message = "ANIME";
				intent.putExtra(SOURCE_MESSAGE, message);
				startActivity(intent);
				}
			}
		});
		
		Button buttonManga = (Button) findViewById(R.id.btn_manga);
		buttonManga.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				if (ONLINE == true)
				{
				Intent intent = new Intent(MainMenuActivity.this, RssMainActivity.class);
				String message = "MANGA";
				intent.putExtra(SOURCE_MESSAGE, message);
				startActivity(intent);
				}
			}
		});
		
		Button buttonMyManga = (Button) findViewById(R.id.btn_myM);
		buttonMyManga.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				if (ONLINE == true)
				{
				Intent intent = new Intent(MainMenuActivity.this, RssMainActivity.class);
				String message = "MY_MANGA";
				intent.putExtra(SOURCE_MESSAGE, message);
				startActivity(intent);
				}
			}
		});
		
		Button buttonMyAnime = (Button) findViewById(R.id.btn_myA);
		buttonMyAnime.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				if (ONLINE == true)
				{
				Intent intent = new Intent(MainMenuActivity.this, RssMainActivity.class);
				String message = "MY_ANIME";
				intent.putExtra(SOURCE_MESSAGE, message);
				startActivity(intent);
				}
			}
		});
		
		Button buttonNews = (Button) findViewById(R.id.btn_news);
		buttonNews.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				if (ONLINE == true)
				{
				Intent intent = new Intent(MainMenuActivity.this, RssMainActivity.class);
				String message = "NEWS";
				intent.putExtra(SOURCE_MESSAGE, message);
				startActivity(intent);
				}
			}
		});
		
		Button buttonLegal = (Button) findViewById(R.id.btn_legal);
		buttonLegal.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				Intent intent = new Intent(MainMenuActivity.this, LegalActivity.class);
				startActivity(intent);
			}
		}); 		
	}

	/*
	 * Create action bar buttons.
	 * Set the buttons to display title if space (e.g. on tablet or rotated phone).
	 * Set the warning icon to only appear if there's no Internet connection.
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		menu.add(Menu.NONE, MENU_WARNING, Menu.NONE, "WARNING")
	    .setIcon(R.drawable.ic_warning)
		.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
		MenuItem warning = menu.findItem(MENU_WARNING);
		if(ONLINE==false)
			warning.setVisible(true);
		else
			warning.setVisible(false);	
		
		menu.add(Menu.NONE, MENU_SETTINGS, Menu.NONE, "Settings")
		.setIcon(R.drawable.ic_settings)
		.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
		
		return true;
	}
	
	/*
	 * Create listeners for action bar buttons.
	 * Warning button explain's that there's no Internet.
	 * Settings button creates intent to preferences activity.
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		switch(item.getItemId())
		{
			case MENU_SETTINGS:
				Intent intent = new Intent(MainMenuActivity.this, PrefsActivity.class);
				startActivity(intent);
				break;
			case MENU_WARNING:
				Toast.makeText(this, "NO INTERNET CONNECTION. Please connect to the internet to use this app.", Toast.LENGTH_SHORT).show();
				break;
		}
		return true;
	}
	
	/*
	 * Check to see if Internet availability has changed.
	 * If it has, update the state flag and redraw the action bar.
	 */
	@Override
	protected void onResume()
	{
		super.onResume();
		if (isNetworkConnected() != ONLINE)
		{
			if (ONLINE)	
				ONLINE = false;
			else
				ONLINE = true;
			
			this.invalidateOptionsMenu();  
		}
	}
	
	/*
	 * Check whether WIFI and/or 2/3/4G is available.
	 * Returns false if three are no active networks.
	 */
	private boolean isNetworkConnected() 
	{
		  ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		  NetworkInfo ni = cm.getActiveNetworkInfo();
		  if (ni == null) 
			  return false;	 
		  else
			  return true;
	}
}