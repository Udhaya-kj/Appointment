package com.corals.appointment.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.corals.appointment.R;

public class ApptSuccessActivity extends AppCompatActivity {
    Button button_close;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_complete);

        button_close=findViewById(R.id.button_setup_close);
        button_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(ApptSuccessActivity.this, DashboardActivity.class);
                startActivity(in);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent in = new Intent(ApptSuccessActivity.this, DashboardActivity.class);
        startActivity(in);
        finish();
    }
}
