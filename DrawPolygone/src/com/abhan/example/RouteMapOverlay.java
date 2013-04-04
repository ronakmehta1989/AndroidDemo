package com.abhan.example;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.Drawable;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

public class RouteMapOverlay extends ItemizedOverlay<OverlayItem> 
{
	public ArrayList<GeoPoint> points;
//	private final GeoPoint geoPoint;
	private final ArrayList<OverlayItem> overlayItemList = new ArrayList<OverlayItem>();

//	public RouteMapOverlay() {
//		points = new ArrayList<GeoPoint>();
//	}
	
//	public RouteMapOverlay(GeoPoint geoPoint, Drawable marker) {
//		this.geoPoint = geoPoint;
//	}

	public void addPoint(GeoPoint point) {
		points.add(point);
	}
	
	public RouteMapOverlay(Drawable marker) 
	{
		super(boundCenterBottom(marker));
		populate();
	}
	
	public void addItem(GeoPoint p, String title, String snippet) 
	{
		OverlayItem newItem = new OverlayItem(p, title, snippet);
		overlayItemList.add(newItem);
		populate();
	}
	
	@Override
	public void draw(Canvas canvas, MapView mapView, boolean shadow) 
	{
//		Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
//		mPaint.setDither(true);
//		mPaint.setColor(0xFF5AA100);
//		mPaint.setStyle(Paint.Style.STROKE);
//		mPaint.setStrokeJoin(Paint.Join.ROUND);
//        mPaint.setStrokeCap(Paint.Cap.ROUND);
//        mPaint.setStrokeWidth(5);
//        mPaint.setStrokeMiter((float)5.0);
//		
//		Paint nPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
//		nPaint.setDither(true);
//		nPaint.setColor(0x40A5CC75);
//		nPaint.setStyle(Paint.Style.FILL);
//		mPaint.setStrokeJoin(Paint.Join.ROUND);
//        mPaint.setStrokeCap(Paint.Cap.ROUND);
//        
//        /******************* Circle Color *********************/
//        Paint paint1 = new Paint();
//        paint1.setARGB(192, 90, 161, 0);
//        paint1.setStrokeWidth(2);
//        paint1.setStrokeCap(Paint.Cap.ROUND);
//        paint1.setAntiAlias(true);
//        paint1.setDither(false);
//        paint1.setStyle(Paint.Style.STROKE);
//
//        Paint paint2 = new Paint();
//        paint2.setARGB(32, 165, 204, 117);
//        /******************* Circle Color *********************/
//
//		Path path = new Path();
//		
//		if(points.size() > 2) {
//			for (int i = 0; i < points.size(); i++) 
//			{
//				GeoPoint gP1 = points.get(i);
//				
//				/******************* Circle Code *********************/
//				Point pt = mapView.getProjection().toPixels(gP1, null);
//			    float radius = (float) Math.pow(2, mapView.getZoomLevel());
//			    
//			    if(radius < (canvas.getHeight() / 25)){
//			        radius = (canvas.getHeight() / 25);
//			    }
//			    float doMinus = radius;
//			    if(mapView.getZoomLevel() <= 6) {
//			    	doMinus *= 1; 
//			    } else if(mapView.getZoomLevel() > 6) {
//			    	doMinus = (mapView.getZoomLevel() - 6) * 2;
//			    	doMinus = (canvas.getHeight()/25) * doMinus;
//			    } 
//			    
//			    canvas.drawCircle(pt.x, pt.y, doMinus, paint2);
//			    canvas.drawCircle(pt.x, pt.y, doMinus, paint1);
//			    /******************* Circle Code *********************/
//				
//				Point currentScreenPoint = new Point();
//				Projection proj = mapView.getProjection();
//				proj.toPixels(gP1, currentScreenPoint);
//
//				if (i == 0) {
//					path.moveTo(currentScreenPoint.x, currentScreenPoint.y);
//				}
//				else {
//					path.lineTo(currentScreenPoint.x, currentScreenPoint.y);
//				}
//			}
//		}
//		canvas.drawPath(path, nPaint);
//		canvas.drawPath(path, mPaint);
		
		
		
//		canvas.drawARGB(255, 255, 255, 255);
//		canvas.drawColor(Color.TRANSPARENT);
		super.draw(canvas, mapView, false);
		
//		Log.d("Ketan", "previousX: " + previousX + " previousY: " + previousY + " doMinus: " + doMinus);
//		
//		if(previousX != 0.0 && previousY != 0.0) {
//			if(doMinus != 0.0) {
//				Paint paint3 = new Paint();
//				paint3.setARGB(255, 255, 255, 255);
//				paint3.setStrokeWidth(2);
//				paint3.setStrokeCap(Paint.Cap.ROUND);
//				paint3.setAntiAlias(true);
//				paint3.setDither(false);
//				paint3.setStyle(Paint.Style.STROKE);
//
//				Paint paint4 = new Paint();
//				paint4.setARGB(255, 255, 255, 255);
//				
//				canvas.drawCircle(previousX, previousY, doMinus, paint3);
//			    canvas.drawCircle(previousX, previousY, doMinus, paint4);
//			}
//		}
		
		Paint paint1 = new Paint();
		paint1.setARGB(255, 0, 0, 255);
		paint1.setStrokeWidth(3);
		paint1.setStrokeCap(Paint.Cap.ROUND);
		paint1.setAntiAlias(true);
		paint1.setDither(false);
		paint1.setStyle(Paint.Style.STROKE);

		Paint paint2 = new Paint();
		paint2.setARGB(32, 0, 0, 255);
		
		Point pt = mapView.getProjection().toPixels(overlayItemList.get(0).getPoint(), null);
	    float radius = (float) Math.pow(2, mapView.getZoomLevel());
	    
	    if(radius < (canvas.getHeight() / 25)){
	        radius = (canvas.getHeight() / 25);
	    }
	    float doMinus = radius;
	    if(mapView.getZoomLevel() <= 6) {
	    	doMinus *= 1; 
	    } else if(mapView.getZoomLevel() > 6) {
	    	doMinus = (mapView.getZoomLevel() - 6) * 2;
	    	doMinus = (canvas.getHeight()/25) * doMinus;
	    } 
	    
	    canvas.drawCircle(pt.x, pt.y, doMinus, paint2);
	    canvas.drawCircle(pt.x, pt.y, doMinus, paint1);
	}

	@Override
	protected OverlayItem createItem(int i) {
		return overlayItemList.get(i);
	}

	@Override
	public int size() {
		return overlayItemList.size();
	}
}