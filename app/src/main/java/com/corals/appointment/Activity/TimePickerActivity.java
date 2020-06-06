package com.corals.appointment.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.corals.appointment.R;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class TimePickerActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {
    private TextView mCurrentTime;
    DateFormat mTimeFormat;
    private int mPickerTheme;

    TimePickerDialog timePickerDialog;
    int Year, Month, Day, Hour, Minute;
    Calendar calendar;
    private int mYear, mMonth, mDay, mHour, mMinute, mSeconds;
    String start_time_p;
    TimePicker pickStartTime;
    NumberPicker numberPicker_hr, numberPicker_min;
    String[] numbers = new String[4];
    ArrayList<String> arrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_picker);

        numberPicker_hr = findViewById(R.id.picker_hr);
        numberPicker_min = findViewById(R.id.picker_min);

        numberPicker_hr.setMinValue(0);
        numberPicker_hr.setMaxValue(8);



        numbers[0]="0";
        numbers[1]="15";
        numbers[2]="30";
        numbers[3]="45";

        numberPicker_min.setMinValue(0);
        numberPicker_min.setMaxValue((numbers.length)-1);

        numberPicker_min.setDisplayedValues(numbers);
        numberPicker_min.setWrapSelectorWheel(false);
        numberPicker_hr.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                Toast.makeText(TimePickerActivity.this, "HOUR : " + newVal, Toast.LENGTH_SHORT).show();
            }
        });

        numberPicker_min.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

                //picker.setValue((newVal < oldVal) ? oldVal - 5 : oldVal + 5);
                Toast.makeText(TimePickerActivity.this, "MINUTE : " + newVal, Toast.LENGTH_SHORT).show();
            }
        });


/*        pickStartTime = findViewById(R.id.StartTime);
        pickStartTime.setIs24HourView(true);
        pickStartTime.setCurrentHour(00);
        pickStartTime.setCurrentMinute(00);*/
        //pickStartTime.setOnTimeChangedListener(mStartTimeChangedListener);

        calendar = Calendar.getInstance();
        Year = calendar.get(Calendar.YEAR);
        Month = calendar.get(Calendar.MONTH);
        Day = calendar.get(Calendar.DAY_OF_MONTH);
        Hour = calendar.get(Calendar.HOUR_OF_DAY);
        Minute = calendar.get(Calendar.MINUTE);

        Button button_timepicker = (Button) findViewById(R.id.text_timepicker);
        button_timepicker.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
              /*  timePickerDialog = TimePickerDialog.newInstance(TimePickerActivity.this, 00, 15, true);
                timePickerDialog.setThemeDark(false);
                timePickerDialog.setTimeInterval(0,15);
                //timePickerDialog.showYearPickerFirst(false);
                timePickerDialog.setTitle("Time Picker");
                timePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        Toast.makeText(TimePickerActivity.this, "Timepicker Canceled", Toast.LENGTH_SHORT).show();
                    }
                });
                timePickerDialog.show(getFragmentManager(), "TimePickerDialog");*/


                timepicker_dialog();


            }
        });

    }

    void timepicker_dialog(){
        LayoutInflater factory = LayoutInflater.from(this);
        final View deleteDialogView = factory.inflate(R.layout.timepicker_layout, null);
        final AlertDialog pickerDialog = new AlertDialog.Builder(this).create();
        pickerDialog.setView(deleteDialogView);
        final TimePicker pickStartTime = (TimePicker) deleteDialogView.findViewById(R.id.StartTime);
        Button button = (Button) deleteDialogView.findViewById(R.id.button_picker_ok);
        pickStartTime.setIs24HourView(true);
        pickStartTime.setCurrentHour(00);
        pickStartTime.setCurrentMinute(00);
        pickStartTime.setOnTimeChangedListener(mStartTimeChangedListener);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int hour = pickStartTime.getCurrentHour();
                int minute = pickStartTime.getCurrentMinute();
                Toast.makeText(TimePickerActivity.this, hour+" hrs "+minute+" mins", Toast.LENGTH_SHORT).show();
                pickerDialog.dismiss();
            }
        });
        pickerDialog.show();

    }
    public void getStartTime_P() {
        // Get Current Time*
        final Calendar c = Calendar.getInstance();
     /*   mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        mSeconds = c.get(Calendar.SECOND);*/
        // Launch Time Picker Dialog


        android.app.TimePickerDialog timePickerDialog = new android.app.TimePickerDialog(this, android.R.style.Theme_Holo_Dialog_MinWidth,
                new android.app.TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(android.widget.TimePicker view, int hourOfDay, int minute) {
                        // view.setBackgroundColor(getResources().getColor(R.color.button_background));
                        String time_hr = String.valueOf(hourOfDay);
                        String time_min = String.valueOf(minute);
                        String min = null, hr = null;
                        if (time_hr.length() == 1 && time_min.length() == 1) {
                            min = "0" + "" + time_min;
                            hr = "0" + "" + time_hr;
                            start_time_p = hr + ":" + min;
                        } else if (time_hr.length() == 1 && time_min.length() != 1) {
                            hr = "0" + "" + time_hr;
                            start_time_p = hr + ":" + minute;

                        } else if (time_hr.length() != 1 && time_min.length() == 1) {
                            min = "0" + "" + time_min;
                            start_time_p = hourOfDay + ":" + min;

                        } else {
                            start_time_p = hourOfDay + ":" + minute;
                        }
                        Log.d("Time===>", "" + start_time_p);

                        Toast.makeText(TimePickerActivity.this, "Time :" + start_time_p, Toast.LENGTH_SHORT).show();
                        //editText_start_time.setText(time_active);
                        //tv_start_time_P.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + start_time_p + "</u>  </font>"));

                    }
                }, 00, 15, true);


        timePickerDialog.show();

    }

    private TimePicker.OnTimeChangedListener mStartTimeChangedListener =
            new TimePicker.OnTimeChangedListener() {

                public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                    updateDisplay(view, hourOfDay, minute);
                }
            };

    private TimePicker.OnTimeChangedListener mNullTimeChangedListener =
            new TimePicker.OnTimeChangedListener() {

                public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {

                }
            };


    private void updateDisplay(TimePicker timePicker, int hourOfDay, int minute) {
        int nextMinute = 0;
        //int nextHour = 0;

            timePicker.setOnTimeChangedListener(mNullTimeChangedListener);

            if (minute >= 45 && minute <= 59)
                nextMinute = 45;
            else if (minute >= 30)
                nextMinute = 30;
            else if (minute >= 15)
                nextMinute = 15;
            else if (minute > 0)
                nextMinute = 0;
            else {
                nextMinute = 45;
            }

            if (minute - nextMinute == 1) {
                if (minute >= 45 && minute <= 59) {
                    nextMinute = 00;
                    hourOfDay = hourOfDay + 1;
                } else if (minute >= 30)
                    nextMinute = 45;
                else if (minute >= 15)
                    nextMinute = 30;
                else if (minute > 0)
                    nextMinute = 15;
                else {
                    nextMinute = 15;
                }
            }

            timePicker.setCurrentHour(hourOfDay);
            timePicker.setCurrentMinute(nextMinute);

            timePicker.setOnTimeChangedListener(mStartTimeChangedListener);

    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        String time = "Time: " + hourOfDay + ":" + minute * 15;
        Toast.makeText(this, "" + time, Toast.LENGTH_SHORT).show();
    }


}
