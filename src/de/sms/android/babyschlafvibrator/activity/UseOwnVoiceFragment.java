package de.sms.android.babyschlafvibrator.activity;

import de.sms.android.babyschlafvibrator.R;
import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * Fragment to choose whether to use the own voice
 * 
 * @author streeter
 *
 */
public class UseOwnVoiceFragment extends PreferenceFragment
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
        //add checkbox box
        addPreferencesFromResource(R.xml.preferences);
	}
}
