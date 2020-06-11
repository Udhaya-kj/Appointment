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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.corals.appointment.Adapters.ChangeApptSlotRecycleAdapter;
import com.corals.appointment.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;

public class ChangeApptActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    Spinner spinner_services, spinner_staffs;
    ImageView imageView_calendar;
    EditText editText_comment;
    Button button_changes_appt;
    public static TextView textView_appt_date, textView_appt_slot;
    private SharedPreferences sharedpreferences_services, sharedpreferences_staffs;
    private ArrayList<String> service_name_list, service_dur_list;
    private ArrayList<String> staff_name_list, staff_mob_list;
    private ArrayList<String> list_time_slot;
    DatePickerDialog datePickerDialog;
    int Year, Month, Day, Hour, Minute;
    Calendar calendar;
    RecyclerView recyclerView;

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
        textView_appt_slot = findViewById(R.id.tv_appt_slot);
        editText_comment = findViewById(R.id.et_comment);
        button_changes_appt = findViewById(R.id.button_apply_appt_changes);

        GridLayoutManager li = new GridLayoutManager(this, 2);
        //recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(li);
        sharedpreferences_services = getSharedPreferences(AddServiceActivity.MyPREFERENCES_SERVICES, Context.MODE_PRIVATE);
        sharedpreferences_staffs = getSharedPreferences(AddStaffActivity.MyPREFERENCES_STAFFS, Context.MODE_PRIVATE);
        String nameList = sharedpreferences_services.getString(AddServiceActivity.SERVICE_NAME, "");
        String mobList = sharedpreferences_services.getString(AddServiceActivity.SERVICE_DURATION, "");
        if (!TextUtils.isEmpty(nameList) && !TextUtils.isEmpty(mobList)) {
            service_name_list = new Gson().fromJson(nameList, new TypeToken<ArrayList<String>>() {
            }.getType());
            service_dur_list = new Gson().fromJson(mobList, new TypeToken<ArrayList<String>>() {
            }.getType());
        }

        if (service_name_list !=null && !service_name_list.isEmpty()) {
            ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, service_name_list);
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner_services.setAdapter(arrayAdapter);
        }
        else {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ChangeApptActivity.this);
            alertDialogBuilder.setMessage("Set up service before change appointment");
            alertDialogBuilder.setCancelable(false);
            alertDialogBuilder.setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            arg0.dismiss();
                            Intent in = new Intent(ChangeApptActivity.this, AddServiceActivity.class);
                            in.putExtra("page_id","3");
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
        if (staff_name_list !=null && !staff_name_list.isEmpty()) {
            ArrayAdapter arrayAdapter_staff = new ArrayAdapter(this, android.R.layout.simple_spinner_item, staff_name_list);
            arrayAdapter_staff.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner_staffs.setAdapter(arrayAdapter_staff);
        }
        else {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ChangeApptActivity.this);
            alertDialogBuilder.setMessage("Set up staff before change appointment");
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

        list_time_slot.add("9 am - 9.15 am");
        list_time_slot.add("9.15 am - 9.30 am");
        list_time_slot.add("9.30 am - 9.45 am");
        list_time_slot.add("9.45 am - 10 am");
        list_time_slot.add("10 am - 10.15 am");
        list_time_slot.add("10.15 am - 10.30 am");
        list_time_slot.add("10.30 am - 10.45 am");
        list_time_slot.add("10.45 am - 11 am");
        list_time_slot.add("11.15 am - 11.30 am");
        list_time_slot.add("11.30 am - 11.45 am");

        ChangeApptSlotRecycleAdapter changeApptSlotGridviewAdapter = new ChangeApptSlotRecycleAdapter(ChangeApptActivity.this, list_time_slot);
        recyclerView.setAdapter(changeApptSlotGridviewAdapter);

        imageView_calendar.setOnClickListener(new View.OnClickListener() {
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

                            Toast.makeText(ChangeApptActivity.this, "Datepicker Canceled", Toast.LENGTH_SHORT).show();
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
                pd.setMessage("Changing Appointment...");
                pd.show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pd.dismiss();
                        Intent i = new Intent(ChangeApptActivity.this, ViewApptServiceActivity.class);
                        startActivity(i);
                        finish();
                    }
                }, 2000);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
        textView_appt_date.setText(date);
    }
}
