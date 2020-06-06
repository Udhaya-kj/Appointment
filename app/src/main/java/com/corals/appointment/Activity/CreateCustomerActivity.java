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
import android.widget.EditText;

import com.corals.appointment.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class CreateCustomerActivity extends AppCompatActivity {
    EditText editText_cus_name, editText_cus_mob;
    Button button_continue;
    private SharedPreferences sharedpreferences_customers;
    public static final String MyPREFERENCES_CUSTOMERS = "MyPrefs_Customers";
    public static final String CUSTOMER_NAME = "CUSTOMER_NAME";
    public static final String CUSTOMER_MOBILE = "CUSTOMER_MOBILE";
    private ArrayList<String> cus_name_list, cus_mob_list;
    public String pageId = "", position = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_customer);

        sharedpreferences_customers = getSharedPreferences(MyPREFERENCES_CUSTOMERS, Context.MODE_PRIVATE);
        Toolbar toolbar = findViewById(R.id.toolbar_create_customer);
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

        cus_name_list = new ArrayList<>();
        cus_mob_list = new ArrayList<>();
        editText_cus_name = findViewById(R.id.et_cus_name);
        editText_cus_mob = findViewById(R.id.et_cus_mob);
        button_continue = findViewById(R.id.button_create_customer);

        if (getIntent().getExtras() != null) {
            pageId = getIntent().getStringExtra("page_id");
            position = getIntent().getStringExtra("position");
            String cus_name = getIntent().getStringExtra("cus_name");
            String cus_mob = getIntent().getStringExtra("cus_mob");
            if (pageId.equals("22")) {
                toolbar.setTitle("Edit Customer");
                button_continue.setText("UPDATE");
            }
            editText_cus_name.setText(cus_name);
            editText_cus_mob.setText(cus_mob);
        }

        button_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           /*     String name=editText_biz_name.getText().toString();
                String mob=editText_biz_mob.getText().toString();
                Intent in = new Intent(CreateCustomerActivity.this, ApptConfirmActivity.class);
                in.putExtra("cus_name",name);
                in.putExtra("cus_mob",mob);
                startActivity(in);
                finish();*/


                String name = editText_cus_name.getText().toString();
                String mob = editText_cus_mob.getText().toString();
                if (name.length() > 0) {
                    if (mob.length() > 0) {
                        if (pageId.equals("22")) {
                            String nameList = sharedpreferences_customers.getString(CUSTOMER_NAME, "");
                            String mobList = sharedpreferences_customers.getString(CUSTOMER_MOBILE, "");
                            if (!TextUtils.isEmpty(nameList) && !TextUtils.isEmpty(mobList)) {
                                cus_name_list = new Gson().fromJson(nameList, new TypeToken<ArrayList<String>>() {
                                }.getType());
                                cus_mob_list = new Gson().fromJson(mobList, new TypeToken<ArrayList<String>>() {
                                }.getType());
                            }


                            cus_name_list.set(Integer.parseInt(position), name);
                            cus_mob_list.set(Integer.parseInt(position), mob);

                            String nameList1 = new Gson().toJson(cus_name_list);
                            String mobList1 = new Gson().toJson(cus_mob_list);
                            SharedPreferences.Editor editor = sharedpreferences_customers.edit();
                            editor.putString(CUSTOMER_NAME, nameList1);
                            editor.putString(CUSTOMER_MOBILE, mobList1);
                            editor.commit();
                            Intent in = new Intent(CreateCustomerActivity.this, CustomerActivity_Bottom.class);
                            startActivity(in);
                            finish();
                        } else if (pageId.equals("2")) {

                            String nameList = sharedpreferences_customers.getString(CUSTOMER_NAME, "");
                            String mobList = sharedpreferences_customers.getString(CUSTOMER_MOBILE, "");
                            if (!TextUtils.isEmpty(nameList) && !TextUtils.isEmpty(mobList)) {
                                cus_name_list = new Gson().fromJson(nameList, new TypeToken<ArrayList<String>>() {
                                }.getType());
                                cus_mob_list = new Gson().fromJson(mobList, new TypeToken<ArrayList<String>>() {
                                }.getType());
                            }

                            cus_name_list.add(name);
                            cus_mob_list.add(mob);

                            String nameList1 = new Gson().toJson(cus_name_list);
                            String mobList1 = new Gson().toJson(cus_mob_list);
                            SharedPreferences.Editor editor = sharedpreferences_customers.edit();
                            editor.putString(CUSTOMER_NAME, nameList1);
                            editor.putString(CUSTOMER_MOBILE, mobList1);
                            editor.commit();
                            Intent in = new Intent(CreateCustomerActivity.this, CustomerActivity_Bottom.class);
                            startActivity(in);
                            finish();
                        } else if (pageId.equals("02")) {

                            String nameList = sharedpreferences_customers.getString(CUSTOMER_NAME, "");
                            String mobList = sharedpreferences_customers.getString(CUSTOMER_MOBILE, "");
                            if (!TextUtils.isEmpty(nameList) && !TextUtils.isEmpty(mobList)) {
                                cus_name_list = new Gson().fromJson(nameList, new TypeToken<ArrayList<String>>() {
                                }.getType());
                                cus_mob_list = new Gson().fromJson(mobList, new TypeToken<ArrayList<String>>() {
                                }.getType());
                            }

                            cus_name_list.add(name);
                            cus_mob_list.add(mob);

                            String nameList1 = new Gson().toJson(cus_name_list);
                            String mobList1 = new Gson().toJson(cus_mob_list);
                            SharedPreferences.Editor editor = sharedpreferences_customers.edit();
                            editor.putString(CUSTOMER_NAME, nameList1);
                            editor.putString(CUSTOMER_MOBILE, mobList1);
                            editor.commit();


                            Intent in = new Intent(CreateCustomerActivity.this, ApptConfirmActivity.class);
                            in.putExtra("cus_name",name);
                            in.putExtra("cus_mob",mob);
                            startActivity(in);
                            finish();
                        }


                    } else {
                        editText_cus_mob.setError("Enter valid mobile");
                        editText_cus_mob.requestFocus();
                    }

                } else {
                    editText_cus_name.setError("Enter valid name");
                    editText_cus_name.requestFocus();
                }


            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

            Intent in = new Intent(CreateCustomerActivity.this, CustomerActivity_Bottom.class);
            startActivity(in);
            finish();

    }
}
