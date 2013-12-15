package dev.maynooth.mobile.leglessindublin.asynctasks;

import static dev.maynooth.mobile.leglessindublin.utils.StringUtilities.toTitleCase;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import dev.maynooth.mobile.leglessindublin.datastore.LeglessDbAdapter;
import dev.maynooth.mobile.leglessindublin.datastore.Location;

public class PopulateLocationSpinner extends AsyncTask<Void, Void, List<Location>> {

	/**
	 * 
	 */
	private final Context ctx;

	/**
	 * @param mainMenu
	 */
	public PopulateLocationSpinner(Context context) {
		this.ctx = context;
	}

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