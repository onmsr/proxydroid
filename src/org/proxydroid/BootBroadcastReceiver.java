package org.proxydroid;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

public class BootBroadcastReceiver extends BroadcastReceiver {

	private static final String BOOT_COMPLETED_ACTION = "android.intent.action.BOOT_COMPLETED";

	@Override
	public void onReceive(Context context, Intent intent) {

		String action = intent.getAction();
		if (action.equals(BOOT_COMPLETED_ACTION)) {
			startProxyDroidService(context);
		}

	}

	private void startProxyDroidService(Context context) {

		SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
		boolean isRunning = settings.getBoolean("isRunning", false);
		if (isRunning) {
			Profile profile = new Profile();
			profile.getProfile(settings);

			Intent it = new Intent(context, ProxyDroidService.class);
			Bundle bundle = new Bundle();
			bundle.putString("host", profile.getHost());
			bundle.putString("user", profile.getUser());
			bundle.putString("bypassAddrs", profile.getBypassAddrs());
			bundle.putString("password", profile.getPassword());
			bundle.putString("domain", profile.getDomain());
			bundle.putString("proxyType", profile.getProxyType());
			bundle.putBoolean("isAutoSetProxy", profile.isAutoSetProxy());
			bundle.putBoolean("isBypassApps", profile.isBypassApps());
			bundle.putBoolean("isAuth", profile.isAuth());
			bundle.putBoolean("isNTLM", profile.isNTLM());
			bundle.putBoolean("isDNSProxy", profile.isDNSProxy());
			bundle.putBoolean("isPAC", profile.isPAC());
			bundle.putInt("port", profile.getPort());
			it.putExtras(bundle);

			context.startService(it);
		}
	}

}
