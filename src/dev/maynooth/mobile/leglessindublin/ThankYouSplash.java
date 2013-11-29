package dev.maynooth.mobile.leglessindublin;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;

public class ThankYouSplash extends Activity{

	//test uodate
	//variable for splash screen sound
	MediaPlayer splashSound;
	
	//onCreate method
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.thank_you_splash);
		
		//splash screen sound
		splashSound = MediaPlayer.create(ThankYouSplash.this, R.raw.splashsound);
		splashSound.start();
		
		//thread for splash activity timer
		Thread timer = new Thread(){
			//run method for timer thread
			public void run(){
				try{
					sleep(6000);
				} catch (InterruptedException e){
					e.printStackTrace();	//error handling
				} finally {
					//intent to open main activity
					Intent openStartingPoint = new Intent("com.example.leglessindublin.MAIN");
					//start activity
					startActivity(openStartingPoint);
				}
			}
		};	//ends timer thread
		
		//start splash screen timer thread
		timer.start();
	}	//ends onCreate

	//onPause method
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		splashSound.release();	//kill sound
		finish();	//kill splash screen
	}

}	//ends Splash class
