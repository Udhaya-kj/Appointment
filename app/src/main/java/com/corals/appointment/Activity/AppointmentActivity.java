package com.corals.appointment.Activity;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.corals.appointment.Adapters.ServicesAdapter_Calender;
import com.corals.appointment.Adapters.ServicesMainRecyclerviewAdapter;
import com.corals.appointment.Adapters.ServicesRecyclerviewAdapter;
import com.corals.appointment.Client.ApiCallback;
import com.corals.appointment.Client.ApiException;
import com.corals.appointment.Client.OkHttpApiClient;
import com.corals.appointment.Client.api.MerchantApisApi;
import com.corals.appointment.Client.model.AppointmentEnquiryBody;
import com.corals.appointment.Client.model.AppointmentEnquiryResponse;
import com.corals.appointment.Client.model.AppointmentService;
import com.corals.appointment.Client.model.SecurityAPIBody;
import com.corals.appointment.Client.model.SecurityAPIResponse;
import com.corals.appointment.Constants.Constants;
import com.corals.appointment.Dialogs.AlertDialogFailure;
import com.corals.appointment.Dialogs.IntermediateAlertDialog;
import com.corals.appointment.Interface.AdapterCallback;
import com.corals.appointment.Interface.OnItemClick_Main_Services;
import com.corals.appointment.R;
import com.corals.appointment.Utils.CAllLoginAPI;
import com.corals.appointment.receiver.ConnectivityReceiver;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wdullaer.materialdatetimepicker.Utils;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class AppointmentActivity extends AppCompatActivity implements AdapterCallback, OnItemClick_Main_Services {

    DatePickerDialog datePickerDialog;
    int Year, Month, Day, Hour, Minute;
    Calendar calendar;
    RecyclerView recyclerView_services;
    TextView textView_no_ser;
    String calendar_date;
    private IntermediateAlertDialog intermediateAlertDialog;
    private SharedPreferences sharedpreferences_sessionToken;
    Menu menu;
    private LinearLayout linearLayout_bottom;
    Button button_appts, button_book;
    public String ser_id, service, service_dur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        Toolbar toolbar = findViewById(R.id.toolbar_biz_appt_date);
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
        calendar = Calendar.getInstance();
        Year = calendar.get(Calendar.YEAR);
        Month = calendar.get(Calendar.MONTH);
        Day = calendar.get(Calendar.DAY_OF_MONTH);
        Hour = calendar.get(Calendar.HOUR_OF_DAY);
        Minute = calendar.get(Calendar.MINUTE);

        button_appts = findViewById(R.id.button_appointment);
        button_book = findViewById(R.id.button_book_appointment);
        linearLayout_bottom = findViewById(R.id.layout_bottom_options);
        recyclerView_services = findViewById(R.id.recyclerview_services);

        LinearLayoutManager li = new LinearLayoutManager(AppointmentActivity.this);
        recyclerView_services.setLayoutManager(li);
        //recyclerView_services.addItemDecoration(new DividerItemDecoration(recyclerView_services.getContext(), li.getOrientation()));
        textView_no_ser = findViewById(R.id.tv_no_services);

        button_appts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AppointmentActivity.this, ViewApptServiceActivity.class);
                i.putExtra("service_id", ser_id);
                i.putExtra("service", service);
                i.putExtra("service_dur", service_dur);
                startActivity(i);
                //finish();
                overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);
            }
        });
        button_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AppointmentActivity.this, CalendarViewActivity.class);
                i.putExtra("service_id", ser_id);
                i.putExtra("service", service);
                i.putExtra("service_dur", service_dur);
                i.putExtra("page_id", "1");
                startActivity(i);
                //finish();
                overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);
            }
        });

        callAPI();
    }

    private void callAPI() {
        AppointmentEnquiryBody enquiryBody = new AppointmentEnquiryBody();
        enquiryBody.setReqType(Constants.SERVICES_LIST_API);
        enquiryBody.setMerId(sharedpreferences_sessionToken.getString(LoginActivity.MERID, ""));
        enquiryBody.callerType("m");
        enquiryBody.setDeviceId(sharedpreferences_sessionToken.getString(LoginActivity.DEVICEID, ""));
        enquiryBody.setSessionToken(sharedpreferences_sessionToken.getString(LoginActivity.SESSIONTOKEN, ""));
        boolean isConn = ConnectivityReceiver.isConnected();
        if (isConn) {
            try {
                intermediateAlertDialog = new IntermediateAlertDialog(AppointmentActivity.this);
                fetchServices(enquiryBody);
            } catch (ApiException e) {
                e.printStackTrace();
            }
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    new AlertDialogFailure(AppointmentActivity.this, getResources().getString(R.string.no_internet_sub_title), "OK", getResources().getString(R.string.no_internet_title), getResources().getString(R.string.no_internet_heading)) {
                        @Override
                        public void onButtonClick() {
                            callAPI();
                        }
                    };
                }
            });
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(AppointmentActivity.this, DashboardActivity.class);
        startActivity(i);
        finish();
        overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);
    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }*/

  /*  @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            //Back button
            case R.id.action_calendar:
                String weekdays = "yyyyyyy";
                if (weekdays.length() != 0 && weekdays.length() == 7) {
                    datePickerDialog = DatePickerDialog.newInstance(AppointmentActivity.this, Year, Month, Day);
                    datePickerDialog.setThemeDark(false);
                    datePickerDialog.showYearPickerFirst(false);
                    datePickerDialog.setTitle("Select Appointment Date");

                    // Setting Min Date to today date
                    Calendar min_date_c = Calendar.getInstance();
                    datePickerDialog.setMinDate(min_date_c);

                    // Setting Max Date to next 2 years
                    Calendar max_date_c = Calendar.getInstance();
                    max_date_c.set(Calendar.YEAR, Year + 2);
                    datePickerDialog.setMaxDate(max_date_c);


                    //Disable all SUNDAYS and SATURDAYS between Min and Max Dates
                    for (Calendar loopdate = min_date_c; min_date_c.before(max_date_c); min_date_c.add(Calendar.DATE, 1), loopdate = min_date_c) {
                        int dayOfWeek = loopdate.get(Calendar.DAY_OF_WEEK);
                        String sun = String.valueOf(weekdays.charAt(0));
                        String mon = String.valueOf(weekdays.charAt(1));
                        String tue = String.valueOf(weekdays.charAt(2));
                        String wed = String.valueOf(weekdays.charAt(3));
                        String thu = String.valueOf(weekdays.charAt(4));
                        String fri = String.valueOf(weekdays.charAt(5));
                        String sat = String.valueOf(weekdays.charAt(6));
                        Log.d("weekdays---", "onClick: " + sun + "," + mon + "," + tue + "," + wed + "," + thu + "," + fri + "," + sat);
                        if ((sun.equals("n") ? dayOfWeek == Calendar.SUNDAY : dayOfWeek == 0) || (mon.equals("n") ? dayOfWeek == Calendar.MONDAY : dayOfWeek == 0) || (tue.equals("n") ? dayOfWeek == Calendar.TUESDAY : dayOfWeek == 0) || (wed.equals("n") ? dayOfWeek == Calendar.WEDNESDAY : dayOfWeek == 0) || (thu.equals("n") ? dayOfWeek == Calendar.THURSDAY : dayOfWeek == 0) || (fri.equals("n") ? dayOfWeek == Calendar.FRIDAY : dayOfWeek == 0) || (sat.equals("n") ? dayOfWeek == Calendar.SATURDAY : dayOfWeek == 0)) {
                            Calendar[] disabledDays = new Calendar[1];
                            disabledDays[0] = loopdate;
                            datePickerDialog.setDisabledDays(disabledDays);
                        }
                    }

                    datePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

                        @Override
                        public void onCancel(DialogInterface dialogInterface) {

                            //Toast.makeText(AppointmentActivity.this, "Datepicker Canceled", Toast.LENGTH_SHORT).show();
                        }
                    });

                    datePickerDialog.show(getFragmentManager(), "DatePickerDialog");

                } else {
                    Toast.makeText(AppointmentActivity.this, "Enter valid active weekdays", Toast.LENGTH_SHORT).show();
                }
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }*/

 /*   @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.action_calendar);

        if (service_name_list.size() != 0) {
            item.setEnabled(true);
            item.getIcon().setAlpha(255);
        } else {
            // disabled
            item.setEnabled(false);
            item.getIcon().setAlpha(130);
        }
        return true;
    }*/

    private void fetchServices(AppointmentEnquiryBody requestBody) throws ApiException {
        Log.d("login---", "login: " + requestBody);
        OkHttpApiClient okHttpApiClient = new OkHttpApiClient(AppointmentActivity.this);
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
                        new AlertDialogFailure(AppointmentActivity.this, getResources().getString(R.string.try_again), "OK", getResources().getString(R.string.went_wrong), "Failed") {
                            @Override
                            public void onButtonClick() {
                                startActivity(new Intent(AppointmentActivity.this, DashboardActivity.class));
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

                if (Integer.parseInt(result.getStatusCode()) == 200) {
                    if (intermediateAlertDialog != null) {
                        intermediateAlertDialog.dismissAlertDialog();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            if (!result.getServices().isEmpty() && result.getServices() != null) {
                                textView_no_ser.setVisibility(View.GONE);
                                recyclerView_services.setVisibility(View.VISIBLE);
                                /*ServicesRecyclerviewAdapter servicesRecyclerviewAdapter = new ServicesRecyclerviewAdapter(AppointmentActivity.this, "1", result.getServices());
                                recyclerView_services.setAdapter(servicesRecyclerviewAdapter);*/

                                ServicesMainRecyclerviewAdapter servicesRecyclerviewAdapter = new ServicesMainRecyclerviewAdapter(AppointmentActivity.this, result.getServices());
                                recyclerView_services.setAdapter(servicesRecyclerviewAdapter);

                            } else {
                                //menu.findItem(R.id.action_calendar).setEnabled(false);
                                textView_no_ser.setVisibility(View.VISIBLE);
                                recyclerView_services.setVisibility(View.GONE);
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
                            new AlertDialogFailure(AppointmentActivity.this, "No services created!", "OK","", "Failed") {
                                @Override
                                public void onButtonClick() {
                                    startActivity(new Intent(AppointmentActivity.this, DashboardActivity.class));
                                    finish();
                                    overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);
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
                            new AlertDialogFailure(AppointmentActivity.this, getResources().getString(R.string.try_again), "OK", "Invalid data", "Failed") {
                                @Override
                                public void onButtonClick() {
                                    startActivity(new Intent(AppointmentActivity.this, DashboardActivity.class));
                                    finish();
                                    overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);
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
                            }.callLoginAPI(AppointmentActivity.this);
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
                            new AlertDialogFailure(AppointmentActivity.this, getResources().getString(R.string.try_again), "OK", getResources().getString(R.string.went_wrong), "Failed") {
                                @Override
                                public void onButtonClick() {
                                    startActivity(new Intent(AppointmentActivity.this, DashboardActivity.class));
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
    public void onDestroy() {
        super.onDestroy();
        if (intermediateAlertDialog != null) {
            intermediateAlertDialog.dismissAlertDialog();
            intermediateAlertDialog = null;
        }
    }

    @Override
    public void onMethodCallback() {
        button_book.setEnabled(true);
        button_appts.setEnabled(true);
    }

    @Override
    public void onClick(String id, String ser, String ser_dur) {
        ser_id = id;
        service = ser;
        service_dur = ser_dur;
        Log.d("Data---", "onClick: " + ser_id + "," + service);
    }
}
