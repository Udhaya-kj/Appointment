package com.corals.appointment.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.corals.appointment.Adapters.ServicesAdapter_Calender;
import com.corals.appointment.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class CalendarServicesActivity extends AppCompatActivity {

    ListView recyclerView_services;
    TextView textView_no_ser,textView_appt_dt;
    private ArrayList<String> service_name_list, service_dur_list;
    private SharedPreferences sharedpreferences_services;
    public static String cal_date = "", pageId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_services);

        Toolbar toolbar = findViewById(R.id.toolbar_select_ser);
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


        textView_appt_dt = findViewById(R.id.text_appn_dt);
        textView_no_ser = findViewById(R.id.tv_no_services);
        recyclerView_services = findViewById(R.id.recyclerview_services);

        if (getIntent().getExtras() != null) {
            cal_date = getIntent().getStringExtra("date");
            pageId = getIntent().getStringExtra("page_id");
            textView_appt_dt.setText(cal_date);
        }

        service_name_list = new ArrayList<>();
        service_dur_list = new ArrayList<>();

        sharedpreferences_services = getSharedPreferences(AddServiceActivity.MyPREFERENCES_SERVICES, Context.MODE_PRIVATE);
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
          /*  servicesAdapter = new ServicesAdapter_Calender(MaterialDatePickerActivity.this, service_name_list, service_dur_list);
            listView_services.setAdapter(servicesAdapter);*/
            textView_no_ser.setVisibility(View.GONE);
            recyclerView_services.setVisibility(View.VISIBLE);

            ServicesAdapter_Calender servicesAdapter_calender = new ServicesAdapter_Calender(CalendarServicesActivity.this, cal_date, service_name_list, service_dur_list);
            recyclerView_services.setAdapter(servicesAdapter_calender);

        } else {
            textView_no_ser.setVisibility(View.VISIBLE);
            recyclerView_services.setVisibility(View.GONE);
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (!TextUtils.isEmpty(pageId) && pageId.equals("1")) {
            Intent i = new Intent(CalendarServicesActivity.this, AppointmentActivity.class);
            startActivity(i);
            finish();
        } else if (!TextUtils.isEmpty(pageId) && pageId.equals("2")) {
            Intent i = new Intent(CalendarServicesActivity.this, CalendarViewActivity.class);
            startActivity(i);
            finish();
        }
    }
}
