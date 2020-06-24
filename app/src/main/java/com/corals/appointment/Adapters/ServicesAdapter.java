package com.corals.appointment.Adapters;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;


import com.corals.appointment.Activity.AddServiceActivity;
import com.corals.appointment.Client.model.AppointmentService;
import com.corals.appointment.R;

import java.util.ArrayList;
import java.util.List;

public class ServicesAdapter extends BaseAdapter {

    private final Activity context;
    List<AppointmentService> appointmentServiceArrayList;

    public ServicesAdapter(Activity context,List<AppointmentService> appointmentServiceArrayList) {
        // TODO Auto-generated constructor stub

        this.context = context;
        this.appointmentServiceArrayList = appointmentServiceArrayList;
    }

    @Override
    public int getCount() {
        return appointmentServiceArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.layout_services, null, true);

        TextView ser_name = (TextView) rowView.findViewById(R.id.text_service_name);
        TextView ser_dur = (TextView) rowView.findViewById(R.id.text_ser_dur);
        ImageView imageView_edit = (ImageView) rowView.findViewById(R.id.image_edit_service);
        ImageView imageView_delete = (ImageView) rowView.findViewById(R.id.image_delete_service);

     /*   ser_name.setText(arrayList.get(position));
        ser_dur.setText(arrayList1.get(position));*/

        ser_name.setText(appointmentServiceArrayList.get(position).getSerName());

        imageView_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(context, AddServiceActivity.class);
                i.putExtra("page_id", "03");
                i.putExtra("position", String.valueOf(position));
                i.putExtra("ser_id", appointmentServiceArrayList.get(position).getSerId());
                i.putExtra("name", appointmentServiceArrayList.get(position).getSerName());
                i.putExtra("duration", appointmentServiceArrayList.get(position).getSerDuration());
                i.putExtra("amount", appointmentServiceArrayList.get(position).getSerPrice());
                i.putExtra("description", appointmentServiceArrayList.get(position).getSerDescription());
                i.putExtra("show_cust", String.valueOf(appointmentServiceArrayList.get(position).isIsShowCust()));
                context.startActivity(i);
                ((Activity) context).finish();
                ((Activity) context).overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);

            }
        });

        imageView_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setMessage("Are you sure, You want to delete this service?");
                alertDialogBuilder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                arg0.dismiss();
                                final ProgressDialog pd = new ProgressDialog(context);
                                pd.setMessage("Deleting Service...");
                                pd.show();

                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        pd.dismiss();
                                        Toast.makeText(context, "Service Successfully Deleted!", Toast.LENGTH_SHORT).show();
                                    }
                                }, 2000);

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

        return rowView;

    }


}

