package org.scinix.android.mirinae.swissknife;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.app.ListActivity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class SwissKnifeApps extends ListActivity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.apps_list);

		ArrayList<String> appArrayList = new ArrayList<String>();

		PackageManager pm = getPackageManager();
		/* get apps list from directory. but we need XML for it maybe. */
		List<ApplicationInfo> ais = pm.getInstalledApplications(PackageManager.GET_META_DATA);
		Toast.makeText(this, "loading " + ais.size() + " apps...", Toast.LENGTH_LONG).show();
		Iterator<ApplicationInfo> e = ais.iterator();
		while (e.hasNext()) {
			ApplicationInfo ai = e.next();
			String val = (String) ai.loadLabel(pm) + "\n" + ai.sourceDir;
			if (val != null) {
				appArrayList.add(val);
			}
		}

		setListAdapter(new SwissKnifeAppAdapter(this, R.layout.apps_row, ais));
		final ListView listView = getListView();
		listView.setItemsCanFocus(false);
		listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

		//		R.layout.apps_row, R.id.ctv, appArrayList));
		//listView.setItemsCanFocus(false);
		//listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

		//listView.setOnItemClickListener(this);
		//listView.setOnItemLongClickListener(this);
		//listView.setOnItemSelectedListener(this);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		// Get the item that was clicked
		Toast.makeText(this, "" + l.getCheckItemIds()[0], Toast.LENGTH_LONG).show();
		ApplicationInfo ai = (ApplicationInfo) this.getListAdapter().getItem(position);
		Toast.makeText(this, "Package Name: " + ai.packageName, Toast.LENGTH_LONG).show();
	}

	protected void onListItemSelected(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		// Get the item that was clicked
		Object o = this.getListAdapter().getItem(position);
		String keyword = o.toString();
		Toast.makeText(this, "You selected: " + keyword,
				Toast.LENGTH_SHORT).show();

		ApplicationInfo ai = (ApplicationInfo) this.getListAdapter().getItem(position);
		Toast.makeText(this, "Package Name: " + ai.packageName, Toast.LENGTH_LONG).show();
	}


}
