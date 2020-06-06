package com.corals.appointment.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.corals.appointment.R;

import java.util.ArrayList;

public class ApptConfirmActivity extends AppCompatActivity {

    Button button_done;
    TextView tv_n1, tv_m1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appt_confirm);

        Toolbar toolbar = findViewById(R.id.toolbar_appt_confirm);
        setSupportActionBar(toolbar);
   /*     getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_left);*/
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        tv_n1 = findViewById(R.id.tv_name);
        tv_m1 = findViewById(R.id.tv_mob);

        if (getIntent().getExtras() != null) {
            String name = getIntent().getStringExtra("cus_name");
            String mob = getIntent().getStringExtra("cus_mob");
            tv_n1.setText(name);
            tv_m1.setText(mob);
            Log.d("Appn_Date---->", "onCreate: " + name + "," + mob);
        }

        button_done = findViewById(R.id.button_appt_done);
        button_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ApptConfirmActivity.this);
                alertDialogBuilder.setMessage("Are you sure, You want to book this appointment?");
                alertDialogBuilder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                arg0.dismiss();
                                final ProgressDialog pd = new ProgressDialog(ApptConfirmActivity.this);
                                pd.setMessage("Booking Appointment...");
                                pd.show();

                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        pd.dismiss();
                                       // Toast.makeText(ApptConfirmActivity.this, "Appointment Successfully Booked!", Toast.LENGTH_SHORT).show();
                                        Intent in = new Intent(ApptConfirmActivity.this, ApptSuccessActivity.class);
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

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ApptConfirmActivity.this);
        alertDialogBuilder.setMessage("Are you sure, You want to cancel this appointment?");
        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        arg0.dismiss();
                        Intent in = new Intent(ApptConfirmActivity.this, DashboardActivity.class);
                        startActivity(in);
                        finish();


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

}