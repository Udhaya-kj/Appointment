package com.corals.appointment.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.corals.appointment.Adapters.ServicesAdapter;
import com.corals.appointment.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import java.util.ArrayList;

public class SetupServiceActivity extends AppCompatActivity {
    Button button_continue;
    LinearLayout  linearLayout_add_resource;
    private ListView listView_services;
    private ArrayList<String> service_name_list, service_dur_list;
    private SharedPreferences sharedpreferences_services;
    ServicesAdapter servicesAdapter;
    private SharedPreferences sharedpreferences_service_data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_offer);

        Toolbar toolbar = findViewById(R.id.toolbar_service_offer);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_left_black);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        sharedpreferences_service_data = getSharedPreferences(AddServiceActivity.MyPREFERENCES_SERVICE_DATA, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor_oh = sharedpreferences_service_data.edit();
        editor_oh.clear();
        editor_oh.commit();

        service_name_list = new ArrayList<>();
        service_dur_list = new ArrayList<>();
        linearLayout_add_resource = findViewById(R.id.layout_add_resource);
        listView_services = findViewById(R.id.listview_services);
        button_continue = findViewById(R.id.button_offer_continue);

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
            servicesAdapter = new ServicesAdapter(SetupServiceActivity.this, service_name_list, service_dur_list);
            listView_services.setAdapter(servicesAdapter);

        }


      /*  linearLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(ResourceSetupActivity.this, MaterialDatePickerActivity.class);
                startActivity(in);
                finish();
            }
        });
        linearLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(ResourceSetupActivity.this, MaterialDatePickerActivity.class);
                startActivity(in);
                finish();
            }
        });

        linearLayout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(ResourceSetupActivity.this, MaterialDatePickerActivity.class);
                startActivity(in);
                finish();
            }
        });*/

        linearLayout_add_resource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(SetupServiceActivity.this, AddServiceActivity.class);
                in.putExtra("page_id", "03");
                startActivity(in);
                finish();
            }
        });


        button_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog pd = new ProgressDialog(SetupServiceActivity.this);
                pd.setMessage("Creating...");
                pd.show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pd.dismiss();
                        Intent in = new Intent(SetupServiceActivity.this, ApptSuccessActivity.class);
                        startActivity(in);
                        finish();
                    }
                }, 4000);

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

            Intent in = new Intent(SetupServiceActivity.this, StaffActivity.class);
            startActivity(in);
            finish();

    }
}
