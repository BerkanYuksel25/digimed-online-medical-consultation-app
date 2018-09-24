//Main Activity File

package ses1b.group10.android_application;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 1750;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent home_intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(home_intent);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
