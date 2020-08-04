package com.corals.appointment.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.corals.appointment.Adapters.CustomersAdapter;
import com.corals.appointment.Client.ApiCallback;
import com.corals.appointment.Client.ApiException;
import com.corals.appointment.Client.OkHttpApiClient;
import com.corals.appointment.Client.api.MerchantApisApi;
import com.corals.appointment.Client.model.AppointmentEnquiryBody;
import com.corals.appointment.Client.model.AppointmentEnquiryResponse;
import com.corals.appointment.Client.model.ApptTransactionBody;
import com.corals.appointment.Client.model.ApptTransactionResponse;
import com.corals.appointment.Client.model.InlineResponse20013Customersrec;
import com.corals.appointment.Client.model.Merchantinfo;
import com.corals.appointment.Constants.Constants;
import com.corals.appointment.Dialogs.AlertDialogFailure;
import com.corals.appointment.Dialogs.IntermediateAlertDialog;
import com.corals.appointment.Model.CustomersModel;
import com.corals.appointment.R;
import com.corals.appointment.Utils.CAllLoginAPI;
import com.corals.appointment.receiver.ConnectivityReceiver;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MerchantInfoActivity extends AppCompatActivity {
    private IntermediateAlertDialog intermediateAlertDialog;
    private SharedPreferences sharedpreferences_sessionToken;
    EditText et_name, et_disp_name, et_mail, et_mob, et_desc, et_reg_no, et_max_loading_days, et_max_appt_slot, et_c_temp, et_cl_temp, et_r_temp, et_legal_name;
    ApptTransactionBody Body = new ApptTransactionBody();
    private Boolean isUpdateEnabled = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_info);

        sharedpreferences_sessionToken = getSharedPreferences(LoginActivity.MyPREFERENCES_SESSIONTOKEN, Context.MODE_PRIVATE);
        Toolbar toolbar = findViewById(R.id.toolbar_mer_info);
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

        et_name = findViewById(R.id.mer_name);
        et_disp_name = findViewById(R.id.mer_disp_name);
        et_mail = findViewById(R.id.mer_disp_email);
        et_mob = findViewById(R.id.mer_disp_mob);

        et_desc = findViewById(R.id.mer_description);
        et_reg_no = findViewById(R.id.mer_reg_no);
        et_max_loading_days = findViewById(R.id.mer_max_days);
        et_max_appt_slot = findViewById(R.id.mer_max_appts_slot);

        et_c_temp = findViewById(R.id.c_temp_description);
        et_cl_temp = findViewById(R.id.cl_temp_description);
        et_r_temp = findViewById(R.id.r_temp_description);
        et_legal_name = findViewById(R.id.legal_name);

        et_r_temp.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (et_r_temp.hasFocus()) {
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                    switch (event.getAction() & MotionEvent.ACTION_MASK) {
                        case MotionEvent.ACTION_SCROLL:
                            v.getParent().requestDisallowInterceptTouchEvent(false);
                            return true;
                    }
                }
                return false;
            }
        });
        et_c_temp.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (et_c_temp.hasFocus()) {
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                    switch (event.getAction() & MotionEvent.ACTION_MASK) {
                        case MotionEvent.ACTION_SCROLL:
                            v.getParent().requestDisallowInterceptTouchEvent(false);
                            return true;
                    }
                }
                return false;
            }
        });
        et_cl_temp.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (et_cl_temp.hasFocus()) {
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                    switch (event.getAction() & MotionEvent.ACTION_MASK) {
                        case MotionEvent.ACTION_SCROLL:
                            v.getParent().requestDisallowInterceptTouchEvent(false);
                            return true;
                    }
                }
                return false;
            }
        });


        callAPI();
    }


    private void callAPI() {
        try {
            boolean isConn = ConnectivityReceiver.isConnected();
            if (isConn) {
                AppointmentEnquiryBody enquiryBody = new AppointmentEnquiryBody();
                enquiryBody.setMerId(sharedpreferences_sessionToken.getString(LoginActivity.MERID, ""));
                enquiryBody.setOutletId(sharedpreferences_sessionToken.getString(LoginActivity.OUTLETID, ""));
                enquiryBody.setReqType(Constants.GET_MERCHANT_INFO);
                enquiryBody.setCallerType("m");
                enquiryBody.setDeviceId(sharedpreferences_sessionToken.getString(LoginActivity.DEVICEID, ""));
                enquiryBody.setSessionToken(sharedpreferences_sessionToken.getString(LoginActivity.SESSIONTOKEN, ""));
                fetchMerCustomers(enquiryBody);
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new AlertDialogFailure(MerchantInfoActivity.this, getResources().getString(R.string.no_internet_sub_title), "OK", getResources().getString(R.string.no_internet_title), getResources().getString(R.string.no_internet_Heading)) {
                            @Override
                            public void onButtonClick() {
                                callAPI();
                            }
                        };
                    }
                });
            }
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }


    private void callUpdateProfile() {
        String name = et_name.getText().toString().trim();
        String disp_name = et_disp_name.getText().toString().trim();
        String mail = et_mail.getText().toString().trim();
        String desc = et_desc.getText().toString().trim();
        String reg_no = et_reg_no.getText().toString().trim();
        String max_load_days = et_max_loading_days.getText().toString().trim();
        String max_appt = et_max_appt_slot.getText().toString().trim();
        String c_temp = et_c_temp.getText().toString().trim();
        String cl_temp = et_cl_temp.getText().toString().trim();
        String r_temp = et_r_temp.getText().toString().trim();
        String legal_name = et_legal_name.getText().toString().trim();

        Merchantinfo merchantinfo = new Merchantinfo();
        merchantinfo.setMerId(sharedpreferences_sessionToken.getString(LoginActivity.MERID, ""));
        merchantinfo.setContactName(name);
        merchantinfo.setDisplayName(disp_name);
        merchantinfo.setMail(mail);
        merchantinfo.setDescription(desc);
        merchantinfo.setMaxLoadingDays(max_load_days);
        merchantinfo.setMaxApptsAllowed(max_appt);
        merchantinfo.setCTemplate(c_temp);
        merchantinfo.setClTemplate(cl_temp);
        merchantinfo.setRTemplate(r_temp);
        merchantinfo.setRegisterNo(reg_no);
        merchantinfo.setLegalName(legal_name);
        merchantinfo.setContactMobile(et_mob.getText().toString().trim());

        Body.setMerchant(merchantinfo);
        Body.setMerId(sharedpreferences_sessionToken.getString(LoginActivity.MERID, ""));
        Body.setOutletId(sharedpreferences_sessionToken.getString(LoginActivity.OUTLETID, ""));
        Body.setReqType(Constants.UPDATE_MERCHANT_INFO);
        Body.setDeviceId(sharedpreferences_sessionToken.getString(LoginActivity.DEVICEID, ""));
        Body.setSessionToken(sharedpreferences_sessionToken.getString(LoginActivity.SESSIONTOKEN, ""));
        try {
            updateProfile(Body);
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

    private void updateProfile(ApptTransactionBody requestBody) throws ApiException {
        Log.d("createCustomer---", "createCustomer: " + requestBody);
        intermediateAlertDialog = new IntermediateAlertDialog(MerchantInfoActivity.this);
        OkHttpApiClient okHttpApiClient = new OkHttpApiClient(MerchantInfoActivity.this);
        MerchantApisApi webMerchantApisApi = new MerchantApisApi();
        webMerchantApisApi.setApiClient(okHttpApiClient.getApiClient());
        webMerchantApisApi.merchantAppointmentTransactionAsync(requestBody, new ApiCallback<ApptTransactionResponse>() {
            @Override
            public void onFailure(ApiException e, int statusCode, Map<String, List<String>> responseHeaders) {
                Log.d("createCustomer--->", "onFailure-" + e.getMessage()+","+statusCode);
                if (intermediateAlertDialog != null) {
                    intermediateAlertDialog.dismissAlertDialog();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new AlertDialogFailure(MerchantInfoActivity.this, getResources().getString(R.string.try_again), "OK", getResources().getString(R.string.server_error), "Failed") {
                            @Override
                            public void onButtonClick() {
                                onBackPressed();
                            }
                        };
                    }
                });
            }

            @Override
            public void onSuccess(ApptTransactionResponse result, int statusCode, Map<String, List<String>> responseHeaders) {
                Log.d("createCustomer--->", "onSuccess-" + statusCode + "," + result.getStatusMessage());
                Log.d("createCustomer--->", "onSuccess-" + result);

                if (Integer.parseInt(result.getStatusCode()) == 200) {
                    if (intermediateAlertDialog != null) {
                        intermediateAlertDialog.dismissAlertDialog();
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new AlertDialogFailure(MerchantInfoActivity.this, "Profile updated successfully!", "OK", "", "Success") {
                                @Override
                                public void onButtonClick() {
                                    //onBackPressed();
                                    Intent in = new Intent(MerchantInfoActivity.this, DashboardActivity.class);
                                    in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(in);
                                    finish();
                                    overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);
                                }
                            };
                        }
                    });

                } else if (Integer.parseInt(result.getStatusCode()) == 400) {
                    if (intermediateAlertDialog != null) {
                        intermediateAlertDialog.dismissAlertDialog();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new AlertDialogFailure(MerchantInfoActivity.this, getResources().getString(R.string.try_again), "OK", "Invalid request", "Failed") {
                                @Override
                                public void onButtonClick() {
                                    onBackPressed();
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
                                    callUpdateProfile();
                                }
                            }.callLoginAPI(MerchantInfoActivity.this);
                        }
                    });

                } else {
                    if (intermediateAlertDialog != null) {
                        intermediateAlertDialog.dismissAlertDialog();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new AlertDialogFailure(MerchantInfoActivity.this, getResources().getString(R.string.try_again), "OK", getResources().getString(R.string.went_wrong), "Failed") {
                                @Override
                                public void onButtonClick() {
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


    private void fetchMerCustomers(AppointmentEnquiryBody requestBody) throws ApiException {
        intermediateAlertDialog = new IntermediateAlertDialog(MerchantInfoActivity.this);
        Log.d("login---", "login: " + requestBody);
        OkHttpApiClient okHttpApiClient = new OkHttpApiClient(MerchantInfoActivity.this);
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
                        new AlertDialogFailure(MerchantInfoActivity.this, getResources().getString(R.string.try_again), "OK", getResources().getString(R.string.server_error), "Failed") {
                            @Override
                            public void onButtonClick() {
                                onBackPressed();
                            }
                        };
                    }
                });
            }

            @Override
            public void onSuccess(final AppointmentEnquiryResponse result, int statusCode, Map<String, List<String>> responseHeaders) {
                Log.d("fetchService--->", "onSuccess-" + result.getStatusCode() + "," + result.getStatusMessage());
                Log.d("fetchService--->", "onSuccess-" + result);
                if (Integer.parseInt(result.getStatusCode()) == 200) {
                    if (intermediateAlertDialog != null) {
                        intermediateAlertDialog.dismissAlertDialog();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Merchantinfo merchantinfo = result.getMerchant();
                            String mer_id = merchantinfo.getMerId();
                            String name = merchantinfo.getContactName();
                            String disp_name = merchantinfo.getDisplayName();
                            String mail = merchantinfo.getMail();
                            String mob = merchantinfo.getContactMobile();

                            String desc = merchantinfo.getDescription();
                            String country = merchantinfo.getCountry();
                            String reg_no = merchantinfo.getRegisterNo();
                            String max_days = merchantinfo.getMaxLoadingDays();
                            String max_appt = merchantinfo.getMaxApptsAllowed();
                            String c_temp = merchantinfo.getCTemplate();
                            String cl_temp = merchantinfo.getClTemplate();
                            String r_temp = merchantinfo.getRTemplate();
                            String legal_name = merchantinfo.getLegalName();

                            et_name.setText(name);
                            et_disp_name.setText(disp_name);
                            et_mail.setText(mail);
                            et_mob.setText(mob);
                            et_desc.setText(desc);
                            et_reg_no.setText(reg_no);
                            et_max_loading_days.setText(max_days);
                            et_max_appt_slot.setText(max_appt);
                            et_c_temp.setText(c_temp);
                            et_cl_temp.setText(cl_temp);
                            et_r_temp.setText(r_temp);
                            et_legal_name.setText(legal_name);


                        }
                    });
                } else if (Integer.parseInt(result.getStatusCode()) == 400) {
                    if (intermediateAlertDialog != null) {
                        intermediateAlertDialog.dismissAlertDialog();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new AlertDialogFailure(MerchantInfoActivity.this, getResources().getString(R.string.try_again), "OK", "Profile updation failed", "Failed") {
                                @Override
                                public void onButtonClick() {
                                    onBackPressed();
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
                            }.callLoginAPI(MerchantInfoActivity.this);
                        }
                    });

                } else {
                    if (intermediateAlertDialog != null) {
                        intermediateAlertDialog.dismissAlertDialog();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new AlertDialogFailure(MerchantInfoActivity.this, getResources().getString(R.string.try_again), "OK", getResources().getString(R.string.went_wrong), "Failed") {
                                @Override
                                public void onButtonClick() {
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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_edit) {
            if (!isUpdateEnabled) {
                item.setIcon(R.drawable.ic_finish);
                et_name.setEnabled(true);
                et_disp_name.setEnabled(true);
                et_legal_name.setEnabled(true);
                et_mail.setEnabled(true);
                et_desc.setEnabled(true);
                et_reg_no.setEnabled(true);
                et_max_loading_days.setEnabled(true);
                et_max_appt_slot.setEnabled(true);
                et_c_temp.setEnabled(true);
                et_cl_temp.setEnabled(true);
                et_r_temp.setEnabled(true);
            }
            if (isUpdateEnabled) {
                callUpdateProfile();
            }
            isUpdateEnabled = true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}