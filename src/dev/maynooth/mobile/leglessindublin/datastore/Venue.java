package dev.maynooth.mobile.leglessindublin.datastore;

import java.util.List;

import android.database.sqlite.SQLiteDatabase;

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
	public Venue(String name, String addressLine1, String category) {
		this.name = name;
		this.addressLine1 = addressLine1;
		this.category = category;
	}
	
	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	@Override // See Model.java for comments
	public void delete(SQLiteDatabase dbConnect) {

		//Delete any associated ratings before deleting a venue?
        dbConnect.delete(LeglessDbAdapter.DATABASE_VENUE_TABLE, VenueField.ROWID.fieldName + "=" + this.rowid, null);

	}

	@Override // See Model.java for comments
	public void save(SQLiteDatabase dbConnect) {
		// TODO Auto-generated method stub

	}

	@Override // See Model.java for comments
	public void update(SQLiteDatabase dbConnect) {
		// TODO Auto-generated method stub

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
