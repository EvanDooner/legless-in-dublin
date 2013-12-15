package dev.maynooth.mobile.leglessindublin.asynctasks;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import dev.maynooth.mobile.leglessindublin.datastore.LeglessDbAdapter;
import dev.maynooth.mobile.leglessindublin.datastore.Venue;

public class NewVenueInserter extends AsyncTask<Venue, Void, Void> {

	protected Context ctx;

	public NewVenueInserter(Context context) {
		this.ctx = context;
	}

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