package com.corals.appointment.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;

import com.corals.appointment.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ServiceUnavailCalendarActivity extends AppCompatActivity {
    CalendarView calendarView;
    Button button_next;
    TextView textView_ser,textView_title;
    String ser_id,ser, task, calendar_date;
    Calendar c;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_unavail_calendar);

        Toolbar toolbar = findViewById(R.id.toolbar_cal);
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

        imageView = findViewById(R.id.image_cal);
        textView_title = findViewById(R.id.text_unavail_title);
        calendarView = findViewById(R.id.calendarView);
        textView_ser = findViewById(R.id.text_service);
        button_next = findViewById(R.id.button_ser_unavail_cal_next);
        calendarView. setMinDate(System. currentTimeMillis() - 1000);
        if (getIntent().getExtras() != null) {
            task = getIntent().getStringExtra("task");
            ser_id = getIntent().getStringExtra("service_id");
            ser = getIntent().getStringExtra("service");
            textView_ser.setText(ser);
            if (task.equals("1")) {
                imageView.setBackgroundResource(R.drawable.order);
            } else if (task.equals("2")) {
                toolbar.setTitle("Staff Leave");
                textView_title.setText("Select staff leave date");
                imageView.setBackgroundResource(R.drawable.user);
            }

        }
        c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());
        final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        calendar_date = df.format(c.getTime());


        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {

                calendar_date = dayOfMonth + "-" + (month + 1) + "-" + year;
                //textView_cal_next.setEnabled(true);
                // Toast.makeText(MaterialDatePickerActivity.this, "" + dayOfMonth + "-" + (month + 1) + "-" + year, Toast.LENGTH_SHORT).show();
            }
        });


        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in = new Intent(ServiceUnavailCalendarActivity.this, SerUnavailAskTimeActivity.class);
                in.putExtra("task", task);
                in.putExtra("service_id", ser_id);
                in.putExtra("service", ser);
                in.putExtra("date", calendar_date);
                startActivity(in);
                finish();
                overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent in = new Intent(ServiceUnavailCalendarActivity.this, SettingsActivity.class);
        startActivity(in);
        finish();
        overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);
    }
}
