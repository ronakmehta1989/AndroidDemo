package com.abhan.example;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.location.Location;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;

public class CurrentLocationOverlay extends Overlay {
	private final GeoPoint point;
	private final double defaultLatitude = Double.parseDouble("23.0000");
	private final double defaultLongitude = Double.parseDouble("72.0564");
	private final float defaultAccuracy = 250f;
	private Point centerPoint;
	private Point leftPoint;
	private final Location currentLocation;
	private final Drawable defaultMarker;
	private Paint accuracyPaint;
	private int width;
	private int height;

	public CurrentLocationOverlay(final GeoPoint point,
			final Drawable defaultMarker, final Location currentLocation) {
		this.point = point;
		this.defaultMarker = defaultMarker;
		this.currentLocation = currentLocation;
	}

	@Override
	public void draw(Canvas canvas, MapView mapView, boolean shadow) {
		super.draw(canvas, mapView, false);
		accuracyPaint = new Paint();
		accuracyPaint.setAntiAlias(true);
		accuracyPaint.setStrokeWidth(2.0f);

		width = defaultMarker.getIntrinsicWidth();
		height = defaultMarker.getIntrinsicHeight();
		centerPoint = new Point();
		leftPoint = new Point();
		double latitude;
		double longitude;
		float accuracy;
		Projection projection = mapView.getProjection();

		if (currentLocation == null) {
			latitude = defaultLatitude;
			longitude = defaultLongitude;
			accuracy = defaultAccuracy;
		} else {
			latitude = currentLocation.getLatitude();
			longitude = currentLocation.getLongitude();
			accuracy = currentLocation.getAccuracy();
		}

		float[] result = new float[1];

		Location.distanceBetween(latitude, longitude, latitude, longitude + 1,
				result);
		float longitudeLineDistance = result[0];

		GeoPoint leftGeoPoint = new GeoPoint((int) (latitude * 1E6),
				(int) ((longitude - accuracy / longitudeLineDistance) * 1E6));
		projection.toPixels(leftGeoPoint, leftPoint);
		projection.toPixels(point, centerPoint);
		int radius = centerPoint.x - leftPoint.x;

		accuracyPaint.setColor(0xff6666ff);
		accuracyPaint.setStyle(Style.STROKE);
		canvas.drawCircle(centerPoint.x, centerPoint.y, radius, accuracyPaint);

		accuracyPaint.setColor(0x186666ff);
		accuracyPaint.setStyle(Style.FILL);
		canvas.drawCircle(centerPoint.x, centerPoint.y, radius, accuracyPaint);

		defaultMarker.setBounds(centerPoint.x - width / 2, centerPoint.y
				- height / 2, centerPoint.x + width / 2, centerPoint.y + height
				/ 2);
		defaultMarker.draw(canvas);
	}
}