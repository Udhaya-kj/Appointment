package com.corals.appointment.Adapters;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.corals.appointment.Activity.AddStaffActivity;
import com.corals.appointment.Client.model.AppointmentService;
import com.corals.appointment.R;

import java.util.ArrayList;
import java.util.List;

public class MapServiceResourceRecyclerAdapter  extends RecyclerView.Adapter<MapServiceResourceRecyclerAdapter.MyViewHolder> {
    Activity context;
    List<AppointmentService> appointmentServiceArrayList;

    public MapServiceResourceRecyclerAdapter(Activity context,List<AppointmentService> appointmentServiceArrayList) {
        this.context = context;
        this.appointmentServiceArrayList = appointmentServiceArrayList;
    }

    @Override
    public MapServiceResourceRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_map_service_to_resource, parent, false);
        MapServiceResourceRecyclerAdapter.MyViewHolder myViewHolder = new MapServiceResourceRecyclerAdapter.MyViewHolder(v);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MapServiceResourceRecyclerAdapter.MyViewHolder holder, final int position) {

        holder.textView_serv_name.setText(appointmentServiceArrayList.get(position).getSerName());

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked){
                    AddStaffActivity.arrayList_map_service.remove(appointmentServiceArrayList.get(position).getSerId());
                    Log.d("list_map_service--->", "onCheckedChanged: "+AddStaffActivity.arrayList_map_service);
                }
                else {
                    AddStaffActivity.arrayList_map_service.add(appointmentServiceArrayList.get(position).getSerId());
                    Log.d("list_map_service--->", "onCheckedChanged: "+AddStaffActivity.arrayList_map_service);
                }
            }
        });

        holder.linearLayout_bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.checkBox.isChecked()){
                    holder.checkBox.setChecked(false);
                    AddStaffActivity.arrayList_map_service.remove(appointmentServiceArrayList.get(position).getSerId());
                }
                else {
                    AddStaffActivity.arrayList_map_service.add(appointmentServiceArrayList.get(position).getSerId());
                    holder.checkBox.setChecked(true);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return appointmentServiceArrayList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textView_serv_name;
        LinearLayout linearLayout_bg;
        CheckBox checkBox;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView_serv_name = (TextView) itemView.findViewById(R.id.text_service_name);
            linearLayout_bg = (LinearLayout) itemView.findViewById(R.id.layout_map_ser_res);
            checkBox = (CheckBox) itemView.findViewById(R.id.checkbox);
        }
    }
}



