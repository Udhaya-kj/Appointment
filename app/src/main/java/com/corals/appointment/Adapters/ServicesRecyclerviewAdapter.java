package com.corals.appointment.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.corals.appointment.Activity.ServiceUnavailCalendarActivity;
import com.corals.appointment.Activity.TimeSlotsActivity;
import com.corals.appointment.Activity.ViewApptServiceActivity;
import com.corals.appointment.Client.model.AppointmentService;
import com.corals.appointment.R;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ServicesRecyclerviewAdapter extends RecyclerView.Adapter<ServicesRecyclerviewAdapter.MyViewHolder>  {
    List<AppointmentService> appointmentServices;
    Activity context;
    String id;
    public ServicesRecyclerviewAdapter(Activity context,String id,List<AppointmentService> appointmentServices) {

        this.context = context;
        this.id = id;
        this.appointmentServices = appointmentServices;

    }

    @Override
    public ServicesRecyclerviewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_appt_services, parent, false);
        ServicesRecyclerviewAdapter.MyViewHolder myViewHolder = new ServicesRecyclerviewAdapter.MyViewHolder(v);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final ServicesRecyclerviewAdapter.MyViewHolder holder, final int position) {

        holder.textView_ser_name.setText(appointmentServices.get(position).getSerName());
        //holder.textView_ser_dur.setText(appointmentServices.get(position).getSerDuration());

        holder.linearLayout_bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(id.equals("1")) {
                    Intent i = new Intent(context, ViewApptServiceActivity.class);
                    i.putExtra("service_id", appointmentServices.get(position).getSerId());
                    i.putExtra("service", appointmentServices.get(position).getSerName());
                    context.startActivity(i);
                    ((Activity) context).finish();
                    ((Activity) context).overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);
                }
                else if(id.equals("5")){
                    Intent i = new Intent(context, ServiceUnavailCalendarActivity.class);
                    i.putExtra("task", "1");
                    i.putExtra("service_id", appointmentServices.get(position).getSerId());
                    i.putExtra("service", appointmentServices.get(position).getSerName());
                    context.startActivity(i);
                    ((Activity) context).finish();
                    ((Activity) context).overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);
                }
            }
        });

    }


    @Override
    public int getItemCount() {
        return appointmentServices.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textView_ser_name,textView_ser_dur;
        LinearLayout linearLayout_bg;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView_ser_name = (TextView) itemView.findViewById(R.id.text_service_name);
            textView_ser_dur = (TextView) itemView.findViewById(R.id.text_ser_dur);
            linearLayout_bg = (LinearLayout) itemView.findViewById(R.id.layout_ser_calender);
        }
    }
}

