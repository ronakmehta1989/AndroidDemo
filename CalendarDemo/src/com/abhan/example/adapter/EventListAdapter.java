package com.abhan.example.adapter;

import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

import com.abhan.example.R;

public class EventListAdapter extends ResourceCursorAdapter {
	private Drawable circle;
	private int titleColor, descTimeColor;

	public EventListAdapter(Context context, int layout, Cursor c,
			boolean autoRequery) {
		super(context, layout, c, autoRequery);
	}

	public EventListAdapter(Context context, int layout, Cursor c,
			boolean autoRequery, Drawable circle, int titleColor,
			int descTimeColor) {
		super(context, layout, c, autoRequery);
		this.circle = circle;
		this.titleColor = titleColor;
		this.descTimeColor = descTimeColor;
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		final TextView eventTitle = (TextView) view
				.findViewById(R.id.eventrow_eventTitle);
		eventTitle.setText(cursor.getString(cursor.getColumnIndex("title")));
		eventTitle.setTextColor(titleColor);
		eventTitle.setCompoundDrawablesWithIntrinsicBounds(circle, null, null,
				null);
		final TextView eventPlace = (TextView) view
				.findViewById(R.id.eventrow_eventPlace);
		eventPlace.setText(cursor.getString(cursor.getColumnIndex("text")));
		eventPlace.setTextColor(descTimeColor);
		final TextView eventTime = (TextView) view
				.findViewById(R.id.eventrow_eventTime);
		eventTime.setText("From "
				+ cursor.getString(cursor.getColumnIndex("start_time"))
				+ " To " + cursor.getString(cursor.getColumnIndex("end_time")));
		eventTime.setTextColor(descTimeColor);
	}
}