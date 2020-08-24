package com.learning.gametut;

import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "Create");
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getSupportActionBar().hide();
        FullScreencall();


        setContentView(new MainGamePanel(this));
    }

    public void FullScreencall() {
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    protected void onStart() {
        super.onStart();
        Log.d(TAG, "On start...");
    }

    protected void onResume() {
        super.onResume();
        Log.d(TAG, "Resume...");
    }

    protected void onPause() {
        super.onPause();
        Log.d(TAG, "Pause...");


    }

    protected void onStop() {
        super.onStop();
        Log.d(TAG, "Stopp...");
    }

    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "Destroy...");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "Restart...");
    }
}