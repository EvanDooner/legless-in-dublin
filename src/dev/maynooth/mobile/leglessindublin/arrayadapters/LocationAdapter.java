package dev.maynooth.mobile.leglessindublin.arrayadapters;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;
import dev.maynooth.mobile.leglessindublin.R;
import dev.maynooth.mobile.leglessindublin.datastore.Location;

/**
 * Custom array adapter to fill spinners with locations
 * 
 * @author Evan Dooner, 12262480
 * @version 2013-12-18-00
 */
public class LocationAdapter extends ArrayAdapter<Location> {

	private Context mContext;
	private int layoutResourceId;
	private List<Location> locations = new ArrayList<Location>();

	/**
	 * Constructs a new LocationAdapter in the specified context
	 * 
	 * @param context
	 *            - the activity context
	 * @param resource
	 *            an R.layout resource - the spinner layout to use
	 * @param locations
	 *            all the locations to be inserted into the spinner
	 */
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
	 * @see android.widget.ArrayAdapter#getDropDownView(int, android.view.View,
	 * android.view.ViewGroup)
	 */
	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			// inflate the layout
			LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			convertView = inflater.inflate(R.layout.legless_spinner_dropdown,
					parent, false);
		}

		// location item based on the position
		Location currentLocation = locations.get(position);

		// Get the dropdown view and populate it with the location values
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
			LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			convertView = inflater.inflate(layoutResourceId, parent, false);
		}

		// location item based on the position
		Location currentLocation = locations.get(position);

		// Get the spinner main display and populate it
		TextView textViewItem = (TextView) convertView
				.findViewById(R.id.legless_spinner_item);
		textViewItem.setText(currentLocation.getLocation());
		textViewItem.setTag(currentLocation.getRowId());

		return convertView;
	}

}