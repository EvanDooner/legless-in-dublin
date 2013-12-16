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

public class AddNewVenue extends Activity {

	private List<Location> locations;

	private List<VenueType> venueTypes;

	public void addNewVenue(View view) {
		
		EditText nameET = (EditText)findViewById(R.id.etVenueName);
		String name = nameET.getEditableText().toString();
		
		Spinner venueTypeSpnr = (Spinner)findViewById(R.id.spinnerSetVenueType);
		final int venueType = ((VenueType)venueTypeSpnr.getSelectedItem()).getRowId();
		
		EditText streetNameET = (EditText)findViewById(R.id.etStreetName);
		String streetName = streetNameET.getEditableText().toString();
		
		Spinner locationSpnr = (Spinner)findViewById(R.id.spinnerSetLocation);
		final int location = ((Location)locationSpnr.getSelectedItem()).getRowId();
		
		Venue newVenue = new Venue(name, location, venueType);
		newVenue.setStreetName(streetName);
		
		new NewVenueInserter(this){

			/* (non-Javadoc)
			 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
			 */
			@Override
			protected void onPostExecute(Void result) {
				
				Toast.makeText(getApplicationContext(), "Venue saved", Toast.LENGTH_SHORT).show();
				
				Intent backToSearch = new Intent(ctx, SearchResultsList.class);
				
				backToSearch.putExtra(MainMenu.SEARCH_VENUE_TYPE, "" + venueType);
				backToSearch.putExtra(MainMenu.SEARCH_LOCATION, "" + location);
				
				startActivity(backToSearch);
				
			}
			
		}.execute(newVenue);
		
	}

	// @Override
	// public boolean onCreateOptionsMenu(Menu menu) {
	// // Inflate the menu; this adds items to the action bar if it is present.
	// getMenuInflater().inflate(R.menu.add_new_venue, menu);
	// return true;
	// }

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
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
			setTheme(android.R.style.Theme_Holo_Light);
		}
		setContentView(R.layout.activity_add_new_venue);
		// Show the Up button in the action bar.
		setupActionBar();

		populateSpinners();
	}

	/*
	 * Adds choices to Venue Type and Location spinners Prefers cached values
	 * over fetching from DB
	 */
	private void populateSpinners() {
		if (locations != null) {
			setLocationSpinner(locations);
		} else {
			LocationFetcher fillLocSpinner = new LocationFetcher(
					this) {

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
			setVenueTypeSpinner(venueTypes);
		} else {
			VenueTypeFetcher fillVTSpinner = new VenueTypeFetcher(
					this) {

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
