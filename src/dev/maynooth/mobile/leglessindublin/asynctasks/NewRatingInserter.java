/**
 * 
 */
package dev.maynooth.mobile.leglessindublin.asynctasks;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import dev.maynooth.mobile.leglessindublin.datastore.LeglessDbAdapter;
import dev.maynooth.mobile.leglessindublin.datastore.Rating;
import dev.maynooth.mobile.leglessindublin.datastore.Venue;

/**
 * @author evan
 * 
 */
public class NewRatingInserter extends AsyncTask<Rating, Void, Void> {

	protected Context ctx;

	public NewRatingInserter(Context context) {
		this.ctx = context;
	}

	@Override
	protected Void doInBackground(Rating... params) {

		Rating newRating = params[0];
		LeglessDbAdapter dbAdapter = new LeglessDbAdapter(ctx);
		dbAdapter.open();
		SQLiteDatabase dbConnect = dbAdapter.getDbConnect();
		try {
			newRating.save(dbConnect);
			Venue ratedVenue = Venue.findById(newRating.getVenueId(), dbConnect);
			ratedVenue.updateAverageRatings(dbConnect);
			ratedVenue.update(dbConnect);
		} finally {
			dbAdapter.close();
		}

		return null;
	}

}
