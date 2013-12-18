package dev.maynooth.mobile.leglessindublin.asynctasks;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import dev.maynooth.mobile.leglessindublin.datastore.LeglessDbAdapter;
import dev.maynooth.mobile.leglessindublin.datastore.Venue;

/**
 * Inserts a new venue into the database
 * 
 * @author Evan Dooner, 12262480
 * @version 2013-12-18-00
 */
public class NewVenueInserter extends AsyncTask<Venue, Void, Void> {

	protected Context ctx;

	/**
	 * Constructs a new newVenueInserter in the specified context
	 * 
	 * @param context
	 *            - the context in which to construct the newVenueInserter
	 */
	public NewVenueInserter(Context context) {
		this.ctx = context;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * Inserts a single venue into the database
	 * 
	 * @see android.os.AsyncTask#doInBackground(Params[])
	 */
	@Override
	protected Void doInBackground(Venue... params) {
		Venue newVenue = params[0];
		LeglessDbAdapter dbAdapter = new LeglessDbAdapter(ctx);
		dbAdapter.open();
		SQLiteDatabase dbConnect = dbAdapter.getDbConnect();
		try {
			newVenue.save(dbConnect);
		} finally {
			dbAdapter.close();
		}

		return null;
	}

}