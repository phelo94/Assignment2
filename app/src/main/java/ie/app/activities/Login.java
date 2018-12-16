package ie.app.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import ie.app.visitwaterford.R;

public class Login extends AppCompatActivity {





    private EditText Name;
    private EditText Password;
    private TextView Info;
    private Button Login;
    private int counter = 5;
    private Button userRegistration;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_old);
        getSupportActionBar().hide();





        Name = (EditText) findViewById(R.id.loginName);
        Password = (EditText) findViewById(R.id.loginPassword);
        Info = (TextView) findViewById(R.id.attemptsSubTitle);
        Login = (Button) findViewById(R.id.loginButton);
        userRegistration = (Button) findViewById(R.id.returnToLoginButton);

        Info.setText("Number of Attempts Remaining: 5 ");


        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Validate(Name.getText().toString(), Password.getText().toString());
            }
        });

        //userRegistration.setOnClickListener(new View.OnClickListener() {
            //@Override
            //public void onClick(View v) {
            //    startActivity(new Intent(Login.this,  Registration.class));
          //  }
        //});

    }


    private void Validate(String UserName, String UserPassword) {

        if ((UserName.equals("admin")) && (UserPassword.equals("1234"))) {
            Intent Intent = new Intent(Login.this, Home.class);
            startActivity(Intent);

        } else {

            counter--;

            Info.setText("Number of Attempts Remaining: " + String.valueOf(counter));

            if (counter == 0) {

                Login.setEnabled(false);
            }
        }
    }
}