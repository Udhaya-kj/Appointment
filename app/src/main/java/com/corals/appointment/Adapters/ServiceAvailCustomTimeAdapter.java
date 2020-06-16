package com.corals.appointment.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.corals.appointment.Activity.CalendarServicesActivity;
import com.corals.appointment.Activity.TimeSlotsActivity;
import com.corals.appointment.R;

import java.util.ArrayList;

public class ServiceAvailCustomTimeAdapter extends RecyclerView.Adapter<ServiceAvailCustomTimeAdapter.MyViewHolder> {
    ArrayList<String> list_sun, list_mon, list_tue, list_wed, list_thu, list_fri, list_sat;
    Activity context;
    String res;

    public ServiceAvailCustomTimeAdapter(Activity context, ArrayList<String> list_sun, ArrayList<String> list_mon, ArrayList<String> list_tue, ArrayList<String> list_wed, ArrayList<String> list_thu, ArrayList<String> list_fri, ArrayList<String> list_sat) {

        this.context = context;
        this.list_sun = list_sun;
        this.list_mon = list_mon;
        this.list_tue = list_tue;
        this.list_wed = list_wed;
        this.list_thu = list_thu;
        this.list_fri = list_fri;
        this.list_sat = list_sat;

    }

    @Override
    public ServiceAvailCustomTimeAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_serv_avail_custom_time, parent, false);
        ServiceAvailCustomTimeAdapter.MyViewHolder myViewHolder = new ServiceAvailCustomTimeAdapter.MyViewHolder(v);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final ServiceAvailCustomTimeAdapter.MyViewHolder holder, final int position) {
//sunday
        Log.d("list_sun---", "list_sun: " + list_sun.size());
        if (list_sun.size() == 1) {
            holder.linearLayout__sun.setVisibility(View.VISIBLE);
            holder.textView_sun_time1.setText(list_sun.get(0));
        } else if (list_sun.size() == 2) {
            holder.linearLayout__sun.setVisibility(View.VISIBLE);
            holder.linearLayout__sun2.setVisibility(View.VISIBLE);
            holder.textView_sun_time1.setText(list_sun.get(0));
            holder.textView_sun_time2.setText(list_sun.get(1));
        }
//mon
        Log.d("list_sun---", "list_mon: " + list_mon.size());
        if (list_mon.size() == 1) {
            holder.linearLayout__mon.setVisibility(View.VISIBLE);
            holder.textView_mon_time1.setText(list_mon.get(0));
        } else if (list_mon.size() == 2) {
            holder.linearLayout__mon.setVisibility(View.VISIBLE);
            holder.linearLayout__mon2.setVisibility(View.VISIBLE);
            holder.textView_mon_time1.setText(list_mon.get(0));
            holder.textView_mon_time2.setText(list_mon.get(1));
        }
//tue
        Log.d("list_sun---", "list_tue: " + list_tue.size());
        if (list_tue.size() == 1) {
            holder.linearLayout__tue.setVisibility(View.VISIBLE);
            holder.textView_tue_time1.setText(list_tue.get(0));
        } else if (list_tue.size() == 2) {
            holder.linearLayout__tue.setVisibility(View.VISIBLE);
            holder.linearLayout__tue2.setVisibility(View.VISIBLE);
            holder.textView_tue_time1.setText(list_tue.get(0));
            holder.textView_tue_time2.setText(list_tue.get(1));
        }
//wed
        Log.d("list_sun---", "list_wed: " + list_wed.size());
        if (list_wed.size() == 1) {
            holder.linearLayout__wed.setVisibility(View.VISIBLE);
            holder.textView_wed_time1.setText(list_wed.get(0));
        } else if (list_wed.size() == 2) {
            holder.linearLayout__wed.setVisibility(View.VISIBLE);
            holder.linearLayout__wed2.setVisibility(View.VISIBLE);
            holder.textView_wed_time1.setText(list_wed.get(0));
            holder.textView_wed_time2.setText(list_wed.get(1));
        }
//thu
        Log.d("list_sun---", "list_thu: " + list_thu.size());
        if (list_thu.size() == 1) {
            holder.linearLayout__thur.setVisibility(View.VISIBLE);
            holder.textView_thu_time1.setText(list_thu.get(0));
        } else if (list_thu.size() == 2) {
            holder.linearLayout__thur.setVisibility(View.VISIBLE);
            holder.linearLayout__thur2.setVisibility(View.VISIBLE);
            holder.textView_thu_time1.setText(list_thu.get(0));
            holder.textView_thu_time2.setText(list_thu.get(1));
        }
//fri
        Log.d("list_sun---", "list_fri: " + list_fri.size());
        if (list_fri.size() == 1) {
            holder.linearLayout__fri.setVisibility(View.VISIBLE);
            holder.textView_fri_time1.setText(list_fri.get(0));
        } else if (list_fri.size() == 2) {
            holder.linearLayout__fri.setVisibility(View.VISIBLE);
            holder.linearLayout__fri2.setVisibility(View.VISIBLE);
            holder.textView_fri_time1.setText(list_fri.get(0));
            holder.textView_fri_time2.setText(list_fri.get(1));
        }
//sat
        Log.d("list_sun---", "list_sat: " + list_sat.size());
        if (list_sat.size() == 1) {
            holder.linearLayout__sat.setVisibility(View.VISIBLE);
            holder.textView_sat_time1.setText(list_sat.get(0));
        } else if (list_sat.size() == 2) {
            holder.linearLayout__sat.setVisibility(View.VISIBLE);
            holder.linearLayout__sat2.setVisibility(View.VISIBLE);
            holder.textView_sat_time1.setText(list_sat.get(0));
            holder.textView_sat_time2.setText(list_sat.get(1));
        }

        //Edit sunday
        holder.imageView_sun_time1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        holder.imageView_sun_time2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //Edit monday
        holder.imageView_mon_time1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        holder.imageView_mon_time2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //Edit tuesday
        holder.imageView_tue_time1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        holder.imageView_tue_time2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //Edit wednesday
        holder.imageView_wed_time1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        holder.imageView_wed_time2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //Edit thursday
        holder.imageView_thu_time1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        holder.imageView_thu_time2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //Edit friday
        holder.imageView_fri_time1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        holder.imageView_fri_time2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //Edit saturday
        holder.imageView_sat_time1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        holder.imageView_sat_time2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }


    @Override
    public int getItemCount() {
        return 1;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textView_sun_time1, textView_sun_time2, textView_mon_time1, textView_mon_time2, textView_tue_time1, textView_tue_time2, textView_wed_time1, textView_wed_time2,
                textView_thu_time1, textView_thu_time2, textView_fri_time1, textView_fri_time2, textView_sat_time1, textView_sat_time2;
        LinearLayout linearLayout__sun, linearLayout__mon, linearLayout__tue, linearLayout__wed, linearLayout__thur, linearLayout__fri, linearLayout__sat,
                linearLayout__sun2, linearLayout__mon2, linearLayout__tue2, linearLayout__wed2, linearLayout__thur2, linearLayout__fri2, linearLayout__sat2;
        ImageView imageView_sun_time1, imageView_sun_time2, imageView_mon_time1, imageView_mon_time2, imageView_tue_time1, imageView_tue_time2, imageView_wed_time1, imageView_wed_time2,
                imageView_thu_time1, imageView_thu_time2, imageView_fri_time1, imageView_fri_time2, imageView_sat_time1, imageView_sat_time2;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView_sun_time1 = (TextView) itemView.findViewById(R.id.text_sunday_time1);
            textView_sun_time2 = (TextView) itemView.findViewById(R.id.text_sunday_time2);
            textView_mon_time1 = (TextView) itemView.findViewById(R.id.text_monday_time1);
            textView_mon_time2 = (TextView) itemView.findViewById(R.id.text_monday_time2);
            textView_tue_time1 = (TextView) itemView.findViewById(R.id.text_tuesday_time1);
            textView_tue_time2 = (TextView) itemView.findViewById(R.id.text_tuesday_time2);
            textView_wed_time1 = (TextView) itemView.findViewById(R.id.text_wednesday_time1);
            textView_wed_time2 = (TextView) itemView.findViewById(R.id.text_wednesday_time2);
            textView_thu_time1 = (TextView) itemView.findViewById(R.id.text_thursday_time1);
            textView_thu_time2 = (TextView) itemView.findViewById(R.id.text_thursday_time2);
            textView_fri_time1 = (TextView) itemView.findViewById(R.id.text_friday_time1);
            textView_fri_time2 = (TextView) itemView.findViewById(R.id.text_friday_time2);
            textView_sat_time1 = (TextView) itemView.findViewById(R.id.text_saturday_time1);
            textView_sat_time2 = (TextView) itemView.findViewById(R.id.text_saturday_time2);

            imageView_sun_time1 = (ImageView) itemView.findViewById(R.id.image_edit_sun1);
            imageView_sun_time2 = (ImageView) itemView.findViewById(R.id.image_edit_sun2);
            imageView_mon_time1 = (ImageView) itemView.findViewById(R.id.image_edit_mon1);
            imageView_mon_time2 = (ImageView) itemView.findViewById(R.id.image_edit_mon2);
            imageView_tue_time1 = (ImageView) itemView.findViewById(R.id.image_edit_tue1);
            imageView_tue_time2 = (ImageView) itemView.findViewById(R.id.image_edit_tue2);
            imageView_wed_time1 = (ImageView) itemView.findViewById(R.id.image_edit_wed1);
            imageView_wed_time2 = (ImageView) itemView.findViewById(R.id.image_edit_wed2);
            imageView_thu_time1 = (ImageView) itemView.findViewById(R.id.image_edit_thu1);
            imageView_thu_time2 = (ImageView) itemView.findViewById(R.id.image_edit_thu2);
            imageView_fri_time1 = (ImageView) itemView.findViewById(R.id.image_edit_fri1);
            imageView_fri_time2 = (ImageView) itemView.findViewById(R.id.image_edit_fri2);
            imageView_sat_time1 = (ImageView) itemView.findViewById(R.id.image_edit_sat1);
            imageView_sat_time2 = (ImageView) itemView.findViewById(R.id.image_edit_sat2);

            linearLayout__sun = (LinearLayout) itemView.findViewById(R.id.layout_sunday);
            linearLayout__mon = (LinearLayout) itemView.findViewById(R.id.layout_monday);
            linearLayout__tue = (LinearLayout) itemView.findViewById(R.id.layout_tuesday);
            linearLayout__wed = (LinearLayout) itemView.findViewById(R.id.layout_wednesday);
            linearLayout__thur = (LinearLayout) itemView.findViewById(R.id.layout_thursday);
            linearLayout__fri = (LinearLayout) itemView.findViewById(R.id.layout_friday);
            linearLayout__sat = (LinearLayout) itemView.findViewById(R.id.layout_saturday);

            linearLayout__sun2 = (LinearLayout) itemView.findViewById(R.id.layout_sunday_time2);
            linearLayout__mon2 = (LinearLayout) itemView.findViewById(R.id.layout_monday_time2);
            linearLayout__tue2 = (LinearLayout) itemView.findViewById(R.id.layout_tuesday_time2);
            linearLayout__wed2 = (LinearLayout) itemView.findViewById(R.id.layout_wednesday_time2);
            linearLayout__thur2 = (LinearLayout) itemView.findViewById(R.id.layout_thursday_time2);
            linearLayout__fri2 = (LinearLayout) itemView.findViewById(R.id.layout_friday_time2);
            linearLayout__sat2 = (LinearLayout) itemView.findViewById(R.id.layout_saturday_time2);
        }
    }
}


