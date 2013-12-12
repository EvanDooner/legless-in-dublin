package dev.maynooth.mobile.leglessindublin;

import static dev.maynooth.mobile.leglessindublin.utils.StringUtilities.toTitleCase;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import dev.maynooth.mobile.leglessindublin.datastore.LeglessDbAdapter;
import dev.maynooth.mobile.leglessindublin.datastore.Location;
import dev.maynooth.mobile.leglessindublin.datastore.VenueType;

public class MainMenu extends Activity {

	private class PopulateLocationSpinner extends
			AsyncTask<Void, Void, List<String>> {

		@Override
		protected List<String> doInBackground(Void... params) {
			LeglessDbAdapter dbAdapter = new LeglessDbAdapter(
					getApplicationContext());
			dbAdapter.open();

			List<String> locationsUnformatted;
			try {
				locationsUnformatted = Location.fetchAllLocationNames(dbAdapter
						.getDbConnect());
			} finally {
				dbAdapter.close();
			}

			List<String> locationsFormatted = new ArrayList<String>();
			for (String option : locationsUnformatted) {
				locationsFormatted.add(toTitleCase(option));
			}

			return locationsFormatted;
		}

		@Override
		protected void onPostExecute(List<String> result) {
			super.onPostExecute(result);

			Spinner location = (Spinner) findViewById(R.id.spinnerLocation);
			ArrayAdapter<String> adapterLoc = new ArrayAdapter<String>(
					getApplicationContext(),
					android.R.layout.simple_spinner_item, result);
			adapterLoc.setDropDownViewResource(R.layout.my_spinner_dropdown);
			location.setAdapter(adapterLoc);
		}

	}

	private class PopulateVenueTypeSpinner extends
			AsyncTask<Void, Void, List<String>> {

		@Override
		protected List<String> doInBackground(Void... params) {
			LeglessDbAdapter dbAdapter = new LeglessDbAdapter(
					getApplicationContext());
			dbAdapter.open();

			List<String> venueTypesUnformatted;
			try {
				venueTypesUnformatted = VenueType.fetchAllTypeNames(dbAdapter
						.getDbConnect());
			} finally {
				dbAdapter.close();
			}

			List<String> venueTypeFormatted = new ArrayList<String>();
			for (String option : venueTypesUnformatted) {
				venueTypeFormatted.add(toTitleCase(option));
			}

			return venueTypeFormatted;
		}

		@Override
		protected void onPostExecute(List<String> result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			Spinner venueType = (Spinner) findViewById(R.id.spinnerVenue);
			ArrayAdapter<String> adapterVT = new ArrayAdapter<String>(
					getApplicationContext(),
					android.R.layout.simple_spinner_item, result);
			adapterVT.setDropDownViewResource(R.layout.my_spinner_dropdown);
			venueType.setAdapter(adapterVT);
		}

	}

	public static final String SEARCH_VENUE_TYPE = "dev.maynooth.mobile.leglessindublin.SEARCH_VENUE_TYPE";
	public static final String SEARCH_LOCATION = "dev.maynooth.mobile.leglessindublin.SEARCH_LOCATION";

	/**
	 * Extracts the user's choices for venue type and location and forwards them
	 * to the search page.
	 * 
	 * @param view
	 *            a view - the view that called this method
	 */
	public void searchRatings(View view) {
		// Intent redirects to search results page
		Intent intent = new Intent(this, SearchResultsList.class);

		// Get currently selected venue type and location
		Spinner venueType = (Spinner) findViewById(R.id.spinnerVenue);
		String venueTypeStr = venueType.getSelectedItem().toString();
		Spinner location = (Spinner) findViewById(R.id.spinnerLocation);
		String locStr = location.getSelectedItem().toString();

		// Store selected values in passed intent
		intent.putExtra(SEARCH_VENUE_TYPE, venueTypeStr);
		intent.putExtra(SEARCH_LOCATION, locStr);

		startActivity(intent);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);

		populateSpinners();
	}

	/**
	 * Adds choices to Venue Type and Location spinners
	 */
	private void populateSpinners() {
		new PopulateLocationSpinner().execute();
		new PopulateVenueTypeSpinner().execute();
	}

}
