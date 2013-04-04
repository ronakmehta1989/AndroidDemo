package com.abhan.example;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class StoreAndRetrieveImageFromDatabaseActivity extends Activity {
	private DBhelper DbHelper;
	private ImageView mImage;
	private TextView mStatus;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		mStatus = (TextView) findViewById(R.id.Status);
		mImage = (ImageView) findViewById(R.id.Image);

		DbHelper = new DBhelper(this);

		Fruit testFruit = new Fruit(BitmapFactory.decodeResource(
				getResources(), R.drawable.icon), "Icon", 100, 0);

		DbHelper.open();
		DbHelper.createFruitEntry(testFruit);
		DbHelper.close();

		testFruit = null;

		DbHelper.open();
		testFruit = DbHelper.getFirstFruitFromDB();
		DbHelper.close();

		mImage.setImageBitmap(testFruit.getBitmap());
		mStatus.setText("Name: " + testFruit.getName() + ", "
				+ testFruit.getKcal() + "kcal, " + testFruit.getVitaminC()
				+ "mg Vitamin C");

	}
}