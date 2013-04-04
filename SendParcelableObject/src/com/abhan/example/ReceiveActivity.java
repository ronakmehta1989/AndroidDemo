package com.abhan.example;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class ReceiveActivity extends Activity {

	private TextView descTxt = null;
	private ImageView imageView = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_receive);

		descTxt = (TextView) findViewById(R.id.desc);
		imageView = (ImageView) findViewById(R.id.icon);

		Intent intent = getIntent();

		ParcelableLaptop parcelableLaptop = (ParcelableLaptop) intent
				.getParcelableExtra("laptop");
		Laptop laptop = parcelableLaptop.getLaptop();
		String desc = laptop.getId() + ": " + laptop.getBrand() + "\n"
				+ laptop.getPrice();
		descTxt.setText(desc);
		imageView.setImageBitmap(laptop.getImageBitmap());
	}
}