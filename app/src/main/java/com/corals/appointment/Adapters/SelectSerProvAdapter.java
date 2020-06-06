package com.corals.appointment.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.corals.appointment.Activity.SelectServiceProvidersActivity;
import com.corals.appointment.R;

import java.util.ArrayList;

public class SelectSerProvAdapter extends ArrayAdapter<String> {

    private final Activity context;
    ArrayList<String> arrayList;


    public SelectSerProvAdapter(Activity context, ArrayList<String> arrayList) {
        super(context, R.layout.layout_select_ser_providers, arrayList);
        // TODO Auto-generated constructor stub

        this.context = context;
        this.arrayList = arrayList;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.layout_select_ser_providers, null, true);

        TextView titleText = (TextView) rowView.findViewById(R.id.text_staff_name);
        CheckBox checkBox = (CheckBox) rowView.findViewById(R.id.checkbox);
        titleText.setText(arrayList.get(position));

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked){
                    Toast.makeText(context, ""+arrayList.get(position), Toast.LENGTH_SHORT).show();
                }
                else {
                    if(SelectServiceProvidersActivity.providers.length()==0){
                        SelectServiceProvidersActivity.providers=arrayList.get(position);
                    }
                    else {
                        SelectServiceProvidersActivity.providers=SelectServiceProvidersActivity.providers+", "+arrayList.get(position);
                    }
                    //Toast.makeText(context, ""+SelectServiceProvidersActivity.providers, Toast.LENGTH_SHORT).show();
                }
            }
        });
        return rowView;

    }

    ;


}

