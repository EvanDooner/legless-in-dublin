package dev.maynooth.mobile.leglessindublin.datastore;

import java.util.List;

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
	public Rating() {
		
	}

	@Override // See Model.java for comments
	public void delete(SQLiteDatabase dbConnect) {
		// TODO Auto-generated method stub

	}

	@Override // See Model.java for comments
	public void save(SQLiteDatabase dbConnect) {
		// TODO Auto-generated method stub

	}

	@Override // See Model.java for comments
	public void update(SQLiteDatabase dbConnect) {
		// TODO Auto-generated method stub

	}

}
