package com.corals.appointment.Activity;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.corals.appointment.Client.ApiCallback;
import com.corals.appointment.Client.ApiException;
import com.corals.appointment.Client.OkHttpApiClient;
import com.corals.appointment.Client.api.MerchantApisApi;
import com.corals.appointment.Client.model.AppointmentService;
import com.corals.appointment.Client.model.ApptTransactionBody;
import com.corals.appointment.Client.model.ApptTransactionResponse;
import com.corals.appointment.Dialogs.IntermediateAlertDialog;
import com.corals.appointment.R;
import com.corals.appointment.receiver.ConnectivityReceiver;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class AddServiceActivity extends AppCompatActivity {

    EditText et_ser_name, et_ser_desc, et_ser_amt;
    Button button_continue;
    public String pageId = "";

    private SharedPreferences sharedpreferences_service_data;
    public static final String MyPREFERENCES_SERVICE_DATA = "MyPREFERENCES_SERVICE_DATA";
    public static final String SER_NAME = "SER_NAME";
    public static final String SER_DURATION = "SER_DURATION";
    public static final String SER_AMOUNT = "SER_AMOUNT";
    public static final String SER_SHOW_AMOUNT = "SER_SHOW_AMOUNT";
    public static final String SER_DESCRIPTION = "SER_DESCRIPTION";
    Switch aSwitch;
    int time_mins = 0;
    String showAmtCustomer = "0";
    private TextView tv_ser_duration;
    String ser_id, ser_name, ser_dur, ser_amount, ser_desc, show_cust;
    private SharedPreferences sharedpreferences_sessionToken;
    private IntermediateAlertDialog intermediateAlertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resource);

        Toolbar toolbar = findViewById(R.id.toolbar_new_resource);
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
        sharedpreferences_service_data = getSharedPreferences(MyPREFERENCES_SERVICE_DATA, Context.MODE_PRIVATE);
        et_ser_name = findViewById(R.id.et_res_name);
        et_ser_desc = findViewById(R.id.et_service_description);
        tv_ser_duration = findViewById(R.id.tv_res_dur);
        et_ser_amt = findViewById(R.id.et_ser_amount);
        button_continue = findViewById(R.id.button_res_continue);
        aSwitch = findViewById(R.id.switch_show_amount);


        if (getIntent().getExtras() != null) {
            pageId = getIntent().getStringExtra("page_id");

            if (pageId.equals("03")) {
                ser_name = getIntent().getStringExtra("name");
                ser_dur = getIntent().getStringExtra("duration");
                ser_amount = getIntent().getStringExtra("amount");
                ser_id = getIntent().getStringExtra("ser_id");
                ser_desc = getIntent().getStringExtra("description");
                show_cust = getIntent().getStringExtra("show_cust");
                button_continue.setText("UPDATE SERVICE");
            } else if (pageId.equals("003")) {
                ser_name = sharedpreferences_service_data.getString(SER_NAME, "");
                ser_dur = sharedpreferences_service_data.getString(SER_DURATION, "");
                ser_desc = sharedpreferences_service_data.getString(SER_DESCRIPTION, "");
                ser_amount = sharedpreferences_service_data.getString(SER_AMOUNT, "");
                show_cust = sharedpreferences_service_data.getString(SER_SHOW_AMOUNT, "");
            }

            Log.d("AddService--->", "onCreate: " + pageId + "," + ser_name + "," + ser_dur + "," + ser_amount + "," + ser_id + "," + ser_desc + "," + show_cust);
            if (!TextUtils.isEmpty(ser_name)) {
                et_ser_name.setText(ser_name);
            }
            if (!TextUtils.isEmpty(ser_dur)) {
                tv_ser_duration.setText(ser_dur);
                time_mins = Integer.parseInt(ser_dur);
            }
            if (!TextUtils.isEmpty(ser_amount)) {
                et_ser_amt.setText(ser_amount);
            }
            if (!TextUtils.isEmpty(ser_desc)) {
                et_ser_desc.setText(ser_desc);
            }

            if (!TextUtils.isEmpty(show_cust) && show_cust.equals("true")) {
                aSwitch.setChecked(true);
            } else {
                aSwitch.setChecked(false);
            }

        }

        tv_ser_duration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timepicker_dialog();
            }
        });
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    showAmtCustomer = "1";
                } else {
                    showAmtCustomer = "0";
                }
            }
        });
        button_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                } catch (Exception e) {
                    // TODO: handle exception
                }
                String name = et_ser_name.getText().toString().trim();
                String duration = tv_ser_duration.getText().toString();
                String s_desc = et_ser_desc.getText().toString().trim();
                String s_amt = et_ser_amt.getText().toString().trim();
                if (name.length() > 0) {
                    if (time_mins != 0) {
                        if (s_amt.length() > 0) {
                            if (s_desc.length() > 0) {

                                if (pageId.equals("3")) {
                                    SharedPreferences.Editor editor = sharedpreferences_service_data.edit();
                                    editor.putString(SER_NAME, name);
                                    editor.putString(SER_DURATION, String.valueOf(time_mins));
                                    editor.putString(SER_DESCRIPTION, s_desc);
                                    editor.putString(SER_AMOUNT, s_amt);
                                    editor.putString(SER_SHOW_AMOUNT, showAmtCustomer);
                                    editor.commit();

                                    Intent in = new Intent(AddServiceActivity.this, AddServiceAvailTimeActivity.class);
                                    in.putExtra("ser_name", name);
                                    in.putExtra("ser_name", name);
                                    in.putExtra("ser_dur", String.valueOf(time_mins));
                                    in.putExtra("ser_desc", s_desc);
                                    in.putExtra("page_id", pageId);
                                    in.putExtra("amount", s_amt);
                                    in.putExtra("show_amount", showAmtCustomer);
                                    startActivity(in);
                                    finish();
                                    overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);
                                } else if (pageId.equals("03")) {
                                    AppointmentService appointmentService = new AppointmentService();
                                    appointmentService.setSerId(ser_id);
                                    appointmentService.setSerName(ser_name);
                                    appointmentService.setSerDuration(ser_dur);
                                    appointmentService.setSerPrice(ser_amount);
                                    appointmentService.setIsActive(true);
                                    appointmentService.setMerId(sharedpreferences_sessionToken.getString(LoginActivity.MERID, ""));
                                    appointmentService.setSerDescription(ser_desc);
                                    if (!TextUtils.isEmpty(show_cust) && show_cust.equals("true")) {
                                        appointmentService.setIsShowCust(true);
                                    } else {
                                        appointmentService.setIsShowCust(false);
                                    }


                                    ApptTransactionBody transactionBody = new ApptTransactionBody();
                                    transactionBody.setReqType("T-S.U");
                                    transactionBody.setMerId(sharedpreferences_sessionToken.getString(LoginActivity.MERID, ""));
                                    transactionBody.setDeviceId("c43cbfe00b37ae6133ca023484869d2c489a8974ba48fb3286aa058292d08f0e");
                                    transactionBody.setSessionToken(sharedpreferences_sessionToken.getString(LoginActivity.SESSIONTOKEN, ""));
                                    transactionBody.setService(appointmentService);
                                    try {
                                        Log.d("Token--->", "token: " + sharedpreferences_sessionToken.getString(LoginActivity.SESSIONTOKEN, ""));
                                        boolean isConn = ConnectivityReceiver.isConnected();
                                        if (isConn) {
                                            intermediateAlertDialog = new IntermediateAlertDialog(AddServiceActivity.this);
                                            apptUpdateService(transactionBody);
                                        } else {
                                            Toast.makeText(AddServiceActivity.this, "No internet connection!", Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (ApiException e) {
                                        e.printStackTrace();
                                    }

                                }

                            } else {
                                et_ser_desc.setError("Enter service description");
                                et_ser_desc.requestFocus();
                            }
                        } else {
                            et_ser_amt.setError("Enter service amount");
                            et_ser_amt.requestFocus();
                        }
                    } else {
                        getDialog("Select valid service duration");
                    }

                } else {
                    et_ser_name.setError("Enter service name");
                    et_ser_name.requestFocus();
                }


            }
        });


    }

    void timepicker_dialog() {
        LayoutInflater factory = LayoutInflater.from(this);
        final View deleteDialogView = factory.inflate(R.layout.timepicker_layout, null);
        final AlertDialog pickerDialog = new AlertDialog.Builder(this).create();
        pickerDialog.setView(deleteDialogView);
        final TimePicker pickStartTime = (TimePicker) deleteDialogView.findViewById(R.id.StartTime);
        Button button = (Button) deleteDialogView.findViewById(R.id.button_picker_ok);
        pickStartTime.setIs24HourView(true);
        pickStartTime.setCurrentHour(00);
        pickStartTime.setCurrentMinute(00);
        pickStartTime.setOnTimeChangedListener(mStartTimeChangedListener);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int hour = pickStartTime.getCurrentHour();
                int minute = pickStartTime.getCurrentMinute();
                time_mins = (hour * 60) + minute;
                tv_ser_duration.setText(String.valueOf(time_mins));
                pickerDialog.dismiss();
            }
        });
        pickerDialog.show();

    }

    private TimePicker.OnTimeChangedListener mStartTimeChangedListener =
            new TimePicker.OnTimeChangedListener() {

                public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                    updateDisplay(view, hourOfDay, minute);
                }
            };

    private TimePicker.OnTimeChangedListener mNullTimeChangedListener =
            new TimePicker.OnTimeChangedListener() {

                public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {

                }
            };


    private void updateDisplay(TimePicker timePicker, int hourOfDay, int minute) {
        int nextMinute = 0;
        //int nextHour = 0;

        timePicker.setOnTimeChangedListener(mNullTimeChangedListener);

        if (minute >= 45 && minute <= 59)
            nextMinute = 45;
        else if (minute >= 30)
            nextMinute = 30;
        else if (minute >= 15)
            nextMinute = 15;
        else if (minute > 0)
            nextMinute = 0;
        else {
            nextMinute = 45;
        }

        if (minute - nextMinute == 1) {
            if (minute >= 45 && minute <= 59) {
                nextMinute = 00;
                hourOfDay = hourOfDay + 1;
            } else if (minute >= 30)
                nextMinute = 45;
            else if (minute >= 15)
                nextMinute = 30;
            else if (minute > 0)
                nextMinute = 15;
            else {
                nextMinute = 15;
            }
        }

        timePicker.setCurrentHour(hourOfDay);
        timePicker.setCurrentMinute(nextMinute);

        timePicker.setOnTimeChangedListener(mStartTimeChangedListener);

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        try {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            // TODO: handle exception
        }

        Intent in = new Intent(AddServiceActivity.this, SetupServiceActivity_Bottom.class);
        startActivity(in);
        finish();
        overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);

    }


    private void getDialog(String msg) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AddServiceActivity.this);
        alertDialogBuilder.setMessage(msg);
        alertDialogBuilder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        arg0.dismiss();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void apptUpdateService(ApptTransactionBody requestBody) throws ApiException {
        Log.d("updateService---", "service: " + requestBody);
        OkHttpApiClient okHttpApiClient = new OkHttpApiClient(AddServiceActivity.this);
        MerchantApisApi webMerchantApisApi = new MerchantApisApi();
        webMerchantApisApi.setApiClient(okHttpApiClient.getApiClient());

        webMerchantApisApi.merchantAppointmentTransactionAsync(requestBody, new ApiCallback<ApptTransactionResponse>() {
            @Override
            public void onFailure(final ApiException e, int statusCode, Map<String, List<String>> responseHeaders) {
                Log.d("updateService--->", "onFailure-" + e.getMessage());
                if (intermediateAlertDialog != null) {
                    intermediateAlertDialog.dismissAlertDialog();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(AddServiceActivity.this, "Service update Failed :" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onSuccess(ApptTransactionResponse result, int statusCode, Map<String, List<String>> responseHeaders) {
                Log.d("updateService--->", "onSuccess-" + statusCode + "," + result.getStatusMessage());
                if (intermediateAlertDialog != null) {
                    intermediateAlertDialog.dismissAlertDialog();
                }
                if (Integer.parseInt(result.getStatusCode()) == 200) {
                    Intent in = new Intent(AddServiceActivity.this, SetupServiceActivity_Bottom.class);
                    startActivity(in);
                    finish();
                    overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(AddServiceActivity.this, "Service update Failed", Toast.LENGTH_SHORT).show();
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