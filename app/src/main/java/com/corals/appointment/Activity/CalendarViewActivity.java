package com.corals.appointment.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
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
    String calendar_date, cus_name;
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
            cus_name = getIntent().getStringExtra("cus_name");
            toolbar.setTitle(cus_name);
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
                Log.d("calendar_date--->", "onClick: " + calendar_date);
                Intent i = new Intent(CalendarViewActivity.this, CalendarServicesActivity.class);
                i.putExtra("page_id", "2");
                i.putExtra("date", calendar_date);
                startActivity(i);
                finish();
                overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);
                } else {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new AlertDialogFailure(CalendarViewActivity.this, getResources().getString(R.string.no_internet_sub_title), "OK", getResources().getString(R.string.no_internet_title),getResources().getString(R.string.no_internet_Heading)) {
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
        Intent in = new Intent(CalendarViewActivity.this, CustomerActivity_Bottom.class);
        startActivity(in);
        finish();
    }
}
