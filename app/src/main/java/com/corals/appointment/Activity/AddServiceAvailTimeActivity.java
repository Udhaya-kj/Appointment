package com.corals.appointment.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.corals.appointment.Adapters.ServiceAvailCustomTimeAdapter;
import com.corals.appointment.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AddServiceAvailTimeActivity extends AppCompatActivity {

    RadioButton radioButton_biz_hrs, radioButton_custom_time;
    Spinner spinner_weekdays;
    LinearLayout layout_custom_time, layout_add_time;
    RecyclerView recyclerView;

    Button btn_yes_sday_p, btn_yes_mnday_p, btn_yes_tsday_p, btn_yes_wedday_p, btn_yes_trsday_p, btn_yes_fdday_p, btn_yes_strday_p, button_add_time, button_add_ser;
    boolean isActiveSunday_p = true, isActiveMonday_p = true, isActiveTuesday_p = true, isActiveWednesday_p = true, isActiveThursday_p = true, isActiveFriday_p = true, isActiveSaturday_p = true;
    TextView text_start_time, text_end_time, text_weekday;
    ArrayList<String> list_sun, list_mon, list_tue, list_wed, list_thu, list_fri, list_sat;
    String start_time, end_time, time_update_id = "";
    boolean isSelected = false;
    StringBuilder weekdays = new StringBuilder("yyyyyyy");
    int hour = 0, minute = 0;

    private SharedPreferences sharedpreferences_services;
    public static final String MyPREFERENCES_SERVICES = "MyPrefs_Services";
    public static final String SERVICE_NAME = "service_name";
    public static final String SERVICE_DURATION = "service_duration";
    private ArrayList<String> service_name_list, service_dur_list;
    String ser_name, ser_dur, page_id, position;

    TextView textView_sun_time1, textView_sun_time2, textView_mon_time1, textView_mon_time2, textView_tue_time1, textView_tue_time2, textView_wed_time1, textView_wed_time2,
            textView_thu_time1, textView_thu_time2, textView_fri_time1, textView_fri_time2, textView_sat_time1, textView_sat_time2;
    LinearLayout linearLayout__sun, linearLayout__mon, linearLayout__tue, linearLayout__wed, linearLayout__thur, linearLayout__fri, linearLayout__sat,
            linearLayout__sun2, linearLayout__mon2, linearLayout__tue2, linearLayout__wed2, linearLayout__thur2, linearLayout__fri2, linearLayout__sat2;
    ImageView imageView_sun_time1, imageView_sun_time2, imageView_mon_time1, imageView_mon_time2, imageView_tue_time1, imageView_tue_time2, imageView_wed_time1, imageView_wed_time2,
            imageView_thu_time1, imageView_thu_time2, imageView_fri_time1, imageView_fri_time2, imageView_sat_time1, imageView_sat_time2;
    ImageView imageView_warning_sun2, imageView_warning_mon2, imageView_warning_tue2, imageView_warning_wed2, imageView_warning_thu2, imageView_warning_fri2, imageView_warning_sat2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_service_avail_time);

        Toolbar toolbar = findViewById(R.id.toolbar_ser_avail_time);
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

        if (getIntent().getExtras() != null) {
            ser_name = getIntent().getStringExtra("ser_name");
            ser_dur = getIntent().getStringExtra("ser_dur");
            page_id = getIntent().getStringExtra("page_id");
            position = getIntent().getStringExtra("position");
            Log.d("AddSer--->", "onCreate: " + ser_name + "," + ser_dur + "," + page_id + "," + position);
        }
        sharedpreferences_services = getSharedPreferences(MyPREFERENCES_SERVICES, Context.MODE_PRIVATE);
        radioButton_biz_hrs = findViewById(R.id.rb_biz_hours);
        radioButton_custom_time = findViewById(R.id.rb_custom_time);
        recyclerView = findViewById(R.id.recylerview_avail_hours);
        spinner_weekdays = findViewById(R.id.spinner_weekdays);
        layout_custom_time = findViewById(R.id.layout_custom_time);
        layout_add_time = findViewById(R.id.layout_add_time);
        btn_yes_sday_p = (Button) findViewById(R.id.btn_yes_sunday_P);
        btn_yes_mnday_p = (Button) findViewById(R.id.btn_yes_monday_P);
        btn_yes_tsday_p = (Button) findViewById(R.id.btn_yes_tuesday_P);
        btn_yes_wedday_p = (Button) findViewById(R.id.btn_yes_wedsnesday_P);
        btn_yes_trsday_p = (Button) findViewById(R.id.btn_yes_thursday_P);
        btn_yes_fdday_p = (Button) findViewById(R.id.btn_yes_friday_P);
        btn_yes_strday_p = (Button) findViewById(R.id.btn_yes_saturday_P);
        text_start_time = findViewById(R.id.text_start_time);
        text_end_time = findViewById(R.id.text_end_time);
        text_weekday = findViewById(R.id.text_weekday);
        button_add_time = findViewById(R.id.button_add_time);
        button_add_ser = findViewById(R.id.button_add_service);

        textView_sun_time1 = (TextView) findViewById(R.id.text_sunday_time1);
        textView_sun_time2 = (TextView) findViewById(R.id.text_sunday_time2);
        textView_mon_time1 = (TextView) findViewById(R.id.text_monday_time1);
        textView_mon_time2 = (TextView) findViewById(R.id.text_monday_time2);
        textView_tue_time1 = (TextView) findViewById(R.id.text_tuesday_time1);
        textView_tue_time2 = (TextView) findViewById(R.id.text_tuesday_time2);
        textView_wed_time1 = (TextView) findViewById(R.id.text_wednesday_time1);
        textView_wed_time2 = (TextView) findViewById(R.id.text_wednesday_time2);
        textView_thu_time1 = (TextView) findViewById(R.id.text_thursday_time1);
        textView_thu_time2 = (TextView) findViewById(R.id.text_thursday_time2);
        textView_fri_time1 = (TextView) findViewById(R.id.text_friday_time1);
        textView_fri_time2 = (TextView) findViewById(R.id.text_friday_time2);
        textView_sat_time1 = (TextView) findViewById(R.id.text_saturday_time1);
        textView_sat_time2 = (TextView) findViewById(R.id.text_saturday_time2);

        imageView_sun_time1 = (ImageView) findViewById(R.id.image_edit_sun1);
        imageView_sun_time2 = (ImageView) findViewById(R.id.image_edit_sun2);
        imageView_mon_time1 = (ImageView) findViewById(R.id.image_edit_mon1);
        imageView_mon_time2 = (ImageView) findViewById(R.id.image_edit_mon2);
        imageView_tue_time1 = (ImageView) findViewById(R.id.image_edit_tue1);
        imageView_tue_time2 = (ImageView) findViewById(R.id.image_edit_tue2);
        imageView_wed_time1 = (ImageView) findViewById(R.id.image_edit_wed1);
        imageView_wed_time2 = (ImageView) findViewById(R.id.image_edit_wed2);
        imageView_thu_time1 = (ImageView) findViewById(R.id.image_edit_thu1);
        imageView_thu_time2 = (ImageView) findViewById(R.id.image_edit_thu2);
        imageView_fri_time1 = (ImageView) findViewById(R.id.image_edit_fri1);
        imageView_fri_time2 = (ImageView) findViewById(R.id.image_edit_fri2);
        imageView_sat_time1 = (ImageView) findViewById(R.id.image_edit_sat1);
        imageView_sat_time2 = (ImageView) findViewById(R.id.image_edit_sat2);

        linearLayout__sun = (LinearLayout) findViewById(R.id.layout_sunday);
        linearLayout__mon = (LinearLayout) findViewById(R.id.layout_monday);
        linearLayout__tue = (LinearLayout) findViewById(R.id.layout_tuesday);
        linearLayout__wed = (LinearLayout) findViewById(R.id.layout_wednesday);
        linearLayout__thur = (LinearLayout) findViewById(R.id.layout_thursday);
        linearLayout__fri = (LinearLayout) findViewById(R.id.layout_friday);
        linearLayout__sat = (LinearLayout) findViewById(R.id.layout_saturday);

        linearLayout__sun2 = (LinearLayout) findViewById(R.id.layout_sunday_time2);
        linearLayout__mon2 = (LinearLayout) findViewById(R.id.layout_monday_time2);
        linearLayout__tue2 = (LinearLayout) findViewById(R.id.layout_tuesday_time2);
        linearLayout__wed2 = (LinearLayout) findViewById(R.id.layout_wednesday_time2);
        linearLayout__thur2 = (LinearLayout) findViewById(R.id.layout_thursday_time2);
        linearLayout__fri2 = (LinearLayout) findViewById(R.id.layout_friday_time2);
        linearLayout__sat2 = (LinearLayout) findViewById(R.id.layout_saturday_time2);

        imageView_warning_sun2 = (ImageView) findViewById(R.id.image_warning_sun2);
        imageView_warning_mon2 = (ImageView) findViewById(R.id.image_warning_mon2);
        imageView_warning_tue2 = (ImageView) findViewById(R.id.image_warning_tue2);
        imageView_warning_wed2 = (ImageView) findViewById(R.id.image_warning_wed2);
        imageView_warning_thu2 = (ImageView) findViewById(R.id.image_warning_thu2);
        imageView_warning_fri2 = (ImageView) findViewById(R.id.image_warning_fri2);
        imageView_warning_sat2 = (ImageView) findViewById(R.id.image_warning_sat2);


        LinearLayoutManager lm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(lm);
        list_sun = new ArrayList<>();
        list_mon = new ArrayList<>();
        list_tue = new ArrayList<>();
        list_wed = new ArrayList<>();
        list_thu = new ArrayList<>();
        list_fri = new ArrayList<>();
        list_sat = new ArrayList<>();

        service_name_list = new ArrayList<>();
        service_dur_list = new ArrayList<>();
        getWeekDays("yyyyyyy");
       /* ArrayAdapter arrayAdapter_weekdays = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, arrayList_weekdays);
        spinner_weekdays.setAdapter(arrayAdapter_weekdays);
        spinner_weekdays.setPrompt("Select weekday");*/

        radioButton_biz_hrs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (radioButton_biz_hrs.isChecked()) {
                    radioButton_biz_hrs.setChecked(true);
                    radioButton_custom_time.setChecked(false);
                    layout_custom_time.setVisibility(View.GONE);
                }
                isSelected = true;
            }
        });

        radioButton_custom_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (radioButton_custom_time.isChecked()) {
                    radioButton_biz_hrs.setChecked(false);
                    radioButton_custom_time.setChecked(true);
                    layout_custom_time.setVisibility(View.VISIBLE);
                }
                isSelected = true;
            }
        });

        spinner_weekdays.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                layout_add_time.setVisibility(View.VISIBLE);
                String day = parent.getSelectedItem().toString();
                text_weekday.setText("" + day);
                text_start_time.setText("00:00");
                text_end_time.setText("00:00");
                button_add_time.setText("ADD");
                time_update_id="";
                // Toast.makeText(AddServiceAvailTimeActivity.this, ""+day, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        text_start_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get_start_time();
                timepicker_dialog("1");
            }
        });

        text_end_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get_end_time();
                timepicker_dialog("0");
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
                    // arrayList_weekdays.add(0, "");

                    weekdays.setCharAt(0, 'n');
                    getWeekDays(String.valueOf(weekdays));
                    Log.d("Weekdayslist--->", "onClick: " + weekdays);
                } else {
                    btn_yes_sday_p.setBackgroundColor(getResources().getColor(R.color.green_hase));
                    btn_yes_sday_p.setTextColor(getResources().getColor(R.color.white));
                    isActiveSunday_p = true;
                    //arrayList_weekdays.add(0, "Sunday");

                    weekdays.setCharAt(0, 'y');
                    getWeekDays(String.valueOf(weekdays));
                    Log.d("Weekdayslist--->", "onClick: " + weekdays);
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
                    //  arrayList_weekdays.add(1, "");

                    weekdays.setCharAt(1, 'n');
                    getWeekDays(String.valueOf(weekdays));
                    Log.d("Weekdayslist--->", "onClick: " + weekdays);
                } else {
                    btn_yes_mnday_p.setBackgroundColor(getResources().getColor(R.color.green_hase));
                    btn_yes_mnday_p.setTextColor(getResources().getColor(R.color.white));
                    isActiveMonday_p = true;
                    //arrayList_weekdays.add(1, "Monday");


                    weekdays.setCharAt(1, 'y');
                    getWeekDays(String.valueOf(weekdays));
                    Log.d("Weekdayslist--->", "onClick: " + weekdays);
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
                    //arrayList_weekdays.add(2, "");


                    weekdays.setCharAt(2, 'n');
                    getWeekDays(String.valueOf(weekdays));
                    Log.d("Weekdayslist--->", "onClick: " + weekdays);
                } else {
                    btn_yes_tsday_p.setBackgroundColor(getResources().getColor(R.color.green_hase));
                    btn_yes_tsday_p.setTextColor(getResources().getColor(R.color.white));
                    isActiveTuesday_p = true;
                    //arrayList_weekdays.add(2, "Tuesday");


                    weekdays.setCharAt(2, 'y');
                    getWeekDays(String.valueOf(weekdays));
                    Log.d("Weekdayslist--->", "onClick: " + weekdays);
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
                    // arrayList_weekdays.add(3, "");

                    weekdays.setCharAt(3, 'n');
                    getWeekDays(String.valueOf(weekdays));
                    Log.d("Weekdayslist--->", "onClick: " + weekdays);
                } else {
                    btn_yes_wedday_p.setBackgroundColor(getResources().getColor(R.color.green_hase));
                    btn_yes_wedday_p.setTextColor(getResources().getColor(R.color.white));
                    isActiveWednesday_p = true;
                    //arrayList_weekdays.add(3, "Wednesday");

                    weekdays.setCharAt(3, 'y');
                    getWeekDays(String.valueOf(weekdays));
                    Log.d("Weekdayslist--->", "onClick: " + weekdays);
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
                    // arrayList_weekdays.add(4, "");

                    weekdays.setCharAt(4, 'n');
                    getWeekDays(String.valueOf(weekdays));
                    Log.d("Weekdayslist--->", "onClick: " + weekdays);
                } else {
                    btn_yes_trsday_p.setBackgroundColor(getResources().getColor(R.color.green_hase));
                    btn_yes_trsday_p.setTextColor(getResources().getColor(R.color.white));
                    isActiveThursday_p = true;
                    // arrayList_weekdays.add(4, "Thursday");

                    weekdays.setCharAt(4, 'y');
                    getWeekDays(String.valueOf(weekdays));
                    Log.d("Weekdayslist--->", "onClick: " + weekdays);
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
                    //  arrayList_weekdays.add(5, "");

                    weekdays.setCharAt(5, 'n');
                    getWeekDays(String.valueOf(weekdays));
                    Log.d("Weekdayslist--->", "onClick: " + weekdays);
                } else {
                    btn_yes_fdday_p.setBackgroundColor(getResources().getColor(R.color.green_hase));
                    btn_yes_fdday_p.setTextColor(getResources().getColor(R.color.white));
                    isActiveFriday_p = true;
                    //arrayList_weekdays.add(5, "Friday");

                    weekdays.setCharAt(5, 'y');
                    getWeekDays(String.valueOf(weekdays));
                    Log.d("Weekdayslist--->", "onClick: " + weekdays);
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
                    // arrayList_weekdays.add(6, "");

                    weekdays.setCharAt(6, 'n');
                    getWeekDays(String.valueOf(weekdays));
                    Log.d("Weekdayslist--->", "onClick: " + weekdays);
                } else {
                    btn_yes_strday_p.setBackgroundColor(getResources().getColor(R.color.green_hase));
                    btn_yes_strday_p.setTextColor(getResources().getColor(R.color.white));
                    isActiveSaturday_p = true;
                    //arrayList_weekdays.add(6, "Saturday");

                    weekdays.setCharAt(6, 'y');
                    getWeekDays(String.valueOf(weekdays));
                    Log.d("Weekdayslist--->", "onClick: " + weekdays);
                }
            }
        });


        //Edit sunday
        imageView_sun_time1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data_sun = list_sun.get(0);
                String[] strs = data_sun.split(" - ");
                String s_tm = strs[0];
                String e_tm = strs[1];

                text_start_time.setText(s_tm);
                text_end_time.setText(e_tm);
                text_weekday.setText("Sunday");
                button_add_time.setText("UPDATE");
                time_update_id = "0";

            }
        });
        imageView_sun_time2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data_sun = list_sun.get(1);
                String[] strs = data_sun.split(" - ");
                String s_tm = strs[0];
                String e_tm = strs[1];

                text_start_time.setText(s_tm);
                text_end_time.setText(e_tm);
                text_weekday.setText("Sunday");
                button_add_time.setText("UPDATE");
                time_update_id = "1";
            }
        });

        //Edit monday
        imageView_mon_time1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String data_sun = list_mon.get(0);
                String[] strs = data_sun.split(" - ");
                String s_tm = strs[0];
                String e_tm = strs[1];

                text_start_time.setText(s_tm);
                text_end_time.setText(e_tm);
                text_weekday.setText("Monday");
                button_add_time.setText("UPDATE");
                time_update_id = "0";

            }
        });
        imageView_mon_time2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data_sun = list_mon.get(1);
                String[] strs = data_sun.split(" - ");
                String s_tm = strs[0];
                String e_tm = strs[1];

                text_start_time.setText(s_tm);
                text_end_time.setText(e_tm);
                text_weekday.setText("Monday");
                button_add_time.setText("UPDATE");
                time_update_id = "1";
            }
        });

        //Edit tuesday
        imageView_tue_time1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String data_sun = list_tue.get(0);
                String[] strs = data_sun.split(" - ");
                String s_tm = strs[0];
                String e_tm = strs[1];

                text_start_time.setText(s_tm);
                text_end_time.setText(e_tm);
                text_weekday.setText("Tuesday");
                button_add_time.setText("UPDATE");
                time_update_id = "0";

            }
        });
        imageView_tue_time2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data_sun = list_tue.get(1);
                String[] strs = data_sun.split(" - ");
                String s_tm = strs[0];
                String e_tm = strs[1];

                text_start_time.setText(s_tm);
                text_end_time.setText(e_tm);
                text_weekday.setText("Tuesday");
                button_add_time.setText("UPDATE");
                time_update_id = "1";
            }
        });

        //Edit wednesday
        imageView_wed_time1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data_sun = list_wed.get(0);
                String[] strs = data_sun.split(" - ");
                String s_tm = strs[0];
                String e_tm = strs[1];

                text_start_time.setText(s_tm);
                text_end_time.setText(e_tm);
                text_weekday.setText("Wednesday");
                button_add_time.setText("UPDATE");
                time_update_id = "0";
            }
        });
        imageView_wed_time2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data_sun = list_wed.get(1);
                String[] strs = data_sun.split(" - ");
                String s_tm = strs[0];
                String e_tm = strs[1];

                text_start_time.setText(s_tm);
                text_end_time.setText(e_tm);
                text_weekday.setText("Wednesday");
                button_add_time.setText("UPDATE");
                time_update_id = "1";
            }
        });

        //Edit thursday
        imageView_thu_time1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data_sun = list_thu.get(0);
                String[] strs = data_sun.split(" - ");
                String s_tm = strs[0];
                String e_tm = strs[1];

                text_start_time.setText(s_tm);
                text_end_time.setText(e_tm);
                text_weekday.setText("Thursday");
                button_add_time.setText("UPDATE");
                time_update_id = "0";
            }
        });
        imageView_thu_time2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data_sun = list_thu.get(1);
                String[] strs = data_sun.split(" - ");
                String s_tm = strs[0];
                String e_tm = strs[1];

                text_start_time.setText(s_tm);
                text_end_time.setText(e_tm);
                text_weekday.setText("Thursday");
                button_add_time.setText("UPDATE");
                time_update_id = "1";
            }
        });

        //Edit friday
        imageView_fri_time1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data_sun = list_fri.get(0);
                String[] strs = data_sun.split(" - ");
                String s_tm = strs[0];
                String e_tm = strs[1];

                text_start_time.setText(s_tm);
                text_end_time.setText(e_tm);
                text_weekday.setText("Friday");
                button_add_time.setText("UPDATE");
                time_update_id = "0";
            }
        });
        imageView_fri_time2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data_sun = list_fri.get(1);
                String[] strs = data_sun.split(" - ");
                String s_tm = strs[0];
                String e_tm = strs[1];

                text_start_time.setText(s_tm);
                text_end_time.setText(e_tm);
                text_weekday.setText("Friday");
                button_add_time.setText("UPDATE");
                time_update_id = "1";
            }
        });

        //Edit saturday
        imageView_sat_time1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data_sun = list_sat.get(0);
                String[] strs = data_sun.split(" - ");
                String s_tm = strs[0];
                String e_tm = strs[1];

                text_start_time.setText(s_tm);
                text_end_time.setText(e_tm);
                text_weekday.setText("Saturday");
                button_add_time.setText("UPDATE");
                time_update_id = "0";
            }
        });
        imageView_sat_time2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data_sun = list_sat.get(1);
                String[] strs = data_sun.split(" - ");
                String s_tm = strs[0];
                String e_tm = strs[1];

                text_start_time.setText(s_tm);
                text_end_time.setText(e_tm);
                text_weekday.setText("Saturday");
                button_add_time.setText("UPDATE");
                time_update_id = "1";
            }
        });


        button_add_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String weekday = text_weekday.getText().toString();
                String s_time = text_start_time.getText().toString();
                String e_time = text_end_time.getText().toString();

                boolean chktime = checktimings(s_time, e_time);
                if (chktime) {
                    if (weekday.equals("Sunday")) {

                        if (button_add_time.getText().equals("ADD")) {
                            if (list_sun.size() <= 1) {
                                if (list_sun.size() == 1) {
                                    String data_sun = list_sun.get(0);
                                    String[] strs = data_sun.split(" - ");
                                    String s_tm = strs[0];
                                    String e_tm = strs[1];

                                    boolean chktime_slot2 = checktimings2(e_tm, s_time);
                                    if (chktime_slot2) {
                                        list_sun.add(s_time + " - " + e_time);
                                        textView_sun_time1.setText(list_sun.get(0));
                                        textView_sun_time2.setText(list_sun.get(1));
                                        linearLayout__sun2.setVisibility(View.VISIBLE);

                      /*            ServiceAvailCustomTimeAdapter timeAdapter = new ServiceAvailCustomTimeAdapter(AddServiceAvailTimeActivity.this, list_sun, list_mon, list_tue, list_wed, list_thu, list_fri, list_sat);
                                    recyclerView.setAdapter(timeAdapter);
                                    timeAdapter.notifyDataSetChanged();*/
                                    } else {
                                        getDialog("Unavailability time cannot overlap");
                                    }

                                } else {

                                    list_sun.add(s_time + " - " + e_time);
                                    textView_sun_time1.setText(list_sun.get(0));
                                    linearLayout__sun.setVisibility(View.VISIBLE);

                                /*recyclerView.setVisibility(View.VISIBLE);
                                ServiceAvailCustomTimeAdapter timeAdapter = new ServiceAvailCustomTimeAdapter(AddServiceAvailTimeActivity.this, list_sun, list_mon, list_tue, list_wed, list_thu, list_fri, list_sat);
                                recyclerView.setAdapter(timeAdapter);
                                timeAdapter.notifyDataSetChanged();*/
                                }
                            } else {
                                getDialog("You have reached the limit for sunday");
                            }
                        } else {
                            //Update
                            if (!TextUtils.isEmpty(time_update_id) && time_update_id.equals("0")) {
                                list_sun.set(0, s_time + " - " + e_time);
                                textView_sun_time1.setText(list_sun.get(0));

                                if (list_sun.size() > 1) {
                                    String start_time = getStartTime(list_sun.get(1));
                                    String end_time = getEndTime(list_sun.get(0));
                                    boolean chktime_slot2 = checktimings2(end_time, start_time);
                                    if (!chktime_slot2) {
                                        imageView_warning_sun2.setVisibility(View.VISIBLE);
                                    } else {
                                        imageView_warning_sun2.setVisibility(View.INVISIBLE);
                                    }

                                    time_update_id = "";
                                    button_add_time.setText("ADD");
                                }
                            } else if (!TextUtils.isEmpty(time_update_id) && time_update_id.equals("1")) {
                                String end_time = getEndTime(list_sun.get(0));
                                boolean chktime_slot2 = checktimings2(end_time, s_time);
                                if (chktime_slot2) {
                                    list_sun.add(1, s_time + " - " + e_time);
                                    textView_sun_time1.setText(list_sun.get(0));
                                    textView_sun_time2.setText(list_sun.get(1));
                                    imageView_warning_sun2.setVisibility(View.INVISIBLE);
                                    time_update_id = "";
                                    button_add_time.setText("ADD");
                                } else {
                                    getDialog("Unavailability time cannot overlap");
                                }

                            }

                        }


                    } else if (weekday.equals("Monday")) {

                        if (button_add_time.getText().equals("ADD")) {
                            if (list_mon.size() <= 1) {
                                if (list_mon.size() == 1) {
                                    String data_sun = list_mon.get(0);
                                    String[] strs = data_sun.split(" - ");
                                    String s_tm = strs[0];
                                    String e_tm = strs[1];

                                    boolean chktime_slot2 = checktimings2(e_tm, s_time);
                                    if (chktime_slot2) {
                                        list_mon.add(s_time + " - " + e_time);
                                        textView_mon_time1.setText(list_mon.get(0));
                                        textView_mon_time2.setText(list_mon.get(1));
                                        linearLayout__mon2.setVisibility(View.VISIBLE);

                      /*            ServiceAvailCustomTimeAdapter timeAdapter = new ServiceAvailCustomTimeAdapter(AddServiceAvailTimeActivity.this, list_sun, list_mon, list_tue, list_wed, list_thu, list_fri, list_sat);
                                    recyclerView.setAdapter(timeAdapter);
                                    timeAdapter.notifyDataSetChanged();*/
                                    } else {
                                        getDialog("Unavailability time cannot overlap");
                                    }

                                } else {

                                    list_mon.add(s_time + " - " + e_time);
                                    textView_mon_time1.setText(list_mon.get(0));
                                    linearLayout__mon.setVisibility(View.VISIBLE);

                                /*recyclerView.setVisibility(View.VISIBLE);
                                ServiceAvailCustomTimeAdapter timeAdapter = new ServiceAvailCustomTimeAdapter(AddServiceAvailTimeActivity.this, list_sun, list_mon, list_tue, list_wed, list_thu, list_fri, list_sat);
                                recyclerView.setAdapter(timeAdapter);
                                timeAdapter.notifyDataSetChanged();*/
                                }
                            } else {
                                getDialog("You have reached the limit for monday");
                            }
                        } else {
                            //Update
                            if (!TextUtils.isEmpty(time_update_id) && time_update_id.equals("0")) {
                                list_mon.set(0, s_time + " - " + e_time);
                                textView_mon_time1.setText(list_mon.get(0));

                                if (list_mon.size() > 1) {
                                    String start_time = getStartTime(list_mon.get(1));
                                    String end_time = getEndTime(list_mon.get(0));
                                    boolean chktime_slot2 = checktimings2(end_time, start_time);
                                    if (!chktime_slot2) {
                                        imageView_warning_mon2.setVisibility(View.VISIBLE);
                                    } else {
                                        imageView_warning_mon2.setVisibility(View.INVISIBLE);
                                    }

                                    time_update_id = "";
                                    button_add_time.setText("ADD");
                                }
                            } else if (!TextUtils.isEmpty(time_update_id) && time_update_id.equals("1")) {
                                String end_time = getEndTime(list_mon.get(0));
                                boolean chktime_slot2 = checktimings2(end_time, s_time);
                                if (chktime_slot2) {
                                    list_mon.add(1, s_time + " - " + e_time);
                                    textView_mon_time1.setText(list_mon.get(0));
                                    textView_mon_time2.setText(list_mon.get(1));
                                    imageView_warning_mon2.setVisibility(View.INVISIBLE);
                                    time_update_id = "";
                                    button_add_time.setText("ADD");
                                } else {
                                    getDialog("Unavailability time cannot overlap");
                                }

                            }

                        }

                    } else if (weekday.equals("Tuesday")) {
                        if (button_add_time.getText().equals("ADD")) {
                            if (list_tue.size() <= 1) {
                                if (list_tue.size() == 1) {
                                    String data_sun = list_tue.get(0);
                                    String[] strs = data_sun.split(" - ");
                                    String s_tm = strs[0];
                                    String e_tm = strs[1];

                                    boolean chktime_slot2 = checktimings2(e_tm, s_time);
                                    if (chktime_slot2) {
                                        list_tue.add(s_time + " - " + e_time);
                                        textView_tue_time1.setText(list_tue.get(0));
                                        textView_tue_time2.setText(list_tue.get(1));
                                        linearLayout__tue2.setVisibility(View.VISIBLE);

                      /*            ServiceAvailCustomTimeAdapter timeAdapter = new ServiceAvailCustomTimeAdapter(AddServiceAvailTimeActivity.this, list_sun, list_mon, list_tue, list_wed, list_thu, list_fri, list_sat);
                                    recyclerView.setAdapter(timeAdapter);
                                    timeAdapter.notifyDataSetChanged();*/
                                    } else {
                                        getDialog("Unavailability time cannot overlap");
                                    }

                                } else {

                                    list_tue.add(s_time + " - " + e_time);
                                    textView_tue_time1.setText(list_tue.get(0));
                                    linearLayout__tue.setVisibility(View.VISIBLE);

                                /*recyclerView.setVisibility(View.VISIBLE);
                                ServiceAvailCustomTimeAdapter timeAdapter = new ServiceAvailCustomTimeAdapter(AddServiceAvailTimeActivity.this, list_sun, list_mon, list_tue, list_wed, list_thu, list_fri, list_sat);
                                recyclerView.setAdapter(timeAdapter);
                                timeAdapter.notifyDataSetChanged();*/
                                }
                            } else {
                                getDialog("You have reached the limit for tuesday");
                            }
                        } else {
                            //Update
                            if (!TextUtils.isEmpty(time_update_id) && time_update_id.equals("0")) {
                                list_tue.set(0, s_time + " - " + e_time);
                                textView_tue_time1.setText(list_tue.get(0));

                                if (list_tue.size() > 1) {
                                    String start_time = getStartTime(list_tue.get(1));
                                    String end_time = getEndTime(list_tue.get(0));
                                    boolean chktime_slot2 = checktimings2(end_time, start_time);
                                    if (!chktime_slot2) {
                                        imageView_warning_tue2.setVisibility(View.VISIBLE);
                                    } else {
                                        imageView_warning_tue2.setVisibility(View.INVISIBLE);
                                    }

                                    time_update_id = "";
                                    button_add_time.setText("ADD");
                                }
                            } else if (!TextUtils.isEmpty(time_update_id) && time_update_id.equals("1")) {
                                String end_time = getEndTime(list_tue.get(0));
                                boolean chktime_slot2 = checktimings2(end_time, s_time);
                                if (chktime_slot2) {
                                    list_tue.add(1, s_time + " - " + e_time);
                                    textView_tue_time1.setText(list_tue.get(0));
                                    textView_tue_time2.setText(list_tue.get(1));
                                    imageView_warning_tue2.setVisibility(View.INVISIBLE);
                                    time_update_id = "";
                                    button_add_time.setText("ADD");
                                } else {
                                    getDialog("Unavailability time cannot overlap");
                                }

                            }

                        }
                    } else if (weekday.equals("Wednesday")) {
                        if (button_add_time.getText().equals("ADD")) {
                            if (list_wed.size() <= 1) {
                                if (list_wed.size() == 1) {
                                    String data_sun = list_wed.get(0);
                                    String[] strs = data_sun.split(" - ");
                                    String s_tm = strs[0];
                                    String e_tm = strs[1];

                                    boolean chktime_slot2 = checktimings2(e_tm, s_time);
                                    if (chktime_slot2) {
                                        list_wed.add(s_time + " - " + e_time);
                                        textView_wed_time1.setText(list_wed.get(0));
                                        textView_wed_time2.setText(list_wed.get(1));
                                        linearLayout__wed2.setVisibility(View.VISIBLE);

                      /*            ServiceAvailCustomTimeAdapter timeAdapter = new ServiceAvailCustomTimeAdapter(AddServiceAvailTimeActivity.this, list_sun, list_mon, list_tue, list_wed, list_thu, list_fri, list_sat);
                                    recyclerView.setAdapter(timeAdapter);
                                    timeAdapter.notifyDataSetChanged();*/
                                    } else {
                                        getDialog("Unavailability time cannot overlap");
                                    }

                                } else {

                                    list_wed.add(s_time + " - " + e_time);
                                    textView_wed_time1.setText(list_wed.get(0));
                                    linearLayout__wed.setVisibility(View.VISIBLE);

                                /*recyclerView.setVisibility(View.VISIBLE);
                                ServiceAvailCustomTimeAdapter timeAdapter = new ServiceAvailCustomTimeAdapter(AddServiceAvailTimeActivity.this, list_sun, list_mon, list_tue, list_wed, list_thu, list_fri, list_sat);
                                recyclerView.setAdapter(timeAdapter);
                                timeAdapter.notifyDataSetChanged();*/
                                }
                            } else {
                                getDialog("You have reached the limit for wednesday");
                            }
                        } else {
                            //Update
                            if (!TextUtils.isEmpty(time_update_id) && time_update_id.equals("0")) {
                                list_wed.set(0, s_time + " - " + e_time);
                                textView_wed_time1.setText(list_wed.get(0));

                                if (list_wed.size() > 1) {
                                    String start_time = getStartTime(list_wed.get(1));
                                    String end_time = getEndTime(list_wed.get(0));
                                    boolean chktime_slot2 = checktimings2(end_time, start_time);
                                    if (!chktime_slot2) {
                                        imageView_warning_wed2.setVisibility(View.VISIBLE);
                                    } else {
                                        imageView_warning_wed2.setVisibility(View.INVISIBLE);
                                    }

                                    time_update_id = "";
                                    button_add_time.setText("ADD");
                                }
                            } else if (!TextUtils.isEmpty(time_update_id) && time_update_id.equals("1")) {
                                String end_time = getEndTime(list_wed.get(0));
                                boolean chktime_slot2 = checktimings2(end_time, s_time);
                                if (chktime_slot2) {
                                    list_wed.add(1, s_time + " - " + e_time);
                                    textView_wed_time1.setText(list_wed.get(0));
                                    textView_wed_time2.setText(list_wed.get(1));
                                    imageView_warning_wed2.setVisibility(View.INVISIBLE);
                                    time_update_id = "";
                                    button_add_time.setText("ADD");
                                } else {
                                    getDialog("Unavailability time cannot overlap");
                                }

                            }

                        }
                    } else if (weekday.equals("Thursday")) {
                        if (button_add_time.getText().equals("ADD")) {
                            if (list_thu.size() <= 1) {
                                if (list_thu.size() == 1) {
                                    String data_sun = list_thu.get(0);
                                    String[] strs = data_sun.split(" - ");
                                    String s_tm = strs[0];
                                    String e_tm = strs[1];

                                    boolean chktime_slot2 = checktimings2(e_tm, s_time);
                                    if (chktime_slot2) {
                                        list_thu.add(s_time + " - " + e_time);
                                        textView_thu_time1.setText(list_thu.get(0));
                                        textView_thu_time2.setText(list_thu.get(1));
                                        linearLayout__thur2.setVisibility(View.VISIBLE);

                      /*            ServiceAvailCustomTimeAdapter timeAdapter = new ServiceAvailCustomTimeAdapter(AddServiceAvailTimeActivity.this, list_sun, list_mon, list_tue, list_wed, list_thu, list_fri, list_sat);
                                    recyclerView.setAdapter(timeAdapter);
                                    timeAdapter.notifyDataSetChanged();*/
                                    } else {
                                        getDialog("Unavailability time cannot overlap");
                                    }

                                } else {

                                    list_thu.add(s_time + " - " + e_time);
                                    textView_thu_time1.setText(list_thu.get(0));
                                    linearLayout__thur.setVisibility(View.VISIBLE);

                                /*recyclerView.setVisibility(View.VISIBLE);
                                ServiceAvailCustomTimeAdapter timeAdapter = new ServiceAvailCustomTimeAdapter(AddServiceAvailTimeActivity.this, list_sun, list_mon, list_tue, list_wed, list_thu, list_fri, list_sat);
                                recyclerView.setAdapter(timeAdapter);
                                timeAdapter.notifyDataSetChanged();*/
                                }
                            } else {
                                getDialog("You have reached the limit for thursday");
                            }
                        } else {
                            //Update
                            if (!TextUtils.isEmpty(time_update_id) && time_update_id.equals("0")) {
                                list_thu.set(0, s_time + " - " + e_time);
                                textView_thu_time1.setText(list_thu.get(0));

                                if (list_thu.size() > 1) {
                                    String start_time = getStartTime(list_thu.get(1));
                                    String end_time = getEndTime(list_thu.get(0));
                                    boolean chktime_slot2 = checktimings2(end_time, start_time);
                                    if (!chktime_slot2) {
                                        imageView_warning_thu2.setVisibility(View.VISIBLE);
                                    } else {
                                        imageView_warning_thu2.setVisibility(View.INVISIBLE);
                                    }

                                    time_update_id = "";
                                    button_add_time.setText("ADD");
                                }
                            } else if (!TextUtils.isEmpty(time_update_id) && time_update_id.equals("1")) {
                                String end_time = getEndTime(list_thu.get(0));
                                boolean chktime_slot2 = checktimings2(end_time, s_time);
                                if (chktime_slot2) {
                                    list_thu.add(1, s_time + " - " + e_time);
                                    textView_thu_time1.setText(list_thu.get(0));
                                    textView_thu_time2.setText(list_thu.get(1));
                                    imageView_warning_thu2.setVisibility(View.INVISIBLE);
                                    time_update_id = "";
                                    button_add_time.setText("ADD");
                                } else {
                                    getDialog("Unavailability time cannot overlap");
                                }

                            }

                        }
                    } else if (weekday.equals("Friday")) {
                        if (button_add_time.getText().equals("ADD")) {
                            if (list_fri.size() <= 1) {
                                if (list_fri.size() == 1) {
                                    String data_sun = list_fri.get(0);
                                    String[] strs = data_sun.split(" - ");
                                    String s_tm = strs[0];
                                    String e_tm = strs[1];

                                    boolean chktime_slot2 = checktimings2(e_tm, s_time);
                                    if (chktime_slot2) {
                                        list_fri.add(s_time + " - " + e_time);
                                        textView_fri_time1.setText(list_fri.get(0));
                                        textView_fri_time2.setText(list_fri.get(1));
                                        linearLayout__fri2.setVisibility(View.VISIBLE);

                      /*            ServiceAvailCustomTimeAdapter timeAdapter = new ServiceAvailCustomTimeAdapter(AddServiceAvailTimeActivity.this, list_sun, list_mon, list_tue, list_wed, list_thu, list_fri, list_sat);
                                    recyclerView.setAdapter(timeAdapter);
                                    timeAdapter.notifyDataSetChanged();*/
                                    } else {
                                        getDialog("Unavailability time cannot overlap");
                                    }

                                } else {

                                    list_fri.add(s_time + " - " + e_time);
                                    textView_fri_time1.setText(list_fri.get(0));
                                    linearLayout__fri.setVisibility(View.VISIBLE);

                                /*recyclerView.setVisibility(View.VISIBLE);
                                ServiceAvailCustomTimeAdapter timeAdapter = new ServiceAvailCustomTimeAdapter(AddServiceAvailTimeActivity.this, list_sun, list_mon, list_tue, list_wed, list_thu, list_fri, list_sat);
                                recyclerView.setAdapter(timeAdapter);
                                timeAdapter.notifyDataSetChanged();*/
                                }
                            } else {
                                getDialog("You have reached the limit for friday");
                            }
                        } else {
                            //Update
                            if (!TextUtils.isEmpty(time_update_id) && time_update_id.equals("0")) {
                                list_fri.set(0, s_time + " - " + e_time);
                                textView_fri_time1.setText(list_fri.get(0));

                                if (list_fri.size() > 1) {
                                    String start_time = getStartTime(list_fri.get(1));
                                    String end_time = getEndTime(list_fri.get(0));
                                    boolean chktime_slot2 = checktimings2(end_time, start_time);
                                    if (!chktime_slot2) {
                                        imageView_warning_fri2.setVisibility(View.VISIBLE);
                                    } else {
                                        imageView_warning_fri2.setVisibility(View.INVISIBLE);
                                    }

                                    time_update_id = "";
                                    button_add_time.setText("ADD");
                                }
                            } else if (!TextUtils.isEmpty(time_update_id) && time_update_id.equals("1")) {
                                String end_time = getEndTime(list_fri.get(0));
                                boolean chktime_slot2 = checktimings2(end_time, s_time);
                                if (chktime_slot2) {
                                    list_fri.add(1, s_time + " - " + e_time);
                                    textView_fri_time1.setText(list_fri.get(0));
                                    textView_fri_time2.setText(list_fri.get(1));
                                    imageView_warning_fri2.setVisibility(View.INVISIBLE);
                                    time_update_id = "";
                                    button_add_time.setText("ADD");
                                } else {
                                    getDialog("Unavailability time cannot overlap");
                                }

                            }

                        }
                    } else if (weekday.equals("Saturday")) {
                        if (button_add_time.getText().equals("ADD")) {
                            if (list_sat.size() <= 1) {
                                if (list_sat.size() == 1) {
                                    String data_sun = list_sat.get(0);
                                    String[] strs = data_sun.split(" - ");
                                    String s_tm = strs[0];
                                    String e_tm = strs[1];

                                    boolean chktime_slot2 = checktimings2(e_tm, s_time);
                                    if (chktime_slot2) {
                                        list_sat.add(s_time + " - " + e_time);
                                        textView_sat_time1.setText(list_sat.get(0));
                                        textView_sat_time2.setText(list_sat.get(1));
                                        linearLayout__sat2.setVisibility(View.VISIBLE);

                      /*            ServiceAvailCustomTimeAdapter timeAdapter = new ServiceAvailCustomTimeAdapter(AddServiceAvailTimeActivity.this, list_sun, list_mon, list_tue, list_wed, list_thu, list_fri, list_sat);
                                    recyclerView.setAdapter(timeAdapter);
                                    timeAdapter.notifyDataSetChanged();*/
                                    } else {
                                        getDialog("Unavailability time cannot overlap");
                                    }

                                } else {

                                    list_sat.add(s_time + " - " + e_time);
                                    textView_sat_time1.setText(list_sat.get(0));
                                    linearLayout__sat.setVisibility(View.VISIBLE);

                                /*recyclerView.setVisibility(View.VISIBLE);
                                ServiceAvailCustomTimeAdapter timeAdapter = new ServiceAvailCustomTimeAdapter(AddServiceAvailTimeActivity.this, list_sun, list_mon, list_tue, list_wed, list_thu, list_fri, list_sat);
                                recyclerView.setAdapter(timeAdapter);
                                timeAdapter.notifyDataSetChanged();*/
                                }
                            } else {
                                getDialog("You have reached the limit for saturday");
                            }
                        } else {
                            //Update
                            if (!TextUtils.isEmpty(time_update_id) && time_update_id.equals("0")) {
                                list_sat.set(0, s_time + " - " + e_time);
                                textView_sat_time1.setText(list_sat.get(0));

                                if (list_sat.size() > 1) {
                                    String start_time = getStartTime(list_sat.get(1));
                                    String end_time = getEndTime(list_sat.get(0));
                                    boolean chktime_slot2 = checktimings2(end_time, start_time);
                                    if (!chktime_slot2) {
                                        imageView_warning_sat2.setVisibility(View.VISIBLE);
                                    } else {
                                        imageView_warning_sat2.setVisibility(View.INVISIBLE);
                                    }

                                    time_update_id = "";
                                    button_add_time.setText("ADD");
                                }
                            } else if (!TextUtils.isEmpty(time_update_id) && time_update_id.equals("1")) {
                                String end_time = getEndTime(list_sat.get(0));
                                boolean chktime_slot2 = checktimings2(end_time, s_time);
                                if (chktime_slot2) {
                                    list_sat.add(1, s_time + " - " + e_time);
                                    textView_sat_time1.setText(list_sat.get(0));
                                    textView_sat_time2.setText(list_sat.get(1));
                                    imageView_warning_sat2.setVisibility(View.INVISIBLE);
                                    time_update_id = "";
                                    button_add_time.setText("ADD");
                                } else {
                                    getDialog("Unavailability time cannot overlap");
                                }

                            }

                        }
                    }

                    //layout_add_time.setVisibility(View.GONE);


                } else {
                    getDialog("Invalid Time");
                }
            }
        });

        button_add_ser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isSelected) {
                    final ProgressDialog pd = new ProgressDialog(AddServiceAvailTimeActivity.this);
                    pd.setMessage("Creating service...");
                    pd.show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            pd.dismiss();
                            String nameList = sharedpreferences_services.getString(SERVICE_NAME, "");
                            String mobList = sharedpreferences_services.getString(SERVICE_DURATION, "");
                            if (!TextUtils.isEmpty(nameList) && !TextUtils.isEmpty(mobList)) {
                                service_name_list = new Gson().fromJson(nameList, new TypeToken<ArrayList<String>>() {
                                }.getType());
                                service_dur_list = new Gson().fromJson(mobList, new TypeToken<ArrayList<String>>() {
                                }.getType());
                            }
                            if (!TextUtils.isEmpty(page_id) && page_id.equals("3")) {
                                service_name_list.add(ser_name);
                                service_dur_list.add(ser_dur);
                                String nameList1 = new Gson().toJson(service_name_list);
                                String mobList1 = new Gson().toJson(service_dur_list);
                                SharedPreferences.Editor editor = sharedpreferences_services.edit();
                                editor.putString(SERVICE_NAME, nameList1);
                                editor.putString(SERVICE_DURATION, mobList1);
                                editor.commit();

                                Intent in = new Intent(AddServiceAvailTimeActivity.this, SetupServiceActivity_Bottom.class);
                                startActivity(in);
                                finish();
                            } else if (!TextUtils.isEmpty(page_id) && page_id.equals("03")) {
                                service_name_list.set(Integer.parseInt(position), ser_name);
                                service_dur_list.set(Integer.parseInt(position), ser_dur);
                                String nameList1 = new Gson().toJson(service_name_list);
                                String mobList1 = new Gson().toJson(service_dur_list);
                                SharedPreferences.Editor editor = sharedpreferences_services.edit();
                                editor.putString(SERVICE_NAME, nameList1);
                                editor.putString(SERVICE_DURATION, mobList1);
                                editor.commit();

                                Intent in = new Intent(AddServiceAvailTimeActivity.this, SetupServiceActivity_Bottom.class);
                                startActivity(in);
                                finish();
                            }

                        }
                    }, 2000);
                } else {
                    getDialog("Pick any one option");
                }
            }
        });
    }


    public void get_start_time() {
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
                            start_time = hr + ":" + min;
                        } else if (time_hr.length() == 1 && time_min.length() != 1) {
                            hr = "0" + "" + time_hr;
                            start_time = hr + ":" + minute;
                        } else if (time_hr.length() != 1 && time_min.length() == 1) {
                            min = "0" + "" + time_min;
                            start_time = hourOfDay + ":" + min;
                        } else {
                            start_time = hourOfDay + ":" + minute;
                        }
                        Log.d("Time===>", "" + start_time);
                        //editText_start_time.setText(time_active);
                        text_start_time.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + start_time + "</u>  </font>"));

                    }
                }, 00, 00, false);
        timePickerDialog.show();
    }

    public void get_end_time() {
        // Get Current Time*
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
                            end_time = hr + ":" + min;
                        } else if (time_hr.length() == 1 && time_min.length() != 1) {
                            hr = "0" + "" + time_hr;
                            end_time = hr + ":" + minute;
                        } else if (time_hr.length() != 1 && time_min.length() == 1) {
                            min = "0" + "" + time_min;
                            end_time = hourOfDay + ":" + min;
                        } else {
                            end_time = hourOfDay + ":" + minute;
                        }
                        Log.d("Time===>", "" + end_time);
                        //editText_start_time.setText(time_active);
                        text_end_time.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + end_time + "</u>  </font>"));

                    }
                }, 00, 00, false);
        timePickerDialog.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent in = new Intent(AddServiceAvailTimeActivity.this, AddServiceActivity.class);
        in.putExtra("page_id", page_id);
        startActivity(in);
        finish();

    }

    public void getWeekDays(String weekday) {
        ArrayList<String> arrayList_weekdays = new ArrayList<>();
        arrayList_weekdays.add("Sunday");
        arrayList_weekdays.add("Monday");
        arrayList_weekdays.add("Tuesday");
        arrayList_weekdays.add("Wednesday");
        arrayList_weekdays.add("Thursday");
        arrayList_weekdays.add("Friday");
        arrayList_weekdays.add("Saturday");

        if (String.valueOf(weekday.charAt(0)).equals("n")) {
            arrayList_weekdays.remove("Sunday");
        }
        if (String.valueOf(weekday.charAt(1)).equals("n")) {
            arrayList_weekdays.remove("Monday");
        }

        if (String.valueOf(weekday.charAt(2)).equals("n")) {
            arrayList_weekdays.remove("Tuesday");
        }

        if (String.valueOf(weekday.charAt(3)).equals("n")) {
            arrayList_weekdays.remove("Wednesday");
        }

        if (String.valueOf(weekday.charAt(4)).equals("n")) {
            arrayList_weekdays.remove("Thursday");
        }

        if (String.valueOf(weekday.charAt(5)).equals("n")) {
            arrayList_weekdays.remove("Friday");
        }

        if (String.valueOf(weekday.charAt(6)).equals("n")) {
            arrayList_weekdays.remove("Saturday");
        }

        ArrayAdapter arrayAdapter_weekdays = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, arrayList_weekdays);
        spinner_weekdays.setAdapter(arrayAdapter_weekdays);

    }

    void timepicker_dialog(final String val) {
        LayoutInflater factory = LayoutInflater.from(this);
        final View deleteDialogView = factory.inflate(R.layout.timepicker_layout, null);
        final AlertDialog pickerDialog = new AlertDialog.Builder(this).create();
        pickerDialog.setView(deleteDialogView);
        final TimePicker pickStartTime = (TimePicker) deleteDialogView.findViewById(R.id.StartTime);
        Button button = (Button) deleteDialogView.findViewById(R.id.button_picker_ok);
        TextView textView = (TextView) deleteDialogView.findViewById(R.id.text_alert_title);
        pickStartTime.setIs24HourView(true);
        pickStartTime.setCurrentHour(00);
        pickStartTime.setCurrentMinute(00);
        pickStartTime.setOnTimeChangedListener(mStartTimeChangedListener);

        if (val.equals("1")) {
            textView.setText("Select start time");
        } else if (val.equals("0")) {
            textView.setText("Select end time");
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hr = "", min = "";
                hour = pickStartTime.getCurrentHour();
                minute = pickStartTime.getCurrentMinute();

                if (String.valueOf(hour).length() == 1) {
                    hr = "0" + hour;
                } else {
                    hr = String.valueOf(hour);
                }
                if (String.valueOf(minute).length() == 1) {
                    min = "0" + minute;
                } else {
                    min = String.valueOf(minute);
                }

                if (val.equals("1")) {
                    text_start_time.setText(hr + ":" + min);
                } else if (val.equals("0")) {
                    text_end_time.setText(hr + ":" + min);
                }
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

    private boolean checktimings(String time, String endtime) {
        String pattern = "HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);

        try {
            Date date1 = sdf.parse(time);
            Date date2 = sdf.parse(endtime);

            if (date1.before(date2)) {
                return true;
            } else {

                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean checktimings2(String time, String endtime) {
        String pattern = "HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);

        try {
            Date date1 = sdf.parse(time);
            Date date2 = sdf.parse(endtime);

            if ((date1.before(date2)) || (date1.equals(date2))) {
                return true;
            } else {

                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

/*    private void getAnimation(String msg) {
        textView_invalid_time.setVisibility(View.VISIBLE);
        textView_invalid_time.setText(msg);
        Animation startAnimation = AnimationUtils.loadAnimation(AddServiceAvailTimeActivity.this, R.anim.blinking_animation1);
        textView_invalid_time.startAnimation(startAnimation);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                textView_invalid_time.clearAnimation();
            }
        }, 2000);

    }*/

    private void getDialog(String msg) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AddServiceAvailTimeActivity.this);
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

    private String getStartTime(String time) {
        String[] strs = time.split(" - ");
        String s_tm = strs[0];
        String e_tm = strs[1];
        return s_tm;
    }

    private String getEndTime(String time) {
        String[] strs = time.split(" - ");
        String s_tm = strs[0];
        String e_tm = strs[1];
        return e_tm;
    }
}
