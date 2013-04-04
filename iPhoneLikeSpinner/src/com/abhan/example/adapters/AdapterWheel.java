package com.abhan.example.adapters;

import com.abhan.example.widget.WheelAdapter;

import android.content.Context;

public class AdapterWheel extends AbstractWheelTextAdapter {
	private WheelAdapter adapter;

	public AdapterWheel(Context context, WheelAdapter adapter) {
		super(context);

		this.adapter = adapter;
	}

	public WheelAdapter getAdapter() {
		return adapter;
	}

	@Override
	public int getItemsCount() {
		return adapter.getItemsCount();
	}

	@Override
	protected CharSequence getItemText(int index) {
		return adapter.getItem(index);
	}
}