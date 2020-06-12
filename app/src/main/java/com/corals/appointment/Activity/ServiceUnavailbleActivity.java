package com.corals.appointment.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.corals.appointment.Adapters.ServiceUnvailabilityServicesAdapter;
import com.corals.appointment.Adapters.ServicesAdapter_Calender;
import com.corals.appointment.Adapters.ServicesRecyclerviewAdapter;
import com.corals.appointment.Adapters.StaffLeaveAdapter;
import com.corals.appointment.Adapters.StaffListAdapter;
import com.corals.appointment.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class ServiceUnavailbleActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView textView_no_ser,textView_ser_staff;
    private ArrayList<String> service_name_list, service_dur_list;
    private SharedPreferences sharedpreferences_services;
    private ArrayList<String> staff_name_list, staff_mob_list;
    private SharedPreferences sharedpreferences_staffs;
    String task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_unavailble);

        Toolbar toolbar = findViewById(R.id.toolbar_service_unavail);
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

        if (getIntent().getExtras() != null) {
            task = getIntent().getStringExtra("task");
            if (task.equals("2")) {
                toolbar.setTitle("Staff Leave");
            }
        }
        textView_ser_staff = findViewById(R.id.text_ser_staff);
        textView_no_ser = findViewById(R.id.tv_no_services);
        recyclerView = findViewById(R.id.recyclerview_unavailability);
        LinearLayoutManager li = new LinearLayoutManager(ServiceUnavailbleActivity.this);
        recyclerView.setLayoutManager(li);

        service_name_list = new ArrayList<>();
        service_dur_list = new ArrayList<>();
        staff_name_list = new ArrayList<>();
        staff_mob_list = new ArrayList<>();

        sharedpreferences_services = getSharedPreferences(AddServiceActivity.MyPREFERENCES_SERVICES, Context.MODE_PRIVATE);
        sharedpreferences_staffs = getSharedPreferences(AddStaffActivity.MyPREFERENCES_STAFFS, Context.MODE_PRIVATE);

        if (task.equals("1")) {
            textView_ser_staff.setText("Select service");
            String nameList = sharedpreferences_services.getString(AddServiceActivity.SERVICE_NAME, "");
            String mobList = sharedpreferences_services.getString(AddServiceActivity.SERVICE_DURATION, "");
            if (!TextUtils.isEmpty(nameList) && !TextUtils.isEmpty(mobList)) {
                service_name_list = new Gson().fromJson(nameList, new TypeToken<ArrayList<String>>() {
                }.getType());
                service_dur_list = new Gson().fromJson(mobList, new TypeToken<ArrayList<String>>() {
                }.getType());
            }

            Log.d("listsize---->", "onCreate: " + service_name_list + "," + service_dur_list);
            if (service_name_list.size() != 0 && service_dur_list.size() != 0) {
                ServiceUnvailabilityServicesAdapter servicesRecyclerviewAdapter = new ServiceUnvailabilityServicesAdapter(ServiceUnavailbleActivity.this, service_name_list, service_dur_list);
                recyclerView.setAdapter(servicesRecyclerviewAdapter);

            } else {
                textView_no_ser.setText("No services created");
                textView_no_ser.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            }
        } else if (task.equals("2")) {
            textView_ser_staff.setText("Select staff");
            String nameList1 = sharedpreferences_staffs.getString(AddStaffActivity.NAME, "");
            String mobList1 = sharedpreferences_staffs.getString(AddStaffActivity.MOBILE, "");
            if (!TextUtils.isEmpty(nameList1) && !TextUtils.isEmpty(mobList1)) {
                staff_name_list = new Gson().fromJson(nameList1, new TypeToken<ArrayList<String>>() {
                }.getType());
                staff_mob_list = new Gson().fromJson(mobList1, new TypeToken<ArrayList<String>>() {
                }.getType());
            }

            if (staff_name_list.size() != 0) {
                StaffLeaveAdapter staffListAdapter = new StaffLeaveAdapter(ServiceUnavailbleActivity.this, staff_name_list);
                recyclerView.setAdapter(staffListAdapter);

            } else {
                textView_no_ser.setText("No staffs created");
                textView_no_ser.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            }
        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent i = new Intent(ServiceUnavailbleActivity.this, SettingsActivity.class);
        startActivity(i);
        finish();
    }
}
