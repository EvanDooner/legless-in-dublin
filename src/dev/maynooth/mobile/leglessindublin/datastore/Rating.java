package dev.maynooth.mobile.leglessindublin.datastore;

import java.util.ArrayList;
import java.util.List;

import dev.maynooth.mobile.leglessindublin.datastore.Venue.VenueField;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Represents a database model of a venue rating.
 * 
 * @author Evan Dooner, 12262480
 * @author Dennis Muldoon, 12260550
 * 
 * @version 2013-11-25-00
 */
public class Rating implements Model {

	/**
	 * Represents the fields contained in the ratings database table. Used in
	 * generating queries for the finders.
	 */
	public enum RatingField {
		//@formatter:off
		ROWID("_id"),
		VENUE_ID("venue"),
		APPROACH("approach"),
		DOORS("doors"),
		FLOORING("flooring"),
		STEPS("steps"),
		LIFTS("lifts"),
		BATHROOMS("bathrooms"),
		LAYOUT("layout"),
		STAFF("staff"),
		PARKING("parking"),
		SUB_TOTAL("sub_total");
		//@formatter:on

		public String fieldName;

		private RatingField(String fieldName) {
			this.fieldName = fieldName;
		}
	}

	private static final String RATING_TABLE = "rating";

	/**
	 * Returns a rating by id.
	 * 
	 * @param ratingId
	 *            an int - the id of the rating to be retrieved
	 * @return a rating - the rating with the specified id
	 */
	public static Rating findById(int ratingId, SQLiteDatabase dbConnect) {

		Cursor mCursor =

		dbConnect.query(true, RATING_TABLE,
				new String[] { RatingField.ROWID.fieldName,
						RatingField.VENUE_ID.fieldName,
						RatingField.APPROACH.fieldName,
						RatingField.DOORS.fieldName,
						RatingField.FLOORING.fieldName,
						RatingField.STEPS.fieldName,
						RatingField.LIFTS.fieldName,
						RatingField.BATHROOMS.fieldName,
						RatingField.LAYOUT.fieldName,
						RatingField.STAFF.fieldName,
						RatingField.PARKING.fieldName,
						RatingField.SUB_TOTAL.fieldName },
				RatingField.ROWID.fieldName + "=" + ratingId, null, null, null,
				null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}

		Rating result = buildRating(mCursor);

		return result;
	}

	/**
	 * Returns all the ratings associated with a particular venue
	 * 
	 * @param venueId
	 *            an int - the id of the associated venue
	 * @return all the ratings associated with the specified venue
	 */
	public static List<Rating> findByVenueId(int venueId,
			SQLiteDatabase dbConnect) {

		Cursor mCursor =

		dbConnect.query(RATING_TABLE,
				new String[] { RatingField.ROWID.fieldName,
						RatingField.VENUE_ID.fieldName,
						RatingField.APPROACH.fieldName,
						RatingField.DOORS.fieldName,
						RatingField.FLOORING.fieldName,
						RatingField.STEPS.fieldName,
						RatingField.LIFTS.fieldName,
						RatingField.BATHROOMS.fieldName,
						RatingField.LAYOUT.fieldName,
						RatingField.STAFF.fieldName,
						RatingField.PARKING.fieldName,
						RatingField.SUB_TOTAL.fieldName },
				RatingField.VENUE_ID.fieldName + "=" + venueId, null, null,
				null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}

		List<Rating> ratingList = new ArrayList<Rating>();
		int rowCount = mCursor.getCount();
		while (mCursor.getPosition() < rowCount) {
			Rating result = buildRating(mCursor);
			ratingList.add(result);
			mCursor.moveToNext();
		}

		return ratingList;
	}
	
	/**
	 * Returns the number of ratings associated with a particular venue
	 * 
	 * @param venueId
	 *            an int - the id of the associated venue
	 * @return an int - the number of ratings
	 */
	public static int findCountByVenueId(int venueId,
			SQLiteDatabase dbConnect) {
		
		int result = 0;

		Cursor mCursor =

		dbConnect.query(RATING_TABLE,
				new String[] { "COUNT(*)" },
				WHERE_VENUE_ID_EQUALS, new String[] {"" + venueId}, null,
				null, null);
		if (mCursor != null && mCursor.moveToFirst()) {
			result = mCursor.getInt(0);
		}

		return result;
	}
	
