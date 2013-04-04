package com.abhan.example.utils;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.abhan.example.R;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;
import com.google.android.maps.Projection;

public class PinDropItemizedOverlay extends ItemizedOverlay<OverlayItem> {

	private final ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
	private final Context mContext;
	private final MapView mapView;
	private final GeoPoint geoPoint;
	private int newHeight = 0;
	
	public PinDropItemizedOverlay(Context mContext, Drawable defaultMarker, MapView mapView, GeoPoint geoPoint) {
		super(defaultMarker);
		this.mContext = mContext;
		this.mapView = mapView;
		this.geoPoint = geoPoint;
		showAnimatedMarker(geoPoint);
	}
	
	@Override
	public void draw(Canvas canvas, MapView mapView, boolean shadow) {
        GeoPoint newGeos = new GeoPoint(geoPoint.getLatitudeE6(), geoPoint.getLongitudeE6());
        Projection projection = mapView.getProjection();
        Point pt2 = projection.toPixels(newGeos, null);

        Paint circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        circlePaint.setColor(0x40000000);
        circlePaint.setStyle(Style.FILL_AND_STROKE);
        canvas.drawCircle(pt2.x, pt2.y + (newHeight / 2), (float)75.0, circlePaint);

		super.draw(canvas, mapView, shadow);
	}
	
	@Override
	protected OverlayItem createItem(int i) {
		return mOverlays.get(i);
	}

	@Override
	public int size() {
		return mOverlays.size();
	}

	public void addOverlayItem(OverlayItem overlayItem) {
		mOverlays.add(overlayItem);
		populate();
	}
	
	@Override
	public boolean onTap(GeoPoint p, MapView mapView) {
		return super.onTap(p, mapView);
	}
	
	public void showAnimatedMarker(GeoPoint point) {
	    LinearLayout v = (LinearLayout) View.inflate(mContext, R.layout.mapicon, null);
	    ImageView marker = (ImageView) v.findViewById(R.id.marker);
	    newHeight = marker.getDrawable().getIntrinsicHeight();
	    Animation anim = new TranslateAnimation(0, 0, -400, 0);
	    anim.setInterpolator(new BounceInterpolator());
	    anim.setDuration(1500);
	    marker.startAnimation(anim);
	    mapView.addView(v, 0, new MapView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.FILL_PARENT, point, MapView.LayoutParams.CENTER));
	}}
