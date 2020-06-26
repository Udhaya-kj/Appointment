package com.corals.appointment.Activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import com.corals.appointment.Dialogs.AlertDialogFailure;
import com.corals.appointment.Dialogs.IntermediateAlertDialog;
import com.corals.appointment.R;
import com.corals.appointment.receiver.ConnectivityReceiver;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TimeSlotsActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<String> arrayList_s_time, arrayList_e_time;
    TextView textView_appn_dt, textView_res;
    private IntermediateAlertDialog intermediateAlertDialog;
    String service_id, service, date;
    private SharedPreferences sharedpreferences_sessionToken;
    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_slots);

        Toolbar toolbar = findViewById(R.id.toolbar_biz_new_appt);
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
        recyclerView = findViewById(R.id.recyclerview_time_slots);
        textView_res = findViewById(R.id.text_resource);
        textView_appn_dt = findViewById(R.id.text_appn_dt);
        arrayList_s_time = new ArrayList<>();
        arrayList_e_time = new ArrayList<>();

        arrayList_s_time.add("8 AM  -  8:30 AM");
        arrayList_s_time.add("8:30 AM  -  9 AM");
        arrayList_s_time.add("9 AM  -  9:30 AM");
        arrayList_s_time.add("9:30 AM  -  10 AM");
        arrayList_s_time.add("10 AM  -  10:30 AM");
        arrayList_s_time.add("10:30 AM  -  11 AM");
        arrayList_s_time.add("11 AM  -  11:30 AM");
        arrayList_s_time.add("11:30 AM  -  12 PM");
        arrayList_s_time.add("12 PM  -  12:30 PM");
        arrayList_s_time.add("12:30 PM  -  13 PM");
        arrayList_s_time.add("13 PM  -  13:30 PM");
        arrayList_s_time.add("13:30 PM  -  14 PM");

        arrayList_e_time.add("0");
        arrayList_e_time.add("1");
        arrayList_e_time.add("0");
        arrayList_e_time.add("1");
        arrayList_e_time.add("2");
        arrayList_e_time.add("1");
        arrayList_e_time.add("0");
        arrayList_e_time.add("2");
        arrayList_e_time.add("0");
        arrayList_e_time.add("1");
        arrayList_e_time.add("0");
        arrayList_e_time.add("1");

        LinearLayoutManager li = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(li);
        /*RecyclerAdapter_TimeSlots recyclerAdapter_timeSlots = new RecyclerAdapter_TimeSlots(TimeSlotsActivity.this, arrayList_s_time, arrayList_e_time);
        recyclerView.setAdapter(recyclerAdapter_timeSlots);*/
        sharedpreferences_sessionToken = getSharedPreferences(LoginActivity.MyPREFERENCES_SESSIONTOKEN, Context.MODE_PRIVATE);

        if (getIntent().getExtras() != null) {
            service_id = getIntent().getStringExtra("service_id");
            service = getIntent().getStringExtra("service");
            date = getIntent().getStringExtra("date");
            Log.d("Appn_Date---->", "onCreate: " + date + "," + service+ "," + service_id);
            textView_appn_dt.setText(date);
            textView_res.setText(service);
        }

        //printTimeSlots(LocalTime.parse("08:00"), LocalTime.parse("17:00"), 30);

        AppointmentEnquiryBody enquiryBody = new AppointmentEnquiryBody();
        enquiryBody.setReqType("E-AA.");
        enquiryBody.setMerId(sharedpreferences_sessionToken.getString(LoginActivity.MERID, ""));
        enquiryBody.callerType("m");
        enquiryBody.setDate("2020-06-24");
        enquiryBody.setSerId(service_id);
        enquiryBody.setDeviceId("c43cbfe00b37ae6133ca023484869d2c489a8974ba48fb3286aa058292d08f0e");
        enquiryBody.setSessionToken(sharedpreferences_sessionToken.getString(LoginActivity.SESSIONTOKEN, ""));
        boolean isConn = ConnectivityReceiver.isConnected();
        if (isConn) {
            try {
                fetchApptAvailSlots(enquiryBody);
            } catch (ApiException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(TimeSlotsActivity.this, "No internet connection!", Toast.LENGTH_SHORT).show();
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
        Intent i = new Intent(TimeSlotsActivity.this, CalendarServicesActivity.class);
        startActivity(i);
        finish();
        overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);
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
                        new AlertDialogFailure(TimeSlotsActivity.this, getResources().getString(R.string.try_again), "OK", getResources().getString(R.string.went_wrong),"Failed") {
                            @Override
                            public void onButtonClick() {
                                startActivity(new Intent(TimeSlotsActivity.this, DashboardActivity.class));
                                finish();
                                overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);
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
                if(Integer.parseInt(result.getStatusCode())==200){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //Toast.makeText(AppointmentActivity.this, ""+result.getStatusMessage(), Toast.LENGTH_SHORT).show();
                            List<AppointmentAvailableSlots> appointmentAvailableSlots = new ArrayList<>();
                            List<AppointmentAvailableSlots> appointmentAvailableSlotsList = result.getAvailAppointments();
                            for (int t = 0; t < appointmentAvailableSlotsList.size(); t++) {
                                String startTime = appointmentAvailableSlotsList.get(t).getSerStartTime();
                                String endTime = appointmentAvailableSlotsList.get(t).getSerEndTime();
                                String serDuration = appointmentAvailableSlotsList.get(t).getSerDuration();
                                String numOfSlots = appointmentAvailableSlotsList.get(t).getNumOfSlots();
                                String currentSlot = appointmentAvailableSlotsList.get(t).getCurrentSlot();

                                Log.d("Slots---", "run: "+startTime+","+endTime+","+numOfSlots+","+currentSlot);
                                AppointmentAvailableSlots availableSlots = new AppointmentAvailableSlots();
                                availableSlots.setSerStartTime(startTime);
                                availableSlots.setSerEndTime(endTime);
                                availableSlots.setSerDuration(serDuration);
                                availableSlots.setNumOfSlots(numOfSlots);
                                availableSlots.setCurrentSlot(currentSlot);
                                appointmentAvailableSlots.add(availableSlots);

                            }
                            if (!appointmentAvailableSlots.isEmpty()) {
                                RecyclerAdapter_TimeSlots recyclerAdapter_timeSlots = new RecyclerAdapter_TimeSlots(TimeSlotsActivity.this, arrayList_s_time, arrayList_e_time,appointmentAvailableSlots);
                                recyclerView.setAdapter(recyclerAdapter_timeSlots);
                            }
                        }
                    });
                }
                else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new AlertDialogFailure(TimeSlotsActivity.this, getResources().getString(R.string.try_again), "OK", getResources().getString(R.string.went_wrong),"Failed") {
                                @Override
                                public void onButtonClick() {
                                    startActivity(new Intent(TimeSlotsActivity.this, DashboardActivity.class));
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
}
