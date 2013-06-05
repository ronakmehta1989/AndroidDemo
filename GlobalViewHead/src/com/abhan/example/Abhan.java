package com.abhan.example;

import android.app.Application;
import android.content.IntentFilter;

import com.abhan.example.receivers.StartServiceReceiver;

public class Abhan extends Application {

	public static final String TAG = Abhan.class.getSimpleName();
	public static final boolean DEBUG = true;
	private static boolean isConnectivity = false;
	private int androidVersion = 4;
	private StartServiceReceiver startServiceReceiver;

	@Override
	public void onCreate() {
		super.onCreate();
		setAndroidVersion(android.os.Build.VERSION.SDK_INT);
	}

	public int getAndroidVersion() {
		return androidVersion;
	}

	public void setAndroidVersion(int androidVersion) {
		this.androidVersion = androidVersion;
	}

	public void registerAllReceivers() {
		startServiceReceiver = new StartServiceReceiver();
		registerReceiver(startServiceReceiver, new IntentFilter("android.intent.action.BOOT_COMPLETED"));
		if(DEBUG) {
			android.util.Log.w(TAG, "All receivers are registerd.");
		}
	}

	public void unRegisterAllReceivers() {
		unregisterReceiver(startServiceReceiver);
		if(DEBUG) {
			android.util.Log.w(TAG, "All receivers are unregisterd.");
		}
	}

	public static void setConnectivity(final boolean isConnectivity) {
		Abhan.isConnectivity = isConnectivity;
	}

	public static boolean getConnectivity() {
		return isConnectivity;
	}
}