package com.corals.appointment.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.corals.appointment.Adapters.CustomStaffSpinnerAdapter;
import com.corals.appointment.Client.ApiCallback;
import com.corals.appointment.Client.ApiException;
import com.corals.appointment.Client.OkHttpApiClient;
import com.corals.appointment.Client.api.MerchantApisApi;
import com.corals.appointment.Client.model.AppointmentEnquiryBody;
import com.corals.appointment.Client.model.AppointmentEnquiryResponse;
import com.corals.appointment.Client.model.ApptTransactionBody;
import com.corals.appointment.Client.model.ApptTransactionResponse;
import com.corals.appointment.Constants.Constants;
import com.corals.appointment.Dialogs.AlertDialogFailure;
import com.corals.appointment.Dialogs.AlertDialogYesNo;
import com.corals.appointment.Dialogs.IntermediateAlertDialog;
import com.corals.appointment.R;
import com.corals.appointment.Utils.CAllLoginAPI;
import com.corals.appointment.receiver.ConnectivityReceiver;

import java.util.List;
import java.util.Map;

public class ApptSlotDetailsActivity extends AppCompatActivity {

    TextView textView_name, textView_mob, textView_ser_name, textView_staff, textView_booking_date, textView_booking_time;
    LinearLayout layout_call, layout_remainder, layout_cancel_appt, layout_change_appt;
    String name, mob, time, date, cus_id, service_id, service, res_name, appt_id;
    private IntermediateAlertDialog intermediateAlertDialog;
    private SharedPreferences sharedpreferences_sessionToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appt_slot_details);

        Toolbar toolbar = findViewById(R.id.toolbar_appt_details);
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


        textView_name = findViewById(R.id.tv_appt_det_name);
        textView_mob = findViewById(R.id.tv_appt_det_mob);
        textView_ser_name = findViewById(R.id.tv_appt_det_ser_name);
        textView_staff = findViewById(R.id.tv_appt_det_staff_name);
        textView_booking_date = findViewById(R.id.tv_appt_det_booking_date);
        textView_booking_time = findViewById(R.id.tv_appt_det_booking_time);

        layout_call = findViewById(R.id.layout_call);
        layout_remainder = findViewById(R.id.layout_sent_reminder);
        layout_cancel_appt = findViewById(R.id.layout_cancel_appt);
        layout_change_appt = findViewById(R.id.layout_change_appt);

        if (getIntent().getExtras() != null) {
            name = getIntent().getStringExtra("name");
            mob = getIntent().getStringExtra("mob");
            time = getIntent().getStringExtra("time");
            date = getIntent().getStringExtra("date");
            cus_id = getIntent().getStringExtra("cus_id");
            service_id = getIntent().getStringExtra("service_id");
            service = getIntent().getStringExtra("service");
            res_name = getIntent().getStringExtra("res_name");
            appt_id = getIntent().getStringExtra("appt_id");
            textView_name.setText(name);
            textView_mob.setText(mob);
            textView_ser_name.setText(service);
            textView_booking_time.setText(time);
            textView_booking_date.setText(date);
            textView_staff.setText(res_name);
        }


        layout_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri number = Uri.parse("tel:" + mob);
                Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
                startActivity(callIntent);
            }
        });

        layout_remainder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ApptSlotDetailsActivity.this, "Sent Reminder...", Toast.LENGTH_SHORT).show();
            }
        });


        layout_cancel_appt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                callAPI();

            }
        });

        layout_change_appt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isConn = ConnectivityReceiver.isConnected();
                if (isConn) {
                    Intent in = new Intent(ApptSlotDetailsActivity.this, ChangeApptActivity.class);
                    in.putExtra("service", textView_ser_name.getText().toString().trim());
                    in.putExtra("service_id", service_id);
                    in.putExtra("staff", textView_staff.getText().toString().trim());
                    in.putExtra("cus_id", cus_id);
                    in.putExtra("appt_date", textView_booking_date.getText().toString().trim());
                    in.putExtra("appt_time", textView_booking_time.getText().toString().trim());
                    in.putExtra("appt_id", appt_id);
                    startActivity(in);
                    // finish();
                    overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);
                } else {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new AlertDialogFailure(ApptSlotDetailsActivity.this, getResources().getString(R.string.no_internet_sub_title), "OK", getResources().getString(R.string.no_internet_title), getResources().getString(R.string.no_internet_Heading)) {
                                @Override
                                public void onButtonClick() {

                                }
                            };
                        }
                    });
                }
            }
        });


    }

    private void callAPI() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new AlertDialogYesNo(ApptSlotDetailsActivity.this, "Cancel Appointment?", "Are you sure, You want to cancel this appointment?", "Yes", "No") {
                    @Override
                    public void onOKButtonClick() {
                        ApptTransactionBody transactionBody = new ApptTransactionBody();
                        transactionBody.setReqType(Constants.CANCEL_APPOINTMENT);
                        transactionBody.setApptId(appt_id);
                        transactionBody.setMerId(sharedpreferences_sessionToken.getString(LoginActivity.MERID, ""));
                        transactionBody.setDeviceId(sharedpreferences_sessionToken.getString(LoginActivity.DEVICEID, ""));
                        transactionBody.setSessionToken(sharedpreferences_sessionToken.getString(LoginActivity.SESSIONTOKEN, ""));
                        try {
                            Log.d("Token--->", "token: " + sharedpreferences_sessionToken.getString(LoginActivity.SESSIONTOKEN, ""));
                            boolean isConn = ConnectivityReceiver.isConnected();
                            if (isConn) {
                                intermediateAlertDialog = new IntermediateAlertDialog(ApptSlotDetailsActivity.this);
                                cancelAppointment(transactionBody);
                            } else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        new AlertDialogFailure(ApptSlotDetailsActivity.this, getResources().getString(R.string.no_internet_sub_title), "OK", getResources().getString(R.string.no_internet_title), getResources().getString(R.string.no_internet_Heading)) {
                                            @Override
                                            public void onButtonClick() {
                                            }
                                        };
                                    }
                                });
                            }
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();

      /*  Intent i = new Intent(ApptSlotDetailsActivity.this, AppointmentActivity.class);
        startActivity(i);
        finish();
        overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);*/

    }

    private void cancelAppointment(ApptTransactionBody requestBody) throws ApiException {
        Log.d("changeApptCustomer---", "createStaff: " + requestBody);
        intermediateAlertDialog = new IntermediateAlertDialog(ApptSlotDetailsActivity.this);
        OkHttpApiClient okHttpApiClient = new OkHttpApiClient(ApptSlotDetailsActivity.this);
        MerchantApisApi webMerchantApisApi = new MerchantApisApi();
        webMerchantApisApi.setApiClient(okHttpApiClient.getApiClient());

        webMerchantApisApi.merchantAppointmentTransactionAsync(requestBody, new ApiCallback<ApptTransactionResponse>() {
            @Override
            public void onFailure(ApiException e, int statusCode, Map<String, List<String>> responseHeaders) {
                Log.d("changeApptCustomer--->", "onFailure-" + e.getMessage());
                if (intermediateAlertDialog != null) {
                    intermediateAlertDialog.dismissAlertDialog();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new AlertDialogFailure(ApptSlotDetailsActivity.this, getResources().getString(R.string.try_again), "OK", getResources().getString(R.string.server_error), "Failed") {
                            @Override
                            public void onButtonClick() {
                           /*     startActivity(new Intent(ApptSlotDetailsActivity.this, AppointmentActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                                finish();
                                overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);*/
                                onBackPressed();
                            }
                        };
                    }
                });
            }

            @Override
            public void onSuccess(ApptTransactionResponse result, int statusCode, Map<String, List<String>> responseHeaders) {
                Log.d("changeApptCustomer--->", "onSuccess-" + result.getStatusCode() + "," + result.getStatusMessage());

                if (Integer.parseInt(result.getStatusCode()) == 200) {
                    if (intermediateAlertDialog != null) {
                        intermediateAlertDialog.dismissAlertDialog();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new AlertDialogFailure(ApptSlotDetailsActivity.this, "Appointment cancelled successfully!", "OK", "", "Success") {
                                @Override
                                public void onButtonClick() {
                                    startActivity(new Intent(ApptSlotDetailsActivity.this, AppointmentActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                                    finish();
                                    overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);
                                }
                            };
                        }
                    });
                }
                if (Integer.parseInt(result.getStatusCode()) == 409) {
                    if (intermediateAlertDialog != null) {
                        intermediateAlertDialog.dismissAlertDialog();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new AlertDialogFailure(ApptSlotDetailsActivity.this, getResources().getString(R.string.try_again), "OK", "Appointment Cancel failed", "Failed") {
                                @Override
                                public void onButtonClick() {
                                    startActivity(new Intent(ApptSlotDetailsActivity.this, AppointmentActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                                    finish();
                                    overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);
                                }
                            };
                        }
                    });
                } else if (Integer.parseInt(result.getStatusCode()) == 401) {
                    if (intermediateAlertDialog != null) {
                        intermediateAlertDialog.dismissAlertDialog();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new CAllLoginAPI() {
                                @Override
                                public void onButtonClick() {

                                    callAPI();
                                }
                            }.callLoginAPI(ApptSlotDetailsActivity.this);
                        }
                    });

                } else {
                    if (intermediateAlertDialog != null) {
                        intermediateAlertDialog.dismissAlertDialog();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new AlertDialogFailure(ApptSlotDetailsActivity.this, getResources().getString(R.string.try_again), "OK", getResources().getString(R.string.went_wrong), "Failed") {
                                @Override
                                public void onButtonClick() {
                                    /*startActivity(new Intent(ApptSlotDetailsActivity.this, AppointmentActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
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
