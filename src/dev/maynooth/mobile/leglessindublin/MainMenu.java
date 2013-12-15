package dev.maynooth.mobile.leglessindublin;

import static dev.maynooth.mobile.leglessindublin.utils.StringUtilities.toTitleCase;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import dev.maynooth.mobile.leglessindublin.datastore.LeglessDbAdapter;
import dev.maynooth.mobile.leglessindublin.datastore.Location;
import dev.maynooth.mobile.leglessindublin.datastore.Venue;
import dev.maynooth.mobile.leglessindublin.datastore.VenueType;

public class MainMenu extends Activity {

	private class LocationAdapter extends ArrayAdapter<Location> {

		private Context mContext;
		private int layoutResourceId;
		private List<Location> locations = new ArrayList<Location>();

		public LocationAdapter(Context context, int resource,
				List<Location> locations) {
			super(context, resource, locations);
			this.mContext = context;
			this.layoutResourceId = resource;
			this.locations = locations;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.widget.ArrayAdapter#getDropDownView(int,
		 * android.view.View, android.view.ViewGroup)
		 */
		@Override
		public View getDropDownView(int position, View convertView,
				ViewGroup parent) {
			if (convertView == null) {
				// inflate the layout
				LayoutInflater inflater = ((Activity) mContext)
						.getLayoutInflater();
				convertView = inflater.inflate(
						R.layout.legless_spinner_dropdown, parent, false);
			}

			// location item based on the position
			Location currentLocation = locations.get(position);

			// get the TextView and then set the text (location name) and tag
			// (location
			// ID) values
			CheckedTextView textViewItem = (CheckedTextView) convertView
					.findViewById(R.id.spinnerDropdown);
			textViewItem.setText(currentLocation.getLocation());
			textViewItem.setTag(currentLocation.getRowId());

			return convertView;
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
			Location currentLocation = locations.get(position);

			// get the TextView and then set the text (item name) and tag (item
			// ID) values
			TextView textViewItem = (TextView) convertView
					.findViewById(R.id.legless_spinner_item);
			textViewItem.setText(currentLocation.getLocation());
			textViewItem.setTag(currentLocation.getRowId());

			return convertView;
		}

	}

	private class PopulateLocationSpinner extends
			AsyncTask<Void, Void, List<Location>> {

		private Context ctx;

		private PopulateLocationSpinner(Context context) {
			super();
			this.ctx = context;
		}

		@Override
		protected List<Location> doInBackground(Void... params) {
			LeglessDbAdapter dbAdapter = new LeglessDbAdapter(
					getApplicationContext());
			dbAdapter.open();

			List<Location> locationsUnformatted;
			try {
				locationsUnformatted = Location.fetchAll(dbAdapter
						.getDbConnect());
			} finally {
				dbAdapter.close();
			}

			List<Location> locationsFormatted = new ArrayList<Location>();
			for (Location option : locationsUnformatted) {
				option.setLocation(toTitleCase(option.getLocation()));
				locationsFormatted.add(option);
			}

			locations = locationsUnformatted;

			return locations;
		}

		@Override
		protected void onPostExecute(List<Location> result) {
			super.onPostExecute(result);

			setLocationSpinner(result);
		}

	}

	private class PopulateVenueTypeSpinner extends
			AsyncTask<Void, Void, List<VenueType>> {

		private Context ctx;

		private PopulateVenueTypeSpinner(Context context) {
			super();
			this.ctx = context;
		}

		@Override
		protected List<VenueType> doInBackground(Void... params) {
			LeglessDbAdapter dbAdapter = new LeglessDbAdapter(
					getApplicationContext());
			dbAdapter.open();
			SQLiteDatabase dbConnect = dbAdapter.getDbConnect();

			List<VenueType> venueTypesUnformatted;
			try {
				venueTypesUnformatted = VenueType.fetchAll(dbConnect);
			} finally {
				dbAdapter.close();
			}

			List<VenueType> venueTypeFormatted = new ArrayList<VenueType>();
			for (VenueType option : venueTypesUnformatted) {
				option.setVenueType(toTitleCase(option.getVenueType()));
				venueTypeFormatted.add(option);
			}

			venueTypes = venueTypeFormatted;

			return venueTypes;
		}

		@Override
		protected void onPostExecute(List<VenueType> result) {
			super.onPostExecute(result);

			setVenueTypeSpinner(result);
		}

	}

	private class VenueTypeArrayAdapter extends ArrayAdapter<VenueType> {

		private Context mContext;
		private int layoutResourceId;
		private List<VenueType> venueTypes = new ArrayList<VenueType>();

		public VenueTypeArrayAdapter(Context context, int resource,
				List<VenueType> venueTypes) {
			super(context, resource, venueTypes);
			this.mContext = context;
			this.layoutResourceId = resource;
			this.venueTypes = venueTypes;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.widget.ArrayAdapter#getDropDownView(int,
		 * android.view.View, android.view.ViewGroup)
		 */
		@Override
		public View getDropDownView(int position, View convertView,
				ViewGroup parent) {
			if (convertView == null) {
				// inflate the layout
				LayoutInflater inflater = ((Activity) mContext)
						.getLayoutInflater();
				convertView = inflater.inflate(
						R.layout.legless_spinner_dropdown, parent, false);
			}

			// object item based on the position
			VenueType currentVenueType = venueTypes.get(position);

			// get the TextView and then set the text (item name) and tag (item
			// ID) values
			CheckedTextView textViewItem = (CheckedTextView) convertView
					.findViewById(R.id.spinnerDropdown);
			textViewItem.setText(currentVenueType.getVenueType());
			textViewItem.setTag(currentVenueType.getRowId());

			return convertView;
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
			VenueType currentVenueType = venueTypes.get(position);

			// get the TextView and then set the text (item name) and tag (item
			// ID) values
			TextView textViewItem = (TextView) convertView
					.findViewById(R.id.legless_spinner_item);
			textViewItem.setText(currentVenueType.getVenueType());
			textViewItem.setTag(currentVenueType.getRowId());

			return convertView;
		}

	}

	public static final String SEARCH_VENUE_TYPE = "dev.maynooth.mobile.leglessindublin.SEARCH_VENUE_TYPE";

	public static final String SEARCH_LOCATION = "dev.maynooth.mobile.leglessindublin.SEARCH_LOCATION";

	private List<Location> locations;

	private List<VenueType> venueTypes;

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

	private class SearchCounter extends AsyncTask<String, Void, Boolean> {

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
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
				Toast.makeText(ctx, R.string.no_results_found, Toast.LENGTH_SHORT).show();
			}
		}

		private Context ctx;

		public SearchCounter(Context context) {
			this.ctx = context;
		}

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

	}

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
	 * Adds choices to Venue Type and Location spinners
	 */
	private void populateSpinners() {
		new PopulateLocationSpinner(this).execute();
		if (venueTypes != null) {
			setVenueTypeSpinner(venueTypes);
		} else {
			new PopulateVenueTypeSpinner(this).execute();
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
		VenueTypeArrayAdapter adapterVT = new VenueTypeArrayAdapter(this,
				R.layout.legless_spinner_style, venueTypes);
		adapterVT.setDropDownViewResource(R.layout.legless_spinner_dropdown);
		venueType.setAdapter(adapterVT);
	}

}
