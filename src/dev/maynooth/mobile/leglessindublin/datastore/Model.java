package dev.maynooth.mobile.leglessindublin.datastore;

import android.database.sqlite.SQLiteDatabase;

/**
 * Specifies the access interface for database models
 * 
 * @author Evan Dooner
 * @version 2013-11-25-00
 */
public interface Model {
	
	/**
	 * Removes this model from the database
	 */
	public void delete(SQLiteDatabase dbConnect);
	
	/**
	 * Persists this model to the database
	 */
	public void save(SQLiteDatabase dbConnect);
	
	/**
	 * Updates this model's record in the database
	 */
	public void update(SQLiteDatabase dbConnect);

}
