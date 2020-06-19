package com.corals.appointment.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.corals.appointment.Adapters.ServicesAdapter_Calender;
import com.corals.appointment.Client.ApiCallback;
import com.corals.appointment.Client.ApiException;
import com.corals.appointment.Client.OkHttpApiClient;
import com.corals.appointment.Client.api.MerchantApisApi;
import com.corals.appointment.Client.model.AppointmentEnquiryBody;
import com.corals.appointment.Client.model.AppointmentEnquiryResponse;
import com.corals.appointment.Dialogs.IntermediateAlertDialog;
import com.corals.appointment.R;
import com.corals.appointment.receiver.ConnectivityReceiver;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CalendarServicesActivity extends AppCompatActivity {

    ListView recyclerView_services;
    TextView textView_no_ser, textView_appt_dt;
    private ArrayList<String> service_name_list, service_dur_list;
    private SharedPreferences sharedpreferences_services;
    public static String cal_date = "", pageId;
    private IntermediateAlertDialog intermediateAlertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_services);

        Toolbar toolbar = findViewById(R.id.toolbar_select_ser);
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


        textView_appt_dt = findViewById(R.id.text_appn_dt);
        textView_no_ser = findViewById(R.id.tv_no_services);
        recyclerView_services = findViewById(R.id.recyclerview_services);

        if (getIntent().getExtras() != null) {
            cal_date = getIntent().getStringExtra("date");
            pageId = getIntent().getStringExtra("page_id");
            textView_appt_dt.setText(cal_date);
        }

        service_name_list = new ArrayList<>();
        service_dur_list = new ArrayList<>();

        sharedpreferences_services = getSharedPreferences(AddServiceAvailTimeActivity.MyPREFERENCES_SERVICES, Context.MODE_PRIVATE);
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
          /*  servicesAdapter = new ServicesAdapter_Calender(MaterialDatePickerActivity.this, service_name_list, service_dur_list);
            listView_services.setAdapter(servicesAdapter);*/
            textView_no_ser.setVisibility(View.GONE);
            recyclerView_services.setVisibility(View.VISIBLE);

            ServicesAdapter_Calender servicesAdapter_calender = new ServicesAdapter_Calender(CalendarServicesActivity.this, cal_date, service_name_list, service_dur_list);
            recyclerView_services.setAdapter(servicesAdapter_calender);

        } else {
            textView_no_ser.setVisibility(View.VISIBLE);
            recyclerView_services.setVisibility(View.GONE);
        }


       /* AppointmentEnquiryBody enquiryBody = new AppointmentEnquiryBody();
        enquiryBody.setReqType("");
        boolean isConn = ConnectivityReceiver.isConnected();
        if (isConn) {
            try {
                fetchServices(enquiryBody);
            } catch (ApiException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(CalendarServicesActivity.this, "No internet connection!", Toast.LENGTH_SHORT).show();
        }*/

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (!TextUtils.isEmpty(pageId) && pageId.equals("1")) {
            Intent i = new Intent(CalendarServicesActivity.this, AppointmentActivity.class);
            startActivity(i);
            finish();
            overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);
        } else if (!TextUtils.isEmpty(pageId) && pageId.equals("2")) {
            Intent i = new Intent(CalendarServicesActivity.this, CalendarViewActivity.class);
            startActivity(i);
            finish();
            overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);
        }
    }

    private void fetchServices(AppointmentEnquiryBody requestBody) throws ApiException {

        intermediateAlertDialog = new IntermediateAlertDialog(CalendarServicesActivity.this);

        Log.d("login---", "login: " + requestBody);
        OkHttpApiClient okHttpApiClient = new OkHttpApiClient(CalendarServicesActivity.this);
        MerchantApisApi webMerchantApisApi = new MerchantApisApi();
        webMerchantApisApi.setApiClient(okHttpApiClient.getApiClient());

        webMerchantApisApi.merchantAppointmentEnquiryAsync(requestBody, new ApiCallback<AppointmentEnquiryResponse>() {
            @Override
            public void onFailure(ApiException e, int statusCode, Map<String, List<String>> responseHeaders) {
                Log.d("fetchService--->", "onFailure-" + e.getMessage());
                if (intermediateAlertDialog != null) {
                    intermediateAlertDialog.dismissAlertDialog();
                }
            }

            @Override
            public void onSuccess(AppointmentEnquiryResponse result, int statusCode, Map<String, List<String>> responseHeaders) {

                Log.d("fetchService--->", "onSuccess-" + statusCode + "," + result.getStatusMessage());
                if (intermediateAlertDialog != null) {
                    intermediateAlertDialog.dismissAlertDialog();
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
