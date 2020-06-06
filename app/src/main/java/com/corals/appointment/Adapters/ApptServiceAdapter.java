package com.corals.appointment.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.corals.appointment.Activity.CalendarServicesActivity;
import com.corals.appointment.Activity.TimeSlotsActivity;
import com.corals.appointment.R;


import java.util.ArrayList;

public class ApptServiceAdapter extends RecyclerView.Adapter<ApptServiceAdapter.MyViewHolder> {
    ArrayList<String> arrayList1, arrayList2, arrayList_val;
    Activity context;
    String res;
    public ApptServiceAdapter(Activity context, ArrayList<String> arrayList1, ArrayList<String> arrayList2) {

        this.context = context;
        this.arrayList1 = arrayList1;
        this.arrayList2 = arrayList2;

    }

    @Override
    public ApptServiceAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_services_calender, parent, false);
        ApptServiceAdapter.MyViewHolder myViewHolder = new ApptServiceAdapter.MyViewHolder(v);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final ApptServiceAdapter.MyViewHolder holder, final int position) {

        holder.textView_ser_name.setText(arrayList1.get(position));
        holder.textView_ser_dur.setText(arrayList2.get(position)+" mins");

        holder.linearLayout_bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                res = arrayList1.get(position);
                Intent i = new Intent(context, TimeSlotsActivity.class);
                i.putExtra("resource", res);
                i.putExtra("date", CalendarServicesActivity.cal_date);
                context.startActivity(i);
                ((Activity)context).finish();
            }
        });

    }


    @Override
    public int getItemCount() {
        return arrayList1.size();
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

