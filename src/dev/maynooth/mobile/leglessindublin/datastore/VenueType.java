package dev.maynooth.mobile.leglessindublin.datastore;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class VenueType {

	/**
	 * Represents the fields contained in the venue_type database table. Used in
	 * generating queries.
	 */
	public enum VenueTypeField {
		//@formatter:off
		ROWID("_id"),
		TYPE("venue_type");
		//@formatter:on

		public String fieldName;

		private VenueTypeField(String fieldName) {
			this.fieldName = fieldName;
		}
	}

	/**
	 * Finds the ID of a venue type in the database
	 * 
	 * @param dbConnect
	 *            an SQLiteDatabase - the database to query
	 * @param type
	 *            a string - the venue type
	 * @return an int - the id of the specified venue type
	 */
	public static int findIdByType(SQLiteDatabase dbConnect, String type) {
		Cursor mCursor = dbConnect.query(VENUE_TYPE_TABLE, VENUE_TYPE_ID,
				WHERE_TYPE_EQUALS, new String[] { type }, null, null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		} else {
			Log.e("Lglss", "mCursor is null");
			return -1;
		}

		return mCursor.getInt(0);
	}

	private static final String VENUE_TYPE_TABLE = "venue_type";
	private static final String[] VENUE_TYPE_ID = { VenueTypeField.ROWID.fieldName };
	private static final String WHERE_TYPE_EQUALS = VenueTypeField.TYPE.fieldName
			+ "=?";

	/**
	 * Builds a venueType object from a venue type record in the database
	 * 
	 * @param mCursor
	 *            a cursor - the cursor pointing to the record to be used
	 * @return a venueType - the object representing the database record
	 */
	private static VenueType buildVenueType(Cursor mCursor) {
		int idIndex = mCursor.getColumnIndex(VenueTypeField.ROWID.fieldName);
		int rowId = mCursor.getInt(idIndex);

		int typeIndex = mCursor.getColumnIndex(VenueTypeField.TYPE.fieldName);
		String venueType = mCursor.getString(typeIndex);

		return new VenueType(rowId, venueType);
	}

	/**
	 * Fetches all venue types from the database.
	 * 
	 * @param dbConnect
	 *            an sQLiteDatabase - the database from which to retrieve the
	 *            venue types
	 * @return all the venue types in the database
	 */
	public static List<VenueType> fetchAll(SQLiteDatabase dbConnect) {

		Cursor mCursor =

		dbConnect.query(VENUE_TYPE_TABLE,
				new String[] { VenueTypeField.ROWID.fieldName,
						VenueTypeField.TYPE.fieldName }, null, null, null,
				null, VenueTypeField.TYPE.fieldName);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}

		List<VenueType> results = new ArrayList<VenueType>();
		while (!mCursor.isAfterLast()) {
			VenueType currentRow = buildVenueType(mCursor);
			results.add(currentRow);
			mCursor.moveToNext();
		}

		return results;
	}

	/**
	 * Fetches all venue type names from the database.
	 * 
	 * @param dbConnect
	 *            an sQLiteDatabase - the database from which to retrieve the
	 *            venue type names
	 * @return all the venue type names in the database
	 */
	public static List<String> fetchAllTypeNames(SQLiteDatabase dbConnect) {

		Cursor mCursor =

		dbConnect.query(VENUE_TYPE_TABLE,
				new String[] { VenueTypeField.TYPE.fieldName }, null, null,
				null, null, VenueTypeField.TYPE.fieldName);
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

	public static String getTableName() {
		return VENUE_TYPE_TABLE;
	}

	private int rowId;

	private String venueType;

	private VenueType(int rowId, String venueType) {
		this.rowId = rowId;
		this.venueType = venueType;
	}

	public int getRowId() {
		return rowId;
	}

	public String getVenueType() {
		return venueType;
	}

	public void setVenueType(String venueType) {
		this.venueType = venueType;
	};
}
