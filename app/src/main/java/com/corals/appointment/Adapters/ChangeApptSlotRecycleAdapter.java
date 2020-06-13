package com.corals.appointment.Adapters;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;


import com.corals.appointment.Activity.ChangeApptActivity;
import com.corals.appointment.R;

import java.util.ArrayList;

public class ChangeApptSlotRecycleAdapter extends RecyclerView.Adapter<ChangeApptSlotRecycleAdapter.MyViewHolder> {
    ArrayList<String> arrayList1;
    Activity context;
    String time;
    int index = -1;


    public ChangeApptSlotRecycleAdapter(Activity context, ArrayList<String> arrayList1, String time) {
        this.context = context;
        this.arrayList1 = arrayList1;
        this.time = time;

    }

    @Override
    public ChangeApptSlotRecycleAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_change_appt_slot, parent, false);
        ChangeApptSlotRecycleAdapter.MyViewHolder myViewHolder = new ChangeApptSlotRecycleAdapter.MyViewHolder(v);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final ChangeApptSlotRecycleAdapter.MyViewHolder holder, final int position) {

        holder.textView_slot.setText(arrayList1.get(position));
        holder.linearLayout_bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                index = position;
                notifyDataSetChanged();
            }
        });


        if (index == position) {
            holder.linearLayout_bg.setBackgroundResource(R.drawable.corner_change_appt_slot_blue);

        } else {
            holder.linearLayout_bg.setBackgroundResource(R.drawable.layout_bg_time_slots);
        }

        if (arrayList1.get(position).equals(time)) {
            holder.linearLayout_bg.setBackgroundResource(R.drawable.corner_change_appt_slot_green);
        }

    }


    @Override
    public int getItemCount() {
        return arrayList1.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textView_slot;
        LinearLayout linearLayout_bg;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView_slot = (TextView) itemView.findViewById(R.id.text_slot_change_appt);
            linearLayout_bg = (LinearLayout) itemView.findViewById(R.id.layout_change_slot);
        }
    }
}


