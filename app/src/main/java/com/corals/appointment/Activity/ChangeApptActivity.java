package com.corals.appointment.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.corals.appointment.Adapters.ChangeApptSlotRecycleAdapter;
import com.corals.appointment.Adapters.CustomStaffSpinnerAdapter;
import com.corals.appointment.Adapters.RecyclerAdapter_TimeSlots;
import com.corals.appointment.Adapters.StaffListAdapter;
import com.corals.appointment.Client.ApiCallback;
import com.corals.appointment.Client.ApiException;
import com.corals.appointment.Client.OkHttpApiClient;
import com.corals.appointment.Client.api.MerchantApisApi;
import com.corals.appointment.Client.model.AppointmentAvailableSlots;
import com.corals.appointment.Client.model.AppointmentEnquiryBody;
import com.corals.appointment.Client.model.AppointmentEnquiryResponse;
import com.corals.appointment.Client.model.AppointmentResources;
import com.corals.appointment.Client.model.ApptTransactionBody;
import com.corals.appointment.Client.model.ApptTransactionResponse;
import com.corals.appointment.Client.model.MapServiceResourceBody;
import com.corals.appointment.Constants.Constants;
import com.corals.appointment.Dialogs.AlertDialogFailure;
import com.corals.appointment.Dialogs.AlertDialogYesNo;
import com.corals.appointment.Dialogs.IntermediateAlertDialog;
import com.corals.appointment.Interface.ChangeApptCallback;
import com.corals.appointment.R;
import com.corals.appointment.Utils.CAllLoginAPI;
import com.corals.appointment.receiver.ConnectivityReceiver;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class ChangeApptActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, ChangeApptCallback {

    Spinner spinner_services, spinner_staffs;
    ImageView imageView_calendar;
    EditText editText_comment;
    Button button_changes_appt;
    public static TextView textView_appt_date;
    DatePickerDialog datePickerDialog;
    int Year, Month, Day, Hour, Minute;
    Calendar calendar;
    RecyclerView recyclerView;
    TextView textView_ser, textView_staff, textView_date, textView_time, textView_sel_staff_title;
    LinearLayout linearLayout, linearLayout_no_slots, linearLayout_select_staff;
    private IntermediateAlertDialog intermediateAlertDialog;
    private SharedPreferences sharedpreferences_sessionToken;
    String service_id, service, staff, appt_date, appt_time, appt_id, cus_id;
    public String startTime = "", endTime = "", slotNo = "";
    List<AppointmentResources> appointmentResources = new ArrayList<>();
    String res_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_appt);

        Toolbar toolbar = findViewById(R.id.toolbar_change_appt);
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

        calendar = Calendar.getInstance();
        Year = calendar.get(Calendar.YEAR);
        Month = calendar.get(Calendar.MONTH);
        Day = calendar.get(Calendar.DAY_OF_MONTH);
        Hour = calendar.get(Calendar.HOUR_OF_DAY);
        Minute = calendar.get(Calendar.MINUTE);

        recyclerView = findViewById(R.id.recyclerview_change_appt_slots);
        spinner_services = findViewById(R.id.spinner_services);
        spinner_staffs = findViewById(R.id.spinner_staff);
        imageView_calendar = findViewById(R.id.image_calendar);
        textView_appt_date = findViewById(R.id.tv_appt_date);
        linearLayout = findViewById(R.id.layout_change_date);
        editText_comment = findViewById(R.id.et_comment);
        button_changes_appt = findViewById(R.id.button_apply_appt_changes);
        linearLayout_no_slots = findViewById(R.id.layout_no_avail_slots);

        textView_ser = findViewById(R.id.text_service);
        textView_staff = findViewById(R.id.text_staff);
        textView_date = findViewById(R.id.text_appn_dt);
        textView_time = findViewById(R.id.text_appn_time);

        textView_sel_staff_title = findViewById(R.id.text_select_staff_title);
        linearLayout_select_staff = findViewById(R.id.layout_select_staff);
        sharedpreferences_sessionToken = getSharedPreferences(LoginActivity.MyPREFERENCES_SESSIONTOKEN, Context.MODE_PRIVATE);

        if (getIntent().getExtras() != null) {
            service_id = getIntent().getStringExtra("service_id");
            service = getIntent().getStringExtra("service");
            staff = getIntent().getStringExtra("staff");
            cus_id = getIntent().getStringExtra("cus_id");
            appt_date = getIntent().getStringExtra("appt_date");
            appt_time = getIntent().getStringExtra("appt_time");
            appt_id = getIntent().getStringExtra("appt_id");
            textView_ser.setText(service);
            textView_staff.setText(staff);
            textView_date.setText(appt_date);
            textView_time.setText(appt_time);
            textView_appt_date.setText(appt_date);

          /*  String[] strs = appt_time.split("-");
            startTime = strs[0];
            endTime = strs[1];*/

            Log.d("ChangeAppt---", "onCreate: " + service_id + "," + service + "," + staff + "," + cus_id + "," + appt_date + "," + startTime + "," + endTime + "," + appt_id);
        }
        GridLayoutManager li = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(li);
        recyclerView.setFocusable(false);

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String weekdays = "yyyyyyy";
                if (weekdays.length() != 0 && weekdays.length() == 7) {
                    datePickerDialog = DatePickerDialog.newInstance(ChangeApptActivity.this, Year, Month, Day);
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
                            //Toast.makeText(ChangeApptActivity.this, "Datepicker Canceled", Toast.LENGTH_SHORT).show();
                        }
                    });
                    datePickerDialog.show(getFragmentManager(), "DatePickerDialog");

                } else {
                    Toast.makeText(ChangeApptActivity.this, "Enter valid active weekdays", Toast.LENGTH_SHORT).show();
                }
            }
        });

        button_changes_appt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callAPI();
            }
        });

        callAPIFetchSlots(appt_date);

        spinner_staffs.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                res_id = appointmentResources.get(position).getResId();
                Log.d("res_id---", "onItemSelected: " + res_id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void callAPI() {
        if (!TextUtils.isEmpty(startTime) && !TextUtils.isEmpty(endTime) && !TextUtils.isEmpty(slotNo)) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    new AlertDialogYesNo(ChangeApptActivity.this, "Reschedule Appointment?", "Are you sure, You want to reschedule this appointment?", "Yes", "No") {
                        @Override
                        public void onOKButtonClick() {

                            callChangeApptAPI();

                        }

                        @Override
                        public void onCancelButtonClick() {

                        }
                    };
                }
            });
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    new AlertDialogFailure(ChangeApptActivity.this, "Select appointment time", "OK", "", "Warning") {
                        @Override
                        public void onButtonClick() {

                        }
                    };
                }
            });
        }
    }

    private void callChangeApptAPI() {
        Log.d("ChangeAppt---", "onClick: " + appt_id + "," + appt_date + "," + service_id + "," + cus_id + "," + startTime + "," + endTime + "," + slotNo);
        ApptTransactionBody transactionBody = new ApptTransactionBody();
        transactionBody.setReqType(Constants.UPDATE_APPOINTMENT);
        transactionBody.setApptId(appt_id);
        transactionBody.setSerId(service_id);
        transactionBody.setResId(res_id);
        transactionBody.setCustId(cus_id);
        transactionBody.setDate(appt_date);
        transactionBody.setSlotNo(slotNo);
        transactionBody.setStartTime(startTime);
        transactionBody.setEndTime(endTime);
        transactionBody.setMerId(sharedpreferences_sessionToken.getString(LoginActivity.MERID, ""));
        transactionBody.setDeviceId(sharedpreferences_sessionToken.getString(LoginActivity.DEVICEID, ""));
        transactionBody.setSessionToken(sharedpreferences_sessionToken.getString(LoginActivity.SESSIONTOKEN, ""));
        try {
            Log.d("Token--->", "token: " + sharedpreferences_sessionToken.getString(LoginActivity.SESSIONTOKEN, ""));
            boolean isConn = ConnectivityReceiver.isConnected();
            if (isConn) {
                intermediateAlertDialog = new IntermediateAlertDialog(ChangeApptActivity.this);
                changeApptCustomer(transactionBody);
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new AlertDialogFailure(ChangeApptActivity.this, getResources().getString(R.string.no_internet_sub_title), "OK", getResources().getString(R.string.no_internet_title), getResources().getString(R.string.no_internet_Heading)) {
                            @Override
                            public void onButtonClick() {

                            }
                        };
                    }
                });
            }
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

    public void callAPIFetchSlots(String date) {
        AppointmentEnquiryBody enquiryBody = new AppointmentEnquiryBody();
        enquiryBody.setReqType(Constants.AVAILABLE_SLOTS);
        enquiryBody.setMerId(sharedpreferences_sessionToken.getString(LoginActivity.MERID, ""));
        enquiryBody.callerType("m");
        enquiryBody.setDate(date);
        enquiryBody.setSerId(service_id);
        enquiryBody.setDeviceId(sharedpreferences_sessionToken.getString(LoginActivity.DEVICEID, ""));
        enquiryBody.setSessionToken(sharedpreferences_sessionToken.getString(LoginActivity.SESSIONTOKEN, ""));
        boolean isConn = ConnectivityReceiver.isConnected();
        if (isConn) {
            try {
                intermediateAlertDialog = new IntermediateAlertDialog(ChangeApptActivity.this);
                fetchApptAvailSlots(enquiryBody);
            } catch (ApiException e) {
                e.printStackTrace();
            }
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    new AlertDialogFailure(ChangeApptActivity.this, getResources().getString(R.string.no_internet_sub_title), "OK", getResources().getString(R.string.no_internet_title), getResources().getString(R.string.no_internet_Heading)) {
                        @Override
                        public void onButtonClick() {
                            Intent i = new Intent(ChangeApptActivity.this, AppointmentActivity.class);
                            startActivity(i);
                            finish();
                            overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);
                        }
                    };
                }
            });
        }
    }

    public void callAPIFetchStaff() {

        AppointmentEnquiryBody enquiryBody = new AppointmentEnquiryBody();
        enquiryBody.setReqType(Constants.RESOURCE_LIST_API);
        enquiryBody.setMerId(sharedpreferences_sessionToken.getString(LoginActivity.MERID, ""));
        enquiryBody.callerType("m");
        enquiryBody.setSerId(service_id);
        enquiryBody.setDeviceId(sharedpreferences_sessionToken.getString(LoginActivity.DEVICEID, ""));
        enquiryBody.setSessionToken(sharedpreferences_sessionToken.getString(LoginActivity.SESSIONTOKEN, ""));
        boolean isConn = ConnectivityReceiver.isConnected();
        if (isConn) {
            try {
                intermediateAlertDialog = new IntermediateAlertDialog(ChangeApptActivity.this);
                fetchStaff(enquiryBody);
            } catch (ApiException e) {
                e.printStackTrace();
            }
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    new AlertDialogFailure(ChangeApptActivity.this, getResources().getString(R.string.no_internet_sub_title), "OK", getResources().getString(R.string.no_internet_title), getResources().getString(R.string.no_internet_Heading)) {
                        @Override
                        public void onButtonClick() {
                            callAPIFetchStaff();
                        }
                    };
                }
            });
        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    /*    Intent i = new Intent(ChangeApptActivity.this, AppointmentActivity.class);
        startActivity(i);
        finish();
        overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);*/
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String month = "";
        int mnth = monthOfYear + 1;
        if (Integer.toString(mnth).length() == 1) {
            month = "0" + mnth;
        } else {
            month = Integer.toString(mnth);
        }
        Log.d("month---", "createStaff: " + month);
        appt_date = year + "-" + month + "-" + dayOfMonth;
        textView_appt_date.setText(appt_date);

        startTime = "";
        endTime = "";
        slotNo = "";
        callAPIFetchSlots(appt_date);
    }

    private void changeApptCustomer(ApptTransactionBody requestBody) throws ApiException {
        Log.d("changeApptCustomer---", "createStaff: " + requestBody);
        intermediateAlertDialog = new IntermediateAlertDialog(ChangeApptActivity.this);
        OkHttpApiClient okHttpApiClient = new OkHttpApiClient(ChangeApptActivity.this);
        MerchantApisApi webMerchantApisApi = new MerchantApisApi();
        webMerchantApisApi.setApiClient(okHttpApiClient.getApiClient());

        webMerchantApisApi.merchantAppointmentTransactionAsync(requestBody, new ApiCallback<ApptTransactionResponse>() {
            @Override
            public void onFailure(ApiException e, int statusCode, Map<String, List<String>> responseHeaders) {
                Log.d("changeApptCustomer--->", "onFailure-" + e.getMessage());
                if (intermediateAlertDialog != null) {
                    intermediateAlertDialog.dismissAlertDialog();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new AlertDialogFailure(ChangeApptActivity.this, getResources().getString(R.string.try_again), "OK", getResources().getString(R.string.server_error), "Failed") {
                            @Override
                            public void onButtonClick() {
                              /*  startActivity(new Intent(ChangeApptActivity.this, AppointmentActivity.class));
                                finish();
                                overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);*/
                                onBackPressed();
                            }
                        };
                    }
                });
            }

            @Override
            public void onSuccess(final ApptTransactionResponse result, int statusCode, Map<String, List<String>> responseHeaders) {
                Log.d("changeApptCustomer--->", "onSuccess-" + result.getStatusCode() + "," + result.getStatusMessage());
                if (Integer.parseInt(result.getStatusCode()) == 200) {
                    if (intermediateAlertDialog != null) {
                        intermediateAlertDialog.dismissAlertDialog();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new AlertDialogFailure(ChangeApptActivity.this, "Appointment rescheduled successfully!", "OK", "", "Success") {
                                @Override
                                public void onButtonClick() {
                                    startActivity(new Intent(ChangeApptActivity.this, AppointmentActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                                    finish();
                                    overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);
                                }
                            };
                        }
                    });
                } else if (Integer.parseInt(result.getStatusCode()) == 303) {
                    if (intermediateAlertDialog != null) {
                        intermediateAlertDialog.dismissAlertDialog();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new AlertDialogFailure(ChangeApptActivity.this, "Staff not available for the selected date and time", "OK", "", "Failed") {
                                @Override
                                public void onButtonClick() {
                                    startActivity(new Intent(ChangeApptActivity.this, AppointmentActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                                    finish();
                                    overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);
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
                            new AlertDialogFailure(ChangeApptActivity.this, getResources().getString(R.string.try_again), "OK", "Invalid data", "Failed") {
                                @Override
                                public void onButtonClick() {
                                    startActivity(new Intent(ChangeApptActivity.this, AppointmentActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                                    finish();
                                    overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);
                                }
                            };
                        }
                    });
                } else if (Integer.parseInt(result.getStatusCode()) == 404) {
                    if (intermediateAlertDialog != null) {
                        intermediateAlertDialog.dismissAlertDialog();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new AlertDialogFailure(ChangeApptActivity.this, "Appointment does not exist", "OK", "", "Failed") {
                                @Override
                                public void onButtonClick() {
                                    startActivity(new Intent(ChangeApptActivity.this, AppointmentActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                                    finish();
                                    overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);
                                }
                            };
                        }
                    });
                } else if (Integer.parseInt(result.getStatusCode()) == 409) {
                    if (intermediateAlertDialog != null) {
                        intermediateAlertDialog.dismissAlertDialog();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new AlertDialogFailure(ChangeApptActivity.this, "Appointment reschedule failed!", "OK", "", "Failed") {
                                @Override
                                public void onButtonClick() {
                                    startActivity(new Intent(ChangeApptActivity.this, AppointmentActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                                    finish();
                                    overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);
                                }
                            };
                        }
                    });
                } else if (Integer.parseInt(result.getStatusCode()) == 503) {
                    if (intermediateAlertDialog != null) {
                        intermediateAlertDialog.dismissAlertDialog();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new AlertDialogFailure(ChangeApptActivity.this, "Selected time slot not available for booking!", "OK", "", "Failed") {
                                @Override
                                public void onButtonClick() {
                                 /*   startActivity(new Intent(ChangeApptActivity.this, AppointmentActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                                    finish();
                                    overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);*/
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
                                    callChangeApptAPI();
                                }
                            }.callLoginAPI(ChangeApptActivity.this);
                        }
                    });

                } else {
                    if (intermediateAlertDialog != null) {
                        intermediateAlertDialog.dismissAlertDialog();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new AlertDialogFailure(ChangeApptActivity.this, getResources().getString(R.string.try_again), "OK", getResources().getString(R.string.went_wrong), "Failed") {
                                @Override
                                public void onButtonClick() {
                                    startActivity(new Intent(ChangeApptActivity.this, AppointmentActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
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

    private void fetchApptAvailSlots(AppointmentEnquiryBody requestBody) throws ApiException {
        Log.d("fetchApptSlots---", "login: " + requestBody);
        OkHttpApiClient okHttpApiClient = new OkHttpApiClient(ChangeApptActivity.this);
        MerchantApisApi webMerchantApisApi = new MerchantApisApi();
        webMerchantApisApi.setApiClient(okHttpApiClient.getApiClient());

        webMerchantApisApi.merchantAppointmentEnquiryAsync(requestBody, new ApiCallback<AppointmentEnquiryResponse>() {
            @Override
            public void onFailure(ApiException e, int statusCode, Map<String, List<String>> responseHeaders) {
                Log.d("fetchApptSlots--->", "onFailure-" + e.getMessage());
                if (intermediateAlertDialog != null) {
                    intermediateAlertDialog.dismissAlertDialog();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new AlertDialogFailure(ChangeApptActivity.this, getResources().getString(R.string.try_again), "OK", getResources().getString(R.string.went_wrong), "Failed") {
                            @Override
                            public void onButtonClick() {
                           /*     startActivity(new Intent(ChangeApptActivity.this, AppointmentActivity.class));
                                finish();
                                overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);*/

                                onBackPressed();
                            }
                        };
                    }
                });
            }

            @Override
            public void onSuccess(final AppointmentEnquiryResponse result, int statusCode, Map<String, List<String>> responseHeaders) {

                Log.d("fetchApptSlots--->", "onSuccess-" + statusCode + "," + result);
                if (intermediateAlertDialog != null) {
                    intermediateAlertDialog.dismissAlertDialog();
                }
                if (Integer.parseInt(result.getStatusCode()) == 200) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            if (!result.getAvailAppointments().isEmpty() && result.getAvailAppointments() != null) {
                                recyclerView.setVisibility(View.VISIBLE);
                                linearLayout_no_slots.setVisibility(View.GONE);
                                ChangeApptSlotRecycleAdapter changeApptSlotGridviewAdapter = new ChangeApptSlotRecycleAdapter(ChangeApptActivity.this, appt_time, result.getAvailAppointments());
                                recyclerView.setAdapter(changeApptSlotGridviewAdapter);
                                changeApptSlotGridviewAdapter.notifyDataSetChanged();
                                callAPIFetchStaff();
                            } else {
                                recyclerView.setVisibility(View.GONE);
                                linearLayout_no_slots.setVisibility(View.VISIBLE);
                            }
                        }
                    });
                } else if (Integer.parseInt(result.getStatusCode()) == 404) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new AlertDialogFailure(ChangeApptActivity.this, getResources().getString(R.string.no_avail_slots), "OK", "", "Warning") {
                                @Override
                                public void onButtonClick() {

                                }
                            };
                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new AlertDialogFailure(ChangeApptActivity.this, getResources().getString(R.string.try_again), "OK", getResources().getString(R.string.went_wrong), "Failed") {
                                @Override
                                public void onButtonClick() {
                                   /* startActivity(new Intent(ChangeApptActivity.this, AppointmentActivity.class));
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

    private void fetchStaff(AppointmentEnquiryBody requestBody) throws ApiException {

        Log.d("fetchService--->", "fetchService: " + requestBody);
        OkHttpApiClient okHttpApiClient = new OkHttpApiClient(ChangeApptActivity.this);
        MerchantApisApi webMerchantApisApi = new MerchantApisApi();
        webMerchantApisApi.setApiClient(okHttpApiClient.getApiClient());

        webMerchantApisApi.merchantAppointmentEnquiryAsync(requestBody, new ApiCallback<AppointmentEnquiryResponse>() {
            @Override
            public void onFailure(ApiException e, int statusCode, Map<String, List<String>> responseHeaders) {
                Log.d("fetchStaff--->", "onFailure-" + e.getMessage());
                if (intermediateAlertDialog != null) {
                    intermediateAlertDialog.dismissAlertDialog();
                }
                textView_sel_staff_title.setVisibility(View.GONE);
                linearLayout_select_staff.setVisibility(View.GONE);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new AlertDialogFailure(ChangeApptActivity.this, getResources().getString(R.string.try_again), "OK", getResources().getString(R.string.went_wrong), "Failed") {
                            @Override
                            public void onButtonClick() {
                                /*startActivity(new Intent(ChangeApptActivity.this, AppointmentActivity.class));
                                finish();
                                overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);*/

                                onBackPressed();
                            }
                        };
                    }
                });
            }

            @Override
            public void onSuccess(final AppointmentEnquiryResponse result, int statusCode, Map<String, List<String>> responseHeaders) {

                Log.d("fetchStaff--->", "onSuccess-" + statusCode + "," + result + "," + result.getResources());
                if (intermediateAlertDialog != null) {
                    intermediateAlertDialog.dismissAlertDialog();
                }
                if (Integer.parseInt(result.getStatusCode()) == 200) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            appointmentResources = result.getResources();
                            if (!result.getResources().isEmpty() && result.getResources() != null) {

                                textView_sel_staff_title.setVisibility(View.VISIBLE);
                                linearLayout_select_staff.setVisibility(View.VISIBLE);

                                CustomStaffSpinnerAdapter staffSpinnerAdapter = new CustomStaffSpinnerAdapter(ChangeApptActivity.this, result.getResources());
                                spinner_staffs.setAdapter(staffSpinnerAdapter);
                            } else {
                                textView_sel_staff_title.setVisibility(View.GONE);
                                linearLayout_select_staff.setVisibility(View.GONE);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        new AlertDialogFailure(ChangeApptActivity.this, "Setup staff before reschedule appointment", "OK", "", "Failed") {
                                            @Override
                                            public void onButtonClick() {
                                                startActivity(new Intent(ChangeApptActivity.this, DashboardActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                                                finish();
                                                overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);
                                            }
                                        };
                                    }
                                });
                            }


                        }
                    });
                } else {
                    textView_sel_staff_title.setVisibility(View.GONE);
                    linearLayout_select_staff.setVisibility(View.GONE);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new AlertDialogFailure(ChangeApptActivity.this, getResources().getString(R.string.try_again), "OK", getResources().getString(R.string.went_wrong), "Failed") {
                                @Override
                                public void onButtonClick() {
                                   /* startActivity(new Intent(ChangeApptActivity.this, AppointmentActivity.class));
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
    public void slot_onClick(String slot_no, String start_time, String end_time) {
        slotNo = slot_no;
        startTime = start_time;
        endTime = end_time;

        Log.d("ChangeSlot--->", "slot_onClick: " + slotNo + "," + startTime + "," + endTime);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.finish_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_finish) {
            okButtonProcess();
        }
        return super.onOptionsItemSelected(item);
    }

    private void okButtonProcess() {
        if (!TextUtils.isEmpty(startTime) && !TextUtils.isEmpty(endTime) && !TextUtils.isEmpty(slotNo)) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    new AlertDialogYesNo(ChangeApptActivity.this, "Reschedule Appointment?", "Are you sure, You want to reschedule this appointment?", "Yes", "No") {
                        @Override
                        public void onOKButtonClick() {

                            Log.d("ChangeAppt---", "onClick: " + appt_id + "," + appt_date + "," + service_id + "," + cus_id + "," + startTime + "," + endTime + "," + slotNo);
                            ApptTransactionBody transactionBody = new ApptTransactionBody();
                            transactionBody.setReqType(Constants.UPDATE_APPOINTMENT);
                            transactionBody.setApptId(appt_id);
                            transactionBody.setSerId(service_id);
                            transactionBody.setResId(res_id);
                            transactionBody.setCustId(cus_id);
                            transactionBody.setDate(appt_date);
                            transactionBody.setSlotNo(slotNo);
                            transactionBody.setStartTime(startTime);
                            transactionBody.setEndTime(endTime);
                            transactionBody.setMerId(sharedpreferences_sessionToken.getString(LoginActivity.MERID, ""));
                            transactionBody.setDeviceId(sharedpreferences_sessionToken.getString(LoginActivity.DEVICEID, ""));
                            transactionBody.setSessionToken(sharedpreferences_sessionToken.getString(LoginActivity.SESSIONTOKEN, ""));
                            try {
                                Log.d("Token--->", "token: " + sharedpreferences_sessionToken.getString(LoginActivity.SESSIONTOKEN, ""));
                                boolean isConn = ConnectivityReceiver.isConnected();
                                if (isConn) {
                                    intermediateAlertDialog = new IntermediateAlertDialog(ChangeApptActivity.this);
                                    changeApptCustomer(transactionBody);
                                } else {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            new AlertDialogFailure(ChangeApptActivity.this, getResources().getString(R.string.no_internet_sub_title), "OK", getResources().getString(R.string.no_internet_title), getResources().getString(R.string.no_internet_Heading)) {
                                                @Override
                                                public void onButtonClick() {

                                                }
                                            };
                                        }
                                    });
                                }
                            } catch (ApiException e) {
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onCancelButtonClick() {

                        }
                    };
                }
            });
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    new AlertDialogFailure(ChangeApptActivity.this, "Select appointment time", "OK", "", "Warning") {
                        @Override
                        public void onButtonClick() {

                        }
                    };
                }
            });
        }
    }
}
