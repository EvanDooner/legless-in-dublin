package dev.maynooth.mobile.leglessindublin;

import java.util.List;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import dev.maynooth.mobile.leglessindublin.arrayadapters.LocationAdapter;
import dev.maynooth.mobile.leglessindublin.arrayadapters.VenueTypeAdapter;
import dev.maynooth.mobile.leglessindublin.asynctasks.NewVenueInserter;
import dev.maynooth.mobile.leglessindublin.asynctasks.LocationFetcher;
import dev.maynooth.mobile.leglessindublin.asynctasks.VenueTypeFetcher;
import dev.maynooth.mobile.leglessindublin.datastore.Location;
import dev.maynooth.mobile.leglessindublin.datastore.Venue;
import dev.maynooth.mobile.leglessindublin.datastore.VenueType;

/**
 * Displays the add new venue page, which allows the user to create a new venue
 * 
 * @author Evan Dooner, 12262480
 * @version 2013-12-18-00
 */
public class AddNewVenue extends Activity {

	private List<Location> locations;

	private List<VenueType> venueTypes;

	/**
	 * On button press, extracts the details from the edit boxes and spinners,
	 * creates a new venue, and saves it to the database.
	 * <p>
	 * The user is notified that the venue has been saved and is then forwarded
	 * to the search results page. The search results page displays the results
	 * for the same location and venue type as the new venue, ensuring that it
	 * is visible.
	 * 
	 * @param view
	 *            - the Submit button
	 */
	public void addNewVenue(View view) {

		// Extract the venue details
		EditText nameET = (EditText) findViewById(R.id.etVenueName);
		String name = nameET.getEditableText().toString();

		Spinner venueTypeSpnr = (Spinner) findViewById(R.id.spinnerSetVenueType);
		final int venueType = ((VenueType) venueTypeSpnr.getSelectedItem())
				.getRowId();

		EditText streetNameET = (EditText) findViewById(R.id.etStreetName);
		String streetName = streetNameET.getEditableText().toString();

		Spinner locationSpnr = (Spinner) findViewById(R.id.spinnerSetLocation);
		final int location = ((Location) locationSpnr.getSelectedItem())
				.getRowId();

		// Create a new venue with the input details
		Venue newVenue = new Venue(name, location, venueType);
		newVenue.setStreetName(streetName);

		// Anonymous class extending NewVenueInserter, which saves the new venue
		// to the database, informs the user that it has been saved, and returns
		// the user to the search results page
		new NewVenueInserter(this) {

			/*
			 * (non-Javadoc)
			 * 
			 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
			 */
			@Override
			protected void onPostExecute(Void result) {

				Toast.makeText(getApplicationContext(), "Venue saved",
						Toast.LENGTH_SHORT).show();

				Intent backToSearch = new Intent(ctx, SearchResultsList.class);

				backToSearch.putExtra(MainMenu.SEARCH_VENUE_TYPE, ""
						+ venueType);
				backToSearch.putExtra(MainMenu.SEARCH_LOCATION, "" + location);

				startActivity(backToSearch);

			}

		}.execute(newVenue);

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
			setTheme(android.R.style.Theme_Holo_Light);
		}
		setContentView(R.layout.activity_add_new_venue);
		// Show the Up button in the action bar.
		setupActionBar();

		populateSpinners(); // Populate spinner from db
	}

	/*
	 * (non-Javadoc)
	 * 
	 * Adds choices to Venue Type and Location spinners. Prefers cached values
	 * over fetching from DB
	 */
	private void populateSpinners() {
		if (locations != null) {
			setLocationSpinner(locations); // Use cached values if available
		} else {
			LocationFetcher fillLocSpinner = new LocationFetcher(this) {

				/*
				 * (non-Javadoc)
				 * 
				 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
				 */
				@Override
				protected void onPostExecute(List<Location> result) {
					locations = result;
					setLocationSpinner(result);
				}

			};
			fillLocSpinner.execute();
		}

		if (venueTypes != null) {
			setVenueTypeSpinner(venueTypes); // Use cached values if available
		} else {
			VenueTypeFetcher fillVTSpinner = new VenueTypeFetcher(this) {

				/*
				 * (non-Javadoc)
				 * 
				 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
				 */
				@Override
				protected void onPostExecute(List<VenueType> result) {
					venueTypes = result;

					setVenueTypeSpinner(result);
				}

			};
			fillVTSpinner.execute();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * Fills the location spinner with a list of locations
	 */
	private void setLocationSpinner(List<Location> locations) {
		Spinner location = (Spinner) findViewById(R.id.spinnerSetLocation);
		LocationAdapter adapterLoc = new LocationAdapter(this,
				R.layout.legless_spinner_style_light, locations);
		adapterLoc.setDropDownViewResource(R.layout.legless_spinner_dropdown);
		location.setAdapter(adapterLoc);
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * Fills the venue type spinner with a list of venue types
	 */
	private void setVenueTypeSpinner(final List<VenueType> venueTypes) {
		Spinner venueType = (Spinner) findViewById(R.id.spinnerSetVenueType);
		VenueTypeAdapter adapterVT = new VenueTypeAdapter(this,
				R.layout.legless_spinner_style_light, venueTypes);
		adapterVT.setDropDownViewResource(R.layout.legless_spinner_dropdown);
		venueType.setAdapter(adapterVT);
	}

}
