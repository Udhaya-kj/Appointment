package com.corals.appointment.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.corals.appointment.R;

import java.util.ArrayList;

public class CountrySpinnerAdapter extends BaseAdapter {
    Context context;
    LayoutInflater inflter;
    private Integer[] imageArray;
    private ArrayList<String> country_code_list,dial_code_list;

    public CountrySpinnerAdapter(Context applicationContext,  Integer[] imageArray,ArrayList<String> country_code_list,ArrayList<String> dial_code_list) {
        this.context = applicationContext;
        this.imageArray = imageArray;
        this.country_code_list = country_code_list;
        this.dial_code_list = dial_code_list;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return imageArray.length;
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
        view = inflter.inflate(R.layout.custom_spinner_country, null);
        ImageView imageView_country = (ImageView) view.findViewById(R.id.image_flag);
        TextView country_code = (TextView) view.findViewById(R.id.textView_country_code);
        TextView dial_code = (TextView) view.findViewById(R.id.textView_dial_code);
        imageView_country.setImageResource(imageArray[i]);
        country_code.setText(country_code_list.get(i));
        dial_code.setText(dial_code_list.get(i));
        return view;
    }
}

