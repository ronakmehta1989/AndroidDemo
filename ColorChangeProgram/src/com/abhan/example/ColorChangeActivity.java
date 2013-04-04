package com.abhan.example;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;

public class ColorChangeActivity extends Activity {
	protected static final String TAG = "ColorChangeActivity";
	Bitmap mSourceBitmap;
	ImageView mImageView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		mImageView = (ImageView) findViewById(R.id.myimage);

		mSourceBitmap = BitmapFactory.decodeResource(getResources(),
				R.drawable.auramental);

		Bitmap mNewBitmap = adjustedHSV(mSourceBitmap, 200, (float) 1.0,
				(float) 1.0);
		mImageView.setImageBitmap(mNewBitmap);
		mImageView.invalidate();
	}

	private Bitmap adjustedHSV(Bitmap nSource, int nHue, float nSaturation,
			float nLightness) {
		Bitmap nSourceBitmap = nSource;
		Bitmap nNewBitmap = nSourceBitmap.copy(Bitmap.Config.ARGB_8888, true);
		for (int x = 0; x < nNewBitmap.getWidth(); x++)
			for (int y = 0; y < nNewBitmap.getHeight(); y++) {
				int nNewPixel = hueChange(nNewBitmap.getPixel(x, y), nHue,
						nSaturation, nLightness);
				nNewBitmap.setPixel(x, y, nNewPixel);
			}
		return nNewBitmap;
	}

	private int hueChange(int nStartPixel, int nHue, float nSaturation,
			float nLightness) {
		float[] hsv = new float[3];
		Color.colorToHSV(nStartPixel, hsv);
		hsv[0] = nHue;// hsv[0] + nDegree //Hue range 0 to 360(H)
		// hsv[0] = hsv[0] % 360; //Saturation range 0.0 to 1.0(S)
		hsv[1] = nSaturation;// hsv[1] + 10 //Lightness range 0.0.to 1.0(V)
		// hsv[1] = hsv[1] % 360;
		hsv[2] = nLightness;// -(hsv[2]+25)
		// hsv[2] = (hsv[2] % 360);
		return Color.HSVToColor(Color.alpha(nStartPixel), hsv);
	}
}