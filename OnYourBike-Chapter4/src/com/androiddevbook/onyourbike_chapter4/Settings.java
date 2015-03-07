package com.androiddevbook.onyourbike_chapter4;


import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

public class Settings {
	private static String CLASS_NAME;
	private static String VIBRATE = "vibrate";
	
	public Settings(){
		CLASS_NAME = getClass().getName();
	}
	
	protected boolean vibrateOn;
	
	public boolean isVibrateOn(Activity activity){
		Log.d(CLASS_NAME, "isVibrateOn");
		
		SharedPreferences preferences = activity
				.getPreferences(Activity.MODE_PRIVATE);

		
		if(preferences.contains(VIBRATE)){
			vibrateOn = preferences.getBoolean(VIBRATE, false);
		}
		
		return vibrateOn;

	}
	
	
	public void setVibrate(Activity activity, boolean vibrate){
		Log.d(CLASS_NAME, "setVibrate");
		
		vibrateOn = vibrate;
		
		SharedPreferences preferences = activity
				.getPreferences(Activity.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putBoolean(VIBRATE, vibrate);
		editor.apply();
	}
}


