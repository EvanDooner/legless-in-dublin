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
	 * generating queries for the finders.
	 */
	public enum VenueField {
		ROWID("_id"), NAME("name"), ADDRESS_1("address_line_1"), ADDRESS_2(
				"address_line2"), ADDRESS_3("address_line_3"), CATEGORY(
				"category"), TOT_RATING("tot_rating");

		public String fieldName;

		private VenueField(String fieldName) {
			this.fieldName = fieldName;
		}
	}

	private int rowid;
	private String name;
	private String addressLine1;
	private String addressLine2;
	private String addressLine3;
	private String category;
	private int totalRating;

	/**
	 * Lists all venues in the database
	 * 
	 * @return all the venues
	 */
	public static List<Venue> listAll(SQLiteDatabase dbConnect) {

		Cursor mCursor =

		dbConnect.query(LeglessDbAdapter.DATABASE_VENUE_TABLE,
				new String[] { VenueField.ROWID.fieldName,
						VenueField.NAME.fieldName,
						VenueField.ADDRESS_1.fieldName,
						VenueField.ADDRESS_2.fieldName,
						VenueField.ADDRESS_3.fieldName,
						VenueField.CATEGORY.fieldName,
						VenueField.TOT_RATING.fieldName }, 
						null, null, null, null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		
		//Create an ArrayList to hold the results of the query
		List<Venue> venueList = new ArrayList<Venue>();
		//Count the number of rows in the result set
		int rowCount = mCursor.getCount();
		//Loop mCursor through the rows of the result set
		while(mCursor.getPosition() < rowCount){
			//Build a Venue object for each row
			Venue result = buildVenue(mCursor);
			//Store each venue object in the List
			venueList.add(result);
			//Move mCursor to the next row
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
	 *            an int - the number of the page to retrieve
	 * @param entriesPerPage
	 *            an int - the number of venue entries per page
	 * @param orderBy
	 *            a venueField - the field on which to order the list
	 * @return a page of venues, in the specified order
	 */
	public static List<Venue> page(int pageNumber, int entriesPerPage,
			VenueField orderBy, SQLiteDatabase dbConnect) {

		Cursor mCursor =

		dbConnect.query(LeglessDbAdapter.DATABASE_VENUE_TABLE,
				new String[] { VenueField.ROWID.fieldName,
						VenueField.NAME.fieldName,
						VenueField.ADDRESS_1.fieldName,
						VenueField.ADDRESS_2.fieldName,
						VenueField.ADDRESS_3.fieldName,
						VenueField.CATEGORY.fieldName,
						VenueField.TOT_RATING.fieldName }, 
						null, null, null, null, orderBy.fieldName);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		
		int startingRow = (pageNumber-1)*entriesPerPage;
		int endingRow = (pageNumber * entriesPerPage)-1;
		List<Venue> venueList = new ArrayList<Venue>();
		for(mCursor.moveToPosition(startingRow); 
				mCursor.getPosition()<=endingRow; 
				mCursor.moveToNext()){
			Venue result = buildVenue(mCursor);
			venueList.add(result);
		}
		
		return venueList;		
	}

	/**
	 * Finds the venue with the specified id
	 * 
	 * @param id
	 *            an int - the id of the venue to be retrieved
	 * @return a venue with the specified id
	 */
	public static Venue findById(int rowId, SQLiteDatabase dbConnect) {

		Cursor mCursor =

		dbConnect.query(true, LeglessDbAdapter.DATABASE_VENUE_TABLE,
				new String[] { VenueField.ROWID.fieldName,
						VenueField.NAME.fieldName,
						VenueField.ADDRESS_1.fieldName,
						VenueField.ADDRESS_2.fieldName,
						VenueField.ADDRESS_3.fieldName,
						VenueField.CATEGORY.fieldName,
						VenueField.TOT_RATING.fieldName },
				VenueField.ROWID.fieldName + "=" + rowId, null, null, null,
				null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		
		Venue result = buildVenue(mCursor);
		
		return result;
	}

	/**
	 * Builds a Venue object from the values of a row of data that is returned by a
	 * query on the database
	 * @param mCursor
	 * @return - Venue object
	 */
	private static Venue buildVenue(Cursor mCursor) {
		int rowIdIndex = mCursor.getColumnIndex(VenueField.ROWID.fieldName);
		int id = mCursor.getInt(rowIdIndex);
		
		int nameIndex = mCursor.getColumnIndex(VenueField.NAME.fieldName);
		String name = mCursor.getString(nameIndex);
		
		int address1Index = mCursor.getColumnIndex(VenueField.ADDRESS_1.fieldName);
		String addressLine1 = mCursor.getString(address1Index);
		
		int address2Index = mCursor.getColumnIndex(VenueField.ADDRESS_2.fieldName);
		String addressLine2 = mCursor.getString(address2Index);
		
		int address3Index = mCursor.getColumnIndex(VenueField.ADDRESS_3.fieldName);
		String addressLine3 = mCursor.getString(address3Index);
		
		int categoryIndex = mCursor.getColumnIndex(VenueField.CATEGORY.fieldName);
		String category = mCursor.getString(categoryIndex);
		
		int totRatingIndex = mCursor.getColumnIndex(VenueField.TOT_RATING.fieldName);
		int totRating = mCursor.getInt(totRatingIndex);
		
		Venue result = new Venue(name, addressLine1, category);
		result.rowid = id;
		result.addressLine2 = addressLine2;
		result.addressLine3 = addressLine3;
		result.category = category;
		result.totalRating = totRating;
		return result;
	}

	// Either specify all fields in constructor, or allow them to be set after
	// construction
	// using setters. Could also look into using a Builder pattern, but prob.
	// overkill
	public Venue(String name, String addressLine1, String category) {
		this.name = name;
		this.addressLine1 = addressLine1;
		this.category = category;
	}

	@Override
	// Removes this venue model from the database
	public void delete(SQLiteDatabase dbConnect) {
		// Delete any associated ratings before deleting a venue?
		dbConnect.delete(LeglessDbAdapter.DATABASE_VENUE_TABLE,
				VenueField.ROWID.fieldName + "=" + this.rowid, null);
	}

	@Override
	// Persists this venue model to the database
	public void save(SQLiteDatabase dbConnect) {

		ContentValues initialValues = new ContentValues();
		initialValues.put(VenueField.NAME.fieldName, this.name);
		initialValues.put(VenueField.ADDRESS_1.fieldName, this.addressLine1);
		initialValues.put(VenueField.ADDRESS_2.fieldName, this.addressLine2);
		initialValues.put(VenueField.ADDRESS_3.fieldName, this.addressLine3);
		initialValues.put(VenueField.CATEGORY.fieldName, this.category);
		initialValues.put(VenueField.TOT_RATING.fieldName, this.totalRating);

		dbConnect.insert(LeglessDbAdapter.DATABASE_VENUE_TABLE, null,
				initialValues);
	}

	@Override
	// Updates this venue model's record in the database
	public void update(SQLiteDatabase dbConnect) {

		ContentValues args = new ContentValues();
		args.put(VenueField.NAME.fieldName, this.name);
		args.put(VenueField.ADDRESS_1.fieldName, this.addressLine1);
		args.put(VenueField.ADDRESS_2.fieldName, this.addressLine2);
		args.put(VenueField.ADDRESS_3.fieldName, this.addressLine3);
		args.put(VenueField.CATEGORY.fieldName, this.category);
		args.put(VenueField.TOT_RATING.fieldName, this.totalRating);

		dbConnect.update(LeglessDbAdapter.DATABASE_VENUE_TABLE, args,
				VenueField.ROWID.fieldName + "=" + this.rowid, null);
	}

	public int getRowid() {
		return rowid;
	}

	public String getName() {
		return name;
	}

	public String getAddressLine1() {
		return addressLine1;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public String getAddressLine3() {
		return addressLine3;
	}

	public String getCategory() {
		return category;
	}

	public int getTotalRating() {
		return totalRating;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	public void setAddressLine3(String addressLine3) {
		this.addressLine3 = addressLine3;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void setTotalRating(int totalRating) {
		this.totalRating = totalRating;
	}

}
