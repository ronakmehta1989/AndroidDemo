package com.abhan.example;

import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabHost.TabContentFactory;
import android.widget.TextView;

public class MainActivity extends FragmentActivity implements
		TabHost.OnTabChangeListener, ViewPager.OnPageChangeListener {

	private TabHost mTabHost;
	private ViewPager mViewPager;
	private HashMap<String, TabInfo> mapTabInfo = new HashMap<String, MainActivity.TabInfo>();
	private PagerAdapter mPagerAdapter;

	private class TabInfo {
		private String tag;
		private Class<?> clazz;
		private Bundle args;

		TabInfo(String tag, Class<?> clazz, Bundle args) {
			this.tag = tag;
			this.clazz = clazz;
			this.args = args;
		}
	}

	class TabFactory implements TabContentFactory {
		private final Context mContext;

		public TabFactory(Context context) {
			mContext = context;
		}

		public View createTabContent(String tag) {
			View view = new View(mContext);
			view.setMinimumWidth(0);
			view.setMinimumHeight(0);
			return view;
		}
	}

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initialiseTabHost(savedInstanceState);
		if (savedInstanceState != null) {
			mTabHost.setCurrentTabByTag(savedInstanceState.getString("tab"));
		}
		intialiseViewPager();
	}

	protected void onSaveInstanceState(Bundle outState) {
		outState.putString("tab", mTabHost.getCurrentTabTag());
		super.onSaveInstanceState(outState);
	}

	private void intialiseViewPager() {
		List<Fragment> fragments = new Vector<Fragment>();
		fragments.add(Fragment.instantiate(this, Tab1Fragment.class.getName()));
		fragments.add(Fragment.instantiate(this, Tab2Fragment.class.getName()));
		fragments.add(Fragment.instantiate(this, Tab3Fragment.class.getName()));
		mPagerAdapter = new PagerAdapter(
				super.getSupportFragmentManager(), fragments);
		mViewPager = (ViewPager) super.findViewById(R.id.viewpager);
		mViewPager.setAdapter(mPagerAdapter);
		mViewPager.setOnPageChangeListener(this);
	}

	private void initialiseTabHost(Bundle args) {
		mTabHost = (TabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup();
		TabInfo tabInfo = null;
		View tabView = createTabView(mTabHost.getContext(), "Android");
		MainActivity.AddTab(this, mTabHost,
				mTabHost.newTabSpec("Android").setIndicator(tabView),
				(tabInfo = new TabInfo("Android", Tab1Fragment.class, args)));
		this.mapTabInfo.put(tabInfo.tag, tabInfo);
		tabView = createTabView(mTabHost.getContext(), "Abhan");
		MainActivity.AddTab(this, mTabHost,
				mTabHost.newTabSpec("Abhan").setIndicator(tabView),
				(tabInfo = new TabInfo("Abhan", Tab2Fragment.class, args)));
		this.mapTabInfo.put(tabInfo.tag, tabInfo);
		tabView = createTabView(mTabHost.getContext(), "Android");
		MainActivity.AddTab(this, mTabHost,
				mTabHost.newTabSpec("Android").setIndicator(tabView),
				(tabInfo = new TabInfo("Android", Tab3Fragment.class, args)));
		mapTabInfo.put(tabInfo.tag, tabInfo);
		mTabHost.setOnTabChangedListener(this);
	}

	private View createTabView(final Context context, final String text) {
		final View view = LayoutInflater.from(context).inflate(
				R.layout.tabs_bg, null);
		TextView textView = (TextView) view.findViewById(R.id.tabsText);
		textView.setText(text);
		return view;
	}

	private static void AddTab(MainActivity activity, TabHost tabHost,
			TabHost.TabSpec tabSpec, TabInfo tabInfo) {
		tabSpec.setContent(activity.new TabFactory(activity));
		tabHost.addTab(tabSpec);
	}

	public void onTabChanged(String tag) {
		int pos = mTabHost.getCurrentTab();
		mViewPager.setCurrentItem(pos);
	}

	@Override
	public void onPageSelected(int position) {
		mTabHost.setCurrentTab(position);
	}
	
	@Override
	public void onPageScrolled(int position, float positionOffset,
			int positionOffsetPixels) {}

	@Override
	public void onPageScrollStateChanged(int state) {}
}