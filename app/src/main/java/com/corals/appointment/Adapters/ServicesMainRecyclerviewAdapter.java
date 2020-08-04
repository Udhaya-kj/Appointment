package com.corals.appointment.Adapters;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.corals.appointment.Client.model.AppointmentService;
import com.corals.appointment.Interface.AdapterCallback;
import com.corals.appointment.Interface.OnItemClick_Main_Services;
import com.corals.appointment.R;

import java.util.List;

public class ServicesMainRecyclerviewAdapter extends RecyclerView.Adapter<ServicesMainRecyclerviewAdapter.MyViewHolder> {
    List<AppointmentService> appointmentServices;
    Activity context;
    int index = -1;
    private AdapterCallback adapterCallback;
    private OnItemClick_Main_Services mCallback;
    public ServicesMainRecyclerviewAdapter(Activity context, List<AppointmentService> appointmentServices) {

        this.context = context;
        this.appointmentServices = appointmentServices;
        adapterCallback = ((AdapterCallback) context);
        mCallback = ((OnItemClick_Main_Services) context);
    }

    @Override
    public ServicesMainRecyclerviewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_main_services, parent, false);
        ServicesMainRecyclerviewAdapter.MyViewHolder myViewHolder = new ServicesMainRecyclerviewAdapter.MyViewHolder(v);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final ServicesMainRecyclerviewAdapter.MyViewHolder holder, final int position) {

        holder.textView_ser_name.setText(appointmentServices.get(position).getSerName());
        holder.imageView_indicator.setColorFilter(ContextCompat.getColor(context, R.color.light_grey), android.graphics.PorterDuff.Mode.MULTIPLY);

        //holder.textView_ser_dur.setText(appointmentServices.get(position).getSerDuration());
        holder.linearLayout_bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterCallback.onMethodCallback();
                mCallback.onClick(appointmentServices.get(position).getSerId(),appointmentServices.get(position).getSerName(),appointmentServices.get(position).getSerDuration());
                index = position;
                notifyDataSetChanged();
            }
        });

        if (index == position) {
            holder.imageView_indicator.setColorFilter(Color.TRANSPARENT);
            Log.d("SerName---", "onBindViewHolder: "+appointmentServices.get(position).getSerName());
        } else {
            holder.imageView_indicator.setColorFilter(Color.parseColor("#A5D1FE"));
        }
    }


    @Override
    public int getItemCount() {
        return appointmentServices.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textView_ser_name, textView_ser_dur;
        LinearLayout linearLayout_bg;
        ImageView imageView_indicator;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView_ser_name = (TextView) itemView.findViewById(R.id.text_service_name);
            textView_ser_dur = (TextView) itemView.findViewById(R.id.text_ser_dur);
            linearLayout_bg = (LinearLayout) itemView.findViewById(R.id.layout_ser_calender);
            imageView_indicator = (ImageView) itemView.findViewById(R.id.image_tick);
        }
    }

}


