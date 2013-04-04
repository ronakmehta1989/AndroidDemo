package com.abhan.example;

import android.app.Activity;
import android.os.Bundle;

import com.abhan.example.adapters.NumericWheelAdapter;
import com.abhan.example.widget.WheelView;

public class IPhoneLikeSpinnerActivity extends Activity 
{
	protected static final String TAG = "IPhoneLikeSpinnerActivity";
	private WheelView mCount, mCountTwo, mCountThree;
	
	@Override
    public void onCreate(Bundle savedInstanceState) 
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        mCount = (WheelView) findViewById(R.id.count);
        mCount.setViewAdapter(new NumericWheelAdapter(this, 0, 9));
        
        mCountTwo = (WheelView) findViewById(R.id.countTwo);
        mCountTwo.setViewAdapter(new NumericWheelAdapter(this, 0, 9));
        
        mCountThree = (WheelView) findViewById(R.id.countThree);
        mCountThree.setViewAdapter(new NumericWheelAdapter(this, 0, 9));
    }
}