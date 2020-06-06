package com.corals.appointment.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.corals.appointment.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    EditText editText_id,editText_password;
    Button button_login;
    TextView textView;

    private SharedPreferences sharedpreferences_services;
    private SharedPreferences sharedpreferences_staffs;
    private SharedPreferences sharedpreferences_ask_again;
    private ArrayList<String> service_name_list, service_dur_list;
    private ArrayList<String> staff_name_list, staff_mob_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editText_id=findViewById(R.id.edit_userid);
        editText_password=findViewById(R.id.edit_password);
        button_login=findViewById(R.id.button_login);
        textView=findViewById(R.id.tv_signup);

        service_name_list = new ArrayList<>();
        service_dur_list = new ArrayList<>();
        staff_name_list = new ArrayList<>();
        staff_mob_list = new ArrayList<>();

        sharedpreferences_ask_again = getSharedPreferences(StatusServiceStaffActivity.MyPREFERENCES_ASK_AGAIN, Context.MODE_PRIVATE);
        sharedpreferences_services = getSharedPreferences(AddServiceActivity.MyPREFERENCES_SERVICES, Context.MODE_PRIVATE);
        sharedpreferences_staffs = getSharedPreferences(AddStaffActivity.MyPREFERENCES_STAFFS, Context.MODE_PRIVATE);

       /* SharedPreferences.Editor editor = sharedpreferences_ask_again.edit();
        editor.putString(StatusServiceStaffActivity.VALUE, "0");
        editor.commit();*/


        String nameList = sharedpreferences_services.getString(AddServiceActivity.SERVICE_NAME, "");
        String mobList = sharedpreferences_services.getString(AddServiceActivity.SERVICE_DURATION, "");
        if (!TextUtils.isEmpty(nameList) && !TextUtils.isEmpty(mobList)) {
            service_name_list = new Gson().fromJson(nameList, new TypeToken<ArrayList<String>>() {
            }.getType());
            service_dur_list = new Gson().fromJson(mobList, new TypeToken<ArrayList<String>>() {
            }.getType());
        }

        String nameList1 = sharedpreferences_staffs.getString(AddStaffActivity.NAME, "");
        String mobList1 = sharedpreferences_staffs.getString(AddStaffActivity.MOBILE, "");
        if(!TextUtils.isEmpty(nameList1) && !TextUtils.isEmpty(mobList1)) {
            staff_name_list = new Gson().fromJson(nameList1, new TypeToken<ArrayList<String>>() {
            }.getType());
            staff_mob_list = new Gson().fromJson(mobList1, new TypeToken<ArrayList<String>>() {
            }.getType());
        }


        Log.d("listsize---->", "onCreate: " + service_name_list.size() + "," + service_dur_list.size()+","+ staff_name_list.size() + "," + staff_mob_list.size());

        textView.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + "Sign up here" + "</u>  </font>"));

    /*    textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
                finish();
            }
        });*/

        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String id=editText_id.getText().toString().trim();
                String pswd=editText_password.getText().toString().trim();
                 String value = sharedpreferences_ask_again.getString(StatusServiceStaffActivity.VALUE, "");
               // Toast.makeText(LoginActivity.this, "Value :"+value, Toast.LENGTH_SHORT).show();
                if(TextUtils.isEmpty(value)) {
                    if (service_name_list.size() == 0 || staff_name_list.size() == 0) {
                        startActivity(new Intent(LoginActivity.this, StatusServiceStaffActivity.class));
                        finish();
                    } else {
                        startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
                        finish();
                    }
                }
                else {
                    startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
                    finish();
                }
            }
        });
    }
}
