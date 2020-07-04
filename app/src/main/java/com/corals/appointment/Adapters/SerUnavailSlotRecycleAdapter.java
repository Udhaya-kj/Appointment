package com.corals.appointment.Adapters;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.corals.appointment.Activity.ApptConfirmActivity;
import com.corals.appointment.Activity.ApptSuccessActivity;
import com.corals.appointment.Activity.ChangeApptActivity;
import com.corals.appointment.Activity.SerUnavailAskTimeActivity;
import com.corals.appointment.Client.model.AppointmentAvailableSlots;
import com.corals.appointment.Dialogs.AlertDialogFailure;
import com.corals.appointment.R;

import java.util.ArrayList;
import java.util.List;

public class SerUnavailSlotRecycleAdapter extends RecyclerView.Adapter<SerUnavailSlotRecycleAdapter.MyViewHolder> {
    Activity context;
    String code;
    List<AppointmentAvailableSlots> appointmentAvailableSlots;

    public SerUnavailSlotRecycleAdapter(Activity context,String code, List<AppointmentAvailableSlots> appointmentAvailableSlots) {
        this.context = context;
        this.appointmentAvailableSlots = appointmentAvailableSlots;
        this.code = code;

    }

    @Override
    public SerUnavailSlotRecycleAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_unavailability_slot, parent, false);
        SerUnavailSlotRecycleAdapter.MyViewHolder myViewHolder = new SerUnavailSlotRecycleAdapter.MyViewHolder(v);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final SerUnavailSlotRecycleAdapter.MyViewHolder holder, final int position) {
        holder.textView_slot.setText(appointmentAvailableSlots.get(position).getSerStartTime() + " - " + appointmentAvailableSlots.get(position).getSerEndTime());
        final int total_appt = Integer.parseInt(appointmentAvailableSlots.get(position).getAllowed());
        final int booked_appt = Integer.parseInt(appointmentAvailableSlots.get(position).getBooked());


        holder.linearLayout_bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (code.equals("1")) {
                    if (holder.textView_response.getVisibility() == View.GONE) {
                        if (booked_appt == 0) {
                            holder.linearLayout_bg.setBackgroundResource(R.drawable.corner_change_appt_slot_green);
                            holder.textView_response.setVisibility(View.VISIBLE);
                            holder.textView_response.setText("Unavailable");

                            SerUnavailAskTimeActivity.arrayList_startTime.add(appointmentAvailableSlots.get(position).getSerStartTime());
                            SerUnavailAskTimeActivity.arrayList_endTime.add(appointmentAvailableSlots.get(position).getSerEndTime());
                            SerUnavailAskTimeActivity.arrayList_slotNo.add(appointmentAvailableSlots.get(position).getSlotNo());

                        } else if (booked_appt > 0) {
                            context.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    new AlertDialogFailure(context, "Unable to make this slot unavailable", "OK", "Appointment already available on this slot", "Warning") {
                                        @Override
                                        public void onButtonClick() {

                                        }
                                    };
                                }
                            });

                        } else if (total_appt == 0 && booked_appt == 0) {
                            context.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    new AlertDialogFailure(context, "Unable to make this slot unavailable", "OK", "Appointment not available for this slot", "Warning") {
                                        @Override
                                        public void onButtonClick() {

                                        }
                                    };
                                }
                            });

                        }
                    }
                     else {
                        holder.textView_response.setVisibility(View.GONE);
                        holder.linearLayout_bg.setBackgroundResource(R.drawable.layout_bg_time_slots);

                        SerUnavailAskTimeActivity.arrayList_startTime.remove(appointmentAvailableSlots.get(position).getSerStartTime());
                        SerUnavailAskTimeActivity.arrayList_endTime.remove(appointmentAvailableSlots.get(position).getSerEndTime());
                        SerUnavailAskTimeActivity.arrayList_slotNo.remove(appointmentAvailableSlots.get(position).getSlotNo());
                    }
                }/* else if (code.equals("2")) {
                    if (holder.textView_response.getVisibility() == View.GONE) {
                        if (total_appt == booked_appt) {
                            getSlotUnavailDialog("Appointment already available on this slot. Staff cannot absent on this time");
                        }
                        else if (total_appt > booked_appt) {
                            holder.linearLayout_bg.setBackgroundResource(R.drawable.corner_change_appt_slot_green);
                            holder.textView_response.setVisibility(View.VISIBLE);
                            holder.textView_response.setText("Absent");
                        }
                    } else {
                        holder.textView_response.setVisibility(View.GONE);
                        holder.linearLayout_bg.setBackgroundResource(R.drawable.layout_bg_time_slots);
                    }
                }*/
            }
        });


    }


    @Override
    public int getItemCount() {
        return appointmentAvailableSlots.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textView_slot, textView_response;
        LinearLayout linearLayout_bg;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView_slot = (TextView) itemView.findViewById(R.id.text_slot_change_appt);
            textView_response = (TextView) itemView.findViewById(R.id.text_response);
            linearLayout_bg = (LinearLayout) itemView.findViewById(R.id.layout_change_slot);
        }
    }


}



