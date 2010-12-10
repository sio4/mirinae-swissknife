package org.scinix.android.mirinae.swissknife;

import java.util.List;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SwissKnifeAppAdapter extends ArrayAdapter<ApplicationInfo> {
	private final Context context;
	private final List<ApplicationInfo> appInfo;

	public SwissKnifeAppAdapter(Context context, int resId,
			List<ApplicationInfo> appInfo) {
		super(context, R.layout.apps_row, appInfo);
		this.context = context;
		this.appInfo = appInfo;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = LayoutInflater.from(context);
		PackageManager pm = context.getPackageManager();

		View rv = inflater.inflate(R.layout.apps_row, null);
		((TextView) rv.findViewById(R.id.app_name)).setText(appInfo.get(position).loadLabel(pm));
		if (appInfo.get(position).sourceDir.startsWith("/system/app")) {
			((ImageView) rv.findViewById(R.id.tag_system)).setVisibility(ImageView.VISIBLE);
		}
		((TextView) rv.findViewById(R.id.app_packagename)).setText(appInfo.get(position).packageName);
		((TextView) rv.findViewById(R.id.app_filename)).setText(appInfo.get(position).sourceDir.replaceAll(".*/", ""));
		((ImageView) rv.findViewById(R.id.icon)).setImageDrawable(appInfo.get(position).loadIcon(pm));
		return rv;
	}
}
