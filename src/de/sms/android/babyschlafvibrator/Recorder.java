package de.sms.android.babyschlafvibrator;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;


/**
 * activity to set own voice
 * 
 * @author streeter
 *
 */
public class Recorder extends PreferenceActivity
{
	/** button to record own voice */
	private RecordButton mRecordButton;
	/** button to play own voice */
	private PlayButton mPlayButton;

    @Override
    public void onCreate(Bundle icicle)
    {
        super.onCreate(icicle);

        //set layout
        setContentView(R.layout.recorder);
        
        //get buttons
        mRecordButton = (RecordButton) findViewById(R.id.recordButton);
        mPlayButton = (PlayButton) findViewById(R.id.playButton);

        //set storage location
        RecorderButton.mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
        RecorderButton.mFileName += "/babysleepbuzzer_ownvoice.3gp";

        //add checkbox box
        addPreferencesFromResource(R.xml.preferences);
        
        //save current state of checkbox
        final SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        Preference useOwnVoice = findPreference("pref_useOwnVoice");
        sharedPrefs.edit().putBoolean("pref_useOwnVoice", sharedPrefs.getBoolean("pref_useOwnVoice", false));
        sharedPrefs.edit().commit();
    }

    /**
     * release recorder and player
     */
    @Override
    public void onPause()
    {
        super.onPause();
        if (mRecordButton.getmRecorder() != null)
        {
        	mRecordButton.getmRecorder().release();
        }

        if (mPlayButton.getmPlayer() != null)
        {
        	mPlayButton.getmPlayer().release();
        }
    }


}
