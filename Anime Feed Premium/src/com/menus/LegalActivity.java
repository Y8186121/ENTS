package com.menus;

import com.example.anime_feed.R;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.view.MenuItem;

/*
 * Display's legal information to the user.
 */
public class LegalActivity extends Activity {

	/*
	 * Set new content view and add a back button to the action bar.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_legal);
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true); 		
	}
		
	/*
	 * 	Listener for action bar buttons.
	 *  Create intent from back button to MainMenuActivity.
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		switch(item.getItemId())
		{
			case android.R.id.home:
				Intent intent = new Intent(LegalActivity.this, MainMenuActivity.class);
				   startActivity(intent);
				break;
		}
	   return true;
	}	
}
