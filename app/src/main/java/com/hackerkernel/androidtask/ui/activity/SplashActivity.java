package com.hackerkernel.androidtask.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.hackerkernel.androidtask.R;
import com.hackerkernel.androidtask.session.SessionManager;

public class SplashActivity extends AppCompatActivity {

    private String TAG = getClass().getSimpleName();

    private SessionManager sessionManager;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        sessionManager = SessionManager.getInstance(this);
        Log.e(TAG, "onCreate: checking");
        if(!sessionManager.isLoggedIn()){
            intent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }else{
            intent = new Intent(SplashActivity.this, NavigationActivity.class);
            startActivity(intent);
            finish();
        }
    }
}