	private static final String WHERE_VENUE_ID_EQUALS = RatingField.VENUE_ID.fieldName + "=?";
	/**
	 * Returns all rating objects that match the search query, i.e. their value
	 * for the specified field is the same as the specified value.
	 * 
	 * @param field
	 *            a ratingField - the field to query
	 * @param value
	 *            an int - the value of the field
	 * @return all the ratings that match the specified requirements
	 */
	public static List<Rating> findEquals(RatingField field, int value,
			SQLiteDatabase dbConnect) {

		Cursor mCursor =

		dbConnect.query(RATING_TABLE,
				new String[] { RatingField.ROWID.fieldName,
						RatingField.VENUE_ID.fieldName,
						RatingField.APPROACH.fieldName,
						RatingField.DOORS.fieldName,
						RatingField.FLOORING.fieldName,
						RatingField.STEPS.fieldName,
						RatingField.LIFTS.fieldName,
						RatingField.BATHROOMS.fieldName,
						RatingField.LAYOUT.fieldName,
						RatingField.STAFF.fieldName,
						RatingField.PARKING.fieldName,
						RatingField.SUB_TOTAL.fieldName }, field.fieldName
						+ "=" + value, null, null, null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}

		List<Rating> ratingList = new ArrayList<Rating>();
		int rowCount = mCursor.getCount();
		while (mCursor.getPosition() < rowCount) {
			Rating result = buildRating(mCursor);
			ratingList.add(result);
			mCursor.moveToNext();
		}

		return ratingList;
	}

	/**
	 * Returns the name of the rating table in the database.
	 * 
	 * @return a String - the name of the rating table in the database.
	 */
	public static String getTableName() {
		return RATING_TABLE;
	}

	/**
	 * Fetches all stored ratings
	 * 
	 * @return all ratings
	 */
	public static List<Rating> listAll(SQLiteDatabase dbConnect) {

		Cursor mCursor =

		dbConnect.query(RATING_TABLE,
				new String[] { RatingField.ROWID.fieldName,
						RatingField.VENUE_ID.fieldName,
						RatingField.APPROACH.fieldName,
						RatingField.DOORS.fieldName,
						RatingField.FLOORING.fieldName,
						RatingField.STEPS.fieldName,
						RatingField.LIFTS.fieldName,
						RatingField.BATHROOMS.fieldName,
						RatingField.LAYOUT.fieldName,
						RatingField.STAFF.fieldName,
						RatingField.PARKING.fieldName,
						RatingField.SUB_TOTAL.fieldName }, null, null, null,
				null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}

		// Create an ArrayList to hold the results of the query
		List<Rating> ratingList = new ArrayList<Rating>();
		// Count the number of rows in the result set
		int rowCount = mCursor.getCount();
		// Loop mCursor through the rows of the result set
		while (mCursor.getPosition() < rowCount) {
			// Build a Rating object for each row
			Rating result = buildRating(mCursor);
			// Store each Rating object in the List
			ratingList.add(result);
			// Move mCursor to the next row
			mCursor.moveToNext();
		}

		return ratingList;
	}

	/**
	 * Builds a Rating object from the values of a row of data that is returned
	 * by a query on the database
	 * 
	 * @param mCursor
	 * @return - Rating object
	 */
	private static Rating buildRating(Cursor mCursor) {
		int rowIdIndex = mCursor.getColumnIndex(RatingField.ROWID.fieldName);
		int id = mCursor.getInt(rowIdIndex);

		int venueIdIndex = mCursor
				.getColumnIndex(RatingField.VENUE_ID.fieldName);
		int venueId = mCursor.getInt(venueIdIndex);

		int approachIndex = mCursor
				.getColumnIndex(RatingField.APPROACH.fieldName);
		double approach = mCursor.getDouble(approachIndex);

		int doorsIndex = mCursor.getColumnIndex(RatingField.DOORS.fieldName);
		double doors = mCursor.getDouble(doorsIndex);

		int flooringIndex = mCursor
				.getColumnIndex(RatingField.FLOORING.fieldName);
		double flooring = mCursor.getDouble(flooringIndex);

		int stepsIndex = mCursor.getColumnIndex(RatingField.STEPS.fieldName);
		double steps = mCursor.getDouble(stepsIndex);

		int liftsIndex = mCursor.getColumnIndex(RatingField.LIFTS.fieldName);
		double lifts = mCursor.getDouble(liftsIndex);

		int bathroomsIndex = mCursor
				.getColumnIndex(RatingField.BATHROOMS.fieldName);
		double bathrooms = mCursor.getDouble(bathroomsIndex);

		int layoutIndex = mCursor.getColumnIndex(RatingField.LAYOUT.fieldName);
		double layout = mCursor.getDouble(layoutIndex);

		int staffIndex = mCursor.getColumnIndex(RatingField.STAFF.fieldName);
		double staff = mCursor.getDouble(staffIndex);

		int parkingIndex = mCursor
				.getColumnIndex(RatingField.PARKING.fieldName);
		double parking = mCursor.getDouble(parkingIndex);

		int subRatingIndex = mCursor
				.getColumnIndex(RatingField.SUB_TOTAL.fieldName);
		double subRating = mCursor.getDouble(subRatingIndex);

		Rating result = new Rating(venueId);
		result.rowid = id;
		result.approach = approach;
		result.doors = doors;
		result.flooring = flooring;
		result.steps = steps;
		result.lifts = lifts;
		result.bathrooms = bathrooms;
		result.layout = layout;
		result.staff = staff;
		result.parking = parking;
		result.subTotal = subRating;

		return result;
	}

