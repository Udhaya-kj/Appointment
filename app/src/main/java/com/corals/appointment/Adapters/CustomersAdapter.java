package com.corals.appointment.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.corals.appointment.Dialogs.CustomerBottomSheetDialog;
import com.corals.appointment.Interface.SearchCustomerCallback;
import com.corals.appointment.Model.CustomersModel;
import com.corals.appointment.R;

import java.util.ArrayList;

public class CustomersAdapter extends RecyclerView.Adapter<CustomersAdapter.MyViewHolder> implements Filterable {
    private ArrayList<CustomersModel> customersModelArrayList;
    private ArrayList<CustomersModel> mDisplayedValues;
    private Context context;
    private SharedPreferences preferences;
    String ser_id;
    String date;
    String slot_no;
    String startTime;
    String endTime, res_id, res;
    SearchCustomerCallback searchCustomerCallback;

    public CustomersAdapter(Context mCtx, ArrayList<CustomersModel> mCustomersValues, String ser_id, String date, String slot_no, String startTime, String endTime, String res_id, String res) {
        this.context = mCtx;
        this.customersModelArrayList = mCustomersValues;
        this.mDisplayedValues = mCustomersValues;
        this.ser_id = ser_id;
        this.date = date;
        this.slot_no = slot_no;
        this.startTime = startTime;
        this.endTime = endTime;
        this.res_id = res_id;
        this.res = res;
        searchCustomerCallback= (SearchCustomerCallback) context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, mobile;

        LinearLayout layout;

        private MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.text_cus_name);
            mobile = (TextView) view.findViewById(R.id.text_cus_mob);
            layout = (LinearLayout) view.findViewById(R.id.layout_row_customer);
        }
    }

    @NonNull
    @Override
    public CustomersAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_customers, parent, false);
        return new CustomersAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final CustomersAdapter.MyViewHolder holder, final int position) {
        CustomersModel customersrec = mDisplayedValues.get(position);
        holder.name.setText(customersrec.getName());
        holder.mobile.setText(customersrec.getMobile());
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomerBottomSheetDialog coralsBottomSheetDialog = new CustomerBottomSheetDialog(context, mDisplayedValues.get(position).getCus_id(), mDisplayedValues.get(position).getName(), mDisplayedValues.get(position).getMobile(), mDisplayedValues.get(position).getEmail());
                coralsBottomSheetDialog.showBottomSheetDialog();
            }
        });


    }

    @Override
    public int getItemCount() {
        return mDisplayedValues.size();
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
                            FilteredArrList.add(new CustomersModel(customersModelArrayList.get(i).cus_id, customersModelArrayList.get(i).name, customersModelArrayList.get(i).mobile, ""));
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
                                FilteredArrList.add(new CustomersModel(customersModelArrayList.get(i).cus_id, customersModelArrayList.get(i).name, customersModelArrayList.get(i).mobile, ""));
                            }
                        }
                        // set the Filtered result to return
                        results.count = FilteredArrList.size();
                        results.values = FilteredArrList;
                        if (results.count == 0) {
                            searchCustomerCallback.customerCallback("0");
                            Log.d("Flag--->", "performFiltering: 0");
                        }else {
                            searchCustomerCallback.customerCallback("1");
                            Log.d("Flag--->", "performFiltering: 1");
                        }
                    }
                    else {
                        searchCustomerCallback.customerCallback("1");
                        Log.d("Flag--->", "performFiltering: 1");
                    }
                }
                return results;
            }
        };
        return filter;
    }
}