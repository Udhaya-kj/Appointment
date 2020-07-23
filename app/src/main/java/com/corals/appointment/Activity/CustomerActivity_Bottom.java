package com.corals.appointment.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.corals.appointment.Constants.Constants;
import com.corals.appointment.Dialogs.AlertDialogFailure;
import com.corals.appointment.Dialogs.IntermediateAlertDialog;
import com.corals.appointment.Interface.SearchCustomerCallback;
import com.corals.appointment.Model.CustomersModel;
import com.corals.appointment.R;
import com.corals.appointment.Utils.CAllLoginAPI;
import com.corals.appointment.receiver.ConnectivityReceiver;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CustomerActivity_Bottom extends AppCompatActivity implements SearchCustomerCallback {
    private RecyclerView list_customers;

    CustomersAdapter customersAdapter_filter;
    TextView textView_no_results;
    private ArrayList<CustomersModel> mCustomersArrayList = new ArrayList<CustomersModel>();
    EditText editText_search;
    private IntermediateAlertDialog intermediateAlertDialog;
    AppointmentEnquiryBody enquiryBody = new AppointmentEnquiryBody();
    private SharedPreferences sharedpreferences_sessionToken;
    String service_id, date, slot_no, start_time, end_time, res_id, res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer__bottom);
        sharedpreferences_sessionToken = getSharedPreferences(LoginActivity.MyPREFERENCES_SESSIONTOKEN, Context.MODE_PRIVATE);
        Toolbar toolbar = findViewById(R.id.toolbar_customers);
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
            service_id = getIntent().getStringExtra("service_id");
            date = getIntent().getStringExtra("date");
            slot_no = getIntent().getStringExtra("slot_no");
            start_time = getIntent().getStringExtra("start_time");
            end_time = getIntent().getStringExtra("end_time");
            res_id = getIntent().getStringExtra("res_id");
            res = getIntent().getStringExtra("res");
        }

        textView_no_results = findViewById(R.id.tv_no_results);
        editText_search = findViewById(R.id.et_search_customer);
        list_customers = findViewById(R.id.listview_customers);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        list_customers.setLayoutManager(mLayoutManager);
        list_customers.setItemAnimator(new DefaultItemAnimator());
        list_customers.setItemAnimator(new DefaultItemAnimator());


        editText_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                customersAdapter_filter.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        callAPI();
    }

    private void callAPI() {
        try {
            boolean isConn = ConnectivityReceiver.isConnected();
            if (isConn) {
                getRequestBody();
                fetchMerCustomers(enquiryBody);
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new AlertDialogFailure(CustomerActivity_Bottom.this, getResources().getString(R.string.no_internet_sub_title), "OK", getResources().getString(R.string.no_internet_title), getResources().getString(R.string.no_internet_Heading)) {
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

    private void getRequestBody() {
        enquiryBody.setMerId(sharedpreferences_sessionToken.getString(LoginActivity.MERID, ""));
        enquiryBody.setReqType(Constants.CUSTOMER_LIST);
        enquiryBody.setCallerType("m");
        enquiryBody.setDeviceId(sharedpreferences_sessionToken.getString(LoginActivity.DEVICEID, ""));
        enquiryBody.setSessionToken(sharedpreferences_sessionToken.getString(LoginActivity.SESSIONTOKEN, ""));
    }

    private void fetchMerCustomers(AppointmentEnquiryBody requestBody) throws ApiException {
        intermediateAlertDialog = new IntermediateAlertDialog(CustomerActivity_Bottom.this);
        Log.d("login---", "login: " + requestBody);
        OkHttpApiClient okHttpApiClient = new OkHttpApiClient(CustomerActivity_Bottom.this);
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
                        new AlertDialogFailure(CustomerActivity_Bottom.this, getResources().getString(R.string.try_again), "OK", getResources().getString(R.string.server_error), "Failed") {
                            @Override
                            public void onButtonClick() {
                                startActivity(new Intent(CustomerActivity_Bottom.this, DashboardActivity.class));
                                finish();
                                overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);
                            }
                        };
                    }
                });
            }

            @Override
            public void onSuccess(final AppointmentEnquiryResponse result, int statusCode, Map<String, List<String>> responseHeaders) {
                Log.d("fetchService--->", "onSuccess-" + statusCode + "," + result.getStatusMessage());
                Log.d("fetchService--->", "onSuccess-" + result);
                if (Integer.parseInt(result.getStatusCode()) == 200) {
                    if (intermediateAlertDialog != null) {
                        intermediateAlertDialog.dismissAlertDialog();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            editText_search.setEnabled(true);
                            ArrayList<CustomersModel> customersModelArrayList = new ArrayList<>();
                            List<InlineResponse20013Customersrec> customersrecList = result.getCustomers();
                            for (int i = 0; i < customersrecList.size(); i++) {
                                String cus_id = customersrecList.get(i).getCustId();
                                String cus_name = customersrecList.get(i).getCustName();
                                String cus_mob = customersrecList.get(i).getCustMobile();
                                String cus_email = customersrecList.get(i).getMailId();
                                CustomersModel customersModel = new CustomersModel(cus_id, cus_name, cus_mob, cus_email);
                                customersModelArrayList.add(customersModel);

                                Log.d("Customer---", "run: " + cus_id + "," + cus_name + "," + cus_mob+ "," + cus_email);
                            }

                            customersAdapter_filter = new CustomersAdapter(CustomerActivity_Bottom.this, customersModelArrayList, service_id, date, slot_no, start_time, end_time, res_id, res);
                            list_customers.setAdapter(customersAdapter_filter);
                        }
                    });
                } else if (Integer.parseInt(result.getStatusCode()) == 404) {
                    if (intermediateAlertDialog != null) {
                        intermediateAlertDialog.dismissAlertDialog();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new AlertDialogFailure(CustomerActivity_Bottom.this, "No customers found", "OK", "", "Warning") {
                                @Override
                                public void onButtonClick() {
                                    editText_search.setEnabled(false);
                                }
                            };
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

                                    callAPI();
                                }
                            }.callLoginAPI(CustomerActivity_Bottom.this);
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
                            new AlertDialogFailure(CustomerActivity_Bottom.this, getResources().getString(R.string.try_again), "OK", getResources().getString(R.string.went_wrong), "Failed") {
                                @Override
                                public void onButtonClick() {
                                    startActivity(new Intent(CustomerActivity_Bottom.this, DashboardActivity.class));
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
        Intent i = new Intent(CustomerActivity_Bottom.this, DashboardActivity.class);
        startActivity(i);
        finish();
        overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);
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
        inflater.inflate(R.menu.add_new_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_add_new) {
            Intent i = new Intent(CustomerActivity_Bottom.this, CreateCustomerActivity.class);
            i.putExtra("page_id", "02");
            startActivity(i);
            //finish();
            overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void customerCallback(final String flag) {
        Log.d("CustomerFlag--->", "customerCallback: "+flag);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (flag.equals("1")) {
                    textView_no_results.setVisibility(View.GONE);
                    list_customers.setVisibility(View.VISIBLE);
                } else if (flag.equals("0")) {
                    textView_no_results.setVisibility(View.VISIBLE);
                    list_customers.setVisibility(View.GONE);
                }
            }
        });

    }
}