package com.abhan.example.adapter;

import java.util.Calendar;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.abhan.example.CalendarActivity;
import com.abhan.example.R;

public class CalendarAdapter extends BaseAdapter {
	private final String TAG = CalendarAdapter.class.getSimpleName();
	private final Activity mContext;
	private final java.util.Calendar month, selectedDate;
	private String[] days;
	private final short FIRST_DAY_OF_WEEK = 0;
	private short firstDay, maxday, currentDatePosition;
	private final LayoutInflater inflater;
	private ViewHolder holder;
	private short lastClicked = -1;
	private LinearLayout selectedView;
	private TextView selectedTextView;
	private final GradientDrawable dateClickedBackgroundColor;
	private final int currentMonthDateColor, prevNextMonthDateColor,
			parentGridBackgroundColor, sundayColor;

	public CalendarAdapter(Activity context, Calendar month,
			int parentGridBackgroundColor, int currentMonthDateColor,
			int prevNextMonthDateColor, Drawable smallCircle,
			GradientDrawable dateClickedBackgroundColor, int eventTitleColor,
			int eventDescTimeColor, int sundayColor) {
		mContext = context;
		this.month = month;
		selectedDate = (Calendar) month.clone();
		this.month.set(Calendar.DAY_OF_MONTH, 1);
		inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.parentGridBackgroundColor = parentGridBackgroundColor;
		this.currentMonthDateColor = currentMonthDateColor;
		this.prevNextMonthDateColor = prevNextMonthDateColor;
		this.dateClickedBackgroundColor = dateClickedBackgroundColor;
		this.sundayColor = sundayColor;
	}

	public View getView(final int position, View convertView,
			final ViewGroup parent) {
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.calendarrow, null);
			holder.txtDay = (TextView) convertView
					.findViewById(R.id.calendarrow_date);
			holder.txtDay
					.setWidth((int) (CalendarActivity.display.getWidth() * 14.3) / 100);
			convertView.setBackgroundColor(parentGridBackgroundColor);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.txtDay.setTag(position);

		if ((position + 1 < firstDay)) {
			holder.txtDay.setText(days[position]);
			holder.txtDay.setTextColor(prevNextMonthDateColor);
			holder.txtDay.setClickable(false);
			holder.txtDay.setEnabled(false);
			holder.txtDay.setFocusable(false);
		} else if ((position + 1 >= maxday + firstDay)) {
			holder.txtDay.setText(days[position]);
			holder.txtDay.setTextColor(prevNextMonthDateColor);
			holder.txtDay.setClickable(false);
			holder.txtDay.setEnabled(false);
			holder.txtDay.setFocusable(false);
		} else {
			holder.txtDay.setText(days[position]);
			holder.txtDay.setClickable(true);
			holder.txtDay.setEnabled(true);
			holder.txtDay.setFocusable(true);
			final Calendar calendar = Calendar.getInstance();
			calendar.set(month.get(Calendar.YEAR), month.get(Calendar.MONTH),
					Integer.parseInt(days[position]));
			if (month.get(Calendar.YEAR) == selectedDate.get(Calendar.YEAR)
					&& month.get(Calendar.MONTH) == selectedDate
							.get(Calendar.MONTH)
					&& days[position].equals(""
							+ selectedDate.get(Calendar.DAY_OF_MONTH))) {
				currentDatePosition = (short) position;
				holder.txtDay.setBackgroundColor(currentMonthDateColor);
				holder.txtDay.setTextColor(mContext.getResources().getColor(
						R.color.white));
			} else {
				holder.txtDay.setBackgroundColor(mContext.getResources()
						.getColor(R.color.transparent));
				holder.txtDay.setTextColor(currentMonthDateColor);
			}

			if (position % 7 == 0) {
				holder.txtDay.setTextColor(sundayColor);
			}
		}

