package dev.maynooth.mobile.leglessindublin.asynctasks;

import static dev.maynooth.mobile.leglessindublin.utils.StringUtilities.toTitleCase;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import dev.maynooth.mobile.leglessindublin.datastore.LeglessDbAdapter;
import dev.maynooth.mobile.leglessindublin.datastore.VenueType;

/**
 * Fetches all venue types from the database
 * 
 * @author Evan Dooner, 12262480
 * @version 2013-12-18-00
 */
public class VenueTypeFetcher extends AsyncTask<Void, Void, List<VenueType>> {

	private Context ctx;

	/**
	 * Constructs a new venueTypeFetcher in the specified context
	 * 
	 * @param context
	 *            - the context in which to construct the venueTypeFetcher
	 */
	public VenueTypeFetcher(Context context) {
		this.ctx = context;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * Fetches all venue types from the database and formats their venue type
	 * strings in title case
	 * 
	 * @see android.os.AsyncTask#doInBackground(Params[])
	 */
	@Override
	protected List<VenueType> doInBackground(Void... params) {
		LeglessDbAdapter dbAdapter = new LeglessDbAdapter(ctx);
		dbAdapter.open();
		SQLiteDatabase dbConnect = dbAdapter.getDbConnect();

		List<VenueType> venueTypesUnformatted;
		try {
			venueTypesUnformatted = VenueType.fetchAll(dbConnect);
		} finally {
			dbAdapter.close();
		}

		List<VenueType> venueTypeFormatted = new ArrayList<VenueType>();
		for (VenueType option : venueTypesUnformatted) {
			option.setVenueType(toTitleCase(option.getVenueType()));
			venueTypeFormatted.add(option);
		}

		return venueTypeFormatted;
	}

}