package dev.maynooth.mobile.leglessindublin;

import java.util.List;
import java.util.Locale;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import dev.maynooth.mobile.leglessindublin.datastore.LeglessDbAdapter;
import dev.maynooth.mobile.leglessindublin.datastore.Location;
import dev.maynooth.mobile.leglessindublin.datastore.Venue;
import dev.maynooth.mobile.leglessindublin.datastore.VenueType;

public class SearchResultsList extends Activity {

	private final Context ctx = this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_results_list);
		// Show the Up button in the action bar.
		setupActionBar();

		Intent intent = getIntent();
		final String venueType = intent
				.getStringExtra(MainMenu.SEARCH_VENUE_TYPE);
		final String location = intent.getStringExtra(MainMenu.SEARCH_LOCATION);
		final String[] responses = { location, venueType };

		// new GetLocationId().execute(responses);

		// new Thread(new Runnable() {
		//
		// @Override
		// public void run() {
		// LeglessDbAdapter dbAdapter = new LeglessDbAdapter(
		// getApplicationContext());
		// dbAdapter.open();
		//
		// String[] searchArgs = new String[2];
		// searchArgs[0] = Integer.toString(Location.findIdByLocation(
		// dbAdapter.getDbConnect(), location));
		// searchArgs[1] = Integer.toString(VenueType.findIdByType(
		// dbAdapter.getDbConnect(),
		// venueType.toLowerCase(Locale.getDefault())));
		//
		// final List<Venue> searchResults = Venue
		// .findByLocationAndVenueType(dbAdapter.getDbConnect(),
		// searchArgs);
		//
		// runOnUiThread(new Runnable() {
		//
		// @Override
		// public void run() {
		// Toast.makeText(ctx, searchResults.get(0).getName(),
		// Toast.LENGTH_SHORT).show();
		// }
		// });
		// }
		// }).start();
	}

	private class GetLocationId extends AsyncTask<String, Void, List<Venue>> {

		@Override
		protected void onPostExecute(List<Venue> results) {
			// TODO Auto-generated method stub
			super.onPostExecute(results);
			Toast.makeText(ctx, results.get(0).getName(), Toast.LENGTH_SHORT)
					.show();
		}

		@Override
		protected List<Venue> doInBackground(String... searchInputs) {
			LeglessDbAdapter dbAdapter = new LeglessDbAdapter(
					getApplicationContext());
			dbAdapter.open();

			String[] searchArgs = new String[2];
			searchArgs[0] = Integer.toString(Location.findIdByLocation(
					dbAdapter.getDbConnect(),
					searchInputs[0].toLowerCase(Locale.getDefault())));
			searchArgs[1] = Integer.toString(VenueType.findIdByType(
					dbAdapter.getDbConnect(),
					searchInputs[1].toLowerCase(Locale.getDefault())));

			return Venue.findByLocationAndVenueType(dbAdapter.getDbConnect(),
					searchArgs);
		}

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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search_results_list, menu);
		return true;
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

}
