package com.corals.appointment.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.corals.appointment.Client.model.Appointments;
import com.corals.appointment.Dialogs.ViewApptCustomersBottomSheetDialog;
import com.corals.appointment.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

public class ViewCustomersApptRecyclerAdapter extends RecyclerView.Adapter<ViewCustomersApptRecyclerAdapter.MyViewHolder> {

    Activity context;
    List<Appointments> appointmentsList;

    public ViewCustomersApptRecyclerAdapter(Activity context, List<Appointments> appointmentsList) {
        this.context = context;
        this.appointmentsList = appointmentsList;
    }

    @Override
    public ViewCustomersApptRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_customers_appt, parent, false);
        ViewCustomersApptRecyclerAdapter.MyViewHolder myViewHolder = new ViewCustomersApptRecyclerAdapter.MyViewHolder(v);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewCustomersApptRecyclerAdapter.MyViewHolder holder, final int position) {
        holder.textView_dt_time.setText(appointmentsList.get(position).getStartTime() + "-" + appointmentsList.get(position).getEndTime());

    }

    @Override
    public int getItemCount() {
        return appointmentsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textView_dt_time;
        LinearLayout linearLayout_bg;
        ImageView imageView_avail;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView_dt_time = (TextView) itemView.findViewById(R.id.tv_slot_dt_time);
            linearLayout_bg = (LinearLayout) itemView.findViewById(R.id.layout_appt_cust_slot);
            imageView_avail = (ImageView) itemView.findViewById(R.id.image_available);
        }
    }
}



