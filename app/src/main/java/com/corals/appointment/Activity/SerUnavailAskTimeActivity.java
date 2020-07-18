package com.corals.appointment.Activity;

import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.corals.appointment.Adapters.ApptServiceSlotsAdapter;
import com.corals.appointment.Adapters.SerUnavailSlotRecycleAdapter;
import com.corals.appointment.Client.ApiCallback;
import com.corals.appointment.Client.ApiException;
import com.corals.appointment.Client.OkHttpApiClient;
import com.corals.appointment.Client.api.MerchantApisApi;
import com.corals.appointment.Client.model.AppointmentAvailableSlots;
import com.corals.appointment.Client.model.AppointmentEnquiryBody;
import com.corals.appointment.Client.model.AppointmentEnquiryResponse;
import com.corals.appointment.Client.model.Appointments;
import com.corals.appointment.Client.model.ApptTransactionBody;
import com.corals.appointment.Client.model.ApptTransactionResponse;
import com.corals.appointment.Client.model.MapServiceResourceBody;
import com.corals.appointment.Client.model.ServiceResourceUnavailBody;
import com.corals.appointment.Client.model.UnavailSlotBody;
import com.corals.appointment.Constants.Constants;
import com.corals.appointment.Dialogs.AlertDialogFailure;
import com.corals.appointment.Dialogs.IntermediateAlertDialog;
import com.corals.appointment.Interface.UnavailCallback;
import com.corals.appointment.R;
import com.corals.appointment.receiver.ConnectivityReceiver;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class SerUnavailAskTimeActivity extends AppCompatActivity implements UnavailCallback {

    TextView text_start_time, text_end_time, textView_appn_dt, textView_res, textView_time_title, text_st_am_pm, text_et_am_pm;
    LinearLayout linearLayout, linearLayout_show_time, layout_show_time1, layout_show_time2, layout_slots;
    Button button_submit, button_add_time;
    String start_time = "00:00", end_time = "00:00", task;
    TextView tv_time_yes, tv_time_no, textView_time_validation, textView_ser_unavail_time1, textView_ser_unavail_time2, text_unavail_time_dt, textView_response, textView_fullday_hint;
    ImageView imageView;
    boolean isValidTime = false;
    ArrayList<String> arrayList_start_time1, arrayList_start_time2, arrayList_end_time1, arrayList_end_time2;
    int hour = 0, minute = 0;
    RecyclerView recyclerView_slots;
    private IntermediateAlertDialog intermediateAlertDialog;
    String ser_id, ser, dt;
    private SharedPreferences sharedpreferences_sessionToken;
    boolean isFullDay = true, isValidUnavailTime = false;
    public ArrayList<String> arrayList_startTime, arrayList_endTime, arrayList_slotNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ser_unavail_ask_time);

        Toolbar toolbar = findViewById(R.id.toolbar_ser_unavail_ask_time);
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

        textView_fullday_hint = findViewById(R.id.text_full_day_hint);
        textView_response = findViewById(R.id.text_res);
        text_st_am_pm = findViewById(R.id.text_start_time_am_pm);
        text_et_am_pm = findViewById(R.id.text_end_time_am_pm);
        layout_slots = findViewById(R.id.layout_slots);
        layout_show_time1 = findViewById(R.id.layout_show_time1);
        layout_show_time2 = findViewById(R.id.layout_show_time2);
        linearLayout_show_time = findViewById(R.id.layout_show_unavail_time);
        text_unavail_time_dt = findViewById(R.id.text_unavail_time_dt);
        textView_ser_unavail_time1 = findViewById(R.id.text_ser_unavail_time1);
        textView_ser_unavail_time2 = findViewById(R.id.text_ser_unavail_time2);
        textView_time_validation = findViewById(R.id.text_validation_time);
        textView_time_title = findViewById(R.id.text_time_title);
        imageView = findViewById(R.id.image_cal);
        linearLayout = findViewById(R.id.layout_ask_time);
        textView_res = findViewById(R.id.text_resource);
        textView_appn_dt = findViewById(R.id.text_appn_dt);
        tv_time_yes = findViewById(R.id.text_time_yes);
        tv_time_no = findViewById(R.id.text_time_no);
        text_start_time = findViewById(R.id.text_start_time);
        text_end_time = findViewById(R.id.text_end_time);
        button_add_time = findViewById(R.id.button_add_time);
        button_submit = findViewById(R.id.button_ser_unavail_submit);
        recyclerView_slots = findViewById(R.id.recyclerview_appt_slots);

        GridLayoutManager lm = new GridLayoutManager(this, 2);
        recyclerView_slots.setLayoutManager(lm);
        recyclerView_slots.setHasFixedSize(true);


        arrayList_startTime = new ArrayList<>();
        arrayList_endTime = new ArrayList<>();
        arrayList_slotNo = new ArrayList<>();

        arrayList_start_time1 = new ArrayList<>();
        arrayList_start_time2 = new ArrayList<>();
        arrayList_end_time1 = new ArrayList<>();
        arrayList_end_time2 = new ArrayList<>();
        sharedpreferences_sessionToken = getSharedPreferences(LoginActivity.MyPREFERENCES_SESSIONTOKEN, Context.MODE_PRIVATE);
        if (getIntent().getExtras() != null) {
            task = getIntent().getStringExtra("task");
            ser_id = getIntent().getStringExtra("service_id");
            ser = getIntent().getStringExtra("service");
            dt = getIntent().getStringExtra("date");
            Log.d("Appn_Date---->", "onCreate: " + ser_id + "," + ser + "," + dt);
            textView_appn_dt.setText(dt);
            textView_res.setText(ser);

            if (task.equals("1")) {
                imageView.setBackgroundResource(R.drawable.order);
            } else if (task.equals("2")) {
                toolbar.setTitle("Staff Leave");
                textView_fullday_hint.setText("Full day absent ?");
                imageView.setBackgroundResource(R.drawable.user);
                textView_time_title.setText("Select staff leave time");
                textView_response.setText("Select staff absent time");
            }
        }


        tv_time_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_time_yes.setBackgroundColor(getResources().getColor(R.color.green_hase));
                tv_time_no.setBackgroundColor(getResources().getColor(R.color.dark_grey));
                tv_time_yes.setTextColor(Color.parseColor("#EEEEEE"));
                tv_time_no.setTextColor(Color.parseColor("#000000"));
                isFullDay = true;
                if (!TextUtils.isEmpty(task) && task.equals("1")) {
                    layout_slots.setVisibility(View.GONE);
                } else if (!TextUtils.isEmpty(task) && task.equals("2")) {
                    linearLayout.setVisibility(View.GONE);
                    text_start_time.setText("00:00");
                    text_end_time.setText("00:00");
                    isValidUnavailTime = false;
                }
            }
        });


        tv_time_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tv_time_yes.setBackgroundColor(getResources().getColor(R.color.dark_grey));
                tv_time_no.setBackgroundColor(getResources().getColor(R.color.green_hase));
                tv_time_yes.setTextColor(Color.parseColor("#000000"));
                tv_time_no.setTextColor(Color.parseColor("#FFFFFF"));
                isFullDay = false;
                if (!TextUtils.isEmpty(task) && task.equals("1")) {
                    layout_slots.setVisibility(View.VISIBLE);
                    callFetchSlotAPI();
                } else if (!TextUtils.isEmpty(task) && task.equals("2")) {
                    linearLayout.setVisibility(View.VISIBLE);
                }

            }
        });

        text_start_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_start_time();
                //timepicker_dialog("1");
            }
        });

        text_end_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_end_time();
                //timepicker_dialog("2");
            }
        });

        button_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!TextUtils.isEmpty(task) && task.equals("1")) {
                    //Service unavailable
                    callSerUnavailAPI();
                } else if (!TextUtils.isEmpty(task) && task.equals("2")) {
                    if (isFullDay == true) {
                        //Resource unavailable
                        callResUnavailAPI();
                    } else {
                        if (isValidUnavailTime) {
                            callResUnavailAPI();
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    new AlertDialogFailure(SerUnavailAskTimeActivity.this, "Select valid unavailable time", "OK", "", "Warning") {
                                        @Override
                                        public void onButtonClick() {
                                        }
                                    };
                                }
                            });
                        }
                    }
                }


            }
        });
        Log.d("arrayList_time--->", "onClick: " + arrayList_start_time1.size());
        button_add_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("arrayList_time--->", "onClick: " + arrayList_start_time1.size());
                if ((arrayList_start_time2.size() < 1) && (arrayList_start_time2.size() != 1)) {
                    String st_time = text_start_time.getText().toString();
                    String end_time = text_end_time.getText().toString();

                    boolean chktime = checktimings(st_time, end_time);
                    if (chktime) {
                        textView_time_validation.setVisibility(View.GONE);
                        isValidTime = true;
                        if (arrayList_start_time1.size() == 0) {
                            if (task.equals("1")) {
                                text_unavail_time_dt.setText("Service unavailability time on " + textView_appn_dt.getText().toString());
                            } else {
                                text_unavail_time_dt.setText("Staff unavailability time on " + textView_appn_dt.getText().toString());
                            }
                            linearLayout_show_time.setVisibility(View.VISIBLE);
                            arrayList_start_time1.add(st_time);
                            arrayList_end_time1.add(end_time);
                            textView_ser_unavail_time1.setText(arrayList_start_time1.get(0) + " - " + arrayList_end_time1.get(0));
                        } else if (arrayList_start_time1.size() == 1) {

                            boolean chktime_slot2 = checktimings2(arrayList_end_time1.get(0), st_time);
                            if (chktime_slot2) {
                                layout_show_time2.setVisibility(View.VISIBLE);
                                arrayList_start_time2.add(st_time);
                                arrayList_end_time2.add(end_time);
                                textView_ser_unavail_time2.setText(arrayList_start_time2.get(0) + " - " + arrayList_end_time2.get(0));
                            } else {
                                getAnimation("Unavailability time cannot overlap");
                            }
                        }
                        text_start_time.setText("00:00");
                        text_end_time.setText("00:00");
                        text_st_am_pm.setText("am");
                        text_et_am_pm.setText("am");
                    } else {
                        isValidTime = false;
                        getAnimation("Invalid Time");

                     /*   Animation anim = new AlphaAnimation(0.0f, 1.0f);
                        anim.setDuration(3000); //You can manage the blinking time with this parameter
                        anim.setStartOffset(100);
                        anim.setRepeatMode(Animation.REVERSE);
                        anim.setRepeatCount(Animation.INFINITE);
                        textView_time_validation.startAnimation(anim);*/
                    }

                } else {
                    Toast.makeText(SerUnavailAskTimeActivity.this, "You have reached the limit", Toast.LENGTH_LONG).show();
                }
            }

        });


    }

    public void callSerUnavailAPI() {


        if (!isFullDay) {
            if (!arrayList_startTime.isEmpty() && arrayList_startTime != null) {
                List<ServiceResourceUnavailBody> serviceResourceUnavailBodyList = new ArrayList<>();
                for (int j = 0; j < arrayList_startTime.size(); j++) {
                    ServiceResourceUnavailBody serviceResourceUnavailBody = new ServiceResourceUnavailBody();
                    List<UnavailSlotBody> slotBody = new ArrayList<>();
                    UnavailSlotBody unavailSlotBody = new UnavailSlotBody();
                    unavailSlotBody.setSlotNo(arrayList_slotNo.get(j));
                    unavailSlotBody.setStartTime(arrayList_startTime.get(j));
                    unavailSlotBody.setEndTime(arrayList_endTime.get(j));
                    slotBody.add(unavailSlotBody);

                    serviceResourceUnavailBody.setDate(dt);
                    serviceResourceUnavailBody.setIsFullDay(isFullDay);
                    serviceResourceUnavailBody.setSlotBody(slotBody);
                    serviceResourceUnavailBodyList.add(serviceResourceUnavailBody);
                    Log.d("Slots---", "onClick: " + arrayList_startTime.size() + "," + arrayList_slotNo.get(j) + "," + arrayList_startTime.get(j) + "," + arrayList_endTime.get(j));
                }

                ApptTransactionBody transactionBody = new ApptTransactionBody();
                transactionBody.setReqType(Constants.SERVICE_UNAVAILABLE);
                transactionBody.setSerId(ser_id);
                transactionBody.setSerResUnavail(serviceResourceUnavailBodyList);
                transactionBody.setMerId(sharedpreferences_sessionToken.getString(LoginActivity.MERID, ""));
                transactionBody.setDeviceId(sharedpreferences_sessionToken.getString(LoginActivity.DEVICEID, ""));
                transactionBody.setSessionToken(sharedpreferences_sessionToken.getString(LoginActivity.SESSIONTOKEN, ""));

                try {
                    Log.d("Token--->", "token: " + sharedpreferences_sessionToken.getString(LoginActivity.SESSIONTOKEN, ""));
                    boolean isConn = ConnectivityReceiver.isConnected();
                    if (isConn) {
                        intermediateAlertDialog = new IntermediateAlertDialog(SerUnavailAskTimeActivity.this);
                        apptServiceUnavailability(transactionBody);
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                new AlertDialogFailure(SerUnavailAskTimeActivity.this, getResources().getString(R.string.no_internet_sub_title), "OK", getResources().getString(R.string.no_internet_title), getResources().getString(R.string.no_internet_Heading)) {
                                    @Override
                                    public void onButtonClick() {

                                    }
                                };
                            }
                        });
                    }
                } catch (ApiException e) {
                    e.printStackTrace();
                }
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new AlertDialogFailure(SerUnavailAskTimeActivity.this, "No unavailable time selected", "OK", "", "Failed") {
                            @Override
                            public void onButtonClick() {

                            }
                        };
                    }
                });
            }
        } else {

            List<ServiceResourceUnavailBody> serviceResourceUnavailBodyList = new ArrayList<>();
            ServiceResourceUnavailBody serviceResourceUnavailBody = new ServiceResourceUnavailBody();
            serviceResourceUnavailBody.setDate(dt);
            serviceResourceUnavailBody.setIsFullDay(isFullDay);
            serviceResourceUnavailBodyList.add(serviceResourceUnavailBody);

            ApptTransactionBody transactionBody = new ApptTransactionBody();
            transactionBody.setReqType(Constants.SERVICE_UNAVAILABLE);
            transactionBody.setSerId(ser_id);
            transactionBody.setSerResUnavail(serviceResourceUnavailBodyList);
            transactionBody.setMerId(sharedpreferences_sessionToken.getString(LoginActivity.MERID, ""));
            transactionBody.setDeviceId(sharedpreferences_sessionToken.getString(LoginActivity.DEVICEID, ""));
            transactionBody.setSessionToken(sharedpreferences_sessionToken.getString(LoginActivity.SESSIONTOKEN, ""));

            try {
                Log.d("Token--->", "token: " + sharedpreferences_sessionToken.getString(LoginActivity.SESSIONTOKEN, ""));
                boolean isConn = ConnectivityReceiver.isConnected();
                if (isConn) {
                    intermediateAlertDialog = new IntermediateAlertDialog(SerUnavailAskTimeActivity.this);
                    apptServiceUnavailability(transactionBody);
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new AlertDialogFailure(SerUnavailAskTimeActivity.this, getResources().getString(R.string.no_internet_sub_title), "OK", getResources().getString(R.string.no_internet_title), getResources().getString(R.string.no_internet_Heading)) {
                                @Override
                                public void onButtonClick() {

                                }
                            };
                        }
                    });
                }
            } catch (ApiException e) {
                e.printStackTrace();
            }
        }
    }


    public void callResUnavailAPI() {
        ServiceResourceUnavailBody serviceResourceUnavailBody = new ServiceResourceUnavailBody();
        List<ServiceResourceUnavailBody> serviceResourceUnavailBodyList = new ArrayList<>();
        List<UnavailSlotBody> slotBody = new ArrayList<>();
        UnavailSlotBody unavailSlotBody = new UnavailSlotBody();
        unavailSlotBody.setSlotNo("");
        unavailSlotBody.setStartTime(text_start_time.getText().toString().trim() + ":00");
        unavailSlotBody.setEndTime(text_end_time.getText().toString().trim() + ":00");
        slotBody.add(unavailSlotBody);
        serviceResourceUnavailBody.setDate(dt);
        serviceResourceUnavailBody.setIsFullDay(isFullDay);
        serviceResourceUnavailBody.setSlotBody(slotBody);
        serviceResourceUnavailBodyList.add(serviceResourceUnavailBody);

        Log.d("ResUnavailTime---", "onClick: " + text_start_time.getText().toString() + "," + text_end_time.getText().toString());
        ApptTransactionBody transactionBody = new ApptTransactionBody();
        transactionBody.setReqType(Constants.RESOURCE_UNAVAILABLE);
        transactionBody.setResId(ser_id);
        transactionBody.serResUnavail(serviceResourceUnavailBodyList);
        transactionBody.setMerId(sharedpreferences_sessionToken.getString(LoginActivity.MERID, ""));
        transactionBody.setDeviceId(sharedpreferences_sessionToken.getString(LoginActivity.DEVICEID, ""));
        transactionBody.setSessionToken(sharedpreferences_sessionToken.getString(LoginActivity.SESSIONTOKEN, ""));

        try {
            Log.d("Token--->", "token: " + sharedpreferences_sessionToken.getString(LoginActivity.SESSIONTOKEN, ""));
            boolean isConn = ConnectivityReceiver.isConnected();
            if (isConn) {
                intermediateAlertDialog = new IntermediateAlertDialog(SerUnavailAskTimeActivity.this);
                apptResourceUnavailability(transactionBody);
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new AlertDialogFailure(SerUnavailAskTimeActivity.this, getResources().getString(R.string.no_internet_sub_title), "OK", getResources().getString(R.string.no_internet_title), getResources().getString(R.string.no_internet_Heading)) {
                            @Override
                            public void onButtonClick() {

                            }
                        };
                    }
                });
            }
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

    public void callFetchSlotAPI() {
        AppointmentEnquiryBody enquiryBody = new AppointmentEnquiryBody();
        enquiryBody.setReqType(Constants.AVAILABLE_SLOTS);
        enquiryBody.setMerId(sharedpreferences_sessionToken.getString(LoginActivity.MERID, ""));
        enquiryBody.callerType("m");
        enquiryBody.setSerId(ser_id);
        enquiryBody.setDate(dt); //dt
        enquiryBody.setDeviceId(sharedpreferences_sessionToken.getString(LoginActivity.DEVICEID, ""));
        enquiryBody.setSessionToken(sharedpreferences_sessionToken.getString(LoginActivity.SESSIONTOKEN, ""));
        boolean isConn = ConnectivityReceiver.isConnected();
        if (isConn) {
            try {
                intermediateAlertDialog = new IntermediateAlertDialog(SerUnavailAskTimeActivity.this);
                fetchApptAvailSlots(enquiryBody);
            } catch (ApiException e) {
                e.printStackTrace();
            }
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    new AlertDialogFailure(SerUnavailAskTimeActivity.this, getResources().getString(R.string.no_internet_sub_title), "OK", getResources().getString(R.string.no_internet_title), getResources().getString(R.string.no_internet_Heading)) {
                        @Override
                        public void onButtonClick() {
                            callFetchSlotAPI();
                        }
                    };
                }
            });
        }
    }


    private void getAnimation(String msg) {
        textView_time_validation.setVisibility(View.VISIBLE);
        textView_time_validation.setText(msg);
        Animation startAnimation = AnimationUtils.loadAnimation(SerUnavailAskTimeActivity.this, R.anim.blinking_animation1);
        textView_time_validation.startAnimation(startAnimation);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                textView_time_validation.clearAnimation();
            }
        }, 2000);

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
                        //text_start_time.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + start_time + "</u>  </font>"));
                        text_start_time.setText(start_time);

                    }
                }, 00, 00, false);
        timePickerDialog.show();
    }

    public void get_end_time() {

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
                        //text_end_time.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + end_time + "</u>  </font>"));


                        boolean chktime = checktimings(start_time, end_time);
                        if (chktime) {
                            text_end_time.setText(end_time);
                            isValidUnavailTime = true;
                        } else {
                            isValidUnavailTime = false;
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    new AlertDialogFailure(SerUnavailAskTimeActivity.this, "Select valid unavailable time", "OK", "", "Warning") {
                                        @Override
                                        public void onButtonClick() {
                                        }
                                    };
                                }
                            });
                        }

                    }
                }, 00, 00, false);
        timePickerDialog.show();


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

    @Override
    public void onBackPressed() {
        super.onBackPressed();

/*        Intent in = new Intent(SerUnavailAskTimeActivity.this, SettingsActivity.class);
        startActivity(in);
        finish();
        overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);*/
    }

    void timepicker_dialog(final String id) {
        LayoutInflater factory = LayoutInflater.from(this);
        final View deleteDialogView = factory.inflate(R.layout.timepicker_layout, null);
        final AlertDialog pickerDialog = new AlertDialog.Builder(this).create();
        pickerDialog.setView(deleteDialogView);
        final TimePicker pickStartTime = (TimePicker) deleteDialogView.findViewById(R.id.StartTime);
        Button button = (Button) deleteDialogView.findViewById(R.id.button_picker_ok);
        TextView textView_title = (TextView) deleteDialogView.findViewById(R.id.text_alert_title);
        pickStartTime.setIs24HourView(true);
        pickStartTime.setCurrentHour(00);
        pickStartTime.setCurrentMinute(00);
        pickStartTime.setOnTimeChangedListener(mStartTimeChangedListener);

        if (task.equals("1")) {
            textView_title.setText("Select service unavailability time");
        } else if (task.equals("2")) {
            textView_title.setText("Select staff unavailability time");
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

                if (id.equals("1")) {
                    text_start_time.setText(hr + ":" + min);
                    if (hour >= 12) {
                        text_st_am_pm.setText("pm");
                    }
                } else if (id.equals("2")) {
                    text_end_time.setText(hr + ":" + min);
                    if (hour >= 12) {
                        text_et_am_pm.setText("pm");
                    }
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

    private void apptServiceUnavailability(ApptTransactionBody requestBody) throws ApiException {
        Log.d("createStaff---", "createStaff: " + requestBody);
        intermediateAlertDialog = new IntermediateAlertDialog(SerUnavailAskTimeActivity.this);
        OkHttpApiClient okHttpApiClient = new OkHttpApiClient(SerUnavailAskTimeActivity.this);
        MerchantApisApi webMerchantApisApi = new MerchantApisApi();
        webMerchantApisApi.setApiClient(okHttpApiClient.getApiClient());

        webMerchantApisApi.merchantAppointmentTransactionAsync(requestBody, new ApiCallback<ApptTransactionResponse>() {
            @Override
            public void onFailure(ApiException e, int statusCode, Map<String, List<String>> responseHeaders) {
                Log.d("createStaff--->", "onFailure-" + e.getMessage());
                if (intermediateAlertDialog != null) {
                    intermediateAlertDialog.dismissAlertDialog();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new AlertDialogFailure(SerUnavailAskTimeActivity.this, getResources().getString(R.string.try_again), "OK", getResources().getString(R.string.went_wrong), "Failed") {
                            @Override
                            public void onButtonClick() {
                           /*     startActivity(new Intent(SerUnavailAskTimeActivity.this, SettingsActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                                finish();
                                overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);*/
                                onBackPressed();
                            }
                        };
                    }
                });
            }

            @Override
            public void onSuccess(ApptTransactionResponse result, int statusCode, Map<String, List<String>> responseHeaders) {
                Log.d("createStaff--->", "onSuccess-" + result.getStatusCode() + "," + result.getStatusMessage());
                if (intermediateAlertDialog != null) {
                    intermediateAlertDialog.dismissAlertDialog();
                }
                if (Integer.parseInt(result.getStatusCode()) == 200) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new AlertDialogFailure(SerUnavailAskTimeActivity.this, "Service unavailability added successfully!", "OK", "", "Success") {
                                @Override
                                public void onButtonClick() {
                                    startActivity(new Intent(SerUnavailAskTimeActivity.this, DashboardActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                                    finish();
                                    overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);
                                }
                            };
                        }
                    });

                } else if (Integer.parseInt(result.getStatusCode()) == 406) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new AlertDialogFailure(SerUnavailAskTimeActivity.this, "Appointment already available for this slot!", "OK", "", "Warning") {
                                @Override
                                public void onButtonClick() {
                                    if (intermediateAlertDialog != null) {
                                        intermediateAlertDialog.dismissAlertDialog();
                                    }
                                }
                            };
                        }
                    });

                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new AlertDialogFailure(SerUnavailAskTimeActivity.this, "Service unavailability added failed. Please try again later!", "OK", "", "Failed") {
                                @Override
                                public void onButtonClick() {

                                    onBackPressed();
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

    private void apptResourceUnavailability(ApptTransactionBody requestBody) throws ApiException {
        Log.d("createStaff---", "createStaff: " + requestBody);
        intermediateAlertDialog = new IntermediateAlertDialog(SerUnavailAskTimeActivity.this);
        OkHttpApiClient okHttpApiClient = new OkHttpApiClient(SerUnavailAskTimeActivity.this);
        MerchantApisApi webMerchantApisApi = new MerchantApisApi();
        webMerchantApisApi.setApiClient(okHttpApiClient.getApiClient());

        webMerchantApisApi.merchantAppointmentTransactionAsync(requestBody, new ApiCallback<ApptTransactionResponse>() {
            @Override
            public void onFailure(ApiException e, int statusCode, Map<String, List<String>> responseHeaders) {
                Log.d("createStaff--->", "onFailure-" + e.getMessage());
                if (intermediateAlertDialog != null) {
                    intermediateAlertDialog.dismissAlertDialog();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new AlertDialogFailure(SerUnavailAskTimeActivity.this, getResources().getString(R.string.try_again), "OK", getResources().getString(R.string.went_wrong), "Failed") {
                            @Override
                            public void onButtonClick() {
                              /*  startActivity(new Intent(SerUnavailAskTimeActivity.this, SettingsActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                                finish();
                                overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);*/

                                onBackPressed();
                            }
                        };
                    }
                });
            }

            @Override
            public void onSuccess(ApptTransactionResponse result, int statusCode, Map<String, List<String>> responseHeaders) {
                Log.d("createStaff--->", "onSuccess-" + result.getStatusCode() + "," + result.getStatusMessage());
                if (intermediateAlertDialog != null) {
                    intermediateAlertDialog.dismissAlertDialog();
                }
                if (Integer.parseInt(result.getStatusCode()) == 200) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new AlertDialogFailure(SerUnavailAskTimeActivity.this, "Staff unavailability added successfully!", "OK", "", "Success") {
                                @Override
                                public void onButtonClick() {
                                    startActivity(new Intent(SerUnavailAskTimeActivity.this, DashboardActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                                    finish();
                                    overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);
                                }
                            };
                        }
                    });

                } else if (Integer.parseInt(result.getStatusCode()) == 406) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new AlertDialogFailure(SerUnavailAskTimeActivity.this, "Appointment already available for this slot!", "OK", "", "Warning") {
                                @Override
                                public void onButtonClick() {
                                   /* startActivity(new Intent(SerUnavailAskTimeActivity.this, SettingsActivity.class));
                                    finish();
                                    overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);*/
                                }
                            };
                        }
                    });

                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new AlertDialogFailure(SerUnavailAskTimeActivity.this, "Staff unavailability failed. Please try again later!", "OK", "", "Failed") {
                                @Override
                                public void onButtonClick() {
                                   /* startActivity(new Intent(SerUnavailAskTimeActivity.this, SettingsActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                                    finish();
                                    overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);*/

                                    onBackPressed();
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

    private void fetchApptAvailSlots(AppointmentEnquiryBody requestBody) throws ApiException {
        Log.d("fetchApptService---", "login: " + requestBody);
        OkHttpApiClient okHttpApiClient = new OkHttpApiClient(SerUnavailAskTimeActivity.this);
        MerchantApisApi webMerchantApisApi = new MerchantApisApi();
        webMerchantApisApi.setApiClient(okHttpApiClient.getApiClient());

        webMerchantApisApi.merchantAppointmentEnquiryAsync(requestBody, new ApiCallback<AppointmentEnquiryResponse>() {
            @Override
            public void onFailure(ApiException e, int statusCode, Map<String, List<String>> responseHeaders) {
                Log.d("fetchApptService--->", "onFailure-" + e.getMessage());
                if (intermediateAlertDialog != null) {
                    intermediateAlertDialog.dismissAlertDialog();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new AlertDialogFailure(SerUnavailAskTimeActivity.this, getResources().getString(R.string.try_again), "OK", getResources().getString(R.string.went_wrong), "Failed") {
                            @Override
                            public void onButtonClick() {
                              /*  startActivity(new Intent(SerUnavailAskTimeActivity.this, SettingsActivity.class));
                                finish();
                                overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);*/
                                onBackPressed();
                            }
                        };
                    }
                });

            }

            @Override
            public void onSuccess(final AppointmentEnquiryResponse result, int statusCode, Map<String, List<String>> responseHeaders) {

                Log.d("fetchApptService--->", "onSuccess-" + statusCode + "," + result);
                if (intermediateAlertDialog != null) {
                    intermediateAlertDialog.dismissAlertDialog();
                }

                if (Integer.parseInt(result.getStatusCode()) == 200) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            if (!result.getAvailAppointments().isEmpty() && result.getAvailAppointments() != null) {
                                final SerUnavailSlotRecycleAdapter serUnavailSlotRecycleAdapter = new SerUnavailSlotRecycleAdapter(SerUnavailAskTimeActivity.this, task, result.getAvailAppointments());
                                recyclerView_slots.setAdapter(serUnavailSlotRecycleAdapter);

                            }


                        }
                    });
                } else if (Integer.parseInt(result.getStatusCode()) == 404) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new AlertDialogFailure(SerUnavailAskTimeActivity.this, "No available slots found for " + ser + " service", "OK", "", "Success") {
                                @Override
                                public void onButtonClick() {
                          /*          startActivity(new Intent(SerUnavailAskTimeActivity.this, SettingsActivity.class));
                                    finish();
                                    overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);*/
                                    onBackPressed();
                                }
                            };
                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new AlertDialogFailure(SerUnavailAskTimeActivity.this, getResources().getString(R.string.try_again), "OK", getResources().getString(R.string.went_wrong), "Failed") {
                                @Override
                                public void onButtonClick() {
                               /*     startActivity(new Intent(SerUnavailAskTimeActivity.this, SettingsActivity.class));
                                    finish();
                                    overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);*/

                                    onBackPressed();
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (intermediateAlertDialog != null) {
            intermediateAlertDialog.dismissAlertDialog();
            intermediateAlertDialog = null;
        }
    }

    @Override
    public void unavailList(String flag, String slot_no, String start_time, String end_time) {
        if (flag.equals("1")) {
            arrayList_slotNo.add(slot_no);
            arrayList_startTime.add(start_time);
            arrayList_endTime.add(end_time);
            Log.d("unavailList--->", "unavailList: " + arrayList_slotNo + "," + arrayList_startTime + "," + arrayList_endTime);
        } else if (flag.equals("0")) {
            arrayList_slotNo.remove(slot_no);
            arrayList_startTime.remove(start_time);
            arrayList_endTime.remove(end_time);
            Log.d("unavailList--->", "unavailList: " + arrayList_slotNo + "," + arrayList_startTime + "," + arrayList_endTime);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.finish_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_finish) {
            if (!TextUtils.isEmpty(task) && task.equals("1")) {
                //Service unavailable
                callSerUnavailAPI();
            } else if (!TextUtils.isEmpty(task) && task.equals("2")) {
                if (isFullDay == true) {
                    //Resource unavailable
                    callResUnavailAPI();
                } else {
                    if (isValidUnavailTime) {
                        callResUnavailAPI();
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                new AlertDialogFailure(SerUnavailAskTimeActivity.this, "Select valid unavailable time", "OK", "", "Warning") {
                                    @Override
                                    public void onButtonClick() {
                                    }
                                };
                            }
                        });
                    }
                }
            }

        }
        return super.onOptionsItemSelected(item);
    }

}
