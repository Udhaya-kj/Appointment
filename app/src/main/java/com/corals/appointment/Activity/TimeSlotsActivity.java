package com.corals.appointment.Activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.corals.appointment.Adapters.RecyclerAdapter_TimeSlots;
import com.corals.appointment.Adapters.ServicesAdapter_Calender;
import com.corals.appointment.Client.ApiCallback;
import com.corals.appointment.Client.ApiException;
import com.corals.appointment.Client.OkHttpApiClient;
import com.corals.appointment.Client.api.MerchantApisApi;
import com.corals.appointment.Client.model.AppointmentAvailableSlots;
import com.corals.appointment.Client.model.AppointmentEnquiryBody;
import com.corals.appointment.Client.model.AppointmentEnquiryResponse;
import com.corals.appointment.Client.model.AppointmentService;
import com.corals.appointment.Constants.Constants;
import com.corals.appointment.Dialogs.AlertDialogFailure;
import com.corals.appointment.Dialogs.IntermediateAlertDialog;
import com.corals.appointment.Model.TimeSlotDataModel;
import com.corals.appointment.R;
import com.corals.appointment.receiver.ConnectivityReceiver;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TimeSlotsActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    TextView textView_appn_dt, textView_res;
    private IntermediateAlertDialog intermediateAlertDialog;
    String service_id, service, date, cus_id, cus, cus_email, page_id,cus_mob,service_dur;
    private SharedPreferences sharedpreferences_sessionToken;
    TimeSlotDataModel timeSlotDataModel;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_slots);

        Toolbar toolbar = findViewById(R.id.toolbar_biz_new_appt);
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
        recyclerView = findViewById(R.id.recyclerview_time_slots);
        textView_res = findViewById(R.id.text_resource);
        textView_appn_dt = findViewById(R.id.text_appn_dt);

        LinearLayoutManager li = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(li);
        sharedpreferences_sessionToken = getSharedPreferences(LoginActivity.MyPREFERENCES_SESSIONTOKEN, Context.MODE_PRIVATE);

        if (getIntent().getExtras() != null) {
            page_id = getIntent().getStringExtra("page_id");
            service_id = getIntent().getStringExtra("service_id");
            service = getIntent().getStringExtra("service");
            date = getIntent().getStringExtra("date");
            cus_id = getIntent().getStringExtra("cus_id");
            cus = getIntent().getStringExtra("cus");
            cus_email = getIntent().getStringExtra("cus_email");
            cus_mob = getIntent().getStringExtra("cus_mob");
            service_dur = getIntent().getStringExtra("service_dur");
            Log.d("Appn_Date-->", "onCreate: " + page_id+","+date + "," + service + "," + service_id + "," + cus_id + "," + cus + "," + cus_email+ "," + cus_mob+ "," + service_dur);
            textView_appn_dt.setText(date);
            textView_res.setText(service);

            timeSlotDataModel=new TimeSlotDataModel(page_id,cus_id,cus,cus_email,cus_mob,date,service_id,service,service_dur);
           /* timeSlotDataModel.setPage_id(page_id);
            timeSlotDataModel.setCus_id(cus_id);
            timeSlotDataModel.setCus(cus);
            timeSlotDataModel.setCus_email(cus_email);
            timeSlotDataModel.setDate(date);
            timeSlotDataModel.setSer_id(service_id);
            timeSlotDataModel.setSer(service);
            timeSlotDataModel.setRes_id(res_id);
            timeSlotDataModel.setRes(res);*/

            Log.d("Appn_Date2--->", "onCreate: " + timeSlotDataModel.getPage_id()+","+timeSlotDataModel.getSer());

        }
        callAPI();
    }

    private void callAPI() {

        boolean isConn = ConnectivityReceiver.isConnected();
        if (isConn) {
            try {
                AppointmentEnquiryBody enquiryBody = new AppointmentEnquiryBody();
                enquiryBody.setReqType(Constants.AVAILABLE_SLOTS);
                enquiryBody.setMerId(sharedpreferences_sessionToken.getString(LoginActivity.MERID, ""));
                enquiryBody.callerType("m");
                enquiryBody.setDate(date);
                enquiryBody.setSerId(service_id);
                enquiryBody.setDeviceId(sharedpreferences_sessionToken.getString(LoginActivity.DEVICEID, ""));
                enquiryBody.setSessionToken(sharedpreferences_sessionToken.getString(LoginActivity.SESSIONTOKEN, ""));
                fetchApptAvailSlots(enquiryBody);
            } catch (ApiException e) {
                e.printStackTrace();
            }
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    new AlertDialogFailure(TimeSlotsActivity.this, getResources().getString(R.string.no_internet_sub_title), "OK", getResources().getString(R.string.no_internet_title), getResources().getString(R.string.no_internet_Heading)) {
                        @Override
                        public void onButtonClick() {
                            callAPI();
                        }
                    };
                }
            });
        }
    }

    @SuppressLint("NewApi")
    public void printTimeSlots(LocalTime startTime, LocalTime endTime, int slotSizeInMinutes) {
        for (LocalTime time = startTime, nextTime; time.isBefore(endTime); time = nextTime) {
            nextTime = time.plusMinutes(slotSizeInMinutes);
            if (nextTime.isAfter(endTime))
                break; // time slot crosses end time
            System.out.println("TimeSlots---->" + time + "-" + nextTime);
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        failedIntent();
    }

    private void fetchApptAvailSlots(AppointmentEnquiryBody requestBody) throws ApiException {

        intermediateAlertDialog = new IntermediateAlertDialog(TimeSlotsActivity.this);
        Log.d("fetchApptSlots---", "login: " + requestBody);
        OkHttpApiClient okHttpApiClient = new OkHttpApiClient(TimeSlotsActivity.this);
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
                        new AlertDialogFailure(TimeSlotsActivity.this, getResources().getString(R.string.try_again), "OK", getResources().getString(R.string.went_wrong), "Failed") {
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
                            if (!result.getAvailAppointments().isEmpty() && result.getAvailAppointments() != null) {
                                RecyclerAdapter_TimeSlots recyclerAdapter_timeSlots = new RecyclerAdapter_TimeSlots(TimeSlotsActivity.this, result.getAvailAppointments(), timeSlotDataModel);
                                recyclerView.setAdapter(recyclerAdapter_timeSlots);
                            }
                        }
                    });
                } else if (Integer.parseInt(result.getStatusCode()) == 404) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new AlertDialogFailure(TimeSlotsActivity.this, getResources().getString(R.string.no_avail_slots), "OK", "", "Warning") {
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
                            new AlertDialogFailure(TimeSlotsActivity.this, getResources().getString(R.string.try_again), "OK", getResources().getString(R.string.went_wrong), "Failed") {
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (intermediateAlertDialog != null) {
            intermediateAlertDialog.dismissAlertDialog();
            intermediateAlertDialog = null;
        }
    }
    private void failedIntent(){
        if(!TextUtils.isEmpty(page_id) && page_id.equals("1")) {
            startActivity(new Intent(TimeSlotsActivity.this, AppointmentActivity.class));
            finish();
            overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);
        }
        else if(!TextUtils.isEmpty(page_id) && page_id.equals("2")) {
            startActivity(new Intent(TimeSlotsActivity.this, CustomerActivity_Bottom.class));
            finish();
            overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);
        }
        else {
            startActivity(new Intent(TimeSlotsActivity.this, DashboardActivity.class));
            finish();
            overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);
        }
    }
}
