package com.corals.appointment.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.corals.appointment.Adapters.MapServiceResourceRecyclerAdapter;
import com.corals.appointment.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Calendar;

public class AddStaffActivity extends AppCompatActivity {
    EditText et_staff_name, et_staff_mob;
    Button button_continue;
    private SharedPreferences sharedpreferences_staffs;
    public static final String MyPREFERENCES_STAFFS = "MyPrefs_Staffs";
    public static final String NAME = "name";
    public static final String MOBILE = "mobile";
    private ArrayList<String> staff_name_list, staff_mob_list;
    public String pageId = "", position = "";

    TextView text_sunday_op_time, text_sunday_cl_time, text_monday_op_time, text_monday_cl_time, text_tuesday_op_time, text_tuesday_cl_time,
            text_wednesday_op_time, text_wednesday_cl_time, text_thursday_op_time, text_thursday_cl_time, text_friday_op_time, text_friday_cl_time, text_saturday_op_time, text_saturday_cl_time;
    private int mYear, mMonth, mDay, mHour, mMinute, mSeconds;
    private String sunday_op_time, sunday_cl_time, monday_op_time, monday_cl_time, tuesday_op_time, tuesday_cl_time,
            wednesday_op_time, wednesday_cl_time, thursday_op_time, thursday_cl_time, friday_op_time, friday_cl_time, saturday_op_time, saturday_cl_time;

    RecyclerView recyclerView_services;
    private SharedPreferences sharedpreferences_services;
    private ArrayList<String> service_name_list, service_dur_list;
    public static ArrayList<String> arrayList_map_service;
    public static String map_services = "";

