package org.scinix.android.mirinae.swissknife;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class SwissKnifeApps extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		TextView textview = new TextView(this);
		textview.setText("This is the Apps Tab");
		setContentView(textview);
	}
}