	private int rowid;
	private int venueId;
	private double approach;
	private double doors;
	private double flooring;
	private double steps;
	private double lifts;
	private double bathrooms;
	private double layout;
	private double staff;
	private double parking;
	private double subTotal;


	/**
	 * Constructs a rating object.
	 * 
	 * @param venueid
	 * 				- an int - the id of the venue for this rating. 
	 */
	public Rating(int venueid) {
		this.venueId = venueid;
	}

	@Override
	//Removes this rating model from the database.
	public void delete(SQLiteDatabase dbConnect) {
		dbConnect.delete(RATING_TABLE, VenueField.ROWID.fieldName + "="
				+ this.rowid, null);
	}

	/**
	 * Returns the Approach rating of a rating object.
	 * 
	 * @return approach 
	 * 				- a double - the Approach rating of a rating object.   
	 */
	public double getApproach() {
		return approach;
	}

	/**
	 * Returns the Bathrooms rating of a rating object.
	 * 
	 * @return bathrooms 
	 * 				- a double - the Bathrooms rating of a rating object.   
	 */
	public double getBathrooms() {
		return bathrooms;
	}

	/**
	 * Returns the Doors rating of a rating object.
	 * 
	 * @return doors 
	 * 				- a double - the Doors rating of a rating object.   
	 */
	public double getDoors() {
		return doors;
	}

	/**
	 * Returns the Flooring rating of a rating object.
	 * 
	 * @return flooring 
	 * 				- a double - the Flooring rating of a rating object.   
	 */
	public double getFlooring() {
		return flooring;
	}

	/**
	 * Returns the Layout rating of a rating object.
	 * 
	 * @return layout 
	 * 				- a double - the Layout rating of a rating object.   
	 */
	public double getLayout() {
		return layout;
	}

	/**
	 * Returns the Lifts rating of a rating object.
	 * 
	 * @return lifts 
	 * 				- a double - the Lifts rating of a rating object.   
	 */
	public double getLifts() {
		return lifts;
	}

	/**
	 * Returns the Parking rating of a rating object.
	 * 
	 * @return parking 
	 * 				- a double - the Parking rating of a rating object.   
	 */
	public double getParking() {
		return parking;
	}

	/**
	 * Returns the database row id of a rating object.
	 * 
	 * @return rowid 
	 * 				- an int - the database row id of a rating object.   
	 */
	public int getRowid() {
		return rowid;
	}

	/**
	 * Returns the Staff rating of a rating object.
	 * 
	 * @return staff 
	 * 				- a double - the Staff rating of a rating object.   
	 */
	public double getStaff() {
		return staff;
	}

	/**
	 * Returns the Steps rating of a rating object.
	 * 
	 * @return steps 
	 * 				- a double - the Steps rating of a rating object.   
	 */
	public double getSteps() {
		return steps;
	}

	/**
	 * Returns the SubTotal rating of a rating object.
	 * 
	 * @return subTotal 
	 * 				- a double - the SubTotal rating of a rating object.   
	 */
	public double getSubTotal() {
		return subTotal;
	}

	/**
	 * Returns the VenueId of a rating object.
	 * 
	 * @return venueId 
	 * 				- an int - the VenueId of a rating object.   
	 */
	public int getVenueId() {
		return venueId;
	}

