package com.abhan.example;

import java.io.IOException;
import java.util.ArrayList;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DBAdapter {

	private static final String TAG = DBAdapter.class.getSimpleName();
	private final Context mContext;
	private SQLiteDatabase mDb;
	private final DataBaseHelper mDbHelper;
	
	public DBAdapter(Context context) {
		this.mContext = context;
		mDbHelper = new DataBaseHelper(mContext);
	}

	public DBAdapter createDatabase() throws SQLException {
		try {
			mDbHelper.createDataBase();
		} catch (IOException mIOException) {
			throw new Error("UnableToCreateDatabase");
		}
		return this;
	}

	public DBAdapter open() throws SQLException {
		try {
			mDbHelper.openDataBase();
			mDbHelper.close();
			mDb = mDbHelper.getReadableDatabase();
		} catch (SQLException mSQLException) {
			Log.e(TAG, mSQLException.toString());
			throw mSQLException;
		}
		return this;
	}

	public void close() {
		mDbHelper.close();
	}
	
	public String getValue(final String columnField, final String id) {
		String returnedValue = null;
		Cursor cursor = mDb.rawQuery("SELECT * FROM users "
				+ "WHERE _id = '" + id + "'", null);
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			returnedValue = cursor.getString(cursor.getColumnIndex(columnField));
			cursor.close();
			return returnedValue;
		}
		return returnedValue;
	}
	
	public void updateValue(final String columnField, final String passedValue, final String id) {
		Cursor cursor = mDb.rawQuery("UPDATE users SET " + columnField + " = '" + passedValue +"' "
				+ "WHERE _id = '" + id + "'", null);
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
		}
	}
	
	public ArrayList<Users> getAllValues() {
		ArrayList<Users> arrList = null;
		Cursor cursor = null;
		cursor = mDb.rawQuery("SELECT * FROM users ORDER BY name", null);
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			arrList = new ArrayList<Users>();
			final int count = cursor.getCount();
			for(int index = 0; index < count; index++) {
				final String id = cursor.getString(cursor.getColumnIndex("_id"));
				final String name = cursor.getString(cursor.getColumnIndex("name"));
				final String code = cursor.getString(cursor.getColumnIndex("code"));
				final String numOfItems = cursor.getString(cursor.getColumnIndex("noofitems"));
				Users users = new Users();
				users.setId(id);
				users.setName(name);
				users.setCode(code);
				users.setNumOfItems(numOfItems);
				users.setChecked(false);
				arrList.add(users);
				cursor.moveToNext();
			}
			cursor.close();
		}
		
		return arrList;
	}
}