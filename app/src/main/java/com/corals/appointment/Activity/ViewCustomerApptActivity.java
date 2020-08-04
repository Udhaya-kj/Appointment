package com.corals.appointment.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.corals.appointment.Adapters.ViewCustomersApptRecyclerAdapter;
import com.corals.appointment.Client.ApiCallback;
import com.corals.appointment.Client.ApiException;
import com.corals.appointment.Client.OkHttpApiClient;
import com.corals.appointment.Client.api.MerchantApisApi;
import com.corals.appointment.Client.model.AppointmentEnquiryBody;
import com.corals.appointment.Client.model.AppointmentEnquiryResponse;
import com.corals.appointment.Constants.Constants;
import com.corals.appointment.Dialogs.AlertDialogFailure;
import com.corals.appointment.Dialogs.AlertDialogYesNo;
import com.corals.appointment.Dialogs.IntermediateAlertDialog;
import com.corals.appointment.R;
import com.corals.appointment.Utils.CAllLoginAPI;
import com.corals.appointment.receiver.ConnectivityReceiver;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class ViewCustomerApptActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ImageView imageView_back, imageView_next;
    TextView textView_date, textView_no_appts;
    Calendar c;
    String formattedDate, cus_name, cus_mob, cus_id;
    private SharedPreferences sharedpreferences_sessionToken;
    private IntermediateAlertDialog intermediateAlertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_customer_appt);

        Toolbar toolbar = findViewById(R.id.toolbar_view_service_appt);
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

        if (getIntent().getExtras() != null) {
            cus_id = getIntent().getStringExtra("cus_id");
            cus_name = getIntent().getStringExtra("cus_name");
            cus_mob = getIntent().getStringExtra("cus_mob");
            toolbar.setTitle(cus_name);
        }
        sharedpreferences_sessionToken = getSharedPreferences(LoginActivity.MyPREFERENCES_SESSIONTOKEN, Context.MODE_PRIVATE);
        textView_no_appts = findViewById(R.id.text_no_appts);
        imageView_back = findViewById(R.id.image_appt_back);
        imageView_next = findViewById(R.id.image_appt_next);
        textView_date = findViewById(R.id.text_appt_date);
        recyclerView = findViewById(R.id.recyclerview_view_appts);
        LinearLayoutManager lm = new LinearLayoutManager(ViewCustomerApptActivity.this);
        recyclerView.setLayoutManager(lm);

        c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());
        final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        formattedDate = df.format(c.getTime());
        textView_date.setText(formattedDate);

        imageView_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new AlertDialogYesNo(ViewCustomerApptActivity.this, "Show Appointment?", "Do you want to show previous day appointments?", "Yes", "No") {
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
                        new AlertDialogYesNo(ViewCustomerApptActivity.this, "Show Appointment?", "Do you want to show next day appointments?", "Yes", "No") {
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

        callAPI(textView_date.getText().toString().trim());

    }

    public void callAPI(final String date) {
        AppointmentEnquiryBody enquiryBody = new AppointmentEnquiryBody();
        enquiryBody.setReqType(Constants.CUSTOMER_BOOKED_APPOINTMENT);
        enquiryBody.setMerId(sharedpreferences_sessionToken.getString(LoginActivity.MERID, ""));
        enquiryBody.setOutletId(sharedpreferences_sessionToken.getString(LoginActivity.OUTLETID, ""));
        enquiryBody.callerType("m");
        enquiryBody.setCustId(cus_id);
        enquiryBody.setDate(date);
        enquiryBody.setDeviceId(sharedpreferences_sessionToken.getString(LoginActivity.DEVICEID, ""));
        enquiryBody.setSessionToken(sharedpreferences_sessionToken.getString(LoginActivity.SESSIONTOKEN, ""));
        boolean isConn = ConnectivityReceiver.isConnected();
        if (isConn) {
            try {
                intermediateAlertDialog = new IntermediateAlertDialog(ViewCustomerApptActivity.this);
                fetchAppointments(enquiryBody);
            } catch (ApiException e) {
                e.printStackTrace();
            }
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    new AlertDialogFailure(ViewCustomerApptActivity.this, getResources().getString(R.string.no_internet_sub_title), "OK", getResources().getString(R.string.no_internet_title), "Failed") {
                        @Override
                        public void onButtonClick() {
                            callAPI(date);
                        }
                    };
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
     /*   Intent in = new Intent(ViewCustomerApptActivity.this, CustomerActivity_Bottom.class);
        startActivity(in);
        finish();
        overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);*/
    }

    private void fetchAppointments(AppointmentEnquiryBody requestBody) throws ApiException {
        Log.d("login---", "login: " + requestBody);
        OkHttpApiClient okHttpApiClient = new OkHttpApiClient(ViewCustomerApptActivity.this);
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
                        new AlertDialogFailure(ViewCustomerApptActivity.this, getResources().getString(R.string.try_again), "OK", getResources().getString(R.string.server_error), "Failed") {
                            @Override
                            public void onButtonClick() {
                               /* startActivity(new Intent(ViewCustomerApptActivity.this, CustomerActivity_Bottom.class));
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
                Log.d("fetchService--->", "onSuccess-" + statusCode + "," + result);
                if (Integer.parseInt(result.getStatusCode()) == 200) {
                    if (intermediateAlertDialog != null) {
                        intermediateAlertDialog.dismissAlertDialog();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            if (!result.getAppointments().isEmpty()) {
                                textView_no_appts.setVisibility(View.GONE);
                                recyclerView.setVisibility(View.VISIBLE);
                                ViewCustomersApptRecyclerAdapter apptServiceSlotsAdapter = new ViewCustomersApptRecyclerAdapter(ViewCustomerApptActivity.this, result.getAppointments());
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
                    if (intermediateAlertDialog != null) {
                        intermediateAlertDialog.dismissAlertDialog();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textView_no_appts.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.GONE);
                        }
                    });

                }
                else if (Integer.parseInt(result.getStatusCode()) == 401) {
                    if (intermediateAlertDialog != null) {
                        intermediateAlertDialog.dismissAlertDialog();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new CAllLoginAPI() {
                                @Override
                                public void onButtonClick() {

                                    callAPI(textView_date.getText().toString().trim());
                                }
                            }.callLoginAPI(ViewCustomerApptActivity.this);
                        }
                    });

                }
                else {
                    if (intermediateAlertDialog != null) {
                        intermediateAlertDialog.dismissAlertDialog();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new AlertDialogFailure(ViewCustomerApptActivity.this, getResources().getString(R.string.try_again), "OK", getResources().getString(R.string.went_wrong), "Failed") {
                                @Override
                                public void onButtonClick() {
                                   /* startActivity(new Intent(ViewCustomerApptActivity.this, CustomerActivity_Bottom.class));
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

}
