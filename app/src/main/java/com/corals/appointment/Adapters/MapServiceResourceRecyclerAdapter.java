package com.corals.appointment.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.corals.appointment.Activity.AddStaffActivity;
import com.corals.appointment.R;

import java.util.ArrayList;

public class MapServiceResourceRecyclerAdapter  extends RecyclerView.Adapter<MapServiceResourceRecyclerAdapter.MyViewHolder> {
    ArrayList<String> arrayList1;
    Activity context;
    String res;

    public MapServiceResourceRecyclerAdapter(Activity context, ArrayList<String> arrayList1) {
        this.context = context;
        this.arrayList1 = arrayList1;

    }

    @Override
    public MapServiceResourceRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_map_service_to_resource, parent, false);
        MapServiceResourceRecyclerAdapter.MyViewHolder myViewHolder = new MapServiceResourceRecyclerAdapter.MyViewHolder(v);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MapServiceResourceRecyclerAdapter.MyViewHolder holder, final int position) {

        holder.textView_serv_name.setText(arrayList1.get(position));

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked){
                    AddStaffActivity.arrayList_map_service.remove(arrayList1.get(position));
                }
                else {
                    AddStaffActivity.arrayList_map_service.add(arrayList1.get(position));

                }
            }
        });

        holder.linearLayout_bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.checkBox.isChecked()){
                    holder.checkBox.setChecked(false);
                    AddStaffActivity.arrayList_map_service.remove(arrayList1.get(position));
                }
                else {
                    AddStaffActivity.arrayList_map_service.add(arrayList1.get(position));
                    holder.checkBox.setChecked(true);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList1.size();
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



