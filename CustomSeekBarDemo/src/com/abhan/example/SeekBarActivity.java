package com.abhan.example;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class SeekBarActivity extends Activity implements
		OnSeekBarChangeListener {
	private final String TAG = SeekBarActivity.class.getSimpleName();
	private SeekBar mSeekBar = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.customseekbar);

		mSeekBar = (SeekBar) findViewById(R.id.customSeekBar);
		mSeekBar.setOnSeekBarChangeListener(this);
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		Log.d(TAG, "Progress: " + progress);
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {}
}