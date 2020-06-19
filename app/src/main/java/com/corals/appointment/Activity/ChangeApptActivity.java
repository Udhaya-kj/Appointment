package com.corals.appointment.Activity;

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
import android.view.View;
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
import com.corals.appointment.Client.ApiCallback;
import com.corals.appointment.Client.ApiException;
import com.corals.appointment.Client.OkHttpApiClient;
import com.corals.appointment.Client.api.MerchantApisApi;
import com.corals.appointment.Client.model.ApptTransactionBody;
import com.corals.appointment.Client.model.ApptTransactionResponse;
import com.corals.appointment.Dialogs.IntermediateAlertDialog;
import com.corals.appointment.R;
import com.corals.appointment.receiver.ConnectivityReceiver;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class ChangeApptActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    Spinner spinner_services, spinner_staffs;
    ImageView imageView_calendar;
    EditText editText_comment;
    Button button_changes_appt;
    public static TextView textView_appt_date;
    private SharedPreferences sharedpreferences_services, sharedpreferences_staffs;
    private ArrayList<String> service_name_list, service_dur_list;
    private ArrayList<String> staff_name_list, staff_mob_list;
    private ArrayList<String> list_time_slot;
    DatePickerDialog datePickerDialog;
    int Year, Month, Day, Hour, Minute;
    Calendar calendar;
    RecyclerView recyclerView;
    TextView textView_ser, textView_staff, textView_date, textView_time;
    LinearLayout linearLayout;
    private IntermediateAlertDialog intermediateAlertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_appt);

        Toolbar toolbar = findViewById(R.id.toolbar_change_appt);
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

        calendar = Calendar.getInstance();
        Year = calendar.get(Calendar.YEAR);
        Month = calendar.get(Calendar.MONTH);
        Day = calendar.get(Calendar.DAY_OF_MONTH);
        Hour = calendar.get(Calendar.HOUR_OF_DAY);
        Minute = calendar.get(Calendar.MINUTE);

        service_name_list = new ArrayList<>();
        service_dur_list = new ArrayList<>();
        staff_name_list = new ArrayList<>();
        staff_mob_list = new ArrayList<>();
        list_time_slot = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerview_change_appt_slots);
        spinner_services = findViewById(R.id.spinner_services);
        spinner_staffs = findViewById(R.id.spinner_staff);
        imageView_calendar = findViewById(R.id.image_calendar);
        textView_appt_date = findViewById(R.id.tv_appt_date);
        linearLayout = findViewById(R.id.layout_change_date);
        editText_comment = findViewById(R.id.et_comment);
        button_changes_appt = findViewById(R.id.button_apply_appt_changes);

        textView_ser = findViewById(R.id.text_service);
        textView_staff = findViewById(R.id.text_staff);
        textView_date = findViewById(R.id.text_appn_dt);
        textView_time = findViewById(R.id.text_appn_time);

        if (getIntent().getExtras() != null) {
            String service = getIntent().getStringExtra("service");
            String staff = getIntent().getStringExtra("staff");
            String appt_date = getIntent().getStringExtra("appt_date");
            String appt_time = getIntent().getStringExtra("appt_time");
            textView_ser.setText(service);
            textView_staff.setText(staff);
            textView_date.setText(appt_date);
            textView_time.setText(appt_time);
            textView_appt_date.setText(appt_date);
        }
        GridLayoutManager li = new GridLayoutManager(this, 2);
        //recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(li);
        recyclerView.setFocusable(false);
        sharedpreferences_services = getSharedPreferences(AddServiceAvailTimeActivity.MyPREFERENCES_SERVICES, Context.MODE_PRIVATE);
        sharedpreferences_staffs = getSharedPreferences(AddStaffActivity.MyPREFERENCES_STAFFS, Context.MODE_PRIVATE);
        String nameList = sharedpreferences_services.getString(AddServiceAvailTimeActivity.SERVICE_NAME, "");
        String mobList = sharedpreferences_services.getString(AddServiceAvailTimeActivity.SERVICE_DURATION, "");
        if (!TextUtils.isEmpty(nameList) && !TextUtils.isEmpty(mobList)) {
            service_name_list = new Gson().fromJson(nameList, new TypeToken<ArrayList<String>>() {
            }.getType());
            service_dur_list = new Gson().fromJson(mobList, new TypeToken<ArrayList<String>>() {
            }.getType());
        }

        if (service_name_list != null && !service_name_list.isEmpty()) {
            ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, service_name_list);
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner_services.setAdapter(arrayAdapter);
        } else {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ChangeApptActivity.this);
            alertDialogBuilder.setMessage("Set up service before reschedule appointment");
            alertDialogBuilder.setCancelable(false);
            alertDialogBuilder.setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            arg0.dismiss();
                            Intent in = new Intent(ChangeApptActivity.this, AddServiceActivity.class);
                            in.putExtra("page_id", "3");
                            startActivity(in);
                            finish();

                        }
                    });


            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
        String nameList_staff = sharedpreferences_staffs.getString(AddStaffActivity.NAME, "");
        String mobList_staff = sharedpreferences_staffs.getString(AddStaffActivity.MOBILE, "");
        if (!TextUtils.isEmpty(nameList) && !TextUtils.isEmpty(mobList)) {
            staff_name_list = new Gson().fromJson(nameList_staff, new TypeToken<ArrayList<String>>() {
            }.getType());
            staff_mob_list = new Gson().fromJson(mobList_staff, new TypeToken<ArrayList<String>>() {
            }.getType());
        }

        //Log.d("staff_name_list--->", "onCreate: "+staff_name_list.size());
        if (staff_name_list != null && !staff_name_list.isEmpty()) {
            ArrayAdapter arrayAdapter_staff = new ArrayAdapter(this, android.R.layout.simple_spinner_item, staff_name_list);
            arrayAdapter_staff.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner_staffs.setAdapter(arrayAdapter_staff);
        } else {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ChangeApptActivity.this);
            alertDialogBuilder.setMessage("Set up staff before reschedule appointment");
            alertDialogBuilder.setCancelable(false);
            alertDialogBuilder.setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            arg0.dismiss();
                            Intent in = new Intent(ChangeApptActivity.this, AppointmentActivity.class);
                            startActivity(in);
                            finish();

                        }
                    });


            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }

        list_time_slot.add("08:00 am - 08:30 am");
        list_time_slot.add("12:00 pm - 12:30 pm");
        list_time_slot.add("02:30 pm - 02:45 pm");
        list_time_slot.add("06:00 pm - 06:30 pm");
        list_time_slot.add("06:30 pm - 07:00 pm");
        list_time_slot.add("07:00 pm - 07:30 pm");
        list_time_slot.add("07:30 pm - 08:00 pm");
        list_time_slot.add("08:00 pm - 08:30 pm");
        list_time_slot.add("08:30 pm - 09:00 pm");
        list_time_slot.add("09:00 pm - 09:30 pm");

        ChangeApptSlotRecycleAdapter changeApptSlotGridviewAdapter = new ChangeApptSlotRecycleAdapter(ChangeApptActivity.this, list_time_slot, textView_time.getText().toString().trim());
        recyclerView.setAdapter(changeApptSlotGridviewAdapter);

        changeApptSlotGridviewAdapter.notifyDataSetChanged();
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
                final ProgressDialog pd = new ProgressDialog(ChangeApptActivity.this);
                pd.setMessage("Rescheduling Appointment...");
                pd.show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pd.dismiss();
                        Intent i = new Intent(ChangeApptActivity.this, AppointmentActivity.class);
                        startActivity(i);
                        finish();
                        overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);
                    }
                }, 2000);
            }
        });

       /*         ApptTransactionBody transactionBody = new ApptTransactionBody();
        transactionBody.setReqType("TS-SR.CR");
        try {
            boolean isConn = ConnectivityReceiver.isConnected();
            if (isConn) {
                changeApptCustomer(transactionBody);
            } else {
                Toast.makeText(ChangeApptActivity.this, "No internet connection!", Toast.LENGTH_SHORT).show();
            }
        } catch (ApiException e) {
            e.printStackTrace();
        }*/

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(ChangeApptActivity.this, AppointmentActivity.class);
        startActivity(i);
        finish();
        overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
        textView_appt_date.setText(date);
    }

    private void changeApptCustomer(ApptTransactionBody requestBody) throws ApiException {
        Log.d("createStaff---", "createStaff: " + requestBody);
        intermediateAlertDialog = new IntermediateAlertDialog(ChangeApptActivity.this);
        OkHttpApiClient okHttpApiClient = new OkHttpApiClient(ChangeApptActivity.this);
        MerchantApisApi webMerchantApisApi = new MerchantApisApi();
        webMerchantApisApi.setApiClient(okHttpApiClient.getApiClient());

        webMerchantApisApi.merchantAppointmentTransactionAsync(requestBody, new ApiCallback<ApptTransactionResponse>() {
            @Override
            public void onFailure(ApiException e, int statusCode, Map<String, List<String>> responseHeaders) {
                Log.d("createStaff--->", "onFailure-" + e.getMessage());
                if (intermediateAlertDialog != null) {
                    intermediateAlertDialog.dismissAlertDialog();
                }
            }

            @Override
            public void onSuccess(ApptTransactionResponse result, int statusCode, Map<String, List<String>> responseHeaders) {
                Log.d("createStaff--->", "onSuccess-" + statusCode + "," + result.getStatusMessage());
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
