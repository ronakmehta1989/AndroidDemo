package com.abhan.example;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.KeyEvent;

import com.abhan.example.utils.PinDropItemizedOverlay;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class PinDropActivity extends MapActivity 
{
	private MapView mapView;
	private GeoPoint geoPoint;
	private PinDropItemizedOverlay itemizedOverlay;
	private OverlayItem overlayItem;
	private List<Overlay> mapOverlays;
	private LocationManager locationManager = null;
	private Location location;
	private double currentLatitude, currentLongitude;

	@Override
	public void onCreate(Bundle savedInstance) {
		super.onCreate(savedInstance);
		setContentView(R.layout.mapscreen);

		if (locationManager == null) {
			locationManager = (LocationManager) PinDropActivity.this
					.getSystemService(Context.LOCATION_SERVICE);
		}
		LocationListener locationListener = new CustomLocationListener();
		
		initComponent();

		location = locationManager
				.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		if (location == null) {
			location = locationManager
					.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		}
		((CustomLocationListener) locationListener)
				.updateWithNewLocation(location);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
				60000L, 1000, locationListener);
		if (location != null) {
			currentLatitude = location.getLatitude();
			currentLongitude = location.getLongitude();
		}
		
		//Go with your location. Here I used static LatLong to show how animation works.
		
		final ArrayList<Double> latList = new ArrayList<Double>();
		latList.add(25.839449);
		latList.add(23.241346);
		latList.add(22.593726);
		latList.add(13.197165);
		latList.add(20.262197);
		final ArrayList<Double> longList = new ArrayList<Double>();
		longList.add(75.366211);
		longList.add(79.453125);
		longList.add(71.499023);
		longList.add(77.34375);
		longList.add(84.770508);

		final Drawable drawable = this.getResources().getDrawable(R.drawable.map);
		mapOverlays = mapView.getOverlays();
		if (latList != null && !latList.isEmpty()) {
			for(int i = 0; i < latList.size(); i++) {
				geoPoint = new GeoPoint((int) (latList.get(i) * 1E6), (int) (longList.get(i) * 1E6));
				itemizedOverlay = new PinDropItemizedOverlay(this, drawable, mapView, geoPoint);
				mapOverlays = mapView.getOverlays();
				overlayItem = new OverlayItem(geoPoint, getString(R.string.app_name), getString(R.string.app_name));
				itemizedOverlay.addOverlayItem(overlayItem);
				mapOverlays.add(itemizedOverlay);
				mapView.getController().setZoom(7);
			}
		} else if (latList == null || latList.isEmpty()) {
			for(int i = 0; i < latList.size(); i++) {
				geoPoint = new GeoPoint((int) (latList.get(i) * 1E6), (int) (longList.get(i) * 1E6));
				itemizedOverlay = new PinDropItemizedOverlay(this, drawable, mapView, geoPoint);
				mapOverlays = mapView.getOverlays();
				overlayItem = new OverlayItem(geoPoint, getString(R.string.app_name), getString(R.string.app_name));
				itemizedOverlay.addOverlayItem(overlayItem);
				mapOverlays.add(itemizedOverlay);
				mapView.getController().setZoom(7);
			}
		}
		mapView.getController().animateTo(geoPoint);
	}

	private void initComponent() {
		mapView = (MapView) findViewById(R.id.mapscreen_map);
		mapView.setSatellite(false);
		mapView.setTraffic(false);
		mapView.setBuiltInZoomControls(true);
	}

	private class CustomLocationListener implements LocationListener {

		@Override
		public void onLocationChanged(Location location) {
			updateWithNewLocation(location);
		}

		private void updateWithNewLocation(Location location) {
			if (location != null) {
				currentLatitude = location.getLatitude();
				currentLongitude = location.getLongitude();
			}
		}

		@Override
		public void onProviderDisabled(String provider) {
		}

		@Override
		public void onProviderEnabled(String provider) {
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent keyEvent) {
		if (!(android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.DONUT)
				&& keyCode == KeyEvent.KEYCODE_BACK
				&& keyEvent.getRepeatCount() == 0) {
			onBackPressed();
		}
		return super.onKeyDown(keyCode, keyEvent);
	}

	@Override
	public void onBackPressed() {
		finish();
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

}