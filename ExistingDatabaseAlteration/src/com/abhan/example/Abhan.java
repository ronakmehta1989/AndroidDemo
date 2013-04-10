package com.abhan.example;

import android.app.Application;

public class Abhan extends Application {
	public static final String TAG = "Abhan";
	public static final boolean DEBUG = true;
	private final boolean DATABASE_ON = true;
	private int androidVersion = 4;
	private DBAdapter dbAdapter = null;
	
	@Override
	public void onCreate() {
		super.onCreate();
		setAndroidVersion(android.os.Build.VERSION.SDK_INT);
		
		if(DATABASE_ON) { 
			if(dbAdapter == null) {
				dbAdapter = new DBAdapter(getBaseContext());
				dbAdapter.createDatabase();
			}
		}
	}

	public int getAndroidVersion() {
		return androidVersion;
	}

	public void setAndroidVersion(int androidVersion) {
		this.androidVersion = androidVersion;
	}
	
	public DBAdapter getDBInstatnce() {
		dbAdapter.open();
		if(DEBUG) {
			android.util.Log.d(TAG, "DataBase Opened.");
		}
		return dbAdapter;
	}

	public void closeDataBase() {
		dbAdapter.close();
		if(DEBUG) {
			android.util.Log.d(TAG, "DataBase Closed.");
		}
	}
}