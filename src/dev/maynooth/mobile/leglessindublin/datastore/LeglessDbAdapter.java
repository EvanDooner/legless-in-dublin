
package dev.maynooth.mobile.leglessindublin.datastore;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Legless in Dublin database access helper class. Defines the basic CRUD operations
 * for the Legless in Dublin app, and gives the ability to list all venues and ratings 
 * as well as retrieve or modify a specific entry.
 */
public class LeglessDbAdapter {
    
    public static final String V_ROWID = "_id";
    public static final String V_NAME = "name";
    public static final String V_ADDRESS_LINE1 = "address_line1";
    public static final String V_ADDRESS_LINE2 = "address_line2";
    public static final String V_ADDRESS_LINE3 = "address_line3";
    public static final String V_CATEGORY = "category";
    public static final String V_TOT_RATING = "tot_rating";
    public static final String R_ROWID = "_id";
    public static final String R_VENUE_ID = "venue_id";
    public static final String R_APPROACH = "approach";
    public static final String R_DOORS = "doors";
    public static final String R_FLOORING = "flooring";
    public static final String R_STEPS = "steps";
    public static final String R_LIFTS = "lifts";
    public static final String R_BATHROOMS = "bathrooms";
    public static final String R_LAYOUT = "layout";
    public static final String R_STAFF = "staff";
    public static final String R_PARKING = "parking";
    public static final String R_SUB_RATING = "sub_rating";

    private static final String TAG = "LeglessDbAdapter";
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;
    

     
    //Venue table creation sql statement  
    private static final String VENUE_TABLE_CREATE =
    		"CREATE TABLE venue ( " +
    		"_id SERIAL, " +
    		"name VARCHAR(30) NOT NULL, " +
    		"address_line1 VARCHAR(30), " +
    		"address_line2 VARCHAR(30), " +
    		"address_line3 VARCHAR(30), " +
    		"category VARCHAR(30), " +
    		"tot_rating INT, " +
    		"CONSTRAINT venue_pk PRIMARY KEY(_id));";
    
    //Rating table creation sql statement
    private static final String RATING_TABLE_CREATE = 
    		"CREATE TABLE rating ( " +
    		"_id SERIAL, " +
    		"venue_id INT, " +
    		"approach INT NOT NULL, " +
    		"doors INT NOT NULL, " +
    		"flooring INT NOT NULL, " +
    		"steps INT NOT NULL, " +
    		"lifts INT NOT NULL, " +
    		"bathrooms INT NOT NULL, " +
    		"layout INT NOT NULL, " +
    		"staff INT NOT NULL, " +
    		"parking INT NOT NULL, " +
    		"sub_rating INT, " +
    		"CONSTRAINT rating_pk PRIMARY KEY(_id), " +
    		"CONSTRAINT rating_fk FOREIGN KEY(venue_id) REFERENCES venue(_id));";
    
    private static final String DATABASE_NAME = "data";
    private static final String DATABASE_VENUE_TABLE = "venue";
    private static final String DATABASE_RATING_TABLE = "rating";
    private static final int DATABASE_VERSION = 2;

    private final Context mCtx;
    
