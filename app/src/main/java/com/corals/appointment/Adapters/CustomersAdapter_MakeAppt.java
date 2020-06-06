package com.corals.appointment.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.corals.appointment.Activity.ApptConfirmActivity;
import com.corals.appointment.Model.CustomersModel;
import com.corals.appointment.R;

import java.util.ArrayList;

public class CustomersAdapter_MakeAppt extends BaseAdapter implements Filterable {

    private final Activity context;
    private ArrayList<CustomersModel> mOriginalValues; // Original Values
    private ArrayList<CustomersModel> mDisplayedValues;    // Values to be displayed
    LayoutInflater inflater;

    public CustomersAdapter_MakeAppt(Activity context, ArrayList<CustomersModel> mCustomersValues) {
        // TODO Auto-generated constructor stub

        this.context = context;
        this.mOriginalValues = mCustomersValues;
        this.mDisplayedValues = mCustomersValues;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mDisplayedValues.size();
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
        View rowView = inflater.inflate(R.layout.layout_customers_make_appt, null, true);

        TextView text_cus_name = (TextView) rowView.findViewById(R.id.text_cus_name);
        TextView text_cus_mob = (TextView) rowView.findViewById(R.id.text_cus_mob);
        LinearLayout linearLayout = (LinearLayout) rowView.findViewById(R.id.layout_row_customer_make_appt);
        text_cus_name.setText(mDisplayedValues.get(position).name);
        text_cus_mob.setText(mDisplayedValues.get(position).mobile);

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, ApptConfirmActivity.class);
                i.putExtra("cus_name", mDisplayedValues.get(position).name);
                i.putExtra("cus_mob", mDisplayedValues.get(position).mobile);
                context.startActivity(i);
                ((Activity) context).finish();
            }
        });


        return rowView;

    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint,FilterResults results) {

                mDisplayedValues = (ArrayList<CustomersModel>) results.values; // has the filtered values
                notifyDataSetChanged();  // notifies the data with new filtered values
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();        // Holds the results of a filtering operation in values
                ArrayList<CustomersModel> FilteredArrList = new ArrayList<CustomersModel>();

                if (mOriginalValues == null) {
                    mOriginalValues = new ArrayList<CustomersModel>(mDisplayedValues); // saves the original data in mOriginalValues
                }

                /********
                 *
                 *  If constraint(CharSequence that is received) is null returns the mOriginalValues(Original) values
                 *  else does the Filtering and returns FilteredArrList(Filtered)
                 *
                 ********/
                if (constraint == null || constraint.length() == 0) {

                    // set the Original result to return
                    results.count = mOriginalValues.size();
                    results.values = mOriginalValues;
                } else {
                    constraint = constraint.toString().toLowerCase();
                    for (int i = 0; i < mOriginalValues.size(); i++) {
                        String data = mOriginalValues.get(i).name;
                        if (data.toLowerCase().startsWith(constraint.toString())) {
                            FilteredArrList.add(new CustomersModel(mOriginalValues.get(i).name,mOriginalValues.get(i).mobile,""));
                        }
                    }
                    // set the Filtered result to return
                    results.count = FilteredArrList.size();
                    results.values = FilteredArrList;

                    if(results.count==0){
                        constraint = constraint.toString().toLowerCase();
                        for (int i = 0; i < mOriginalValues.size(); i++) {
                            String data = mOriginalValues.get(i).mobile;
                            if (data.toLowerCase().startsWith(constraint.toString())) {
                                FilteredArrList.add(new CustomersModel(mOriginalValues.get(i).name,mOriginalValues.get(i).mobile,""));
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
