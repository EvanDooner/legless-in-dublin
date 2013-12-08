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
 * @author Evan Dooner
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
		int approach = mCursor.getInt(approachIndex);

		int doorsIndex = mCursor.getColumnIndex(RatingField.DOORS.fieldName);
		int doors = mCursor.getInt(doorsIndex);

		int flooringIndex = mCursor
				.getColumnIndex(RatingField.FLOORING.fieldName);
		int flooring = mCursor.getInt(flooringIndex);

		int stepsIndex = mCursor.getColumnIndex(RatingField.STEPS.fieldName);
		int steps = mCursor.getInt(stepsIndex);

		int liftsIndex = mCursor.getColumnIndex(RatingField.LIFTS.fieldName);
		int lifts = mCursor.getInt(liftsIndex);

		int bathroomsIndex = mCursor
				.getColumnIndex(RatingField.BATHROOMS.fieldName);
		int bathrooms = mCursor.getInt(bathroomsIndex);

		int layoutIndex = mCursor.getColumnIndex(RatingField.LAYOUT.fieldName);
		int layout = mCursor.getInt(layoutIndex);

		int staffIndex = mCursor.getColumnIndex(RatingField.STAFF.fieldName);
		int staff = mCursor.getInt(staffIndex);

		int parkingIndex = mCursor
				.getColumnIndex(RatingField.PARKING.fieldName);
		int parking = mCursor.getInt(parkingIndex);

		int subRatingIndex = mCursor
				.getColumnIndex(RatingField.SUB_TOTAL.fieldName);
		int subRating = mCursor.getInt(subRatingIndex);

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
	private int approach;
	private int doors;
	private int flooring;
	private int steps;
	private int lifts;
	private int bathrooms;
	private int layout;
	private int staff;
	private int parking;
	private int subTotal;

	// Either specify all fields in constructor, or allow them to be set after
	// construction
	// using setters. Could also look into using a Builder pattern, but prob.
	// overkill
	public Rating(int venueid) {
		this.venueId = venueid;
	}

	@Override
	// Removes this rating model from the database
	public void delete(SQLiteDatabase dbConnect) {
		dbConnect.delete(RATING_TABLE, VenueField.ROWID.fieldName + "="
				+ this.rowid, null);
	}

	public int getApproach() {
		return approach;
	}

	public int getBathrooms() {
		return bathrooms;
	}

	public int getDoors() {
		return doors;
	}

	public int getFlooring() {
		return flooring;
	}

	public int getLayout() {
		return layout;
	}

	public int getLifts() {
		return lifts;
	}

	public int getParking() {
		return parking;
	}

	public int getRowid() {
		return rowid;
	}

	public int getStaff() {
		return staff;
	}

	public int getSteps() {
		return steps;
	}

	public int getSubTotal() {
		return subTotal;
	}

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

	public void setApproach(int approach) {
		this.approach = approach;
	}

	public void setBathrooms(int bathrooms) {
		this.bathrooms = bathrooms;
	}

	public void setDoors(int doors) {
		this.doors = doors;
	}

	public void setFlooring(int flooring) {
		this.flooring = flooring;
	}

	public void setLayout(int layout) {
		this.layout = layout;
	}

	public void setLifts(int lifts) {
		this.lifts = lifts;
	}

	public void setParking(int parking) {
		this.parking = parking;
	}

	public void setStaff(int staff) {
		this.staff = staff;
	}

	public void setSteps(int steps) {
		this.steps = steps;
	}

	public void setSubTotal(int subrating) {
		this.subTotal = subrating;
	}

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
