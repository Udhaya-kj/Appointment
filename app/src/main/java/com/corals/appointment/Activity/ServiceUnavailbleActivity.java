package com.corals.appointment.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.corals.appointment.Adapters.ServiceUnvailabilityServicesAdapter;
import com.corals.appointment.Adapters.ServicesAdapter_Calender;
import com.corals.appointment.Adapters.ServicesRecyclerviewAdapter;
import com.corals.appointment.Adapters.StaffLeaveAdapter;
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

public class ServiceUnavailbleActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView textView_no_ser,textView_ser_staff;
    private ArrayList<String> service_name_list, service_dur_list;
    private SharedPreferences sharedpreferences_services;
    private ArrayList<String> staff_name_list, staff_mob_list;
    private SharedPreferences sharedpreferences_staffs;
    String task;
    private IntermediateAlertDialog intermediateAlertDialog;
    private SharedPreferences sharedpreferences_sessionToken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_unavailble);

        Toolbar toolbar = findViewById(R.id.toolbar_service_unavail);
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
        if (getIntent().getExtras() != null) {
            task = getIntent().getStringExtra("task");
            if (task.equals("2")) {
                toolbar.setTitle("Staff Leave");
            }
        }
        textView_ser_staff = findViewById(R.id.text_ser_staff);
        textView_no_ser = findViewById(R.id.tv_no_services);
        recyclerView = findViewById(R.id.recyclerview_unavailability);
        LinearLayoutManager li = new LinearLayoutManager(ServiceUnavailbleActivity.this);
        recyclerView.setLayoutManager(li);

        service_name_list = new ArrayList<>();
        service_dur_list = new ArrayList<>();
        staff_name_list = new ArrayList<>();
        staff_mob_list = new ArrayList<>();

        sharedpreferences_services = getSharedPreferences(AddServiceAvailTimeActivity.MyPREFERENCES_SERVICES, Context.MODE_PRIVATE);
        sharedpreferences_staffs = getSharedPreferences(AddStaffActivity.MyPREFERENCES_STAFFS, Context.MODE_PRIVATE);

        if (task.equals("1")) {
            textView_ser_staff.setText("Select service");
            AppointmentEnquiryBody enquiryBody = new AppointmentEnquiryBody();
            enquiryBody.setReqType("E-S.");
            enquiryBody.setMerId(sharedpreferences_sessionToken.getString(LoginActivity.MERID, ""));
            enquiryBody.callerType("m");
            enquiryBody.setDeviceId("c43cbfe00b37ae6133ca023484869d2c489a8974ba48fb3286aa058292d08f0e");
            enquiryBody.setSessionToken(sharedpreferences_sessionToken.getString(LoginActivity.SESSIONTOKEN, ""));
            boolean isConn = ConnectivityReceiver.isConnected();
            if (isConn) {
                try {
                    intermediateAlertDialog = new IntermediateAlertDialog(ServiceUnavailbleActivity.this);
                    fetchServices(enquiryBody);
                } catch (ApiException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(ServiceUnavailbleActivity.this, "No internet connection!", Toast.LENGTH_SHORT).show();
            }
     /*       String nameList = sharedpreferences_services.getString(AddServiceAvailTimeActivity.SERVICE_NAME, "");
            String mobList = sharedpreferences_services.getString(AddServiceAvailTimeActivity.SERVICE_DURATION, "");
            if (!TextUtils.isEmpty(nameList) && !TextUtils.isEmpty(mobList)) {
                service_name_list = new Gson().fromJson(nameList, new TypeToken<ArrayList<String>>() {
                }.getType());
                service_dur_list = new Gson().fromJson(mobList, new TypeToken<ArrayList<String>>() {
                }.getType());
            }

            Log.d("listsize---->", "onCreate: " + service_name_list + "," + service_dur_list);
            if (service_name_list.size() != 0 && service_dur_list.size() != 0) {
                ServiceUnvailabilityServicesAdapter servicesRecyclerviewAdapter = new ServiceUnvailabilityServicesAdapter(ServiceUnavailbleActivity.this, service_name_list, service_dur_list);
                recyclerView.setAdapter(servicesRecyclerviewAdapter);

            } else {
                textView_no_ser.setText("No services created");
                textView_no_ser.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            }*/
        } else if (task.equals("2")) {
            textView_ser_staff.setText("Select staff");
          /*  String nameList1 = sharedpreferences_staffs.getString(AddStaffActivity.NAME, "");
            String mobList1 = sharedpreferences_staffs.getString(AddStaffActivity.MOBILE, "");
            if (!TextUtils.isEmpty(nameList1) && !TextUtils.isEmpty(mobList1)) {
                staff_name_list = new Gson().fromJson(nameList1, new TypeToken<ArrayList<String>>() {
                }.getType());
                staff_mob_list = new Gson().fromJson(mobList1, new TypeToken<ArrayList<String>>() {
                }.getType());
            }

            if (staff_name_list.size() != 0) {
                StaffLeaveAdapter staffListAdapter = new StaffLeaveAdapter(ServiceUnavailbleActivity.this, staff_name_list);
                recyclerView.setAdapter(staffListAdapter);

            } else {
                textView_no_ser.setText("No staffs created");
                textView_no_ser.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            }*/

            AppointmentEnquiryBody enquiryBody = new AppointmentEnquiryBody();
            enquiryBody.setReqType("E-R.");
            enquiryBody.setMerId(sharedpreferences_sessionToken.getString(LoginActivity.MERID, ""));
            enquiryBody.callerType("m");
            enquiryBody.setDeviceId("c43cbfe00b37ae6133ca023484869d2c489a8974ba48fb3286aa058292d08f0e");
            enquiryBody.setSessionToken(sharedpreferences_sessionToken.getString(LoginActivity.SESSIONTOKEN, ""));
            boolean isConn = ConnectivityReceiver.isConnected();
            if (isConn) {
                try {
                    intermediateAlertDialog = new IntermediateAlertDialog(ServiceUnavailbleActivity.this);
                    fetchStaff(enquiryBody);
                } catch (ApiException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(ServiceUnavailbleActivity.this, "No internet connection!", Toast.LENGTH_SHORT).show();
            }
        }




    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent i = new Intent(ServiceUnavailbleActivity.this, SettingsActivity.class);
        startActivity(i);
        finish();
        overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);
    }

    private void fetchServices(AppointmentEnquiryBody requestBody) throws ApiException {



        Log.d("login---", "login: " + requestBody);
        OkHttpApiClient okHttpApiClient = new OkHttpApiClient(ServiceUnavailbleActivity.this);
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
                        new AlertDialogFailure(ServiceUnavailbleActivity.this, getResources().getString(R.string.try_again), "OK", getResources().getString(R.string.went_wrong), "Failed") {
                            @Override
                            public void onButtonClick() {
                                startActivity(new Intent(ServiceUnavailbleActivity.this, DashboardActivity.class));
                                finish();
                                overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);
                            }
                        };
                    }
                });
            }

            @Override
            public void onSuccess(final AppointmentEnquiryResponse result, int statusCode, Map<String, List<String>> responseHeaders) {

                Log.d("fetchService--->", "onSuccess-" + statusCode + "," + result);
                if (intermediateAlertDialog != null) {
                    intermediateAlertDialog.dismissAlertDialog();
                }
                if (Integer.parseInt(result.getStatusCode()) == 200) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //Toast.makeText(AppointmentActivity.this, ""+result.getStatusMessage(), Toast.LENGTH_SHORT).show();
                            List<AppointmentService> appointmentServices = new ArrayList<>();
                            List<AppointmentService> appointmentServicesList = result.getServices();
                            for (int t = 0; t < appointmentServicesList.size(); t++) {
                                String ser_name = appointmentServicesList.get(t).getSerName();
                                String ser_dur = appointmentServicesList.get(t).getSerDuration();
                                String ser_Id = appointmentServicesList.get(t).getSerId();

                                AppointmentService appointmentService = new AppointmentService();
                                appointmentService.setSerId(ser_Id);
                                appointmentService.setSerName(ser_name);
                                appointmentService.setSerDuration(ser_dur);
                                appointmentServices.add(appointmentService);
                            }
                            if (!appointmentServices.isEmpty()) {
                                textView_no_ser.setVisibility(View.GONE);
                                recyclerView.setVisibility(View.VISIBLE);
                                ServicesRecyclerviewAdapter servicesRecyclerviewAdapter = new ServicesRecyclerviewAdapter(ServiceUnavailbleActivity.this,"5", appointmentServices);
                                recyclerView.setAdapter(servicesRecyclerviewAdapter);

                            } else {
                                textView_no_ser.setText("No services created");
                                textView_no_ser.setVisibility(View.VISIBLE);
                                recyclerView.setVisibility(View.GONE);
                            }


                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new AlertDialogFailure(ServiceUnavailbleActivity.this, getResources().getString(R.string.try_again), "OK", getResources().getString(R.string.went_wrong), "Failed") {
                                @Override
                                public void onButtonClick() {
                                    startActivity(new Intent(ServiceUnavailbleActivity.this, DashboardActivity.class));
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


    private void fetchStaff(AppointmentEnquiryBody requestBody) throws ApiException {

        Log.d("fetchService--->", "fetchService: " + requestBody);
        OkHttpApiClient okHttpApiClient = new OkHttpApiClient(ServiceUnavailbleActivity.this);
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
                        new AlertDialogFailure(ServiceUnavailbleActivity.this, getResources().getString(R.string.try_again), "OK", getResources().getString(R.string.went_wrong),"Failed") {
                            @Override
                            public void onButtonClick() {
                                startActivity(new Intent(ServiceUnavailbleActivity.this, DashboardActivity.class));
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
                            if (!appointmentResources.isEmpty() && appointmentResources!=null) {
                                StaffLeaveAdapter staffListAdapter = new StaffLeaveAdapter(ServiceUnavailbleActivity.this, appointmentResources);
                                recyclerView.setAdapter(staffListAdapter);


                            } else {
                                textView_no_ser.setText("No staffs created");
                                textView_no_ser.setVisibility(View.VISIBLE);
                                recyclerView.setVisibility(View.GONE);
                            }

                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new AlertDialogFailure(ServiceUnavailbleActivity.this, getResources().getString(R.string.try_again), "OK", getResources().getString(R.string.went_wrong),"Failed") {
                                @Override
                                public void onButtonClick() {
                                    startActivity(new Intent(ServiceUnavailbleActivity.this, DashboardActivity.class));
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
