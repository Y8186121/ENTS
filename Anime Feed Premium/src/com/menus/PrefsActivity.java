package com.menus;

import com.premium.PrefsFragment;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

/*
 * Display's user preferences.
 */
public class PrefsActivity extends Activity
{
	/*
	 * Add a back button to the action bar.
	 * Set content to a preference fragment to retrieve/store data.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true); 	
		getFragmentManager().beginTransaction().replace(android.R.id.content, new PrefsFragment()).commit();
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
				Intent intent = new Intent(PrefsActivity.this, MainMenuActivity.class);
				   startActivity(intent);
				break;
		}
	   return true;
	}
}
