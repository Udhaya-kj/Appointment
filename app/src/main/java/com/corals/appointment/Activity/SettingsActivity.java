package com.corals.appointment.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.corals.appointment.R;

public class SettingsActivity extends AppCompatActivity {

    LinearLayout layout_services,layout_staffs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        layout_services=findViewById(R.id.layout_settings_services);
        layout_staffs=findViewById(R.id.layout_settings_staffs);


        layout_services.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SettingsActivity.this, SetupServiceActivity_Bottom.class);
                i.putExtra("page_id", "3");
                startActivity(i);
                finish();
            }
        });

        layout_staffs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SettingsActivity.this, StaffActivity_Bottom.class);
                i.putExtra("page_id", "3");
                startActivity(i);
                finish();
            }
        });
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(SettingsActivity.this, AppointmentActivity.class);
        startActivity(i);
        finish();
    }
}
