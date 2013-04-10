package com.abhan.example;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

public class ConnectivityChangedReceiver extends BroadcastReceiver {
	
	public ConnectivityChangedReceiver() {}
	
	@Override
	public void onReceive(Context context, Intent intent) {
        boolean isConnectivity = intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false);
        Abhan.setConnectivity(isConnectivity);
	}
}