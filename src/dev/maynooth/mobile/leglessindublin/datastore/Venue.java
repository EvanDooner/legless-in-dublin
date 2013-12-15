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

	/**
	 * Finds the venue with the specified id
	 * 
	 * @param id
	 *            an int - the id of the venue to be retrieved
	 * @return a venue with the specified id
	 */
	public static Venue findById(int rowId, SQLiteDatabase dbConnect) {

		Cursor mCursor =

		dbConnect.query(true, VENUE_TABLE, null, WHERE_ID_EQUALS,
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

		Cursor mCursor = dbConnect.query(false, VENUE_TABLE, null,
				WHERE_LOCATION_AND_TYPE_EQUALS, selectionArgs, null, null,
				VenueField.NAME.fieldName, null);
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

		dbConnect.query(VENUE_TABLE, null, null, null, null, null,
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

		dbConnect.query(VENUE_TABLE, null, null, null, null, null,
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
		dbConnect.delete(VENUE_TABLE, WHERE_ID_EQUALS, new String[] {"" + rowId});
	}

	/**
	 * @return the approach
	 */
	public double getApproach() {
		return approach;
	}

	/**
	 * @return the bathrooms
	 */
	public double getBathrooms() {
		return bathrooms;
	}

	/**
	 * @return the doors
	 */
	public double getDoors() {
		return doors;
	}

	/**
	 * @return the flooring
	 */
	public double getFlooring() {
		return flooring;
	}

	/**
	 * @return the layout
	 */
	public double getLayout() {
		return layout;
	}

	/**
	 * @return the lifts
	 */
	public double getLifts() {
		return lifts;
	}

	public int getLocationId() {
		return locationId;
	}

	public String getName() {
		return name;
	}

	/**
	 * @return the numRatings
	 */
	public int getNumRatings() {
		return numRatings;
	}

	/**
	 * @return the parking
	 */
	public double getParking() {
		return parking;
	}

	public int getRowId() {
		return rowId;
	}

	/**
	 * @return the staff
	 */
	public double getStaff() {
		return staff;
	}

	/**
	 * @return the steps
	 */
	public double getSteps() {
		return steps;
	}

	public String getStreetName() {
		return streetName;
	}

	public double getTotalRating() {
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
		initialValues.put(VenueField.NUMBER_OF_RATINGS.fieldName, this.numRatings);

		dbConnect.insert(VENUE_TABLE, null, initialValues);
	}

	/**
	 * @param approach
	 *            the approach to set
	 */
	public void setApproach(double approach) {
		this.approach = approach;
	}

	/**
	 * @param bathrooms
	 *            the bathrooms to set
	 */
	public void setBathrooms(double bathrooms) {
		this.bathrooms = bathrooms;
	}

	/**
	 * @param doors
	 *            the doors to set
	 */
	public void setDoors(double doors) {
		this.doors = doors;
	}

	/**
	 * @param flooring
	 *            the flooring to set
	 */
	public void setFlooring(double flooring) {
		this.flooring = flooring;
	}

	/**
	 * @param layout
	 *            the layout to set
	 */
	public void setLayout(double layout) {
		this.layout = layout;
	}

	/**
	 * @param lifts
	 *            the lifts to set
	 */
	public void setLifts(double lifts) {
		this.lifts = lifts;
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

	/**
	 * @param numRatings
	 *            the numRatings to set
	 */
	public void setNumRatings(int numRatings) {
		this.numRatings = numRatings;
	}

	/**
	 * @param parking
	 *            the parking to set
	 */
	public void setParking(double parking) {
		this.parking = parking;
	}

	/**
	 * @param staff
	 *            the staff to set
	 */
	public void setStaff(double staff) {
		this.staff = staff;
	}

	/**
	 * @param steps
	 *            the steps to set
	 */
	public void setSteps(double steps) {
		this.steps = steps;
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
		updatedValues.put(VenueField.NUMBER_OF_RATINGS.fieldName, this.numRatings);

		dbConnect.update(VENUE_TABLE, updatedValues, WHERE_ID_EQUALS, new String[] {"" + this.rowId});
	}

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
			
			double numRatingsAsDouble = (double)numberOfRatings;
			
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
