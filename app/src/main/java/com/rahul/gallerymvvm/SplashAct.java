package com.rahul.gallerymvvm;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

public class SplashAct extends AppCompatActivity {

    public int checkVal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        permission_check();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                nextAct();
            }
        }, 5000);

    }

    private void nextAct() {
        if (checkVal == PackageManager.PERMISSION_GRANTED) {
            startActivity(new Intent(SplashAct.this, DashboardAct.class));
            finish();
        } else {
            startActivity(new Intent(SplashAct.this, Permission_Act.class));
            finish();
        }

    }

    private void permission_check() {
        String[] requiredPermission = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        for (int i = 0; i < requiredPermission.length; i++) {
            checkVal = checkCallingOrSelfPermission(requiredPermission[i]);
        }

    }
}