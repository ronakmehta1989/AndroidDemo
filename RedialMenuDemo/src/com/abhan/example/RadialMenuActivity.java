package com.abhan.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

import com.abhan.example.RadialMenuWidget.RadialMenuEntry;

public class RadialMenuActivity extends Activity {
	private RadialMenuWidget	redialMenu;
	private LinearLayout		linearLayout;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_radialmenu);
		linearLayout = new LinearLayout(this);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.FILL_PARENT);
		addContentView(linearLayout, params);
		Button testButton = (Button) this.findViewById(R.id.button1);
		testButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				redialMenu = new RadialMenuWidget(getBaseContext());
				redialMenu.setAnimationSpeed(0L);
				int xLayoutSize = linearLayout.getWidth();
				int yLayoutSize = linearLayout.getHeight();
				redialMenu.setSourceLocation(xLayoutSize, yLayoutSize);
				redialMenu.setIconSize(15, 30);
				redialMenu.setTextSize(13);
				redialMenu.setCenterCircle(new Close());
				redialMenu.addMenuEntry(new Menu1());
				redialMenu.addMenuEntry(new NewTestMenu());
				redialMenu.addMenuEntry(new CircleOptions());
				redialMenu.addMenuEntry(new Menu2());
				redialMenu.addMenuEntry(new Menu3());
				linearLayout.addView(redialMenu);
			}
		});
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent e) {
		int state = e.getAction();
		int eventX = (int) e.getX();
		int eventY = (int) e.getY();
		if (state == MotionEvent.ACTION_DOWN) {
			System.out.println("Button Pressed");
			int xScreenSize = (getResources().getDisplayMetrics().widthPixels);
			int yScreenSize = (getResources().getDisplayMetrics().heightPixels);
			int xLayoutSize = linearLayout.getWidth();
			int yLayoutSize = linearLayout.getHeight();
			int xCenter = xScreenSize / 2;
			int xSource = eventX;
			int yCenter = yScreenSize / 2;
			int ySource = eventY;
			if (xScreenSize != xLayoutSize) {
				xCenter = xLayoutSize / 2;
				xSource = eventX - (xScreenSize - xLayoutSize);
			}
			if (yScreenSize != yLayoutSize) {
				yCenter = yLayoutSize / 2;
				ySource = eventY - (yScreenSize - yLayoutSize);
			}
			redialMenu = new RadialMenuWidget(getBaseContext());
			redialMenu.setSourceLocation(xSource, ySource);
			redialMenu.setShowSourceLocation(true);
			redialMenu.setCenterLocation(xCenter, yCenter);
			redialMenu.setHeader("X:" + xSource + " Y:" + ySource, 20);
			redialMenu.setCenterCircle(new Close());
			redialMenu.addMenuEntry(new CircleOptions());
			redialMenu.addMenuEntry(new Menu1());
			redialMenu.addMenuEntry(new Menu2());
			redialMenu.addMenuEntry(new Menu3());
			linearLayout.addView(redialMenu);
		}
		return true;
	}
	
	public class Close implements RadialMenuEntry {
		@Override
		public String getName() {
			return "Close";
		}
		
		@Override
		public String getLabel() {
			return null;
		}
		
		@Override
		public int getIcon() {
			return android.R.drawable.ic_menu_close_clear_cancel;
		}
		
		@Override
		public List<RadialMenuEntry> getChildren() {
			return null;
		}
		
		@Override
		public void menuActiviated() {
			System.out.println("Close Menu Activated");
			((LinearLayout) redialMenu.getParent()).removeView(redialMenu);
		}
	}
	
	public static class Menu1 implements RadialMenuEntry {
		@Override
		public String getName() {
			return "Menu1 - No Children";
		}
		
		@Override
		public String getLabel() {
			return "Menu1";
		}
		
		@Override
		public int getIcon() {
			return 0;
		}
		
		@Override
		public List<RadialMenuEntry> getChildren() {
			return null;
		}
		
		@Override
		public void menuActiviated() {
			System.out.println("Menu #1 Activated - No Children");
		}
	}
	
	public static class Menu2 implements RadialMenuEntry {
		@Override
		public String getName() {
			return "Menu2 - Children";
		}
		
		@Override
		public String getLabel() {
			return "Menu2";
		}
		
		@Override
		public int getIcon() {
			return R.drawable.ic_launcher;
		}
		private final List<RadialMenuEntry>	children	= new ArrayList<RadialMenuEntry>(
															Arrays.asList(
																	new StringOnly(),
																	new IconOnly(),
																	new StringAndIcon()));
		
		@Override
		public List<RadialMenuEntry> getChildren() {
			return children;
		}
		
		@Override
		public void menuActiviated() {
			System.out.println("Menu #2 Activated - Children");
		}
	}
	
	public static class Menu3 implements RadialMenuEntry {
		@Override
		public String getName() {
			return "Menu3 - No Children";
		}
		
		@Override
		public String getLabel() {
			return null;
		}
		
		@Override
		public int getIcon() {
			return R.drawable.ic_launcher;
		}
		
		@Override
		public List<RadialMenuEntry> getChildren() {
			return null;
		}
		
		@Override
		public void menuActiviated() {
			System.out.println("Menu #3 Activated - No Children");
		}
	}
	
	public static class IconOnly implements RadialMenuEntry {
		@Override
		public String getName() {
			return "IconOnly";
		}
		
		@Override
		public String getLabel() {
			return null;
		}
		
		@Override
		public int getIcon() {
			return R.drawable.ic_launcher;
		}
		
		@Override
		public List<RadialMenuEntry> getChildren() {
			return null;
		}
		
		@Override
		public void menuActiviated() {
			System.out.println("IconOnly Menu Activated");
		}
	}
	
	public static class StringAndIcon implements RadialMenuEntry {
		@Override
		public String getName() {
			return "StringAndIcon";
		}
		
		@Override
		public String getLabel() {
			return "String";
		}
		
		@Override
		public int getIcon() {
			return R.drawable.ic_launcher;
		}
		
		@Override
		public List<RadialMenuEntry> getChildren() {
			return null;
		}
		
		@Override
		public void menuActiviated() {
			System.out.println("StringAndIcon Menu Activated");
		}
	}
	
	public static class StringOnly implements RadialMenuEntry {
		@Override
		public String getName() {
			return "StringOnly";
		}
		
		@Override
		public String getLabel() {
			return "String\nOnly";
		}
		
		@Override
		public int getIcon() {
			return 0;
		}
		
		@Override
		public List<RadialMenuEntry> getChildren() {
			return null;
		}
		
		@Override
		public void menuActiviated() {
			System.out.println("StringOnly Menu Activated");
		}
	}
	
	public static class NewTestMenu implements RadialMenuEntry {
		@Override
		public String getName() {
			return "NewTestMenu";
		}
		
		@Override
		public String getLabel() {
			return "New\nTest\nMenu";
		}
		
		@Override
		public int getIcon() {
			return 0;
		}
		private final List<RadialMenuEntry>	children	= new ArrayList<RadialMenuEntry>(
															Arrays.asList(
																	new StringOnly(),
																	new IconOnly()));
		
		@Override
		public List<RadialMenuEntry> getChildren() {
			return children;
		}
		
		@Override
		public void menuActiviated() {
			System.out.println("New Test Menu Activated");
		}
	}
	
	public static class CircleOptions implements RadialMenuEntry {
		@Override
		public String getName() {
			return "CircleOptions";
		}
		
		@Override
		public String getLabel() {
			return "Circle\nSymbols";
		}
		
		@Override
		public int getIcon() {
			return 0;
		}
		private final List<RadialMenuEntry>	children	= new ArrayList<RadialMenuEntry>(
															Arrays.asList(
																	new RedCircle(),
																	new YellowCircle(),
																	new GreenCircle(),
																	new BlueCircle()));
		
		@Override
		public List<RadialMenuEntry> getChildren() {
			return children;
		}
		
		@Override
		public void menuActiviated() {
			System.out.println("Circle Options Activated");
		}
	}
	
	public static class RedCircle implements RadialMenuEntry {
		@Override
		public String getName() {
			return "RedCircle";
		}
		
		@Override
		public String getLabel() {
			return "Red";
		}
		
		@Override
		public int getIcon() {
			return R.drawable.red_circle;
		}
		
		@Override
		public List<RadialMenuEntry> getChildren() {
			return null;
		}
		
		@Override
		public void menuActiviated() {
			System.out.println("Red Circle Activated");
		}
	}
	
	public static class YellowCircle implements RadialMenuEntry {
		@Override
		public String getName() {
			return "YellowCircle";
		}
		
		@Override
		public String getLabel() {
			return "Yellow";
		}
		
		@Override
		public int getIcon() {
			return R.drawable.yellow_circle;
		}
		
		@Override
		public List<RadialMenuEntry> getChildren() {
			return null;
		}
		
		@Override
		public void menuActiviated() {
			System.out.println("Yellow Circle Activated");
		}
	}
	
	public static class GreenCircle implements RadialMenuEntry {
		@Override
		public String getName() {
			return "GreenCircle";
		}
		
		@Override
		public String getLabel() {
			return "Green";
		}
		
		@Override
		public int getIcon() {
			return R.drawable.green_circle;
		}
		
		@Override
		public List<RadialMenuEntry> getChildren() {
			return null;
		}
		
		@Override
		public void menuActiviated() {
			System.out.println("Green Circle Activated");
		}
	}
	
	public static class BlueCircle implements RadialMenuEntry {
		@Override
		public String getName() {
			return "BlueCircle";
		}
		
		@Override
		public String getLabel() {
			return "Blue";
		}
		
		@Override
		public int getIcon() {
			return R.drawable.blue_circle;
		}
		
		@Override
		public List<RadialMenuEntry> getChildren() {
			return null;
		}
		
		@Override
		public void menuActiviated() {
			System.out.println("Blue Circle Activated");
		}
	}
}