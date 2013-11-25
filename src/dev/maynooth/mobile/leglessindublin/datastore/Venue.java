package dev.maynooth.mobile.leglessindublin.datastore;

import java.util.List;

/**
 * Represents a database model of a venue.
 * 
 * @author Evan Dooner
 * @version 2013-11-25-00
 */
public class Venue implements Model {
	
	/**
	 * Represents the fields contained in the venue database table.
	 * Used in generating queries for the finders.
	 */
	public enum VenueField {
		ROWID("_id"),
		NAME("name"),
		ADDRESS_1("address_line_1"),
		ADDRESS_2("address_line2"),
		ADDRESS_3("address_line_3"),
		CATEGORY("category"),
		TOT_RATING("tot_rating");
		
		public String fieldName;
		
		private VenueField(String fieldName) {
			this.fieldName = fieldName;
		}
	}
	
	/**
	 * Lists all venues in the database
	 * 
	 * @return all the venues
	 */
	public static List<Venue> listAll() {
		return null;
	}
	
	/**
	 * Returns a page of venues.
	 * <p>
	 * The page number and the number of venues per page can be specified, 
	 * along with the column on which the list if ordered.
	 * 
	 * @param pageNumber an int - the number of the page to retrieve
	 * @param entriesPerPage an int - the number of venue entries per page
	 * @param orderBy a venueField - the field on which to order the list
	 * @return a page of venues, in the specified order
	 */
	public static List<Venue> page(int pageNumber, int entriesPerPage, VenueField orderBy) {
		return null;
	}
	
	/**
	 * Finds the venue with the specified id
	 * 
	 * @param id an int - the id of the venue to be retrieved
	 * @return a venue with the specified id
	 */
	public static Venue findById(int id) {
		return null;
	}
	
	// Either specify all fields in constructor, or allow them to be set after construction
	// using setters. Could also look into using a Builder pattern, but prob. overkill
	public Venue() {
		
	}

	@Override // See Model.java for comments
	public void delete(/* Might need to pass in a DatabaseHelper here */) {
		// TODO Auto-generated method stub

	}

	@Override // See Model.java for comments
	public void save(/* Might need to pass in a DatabaseHelper here */) {
		// TODO Auto-generated method stub

	}

	@Override // See Model.java for comments
	public void update(/* Might need to pass in a DatabaseHelper here */) {
		// TODO Auto-generated method stub

	}

}
