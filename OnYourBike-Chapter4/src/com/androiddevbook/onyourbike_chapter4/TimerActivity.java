package com.androiddevbook.onyourbike_chapter4;

import com.androiddevbook.onyourbike_chapter4.R;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TimerActivity extends ActionBarActivity {
	
	private static String CLASS_NAME;
	private static long UPDATE_EVERY;
	
	protected TextView counter;
	protected Button start;
	protected Button stop;
	
	protected boolean timerRunning;
	protected long startedAt;
	protected long lastStopped;
	
	protected Handler handler;
	protected UpdateTimer updateTimer;
	
	public TimerActivity(){
		CLASS_NAME = getClass().getName();
	}
	
	class UpdateTimer implements Runnable{
		public void run(){
			//Log.d(CLASS_NAME, "run");
			setTimeDisplay();
			if(handler !=null){
				handler.postDelayed(this, UPDATE_EVERY);
			}
		}
	}
	
	public void clickedStart(View view){
		Log.d(CLASS_NAME, "Clicked Start");
		startedAt=System.currentTimeMillis();
		timerRunning=true;
		setTimeDisplay();
		enableButtons();
		
		handler = new Handler();
		updateTimer = new UpdateTimer();
		handler.postDelayed(updateTimer, UPDATE_EVERY);
	}
	
	public void clickedStop(View view){
		Log.d(CLASS_NAME, "Clicked Stop");
		lastStopped=System.currentTimeMillis();
		timerRunning=false;
		setTimeDisplay();
		enableButtons();
		
		handler.removeCallbacks(updateTimer);
		handler = null;
	}
	
	protected void enableButtons(){
		Log.d(CLASS_NAME, "Set buttons enabled/disabled");
		start.setEnabled(!timerRunning);
		stop.setEnabled(timerRunning);
	}
	
	protected void setTimeDisplay(){
		String display;
		long timeNow;
		long diff;
		long seconds;
		long minutes;
		long hours;
		
		Log.d(CLASS_NAME, "Setting time display");
		
		if (timerRunning){
			timeNow = System.currentTimeMillis();
		}else{
			timeNow = lastStopped;
		}
		
		diff = timeNow - startedAt;
		
		//no negative time
		if (diff<0){
			diff = 0;
		}
		
		seconds = diff/1000;
		minutes = seconds/60;
		hours = minutes/60;
		seconds = seconds%60;
		minutes = minutes%60;
		
		display=String.format("%d", hours)+":"
				+String.format("%02d", minutes)+":"
				+String.format("%02d", seconds);
		counter.setText(display);
	}
	
	@Override
	public void onStart(){
		super.onStart();
		Log.d(CLASS_NAME, "onStart");
		
		if(timerRunning){
			handler = new Handler();
			updateTimer = new UpdateTimer();
			handler.postDelayed(updateTimer, UPDATE_EVERY);
		}
	}
	
	@Override
	public void onPause(){
		super.onPause();
		Log.d(CLASS_NAME, "onPause");
	}
	
	@Override
	public void onResume(){
		super.onResume();
		Log.d(CLASS_NAME, "onResume");
		enableButtons();
		setTimeDisplay();
	}
	
	@Override
	public void onStop(){
		super.onStop();
		Log.d(CLASS_NAME, "onStop");
		
		if(timerRunning){
			handler.removeCallbacks(updateTimer);
			updateTimer = null;
			handler = null;
		}
	}
	
	@Override
	public void onDestroy(){
		super.onDestroy();
		Log.d(CLASS_NAME, "onDestroy");
	}
	
	@Override
	public void onRestart(){
		super.onDestroy();
		Log.d(CLASS_NAME, "onRestart");
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timer);
		
	//	TextView hello = (TextView) findViewById(R.id.timer);
		
		counter = (TextView) findViewById(R.id.timer);
		start = (Button) findViewById(R.id.start_button);
		stop = (Button) findViewById(R.id.stop_button);
		
		Log.d(CLASS_NAME, "Setting Text");
		
		if(BuildConfig.DEBUG){
			StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
			.detectAll().penaltyLog().build());
		StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
		.detectAll().penaltyLog().penaltyDeath().build());	
		}
		
		//hello.setText("On Your Bike");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		Log.d(CLASS_NAME, "Showing Menu");
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
