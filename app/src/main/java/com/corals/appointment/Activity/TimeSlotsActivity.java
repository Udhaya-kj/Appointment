package com.corals.appointment.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.corals.appointment.Adapters.RecyclerAdapter_TimeSlots;
import com.corals.appointment.R;

import java.time.LocalTime;
import java.util.ArrayList;

public class TimeSlotsActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<String> arrayList_s_time, arrayList_e_time;
    TextView textView_appn_dt,textView_res;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_slots);

        Toolbar toolbar = findViewById(R.id.toolbar_biz_new_appt);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_left);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        recyclerView = findViewById(R.id.recyclerview_time_slots);
        textView_res = findViewById(R.id.text_resource);
        textView_appn_dt = findViewById(R.id.text_appn_dt);
        arrayList_s_time = new ArrayList<>();
        arrayList_e_time = new ArrayList<>();

        arrayList_s_time.add("8 AM  -  8:30 AM");
        arrayList_s_time.add("8:30 AM  -  9 AM");
        arrayList_s_time.add("9 AM  -  9:30 AM");
        arrayList_s_time.add("9:30 AM  -  10 AM");
        arrayList_s_time.add("10 AM  -  10:30 AM");
        arrayList_s_time.add("10:30 AM  -  11 AM");
        arrayList_s_time.add("11 AM  -  11:30 AM");
        arrayList_s_time.add("11:30 AM  -  12 PM");
        arrayList_s_time.add("12 PM  -  12:30 PM");
        arrayList_s_time.add("12:30 PM  -  13 PM");
        arrayList_s_time.add("13 PM  -  13:30 PM");
        arrayList_s_time.add("13:30 PM  -  14 PM");

        arrayList_e_time.add("0");
        arrayList_e_time.add("1");
        arrayList_e_time.add("0");
        arrayList_e_time.add("1");
        arrayList_e_time.add("2");
        arrayList_e_time.add("1");
        arrayList_e_time.add("0");
        arrayList_e_time.add("2");
        arrayList_e_time.add("0");
        arrayList_e_time.add("1");
        arrayList_e_time.add("0");
        arrayList_e_time.add("1");

        LinearLayoutManager li = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(li);
        RecyclerAdapter_TimeSlots recyclerAdapter_timeSlots = new RecyclerAdapter_TimeSlots(TimeSlotsActivity.this, arrayList_s_time,arrayList_e_time);
        recyclerView.setAdapter(recyclerAdapter_timeSlots);

        if (getIntent().getExtras() != null) {
            String res = getIntent().getStringExtra("service");
            String dt = getIntent().getStringExtra("date");
            Log.d("Appn_Date---->", "onCreate: " + dt+","+res);
            textView_appn_dt.setText(dt);
            textView_res.setText(res);

        }

        //printTimeSlots(LocalTime.parse("08:00"), LocalTime.parse("17:00"), 30);


    }

    @SuppressLint("NewApi")
    public void printTimeSlots(LocalTime startTime, LocalTime endTime, int slotSizeInMinutes) {
        for (LocalTime time = startTime, nextTime; time.isBefore(endTime); time = nextTime) {
            nextTime = time.plusMinutes(slotSizeInMinutes);
            if (nextTime.isAfter(endTime))
                break; // time slot crosses end time
            System.out.println("TimeSlots---->" + time + "-" + nextTime);
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(TimeSlotsActivity.this, CalendarServicesActivity.class);
        startActivity(i);
        finish();
    }
}
