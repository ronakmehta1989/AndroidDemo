package com.abhan.example;

import android.app.Application;
import android.content.IntentFilter;
import android.net.ConnectivityManager;

public class Abhan extends Application {
	public static final String TAG = "Abhan";
	public static final boolean DEBUG = true;
	private static boolean isConnectivity = false;
	private int androidVersion = 4;
	private String progressMessage = "";
	private String serverResponse = null;
	private String serverMessage = null;
	private boolean isServerResValid = false;
	private ConnectivityChangedReceiver connectivityChangedReceiver;
	
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
	
	public String getProgressMessage() {
		return progressMessage;
	}

	public void setProgressMessage(String progressMessage) {
		this.progressMessage = progressMessage;
	}
	
	public String getServerResponse() {
		return serverResponse;
	}
	
	public void setServerResponse(String serverResponse) {
		this.serverResponse = serverResponse;
	}

	public String getServerMessage() {
		return serverMessage;
	}

	public void setServerMessage(String serverMessage) {
		this.serverMessage = serverMessage;
	}
	
	public boolean isServerResValid() {
		return isServerResValid;
	}

	public void setServerResValid(boolean isServerResValid) {
		this.isServerResValid = isServerResValid;
	}

	public boolean isHoneycombLaterVersion() {
		if(getAndroidVersion() < 11) {
			return false;
		} else {
			return true;
		}
	}
	
	public void registerAllReceivers() {
		connectivityChangedReceiver = new ConnectivityChangedReceiver();
		registerReceiver(connectivityChangedReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
		if(DEBUG) {
			android.util.Log.d(TAG, "All receivers are registerd.");
		}
	}
	
	public void unRegisterAllReceivers() {
		unregisterReceiver(connectivityChangedReceiver);
		if(DEBUG) {
			android.util.Log.d(TAG, "All receivers are unregisterd.");
		}
	}
	
	public static void setConnectivity(final boolean isConnectivity) {
		Abhan.isConnectivity = isConnectivity;
	}
	
	public static boolean getConnectivity() {
		return isConnectivity;
	}
}