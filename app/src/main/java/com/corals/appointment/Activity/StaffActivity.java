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
import android.widget.LinearLayout;
import android.widget.ListView;

import com.corals.appointment.Activity.AddStaffActivity;
import com.corals.appointment.Adapters.StaffListAdapter;
import com.corals.appointment.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class StaffActivity extends AppCompatActivity {

    private ListView listView_staffs;
    private ArrayList<String> staff_name_list, staff_mob_list;
    private SharedPreferences sharedpreferences_staffs;
    StaffListAdapter staffListAdapter;
    Button button_continue;
    LinearLayout linearLayout_add_staff;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff);

        sharedpreferences_staffs = getSharedPreferences(AddStaffActivity.MyPREFERENCES_STAFFS, Context.MODE_PRIVATE);
        Toolbar toolbar = findViewById(R.id.toolbar_staffs);
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
        listView_staffs=findViewById(R.id.listview_staffs);
        linearLayout_add_staff = findViewById(R.id.layout_add_staff);
        button_continue = findViewById(R.id.button_staff_continue);


        String nameList = sharedpreferences_staffs.getString(AddStaffActivity.NAME, "");
        String mobList = sharedpreferences_staffs.getString(AddStaffActivity.MOBILE, "");
        if(!TextUtils.isEmpty(nameList) && !TextUtils.isEmpty(mobList)) {
            staff_name_list = new Gson().fromJson(nameList, new TypeToken<ArrayList<String>>() {
            }.getType());
            staff_mob_list = new Gson().fromJson(mobList, new TypeToken<ArrayList<String>>() {
            }.getType());
        }

        if(staff_name_list.size()!=0){
            staffListAdapter=new StaffListAdapter(StaffActivity.this,staff_name_list,staff_mob_list);
            listView_staffs.setAdapter(staffListAdapter);
        }

        linearLayout_add_staff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(StaffActivity.this, AddStaffActivity.class);
                in.putExtra("page_id", "03");
                startActivity(in);
                finish();
                overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);
            }
        });


        button_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(StaffActivity.this, SetupServiceActivity.class);
                startActivity(in);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

/*
            Intent in = new Intent(StaffActivity.this, RegisterActivity.class);
            startActivity(in);
            finish();*/


    }
}
