package com.abhan.example;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;

public class DrawPolygoneActivity extends MapActivity implements
		LocationListener {
	private MapView mapView = null;
	private ArrayList<Latlong> list = null;
	private final String[] latitude = { "22.593726", "26.194877", "25.363882",
			"23.443089", "20.673905", "22.593726" };
	private final String[] longitude = { "75.798339", "78.171386", "81.950683",
			"83.620605", "78.654784", "75.798339" };
	private Latlong latlong;
	private LocationManager locationManager;
	private Location location;
	private Double currentLatitude, currentLongitude;
	private boolean isNetworkEnabled = false, isGpsEnabled = false;
	private Drawable marker;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		if (locationManager == null) {
			locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		}

		mapView = (MapView) findViewById(R.id.mapView);
		mapView.setClickable(true);
		mapView.setBuiltInZoomControls(true);
		mapView.setSatellite(false);
		mapView.getController().setZoom(6);

		marker = getResources().getDrawable(R.drawable.current_location);
		int markerWidth = marker.getIntrinsicWidth();
		int markerHeight = marker.getIntrinsicHeight();
		marker.setBounds(0, markerHeight, markerWidth, 0);

		if (checkMyProvider()) {
			getInitLocation();
		}

		list = new ArrayList<Latlong>();
		list.clear();

		for (int i = 0; i < latitude.length; i++) {
			latlong = new Latlong();
			latlong.setLatitude(latitude[i]);
			latlong.setLongitude(longitude[i]);
			list.add(latlong);
		}

		final RouteMapOverlay overlay = new RouteMapOverlay(marker);

		for (int i = 0; i < list.size(); i++) {
			final Latlong model = list.get(i);
			GeoPoint pt = new GeoPoint((int) (Double.valueOf(model
					.getLatitude()) * 1E6), (int) (Double.valueOf(model
					.getLongitude()) * 1E6));
			overlay.addPoint(pt);
			mapView.getController().animateTo(pt);
			mapView.getController().setZoom(6);
		}
		mapView.getOverlays().add(overlay);

		getPlaceLocation(currentLatitude, currentLongitude);
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	private boolean checkMyProvider() {
		try {
			isNetworkEnabled = locationManager
					.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		try {
			isGpsEnabled = locationManager
					.isProviderEnabled(LocationManager.GPS_PROVIDER);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		if (isNetworkEnabled) {
			locationManager.requestLocationUpdates(
					LocationManager.NETWORK_PROVIDER, 60000L, 1000, this);
		}

		if (isGpsEnabled) {
			locationManager.requestLocationUpdates(
					LocationManager.GPS_PROVIDER, 60000L, 1000, this);
		}

		if (isNetworkEnabled || isGpsEnabled) {
			return true;
		} else {
			return false;
		}
	}

	private void getInitLocation() {
		try {
			if (location != null) {
				currentLatitude = location.getLatitude();
				currentLongitude = location.getLongitude();
			} else if (location == null) {
				if (isNetworkEnabled) {
					location = locationManager
							.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
				} else if (isGpsEnabled) {
					location = locationManager
							.getLastKnownLocation(LocationManager.GPS_PROVIDER);
				}

				if (location != null) {
					currentLatitude = location.getLatitude();
					currentLongitude = location.getLongitude();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void getPlaceLocation(double latitude, double longitude) {
		GeoPoint geoPoint = new GeoPoint((int) (latitude * 1E6),
				(int) (longitude * 1E6));
		final RouteMapOverlay overlay = new RouteMapOverlay(marker);
		mapView.getController().animateTo(geoPoint);
		mapView.getOverlays().add(overlay);
		overlay.addItem(geoPoint, "Current Location", "Current Location");
	}

	@Override
	public void onLocationChanged(Location location) {

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {

	}

	@Override
	public void onProviderEnabled(String provider) {

	}

	@Override
	public void onProviderDisabled(String provider) {

	}

}