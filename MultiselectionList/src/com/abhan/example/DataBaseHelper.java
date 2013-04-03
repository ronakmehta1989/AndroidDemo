package com.abhan.example;

import java.io.File;
import java.io.IOException;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataBaseHelper extends SQLiteOpenHelper {
	
	private static final String TAG = DataBaseHelper.class.getSimpleName();
	private static String DB_PATH = "/data/data/YOUR_PACKAGE/databases/";
	private static String DB_NAME = "MultiSelectDemo";
	private SQLiteDatabase mDataBase;
	private static final String TABLE_EXISTS = "DROP TABLE IF EXISTS users";
	private static final String TABLE_CREATION = "CREATE TABLE IF NOT EXISTS users(_id INTEGER, name TEXT, code TEXT, noofitems TEXT)";
	private static final String TABLE_INSERTION = "INSERT INTO users (_id, name, code, noofitems) VALUES (1, 'A', 'A101', '');";
	private static final String TABLE_INSERTION1 = "INSERT INTO users (_id, name, code, noofitems) VALUES (2, 'B', 'B102', '');";
	private static final String TABLE_INSERTION2 = "INSERT INTO users (_id, name, code, noofitems) VALUES (3, 'C', 'C103', '0');";
	private static final String TABLE_INSERTION3 = "INSERT INTO users (_id, name, code, noofitems) VALUES (4, 'D', 'D104', '0');";
	private static final String TABLE_INSERTION4 = "INSERT INTO users (_id, name, code, noofitems) VALUES (5, 'E', 'E105', '0');";
	private static final String TABLE_INSERTION5 = "INSERT INTO users (_id, name, code, noofitems) VALUES (6, 'F', 'F106', '0');";
	private static final String TABLE_INSERTION6 = "INSERT INTO users (_id, name, code, noofitems) VALUES (7, 'G', 'G107', '0');";
	private static final String TABLE_INSERTION7 = "INSERT INTO users (_id, name, code, noofitems) VALUES (8, 'H', 'H108', '0');";
	private static final String TABLE_INSERTION8 = "INSERT INTO users (_id, name, code, noofitems) VALUES (9, 'I', 'I109', '0');";
	private static final String TABLE_INSERTION9 = "INSERT INTO users (_id, name, code, noofitems) VALUES (10, 'J', 'J110', '0');";
	private static final String TABLE_INSERTION10 = "INSERT INTO users (_id, name, code, noofitems) VALUES (11, 'K', 'K111', '0');";
	private static final String TABLE_INSERTION11 = "INSERT INTO users (_id, name, code, noofitems) VALUES (12, 'L', 'L112', '0');";
	private static final String TABLE_INSERTION12 = "INSERT INTO users (_id, name, code, noofitems) VALUES (13, 'M', 'M113', '0');";
	private static final String TABLE_INSERTION13 = "INSERT INTO users (_id, name, code, noofitems) VALUES (14, 'N', 'N114', '0');";
	private static final String TABLE_INSERTION14 = "INSERT INTO users (_id, name, code, noofitems) VALUES (15, 'O', 'O115', '0');";
	private static final String TABLE_INSERTION15 = "INSERT INTO users (_id, name, code, noofitems) VALUES (16, 'Q', 'Q116', '0');";
	private static final String TABLE_INSERTION16 = "INSERT INTO users (_id, name, code, noofitems) VALUES (17, 'R', 'R117', '0');";
	private static final String TABLE_INSERTION17 = "INSERT INTO users (_id, name, code, noofitems) VALUES (18, 'S', 'S118', '0');";
	private static final String TABLE_INSERTION18 = "INSERT INTO users (_id, name, code, noofitems) VALUES (19, 'T', 'T119', '0');";
	private static final String TABLE_INSERTION19 = "INSERT INTO users (_id, name, code, noofitems) VALUES (20, 'U', 'U120', '0');";
	private static final String TABLE_INSERTION20 = "INSERT INTO users (_id, name, code, noofitems) VALUES (21, 'AA', 'AA101', '');";
	private static final String TABLE_INSERTION21 = "INSERT INTO users (_id, name, code, noofitems) VALUES (22, 'BB', 'BB102', '');";
	private static final String TABLE_INSERTION22 = "INSERT INTO users (_id, name, code, noofitems) VALUES (23, 'CC', 'CC103', '0');";
	private static final String TABLE_INSERTION23 = "INSERT INTO users (_id, name, code, noofitems) VALUES (24, 'DD', 'DD104', '0');";
	private static final String TABLE_INSERTION24 = "INSERT INTO users (_id, name, code, noofitems) VALUES (25, 'EE', 'EE105', '0');";
	private static final String TABLE_INSERTION25 = "INSERT INTO users (_id, name, code, noofitems) VALUES (26, 'FF', 'FF106', '0');";
	private static final String TABLE_INSERTION26 = "INSERT INTO users (_id, name, code, noofitems) VALUES (27, 'GG', 'GG107', '0');";
	private static final String TABLE_INSERTION27 = "INSERT INTO users (_id, name, code, noofitems) VALUES (28, 'HH', 'HH108', '0');";
	private static final String TABLE_INSERTION28 = "INSERT INTO users (_id, name, code, noofitems) VALUES (29, 'II', 'II109', '0');";
	private static final String TABLE_INSERTION29 = "INSERT INTO users (_id, name, code, noofitems) VALUES (30, 'JJ', 'JJ110', '0');";
	private static final String TABLE_INSERTION30 = "INSERT INTO users (_id, name, code, noofitems) VALUES (31, 'KK', 'KK111', '0');";
	private static final String TABLE_INSERTION31 = "INSERT INTO users (_id, name, code, noofitems) VALUES (32, 'LL', 'LL112', '0');";
	private static final String TABLE_INSERTION32 = "INSERT INTO users (_id, name, code, noofitems) VALUES (33, 'MM', 'MM113', '0');";
	private static final String TABLE_INSERTION33 = "INSERT INTO users (_id, name, code, noofitems) VALUES (34, 'NN', 'NN114', '0');";
	private static final String TABLE_INSERTION34 = "INSERT INTO users (_id, name, code, noofitems) VALUES (35, 'OO', 'OO115', '0');";
	private static final String TABLE_INSERTION35 = "INSERT INTO users (_id, name, code, noofitems) VALUES (36, 'QQ', 'QQ116', '0');";
	private static final String TABLE_INSERTION36 = "INSERT INTO users (_id, name, code, noofitems) VALUES (37, 'RR', 'RR117', '0');";
	private static final String TABLE_INSERTION37 = "INSERT INTO users (_id, name, code, noofitems) VALUES (38, 'SS', 'SS118', '0');";
	private static final String TABLE_INSERTION38 = "INSERT INTO users (_id, name, code, noofitems) VALUES (39, 'TT', 'TT119', '0');";
	private static final String TABLE_INSERTION39 = "INSERT INTO users (_id, name, code, noofitems) VALUES (40, 'UU', 'UU120', '0');";
	private static final String TABLE_INSERTION40 = "INSERT INTO users (_id, name, code, noofitems) VALUES (41, 'AAA', 'AAA101', '');";
	private static final String TABLE_INSERTION41 = "INSERT INTO users (_id, name, code, noofitems) VALUES (42, 'BBB', 'BBB102', '');";
	private static final String TABLE_INSERTION42 = "INSERT INTO users (_id, name, code, noofitems) VALUES (43, 'CCC', 'CCC103', '0');";
	private static final String TABLE_INSERTION43 = "INSERT INTO users (_id, name, code, noofitems) VALUES (44, 'DDD', 'DDD104', '0');";
	private static final String TABLE_INSERTION44 = "INSERT INTO users (_id, name, code, noofitems) VALUES (45, 'EEE', 'EEE105', '0');";
	private static final String TABLE_INSERTION45 = "INSERT INTO users (_id, name, code, noofitems) VALUES (46, 'FFF', 'FFF106', '0');";
	private static final String TABLE_INSERTION46 = "INSERT INTO users (_id, name, code, noofitems) VALUES (47, 'GGG', 'GGG107', '0');";
	private static final String TABLE_INSERTION47 = "INSERT INTO users (_id, name, code, noofitems) VALUES (48, 'HHH', 'HHH108', '0');";
	private static final String TABLE_INSERTION48 = "INSERT INTO users (_id, name, code, noofitems) VALUES (49, 'III', 'III109', '0');";
	private static final String TABLE_INSERTION49 = "INSERT INTO users (_id, name, code, noofitems) VALUES (50, 'JJJ', 'JJJ110', '0');";
	private static final String TABLE_INSERTION50 = "INSERT INTO users (_id, name, code, noofitems) VALUES (51, 'AAAA', 'AAAA101', '');";
	private static final String TABLE_INSERTION51 = "INSERT INTO users (_id, name, code, noofitems) VALUES (52, 'BBBB', 'BBBB102', '');";
	private static final String TABLE_INSERTION52 = "INSERT INTO users (_id, name, code, noofitems) VALUES (53, 'CCCC', 'CCCC103', '0');";
	private static final String TABLE_INSERTION53 = "INSERT INTO users (_id, name, code, noofitems) VALUES (54, 'DDDD', 'DDDD104', '0');";
	private static final String TABLE_INSERTION54 = "INSERT INTO users (_id, name, code, noofitems) VALUES (55, 'EEEE', 'EEEE105', '0');";
	private static final String TABLE_INSERTION55 = "INSERT INTO users (_id, name, code, noofitems) VALUES (56, 'FFFF', 'FFFF106', '0');";
	private static final String TABLE_INSERTION56 = "INSERT INTO users (_id, name, code, noofitems) VALUES (57, 'GGGG', 'GGGG107', '0');";
	private static final String TABLE_INSERTION57 = "INSERT INTO users (_id, name, code, noofitems) VALUES (58, 'HHHH', 'HHHH108', '0');";
	private static final String TABLE_INSERTION58 = "INSERT INTO users (_id, name, code, noofitems) VALUES (59, 'IIII', 'IIII109', '0');";
	private static final String TABLE_INSERTION59 = "INSERT INTO users (_id, name, code, noofitems) VALUES (60, 'JJJJ', 'JJJJ110', '0');";

	public DataBaseHelper(Context context) {
		super(context, DB_NAME, null, 1);
		DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
	}

	public void createDataBase() throws IOException {
		boolean mDataBaseExist = checkDataBase();
		if (!mDataBaseExist) {
			this.getReadableDatabase();
		}
	}

	private boolean checkDataBase() {
		SQLiteDatabase mCheckDataBase = null;
		try {
			String mPath = DB_PATH + DB_NAME;
			File pathFile = new File(mPath);
			if(pathFile.exists()) {
				mCheckDataBase = SQLiteDatabase.openDatabase(mPath, null, SQLiteDatabase.NO_LOCALIZED_COLLATORS);
			}
		} catch (SQLiteException mSQLiteException) {
			Log.e(TAG, "DatabaseNotFound " + mSQLiteException.toString());
		}

		if (mCheckDataBase != null) {
			mCheckDataBase.close();
		}
		return mCheckDataBase != null;
	}
	
	public boolean openDataBase() throws SQLException {
		String mPath = DB_PATH + DB_NAME;
		mDataBase = SQLiteDatabase.openDatabase(mPath, null,
				SQLiteDatabase.NO_LOCALIZED_COLLATORS);
		return mDataBase != null;
	}

	@Override
	public synchronized void close() {
		if(mDataBase != null) {
			mDataBase.close();
		}
		super.close();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(TABLE_EXISTS);
		db.execSQL(TABLE_CREATION);
		db.execSQL(TABLE_INSERTION);
		db.execSQL(TABLE_INSERTION1);
		db.execSQL(TABLE_INSERTION2);
		db.execSQL(TABLE_INSERTION3);
		db.execSQL(TABLE_INSERTION4);
		db.execSQL(TABLE_INSERTION5);
		db.execSQL(TABLE_INSERTION6);
		db.execSQL(TABLE_INSERTION7);
		db.execSQL(TABLE_INSERTION8);
		db.execSQL(TABLE_INSERTION9);
		db.execSQL(TABLE_INSERTION10);
		db.execSQL(TABLE_INSERTION11);
		db.execSQL(TABLE_INSERTION12);
		db.execSQL(TABLE_INSERTION13);
		db.execSQL(TABLE_INSERTION14);
		db.execSQL(TABLE_INSERTION15);
		db.execSQL(TABLE_INSERTION16);
		db.execSQL(TABLE_INSERTION17);
		db.execSQL(TABLE_INSERTION18);
		db.execSQL(TABLE_INSERTION19);
		db.execSQL(TABLE_INSERTION20);
		db.execSQL(TABLE_INSERTION21);
		db.execSQL(TABLE_INSERTION22);
		db.execSQL(TABLE_INSERTION23);
		db.execSQL(TABLE_INSERTION24);
		db.execSQL(TABLE_INSERTION25);
		db.execSQL(TABLE_INSERTION26);
		db.execSQL(TABLE_INSERTION27);
		db.execSQL(TABLE_INSERTION28);
		db.execSQL(TABLE_INSERTION29);
		db.execSQL(TABLE_INSERTION30);
		db.execSQL(TABLE_INSERTION31);
		db.execSQL(TABLE_INSERTION32);
		db.execSQL(TABLE_INSERTION33);
		db.execSQL(TABLE_INSERTION34);
		db.execSQL(TABLE_INSERTION35);
		db.execSQL(TABLE_INSERTION36);
		db.execSQL(TABLE_INSERTION37);
		db.execSQL(TABLE_INSERTION38);
		db.execSQL(TABLE_INSERTION39);
		db.execSQL(TABLE_INSERTION40);
		db.execSQL(TABLE_INSERTION41);
		db.execSQL(TABLE_INSERTION42);
		db.execSQL(TABLE_INSERTION43);
		db.execSQL(TABLE_INSERTION44);
		db.execSQL(TABLE_INSERTION45);
		db.execSQL(TABLE_INSERTION46);
		db.execSQL(TABLE_INSERTION47);
		db.execSQL(TABLE_INSERTION48);
		db.execSQL(TABLE_INSERTION49);
		db.execSQL(TABLE_INSERTION50);
		db.execSQL(TABLE_INSERTION51);
		db.execSQL(TABLE_INSERTION52);
		db.execSQL(TABLE_INSERTION53);
		db.execSQL(TABLE_INSERTION54);
		db.execSQL(TABLE_INSERTION55);
		db.execSQL(TABLE_INSERTION56);
		db.execSQL(TABLE_INSERTION57);
		db.execSQL(TABLE_INSERTION58);
		db.execSQL(TABLE_INSERTION59);
		Log.i(TAG, "Database Created.");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.i(TAG, "Database Upgraded.");
	}
}