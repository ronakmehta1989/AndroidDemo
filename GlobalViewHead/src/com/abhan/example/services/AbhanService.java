package com.abhan.example.services;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.abhan.example.R;

public class AbhanService extends Service {

	private WindowManager windowManager;
	private ImageView globalView;
	private boolean inDragMode;
	private int screenWidth, screenHight;
	private int initX;
	private int initY;
	private int initViewX, initViewY;
	private int newX, newY;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
		DisplayMetrics displaymetrics = new DisplayMetrics();
		windowManager.getDefaultDisplay().getMetrics(displaymetrics);
		screenWidth = displaymetrics.widthPixels;
		screenHight = displaymetrics.heightPixels;
		inDragMode = false;
		globalView = new ImageView(this);
		globalView.setImageResource(R.drawable.ic_launcher);
		final WindowManager.LayoutParams params = new WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.TYPE_PHONE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, PixelFormat.TRANSLUCENT);
		params.gravity = Gravity.TOP | Gravity.LEFT;
		params.x = 0;
		params.y = 0;
		globalView.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View view, MotionEvent event) {
				switch(event.getAction()) {
					case MotionEvent.ACTION_DOWN:
						inDragMode = true;
						initX = params.x;
						initY = params.y;
						initViewX = Math.abs((int) event.getRawX() - ((ImageView) view).getLeft());
						initViewY = Math.abs((int) event.getRawY() - ((ImageView) view).getTop());
					case MotionEvent.ACTION_UP:
						inDragMode = false;
					case MotionEvent.ACTION_MOVE:
						if(! inDragMode) {
							newX = initX + (int) event.getRawX() - initViewX;
							newY = initY + (int) event.getRawY() - initViewY;
							if((newX <= 0 || newX >= screenWidth) || (newY <= 0 || newY >= screenHight)) {
								inDragMode = false;
							} else {
								params.x = newX;
								params.y = newY;
								updateViews(globalView, params);
							}
						}
				}
				return true;
			}
		});
		windowManager.addView(globalView, params);
	}

	private void updateViews(final ImageView headImage, final WindowManager.LayoutParams params) {
		windowManager.updateViewLayout(headImage, params);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if(globalView != null) {
			windowManager.removeView(globalView);
		}
	}
}