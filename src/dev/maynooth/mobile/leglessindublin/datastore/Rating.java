package dev.maynooth.mobile.leglessindublin.datastore;

import java.util.List;

import dev.maynooth.mobile.leglessindublin.datastore.Venue.VenueField;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

/**
 * Represents a database model of a venue rating.
 * 
 * @author Evan Dooner
 * @version 2013-11-25-00
 */
public class Rating implements Model {
	
	/**
	 * Represents the fields contained in the ratings database table.
	 * Used in generating queries for the finders.
	 */
	public enum RatingField {
		ROWID("_id"), 
		VENUE_ID("venue_id"), 
		APPROACH("approach"),
		DOORS("doors"),
		FLOORING("flooring"),
		STEPS("steps"),
		LIFTS("lifts"),
		BATHROOMS("bathrooms"),
		LAYOUT("layout"),
		STAFF("staff"),
		PARKING("parking"),
		SUB_RATING("sub_rating");
		
		public String fieldName;
		
		private RatingField(String fieldName) {
			this.fieldName = fieldName;
		}
	}
	
	private int rowid;
	private int venueid;
	private int approach;
	private int doors;
	private int flooring;
	private int steps;
	private int lifts;
	private int bathrooms;
	private int layout;
	private int staff;
	private int parking;
	private int subrating;
	
	/**
	 * Fetches all stored ratings
	 * 
	 * @return all ratings
	 */
	public static List<Rating> listAll() {
		
		return null;
	}
	
	/**
	 * Returns all rating objects that match the search query, 
	 * i.e. their value for the specified field is the same as the specified value.
	 * 
	 * @param field a ratingField - the field to query
	 * @param value an int - the value of the field
	 * @return all the ratings that match the specified requirements
	 */
	public static List<Rating> findEquals(RatingField field, int value) {
		return null;
	}
	
	/**
	 * Returns all the ratings associated with a particular venue
	 * 
	 * @param venueId an int - the id of the associated venue
	 * @return all the ratings associated with the specified venue
	 */
	public static List<Rating> findByVenueId(int venueId) {
		return null;
	}
	
	/**
	 * Returns a rating by id.
	 * 
	 * @param ratingId an int - the id of the rating to be retrieved
	 * @return a rating - the rating with the specified id
	 */
	public static Rating findById(int ratingId) {
		return null;
	}
	
	// Either specify all fields in constructor, or allow them to be set after construction
	// using setters. Could also look into using a Builder pattern, but prob. overkill
	public Rating(int venueid) {
		this.venueid = venueid;
	}

	@Override // Removes this rating model from the database
	public void delete(SQLiteDatabase dbConnect) {
		dbConnect.delete(LeglessDbAdapter.DATABASE_RATING_TABLE, VenueField.ROWID.fieldName + "=" + this.rowid, null);
	}

	@Override // Persists this rating model to the database
	public void save(SQLiteDatabase dbConnect) {
		
		ContentValues initialValues = new ContentValues();
		initialValues.put(RatingField.VENUE_ID.fieldName, this.venueid);
		initialValues.put(RatingField.APPROACH.fieldName, this.approach);
		initialValues.put(RatingField.DOORS.fieldName, this.doors);
		initialValues.put(RatingField.FLOORING.fieldName, this.flooring);
		initialValues.put(RatingField.STEPS.fieldName, this.steps);
		initialValues.put(RatingField.LIFTS.fieldName, this.lifts);
		initialValues.put(RatingField.BATHROOMS.fieldName, this.bathrooms);
		initialValues.put(RatingField.LAYOUT.fieldName, this.layout);
		initialValues.put(RatingField.STAFF.fieldName, this.staff);
		initialValues.put(RatingField.PARKING.fieldName, this.parking);
		initialValues.put(RatingField.SUB_RATING.fieldName, this.subrating);		
		
		dbConnect.insert(LeglessDbAdapter.DATABASE_RATING_TABLE, null, initialValues);
	}
		

	@Override // Updates this rating model's record in the database
	public void update(SQLiteDatabase dbConnect) {
		
		ContentValues args = new ContentValues();
		args.put(RatingField.VENUE_ID.fieldName, this.venueid);
		args.put(RatingField.APPROACH.fieldName, this.approach);
		args.put(RatingField.DOORS.fieldName, this.doors);
		args.put(RatingField.FLOORING.fieldName, this.flooring);
        args.put(RatingField.STEPS.fieldName, this.steps);
        args.put(RatingField.LIFTS.fieldName, this.lifts);
        args.put(RatingField.BATHROOMS.fieldName, this.bathrooms);
        args.put(RatingField.LAYOUT.fieldName, this.layout);
        args.put(RatingField.STAFF.fieldName, this.staff);
        args.put(RatingField.PARKING.fieldName, this.parking);
        args.put(RatingField.SUB_RATING.fieldName, this.subrating);		
		
		dbConnect.update(LeglessDbAdapter.DATABASE_RATING_TABLE, args, VenueField.ROWID.fieldName + "=" + this.rowid, null);
	}

	public int getRowid() {
		return rowid;
	}

	public int getVenueid() {
		return venueid;
	}

	public int getApproach() {
		return approach;
	}

	public int getDoors() {
		return doors;
	}

	public int getFlooring() {
		return flooring;
	}

	public int getSteps() {
		return steps;
	}

	public int getLifts() {
		return lifts;
	}

	public int getBathrooms() {
		return bathrooms;
	}

	public int getLayout() {
		return layout;
	}

	public int getStaff() {
		return staff;
	}

	public int getParking() {
		return parking;
	}

	public int getSubrating() {
		return subrating;
	}

	public void setVenueid(int venueid) {
		this.venueid = venueid;
	}

	public void setApproach(int approach) {
		this.approach = approach;
	}

	public void setDoors(int doors) {
		this.doors = doors;
	}

	public void setFlooring(int flooring) {
		this.flooring = flooring;
	}

	public void setSteps(int steps) {
		this.steps = steps;
	}

	public void setLifts(int lifts) {
		this.lifts = lifts;
	}

	public void setBathrooms(int bathrooms) {
		this.bathrooms = bathrooms;
	}

	public void setLayout(int layout) {
		this.layout = layout;
	}

	public void setStaff(int staff) {
		this.staff = staff;
	}

	public void setParking(int parking) {
		this.parking = parking;
	}

	public void setSubrating(int subrating) {
		this.subrating = subrating;
	}

}
