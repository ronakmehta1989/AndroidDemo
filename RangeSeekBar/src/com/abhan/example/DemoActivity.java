package com.abhan.example;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

import com.abhan.example.RangeSeekBar.OnRangeSeekBarChangeListener;

public class DemoActivity extends Activity {

	private static final String TAG = DemoActivity.class.getSimpleName();
	private LinearLayout layoutContainer;
	private RangeSeekBar<Long> seekBarLongDate;
	private RangeSeekBar<Double> seekBarDouble;
	private RangeSeekBar<Integer> seekBarInteger;
	private RangeSeekBar<Short> seekBarShort;
	private Date minDate, maxDate;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        
        layoutContainer = (LinearLayout) findViewById(R.id.container);
        
        try {
			minDate = new SimpleDateFormat("MM/dd/yyyy").parse("01/01/1970");
			maxDate = new Date();
		}
		catch (ParseException exception) {
			exception.printStackTrace();
		}
        seekBarLongDate = new RangeSeekBar<Long>(minDate.getTime(), maxDate.getTime(), DemoActivity.this);
        seekBarLongDate.setOnRangeSeekBarChangeListener(new OnRangeSeekBarChangeListener<Long>() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar, Long minValue, Long maxValue) {
                    Log.i(TAG, "FromDate: " + new SimpleDateFormat("MM/dd/yyyy").format(new Date(minValue)) + ", ToDate: " + new SimpleDateFormat("MM/dd/yyyy").format(new Date(maxValue)));
            }
        });
        layoutContainer.addView(seekBarLongDate);
        
        seekBarDouble = new RangeSeekBar<Double>(0.01, 0.10, DemoActivity.this);
        seekBarDouble.setOnRangeSeekBarChangeListener(new OnRangeSeekBarChangeListener<Double>() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar, Double minValue, Double maxValue) {
            	DecimalFormat decimalFormat = new DecimalFormat("#.##");
            	Log.i(TAG, "MinValue: " + decimalFormat.format(minValue) + ", MaxValue: " + decimalFormat.format(maxValue));
            }
        });
        layoutContainer.addView(seekBarDouble);
        
        seekBarInteger = new RangeSeekBar<Integer>(1, 100, DemoActivity.this);
        seekBarInteger.setOnRangeSeekBarChangeListener(new OnRangeSeekBarChangeListener<Integer>() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar, Integer minValue, Integer maxValue) {
            	Log.i(TAG, "MinValue: " + minValue + ", MaxValue: " + maxValue);
            }
        });
        layoutContainer.addView(seekBarInteger);
        
        seekBarShort = new RangeSeekBar<Short>((short)1, (short)10, DemoActivity.this);
        seekBarShort.setOnRangeSeekBarChangeListener(new OnRangeSeekBarChangeListener<Short>() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar, Short minValue, Short maxValue) {
            	Log.i(TAG, "MinValue: " + minValue + ", MaxValue: " + maxValue);
            }
        });
        layoutContainer.addView(seekBarShort);
    }
}