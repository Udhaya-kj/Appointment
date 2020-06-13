package com.corals.appointment.Activity;


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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.corals.appointment.Adapters.ServicesAdapter_Calender;
import com.corals.appointment.Adapters.ServicesRecyclerviewAdapter;
import com.corals.appointment.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;

public class AppointmentActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;
    int Year, Month, Day, Hour, Minute;
    Calendar calendar;
    EditText textView;
    String res;
    ListView listView_services;
    RecyclerView recyclerView_services;
    private ArrayList<String> service_name_list, service_dur_list;
    private SharedPreferences sharedpreferences_services;
    ServicesAdapter_Calender servicesAdapter;
    TextView textView_no_ser, textView_cal_next;
    CalendarView calendarView;
    String calendar_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        Toolbar toolbar = findViewById(R.id.toolbar_biz_appt_date);
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

        calendarView = findViewById(R.id.calendarView);
        listView_services = findViewById(R.id.listview_services);
        recyclerView_services = findViewById(R.id.recyclerview_services);

        LinearLayoutManager li = new LinearLayoutManager(AppointmentActivity.this);
        recyclerView_services.setLayoutManager(li);

        textView = findViewById(R.id.text_weekdays);
        textView_no_ser = findViewById(R.id.tv_no_services);
        textView_cal_next = findViewById(R.id.text_calendar_next);
        textView.setSelection(7);

        service_name_list = new ArrayList<>();
        service_dur_list = new ArrayList<>();


        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {

                calendar_date = dayOfMonth + "-" + (month + 1) + "-" + year;
                textView_cal_next.setEnabled(true);
                // Toast.makeText(MaterialDatePickerActivity.this, "" + dayOfMonth + "-" + (month + 1) + "-" + year, Toast.LENGTH_SHORT).show();
            }
        });

        textView_cal_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AppointmentActivity.this, CalendarServicesActivity.class);
                i.putExtra("date", calendar_date);
                startActivity(i);
                finish();
                // Toast.makeText(MaterialDatePickerActivity.this, ""+calendar_date, Toast.LENGTH_SHORT).show();
            }
        });

        sharedpreferences_services = getSharedPreferences(AddServiceActivity.MyPREFERENCES_SERVICES, Context.MODE_PRIVATE);
        String nameList = sharedpreferences_services.getString(AddServiceActivity.SERVICE_NAME, "");
        String mobList = sharedpreferences_services.getString(AddServiceActivity.SERVICE_DURATION, "");
        if (!TextUtils.isEmpty(nameList) && !TextUtils.isEmpty(mobList)) {
            service_name_list = new Gson().fromJson(nameList, new TypeToken<ArrayList<String>>() {
            }.getType());
            service_dur_list = new Gson().fromJson(mobList, new TypeToken<ArrayList<String>>() {
            }.getType());
        }

        Log.d("listsize---->", "onCreate: " + service_name_list + "," + service_dur_list);
        if (service_name_list.size() != 0 && service_dur_list.size() != 0) {
            servicesAdapter = new ServicesAdapter_Calender(AppointmentActivity.this, "",service_name_list, service_dur_list);
            listView_services.setAdapter(servicesAdapter);
            textView_no_ser.setVisibility(View.GONE);
            recyclerView_services.setVisibility(View.VISIBLE);

            ServicesRecyclerviewAdapter servicesRecyclerviewAdapter = new ServicesRecyclerviewAdapter(AppointmentActivity.this, service_name_list, service_dur_list);
            recyclerView_services.setAdapter(servicesRecyclerviewAdapter);

        } else {
            textView_no_ser.setVisibility(View.VISIBLE);
            recyclerView_services.setVisibility(View.GONE);
        }


        final Button button_datepicker = (Button) findViewById(R.id.button_datepicker);
        button_datepicker.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //make weekdays ex: yyyyyny
                //String weekdays = textView.getText().toString();
                String weekdays = "yyynyyy";
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
            }
        });


        final Button button_timepicker = (Button) findViewById(R.id.button_timepicker);
        button_timepicker.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                timePickerDialog = TimePickerDialog.newInstance(AppointmentActivity.this, Hour, Minute, false);
                timePickerDialog.setThemeDark(false);
                timePickerDialog.setTimeInterval(0,15);
                //timePickerDialog.showYearPickerFirst(false);
                timePickerDialog.setTitle("Time Picker");

                timePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

                    @Override
                    public void onCancel(DialogInterface dialogInterface) {

                        Toast.makeText(AppointmentActivity.this, "Timepicker Canceled", Toast.LENGTH_SHORT).show();
                    }
                });

                timePickerDialog.show(getFragmentManager(), "TimePickerDialog");
            }
        });


    }


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
        Intent i = new Intent(AppointmentActivity.this, CalendarServicesActivity.class);
        i.putExtra("page_id", "1");
        i.putExtra("date", date);
        startActivity(i);
        finish();
    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        String time = "Time: " + hourOfDay + ":" + minute + ":" + second;
        TextView text_timepicker = (TextView) findViewById(R.id.text_timepicker);
        text_timepicker.setText(time);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(AppointmentActivity.this, DashboardActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
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
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.action_calendar);

        if (service_name_list.size()!=0) {
            item.setEnabled(true);
            item.getIcon().setAlpha(255);
        } else {
            // disabled
            item.setEnabled(false);
            item.getIcon().setAlpha(130);
        }
        return true;
    }
}
