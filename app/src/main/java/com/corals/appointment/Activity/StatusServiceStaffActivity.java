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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_service_staff);

        imageView_service = findViewById(R.id.image_service);
        imageView_staff = findViewById(R.id.image_staff);
        button_ok = findViewById(R.id.button_status_create);
        button_dont_ask_again = findViewById(R.id.button_status_dont_ask);

        sharedpreferences_ask_again = getSharedPreferences(MyPREFERENCES_ASK_AGAIN, Context.MODE_PRIVATE);

        if(getIntent().getExtras()!=null){
            String tl_ser=getIntent().getStringExtra("total_service");
            String tl_res=getIntent().getStringExtra("total_resource");

            if(Integer.parseInt(tl_ser)!=0){
                imageView_service.setColorFilter(Color.TRANSPARENT);
            }
            if(Integer.parseInt(tl_res)!=0){
                imageView_staff.setColorFilter(Color.TRANSPARENT);
            }

        }
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



    }
}
