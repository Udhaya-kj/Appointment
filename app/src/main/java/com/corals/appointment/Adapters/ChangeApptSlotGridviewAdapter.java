package com.corals.appointment.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.corals.appointment.R;

import java.util.ArrayList;

public class ChangeApptSlotGridviewAdapter extends BaseAdapter {
    private final Activity context;
    ArrayList<String> arrayList;
    private static LayoutInflater inflater = null;

    public ChangeApptSlotGridviewAdapter(Activity context, ArrayList<String> arrayList) {
        // TODO Auto-generated constructor stub

        this.context = context;
        this.arrayList = arrayList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = inflater.inflate(R.layout.layout_change_appt_slot, null);
        TextView textView_slot = (TextView) rowView.findViewById(R.id.text_slot_change_appt);
        textView_slot.setText(arrayList.get(position));
        return rowView;
    }
}
