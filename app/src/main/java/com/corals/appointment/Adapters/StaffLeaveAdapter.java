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
import com.corals.appointment.R;

import java.util.ArrayList;

public class StaffLeaveAdapter extends RecyclerView.Adapter<StaffLeaveAdapter.MyViewHolder> {
    ArrayList<String> arrayList1;
    Activity context;
    public StaffLeaveAdapter(Activity context, ArrayList<String> arrayList1) {

        this.context = context;
        this.arrayList1 = arrayList1;

    }

    @Override
    public StaffLeaveAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_staff_leave, parent, false);
        StaffLeaveAdapter.MyViewHolder myViewHolder = new StaffLeaveAdapter.MyViewHolder(v);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final StaffLeaveAdapter.MyViewHolder holder, final int position) {

        holder.textView_staff_name.setText(arrayList1.get(position));
        // holder.textView_ser_dur.setText(arrayList2.get(position)+" mins");

        holder.linearLayout_bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(context, ServiceUnavailCalendarActivity.class);
                i.putExtra("task", "2");
                i.putExtra("service", arrayList1.get(position));
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

        TextView textView_staff_name;
        LinearLayout linearLayout_bg;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView_staff_name = (TextView) itemView.findViewById(R.id.text_staff_name);
            linearLayout_bg = (LinearLayout) itemView.findViewById(R.id.layout_staff_leave);
        }
    }
}



