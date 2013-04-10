package com.abhan.example;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity {
	private TextView txtStatus = null;
	private Abhan abhan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        abhan = (Abhan) this.getApplication();
        txtStatus = (TextView) findViewById(R.id.main_txtStatus);
        txtStatus.setText(getString(R.string.app_name));
        final String record = abhan.getDBInstatnce().getValue("event");
        if(Abhan.DEBUG) {
        	android.util.Log.i(Abhan.TAG, "record: " + record);
        }
    }
    
    @Override
    protected void onStop() {
    	super.onStop();
    	abhan.closeDataBase();
    }
}