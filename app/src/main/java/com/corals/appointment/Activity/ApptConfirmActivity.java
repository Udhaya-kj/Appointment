package com.corals.appointment.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.corals.appointment.Client.ApiCallback;
import com.corals.appointment.Client.ApiException;
import com.corals.appointment.Client.OkHttpApiClient;
import com.corals.appointment.Client.api.MerchantApisApi;
import com.corals.appointment.Client.model.ApptTransactionBody;
import com.corals.appointment.Client.model.ApptTransactionResponse;
import com.corals.appointment.Dialogs.AlertDialogFailure;
import com.corals.appointment.Dialogs.AlertDialogYesNo;
import com.corals.appointment.Dialogs.IntermediateAlertDialog;
import com.corals.appointment.R;
import com.corals.appointment.receiver.ConnectivityReceiver;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ApptConfirmActivity extends AppCompatActivity {

    Button button_done;
    TextView tv_n1, tv_m1, tv_ser_name, tv_time, tv_date,tv_res;
    private IntermediateAlertDialog intermediateAlertDialog;
    private SharedPreferences sharedpreferences_sessionToken;
    String name, mob, cus_id, service_id, date, slot_no, start_time, end_time, res_id, res, service,cus_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appt_confirm);

        Toolbar toolbar = findViewById(R.id.toolbar_appt_confirm);
        setSupportActionBar(toolbar);
   /*     getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_left);*/
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        tv_n1 = findViewById(R.id.tv_cus_name);
        tv_m1 = findViewById(R.id.tv_cus_mob);
        tv_ser_name = findViewById(R.id.text_ser_name);
        tv_time = findViewById(R.id.text_appt_time);
        tv_date = findViewById(R.id.text_appt_date);
        tv_res = findViewById(R.id.text_res_name);

        if (getIntent().getExtras() != null) {
            name = getIntent().getStringExtra("cus_name");
            mob = getIntent().getStringExtra("cus_mob");
            cus_id = getIntent().getStringExtra("cus_id");
            service_id = getIntent().getStringExtra("service_id");
            date = getIntent().getStringExtra("date");
            slot_no = getIntent().getStringExtra("slot_no");
            start_time = getIntent().getStringExtra("start_time");
            end_time = getIntent().getStringExtra("end_time");
            res_id = getIntent().getStringExtra("res_id");
            res = getIntent().getStringExtra("res");
            service = getIntent().getStringExtra("service");
            cus_email = getIntent().getStringExtra("cus_email");
            tv_n1.setText(name);
            tv_m1.setText(mob);
            tv_ser_name.setText(service);
            tv_time.setText(start_time+"-"+end_time);
            tv_date.setText(date);
            tv_res.setText("w/ "+res);
            Log.d("Appn_Date---->", "onCreate: " + name + "," + mob + "," + cus_id + "," + service_id + "," + date + "," + slot_no + "," + start_time + "," + end_time + "," + res_id + "," + res + "," + service+ "," + cus_email);
        }
        sharedpreferences_sessionToken = getSharedPreferences(LoginActivity.MyPREFERENCES_SESSIONTOKEN, Context.MODE_PRIVATE);
        button_done = findViewById(R.id.button_appt_done);
        button_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new AlertDialogYesNo(ApptConfirmActivity.this, "Book Appointment?", "Are you sure, You want to book appointment?", "Yes", "No") {
                            @Override
                            public void onOKButtonClick() {

                                try {
                                    Log.d("Token--->", "token: " + sharedpreferences_sessionToken.getString(LoginActivity.SESSIONTOKEN, ""));
                                    boolean isConn = ConnectivityReceiver.isConnected();
                                    if (isConn) {
                                        ApptTransactionBody transactionBody = new ApptTransactionBody();
                                        transactionBody.setReqType("T-A.C");
                                        transactionBody.setMerId(sharedpreferences_sessionToken.getString(LoginActivity.MERID, ""));
                                        transactionBody.setSerId(service_id);
                                        transactionBody.setDate(date); //date
                                        transactionBody.setSlotNo(slot_no);
                                        transactionBody.setStartTime(start_time);
                                        transactionBody.setEndTime(end_time);
                                        transactionBody.setCustId(cus_id);
                                        transactionBody.setResId(res_id);
                                        transactionBody.setDeviceId(sharedpreferences_sessionToken.getString(LoginActivity.DEVICEID, ""));
                                        transactionBody.setSessionToken(sharedpreferences_sessionToken.getString(LoginActivity.SESSIONTOKEN, ""));

                                        intermediateAlertDialog = new IntermediateAlertDialog(ApptConfirmActivity.this);
                                        bookAppointment(transactionBody);
                                    } else {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                new AlertDialogFailure(ApptConfirmActivity.this, getResources().getString(R.string.no_internet_sub_title), "OK", getResources().getString(R.string.no_internet_title),getResources().getString(R.string.no_internet_Heading)) {
                                                    @Override
                                                    public void onButtonClick() {

                                                    }
                                                };
                                            }
                                        });                                    }
                                } catch (ApiException e) {
                                    e.printStackTrace();
                                }


                            }

                            @Override
                            public void onCancelButtonClick() {

                            }
                        };
                    }
                });

            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new AlertDialogYesNo(ApptConfirmActivity.this, "Cancel Book Appointment?", "Are you sure, You want to cancel this book appointment?", "Yes", "No") {
                    @Override
                    public void onOKButtonClick() {
                        Intent in = new Intent(ApptConfirmActivity.this, DashboardActivity.class);
                        startActivity(in);
                        finish();
                    }

                    @Override
                    public void onCancelButtonClick() {

                    }
                };
            }
        });

    }

    private void bookAppointment(ApptTransactionBody requestBody) throws ApiException {
        Log.d("createStaff---", "createStaff: " + requestBody);

        OkHttpApiClient okHttpApiClient = new OkHttpApiClient(ApptConfirmActivity.this);
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
                        new AlertDialogFailure(ApptConfirmActivity.this, getResources().getString(R.string.try_again), "OK", getResources().getString(R.string.went_wrong), "Failed") {
                            @Override
                            public void onButtonClick() {
                                startActivity(new Intent(ApptConfirmActivity.this, AppointmentActivity.class));
                                finish();
                                overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);
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
                            new AlertDialogFailure(ApptConfirmActivity.this, "Appointment booked successfully!", "OK", "", "Success") {
                                @Override
                                public void onButtonClick() {
                                    startActivity(new Intent(ApptConfirmActivity.this, ApptSuccessActivity.class).putExtra("cus_email",cus_email));
                                    finish();
                                    overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);
                                }
                            };
                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new AlertDialogFailure(ApptConfirmActivity.this, getResources().getString(R.string.try_again), "OK", getResources().getString(R.string.went_wrong), "Failed") {
                                @Override
                                public void onButtonClick() {
                                    startActivity(new Intent(ApptConfirmActivity.this, AppointmentActivity.class));
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