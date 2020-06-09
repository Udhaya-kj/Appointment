package com.corals.appointment.Adapters;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.corals.appointment.Activity.CreateCustomerActivity;
import com.corals.appointment.Dialogs.CustomerBottomSheetDialog;
import com.corals.appointment.Model.CustomersModel;
import com.corals.appointment.R;


import java.util.ArrayList;

public class CustomersAdapter extends BaseAdapter implements Filterable {

    private final Activity context;
    private ArrayList<CustomersModel> mOriginalValues; // Original Values
    private ArrayList<CustomersModel> mDisplayedValues;    // Values to be displayed
    LayoutInflater inflater;

    public CustomersAdapter(Activity context, ArrayList<CustomersModel> mCustomersValues) {
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
        View rowView = inflater.inflate(R.layout.layout_customers, null, true);

        TextView text_cus_name = (TextView) rowView.findViewById(R.id.text_cus_name);
        TextView text_cus_mob = (TextView) rowView.findViewById(R.id.text_cus_mob);
        LinearLayout linearLayout = (LinearLayout) rowView.findViewById(R.id.layout_row_customer);
        Button button_edit = (Button) rowView.findViewById(R.id.button_edit_customer);
        Button button_delete = (Button) rowView.findViewById(R.id.button_delete_customer);
        Button button_view = (Button) rowView.findViewById(R.id.button_view_customer);
        text_cus_name.setText(mDisplayedValues.get(position).name);
        text_cus_mob.setText(mDisplayedValues.get(position).mobile);

       /* linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, ApptConfirmActivity.class);
                i.putExtra("cus_name", (arrayList.get(position)));
                i.putExtra("cus_mob", (arrayList1.get(position)));
                context.startActivity(i);
                ((Activity) context).finish();
            }
        });*/

        button_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomerBottomSheetDialog coralsBottomSheetDialog = new CustomerBottomSheetDialog(context);
                coralsBottomSheetDialog.showBottomSheetDialog();

              /*  final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.alert_cus_details);
                TextView textView_name = (TextView) dialog.findViewById(R.id.tv_cus_alert_name);
                TextView textView_mob = (TextView) dialog.findViewById(R.id.tv_cus_alert_mob);
                TextView textView_email = (TextView) dialog.findViewById(R.id.tv_cus_alert_email);

                textView_name.setText(mDisplayedValues.get(position).name);
                textView_mob.setText(mDisplayedValues.get(position).mobile);
                // if button is clicked, close the custom dialog
                          *//* dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(),"Dismissed..!!",Toast.LENGTH_SHORT).show();
                    }
                });*//*
                dialog.show();*/
            }
        });

        button_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, CreateCustomerActivity.class);
                i.putExtra("page_id", "22");
                i.putExtra("position", String.valueOf(position));
                i.putExtra("cus_name", (mDisplayedValues.get(position).name));
                i.putExtra("cus_mob", (mDisplayedValues.get(position).mobile));
                context.startActivity(i);
                ((Activity) context).finish();
            }
        });

        button_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setMessage("Are you sure, You want to delete this customer?");
                alertDialogBuilder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                arg0.dismiss();
                                final ProgressDialog pd = new ProgressDialog(context);
                                pd.setMessage("Deleting Customer...");
                                pd.show();

                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        pd.dismiss();
                                        Toast.makeText(context, "Customer Successfully Deleted!", Toast.LENGTH_SHORT).show();
                                    }
                                }, 2000);

                            }
                        });

                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();



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