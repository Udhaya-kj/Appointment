package com.corals.appointment.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.corals.appointment.Client.ApiCallback;
import com.corals.appointment.Client.ApiException;
import com.corals.appointment.Client.OkHttpApiClient;
import com.corals.appointment.Client.api.MerchantApisApi;
import com.corals.appointment.Client.model.AppointmentEnquiryBody;
import com.corals.appointment.Client.model.ApptTransactionBody;
import com.corals.appointment.Client.model.ApptTransactionResponse;
import com.corals.appointment.Dialogs.AlertDialogFailure;
import com.corals.appointment.Dialogs.IntermediateAlertDialog;
import com.corals.appointment.R;
import com.corals.appointment.receiver.ConnectivityReceiver;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CreateCustomerActivity extends AppCompatActivity {
    EditText editText_cus_name, editText_cus_mob, editText_cus_mail;
    Button button_continue;
    private SharedPreferences sharedpreferences_customers;
    public static final String MyPREFERENCES_CUSTOMERS = "MyPrefs_Customers";
    public static final String CUSTOMER_NAME = "CUSTOMER_NAME";
    public static final String CUSTOMER_MOBILE = "CUSTOMER_MOBILE";
    private ArrayList<String> cus_name_list, cus_mob_list;
    public String pageId = "", position = "";
    private IntermediateAlertDialog intermediateAlertDialog;
    private String name;
    private String mob;
    private String mail;
    private SharedPreferences sharedpreferences_sessionToken;
    ApptTransactionBody Body = new ApptTransactionBody();

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
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_left);
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
        /*if (getIntent().getExtras() != null) {
            pageId = getIntent().getStringExtra("page_id");
            position = getIntent().getStringExtra("position");
            String cus_name = getIntent().getStringExtra("cus_name");
            String cus_mob = getIntent().getStringExtra("cus_mob");
            if (pageId.equals("22")) {
                toolbar.setTitle("Edit Customer");
                button_continue.setText("UPDATE");
            }
            editText_cus_name.setText(cus_name);
            editText_cus_mob.setText(cus_mob);
        }*/
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
        name = editText_cus_name.getText().toString().trim();
        mob = editText_cus_mob.getText().toString().trim();
        mail = editText_cus_mail.getText().toString().trim();

        if (!TextUtils.isEmpty(name)) {
            if (!TextUtils.isEmpty(mob) && mob.length() >= 8) {
                if (!TextUtils.isEmpty(mail) && Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
                    Body.setMobile(mob);
                    Body.setName(name);
                    Body.setMail(mail);
                    Body.setMerId(sharedpreferences_sessionToken.getString(LoginActivity.MERID, ""));
                    Body.setReqType("T-CC.");
                    Body.setDeviceId(sharedpreferences_sessionToken.getString(LoginActivity.DEVICEID, ""));
                    Body.setSessionToken(sharedpreferences_sessionToken.getString(LoginActivity.SESSIONTOKEN, ""));

                    try {
                        createCustomer(Body);
                    } catch (ApiException e) {
                        e.printStackTrace();
                    }
                } else {
                    editText_cus_mail.setError("Enter valid email");
                    editText_cus_mail.requestFocus();
                }
            } else {
                editText_cus_mail.setError("Enter valid mobile");
                editText_cus_mail.requestFocus();
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
        Intent in = new Intent(CreateCustomerActivity.this, CustomerActivity_Bottom.class);
        startActivity(in);
        finish();
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