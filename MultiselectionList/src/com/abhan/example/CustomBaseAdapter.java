package com.abhan.example;

import java.util.ArrayList;

import android.content.Context;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

public class CustomBaseAdapter extends BaseAdapter implements Filterable {

	private final LayoutInflater inflater;
	private ArrayList<Users> arrList;
	private ArrayList<Users> displayArrList;
	
	public CustomBaseAdapter(Context context, ArrayList<Users> arrList) {
		this.arrList = arrList;
		this.displayArrList = arrList;
		inflater = LayoutInflater.from(context);
	}
	
	public static class ViewHolder {
		CheckBox chkBoxTicked;
		EditText edtNo;
	}
	
	@Override
	public int getCount() {
		if(this.displayArrList.size() > 0) {
			return this.displayArrList.size();
		}
		return 0;
	}

	@Override
	public Users getItem(final int position) {
		if(this.displayArrList.size() > 0) {
			return this.displayArrList.get(position);
		}
		return null;
	}
	
	public void setItemChecked(final int position, final boolean value) {
		Users users = getItem(position);
		users.setChecked(value);
		displayArrList.set(position, users);
	}
	
	public void setNoOfItems(final int position, final String value) {
		Users users = getItem(position);
		users.setNumOfItems(value);
		displayArrList.set(position, users);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}
	
	@Override
	public boolean areAllItemsEnabled() {
		return true;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		Users users = getItem(position);
		if(convertView == null) {
			convertView = inflater.inflate(R.layout.custom_row, parent, false);
			holder = new ViewHolder();
			holder.chkBoxTicked = (CheckBox)convertView.findViewById(R.id.checkedBoxView);
			holder.edtNo = (EditText) convertView.findViewById(R.id.checkedTextEdt);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.edtNo.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			   @Override
			   public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
			       if (actionId == EditorInfo.IME_ACTION_DONE ||
			    		   actionId == EditorInfo.IME_ACTION_GO ||
			    		   actionId == EditorInfo.IME_NULL) {
			    	   setNoOfItems(position, view.getText().toString().trim());
			           return false;
			       }
			       return false;
			   }
			});
		
		holder.chkBoxTicked.setText(users.getName().trim());
		holder.chkBoxTicked.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View view) {
				CheckBox chkBox = (CheckBox) view;
				setItemChecked(position, chkBox.isChecked());
				notifyDataSetChanged();
			}
		}); 
		
		boolean isChkBoxChecked = users.isChecked();
		if(isChkBoxChecked) {
			holder.chkBoxTicked.setChecked(true);
			holder.edtNo.setClickable(true);
			holder.edtNo.setEnabled(true);
			holder.edtNo.setText(users.getNumOfItems());
			holder.edtNo.setSelection(holder.edtNo.getText().toString().trim().length());
		} else {
			holder.chkBoxTicked.setChecked(false);
			holder.edtNo.setClickable(false);
			holder.edtNo.setEnabled(false);
			holder.edtNo.setText(users.getNumOfItems());
			holder.edtNo.setSelection(holder.edtNo.getText().toString().trim().length());
		}
		
		return convertView;
	}
	
	@Override
	public Filter getFilter() {
		Filter filter = new Filter(){
			@SuppressWarnings("unchecked")
			@Override
			protected void publishResults(CharSequence constraint,
					FilterResults results) {
				displayArrList = (ArrayList<Users>) results.values;
				notifyDataSetChanged();
			}
			
			@Override
			protected FilterResults performFiltering(CharSequence constraint) {
				FilterResults results = new FilterResults();
				ArrayList<Users> filteredArrList = new ArrayList<Users>();
				if (arrList == null) {
					arrList = new ArrayList<Users>(displayArrList);
				}
				if (constraint == null || constraint.length() == 0) {
					results.count = arrList.size();
					results.values = arrList;
				} else {
					constraint = constraint.toString().toLowerCase().trim();
					for (int i = 0; i < arrList.size(); i++) {
						String data = arrList.get(i).getName();
						if (data.toLowerCase()
								.startsWith(constraint.toString())) {
							filteredArrList.add(arrList.get(i));
						}
					}
					results.count = filteredArrList.size();
					results.values = filteredArrList;
				}
				return results;
			}
		};
		return filter;
	}
}