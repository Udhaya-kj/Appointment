package com.corals.appointment.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.corals.appointment.Client.model.AppointmentResources;
import com.corals.appointment.R;

import java.util.List;

public class CustomStaffSpinnerAdapter extends BaseAdapter {
    Context context;
    List<AppointmentResources> appointmentResources;
    LayoutInflater inflter;

    public CustomStaffSpinnerAdapter(Context applicationContext,  List<AppointmentResources> appointmentResources) {
        this.context = applicationContext;
        this.appointmentResources = appointmentResources;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return appointmentResources.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.custom_spinner_staff_change_appt, null);
        TextView names = (TextView) view.findViewById(R.id.textView);
        names.setText(appointmentResources.get(i).getResName());
        return view;
    }
}
