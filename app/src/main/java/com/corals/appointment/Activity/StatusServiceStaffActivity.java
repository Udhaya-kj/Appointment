package com.corals.appointment.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;

import com.corals.appointment.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class StatusServiceStaffActivity extends AppCompatActivity {

    Button button_ok, button_dont_ask_again;
    public static final String MyPREFERENCES_ASK_AGAIN = "MyPREFERENCES_ASK_AGAIN";
    public static final String VALUE = "VALUE";
    private SharedPreferences sharedpreferences_ask_again;
    ImageView imageView_service, imageView_staff;
    private SharedPreferences sharedpreferences_services, sharedpreferences_staffs;
    private ArrayList<String> service_name_list, service_dur_list;
    private ArrayList<String> staff_name_list, staff_mob_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_service_staff);

        imageView_service = findViewById(R.id.image_service);
        imageView_staff = findViewById(R.id.image_staff);
        button_ok = findViewById(R.id.button_status_create);
        button_dont_ask_again = findViewById(R.id.button_status_dont_ask);

        sharedpreferences_ask_again = getSharedPreferences(MyPREFERENCES_ASK_AGAIN, Context.MODE_PRIVATE);
        sharedpreferences_services = getSharedPreferences(AddServiceAvailTimeActivity.MyPREFERENCES_SERVICES, Context.MODE_PRIVATE);
        sharedpreferences_staffs = getSharedPreferences(AddStaffActivity.MyPREFERENCES_STAFFS, Context.MODE_PRIVATE);

        service_name_list = new ArrayList<>();
        service_dur_list = new ArrayList<>();
        staff_name_list = new ArrayList<>();
        staff_mob_list = new ArrayList<>();

        button_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(StatusServiceStaffActivity.this, DashboardActivity.class);
                startActivity(i);
                finish();

            }
        });

        button_dont_ask_again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedpreferences_ask_again.edit();
                editor.putString(VALUE, "1");
                editor.commit();

                Intent i = new Intent(StatusServiceStaffActivity.this, DashboardActivity.class);
                startActivity(i);
                finish();
            }
        });


        String nameList = sharedpreferences_services.getString(AddServiceAvailTimeActivity.SERVICE_NAME, "");
        String mobList = sharedpreferences_services.getString(AddServiceAvailTimeActivity.SERVICE_DURATION, "");
        if (!TextUtils.isEmpty(nameList) && !TextUtils.isEmpty(mobList)) {
            service_name_list = new Gson().fromJson(nameList, new TypeToken<ArrayList<String>>() {
            }.getType());
            service_dur_list = new Gson().fromJson(mobList, new TypeToken<ArrayList<String>>() {
            }.getType());
        }
        Log.d("service_name_list--->", "onCreate: "+service_name_list.size());
        if (service_name_list != null && !service_name_list.isEmpty()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                imageView_service.setColorFilter(Color.TRANSPARENT);
            }

            Log.d("service_name_list", "onCreate: "+service_name_list);
        }
        String nameList_staff = sharedpreferences_staffs.getString(AddStaffActivity.NAME, "");
        String mobList_staff = sharedpreferences_staffs.getString(AddStaffActivity.MOBILE, "");
        if (!TextUtils.isEmpty(nameList) && !TextUtils.isEmpty(mobList)) {
            staff_name_list = new Gson().fromJson(nameList_staff, new TypeToken<ArrayList<String>>() {
            }.getType());
            staff_mob_list = new Gson().fromJson(mobList_staff, new TypeToken<ArrayList<String>>() {
            }.getType());
        }

      //  Log.d("staff_name_list--->", "onCreate: "+staff_name_list.size());
        if (staff_name_list != null && !staff_name_list.isEmpty()) {
            imageView_staff.setColorFilter(Color.TRANSPARENT);
            Log.d("staff_name_list", "onCreate: "+staff_name_list);
        }


    }
}
