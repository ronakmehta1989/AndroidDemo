package com.abhan.example;

import java.util.Calendar;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.abhan.example.adapter.CalendarAdapter;

public class CalendarActivity extends Activity {
	private RelativeLayout parentLayout = null;
	private Button btnNext, btnPrevious;
	private TextView txtCurrentMonth = null, txtActivityHeader = null;
	private TextView txtMonday = null, txtTuesday = null, txtWednesday = null,
			txtThursday = null, txtFriday = null, txtSaturday = null,
			txtSunday = null;
	private GridView gridView = null;
	private Calendar calMonth = null;
	private CalendarAdapter adapter = null;
	public static Display display = null;
	private GradientDrawable gradientDrawable, parentBackgroundColor,
			dateClickedBackgroundColor;
	private Drawable leftArrowDrawable, rightArrowDrawable, smallCircle;
	private int calendarTitleColor, sundayColor, restdaysColor,
			parentGridBackgroundColor;
	private int currentMonthDateColor, prevNextMonthDateColor;
	private String formatedMonthTitleDate;
	private String themeName = "black";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.calendaractivity);

		// Get application object to access preference
		display = CalendarActivity.this.getWindow().getWindowManager()
				.getDefaultDisplay();
		themeName = "green";
		setAccordingTheme();
		calMonth = Calendar.getInstance();
		calMonth.set(calMonth.get(Calendar.YEAR), calMonth.get(Calendar.MONTH),
				calMonth.get(Calendar.DATE));

		// Initialize screen component
		initComponent();
		setWeekTitle(display.getWidth());
		setViewBackground();
		formatedMonthTitleDate = android.text.format.DateFormat.format(
				getString(R.string.month_format), calMonth).toString();
		setMonthTitle(formatedMonthTitleDate);

		adapter = new CalendarAdapter(CalendarActivity.this, calMonth,
				parentGridBackgroundColor, currentMonthDateColor,
				prevNextMonthDateColor, smallCircle,
				dateClickedBackgroundColor, calendarTitleColor, restdaysColor,
				sundayColor);
		refreshCalendar();

		btnPrevious.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (calMonth.get(Calendar.MONTH) == calMonth
						.getActualMinimum(Calendar.MONTH)) {
					calMonth.set((calMonth.get(Calendar.YEAR) - 1),
							calMonth.getActualMaximum(Calendar.MONTH), 1);
				} else {
					calMonth.set(Calendar.MONTH,
							calMonth.get(Calendar.MONTH) - 1);
				}
				refreshCalendar();
				formatedMonthTitleDate = android.text.format.DateFormat.format(
						getString(R.string.month_format), calMonth).toString();
				setMonthTitle(formatedMonthTitleDate);
			}
		});

		btnNext.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				if (calMonth.get(Calendar.MONTH) == calMonth
						.getActualMaximum(Calendar.MONTH)) {
					calMonth.set((calMonth.get(Calendar.YEAR) + 1),
							calMonth.getActualMinimum(Calendar.MONTH), 1);
				} else {
					calMonth.set(Calendar.MONTH,
							calMonth.get(Calendar.MONTH) + 1);
				}
				refreshCalendar();
				formatedMonthTitleDate = android.text.format.DateFormat.format(
						getString(R.string.month_format), calMonth).toString();
				setMonthTitle(formatedMonthTitleDate);
			}
		});
	}

	private void initComponent() {
		parentLayout = (RelativeLayout) findViewById(R.id.calendaractivity_parentbar);
		txtActivityHeader = (TextView) findViewById(R.id.calendaractivity_header);
		btnPrevious = (Button) findViewById(R.id.btnPreviousMonth);
		txtCurrentMonth = (TextView) findViewById(R.id.txtMonth);
		btnNext = (Button) findViewById(R.id.btnNextMonth);
		gridView = (GridView) findViewById(R.id.month_gridView);
		txtMonday = (TextView) findViewById(R.id.txtMonday);
		txtTuesday = (TextView) findViewById(R.id.txtTuesday);
		txtWednesday = (TextView) findViewById(R.id.txtWednesday);
		txtThursday = (TextView) findViewById(R.id.txtThursday);
		txtFriday = (TextView) findViewById(R.id.txtFriday);
		txtSaturday = (TextView) findViewById(R.id.txtSaturday);
		txtSunday = (TextView) findViewById(R.id.txtSunday);
	}

	private void setAccordingTheme() {
		if (themeName.trim().toLowerCase().equals("red")) {
			parentBackgroundColor = new GradientDrawable(
					GradientDrawable.Orientation.TOP_BOTTOM, new int[] {
							0xFFEEF8F4, 0xFFDCD4B8 });
			gradientDrawable = new GradientDrawable(
					GradientDrawable.Orientation.TOP_BOTTOM, new int[] {
							0xFFE96866, 0xFFBD0400 });
			gradientDrawable.setCornerRadius(0f);
			gradientDrawable.setDither(true);
			leftArrowDrawable = getResources().getDrawable(R.drawable.left_red);
			rightArrowDrawable = getResources().getDrawable(
					R.drawable.right_red);
			calendarTitleColor = Color.parseColor("#980000");
			sundayColor = Color.parseColor("#E30000");
			restdaysColor = Color.parseColor("#8C3C01");
			parentGridBackgroundColor = Color.parseColor("#EEF8F4");

			currentMonthDateColor = Color.parseColor("#67351D");
			prevNextMonthDateColor = Color.parseColor("#BF8E76");
			smallCircle = getResources().getDrawable(R.drawable.select_common);
			dateClickedBackgroundColor = new GradientDrawable(
					GradientDrawable.Orientation.TOP_BOTTOM, new int[] {
							0xFF753B15, 0xFF2C0D0A });
		} else if (themeName.trim().toLowerCase().equals("blue")) {
			parentBackgroundColor = new GradientDrawable(
					GradientDrawable.Orientation.TOP_BOTTOM, new int[] {
							0xFFF0F9FF, 0xFFA1DBFF });
			gradientDrawable = new GradientDrawable(
					GradientDrawable.Orientation.TOP_BOTTOM, new int[] {
							0xFF63ADE1, 0xFF316EB7 });
			gradientDrawable.setCornerRadius(0f);
			gradientDrawable.setDither(true);
			leftArrowDrawable = getResources()
					.getDrawable(R.drawable.left_blue);
			rightArrowDrawable = getResources().getDrawable(
					R.drawable.right_blue);
			calendarTitleColor = Color.parseColor("#10439C");
			sundayColor = Color.parseColor("#E30000");
			restdaysColor = Color.parseColor("#414141");
			parentGridBackgroundColor = Color.parseColor("#F0F9FF");

			currentMonthDateColor = Color.parseColor("#13479F");
			prevNextMonthDateColor = Color.parseColor("#77B6F9");
			smallCircle = getResources().getDrawable(R.drawable.select_common);
			dateClickedBackgroundColor = new GradientDrawable(
					GradientDrawable.Orientation.TOP_BOTTOM, new int[] {
							0xFF3C7FC6, 0xFF10439C });
		} else if (themeName.trim().toLowerCase().equals("black")) {
			parentBackgroundColor = new GradientDrawable(
					GradientDrawable.Orientation.TOP_BOTTOM, new int[] {
							0xFFF1F1F1, 0xFFC4C4C4 });
			gradientDrawable = new GradientDrawable(
					GradientDrawable.Orientation.TOP_BOTTOM, new int[] {
							0xFFF49727, 0xFFF86820 });
			gradientDrawable.setCornerRadius(0f);
			gradientDrawable.setDither(true);
			leftArrowDrawable = getResources().getDrawable(
					R.drawable.left_black);
			rightArrowDrawable = getResources().getDrawable(
					R.drawable.right_black);
			calendarTitleColor = Color.parseColor("#EB5102");
			sundayColor = Color.parseColor("#E30000");
			restdaysColor = Color.parseColor("#5E5E5E");
			parentGridBackgroundColor = Color.parseColor("#F1F1F1");

			currentMonthDateColor = Color.parseColor("#000000");
			prevNextMonthDateColor = Color.parseColor("#9D9D9D");
			smallCircle = getResources().getDrawable(R.drawable.select_common);
			dateClickedBackgroundColor = new GradientDrawable(
					GradientDrawable.Orientation.TOP_BOTTOM, new int[] {
							0xFFFF6100, 0xFFBA2907 });
		} else if (themeName.trim().toLowerCase().equals("green")) {
			parentBackgroundColor = new GradientDrawable(
					GradientDrawable.Orientation.TOP_BOTTOM, new int[] {
							0xFFFEFFE8, 0xFFD6DBBF });
			gradientDrawable = new GradientDrawable(
					GradientDrawable.Orientation.TOP_BOTTOM, new int[] {
							0xFFB2D736, 0xFF8AA427 });
			gradientDrawable.setCornerRadius(0f);
			gradientDrawable.setDither(true);
			leftArrowDrawable = getResources().getDrawable(
					R.drawable.left_green);
			rightArrowDrawable = getResources().getDrawable(
					R.drawable.right_green);
			calendarTitleColor = Color.parseColor("#506406");
			sundayColor = Color.parseColor("#E30000");
			restdaysColor = Color.parseColor("#303030");
			parentGridBackgroundColor = Color.parseColor("#FEFFE8");

			currentMonthDateColor = Color.parseColor("#354400");
			prevNextMonthDateColor = Color.parseColor("#A7C04E");
			smallCircle = getResources().getDrawable(R.drawable.select_common);
			dateClickedBackgroundColor = new GradientDrawable(
					GradientDrawable.Orientation.TOP_BOTTOM, new int[] {
							0xFF617A08, 0xFF415205 });
		} else if (themeName.trim().toLowerCase().equals("orange")) {
			parentBackgroundColor = new GradientDrawable(
					GradientDrawable.Orientation.TOP_BOTTOM, new int[] {
							0xFFFFF5EC, 0xFFFFEEDA });
			gradientDrawable = new GradientDrawable(
					GradientDrawable.Orientation.TOP_BOTTOM, new int[] {
							0xFFF49727, 0xFFF86820 });
			gradientDrawable.setCornerRadius(0f);
			gradientDrawable.setDither(true);
			leftArrowDrawable = getResources().getDrawable(
					R.drawable.left_orange);
			rightArrowDrawable = getResources().getDrawable(
					R.drawable.right_orange);
			calendarTitleColor = Color.parseColor("#BB450A");
			sundayColor = Color.parseColor("#E30000");
			restdaysColor = Color.parseColor("#4A4949");
			parentGridBackgroundColor = Color.parseColor("#FFF5EC");

			currentMonthDateColor = Color.parseColor("#B5420A");
			prevNextMonthDateColor = Color.parseColor("#E4926A");
			smallCircle = getResources().getDrawable(R.drawable.select_common);
			dateClickedBackgroundColor = new GradientDrawable(
					GradientDrawable.Orientation.TOP_BOTTOM, new int[] {
							0xFFEE5E05, 0xFF792410 });
		}
	}

	private void refreshCalendar() {
		adapter.refreshDays();
		adapter.notifyDataSetChanged();
		gridView.setAdapter(adapter);
	}

	public void setMonthTitle(final String dateFormat) {
		txtCurrentMonth.setText(dateFormat);
		txtCurrentMonth.setTextColor(calendarTitleColor);
	}

	private void setWeekTitle(int totalWidth) {
		txtSunday.setWidth((int) (totalWidth * 14.3) / 100);
		txtMonday.setWidth((int) (totalWidth * 14.3) / 100);
		txtTuesday.setWidth((int) (totalWidth * 14.3) / 100);
		txtWednesday.setWidth((int) (totalWidth * 14.3) / 100);
		txtThursday.setWidth((int) (totalWidth * 14.3) / 100);
		txtFriday.setWidth((int) (totalWidth * 14.3) / 100);
		txtSaturday.setWidth((int) (totalWidth * 14.3) / 100);
	}

	private void setViewBackground() {
		parentLayout.setBackgroundDrawable(parentBackgroundColor);
		txtActivityHeader.setBackgroundColor(calendarTitleColor);
		btnPrevious.setBackgroundDrawable(leftArrowDrawable);
		btnNext.setBackgroundDrawable(rightArrowDrawable);
		txtSunday.setTextColor(sundayColor);
		txtMonday.setTextColor(restdaysColor);
		txtTuesday.setTextColor(restdaysColor);
		txtWednesday.setTextColor(restdaysColor);
		txtThursday.setTextColor(restdaysColor);
		txtFriday.setTextColor(restdaysColor);
		txtSaturday.setTextColor(restdaysColor);
	}
}