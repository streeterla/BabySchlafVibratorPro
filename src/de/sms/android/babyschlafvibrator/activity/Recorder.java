package de.sms.android.babyschlafvibrator.activity;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import de.sms.android.babyschlafvibrator.R;
import de.sms.android.babyschlafvibrator.button.PlayButton;
import de.sms.android.babyschlafvibrator.button.RecordButton;


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
        mRecordButton =  (RecordButton) findViewById(R.id.recordButton);
        mPlayButton = (PlayButton) findViewById(R.id.playButton);

        //add checkbox box
        getFragmentManager().beginTransaction()
        .replace(R.id.frame_containerone, new UseOwnVoiceFragment())
        .commit();
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
