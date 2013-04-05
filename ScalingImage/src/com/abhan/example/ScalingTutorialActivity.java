package com.abhan.example;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.abhan.example.util.ScalingUtilities;
import com.abhan.example.util.ScalingUtilities.ScalingLogic;

public class ScalingTutorialActivity extends Activity {

    private int mSourceId;
    private int mDstWidth;
    private int mDstHeight;
    private ImageView mImageView;
    private TextView mResultView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mSourceId = R.drawable.image;
        mResultView = (TextView)findViewById(R.id.text_result);
        mImageView = (ImageView)findViewById(R.id.image);
        mDstWidth = getResources().getDimensionPixelSize(R.dimen.destination_width);
        mDstHeight = getResources().getDimensionPixelSize(R.dimen.destination_height);

        findViewById(R.id.button_scaling_bad).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                badButtonPressed();
            }
        });
        findViewById(R.id.button_scaling_fit).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                fitButtonPressed();
            }
        });
        findViewById(R.id.button_scaling_crop).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                cropButtonPressed();
            }
        });

    }

    protected void badButtonPressed() {
        final long startTime = SystemClock.uptimeMillis();
        Bitmap unscaledBitmap = BitmapFactory.decodeResource(getResources(), mSourceId);
        Bitmap scaledBitmap = Bitmap
                .createScaledBitmap(unscaledBitmap, mDstWidth, mDstHeight, true);
        unscaledBitmap.recycle();

        final int memUsageKb = (unscaledBitmap.getRowBytes() * unscaledBitmap.getHeight()) / 1024;
        final long stopTime = SystemClock.uptimeMillis();

        mResultView.setText("Time taken: " + (stopTime - startTime)
                + " ms. Memory used for scaling: " + memUsageKb + " kb.");
        mImageView.setImageBitmap(scaledBitmap);
    }

    protected void fitButtonPressed() {
        final long startTime = SystemClock.uptimeMillis();
        Bitmap unscaledBitmap = ScalingUtilities.decodeResource(getResources(), mSourceId,
                mDstWidth, mDstHeight, ScalingLogic.FIT);
        Bitmap scaledBitmap = ScalingUtilities.createScaledBitmap(unscaledBitmap, mDstWidth,
                mDstHeight, ScalingLogic.FIT);
        unscaledBitmap.recycle();

        final int memUsageKb = (unscaledBitmap.getRowBytes() * unscaledBitmap.getHeight()) / 1024;
        final long stopTime = SystemClock.uptimeMillis();

        mResultView.setText("Time taken: " + (stopTime - startTime)
                + " ms. Memory used for scaling: " + memUsageKb + " kb.");
        mImageView.setImageBitmap(scaledBitmap);
    }

    protected void cropButtonPressed() {
        final long startTime = SystemClock.uptimeMillis();
        Bitmap unscaledBitmap = ScalingUtilities.decodeResource(getResources(), mSourceId,
                mDstWidth, mDstHeight, ScalingLogic.CROP);
        Bitmap scaledBitmap = ScalingUtilities.createScaledBitmap(unscaledBitmap, mDstWidth,
                mDstHeight, ScalingLogic.CROP);
        unscaledBitmap.recycle();

        final int memUsageKb = (unscaledBitmap.getRowBytes() * unscaledBitmap.getHeight()) / 1024;
        final long stopTime = SystemClock.uptimeMillis();

        mResultView.setText("Time taken: " + (stopTime - startTime)
                + " ms. Memory used for scaling: " + memUsageKb + " kb.");
        mImageView.setImageBitmap(scaledBitmap);
    }
}