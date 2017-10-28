package com.example.freecats.numberarcview.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.freecats.numberarcview.R;
import com.example.freecats.numberarcview.view.NumberArcView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Button btnRandom;
    private NumberArcView navMoney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        btnRandom = findViewById(R.id.btn);
        navMoney = findViewById(R.id.nav);

        btnRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navMoney.setNumber(new Random().nextInt(200));
            }
        });
    }
}
