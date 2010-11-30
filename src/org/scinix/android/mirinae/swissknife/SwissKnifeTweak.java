package org.scinix.android.mirinae.swissknife;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class SwissKnifeTweak extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		TextView textview = new TextView(this);
		textview.setText("No tweaks implemented yet.");
		setContentView(textview);
	}
}
