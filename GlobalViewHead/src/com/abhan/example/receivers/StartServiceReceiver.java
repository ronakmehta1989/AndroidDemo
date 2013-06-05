package com.abhan.example.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.abhan.example.Abhan;
import com.abhan.example.services.AbhanService;

public class StartServiceReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		if(Abhan.DEBUG) {
			android.util.Log.i(Abhan.TAG, "Service Started");
		}
		Intent service = new Intent(context, AbhanService.class);
		context.startService(service);
	}
}