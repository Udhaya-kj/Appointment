package com.corals.appointment.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.corals.appointment.R;

public class DashboardActivity extends AppCompatActivity {

    private CardView textView_appointments,textView_customers,textView_staff,textView_services;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Toolbar toolbar = findViewById(R.id.toolbar_biz_appt_date);
        setSupportActionBar(toolbar);
        textView_appointments=findViewById(R.id.text_appointments);
        textView_customers=findViewById(R.id.text_customers);
        textView_staff=findViewById(R.id.text_staff);
        textView_services=findViewById(R.id.text_services);

        textView_appointments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DashboardActivity.this, AppointmentActivity.class);
                startActivity(i);
                finish();
                overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);
            }
        });

        textView_customers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DashboardActivity.this, CustomerActivity_Bottom.class);
                startActivity(i);
                finish();
                overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);
            }
        });

        textView_staff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DashboardActivity.this, SetupStaffActivity_Bottom.class);
                startActivity(i);
                finish();
                overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);
            }
        });

        textView_services.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DashboardActivity.this, SetupServiceActivity_Bottom.class);
                startActivity(i);
                finish();
                overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);
            }
        });

      /*  textView_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DashboardActivity.this, SettingsActivity.class);
                startActivity(i);
                finish();
                overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);
            }
        });*/
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.dashboard_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            Intent i = new Intent(DashboardActivity.this, SettingsActivity.class);
            startActivity(i);
            overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);
        }
        return super.onOptionsItemSelected(item);
    }

}
