package com.abhan.example;

import java.io.IOException;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DBAdapter {
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
			Log.e(Abhan.TAG, mSQLException.toString());
			throw mSQLException;
		}
		return this;
	}

	public void close() {
		mDbHelper.close();
	}
	
	public String getValue(final String columnField) {
		String returnedValue = null;
		Cursor cursor = mDb.rawQuery("SELECT * FROM Android "
				+ "WHERE _id = 1", null);
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			returnedValue = cursor.getString(cursor.getColumnIndex(columnField));
			cursor.close();
			return returnedValue;
		}
		return returnedValue;
	}
}