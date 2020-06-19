package com.corals.appointment.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.corals.appointment.R;

public class ForgotPasswordActivity extends AppCompatActivity {

    EditText editText_mob;
    Button button_forgot_continue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        Toolbar toolbar = findViewById(R.id.toolbar_fg_pass);
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

        editText_mob = findViewById(R.id.edit_otp_mobile);
        button_forgot_continue = findViewById(R.id.button_otp_continue);


        button_forgot_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mob = editText_mob.getText().toString().trim();
                if (!TextUtils.isEmpty(mob)) {
                    Intent in = new Intent(ForgotPasswordActivity.this, OtpActivity.class);
                    in.putExtra("mobile", mob);
                    startActivity(in);
                    finish();
                    overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);
                }
                else {
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
