package org.scinix.android.mirinae.swissknife;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;

public class SwissKnife extends TabActivity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		Resources res = getResources();
		TabHost tabHost = getTabHost();
		TabHost.TabSpec spec;
		Intent intent;

		intent = new Intent().setClass(this, SwissKnifeApps.class);
		spec = tabHost.newTabSpec("apps").setIndicator("Apps",
				res.getDrawable(R.drawable.ic_tab_apps)).setContent(intent);
		tabHost.addTab(spec);

		intent = new Intent().setClass(this, SwissKnifeTweak.class);
		spec = tabHost.newTabSpec("tweak").setIndicator("Tweak",
				res.getDrawable(R.drawable.ic_tab_tweak)).setContent(intent);
		tabHost.addTab(spec);

		intent = new Intent().setClass(this, SwissKnifeRTether.class)
				.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		spec = tabHost.newTabSpec("usbconn").setIndicator("R Tether",
				res.getDrawable(R.drawable.ic_tab_rtether)).setContent(intent);
		tabHost.addTab(spec);
		tabHost.setCurrentTab(0);
	}
}