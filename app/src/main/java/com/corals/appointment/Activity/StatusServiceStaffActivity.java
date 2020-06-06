package com.corals.appointment.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.corals.appointment.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class StatusServiceStaffActivity extends AppCompatActivity {

    Button button_create,button_dont_ask_again;
    public static final String MyPREFERENCES_ASK_AGAIN = "MyPREFERENCES_ASK_AGAIN";
    public static final String VALUE = "VALUE";
    private SharedPreferences sharedpreferences_ask_again;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_service_staff);

        button_create=findViewById(R.id.button_status_create);
        button_dont_ask_again=findViewById(R.id.button_status_dont_ask);

        sharedpreferences_ask_again = getSharedPreferences(MyPREFERENCES_ASK_AGAIN, Context.MODE_PRIVATE);


        button_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(StatusServiceStaffActivity.this, DashboardActivity.class);
                startActivity(i);
                finish();

          /*      Intent i = new Intent(StatusServiceStaffActivity.this, SetupServiceActivity_Bottom.class);
                startActivity(i);
                finish();*/
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
