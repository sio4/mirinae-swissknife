package org.scinix.android.mirinae.swissknife;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SwissKnifeApps extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.apps_list);

		ArrayList<String> appArrayList = new ArrayList<String>();

		/* get apps list from directory. but we need XML for it maybe. */
		File appDir = new File("/system/app");
		if (appDir.exists() && appDir.isDirectory()) {
			ArrayList<File> appFileArrayList = new ArrayList<File>(Arrays.asList(appDir.listFiles()));
			Iterator<File> e = appFileArrayList.iterator();
			while (e.hasNext()) {
				appArrayList.add((e.next()).getName());
			}
		}
		ListView listView = (ListView) findViewById(R.id.apps_list);
		listView.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_multiple_choice,
				appArrayList));

		listView.setItemsCanFocus(false);
		listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
	}
}
