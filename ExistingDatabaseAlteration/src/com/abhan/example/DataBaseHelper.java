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
	private static String DB_PATH = "/data/data/YOUR_PACKAGE/databases/";
	private static String DB_NAME = "DatabaseAlterDemo";
	private static final String TABLE_NAME = "Android";
	private static final String DROP_TABLE = "DROP TABLE IF EXISTS ";
	private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS ";
	private static final int dbVersion = 1;
	private SQLiteDatabase mDataBase;

	public DataBaseHelper(Context context) {
		super(context, DB_NAME, null, dbVersion);
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
			Log.e(Abhan.TAG, "DatabaseNotFound " + mSQLiteException.toString());
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
		db.execSQL(DROP_TABLE + TABLE_NAME);
		db.execSQL(CREATE_TABLE + TABLE_NAME + "(" + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ "name TEXT, " + "eventDate TEXT, " + "eventMonth TEXT, " + "eventYear TEXT, " + "event TEXT" + ")");
		db.execSQL("INSERT INTO " + TABLE_NAME + 
				"(_id, name, eventDate, eventMonth, eventYear, event) " +
				"VALUES (1, 'Android', '01-Jan-2013', 'January', '2013', 'Cupcake');");
		db.execSQL("INSERT INTO " + TABLE_NAME + 
				"(_id, name, eventDate, eventMonth, eventYear, event) " +
				"VALUES (2, 'Android', '02-Feb-2013', 'February', '2013', 'Donut');");
		db.execSQL("INSERT INTO " + TABLE_NAME + 
				"(_id, name, eventDate, eventMonth, eventYear, event) " +
				"VALUES (3, 'Android', '03-Mar-2013', 'March', '2013', 'Eclair');");
		db.execSQL("INSERT INTO " + TABLE_NAME + 
				"(_id, name, eventDate, eventMonth, eventYear, event) " +
				"VALUES (4, 'Android', '04-Apr-2013', 'April', '2013', 'Froyo');");
		db.execSQL("INSERT INTO " + TABLE_NAME + 
				"(_id, name, eventDate, eventMonth, eventYear, event) " +
				"VALUES (5, 'Android', '05-May-2013', 'May', '2013', 'Gingerbread');");
		db.execSQL("INSERT INTO " + TABLE_NAME + 
				"(_id, name, eventDate, eventMonth, eventYear, event) " +
				"VALUES (6, 'Android', '06-Jun-2013', 'June', '2013', 'HoneyComb');");
		db.execSQL("INSERT INTO " + TABLE_NAME + 
				"(_id, name, eventDate, eventMonth, eventYear, event) " +
				"VALUES (7, 'Android', '07-Jul-2013', 'July', '2013', 'ICS');");
		db.execSQL("INSERT INTO " + TABLE_NAME + 
				"(_id, name, eventDate, eventMonth, eventYear, event) " +
				"VALUES (8, 'Android', '08-Aug-2013', 'August', '2013', 'Jelly Bean');");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		if(Abhan.DEBUG) {
			android.util.Log.i(Abhan.TAG, "onUpgrade: " + oldVersion + " To " + newVersion);
		}
		migrateDatabase(db, oldVersion, newVersion);
	}
	
	private void migrateDatabase(SQLiteDatabase db, int oldVersion, int newVersion) {
		final int currentVersion = db.getVersion();
		if(Abhan.DEBUG) {
			android.util.Log.i(Abhan.TAG, "currentVersion: " + currentVersion);
		}
		
		if(currentVersion >= newVersion) {
			return;
		} else {
			db.beginTransaction();
			switch (currentVersion) {
				case 1:
					createTableOne(db);
					break;
				case 2:
					alterTableOne(db);
					break;
				case 3:
					createTableTwo(db);
					break;
				case 4:
					alterTableTwo(db);
					break;
				case 5:
					createTableThree(db);
					break;
				case 6:
					alterTableThree(db);
					break;
			}
			db.setTransactionSuccessful();
			db.endTransaction();
			db.setVersion(newVersion);
		}
	}

	private void createTableOne(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE IF NOT EXISTS Files1("
				+ "_id INTEGER PRIMARY KEY AUTOINCREMENT," + "name TEXT, "
				+ "size INTEGER" + ")");
		db.execSQL("INSERT INTO Files1("
				+ "_id, name, size)" +
				"VALUES (1, 'Android', 100);");
	}
	
	private void alterTableOne(SQLiteDatabase db) {
		db.execSQL("ALTER TABLE Files1 RENAME TO Files1_Obsolete");
		db.execSQL("CREATE TABLE IF NOT EXISTS Files1("
				+ "_id INTEGER PRIMARY KEY AUTOINCREMENT," + "name TEXT, "
				+ "size INTEGER, " + "version TEXT"+ ")");
		db.execSQL("INSERT INTO Files1("
				+ "_id, name, size) " +
				" SELECT _id, name, size FROM Files1_Obsolete");
		db.execSQL("DROP TABLE IF EXISTS Files1_Obsolete");
	}
	
	private void createTableTwo(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE IF NOT EXISTS Files2("
				+ "_id INTEGER PRIMARY KEY AUTOINCREMENT," + "name TEXT, "
				+ "size INTEGER" + ")");
		db.execSQL("INSERT INTO Files2("
				+ "_id, name, size)" +
				"VALUES (1, 'BB', 101);");
	}
	
	private void alterTableTwo(SQLiteDatabase db) {
		db.execSQL("ALTER TABLE Files2 RENAME TO Files2_Obsolete");
		db.execSQL("CREATE TABLE IF NOT EXISTS Files2("
				+ "_id INTEGER PRIMARY KEY AUTOINCREMENT," + "name TEXT, "
				+ "size INTEGER, " + "version TEXT"+ ")");
		db.execSQL("INSERT INTO Files2("
				+ "_id, name, size) " +
				" SELECT _id, name, size FROM Files2_Obsolete");
		db.execSQL("DROP TABLE IF EXISTS Files2_Obsolete");
	}
	
	private void createTableThree(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE IF NOT EXISTS Files3("
				+ "_id INTEGER PRIMARY KEY AUTOINCREMENT," + "name TEXT, "
				+ "size INTEGER" + ")");
		db.execSQL("INSERT INTO Files3("
				+ "_id, name, size)" +
				"VALUES (1, 'CC', 103);");
	}
	
	private void alterTableThree(SQLiteDatabase db) {
		db.execSQL("ALTER TABLE Files3 RENAME TO Files3_Obsolete");
		db.execSQL("CREATE TABLE IF NOT EXISTS Files3("
				+ "_id INTEGER PRIMARY KEY AUTOINCREMENT," + "name TEXT, "
				+ "size INTEGER, " + "version TEXT"+ ")");
		db.execSQL("INSERT INTO Files3("
				+ "_id, name, size) " +
				" SELECT _id, name, size FROM Files3_Obsolete");
		db.execSQL("DROP TABLE IF EXISTS Files3_Obsolete");
	}
}