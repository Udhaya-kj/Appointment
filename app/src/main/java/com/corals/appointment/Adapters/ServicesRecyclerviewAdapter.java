package com.corals.appointment.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.corals.appointment.Activity.TimeSlotsActivity;
import com.corals.appointment.Activity.ViewApptServiceActivity;
import com.corals.appointment.R;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;

public class ServicesRecyclerviewAdapter extends RecyclerView.Adapter<ServicesRecyclerviewAdapter.MyViewHolder> implements DatePickerDialog.OnDateSetListener {
    ArrayList<String> arrayList1, arrayList2, arrayList_val;
    Activity context;
    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;
    int Year, Month, Day, Hour, Minute;
    Calendar calendar;
    String res;
    public ServicesRecyclerviewAdapter(Activity context, ArrayList<String> arrayList1, ArrayList<String> arrayList2) {

        this.context = context;
        this.arrayList1 = arrayList1;
        this.arrayList2 = arrayList2;

        calendar = Calendar.getInstance();
        Year = calendar.get(Calendar.YEAR);
        Month = calendar.get(Calendar.MONTH);
        Day = calendar.get(Calendar.DAY_OF_MONTH);
        Hour = calendar.get(Calendar.HOUR_OF_DAY);
        Minute = calendar.get(Calendar.MINUTE);
    }

    @Override
    public ServicesRecyclerviewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_appt_services, parent, false);
        ServicesRecyclerviewAdapter.MyViewHolder myViewHolder = new ServicesRecyclerviewAdapter.MyViewHolder(v);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final ServicesRecyclerviewAdapter.MyViewHolder holder, final int position) {

        holder.textView_ser_name.setText(arrayList1.get(position));
        holder.textView_ser_dur.setText(arrayList2.get(position));

        holder.linearLayout_bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(context, ViewApptServiceActivity.class);
                i.putExtra("service", arrayList1.get(position));
                //i.putExtra("date", CalendarServicesActivity.cal_date);
                context.startActivity(i);
                ((Activity)context).finish();
                ((Activity) context).overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);
            }
        });

    }


    @Override
    public int getItemCount() {
        return arrayList1.size();
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;

        //Toast.makeText(context, date + "," + res, Toast.LENGTH_LONG).show();

        //TextView text_datepicker = (TextView) findViewById(R.id.text_datepicker);
        // text_datepicker.setText(date);
        Intent i = new Intent(context, TimeSlotsActivity.class);
        i.putExtra("resource", res);
        i.putExtra("date", date);
        context.startActivity(i);
        ((Activity) context).finish();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textView_ser_name,textView_ser_dur;
        LinearLayout linearLayout_bg;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView_ser_name = (TextView) itemView.findViewById(R.id.text_service_name);
            textView_ser_dur = (TextView) itemView.findViewById(R.id.text_ser_dur);
            linearLayout_bg = (LinearLayout) itemView.findViewById(R.id.layout_ser_calender);
        }
    }
}

