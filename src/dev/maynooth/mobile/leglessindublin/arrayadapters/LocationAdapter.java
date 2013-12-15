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

public class LocationAdapter extends ArrayAdapter<Location> {

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