package dev.maynooth.mobile.leglessindublin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

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
	 * 
	 */
	private void populateSpinners() {
		/* Get venue types from DB */
		/* Get number of venue types */
		final int numTypes = 2; // For test purposes
		String[] venueTypes = new String[numTypes];
		/* Put venue types in array */
		venueTypes[0] = "Bar";
		venueTypes[1] = "Club";
		ArrayAdapter<String> adapterVT = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, venueTypes);
		Spinner venueType = (Spinner) findViewById(R.id.spinnerVenue);
		venueType.setAdapter(adapterVT);

		// Add content to location spinner
		/* Get locations from DB */
		/* Get number of locations */
		final int numLocs = 2; // For test purposes
		String[] locs = new String[numLocs];
		/* Put venue types in array */
		locs[0] = "Dublin 1";
		locs[1] = "Dublin 2";
		ArrayAdapter<String> adapterLoc = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, locs);
		Spinner location = (Spinner) findViewById(R.id.spinnerLocation);
		location.setAdapter(adapterLoc);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_menu, menu);
		return true;
	}

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
