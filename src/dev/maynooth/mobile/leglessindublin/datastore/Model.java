package dev.maynooth.mobile.leglessindublin.datastore;

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
	public void delete(/* Might need to pass in a DatabaseHelper here */);
	
	/**
	 * Persists this model to the database
	 */
	public void save(/* Might need to pass in a DatabaseHelper here */);
	
	/**
	 * Updates this model's record in the database
	 */
	public void update(/* Might need to pass in a DatabaseHelper here */);

}
