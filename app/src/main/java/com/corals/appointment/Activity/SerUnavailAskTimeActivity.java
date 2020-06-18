package com.corals.appointment.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.corals.appointment.Adapters.ChangeApptSlotRecycleAdapter;
import com.corals.appointment.Adapters.SerUnavailSlotRecycleAdapter;
import com.corals.appointment.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class SerUnavailAskTimeActivity extends AppCompatActivity {

    TextView text_start_time, text_end_time, textView_appn_dt, textView_res, textView_time_title, text_st_am_pm, text_et_am_pm;
    LinearLayout linearLayout, linearLayout_show_time, layout_show_time1, layout_show_time2,layout_slots;
    Button button_submit, button_add_time;
    String start_time, end_time, task;
    TextView tv_time_yes, tv_time_no, textView_time_validation, textView_ser_unavail_time1, textView_ser_unavail_time2, text_unavail_time_dt,textView_response,textView_fullday_hint;
    ImageView imageView;
    boolean isValidTime = false;
    ArrayList<String> arrayList_start_time1, arrayList_start_time2, arrayList_end_time1, arrayList_end_time2;
    int hour = 0, minute = 0;
    RecyclerView recyclerView_slots;
    private ArrayList<String> list_time_slot,list_unavail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ser_unavail_ask_time);

        Toolbar toolbar = findViewById(R.id.toolbar_ser_unavail_ask_time);
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

        textView_fullday_hint = findViewById(R.id.text_full_day_hint);
        textView_response = findViewById(R.id.text_res);
        text_st_am_pm = findViewById(R.id.text_start_time_am_pm);
        text_et_am_pm = findViewById(R.id.text_end_time_am_pm);
        layout_slots = findViewById(R.id.layout_slots);
        layout_show_time1 = findViewById(R.id.layout_show_time1);
        layout_show_time2 = findViewById(R.id.layout_show_time2);
        linearLayout_show_time = findViewById(R.id.layout_show_unavail_time);
        text_unavail_time_dt = findViewById(R.id.text_unavail_time_dt);
        textView_ser_unavail_time1 = findViewById(R.id.text_ser_unavail_time1);
        textView_ser_unavail_time2 = findViewById(R.id.text_ser_unavail_time2);
        textView_time_validation = findViewById(R.id.text_validation_time);
        textView_time_title = findViewById(R.id.text_time_title);
        imageView = findViewById(R.id.image_cal);
        linearLayout = findViewById(R.id.layout_ask_time);
        textView_res = findViewById(R.id.text_resource);
        textView_appn_dt = findViewById(R.id.text_appn_dt);
        tv_time_yes = findViewById(R.id.text_time_yes);
        tv_time_no = findViewById(R.id.text_time_no);
        text_start_time = findViewById(R.id.text_start_time);
        text_end_time = findViewById(R.id.text_end_time);
        button_add_time = findViewById(R.id.button_add_time);
        button_submit = findViewById(R.id.button_ser_unavail_submit);
        recyclerView_slots = findViewById(R.id.recyclerview_appt_slots);

        GridLayoutManager lm = new GridLayoutManager(this, 2);
        recyclerView_slots.setLayoutManager(lm);
        recyclerView_slots.setHasFixedSize(true);

        list_time_slot = new ArrayList<>();
        list_unavail = new ArrayList<>();
        arrayList_start_time1 = new ArrayList<>();
        arrayList_start_time2 = new ArrayList<>();
        arrayList_end_time1 = new ArrayList<>();
        arrayList_end_time2 = new ArrayList<>();
        if (getIntent().getExtras() != null) {
            task = getIntent().getStringExtra("task");
            String res = getIntent().getStringExtra("service");
            String dt = getIntent().getStringExtra("date");
            Log.d("Appn_Date---->", "onCreate: " + dt + "," + res);
            textView_appn_dt.setText(dt);
            textView_res.setText(res);

            if (task.equals("1")) {
                imageView.setBackgroundResource(R.drawable.order);
            } else if (task.equals("2")) {
                toolbar.setTitle("Staff Leave");
                textView_fullday_hint.setText("Full day absent ?");
                imageView.setBackgroundResource(R.drawable.user);
                textView_time_title.setText("Select staff leave time");
                textView_response.setText("Select staff absent time");
            }

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

        list_unavail.add("0");
        list_unavail.add("0");
        list_unavail.add("1");
        list_unavail.add("0");
        list_unavail.add("1");
        list_unavail.add("0");
        list_unavail.add("1");
        list_unavail.add("1");
        list_unavail.add("0");
        list_unavail.add("0");

        final SerUnavailSlotRecycleAdapter serUnavailSlotRecycleAdapter = new SerUnavailSlotRecycleAdapter(SerUnavailAskTimeActivity.this, list_time_slot,list_unavail,task);
        recyclerView_slots.setAdapter(serUnavailSlotRecycleAdapter);
       /* Drawable horizontalDivider = ContextCompat.getDrawable(this, R.drawable.line_divider);
        Drawable verticalDivider = ContextCompat.getDrawable(this, R.drawable.line_divider);
        recyclerView_slots.addItemDecoration(new GridDividerItemDecoration(horizontalDivider, verticalDivider, 3));*/

        tv_time_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_time_yes.setBackgroundColor(getResources().getColor(R.color.green_hase));
                tv_time_no.setBackgroundColor(getResources().getColor(R.color.dark_grey));
                tv_time_yes.setTextColor(Color.parseColor("#EEEEEE"));
                tv_time_no.setTextColor(Color.parseColor("#000000"));
                //linearLayout.setVisibility(View.GONE);
                layout_slots.setVisibility(View.GONE);
                //Toast.makeText(VoucherSetupCreate.this, ""+sharable_p1, Toast.LENGTH_SHORT).show();

            }
        });


        tv_time_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tv_time_yes.setBackgroundColor(getResources().getColor(R.color.dark_grey));
                tv_time_no.setBackgroundColor(getResources().getColor(R.color.green_hase));
                tv_time_yes.setTextColor(Color.parseColor("#000000"));
                tv_time_no.setTextColor(Color.parseColor("#FFFFFF"));
                // linearLayout.setVisibility(View.VISIBLE);
                layout_slots.setVisibility(View.VISIBLE);

            }
        });

        text_start_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get_start_time();
                timepicker_dialog("1");
            }
        });

        text_end_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get_end_time();
                timepicker_dialog("2");
            }
        });

        button_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //if (isValidTime) {
                    final ProgressDialog pd = new ProgressDialog(SerUnavailAskTimeActivity.this);
                    pd.setMessage("Submitting...");
                    pd.show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            pd.dismiss();
                            Intent in = new Intent(SerUnavailAskTimeActivity.this, DashboardActivity.class);
                            startActivity(in);
                            finish();
                        }
                    }, 2000);
              /*  } else {
                    Toast.makeText(SerUnavailAskTimeActivity.this, "Select valid time!", Toast.LENGTH_SHORT).show();
                }*/
            }
        });
        Log.d("arrayList_time--->", "onClick: " + arrayList_start_time1.size());
        button_add_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("arrayList_time--->", "onClick: " + arrayList_start_time1.size());
                if ((arrayList_start_time2.size() < 1) && (arrayList_start_time2.size() != 1)) {
                    String st_time = text_start_time.getText().toString();
                    String end_time = text_end_time.getText().toString();

                    boolean chktime = checktimings(st_time, end_time);
                    if (chktime) {
                        textView_time_validation.setVisibility(View.GONE);
                        isValidTime = true;
                        if (arrayList_start_time1.size() == 0) {
                            if (task.equals("1")) {
                                text_unavail_time_dt.setText("Service unavailability time on " + textView_appn_dt.getText().toString());
                            } else {
                                text_unavail_time_dt.setText("Staff unavailability time on " + textView_appn_dt.getText().toString());
                            }
                            linearLayout_show_time.setVisibility(View.VISIBLE);
                            arrayList_start_time1.add(st_time);
                            arrayList_end_time1.add(end_time);
                            textView_ser_unavail_time1.setText(arrayList_start_time1.get(0) + " - " + arrayList_end_time1.get(0));
                        } else if (arrayList_start_time1.size() == 1) {

                            boolean chktime_slot2 = checktimings2(arrayList_end_time1.get(0), st_time);
                            if (chktime_slot2) {
                                layout_show_time2.setVisibility(View.VISIBLE);
                                arrayList_start_time2.add(st_time);
                                arrayList_end_time2.add(end_time);
                                textView_ser_unavail_time2.setText(arrayList_start_time2.get(0) + " - " + arrayList_end_time2.get(0));
                            } else {
                                getAnimation("Unavailability time cannot overlap");
                            }
                        }
                        text_start_time.setText("00:00");
                        text_end_time.setText("00:00");
                        text_st_am_pm.setText("am");
                        text_et_am_pm.setText("am");
                    } else {
                        isValidTime = false;
                        getAnimation("Invalid Time");

                     /*   Animation anim = new AlphaAnimation(0.0f, 1.0f);
                        anim.setDuration(3000); //You can manage the blinking time with this parameter
                        anim.setStartOffset(100);
                        anim.setRepeatMode(Animation.REVERSE);
                        anim.setRepeatCount(Animation.INFINITE);
                        textView_time_validation.startAnimation(anim);*/
                    }

                } else {
                    Toast.makeText(SerUnavailAskTimeActivity.this, "You have reached the limit", Toast.LENGTH_LONG).show();
                }


            }

        });
    }

    private void getAnimation(String msg) {
        textView_time_validation.setVisibility(View.VISIBLE);
        textView_time_validation.setText(msg);
        Animation startAnimation = AnimationUtils.loadAnimation(SerUnavailAskTimeActivity.this, R.anim.blinking_animation1);
        textView_time_validation.startAnimation(startAnimation);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                textView_time_validation.clearAnimation();
            }
        }, 2000);

    }


    public void get_start_time() {

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, android.R.style.Theme_Holo_Dialog_MinWidth,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        // view.setBackgroundColor(getResources().getColor(R.color.button_background));
                        String time_hr = String.valueOf(hourOfDay);
                        String time_min = String.valueOf(minute);
                        String min = null, hr = null;
                        if (time_hr.length() == 1 && time_min.length() == 1) {
                            min = "0" + "" + time_min;
                            hr = "0" + "" + time_hr;
                            start_time = hr + ":" + min;
                        } else if (time_hr.length() == 1 && time_min.length() != 1) {
                            hr = "0" + "" + time_hr;
                            start_time = hr + ":" + minute;
                        } else if (time_hr.length() != 1 && time_min.length() == 1) {
                            min = "0" + "" + time_min;
                            start_time = hourOfDay + ":" + min;
                        } else {
                            start_time = hourOfDay + ":" + minute;
                        }
                        Log.d("Time===>", "" + start_time);
                        //text_start_time.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + start_time + "</u>  </font>"));
                        text_start_time.setText(start_time);

                    }
                }, 00, 00, false);
        timePickerDialog.show();
    }

    public void get_end_time() {

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, android.R.style.Theme_Holo_Dialog_MinWidth,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        // view.setBackgroundColor(getResources().getColor(R.color.button_background));
                        String time_hr = String.valueOf(hourOfDay);
                        String time_min = String.valueOf(minute);
                        String min = null, hr = null;
                        if (time_hr.length() == 1 && time_min.length() == 1) {
                            min = "0" + "" + time_min;
                            hr = "0" + "" + time_hr;
                            end_time = hr + ":" + min;
                        } else if (time_hr.length() == 1 && time_min.length() != 1) {
                            hr = "0" + "" + time_hr;
                            end_time = hr + ":" + minute;
                        } else if (time_hr.length() != 1 && time_min.length() == 1) {
                            min = "0" + "" + time_min;
                            end_time = hourOfDay + ":" + min;
                        } else {
                            end_time = hourOfDay + ":" + minute;
                        }
                        Log.d("Time===>", "" + end_time);
                        //text_end_time.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + end_time + "</u>  </font>"));
                        text_end_time.setText(end_time);


                    }
                }, 00, 00, false);
        timePickerDialog.show();


    }

    private boolean checktimings(String time, String endtime) {
        String pattern = "HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);

        try {
            Date date1 = sdf.parse(time);
            Date date2 = sdf.parse(endtime);

            if (date1.before(date2)) {
                return true;
            } else {

                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean checktimings2(String time, String endtime) {
        String pattern = "HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);

        try {
            Date date1 = sdf.parse(time);
            Date date2 = sdf.parse(endtime);

            if ((date1.before(date2)) || (date1.equals(date2))) {
                return true;
            } else {

                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent in = new Intent(SerUnavailAskTimeActivity.this, SettingsActivity.class);
        startActivity(in);
        finish();
    }

    void timepicker_dialog(final String id) {
        LayoutInflater factory = LayoutInflater.from(this);
        final View deleteDialogView = factory.inflate(R.layout.timepicker_layout, null);
        final AlertDialog pickerDialog = new AlertDialog.Builder(this).create();
        pickerDialog.setView(deleteDialogView);
        final TimePicker pickStartTime = (TimePicker) deleteDialogView.findViewById(R.id.StartTime);
        Button button = (Button) deleteDialogView.findViewById(R.id.button_picker_ok);
        TextView textView_title = (TextView) deleteDialogView.findViewById(R.id.text_alert_title);
        pickStartTime.setIs24HourView(true);
        pickStartTime.setCurrentHour(00);
        pickStartTime.setCurrentMinute(00);
        pickStartTime.setOnTimeChangedListener(mStartTimeChangedListener);

        if (task.equals("1")) {
            textView_title.setText("Select service unavailability time");
        } else if (task.equals("2")) {
            textView_title.setText("Select staff unavailability time");
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hr = "", min = "";
                hour = pickStartTime.getCurrentHour();
                minute = pickStartTime.getCurrentMinute();
                if (String.valueOf(hour).length() == 1) {
                    hr = "0" + hour;
                } else {
                    hr = String.valueOf(hour);
                }
                if (String.valueOf(minute).length() == 1) {
                    min = "0" + minute;
                } else {
                    min = String.valueOf(minute);
                }

                if (id.equals("1")) {
                    text_start_time.setText(hr + ":" + min);
                    if (hour >= 12) {
                        text_st_am_pm.setText("pm");
                    }
                } else if (id.equals("2")) {
                    text_end_time.setText(hr + ":" + min);
                    if (hour >= 12) {
                        text_et_am_pm.setText("pm");
                    }
                }


                //  Toast.makeText(AddServiceActivity.this, hour+" hrs "+minute+" mins", Toast.LENGTH_SHORT).show();
                pickerDialog.dismiss();
            }
        });
        pickerDialog.show();

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


}
