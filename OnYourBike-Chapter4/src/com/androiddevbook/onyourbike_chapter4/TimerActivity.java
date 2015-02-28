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

	private static long UPDATE_EVERY=200;
	private static String CLASS_NAME;
	
	//display
	protected TextView counter;
	protected Button start;
	protected Button stop;
	protected boolean timerRunning;
	
	//timer
	protected long startedAt;
	protected long lastStopped;
	protected Handler handler;
	protected UpdateTimer updateTimer;
	
	//captures class name
	public TimerActivity(){
		CLASS_NAME=getClass().getName();
	}
	
	//start button
	public void clickedStart(View view){
		Log.d(CLASS_NAME, "Clicked Start Button");
		timerRunning=true;
		enableButtons();
		startedAt = System.currentTimeMillis();
		setTimeDisplay();
		handler = new Handler();
		updateTimer = new UpdateTimer();
		handler.postDelayed(updateTimer, UPDATE_EVERY);
	}
	
	//stop button
	public void clickedStop(View view){
		Log.d(CLASS_NAME, "Clicked Stop Button");
		timerRunning=false;
		enableButtons();
		lastStopped = System.currentTimeMillis();
		setTimeDisplay();
		handler.removeCallbacks(updateTimer);
		handler = null;
	}
	
	@Override
	public void onStart(){
		super.onStart();
		Log.d(CLASS_NAME, "onStart");
	}
	
	//enable buttons
	protected void enableButtons(){
		Log.d(CLASS_NAME, "Set Buttons enabled/disabled");
		start.setEnabled(!timerRunning);
		stop.setEnabled(timerRunning);
	}
	
	@Override
	public void onResume(){
		super.onResume();
		Log.d(CLASS_NAME, "onResume");
	}
	
	@Override
	public void onPause(){
		super.onPause();
		Log.d(CLASS_NAME, "onPause");
	}
	
	@Override
	public void onStop(){
		super.onStop();
		Log.d(CLASS_NAME, "onStop");
	}
	
	@Override
	public void onDestroy(){
		super.onDestroy();
		Log.d(CLASS_NAME, "onDestroy");
	}
	
	@Override
	public void onRestart(){
		super.onRestart();
		Log.d(CLASS_NAME, "onRestart");
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timer);
		
		//TextView hello = (TextView) findViewById(R.id.timer);
		
		counter=(TextView) findViewById(R.id.timer);
		start=(Button) findViewById(R.id.start_button);
		stop=(Button) findViewById(R.id.stop_button);
		
		Log.d(CLASS_NAME, "Setting Text");
		
		if(BuildConfig.DEBUG){
			StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
			.detectAll().penaltyLog().build());
			StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
			.detectAll().penaltyLog().penaltyDeath().build());
		}
		
		//hello.setText("On Your Bike");
	}
	
	//timer
	protected void setTimeDisplay(){
		String display;
		long timeNow;
		long diff;
		long seconds;
		long minutes;
		long hours;
		
		Log.d(CLASS_NAME, "Setting Time Display");
		
		if(timerRunning){
			timeNow=System.currentTimeMillis();
		}else{
			timeNow=lastStopped;
		}
		
		diff = timeNow-startedAt;
		
		//no negative time
		if(diff<0){
			diff=0;
		}
		seconds = diff/1000;
		minutes= seconds/60;
		hours= minutes/60;
		seconds= seconds % 60;
		minutes= minutes %60;
		
		display = String.format("%d", hours)+":"
				+String.format("%02d", minutes)+":"
				+String.format("%02d", seconds);
		counter.setText(display);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		Log.d(CLASS_NAME, "Showing Menu");
		
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
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
