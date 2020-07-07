package com.corals.appointment.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.corals.appointment.Adapters.ApptServiceSlotsAdapter;
import com.corals.appointment.Adapters.ServicesRecyclerviewAdapter;
import com.corals.appointment.Client.ApiCallback;
import com.corals.appointment.Client.ApiException;
import com.corals.appointment.Client.OkHttpApiClient;
import com.corals.appointment.Client.api.MerchantApisApi;
import com.corals.appointment.Client.model.AppointmentAvailableSlots;
import com.corals.appointment.Client.model.AppointmentEnquiryBody;
import com.corals.appointment.Client.model.AppointmentEnquiryResponse;
import com.corals.appointment.Client.model.AppointmentService;
import com.corals.appointment.Client.model.Appointments;
import com.corals.appointment.Constants.Constants;
import com.corals.appointment.Dialogs.AlertDialogFailure;
import com.corals.appointment.Dialogs.AlertDialogYesNo;
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
    ImageView imageView_back, imageView_next;
    TextView textView_date,textView_no_appts;
    Calendar c;
    String formattedDate, service, service_id;
    private IntermediateAlertDialog intermediateAlertDialog;
    private SharedPreferences sharedpreferences_sessionToken;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_appt_service);

        toolbar = findViewById(R.id.toolbar_view_service_appt);
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
        if (getIntent().getExtras() != null) {
            service_id = getIntent().getStringExtra("service_id");
            service = getIntent().getStringExtra("service");
            toolbar.setTitle(service);
        }

        textView_no_appts = findViewById(R.id.text_no_appts);
        imageView_back = findViewById(R.id.image_appt_back);
        imageView_next = findViewById(R.id.image_appt_next);
        textView_date = findViewById(R.id.text_appt_date);
        recyclerView = findViewById(R.id.recyclerview_view_appts);

        c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());
        final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        formattedDate = df.format(c.getTime());
        textView_date.setText(formattedDate);

        LinearLayoutManager lm = new LinearLayoutManager(ViewApptServiceActivity.this);
        recyclerView.setLayoutManager(lm);

        imageView_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new AlertDialogYesNo(ViewApptServiceActivity.this, "Show Appointment?", "Do you want to show previous day appointments?", "Yes", "No") {
                            @Override
                            public void onOKButtonClick() {
                                c.add(Calendar.DATE, -1);
                                formattedDate = df.format(c.getTime());
                                Log.v("PREVIOUS DATE : ", formattedDate);
                                textView_date.setText(formattedDate);
                                callAPI(formattedDate);
                            }

                            @Override
                            public void onCancelButtonClick() {

                            }
                        };
                    }
                });

            }
        });

        imageView_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new AlertDialogYesNo(ViewApptServiceActivity.this, "Show Appointment?", "Do you want to show next day appointments?", "Yes", "No") {
                            @Override
                            public void onOKButtonClick() {
                                c.add(Calendar.DATE, 1);
                                formattedDate = df.format(c.getTime());
                                Log.v("NEXT DATE : ", formattedDate);
                                textView_date.setText(formattedDate);
                                callAPI(formattedDate);
                            }

                            @Override
                            public void onCancelButtonClick() {

                            }
                        };
                    }
                });

            }
        });

        final SimpleDateFormat df_ser = new SimpleDateFormat("yyyy-MM-dd");
        String dt_appt = df_ser.format(c.getTime());

        callAPI(textView_date.getText().toString().trim());

    }

    public void callAPI(final String date) {
        AppointmentEnquiryBody enquiryBody = new AppointmentEnquiryBody();
        enquiryBody.setReqType(Constants.SERVICE_BOOKED_APPOINTMENT);
        enquiryBody.setMerId(sharedpreferences_sessionToken.getString(LoginActivity.MERID, ""));
        enquiryBody.callerType("m");
        enquiryBody.setSerId(service_id);
        enquiryBody.setDate(date); //dt_appt
        enquiryBody.setDeviceId(sharedpreferences_sessionToken.getString(LoginActivity.DEVICEID, ""));
        enquiryBody.setSessionToken(sharedpreferences_sessionToken.getString(LoginActivity.SESSIONTOKEN, ""));
        boolean isConn = ConnectivityReceiver.isConnected();
        if (isConn) {
            try {
                intermediateAlertDialog = new IntermediateAlertDialog(ViewApptServiceActivity.this);
                fetchApptService(enquiryBody, service_id, toolbar.getTitle().toString(), date); ////dt_appt
            } catch (ApiException e) {
                e.printStackTrace();
            }
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    new AlertDialogFailure(ViewApptServiceActivity.this, getResources().getString(R.string.no_internet_sub_title), "OK", getResources().getString(R.string.no_internet_title),getResources().getString(R.string.no_internet_Heading)) {
                        @Override
                        public void onButtonClick() {
                           callAPI(date);
                        }
                    };
                }
            });        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent in = new Intent(ViewApptServiceActivity.this, AppointmentActivity.class);
        startActivity(in);
        finish();
        overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);
    }

    private void fetchApptService(AppointmentEnquiryBody requestBody, final String service_id, final String service, final String date) throws ApiException {

        Log.d("fetchApptService---", "login: " + requestBody + "," + service + "," + service_id + "," + date);
        OkHttpApiClient okHttpApiClient = new OkHttpApiClient(ViewApptServiceActivity.this);
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
                        new AlertDialogFailure(ViewApptServiceActivity.this, getResources().getString(R.string.try_again), "OK", getResources().getString(R.string.went_wrong), "Failed") {
                            @Override
                            public void onButtonClick() {
                                startActivity(new Intent(ViewApptServiceActivity.this, AppointmentActivity.class));
                                finish();
                                overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);
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
                            if (!result.getAppointments().isEmpty() && result.getAppointments() != null) {
                                textView_no_appts.setVisibility(View.GONE);
                                recyclerView.setVisibility(View.VISIBLE);
                                ApptServiceSlotsAdapter apptServiceSlotsAdapter = new ApptServiceSlotsAdapter(ViewApptServiceActivity.this, service_id, service, date, result.getAppointments());
                                recyclerView.setAdapter(apptServiceSlotsAdapter);

                            } else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        textView_no_appts.setVisibility(View.VISIBLE);
                                        recyclerView.setVisibility(View.GONE);
                                    }
                                });

                            }


                        }
                    });
                } else if (Integer.parseInt(result.getStatusCode()) == 404) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textView_no_appts.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.GONE);
                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new AlertDialogFailure(ViewApptServiceActivity.this, getResources().getString(R.string.try_again), "OK", getResources().getString(R.string.went_wrong), "Failed") {
                                @Override
                                public void onButtonClick() {
                                    startActivity(new Intent(ViewApptServiceActivity.this, AppointmentActivity.class));
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
