package com.corals.appointment.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.corals.appointment.Adapters.ApptServiceSlotsAdapter;
import com.corals.appointment.Client.ApiCallback;
import com.corals.appointment.Client.ApiException;
import com.corals.appointment.Client.OkHttpApiClient;
import com.corals.appointment.Client.api.MerchantApisApi;
import com.corals.appointment.Client.model.AppointmentEnquiryBody;
import com.corals.appointment.Client.model.AppointmentEnquiryResponse;
import com.corals.appointment.Dialogs.IntermediateAlertDialog;
import com.corals.appointment.R;
import com.corals.appointment.receiver.ConnectivityReceiver;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class ViewApptServiceActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<String> arrayList_slot_time, arrayList_slot_cus_name, arrayList_slot_cus_mob, arrayList_available;
    ImageView imageView_back, imageView_next;
    TextView textView_date;
    Calendar c;
    String formattedDate,service;
    private IntermediateAlertDialog intermediateAlertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_appt_service);

        Toolbar toolbar = findViewById(R.id.toolbar_view_service_appt);
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
            service = getIntent().getStringExtra("service");
            toolbar.setTitle(service);
        }
        //Appt Slots
        arrayList_slot_time = new ArrayList<>();
        arrayList_slot_cus_name = new ArrayList<>();
        arrayList_slot_cus_mob = new ArrayList<>();
        arrayList_available = new ArrayList<>();

        arrayList_slot_time.add("08:00 am - 08:30 am");
        arrayList_slot_time.add("12:00 pm - 12:30 pm");
        arrayList_slot_time.add("02:30 pm - 02:45 pm");
        arrayList_slot_time.add("06:00 pm - 06:30 pm");
        arrayList_slot_time.add("06:30 pm - 07:00 pm");
        arrayList_slot_time.add("07:00 pm - 07:30 pm");
        arrayList_slot_time.add("07:30 pm - 08:00 pm");
        arrayList_slot_time.add("08:00 pm - 08:30 pm");

        arrayList_slot_cus_name.add("John");
        arrayList_slot_cus_name.add("Madhan");
        arrayList_slot_cus_name.add("Irfan");


        arrayList_slot_cus_mob.add("9898989898");
        arrayList_slot_cus_mob.add("8787878787");
        arrayList_slot_cus_mob.add("9090909090");


        arrayList_available.add("1");
        arrayList_available.add("0");
        arrayList_available.add("0");
        arrayList_available.add("1");
        arrayList_available.add("0");
        arrayList_available.add("0");
        arrayList_available.add("1");
        arrayList_available.add("1");

        imageView_back = findViewById(R.id.image_appt_back);
        imageView_next = findViewById(R.id.image_appt_next);
        textView_date = findViewById(R.id.text_appt_date);
        recyclerView = findViewById(R.id.recyclerview_view_appts);

        c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());
        final SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        formattedDate = df.format(c.getTime());
        textView_date.setText(formattedDate);

        LinearLayoutManager lm = new LinearLayoutManager(ViewApptServiceActivity.this);
        recyclerView.setLayoutManager(lm);
        ApptServiceSlotsAdapter apptServiceSlotsAdapter = new ApptServiceSlotsAdapter(ViewApptServiceActivity.this, arrayList_slot_time, arrayList_slot_cus_name, arrayList_slot_cus_mob, arrayList_available);
        recyclerView.setAdapter(apptServiceSlotsAdapter);

        imageView_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ViewApptServiceActivity.this);
                alertDialogBuilder.setMessage("Do you want to show previous day appointments?");
                alertDialogBuilder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                arg0.dismiss();
                                c.add(Calendar.DATE, -1);
                                formattedDate = df.format(c.getTime());
                                Log.v("PREVIOUS DATE : ", formattedDate);
                                textView_date.setText(formattedDate);
                            }
                        });

                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

        imageView_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ViewApptServiceActivity.this);
                alertDialogBuilder.setMessage("Do you want to show next day appointments?");
                alertDialogBuilder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                arg0.dismiss();
                                c.add(Calendar.DATE, 1);
                                formattedDate = df.format(c.getTime());
                                Log.v("NEXT DATE : ", formattedDate);
                                textView_date.setText(formattedDate);
                            }
                        });

                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();


            }
        });

       /*       AppointmentEnquiryBody enquiryBody = new AppointmentEnquiryBody();
        boolean isConn = ConnectivityReceiver.isConnected();
        if (isConn) {
            try {
                fetchApptService(enquiryBody);
            } catch (ApiException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(ViewApptServiceActivity.this, "No internet connection!", Toast.LENGTH_SHORT).show();
        }*/



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent in = new Intent(ViewApptServiceActivity.this, AppointmentActivity.class);
        startActivity(in);
        finish();
        overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);
    }

    private void fetchApptService(AppointmentEnquiryBody requestBody) throws ApiException {

        intermediateAlertDialog = new IntermediateAlertDialog(ViewApptServiceActivity.this);

        Log.d("login---", "login: " + requestBody);
        OkHttpApiClient okHttpApiClient = new OkHttpApiClient(ViewApptServiceActivity.this);
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
