package dev.maynooth.mobile.leglessindublin.datastore;

//import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Legless in Dublin database access helper class. Defines the basic CRUD
 * operations for the Legless in Dublin app, and gives the ability to list all
 * venues and ratings as well as retrieve or modify a specific entry.
 */
public class LeglessDbAdapter {

	// Local implementation of the SQLiteOpenHelper class
	private static class DatabaseHelper extends SQLiteAssetHelper {
		
		private static final String DATABASE_NAME = "legless";
		private static final int DATABASE_VERSION = 1;

		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}
	}

	private DatabaseHelper mDbHelper;
	private SQLiteDatabase mDb;

	private final Context mCtx;

	/**
	 * Constructor - takes the context to allow the database to be
	 * opened/created
	 * 
	 * @param ctx
	 *            the Context within which to work
	 */
	public LeglessDbAdapter(Context ctx) {
		this.mCtx = ctx;
	}

	public void close() {
		mDbHelper.close();
	}

	public SQLiteDatabase getDbConnect() {
		return mDb;
	}

	/**
	 * Open the database. If it cannot be opened, try to create a new instance
	 * of the database. If it cannot be created, throw an exception to signal
	 * the failure
	 * 
	 * @return this (self reference, allowing this to be chained in an
	 *         initialization call)
	 * @throws SQLException
	 *             if the database could be neither opened or created
	 */
	public LeglessDbAdapter open() throws SQLException {
		mDbHelper = new DatabaseHelper(mCtx);
		mDb = mDbHelper.getWritableDatabase();
		return this;
	}
}
