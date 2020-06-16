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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.corals.appointment.R;

import java.util.Calendar;

public class AddServiceActivity extends AppCompatActivity {

    EditText et_ser_name,et_ser_desc;
    TextView tv_ser_dur;
    Button button_continue;
    LinearLayout linearLayout_ser_providers;
    TextView textView_ser_providers;
    public String pageId = "", position = "";

    TextView text_service_op_time, text_service_cl_time;
    TextView text_sunday_add1_1, text_sunday_add1_2, text_sunday_add2_1, text_sunday_add2_2, text_sunday_add3_1, text_sunday_add3_2, text_monday_add1_1, text_monday_add1_2, text_monday_add2_1, text_monday_add2_2, text_monday_add3_1, text_monday_add3_2,
            text_tuesday_add1_1, text_tuesday_add1_2, text_tuesday_add2_1, text_tuesday_add2_2, text_tuesday_add3_1, text_tuesday_add3_2, text_wednesday_add1_1, text_wednesday_add1_2, text_wednesday_add2_1, text_wednesday_add2_2, text_wednesday_add3_1, text_wednesday_add3_2,
            text_thursday_add1_1, text_thursday_add1_2, text_thursday_add2_1, text_thursday_add2_2, text_thursday_add3_1, text_thursday_add3_2, text_friday_add1_1, text_friday_add1_2, text_friday_add2_1, text_friday_add2_2, text_friday_add3_1, text_friday_add3_2, text_saturday_add1_1, text_saturday_add1_2, text_saturday_add2_1, text_saturday_add2_2, text_saturday_add3_1, text_saturday_add3_2;
    ;
    private int mYear, mMonth, mDay, mHour, mMinute, mSeconds;
    private String sunday_add1_1, sunday_add1_2, sunday_add2_1, sunday_add2_2, sunday_add3_1, sunday_add3_2, monday_add1_1, monday_add1_2, monday_add2_1, monday_add2_2, monday_add3_1, monday_add3_2,
            tuesday_add1_1, tuesday_add1_2, tuesday_add2_1, tuesday_add2_2, tuesday_add3_1, tuesday_add3_2, wednesday_add1_1, wednesday_add1_2, wednesday_add2_1, wednesday_add2_2, wednesday_add3_1, wednesday_add3_2,
            thursday_add1_1, thursday_add1_2, thursday_add2_1, thursday_add2_2, thursday_add3_1, thursday_add3_2, friday_add1_1, friday_add1_2, friday_add2_1, friday_add2_2, friday_add3_1, friday_add3_2, saturday_add1_1, saturday_add1_2, saturday_add2_1, saturday_add2_2, saturday_add3_1, saturday_add3_2;
    private SharedPreferences sharedpreferences_service_data;
    public static final String MyPREFERENCES_SERVICE_DATA = "MyPREFERENCES_SERVICE_DATA";
    public static final String SER_NAME = "SER_NAME";
    public static final String SER_DURATION = "SER_DURATION";
    public static final String SER_DESCRIPTION = "SER_DESCRIPTION";

    Button btn_yes_sday_p, btn_yes_mnday_p, btn_yes_tsday_p, btn_yes_wedday_p, btn_yes_trsday_p, btn_yes_fdday_p, btn_yes_strday_p;
    boolean isActiveSunday_p = true, isActiveMonday_p = true, isActiveTuesday_p = true, isActiveWednesday_p = true, isActiveThursday_p = true, isActiveFriday_p = true, isActiveSaturday_p = true;

    private String service_op_time, service_cl_time;
    int hour = 0, minute = 0;

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
        tv_ser_dur = findViewById(R.id.et_res_dur);
        textView_ser_providers = findViewById(R.id.tv_ser_providers);
        linearLayout_ser_providers = findViewById(R.id.layout_ser_providers);
        button_continue = findViewById(R.id.button_res_continue);

        btn_yes_sday_p = (Button) findViewById(R.id.btn_yes_sunday_P);
        btn_yes_mnday_p = (Button) findViewById(R.id.btn_yes_monday_P);
        btn_yes_tsday_p = (Button) findViewById(R.id.btn_yes_tuesday_P);
        btn_yes_wedday_p = (Button) findViewById(R.id.btn_yes_wedsnesday_P);
        btn_yes_trsday_p = (Button) findViewById(R.id.btn_yes_thursday_P);
        btn_yes_fdday_p = (Button) findViewById(R.id.btn_yes_friday_P);
        btn_yes_strday_p = (Button) findViewById(R.id.btn_yes_saturday_P);

        text_service_op_time = findViewById(R.id.text_service_op_time);
        text_service_cl_time = findViewById(R.id.text_service_cl_time);

        //Sunday
        text_sunday_add1_1 = findViewById(R.id.text_sunday_add1_1);
        text_sunday_add1_2 = findViewById(R.id.text_sunday_add1_2);
        text_sunday_add2_1 = findViewById(R.id.text_sunday_add2_1);
        text_sunday_add2_2 = findViewById(R.id.text_sunday_add2_2);
        text_sunday_add3_1 = findViewById(R.id.text_sunday_add3_1);
        text_sunday_add3_2 = findViewById(R.id.text_sunday_add3_2);

        //Monday
        text_monday_add1_1 = findViewById(R.id.text_monday_add1_1);
        text_monday_add1_2 = findViewById(R.id.text_monday_add1_2);
        text_monday_add2_1 = findViewById(R.id.text_monday_add2_1);
        text_monday_add2_2 = findViewById(R.id.text_monday_add2_2);
        text_monday_add3_1 = findViewById(R.id.text_monday_add3_1);
        text_monday_add3_2 = findViewById(R.id.text_monday_add3_2);

        //Tuesday
        text_tuesday_add1_1 = findViewById(R.id.text_tuesday_add1_1);
        text_tuesday_add1_2 = findViewById(R.id.text_tuesday_add1_2);
        text_tuesday_add2_1 = findViewById(R.id.text_tuesday_add2_1);
        text_tuesday_add2_2 = findViewById(R.id.text_tuesday_add2_2);
        text_tuesday_add3_1 = findViewById(R.id.text_tuesday_add3_1);
        text_tuesday_add3_2 = findViewById(R.id.text_tuesday_add3_2);

        //Wednesday
        text_wednesday_add1_1 = findViewById(R.id.text_wednesday_add1_1);
        text_wednesday_add1_2 = findViewById(R.id.text_wednesday_add1_2);
        text_wednesday_add2_1 = findViewById(R.id.text_wednesday_add2_1);
        text_wednesday_add2_2 = findViewById(R.id.text_wednesday_add2_2);
        text_wednesday_add3_1 = findViewById(R.id.text_wednesday_add3_1);
        text_wednesday_add3_2 = findViewById(R.id.text_wednesday_add3_2);

        //Thursday
        text_thursday_add1_1 = findViewById(R.id.text_thursday_add1_1);
        text_thursday_add1_2 = findViewById(R.id.text_thursday_add1_2);
        text_thursday_add2_1 = findViewById(R.id.text_thursday_add2_1);
        text_thursday_add2_2 = findViewById(R.id.text_thursday_add2_2);
        text_thursday_add3_1 = findViewById(R.id.text_thursday_add3_1);
        text_thursday_add3_2 = findViewById(R.id.text_thursday_add3_2);

        //Friday
        text_friday_add1_1 = findViewById(R.id.text_friday_add1_1);
        text_friday_add1_2 = findViewById(R.id.text_friday_add1_2);
        text_friday_add2_1 = findViewById(R.id.text_friday_add2_1);
        text_friday_add2_2 = findViewById(R.id.text_friday_add2_2);
        text_friday_add3_1 = findViewById(R.id.text_friday_add3_1);
        text_friday_add3_2 = findViewById(R.id.text_friday_add3_2);

        //Saturday
        text_saturday_add1_1 = findViewById(R.id.text_saturday_add1_1);
        text_saturday_add1_2 = findViewById(R.id.text_saturday_add1_2);
        text_saturday_add2_1 = findViewById(R.id.text_saturday_add2_1);
        text_saturday_add2_2 = findViewById(R.id.text_saturday_add2_2);
        text_saturday_add3_1 = findViewById(R.id.text_saturday_add3_1);
        text_saturday_add3_2 = findViewById(R.id.text_saturday_add3_2);

        String name = sharedpreferences_service_data.getString(SER_NAME, "");
        String dur = sharedpreferences_service_data.getString(SER_DURATION, "");
        String desc = sharedpreferences_service_data.getString(SER_DESCRIPTION, "");
        Log.d("AddService--->", "onCreate: "+name+","+dur+","+desc);
        et_ser_name.setText(name);
        tv_ser_dur.setText(dur);
        et_ser_desc.setText(desc);

        if (getIntent().getExtras() != null) {
            pageId = getIntent().getStringExtra("page_id");
            position = getIntent().getStringExtra("position");
            String ser_name = getIntent().getStringExtra("name");
            String ser_dur = getIntent().getStringExtra("duration");

            if(TextUtils.isEmpty(name)) {
                et_ser_name.setText(ser_name);
            }
            if(TextUtils.isEmpty(dur)) {
                tv_ser_dur.setText(ser_dur);
            }

        }

