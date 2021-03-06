package dev.maynooth.mobile.leglessindublin.datastore;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Represents a database model of a venue.
 * 
 * @author Evan Dooner, 12262480
 * @author Dennis Muldoon, 12260550
 * 
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
		APPROACH("approach"),
		DOORS("doors"),
		FLOORING("flooring"),
		STEPS("steps"),
		LIFTS("lifts"),
		BATHROOMS("bathrooms"),
		LAYOUT("layout"),
		STAFF("staff"),
		PARKING("parking"),
		TOTAL_RATING("total_rating"),
		NUMBER_OF_RATINGS("num_ratings");
		//@formatter:on

		public String fieldName;

		private VenueField(String fieldName) {
			this.fieldName = fieldName;
		}
	}

	private static final String[] ALL_COLUMNS;

	// Initialise ALL_COLUMNS to contain the field names of all columns
	// No need to change ALL_COLUMNS if enum changed
	static {
		int numEnums = VenueField.values().length;
		String[] workingArray = new String[numEnums];
		for (int i = 0; i < numEnums; i++) {
			workingArray[i] = VenueField.values()[i].fieldName;
		}
		ALL_COLUMNS = workingArray;
	}

	private static final String WHERE_LOCATION_AND_TYPE_EQUALS = VenueField.LOCATION.fieldName
			+ "=? " + "AND " + VenueField.VENUE_TYPE.fieldName + "=?";

	private static final String VENUE_TABLE = "venue";

	private static final String WHERE_ID_EQUALS = VenueField.ROWID.fieldName
			+ "=?";

	private static final String[] COUNT = { "COUNT(*)" };

	/**
	 * Finds the venue with the specified id
	 * 
	 * @param id
	 *            an int - the id of the venue to be retrieved
	 * @return a venue with the specified id
	 */
	public static Venue findById(int rowId, SQLiteDatabase dbConnect) {

		Cursor mCursor =

		dbConnect.query(true, VENUE_TABLE, ALL_COLUMNS, WHERE_ID_EQUALS,
				new String[] { "" + rowId }, null, null,
				VenueField.NAME.fieldName, null);
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

		if (selectionArgs.length != 2) {
			throw new IllegalArgumentException(
					"Incorrect number of search arguments");
		}

		Cursor mCursor = dbConnect.query(false, VENUE_TABLE, ALL_COLUMNS,
				WHERE_LOCATION_AND_TYPE_EQUALS, selectionArgs, null, null,
				VenueField.TOTAL_RATING.fieldName + " DESC", null);
		if (mCursor == null || mCursor.getCount() < 1) {
			return null;
		}

		mCursor.moveToFirst();

		List<Venue> results = new ArrayList<Venue>();
		while (!mCursor.isAfterLast()) {
			Venue result = buildVenue(mCursor);
			results.add(result);
			mCursor.moveToNext();
		}

		return results;
	}

	/**
	 * Returns the number of venues that have the specified location and venue type.
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
	 * @return count
	 * 				- an int - the number of venues that have the specified location and venue type
	 */
	public static int findCountByLocationAndVenueType(SQLiteDatabase dbConnect,
			String[] selectionArgs) {

		if (selectionArgs.length != 2) {
			throw new IllegalArgumentException(
					"Incorrect number of search arguments");
		}

		int count = 0;

		Cursor mCursor = dbConnect.query(false, VENUE_TABLE, COUNT,
				WHERE_LOCATION_AND_TYPE_EQUALS, selectionArgs, null, null,
				null, null);
		if (mCursor != null && mCursor.getCount() == 1) {
			mCursor.moveToFirst();
			count = mCursor.getInt(0);
		}

		return count;

	}

	/**
	 * Returns the name of the Venue table in the database.
	 * @return VENUE_TABLE
	 * 					- a String - the name of the Venue table in the database.
	 */
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

		dbConnect.query(VENUE_TABLE, ALL_COLUMNS, null, null, null, null, null);
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
	 * with the column on which the list is ordered.
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

		dbConnect.query(VENUE_TABLE, ALL_COLUMNS, null, null, null, null,
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

		int approachIndex = mCursor
				.getColumnIndex(VenueField.APPROACH.fieldName);
		double approach = mCursor.getDouble(approachIndex);

		int doorsIndex = mCursor.getColumnIndex(VenueField.DOORS.fieldName);
		double doors = mCursor.getDouble(doorsIndex);

		int flooringIndex = mCursor
				.getColumnIndex(VenueField.FLOORING.fieldName);
		double flooring = mCursor.getDouble(flooringIndex);

		int stepsIndex = mCursor.getColumnIndex(VenueField.STEPS.fieldName);
		double steps = mCursor.getDouble(stepsIndex);

		int liftsIndex = mCursor.getColumnIndex(VenueField.LIFTS.fieldName);
		double lifts = mCursor.getDouble(liftsIndex);

		int bathroomsIndex = mCursor
				.getColumnIndex(VenueField.BATHROOMS.fieldName);
		double bathrooms = mCursor.getDouble(bathroomsIndex);

		int layoutIndex = mCursor.getColumnIndex(VenueField.LAYOUT.fieldName);
		double layout = mCursor.getDouble(layoutIndex);

		int staffIndex = mCursor.getColumnIndex(VenueField.STAFF.fieldName);
		double staff = mCursor.getDouble(staffIndex);

		int parkingIndex = mCursor.getColumnIndex(VenueField.PARKING.fieldName);
		double parking = mCursor.getDouble(parkingIndex);

		int totalRatingIndex = mCursor
				.getColumnIndex(VenueField.TOTAL_RATING.fieldName);
		double totalRating = mCursor.getDouble(totalRatingIndex);

		int numRatingsIndex = mCursor
				.getColumnIndex(VenueField.NUMBER_OF_RATINGS.fieldName);
		int numRatings = mCursor.getInt(numRatingsIndex);

		Venue result = new Venue(rowId, name, location, venueType);
		result.streetName = streetName;
		result.approach = approach;
		result.doors = doors;
		result.flooring = flooring;
		result.steps = steps;
		result.lifts = lifts;
		result.bathrooms = bathrooms;
		result.layout = layout;
		result.staff = staff;
		result.parking = parking;
		result.totalRating = totalRating;
		result.numRatings = numRatings;
		return result;
	}

	private int rowId;

	private String name;

	private String streetName;

	private int locationId;

	private int venueTypeId;

	private double approach;

	private double doors;

	private double flooring;

	private double steps;

	private double lifts;

	private double bathrooms;

	private double layout;

	private double staff;

	private double parking;

	private double totalRating;

	private int numRatings;

	/**
	 * Constructs a venue object.
	 * 
	 * @param name
	 * 			a String - the name of the venue.
	 * @param location
	 * 			an int - the location id of the venue.
	 * @param venueType
	 * 			an int - the venue type id of the venue.
	 */
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
		dbConnect.delete(VENUE_TABLE, WHERE_ID_EQUALS, new String[] { ""
				+ rowId });
	}

	/**
	 * Returns the Approach rating of a venue.
	 * 
	 * @return approach
	 * 				- a double - the Approach rating of a venue.
	 */
	public double getApproach() {
		return approach;
	}

	/**
	 * Returns the Bathrooms rating of a venue.
	 * 
	 * @return bathrooms
	 * 				- a double - the Bathrooms rating of a venue.
	 */
	public double getBathrooms() {
		return bathrooms;
	}

	/**
	 * Returns the Doors rating of a venue.
	 * 
	 * @return doors
	 * 				- a double - the Doors rating of a venue.
	 */
	public double getDoors() {
		return doors;
	}

	/**
	 * Returns the Flooring rating of a venue.
	 * 
	 * @return flooring
	 * 				- a double - the Flooring rating of a venue.
	 */
	public double getFlooring() {
		return flooring;
	}

	/**
	 * Returns the Layout rating of a venue.
	 * 
	 * @return layout
	 * 				- a double - the Layout rating of a venue.
	 */
	public double getLayout() {
		return layout;
	}

	/**
	 * Returns the Lifts rating of a venue.
	 * 
	 * @return lifts
	 * 				- a double - the Lifts rating of a venue.
	 */
	public double getLifts() {
		return lifts;
	}

	/**
	 * Returns the Location id of a venue.
	 * 
	 * @return locationId
	 * 				- an int - the Location id of a venue.
	 */
	public int getLocationId() {
		return locationId;
	}

	/**
	 * Returns the name of a venue.
	 * 
	 * @return name
	 * 				- a String - the name of a venue.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the number of ratings of a venue.
	 * 
	 * @return numRatings
	 * 				- an int - the number of ratings of a venue.
	 */
	public int getNumRatings() {
		return numRatings;
	}

	/**
	 * Returns the Parking rating of a venue.
	 * 
	 * @return parking
	 * 				- a double - the Parking rating of a venue.
	 */
	public double getParking() {
		return parking;
	}

	/**
	 * Returns the database row id of a venue.
	 * 
	 * @return rowId
	 * 				- an int - the database row id of a venue.
	 */
	public int getRowId() {
		return rowId;
	}

	/**
	 * Returns the Staff rating of a venue.
	 * 
	 * @return staff
	 * 				- a double - the Staff rating of a venue.
	 */
	public double getStaff() {
		return staff;
	}

	/**
	 * Returns the Steps rating of a venue.
	 * 
	 * @return steps
	 * 				- a double - the Steps rating of a venue.
	 */
	public double getSteps() {
		return steps;
	}

	/**
	 * Returns the Street Name of a venue.
	 * 
	 * @return streetName
	 * 				- a String - the Street Name of a venue.
	 */
	public String getStreetName() {
		return streetName;
	}

	/**
	 * Returns the total rating of a venue.
	 * 
	 * @return totalRating
	 * 				- a double - the total rating of a venue.
	 */
	public double getTotalRating() {
		return totalRating;
	}

	/**
	 * Returns the venue type id of a venue.
	 * 
	 * @return venueTypeId
	 * 				- an int - the venue type id of a venue.
	 */
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
		initialValues.put(VenueField.APPROACH.fieldName, this.approach);
		initialValues.put(VenueField.DOORS.fieldName, this.doors);
		initialValues.put(VenueField.FLOORING.fieldName, this.flooring);
		initialValues.put(VenueField.STEPS.fieldName, this.steps);
		initialValues.put(VenueField.LIFTS.fieldName, this.lifts);
		initialValues.put(VenueField.BATHROOMS.fieldName, this.bathrooms);
		initialValues.put(VenueField.LAYOUT.fieldName, this.layout);
		initialValues.put(VenueField.STAFF.fieldName, this.staff);
		initialValues.put(VenueField.PARKING.fieldName, this.parking);
		initialValues.put(VenueField.TOTAL_RATING.fieldName, this.totalRating);
		initialValues.put(VenueField.NUMBER_OF_RATINGS.fieldName,
				this.numRatings);

		dbConnect.insert(VENUE_TABLE, null, initialValues);
	}

	/**
	 * Sets the Approach rating for a venue.
	 * 
	 * @param approach
	 *            - a double - the Approach rating for a venue.
	 */
	public void setApproach(double approach) {
		this.approach = approach;
	}

	/**
	 * Sets the Bathrooms rating for a venue.
	 * 
	 * @param bathrooms
	 *            - a double - the Bathrooms rating for a venue.
	 */
	public void setBathrooms(double bathrooms) {
		this.bathrooms = bathrooms;
	}

	/**
	 * Sets the Doors rating for a venue.
	 * 
	 * @param doors
	 *            - a double - the Doors rating for a venue.
	 */
	public void setDoors(double doors) {
		this.doors = doors;
	}

	/**
	 * Sets the Flooring rating for a venue.
	 * 
	 * @param flooring
	 *            - a double - the Flooring rating for a venue.
	 */
	public void setFlooring(double flooring) {
		this.flooring = flooring;
	}

	/**
	 * Sets the Layout rating for a venue.
	 * 
	 * @param layout
	 *            - a double - the Layout rating for a venue.
	 */
	public void setLayout(double layout) {
		this.layout = layout;
	}

	/**
	 * Sets the Lifts rating for a venue.
	 * 
	 * @param lifts
	 *            - a double - the Lifts rating for a venue.
	 */
	public void setLifts(double lifts) {
		this.lifts = lifts;
	}

	/**
	 * Sets the location for a venue.
	 * 
	 * @param location
	 *            - a location - the Location for a venue.
	 */
	public void setLocation(Location location) {
		this.locationId = location.getRowId();
	}

	/**
	 * Sets the location id for a venue.
	 * 
	 * @param location
	 *            - a Location - the location for a venue.
	 */
	public void setLocationId(int location) {
		this.locationId = location;
	}

	/**
	 * Sets the name for a venue.
	 * 
	 * @param name
	 *            - a String - the name for a venue.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Sets the number of ratings for a venue.
	 * 
	 * @param numRatings
	 *            - an int - the number of ratings for a venue.
	 */
	public void setNumRatings(int numRatings) {
		this.numRatings = numRatings;
	}

	/**
	 * Sets the Parking rating for a venue.
	 * 
	 * @param parking
	 *            - a double - the Parking rating for a venue.
	 */
	public void setParking(double parking) {
		this.parking = parking;
	}

	/**
	 * Sets the Staff rating for a venue.
	 * 
	 * @param staff
	 *            - a double - the Staff rating for a venue.
	 */
	public void setStaff(double staff) {
		this.staff = staff;
	}

	/**
	 * Sets the Steps rating for a venue.
	 * 
	 * @param steps
	 *            - a double - the Steps rating for a venue.
	 */
	public void setSteps(double steps) {
		this.steps = steps;
	}

	/**
	 * Sets the street name for a venue.
	 * 
	 * @param addressLine1
	 *            - a String - the street name for a venue.
	 */
	public void setStreetName(String addressLine1) {
		this.streetName = addressLine1;
	}

	/**
	 * Sets the venue type for a venue.
	 * 
	 * @param venueType
	 *            - a VenueType - the venue type for a venue.
	 */
	public void setVenueType(VenueType venueType) {
		this.venueTypeId = venueType.getRowId();
	}

	/**
	 * Sets the venue type id for a venue.
	 * 
	 * @param venueType
	 *            - an int - the venue type id for a venue.
	 */
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

		ContentValues updatedValues = new ContentValues();
		updatedValues.put(VenueField.NAME.fieldName, this.name);
		updatedValues.put(VenueField.STREET_NAME.fieldName, this.streetName);
		updatedValues.put(VenueField.LOCATION.fieldName, this.locationId);
		updatedValues.put(VenueField.VENUE_TYPE.fieldName, this.venueTypeId);
		updatedValues.put(VenueField.APPROACH.fieldName, this.approach);
		updatedValues.put(VenueField.DOORS.fieldName, this.doors);
		updatedValues.put(VenueField.FLOORING.fieldName, this.flooring);
		updatedValues.put(VenueField.STEPS.fieldName, this.steps);
		updatedValues.put(VenueField.LIFTS.fieldName, this.lifts);
		updatedValues.put(VenueField.BATHROOMS.fieldName, this.bathrooms);
		updatedValues.put(VenueField.LAYOUT.fieldName, this.layout);
		updatedValues.put(VenueField.STAFF.fieldName, this.staff);
		updatedValues.put(VenueField.PARKING.fieldName, this.parking);
		updatedValues.put(VenueField.TOTAL_RATING.fieldName, this.totalRating);
		updatedValues.put(VenueField.NUMBER_OF_RATINGS.fieldName,
				this.numRatings);

		dbConnect.update(VENUE_TABLE, updatedValues, WHERE_ID_EQUALS,
				new String[] { "" + this.rowId });
	}

	/**
	 * Updates the average ratings for a venue.
	 * 
	 * @param dbConnect
	 * 				- an SQLiteDatabase - the database that includes the rating table
	 */
	public void updateAverageRatings(SQLiteDatabase dbConnect) {
		List<Rating> ratings = Rating.findByVenueId(this.rowId, dbConnect);
		int numberOfRatings = ratings.size();
		if (numberOfRatings == 0) {
			approach = 0;
			doors = 0;
			flooring = 0;
			steps = 0;
			lifts = 0;
			bathrooms = 0;
			layout = 0;
			staff = 0;
			parking = 0;
			totalRating = 0;
			numRatings = 0;
		} else {
			int sumApproach = 0;
			int sumDoors = 0;
			int sumFlooring = 0;
			int sumSteps = 0;
			int sumLifts = 0;
			int sumBathrooms = 0;
			int sumLayout = 0;
			int sumStaff = 0;
			int sumParking = 0;
			int sumTotalRatings = 0;
			for (Rating rating : ratings) {
				sumApproach += rating.getApproach();
				sumDoors += rating.getDoors();
				sumFlooring += rating.getFlooring();
				sumSteps += rating.getSteps();
				sumLifts += rating.getLifts();
				sumBathrooms += rating.getBathrooms();
				sumLayout += rating.getLayout();
				sumStaff += rating.getStaff();
				sumParking += rating.getParking();
				sumTotalRatings += rating.getSubTotal();
			}

			double numRatingsAsDouble = (double) numberOfRatings;

			approach = sumApproach / numRatingsAsDouble;
			doors = sumDoors / numRatingsAsDouble;
			flooring = sumFlooring / numRatingsAsDouble;
			steps = sumSteps / numRatingsAsDouble;
			lifts = sumLifts / numRatingsAsDouble;
			bathrooms = sumBathrooms / numRatingsAsDouble;
			layout = sumLayout / numRatingsAsDouble;
			staff = sumStaff / numRatingsAsDouble;
			parking = sumParking / numRatingsAsDouble;
			totalRating = sumTotalRatings / numRatingsAsDouble;
			numRatings = numberOfRatings;
		}
	}

}
