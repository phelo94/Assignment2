package ie.app.activities;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import ie.app.visitwaterford.R;

public class LocationList extends ArrayAdapter<Location> {

    private Activity context;
    private List<Location> locationList;

    public LocationList(Activity context, List<Location> locationList){
        super(context, R.layout.listview_layout, locationList);
        this.context = context;
        this.locationList = locationList;

    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.listview_layout,null, true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewName);
        TextView textViewGenre = (TextView) listViewItem.findViewById(R.id.textViewGenre);

        Location location = locationList.get(position);

        textViewName.setText(location.getLocationName());
        textViewGenre.setText(location.getLocationGenre());

        return listViewItem;

    }
}
