package dev.maynooth.mobile.leglessindublin;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.Toast;
import dev.maynooth.mobile.leglessindublin.arrayadapters.LocationAdapter;
import dev.maynooth.mobile.leglessindublin.arrayadapters.VenueTypeAdapter;
import dev.maynooth.mobile.leglessindublin.asynctasks.LocationFetcher;
import dev.maynooth.mobile.leglessindublin.asynctasks.VenueTypeFetcher;
import dev.maynooth.mobile.leglessindublin.datastore.LeglessDbAdapter;
import dev.maynooth.mobile.leglessindublin.datastore.Location;
import dev.maynooth.mobile.leglessindublin.datastore.Venue;
import dev.maynooth.mobile.leglessindublin.datastore.VenueType;

public class MainMenu extends Activity {

	/**
	 * Determines if results exist in the database for the specified query
	 * 
	 * @param String - usually two strings, location id and venue type id
	 * @return Boolean - true if results are found; false otherwise
	 */
	private class SearchCounter extends AsyncTask<String, Void, Boolean> {

		private Context ctx;

		public SearchCounter(Context context) {
			this.ctx = context;
		}

		/**
		 * Queries the database with the supplied search parameters, and determines if results exist
		 */
		@Override
		protected Boolean doInBackground(String... searchArgs) {
			LeglessDbAdapter dbAdapter = new LeglessDbAdapter(
					getApplicationContext());
			dbAdapter.open();
			SQLiteDatabase dbConnect = dbAdapter.getDbConnect();

			int count = Venue.findCountByLocationAndVenueType(dbConnect,
					searchArgs);

			return (count > 0) ? Boolean.TRUE : Boolean.FALSE;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
		 */
		/**
		 * If results were found in #doInBackground(), forwards the search query to SearchResultsPage
		 * otherwise displays a failure Toast
		 */
		@Override
		protected void onPostExecute(Boolean result) {
			if (result) {

				// Intent redirects to search results page
				Intent intent = new Intent(ctx, SearchResultsList.class);

				// Get currently selected venue type and location
				Spinner venueTypeSpnr = (Spinner) findViewById(R.id.spinnerVenue);
				VenueType venueType = (VenueType) venueTypeSpnr
						.getSelectedItem();

				Spinner locationSpnr = (Spinner) findViewById(R.id.spinnerLocation);
				Location location = (Location) locationSpnr.getSelectedItem();

				// Store selected values in passed intent
				intent.putExtra(SEARCH_VENUE_TYPE, "" + venueType.getRowId());
				intent.putExtra(SEARCH_LOCATION, "" + location.getRowId());

				startActivity(intent);
			} else {
				Toast.makeText(ctx, R.string.no_results_found,
						Toast.LENGTH_SHORT).show();
			}
		}

	}

	public static final String SEARCH_VENUE_TYPE = "dev.maynooth.mobile.leglessindublin.SEARCH_VENUE_TYPE";

	public static final String SEARCH_LOCATION = "dev.maynooth.mobile.leglessindublin.SEARCH_LOCATION";

	private List<Location> locations; // Cache locations

	private List<VenueType> venueTypes; // Cache venue types

	private int backPressed = 0;

	private Toast backTwiceReminder; // Member to allow toast to be cancelled
	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onBackPressed()
	 */
	@Override
	public void onBackPressed() {
		if (backPressed >= 1) {
			if (backTwiceReminder != null) {
				backTwiceReminder.cancel();
			}
			Intent intent = new Intent(Intent.ACTION_MAIN);
			intent.addCategory(Intent.CATEGORY_HOME);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
			backPressed = 0;
			finish();
		} else {
			backTwiceReminder = Toast
					.makeText(
							this,
							"Press the back button once again to close the application.",
							Toast.LENGTH_SHORT);
			backTwiceReminder.show();
			backPressed++;
		}
	}

	/**
	 * Extracts the user's choices for venue type and location and forwards
	 * their id to the search page.
	 * 
	 * @param view
	 *            a view - the view that called this method
	 */
	public void searchRatings(View view) {

		// Get currently selected venue type and location
		Spinner venueTypeSpnr = (Spinner) findViewById(R.id.spinnerVenue);
		VenueType venueType = (VenueType) venueTypeSpnr.getSelectedItem();

		Spinner locationSpnr = (Spinner) findViewById(R.id.spinnerLocation);
		Location location = (Location) locationSpnr.getSelectedItem();

		String[] searchParams = { "" + location.getRowId(),
				"" + venueType.getRowId(), };

		new SearchCounter(this).execute(searchParams);
	}

	/**
	 * Forwards the user to the terms and conditions page
	 */
	public void termsAndConditions(View view) {

		// Intent redirects to terms and conditions page
		Intent intent = new Intent(this, TermsAndConditions.class);

		startActivity(intent);

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
			setTheme(android.R.style.Theme_Holo_Light_NoActionBar_Fullscreen);
		}
		setContentView(R.layout.activity_main_menu);
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
			setVenueTypeSpinner(venueTypes);
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
	 * Fills the location spinner with a list of locations
	 */
	private void setLocationSpinner(List<Location> locations) {
		Spinner location = (Spinner) findViewById(R.id.spinnerLocation);
		LocationAdapter adapterLoc = new LocationAdapter(this,
				R.layout.legless_spinner_style, locations);
		adapterLoc.setDropDownViewResource(R.layout.legless_spinner_dropdown);
		location.setAdapter(adapterLoc);
	}

	/*
	 * Fills the venue type spinner with a list of venue types
	 */
	private void setVenueTypeSpinner(final List<VenueType> venueTypes) {
		Spinner venueType = (Spinner) findViewById(R.id.spinnerVenue);
		VenueTypeAdapter adapterVT = new VenueTypeAdapter(this,
				R.layout.legless_spinner_style, venueTypes);
		adapterVT.setDropDownViewResource(R.layout.legless_spinner_dropdown);
		venueType.setAdapter(adapterVT);
	}

}
