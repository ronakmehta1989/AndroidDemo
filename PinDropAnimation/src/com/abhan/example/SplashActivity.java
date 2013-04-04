package com.abhan.example;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

public class SplashActivity extends Activity implements Runnable
{
	private final String TAG = SplashActivity.class.getSimpleName();
	private final int SPLASH_TIMEOUT = 2000;
	private Thread mSplashThread;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        
        mSplashThread = new Thread(SplashActivity.this); 
        mSplashThread.start();
    }

    public void run() 
	{
    	try {
			int waited = 0;
			while (waited < SPLASH_TIMEOUT) {
				Thread.sleep(100);
				waited += 100;
			}
	    	
			Intent intent = new Intent(SplashActivity.this, PinDropActivity.class);
    		intent.setFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS | Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_CLEAR_TOP);
    		startActivity(intent);
    		SplashActivity.this.finish();
		} catch (InterruptedException e) {
			Log.e(TAG, e.toString());
		}
	}
    
    @Override
	public boolean onKeyDown(int keyCode, KeyEvent keyEvent) {
		if (!(android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.DONUT)
				&& keyCode == KeyEvent.KEYCODE_BACK
				&& keyEvent.getRepeatCount() == 0) {
			Log.i(TAG, "PinDrop Animation In Map");
		}
		return false;
	}
}