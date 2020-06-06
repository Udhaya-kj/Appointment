package com.corals.appointment.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.corals.appointment.Adapters.SelectSerProvAdapter;
import com.corals.appointment.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class SelectServiceProvidersActivity extends AppCompatActivity {

    private ListView listView_staffs;
    private ArrayList<String> staff_name_list, staff_mob_list;
    private SharedPreferences sharedpreferences_staffs;
    SelectSerProvAdapter staffListAdapter;
    Button button_continue;
    public static String providers="";
    public String pageId = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_service_providers);

        providers="";
        sharedpreferences_staffs = getSharedPreferences(AddStaffActivity.MyPREFERENCES_STAFFS, Context.MODE_PRIVATE);
        Toolbar toolbar = findViewById(R.id.toolbar_select_ser_provd);
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

        staff_name_list = new ArrayList<>();
        staff_mob_list = new ArrayList<>();
        listView_staffs=findViewById(R.id.listview_select_ser_providers);
        button_continue = findViewById(R.id.button_providers_continue);

        String nameList = sharedpreferences_staffs.getString(AddStaffActivity.NAME, "");
        String mobList = sharedpreferences_staffs.getString(AddStaffActivity.MOBILE, "");
        if(!TextUtils.isEmpty(nameList) && !TextUtils.isEmpty(mobList)) {
            staff_name_list = new Gson().fromJson(nameList, new TypeToken<ArrayList<String>>() {
            }.getType());
            staff_mob_list = new Gson().fromJson(mobList, new TypeToken<ArrayList<String>>() {
            }.getType());
        }

        if(staff_name_list.size()!=0){
            staffListAdapter=new SelectSerProvAdapter(SelectServiceProvidersActivity.this,staff_name_list);
            listView_staffs.setAdapter(staffListAdapter);

        }

        if (getIntent().getExtras() != null) {
            pageId = getIntent().getStringExtra("page_id");
        }


        button_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // Toast.makeText(SelectServiceProvidersActivity.this, ""+providers, Toast.LENGTH_SHORT).show();
                    Intent in = new Intent(SelectServiceProvidersActivity.this, AddServiceActivity.class);
                    in.putExtra("providers", providers);
                    in.putExtra("page_id", pageId);
                    startActivity(in);
                    finish();
                 //onBackPressed();

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent in = new Intent(SelectServiceProvidersActivity.this, AddServiceActivity.class);
        in.putExtra("page_id", pageId);
        startActivity(in);
        finish();
    }

}
