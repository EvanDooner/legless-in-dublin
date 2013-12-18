package dev.maynooth.mobile.leglessindublin;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

/**
 * Displays the Thank You splash screen after a user rates a venue.
 * 
 * @author Evan Dooner, 12262480
 * @version 2013-12-18-00
 */
public class ThankYouSplash extends Activity {

	private static final int SPLASH_TIME_OUT = 3000; // Time in milliseconds to
														// display screen
	private Context ctx;

	MediaPlayer splashSound;

	// onCreate method
	@Override
	@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
			setTheme(android.R.style.Theme_Holo_Light);
		}
		setContentView(R.layout.thank_you_splash);

		this.ctx = this;

		// splash screen sound
		splashSound = MediaPlayer
				.create(ThankYouSplash.this, R.raw.splashsound);
		splashSound.start();

		// Forward user to Main menu after delay
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				// This method will be executed once the timer is over
				// Start your app main activity
				Intent i = new Intent(ctx, MainMenu.class);
				startActivity(i);

				// close this activity
				finish();
			}
		}, SPLASH_TIME_OUT);
	} // ends onCreate

	// onPause method
	@Override
	protected void onPause() {
		super.onPause();
		splashSound.release(); // kill sound
		finish(); // kill splash screen
	}

} // ends Splash class
