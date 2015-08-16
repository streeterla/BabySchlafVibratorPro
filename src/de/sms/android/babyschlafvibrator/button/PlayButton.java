package de.sms.android.babyschlafvibrator.button;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import de.sms.android.babyschlafvibrator.R;

/**
 * button to play own voice
 * 
 * @author streeter
 *
 */
public class PlayButton extends RecorderButton
{
	/** click listener */
    private OnClickListener clicker = new OnClickListener()
    {
        public void onClick(View v) 
        {
        	try
        	{
	        	startPlaying();
	        	while(mPlayer.isPlaying())
	        	{
	        		Thread.sleep(200);
	        		setText(context.getString(R.string.stop_playing));
	        	}
	        	stopPlaying();
        	}
        	catch(InterruptedException ie)
        	{
        		ie.printStackTrace();
        	}
        }
    };

    
    //constructors
    public PlayButton(Context context)
    {
        super(context);
        setText(context.getString(R.string.start_playing));
        setOnClickListener(clicker);
    }
    
    
    public PlayButton(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        setText(context.getString(R.string.start_playing));
        setOnClickListener(clicker);
    }
    
    
    public PlayButton(Context context, AttributeSet attrs, int defStyle)
    {
    	super(context, attrs, defStyle);
    	setText(context.getString(R.string.start_playing));
        setOnClickListener(clicker);
    }
}
