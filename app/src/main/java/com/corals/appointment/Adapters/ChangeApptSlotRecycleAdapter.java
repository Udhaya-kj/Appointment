package com.corals.appointment.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;


import com.corals.appointment.Activity.ChangeApptActivity;
import com.corals.appointment.Activity.DashboardActivity;
import com.corals.appointment.Activity.SetupServiceActivity_Bottom;
import com.corals.appointment.Client.model.AppointmentAvailableSlots;
import com.corals.appointment.Dialogs.AlertDialogFailure;
import com.corals.appointment.R;

import java.util.ArrayList;
import java.util.List;

public class ChangeApptSlotRecycleAdapter extends RecyclerView.Adapter<ChangeApptSlotRecycleAdapter.MyViewHolder> {
    Activity context;
    String time;
    int index = -1;
    int selecteTimePos = -1;
    List<AppointmentAvailableSlots> appointmentAvailableSlots;

    public ChangeApptSlotRecycleAdapter(Activity context, String time, List<AppointmentAvailableSlots> appointmentAvailableSlots) {
        this.context = context;
        this.time = time;
        this.appointmentAvailableSlots = appointmentAvailableSlots;
    }

    @Override
    public ChangeApptSlotRecycleAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_change_appt_slot, parent, false);
        ChangeApptSlotRecycleAdapter.MyViewHolder myViewHolder = new ChangeApptSlotRecycleAdapter.MyViewHolder(v);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final ChangeApptSlotRecycleAdapter.MyViewHolder holder, final int position) {

        holder.textView_slot.setText(appointmentAvailableSlots.get(position).getSerStartTime() + "-" + appointmentAvailableSlots.get(position).getSerEndTime());
        final int total_appt = Integer.parseInt(appointmentAvailableSlots.get(position).getAllowed());
        final int booked_appt = Integer.parseInt(appointmentAvailableSlots.get(position).getBooked());

        if (total_appt == booked_appt) {
            holder.linearLayout_bg.setBackgroundResource(R.drawable.layout_bg_time_slots);
            holder.linearLayout_bg.setEnabled(false);
        } else if (total_appt > booked_appt) {
            holder.linearLayout_bg.setBackgroundResource(R.drawable.layout_bg_change_appt_blue);
            holder.linearLayout_bg.setEnabled(true);
        }

        holder.linearLayout_bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (total_appt > booked_appt) {
                    index = position;
                    notifyDataSetChanged();
                    ChangeApptActivity.startTime = appointmentAvailableSlots.get(position).getSerStartTime();
                    ChangeApptActivity.endTime = appointmentAvailableSlots.get(position).getSerEndTime();
                    ChangeApptActivity.slotNo = appointmentAvailableSlots.get(position).getSlotNo();

                    Log.d("index---", "onClick: Yes :" + position);
                } else {
                    context.runOnUiThread(new Runnable() {
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
            }
        });
        if (index == position) {
            holder.linearLayout_bg.setBackgroundResource(R.drawable.layout_change_slot_selecting);
            holder.textView_slot.setTextColor(Color.parseColor("#FFFFFF"));

        } else {
            holder.textView_slot.setTextColor(Color.parseColor("#000000"));
            if (total_appt == booked_appt) {
                holder.linearLayout_bg.setBackgroundResource(R.drawable.layout_bg_time_slots);
                holder.linearLayout_bg.setEnabled(false);
            } else if (total_appt > booked_appt) {
                holder.linearLayout_bg.setBackgroundResource(R.drawable.layout_bg_change_appt_blue);
                holder.linearLayout_bg.setEnabled(true);
            }

        }


        String tm = (appointmentAvailableSlots.get(position).getSerStartTime() + "-" + appointmentAvailableSlots.get(position).getSerEndTime());
        if (tm.equals(time)) {
            ChangeApptActivity.slotNo = appointmentAvailableSlots.get(position).getSlotNo();
            holder.linearLayout_bg.setBackgroundResource(R.drawable.layout_change_slot_selected);
            holder.textView_slot.setTextColor(Color.parseColor("#FFFFFF"));
            selecteTimePos=position;
            Log.d("selecteTimePos---", "onBindViewHolder: "+selecteTimePos);
        }

    /*    if(selecteTimePos==selecteTimePos){
            if (total_appt == booked_appt) {
                holder.linearLayout_bg.setBackgroundResource(R.drawable.layout_bg_time_slots);
                holder.linearLayout_bg.setEnabled(false);
            } else if (total_appt > booked_appt) {
                holder.linearLayout_bg.setBackgroundResource(R.drawable.layout_bg_change_appt_blue);
                holder.linearLayout_bg.setEnabled(true);
            }
        }*/

    }


    @Override
    public int getItemCount() {
        return appointmentAvailableSlots.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textView_slot;
         //CardView cardView;
        LinearLayout linearLayout_bg;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView_slot = (TextView) itemView.findViewById(R.id.text_slot_change_appt);
            linearLayout_bg = (LinearLayout) itemView.findViewById(R.id.layout_change_slot);
            // cardView = (cardView) itemView.findViewById(R.id.card_time_text);
        }
    }
}


