package dev.maynooth.mobile.leglessindublin;

import java.util.List;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import dev.maynooth.mobile.leglessindublin.datastore.LeglessDbAdapter;
import dev.maynooth.mobile.leglessindublin.datastore.Venue;

public class SearchResultsList extends Activity {

	/*
	 * Here you can control what to do next when the user selects an item
	 */
	private class ResultsListOnItemClickListener implements OnItemClickListener {
		// TODO
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {

			Context context = view.getContext();

			TextView textViewItem = (TextView) view
					.findViewById(R.id.resultName);

			// get the clicked item name
			String listItemText = textViewItem.getText().toString();

			// get the clicked item ID
			String listItemId = textViewItem.getTag().toString();

			// just toast it
			Toast.makeText(context,
					"Item: " + listItemText + ", Item ID: " + listItemId,
					Toast.LENGTH_SHORT).show();

		}

	}

	private class SearchVenues extends AsyncTask<String, Void, List<Venue>> {

		private Context ctx;

		private SearchVenues(Context ctx) {
			super();
			this.ctx = ctx;
		}

		@Override
		protected List<Venue> doInBackground(String... searchArgs) {
			LeglessDbAdapter dbAdapter = new LeglessDbAdapter(
					getApplicationContext());
			dbAdapter.open();
			SQLiteDatabase dbConnect = dbAdapter.getDbConnect();

			List<Venue> results = Venue.findByLocationAndVenueType(dbConnect,
					searchArgs);
			
			cachedVenues = results;

			return results;
		}

		@Override
		protected void onPostExecute(List<Venue> results) {
			super.onPostExecute(results);

			VenueArrayAdapter adapter = new VenueArrayAdapter(ctx,
					R.layout.row, results);

			fillListView(results);
		}

	}

	private class VenueArrayAdapter extends ArrayAdapter<Venue> {

		private Context mContext;
		private int layoutResourceId;
		private List<Venue> venues;

		public VenueArrayAdapter(Context context, int resource,
				List<Venue> venues) {
			super(context, resource, venues);
			this.mContext = context;
			this.layoutResourceId = resource;
			this.venues = venues;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.widget.ArrayAdapter#getView(int, android.view.View,
		 * android.view.ViewGroup)
		 */
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				// inflate the layout
				LayoutInflater inflater = ((Activity) mContext)
						.getLayoutInflater();
				convertView = inflater.inflate(layoutResourceId, parent, false);
			}

			// object item based on the position
			Venue currentVenue = venues.get(position);

			// get the TextView and then set the text (item name) and tag (item
			// ID) values
			TextView textViewName = (TextView) convertView
					.findViewById(R.id.resultName);
			textViewName.setText(currentVenue.getName());
			textViewName.setTag(currentVenue.getRowId());

			RatingBar stars = (RatingBar) convertView
					.findViewById(R.id.venueRatingBar);
			Log.d("legless", "Current venue: " + currentVenue.getName()
					+ " rated: " + currentVenue.getTotalRating());
			stars.setRating((float) (currentVenue.getTotalRating()));

			TextView textViewStreet = (TextView) convertView
					.findViewById(R.id.resultStreetName);
			textViewStreet.setText(currentVenue.getStreetName());

			TextView textViewNumRatings = (TextView) convertView
					.findViewById(R.id.resultNumRatings);
			textViewNumRatings.setText("Ratings: "
					+ currentVenue.getNumRatings());

			return convertView;
		}

	}

	private String cachedLocation;
	private String cachedVenueType;
	private List<Venue> cachedVenues;

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

	// @Override
	// public boolean onCreateOptionsMenu(Menu menu) {
	// // Inflate the menu; this adds items to the action bar if it is present.
	// getMenuInflater().inflate(R.menu.search_results_list, menu);
	// return true;
	// }

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

		if (cachedVenues != null && cachedLocation != null
				&& cachedVenueType != null && location != null
				&& venueType != null && location.equals(cachedLocation)
				&& venueType.equals(cachedVenueType)) {

		} else {
			cachedLocation = location;
			cachedVenueType = venueType;
			new SearchVenues(this).execute(responses);
		}
	}

	private void fillListView(List<Venue> venues) {
		VenueArrayAdapter adapter = new VenueArrayAdapter(this, R.layout.row,
				venues);

		ListView resultsList = (ListView) findViewById(R.id.resultsView);
		resultsList.setAdapter(adapter);
		resultsList
				.setOnItemClickListener(new ResultsListOnItemClickListener());
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

}
