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
import android.widget.TextView;

import com.corals.appointment.Dialogs.AlertDialogFailure;
import com.corals.appointment.Model.ParamProperties;
import com.corals.appointment.R;
import com.corals.appointment.receiver.ConnectivityReceiver;
import com.google.android.material.button.MaterialButton;

import static com.corals.appointment.Model.ParamProperties.MOBILE_CODE;

public class ForgotPasswordActivity extends AppCompatActivity {

    EditText editText_mob;
    MaterialButton button_forgot_continue;
    TextView textView_country_code;
    private SharedPreferences sharedpreferences_sessionToken;
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


        textView_country_code = findViewById(R.id.text_dial_code);
        editText_mob = findViewById(R.id.edit_otp_mobile);
        button_forgot_continue = findViewById(R.id.button_otp_continue);
        String code = sharedpreferences_sessionToken.getString(LoginActivity.COUNTRY_CODE, "");
        ParamProperties paramProperties = new ParamProperties();
        String mob_code = paramProperties.getProperty(code, MOBILE_CODE);
        textView_country_code.setText(mob_code);

        button_forgot_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mob = editText_mob.getText().toString().trim();
                if (!TextUtils.isEmpty(mob) && mob.length() >= 8) {
                    boolean isConn = ConnectivityReceiver.isConnected();
                    if (isConn) {
                        Intent in = new Intent(ForgotPasswordActivity.this, OtpActivity.class);
                        in.putExtra("mobile", mob);
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
        Intent in = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
        startActivity(in);
        finish();
        overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);
    }
}
