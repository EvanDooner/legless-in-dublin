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
import dev.maynooth.mobile.leglessindublin.datastore.VenueType;

public class VenueTypeAdapter extends ArrayAdapter<VenueType> {

	private Context mContext;
	private int layoutResourceId;
	private List<VenueType> venueTypes = new ArrayList<VenueType>();

	public VenueTypeAdapter(Context context, int resource,
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