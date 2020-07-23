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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.corals.appointment.Adapters.ServicesAdapter;
import com.corals.appointment.Adapters.StaffListAdapter;
import com.corals.appointment.Client.ApiCallback;
import com.corals.appointment.Client.ApiException;
import com.corals.appointment.Client.OkHttpApiClient;
import com.corals.appointment.Client.api.MerchantApisApi;
import com.corals.appointment.Client.model.AppointmentEnquiryBody;
import com.corals.appointment.Client.model.AppointmentEnquiryResponse;
import com.corals.appointment.Client.model.AppointmentResources;
import com.corals.appointment.Client.model.AppointmentService;
import com.corals.appointment.Client.model.MapServiceResourceBody;
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

public class SetupStaffActivity_Bottom extends AppCompatActivity {
    private ListView listView_staffs;

    StaffListAdapter staffListAdapter;
    public String pageId = "";
    private SharedPreferences sharedpreferences_sessionToken;
    private IntermediateAlertDialog intermediateAlertDialog;
    TextView textView_no_staff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff__bottom);

        Toolbar toolbar = findViewById(R.id.toolbar_staffs);
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
        textView_no_staff = findViewById(R.id.tv_no_staffs);
        listView_staffs = findViewById(R.id.listview_staffs);

        if (getIntent().getExtras() != null) {
            pageId = getIntent().getStringExtra("page_id");
        }
        callAPI_Staff();

    }

    private void callAPI_Staff() {
        AppointmentEnquiryBody enquiryBody = new AppointmentEnquiryBody();
        enquiryBody.setReqType(Constants.ALL_RESOURCE_LIST);
        enquiryBody.setMerId(sharedpreferences_sessionToken.getString(LoginActivity.MERID, ""));
        enquiryBody.callerType("m");
        enquiryBody.setDeviceId(sharedpreferences_sessionToken.getString(LoginActivity.DEVICEID, ""));
        enquiryBody.setSessionToken(sharedpreferences_sessionToken.getString(LoginActivity.SESSIONTOKEN, ""));
        boolean isConn = ConnectivityReceiver.isConnected();
        if (isConn) {
            try {
                intermediateAlertDialog = new IntermediateAlertDialog(SetupStaffActivity_Bottom.this);
                fetchStaff(enquiryBody);
            } catch (ApiException e) {
                e.printStackTrace();
            }
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    new AlertDialogFailure(SetupStaffActivity_Bottom.this, getResources().getString(R.string.no_internet_sub_title), "OK", getResources().getString(R.string.no_internet_title), getResources().getString(R.string.no_internet_Heading)) {
                        @Override
                        public void onButtonClick() {
                            callAPI_Staff();
                        }
                    };
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent in = new Intent(SetupStaffActivity_Bottom.this, DashboardActivity.class);
        startActivity(in);
        finish();
        overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);
    }

    private void fetchStaff(AppointmentEnquiryBody requestBody) throws ApiException {
        Log.d("fetchService--->", "fetchService: " + requestBody);
        OkHttpApiClient okHttpApiClient = new OkHttpApiClient(SetupStaffActivity_Bottom.this);
        MerchantApisApi webMerchantApisApi = new MerchantApisApi();
        webMerchantApisApi.setApiClient(okHttpApiClient.getApiClient());

        webMerchantApisApi.merchantAppointmentEnquiryAsync(requestBody, new ApiCallback<AppointmentEnquiryResponse>() {
            @Override
            public void onFailure(ApiException e, int statusCode, Map<String, List<String>> responseHeaders) {
                Log.d("fetchStaff--->", "onFailure-" + e.getMessage());
                if (intermediateAlertDialog != null) {
                    intermediateAlertDialog.dismissAlertDialog();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new AlertDialogFailure(SetupStaffActivity_Bottom.this, getResources().getString(R.string.try_again), "OK", getResources().getString(R.string.server_error), "Failed") {
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
                Log.d("fetchStaff--->", "onSuccess-" + statusCode + "," + result + "," + result.getResources());
                if (Integer.parseInt(result.getStatusCode()) == 200) {
                    if (intermediateAlertDialog != null) {
                        intermediateAlertDialog.dismissAlertDialog();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (!result.getResources().isEmpty() && result.getResources() != null) {
                                textView_no_staff.setVisibility(View.GONE);
                                listView_staffs.setVisibility(View.VISIBLE);
                                staffListAdapter = new StaffListAdapter(SetupStaffActivity_Bottom.this, result.getResources());
                                listView_staffs.setAdapter(staffListAdapter);

                            } else {
                                textView_no_staff.setVisibility(View.VISIBLE);
                                listView_staffs.setVisibility(View.GONE);
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
                            new AlertDialogFailure(SetupStaffActivity_Bottom.this, "No staffs created", "OK", "", "Failed") {
                                @Override
                                public void onButtonClick() {

                                    onBackPressed();
                                }
                            };
                        }
                    });
                } else if (Integer.parseInt(result.getStatusCode()) == 501) {
                    if (intermediateAlertDialog != null) {
                        intermediateAlertDialog.dismissAlertDialog();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new AlertDialogFailure(SetupStaffActivity_Bottom.this, "Staff not found", "OK", "", "Failed") {
                                @Override
                                public void onButtonClick() {

                                    onBackPressed();
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
                            new AlertDialogFailure(SetupStaffActivity_Bottom.this, getResources().getString(R.string.try_again), "OK", "Invalid data", "Failed") {
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
                                    callAPI_Staff();
                                }
                            }.callLoginAPI(SetupStaffActivity_Bottom.this);
                        }
                    });

                } else {
                    if (intermediateAlertDialog != null) {
                        intermediateAlertDialog.dismissAlertDialog();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new AlertDialogFailure(SetupStaffActivity_Bottom.this, getResources().getString(R.string.try_again), "OK", getResources().getString(R.string.went_wrong), "Failed") {
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
            Intent in = new Intent(SetupStaffActivity_Bottom.this, AddStaffActivity.class);
            in.putExtra("page_id", "3");
            startActivity(in);
            // finish();
            overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);
        }
        return super.onOptionsItemSelected(item);
    }

}
