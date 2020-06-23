package com.corals.appointment.Activity;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.corals.appointment.R;

import java.util.Calendar;

public class AddServiceActivity extends AppCompatActivity {

    EditText et_ser_name, et_ser_desc, et_ser_amt;
    Button button_continue;
    public String pageId = "", position = "";

    private SharedPreferences sharedpreferences_service_data;
    public static final String MyPREFERENCES_SERVICE_DATA = "MyPREFERENCES_SERVICE_DATA";
    public static final String SER_NAME = "SER_NAME";
    public static final String SER_DURATION = "SER_DURATION";
    public static final String SER_AMOUNT = "SER_AMOUNT";
    public static final String SER_SHOW_AMOUNT = "SER_SHOW_AMOUNT";
    public static final String SER_DESCRIPTION = "SER_DESCRIPTION";
    Switch aSwitch;
    int time_mins = 0;
    String showAmtCustomer = "0";
    private TextView tv_ser_duration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resource);

        Toolbar toolbar = findViewById(R.id.toolbar_new_resource);
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

        sharedpreferences_service_data = getSharedPreferences(MyPREFERENCES_SERVICE_DATA, Context.MODE_PRIVATE);
        et_ser_name = findViewById(R.id.et_res_name);
        et_ser_desc = findViewById(R.id.et_service_description);
        tv_ser_duration = findViewById(R.id.tv_res_dur);
        et_ser_amt = findViewById(R.id.et_ser_amount);
        button_continue = findViewById(R.id.button_res_continue);
        aSwitch = findViewById(R.id.switch_show_amount);

        String name = sharedpreferences_service_data.getString(SER_NAME, "");
        String dur = sharedpreferences_service_data.getString(SER_DURATION, "");
        String desc = sharedpreferences_service_data.getString(SER_DESCRIPTION, "");
        String amount = sharedpreferences_service_data.getString(SER_AMOUNT, "");
        String show_amount = sharedpreferences_service_data.getString(SER_SHOW_AMOUNT, "");
        Log.d("AddService--->", "onCreate: " + name + "," + dur + "," + desc + "," + amount + "," + show_amount);
        if (!TextUtils.isEmpty(name)) {
            et_ser_name.setText(name);
        }
        if (!TextUtils.isEmpty(dur)) {
            tv_ser_duration.setText(dur);
        }
        if (!TextUtils.isEmpty(desc)) {
            et_ser_desc.setText(desc);
        }
        if (!TextUtils.isEmpty(amount)) {
            et_ser_amt.setText(amount);
        }


        if (!TextUtils.isEmpty(show_amount) && show_amount.equals("1")) {
            aSwitch.setChecked(true);
        } else {
            aSwitch.setChecked(false);
        }
        if (!TextUtils.isEmpty(dur)) {
            time_mins = Integer.parseInt(dur);
        }
        if (getIntent().getExtras() != null) {
            pageId = getIntent().getStringExtra("page_id");
            position = getIntent().getStringExtra("position");
            String ser_name = getIntent().getStringExtra("name");
            String ser_dur = getIntent().getStringExtra("duration");

            Log.d("AddService--->", "onCreate: " + pageId + "," + position + "," + ser_name + "," + ser_dur);
            if (TextUtils.isEmpty(name) && !TextUtils.isEmpty(ser_name)) {
                et_ser_name.setText(ser_name);
            }
            if (TextUtils.isEmpty(dur) && !TextUtils.isEmpty(ser_dur)) {
                tv_ser_duration.setText(ser_dur);
                time_mins = Integer.parseInt(ser_dur);
            }

        }

        tv_ser_duration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timepicker_dialog();
            }
        });
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    showAmtCustomer = "1";
                } else {
                    showAmtCustomer = "0";
                }
            }
        });
        button_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = et_ser_name.getText().toString();
                String duration = tv_ser_duration.getText().toString();
                String s_desc = et_ser_desc.getText().toString();
                String s_amt = et_ser_amt.getText().toString();
                if (name.length() > 0) {
                    if (time_mins != 0) {
                        if (s_amt.length() > 0) {
                            SharedPreferences.Editor editor = sharedpreferences_service_data.edit();
                            editor.putString(SER_NAME, name);
                            editor.putString(SER_DURATION, String.valueOf(time_mins));
                            editor.putString(SER_DESCRIPTION, s_desc);
                            editor.putString(SER_AMOUNT, s_amt);
                            editor.putString(SER_SHOW_AMOUNT, showAmtCustomer);
                            editor.commit();

                            Intent in = new Intent(AddServiceActivity.this, AddServiceAvailTimeActivity.class);
                            in.putExtra("ser_name", name);
                            in.putExtra("ser_dur", String.valueOf(time_mins));
                            in.putExtra("ser_desc", s_desc);
                            in.putExtra("page_id", pageId);
                            in.putExtra("position", position);
                            in.putExtra("amount", s_amt);
                            in.putExtra("show_amount", showAmtCustomer);
                            startActivity(in);
                            finish();
                            overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);
                        } else {
                            et_ser_amt.setError("Enter service amount");
                            et_ser_amt.requestFocus();
                        }
                    } else {
                        getDialog("Select valid service duration");
                    }

                } else {
                    et_ser_name.setError("Enter service name");
                    et_ser_name.requestFocus();
                }


            }
        });


    }

    void timepicker_dialog() {
        LayoutInflater factory = LayoutInflater.from(this);
        final View deleteDialogView = factory.inflate(R.layout.timepicker_layout, null);
        final AlertDialog pickerDialog = new AlertDialog.Builder(this).create();
        pickerDialog.setView(deleteDialogView);
        final TimePicker pickStartTime = (TimePicker) deleteDialogView.findViewById(R.id.StartTime);
        Button button = (Button) deleteDialogView.findViewById(R.id.button_picker_ok);
        pickStartTime.setIs24HourView(true);
        pickStartTime.setCurrentHour(00);
        pickStartTime.setCurrentMinute(00);
        pickStartTime.setOnTimeChangedListener(mStartTimeChangedListener);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int hour = pickStartTime.getCurrentHour();
                int minute = pickStartTime.getCurrentMinute();
                time_mins = (hour * 60) + minute;
                tv_ser_duration.setText(String.valueOf(time_mins));
                pickerDialog.dismiss();
            }
        });
        pickerDialog.show();

    }

    private TimePicker.OnTimeChangedListener mStartTimeChangedListener =
            new TimePicker.OnTimeChangedListener() {

                public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                    updateDisplay(view, hourOfDay, minute);
                }
            };

    private TimePicker.OnTimeChangedListener mNullTimeChangedListener =
            new TimePicker.OnTimeChangedListener() {

                public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {

                }
            };


    private void updateDisplay(TimePicker timePicker, int hourOfDay, int minute) {
        int nextMinute = 0;
        //int nextHour = 0;

        timePicker.setOnTimeChangedListener(mNullTimeChangedListener);

        if (minute >= 45 && minute <= 59)
            nextMinute = 45;
        else if (minute >= 30)
            nextMinute = 30;
        else if (minute >= 15)
            nextMinute = 15;
        else if (minute > 0)
            nextMinute = 0;
        else {
            nextMinute = 45;
        }

        if (minute - nextMinute == 1) {
            if (minute >= 45 && minute <= 59) {
                nextMinute = 00;
                hourOfDay = hourOfDay + 1;
            } else if (minute >= 30)
                nextMinute = 45;
            else if (minute >= 15)
                nextMinute = 30;
            else if (minute > 0)
                nextMinute = 15;
            else {
                nextMinute = 15;
            }
        }

        timePicker.setCurrentHour(hourOfDay);
        timePicker.setCurrentMinute(nextMinute);

        timePicker.setOnTimeChangedListener(mStartTimeChangedListener);

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent in = new Intent(AddServiceActivity.this, SetupServiceActivity_Bottom.class);
        startActivity(in);
        finish();
        overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);

    }


    private void getDialog(String msg) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AddServiceActivity.this);
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
}