        tv_ser_dur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timepicker_dialog();
            }
        });
        linearLayout_ser_providers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(AddServiceActivity.this, SelectServiceProvidersActivity.class);
                in.putExtra("page_id", pageId);
                startActivity(in);
                finish();
            }
        });


        btn_yes_sday_p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //s_status = "1";
                if (isActiveSunday_p) {
                    btn_yes_sday_p.setBackgroundColor(getResources().getColor(R.color.dark_grey));
                    btn_yes_sday_p.setTextColor(getResources().getColor(R.color.black));
                    isActiveSunday_p = false;
                } else {
                    btn_yes_sday_p.setBackgroundColor(getResources().getColor(R.color.green_hase));
                    btn_yes_sday_p.setTextColor(getResources().getColor(R.color.white));
                    isActiveSunday_p = true;
                }
            }
        });

        btn_yes_mnday_p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  mn_status = "1";
                btn_yes_mnday.setBackgroundResource(R.drawable.yes_select_selector);*/
                if (isActiveMonday_p) {
                    btn_yes_mnday_p.setBackgroundColor(getResources().getColor(R.color.dark_grey));
                    btn_yes_mnday_p.setTextColor(getResources().getColor(R.color.black));
                    isActiveMonday_p = false;
                } else {
                    btn_yes_mnday_p.setBackgroundColor(getResources().getColor(R.color.green_hase));
                    btn_yes_mnday_p.setTextColor(getResources().getColor(R.color.white));
                    isActiveMonday_p = true;
                }
            }
        });


        btn_yes_tsday_p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* ts_status = "1";
                btn_yes_tsday.setBackgroundResource(R.drawable.yes_select_selector);*/
                if (isActiveTuesday_p) {
                    btn_yes_tsday_p.setBackgroundColor(getResources().getColor(R.color.dark_grey));
                    btn_yes_tsday_p.setTextColor(getResources().getColor(R.color.black));
                    isActiveTuesday_p = false;
                } else {
                    btn_yes_tsday_p.setBackgroundColor(getResources().getColor(R.color.green_hase));
                    btn_yes_tsday_p.setTextColor(getResources().getColor(R.color.white));
                    isActiveTuesday_p = true;
                }
            }
        });


        btn_yes_wedday_p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*wed_status = "1";
                btn_yes_wedday.setBackgroundResource(R.drawable.yes_select_selector);*/
                if (isActiveWednesday_p) {
                    btn_yes_wedday_p.setBackgroundColor(getResources().getColor(R.color.dark_grey));
                    btn_yes_wedday_p.setTextColor(getResources().getColor(R.color.black));
                    isActiveWednesday_p = false;
                } else {
                    btn_yes_wedday_p.setBackgroundColor(getResources().getColor(R.color.green_hase));
                    btn_yes_wedday_p.setTextColor(getResources().getColor(R.color.white));
                    isActiveWednesday_p = true;
                }

            }
        });


        btn_yes_trsday_p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* thrs_status = "1";
                btn_yes_trsday.setBackgroundResource(R.drawable.yes_select_selector);*/

                if (isActiveThursday_p) {
                    btn_yes_trsday_p.setBackgroundColor(getResources().getColor(R.color.dark_grey));
                    btn_yes_trsday_p.setTextColor(getResources().getColor(R.color.black));
                    isActiveThursday_p = false;
                } else {
                    btn_yes_trsday_p.setBackgroundColor(getResources().getColor(R.color.green_hase));
                    btn_yes_trsday_p.setTextColor(getResources().getColor(R.color.white));
                    isActiveThursday_p = true;
                }

            }
        });


        btn_yes_fdday_p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* fr_status = "1";
                btn_yes_fdday.setBackgroundResource(R.drawable.yes_select_selector);*/

                if (isActiveFriday_p) {
                    btn_yes_fdday_p.setBackgroundColor(getResources().getColor(R.color.dark_grey));
                    btn_yes_fdday_p.setTextColor(getResources().getColor(R.color.black));
                    isActiveFriday_p = false;
                } else {
                    btn_yes_fdday_p.setBackgroundColor(getResources().getColor(R.color.green_hase));
                    btn_yes_fdday_p.setTextColor(getResources().getColor(R.color.white));
                    isActiveFriday_p = true;
                }

            }
        });


        btn_yes_strday_p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  st_status = "1";
                btn_yes_strday.setBackgroundResource(R.drawable.yes_select_selector);*/

                if (isActiveSaturday_p) {
                    btn_yes_strday_p.setBackgroundColor(getResources().getColor(R.color.dark_grey));
                    btn_yes_strday_p.setTextColor(getResources().getColor(R.color.black));
                    isActiveSaturday_p = false;
                } else {
                    btn_yes_strday_p.setBackgroundColor(getResources().getColor(R.color.green_hase));
                    btn_yes_strday_p.setTextColor(getResources().getColor(R.color.white));
                    isActiveSaturday_p = true;
                }
            }
        });


        button_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = et_ser_name.getText().toString();
                String duration = tv_ser_dur.getText().toString();
                String s_desc = et_ser_desc .getText().toString();
                if (name.length() > 0) {
                    if (pageId.equals("3")) {
                        if (hour == 0 && minute != 0) {
                            SharedPreferences.Editor editor = sharedpreferences_service_data.edit();
                            editor.putString(SER_NAME, name);
                            editor.putString(SER_DURATION, duration);
                            editor.putString(SER_DESCRIPTION, s_desc);
                            editor.commit();

                            Intent in = new Intent(AddServiceActivity.this, AddServiceAvailTimeActivity.class);
                            in.putExtra("ser_name", name);
                            in.putExtra("ser_dur", duration);
                            in.putExtra("ser_desc", s_desc);
                            in.putExtra("page_id", pageId);
                            in.putExtra("position", position);
                            startActivity(in);
                            finish();
                        } else if ((hour != 0 && minute == 0) || (hour != 0 && minute != 0)) {
                            SharedPreferences.Editor editor = sharedpreferences_service_data.edit();
                            editor.putString(SER_NAME, name);
                            editor.putString(SER_DURATION, duration);
                            editor.putString(SER_DESCRIPTION, s_desc);
                            editor.commit();
                            Intent in = new Intent(AddServiceActivity.this, AddServiceAvailTimeActivity.class);
                            in.putExtra("ser_name", name);
                            in.putExtra("ser_dur", duration);
                            in.putExtra("ser_desc", s_desc);
                            in.putExtra("page_id", pageId);
                            in.putExtra("position", position);
                            startActivity(in);
                            finish();
                        } else {
                            getDialog("Select valid service duration");
                        }
                    } else {
                        SharedPreferences.Editor editor = sharedpreferences_service_data.edit();
                        editor.putString(SER_NAME, name);
                        editor.putString(SER_DURATION, duration);
                        editor.putString(SER_DESCRIPTION, s_desc);
                        editor.commit();
                        Intent in = new Intent(AddServiceActivity.this, AddServiceAvailTimeActivity.class);
                        in.putExtra("ser_name", name);
                        in.putExtra("ser_dur", duration);
                        in.putExtra("ser_desc", s_desc);
                        in.putExtra("page_id", pageId);
                        in.putExtra("position", position);
                        startActivity(in);
                        finish();
                    }

                } else {
                    et_ser_name.setError("Enter service name");
                    et_ser_name.requestFocus();
                }


            }
        });

        text_service_op_time.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + "08:00" + "</u>  </font>"));
        text_service_cl_time.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + "20:00" + "</u>  </font>"));

        //Sunday
        text_sunday_add1_1.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + "00:00" + "</u>  </font>"));
        text_sunday_add1_2.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + "00:00" + "</u>  </font>"));
        text_sunday_add2_1.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + "00:00" + "</u>  </font>"));
        text_sunday_add2_2.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + "00:00" + "</u>  </font>"));
        text_sunday_add3_1.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + "00:00" + "</u>  </font>"));
        text_sunday_add3_2.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + "00:00" + "</u>  </font>"));

        //Monday
        text_monday_add1_1.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + "00:00" + "</u>  </font>"));
        text_monday_add1_2.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + "00:00" + "</u>  </font>"));
        text_monday_add2_1.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + "00:00" + "</u>  </font>"));
        text_monday_add2_2.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + "00:00" + "</u>  </font>"));
        text_monday_add3_1.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + "00:00" + "</u>  </font>"));
        text_monday_add3_2.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + "00:00" + "</u>  </font>"));

        //Tuesday
        text_tuesday_add1_1.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + "00:00" + "</u>  </font>"));
        text_tuesday_add1_2.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + "00:00" + "</u>  </font>"));
        text_tuesday_add2_1.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + "00:00" + "</u>  </font>"));
        text_tuesday_add2_2.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + "00:00" + "</u>  </font>"));
        text_tuesday_add3_1.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + "00:00" + "</u>  </font>"));
        text_tuesday_add3_2.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + "00:00" + "</u>  </font>"));

        //Wednesday
        text_wednesday_add1_1.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + "00:00" + "</u>  </font>"));
        text_wednesday_add1_2.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + "00:00" + "</u>  </font>"));
        text_wednesday_add2_1.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + "00:00" + "</u>  </font>"));
        text_wednesday_add2_2.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + "00:00" + "</u>  </font>"));
        text_wednesday_add3_1.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + "00:00" + "</u>  </font>"));
        text_wednesday_add3_2.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + "00:00" + "</u>  </font>"));

        //Thursday
        text_thursday_add1_1.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + "00:00" + "</u>  </font>"));
        text_thursday_add1_2.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + "00:00" + "</u>  </font>"));
        text_thursday_add2_1.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + "00:00" + "</u>  </font>"));
        text_thursday_add2_2.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + "00:00" + "</u>  </font>"));
        text_thursday_add3_1.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + "00:00" + "</u>  </font>"));
        text_thursday_add3_2.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + "00:00" + "</u>  </font>"));

        //Friday
        text_friday_add1_1.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + "00:00" + "</u>  </font>"));
        text_friday_add1_2.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + "00:00" + "</u>  </font>"));
        text_friday_add2_1.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + "00:00" + "</u>  </font>"));
        text_friday_add2_2.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + "00:00" + "</u>  </font>"));
        text_friday_add3_1.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + "00:00" + "</u>  </font>"));
        text_friday_add3_2.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + "00:00" + "</u>  </font>"));

        //Saturday
        text_saturday_add1_1.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + "00:00" + "</u>  </font>"));
        text_saturday_add1_2.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + "00:00" + "</u>  </font>"));
        text_saturday_add2_1.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + "00:00" + "</u>  </font>"));
        text_saturday_add2_2.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + "00:00" + "</u>  </font>"));
        text_saturday_add3_1.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + "00:00" + "</u>  </font>"));
        text_saturday_add3_2.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + "00:00" + "</u>  </font>"));


        text_service_op_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_service_op_time();
            }
        });
        text_service_cl_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_service_cl_time();
            }
        });

        //sunday
        text_sunday_add1_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_sunday_add1_1();
            }
        });

        text_sunday_add1_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_sunday_add1_2();
            }
        });

        text_sunday_add2_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_sunday_add2_1();
            }
        });

        text_sunday_add2_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_sunday_add2_2();
            }
        });

        text_sunday_add3_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_sunday_add3_1();
            }
        });

        text_sunday_add3_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_sunday_add3_2();
            }
        });


