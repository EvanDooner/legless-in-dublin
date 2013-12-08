package dev.maynooth.mobile.leglessindublin;

import static dev.maynooth.mobile.leglessindublin.utils.StringUtilities.toTitleCase;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import dev.maynooth.mobile.leglessindublin.datastore.LeglessDbAdapter;
import dev.maynooth.mobile.leglessindublin.datastore.Location;
import dev.maynooth.mobile.leglessindublin.datastore.VenueType;

public class MainMenu extends Activity {

	public static final String SEARCH_VENUE_TYPE = "dev.maynooth.mobile.leglessindublin.SEARCH_VENUE_TYPE";
	public static final String SEARCH_LOCATION = "dev.maynooth.mobile.leglessindublin.SEARCH_LOCATION";

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

		// Database actions are performed on a new thread and spinners are
		// filled with retrieved values on UI thread
		new Thread(new Runnable() {

			@Override
			public void run() {
				LeglessDbAdapter dbAdapter = new LeglessDbAdapter(
						getApplicationContext());
				dbAdapter.open();

				List<String> venueTypesUnformatted = VenueType
						.fetchAllTypeNames(dbAdapter.getDbConnect());
				List<String> venueTypeFormattingList = new ArrayList<String>();
				for (String option : venueTypesUnformatted) {
					venueTypeFormattingList.add(toTitleCase(option));
				}
				final List<String> venueTypes = new ArrayList<String>(
						venueTypeFormattingList);

				List<String> locationsUnformatted = Location
						.fetchAllLocationNames(dbAdapter.getDbConnect());
				List<String> locationsFormattingList = new ArrayList<String>();
				for (String option : locationsUnformatted) {
					locationsFormattingList.add(toTitleCase(option));
				}
				final List<String> locations = new ArrayList<String>(
						locationsUnformatted);

				dbAdapter.close();
				
				// Back to UI thread
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						Spinner venueType = (Spinner) findViewById(R.id.spinnerVenue);
						ArrayAdapter<String> adapterVT = new ArrayAdapter<String>(
								getApplicationContext(),
								android.R.layout.simple_spinner_item,
								venueTypes);
						venueType.setAdapter(adapterVT);

						Spinner location = (Spinner) findViewById(R.id.spinnerLocation);
						ArrayAdapter<String> adapterLoc = new ArrayAdapter<String>(
								getApplicationContext(),
								android.R.layout.simple_spinner_item, locations);
						location.setAdapter(adapterLoc);
					}
				});
			}
		}).start();
	}

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

}
