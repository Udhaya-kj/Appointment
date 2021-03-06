package com.corals.appointment.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.corals.appointment.Client.model.Merchantinfo;
import com.corals.appointment.R;

public class SettingsActivity extends AppCompatActivity {

    LinearLayout layout_ser_unavail,layout_staff_leave,layout_merchant_info;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Toolbar toolbar = findViewById(R.id.toolbar_settings);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        layout_ser_unavail=findViewById(R.id.layout_ser_unavail);
        layout_staff_leave=findViewById(R.id.layout_staff_leave);
        layout_merchant_info=findViewById(R.id.layout_merchant_info);


        layout_ser_unavail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SettingsActivity.this, ServiceUnavailbleActivity.class);
                i.putExtra("task", "1");
                startActivity(i);
               // finish();
                overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);
            }
        });

        layout_staff_leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SettingsActivity.this, ServiceUnavailbleActivity.class);
                i.putExtra("task", "2");
                startActivity(i);
               // finish();
                overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);
            }
        });

        layout_merchant_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SettingsActivity.this, MerchantInfoActivity.class);
                startActivity(i);
               // finish();
                overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);
            }
        });
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(SettingsActivity.this, DashboardActivity.class);
        startActivity(i);
        finish();
        overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);
    }
}
