package de.sms.android.babyschlafvibrator.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.Surface;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import de.sms.android.babyschlafvibrator.R;
import de.sms.android.babyschlafvibrator.countdown.BabyCountDownTimer;
import de.sms.android.babyschlafvibrator.countdown.CountdownPicker;


/**
 * main activity
 * 
 * @author streeter
 *
 */
public class MainActivity extends Activity 
{
	/** vibrator */
	private Vibrator myVib;
	/** player */
	final MediaPlayer mPlayer = new MediaPlayer();
	/** picker to set hours */
	private CountdownPicker hourPicker;
	/** picker to set minutes */
	private CountdownPicker minutePicker;
	/** picker to set seconds */
	private CountdownPicker secondPicker;
	/** button to start buzzer */
	private Button startButton;
	/** button to stop buzzer */
	private Button stopButton;
	/** button to exit app */
	private Button exitButton;
	/** hours to buzz */
	private int hour;
	/** minutes to buzz */
	private int minute;
	/** seconds to buzz */
	private int second;
	/** total seconds  to  buzz */
	private int totalSeconds;
	/** show countdown */
	private TextView countdown;
	/** show toast */
	private Toast toast;
	/** progress bar */
	private ProgressBar mProgress;
	/** timer for own voice */
	private BabyCountDownTimer babyTimer;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		
		//layout
		setContentView(R.layout.activity_main);
		
		//preferences
		final SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
		
		//pickers
		hourPicker = (CountdownPicker) findViewById(R.id.hour_picker);
		minutePicker = (CountdownPicker) findViewById(R.id.minute_picker);
		secondPicker = (CountdownPicker) findViewById(R.id.second_picker);
		
		//countdown
		countdown = (TextView) findViewById(R.id.countdown);
		countdown.setVisibility(TextView.GONE);
		
		 String mFileName = Environment.getExternalStorageDirectory().getAbsolutePath() + "/babysleepbuzzer_ownvoice.3gp";
	     try 
	     {
	    	 mPlayer.setDataSource(mFileName);
	    	 mPlayer.prepare();
	     } 
	     catch (Exception e)
	     {
			 toast = Toast.makeText(getApplicationContext(), getText(R.string.error_own_voice), Toast.LENGTH_SHORT);
			 toast.show();
		 }
		
		getUI();
		
		startButton.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				getNumbers();
				totalSeconds = hour*3600 + minute*60 + second;
				countdown.setVisibility(TextView.VISIBLE);
				if(mProgress != null)
				{
					mProgress.setMax(totalSeconds);
					mProgress.setProgress(totalSeconds);
				}
				babyTimer = new BabyCountDownTimer(totalSeconds*1000+1000, 1000L, countdown, toast, getApplicationContext(), getText(R.string.good_night), getString(R.string.countdown), mPlayer, mProgress);
				babyTimer.start();
				 if(!sharedPrefs.getBoolean("pref_useOwnVoice", false))
				 {
					 toast = Toast.makeText(getApplicationContext(), getText(R.string.vibration), Toast.LENGTH_SHORT);
					 toast.show();
					 myVib.vibrate(totalSeconds * 1000);
				 }
				 else
				 {
						 toast = Toast.makeText(getApplicationContext(), getText(R.string.own_voice), Toast.LENGTH_SHORT);
						 toast.show();

			             mPlayer.setLooping(true);
			             mPlayer.start();
			             if(mProgress != null)
			             {
			            	 mProgress.setMax(totalSeconds);
			            	 mProgress.setProgress(totalSeconds);
			             }
				 }
			}
		});
		stopButton.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) 
			{
				myVib.cancel();
		        countdown.setVisibility(TextView.GONE);
		        toast = Toast.makeText(getApplicationContext(), getText(R.string.good_night), Toast.LENGTH_LONG);
		        toast.show();
		        babyTimer.onFinish();
		        if(mProgress != null)
	        	{
		        	mProgress.setProgress(0);
	        	}
				clearCountdown();
			}
		});
		exitButton.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) 
			{
				babyTimer.onFinish();
				finish();
			}
		});
		
	}
	
	
	/**
	 * destroy ad
	 */
	@Override
	 public void onDestroy()
	 {
		Log.i("MainActivity", "destroy");

	    if(mPlayer != null)
	    {
	    	mPlayer.release();
	    }
	    super.onDestroy();
	 }
	
	
	  /**
	   * pause ad
	   */
	  @Override
	  public void onPause() 
	  {
		Log.i("MainActivity", "pause");
	    super.onPause();
	  }

	  
	/**
	 * create menu
	 */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {
    	//settings
        getMenuInflater().inflate(R.menu.activity_menu, menu);

        //record settings
        MenuItem recorderItem = menu.getItem(0);
        Intent recorderIntent = new Intent(this, Recorder.class);
        recorderItem.setIntent(recorderIntent);
        
        //exit
        MenuItem exitItem = menu.getItem(1);
        exitItem.setOnMenuItemClickListener(new OnMenuItemClickListener()
        {
			
			public boolean onMenuItemClick(MenuItem item) 
			{
				finish();
				return false;
			}
		});
        return true;
    }
	
	
    /**
     * get UI
     */
	private void getUI()
	{
		startButton = (Button) findViewById(R.id.startButton);
		stopButton = (Button) findViewById(R.id.stopButton);
		exitButton = (Button) findViewById(R.id.exitButton);
		myVib = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);
		if(!isLandscape(this))
		{
			mProgress = (ProgressBar) findViewById(R.id.progress);
		}
		
		hourPicker.setTimeUnit(R.string.hour);
		minutePicker.setTimeUnit(R.string.minute);
		secondPicker.setTimeUnit(R.string.second);
	}
	
	
	/**
	 * get duration
	 */
	private void getNumbers()
	{
		hour = hourPicker.getNumber();
		minute = minutePicker.getNumber();
		second = secondPicker.getNumber();
	}
	
	
	/**
	 * cleanup
	 */
	private void clearCountdown()
	{
		countdown.setText("0");
		countdown.setVisibility(TextView.GONE);
		hourPicker.setNumber(0);
		minutePicker.setNumber(0);
		secondPicker.setNumber(0);
	}
	
	
	/**
	 * 
	 * @param context
	 * @return true if device is in landscape mode, false if not
	 */
    private boolean isLandscape(Context context)
    {
    	final int rotation = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getRotation();
        if(rotation == Surface.ROTATION_90 || rotation == Surface.ROTATION_270)  
        {
        	return true;
        }
        return false;
    }
}