	@Override
	// Persists this rating model to the database
	public void save(SQLiteDatabase dbConnect) {

		ContentValues initialValues = new ContentValues();
		initialValues.put(RatingField.VENUE_ID.fieldName, this.venueId);
		initialValues.put(RatingField.APPROACH.fieldName, this.approach);
		initialValues.put(RatingField.DOORS.fieldName, this.doors);
		initialValues.put(RatingField.FLOORING.fieldName, this.flooring);
		initialValues.put(RatingField.STEPS.fieldName, this.steps);
		initialValues.put(RatingField.LIFTS.fieldName, this.lifts);
		initialValues.put(RatingField.BATHROOMS.fieldName, this.bathrooms);
		initialValues.put(RatingField.LAYOUT.fieldName, this.layout);
		initialValues.put(RatingField.STAFF.fieldName, this.staff);
		initialValues.put(RatingField.PARKING.fieldName, this.parking);
		initialValues.put(RatingField.SUB_TOTAL.fieldName, this.subTotal);

		dbConnect.insert(RATING_TABLE, null, initialValues);
	}

	/**
	 * Sets the Approach rating of a rating object.
	 * 
	 * @param approach
	 * 				- a double - the Approach rating of a rating object.
	 */
	public void setApproach(double approach) {
		this.approach = approach;
	}

	/**
	 * Sets the Bathrooms rating of a rating object.
	 * 
	 * @param bathrooms
	 * 				- a double - the Bathrooms rating of a rating object.
	 */
	public void setBathrooms(double bathrooms) {
		this.bathrooms = bathrooms;
	}

	/**
	 * Sets the Doors rating of a rating object.
	 * 
	 * @param doors
	 * 				- a double - the Doors rating of a rating object.
	 */
	public void setDoors(double doors) {
		this.doors = doors;
	}

	/**
	 * Sets the Flooring rating of a rating object.
	 * 
	 * @param flooring
	 * 				- a double - the Flooring rating of a rating object.
	 */
	public void setFlooring(double flooring) {
		this.flooring = flooring;
	}

	/**
	 * Sets the Layout rating of a rating object.
	 * 
	 * @param layout
	 * 				- a double - the Layout rating of a rating object.
	 */
	public void setLayout(double layout) {
		this.layout = layout;
	}

	/**
	 * Sets the Lifts rating of a rating object.
	 * 
	 * @param lifts
	 * 				- a double - the Lifts rating of a rating object.
	 */
	public void setLifts(double lifts) {
		this.lifts = lifts;
	}

	/**
	 * Sets the Parking rating of a rating object.
	 * 
	 * @param parking
	 * 				- a double - the Parking rating of a rating object.
	 */
	public void setParking(double parking) {
		this.parking = parking;
	}

	/**
	 * Sets the Staff rating of a rating object.
	 * 
	 * @param staff
	 * 				- a double - the Staff rating of a rating object.
	 */
	public void setStaff(double staff) {
		this.staff = staff;
	}

	/**
	 * Sets the Steps rating of a rating object.
	 * 
	 * @param steps
	 * 				- a double - the Steps rating of a rating object.
	 */
	public void setSteps(double steps) {
		this.steps = steps;
	}

	/**
	 * Sets the SubTotal rating of a rating object.
	 * 
	 * @param subrating
	 * 				- a double - the SubTotal rating of a rating object.
	 */
	public void setSubTotal(double subrating) {
		this.subTotal = subrating;
	}

	/**
	 * Sets the VenueId of a rating object.
	 * 
	 * @param venueid
	 * 				- an int - the VenueId of a rating object.
	 */
	public void setVenueId(int venueid) {
		this.venueId = venueid;
	}

	@Override
	// Updates this rating model's record in the database
	public void update(SQLiteDatabase dbConnect) {

		ContentValues args = new ContentValues();
		args.put(RatingField.VENUE_ID.fieldName, this.venueId);
		args.put(RatingField.APPROACH.fieldName, this.approach);
		args.put(RatingField.DOORS.fieldName, this.doors);
		args.put(RatingField.FLOORING.fieldName, this.flooring);
		args.put(RatingField.STEPS.fieldName, this.steps);
		args.put(RatingField.LIFTS.fieldName, this.lifts);
		args.put(RatingField.BATHROOMS.fieldName, this.bathrooms);
		args.put(RatingField.LAYOUT.fieldName, this.layout);
		args.put(RatingField.STAFF.fieldName, this.staff);
		args.put(RatingField.PARKING.fieldName, this.parking);
		args.put(RatingField.SUB_TOTAL.fieldName, this.subTotal);

		dbConnect.update(RATING_TABLE, args, VenueField.ROWID.fieldName + "="
				+ this.rowid, null);
	}

}
