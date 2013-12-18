package dev.maynooth.mobile.leglessindublin.asynctasks;

import static dev.maynooth.mobile.leglessindublin.utils.StringUtilities.toTitleCase;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import dev.maynooth.mobile.leglessindublin.datastore.LeglessDbAdapter;
import dev.maynooth.mobile.leglessindublin.datastore.Location;

/**
 * Fetches all locations from the database and returns them
 * 
 * @author Evan Dooner, 12262480
 * @version 2013-12-18-00
 */
public class LocationFetcher extends AsyncTask<Void, Void, List<Location>> {

	private final Context ctx;

	/**
	 * Constructs a new LocationFetcher in the specified context
	 * 
	 * @param context
	 *            - the context in which to set the locationFetcher
	 */
	public LocationFetcher(Context context) {
		this.ctx = context;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * Fetches all the location records from the database and formats their
	 * location string to title case
	 * 
	 * @see android.os.AsyncTask#doInBackground(Params[])
	 */
	@Override
	protected List<Location> doInBackground(Void... params) {
		LeglessDbAdapter dbAdapter = new LeglessDbAdapter(ctx);
		dbAdapter.open();
		SQLiteDatabase dbConnect = dbAdapter.getDbConnect();

		List<Location> locationsUnformatted;
		try {
			locationsUnformatted = Location.fetchAll(dbConnect);
		} finally {
			dbAdapter.close();
		}

		List<Location> locationsFormatted = new ArrayList<Location>();
		for (Location option : locationsUnformatted) {
			option.setLocation(toTitleCase(option.getLocation()));
			locationsFormatted.add(option);
		}

		return locationsFormatted;
	}

}