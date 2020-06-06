package com.corals.appointment.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.corals.appointment.Adapters.ViewCustomersApptRecyclerAdapter;
import com.corals.appointment.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ViewCustomerApptActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<String> arrayList_slot_time, arrayList_slot_cus_name, arrayList_slot_cus_mob, arrayList_available;
    ImageView imageView_back, imageView_next;
    TextView textView_date;
    Calendar c;
    String formattedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_customer_appt);

        Toolbar toolbar = findViewById(R.id.toolbar_view_service_appt);
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
            String service = getIntent().getStringExtra("service");
            toolbar.setTitle(service);
        }
        //Appt Slots
        arrayList_slot_time = new ArrayList<>();
        arrayList_slot_cus_name = new ArrayList<>();
        arrayList_slot_cus_mob = new ArrayList<>();
        arrayList_available = new ArrayList<>();

        arrayList_slot_time.add("08:00 am - 08:30 am");
        arrayList_slot_time.add("12:00 pm - 12:30 pm");
        arrayList_slot_time.add("02:30 pm - 02:45 pm");
        arrayList_slot_time.add("06:00 pm - 06:30 pm");
        arrayList_slot_time.add("06:30 pm - 07:00 pm");
        arrayList_slot_time.add("07:00 pm - 07:30 pm");
        arrayList_slot_time.add("07:30 pm - 08:00 pm");
        arrayList_slot_time.add("08:00 pm - 08:30 pm");


        imageView_back = findViewById(R.id.image_appt_back);
        imageView_next = findViewById(R.id.image_appt_next);
        textView_date = findViewById(R.id.text_appt_date);
        recyclerView = findViewById(R.id.recyclerview_view_appts);
        LinearLayoutManager lm = new LinearLayoutManager(ViewCustomerApptActivity.this);
        recyclerView.setLayoutManager(lm);

        ViewCustomersApptRecyclerAdapter apptServiceSlotsAdapter = new ViewCustomersApptRecyclerAdapter(ViewCustomerApptActivity.this, arrayList_slot_time);
        recyclerView.setAdapter(apptServiceSlotsAdapter);

        c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());
        final SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        formattedDate = df.format(c.getTime());
        textView_date.setText(formattedDate);

        imageView_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ViewCustomerApptActivity.this);
                alertDialogBuilder.setMessage("Do you want to show previous day appointments?");
                alertDialogBuilder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                arg0.dismiss();
                                c.add(Calendar.DATE, -1);
                                formattedDate = df.format(c.getTime());
                                Log.v("PREVIOUS DATE : ", formattedDate);
                                textView_date.setText(formattedDate);
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

        imageView_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ViewCustomerApptActivity.this);
                alertDialogBuilder.setMessage("Do you want to show next day appointments?");
                alertDialogBuilder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                arg0.dismiss();
                                c.add(Calendar.DATE, 1);
                                formattedDate = df.format(c.getTime());
                                Log.v("NEXT DATE : ", formattedDate);
                                textView_date.setText(formattedDate);
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

/*        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    System.out.println("Scrolled Downwards");
                    Toast.makeText(ViewApptServiceActivity.this, "Scrolled Downwards", Toast.LENGTH_SHORT).show();
                } else if (dy < 0) {
                    System.out.println("Scrolled Upwards");
                    Toast.makeText(ViewApptServiceActivity.this, "Scrolled Upwards", Toast.LENGTH_SHORT).show();
                } else {
                    System.out.println("No Vertical Scrolled");
                    Toast.makeText(ViewApptServiceActivity.this, "No Vertical Scrolled", Toast.LENGTH_SHORT).show();
                }
            }
        });*/


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent in = new Intent(ViewCustomerApptActivity.this, CustomerActivity_Bottom.class);
        startActivity(in);
        finish();
    }
}