    Button btn_yes_sday_p, btn_yes_mnday_p, btn_yes_tsday_p, btn_yes_wedday_p, btn_yes_trsday_p, btn_yes_fdday_p, btn_yes_strday_p;
    boolean isActiveSunday_p = true, isActiveMonday_p = true, isActiveTuesday_p = true, isActiveWednesday_p = true, isActiveThursday_p = true, isActiveFriday_p = true, isActiveSaturday_p = true;
    TextView text_staff_in_time, text_staff_out_time;
    private String staff_in_time, staff_out_time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_staff);

        map_services = "";
        Toolbar toolbar = findViewById(R.id.toolbar_add_staff);
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

        sharedpreferences_staffs = getSharedPreferences(MyPREFERENCES_STAFFS, Context.MODE_PRIVATE);
        et_staff_name = findViewById(R.id.et_staff_name);
        et_staff_mob = findViewById(R.id.et_staff_mob);
        button_continue = findViewById(R.id.button_add_staff_continue);

        text_staff_in_time = findViewById(R.id.text_staff_in_time);
        text_staff_out_time = findViewById(R.id.text_staff_out_time);

        btn_yes_sday_p = (Button) findViewById(R.id.btn_yes_sunday_P);
        btn_yes_mnday_p = (Button) findViewById(R.id.btn_yes_monday_P);
        btn_yes_tsday_p = (Button) findViewById(R.id.btn_yes_tuesday_P);
        btn_yes_wedday_p = (Button) findViewById(R.id.btn_yes_wedsnesday_P);
        btn_yes_trsday_p = (Button) findViewById(R.id.btn_yes_thursday_P);
        btn_yes_fdday_p = (Button) findViewById(R.id.btn_yes_friday_P);
        btn_yes_strday_p = (Button) findViewById(R.id.btn_yes_saturday_P);

        text_sunday_op_time = findViewById(R.id.text_sunday_op_time);
        text_sunday_cl_time = findViewById(R.id.text_sunday_cl_time);

        text_monday_op_time = findViewById(R.id.text_monday_op_time);
        text_monday_cl_time = findViewById(R.id.text_monday_cl_time);

        text_tuesday_op_time = findViewById(R.id.text_tuesday_op_time);
        text_tuesday_cl_time = findViewById(R.id.text_tuesday_cl_time);

        text_wednesday_op_time = findViewById(R.id.text_wednesday_op_time);
        text_wednesday_cl_time = findViewById(R.id.text_wednesday_cl_time);

        text_thursday_op_time = findViewById(R.id.text_thursday_op_time);
        text_thursday_cl_time = findViewById(R.id.text_thursday_cl_time);

        text_friday_op_time = findViewById(R.id.text_friday_op_time);
        text_friday_cl_time = findViewById(R.id.text_friday_cl_time);

        text_saturday_op_time = findViewById(R.id.text_saturday_op_time);
        text_saturday_cl_time = findViewById(R.id.text_saturday_cl_time);

        recyclerView_services = findViewById(R.id.recyclerview_select_services);
        LinearLayoutManager lm = new LinearLayoutManager(this);
        recyclerView_services.setLayoutManager(lm);

        if (getIntent().getExtras() != null) {
            pageId = getIntent().getStringExtra("page_id");
            position = getIntent().getStringExtra("position");
            String name = getIntent().getStringExtra("name");
            String mobile = getIntent().getStringExtra("mobile");

            et_staff_name.setText(name);
            et_staff_mob.setText(mobile);
        }

        staff_name_list = new ArrayList<>();
        staff_mob_list = new ArrayList<>();
        service_name_list = new ArrayList<>();
        service_dur_list = new ArrayList<>();
        arrayList_map_service = new ArrayList<>();

        sharedpreferences_services = getSharedPreferences(AddServiceActivity.MyPREFERENCES_SERVICES, Context.MODE_PRIVATE);
        String nameList = sharedpreferences_services.getString(AddServiceActivity.SERVICE_NAME, "");
        String mobList = sharedpreferences_services.getString(AddServiceActivity.SERVICE_DURATION, "");
        if (!TextUtils.isEmpty(nameList) && !TextUtils.isEmpty(mobList)) {
            service_name_list = new Gson().fromJson(nameList, new TypeToken<ArrayList<String>>() {
            }.getType());
            service_dur_list = new Gson().fromJson(mobList, new TypeToken<ArrayList<String>>() {
            }.getType());
        }

        if (service_name_list.size() != 0) {
            MapServiceResourceRecyclerAdapter mapServiceResourceRecyclerAdapter = new MapServiceResourceRecyclerAdapter(AddStaffActivity.this, service_name_list);
            recyclerView_services.setAdapter(mapServiceResourceRecyclerAdapter);
        }

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
                String name = et_staff_name.getText().toString();
                String mob = et_staff_mob.getText().toString();
                if (name.length() > 0) {
                    if (mob.length() > 0) {
                        if (arrayList_map_service.size() != 0) {
                            String nameList = sharedpreferences_staffs.getString(AddStaffActivity.NAME, "");
                            String mobList = sharedpreferences_staffs.getString(AddStaffActivity.MOBILE, "");
                            if (!TextUtils.isEmpty(nameList) && !TextUtils.isEmpty(mobList)) {
                                staff_name_list = new Gson().fromJson(nameList, new TypeToken<ArrayList<String>>() {
                                }.getType());
                                staff_mob_list = new Gson().fromJson(mobList, new TypeToken<ArrayList<String>>() {
                                }.getType());
                            }

                            if (pageId.equals("3")) {
                                staff_name_list.add(name);
                                staff_mob_list.add(mob);

                                String nameList1 = new Gson().toJson(staff_name_list);
                                String mobList1 = new Gson().toJson(staff_mob_list);
                                SharedPreferences.Editor editor = sharedpreferences_staffs.edit();
                                editor.putString(NAME, nameList1);
                                editor.putString(MOBILE, mobList1);
                                editor.commit();

                                Intent in = new Intent(AddStaffActivity.this, StaffActivity_Bottom.class);
                                startActivity(in);
                                finish();
                            } else  if (pageId.equals("03")) {
                                staff_name_list.set(Integer.parseInt(position),name);
                                staff_mob_list.set(Integer.parseInt(position),mob);

                                String nameList1 = new Gson().toJson(staff_name_list);
                                String mobList1 = new Gson().toJson(staff_mob_list);
                                SharedPreferences.Editor editor = sharedpreferences_staffs.edit();
                                editor.putString(NAME, nameList1);
                                editor.putString(MOBILE, mobList1);
                                editor.commit();

                                Intent in = new Intent(AddStaffActivity.this, StaffActivity_Bottom.class);
                                //in.putExtra("page_id", "3");
                                startActivity(in);
                                finish();
                            }
                        } else {
                            Toast.makeText(AddStaffActivity.this, "Must select atleast one service", Toast.LENGTH_LONG).show();
                        }

                    } else {
                        et_staff_mob.setError("Enter valid mobile");
                        et_staff_mob.requestFocus();
                    }
                } else {
                    et_staff_name.setError("Enter valid name");
                    et_staff_name.requestFocus();
                }
            }
        });

        text_staff_in_time.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + "08:00" + "</u>  </font>"));
        text_staff_out_time.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + "20:00" + "</u>  </font>"));

        text_sunday_op_time.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + "08:00" + "</u>  </font>"));
        text_sunday_cl_time.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + "20:00" + "</u>  </font>"));
        text_monday_op_time.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + "08:00" + "</u>  </font>"));
        text_monday_cl_time.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + "20:00" + "</u>  </font>"));
        text_tuesday_op_time.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + "08:00" + "</u>  </font>"));

        text_tuesday_cl_time.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + "20:00" + "</u>  </font>"));
        text_wednesday_op_time.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + "08:00" + "</u>  </font>"));
        text_wednesday_cl_time.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + "20:00" + "</u>  </font>"));
        text_thursday_op_time.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + "08:00" + "</u>  </font>"));
        text_thursday_cl_time.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + "20:00" + "</u>  </font>"));

        text_friday_op_time.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + "08:00" + "</u>  </font>"));
        text_friday_cl_time.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + "20:00" + "</u>  </font>"));
        text_saturday_op_time.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + "08:00" + "</u>  </font>"));
        text_saturday_cl_time.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + "20:00" + "</u>  </font>"));

        text_staff_in_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_staff_in_time();
            }
        });

        text_staff_out_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_staff_out_time();
            }
        });


        text_sunday_op_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_sunday_op_time();
            }
        });

        text_sunday_cl_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_sunday_cl_time();
            }
        });

        text_monday_op_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_monday_op_time();
            }
        });

        text_monday_cl_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_monday_cl_time();
            }
        });

        text_tuesday_op_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_tuesday_op_time();
            }
        });

        text_tuesday_cl_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_tuesday_cl_time();
            }
        });

        text_wednesday_op_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_wednesday_op_time();
            }
        });

        text_wednesday_cl_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_wednesday_cl_time();
            }
        });

        text_thursday_op_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_thursday_op_time();
            }
        });

        text_thursday_cl_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_thursday_cl_time();
            }
        });

        text_friday_op_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_friday_op_time();
            }
        });

        text_friday_cl_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_friday_cl_time();
            }
        });

        text_saturday_op_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_saturday_op_time();
            }
        });

        text_saturday_cl_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_saturday_cl_time();
            }
        });


    }

    public void get_staff_in_time() {
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
                            staff_in_time = hr + ":" + min;
                        } else if (time_hr.length() == 1 && time_min.length() != 1) {
                            hr = "0" + "" + time_hr;
                            staff_in_time = hr + ":" + minute;
                        } else if (time_hr.length() != 1 && time_min.length() == 1) {
                            min = "0" + "" + time_min;
                            staff_in_time = hourOfDay + ":" + min;
                        } else {
                            staff_in_time = hourOfDay + ":" + minute;
                        }
                        Log.d("Time===>", "" + staff_in_time);
                        //editText_start_time.setText(time_active);
                        text_staff_in_time.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + staff_in_time + "</u>  </font>"));

                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();

    }

    public void get_staff_out_time() {
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
                            staff_out_time = hr + ":" + min;
                        } else if (time_hr.length() == 1 && time_min.length() != 1) {
                            hr = "0" + "" + time_hr;
                            staff_out_time = hr + ":" + minute;
                        } else if (time_hr.length() != 1 && time_min.length() == 1) {
                            min = "0" + "" + time_min;
                            staff_out_time = hourOfDay + ":" + min;
                        } else {
                            staff_out_time = hourOfDay + ":" + minute;
                        }
                        Log.d("Time===>", "" + staff_out_time);
                        //editText_start_time.setText(time_active);
                        text_staff_out_time.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + staff_out_time + "</u>  </font>"));

                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();

    }


    //Sunday
    public void get_sunday_op_time() {
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
                            sunday_op_time = hr + ":" + min;
                        } else if (time_hr.length() == 1 && time_min.length() != 1) {
                            hr = "0" + "" + time_hr;
                            sunday_op_time = hr + ":" + minute;
                        } else if (time_hr.length() != 1 && time_min.length() == 1) {
                            min = "0" + "" + time_min;
                            sunday_op_time = hourOfDay + ":" + min;
                        } else {
                            sunday_op_time = hourOfDay + ":" + minute;
                        }
                        Log.d("Time===>", "" + sunday_op_time);
                        //editText_start_time.setText(time_active);
                        text_sunday_op_time.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + sunday_op_time + "</u>  </font>"));

                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();

    }

    public void get_sunday_cl_time() {
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
                            sunday_cl_time = hr + ":" + min;
                        } else if (time_hr.length() == 1 && time_min.length() != 1) {
                            hr = "0" + "" + time_hr;
                            sunday_cl_time = hr + ":" + minute;
                        } else if (time_hr.length() != 1 && time_min.length() == 1) {
                            min = "0" + "" + time_min;
                            sunday_cl_time = hourOfDay + ":" + min;
                        } else {
                            sunday_cl_time = hourOfDay + ":" + minute;
                        }
                        Log.d("Time===>", "" + sunday_cl_time);
                        //editText_start_time.setText(time_active);
                        text_sunday_cl_time.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + sunday_cl_time + "</u>  </font>"));

                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();

    }


    //Monday
    public void get_monday_op_time() {
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
                            monday_op_time = hr + ":" + min;
                        } else if (time_hr.length() == 1 && time_min.length() != 1) {
                            hr = "0" + "" + time_hr;
                            monday_op_time = hr + ":" + minute;
                        } else if (time_hr.length() != 1 && time_min.length() == 1) {
                            min = "0" + "" + time_min;
                            monday_op_time = hourOfDay + ":" + min;
                        } else {
                            monday_op_time = hourOfDay + ":" + minute;
                        }
                        Log.d("Time===>", "" + monday_op_time);
                        //editText_start_time.setText(time_active);
                        text_monday_op_time.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + monday_op_time + "</u>  </font>"));

                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();

    }

    public void get_monday_cl_time() {
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
                            monday_cl_time = hr + ":" + min;
                        } else if (time_hr.length() == 1 && time_min.length() != 1) {
                            hr = "0" + "" + time_hr;
                            monday_cl_time = hr + ":" + minute;
                        } else if (time_hr.length() != 1 && time_min.length() == 1) {
                            min = "0" + "" + time_min;
                            monday_cl_time = hourOfDay + ":" + min;
                        } else {
                            monday_cl_time = hourOfDay + ":" + minute;
                        }
                        Log.d("Time===>", "" + monday_cl_time);
                        //editText_start_time.setText(time_active);
                        text_monday_cl_time.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + monday_cl_time + "</u>  </font>"));

                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();

    }

    //Tuesday
    public void get_tuesday_op_time() {
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
                            tuesday_op_time = hr + ":" + min;
                        } else if (time_hr.length() == 1 && time_min.length() != 1) {
                            hr = "0" + "" + time_hr;
                            tuesday_op_time = hr + ":" + minute;
                        } else if (time_hr.length() != 1 && time_min.length() == 1) {
                            min = "0" + "" + time_min;
                            tuesday_op_time = hourOfDay + ":" + min;
                        } else {
                            tuesday_op_time = hourOfDay + ":" + minute;
                        }
                        Log.d("Time===>", "" + tuesday_op_time);
                        //editText_start_time.setText(time_active);
                        text_tuesday_op_time.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + tuesday_op_time + "</u>  </font>"));

                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();

    }

    public void get_tuesday_cl_time() {
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
                            tuesday_cl_time = hr + ":" + min;
                        } else if (time_hr.length() == 1 && time_min.length() != 1) {
                            hr = "0" + "" + time_hr;
                            tuesday_cl_time = hr + ":" + minute;
                        } else if (time_hr.length() != 1 && time_min.length() == 1) {
                            min = "0" + "" + time_min;
                            tuesday_cl_time = hourOfDay + ":" + min;
                        } else {
                            tuesday_cl_time = hourOfDay + ":" + minute;
                        }
                        Log.d("Time===>", "" + tuesday_cl_time);
                        //editText_start_time.setText(time_active);
                        text_tuesday_cl_time.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + tuesday_cl_time + "</u>  </font>"));

                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();

    }

    //Wednesday
    public void get_wednesday_op_time() {
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
                            wednesday_op_time = hr + ":" + min;
                        } else if (time_hr.length() == 1 && time_min.length() != 1) {
                            hr = "0" + "" + time_hr;
                            wednesday_op_time = hr + ":" + minute;
                        } else if (time_hr.length() != 1 && time_min.length() == 1) {
                            min = "0" + "" + time_min;
                            wednesday_op_time = hourOfDay + ":" + min;
                        } else {
                            wednesday_op_time = hourOfDay + ":" + minute;
                        }
                        Log.d("Time===>", "" + wednesday_op_time);
                        //editText_start_time.setText(time_active);
                        text_wednesday_op_time.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + wednesday_op_time + "</u>  </font>"));

                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();

    }

    public void get_wednesday_cl_time() {
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
                            wednesday_cl_time = hr + ":" + min;
                        } else if (time_hr.length() == 1 && time_min.length() != 1) {
                            hr = "0" + "" + time_hr;
                            wednesday_cl_time = hr + ":" + minute;
                        } else if (time_hr.length() != 1 && time_min.length() == 1) {
                            min = "0" + "" + time_min;
                            wednesday_cl_time = hourOfDay + ":" + min;
                        } else {
                            wednesday_cl_time = hourOfDay + ":" + minute;
                        }
                        Log.d("Time===>", "" + wednesday_cl_time);
                        //editText_start_time.setText(time_active);
                        text_wednesday_cl_time.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + wednesday_cl_time + "</u>  </font>"));

                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();

    }

    //Thursday
    public void get_thursday_op_time() {
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
                            thursday_op_time = hr + ":" + min;
                        } else if (time_hr.length() == 1 && time_min.length() != 1) {
                            hr = "0" + "" + time_hr;
                            thursday_op_time = hr + ":" + minute;
                        } else if (time_hr.length() != 1 && time_min.length() == 1) {
                            min = "0" + "" + time_min;
                            thursday_op_time = hourOfDay + ":" + min;
                        } else {
                            thursday_op_time = hourOfDay + ":" + minute;
                        }
                        Log.d("Time===>", "" + thursday_op_time);
                        //editText_start_time.setText(time_active);
                        text_thursday_op_time.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + thursday_op_time + "</u>  </font>"));

                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();

    }

    public void get_thursday_cl_time() {
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
                            thursday_cl_time = hr + ":" + min;
                        } else if (time_hr.length() == 1 && time_min.length() != 1) {
                            hr = "0" + "" + time_hr;
                            thursday_cl_time = hr + ":" + minute;
                        } else if (time_hr.length() != 1 && time_min.length() == 1) {
                            min = "0" + "" + time_min;
                            thursday_cl_time = hourOfDay + ":" + min;
                        } else {
                            thursday_cl_time = hourOfDay + ":" + minute;
                        }
                        Log.d("Time===>", "" + thursday_cl_time);
                        //editText_start_time.setText(time_active);
                        text_thursday_cl_time.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + thursday_cl_time + "</u>  </font>"));

                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();

    }

    //Friday
    public void get_friday_op_time() {
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
                            friday_op_time = hr + ":" + min;
                        } else if (time_hr.length() == 1 && time_min.length() != 1) {
                            hr = "0" + "" + time_hr;
                            friday_op_time = hr + ":" + minute;
                        } else if (time_hr.length() != 1 && time_min.length() == 1) {
                            min = "0" + "" + time_min;
                            friday_op_time = hourOfDay + ":" + min;
                        } else {
                            friday_op_time = hourOfDay + ":" + minute;
                        }
                        Log.d("Time===>", "" + friday_op_time);
                        //editText_start_time.setText(time_active);
                        text_friday_op_time.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + friday_op_time + "</u>  </font>"));

                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();

    }

    public void get_friday_cl_time() {
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
                            friday_cl_time = hr + ":" + min;
                        } else if (time_hr.length() == 1 && time_min.length() != 1) {
                            hr = "0" + "" + time_hr;
                            friday_cl_time = hr + ":" + minute;
                        } else if (time_hr.length() != 1 && time_min.length() == 1) {
                            min = "0" + "" + time_min;
                            friday_cl_time = hourOfDay + ":" + min;
                        } else {
                            friday_cl_time = hourOfDay + ":" + minute;
                        }
                        Log.d("Time===>", "" + friday_cl_time);
                        //editText_start_time.setText(time_active);
                        text_friday_cl_time.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + friday_cl_time + "</u>  </font>"));

                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();

    }

    //Saturday
    public void get_saturday_op_time() {
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
                            saturday_op_time = hr + ":" + min;
                        } else if (time_hr.length() == 1 && time_min.length() != 1) {
                            hr = "0" + "" + time_hr;
                            saturday_op_time = hr + ":" + minute;
                        } else if (time_hr.length() != 1 && time_min.length() == 1) {
                            min = "0" + "" + time_min;
                            saturday_op_time = hourOfDay + ":" + min;
                        } else {
                            saturday_op_time = hourOfDay + ":" + minute;
                        }
                        Log.d("Time===>", "" + saturday_op_time);
                        //editText_start_time.setText(time_active);
                        text_saturday_op_time.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + saturday_op_time + "</u>  </font>"));

                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();

    }

    public void get_saturday_cl_time() {
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
                            saturday_cl_time = hr + ":" + min;
                        } else if (time_hr.length() == 1 && time_min.length() != 1) {
                            hr = "0" + "" + time_hr;
                            saturday_cl_time = hr + ":" + minute;
                        } else if (time_hr.length() != 1 && time_min.length() == 1) {
                            min = "0" + "" + time_min;
                            saturday_cl_time = hourOfDay + ":" + min;
                        } else {
                            saturday_cl_time = hourOfDay + ":" + minute;
                        }
                        Log.d("Time===>", "" + saturday_cl_time);
                        //editText_start_time.setText(time_active);
                        text_saturday_cl_time.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + saturday_cl_time + "</u>  </font>"));

                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

            Intent in = new Intent(AddStaffActivity.this, StaffActivity_Bottom.class);
            startActivity(in);
            finish();

    }
}
