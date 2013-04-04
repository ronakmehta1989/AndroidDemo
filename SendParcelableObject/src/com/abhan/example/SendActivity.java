package com.abhan.example;

import java.io.InputStream;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SendActivity extends Activity implements OnClickListener {
	private Button sendButton = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_send);

		sendButton = (Button) findViewById(R.id.button);
		sendButton.setOnClickListener(this);
	}

	public void onClick(View view) {
		Laptop laptop = new Laptop();
		laptop.setId(1);
		laptop.setBrand("Android");
		laptop.setPrice(1.99);

		InputStream is = getResources().openRawResource(R.drawable.ic_launcher);
		Bitmap bitmap = BitmapFactory.decodeStream(is);
		laptop.setImageBitmap(bitmap);

		Intent intent = new Intent(getApplicationContext(),
				ReceiveActivity.class);
		ParcelableLaptop parcelableLaptop = new ParcelableLaptop(laptop);
		intent.putExtra("laptop", parcelableLaptop);
		startActivity(intent);
	}

}