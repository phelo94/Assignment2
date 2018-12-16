package ie.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import ie.app.visitwaterford.R;

public class Home extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;

    private TextView textViewUserEmail;
    private Button buttonLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() ==null){
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        FirebaseUser user = firebaseAuth.getCurrentUser();

        textViewUserEmail = (TextView) findViewById(R.id.textViewUserEmail);

        textViewUserEmail.setText("Welcome " +user.getEmail());

        buttonLogout = (Button) findViewById(R.id.buttonLogout);

        buttonLogout.setOnClickListener(this);

    }


    @Override
    public void onBackPressed(){

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.menuPlace : startActivity (new Intent(this, GoogleMapsActivity.class));
                break;
            case R.id.menuRatings : startActivity (new Intent(this, Ratings.class));
                break;
            case R.id.menuReport : startActivity (new Intent(this, Report.class));
                break;
            case R.id.menuDonate : startActivity (new Intent(this, Donate.class));
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

    @Override
    public void onClick(View view) {

        if(view == buttonLogout){
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

    }
}
