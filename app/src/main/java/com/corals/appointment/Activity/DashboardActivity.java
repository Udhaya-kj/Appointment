package com.corals.appointment.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.corals.appointment.R;

public class DashboardActivity extends AppCompatActivity {

    private TextView textView_appointments,textView_customers,textView_staff,textView_services,textView_settings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        textView_appointments=findViewById(R.id.text_appointments);
        textView_customers=findViewById(R.id.text_customers);
        textView_staff=findViewById(R.id.text_staff);
        textView_services=findViewById(R.id.text_services);
        textView_settings=findViewById(R.id.text_settings);

        textView_appointments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DashboardActivity.this, AppointmentActivity.class);
                startActivity(i);
                finish();
            }
        });

        textView_customers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DashboardActivity.this, CustomerActivity_Bottom.class);
                startActivity(i);
                finish();
            }
        });

        textView_staff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DashboardActivity.this, StaffActivity_Bottom.class);
                startActivity(i);
                finish();
            }
        });

        textView_services.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DashboardActivity.this, SetupServiceActivity_Bottom.class);
                startActivity(i);
                finish();
            }
        });

        textView_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DashboardActivity.this, SettingsActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}
