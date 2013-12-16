/**
 * 
 */
package dev.maynooth.mobile.leglessindublin.asynctasks;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import dev.maynooth.mobile.leglessindublin.datastore.LeglessDbAdapter;
import dev.maynooth.mobile.leglessindublin.datastore.Venue;

/**
 * Fetches a single venue from the db by id
 * 
 * @author Evan Dooner, 12262480
 * @version 2013-12-15-00
 */
public class VenueFetcher extends AsyncTask<String, Void, Venue> {

	protected Context ctx;

	public VenueFetcher(Context context) {
		this.ctx = context;
	}

	@Override
	protected Venue doInBackground(String... params) {

		int rowId = Integer.parseInt(params[0]);

		LeglessDbAdapter dbAdapter = new LeglessDbAdapter(ctx);
		dbAdapter.open();
		SQLiteDatabase dbConnect = dbAdapter.getDbConnect();

		Venue result = Venue.findById(rowId, dbConnect);

		return result;
	}

}
