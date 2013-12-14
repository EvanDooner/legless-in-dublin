package dev.maynooth.mobile.leglessindublin.datastore;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Represents a database model of a venue.
 * 
 * @author Evan Dooner
 * @version 2013-11-25-00
 */
public class Venue implements Model {

	/**
	 * Represents the fields contained in the venue database table. Used in
	 * generating queries.
	 */
	public enum VenueField {
		//@formatter:off
		ROWID("_id"),
		NAME("name"),
		STREET_NAME("street_name"),
		LOCATION("location"),
		VENUE_TYPE("venue_type"),
		TOTAL_RATING("total_rating");
		//@formatter:on

		public String fieldName;

		private VenueField(String fieldName) {
			this.fieldName = fieldName;
		}
	}

	private static final String[] ALL_COLUMNS = { VenueField.ROWID.fieldName,
			VenueField.NAME.fieldName, VenueField.STREET_NAME.fieldName,
			VenueField.LOCATION.fieldName, VenueField.VENUE_TYPE.fieldName,
			VenueField.TOTAL_RATING.fieldName };

	private static final String WHERE_LOCATION_AND_TYPE_EQUALS = 
			VenueField.LOCATION.fieldName + "=? " +
			"AND " + 
			VenueField.VENUE_TYPE.fieldName + "=?";

	private static final String VENUE_TABLE = "venue";

	/**
	 * Finds the venue with the specified id
	 * 
	 * @param id
	 *            an int - the id of the venue to be retrieved
	 * @return a venue with the specified id
	 */
	public static Venue findById(int rowId, SQLiteDatabase dbConnect) {

		Cursor mCursor =

		dbConnect.query(true, VENUE_TABLE, new String[] {
				VenueField.ROWID.fieldName, VenueField.NAME.fieldName,
				VenueField.STREET_NAME.fieldName,
				VenueField.LOCATION.fieldName, VenueField.VENUE_TYPE.fieldName,
				VenueField.TOTAL_RATING.fieldName }, VenueField.ROWID.fieldName
				+ "=" + rowId, null, null, null, null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}

		Venue result = buildVenue(mCursor);

		return result;
	}

	/**
	 * Returns all venues that have the specified location and venue type.
	 * <p>
	 * <ul>
	 * <li><code>selectionArgs[0]</code> should contain required location id</li>
	 * <li><code>selectionArgs[1]</code> should contain required veueType id</li>
	 * </ul>
	 * 
	 * @param dbConnect
	 *            an SQLiteDatabase - the database in which to search
	 * @param selectionArgs
	 *            strings - the location and venue type to match against
	 * @return all venues that have the specified location and venue type
	 */
	public static List<Venue> findByLocationAndVenueType(
			SQLiteDatabase dbConnect, String[] selectionArgs) {
		
		Cursor mCursor = dbConnect.query(false, VENUE_TABLE, ALL_COLUMNS,
				WHERE_LOCATION_AND_TYPE_EQUALS, selectionArgs, null, null,
				VenueField.NAME.fieldName, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}

		List<Venue> results = new ArrayList<Venue>();
		while (!mCursor.isAfterLast()) {
			Venue result = buildVenue(mCursor);
			results.add(result);
			mCursor.moveToNext();
		}
		
		return results;
	}

	public static String getTableName() {
		return VENUE_TABLE;
	}

	/**
	 * Lists all venues in the database
	 * 
	 * @return all the venues
	 */
	public static List<Venue> listAll(SQLiteDatabase dbConnect) {

		Cursor mCursor =

		dbConnect.query(VENUE_TABLE, new String[] { VenueField.ROWID.fieldName,
				VenueField.NAME.fieldName, VenueField.STREET_NAME.fieldName,
				VenueField.LOCATION.fieldName, VenueField.VENUE_TYPE.fieldName,
				VenueField.TOTAL_RATING.fieldName }, null, null, null, null,
				null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}

		// Create an ArrayList to hold the results of the query
		List<Venue> venueList = new ArrayList<Venue>();
		// Loop mCursor through the rows of the result set
		while (!mCursor.isAfterLast()) {
			// Build a Venue object for each row
			Venue result = buildVenue(mCursor);
			// Store each venue object in the List
			venueList.add(result);
			// Move mCursor to the next row
			mCursor.moveToNext();
		}

		return venueList;
	}

	/**
	 * Returns a page of venues.
	 * <p>
	 * The page number and the number of venues per page can be specified, along
	 * with the column on which the list if ordered.
	 * 
	 * @param pageNumber
	 *            an int - the number of the page to retrieve - 0 indexed
	 * @param entriesPerPage
	 *            an int - the number of venue entries per page
	 * @param orderBy
	 *            a venueField - the field on which to order the list
	 * @return a page of venues, in the specified order
	 */
	public static List<Venue> page(int pageNumber, int entriesPerPage,
			VenueField orderBy, SQLiteDatabase dbConnect) {

		Cursor mCursor =

		// dbConnect.query(LeglessDbAdapter.DATABASE_VENUE_TABLE, new String[] {
		// VenueField.ROWID.fieldName, VenueField.NAME.fieldName,
		// VenueField.STREET_NAME.fieldName, VenueField.ADDRESS_2.fieldName,
		// VenueField.ADDRESS_3.fieldName, VenueField.VENUE_TYPE.fieldName,
		// VenueField.TOTAL_RATING.fieldName }, null, null, null, null,
		// orderBy.fieldName);
		dbConnect.query(VENUE_TABLE, new String[] { VenueField.ROWID.fieldName,
				VenueField.NAME.fieldName, VenueField.STREET_NAME.fieldName,
				VenueField.LOCATION.fieldName, VenueField.VENUE_TYPE.fieldName,
				VenueField.TOTAL_RATING.fieldName }, null, null, null, null,
				orderBy.fieldName, pageNumber * entriesPerPage + ", "
						+ ((pageNumber + 1) * entriesPerPage - 1));
		if (mCursor != null) {
			mCursor.moveToFirst();
		}

		// Create an ArrayList to hold the results of the query
		List<Venue> venueList = new ArrayList<Venue>();
		// Loop mCursor through the rows of the result set
		while (!mCursor.isAfterLast()) {
			// Build a Venue object for each row
			Venue result = buildVenue(mCursor);
			// Store each venue object in the List
			venueList.add(result);
			// Move mCursor to the next row
			mCursor.moveToNext();
		}

		return venueList;
	}

