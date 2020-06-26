package com.corals.appointment.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import com.corals.appointment.Dialogs.AlertDialogFailure;
import com.corals.appointment.Dialogs.IntermediateAlertDialog;
import com.corals.appointment.R;
import com.corals.appointment.receiver.ConnectivityReceiver;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class    SetupStaffActivity_Bottom extends AppCompatActivity {
    private ListView listView_staffs;
    private ArrayList<String> staff_name_list, staff_mob_list;
    private SharedPreferences sharedpreferences_staffs;
    StaffListAdapter staffListAdapter;
    LinearLayout linearLayout_add_staff;
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
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_left);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        sharedpreferences_sessionToken = getSharedPreferences(LoginActivity.MyPREFERENCES_SESSIONTOKEN, Context.MODE_PRIVATE);
        sharedpreferences_staffs = getSharedPreferences(AddStaffActivity.MyPREFERENCES_STAFFS, Context.MODE_PRIVATE);
        textView_no_staff = findViewById(R.id.tv_no_staffs);
        staff_name_list = new ArrayList<>();
        staff_mob_list = new ArrayList<>();
        listView_staffs = findViewById(R.id.listview_staffs);
        linearLayout_add_staff = findViewById(R.id.layout_add_staff);

        if (getIntent().getExtras() != null) {
            pageId = getIntent().getStringExtra("page_id");

        }

   /*     String nameList = sharedpreferences_staffs.getString(AddStaffActivity.NAME, "");
        String mobList = sharedpreferences_staffs.getString(AddStaffActivity.MOBILE, "");
        if (!TextUtils.isEmpty(nameList) && !TextUtils.isEmpty(mobList)) {
            staff_name_list = new Gson().fromJson(nameList, new TypeToken<ArrayList<String>>() {
            }.getType());
            staff_mob_list = new Gson().fromJson(mobList, new TypeToken<ArrayList<String>>() {
            }.getType());
        }

        if (staff_name_list.size() != 0) {
            staffListAdapter = new StaffListAdapter(SetupStaffActivity_Bottom.this, staff_name_list, staff_mob_list);
            listView_staffs.setAdapter(staffListAdapter);

        }*/

        linearLayout_add_staff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(SetupStaffActivity_Bottom.this, AddStaffActivity.class);
                in.putExtra("page_id", "3");
                startActivity(in);
                finish();
                overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);
            }
        });

        AppointmentEnquiryBody enquiryBody = new AppointmentEnquiryBody();
        enquiryBody.setReqType("E-R.");
        enquiryBody.setMerId(sharedpreferences_sessionToken.getString(LoginActivity.MERID, ""));
        enquiryBody.callerType("m");
        enquiryBody.setDeviceId("c43cbfe00b37ae6133ca023484869d2c489a8974ba48fb3286aa058292d08f0e");
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
            Toast.makeText(SetupStaffActivity_Bottom.this, "No internet connection!", Toast.LENGTH_SHORT).show();
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
                        new AlertDialogFailure(SetupStaffActivity_Bottom.this, getResources().getString(R.string.try_again), "OK", getResources().getString(R.string.went_wrong),"Failed") {
                            @Override
                            public void onButtonClick() {
                                startActivity(new Intent(SetupStaffActivity_Bottom.this, DashboardActivity.class));
                                finish();
                                overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);
                            }
                        };
                    }
                });
            }

            @Override
            public void onSuccess(final AppointmentEnquiryResponse result, int statusCode, Map<String, List<String>> responseHeaders) {

                Log.d("fetchStaff--->", "onSuccess-" + statusCode + "," + result+ "," + result.getResources());
                if (intermediateAlertDialog != null) {
                    intermediateAlertDialog.dismissAlertDialog();
                }
                if (Integer.parseInt(result.getStatusCode()) == 200) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //Toast.makeText(AppointmentActivity.this, ""+result.getStatusMessage(), Toast.LENGTH_SHORT).show();
                            List<AppointmentResources> appointmentResources = new ArrayList<>();
                            List<AppointmentResources> appointmentResourcesList = result.getResources();
                            for (int t = 0; t < appointmentResourcesList.size(); t++) {
                                String res_name = appointmentResourcesList.get(t).getResName();
                                String res_mob = appointmentResourcesList.get(t).getMobile();
                                String res_load = appointmentResourcesList.get(t).getManageableLoad();
                                String res_id = appointmentResourcesList.get(t).getResId();
                                List<MapServiceResourceBody> mapServiceResourceBodyList = appointmentResourcesList.get(t).getSerResMaps();
                                //boolean sameBussTime = appointmentResourcesList.get(t).isSameBussTime();
                                Log.d("staff_data---", "run: "+res_name+","+res_mob+","+res_load+","+res_id+","+mapServiceResourceBodyList);

                                AppointmentResources appointmentResources1 = new AppointmentResources();
                                appointmentResources1.setResId(res_id);
                                appointmentResources1.setResName(res_name);
                                appointmentResources1.setMobile(res_mob);
                                appointmentResources1.setManageableLoad(res_load);
                                appointmentResources1.setSameBussTime(true);
                                appointmentResources1.setSerResMaps(mapServiceResourceBodyList);
                                appointmentResources.add(appointmentResources1);
                            }
                            if (appointmentResources.size() != 0) {
                                textView_no_staff.setVisibility(View.GONE);
                                listView_staffs.setVisibility(View.VISIBLE);
                                staffListAdapter = new StaffListAdapter(SetupStaffActivity_Bottom.this, appointmentResources);
                                listView_staffs.setAdapter(staffListAdapter);


                            } else {
                                textView_no_staff.setVisibility(View.VISIBLE);
                                listView_staffs.setVisibility(View.GONE);
                            }


                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new AlertDialogFailure(SetupStaffActivity_Bottom.this, getResources().getString(R.string.try_again), "OK", getResources().getString(R.string.went_wrong),"Failed") {
                                @Override
                                public void onButtonClick() {
                                    startActivity(new Intent(SetupStaffActivity_Bottom.this, DashboardActivity.class));
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
}
