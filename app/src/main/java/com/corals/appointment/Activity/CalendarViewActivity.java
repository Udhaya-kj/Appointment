package com.corals.appointment.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import com.corals.appointment.Dialogs.AlertDialogFailure;
import com.corals.appointment.R;
import com.corals.appointment.receiver.ConnectivityReceiver;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CalendarViewActivity extends AppCompatActivity {

    CalendarView calendarView;
    String calendar_date, cus_name, cus_id, cus_email, cus_mob, page_id, ser, ser_id, res, res_id;
    Button textView_cal_next;
    Calendar c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_appt);

        Toolbar toolbar = findViewById(R.id.toolbar_view_appt);
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

        if (getIntent().getExtras() != null) {
            page_id = getIntent().getStringExtra("page_id");
            cus_name = getIntent().getStringExtra("cus");
            cus_id = getIntent().getStringExtra("cus_id");
            cus_email = getIntent().getStringExtra("cus_email");
            cus_mob = getIntent().getStringExtra("cus_mob");
            ser = getIntent().getStringExtra("service");
            ser_id = getIntent().getStringExtra("service_id");
            res = getIntent().getStringExtra("res");
            res_id = getIntent().getStringExtra("res_id");
            Log.d("CalendarView---->", "onCreate: " + ser + "," + ser_id + "," + res_id + "," + res + "," + cus_id + "," + cus_id + "," + cus_email + "," + cus_mob);

            if (page_id.equals("1")) {
                toolbar.setTitle("Select Date");
            } else {
                toolbar.setTitle(cus_name);
            }
        }

        calendarView = findViewById(R.id.calendarView);
        textView_cal_next = findViewById(R.id.text_calendar_next);

        c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());
        final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        calendar_date = df.format(c.getTime());
        calendarView.setMinDate(System.currentTimeMillis() - 1000);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                calendar_date = year + "-" + (month + 1) + "-" + dayOfMonth;
            }
        });

        textView_cal_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isConn = ConnectivityReceiver.isConnected();
                if (isConn) {
                    Intent i = new Intent(CalendarViewActivity.this, TimeSlotsActivity.class);
                    i.putExtra("page_id", page_id);
                    i.putExtra("date", calendar_date);
                    i.putExtra("cus", cus_name);
                    i.putExtra("cus_id", cus_id);
                    i.putExtra("cus_email", cus_email);
                    i.putExtra("cus_mob", cus_mob);
                    i.putExtra("service", ser);
                    i.putExtra("service_id", ser_id);
                    i.putExtra("res", res);
                    i.putExtra("res_id", res_id);
                    startActivity(i);
                    finish();
                    overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);

                 /*   if (!TextUtils.isEmpty(page_id) && page_id.equals("1")) {
                        Intent i = new Intent(CalendarViewActivity.this, TimeSlotsActivity.class);
                        i.putExtra("page_id", "2");
                        i.putExtra("date", calendar_date);
                        startActivity(i);
                        finish();
                        overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);
                    } else {
                        Log.d("calendar_date--->", "onClick: " + calendar_date);
                        Intent i = new Intent(CalendarViewActivity.this, CalendarServicesActivity.class);
                        i.putExtra("page_id", "2");
                        i.putExtra("date", calendar_date);
                        startActivity(i);
                        finish();
                        overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);
                    }*/
                } else {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new AlertDialogFailure(CalendarViewActivity.this, getResources().getString(R.string.no_internet_sub_title), "OK", getResources().getString(R.string.no_internet_title), getResources().getString(R.string.no_internet_Heading)) {
                                @Override
                                public void onButtonClick() {

                                }
                            };
                        }
                    });
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (!TextUtils.isEmpty(page_id) && page_id.equals("1")) {
            Intent i = new Intent(CalendarViewActivity.this, AppointmentActivity.class);
            startActivity(i);
            finish();
            overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);
        } else if (!TextUtils.isEmpty(page_id) && page_id.equals("2")) {
            Intent i = new Intent(CalendarViewActivity.this, CustomerActivity_Bottom.class);
            startActivity(i);
            finish();
            overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);
        } else {
            Intent i = new Intent(CalendarViewActivity.this, DashboardActivity.class);
            startActivity(i);
            finish();
            overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);
        }

    }
}
