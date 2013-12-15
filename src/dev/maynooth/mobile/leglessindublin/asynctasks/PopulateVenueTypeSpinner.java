package dev.maynooth.mobile.leglessindublin.asynctasks;

import static dev.maynooth.mobile.leglessindublin.utils.StringUtilities.toTitleCase;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import dev.maynooth.mobile.leglessindublin.datastore.LeglessDbAdapter;
import dev.maynooth.mobile.leglessindublin.datastore.VenueType;

public class PopulateVenueTypeSpinner extends
		AsyncTask<Void, Void, List<VenueType>> {

	/**
	 * 
	 */
	private Context ctx;

	/**
	 * @param mainMenu
	 */
	public PopulateVenueTypeSpinner(Context context) {
		this.ctx = context;
	}

	@Override
	protected List<VenueType> doInBackground(Void... params) {
		LeglessDbAdapter dbAdapter = new LeglessDbAdapter(
				ctx);
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