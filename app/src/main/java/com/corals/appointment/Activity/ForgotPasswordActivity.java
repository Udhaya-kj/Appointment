package com.corals.appointment.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.corals.appointment.Adapters.CountrySpinnerAdapter;
import com.corals.appointment.Adapters.CustomStaffSpinnerAdapter;
import com.corals.appointment.Dialogs.AlertDialogFailure;
import com.corals.appointment.Model.ParamProperties;
import com.corals.appointment.R;
import com.corals.appointment.receiver.ConnectivityReceiver;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

import static com.corals.appointment.Model.ParamProperties.MOBILE_CODE;

public class ForgotPasswordActivity extends AppCompatActivity {

    EditText editText_mob;
    MaterialButton button_forgot_continue;
    TextView textView_country_code;
    private SharedPreferences sharedpreferences_sessionToken;
    Spinner spinner;
    Integer[] imageArray = {R.drawable.malaysia, R.drawable.singapore,
            R.drawable.india, R.drawable.usa};
    private ArrayList<String> country_code_list, dial_code_list;
    private String dial_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        Toolbar toolbar = findViewById(R.id.toolbar_fg_pass);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        sharedpreferences_sessionToken = getSharedPreferences(LoginActivity.MyPREFERENCES_SESSIONTOKEN, Context.MODE_PRIVATE);


        spinner = findViewById(R.id.spinner_country);
        textView_country_code = findViewById(R.id.text_dial_code);
        editText_mob = findViewById(R.id.edit_otp_mobile);
        button_forgot_continue = findViewById(R.id.button_otp_continue);
        String code = sharedpreferences_sessionToken.getString(LoginActivity.COUNTRY_CODE, "");
        ParamProperties paramProperties = new ParamProperties();
        String mob_code = paramProperties.getProperty(code, MOBILE_CODE);
        textView_country_code.setText(mob_code);

        country_code_list = new ArrayList<>();
        dial_code_list = new ArrayList<>();

        country_code_list.add("MA");
        country_code_list.add("SG");
        country_code_list.add("IN");
        country_code_list.add("US");
        dial_code_list.add("+60");
        dial_code_list.add("+65");
        dial_code_list.add("+91");
        dial_code_list.add("+1");

        CountrySpinnerAdapter countrySpinnerAdapter = new CountrySpinnerAdapter(ForgotPasswordActivity.this, imageArray, country_code_list, dial_code_list);
        spinner.setAdapter(countrySpinnerAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                dial_code = dial_code_list.get(position);
                Log.d("dial_code---", "onItemSelected: " + dial_code);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        button_forgot_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mob = editText_mob.getText().toString().trim();
                if (!TextUtils.isEmpty(mob) && mob.length() >= 8) {
                    boolean isConn = ConnectivityReceiver.isConnected();
                    if (isConn) {
                        Intent in = new Intent(ForgotPasswordActivity.this, OtpActivity.class);
                        in.putExtra("mobile", mob);
                        in.putExtra("dial_code", dial_code.substring(1));
                        startActivity(in);
                        finish();
                        overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);
                    } else {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                new AlertDialogFailure(ForgotPasswordActivity.this, getResources().getString(R.string.no_internet_sub_title), "OK", getResources().getString(R.string.no_internet_title), getResources().getString(R.string.no_internet_Heading)) {
                                    @Override
                                    public void onButtonClick() {

                                    }
                                };
                            }
                        });
                    }
                } else {
                    editText_mob.setError("Enter valid mobile");
                    editText_mob.requestFocus();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}
