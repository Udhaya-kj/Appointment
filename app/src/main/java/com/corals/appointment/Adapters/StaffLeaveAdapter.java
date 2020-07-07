package com.corals.appointment.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.corals.appointment.Activity.CalendarViewActivity;
import com.corals.appointment.Activity.ServiceUnavailCalendarActivity;
import com.corals.appointment.Activity.TimeSlotsActivity;
import com.corals.appointment.Client.model.AppointmentResources;
import com.corals.appointment.R;

import java.util.ArrayList;
import java.util.List;

public class StaffLeaveAdapter extends RecyclerView.Adapter<StaffLeaveAdapter.MyViewHolder> {
    Activity context;
    String flag,service_id,service, cus_id, cus,cus_email,cus_mob;
    List<AppointmentResources> appointmentResources;
    public StaffLeaveAdapter(Activity context,  List<AppointmentResources> appointmentResources,String flag,String service_id,String service,String cus_id,String cus,String cus_email,String cus_mob) {
        this.context = context;
        this.flag = flag;
        this.service_id = service_id;
        this.service = service;
        this.cus_id = cus_id;
        this.cus = cus;
        this.cus_mob = cus_mob;
        this.cus_email = cus_email;
        this.appointmentResources = appointmentResources;

    }

    @Override
    public StaffLeaveAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_staff_leave, parent, false);
        StaffLeaveAdapter.MyViewHolder myViewHolder = new StaffLeaveAdapter.MyViewHolder(v);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final StaffLeaveAdapter.MyViewHolder holder, final int position) {

        holder.textView_staff_name.setText(appointmentResources.get(position).getResName());
        // holder.textView_ser_dur.setText(arrayList2.get(position)+" mins");

        holder.linearLayout_bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(flag) && flag.equals("01")) {
                    Intent i = new Intent(context, ServiceUnavailCalendarActivity.class);
                    i.putExtra("task", "2");
                    i.putExtra("service_id", appointmentResources.get(position).getResId());
                    i.putExtra("service", appointmentResources.get(position).getResName());
                    context.startActivity(i);
                    ((Activity) context).finish();
                    ((Activity) context).overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);
                }
                else  if (!TextUtils.isEmpty(flag)) {
                    Intent i = new Intent(context, CalendarViewActivity.class);
                    i.putExtra("page_id", flag);
                    i.putExtra("res_id", appointmentResources.get(position).getResId());
                    i.putExtra("res", appointmentResources.get(position).getResName());
                    i.putExtra("service_id", service_id);
                    i.putExtra("service",service );
                    i.putExtra("cus_id", cus_id);
                    i.putExtra("cus", cus);
                    i.putExtra("cus_email",cus_email );
                    i.putExtra("cus_mob",cus_mob );
                    context.startActivity(i);
                    ((Activity) context).finish();
                    ((Activity) context).overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);
                }
            }

        });

    }


    @Override
    public int getItemCount() {
        return appointmentResources.size();
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



