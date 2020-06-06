package com.corals.appointment.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.corals.appointment.Activity.TimeSlotsActivity;
import com.corals.appointment.R;

import java.util.ArrayList;

public class ServicesAdapter_Calender extends ArrayAdapter<String> {

    private final Activity context;
    ArrayList<String> arrayList;
    ArrayList<String> arrayList1;
    String date;

    public ServicesAdapter_Calender(Activity context,String date, ArrayList<String> arrayList, ArrayList<String> arrayList1) {
        super(context, R.layout.layout_services_calender, arrayList);
        // TODO Auto-generated constructor stub

        this.context = context;
        this.date = date;
        this.arrayList = arrayList;
        this.arrayList1 = arrayList1;

    }

    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.layout_services_calender, null, true);

        TextView ser_name = (TextView) rowView.findViewById(R.id.text_service_name);
        TextView ser_dur = (TextView) rowView.findViewById(R.id.text_ser_dur);
        LinearLayout layout_services_calender = (LinearLayout) rowView.findViewById(R.id.layout_ser_calender);
        ser_name.setText(arrayList.get(position));
        ser_dur.setText(arrayList1.get(position));

        layout_services_calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(context, TimeSlotsActivity.class);
                i.putExtra("service", arrayList.get(position));
                i.putExtra("date", date);
                context.startActivity(i);
                ((Activity) context).finish();
            }
        });
        return rowView;

    }
}
