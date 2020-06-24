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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;


import com.corals.appointment.Activity.AddStaffActivity;
import com.corals.appointment.Client.model.AppointmentResources;
import com.corals.appointment.Client.model.AppointmentService;
import com.corals.appointment.R;

import java.util.ArrayList;
import java.util.List;

public class StaffListAdapter extends BaseAdapter {

    private final Activity context;
    List<AppointmentResources> appointmentResources;
    public StaffListAdapter(Activity context,List<AppointmentResources> appointmentResources) {
        // TODO Auto-generated constructor stub

        this.context = context;
        this.appointmentResources = appointmentResources;
    }

    @Override
    public int getCount() {
        return appointmentResources.size();
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
        View rowView = inflater.inflate(R.layout.layout_staff, null, true);

        TextView titleText = (TextView) rowView.findViewById(R.id.text_staff_name);
        ImageView imageView_edit = (ImageView) rowView.findViewById(R.id.image_edit);
        ImageView imageView_delete = (ImageView) rowView.findViewById(R.id.image_delete);
        LinearLayout linearLayout = (LinearLayout) rowView.findViewById(R.id.layout_staff);
        titleText.setText(appointmentResources.get(position).getResName());

        imageView_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, "Edit Staff", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(context, AddStaffActivity.class);
                i.putExtra("page_id", "03");
                i.putExtra("position", String.valueOf(position));
                i.putExtra("res_id", appointmentResources.get(position).getResId());
                i.putExtra("name", appointmentResources.get(position).getResName());
                i.putExtra("mobile", appointmentResources.get(position).getMobile());
                i.putExtra("mng_load", appointmentResources.get(position).getManageableLoad());
                context.startActivity(i);
                ((Activity)context).finish();
                ((Activity)context).overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);
            }
        });

        imageView_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setMessage("Are you sure, You want to delete this staff?");
                alertDialogBuilder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                arg0.dismiss();
                                final ProgressDialog pd = new ProgressDialog(context);
                                pd.setMessage("Deleting Staff...");
                                pd.show();

                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        pd.dismiss();
                                        Toast.makeText(context, "Staff Successfully Deleted!", Toast.LENGTH_SHORT).show();
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

    ;


}
