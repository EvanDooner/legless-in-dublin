package dev.maynooth.mobile.leglessindublin.asynctasks;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import dev.maynooth.mobile.leglessindublin.datastore.LeglessDbAdapter;
import dev.maynooth.mobile.leglessindublin.datastore.Rating;
import dev.maynooth.mobile.leglessindublin.datastore.Venue;

/**
 * Inserts a new rating into the database
 * 
 * @author Evan Dooner, 12262480
 * @version 2013-12-18-00
 */
public class NewRatingInserter extends AsyncTask<Rating, Void, Void> {

	protected Context ctx;

	/**
	 * Constructs a new newRatingInserter in the specified context
	 * 
	 * @param context
	 *            - the context in which to place the newRatingInserter
	 */
	public NewRatingInserter(Context context) {
		this.ctx = context;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * Inserts a single rating into the database. Updates the average ratings of
	 * the related venue to reflect the new rating
	 * 
	 * @see android.os.AsyncTask#doInBackground(Params[])
	 */
	@Override
	protected Void doInBackground(Rating... params) {

		Rating newRating = params[0];
		LeglessDbAdapter dbAdapter = new LeglessDbAdapter(ctx);
		dbAdapter.open();
		SQLiteDatabase dbConnect = dbAdapter.getDbConnect();
		try {
			newRating.save(dbConnect);
			Venue ratedVenue = Venue
					.findById(newRating.getVenueId(), dbConnect);
			ratedVenue.updateAverageRatings(dbConnect);
			ratedVenue.update(dbConnect);
		} finally {
			dbAdapter.close();
		}

		return null;
	}

}
