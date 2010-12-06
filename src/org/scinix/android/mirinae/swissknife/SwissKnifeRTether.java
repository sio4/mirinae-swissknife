package org.scinix.android.mirinae.swissknife;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SwissKnifeRTether extends Activity implements OnClickListener {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rtether);

		Method spGet;
		String wifiStatus = new String ();
		String pdpStatus = new String ();
		try {
			spGet = Class.forName("android.os.SystemProperties").getMethod("get", String.class);

			if (((String) spGet.invoke(null, "wlan.supplicant.status")).equals("connected")) {
				wifiStatus = "WiFi Network connected.";
			} else if (((String) spGet.invoke(null, "wlan.driver.status")).equals("ok")) {
				wifiStatus = "WiFi Network enabled.";
			} else {
				wifiStatus = "WiFi Network disabled.";
			}

			if (((String) spGet.invoke(null, "gsm.defaultpdpcontext.active")).equals("true")) {
				if (((String) spGet.invoke(null, "gsm.network.type")).equals("HSDPA")) {
					pdpStatus = "Data Network enabled, HSDPA.";
				} else if (((String) spGet.invoke(null, "gsm.network.type")).equals("UMTS")) {
					pdpStatus = "Data Network enabled, UMTS.";
				}
			} else {
				pdpStatus = "Data Network disabled.";
			}

			Toast.makeText(this, "Current status:\n   " + wifiStatus + "\n   " + pdpStatus,
					Toast.LENGTH_LONG).show();

			String dns1 = (String) spGet.invoke(null, "net.dns1");
			if (dns1.length() > 7) {		/* need more validations? */ 
				((EditText) findViewById(R.id.rtether_dns)).setText(dns1);
				Toast.makeText(this, "Using current dns : " + dns1, Toast.LENGTH_LONG).show();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		/* get peer address from dnsmasq's log message. */
		try {
			String line;
			String cmd = "cat /data/misc/dhcp/dnsmasq.leases |busybox cut -d' ' -f3";
			Process p = Runtime.getRuntime().exec(new String[] {"su", "-c", cmd});
			BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
			while ((line = input.readLine()) != null) {
				Log.d("SwissKnife", "### PEER address: " + line);
				((EditText) findViewById(R.id.rtether_gateway)).setText(line);
			}
			input.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Button btnApply = (Button) findViewById(R.id.btn_apply);
		Button btnReset = (Button) findViewById(R.id.btn_reset);
		btnApply.setOnClickListener(this);
		btnReset.setOnClickListener(this);
	}

	public void onClick(View v) {
		if (v.getId() == R.id.btn_reset) {
			((EditText) findViewById(R.id.rtether_dns)).setText(R.string.rtether_dns_value);
			return;
		}

		String v_gw = ((TextView) findViewById(R.id.rtether_gateway)).getText().toString();
		String v_dns = ((TextView) findViewById(R.id.rtether_dns)).getText().toString();
		String cmd =
			"busybox route delete default ;"
			+ "busybox route add default gw " + v_gw + ";"
			+ "setprop net.dns1 " + v_dns;

		Toast.makeText(this, R.string.rtether_apply_toast, Toast.LENGTH_LONG).show();
		try {
			String line;
			Process p = Runtime.getRuntime().exec(new String[] {"su", "-c", cmd});
			BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
			while ((line = input.readLine()) != null) {
				Log.d("SwissKnife", "### OUT " + line);
			}
			input.close();
			BufferedReader error = new BufferedReader(new InputStreamReader(p.getErrorStream()));
			while ((line = error.readLine()) != null) {
				Log.d("SwissKnife", "### EEE " + line);
			}
			error.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