    //Local implementation of the SQLiteOpenHelper class
    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            db.execSQL(VENUE_TABLE_CREATE);
            db.execSQL(RATING_TABLE_CREATE);
        }
        
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS venue");
            db.execSQL("DROP TABLE IF EXISTS rating");
            onCreate(db);
        }
    }

    /**
     * Constructor - takes the context to allow the database to be
     * opened/created
     * 
     * @param ctx the Context within which to work
     */
    public LeglessDbAdapter(Context ctx) {
        this.mCtx = ctx;
    }

    /**
     * Open the database. If it cannot be opened, try to create a new
     * instance of the database. If it cannot be created, throw an exception to
     * signal the failure
     * 
     * @return this (self reference, allowing this to be chained in an
     *         initialization call)
     * @throws SQLException if the database could be neither opened or created
     */
    public LeglessDbAdapter open() throws SQLException {
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        mDbHelper.close();
    }


    /**
     * Create a new venue using the fields provided. If the venue is
     * successfully created return the new rowId for that venue, otherwise return
     * a -1 to indicate failure.
     * 
     * @param fields of the venue
     * @return rowId or -1 if failed
     */
    public long createVenue(String name, String line1, String line2, String line3, 
    		String category, int tot_rating) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(V_NAME, name);
        initialValues.put(V_ADDRESS_LINE1, line1);
        initialValues.put(V_ADDRESS_LINE2, line2);
        initialValues.put(V_ADDRESS_LINE3, line3);
        initialValues.put(V_CATEGORY, category);
        initialValues.put(V_TOT_RATING, tot_rating);

        return mDb.insert(DATABASE_VENUE_TABLE, null, initialValues);
    }
    
    /**
     * Create a new rating using the fields provided. If the rating is
     * successfully created return the new rowId for that rating, otherwise return
     * a -1 to indicate failure.
     * 
     * @param fields of the rating
     * @return rowId or -1 if failed
     */
    public long createRating(int venue_id, int approach, int doors, 
    		int flooring, int steps, int lifts, int bathrooms, int layout, 
    		int staff, int parking, int sub_rating) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(R_VENUE_ID, venue_id);
        initialValues.put(R_APPROACH, approach);
        initialValues.put(R_DOORS, doors);
        initialValues.put(R_FLOORING, flooring);
        initialValues.put(R_STEPS, steps);
        initialValues.put(R_LIFTS, lifts);
        initialValues.put(R_BATHROOMS, bathrooms);
        initialValues.put(R_LAYOUT, layout);
        initialValues.put(R_STAFF, staff);
        initialValues.put(R_PARKING, parking);
        initialValues.put(R_SUB_RATING, sub_rating);

        return mDb.insert(DATABASE_RATING_TABLE, null, initialValues);
    }

    /**
     * Delete the venue with the given rowId
     * 
     * @param rowId id of venue to delete
     * @return true if deleted, false otherwise
     */
    public boolean deleteVenue(long rowId) {
    	
    	//Delete any associated ratings before deleting a venue?
        return mDb.delete(DATABASE_VENUE_TABLE, V_ROWID + "=" + rowId, null) > 0;
    }
    
    /**
     * Delete the rating with the given rowId
     * 
     * @param rowId id of rating to delete
     * @return true if deleted, false otherwise
     */
    public boolean deleteRating(long rowId) {

        return mDb.delete(DATABASE_RATING_TABLE, R_ROWID + "=" + rowId, null) > 0;
    }

    /**
     * Return a Cursor over the list of all venues in the database
     * 
     * @return Cursor over all venues
     */
    public Cursor fetchAllVenues() {

        return mDb.query(DATABASE_VENUE_TABLE, new String[] {V_ROWID, V_NAME, V_ADDRESS_LINE1, 
        		V_ADDRESS_LINE2, V_ADDRESS_LINE3, V_CATEGORY, 
        		V_TOT_RATING}, null, null, null, null, null);
    }
    
    /**
     * Return a Cursor over the list of all ratings in the database
     * 
     * @return Cursor over all ratings
     */
    public Cursor fetchAllRatings() {

        return mDb.query(DATABASE_RATING_TABLE, new String[] {R_ROWID, R_VENUE_ID, 
        		R_APPROACH, R_DOORS, R_FLOORING, R_STEPS, R_LIFTS, R_BATHROOMS, 
        		R_LAYOUT, R_STAFF, R_PARKING, R_SUB_RATING}, null, null, null, 
        		null, null);
    }

    /**
     * Return a Cursor positioned at the venue that matches the given rowId
     * 
     * @param rowId id of venue to retrieve
     * @return Cursor positioned to matching venue, if found
     * @throws SQLException if venue could not be found/retrieved
     */
    public Cursor fetchVenue(long rowId) throws SQLException {

        Cursor mCursor =

            mDb.query(true, DATABASE_VENUE_TABLE, new String[] {V_ROWID, V_NAME,
                    V_ADDRESS_LINE1, V_ADDRESS_LINE2, V_ADDRESS_LINE3, 
                    V_CATEGORY, V_TOT_RATING}, V_ROWID + "=" + rowId, null,
                    null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;

    }
    
    /**
     * Return a Cursor positioned at the rating that matches the given rowId
     * 
     * @param rowId id of rating to retrieve
     * @return Cursor positioned to matching rating, if found
     * @throws SQLException if rating could not be found/retrieved
     */
    public Cursor fetchRating(long rowId) throws SQLException {

        Cursor mCursor =

            mDb.query(true, DATABASE_RATING_TABLE, new String[] {R_ROWID,
                    R_VENUE_ID, R_APPROACH, R_DOORS, R_FLOORING, R_STEPS, 
                    R_LIFTS, R_BATHROOMS, R_LAYOUT, R_STAFF, R_PARKING, R_SUB_RATING}, 
                    R_ROWID + "=" + rowId, null,
                    null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;

    }

    /**
     * Update the venue fields using the details provided. The venue to be updated is
     * specified using the rowId, and it is altered to use the parameter
     * values passed in
     * 
     * @param values to set venue fields to
     * @return true if the venue was successfully updated, false otherwise
     */
    public boolean updateVenue(long rowId, String name, String line1, String line2, 
    		String line3, String category, int tot_rating) {
        ContentValues args = new ContentValues();
        args.put(V_NAME, name);
        args.put(V_ADDRESS_LINE1, line1);
        args.put(V_ADDRESS_LINE2, line2);
        args.put(V_ADDRESS_LINE3, line3);
        args.put(V_CATEGORY, category);
        args.put(V_TOT_RATING, tot_rating);

        return mDb.update(DATABASE_VENUE_TABLE, args, V_ROWID + "=" + rowId, null) > 0;
    }
    
    /**
     * Update the rating using the details provided. The rating to be updated is
     * specified using the rowId, and it is altered to use the parameter
     * values passed in
     * 
     * @param values to set rating fields to
     * @return true if the rating was successfully updated, false otherwise
     */
    public boolean updateRating(long rowId, int venue_id, int approach, int doors, 
    		int flooring, int steps, int lifts, int bathrooms, int layout, 
    		int staff, int parking, int sub_rating) {
        ContentValues args = new ContentValues();
        args.put(R_VENUE_ID, venue_id);
        args.put(R_APPROACH, approach);
        args.put(R_DOORS, doors);
        args.put(R_FLOORING, flooring);
        args.put(R_STEPS, steps);
        args.put(R_LIFTS, lifts);
        args.put(R_BATHROOMS, bathrooms);
        args.put(R_LAYOUT, layout);
        args.put(R_STAFF, staff);
        args.put(R_PARKING, parking);
        args.put(R_SUB_RATING, sub_rating);

        return mDb.update(DATABASE_RATING_TABLE, args, R_ROWID + "=" + rowId, null) > 0;
    }
    
}
