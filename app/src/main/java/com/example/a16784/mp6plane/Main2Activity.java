package com.example.a16784.mp6plane;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Vector;

public class Main2Activity extends AppCompatActivity {

    private Button buttonStart;
    private Button buttonExit;
    private long time;

    static boolean showScore = false;
    static int score = 0;
    TextView scoreView;
    ImageView cs125;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        getSupportActionBar().hide();

        scoreView = findViewById(R.id.textViewScore);
        cs125 = findViewById(R.id.imageView);

        buttonStart = (Button) findViewById(R.id.btn_submit);
        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scoreView.setVisibility(View.INVISIBLE);
                Var.kills = 0;
                Var.myHp = 10;
                Var.list = new Vector<>();
                Intent intent = new Intent(Main2Activity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        buttonExit = (Button) findViewById(R.id.button);
        buttonExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        });

        cs125.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://cs125.cs.illinois.edu");
                startActivity(new Intent(Intent.ACTION_VIEW, uri));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (showScore) {
            scoreView.setVisibility(View.VISIBLE);
            scoreView.setText("You Died!\nScore: " + score);
            if (score >= 50) {
                cs125.setVisibility(View.VISIBLE);
            }
            showScore = false;
        }
    }
}
