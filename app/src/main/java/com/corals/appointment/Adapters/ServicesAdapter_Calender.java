package com.corals.appointment.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.corals.appointment.Activity.BookingResourcesActivity;
import com.corals.appointment.Activity.CalendarViewActivity;
import com.corals.appointment.Activity.TimeSlotsActivity;
import com.corals.appointment.Client.model.AppointmentService;
import com.corals.appointment.R;

import java.util.ArrayList;
import java.util.List;

public class ServicesAdapter_Calender extends BaseAdapter {
    String cus_id, cus,cus_email,cus_mob;
    private final Activity context;
    List<AppointmentService> appointmentServices;

    public ServicesAdapter_Calender(Activity context, List<AppointmentService> appointmentServices, String cus_id, String cus, String cus_email, String cus_mob) {
        // TODO Auto-generated constructor stub

        this.context = context;
        this.appointmentServices = appointmentServices;
        this.cus_id = cus_id;
        this.cus = cus;
        this.cus_email = cus_email;
        this.cus_mob = cus_mob;
    }

    @Override
    public int getCount() {
        return appointmentServices.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.layout_services_calender, null, true);

        TextView ser_name = (TextView) rowView.findViewById(R.id.text_service_name);
        TextView ser_dur = (TextView) rowView.findViewById(R.id.text_ser_dur);
        LinearLayout layout_services_calender = (LinearLayout) rowView.findViewById(R.id.layout_ser_calender);
        ser_name.setText(appointmentServices.get(position).getSerName());

        layout_services_calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(context, CalendarViewActivity.class);
                i.putExtra("service_id", appointmentServices.get(position).getSerId());
                i.putExtra("service", appointmentServices.get(position).getSerName());
                i.putExtra("cus_id", cus_id);
                i.putExtra("cus", cus);
                i.putExtra("cus_email", cus_email);
                i.putExtra("cus_mob", cus_mob);
                i.putExtra("page_id", "2");
                i.putExtra("service_dur", appointmentServices.get(position).getSerDuration());
                context.startActivity(i);
                ((Activity) context).finish();
                ((Activity) context).overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);
            }
        });
        return rowView;

    }
}
