package com.corals.appointment.Activity;

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
import com.corals.appointment.Dialogs.AlertDialogFailure;
import com.corals.appointment.Dialogs.IntermediateAlertDialog;
import com.corals.appointment.R;
import com.corals.appointment.receiver.ConnectivityReceiver;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SetupServiceActivity_Bottom extends AppCompatActivity {
    TextView textView_no_ser;
    LinearLayout linearLayout_add_resource;
    private ListView listView_services;
    private ArrayList<String> service_name_list, service_dur_list;
    private SharedPreferences sharedpreferences_services;
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
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_left);
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
        linearLayout_add_resource = findViewById(R.id.layout_add_resource);
        listView_services = findViewById(R.id.listview_services);

        SharedPreferences preferences = getSharedPreferences(AddServiceActivity.MyPREFERENCES_SERVICE_DATA, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();

   /*     sharedpreferences_services = getSharedPreferences(AddServiceAvailTimeActivity.MyPREFERENCES_SERVICES, Context.MODE_PRIVATE);
        String nameList = sharedpreferences_services.getString(AddServiceAvailTimeActivity.SERVICE_NAME, "");
        String mobList = sharedpreferences_services.getString(AddServiceAvailTimeActivity.SERVICE_DURATION, "");
        if (!TextUtils.isEmpty(nameList) && !TextUtils.isEmpty(mobList)) {
            service_name_list = new Gson().fromJson(nameList, new TypeToken<ArrayList<String>>() {
            }.getType());
            service_dur_list = new Gson().fromJson(mobList, new TypeToken<ArrayList<String>>() {
            }.getType());
        }

        Log.d("listsize---->", "onCreate: " + service_name_list + "," + service_dur_list);
        if (service_name_list.size() != 0 && service_dur_list.size() != 0) {
            servicesAdapter = new ServicesAdapter(SetupServiceActivity_Bottom.this, service_name_list, service_dur_list);
            listView_services.setAdapter(servicesAdapter);
        }
*/

        AppointmentEnquiryBody enquiryBody = new AppointmentEnquiryBody();
        enquiryBody.setReqType("E-S.");
        enquiryBody.setMerId(sharedpreferences_sessionToken.getString(LoginActivity.MERID, ""));
        enquiryBody.callerType("m");
        enquiryBody.setDeviceId("c43cbfe00b37ae6133ca023484869d2c489a8974ba48fb3286aa058292d08f0e");
        enquiryBody.setSessionToken(sharedpreferences_sessionToken.getString(LoginActivity.SESSIONTOKEN, ""));
        boolean isConn = ConnectivityReceiver.isConnected();
        if (isConn) {
            try {
                intermediateAlertDialog = new IntermediateAlertDialog(SetupServiceActivity_Bottom.this);
                fetchServices(enquiryBody);
            } catch (ApiException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(SetupServiceActivity_Bottom.this, "No internet connection!", Toast.LENGTH_SHORT).show();
        }

      /*  linearLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(ResourceSetupActivity.this, MaterialDatePickerActivity.class);
                startActivity(in);
                finish();
            }
        });
        linearLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(ResourceSetupActivity.this, MaterialDatePickerActivity.class);
                startActivity(in);
                finish();
            }
        });

        linearLayout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(ResourceSetupActivity.this, MaterialDatePickerActivity.class);
                startActivity(in);
                finish();
            }
        });*/

        linearLayout_add_resource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(SetupServiceActivity_Bottom.this, AddServiceActivity.class);
                in.putExtra("page_id", "3");
                startActivity(in);
                finish();
                overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);
            }
        });


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
                        new AlertDialogFailure(SetupServiceActivity_Bottom.this, getResources().getString(R.string.try_again), "OK", getResources().getString(R.string.went_wrong),"Failed") {
                            @Override
                            public void onButtonClick() {
                                startActivity(new Intent(SetupServiceActivity_Bottom.this, DashboardActivity.class));
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
                                String ser_desc = appointmentServicesList.get(t).getSerDescription();
                                String ser_Id = appointmentServicesList.get(t).getSerId();
                                String ser_price = appointmentServicesList.get(t).getSerPrice();
                                boolean isIsShowCust = appointmentServicesList.get(t).isIsShowCust();
                                Log.d("service_data---", "run: "+ser_Id+","+ser_name+","+ser_desc+","+ser_dur+","+ser_price+","+isIsShowCust);

                                AppointmentService appointmentService = new AppointmentService();
                                appointmentService.setSerId(ser_Id);
                                appointmentService.setSerName(ser_name);
                                appointmentService.setSerDuration(ser_dur);
                                appointmentService.setSerDescription(ser_desc);
                                appointmentService.setSerPrice(ser_price);
                                appointmentService.setIsShowCust(isIsShowCust);
                                appointmentServices.add(appointmentService);

                            }
                            if (appointmentServices.size() != 0) {
                                textView_no_ser.setVisibility(View.GONE);
                                listView_services.setVisibility(View.VISIBLE);
                                servicesAdapter = new ServicesAdapter(SetupServiceActivity_Bottom.this, appointmentServices);
                                listView_services.setAdapter(servicesAdapter);

                           /*     ServicesRecyclerviewAdapter servicesRecyclerviewAdapter = new ServicesRecyclerviewAdapter(SetupServiceActivity_Bottom.this, service_name_list, service_dur_list);
                                recyclerView_services.setAdapter(servicesRecyclerviewAdapter);*/

                            } else {
                                textView_no_ser.setVisibility(View.VISIBLE);
                                listView_services.setVisibility(View.GONE);
                            }


                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new AlertDialogFailure(SetupServiceActivity_Bottom.this, getResources().getString(R.string.try_again), "OK", getResources().getString(R.string.went_wrong),"Failed") {
                                @Override
                                public void onButtonClick() {
                                    startActivity(new Intent(SetupServiceActivity_Bottom.this, DashboardActivity.class));
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
