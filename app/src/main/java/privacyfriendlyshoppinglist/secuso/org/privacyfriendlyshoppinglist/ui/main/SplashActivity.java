package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by yonjuni on 24.10.16.
 */

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* Create an Intent that will start the Menu-Activity. */
        Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
        SplashActivity.this.startActivity(mainIntent);
        SplashActivity.this.finish();


    }

}
