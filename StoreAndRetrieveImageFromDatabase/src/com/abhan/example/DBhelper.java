package com.abhan.example;

import java.io.ByteArrayOutputStream;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class DBhelper {
	public static final String KEY_ID = "id";
	public static final String KEY_NAME = "name";
	public static final String KEY_KCAL = "kcal";
	public static final String KEY_VC = "vitaminc";
	public static final String KEY_IMG = "image";

	private DatabaseHelper mDbHelper;
	private SQLiteDatabase mDb;

	private static final String DATABASE_NAME = "DBtest";
	private static final int DATABASE_VERSION = 1;

	private static final String FRUITS_TABLE = "fruits";

	private static final String CREATE_FRUITS_TABLE = "create table "
			+ FRUITS_TABLE + " (" + KEY_ID
			+ " integer primary key autoincrement, " + KEY_IMG
			+ " blob not null, " + KEY_NAME + " text not null unique, "
			+ KEY_KCAL + " integer not null, " + KEY_VC + " integer not null);";

	private final Context mCtx;

	private static class DatabaseHelper extends SQLiteOpenHelper {
		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		public void onCreate(SQLiteDatabase db) {
			db.execSQL(CREATE_FRUITS_TABLE);
		}

		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS " + FRUITS_TABLE);
			onCreate(db);
		}
	}

	public void Reset() {
		mDbHelper.onUpgrade(this.mDb, 1, 1);
	}

	public DBhelper(Context ctx) {
		mCtx = ctx;
		mDbHelper = new DatabaseHelper(mCtx);
	}

	public DBhelper open() throws SQLException {
		mDb = mDbHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		mDbHelper.close();
	}

	public void createFruitEntry(Fruit fruit) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		fruit.getBitmap().compress(Bitmap.CompressFormat.PNG, 100, out);
		ContentValues cv = new ContentValues();
		cv.put(KEY_IMG, out.toByteArray());
		cv.put(KEY_NAME, fruit.getName());
		cv.put(KEY_KCAL, fruit.getKcal());
		cv.put(KEY_VC, fruit.getVitaminC());
		mDb.insert(FRUITS_TABLE, null, cv);
	}

	public Fruit getFirstFruitFromDB() throws SQLException {
		Cursor cur = mDb.query(true, FRUITS_TABLE, new String[] { KEY_IMG,
				KEY_NAME, KEY_KCAL, KEY_VC }, null, null, null, null, null,
				null);
		if (cur.moveToFirst()) {
			byte[] blob = cur.getBlob(cur.getColumnIndex(KEY_IMG));
			Bitmap bmp = BitmapFactory.decodeByteArray(blob, 0, blob.length);
			String name = cur.getString(cur.getColumnIndex(KEY_NAME));
			int kcal = cur.getInt(cur.getColumnIndex(KEY_KCAL));
			int vc = cur.getInt(cur.getColumnIndex(KEY_VC));
			cur.close();
			return new Fruit(bmp, name, kcal, vc);
		}
		cur.close();
		return null;
	}
}