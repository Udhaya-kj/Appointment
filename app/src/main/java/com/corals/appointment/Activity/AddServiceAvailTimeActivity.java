package com.corals.appointment.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.ObjectAnimator;
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
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.corals.appointment.Adapters.ServiceAvailCustomTimeAdapter;
import com.corals.appointment.Client.ApiCallback;
import com.corals.appointment.Client.ApiException;
import com.corals.appointment.Client.OkHttpApiClient;
import com.corals.appointment.Client.api.MerchantApisApi;
import com.corals.appointment.Client.model.AppointmentService;
import com.corals.appointment.Client.model.ApptTransactionBody;
import com.corals.appointment.Client.model.ApptTransactionResponse;
import com.corals.appointment.Client.model.AvailDay;
import com.corals.appointment.Client.model.SecurityAPIBody;
import com.corals.appointment.Client.model.SecurityAPIResponse;
import com.corals.appointment.Client.model.TimeData;
import com.corals.appointment.Dialogs.AlertDialogFailure;
import com.corals.appointment.Dialogs.IntermediateAlertDialog;
import com.corals.appointment.R;
import com.corals.appointment.receiver.ConnectivityReceiver;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class AddServiceAvailTimeActivity extends AppCompatActivity {

    RadioButton radioButton_biz_hrs, radioButton_custom_time;
    Spinner spinner_weekdays;
    LinearLayout layout_custom_time, layout_add_time;
    RecyclerView recyclerView;
    private IntermediateAlertDialog intermediateAlertDialog;
    TextView btn_yes_sday_p, btn_yes_mnday_p, btn_yes_tsday_p, btn_yes_wedday_p, btn_yes_trsday_p, btn_yes_fdday_p, btn_yes_strday_p, button_add_time, button_add_ser;
    boolean isActiveSunday_p = true, isActiveMonday_p = true, isActiveTuesday_p = true, isActiveWednesday_p = true, isActiveThursday_p = true, isActiveFriday_p = true, isActiveSaturday_p = true;
    TextView text_start_time, text_end_time, text_weekday;
    ArrayList<String> list_sun, list_mon, list_tue, list_wed, list_thu, list_fri, list_sat;
    String start_time, end_time, time_update_id = "";
    boolean isSelected = false;
    StringBuilder weekdays = new StringBuilder("yyyyyyy");
    int hour = 0, minute = 0;

    private SharedPreferences sharedpreferences_sessionToken;
    private SharedPreferences sharedpreferences_services;
    public static final String MyPREFERENCES_SERVICES = "MyPrefs_Services";
    public static final String SERVICE_NAME = "service_name";
    public static final String SERVICE_DURATION = "service_duration";
    private ArrayList<String> service_name_list, service_dur_list;
    String ser_name, ser_dur, ser_desc, page_id, position, amount, showamount;

    TextView textView_sun_time1, textView_sun_time2, textView_sun_time3, textView_mon_time1, textView_mon_time2, textView_mon_time3, textView_tue_time1, textView_tue_time2, textView_tue_time3, textView_wed_time1, textView_wed_time2, textView_wed_time3,
            textView_thu_time1, textView_thu_time2, textView_thu_time3, textView_fri_time1, textView_fri_time2, textView_fri_time3, textView_sat_time1, textView_sat_time2, textView_sat_time3;
    LinearLayout linearLayout__sun, linearLayout__mon, linearLayout__tue, linearLayout__wed, linearLayout__thur, linearLayout__fri, linearLayout__sat,
            linearLayout__sun2, linearLayout__mon2, linearLayout__tue2, linearLayout__wed2, linearLayout__thur2, linearLayout__fri2, linearLayout__sat2,
            linearLayout__sun3, linearLayout__mon3, linearLayout__tue3, linearLayout__wed3, linearLayout__thur3, linearLayout__fri3, linearLayout__sat3;
    ImageView imageView_edit_sun, imageView_edit_mon, imageView_edit_tue, imageView_edit_wed, imageView_edit_thu, imageView_edit_fri, imageView_edit_sat;
    ImageView imageView_warning_sun2, imageView_warning_mon2, imageView_warning_tue2, imageView_warning_wed2, imageView_warning_thu2, imageView_warning_fri2, imageView_warning_sat2,
            imageView_warning_sun3, imageView_warning_mon3, imageView_warning_tue3, imageView_warning_wed3, imageView_warning_thu3, imageView_warning_fri3, imageView_warning_sat3;
    ScrollView scrollView;
    Switch aSwitch_all_days;
    String all_days_time1 = "00:00 - 00:00", all_days_time2 = "00:00 - 00:00", all_days_time3 = "00:00 - 00:00";
    boolean isSameBizHrs = false;

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
            ser_desc = getIntent().getStringExtra("ser_desc");
            page_id = getIntent().getStringExtra("page_id");
            position = getIntent().getStringExtra("position");
            amount = getIntent().getStringExtra("amount");
            showamount = getIntent().getStringExtra("show_amount");
            Log.d("AddSer--->", "onCreate: " + ser_name + "," + ser_dur + "," + page_id + "," + position + "," + amount + "," + showamount);
        }
        sharedpreferences_services = getSharedPreferences(MyPREFERENCES_SERVICES, Context.MODE_PRIVATE);
        sharedpreferences_sessionToken = getSharedPreferences(LoginActivity.MyPREFERENCES_SESSIONTOKEN, Context.MODE_PRIVATE);
        radioButton_biz_hrs = findViewById(R.id.rb_biz_hours);
        radioButton_custom_time = findViewById(R.id.rb_custom_time);
        recyclerView = findViewById(R.id.recylerview_avail_hours);
        spinner_weekdays = findViewById(R.id.spinner_weekdays);
        layout_custom_time = findViewById(R.id.layout_custom_time);
        layout_add_time = findViewById(R.id.layout_add_time);
        btn_yes_sday_p = findViewById(R.id.btn_yes_sunday_P);
        btn_yes_mnday_p = findViewById(R.id.btn_yes_monday_P);
        btn_yes_tsday_p = findViewById(R.id.btn_yes_tuesday_P);
        btn_yes_wedday_p = findViewById(R.id.btn_yes_wedsnesday_P);
        btn_yes_trsday_p = findViewById(R.id.btn_yes_thursday_P);
        btn_yes_fdday_p = findViewById(R.id.btn_yes_friday_P);
        btn_yes_strday_p = findViewById(R.id.btn_yes_saturday_P);
        text_start_time = findViewById(R.id.text_start_time);
        text_end_time = findViewById(R.id.text_end_time);
        text_weekday = findViewById(R.id.text_weekday);
        button_add_time = findViewById(R.id.button_add_time);
        button_add_ser = findViewById(R.id.button_add_service);
        scrollView = findViewById(R.id.scrollview_avail_time);
        aSwitch_all_days = findViewById(R.id.switch_all_days);

        textView_sun_time1 = (TextView) findViewById(R.id.text_sunday_time1);
        textView_sun_time2 = (TextView) findViewById(R.id.text_sunday_time2);
        textView_sun_time3 = (TextView) findViewById(R.id.text_sunday_time3);
        textView_mon_time1 = (TextView) findViewById(R.id.text_monday_time1);
        textView_mon_time2 = (TextView) findViewById(R.id.text_monday_time2);
        textView_mon_time3 = (TextView) findViewById(R.id.text_monday_time3);
        textView_tue_time1 = (TextView) findViewById(R.id.text_tuesday_time1);
        textView_tue_time2 = (TextView) findViewById(R.id.text_tuesday_time2);
        textView_tue_time3 = (TextView) findViewById(R.id.text_tuesday_time3);
        textView_wed_time1 = (TextView) findViewById(R.id.text_wednesday_time1);
        textView_wed_time2 = (TextView) findViewById(R.id.text_wednesday_time2);
        textView_wed_time3 = (TextView) findViewById(R.id.text_wednesday_time3);
        textView_thu_time1 = (TextView) findViewById(R.id.text_thursday_time1);
        textView_thu_time2 = (TextView) findViewById(R.id.text_thursday_time2);
        textView_thu_time3 = (TextView) findViewById(R.id.text_thursday_time3);
        textView_fri_time1 = (TextView) findViewById(R.id.text_friday_time1);
        textView_fri_time2 = (TextView) findViewById(R.id.text_friday_time2);
        textView_fri_time3 = (TextView) findViewById(R.id.text_friday_time3);
        textView_sat_time1 = (TextView) findViewById(R.id.text_saturday_time1);
        textView_sat_time2 = (TextView) findViewById(R.id.text_saturday_time2);
        textView_sat_time3 = (TextView) findViewById(R.id.text_saturday_time3);

        imageView_edit_sun = (ImageView) findViewById(R.id.image_edit_sun);
        imageView_edit_mon = (ImageView) findViewById(R.id.image_edit_mon);
        imageView_edit_tue = (ImageView) findViewById(R.id.image_edit_tue);
        imageView_edit_wed = (ImageView) findViewById(R.id.image_edit_wed);
        imageView_edit_thu = (ImageView) findViewById(R.id.image_edit_thu);
        imageView_edit_fri = (ImageView) findViewById(R.id.image_edit_fri);
        imageView_edit_sat = (ImageView) findViewById(R.id.image_edit_sat);


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

        linearLayout__sun3 = (LinearLayout) findViewById(R.id.layout_sunday_time3);
        linearLayout__mon3 = (LinearLayout) findViewById(R.id.layout_monday_time3);
        linearLayout__tue3 = (LinearLayout) findViewById(R.id.layout_tuesday_time3);
        linearLayout__wed3 = (LinearLayout) findViewById(R.id.layout_wednesday_time3);
        linearLayout__thur3 = (LinearLayout) findViewById(R.id.layout_thursday_time3);
        linearLayout__fri3 = (LinearLayout) findViewById(R.id.layout_friday_time3);
        linearLayout__sat3 = (LinearLayout) findViewById(R.id.layout_saturday_time3);

        imageView_warning_sun2 = (ImageView) findViewById(R.id.image_warning_sun2);
        imageView_warning_mon2 = (ImageView) findViewById(R.id.image_warning_mon2);
        imageView_warning_tue2 = (ImageView) findViewById(R.id.image_warning_tue2);
        imageView_warning_wed2 = (ImageView) findViewById(R.id.image_warning_wed2);
        imageView_warning_thu2 = (ImageView) findViewById(R.id.image_warning_thu2);
        imageView_warning_fri2 = (ImageView) findViewById(R.id.image_warning_fri2);
        imageView_warning_sat2 = (ImageView) findViewById(R.id.image_warning_sat2);

        imageView_warning_sun3 = (ImageView) findViewById(R.id.image_warning_sun3);
        imageView_warning_mon3 = (ImageView) findViewById(R.id.image_warning_mon3);
        imageView_warning_tue3 = (ImageView) findViewById(R.id.image_warning_tue3);
        imageView_warning_wed3 = (ImageView) findViewById(R.id.image_warning_wed3);
        imageView_warning_thu3 = (ImageView) findViewById(R.id.image_warning_thu3);
        imageView_warning_fri3 = (ImageView) findViewById(R.id.image_warning_fri3);
        imageView_warning_sat3 = (ImageView) findViewById(R.id.image_warning_sat3);


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
                    isSameBizHrs = true;
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
                    isSameBizHrs = false;
                }
                isSelected = true;
            }
        });

        aSwitch_all_days.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (list_sun.isEmpty() && list_mon.isEmpty() && list_tue.isEmpty() && list_wed.isEmpty() && list_thu.isEmpty() && list_fri.isEmpty() && list_sat.isEmpty()) {
                        getAllDaysTime("00:00 - 00:00", "00:00 - 00:00", "00:00 - 00:00");
                    } else {
                        if (!list_sun.isEmpty()) {
                            if (list_sun.size() == 1) {
                                all_days_time1 = list_sun.get(0);
                            } else if (list_sun.size() == 2) {
                                all_days_time1 = list_sun.get(0);
                                all_days_time2 = list_sun.get(1);
                            } else if (list_sun.size() == 3) {
                                all_days_time1 = list_sun.get(0);
                                all_days_time2 = list_sun.get(1);
                                all_days_time3 = list_sun.get(2);
                            }
                        } else if (!list_mon.isEmpty()) {
                            if (list_mon.size() == 1) {
                                all_days_time1 = list_mon.get(0);
                            } else if (list_mon.size() == 2) {
                                all_days_time1 = list_mon.get(0);
                                all_days_time2 = list_mon.get(1);
                            } else if (list_mon.size() == 3) {
                                all_days_time1 = list_mon.get(0);
                                all_days_time2 = list_mon.get(1);
                                all_days_time3 = list_mon.get(2);
                            }
                        } else if (!list_tue.isEmpty()) {
                            if (list_tue.size() == 1) {
                                all_days_time1 = list_tue.get(0);
                            } else if (list_tue.size() == 2) {
                                all_days_time1 = list_tue.get(0);
                                all_days_time2 = list_tue.get(1);
                            } else if (list_tue.size() == 3) {
                                all_days_time1 = list_tue.get(0);
                                all_days_time2 = list_tue.get(1);
                                all_days_time3 = list_tue.get(2);
                            }
                        } else if (!list_wed.isEmpty()) {
                            if (list_wed.size() == 1) {
                                all_days_time1 = list_wed.get(0);
                            } else if (list_wed.size() == 2) {
                                all_days_time1 = list_wed.get(0);
                                all_days_time2 = list_wed.get(1);
                            } else if (list_wed.size() == 3) {
                                all_days_time1 = list_wed.get(0);
                                all_days_time2 = list_wed.get(1);
                                all_days_time3 = list_wed.get(2);
                            }
                        } else if (!list_thu.isEmpty()) {
                            if (list_thu.size() == 1) {
                                all_days_time1 = list_thu.get(0);
                            } else if (list_thu.size() == 2) {
                                all_days_time1 = list_thu.get(0);
                                all_days_time2 = list_thu.get(1);
                            } else if (list_thu.size() == 3) {
                                all_days_time1 = list_thu.get(0);
                                all_days_time2 = list_thu.get(1);
                                all_days_time3 = list_thu.get(2);
                            }
                        } else if (!list_fri.isEmpty()) {
                            if (list_fri.size() == 1) {
                                all_days_time1 = list_fri.get(0);
                            } else if (list_fri.size() == 2) {
                                all_days_time1 = list_fri.get(0);
                                all_days_time2 = list_fri.get(1);
                            } else if (list_fri.size() == 3) {
                                all_days_time1 = list_fri.get(0);
                                all_days_time2 = list_fri.get(1);
                                all_days_time3 = list_fri.get(2);
                            }
                        } else if (!list_sat.isEmpty()) {
                            if (list_sat.size() == 1) {
                                all_days_time1 = list_sat.get(0);
                            } else if (list_sat.size() == 2) {
                                all_days_time1 = list_sat.get(0);
                                all_days_time2 = list_sat.get(1);
                            } else if (list_sat.size() == 3) {
                                all_days_time1 = list_sat.get(0);
                                all_days_time2 = list_sat.get(1);
                                all_days_time3 = list_sat.get(2);
                            }
                        }
                   /*     if (all_days_time2.equals("00:00 - 00:00")) {
                            all_days_time2 = "--";
                        }
                        if (all_days_time3.equals("00:00 - 00:00")) {
                            all_days_time3 = "--";
                        }*/

                        getAllDaysTime(all_days_time1, all_days_time2, all_days_time3);
                        getClearList();
                        getCopyTimeAllDays(String.valueOf(weekdays), all_days_time1, all_days_time2, all_days_time3);
                        Log.d("Copy_all_day---->", "onCheckedChanged: " + all_days_time1 + "," + all_days_time2 + "," + all_days_time3);
                    }

                } else {
                    getAllDaysTime("00:00 - 00:00", "00:00 - 00:00", "00:00 - 00:00");
                    getClearList();
                    all_days_time1 = "00:00 - 00:00";
                    all_days_time2 = "00:00 - 00:00";
                    all_days_time3 = "00:00 - 00:00";
                }
            }
        });
        final ArrayList<String> arrayList_weekdays = new ArrayList<>();

        spinner_weekdays.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                layout_add_time.setVisibility(View.VISIBLE);
                String day = parent.getSelectedItem().toString();
                // String day = arrayList_weekdays.get(position);
                text_weekday.setText("" + day);
                text_start_time.setText("00:00");
                text_end_time.setText("00:00");
                button_add_time.setText("ADD");
                time_update_id = "";
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
                    getWeekDaysLayout(String.valueOf(weekdays));
                    Log.d("Weekdayslist--->", "onClick: " + weekdays);
                } else {
                    btn_yes_sday_p.setBackgroundColor(getResources().getColor(R.color.green_hase));
                    btn_yes_sday_p.setTextColor(getResources().getColor(R.color.white));
                    isActiveSunday_p = true;
                    //arrayList_weekdays.add(0, "Sunday");

                    weekdays.setCharAt(0, 'y');
                    getWeekDays(String.valueOf(weekdays));
                    getWeekDaysLayout(String.valueOf(weekdays));
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
                    getWeekDaysLayout(String.valueOf(weekdays));
                    Log.d("Weekdayslist--->", "onClick: " + weekdays);
                } else {
                    btn_yes_mnday_p.setBackgroundColor(getResources().getColor(R.color.green_hase));
                    btn_yes_mnday_p.setTextColor(getResources().getColor(R.color.white));
                    isActiveMonday_p = true;
                    //arrayList_weekdays.add(1, "Monday");
                    weekdays.setCharAt(1, 'y');
                    getWeekDays(String.valueOf(weekdays));
                    getWeekDaysLayout(String.valueOf(weekdays));
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
                    getWeekDaysLayout(String.valueOf(weekdays));
                    Log.d("Weekdayslist--->", "onClick: " + weekdays);
                } else {
                    btn_yes_tsday_p.setBackgroundColor(getResources().getColor(R.color.green_hase));
                    btn_yes_tsday_p.setTextColor(getResources().getColor(R.color.white));
                    isActiveTuesday_p = true;
                    //arrayList_weekdays.add(2, "Tuesday");

                    weekdays.setCharAt(2, 'y');
                    getWeekDays(String.valueOf(weekdays));
                    getWeekDaysLayout(String.valueOf(weekdays));
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
                    getWeekDaysLayout(String.valueOf(weekdays));
                    Log.d("Weekdayslist--->", "onClick: " + weekdays);
                } else {
                    btn_yes_wedday_p.setBackgroundColor(getResources().getColor(R.color.green_hase));
                    btn_yes_wedday_p.setTextColor(getResources().getColor(R.color.white));
                    isActiveWednesday_p = true;
                    //arrayList_weekdays.add(3, "Wednesday");

                    weekdays.setCharAt(3, 'y');
                    getWeekDays(String.valueOf(weekdays));
                    getWeekDaysLayout(String.valueOf(weekdays));
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
                    getWeekDaysLayout(String.valueOf(weekdays));
                    Log.d("Weekdayslist--->", "onClick: " + weekdays);
                } else {
                    btn_yes_trsday_p.setBackgroundColor(getResources().getColor(R.color.green_hase));
                    btn_yes_trsday_p.setTextColor(getResources().getColor(R.color.white));
                    isActiveThursday_p = true;
                    // arrayList_weekdays.add(4, "Thursday");

                    weekdays.setCharAt(4, 'y');
                    getWeekDays(String.valueOf(weekdays));
                    getWeekDaysLayout(String.valueOf(weekdays));
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
                    getWeekDaysLayout(String.valueOf(weekdays));
                    Log.d("Weekdayslist--->", "onClick: " + weekdays);
                } else {
                    btn_yes_fdday_p.setBackgroundColor(getResources().getColor(R.color.green_hase));
                    btn_yes_fdday_p.setTextColor(getResources().getColor(R.color.white));
                    isActiveFriday_p = true;
                    //arrayList_weekdays.add(5, "Friday");

                    weekdays.setCharAt(5, 'y');
                    getWeekDays(String.valueOf(weekdays));
                    getWeekDaysLayout(String.valueOf(weekdays));
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
                    getWeekDaysLayout(String.valueOf(weekdays));
                    Log.d("Weekdayslist--->", "onClick: " + weekdays);
                } else {
                    btn_yes_strday_p.setBackgroundColor(getResources().getColor(R.color.green_hase));
                    btn_yes_strday_p.setTextColor(getResources().getColor(R.color.white));
                    isActiveSaturday_p = true;
                    //arrayList_weekdays.add(6, "Saturday");

                    weekdays.setCharAt(6, 'y');
                    getWeekDays(String.valueOf(weekdays));
                    getWeekDaysLayout(String.valueOf(weekdays));
                    Log.d("Weekdayslist--->", "onClick: " + weekdays);
                }
            }
        });


        //Edit sunday
        imageView_edit_sun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                text_weekday.setText("Sunday");
                text_start_time.setText("00:00");
                text_end_time.setText("00:00");
                list_sun.clear();
                textView_sun_time1.setText("00:00 - 00:00");
                textView_sun_time2.setText("00:00 - 00:00");
                textView_sun_time3.setText("00:00 - 00:00");
                getScrollTop();
            }
        });


        //Edit monday
        imageView_edit_mon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text_weekday.setText("Monday");
                text_start_time.setText("00:00");
                text_end_time.setText("00:00");
                list_mon.clear();
                textView_mon_time1.setText("00:00 - 00:00");
                textView_mon_time2.setText("00:00 - 00:00");
                textView_mon_time3.setText("00:00 - 00:00");
                getScrollTop();
            }
        });

        //Edit tuesday
        imageView_edit_tue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text_weekday.setText("Tuesday");
                text_start_time.setText("00:00");
                text_end_time.setText("00:00");
                list_tue.clear();
                textView_tue_time1.setText("00:00 - 00:00");
                textView_tue_time2.setText("00:00 - 00:00");
                textView_tue_time3.setText("00:00 - 00:00");
                getScrollTop();
            }
        });

        //Edit wednesday
        imageView_edit_wed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text_weekday.setText("Wednesday");
                text_start_time.setText("00:00");
                text_end_time.setText("00:00");
                list_wed.clear();
                textView_wed_time1.setText("00:00 - 00:00");
                textView_wed_time2.setText("00:00 - 00:00");
                textView_wed_time3.setText("00:00 - 00:00");
                getScrollTop();
            }
        });

        //Edit thursday
        imageView_edit_thu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text_weekday.setText("Thursday");
                text_start_time.setText("00:00");
                text_end_time.setText("00:00");
                list_thu.clear();
                textView_thu_time1.setText("00:00 - 00:00");
                textView_thu_time2.setText("00:00 - 00:00");
                textView_thu_time3.setText("00:00 - 00:00");
                getScrollTop();
            }
        });


        //Edit friday
        imageView_edit_fri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text_weekday.setText("Friday");
                text_start_time.setText("00:00");
                text_end_time.setText("00:00");
                list_fri.clear();
                textView_fri_time1.setText("00:00 - 00:00");
                textView_fri_time2.setText("00:00 - 00:00");
                textView_fri_time3.setText("00:00 - 00:00");
                getScrollTop();
            }
        });


        //Edit saturday
        imageView_edit_sat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text_weekday.setText("Saturday");
                text_start_time.setText("00:00");
                text_end_time.setText("00:00");
                list_sat.clear();
                textView_sat_time1.setText("00:00 - 00:00");
                textView_sat_time2.setText("00:00 - 00:00");
                textView_sat_time3.setText("00:00 - 00:00");
                getScrollTop();
            }
        });


        button_add_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("Weekdays_ListSize---", "onClick: " + list_sun.size());
                String weekday = text_weekday.getText().toString();
                String s_time = text_start_time.getText().toString();
                String e_time = text_end_time.getText().toString();

                boolean chktime = checktimings(s_time, e_time);
                if (chktime) {
                    if (weekday.equals("Sunday")) {

                        if (list_sun.size() <= 2) {
                            if (list_sun.size() == 1) {
                                String data_sun = list_sun.get(0);
                                String[] strs = data_sun.split(" - ");
                                String s_tm = strs[0];
                                String e_tm = strs[1];

                                boolean chktime_slot2 = checktimings2(e_tm, s_time);
                                if (chktime_slot2) {
                                    list_sun.add(s_time + " - " + e_time);
                                    textView_sun_time2.setText(list_sun.get(1));
                                    runAnimation(textView_sun_time2);
                                    getAddTimeScrollBottom(linearLayout__sun);

                                    Log.d("sunday_list--->", "add_time: " + list_sun);
                                } else {
                                    getDialog("Availability time cannot overlap");
                                }

                            } else if (list_sun.size() == 2) {
                                String data_sun = list_sun.get(1);
                                String[] strs = data_sun.split(" - ");
                                String s_tm = strs[0];
                                String e_tm = strs[1];

                                boolean chktime_slot2 = checktimings2(e_tm, s_time);
                                if (chktime_slot2) {
                                    list_sun.add(s_time + " - " + e_time);
                                    textView_sun_time3.setText(list_sun.get(2));
                                    runAnimation(textView_sun_time3);
                                    getAddTimeScrollBottom(linearLayout__sun);
                                    Log.d("sunday_list--->", "add_time: " + list_sun);
                                } else {
                                    getDialog("Availability time cannot overlap");
                                }

                            } else {

                                list_sun.add(s_time + " - " + e_time);
                                textView_sun_time1.setText(list_sun.get(0));
                                linearLayout__sun.setVisibility(View.VISIBLE);
                                runAnimation(textView_sun_time1);
                                getAddTimeScrollBottom(linearLayout__sun);

                                /*recyclerView.setVisibility(View.VISIBLE);
                                ServiceAvailCustomTimeAdapter timeAdapter = new ServiceAvailCustomTimeAdapter(AddServiceAvailTimeActivity.this, list_sun, list_mon, list_tue, list_wed, list_thu, list_fri, list_sat);
                                recyclerView.setAdapter(timeAdapter);
                                timeAdapter.notifyDataSetChanged();*/
                            }
                        } else {
                            getDialog("You have reached the limit for sunday");
                        }


                    } else if (weekday.equals("Monday")) {

                        if (list_mon.size() <= 2) {
                            if (list_mon.size() == 1) {
                                String data_sun = list_mon.get(0);
                                String[] strs = data_sun.split(" - ");
                                String s_tm = strs[0];
                                String e_tm = strs[1];

                                boolean chktime_slot2 = checktimings2(e_tm, s_time);
                                if (chktime_slot2) {
                                    list_mon.add(s_time + " - " + e_time);
                                    textView_mon_time2.setText(list_mon.get(1));
                                    getAddTimeScrollBottom(linearLayout__mon);
                                    runAnimation(textView_mon_time2);
                                } else {
                                    getDialog("Availability time cannot overlap");
                                }

                            } else if (list_mon.size() == 2) {
                                String data_sun = list_mon.get(1);
                                String[] strs = data_sun.split(" - ");
                                String s_tm = strs[0];
                                String e_tm = strs[1];

                                boolean chktime_slot2 = checktimings2(e_tm, s_time);
                                if (chktime_slot2) {
                                    list_mon.add(s_time + " - " + e_time);
                                    textView_mon_time3.setText(list_mon.get(2));
                                    getAddTimeScrollBottom(linearLayout__mon);
                                    runAnimation(textView_mon_time3);
                                } else {
                                    getDialog("Availability time cannot overlap");
                                }

                            } else {

                                list_mon.add(s_time + " - " + e_time);
                                textView_mon_time1.setText(list_mon.get(0));
                                linearLayout__mon.setVisibility(View.VISIBLE);
                                getAddTimeScrollBottom(linearLayout__mon);
                                runAnimation(textView_mon_time1);

                                /*recyclerView.setVisibility(View.VISIBLE);
                                ServiceAvailCustomTimeAdapter timeAdapter = new ServiceAvailCustomTimeAdapter(AddServiceAvailTimeActivity.this, list_sun, list_mon, list_tue, list_wed, list_thu, list_fri, list_sat);
                                recyclerView.setAdapter(timeAdapter);
                                timeAdapter.notifyDataSetChanged();*/
                            }
                        } else {
                            getDialog("You have reached the limit for monday");
                        }


                    } else if (weekday.equals("Tuesday")) {
                        if (list_tue.size() <= 2) {
                            if (list_tue.size() == 1) {
                                String data_sun = list_tue.get(0);
                                String[] strs = data_sun.split(" - ");
                                String s_tm = strs[0];
                                String e_tm = strs[1];

                                boolean chktime_slot2 = checktimings2(e_tm, s_time);
                                if (chktime_slot2) {
                                    list_tue.add(s_time + " - " + e_time);
                                    textView_tue_time2.setText(list_tue.get(1));
                                    getAddTimeScrollBottom(linearLayout__tue);
                                    runAnimation(textView_tue_time2);
                                } else {
                                    getDialog("Availability time cannot overlap");
                                }

                            } else if (list_tue.size() == 2) {
                                String data_sun = list_tue.get(1);
                                String[] strs = data_sun.split(" - ");
                                String s_tm = strs[0];
                                String e_tm = strs[1];

                                boolean chktime_slot2 = checktimings2(e_tm, s_time);
                                if (chktime_slot2) {
                                    list_tue.add(s_time + " - " + e_time);
                                    textView_tue_time3.setText(list_tue.get(2));
                                    getAddTimeScrollBottom(linearLayout__tue);
                                    runAnimation(textView_tue_time3);
                                } else {
                                    getDialog("Availability time cannot overlap");
                                }

                            } else {

                                list_tue.add(s_time + " - " + e_time);
                                textView_tue_time1.setText(list_tue.get(0));
                                linearLayout__tue.setVisibility(View.VISIBLE);
                                getAddTimeScrollBottom(linearLayout__tue);
                                runAnimation(textView_tue_time1);

                                /*recyclerView.setVisibility(View.VISIBLE);
                                ServiceAvailCustomTimeAdapter timeAdapter = new ServiceAvailCustomTimeAdapter(AddServiceAvailTimeActivity.this, list_sun, list_mon, list_tue, list_wed, list_thu, list_fri, list_sat);
                                recyclerView.setAdapter(timeAdapter);
                                timeAdapter.notifyDataSetChanged();*/
                            }
                        } else {
                            getDialog("You have reached the limit for tuesday");
                        }

                    } else if (weekday.equals("Wednesday")) {
                        if (list_wed.size() <= 2) {
                            if (list_wed.size() == 1) {
                                String data_sun = list_wed.get(0);
                                String[] strs = data_sun.split(" - ");
                                String s_tm = strs[0];
                                String e_tm = strs[1];

                                boolean chktime_slot2 = checktimings2(e_tm, s_time);
                                if (chktime_slot2) {
                                    list_wed.add(s_time + " - " + e_time);
                                    textView_wed_time2.setText(list_wed.get(1));
                                    getAddTimeScrollBottom(linearLayout__wed);
                                    runAnimation(textView_wed_time2);
                                } else {
                                    getDialog("Availability time cannot overlap");
                                }

                            } else if (list_wed.size() == 2) {
                                String data_sun = list_wed.get(1);
                                String[] strs = data_sun.split(" - ");
                                String s_tm = strs[0];
                                String e_tm = strs[1];

                                boolean chktime_slot2 = checktimings2(e_tm, s_time);
                                if (chktime_slot2) {
                                    list_wed.add(s_time + " - " + e_time);
                                    textView_wed_time3.setText(list_wed.get(2));
                                    getAddTimeScrollBottom(linearLayout__wed);
                                    runAnimation(textView_wed_time3);
                                } else {
                                    getDialog("Availability time cannot overlap");
                                }

                            } else {

                                list_wed.add(s_time + " - " + e_time);
                                textView_wed_time1.setText(list_wed.get(0));
                                linearLayout__wed.setVisibility(View.VISIBLE);
                                getAddTimeScrollBottom(linearLayout__wed);
                                runAnimation(textView_wed_time1);

                                /*recyclerView.setVisibility(View.VISIBLE);
                                ServiceAvailCustomTimeAdapter timeAdapter = new ServiceAvailCustomTimeAdapter(AddServiceAvailTimeActivity.this, list_sun, list_mon, list_tue, list_wed, list_thu, list_fri, list_sat);
                                recyclerView.setAdapter(timeAdapter);
                                timeAdapter.notifyDataSetChanged();*/
                            }
                        } else {
                            getDialog("You have reached the limit for wednesday");
                        }

                    } else if (weekday.equals("Thursday")) {
                        if (list_thu.size() <= 2) {
                            if (list_thu.size() == 1) {
                                String data_sun = list_thu.get(0);
                                String[] strs = data_sun.split(" - ");
                                String s_tm = strs[0];
                                String e_tm = strs[1];

                                boolean chktime_slot2 = checktimings2(e_tm, s_time);
                                if (chktime_slot2) {
                                    list_thu.add(s_time + " - " + e_time);
                                    textView_thu_time2.setText(list_thu.get(1));
                                    getAddTimeScrollBottom(linearLayout__thur);
                                    runAnimation(textView_thu_time2);
                                } else {
                                    getDialog("Availability time cannot overlap");
                                }

                            } else if (list_thu.size() == 2) {
                                String data_sun = list_thu.get(1);
                                String[] strs = data_sun.split(" - ");
                                String s_tm = strs[0];
                                String e_tm = strs[1];

                                boolean chktime_slot2 = checktimings2(e_tm, s_time);
                                if (chktime_slot2) {
                                    list_thu.add(s_time + " - " + e_time);
                                    textView_thu_time3.setText(list_thu.get(2));
                                    getAddTimeScrollBottom(linearLayout__thur);
                                    runAnimation(textView_thu_time3);
                                } else {
                                    getDialog("Availability time cannot overlap");
                                }

                            } else {

                                list_thu.add(s_time + " - " + e_time);
                                textView_thu_time1.setText(list_thu.get(0));
                                linearLayout__thur.setVisibility(View.VISIBLE);
                                getAddTimeScrollBottom(linearLayout__thur);
                                runAnimation(textView_thu_time1);
                                /*recyclerView.setVisibility(View.VISIBLE);
                                ServiceAvailCustomTimeAdapter timeAdapter = new ServiceAvailCustomTimeAdapter(AddServiceAvailTimeActivity.this, list_sun, list_mon, list_tue, list_wed, list_thu, list_fri, list_sat);
                                recyclerView.setAdapter(timeAdapter);
                                timeAdapter.notifyDataSetChanged();*/
                            }
                        } else {
                            getDialog("You have reached the limit for thursday");
                        }

                    } else if (weekday.equals("Friday")) {
                        if (list_fri.size() <= 2) {
                            if (list_fri.size() == 1) {
                                String data_sun = list_fri.get(0);
                                String[] strs = data_sun.split(" - ");
                                String s_tm = strs[0];
                                String e_tm = strs[1];

                                boolean chktime_slot2 = checktimings2(e_tm, s_time);
                                if (chktime_slot2) {
                                    list_fri.add(s_time + " - " + e_time);
                                    textView_fri_time2.setText(list_fri.get(1));
                                    getAddTimeScrollBottom(linearLayout__fri);
                                    runAnimation(textView_fri_time2);
                                } else {
                                    getDialog("Availability time cannot overlap");
                                }

                            } else if (list_fri.size() == 2) {
                                String data_sun = list_fri.get(1);
                                String[] strs = data_sun.split(" - ");
                                String s_tm = strs[0];
                                String e_tm = strs[1];

                                boolean chktime_slot2 = checktimings2(e_tm, s_time);
                                if (chktime_slot2) {
                                    list_fri.add(s_time + " - " + e_time);
                                    textView_fri_time3.setText(list_fri.get(2));
                                    getAddTimeScrollBottom(linearLayout__fri);
                                    runAnimation(textView_fri_time3);
                                } else {
                                    getDialog("Availability time cannot overlap");
                                }

                            } else {

                                list_fri.add(s_time + " - " + e_time);
                                textView_fri_time1.setText(list_fri.get(0));
                                linearLayout__fri.setVisibility(View.VISIBLE);
                                getAddTimeScrollBottom(linearLayout__fri);
                                runAnimation(textView_fri_time1);
                                /*recyclerView.setVisibility(View.VISIBLE);
                                ServiceAvailCustomTimeAdapter timeAdapter = new ServiceAvailCustomTimeAdapter(AddServiceAvailTimeActivity.this, list_sun, list_mon, list_tue, list_wed, list_thu, list_fri, list_sat);
                                recyclerView.setAdapter(timeAdapter);
                                timeAdapter.notifyDataSetChanged();*/
                            }
                        } else {
                            getDialog("You have reached the limit for friday");
                        }

                    } else if (weekday.equals("Saturday")) {
                        if (list_sat.size() <= 2) {
                            if (list_sat.size() == 1) {
                                String data_sun = list_sat.get(0);
                                String[] strs = data_sun.split(" - ");
                                String s_tm = strs[0];
                                String e_tm = strs[1];

                                boolean chktime_slot2 = checktimings2(e_tm, s_time);
                                if (chktime_slot2) {
                                    list_sat.add(s_time + " - " + e_time);
                                    textView_sat_time2.setText(list_sat.get(1));
                                    getAddTimeScrollBottom(linearLayout__sat);
                                    runAnimation(textView_sat_time2);
                                } else {
                                    getDialog("Availability time cannot overlap");
                                }

                            } else if (list_sat.size() == 2) {
                                String data_sun = list_sat.get(1);
                                String[] strs = data_sun.split(" - ");
                                String s_tm = strs[0];
                                String e_tm = strs[1];

                                boolean chktime_slot2 = checktimings2(e_tm, s_time);
                                if (chktime_slot2) {
                                    list_sat.add(s_time + " - " + e_time);
                                    textView_sat_time3.setText(list_sat.get(2));
                                    getAddTimeScrollBottom(linearLayout__sat);
                                    runAnimation(textView_sat_time3);
                                } else {
                                    getDialog("Availability time cannot overlap");
                                }

                            } else {

                                list_sat.add(s_time + " - " + e_time);
                                textView_sat_time1.setText(list_sat.get(0));
                                linearLayout__sat.setVisibility(View.VISIBLE);
                                getAddTimeScrollBottom(linearLayout__sat);
                                runAnimation(textView_sat_time1);
                                /*recyclerView.setVisibility(View.VISIBLE);
                                ServiceAvailCustomTimeAdapter timeAdapter = new ServiceAvailCustomTimeAdapter(AddServiceAvailTimeActivity.this, list_sun, list_mon, list_tue, list_wed, list_thu, list_fri, list_sat);
                                recyclerView.setAdapter(timeAdapter);
                                timeAdapter.notifyDataSetChanged();*/
                            }
                        } else {
                            getDialog("You have reached the limit for saturday");
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
                try {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                } catch (Exception e) {
                    // TODO: handle exception
                }
                Log.d("sunday_list--->", "onClick: " + list_sun.size() + "," + list_mon.size() + "," + list_tue.size() + "," + list_wed.size() + "," + list_thu.size() + "," + list_fri.size() + "," + list_sat.size());
                if (isSelected) {
                    AvailDay availDay_sun = new AvailDay();
                    AvailDay availDay_mon = new AvailDay();
                    AvailDay availDay_tue = new AvailDay();
                    AvailDay availDay_wed = new AvailDay();
                    AvailDay availDay_thu = new AvailDay();
                    AvailDay availDay_fri = new AvailDay();
                    AvailDay availDay_sat = new AvailDay();

                    List<TimeData> timeDataList_sun = new ArrayList<>();
                    List<TimeData> timeDataList_mon = new ArrayList<>();
                    List<TimeData> timeDataList_tue = new ArrayList<>();
                    List<TimeData> timeDataList_wed = new ArrayList<>();
                    List<TimeData> timeDataList_thu = new ArrayList<>();
                    List<TimeData> timeDataList_fri = new ArrayList<>();
                    List<TimeData> timeDataList_sat = new ArrayList<>();


                    TimeData timeData_sun1 = new TimeData();
                    TimeData timeData_sun2 = new TimeData();
                    TimeData timeData_sun3 = new TimeData();

                    TimeData timeData_mon1 = new TimeData();
                    TimeData timeData_mon2 = new TimeData();
                    TimeData timeData_mon3 = new TimeData();

                    TimeData timeData_tue1 = new TimeData();
                    TimeData timeData_tue2 = new TimeData();
                    TimeData timeData_tue3 = new TimeData();

                    TimeData timeData_wed1 = new TimeData();
                    TimeData timeData_wed2 = new TimeData();
                    TimeData timeData_wed3 = new TimeData();

                    TimeData timeData_thu1 = new TimeData();
                    TimeData timeData_thu2 = new TimeData();
                    TimeData timeData_thu3 = new TimeData();

                    TimeData timeData_fri1 = new TimeData();
                    TimeData timeData_fri2 = new TimeData();
                    TimeData timeData_fri3 = new TimeData();

                    TimeData timeData_sat1 = new TimeData();
                    TimeData timeData_sat2 = new TimeData();
                    TimeData timeData_sat3 = new TimeData();

                    //Sunday
                    if (!list_sun.isEmpty()) {
                        Log.d("sunday_list--->", "submit: " + list_sun);
                        if (list_sun.size() > 0) {
                            String[] strs = list_sun.get(0).split(" - ");
                            String s_tm = strs[0];
                            String e_tm = strs[1];

                            timeData_sun1.setAvailId("");
                            timeData_sun1.setStartTime(s_tm + ":00");
                            timeData_sun1.setEndTime(e_tm + ":00");
                            timeDataList_sun.add(timeData_sun1);
                            Log.d("Sun--->", "onClick: >0" + " 1");
                        }

                        if (list_sun.size() > 1) {
                            String[] strs = list_sun.get(1).split(" - ");
                            String s_tm = strs[0];
                            String e_tm = strs[1];

                            timeData_sun2.setAvailId("");
                            timeData_sun2.setStartTime(s_tm + ":00");
                            timeData_sun2.setEndTime(e_tm + ":00");
                            timeDataList_sun.add(timeData_sun2);
                            Log.d("Sun--->", "onClick: > 1" + " 2");
                        }

                        if (list_sun.size() > 2) {
                            String[] strs = list_sun.get(2).split(" - ");
                            String s_tm = strs[0];
                            String e_tm = strs[1];

                            timeData_sun3.setAvailId("");
                            timeData_sun3.setStartTime(s_tm + ":00");
                            timeData_sun3.setEndTime(e_tm + ":00");
                            timeDataList_sun.add(timeData_sun3);
                            Log.d("Sun--->", "onClick: ==3" + " 3");
                        }

                        availDay_sun.setDay("1");
                        availDay_sun.setTiming(timeDataList_sun);
                    } else {
                        timeData_sun1.setAvailId("");
                        timeData_sun1.setStartTime("00:00:00");
                        timeData_sun1.setEndTime("00:00:00");

                        timeDataList_sun.add(timeData_sun1);

                        availDay_sun.setDay("1");
                        availDay_sun.setTiming(timeDataList_sun);
                    }

                    //Monday
                    if (!list_mon.isEmpty()) {
                        if (list_mon.size() > 0) {
                            String[] strs = list_mon.get(0).split(" - ");
                            String s_tm = strs[0];
                            String e_tm = strs[1];

                            timeData_mon1.setAvailId("");
                            timeData_mon1.setStartTime(s_tm + ":00");
                            timeData_mon1.setEndTime(e_tm + ":00");
                            timeDataList_mon.add(timeData_mon1);
                            Log.d("Mon--->", "onClick: >0" + " 1");
                        }

                        if (list_mon.size() > 1) {
                            String[] strs = list_mon.get(1).split(" - ");
                            String s_tm = strs[0];
                            String e_tm = strs[1];

                            timeData_mon2.setAvailId("");
                            timeData_mon2.setStartTime(s_tm + ":00");
                            timeData_mon2.setEndTime(e_tm + ":00");
                            timeDataList_mon.add(timeData_mon2);
                            Log.d("Mon--->", "onClick: > 1" + " 2");
                        }

                        if (list_mon.size() > 2) {
                            String[] strs = list_mon.get(2).split(" - ");
                            String s_tm = strs[0];
                            String e_tm = strs[1];

                            timeData_mon3.setAvailId("");
                            timeData_mon3.setStartTime(s_tm + ":00");
                            timeData_mon3.setEndTime(e_tm + ":00");
                            timeDataList_mon.add(timeData_mon3);
                            Log.d("Mon--->", "onClick: ==3" + " 3");
                        }

                        availDay_mon.setDay("2");
                        availDay_mon.setTiming(timeDataList_mon);
                    } else {
                        timeData_mon1.setAvailId("");
                        timeData_mon1.setStartTime("00:00:00");
                        timeData_mon1.setEndTime("00:00:00");

                        timeDataList_mon.add(timeData_mon1);
                        availDay_mon.setDay("2");
                        availDay_mon.setTiming(timeDataList_mon);
                    }


                    //Tuesday
                    if (!list_tue.isEmpty()) {
                        if (list_tue.size() > 0) {
                            String[] strs = list_tue.get(0).split(" - ");
                            String s_tm = strs[0];
                            String e_tm = strs[1];

                            timeData_tue1.setAvailId("");
                            timeData_tue1.setStartTime(s_tm + ":00");
                            timeData_tue1.setEndTime(e_tm + ":00");
                            timeDataList_tue.add(timeData_tue1);
                            Log.d("Tue--->", "onClick: >0" + " 1");
                        }

                        if (list_tue.size() > 1) {
                            String[] strs = list_tue.get(1).split(" - ");
                            String s_tm = strs[0];
                            String e_tm = strs[1];

                            timeData_tue2.setAvailId("");
                            timeData_tue2.setStartTime(s_tm + ":00");
                            timeData_tue2.setEndTime(e_tm + ":00");
                            timeDataList_tue.add(timeData_tue2);
                            Log.d("Tue--->", "onClick: > 1" + " 2");
                        }

                        if (list_tue.size() > 2) {
                            String[] strs = list_tue.get(2).split(" - ");
                            String s_tm = strs[0];
                            String e_tm = strs[1];

                            timeData_tue3.setAvailId("");
                            timeData_tue3.setStartTime(s_tm + ":00");
                            timeData_tue3.setEndTime(e_tm + ":00");
                            timeDataList_tue.add(timeData_tue3);
                            Log.d("Tue--->", "onClick: ==3" + " 3");
                        }

                        availDay_tue.setDay("3");
                        availDay_tue.setTiming(timeDataList_tue);
                    } else {
                        timeData_tue1.setAvailId("");
                        timeData_tue1.setStartTime("00:00:00");
                        timeData_tue1.setEndTime("00:00:00");

                        timeDataList_tue.add(timeData_tue1);

                        availDay_tue.setDay("3");
                        availDay_tue.setTiming(timeDataList_tue);
                    }


                    //Wednesday
                    if (!list_wed.isEmpty()) {
                        if (list_wed.size() > 0) {
                            String[] strs = list_wed.get(0).split(" - ");
                            String s_tm = strs[0];
                            String e_tm = strs[1];

                            timeData_wed1.setAvailId("");
                            timeData_wed1.setStartTime(s_tm + ":00");
                            timeData_wed1.setEndTime(e_tm + ":00");
                            timeDataList_wed.add(timeData_wed1);
                            Log.d("Wed--->", "onClick: >0" + " 1");
                        }

                        if (list_wed.size() > 1) {
                            String[] strs = list_wed.get(1).split(" - ");
                            String s_tm = strs[0];
                            String e_tm = strs[1];

                            timeData_wed2.setAvailId("");
                            timeData_wed2.setStartTime(s_tm + ":00");
                            timeData_wed2.setEndTime(e_tm + ":00");
                            timeDataList_wed.add(timeData_wed2);
                            Log.d("Wed--->", "onClick: > 1" + " 2");
                        }

                        if (list_wed.size() > 2) {
                            String[] strs = list_wed.get(2).split(" - ");
                            String s_tm = strs[0];
                            String e_tm = strs[1];

                            timeData_wed3.setAvailId("");
                            timeData_wed3.setStartTime(s_tm + ":00");
                            timeData_wed3.setEndTime(e_tm + ":00");
                            timeDataList_wed.add(timeData_wed3);
                            Log.d("Wed--->", "onClick: ==3" + " 3");
                        }

                        availDay_wed.setDay("4");
                        availDay_wed.setTiming(timeDataList_wed);
                    } else {
                        timeData_wed1.setAvailId("");
                        timeData_wed1.setStartTime("00:00:00");
                        timeData_wed1.setEndTime("00:00:00");

                        timeDataList_wed.add(timeData_wed1);

                        availDay_wed.setDay("4");
                        availDay_wed.setTiming(timeDataList_wed);
                    }


                    //Thursday
                    if (!list_thu.isEmpty()) {
                        if (list_thu.size() > 0) {
                            String[] strs = list_thu.get(0).split(" - ");
                            String s_tm = strs[0];
                            String e_tm = strs[1];

                            timeData_thu1.setAvailId("");
                            timeData_thu1.setStartTime(s_tm + ":00");
                            timeData_thu1.setEndTime(e_tm + ":00");
                            timeDataList_thu.add(timeData_thu1);
                            Log.d("Thu--->", "onClick: >0" + " 1");
                        }

                        if (list_thu.size() > 1) {
                            String[] strs = list_thu.get(1).split(" - ");
                            String s_tm = strs[0];
                            String e_tm = strs[1];

                            timeData_thu2.setAvailId("");
                            timeData_thu2.setStartTime(s_tm + ":00");
                            timeData_thu2.setEndTime(e_tm + ":00");
                            timeDataList_thu.add(timeData_thu2);
                            Log.d("Thu--->", "onClick: > 1" + " 2");
                        }

                        if (list_thu.size() > 2) {
                            String[] strs = list_thu.get(2).split(" - ");
                            String s_tm = strs[0];
                            String e_tm = strs[1];

                            timeData_thu3.setAvailId("");
                            timeData_thu3.setStartTime(s_tm + ":00");
                            timeData_thu3.setEndTime(e_tm + ":00");
                            timeDataList_thu.add(timeData_thu3);
                            Log.d("Thu--->", "onClick: ==3" + " 3");
                        }

                        availDay_thu.setDay("5");
                        availDay_thu.setTiming(timeDataList_thu);
                    } else {
                        timeData_thu1.setAvailId("");
                        timeData_thu1.setStartTime("00:00:00");
                        timeData_thu1.setEndTime("00:00:00");

                        timeDataList_thu.add(timeData_thu1);

                        availDay_thu.setDay("5");
                        availDay_thu.setTiming(timeDataList_thu);
                    }


                    //Friday
                    if (!list_fri.isEmpty()) {
                        if (list_fri.size() > 0) {
                            String[] strs = list_fri.get(0).split(" - ");
                            String s_tm = strs[0];
                            String e_tm = strs[1];

                            timeData_fri1.setAvailId("");
                            timeData_fri1.setStartTime(s_tm + ":00");
                            timeData_fri1.setEndTime(e_tm + ":00");
                            timeDataList_fri.add(timeData_fri1);
                            Log.d("Fri--->", "onClick: >0" + " 1");
                        }

                        if (list_fri.size() > 1) {
                            String[] strs = list_fri.get(1).split(" - ");
                            String s_tm = strs[0];
                            String e_tm = strs[1];

                            timeData_fri2.setAvailId("");
                            timeData_fri2.setStartTime(s_tm + ":00");
                            timeData_fri2.setEndTime(e_tm + ":00");
                            timeDataList_fri.add(timeData_fri2);
                            Log.d("Fri--->", "onClick: > 1" + " 2");
                        }

                        if (list_fri.size() > 2) {
                            String[] strs = list_fri.get(2).split(" - ");
                            String s_tm = strs[0];
                            String e_tm = strs[1];

                            timeData_fri3.setAvailId("");
                            timeData_fri3.setStartTime(s_tm + ":00");
                            timeData_fri3.setEndTime(e_tm + ":00");
                            timeDataList_fri.add(timeData_fri3);
                            Log.d("Fri--->", "onClick: ==3" + " 3");
                        }


                        availDay_fri.setDay("6");
                        availDay_fri.setTiming(timeDataList_fri);
                    } else {
                        timeData_fri1.setAvailId("");
                        timeData_fri1.setStartTime("00:00:00");
                        timeData_fri1.setEndTime("00:00:00");

                        timeDataList_fri.add(timeData_fri1);

                        availDay_fri.setDay("6");
                        availDay_fri.setTiming(timeDataList_fri);
                    }


                    //Saturday
                    if (!list_sat.isEmpty()) {
                        if (list_sat.size() > 0) {
                            String[] strs = list_sat.get(0).split(" - ");
                            String s_tm = strs[0];
                            String e_tm = strs[1];

                            timeData_sat1.setAvailId("");
                            timeData_sat1.setStartTime(s_tm + ":00");
                            timeData_sat1.setEndTime(e_tm + ":00");
                            timeDataList_sat.add(timeData_sat1);
                            Log.d("Sat--->", "onClick: >0" + " 1");
                        }

                        if (list_sat.size() > 1) {
                            String[] strs = list_sat.get(1).split(" - ");
                            String s_tm = strs[0];
                            String e_tm = strs[1];

                            timeData_sat2.setAvailId("");
                            timeData_sat2.setStartTime(s_tm + ":00");
                            timeData_sat2.setEndTime(e_tm + ":00");
                            timeDataList_sat.add(timeData_sat2);
                            Log.d("Sat--->", "onClick: > 1" + " 2");
                        }

                        if (list_sat.size() > 2) {
                            String[] strs = list_sat.get(2).split(" - ");
                            String s_tm = strs[0];
                            String e_tm = strs[1];

                            timeData_sat3.setAvailId("");
                            timeData_sat3.setStartTime(s_tm + ":00");
                            timeData_sat3.setEndTime(e_tm + ":00");
                            timeDataList_sat.add(timeData_sat3);
                            Log.d("Sat--->", "onClick: ==3" + " 3");
                        }

                        availDay_sat.setDay("7");
                        availDay_sat.setTiming(timeDataList_sat);
                    } else {
                        timeData_sat1.setAvailId("");
                        timeData_sat1.setStartTime("00:00:00");
                        timeData_sat1.setEndTime("00:00:00");

                        timeDataList_sat.add(timeData_sat1);

                        availDay_sat.setDay("7");
                        availDay_sat.setTiming(timeDataList_sat);
                    }


                    //Final avail list
                    List<AvailDay> availDayList = new ArrayList<>();
                    availDayList.add(availDay_sun);
                    availDayList.add(availDay_mon);
                    availDayList.add(availDay_tue);
                    availDayList.add(availDay_wed);
                    availDayList.add(availDay_thu);
                    availDayList.add(availDay_fri);
                    availDayList.add(availDay_sat);

                    AppointmentService appointmentService = new AppointmentService();
                    appointmentService.setSerName(ser_name);
                    appointmentService.setSerDuration(ser_dur);
                    appointmentService.setSerPrice(amount);
                    appointmentService.setIsActive(true);
                    appointmentService.setMerId(sharedpreferences_sessionToken.getString(LoginActivity.MERID, ""));
                    appointmentService.setSameBussTime(isSameBizHrs);
                    if (showamount.equals("1")) {
                        appointmentService.setIsShowCust(true);
                    } else {
                        appointmentService.setIsShowCust(false);
                    }
                    appointmentService.setSerDescription(ser_desc);
                    appointmentService.availDays(availDayList);

                    ApptTransactionBody transactionBody = new ApptTransactionBody();
                    transactionBody.setReqType("T-S.C");
                    transactionBody.setMerId(sharedpreferences_sessionToken.getString(LoginActivity.MERID, ""));
                    transactionBody.setDeviceId("c43cbfe00b37ae6133ca023484869d2c489a8974ba48fb3286aa058292d08f0e");
                    transactionBody.setSessionToken(sharedpreferences_sessionToken.getString(LoginActivity.SESSIONTOKEN, ""));
                    transactionBody.setService(appointmentService);
                    try {
                        Log.d("Token--->", "token: " + sharedpreferences_sessionToken.getString(LoginActivity.SESSIONTOKEN, ""));
                        boolean isConn = ConnectivityReceiver.isConnected();
                        if (isConn) {
                            apptCreateService(transactionBody);
                        } else {
                            Toast.makeText(AddServiceAvailTimeActivity.this, "No internet connection!", Toast.LENGTH_SHORT).show();
                        }
                    } catch (ApiException e) {
                        e.printStackTrace();
                    }

                   /* String nameList = sharedpreferences_services.getString(SERVICE_NAME, "");
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
                        overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);
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
                        overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);
                    }
*/

                } else {
                    getDialog("Pick any one option");
                }
            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent in = new Intent(AddServiceAvailTimeActivity.this, AddServiceActivity.class);
        in.putExtra("page_id", "003");
        startActivity(in);
        finish();
        overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);

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

    public void getWeekDaysLayout(String weekday) {

        if (String.valueOf(weekday.charAt(0)).equals("n")) {
            linearLayout__sun.setVisibility(View.GONE);
            list_sun.clear();
        } else {
            linearLayout__sun.setVisibility(View.VISIBLE);
        }
        if (String.valueOf(weekday.charAt(1)).equals("n")) {
            linearLayout__mon.setVisibility(View.GONE);
            list_mon.clear();
        } else {
            linearLayout__mon.setVisibility(View.VISIBLE);
        }

        if (String.valueOf(weekday.charAt(2)).equals("n")) {
            linearLayout__tue.setVisibility(View.GONE);
            list_tue.clear();
        } else {
            linearLayout__tue.setVisibility(View.VISIBLE);
        }

        if (String.valueOf(weekday.charAt(3)).equals("n")) {
            linearLayout__wed.setVisibility(View.GONE);
            list_wed.clear();
        } else {
            linearLayout__wed.setVisibility(View.VISIBLE);
        }
        if (String.valueOf(weekday.charAt(4)).equals("n")) {
            linearLayout__thur.setVisibility(View.GONE);
            list_thu.clear();
        } else {
            linearLayout__thur.setVisibility(View.VISIBLE);
        }
        if (String.valueOf(weekday.charAt(5)).equals("n")) {
            linearLayout__fri.setVisibility(View.GONE);
            list_fri.clear();
        } else {
            linearLayout__fri.setVisibility(View.VISIBLE);
        }

        if (String.valueOf(weekday.charAt(6)).equals("n")) {
            linearLayout__sat.setVisibility(View.GONE);
            list_sat.clear();
        } else {
            linearLayout__sat.setVisibility(View.VISIBLE);
        }

    }

    public void getCopyTimeAllDays(String weekday, String time1, String time2, String time3) {
        Log.d("getCopyTimeAllDays", "getCopyTimeAllDays: " + weekday + "," + time1 + "," + time2 + "," + time3);
        if (String.valueOf(weekday.charAt(0)).equals("y")) {
            list_sun.add(time1);
            if (!time2.equals("00:00 - 00:00")) {
                list_sun.add(time2);
            }
            if (!time3.equals("00:00 - 00:00")) {
                list_sun.add(time3);
            }
        }

        if (String.valueOf(weekday.charAt(1)).equals("y")) {
            list_mon.add(time1);
            if (!time2.equals("00:00 - 00:00")) {
                list_mon.add(time2);
            }
            if (!time3.equals("00:00 - 00:00")) {
                list_mon.add(time3);
            }
        }

        if (String.valueOf(weekday.charAt(2)).equals("y")) {
            list_tue.add(time1);
            if (!time2.equals("00:00 - 00:00")) {
                list_tue.add(time2);
            }
            if (!time3.equals("00:00 - 00:00")) {
                list_tue.add(time3);
            }
        }

        if (String.valueOf(weekday.charAt(3)).equals("y")) {
            list_wed.add(time1);
            if (!time2.equals("00:00 - 00:00")) {
                list_wed.add(time2);
            }
            if (!time3.equals("00:00 - 00:00")) {
                list_wed.add(time3);
            }
        }
        if (String.valueOf(weekday.charAt(4)).equals("y")) {
            list_thu.add(time1);
            if (!time2.equals("00:00 - 00:00")) {
                list_thu.add(time2);
            }
            if (!time3.equals("00:00 - 00:00")) {
                list_thu.add(time3);
            }
        }
        if (String.valueOf(weekday.charAt(5)).equals("y")) {
            list_fri.add(time1);
            if (!time2.equals("00:00 - 00:00")) {
                list_fri.add(time2);
            }
            if (!time3.equals("00:00 - 00:00")) {
                list_fri.add(time3);
            }
        }

        if (String.valueOf(weekday.charAt(6)).equals("y")) {
            list_sat.add(time1);
            if (!time2.equals("00:00 - 00:00")) {
                list_sat.add(time2);
            }
            if (!time3.equals("00:00 - 00:00")) {
                list_sat.add(time3);
            }
        }

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

    private void apptCreateService(ApptTransactionBody requestBody) throws ApiException {

        intermediateAlertDialog = new IntermediateAlertDialog(AddServiceAvailTimeActivity.this);
        Log.d("service---", "service: " + requestBody);
        OkHttpApiClient okHttpApiClient = new OkHttpApiClient(AddServiceAvailTimeActivity.this);
        MerchantApisApi webMerchantApisApi = new MerchantApisApi();
        webMerchantApisApi.setApiClient(okHttpApiClient.getApiClient());

        webMerchantApisApi.merchantAppointmentTransactionAsync(requestBody, new ApiCallback<ApptTransactionResponse>() {
            @Override
            public void onFailure(final ApiException e, int statusCode, Map<String, List<String>> responseHeaders) {
                Log.d("createService--->", "onFailure-" + e.getMessage());
                if (intermediateAlertDialog != null) {
                    intermediateAlertDialog.dismissAlertDialog();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new AlertDialogFailure(AddServiceAvailTimeActivity.this, getResources().getString(R.string.try_again), "OK", getResources().getString(R.string.went_wrong), "Failed") {
                            @Override
                            public void onButtonClick() {
                                startActivity(new Intent(AddServiceAvailTimeActivity.this, SetupServiceActivity_Bottom.class));
                                finish();
                                overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);
                            }
                        };
                    }
                });
            }

            @Override
            public void onSuccess(ApptTransactionResponse result, int statusCode, Map<String, List<String>> responseHeaders) {
                Log.d("createService--->", "onSuccess-" + statusCode + "," + result.getStatusMessage());
                if (intermediateAlertDialog != null) {
                    intermediateAlertDialog.dismissAlertDialog();
                }
                if (Integer.parseInt(result.getStatusCode()) == 200) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new AlertDialogFailure(AddServiceAvailTimeActivity.this, "Service successfully created!", "OK", "", getResources().getString(R.string.success)) {
                                @Override
                                public void onButtonClick() {
                                    startActivity(new Intent(AddServiceAvailTimeActivity.this, SetupServiceActivity_Bottom.class));
                                    finish();
                                    overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);
                                }
                            };
                        }
                    });

                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new AlertDialogFailure(AddServiceAvailTimeActivity.this, "Service setup failed. Please try again later!", "OK", "", "Failed") {
                                @Override
                                public void onButtonClick() {
                                    startActivity(new Intent(AddServiceAvailTimeActivity.this, SetupServiceActivity_Bottom.class));
                                    finish();
                                    overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);
                                }
                            };
                        }
                    });
                }
            }

            @Override
            public void onUploadProgress(long bytesWritten, long contentLength, boolean done) {

            }

            @Override
            public void onDownloadProgress(long bytesRead, long contentLength, boolean done) {

            }
        });


    }

    private void getScrollTop() {
        scrollView.postDelayed(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(ScrollView.FOCUS_UP);
            }
        }, 500);
    }

    private void getAddTimeScrollBottom(final View view) {
        scrollView.postDelayed(new Runnable() {
            @Override
            public void run() {
                scrollView.smoothScrollTo(0, view.getBottom());
            }
        }, 500);
    }

    private void runAnimation(final View view) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Animation a = AnimationUtils.loadAnimation(AddServiceAvailTimeActivity.this, R.anim.blinking_animation1);
                a.reset();
                view.clearAnimation();
                view.startAnimation(a);
            }
        }, 800);

    }

    private void getAllDaysTime(String time1, String time2, String time3) {
        textView_sun_time1.setText(time1);
        textView_sun_time2.setText(time2);
        textView_sun_time3.setText(time3);
        textView_mon_time1.setText(time1);
        textView_mon_time2.setText(time2);
        textView_mon_time3.setText(time3);
        textView_tue_time1.setText(time1);
        textView_tue_time2.setText(time2);
        textView_tue_time3.setText(time3);
        textView_wed_time1.setText(time1);
        textView_wed_time2.setText(time2);
        textView_wed_time3.setText(time3);
        textView_thu_time1.setText(time1);
        textView_thu_time2.setText(time2);
        textView_thu_time3.setText(time3);
        textView_fri_time1.setText(time1);
        textView_fri_time2.setText(time2);
        textView_fri_time3.setText(time3);
        textView_sat_time1.setText(time1);
        textView_sat_time2.setText(time2);
        textView_sat_time3.setText(time3);
    }

    private void getClearList() {
        list_sun.clear();
        list_mon.clear();
        list_tue.clear();
        list_wed.clear();
        list_thu.clear();
        list_fri.clear();
        list_sat.clear();
    }
}
