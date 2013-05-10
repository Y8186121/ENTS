package com.premium;

import com.example.anime_feed.R;

import android.os.Bundle;
import android.preference.PreferenceFragment;

// Retrieve preference data.
public class PrefsFragment extends PreferenceFragment 
{
	 // Retrieve preference data from XML
	 @Override
	 public void onCreate(Bundle savedInstanceState) 
	 {
		 super.onCreate(savedInstanceState);
	 	 addPreferencesFromResource(R.xml.prefs);
	 }

}
