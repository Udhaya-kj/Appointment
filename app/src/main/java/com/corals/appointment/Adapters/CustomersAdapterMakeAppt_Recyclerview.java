package com.corals.appointment.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.corals.appointment.Activity.ApptConfirmActivity;
import com.corals.appointment.Client.model.InlineResponse20013Customersrec;
import com.corals.appointment.Model.CustomersModel;
import com.corals.appointment.R;

import java.util.ArrayList;
import java.util.List;

public class CustomersAdapterMakeAppt_Recyclerview extends RecyclerView.Adapter<CustomersAdapterMakeAppt_Recyclerview.MyViewHolder> implements Filterable {
    Activity context;

    String ser_id;
    String date;
    String slot_no;
    String startTime;
    String endTime,res_id,res,service;
    private ArrayList<CustomersModel> customersModelArrayList;
    private ArrayList<CustomersModel> mDisplayedValues;

    public CustomersAdapterMakeAppt_Recyclerview(Activity context, ArrayList<CustomersModel> mCustomersValues, String ser_id, String date, String slot_no, String startTime, String endTime, String res_id, String res, String service) {
        this.context = context;
        this.customersModelArrayList = mCustomersValues;
        this.mDisplayedValues = mCustomersValues;
        this.ser_id = ser_id;
        this.date = date;
        this.slot_no = slot_no;
        this.startTime = startTime;
        this.endTime = endTime;
        this.res_id = res_id;
        this.res = res;
        this.service = service;
    }

    @Override
    public CustomersAdapterMakeAppt_Recyclerview.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_customers_make_appt, parent, false);
        CustomersAdapterMakeAppt_Recyclerview.MyViewHolder myViewHolder = new CustomersAdapterMakeAppt_Recyclerview.MyViewHolder(v);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final CustomersAdapterMakeAppt_Recyclerview.MyViewHolder holder, final int position) {

        CustomersModel customersrec=mDisplayedValues.get(position);
        holder.name.setText(customersrec.getName());
        holder.mobile.setText(customersrec.getMobile());
        holder.linearLayout_bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, ApptConfirmActivity.class);
                i.putExtra("cus_name", mDisplayedValues.get(position).getName());
                i.putExtra("cus_mob", mDisplayedValues.get(position).getMobile());
                i.putExtra("cus_id", mDisplayedValues.get(position).getCus_id());
                i.putExtra("cus_email", mDisplayedValues.get(position).getEmail());
                i.putExtra("service_id", ser_id);
                i.putExtra("service", service);
                i.putExtra("date", date);
                i.putExtra("slot_no",slot_no);
                i.putExtra("start_time", startTime);
                i.putExtra("end_time", endTime);
                i.putExtra("res_id", res_id);
                i.putExtra("res", res);
                context.startActivity(i);
                ((Activity) context).finish();
                ((Activity) context).overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);

            }
        });

    }


    @Override
    public int getItemCount() {
        return mDisplayedValues.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, mobile;
        LinearLayout linearLayout_bg;
        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.text_cus_name);
            mobile = (TextView) itemView.findViewById(R.id.text_cus_mob);
            linearLayout_bg = (LinearLayout) itemView.findViewById(R.id.layout_row_customer_make_appt);
            // cardView = (cardView) itemView.findViewById(R.id.card_time_text);
        }
    }

    //Filterable
    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mDisplayedValues = (ArrayList<CustomersModel>) results.values; // has the filtered values
                notifyDataSetChanged();  // notifies the data with new filtered values
            }
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();        // Holds the results of a filtering operation in values
                ArrayList<CustomersModel> FilteredArrList = new ArrayList<CustomersModel>();
                if (customersModelArrayList == null) {
                    customersModelArrayList = new ArrayList<CustomersModel>(mDisplayedValues); // saves the original data in mOriginalValues
                }

                if (constraint == null || constraint.length() == 0) {
                    // set the Original result to return
                    results.count = customersModelArrayList.size();
                    results.values = customersModelArrayList;
                } else {
                    constraint = constraint.toString().toLowerCase();
                    for (int i = 0; i < customersModelArrayList.size(); i++) {
                        String data = customersModelArrayList.get(i).getName();
                        if (!TextUtils.isEmpty(data) && data.toLowerCase().startsWith(constraint.toString())) {
                            FilteredArrList.add(new CustomersModel(customersModelArrayList.get(i).cus_id,customersModelArrayList.get(i).name,customersModelArrayList.get(i).mobile,""));
                        }
                    }
                    // set the Filtered result to return
                    results.count = FilteredArrList.size();
                    results.values = FilteredArrList;


                    if (results.count == 0) {
                        constraint = constraint.toString().toLowerCase();
                        for (int i = 0; i < customersModelArrayList.size(); i++) {
                            String data = customersModelArrayList.get(i).getMobile();
                            if (!TextUtils.isEmpty(data) && data.toLowerCase().startsWith(constraint.toString())) {
                                FilteredArrList.add(new CustomersModel(customersModelArrayList.get(i).cus_id,customersModelArrayList.get(i).name,customersModelArrayList.get(i).mobile,""));
                            }
                        }
                        // set the Filtered result to return
                        results.count = FilteredArrList.size();
                        results.values = FilteredArrList;
                    }
                }
                return results;
            }
        };
        return filter;
    }

}



