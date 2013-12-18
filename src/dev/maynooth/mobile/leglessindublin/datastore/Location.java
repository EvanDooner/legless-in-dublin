package dev.maynooth.mobile.leglessindublin.datastore;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Represents a location.
 * <p>
 * As valid locations are read from the database, this class cannot be
 * instantiated normally. Instead, use static fetch methods to get the valid
 * locations.
 * 
 * @author Evan Dooner, 12262480
 * @author Dennis Muldoon, 12260550
 * 
 * @version 2013-12-08-00
 */
public class Location {

	/**
	 * Represents the fields contained in the location database table. Used in
	 * generating queries.
	 */
	public enum LocationField {
		//@formatter:off
		ROWID("_id"),
		LOCATION("loc");
		//@formatter:on

		public String fieldName;

		private LocationField(String fieldName) {
			this.fieldName = fieldName;
		}
	}
	
	/**
	 * Finds the ID of a location in the database
	 * 
	 * @param dbConnect
	 *            an SQLiteDatabase - the database to query
	 * @param locations
	 *            a string - the location
	 * @return an int - the id of the specified location
	 */
	public static int findIdByLocation(SQLiteDatabase dbConnect, String location) {
		Cursor mCursor = dbConnect.query(LOCATION_TABLE, LOCATION_ID,
				WHERE_LOCATION_EQUALS, new String[] { location }, null, null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}

		return mCursor.getInt(0);
	}

	private static final String LOCATION_TABLE = "location";
	private static final String[] LOCATION_ID = {LocationField.ROWID.fieldName};
	private static final String WHERE_LOCATION_EQUALS = LocationField.LOCATION.fieldName + "=?";

	/**
	 * Fetches all locations from the database.
	 * 
	 * @param dbConnect
	 *            an sQLiteDatabase - the database from which to retrieve the
	 *            locations
	 * @return all the locations in the database
	 */
	public static List<Location> fetchAll(final SQLiteDatabase dbConnect) {

		Cursor mCursor =

		dbConnect.query(LOCATION_TABLE,
				new String[] { LocationField.ROWID.fieldName,
						LocationField.LOCATION.fieldName }, null, null, null,
				null, LocationField.ROWID.fieldName);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}

		List<Location> results = new ArrayList<Location>();
		while (!mCursor.isAfterLast()) {
			Location currentRow = buildLocation(mCursor);
			results.add(currentRow);
			mCursor.moveToNext();
		}

		return results;
	}

	/**
	 * Fetches all location names from the database.
	 * 
	 * @param dbConnect
	 *            an sQLiteDatabase - the database from which to retrieve the
	 *            location names
	 * @return all the location names in the database
	 */
	public static List<String> fetchAllLocationNames(
			final SQLiteDatabase dbConnect) {

		Cursor mCursor =

		dbConnect.query(LOCATION_TABLE,
				new String[] { LocationField.LOCATION.fieldName }, null, null,
				null, null, LocationField.ROWID.fieldName);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}

		List<String> results = new ArrayList<String>();
		while (!mCursor.isAfterLast()) {
			results.add(mCursor.getString(0));
			mCursor.moveToNext();
		}

		return results;
	}

	/**
	 * Returns the name of the location table in the database.
	 * 
	 * @return a String - the name of the location table in the database.
	 */
	public static String getTableName() {
		return LOCATION_TABLE;
	}

	/**
	 * Builds a location object from a location record in the database
	 * 
	 * @param mCursor
	 *            a cursor - the cursor pointing to the record to be used
	 * @return a location - the object representing the database record
	 */
	private static Location buildLocation(Cursor mCursor) {
		int idIndex = mCursor.getColumnIndex(LocationField.ROWID.fieldName);
		int rowId = mCursor.getInt(idIndex);

		int locationIndex = mCursor
				.getColumnIndex(LocationField.LOCATION.fieldName);
		String location = mCursor.getString(locationIndex);

		return new Location(rowId, location);
	}

	private int rowId;

	private String location;

	/**
	 * Constructs a location with id and location.
	 * 
	 * This constructor is private - only locations fetched from the database
	 * can be initialized with an id
	 * 
	 * @param rowId
	 *            an int - the id of the location in the database
	 * @param location
	 *            a string - the location
	 */
	private Location(int rowId, String location) {
		this.rowId = rowId;
		this.location = location;
	}

	/**
	 * Returns the name of a location.
	 * 
	 * @return a String - the name of a location.
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * Returns the database rowId of a location.
	 * 
	 * @return an int - the database rowId of a location.
	 */
	public int getRowId() {
		return rowId;
	}

	/**
	 * Sets the name of a location.
	 * 
	 * @param location
	 * 				 a String - the name of the location.
	 */
	public void setLocation(String location) {
		this.location = location;
	}

}