	/**
	 * Builds a Venue object from the values of a row of data that is returned
	 * by a query on the database
	 * 
	 * @param mCursor
	 * @return - Venue object
	 */
	private static Venue buildVenue(Cursor mCursor) {
		int rowIdIndex = mCursor.getColumnIndex(VenueField.ROWID.fieldName);
		int rowId = mCursor.getInt(rowIdIndex);

		int nameIndex = mCursor.getColumnIndex(VenueField.NAME.fieldName);
		String name = mCursor.getString(nameIndex);

		int streetNameIndex = mCursor
				.getColumnIndex(VenueField.STREET_NAME.fieldName);
		String streetName = mCursor.getString(streetNameIndex);

		int locationIndex = mCursor
				.getColumnIndex(VenueField.LOCATION.fieldName);
		int location = mCursor.getInt(locationIndex);

		int venueTypeIndex = mCursor
				.getColumnIndex(VenueField.VENUE_TYPE.fieldName);
		int venueType = mCursor.getInt(venueTypeIndex);

		int totalRatingIndex = mCursor
				.getColumnIndex(VenueField.TOTAL_RATING.fieldName);
		int totalRating = mCursor.getInt(totalRatingIndex);

		Venue result = new Venue(rowId, name, location, venueType);
		result.streetName = streetName;
		result.totalRating = totalRating;
		return result;
	}

	private int rowId;

	private String name;
	private String streetName;
	private int locationId;
	private int venueTypeId;
	private int totalRating;
	public Venue(String name, int location, int venueType) {
		this.name = name;
		this.locationId = location;
		this.venueTypeId = venueType;
	}

	private Venue(int rowId, String name, int location, int venueType) {
		this.rowId = rowId;
		this.name = name;
		this.locationId = location;
		this.venueTypeId = venueType;
	}

	@Override
	// Removes this venue model from the database
	public void delete(SQLiteDatabase dbConnect) {
		// Delete any associated ratings before deleting a venue?
		dbConnect.delete(VENUE_TABLE, VenueField.ROWID.fieldName + "="
				+ this.rowId, null);
	}

	public int getLocationId() {
		return locationId;
	}

	public String getName() {
		return name;
	}

	public int getRowId() {
		return rowId;
	}

	public String getStreetName() {
		return streetName;
	}

	public int getTotalRating() {
		return totalRating;
	}

	public int getVenueTypeId() {
		return venueTypeId;
	}

	@Override
	// Persists this venue model to the database
	public void save(SQLiteDatabase dbConnect) {

		ContentValues initialValues = new ContentValues();
		initialValues.put(VenueField.NAME.fieldName, this.name);
		initialValues.put(VenueField.STREET_NAME.fieldName, this.streetName);
		initialValues.put(VenueField.LOCATION.fieldName, this.locationId);
		initialValues.put(VenueField.VENUE_TYPE.fieldName, this.venueTypeId);
		initialValues.put(VenueField.TOTAL_RATING.fieldName, this.totalRating);

		dbConnect.insert(VENUE_TABLE, null, initialValues);
	}

	public void setLocation(Location location) {
		this.locationId = location.getRowId();
	}

	public void setLocationId(int location) {
		this.locationId = location;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setStreetName(String addressLine1) {
		this.streetName = addressLine1;
	}

	public void setTotalRating(int totalRating) {
		this.totalRating = totalRating;
	}

	public void setVenueType(VenueType venueType) {
		this.venueTypeId = venueType.getRowId();
	}

	public void setVenueTypeId(int venueType) {
		this.venueTypeId = venueType;
	}

	@Override
	public String toString() {
		return "Venue [rowId=" + rowId + ", name=" + name + ", streetName="
				+ streetName + ", locationId=" + locationId + ", venueTypeId="
				+ venueTypeId + ", totalRating=" + totalRating + "]";
	}

	@Override
	// Updates this venue model's record in the database
	public void update(SQLiteDatabase dbConnect) {

		ContentValues args = new ContentValues();
		args.put(VenueField.NAME.fieldName, this.name);
		args.put(VenueField.STREET_NAME.fieldName, this.streetName);
		args.put(VenueField.VENUE_TYPE.fieldName, this.venueTypeId);
		args.put(VenueField.TOTAL_RATING.fieldName, this.totalRating);

		dbConnect.update(VENUE_TABLE, args, VenueField.ROWID.fieldName + "="
				+ this.rowId, null);
	}

}
