package com.abhan.example;

import java.util.ArrayList;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;

public class DragNDropListActivity extends ListActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		ArrayList<String> content = new ArrayList<String>(mListContent.length);
		for (int i = 0; i < mListContent.length; i++) {
			content.add(mListContent[i]);
		}

		setListAdapter(new DragNDropAdapter(this,
				new int[] { R.layout.rowitem }, new int[] { R.id.txtName },
				content));
		ListView listView = getListView();

		if (listView instanceof DragNDropListView) {
			((DragNDropListView) listView).setDropListener(mDropListener);
			((DragNDropListView) listView).setRemoveListener(mRemoveListener);
			((DragNDropListView) listView).setDragListener(mDragListener);
		}
	}

	private DropListener mDropListener = new DropListener() {
		public void onDrop(int from, int to) {
			ListAdapter adapter = getListAdapter();
			if (adapter instanceof DragNDropAdapter) {
				((DragNDropAdapter) adapter).onDrop(from, to);
				getListView().invalidateViews();
			}
		}
	};

	private RemoveListener mRemoveListener = new RemoveListener() {
		public void onRemove(int which) {
			ListAdapter adapter = getListAdapter();
			if (adapter instanceof DragNDropAdapter) {
				((DragNDropAdapter) adapter).onRemove(which);
				getListView().invalidateViews();
			}
		}
	};

	private DragListener mDragListener = new DragListener() {

		int backgroundColor = 0xe0103010;
		int defaultBackgroundColor;

		public void onDrag(int x, int y, ListView listView) {
		}

		public void onStartDrag(View itemView) {
			itemView.setVisibility(View.INVISIBLE);
			defaultBackgroundColor = itemView.getDrawingCacheBackgroundColor();
			itemView.setBackgroundColor(backgroundColor);
			ImageView imageView = (ImageView) itemView
					.findViewById(R.id.holdImage);
			if (imageView != null)
				imageView.setVisibility(View.INVISIBLE);
		}

		public void onStopDrag(View itemView) {
			itemView.setVisibility(View.VISIBLE);
			itemView.setBackgroundColor(defaultBackgroundColor);
			ImageView imageView = (ImageView) itemView
					.findViewById(R.id.holdImage);
			if (imageView != null)
				imageView.setVisibility(View.VISIBLE);
		}

	};

	private static String[] mListContent = { "Item 1", "Item 2", "Item 3",
			"Item 4", "Item 5", "Item 6", "Item 7", "Item 8", "Item 9",
			"Item 10", "Item 11", "Item 12", "Item 13", "Item 14", "Item 15",
			"Item 16", "Item 17", "Item 18", "Item 19", "Item 20", "Item 21" };
}