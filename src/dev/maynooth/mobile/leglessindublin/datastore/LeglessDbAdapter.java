package dev.maynooth.mobile.leglessindublin.datastore;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * This class opens, returns and closes an SQLite database.
 * 
 * @author Evan Dooner, 12262480
 * @author Dennis Muldoon, 12260550
 * 
 * @version 2013-12-08-00
 * 
 */
public class LeglessDbAdapter {

	// Local implementation of the SQLiteAssetHelper class
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

	/**
	 * Closes the SQLite database
	 */
	public void close() {
		mDbHelper.close();
	}

	/**
	 * Returns an SQLite database
	 * 
	 * @return mDb
	 * 			- an SqliteDatabase
	 */
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
