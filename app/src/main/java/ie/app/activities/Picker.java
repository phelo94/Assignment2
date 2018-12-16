package ie.app.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import ie.app.visitwaterford.R;

public class Picker extends AppCompatActivity {

    public static final String LOCATION_NAME = "locationname";
    public static final String LOCATION_ID = "locationid";


    EditText editTextName;
    Spinner spinnerGenres;
    Button buttonAddPlace;

    DatabaseReference databaseLocation;

    ListView listViewLocations;

    List<Location> locationList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picker);

        databaseLocation = FirebaseDatabase.getInstance().getReference("locations");

        editTextName = (EditText) findViewById(R.id.editTextName);
        buttonAddPlace = (Button) findViewById(R.id.buttonAddPlace);
        spinnerGenres = (Spinner) findViewById(R.id.spinnerGenres);

        listViewLocations = (ListView) findViewById(R.id.listViewLocations);

        locationList = new ArrayList<>();


        buttonAddPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addLocation();
            }
        });

        listViewLocations.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                Location location = locationList.get(i);

                Intent intent =  new Intent(getApplicationContext(), AddTrackActivity.class);

                intent.putExtra(LOCATION_ID, location.getLocationId());
                intent.putExtra(LOCATION_NAME, location.getLocationName());

                startActivity(intent);

            }
        });

       listViewLocations.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
           @Override
           public boolean onItemLongClick(AdapterView<?> parent, View view, int i, long l) {


               Location location = locationList.get(i);

               showUpdateDialog(location.getLocationId(), location.getLocationName());
               return false;
           }
       });

        }


    @Override
    protected void onStart(){
        super.onStart();

        databaseLocation.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                locationList.clear();

                for(DataSnapshot locationSnapshot  :  dataSnapshot.getChildren()){
                    Location location = locationSnapshot.getValue(Location.class);

                    locationList.add(location);
                }

                LocationList adapter = new LocationList(Picker.this, locationList);
                listViewLocations.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void showUpdateDialog(final String locationId, String locationName){

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();

        final View dialogView = inflater.inflate(R.layout.update_dialog, null);

        dialogBuilder.setView(dialogView);


        final EditText editTextName = (EditText) dialogView.findViewById(R.id.editTextName);
        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.buttonUpdate);
        final Spinner spinnerGenres = (Spinner) dialogView.findViewById(R.id.spinnerGenres);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.buttonDelete);

        dialogBuilder.setTitle("Updating Location" + locationName);

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();


        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = editTextName.getText().toString().trim();
                String genre = spinnerGenres.getSelectedItem().toString();

                if(TextUtils.isEmpty(name)){
                    editTextName.setError("Name Required");
                    return;
                }

                    updateLocation(locationId, name, genre);

                    alertDialog.dismiss();
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteLocation(locationId);
            }
        });


    }

    private void deleteLocation(String locationId) {
        DatabaseReference drLocation = FirebaseDatabase.getInstance().getReference("locations").child(locationId);
        DatabaseReference drTracks = FirebaseDatabase.getInstance().getReference("tracks").child(locationId);

        drLocation.removeValue();
        drTracks.removeValue();

        Toast.makeText(this, "Location is deleted", Toast.LENGTH_LONG).show();

    }

    private boolean updateLocation(String id, String name, String genre){

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Location").child(id);

        Location location = new Location(id, name, genre);

        databaseReference.setValue(location);

        Toast.makeText(this,"Location Updated Successfully", Toast.LENGTH_LONG).show();

        return true;
    }


    private void addLocation() {
        String name = editTextName.getText().toString().trim();
        String genre = spinnerGenres.getSelectedItem().toString();

        if (!TextUtils.isEmpty(name)) {


            String id = databaseLocation.push().getKey();

            Location location = new Location(id, name, genre);

            databaseLocation.child(id).setValue(location);

            editTextName.setText("");

            Toast.makeText(this, "Comment added", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Please enter a comment", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_subplaces, menu);
        return true;

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.menuHome : startActivity (new Intent(this, Home.class));
                break;
            case R.id.menuRatings : startActivity (new Intent(this, Ratings.class));
                break;
            case R.id.menuDonate : startActivity (new Intent(this, Donate.class));
                break;
            case R.id.menuReport : startActivity (new Intent(this, Report.class));
                break;
            case R.id.menuPlace : startActivity (new Intent(this, GoogleMapsActivity.class));
                break;
            case R.id.menuCamera : startActivity (new Intent(this, Camera.class));
                break;
            case R.id.menuJournal : startActivity (new Intent(this, Journal.class));
                break;
            case R.id.menuLogout :
                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity (new Intent(this, Reg.class));

                break;

        }
        return super.onOptionsItemSelected(item);
    }
}