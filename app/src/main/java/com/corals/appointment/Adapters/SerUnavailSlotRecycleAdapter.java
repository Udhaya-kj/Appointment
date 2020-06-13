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
import com.corals.appointment.R;

import java.util.ArrayList;

public class SerUnavailSlotRecycleAdapter extends RecyclerView.Adapter<SerUnavailSlotRecycleAdapter.MyViewHolder> {
    ArrayList<String> arrayList1,arrayList2;
    Activity context;
    String code;
    int row_index;

    public SerUnavailSlotRecycleAdapter(Activity context, ArrayList<String> arrayList1, ArrayList<String> arrayList2, String code) {
        this.context = context;
        this.arrayList1 = arrayList1;
        this.arrayList2 = arrayList2;
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
        // holder.linearLayout_color.setBackgroundResource(R.drawable.left_round_corners_blue);
        holder.textView_slot.setText(arrayList1.get(position));
        holder.linearLayout_bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (code.equals("1")) {

                    if (holder.textView_response.getVisibility() == View.GONE) {
                        if(arrayList2.get(position).equals("1")){
                            getSlotUnavailDialog("Appointment already available on this slot. Cannot make this slot unavailable");
                        }
                        else {
                            holder.linearLayout_bg.setBackgroundResource(R.drawable.corner_change_appt_slot_green);
                            holder.textView_response.setVisibility(View.VISIBLE);
                            holder.textView_response.setText("Unavailable");
                        }
                    } else {
                        holder.textView_response.setVisibility(View.GONE);
                        holder.linearLayout_bg.setBackgroundResource(R.drawable.layout_bg_time_slots);
                    }
                } else if (code.equals("2")) {
                    if (holder.textView_response.getVisibility() == View.GONE) {
                        if(arrayList2.get(position).equals("1")){
                            getSlotUnavailDialog("Appointment already available on this slot. Staff cannot absent on this time");
                        }
                        else {
                            holder.linearLayout_bg.setBackgroundResource(R.drawable.corner_change_appt_slot_green);
                            holder.textView_response.setVisibility(View.VISIBLE);
                            holder.textView_response.setText("Absent");
                        }
                    } else {
                        holder.textView_response.setVisibility(View.GONE);
                        holder.linearLayout_bg.setBackgroundResource(R.drawable.layout_bg_time_slots);
                    }
                }
            }
        });


    }


    @Override
    public int getItemCount() {
        return arrayList1.size();
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

    private void getSlotUnavailDialog(String msg) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setMessage(msg);
        alertDialogBuilder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        arg0.dismiss();

                    }
                });


        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}



