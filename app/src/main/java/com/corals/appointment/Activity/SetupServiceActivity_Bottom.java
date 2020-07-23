package com.corals.appointment.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.corals.appointment.Adapters.ServicesAdapter;
import com.corals.appointment.Adapters.ServicesAdapter_Calender;
import com.corals.appointment.Adapters.ServicesRecyclerviewAdapter;
import com.corals.appointment.Client.ApiCallback;
import com.corals.appointment.Client.ApiException;
import com.corals.appointment.Client.OkHttpApiClient;
import com.corals.appointment.Client.api.MerchantApisApi;
import com.corals.appointment.Client.model.AppointmentEnquiryBody;
import com.corals.appointment.Client.model.AppointmentEnquiryResponse;
import com.corals.appointment.Client.model.AppointmentService;
import com.corals.appointment.Constants.Constants;
import com.corals.appointment.Dialogs.AlertDialogFailure;
import com.corals.appointment.Dialogs.IntermediateAlertDialog;
import com.corals.appointment.R;
import com.corals.appointment.Utils.CAllLoginAPI;
import com.corals.appointment.receiver.ConnectivityReceiver;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SetupServiceActivity_Bottom extends AppCompatActivity {
    TextView textView_no_ser;
    private ListView listView_services;
    private ArrayList<String> service_name_list, service_dur_list;
    ServicesAdapter servicesAdapter;
    public String pageId = "";
    private SharedPreferences sharedpreferences_service_data;
    private IntermediateAlertDialog intermediateAlertDialog;
    private SharedPreferences sharedpreferences_sessionToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_service__bottom);

        Toolbar toolbar = findViewById(R.id.toolbar_service_offer);
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

        textView_no_ser = findViewById(R.id.tv_no_services);

        sharedpreferences_sessionToken = getSharedPreferences(LoginActivity.MyPREFERENCES_SESSIONTOKEN, Context.MODE_PRIVATE);
        sharedpreferences_service_data = getSharedPreferences(AddServiceActivity.MyPREFERENCES_SERVICE_DATA, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor_oh = sharedpreferences_service_data.edit();
        editor_oh.clear();
        editor_oh.commit();

        service_name_list = new ArrayList<>();
        service_dur_list = new ArrayList<>();
        listView_services = findViewById(R.id.listview_services);

        SharedPreferences preferences = getSharedPreferences(AddServiceActivity.MyPREFERENCES_SERVICE_DATA, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();

        callAPI_Service();




    }

    private void callAPI_Service() {

        boolean isConn = ConnectivityReceiver.isConnected();
        if (isConn) {
            try {
                AppointmentEnquiryBody enquiryBody = new AppointmentEnquiryBody();
                enquiryBody.setReqType(Constants.SERVICES_LIST_API);
                enquiryBody.setMerId(sharedpreferences_sessionToken.getString(LoginActivity.MERID, ""));
                enquiryBody.callerType("m");
                enquiryBody.setDeviceId(sharedpreferences_sessionToken.getString(LoginActivity.DEVICEID, ""));
                enquiryBody.setSessionToken(sharedpreferences_sessionToken.getString(LoginActivity.SESSIONTOKEN, ""));
                fetchServices(enquiryBody);
            } catch (ApiException e) {
                e.printStackTrace();
            }
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    new AlertDialogFailure(SetupServiceActivity_Bottom.this, getResources().getString(R.string.no_internet_sub_title), "OK", getResources().getString(R.string.no_internet_title), getResources().getString(R.string.no_internet_Heading)) {
                        @Override
                        public void onButtonClick() {
                            callAPI_Service();
                        }
                    };
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent in = new Intent(SetupServiceActivity_Bottom.this, DashboardActivity.class);
        startActivity(in);
        finish();
        overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);

    }

    private void fetchServices(AppointmentEnquiryBody requestBody) throws ApiException {
        intermediateAlertDialog = new IntermediateAlertDialog(SetupServiceActivity_Bottom.this);
        Log.d("fetchService--->", "fetchService: " + requestBody);
        OkHttpApiClient okHttpApiClient = new OkHttpApiClient(SetupServiceActivity_Bottom.this);
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
                        new AlertDialogFailure(SetupServiceActivity_Bottom.this, getResources().getString(R.string.try_again), "OK", getResources().getString(R.string.server_error), "Failed") {
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

                Log.d("fetchService--->", "onSuccess-" + statusCode + "," + result);
                if (Integer.parseInt(result.getStatusCode()) == 200) {
                    if (intermediateAlertDialog != null) {
                        intermediateAlertDialog.dismissAlertDialog();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (!result.getServices().isEmpty() && result.getServices() != null) {
                                textView_no_ser.setVisibility(View.GONE);
                                listView_services.setVisibility(View.VISIBLE);
                                servicesAdapter = new ServicesAdapter(SetupServiceActivity_Bottom.this, result.getServices());
                                listView_services.setAdapter(servicesAdapter);

                            } else {
                                textView_no_ser.setVisibility(View.VISIBLE);
                                listView_services.setVisibility(View.GONE);
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
                            new AlertDialogFailure(SetupServiceActivity_Bottom.this, "No services created!", "OK","", "Failed") {
                                @Override
                                public void onButtonClick() {
                          /*          startActivity(new Intent(CalendarServicesActivity.this, DashboardActivity.class));
                                    finish();
                                    overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);*/
                                    onBackPressed();
                                }
                            };
                        }
                    });
                }
                else if (Integer.parseInt(result.getStatusCode()) == 400) {
                    if (intermediateAlertDialog != null) {
                        intermediateAlertDialog.dismissAlertDialog();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new AlertDialogFailure(SetupServiceActivity_Bottom.this, getResources().getString(R.string.try_again), "OK", "Invalid data", "Failed") {
                                @Override
                                public void onButtonClick() {
                               /*     startActivity(new Intent(CalendarServicesActivity.this, DashboardActivity.class));
                                    finish();
                                    overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);*/
                                    onBackPressed();
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

                                    callAPI_Service();
                                }
                            }.callLoginAPI(SetupServiceActivity_Bottom.this);
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
                            new AlertDialogFailure(SetupServiceActivity_Bottom.this, getResources().getString(R.string.try_again), "OK", getResources().getString(R.string.went_wrong), "Failed") {
                                @Override
                                public void onButtonClick() {
                       /*             startActivity(new Intent(CalendarServicesActivity.this, DashboardActivity.class));
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_new_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_add_new) {
            Intent in = new Intent(SetupServiceActivity_Bottom.this, AddServiceActivity.class);
            in.putExtra("page_id", "3");
            startActivity(in);
           // finish();
            overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);
        }
        return super.onOptionsItemSelected(item);
    }
}
