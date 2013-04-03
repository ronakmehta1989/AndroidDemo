package com.abhan.example;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {
	private Button button = null;
	private ListView listView = null;
	private EditText searchEdt = null;
	private TextView selectedText = null;
	private RCT rct;
	private CustomBaseAdapter adapter = null;
	private ArrayList<Users> arrList = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		findViewsById();
		rct = (RCT) this.getApplication();
		searchEdt.addTextChangedListener(new TextWatcher(){
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				adapter.getFilter().filter(s.toString());
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {}
			
			@Override
			public void afterTextChanged(Editable s) {}
		});
		arrList = rct.getDBInstatnce().getAllValues();
		adapter = new CustomBaseAdapter(MainActivity.this, arrList);
		listView.setAdapter(adapter);
		button.setOnClickListener(this);
	}
	
	private void findViewsById() {
		searchEdt = (EditText) findViewById(R.id.searchEdtText);
		selectedText = (TextView) findViewById(R.id.selectedText);
		selectedText.setMovementMethod(ScrollingMovementMethod.getInstance());
		listView = (ListView) findViewById(R.id.list);
		button = (Button) findViewById(R.id.testbutton);
	}
	
	@Override
	public void onClick(View view) {
		StringBuffer responseText = new StringBuffer();
		final int length = arrList.size();
		for(int index = 0; index < length; index++) {
			Users users = arrList.get(index);
			if(users.isChecked()) {
				responseText.append(users.getCode() + " " + users.getNumOfItems() + "\n");
			}
		}
		selectedText.setText(responseText);
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		rct.closeDataBase();
		button = null;
		listView = null;
		searchEdt = null;
		selectedText = null;
		adapter = null;
		arrList = null;
	}
}