package com.corals.appointment.Adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.corals.appointment.Activity.ApptConfirmActivity;
import com.corals.appointment.Activity.CustomerActivity_Bottom;
import com.corals.appointment.Activity.CustomersMakeApptActivity;
import com.corals.appointment.Client.model.AppointmentAvailableSlots;
import com.corals.appointment.Dialogs.AlertDialogFailure;
import com.corals.appointment.Model.TimeSlotDataModel;
import com.corals.appointment.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by udhaya on 30-Aug-18.
 */

public class RecyclerAdapter_TimeSlots extends RecyclerView.Adapter<RecyclerAdapter_TimeSlots.MyViewHolder> {
    Context context;
    List<AppointmentAvailableSlots> appointmentAvailableSlots;
    TimeSlotDataModel timeSlotDataModel;
    public RecyclerAdapter_TimeSlots(Context context, List<AppointmentAvailableSlots> appointmentAvailableSlots, TimeSlotDataModel timeSlotDataModel) {
        this.context = context;
        this.appointmentAvailableSlots = appointmentAvailableSlots;
        this.timeSlotDataModel = timeSlotDataModel;
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
        Log.d("TimeSlots--->", "onBindViewHolder: "+total_appt+","+booked_appt);
        if (total_appt == 0) {
            holder.linearLayout_color.setBackgroundResource(R.drawable.left_round_corners_grey);
            holder.imageView_avail.setBackgroundResource(R.drawable.warning);
            holder.imageView_avail.setColorFilter(context.getResources().getColor(R.color.red));
           // holder.imageView_avail.setEnabled(false);
        }
        else if (total_appt == booked_appt) {
            holder.linearLayout_color.setBackgroundResource(R.drawable.left_round_corners_blue);
            holder.imageView_avail.setBackgroundResource(R.drawable.tick);
            //holder.imageView_avail.setEnabled(false);
        } else if (total_appt > booked_appt) {
            holder.linearLayout_color.setBackgroundResource(R.drawable.left_round_corners_green);
            holder.imageView_avail.setBackgroundResource(R.drawable.add_green);
            holder.imageView_avail.setEnabled(true);
        }

        holder.linearLayout_bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (total_appt == 0) {
                    ((Activity)context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new AlertDialogFailure(context, "Appointment not available for this slot!", "OK", "", "Warning") {
                                @Override
                                public void onButtonClick() {

                                }
                            };
                        }
                    });

                }
                else if (total_appt == booked_appt) {
                    ((Activity)context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new AlertDialogFailure(context, "All appointments are booked for this slot!", "OK", "", "Warning") {
                                @Override
                                public void onButtonClick() {

                                }
                            };
                        }
                    });

                }
               else if (total_appt > booked_appt) {
                    if( timeSlotDataModel.getPage_id().equals("1")) {
                        Intent in = new Intent(context, CustomersMakeApptActivity.class);
                        in.putExtra("page_id", "02");
                        in.putExtra("service_id", timeSlotDataModel.getSer_id());
                        in.putExtra("service", timeSlotDataModel.getSer());
                        in.putExtra("date", timeSlotDataModel.getDate());
                        in.putExtra("service_dur", timeSlotDataModel.getService_dur());
                        in.putExtra("slot_no", appointmentAvailableSlots.get(position).getSlotNo());
                        in.putExtra("start_time", appointmentAvailableSlots.get(position).getSerStartTime());
                        in.putExtra("end_time", appointmentAvailableSlots.get(position).getSerEndTime());
                        context.startActivity(in);
                        ((Activity) context).finish();
                        ((Activity) context).overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);
                    }
                    else if( timeSlotDataModel.getPage_id().equals("2")) {
                        Intent in = new Intent(context, ApptConfirmActivity.class);
                        in.putExtra("service_id", timeSlotDataModel.getSer_id());
                        in.putExtra("service", timeSlotDataModel.getSer());
                        in.putExtra("date", timeSlotDataModel.getDate());
                        in.putExtra("cus_id", timeSlotDataModel.getCus_id());
                        in.putExtra("cus", timeSlotDataModel.getCus());
                        in.putExtra("cus_email", timeSlotDataModel.getCus_email());
                        in.putExtra("slot_no", appointmentAvailableSlots.get(position).getSlotNo());
                        in.putExtra("start_time", appointmentAvailableSlots.get(position).getSerStartTime());
                        in.putExtra("end_time", appointmentAvailableSlots.get(position).getSerEndTime());
                        context.startActivity(in);
                        ((Activity) context).finish();
                        ((Activity) context).overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);
                    }
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
