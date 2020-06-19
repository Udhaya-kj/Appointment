package com.corals.appointment.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import com.corals.appointment.Client.ApiCallback;
import com.corals.appointment.Client.ApiException;
import com.corals.appointment.Client.OkHttpApiClient;
import com.corals.appointment.Client.api.MerchantApisApi;
import com.corals.appointment.Client.model.AppointmentEnquiryBody;
import com.corals.appointment.Client.model.AppointmentEnquiryResponse;
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
        RecyclerAdapter_TimeSlots recyclerAdapter_timeSlots = new RecyclerAdapter_TimeSlots(TimeSlotsActivity.this, arrayList_s_time, arrayList_e_time);
        recyclerView.setAdapter(recyclerAdapter_timeSlots);

        if (getIntent().getExtras() != null) {
            String res = getIntent().getStringExtra("service");
            String dt = getIntent().getStringExtra("date");
            Log.d("Appn_Date---->", "onCreate: " + dt + "," + res);
            textView_appn_dt.setText(dt);
            textView_res.setText(res);

        }

        //printTimeSlots(LocalTime.parse("08:00"), LocalTime.parse("17:00"), 30);


   /*     AppointmentEnquiryBody enquiryBody = new AppointmentEnquiryBody();
        enquiryBody.setReqType("");
        boolean isConn = ConnectivityReceiver.isConnected();
        if (isConn) {
            try {
                fetchApptSlots(enquiryBody);
            } catch (ApiException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(TimeSlotsActivity.this, "No internet connection!", Toast.LENGTH_SHORT).show();
        }
*/
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

    private void fetchApptSlots(AppointmentEnquiryBody requestBody) throws ApiException {

        intermediateAlertDialog = new IntermediateAlertDialog(TimeSlotsActivity.this);
        Log.d("login---", "login: " + requestBody);
        OkHttpApiClient okHttpApiClient = new OkHttpApiClient(TimeSlotsActivity.this);
        MerchantApisApi webMerchantApisApi = new MerchantApisApi();
        webMerchantApisApi.setApiClient(okHttpApiClient.getApiClient());

        webMerchantApisApi.merchantAppointmentEnquiryAsync(requestBody, new ApiCallback<AppointmentEnquiryResponse>() {
            @Override
            public void onFailure(ApiException e, int statusCode, Map<String, List<String>> responseHeaders) {
                Log.d("fetchService--->", "onFailure-" + e.getMessage());
                if (intermediateAlertDialog != null) {
                    intermediateAlertDialog.dismissAlertDialog();
                }
            }

            @Override
            public void onSuccess(AppointmentEnquiryResponse result, int statusCode, Map<String, List<String>> responseHeaders) {

                Log.d("fetchService--->", "onSuccess-" + statusCode + "," + result.getStatusMessage());
                if (intermediateAlertDialog != null) {
                    intermediateAlertDialog.dismissAlertDialog();
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
