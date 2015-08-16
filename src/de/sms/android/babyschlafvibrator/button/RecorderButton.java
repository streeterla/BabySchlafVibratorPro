package de.sms.android.babyschlafvibrator.button;

import java.io.IOException;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.Button;
import de.sms.android.babyschlafvibrator.R;


/**
 * superclass for recorder buttons
 * 
 * @author streeter
 *
 */
public class RecorderButton extends Button
{
	/** for debugging */
    private String LOG_TAG = "RecorderButton";
    
    /** storing location for record file */
    private String mFileName = null;

	/** recorder */
    private MediaRecorder mRecorder = null;

    /** player */
    protected MediaPlayer   mPlayer = null;
    
    /** context of the activity */
    protected Context context;
    

    //constructors
    public RecorderButton(Context context)
    {
		super(context);
        setRecordFile();
		this.context = context;
	}

    public RecorderButton(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        setRecordFile();
        this.context = context;
    }
    
    public RecorderButton(Context context, AttributeSet attrs, int defStyle)
    {
    	super(context, attrs, defStyle);
    	setRecordFile();
    	this.context = context;
    }

    
    /**
     * method to start recording
     * 
     * @param start flag to check whether recording has already started
     */
	protected void onRecord(boolean start)
    {
        if (start) 
        {
            startRecording();
        }
        else
        {
            stopRecording();
        }
    }


	/**
	 * method to start playing
	 */
    protected void startPlaying()
    {
    	setText(context.getString(R.string.stop_playing));
        mPlayer = new MediaPlayer();
        try 
        {
            mPlayer.setDataSource(mFileName);
            mPlayer.prepare();
            mPlayer.start();
            while(mPlayer.isPlaying())
            {
            	Thread.sleep(200);
            }
        } 
        catch (IOException e)
        {
            Log.e(LOG_TAG, "prepare() failed");
        } 
        catch (InterruptedException e) 
        {
        	Log.e(LOG_TAG, "Thread Exception");
			e.printStackTrace();
		}
    }

    
    /**
     * method to stop playing
     */
    protected void stopPlaying() 
    {
    	setText(context.getString(R.string.start_playing));
        mPlayer.release();
        mPlayer = null;
    }

    
    /**
     * method to start recording
     */
    private void startRecording() 
    {
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setOutputFile(mFileName);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try 
        {
            mRecorder.prepare();
        } 
        catch (IOException e)
        {
            Log.e(LOG_TAG, "prepare() failed");
        }

        mRecorder.start();
    }

    
    /**
     * method to stop recording
     */
    private void stopRecording()
    {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
    }
    
    /**
     * sets storage location
     */
	private void setRecordFile() {
		mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
        mFileName += "/babysleepbuzzer_ownvoice.3gp";
	}
    
    //getters and setters
	public String getmFileName() 
	{
		return mFileName;
	}

	public void setmFileName(String mFileName)
	{
		this.mFileName = mFileName;
	}

	public MediaRecorder getmRecorder() 
	{
		return mRecorder;
	}

	public void setmRecorder(MediaRecorder mRecorder)
	{
		this.mRecorder = mRecorder;
	}

	public MediaPlayer getmPlayer()
	{
		return mPlayer;
	}

	public void setmPlayer(MediaPlayer mPlayer)
	{
		this.mPlayer = mPlayer;
	}
	
    public String getLOG_TAG()
    {
		return LOG_TAG;
	}

	public void setLOG_TAG(String lOG_TAG) 
	{
		LOG_TAG = lOG_TAG;
	}

}
