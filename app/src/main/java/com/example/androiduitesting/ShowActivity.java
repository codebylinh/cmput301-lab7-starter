package com.example.androiduitesting;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class ShowActivity extends AppCompatActivity {

    public static final String EXTRA_CITY_NAME = "extra_city_name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        TextView cityName = findViewById(R.id.text_city_name);
        Button backBtn = findViewById(R.id.button_back);

        String name = getIntent().getStringExtra(EXTRA_CITY_NAME);
        if (name != null) {
            cityName.setText(name);
        } else {
            cityName.setText("(no city)");
        }

        backBtn.setOnClickListener(v -> finish());
    }
}
