package dev.maynooth.mobile.leglessindublin;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import dev.maynooth.mobile.leglessindublin.asynctasks.NewRatingInserter;
import dev.maynooth.mobile.leglessindublin.asynctasks.VenueFetcher;
import dev.maynooth.mobile.leglessindublin.datastore.Rating;
import dev.maynooth.mobile.leglessindublin.datastore.Venue;

public class RateVenue extends Activity {

	private Venue selectedVenue;

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

	public void submitRating(View view) {

		double numOfCategories = 9.0;

		RatingBar entranceRating = (RatingBar) findViewById(R.id.entranceRating);
		double approach = entranceRating.getRating();

		RatingBar doorsRating = (RatingBar) findViewById(R.id.doorsRating);
		double doors = doorsRating.getRating();

		RatingBar flooringRating = (RatingBar) findViewById(R.id.flooringRating);
		double flooring = flooringRating.getRating();

		RatingBar stepsRating = (RatingBar) findViewById(R.id.stepsRating);
		double steps = stepsRating.getRating();

		RatingBar liftsRating = (RatingBar) findViewById(R.id.liftsRating);
		double lifts = liftsRating.getRating();

		RatingBar bathroomsRating = (RatingBar) findViewById(R.id.bathroomsRating);
		double bathrooms = bathroomsRating.getRating();

		RatingBar layoutRating = (RatingBar) findViewById(R.id.layoutRating);
		double layout = layoutRating.getRating();

		RatingBar staffRating = (RatingBar) findViewById(R.id.staffRating);
		double staff = staffRating.getRating();

		RatingBar parkingRating = (RatingBar) findViewById(R.id.parkingRating);
		double parking = parkingRating.getRating();

		double totalRating = (approach + doors + flooring + steps + lifts
				+ bathrooms + layout + staff + parking)
				/ numOfCategories;

		Rating userRating = new Rating(selectedVenue.getRowId());
		userRating.setApproach(approach);
		userRating.setDoors(doors);
		userRating.setFlooring(flooring);
		userRating.setSteps(steps);
		userRating.setLifts(lifts);
		userRating.setBathrooms(bathrooms);
		userRating.setLayout(layout);
		userRating.setStaff(staff);
		userRating.setParking(parking);
		userRating.setSubTotal(totalRating);

		new NewRatingInserter(this) {

			/*
			 * (non-Javadoc)
			 * 
			 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
			 */
			@Override
			protected void onPostExecute(Void result) {

				Toast.makeText(getApplicationContext(), "Rating saved",
						Toast.LENGTH_SHORT).show();

				Intent showThankYou = new Intent(ctx, ThankYouSplash.class);

				startActivity(showThankYou);

			}

		}.execute(userRating);

	}

	@Override
	@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
			setTheme(android.R.style.Theme_Holo_Light);
		}
		setContentView(R.layout.activity_rate_venue);
		// Show the Up button in the action bar.
		setupActionBar();

		Intent receivedIntent = getIntent();
		String venueId = receivedIntent
				.getStringExtra(SearchResultsList.SELECTED_VENUE_ID);

		new VenueFetcher(this) {

			/*
			 * (non-Javadoc)
			 * 
			 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
			 */
			@Override
			protected void onPostExecute(Venue result) {
				selectedVenue = result;
				fillInfo();
			}

		}.execute(venueId);
	}

	// @Override
	// public boolean onCreateOptionsMenu(Menu menu) {
	// // Inflate the menu; this adds items to the action bar if it is present.
	// getMenuInflater().inflate(R.menu.rate_venue, menu);
	// return true;
	// }

	private void fillInfo() {

		// Fill info header
		TextView tvName = (TextView) findViewById(R.id.resultName);
		tvName.setText(selectedVenue.getName());

		RatingBar totalRating = (RatingBar) findViewById(R.id.venueRatingBar);
		totalRating.setRating((float) selectedVenue.getTotalRating());

		TextView tvStreetname = (TextView) findViewById(R.id.resultStreetName);
		tvStreetname.setText(selectedVenue.getStreetName());

		TextView tvNumRatings = (TextView) findViewById(R.id.resultNumRatings);
		tvNumRatings.setText("Ratings: " + selectedVenue.getNumRatings());

		// Fill rating bars
		RatingBar entranceRating = (RatingBar) findViewById(R.id.entranceRating);
		entranceRating.setRating((float) selectedVenue.getApproach());

		RatingBar doorsRating = (RatingBar) findViewById(R.id.doorsRating);
		doorsRating.setRating((float) selectedVenue.getApproach());

		RatingBar flooringRating = (RatingBar) findViewById(R.id.flooringRating);
		flooringRating.setRating((float) selectedVenue.getFlooring());

		RatingBar stepsRating = (RatingBar) findViewById(R.id.stepsRating);
		stepsRating.setRating((float) selectedVenue.getSteps());

		RatingBar liftsRating = (RatingBar) findViewById(R.id.liftsRating);
		liftsRating.setRating((float) selectedVenue.getLifts());

		RatingBar bathroomsRating = (RatingBar) findViewById(R.id.bathroomsRating);
		bathroomsRating.setRating((float) selectedVenue.getBathrooms());

		RatingBar layoutRating = (RatingBar) findViewById(R.id.layoutRating);
		layoutRating.setRating((float) selectedVenue.getLayout());

		RatingBar staffRating = (RatingBar) findViewById(R.id.staffRating);
		staffRating.setRating((float) selectedVenue.getStaff());

		RatingBar parkingRating = (RatingBar) findViewById(R.id.parkingRating);
		parkingRating.setRating((float) selectedVenue.getParking());

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
