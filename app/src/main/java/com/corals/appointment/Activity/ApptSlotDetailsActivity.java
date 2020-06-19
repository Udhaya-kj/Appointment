package com.corals.appointment.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.corals.appointment.R;

public class ApptSlotDetailsActivity extends AppCompatActivity {

    TextView textView_name, textView_mob, textView_ser_name, textView_staff, textView_booking_date,textView_booking_time;
    LinearLayout layout_call, layout_remainder, layout_cancel_appt, layout_change_appt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appt_slot_details);

        Toolbar toolbar = findViewById(R.id.toolbar_appt_details);
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

        textView_name = findViewById(R.id.tv_appt_det_name);
        textView_mob = findViewById(R.id.tv_appt_det_mob);
        textView_ser_name = findViewById(R.id.tv_appt_det_ser_name);
        textView_staff = findViewById(R.id.tv_appt_det_staff_name);
        textView_booking_date = findViewById(R.id.tv_appt_det_booking_date);
        textView_booking_time = findViewById(R.id.tv_appt_det_booking_time);

        layout_call = findViewById(R.id.layout_call);
        layout_remainder = findViewById(R.id.layout_sent_reminder);
        layout_cancel_appt = findViewById(R.id.layout_cancel_appt);
        layout_change_appt = findViewById(R.id.layout_change_appt);

        layout_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(ApptSlotDetailsActivity.this, "Calling...", Toast.LENGTH_SHORT).show();
            }
        });

        layout_remainder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ApptSlotDetailsActivity.this, "Sent Reminder...", Toast.LENGTH_SHORT).show();

            }
        });

        layout_cancel_appt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ApptSlotDetailsActivity.this);
                alertDialogBuilder.setMessage("Are you sure, You want to cancel this appointment?");
                alertDialogBuilder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                arg0.dismiss();
                                final ProgressDialog pd = new ProgressDialog(ApptSlotDetailsActivity.this);
                                pd.setMessage("Cancelling Appointment...");
                                pd.show();

                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        pd.dismiss();
                                        Toast.makeText(ApptSlotDetailsActivity.this, "Appointment Successfully Cancelled!", Toast.LENGTH_SHORT).show();
                                        Intent in = new Intent(ApptSlotDetailsActivity.this, AppointmentActivity.class);
                                        startActivity(in);
                                        finish();
                                    }
                                }, 3000);

                            }
                        });

                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

            }
        });

        layout_change_appt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(ApptSlotDetailsActivity.this, ChangeApptActivity.class);
                in.putExtra("service",textView_ser_name.getText().toString().trim());
                in.putExtra("staff",textView_staff.getText().toString().trim());
                in.putExtra("appt_date",textView_booking_date.getText().toString().trim());
                in.putExtra("appt_time",textView_booking_time.getText().toString().trim());
                startActivity(in);
                finish();
                overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);
            }
        });

        if (getIntent().getExtras() != null) {
            String name = getIntent().getStringExtra("name");
            String mob = getIntent().getStringExtra("mob");
            String time = getIntent().getStringExtra("time");
            textView_name.setText(name);
            textView_mob.setText(mob);
            textView_booking_time.setText(time);
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent i = new Intent(ApptSlotDetailsActivity.this, AppointmentActivity.class);
        startActivity(i);
        finish();
        overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);

    }
}
