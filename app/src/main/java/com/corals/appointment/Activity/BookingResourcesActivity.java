package com.corals.appointment.Activity;

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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.corals.appointment.Adapters.ServicesAdapter_Calender;
import com.corals.appointment.Adapters.StaffLeaveAdapter;
import com.corals.appointment.Client.ApiCallback;
import com.corals.appointment.Client.ApiException;
import com.corals.appointment.Client.OkHttpApiClient;
import com.corals.appointment.Client.api.MerchantApisApi;
import com.corals.appointment.Client.model.AppointmentEnquiryBody;
import com.corals.appointment.Client.model.AppointmentEnquiryResponse;
import com.corals.appointment.Client.model.AppointmentResources;
import com.corals.appointment.Client.model.AppointmentService;
import com.corals.appointment.Client.model.MapServiceResourceBody;
import com.corals.appointment.Constants.Constants;
import com.corals.appointment.Dialogs.AlertDialogFailure;
import com.corals.appointment.Dialogs.IntermediateAlertDialog;
import com.corals.appointment.R;
import com.corals.appointment.receiver.ConnectivityReceiver;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BookingResourcesActivity extends AppCompatActivity {

    RecyclerView recyclerView_services;
    TextView textView_no_ser, textView_appt_dt, textView_appt_ser;
    private String pageId, service_id, service, cus_id, cus, cus_email, cus_mob;
    private IntermediateAlertDialog intermediateAlertDialog;
    private SharedPreferences sharedpreferences_sessionToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_resources);

        Toolbar toolbar = findViewById(R.id.toolbar_select_res);
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

        sharedpreferences_sessionToken = getSharedPreferences(LoginActivity.MyPREFERENCES_SESSIONTOKEN, Context.MODE_PRIVATE);
        textView_appt_dt = findViewById(R.id.text_appn_dt);
        textView_appt_ser = findViewById(R.id.text_appn_ser);
        textView_no_ser = findViewById(R.id.tv_no_services);
        recyclerView_services = findViewById(R.id.recyclerview_staff);
        LinearLayoutManager lm = new LinearLayoutManager(this);
        recyclerView_services.setLayoutManager(lm);

        if (getIntent().getExtras() != null) {
            pageId = getIntent().getStringExtra("page_id");
            service_id = getIntent().getStringExtra("service_id");
            service = getIntent().getStringExtra("service");
            cus_id = getIntent().getStringExtra("cus_id");
            cus = getIntent().getStringExtra("cus");
            cus_mob = getIntent().getStringExtra("cus_mob");
            cus_email = getIntent().getStringExtra("cus_email");
            //textView_appt_dt.setText(cal_date);
            textView_appt_ser.setText(service);
            Log.d("BookResource---->", "onCreate: " + pageId + "," + service_id + "," + service + "," + cus_id + "," + cus + "," + cus_email + "," + cus_mob);
        }

        callAPI();
    }

    private void callAPI() {
        AppointmentEnquiryBody enquiryBody = new AppointmentEnquiryBody();
        enquiryBody.setReqType(Constants.RESOURCE_MERCHANT);
        enquiryBody.setMerId(sharedpreferences_sessionToken.getString(LoginActivity.MERID, ""));
        enquiryBody.callerType("m");
        enquiryBody.setSerId(service_id);
        enquiryBody.setDeviceId(sharedpreferences_sessionToken.getString(LoginActivity.DEVICEID, ""));
        enquiryBody.setSessionToken(sharedpreferences_sessionToken.getString(LoginActivity.SESSIONTOKEN, ""));
        boolean isConn = ConnectivityReceiver.isConnected();
        if (isConn) {
            try {
                intermediateAlertDialog = new IntermediateAlertDialog(BookingResourcesActivity.this);
                fetchStaff(enquiryBody);
            } catch (ApiException e) {
                e.printStackTrace();
            }
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    new AlertDialogFailure(BookingResourcesActivity.this, getResources().getString(R.string.no_internet_sub_title), "OK", getResources().getString(R.string.no_internet_title), getResources().getString(R.string.no_internet_Heading)) {
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

        if (!TextUtils.isEmpty(pageId) && pageId.equals("1")) {
            Intent i = new Intent(BookingResourcesActivity.this, AppointmentActivity.class);
            startActivity(i);
            finish();
            overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);
        } else if (!TextUtils.isEmpty(pageId) && pageId.equals("2")) {
            Intent i = new Intent(BookingResourcesActivity.this, CustomerActivity_Bottom.class);
            startActivity(i);
            finish();
            overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);
        }
        else {
            Intent i = new Intent(BookingResourcesActivity.this, DashboardActivity.class);
            startActivity(i);
            finish();
            overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);
        }

    }

    private void fetchStaff(AppointmentEnquiryBody requestBody) throws ApiException {

        Log.d("fetchService--->", "fetchService: " + requestBody);
        OkHttpApiClient okHttpApiClient = new OkHttpApiClient(BookingResourcesActivity.this);
        MerchantApisApi webMerchantApisApi = new MerchantApisApi();
        webMerchantApisApi.setApiClient(okHttpApiClient.getApiClient());

        webMerchantApisApi.merchantAppointmentEnquiryAsync(requestBody, new ApiCallback<AppointmentEnquiryResponse>() {
            @Override
            public void onFailure(ApiException e, int statusCode, Map<String, List<String>> responseHeaders) {
                Log.d("fetchStaff--->", "onFailure-" + e.getMessage());
                if (intermediateAlertDialog != null) {
                    intermediateAlertDialog.dismissAlertDialog();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new AlertDialogFailure(BookingResourcesActivity.this, getResources().getString(R.string.try_again), "OK", getResources().getString(R.string.went_wrong), "Failed") {
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

                Log.d("fetchStaff--->", "onSuccess-" + statusCode + "," + result + "," + result.getResources());
                if (intermediateAlertDialog != null) {
                    intermediateAlertDialog.dismissAlertDialog();
                }
                if (Integer.parseInt(result.getStatusCode()) == 200) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (!result.getResources().isEmpty() && result.getResources() != null) {
                                StaffLeaveAdapter staffLeaveAdapter = new StaffLeaveAdapter(BookingResourcesActivity.this, result.getResources(), pageId, service_id, service, cus_id, cus, cus_email, cus_mob);
                                recyclerView_services.setAdapter(staffLeaveAdapter);
                            } else {
                                textView_no_ser.setText("No staffs created");
                                textView_no_ser.setVisibility(View.VISIBLE);
                                recyclerView_services.setVisibility(View.GONE);
                            }

                        }
                    });
                } else if (Integer.parseInt(result.getStatusCode()) == 400) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new AlertDialogFailure(BookingResourcesActivity.this, "Staff not found for this service", "OK", "", "Failed") {
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
                            new AlertDialogFailure(BookingResourcesActivity.this, getResources().getString(R.string.try_again), "OK", getResources().getString(R.string.went_wrong), "Failed") {
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
        if(!TextUtils.isEmpty(pageId) && pageId.equals("1")) {
            startActivity(new Intent(BookingResourcesActivity.this, AppointmentActivity.class));
            finish();
            overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);
        }
        else if(!TextUtils.isEmpty(pageId) && pageId.equals("2")) {
            startActivity(new Intent(BookingResourcesActivity.this, CustomerActivity_Bottom.class));
            finish();
            overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);
        }
        else {
            startActivity(new Intent(BookingResourcesActivity.this, DashboardActivity.class));
            finish();
            overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);
        }
    }
}
