package com.example.a16784.mp6plane;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Toast;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends AppCompatActivity {
    private long time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        setContentView(new game(this));
    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            long t = System.currentTimeMillis();
            if (t - time <= 1000) {
                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                startActivity(intent);
                android.os.Process.killProcess(android.os.Process.myPid());
            } else {
                time = t;
                Toast.makeText(getApplicationContext(), "Push again to exit to main manu", Toast.LENGTH_SHORT).show();
            }
            return true;
        }
        return false;
    }
}
