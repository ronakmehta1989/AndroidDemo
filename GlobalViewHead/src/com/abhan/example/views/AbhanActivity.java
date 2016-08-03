package com.abhan.example.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.abhan.example.R;
import com.abhan.example.services.AbhanService;

public class AbhanActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_abhan);
	}

	@Override
	protected void onStart() {
		super.onStart();
		startService(new Intent(AbhanActivity.this, AbhanService.class));
	}
}