		holder.txtDay.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				if ((Integer.parseInt(view.getTag().toString()) + 1) < firstDay) {
					Log.d(TAG, "Previous Month.");
					if (month.get(Calendar.MONTH) == month
							.getActualMinimum(Calendar.MONTH)) {
						month.set((month.get(Calendar.YEAR) - 1), month
								.getActualMaximum(Calendar.MONTH), 1);
					} else {
						month.set(Calendar.MONTH,
								month.get(Calendar.MONTH) - 1);
					}
					refreshDays();
					/*formatedMonthTitleDate =
							android.text.format.DateFormat.format(mContext.getString(R.string.month_format),
									month).toString();
					txtCurrentMonth.setText(formatedMonthTitleDate);*/
					notifyDataSetChanged();
				} else if ((Integer.parseInt(view.getTag().toString()) + 1) >= maxday
						+ firstDay) {
					Log.d(TAG, "Next Month.");
					if (month.get(Calendar.MONTH) == month
							.getActualMaximum(Calendar.MONTH)) {
						month.set((month.get(Calendar.YEAR) + 1), month
								.getActualMinimum(Calendar.MONTH), 1);
					} else {
						month.set(Calendar.MONTH,
								month.get(Calendar.MONTH) + 1);
					}
					refreshDays();
					/*formatedMonthTitleDate =
							android.text.format.DateFormat.format(mContext.getString(R.string.month_format),
									month).toString();
					txtCurrentMonth.setText(formatedMonthTitleDate);*/
					notifyDataSetChanged();
				} else {
					if (lastClicked != -1) {
						if (lastClicked != Integer.parseInt(view.getTag()
								.toString())) {
							selectedView = (LinearLayout) parent
									.getChildAt(lastClicked);
							selectedView
									.setBackgroundColor(parentGridBackgroundColor);

							if (lastClicked % 7 == 0) {
								selectedTextView.setTextColor(sundayColor);
							} else if (lastClicked == currentDatePosition) {
								selectedTextView.setTextColor(Color.WHITE);
							} else {
								selectedTextView
										.setTextColor(currentMonthDateColor);
							}

							lastClicked = (short) Integer.parseInt(view
									.getTag().toString());
							parent.getChildAt(lastClicked)
									.setBackgroundDrawable(
											dateClickedBackgroundColor);

							selectedTextView = ((TextView) view);
							((TextView) view).setTextColor(Color.WHITE);
						} else {
							if (selectedTextView != null) {
								if (lastClicked == currentDatePosition) {
									selectedTextView.setTextColor(Color.WHITE);
								} else if (lastClicked % 7 == 0) {
									if (lastClicked == Integer.parseInt(view
											.getTag().toString())) {
										selectedTextView
												.setTextColor(Color.WHITE);
									} else {
										selectedTextView
												.setTextColor(sundayColor);
									}
								} else {
									if (lastClicked == Integer.parseInt(view
											.getTag().toString())) {
										selectedTextView
												.setTextColor(Color.WHITE);
									} else if (lastClicked != Integer
											.parseInt(view.getTag().toString())) {
										selectedTextView
												.setTextColor(currentMonthDateColor);
									}
								}
							}
						}
					} else if (lastClicked == -1) {
						selectedView = (LinearLayout) parent.getChildAt(Integer
								.parseInt(view.getTag().toString()));
						lastClicked = (short) Integer.parseInt(view.getTag()
								.toString());
						parent.getChildAt(lastClicked).setBackgroundDrawable(
								dateClickedBackgroundColor);

						selectedTextView = ((TextView) view);
						((TextView) view).setTextColor(Color.WHITE);
					}
				}
			}
		});

		return convertView;
	}

	public void refreshDays() {
		lastClicked = -1;
		days = null;
		firstDay = (short) month.get(Calendar.DAY_OF_WEEK);
		if (firstDay == 7) {
			days = new String[42];
		} else {
			days = new String[35];
		}
		short j = FIRST_DAY_OF_WEEK;

		if (firstDay > 1) {
			Calendar calendar = Calendar.getInstance();
			if (month.get(Calendar.MONTH) == Calendar.JANUARY) {
				calendar.set(month.get(Calendar.YEAR) - 1, Calendar.DECEMBER, 1);
			} else {
				calendar.set(month.get(Calendar.YEAR),
						month.get(Calendar.MONTH) - 1, 1);
			}
			short length = 0;

			for (j = 0; j < firstDay - FIRST_DAY_OF_WEEK; j++) {
				days[j] = "";
				length = j;
			}

			short maxDayPrevious = (short) calendar
					.getActualMaximum(Calendar.DAY_OF_MONTH);

			for (int i = length - 1; i >= 0; i--) {
				days[i] = String.valueOf(maxDayPrevious);
				maxDayPrevious--;
			}
		} else {
			for (j = 0; j < FIRST_DAY_OF_WEEK * 6; j++) {
				days[j] = "";
			}
			j = FIRST_DAY_OF_WEEK * 6 + 1;
		}

		short dayNumber = 1, startDay = 1;
		maxday = (short) month.getActualMaximum(Calendar.DAY_OF_MONTH);
		for (short i = (short) (j - 1); i < days.length; i++) {
			if (dayNumber <= maxday) {
				days[i] = "" + dayNumber;
				dayNumber++;
			} else {
				days[i] = "" + startDay;
				startDay++;
			}
		}
	}

	public class ViewHolder {
		private TextView txtDay;
	}

	public int getCount() {
		return days.length;
	}

	public String getItem(int position) {
		return days[position];
	}

	public long getItemId(int position) {
		return 0;
	}

	@Override
	public boolean areAllItemsEnabled() {
		return super.areAllItemsEnabled();
	}

	@Override
	public boolean isEnabled(int position) {
		if ((position + 1 < firstDay) || (position + 1 >= maxday + firstDay)) {
			return false;
		} else {
			return true;
		}
	}
}