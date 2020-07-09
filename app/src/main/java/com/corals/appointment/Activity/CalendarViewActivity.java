package com.corals.appointment.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import com.corals.appointment.Adapters.RecyclerAdapter_TimeSlots;
import com.corals.appointment.Client.ApiCallback;
import com.corals.appointment.Client.ApiException;
import com.corals.appointment.Client.OkHttpApiClient;
import com.corals.appointment.Client.api.MerchantApisApi;
import com.corals.appointment.Client.model.AppointmentEnquiryBody;
import com.corals.appointment.Client.model.AppointmentEnquiryResponse;
import com.corals.appointment.Client.model.AppointmentService;
import com.corals.appointment.Client.model.AvailDay;
import com.corals.appointment.Client.model.TimeData;
import com.corals.appointment.Constants.Constants;
import com.corals.appointment.Dialogs.AlertDialogFailure;
import com.corals.appointment.Dialogs.IntermediateAlertDialog;
import com.corals.appointment.R;
import com.corals.appointment.receiver.ConnectivityReceiver;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class CalendarViewActivity extends AppCompatActivity {

    CalendarView calendarView;
    String calendar_date, cus_name, cus_id, cus_email, cus_mob, page_id, ser, ser_id, res, res_id;
    Button textView_cal_next;
    Calendar c;
    private SharedPreferences sharedpreferences_sessionToken;
    private IntermediateAlertDialog intermediateAlertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_appt);

        Toolbar toolbar = findViewById(R.id.toolbar_view_appt);
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

        if (getIntent().getExtras() != null) {
            page_id = getIntent().getStringExtra("page_id");
            cus_name = getIntent().getStringExtra("cus");
            cus_id = getIntent().getStringExtra("cus_id");
            cus_email = getIntent().getStringExtra("cus_email");
            cus_mob = getIntent().getStringExtra("cus_mob");
            ser = getIntent().getStringExtra("service");
            ser_id = getIntent().getStringExtra("service_id");
            res = getIntent().getStringExtra("res");
            res_id = getIntent().getStringExtra("res_id");
            Log.d("CalendarView---->", "onCreate: " + ser + "," + ser_id + "," + res_id + "," + res + "," + cus_id + "," + cus_id + "," + cus_email + "," + cus_mob);

            if (page_id.equals("1")) {
                toolbar.setTitle("Select Date");
            } else {
                toolbar.setTitle(cus_name);
            }
        }

        calendarView = findViewById(R.id.calendarView);
        textView_cal_next = findViewById(R.id.text_calendar_next);

        c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());
        final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        calendar_date = df.format(c.getTime());
        calendarView.setMinDate(System.currentTimeMillis() - 1000);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {

                String month_ = null;
                if (String.valueOf(month + 1).length() == 1) {
                    month_ = "0" + (month + 1);
                } else {
                    month_ = String.valueOf(month + 1);
                }

                String day = null;
                if (String.valueOf(dayOfMonth).length() == 1) {
                    day = "0" + dayOfMonth;
                } else {
                    day = String.valueOf(dayOfMonth);
                }
                calendar_date= year + "-" + month_ + "-" + day;

                //calendar_date = year + "-" + (month + 1) + "-" + dayOfMonth;
            }
        });

        textView_cal_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isConn = ConnectivityReceiver.isConnected();
                if (isConn) {
                    Intent i = new Intent(CalendarViewActivity.this, TimeSlotsActivity.class);
                    i.putExtra("page_id", page_id);
                    i.putExtra("date", calendar_date);
                    i.putExtra("cus", cus_name);
                    i.putExtra("cus_id", cus_id);
                    i.putExtra("cus_email", cus_email);
                    i.putExtra("cus_mob", cus_mob);
                    i.putExtra("service", ser);
                    i.putExtra("service_id", ser_id);
                    i.putExtra("res", res);
                    i.putExtra("res_id", res_id);
                    startActivity(i);
                    finish();
                    overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);

                } else {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new AlertDialogFailure(CalendarViewActivity.this, getResources().getString(R.string.no_internet_sub_title), "OK", getResources().getString(R.string.no_internet_title), getResources().getString(R.string.no_internet_Heading)) {
                                @Override
                                public void onButtonClick() {

                                }
                            };
                        }
                    });
                }
            }
        });

        callAPI();
    }

    private void callAPI() {

        boolean isConn = ConnectivityReceiver.isConnected();
        if (isConn) {
            try {
                AppointmentEnquiryBody enquiryBody = new AppointmentEnquiryBody();
                enquiryBody.setReqType(Constants.SERVICE_ACTIVE_DAYS);
                enquiryBody.setMerId(sharedpreferences_sessionToken.getString(LoginActivity.MERID, ""));
                enquiryBody.callerType("m");
                enquiryBody.setSerId(ser_id);
                enquiryBody.setDeviceId(sharedpreferences_sessionToken.getString(LoginActivity.DEVICEID, ""));
                enquiryBody.setSessionToken(sharedpreferences_sessionToken.getString(LoginActivity.SESSIONTOKEN, ""));
                fetchActiveDays(enquiryBody);
            } catch (ApiException e) {
                e.printStackTrace();
            }
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    new AlertDialogFailure(CalendarViewActivity.this, getResources().getString(R.string.no_internet_sub_title), "OK", getResources().getString(R.string.no_internet_title), getResources().getString(R.string.no_internet_Heading)) {
                        @Override
                        public void onButtonClick() {
                            callAPI();
                        }
                    };
                }
            });
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        failedIntent();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (intermediateAlertDialog != null) {
            intermediateAlertDialog.dismissAlertDialog();
            intermediateAlertDialog = null;
        }
    }

    private void fetchActiveDays(AppointmentEnquiryBody requestBody) throws ApiException {

        intermediateAlertDialog = new IntermediateAlertDialog(CalendarViewActivity.this);
        Log.d("fetchApptSlots---", "login: " + requestBody);
        OkHttpApiClient okHttpApiClient = new OkHttpApiClient(CalendarViewActivity.this);
        MerchantApisApi webMerchantApisApi = new MerchantApisApi();
        webMerchantApisApi.setApiClient(okHttpApiClient.getApiClient());

        webMerchantApisApi.merchantAppointmentEnquiryAsync(requestBody, new ApiCallback<AppointmentEnquiryResponse>() {
            @Override
            public void onFailure(ApiException e, int statusCode, Map<String, List<String>> responseHeaders) {
                Log.d("fetchApptSlots--->", "onFailure-" + e.getMessage());
                if (intermediateAlertDialog != null) {
                    intermediateAlertDialog.dismissAlertDialog();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new AlertDialogFailure(CalendarViewActivity.this, getResources().getString(R.string.try_again), "OK", getResources().getString(R.string.went_wrong), "Failed") {
                            @Override
                            public void onButtonClick() {
                                failedIntent();
                            }
                        };
                    }
                });
            }

            @Override
            public void onSuccess(final AppointmentEnquiryResponse result, int statusCode, Map<String, List<String>> responseHeaders) {

                Log.d("fetchApptSlots--->", "onSuccess-" + statusCode + "," + result);
                if (intermediateAlertDialog != null) {
                    intermediateAlertDialog.dismissAlertDialog();
                }
                if (Integer.parseInt(result.getStatusCode()) == 200) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (result.getService() != null) {
                                AppointmentService appointmentService = result.getService();
                                List<AvailDay> availDayList = appointmentService.getAvailDays();

                                String day = availDayList.get(0).getDay();
                                List<TimeData> timing = availDayList.get(0).getTiming();
                                for (int y = 0; y < timing.size(); y++) {
                                    String id = timing.get(y).getAvailId();
                                    String st = timing.get(y).getStartTime();
                                    String et = timing.get(y).getEndTime();
                                    Log.d("Time--->", "run: "+id+","+st+","+et);
                                }
                                Log.d("Day--->", "run: " + day + "," + availDayList);

                              /*  RecyclerAdapter_TimeSlots recyclerAdapter_timeSlots = new RecyclerAdapter_TimeSlots(TimeSlotsActivity.this, result.getAvailAppointments(), timeSlotDataModel);
                                recyclerView.setAdapter(recyclerAdapter_timeSlots);*/
                            }
                        }
                    });
                } else if (Integer.parseInt(result.getStatusCode()) == 404) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new AlertDialogFailure(CalendarViewActivity.this, getResources().getString(R.string.no_avail_slots), "OK", "", "Warning") {
                                @Override
                                public void onButtonClick() {
                                    failedIntent();
                                }
                            };
                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new AlertDialogFailure(CalendarViewActivity.this, getResources().getString(R.string.try_again), "OK", getResources().getString(R.string.went_wrong), "Failed") {
                                @Override
                                public void onButtonClick() {
                                    failedIntent();
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

    private void failedIntent() {
        if (!TextUtils.isEmpty(page_id) && page_id.equals("1")) {
            Intent i = new Intent(CalendarViewActivity.this, AppointmentActivity.class);
            startActivity(i);
            finish();
            overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);
        } else if (!TextUtils.isEmpty(page_id) && page_id.equals("2")) {
            Intent i = new Intent(CalendarViewActivity.this, CustomerActivity_Bottom.class);
            startActivity(i);
            finish();
            overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);
        } else {
            Intent i = new Intent(CalendarViewActivity.this, DashboardActivity.class);
            startActivity(i);
            finish();
            overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);
        }
    }
}
