package com.corals.appointment.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.corals.appointment.Adapters.CustomersAdapter_MakeAppt;
import com.corals.appointment.Model.CustomersModel;
import com.corals.appointment.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import java.util.ArrayList;

public class CustomersMakeApptActivity extends AppCompatActivity {

    private ListView listView_customers;
    private ArrayList<String> cus_name_list, cus_mob_list;
    private SharedPreferences sharedpreferences_customers;
    CustomersAdapter_MakeAppt customersAdapter_makeAppt;
    LinearLayout linearLayout_add_customer;
    EditText editText_search;
    String pageId;
    private ArrayList<CustomersModel> mCustomersArrayList = new ArrayList<CustomersModel>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customers);

        Toolbar toolbar = findViewById(R.id.toolbar_customers);
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
        sharedpreferences_customers = getSharedPreferences(CreateCustomerActivity.MyPREFERENCES_CUSTOMERS, Context.MODE_PRIVATE);
        linearLayout_add_customer = findViewById(R.id.layout_new_cust);
        cus_name_list = new ArrayList<>();
        cus_mob_list = new ArrayList<>();
        listView_customers = findViewById(R.id.listview_customers);
        editText_search = findViewById(R.id.et_search_customer);

        if (getIntent().getExtras() != null) {
            pageId = getIntent().getStringExtra("page_id");
        }
        linearLayout_add_customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CustomersMakeApptActivity.this, CreateCustomerActivity.class);
                i.putExtra("page_id", "02");
                startActivity(i);
                finish();
                overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);
            }
        });


        String nameList = sharedpreferences_customers.getString(CreateCustomerActivity.CUSTOMER_NAME, "");
        String mobList = sharedpreferences_customers.getString(CreateCustomerActivity.CUSTOMER_MOBILE, "");
        if (!TextUtils.isEmpty(nameList) && !TextUtils.isEmpty(mobList)) {
            cus_name_list = new Gson().fromJson(nameList, new TypeToken<ArrayList<String>>() {
            }.getType());
            cus_mob_list = new Gson().fromJson(mobList, new TypeToken<ArrayList<String>>() {
            }.getType());
        }

        for (int y = 0; y < cus_name_list.size(); y++) {
            mCustomersArrayList.add(new CustomersModel(cus_name_list.get(y), cus_mob_list.get(y), ""));
        }

        Log.d("listsize---->", "onCreate: " + cus_name_list + "," + cus_mob_list);
        if (cus_name_list.size() != 0 && cus_mob_list.size() != 0) {
            customersAdapter_makeAppt = new CustomersAdapter_MakeAppt(CustomersMakeApptActivity.this, mCustomersArrayList);
            listView_customers.setAdapter(customersAdapter_makeAppt);

        }

        editText_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                customersAdapter_makeAppt.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (!TextUtils.isEmpty(pageId) && pageId.equals("1")) {
            Intent i = new Intent(CustomersMakeApptActivity.this, TimeSlotsActivity.class);
            startActivity(i);
            finish();
            overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);
        } else if (!TextUtils.isEmpty(pageId) && pageId.equals("2")) {
            Intent i = new Intent(CustomersMakeApptActivity.this, ViewApptServiceActivity.class);
            startActivity(i);
            finish();
            overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);
        }
    }

}
