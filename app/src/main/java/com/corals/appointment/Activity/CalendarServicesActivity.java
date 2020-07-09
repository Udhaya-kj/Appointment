package com.corals.appointment.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.corals.appointment.Adapters.ServicesAdapter_Calender;
import com.corals.appointment.Adapters.ServicesRecyclerviewAdapter;
import com.corals.appointment.Client.ApiCallback;
import com.corals.appointment.Client.ApiException;
import com.corals.appointment.Client.OkHttpApiClient;
import com.corals.appointment.Client.api.MerchantApisApi;
import com.corals.appointment.Client.model.AppointmentEnquiryBody;
import com.corals.appointment.Client.model.AppointmentEnquiryResponse;
import com.corals.appointment.Client.model.AppointmentService;
import com.corals.appointment.Constants.Constants;
import com.corals.appointment.Dialogs.AlertDialogFailure;
import com.corals.appointment.Dialogs.IntermediateAlertDialog;
import com.corals.appointment.R;
import com.corals.appointment.receiver.ConnectivityReceiver;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CalendarServicesActivity extends AppCompatActivity {

    ListView recyclerView_services;
    TextView textView_no_ser, textView_appt_dt;
    public String cus_id,cus,cus_email,cus_mob;
    private IntermediateAlertDialog intermediateAlertDialog;
    private SharedPreferences sharedpreferences_sessionToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_services);

        Toolbar toolbar = findViewById(R.id.toolbar_select_ser);
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
        textView_appt_dt = findViewById(R.id.text_appn_dt);
        textView_no_ser = findViewById(R.id.tv_no_services);
        recyclerView_services = findViewById(R.id.recyclerview_services);

        if (getIntent().getExtras() != null) {
            cus_id = getIntent().getStringExtra("cus_id");
            cus = getIntent().getStringExtra("cus");
            cus_email = getIntent().getStringExtra("cus_email");
            cus_mob = getIntent().getStringExtra("cus_mob");
            Log.d("CalServices---->", "onCreate: " + cus_id + "," + cus + "," + cus_email+ "," + cus_mob);

        }

        AppointmentEnquiryBody enquiryBody = new AppointmentEnquiryBody();
        enquiryBody.setReqType(Constants.SERVICES_LIST_API);
        enquiryBody.setMerId(sharedpreferences_sessionToken.getString(LoginActivity.MERID, ""));
        enquiryBody.callerType("m");
        enquiryBody.setDeviceId(sharedpreferences_sessionToken.getString(LoginActivity.DEVICEID, ""));
        enquiryBody.setSessionToken(sharedpreferences_sessionToken.getString(LoginActivity.SESSIONTOKEN, ""));
        boolean isConn = ConnectivityReceiver.isConnected();
        if (isConn) {
            try {
                intermediateAlertDialog = new IntermediateAlertDialog(CalendarServicesActivity.this);
                fetchServices(enquiryBody);
            } catch (ApiException e) {
                e.printStackTrace();
            }
        } else {

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    new AlertDialogFailure(CalendarServicesActivity.this, getResources().getString(R.string.no_internet_sub_title), "OK", getResources().getString(R.string.no_internet_title), getResources().getString(R.string.no_internet_Heading)) {
                        @Override
                        public void onButtonClick() {
                            Intent i = new Intent(CalendarServicesActivity.this, AppointmentActivity.class);
                            startActivity(i);
                            finish();
                            overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);
                        }
                    };
                }
            });

        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(CalendarServicesActivity.this, CustomerActivity_Bottom.class);
        startActivity(i);
        finish();
        overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);

   /*     if (!TextUtils.isEmpty(pageId) && pageId.equals("1")) {
            Intent i = new Intent(CalendarServicesActivity.this, AppointmentActivity.class);
            startActivity(i);
            finish();
            overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);
        } else if (!TextUtils.isEmpty(pageId) && pageId.equals("2")) {
            Intent i = new Intent(CalendarServicesActivity.this, CalendarViewActivity.class);
            startActivity(i);
            finish();
            overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);
        }*/
    }

    private void fetchServices(AppointmentEnquiryBody requestBody) throws ApiException {
        Log.d("login---", "login: " + requestBody);
        OkHttpApiClient okHttpApiClient = new OkHttpApiClient(CalendarServicesActivity.this);
        MerchantApisApi webMerchantApisApi = new MerchantApisApi();
        webMerchantApisApi.setApiClient(okHttpApiClient.getApiClient());

        webMerchantApisApi.merchantAppointmentEnquiryAsync(requestBody, new ApiCallback<AppointmentEnquiryResponse>() {
            @Override
            public void onFailure(ApiException e, int statusCode, Map<String, List<String>> responseHeaders) {
                Log.d("fetchService--->", "onFailure-" + e.getMessage());
                if (intermediateAlertDialog != null) {
                    intermediateAlertDialog.dismissAlertDialog();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new AlertDialogFailure(CalendarServicesActivity.this, getResources().getString(R.string.try_again), "OK", getResources().getString(R.string.went_wrong), "Failed") {
                            @Override
                            public void onButtonClick() {
                                startActivity(new Intent(CalendarServicesActivity.this, DashboardActivity.class));
                                finish();
                                overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);
                            }
                        };
                    }
                });
            }

            @Override
            public void onSuccess(final AppointmentEnquiryResponse result, int statusCode, Map<String, List<String>> responseHeaders) {

                Log.d("fetchService--->", "onSuccess-" + statusCode + "," + result);
                if (intermediateAlertDialog != null) {
                    intermediateAlertDialog.dismissAlertDialog();
                }
                if (Integer.parseInt(result.getStatusCode()) == 200) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (!result.getServices().isEmpty() && result.getServices() != null) {
                                textView_no_ser.setVisibility(View.GONE);
                                recyclerView_services.setVisibility(View.VISIBLE);
                                ServicesAdapter_Calender servicesAdapter_calender = new ServicesAdapter_Calender(CalendarServicesActivity.this, result.getServices(),cus_id,cus,cus_email,cus_mob);
                                recyclerView_services.setAdapter(servicesAdapter_calender);

                            } else {
                                textView_no_ser.setVisibility(View.VISIBLE);
                                recyclerView_services.setVisibility(View.GONE);
                            }


                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new AlertDialogFailure(CalendarServicesActivity.this, getResources().getString(R.string.try_again), "OK", getResources().getString(R.string.went_wrong), "Failed") {
                                @Override
                                public void onButtonClick() {
                                    startActivity(new Intent(CalendarServicesActivity.this, DashboardActivity.class));
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (intermediateAlertDialog != null) {
            intermediateAlertDialog.dismissAlertDialog();
            intermediateAlertDialog = null;
        }
    }


}
