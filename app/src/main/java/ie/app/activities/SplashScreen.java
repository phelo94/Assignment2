package ie.app.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import ie.app.visitwaterford.R;

public class  SplashScreen extends AppCompatActivity {

    private int SLEEP_TIMER = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //SPLASHSCREEN Reference
        /*
        https://www.youtube.com/watch?v=w4FDTAHRaUY&fbclid=IwAR28E5fTDfQMLYgZynkugnNtmIWdyDw7EGybFZypUAYEU13iqUBmlZITcrU
        */
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash_screen);
        getSupportActionBar().hide();
        LogoLauncher logoLauncher = new LogoLauncher();
        logoLauncher.start();
    }

    private class LogoLauncher extends Thread{

        public void run(){

            try {
                sleep(1000 * SLEEP_TIMER);
            }catch(InterruptedException e){
                e.printStackTrace();

            }

            Intent intent = new Intent(SplashScreen.this, Reg.class);
            startActivity(intent);
            SplashScreen.this.finish();
        }

    }
}
