package com.corals.appointment.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.corals.appointment.R;

public class PasswordResetActivity extends AppCompatActivity {

    String mob;
    EditText editText_mob, editText_new_pass, editText_confirm_pass;
    CheckBox checkBox_new_pass, checkBox_confirm_pass;
    Button button_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_reset);

        Toolbar toolbar = findViewById(R.id.toolbar_reset_pass);
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

        editText_mob = findViewById(R.id.edit_reset_mob);
        editText_new_pass = findViewById(R.id.edit_new_pass);
        editText_confirm_pass = findViewById(R.id.edit_confirm_pass);
        checkBox_new_pass = findViewById(R.id.checkbox_new_pass);
        checkBox_confirm_pass = findViewById(R.id.checkbox_confirm_pass);
        button_submit = findViewById(R.id.button_pass_reset);

        if (getIntent().getExtras() != null) {
            mob = getIntent().getStringExtra("mobile");
            mob="9090909090";
            editText_mob.setText(mob);
            if (mob.length() > 0) {
                editText_mob.setSelection(mob.length());
            }
            Log.d("Mobile---->", "" + mob);
        }

        checkBox_new_pass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // show password
                    editText_new_pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    // hide password
                    editText_new_pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        checkBox_confirm_pass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // show password
                    editText_confirm_pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    // hide password
                    editText_confirm_pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
        button_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mob = editText_mob.getText().toString().trim();
                String new_pass = editText_new_pass.getText().toString().trim();
                String confirm_pass = editText_confirm_pass.getText().toString().trim();

                if (!TextUtils.isEmpty(mob)) {
                    if (!TextUtils.isEmpty(new_pass) && new_pass.length() >= 8) {
                        if (!TextUtils.isEmpty(confirm_pass) && confirm_pass.length() >= 8) {
                            if (new_pass.equals(confirm_pass)){

                            Toast.makeText(PasswordResetActivity.this, "Password has been changed successfully!", Toast.LENGTH_SHORT).show();

                        } else {
                                getDialog("Password does not match. Check your password");
                        }
                        } else {
                            editText_confirm_pass.setError("Enter minimum 8 characters");
                            editText_confirm_pass.requestFocus();
                        }
                    } else {
                        editText_new_pass.setError("Enter minimum 8 characters");
                        editText_new_pass.requestFocus();
                    }
                } else {
                    editText_mob.setError("Enter mobile");
                    editText_mob.requestFocus();
                }
            }
        });
    }

    private void getDialog(String msg) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(PasswordResetActivity.this);
        alertDialogBuilder.setMessage(msg);
        alertDialogBuilder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        arg0.dismiss();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent in = new Intent(PasswordResetActivity.this, ForgotPasswordActivity.class);
        startActivity(in);
        finish();
    }
}
