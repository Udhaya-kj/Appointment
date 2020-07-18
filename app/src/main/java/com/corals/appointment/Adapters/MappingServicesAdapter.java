package com.corals.appointment.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.corals.appointment.R;

import java.util.ArrayList;
import java.util.List;

public class MappingServicesAdapter extends RecyclerView.Adapter<MappingServicesAdapter.MyViewHolder> {
    Activity context;
    ArrayList<String > list_ser_name,list_ser_load;


    public MappingServicesAdapter(Activity context, ArrayList<String> list_ser_name, ArrayList<String> list_ser_load) {
        this.context = context;
        this.list_ser_load = list_ser_load;
        this.list_ser_name = list_ser_name;

    }

    @Override
    public MappingServicesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_show_mapped_services, parent, false);
        MappingServicesAdapter.MyViewHolder myViewHolder = new MappingServicesAdapter.MyViewHolder(v);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MappingServicesAdapter.MyViewHolder holder, final int position) {

        holder.textView_ser_name.setText(list_ser_name.get(position));
        holder.textView_ser_load.setText(list_ser_load.get(position));
    }

    @Override
    public int getItemCount() {
        return list_ser_name.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView_ser_name,textView_ser_load;
        public MyViewHolder(View itemView) {
            super(itemView);
            textView_ser_name = (TextView) itemView.findViewById(R.id.tv_mapped_ser_name);
            textView_ser_load = (TextView) itemView.findViewById(R.id.tv_mapped_ser_load);

        }
    }
}



