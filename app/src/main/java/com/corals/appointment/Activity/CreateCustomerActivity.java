package com.corals.appointment.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.corals.appointment.Adapters.CustomersAdapter_MakeAppt;
import com.corals.appointment.Client.ApiCallback;
import com.corals.appointment.Client.ApiException;
import com.corals.appointment.Client.OkHttpApiClient;
import com.corals.appointment.Client.api.MerchantApisApi;
import com.corals.appointment.Client.model.AppointmentEnquiryBody;
import com.corals.appointment.Client.model.ApptTransactionBody;
import com.corals.appointment.Client.model.ApptTransactionResponse;
import com.corals.appointment.Constants.Constants;
import com.corals.appointment.Dialogs.AlertDialogFailure;
import com.corals.appointment.Dialogs.AlertDialogYesNo;
import com.corals.appointment.Dialogs.IntermediateAlertDialog;
import com.corals.appointment.R;
import com.corals.appointment.receiver.ConnectivityReceiver;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CreateCustomerActivity extends AppCompatActivity {
    private EditText editText_cus_name, editText_cus_mob, editText_cus_mail;
    private Button button_continue;
    private SharedPreferences sharedpreferences_customers;
    public static final String MyPREFERENCES_CUSTOMERS = "MyPrefs_Customers";
    public static final String CUSTOMER_NAME = "CUSTOMER_NAME";
    public static final String CUSTOMER_MOBILE = "CUSTOMER_MOBILE";
    private ArrayList<String> cus_name_list, cus_mob_list;
    public String position = "";
    private IntermediateAlertDialog intermediateAlertDialog;
    private SharedPreferences sharedpreferences_sessionToken;
    ApptTransactionBody Body = new ApptTransactionBody();
    String page_id = "";
    String  ser_id, date, res_id, res, service, slot_no, start_time, end_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_customer);

        sharedpreferences_customers = getSharedPreferences(MyPREFERENCES_CUSTOMERS, Context.MODE_PRIVATE);
        sharedpreferences_sessionToken = getSharedPreferences(LoginActivity.MyPREFERENCES_SESSIONTOKEN, Context.MODE_PRIVATE);
        Toolbar toolbar = findViewById(R.id.toolbar_create_customer);
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
        cus_name_list = new ArrayList<>();
        cus_mob_list = new ArrayList<>();
        editText_cus_name = findViewById(R.id.et_cus_name);
        editText_cus_mob = findViewById(R.id.et_cus_mob);
        editText_cus_mail = findViewById(R.id.et_cus_email);
        button_continue = findViewById(R.id.button_create_customer);

        if (getIntent().getExtras() != null) {
            page_id = getIntent().getStringExtra("page_id");
            if(page_id.equals("2")){
                ser_id = getIntent().getStringExtra("service_id");
                service = getIntent().getStringExtra("service");
                res_id = getIntent().getStringExtra("res_id");
                res = getIntent().getStringExtra("res");
                date = getIntent().getStringExtra("date");
                slot_no = getIntent().getStringExtra("slot_no");
                start_time = getIntent().getStringExtra("start_time");
                end_time = getIntent().getStringExtra("end_time");

                Log.d("Data---", "onCreate: "+page_id+","+ser_id+","+service+","+res_id+","+res+","+date+","+slot_no+","+start_time+","+end_time);
            }
        }
        button_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callAPI();
            }
        });
    }

    private void callAPI() {
        boolean isConn = ConnectivityReceiver.isConnected();
        if (isConn) {
            getRequestBody();

        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    new AlertDialogFailure(CreateCustomerActivity.this, getResources().getString(R.string.no_internet_sub_title), "OK", getResources().getString(R.string.no_internet_title), getResources().getString(R.string.no_internet_Heading)) {
                        @Override
                        public void onButtonClick() {

                        }
                    };
                }
            });
        }
    }

    private void getRequestBody() {
        String cus_name = "";
        cus_name = editText_cus_name.getText().toString().trim();
        String mob = editText_cus_mob.getText().toString().trim();
        String mail = editText_cus_mail.getText().toString().trim();

        String name = cus_name;
        Log.d("Customer--->", "getRequestBody: " + cus_name.length() + "," + mob + "," + mail + "," + name);
        if (!TextUtils.isEmpty(cus_name)) {
            if (mob.length() > 0 && mob.length() >= 8) {
                if (mail.length() > 0 && Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
                    try {
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                    } catch (Exception e) {
                        // TODO: handle exception
                    }
                    Body.setMobile(mob);
                    Body.setName(cus_name);
                    Body.setMail(mail);
                    Body.setMerId(sharedpreferences_sessionToken.getString(LoginActivity.MERID, ""));
                    Body.setReqType(Constants.CUSTOMER_CREATE);
                    Body.setDeviceId(sharedpreferences_sessionToken.getString(LoginActivity.DEVICEID, ""));
                    Body.setSessionToken(sharedpreferences_sessionToken.getString(LoginActivity.SESSIONTOKEN, ""));

                    final String finalCus_name = cus_name;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new AlertDialogYesNo(CreateCustomerActivity.this, "Create Customer?", "Are you sure, You want to create " + finalCus_name + "?", "Yes", "No") {
                                @Override
                                public void onOKButtonClick() {
                                    try {
                                        createCustomer(Body);
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


                } else {
                    editText_cus_mail.setError("Enter valid email");
                    editText_cus_mail.requestFocus();
                }
            } else {
                editText_cus_mob.setError("Enter valid mobile");
                editText_cus_mob.requestFocus();
            }
        }
        editText_cus_name.setError("Enter valid name");
        editText_cus_name.requestFocus();
    }

    private void createCustomer(ApptTransactionBody requestBody) throws ApiException {
        Log.d("createCustomer---", "createCustomer: " + requestBody);
        intermediateAlertDialog = new IntermediateAlertDialog(CreateCustomerActivity.this);
        OkHttpApiClient okHttpApiClient = new OkHttpApiClient(CreateCustomerActivity.this);
        MerchantApisApi webMerchantApisApi = new MerchantApisApi();
        webMerchantApisApi.setApiClient(okHttpApiClient.getApiClient());
        webMerchantApisApi.merchantAppointmentTransactionAsync(requestBody, new ApiCallback<ApptTransactionResponse>() {
            @Override
            public void onFailure(ApiException e, int statusCode, Map<String, List<String>> responseHeaders) {
                Log.d("createCustomer--->", "onFailure-" + e.getMessage());
                if (intermediateAlertDialog != null) {
                    intermediateAlertDialog.dismissAlertDialog();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new AlertDialogFailure(CreateCustomerActivity.this, getResources().getString(R.string.try_again), "OK", getResources().getString(R.string.went_wrong), "Failed") {
                            @Override
                            public void onButtonClick() {
                                startActivity(new Intent(CreateCustomerActivity.this, CustomerActivity_Bottom.class));
                                finish();
                                overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);
                            }
                        };
                    }
                });
            }

            @Override
            public void onSuccess(ApptTransactionResponse result, int statusCode, Map<String, List<String>> responseHeaders) {
                Log.d("createCustomer--->", "onSuccess-" + statusCode + "," + result.getStatusMessage());
                Log.d("createCustomer--->", "onSuccess-" + result);
                if (intermediateAlertDialog != null) {
                    intermediateAlertDialog.dismissAlertDialog();
                }
                if (Integer.parseInt(result.getStatusCode()) == 200) {

                    if(page_id.equals("2")){
                        Intent i=new Intent(CreateCustomerActivity.this, ApptConfirmActivity.class);
           /*             i.putExtra("cus_name", mDisplayedValues.get(position).getName());
                        i.putExtra("cus_mob", mDisplayedValues.get(position).getMobile());
                        i.putExtra("cus_id", mDisplayedValues.get(position).getCus_id());
                        i.putExtra("cus_email", mDisplayedValues.get(position).getEmail());*/
                        i.putExtra("service_id", ser_id);
                        i.putExtra("service", service);
                        i.putExtra("date", date);
                        i.putExtra("slot_no",slot_no);
                        i.putExtra("start_time", start_time);
                        i.putExtra("end_time", end_time);
                        i.putExtra("res_id", res_id);
                        i.putExtra("res", res);
                        startActivity(i);
                        finish();
                        overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);
                    }
                    else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                new AlertDialogFailure(CreateCustomerActivity.this, "Customer created successfully!", "OK", "", "Success") {
                                    @Override
                                    public void onButtonClick() {
                                        startActivity(new Intent(CreateCustomerActivity.this, CustomerActivity_Bottom.class));
                                        finish();
                                        overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);
                                    }
                                };
                            }
                        });
                    }
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new AlertDialogFailure(CreateCustomerActivity.this, getResources().getString(R.string.try_again), "OK", getResources().getString(R.string.went_wrong), "Failed") {
                                @Override
                                public void onButtonClick() {
                                    startActivity(new Intent(CreateCustomerActivity.this, CustomerActivity_Bottom.class));
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
    public void onBackPressed() {
        super.onBackPressed();

        if (!TextUtils.isEmpty(page_id) && page_id.equals("02")) {
            Intent in = new Intent(CreateCustomerActivity.this, CustomerActivity_Bottom.class);
            startActivity(in);
            finish();
            overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);

        } else if (!TextUtils.isEmpty(page_id) && page_id.equals("2")) {
            Intent i = new Intent(CreateCustomerActivity.this, CustomersMakeApptActivity.class);
            i.putExtra("page_id", "2");
            i.putExtra("service_id", ser_id);
            i.putExtra("service", service);
            i.putExtra("res_id", res_id);
            i.putExtra("res", res);
            i.putExtra("date", date);
            i.putExtra("slot_no", slot_no);
            i.putExtra("start_time", start_time);
            i.putExtra("end_time", end_time);
            startActivity(i);
            finish();
            overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);
        } else {
            Intent in = new Intent(CreateCustomerActivity.this, DashboardActivity.class);
            startActivity(in);
            finish();
            overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (intermediateAlertDialog != null) {
            intermediateAlertDialog.dismissAlertDialog();
            intermediateAlertDialog = null;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.finish_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_finish) {
            callAPI();
        }
        return super.onOptionsItemSelected(item);
    }

}