//monday
        text_monday_add1_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_monday_add1_1();
            }
        });

        text_monday_add1_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_monday_add1_2();
            }
        });

        text_monday_add2_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_monday_add2_1();
            }
        });

        text_monday_add2_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_monday_add2_2();
            }
        });

        text_monday_add3_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_monday_add3_1();
            }
        });

        text_monday_add3_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_monday_add3_2();
            }
        });


        //tuesday
        text_tuesday_add1_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_tuesday_add1_1();
            }
        });

        text_tuesday_add1_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_tuesday_add1_2();
            }
        });

        text_tuesday_add2_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_tuesday_add2_1();
            }
        });

        text_tuesday_add2_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_tuesday_add2_2();
            }
        });

        text_tuesday_add3_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_tuesday_add3_1();
            }
        });

        text_tuesday_add3_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_tuesday_add3_2();
            }
        });


        //wednesday
        text_wednesday_add1_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_wednesday_add1_1();
            }
        });

        text_wednesday_add1_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_wednesday_add1_2();
            }
        });

        text_wednesday_add2_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_wednesday_add2_1();
            }
        });

        text_wednesday_add2_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_wednesday_add2_2();
            }
        });

        text_wednesday_add3_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_wednesday_add3_1();
            }
        });

        text_wednesday_add3_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_wednesday_add3_2();
            }
        });


        //thursday
        text_thursday_add1_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_thursday_add1_1();
            }
        });

        text_thursday_add1_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_thursday_add1_2();
            }
        });

        text_thursday_add2_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_thursday_add2_1();
            }
        });

        text_thursday_add2_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_thursday_add2_2();
            }
        });

        text_thursday_add3_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_thursday_add3_1();
            }
        });

        text_thursday_add3_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_thursday_add3_2();
            }
        });


        //friday
        text_friday_add1_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_friday_add1_1();
            }
        });

        text_friday_add1_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_friday_add1_2();
            }
        });

        text_friday_add2_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_friday_add2_1();
            }
        });

        text_friday_add2_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_friday_add2_2();
            }
        });

        text_friday_add3_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_friday_add3_1();
            }
        });

        text_friday_add3_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_friday_add3_2();
            }
        });


        //saturday
        text_saturday_add1_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_saturday_add1_1();
            }
        });

        text_saturday_add1_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_saturday_add1_2();
            }
        });

        text_saturday_add2_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_saturday_add2_1();
            }
        });

        text_saturday_add2_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_saturday_add2_2();
            }
        });

        text_saturday_add3_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_saturday_add3_1();
            }
        });

        text_saturday_add3_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_saturday_add3_2();
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
                hour = pickStartTime.getCurrentHour();
                minute = pickStartTime.getCurrentMinute();
                tv_ser_dur.setText(hour + " hrs " + minute + " mins");
                //  Toast.makeText(AddServiceActivity.this, hour+" hrs "+minute+" mins", Toast.LENGTH_SHORT).show();
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

    }

    public void get_service_op_time() {
        // Get Current Time*
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        mSeconds = c.get(Calendar.SECOND);
        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, android.R.style.Theme_Holo_Dialog_MinWidth,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        // view.setBackgroundColor(getResources().getColor(R.color.button_background));
                        String time_hr = String.valueOf(hourOfDay);
                        String time_min = String.valueOf(minute);
                        String min = null, hr = null;
                        if (time_hr.length() == 1 && time_min.length() == 1) {
                            min = "0" + "" + time_min;
                            hr = "0" + "" + time_hr;
                            service_op_time = hr + ":" + min;
                        } else if (time_hr.length() == 1 && time_min.length() != 1) {
                            hr = "0" + "" + time_hr;
                            service_op_time = hr + ":" + minute;
                        } else if (time_hr.length() != 1 && time_min.length() == 1) {
                            min = "0" + "" + time_min;
                            service_op_time = hourOfDay + ":" + min;
                        } else {
                            service_op_time = hourOfDay + ":" + minute;
                        }
                        Log.d("Time===>", "" + service_op_time);
                        //editText_start_time.setText(time_active);
                        text_service_op_time.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + service_op_time + "</u>  </font>"));

                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();

    }

    public void get_service_cl_time() {
        // Get Current Time*
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        mSeconds = c.get(Calendar.SECOND);
        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, android.R.style.Theme_Holo_Dialog_MinWidth,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        // view.setBackgroundColor(getResources().getColor(R.color.button_background));
                        String time_hr = String.valueOf(hourOfDay);
                        String time_min = String.valueOf(minute);
                        String min = null, hr = null;
                        if (time_hr.length() == 1 && time_min.length() == 1) {
                            min = "0" + "" + time_min;
                            hr = "0" + "" + time_hr;
                            service_cl_time = hr + ":" + min;
                        } else if (time_hr.length() == 1 && time_min.length() != 1) {
                            hr = "0" + "" + time_hr;
                            service_cl_time = hr + ":" + minute;
                        } else if (time_hr.length() != 1 && time_min.length() == 1) {
                            min = "0" + "" + time_min;
                            service_cl_time = hourOfDay + ":" + min;
                        } else {
                            service_cl_time = hourOfDay + ":" + minute;
                        }
                        Log.d("Time===>", "" + service_cl_time);
                        //editText_start_time.setText(time_active);
                        text_service_cl_time.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + service_cl_time + "</u>  </font>"));

                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();

    }


    //Sunday
    public void get_sunday_add1_1() {
        // Get Current Time*
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        mSeconds = c.get(Calendar.SECOND);
        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, android.R.style.Theme_Holo_Dialog_MinWidth,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        // view.setBackgroundColor(getResources().getColor(R.color.button_background));
                        String time_hr = String.valueOf(hourOfDay);
                        String time_min = String.valueOf(minute);
                        String min = null, hr = null;
                        if (time_hr.length() == 1 && time_min.length() == 1) {
                            min = "0" + "" + time_min;
                            hr = "0" + "" + time_hr;
                            sunday_add1_1 = hr + ":" + min;
                        } else if (time_hr.length() == 1 && time_min.length() != 1) {
                            hr = "0" + "" + time_hr;
                            sunday_add1_1 = hr + ":" + minute;
                        } else if (time_hr.length() != 1 && time_min.length() == 1) {
                            min = "0" + "" + time_min;
                            sunday_add1_1 = hourOfDay + ":" + min;
                        } else {
                            sunday_add1_1 = hourOfDay + ":" + minute;
                        }
                        Log.d("Time===>", "" + sunday_add1_1);
                        //editText_start_time.setText(time_active);
                        text_sunday_add1_1.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + sunday_add1_1 + "</u>  </font>"));

                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();

    }

    public void get_sunday_add1_2() {
        // Get Current Time*
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        mSeconds = c.get(Calendar.SECOND);
        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, android.R.style.Theme_Holo_Dialog_MinWidth,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        // view.setBackgroundColor(getResources().getColor(R.color.button_background));
                        String time_hr = String.valueOf(hourOfDay);
                        String time_min = String.valueOf(minute);
                        String min = null, hr = null;
                        if (time_hr.length() == 1 && time_min.length() == 1) {
                            min = "0" + "" + time_min;
                            hr = "0" + "" + time_hr;
                            sunday_add1_2 = hr + ":" + min;
                        } else if (time_hr.length() == 1 && time_min.length() != 1) {
                            hr = "0" + "" + time_hr;
                            sunday_add1_2 = hr + ":" + minute;
                        } else if (time_hr.length() != 1 && time_min.length() == 1) {
                            min = "0" + "" + time_min;
                            sunday_add1_2 = hourOfDay + ":" + min;
                        } else {
                            sunday_add1_2 = hourOfDay + ":" + minute;
                        }
                        Log.d("Time===>", "" + sunday_add1_2);
                        //editText_start_time.setText(time_active);
                        text_sunday_add1_2.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + sunday_add1_2 + "</u>  </font>"));

                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();

    }


    //Monday
    public void get_sunday_add2_1() {
        // Get Current Time*
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        mSeconds = c.get(Calendar.SECOND);
        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, android.R.style.Theme_Holo_Dialog_MinWidth,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        // view.setBackgroundColor(getResources().getColor(R.color.button_background));
                        String time_hr = String.valueOf(hourOfDay);
                        String time_min = String.valueOf(minute);
                        String min = null, hr = null;
                        if (time_hr.length() == 1 && time_min.length() == 1) {
                            min = "0" + "" + time_min;
                            hr = "0" + "" + time_hr;
                            sunday_add2_1 = hr + ":" + min;
                        } else if (time_hr.length() == 1 && time_min.length() != 1) {
                            hr = "0" + "" + time_hr;
                            sunday_add2_1 = hr + ":" + minute;
                        } else if (time_hr.length() != 1 && time_min.length() == 1) {
                            min = "0" + "" + time_min;
                            sunday_add2_1 = hourOfDay + ":" + min;
                        } else {
                            sunday_add2_1 = hourOfDay + ":" + minute;
                        }
                        Log.d("Time===>", "" + sunday_add2_1);
                        //editText_start_time.setText(time_active);
                        text_sunday_add2_1.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + sunday_add2_1 + "</u>  </font>"));

                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();

    }

    public void get_sunday_add2_2() {
        // Get Current Time*
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        mSeconds = c.get(Calendar.SECOND);
        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, android.R.style.Theme_Holo_Dialog_MinWidth,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        // view.setBackgroundColor(getResources().getColor(R.color.button_background));
                        String time_hr = String.valueOf(hourOfDay);
                        String time_min = String.valueOf(minute);
                        String min = null, hr = null;
                        if (time_hr.length() == 1 && time_min.length() == 1) {
                            min = "0" + "" + time_min;
                            hr = "0" + "" + time_hr;
                            sunday_add2_2 = hr + ":" + min;
                        } else if (time_hr.length() == 1 && time_min.length() != 1) {
                            hr = "0" + "" + time_hr;
                            sunday_add2_2 = hr + ":" + minute;
                        } else if (time_hr.length() != 1 && time_min.length() == 1) {
                            min = "0" + "" + time_min;
                            sunday_add2_2 = hourOfDay + ":" + min;
                        } else {
                            sunday_add2_2 = hourOfDay + ":" + minute;
                        }
                        Log.d("Time===>", "" + sunday_add3_1);
                        //editText_start_time.setText(time_active);
                        text_sunday_add2_2.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + sunday_add2_2 + "</u>  </font>"));

                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();

    }

    //Tuesday
    public void get_sunday_add3_1() {
        // Get Current Time*
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        mSeconds = c.get(Calendar.SECOND);
        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, android.R.style.Theme_Holo_Dialog_MinWidth,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        // view.setBackgroundColor(getResources().getColor(R.color.button_background));
                        String time_hr = String.valueOf(hourOfDay);
                        String time_min = String.valueOf(minute);
                        String min = null, hr = null;
                        if (time_hr.length() == 1 && time_min.length() == 1) {
                            min = "0" + "" + time_min;
                            hr = "0" + "" + time_hr;
                            sunday_add3_1 = hr + ":" + min;
                        } else if (time_hr.length() == 1 && time_min.length() != 1) {
                            hr = "0" + "" + time_hr;
                            sunday_add3_1 = hr + ":" + minute;
                        } else if (time_hr.length() != 1 && time_min.length() == 1) {
                            min = "0" + "" + time_min;
                            sunday_add3_1 = hourOfDay + ":" + min;
                        } else {
                            sunday_add3_1 = hourOfDay + ":" + minute;
                        }
                        Log.d("Time===>", "" + sunday_add3_1);
                        //editText_start_time.setText(time_active);
                        text_sunday_add3_1.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + sunday_add3_1 + "</u>  </font>"));

                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();

    }

    public void get_sunday_add3_2() {
        // Get Current Time*
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        mSeconds = c.get(Calendar.SECOND);
        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, android.R.style.Theme_Holo_Dialog_MinWidth,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        // view.setBackgroundColor(getResources().getColor(R.color.button_background));
                        String time_hr = String.valueOf(hourOfDay);
                        String time_min = String.valueOf(minute);
                        String min = null, hr = null;
                        if (time_hr.length() == 1 && time_min.length() == 1) {
                            min = "0" + "" + time_min;
                            hr = "0" + "" + time_hr;
                            sunday_add3_2 = hr + ":" + min;
                        } else if (time_hr.length() == 1 && time_min.length() != 1) {
                            hr = "0" + "" + time_hr;
                            sunday_add3_2 = hr + ":" + minute;
                        } else if (time_hr.length() != 1 && time_min.length() == 1) {
                            min = "0" + "" + time_min;
                            sunday_add3_2 = hourOfDay + ":" + min;
                        } else {
                            sunday_add3_2 = hourOfDay + ":" + minute;
                        }
                        Log.d("Time===>", "" + sunday_add3_1);
                        //editText_start_time.setText(time_active);
                        text_sunday_add3_2.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + sunday_add3_2 + "</u>  </font>"));

                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();

    }

    //Wednesday
    public void get_monday_add1_1() {
        // Get Current Time*
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        mSeconds = c.get(Calendar.SECOND);
        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, android.R.style.Theme_Holo_Dialog_MinWidth,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        // view.setBackgroundColor(getResources().getColor(R.color.button_background));
                        String time_hr = String.valueOf(hourOfDay);
                        String time_min = String.valueOf(minute);
                        String min = null, hr = null;
                        if (time_hr.length() == 1 && time_min.length() == 1) {
                            min = "0" + "" + time_min;
                            hr = "0" + "" + time_hr;
                            monday_add1_1 = hr + ":" + min;
                        } else if (time_hr.length() == 1 && time_min.length() != 1) {
                            hr = "0" + "" + time_hr;
                            monday_add1_1 = hr + ":" + minute;
                        } else if (time_hr.length() != 1 && time_min.length() == 1) {
                            min = "0" + "" + time_min;
                            monday_add1_1 = hourOfDay + ":" + min;
                        } else {
                            monday_add1_1 = hourOfDay + ":" + minute;
                        }
                        Log.d("Time===>", "" + monday_add1_1);
                        //editText_start_time.setText(time_active);
                        text_monday_add1_1.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + monday_add1_1 + "</u>  </font>"));

                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();

    }

    public void get_monday_add1_2() {
        // Get Current Time*
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        mSeconds = c.get(Calendar.SECOND);
        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, android.R.style.Theme_Holo_Dialog_MinWidth,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        // view.setBackgroundColor(getResources().getColor(R.color.button_background));
                        String time_hr = String.valueOf(hourOfDay);
                        String time_min = String.valueOf(minute);
                        String min = null, hr = null;
                        if (time_hr.length() == 1 && time_min.length() == 1) {
                            min = "0" + "" + time_min;
                            hr = "0" + "" + time_hr;
                            monday_add1_2 = hr + ":" + min;
                        } else if (time_hr.length() == 1 && time_min.length() != 1) {
                            hr = "0" + "" + time_hr;
                            monday_add1_2 = hr + ":" + minute;
                        } else if (time_hr.length() != 1 && time_min.length() == 1) {
                            min = "0" + "" + time_min;
                            monday_add1_2 = hourOfDay + ":" + min;
                        } else {
                            monday_add1_2 = hourOfDay + ":" + minute;
                        }
                        Log.d("Time===>", "" + monday_add1_2);
                        //editText_start_time.setText(time_active);
                        text_monday_add1_2.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + monday_add1_2 + "</u>  </font>"));

                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();

    }

    //Thursday
    public void get_monday_add2_1() {
        // Get Current Time*
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        mSeconds = c.get(Calendar.SECOND);
        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, android.R.style.Theme_Holo_Dialog_MinWidth,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        // view.setBackgroundColor(getResources().getColor(R.color.button_background));
                        String time_hr = String.valueOf(hourOfDay);
                        String time_min = String.valueOf(minute);
                        String min = null, hr = null;
                        if (time_hr.length() == 1 && time_min.length() == 1) {
                            min = "0" + "" + time_min;
                            hr = "0" + "" + time_hr;
                            monday_add2_1 = hr + ":" + min;
                        } else if (time_hr.length() == 1 && time_min.length() != 1) {
                            hr = "0" + "" + time_hr;
                            monday_add2_1 = hr + ":" + minute;
                        } else if (time_hr.length() != 1 && time_min.length() == 1) {
                            min = "0" + "" + time_min;
                            monday_add2_1 = hourOfDay + ":" + min;
                        } else {
                            monday_add2_1 = hourOfDay + ":" + minute;
                        }
                        Log.d("Time===>", "" + monday_add2_1);
                        //editText_start_time.setText(time_active);
                        text_monday_add2_1.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + monday_add2_1 + "</u>  </font>"));

                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();

    }

    public void get_monday_add2_2() {
        // Get Current Time*
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        mSeconds = c.get(Calendar.SECOND);
        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, android.R.style.Theme_Holo_Dialog_MinWidth,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        // view.setBackgroundColor(getResources().getColor(R.color.button_background));
                        String time_hr = String.valueOf(hourOfDay);
                        String time_min = String.valueOf(minute);
                        String min = null, hr = null;
                        if (time_hr.length() == 1 && time_min.length() == 1) {
                            min = "0" + "" + time_min;
                            hr = "0" + "" + time_hr;
                            monday_add2_2 = hr + ":" + min;
                        } else if (time_hr.length() == 1 && time_min.length() != 1) {
                            hr = "0" + "" + time_hr;
                            monday_add2_2 = hr + ":" + minute;
                        } else if (time_hr.length() != 1 && time_min.length() == 1) {
                            min = "0" + "" + time_min;
                            monday_add2_2 = hourOfDay + ":" + min;
                        } else {
                            monday_add2_2 = hourOfDay + ":" + minute;
                        }
                        Log.d("Time===>", "" + monday_add2_2);
                        //editText_start_time.setText(time_active);
                        text_monday_add2_2.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + monday_add2_2 + "</u>  </font>"));

                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();

    }


    public void get_monday_add3_1() {
        // Get Current Time*
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        mSeconds = c.get(Calendar.SECOND);
        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, android.R.style.Theme_Holo_Dialog_MinWidth,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        // view.setBackgroundColor(getResources().getColor(R.color.button_background));
                        String time_hr = String.valueOf(hourOfDay);
                        String time_min = String.valueOf(minute);
                        String min = null, hr = null;
                        if (time_hr.length() == 1 && time_min.length() == 1) {
                            min = "0" + "" + time_min;
                            hr = "0" + "" + time_hr;
                            monday_add3_1 = hr + ":" + min;
                        } else if (time_hr.length() == 1 && time_min.length() != 1) {
                            hr = "0" + "" + time_hr;
                            monday_add3_1 = hr + ":" + minute;
                        } else if (time_hr.length() != 1 && time_min.length() == 1) {
                            min = "0" + "" + time_min;
                            monday_add3_1 = hourOfDay + ":" + min;
                        } else {
                            monday_add3_1 = hourOfDay + ":" + minute;
                        }
                        Log.d("Time===>", "" + monday_add3_1);
                        //editText_start_time.setText(time_active);
                        text_monday_add3_1.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + monday_add3_1 + "</u>  </font>"));

                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();

    }

    public void get_monday_add3_2() {
        // Get Current Time*
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        mSeconds = c.get(Calendar.SECOND);
        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, android.R.style.Theme_Holo_Dialog_MinWidth,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        // view.setBackgroundColor(getResources().getColor(R.color.button_background));
                        String time_hr = String.valueOf(hourOfDay);
                        String time_min = String.valueOf(minute);
                        String min = null, hr = null;
                        if (time_hr.length() == 1 && time_min.length() == 1) {
                            min = "0" + "" + time_min;
                            hr = "0" + "" + time_hr;
                            monday_add3_2 = hr + ":" + min;
                        } else if (time_hr.length() == 1 && time_min.length() != 1) {
                            hr = "0" + "" + time_hr;
                            monday_add3_2 = hr + ":" + minute;
                        } else if (time_hr.length() != 1 && time_min.length() == 1) {
                            min = "0" + "" + time_min;
                            monday_add3_2 = hourOfDay + ":" + min;
                        } else {
                            monday_add3_2 = hourOfDay + ":" + minute;
                        }
                        Log.d("Time===>", "" + monday_add3_2);
                        //editText_start_time.setText(time_active);
                        text_monday_add3_2.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + monday_add3_2 + "</u>  </font>"));

                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();

    }


    public void get_tuesday_add1_1() {
        // Get Current Time*
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        mSeconds = c.get(Calendar.SECOND);
        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, android.R.style.Theme_Holo_Dialog_MinWidth,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        // view.setBackgroundColor(getResources().getColor(R.color.button_background));
                        String time_hr = String.valueOf(hourOfDay);
                        String time_min = String.valueOf(minute);
                        String min = null, hr = null;
                        if (time_hr.length() == 1 && time_min.length() == 1) {
                            min = "0" + "" + time_min;
                            hr = "0" + "" + time_hr;
                            tuesday_add1_1 = hr + ":" + min;
                        } else if (time_hr.length() == 1 && time_min.length() != 1) {
                            hr = "0" + "" + time_hr;
                            tuesday_add1_1 = hr + ":" + minute;
                        } else if (time_hr.length() != 1 && time_min.length() == 1) {
                            min = "0" + "" + time_min;
                            tuesday_add1_1 = hourOfDay + ":" + min;
                        } else {
                            tuesday_add1_1 = hourOfDay + ":" + minute;
                        }
                        Log.d("Time===>", "" + tuesday_add1_1);
                        //editText_start_time.setText(time_active);
                        text_tuesday_add1_1.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + tuesday_add1_1 + "</u>  </font>"));

                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();

    }

    public void get_tuesday_add1_2() {
        // Get Current Time*
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        mSeconds = c.get(Calendar.SECOND);
        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, android.R.style.Theme_Holo_Dialog_MinWidth,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        // view.setBackgroundColor(getResources().getColor(R.color.button_background));
                        String time_hr = String.valueOf(hourOfDay);
                        String time_min = String.valueOf(minute);
                        String min = null, hr = null;
                        if (time_hr.length() == 1 && time_min.length() == 1) {
                            min = "0" + "" + time_min;
                            hr = "0" + "" + time_hr;
                            tuesday_add1_2 = hr + ":" + min;
                        } else if (time_hr.length() == 1 && time_min.length() != 1) {
                            hr = "0" + "" + time_hr;
                            tuesday_add1_2 = hr + ":" + minute;
                        } else if (time_hr.length() != 1 && time_min.length() == 1) {
                            min = "0" + "" + time_min;
                            tuesday_add1_2 = hourOfDay + ":" + min;
                        } else {
                            tuesday_add1_2 = hourOfDay + ":" + minute;
                        }
                        Log.d("Time===>", "" + tuesday_add1_2);
                        //editText_start_time.setText(time_active);
                        text_tuesday_add1_2.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + tuesday_add1_2 + "</u>  </font>"));

                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();

    }


    public void get_tuesday_add2_1() {
        // Get Current Time*
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        mSeconds = c.get(Calendar.SECOND);
        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, android.R.style.Theme_Holo_Dialog_MinWidth,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        // view.setBackgroundColor(getResources().getColor(R.color.button_background));
                        String time_hr = String.valueOf(hourOfDay);
                        String time_min = String.valueOf(minute);
                        String min = null, hr = null;
                        if (time_hr.length() == 1 && time_min.length() == 1) {
                            min = "0" + "" + time_min;
                            hr = "0" + "" + time_hr;
                            tuesday_add2_1 = hr + ":" + min;
                        } else if (time_hr.length() == 1 && time_min.length() != 1) {
                            hr = "0" + "" + time_hr;
                            tuesday_add2_1 = hr + ":" + minute;
                        } else if (time_hr.length() != 1 && time_min.length() == 1) {
                            min = "0" + "" + time_min;
                            tuesday_add2_1 = hourOfDay + ":" + min;
                        } else {
                            tuesday_add2_1 = hourOfDay + ":" + minute;
                        }
                        Log.d("Time===>", "" + tuesday_add2_1);
                        //editText_start_time.setText(time_active);
                        text_tuesday_add2_1.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + tuesday_add2_1 + "</u>  </font>"));

                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();

    }


    public void get_tuesday_add2_2() {
        // Get Current Time*
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        mSeconds = c.get(Calendar.SECOND);
        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, android.R.style.Theme_Holo_Dialog_MinWidth,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        // view.setBackgroundColor(getResources().getColor(R.color.button_background));
                        String time_hr = String.valueOf(hourOfDay);
                        String time_min = String.valueOf(minute);
                        String min = null, hr = null;
                        if (time_hr.length() == 1 && time_min.length() == 1) {
                            min = "0" + "" + time_min;
                            hr = "0" + "" + time_hr;
                            tuesday_add2_2 = hr + ":" + min;
                        } else if (time_hr.length() == 1 && time_min.length() != 1) {
                            hr = "0" + "" + time_hr;
                            tuesday_add2_2 = hr + ":" + minute;
                        } else if (time_hr.length() != 1 && time_min.length() == 1) {
                            min = "0" + "" + time_min;
                            tuesday_add2_2 = hourOfDay + ":" + min;
                        } else {
                            tuesday_add2_2 = hourOfDay + ":" + minute;
                        }
                        Log.d("Time===>", "" + tuesday_add2_2);
                        //editText_start_time.setText(time_active);
                        text_tuesday_add2_2.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + tuesday_add2_2 + "</u>  </font>"));

                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();

    }

    public void get_tuesday_add3_1() {
        // Get Current Time*
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        mSeconds = c.get(Calendar.SECOND);
        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, android.R.style.Theme_Holo_Dialog_MinWidth,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        // view.setBackgroundColor(getResources().getColor(R.color.button_background));
                        String time_hr = String.valueOf(hourOfDay);
                        String time_min = String.valueOf(minute);
                        String min = null, hr = null;
                        if (time_hr.length() == 1 && time_min.length() == 1) {
                            min = "0" + "" + time_min;
                            hr = "0" + "" + time_hr;
                            tuesday_add3_1 = hr + ":" + min;
                        } else if (time_hr.length() == 1 && time_min.length() != 1) {
                            hr = "0" + "" + time_hr;
                            tuesday_add3_1 = hr + ":" + minute;
                        } else if (time_hr.length() != 1 && time_min.length() == 1) {
                            min = "0" + "" + time_min;
                            tuesday_add3_1 = hourOfDay + ":" + min;
                        } else {
                            tuesday_add3_1 = hourOfDay + ":" + minute;
                        }
                        Log.d("Time===>", "" + tuesday_add3_1);
                        //editText_start_time.setText(time_active);
                        text_tuesday_add3_1.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + tuesday_add3_1 + "</u>  </font>"));

                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }

    public void get_tuesday_add3_2() {
        // Get Current Time*
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        mSeconds = c.get(Calendar.SECOND);
        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, android.R.style.Theme_Holo_Dialog_MinWidth,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        // view.setBackgroundColor(getResources().getColor(R.color.button_background));
                        String time_hr = String.valueOf(hourOfDay);
                        String time_min = String.valueOf(minute);
                        String min = null, hr = null;
                        if (time_hr.length() == 1 && time_min.length() == 1) {
                            min = "0" + "" + time_min;
                            hr = "0" + "" + time_hr;
                            tuesday_add3_2 = hr + ":" + min;
                        } else if (time_hr.length() == 1 && time_min.length() != 1) {
                            hr = "0" + "" + time_hr;
                            tuesday_add3_2 = hr + ":" + minute;
                        } else if (time_hr.length() != 1 && time_min.length() == 1) {
                            min = "0" + "" + time_min;
                            tuesday_add3_2 = hourOfDay + ":" + min;
                        } else {
                            tuesday_add3_2 = hourOfDay + ":" + minute;
                        }
                        Log.d("Time===>", "" + tuesday_add3_1);
                        //editText_start_time.setText(time_active);
                        text_tuesday_add3_2.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + tuesday_add3_2 + "</u>  </font>"));

                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }


    public void get_wednesday_add1_1() {
        // Get Current Time*
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        mSeconds = c.get(Calendar.SECOND);
        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, android.R.style.Theme_Holo_Dialog_MinWidth,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        // view.setBackgroundColor(getResources().getColor(R.color.button_background));
                        String time_hr = String.valueOf(hourOfDay);
                        String time_min = String.valueOf(minute);
                        String min = null, hr = null;
                        if (time_hr.length() == 1 && time_min.length() == 1) {
                            min = "0" + "" + time_min;
                            hr = "0" + "" + time_hr;
                            wednesday_add1_1 = hr + ":" + min;
                        } else if (time_hr.length() == 1 && time_min.length() != 1) {
                            hr = "0" + "" + time_hr;
                            wednesday_add1_1 = hr + ":" + minute;
                        } else if (time_hr.length() != 1 && time_min.length() == 1) {
                            min = "0" + "" + time_min;
                            wednesday_add1_1 = hourOfDay + ":" + min;
                        } else {
                            wednesday_add1_1 = hourOfDay + ":" + minute;
                        }
                        Log.d("Time===>", "" + wednesday_add1_1);
                        //editText_start_time.setText(time_active);
                        text_wednesday_add1_1.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + wednesday_add1_1 + "</u>  </font>"));

                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }

    public void get_wednesday_add1_2() {
        // Get Current Time*
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        mSeconds = c.get(Calendar.SECOND);
        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, android.R.style.Theme_Holo_Dialog_MinWidth,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        // view.setBackgroundColor(getResources().getColor(R.color.button_background));
                        String time_hr = String.valueOf(hourOfDay);
                        String time_min = String.valueOf(minute);
                        String min = null, hr = null;
                        if (time_hr.length() == 1 && time_min.length() == 1) {
                            min = "0" + "" + time_min;
                            hr = "0" + "" + time_hr;
                            wednesday_add1_2 = hr + ":" + min;
                        } else if (time_hr.length() == 1 && time_min.length() != 1) {
                            hr = "0" + "" + time_hr;
                            wednesday_add1_2 = hr + ":" + minute;
                        } else if (time_hr.length() != 1 && time_min.length() == 1) {
                            min = "0" + "" + time_min;
                            wednesday_add1_2 = hourOfDay + ":" + min;
                        } else {
                            wednesday_add1_2 = hourOfDay + ":" + minute;
                        }
                        Log.d("Time===>", "" + wednesday_add1_2);
                        //editText_start_time.setText(time_active);
                        text_wednesday_add1_2.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + wednesday_add1_2 + "</u>  </font>"));

                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }

    public void get_wednesday_add2_1() {
        // Get Current Time*
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        mSeconds = c.get(Calendar.SECOND);
        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, android.R.style.Theme_Holo_Dialog_MinWidth,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        // view.setBackgroundColor(getResources().getColor(R.color.button_background));
                        String time_hr = String.valueOf(hourOfDay);
                        String time_min = String.valueOf(minute);
                        String min = null, hr = null;
                        if (time_hr.length() == 1 && time_min.length() == 1) {
                            min = "0" + "" + time_min;
                            hr = "0" + "" + time_hr;
                            wednesday_add2_1 = hr + ":" + min;
                        } else if (time_hr.length() == 1 && time_min.length() != 1) {
                            hr = "0" + "" + time_hr;
                            wednesday_add2_1 = hr + ":" + minute;
                        } else if (time_hr.length() != 1 && time_min.length() == 1) {
                            min = "0" + "" + time_min;
                            wednesday_add2_1 = hourOfDay + ":" + min;
                        } else {
                            wednesday_add2_1 = hourOfDay + ":" + minute;
                        }
                        Log.d("Time===>", "" + wednesday_add2_1);
                        //editText_start_time.setText(time_active);
                        text_wednesday_add2_1.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + wednesday_add2_1 + "</u>  </font>"));

                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }

    public void get_wednesday_add2_2() {
        // Get Current Time*
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        mSeconds = c.get(Calendar.SECOND);
        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, android.R.style.Theme_Holo_Dialog_MinWidth,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        // view.setBackgroundColor(getResources().getColor(R.color.button_background));
                        String time_hr = String.valueOf(hourOfDay);
                        String time_min = String.valueOf(minute);
                        String min = null, hr = null;
                        if (time_hr.length() == 1 && time_min.length() == 1) {
                            min = "0" + "" + time_min;
                            hr = "0" + "" + time_hr;
                            wednesday_add2_2 = hr + ":" + min;
                        } else if (time_hr.length() == 1 && time_min.length() != 1) {
                            hr = "0" + "" + time_hr;
                            wednesday_add2_2 = hr + ":" + minute;
                        } else if (time_hr.length() != 1 && time_min.length() == 1) {
                            min = "0" + "" + time_min;
                            wednesday_add2_2 = hourOfDay + ":" + min;
                        } else {
                            wednesday_add2_2 = hourOfDay + ":" + minute;
                        }
                        Log.d("Time===>", "" + wednesday_add2_2);
                        //editText_start_time.setText(time_active);
                        text_wednesday_add2_2.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + wednesday_add2_2 + "</u>  </font>"));

                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }

    public void get_wednesday_add3_1() {
        // Get Current Time*
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        mSeconds = c.get(Calendar.SECOND);
        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, android.R.style.Theme_Holo_Dialog_MinWidth,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        // view.setBackgroundColor(getResources().getColor(R.color.button_background));
                        String time_hr = String.valueOf(hourOfDay);
                        String time_min = String.valueOf(minute);
                        String min = null, hr = null;
                        if (time_hr.length() == 1 && time_min.length() == 1) {
                            min = "0" + "" + time_min;
                            hr = "0" + "" + time_hr;
                            wednesday_add3_1 = hr + ":" + min;
                        } else if (time_hr.length() == 1 && time_min.length() != 1) {
                            hr = "0" + "" + time_hr;
                            wednesday_add3_1 = hr + ":" + minute;
                        } else if (time_hr.length() != 1 && time_min.length() == 1) {
                            min = "0" + "" + time_min;
                            wednesday_add3_1 = hourOfDay + ":" + min;
                        } else {
                            wednesday_add3_1 = hourOfDay + ":" + minute;
                        }
                        Log.d("Time===>", "" + wednesday_add3_1);
                        //editText_start_time.setText(time_active);
                        text_wednesday_add3_1.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + wednesday_add3_1 + "</u>  </font>"));

                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }

    public void get_wednesday_add3_2() {
        // Get Current Time*
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        mSeconds = c.get(Calendar.SECOND);
        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, android.R.style.Theme_Holo_Dialog_MinWidth,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        // view.setBackgroundColor(getResources().getColor(R.color.button_background));
                        String time_hr = String.valueOf(hourOfDay);
                        String time_min = String.valueOf(minute);
                        String min = null, hr = null;
                        if (time_hr.length() == 1 && time_min.length() == 1) {
                            min = "0" + "" + time_min;
                            hr = "0" + "" + time_hr;
                            wednesday_add3_2 = hr + ":" + min;
                        } else if (time_hr.length() == 1 && time_min.length() != 1) {
                            hr = "0" + "" + time_hr;
                            wednesday_add3_2 = hr + ":" + minute;
                        } else if (time_hr.length() != 1 && time_min.length() == 1) {
                            min = "0" + "" + time_min;
                            wednesday_add3_2 = hourOfDay + ":" + min;
                        } else {
                            wednesday_add3_2 = hourOfDay + ":" + minute;
                        }
                        Log.d("Time===>", "" + wednesday_add3_2);
                        //editText_start_time.setText(time_active);
                        text_wednesday_add3_2.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + wednesday_add3_2 + "</u>  </font>"));

                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }


    public void get_thursday_add1_1() {
        // Get Current Time*
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        mSeconds = c.get(Calendar.SECOND);
        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, android.R.style.Theme_Holo_Dialog_MinWidth,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        // view.setBackgroundColor(getResources().getColor(R.color.button_background));
                        String time_hr = String.valueOf(hourOfDay);
                        String time_min = String.valueOf(minute);
                        String min = null, hr = null;
                        if (time_hr.length() == 1 && time_min.length() == 1) {
                            min = "0" + "" + time_min;
                            hr = "0" + "" + time_hr;
                            thursday_add1_1 = hr + ":" + min;
                        } else if (time_hr.length() == 1 && time_min.length() != 1) {
                            hr = "0" + "" + time_hr;
                            thursday_add1_1 = hr + ":" + minute;
                        } else if (time_hr.length() != 1 && time_min.length() == 1) {
                            min = "0" + "" + time_min;
                            thursday_add1_1 = hourOfDay + ":" + min;
                        } else {
                            thursday_add1_1 = hourOfDay + ":" + minute;
                        }
                        Log.d("Time===>", "" + thursday_add1_1);
                        //editText_start_time.setText(time_active);
                        text_thursday_add1_1.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + thursday_add1_1 + "</u>  </font>"));

                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }


    public void get_thursday_add1_2() {
        // Get Current Time*
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        mSeconds = c.get(Calendar.SECOND);
        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, android.R.style.Theme_Holo_Dialog_MinWidth,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        // view.setBackgroundColor(getResources().getColor(R.color.button_background));
                        String time_hr = String.valueOf(hourOfDay);
                        String time_min = String.valueOf(minute);
                        String min = null, hr = null;
                        if (time_hr.length() == 1 && time_min.length() == 1) {
                            min = "0" + "" + time_min;
                            hr = "0" + "" + time_hr;
                            thursday_add1_2 = hr + ":" + min;
                        } else if (time_hr.length() == 1 && time_min.length() != 1) {
                            hr = "0" + "" + time_hr;
                            thursday_add1_2 = hr + ":" + minute;
                        } else if (time_hr.length() != 1 && time_min.length() == 1) {
                            min = "0" + "" + time_min;
                            thursday_add1_2 = hourOfDay + ":" + min;
                        } else {
                            thursday_add1_2 = hourOfDay + ":" + minute;
                        }
                        Log.d("Time===>", "" + thursday_add1_2);
                        //editText_start_time.setText(time_active);
                        text_thursday_add1_2.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + thursday_add1_2 + "</u>  </font>"));

                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }


    public void get_thursday_add2_1() {
        // Get Current Time*
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        mSeconds = c.get(Calendar.SECOND);
        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, android.R.style.Theme_Holo_Dialog_MinWidth,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        // view.setBackgroundColor(getResources().getColor(R.color.button_background));
                        String time_hr = String.valueOf(hourOfDay);
                        String time_min = String.valueOf(minute);
                        String min = null, hr = null;
                        if (time_hr.length() == 1 && time_min.length() == 1) {
                            min = "0" + "" + time_min;
                            hr = "0" + "" + time_hr;
                            thursday_add2_1 = hr + ":" + min;
                        } else if (time_hr.length() == 1 && time_min.length() != 1) {
                            hr = "0" + "" + time_hr;
                            thursday_add2_1 = hr + ":" + minute;
                        } else if (time_hr.length() != 1 && time_min.length() == 1) {
                            min = "0" + "" + time_min;
                            thursday_add2_1 = hourOfDay + ":" + min;
                        } else {
                            thursday_add2_1 = hourOfDay + ":" + minute;
                        }
                        Log.d("Time===>", "" + thursday_add2_1);
                        //editText_start_time.setText(time_active);
                        text_thursday_add2_1.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + thursday_add2_1 + "</u>  </font>"));

                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }


    public void get_thursday_add2_2() {
        // Get Current Time*
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        mSeconds = c.get(Calendar.SECOND);
        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, android.R.style.Theme_Holo_Dialog_MinWidth,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        // view.setBackgroundColor(getResources().getColor(R.color.button_background));
                        String time_hr = String.valueOf(hourOfDay);
                        String time_min = String.valueOf(minute);
                        String min = null, hr = null;
                        if (time_hr.length() == 1 && time_min.length() == 1) {
                            min = "0" + "" + time_min;
                            hr = "0" + "" + time_hr;
                            thursday_add2_2 = hr + ":" + min;
                        } else if (time_hr.length() == 1 && time_min.length() != 1) {
                            hr = "0" + "" + time_hr;
                            thursday_add2_2 = hr + ":" + minute;
                        } else if (time_hr.length() != 1 && time_min.length() == 1) {
                            min = "0" + "" + time_min;
                            thursday_add2_2 = hourOfDay + ":" + min;
                        } else {
                            thursday_add2_2 = hourOfDay + ":" + minute;
                        }
                        Log.d("Time===>", "" + thursday_add2_2);
                        //editText_start_time.setText(time_active);
                        text_thursday_add2_2.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + thursday_add2_2 + "</u>  </font>"));

                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }


    public void get_thursday_add3_1() {
        // Get Current Time*
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        mSeconds = c.get(Calendar.SECOND);
        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, android.R.style.Theme_Holo_Dialog_MinWidth,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        // view.setBackgroundColor(getResources().getColor(R.color.button_background));
                        String time_hr = String.valueOf(hourOfDay);
                        String time_min = String.valueOf(minute);
                        String min = null, hr = null;
                        if (time_hr.length() == 1 && time_min.length() == 1) {
                            min = "0" + "" + time_min;
                            hr = "0" + "" + time_hr;
                            thursday_add3_1 = hr + ":" + min;
                        } else if (time_hr.length() == 1 && time_min.length() != 1) {
                            hr = "0" + "" + time_hr;
                            thursday_add3_1 = hr + ":" + minute;
                        } else if (time_hr.length() != 1 && time_min.length() == 1) {
                            min = "0" + "" + time_min;
                            thursday_add3_1 = hourOfDay + ":" + min;
                        } else {
                            thursday_add3_1 = hourOfDay + ":" + minute;
                        }
                        Log.d("Time===>", "" + thursday_add3_1);
                        //editText_start_time.setText(time_active);
                        text_thursday_add3_1.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + thursday_add3_1 + "</u>  </font>"));

                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }

    public void get_thursday_add3_2() {
        // Get Current Time*
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        mSeconds = c.get(Calendar.SECOND);
        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, android.R.style.Theme_Holo_Dialog_MinWidth,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        // view.setBackgroundColor(getResources().getColor(R.color.button_background));
                        String time_hr = String.valueOf(hourOfDay);
                        String time_min = String.valueOf(minute);
                        String min = null, hr = null;
                        if (time_hr.length() == 1 && time_min.length() == 1) {
                            min = "0" + "" + time_min;
                            hr = "0" + "" + time_hr;
                            thursday_add3_2 = hr + ":" + min;
                        } else if (time_hr.length() == 1 && time_min.length() != 1) {
                            hr = "0" + "" + time_hr;
                            thursday_add3_2 = hr + ":" + minute;
                        } else if (time_hr.length() != 1 && time_min.length() == 1) {
                            min = "0" + "" + time_min;
                            thursday_add3_2 = hourOfDay + ":" + min;
                        } else {
                            thursday_add3_2 = hourOfDay + ":" + minute;
                        }
                        Log.d("Time===>", "" + thursday_add3_2);
                        //editText_start_time.setText(time_active);
                        text_thursday_add3_2.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + thursday_add3_2 + "</u>  </font>"));

                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }


    public void get_friday_add1_1() {
        // Get Current Time*
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        mSeconds = c.get(Calendar.SECOND);
        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, android.R.style.Theme_Holo_Dialog_MinWidth,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        // view.setBackgroundColor(getResources().getColor(R.color.button_background));
                        String time_hr = String.valueOf(hourOfDay);
                        String time_min = String.valueOf(minute);
                        String min = null, hr = null;
                        if (time_hr.length() == 1 && time_min.length() == 1) {
                            min = "0" + "" + time_min;
                            hr = "0" + "" + time_hr;
                            friday_add1_1 = hr + ":" + min;
                        } else if (time_hr.length() == 1 && time_min.length() != 1) {
                            hr = "0" + "" + time_hr;
                            friday_add1_1 = hr + ":" + minute;
                        } else if (time_hr.length() != 1 && time_min.length() == 1) {
                            min = "0" + "" + time_min;
                            friday_add1_1 = hourOfDay + ":" + min;
                        } else {
                            friday_add1_1 = hourOfDay + ":" + minute;
                        }
                        Log.d("Time===>", "" + friday_add1_1);
                        //editText_start_time.setText(time_active);
                        text_friday_add1_1.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + friday_add1_1 + "</u>  </font>"));

                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }


    public void get_friday_add1_2() {
        // Get Current Time*
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        mSeconds = c.get(Calendar.SECOND);
        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, android.R.style.Theme_Holo_Dialog_MinWidth,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        // view.setBackgroundColor(getResources().getColor(R.color.button_background));
                        String time_hr = String.valueOf(hourOfDay);
                        String time_min = String.valueOf(minute);
                        String min = null, hr = null;
                        if (time_hr.length() == 1 && time_min.length() == 1) {
                            min = "0" + "" + time_min;
                            hr = "0" + "" + time_hr;
                            friday_add1_2 = hr + ":" + min;
                        } else if (time_hr.length() == 1 && time_min.length() != 1) {
                            hr = "0" + "" + time_hr;
                            friday_add1_2 = hr + ":" + minute;
                        } else if (time_hr.length() != 1 && time_min.length() == 1) {
                            min = "0" + "" + time_min;
                            friday_add1_2 = hourOfDay + ":" + min;
                        } else {
                            friday_add1_2 = hourOfDay + ":" + minute;
                        }
                        Log.d("Time===>", "" + friday_add1_2);
                        //editText_start_time.setText(time_active);
                        text_friday_add1_2.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + friday_add1_2 + "</u>  </font>"));

                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }


    public void get_friday_add2_1() {
        // Get Current Time*
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        mSeconds = c.get(Calendar.SECOND);
        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, android.R.style.Theme_Holo_Dialog_MinWidth,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        // view.setBackgroundColor(getResources().getColor(R.color.button_background));
                        String time_hr = String.valueOf(hourOfDay);
                        String time_min = String.valueOf(minute);
                        String min = null, hr = null;
                        if (time_hr.length() == 1 && time_min.length() == 1) {
                            min = "0" + "" + time_min;
                            hr = "0" + "" + time_hr;
                            friday_add2_1 = hr + ":" + min;
                        } else if (time_hr.length() == 1 && time_min.length() != 1) {
                            hr = "0" + "" + time_hr;
                            friday_add2_1 = hr + ":" + minute;
                        } else if (time_hr.length() != 1 && time_min.length() == 1) {
                            min = "0" + "" + time_min;
                            friday_add2_1 = hourOfDay + ":" + min;
                        } else {
                            friday_add2_1 = hourOfDay + ":" + minute;
                        }
                        Log.d("Time===>", "" + friday_add2_1);
                        //editText_start_time.setText(time_active);
                        text_friday_add2_1.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + friday_add2_1 + "</u>  </font>"));

                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }

    public void get_friday_add2_2() {
        // Get Current Time*
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        mSeconds = c.get(Calendar.SECOND);
        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, android.R.style.Theme_Holo_Dialog_MinWidth,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        // view.setBackgroundColor(getResources().getColor(R.color.button_background));
                        String time_hr = String.valueOf(hourOfDay);
                        String time_min = String.valueOf(minute);
                        String min = null, hr = null;
                        if (time_hr.length() == 1 && time_min.length() == 1) {
                            min = "0" + "" + time_min;
                            hr = "0" + "" + time_hr;
                            friday_add2_2 = hr + ":" + min;
                        } else if (time_hr.length() == 1 && time_min.length() != 1) {
                            hr = "0" + "" + time_hr;
                            friday_add2_2 = hr + ":" + minute;
                        } else if (time_hr.length() != 1 && time_min.length() == 1) {
                            min = "0" + "" + time_min;
                            friday_add2_2 = hourOfDay + ":" + min;
                        } else {
                            friday_add2_2 = hourOfDay + ":" + minute;
                        }
                        Log.d("Time===>", "" + friday_add2_2);
                        //editText_start_time.setText(time_active);
                        text_friday_add2_2.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + friday_add2_2 + "</u>  </font>"));

                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }

    public void get_friday_add3_1() {
        // Get Current Time*
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        mSeconds = c.get(Calendar.SECOND);
        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, android.R.style.Theme_Holo_Dialog_MinWidth,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        // view.setBackgroundColor(getResources().getColor(R.color.button_background));
                        String time_hr = String.valueOf(hourOfDay);
                        String time_min = String.valueOf(minute);
                        String min = null, hr = null;
                        if (time_hr.length() == 1 && time_min.length() == 1) {
                            min = "0" + "" + time_min;
                            hr = "0" + "" + time_hr;
                            friday_add3_1 = hr + ":" + min;
                        } else if (time_hr.length() == 1 && time_min.length() != 1) {
                            hr = "0" + "" + time_hr;
                            friday_add3_1 = hr + ":" + minute;
                        } else if (time_hr.length() != 1 && time_min.length() == 1) {
                            min = "0" + "" + time_min;
                            friday_add3_1 = hourOfDay + ":" + min;
                        } else {
                            friday_add3_1 = hourOfDay + ":" + minute;
                        }
                        Log.d("Time===>", "" + friday_add3_1);
                        //editText_start_time.setText(time_active);
                        text_friday_add3_1.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + friday_add3_1 + "</u>  </font>"));

                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }

    public void get_friday_add3_2() {
        // Get Current Time*
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        mSeconds = c.get(Calendar.SECOND);
        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, android.R.style.Theme_Holo_Dialog_MinWidth,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        // view.setBackgroundColor(getResources().getColor(R.color.button_background));
                        String time_hr = String.valueOf(hourOfDay);
                        String time_min = String.valueOf(minute);
                        String min = null, hr = null;
                        if (time_hr.length() == 1 && time_min.length() == 1) {
                            min = "0" + "" + time_min;
                            hr = "0" + "" + time_hr;
                            friday_add3_2 = hr + ":" + min;
                        } else if (time_hr.length() == 1 && time_min.length() != 1) {
                            hr = "0" + "" + time_hr;
                            friday_add3_2 = hr + ":" + minute;
                        } else if (time_hr.length() != 1 && time_min.length() == 1) {
                            min = "0" + "" + time_min;
                            friday_add3_2 = hourOfDay + ":" + min;
                        } else {
                            friday_add3_2 = hourOfDay + ":" + minute;
                        }
                        Log.d("Time===>", "" + friday_add3_2);
                        //editText_start_time.setText(time_active);
                        text_friday_add3_2.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + friday_add3_2 + "</u>  </font>"));

                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }


    public void get_saturday_add1_1() {
        // Get Current Time*
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        mSeconds = c.get(Calendar.SECOND);
        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, android.R.style.Theme_Holo_Dialog_MinWidth,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        // view.setBackgroundColor(getResources().getColor(R.color.button_background));
                        String time_hr = String.valueOf(hourOfDay);
                        String time_min = String.valueOf(minute);
                        String min = null, hr = null;
                        if (time_hr.length() == 1 && time_min.length() == 1) {
                            min = "0" + "" + time_min;
                            hr = "0" + "" + time_hr;
                            saturday_add1_1 = hr + ":" + min;
                        } else if (time_hr.length() == 1 && time_min.length() != 1) {
                            hr = "0" + "" + time_hr;
                            saturday_add1_1 = hr + ":" + minute;
                        } else if (time_hr.length() != 1 && time_min.length() == 1) {
                            min = "0" + "" + time_min;
                            saturday_add1_1 = hourOfDay + ":" + min;
                        } else {
                            saturday_add1_1 = hourOfDay + ":" + minute;
                        }
                        Log.d("Time===>", "" + saturday_add1_1);
                        //editText_start_time.setText(time_active);
                        text_saturday_add1_1.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + saturday_add1_1 + "</u>  </font>"));

                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }

    public void get_saturday_add1_2() {
        // Get Current Time*
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        mSeconds = c.get(Calendar.SECOND);
        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, android.R.style.Theme_Holo_Dialog_MinWidth,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        // view.setBackgroundColor(getResources().getColor(R.color.button_background));
                        String time_hr = String.valueOf(hourOfDay);
                        String time_min = String.valueOf(minute);
                        String min = null, hr = null;
                        if (time_hr.length() == 1 && time_min.length() == 1) {
                            min = "0" + "" + time_min;
                            hr = "0" + "" + time_hr;
                            saturday_add1_2 = hr + ":" + min;
                        } else if (time_hr.length() == 1 && time_min.length() != 1) {
                            hr = "0" + "" + time_hr;
                            saturday_add1_2 = hr + ":" + minute;
                        } else if (time_hr.length() != 1 && time_min.length() == 1) {
                            min = "0" + "" + time_min;
                            saturday_add1_2 = hourOfDay + ":" + min;
                        } else {
                            saturday_add1_2 = hourOfDay + ":" + minute;
                        }
                        Log.d("Time===>", "" + saturday_add1_2);
                        //editText_start_time.setText(time_active);
                        text_saturday_add1_2.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + saturday_add1_2 + "</u>  </font>"));

                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }

    public void get_saturday_add2_1() {
        // Get Current Time*
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        mSeconds = c.get(Calendar.SECOND);
        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, android.R.style.Theme_Holo_Dialog_MinWidth,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        // view.setBackgroundColor(getResources().getColor(R.color.button_background));
                        String time_hr = String.valueOf(hourOfDay);
                        String time_min = String.valueOf(minute);
                        String min = null, hr = null;
                        if (time_hr.length() == 1 && time_min.length() == 1) {
                            min = "0" + "" + time_min;
                            hr = "0" + "" + time_hr;
                            saturday_add2_1 = hr + ":" + min;
                        } else if (time_hr.length() == 1 && time_min.length() != 1) {
                            hr = "0" + "" + time_hr;
                            saturday_add2_1 = hr + ":" + minute;
                        } else if (time_hr.length() != 1 && time_min.length() == 1) {
                            min = "0" + "" + time_min;
                            saturday_add2_1 = hourOfDay + ":" + min;
                        } else {
                            saturday_add2_1 = hourOfDay + ":" + minute;
                        }
                        Log.d("Time===>", "" + saturday_add2_1);
                        //editText_start_time.setText(time_active);
                        text_saturday_add2_1.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + saturday_add2_1 + "</u>  </font>"));

                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }

    public void get_saturday_add2_2() {
        // Get Current Time*
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        mSeconds = c.get(Calendar.SECOND);
        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, android.R.style.Theme_Holo_Dialog_MinWidth,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        // view.setBackgroundColor(getResources().getColor(R.color.button_background));
                        String time_hr = String.valueOf(hourOfDay);
                        String time_min = String.valueOf(minute);
                        String min = null, hr = null;
                        if (time_hr.length() == 1 && time_min.length() == 1) {
                            min = "0" + "" + time_min;
                            hr = "0" + "" + time_hr;
                            saturday_add2_2 = hr + ":" + min;
                        } else if (time_hr.length() == 1 && time_min.length() != 1) {
                            hr = "0" + "" + time_hr;
                            saturday_add2_2 = hr + ":" + minute;
                        } else if (time_hr.length() != 1 && time_min.length() == 1) {
                            min = "0" + "" + time_min;
                            saturday_add2_2 = hourOfDay + ":" + min;
                        } else {
                            saturday_add2_2 = hourOfDay + ":" + minute;
                        }
                        Log.d("Time===>", "" + saturday_add2_2);
                        //editText_start_time.setText(time_active);
                        text_saturday_add2_2.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + saturday_add2_2 + "</u>  </font>"));

                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }


    public void get_saturday_add3_1() {
        // Get Current Time*
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        mSeconds = c.get(Calendar.SECOND);
        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, android.R.style.Theme_Holo_Dialog_MinWidth,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        // view.setBackgroundColor(getResources().getColor(R.color.button_background));
                        String time_hr = String.valueOf(hourOfDay);
                        String time_min = String.valueOf(minute);
                        String min = null, hr = null;
                        if (time_hr.length() == 1 && time_min.length() == 1) {
                            min = "0" + "" + time_min;
                            hr = "0" + "" + time_hr;
                            saturday_add3_1 = hr + ":" + min;
                        } else if (time_hr.length() == 1 && time_min.length() != 1) {
                            hr = "0" + "" + time_hr;
                            saturday_add3_1 = hr + ":" + minute;
                        } else if (time_hr.length() != 1 && time_min.length() == 1) {
                            min = "0" + "" + time_min;
                            saturday_add3_1 = hourOfDay + ":" + min;
                        } else {
                            saturday_add3_1 = hourOfDay + ":" + minute;
                        }
                        Log.d("Time===>", "" + saturday_add3_1);
                        //editText_start_time.setText(time_active);
                        text_saturday_add3_1.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + saturday_add3_1 + "</u>  </font>"));

                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }


    public void get_saturday_add3_2() {
        // Get Current Time*
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        mSeconds = c.get(Calendar.SECOND);
        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, android.R.style.Theme_Holo_Dialog_MinWidth,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        // view.setBackgroundColor(getResources().getColor(R.color.button_background));
                        String time_hr = String.valueOf(hourOfDay);
                        String time_min = String.valueOf(minute);
                        String min = null, hr = null;
                        if (time_hr.length() == 1 && time_min.length() == 1) {
                            min = "0" + "" + time_min;
                            hr = "0" + "" + time_hr;
                            saturday_add3_2 = hr + ":" + min;
                        } else if (time_hr.length() == 1 && time_min.length() != 1) {
                            hr = "0" + "" + time_hr;
                            saturday_add3_2 = hr + ":" + minute;
                        } else if (time_hr.length() != 1 && time_min.length() == 1) {
                            min = "0" + "" + time_min;
                            saturday_add3_2 = hourOfDay + ":" + min;
                        } else {
                            saturday_add3_2 = hourOfDay + ":" + minute;
                        }
                        Log.d("Time===>", "" + saturday_add3_2);
                        //editText_start_time.setText(time_active);
                        text_saturday_add3_2.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + saturday_add3_2 + "</u>  </font>"));

                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
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