package com.corals.appointment.Adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.corals.appointment.Activity.CustomerActivity_Bottom;
import com.corals.appointment.Activity.CustomersMakeApptActivity;
import com.corals.appointment.Client.model.AppointmentAvailableSlots;
import com.corals.appointment.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by udhaya on 30-Aug-18.
 */

public class RecyclerAdapter_TimeSlots extends RecyclerView.Adapter<RecyclerAdapter_TimeSlots.MyViewHolder> {
    Context context;
    List<AppointmentAvailableSlots> appointmentAvailableSlots;
    String ser_id, date,res_id,res,service;

    public RecyclerAdapter_TimeSlots(Context context, List<AppointmentAvailableSlots> appointmentAvailableSlots, String ser_id,String service, String date, String res_id, String res) {

        this.context = context;
        this.appointmentAvailableSlots = appointmentAvailableSlots;
        this.ser_id = ser_id;
        this.date = date;
        this.res_id = res_id;
        this.res = res;
        this.service = service;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.timeslot_row_item, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(v);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.textView_ser_time.setText(appointmentAvailableSlots.get(position).getSerStartTime() + " - " + appointmentAvailableSlots.get(position).getSerEndTime());

        final int total_appt = Integer.parseInt(appointmentAvailableSlots.get(position).getAllowed());
        final int booked_appt = Integer.parseInt(appointmentAvailableSlots.get(position).getBooked());

        if (total_appt == booked_appt) {
            holder.linearLayout_color.setBackgroundResource(R.drawable.left_round_corners_blue);
            holder.imageView_avail.setBackgroundResource(R.drawable.tick);
            holder.imageView_avail.setEnabled(false);
        } else if (total_appt > booked_appt) {
            holder.linearLayout_color.setBackgroundResource(R.drawable.left_round_corners_green);
            holder.imageView_avail.setBackgroundResource(R.drawable.add_green);
            holder.imageView_avail.setEnabled(true);
        }
     /*   else if (arrayList2.get(position).equals("2")) {
            holder.linearLayout_color.setBackgroundResource(R.drawable.left_round_corners_grey);
            holder.imageView_avail.setBackgroundResource(R.drawable.block);
            holder.imageView_avail.setEnabled(false);
        }
*/
        holder.linearLayout_bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (total_appt > booked_appt) {
                    Intent in = new Intent(context, CustomersMakeApptActivity.class);
                    in.putExtra("page_id", "02");
                    in.putExtra("service_id", ser_id);
                    in.putExtra("service", service);
                    in.putExtra("date", date);
                    in.putExtra("res_id", res_id);
                    in.putExtra("res", res);
                    in.putExtra("slot_no", appointmentAvailableSlots.get(position).getSlotNo());
                    in.putExtra("start_time", appointmentAvailableSlots.get(position).getSerStartTime());
                    in.putExtra("end_time", appointmentAvailableSlots.get(position).getSerEndTime());
                    context.startActivity(in);
                    ((Activity) context).finish();
                    ((Activity) context).overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);
                }


            }
        });


    }


    @Override
    public int getItemCount() {
        return appointmentAvailableSlots.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textView_ser_time;
        LinearLayout linearLayout_bg, linearLayout_color;
        ImageView imageView_avail;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView_ser_time = (TextView) itemView.findViewById(R.id.tv_slot_time);
            linearLayout_bg = (LinearLayout) itemView.findViewById(R.id.layout_appt_slot);
            linearLayout_color = (LinearLayout) itemView.findViewById(R.id.layout_color_status);
            imageView_avail = (ImageView) itemView.findViewById(R.id.image_available);
        }
    }
}
