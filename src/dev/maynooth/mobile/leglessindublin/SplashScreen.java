package dev.maynooth.mobile.leglessindublin;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import dev.maynooth.mobile.leglessindublin.datastore.LeglessDbAdapter;
import dev.maynooth.mobile.leglessindublin.datastore.Venue;

/**
 * Displays the start up splash screen and runs background maintenance
 * activities.
 * 
 * @author Evan Dooner, 12262480
 * @version 2013-12-18-00
 */
public class SplashScreen extends Activity {

	private static int SPLASH_TIME_OUT = 3000; // Duration to display splash in
												// milliseconds

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_screen);

		// splashSound = MediaPlayer.create(this, R.)

		new VenueTotalRatingUpdater(this).execute();

		// Start the MainMenu activity after a delay
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				// This method will be executed once the timer is over
				// Start your app main activity
				Intent i = new Intent(SplashScreen.this, MainMenu.class);
				startActivity(i);

				// close this activity
				finish();
			}
		}, SPLASH_TIME_OUT);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * Retrieves all venues from the database and recalculates their average
	 * ratings
	 */
	private class VenueTotalRatingUpdater extends AsyncTask<Void, Void, Void> {

		Context ctx;

		private VenueTotalRatingUpdater(Context context) {
			super();
			this.ctx = context;
		}

		@Override
		protected Void doInBackground(Void... params) {

			LeglessDbAdapter dbAdapter = new LeglessDbAdapter(ctx);
			dbAdapter.open();
			SQLiteDatabase dbConnect = dbAdapter.getDbConnect();

			List<Venue> venues = Venue.listAll(dbConnect);

			for (Venue venue : venues) {
				venue.updateAverageRatings(dbConnect);
				venue.update(dbConnect);
			}

			Log.d("legless", "Rating update complete");

			return null;
		}

	}

}
