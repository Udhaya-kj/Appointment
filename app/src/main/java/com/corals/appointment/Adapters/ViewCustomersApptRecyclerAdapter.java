package com.corals.appointment.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.corals.appointment.Dialogs.ViewApptCustomersBottomSheetDialog;
import com.corals.appointment.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

public class ViewCustomersApptRecyclerAdapter extends RecyclerView.Adapter<ViewCustomersApptRecyclerAdapter.MyViewHolder> {
    ArrayList<String> arrayList_time, arrayList_date;
    Activity context;

    public ViewCustomersApptRecyclerAdapter(Activity context, ArrayList<String> arrayList_time) {
        this.context = context;
        this.arrayList_time = arrayList_time;

    }

    @Override
    public ViewCustomersApptRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_customers_appt, parent, false);
        ViewCustomersApptRecyclerAdapter.MyViewHolder myViewHolder = new ViewCustomersApptRecyclerAdapter.MyViewHolder(v);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewCustomersApptRecyclerAdapter.MyViewHolder holder, final int position) {

        holder.textView_dt_time.setText(arrayList_time.get(position));

        holder.linearLayout_bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialog bd = new BottomSheetDialog(context);
                ViewApptCustomersBottomSheetDialog viewSlotCustomersBottomDialog = new ViewApptCustomersBottomSheetDialog(context);
                viewSlotCustomersBottomDialog.showBottomSheetDialog();

            /*    Intent i = new Intent(context, ApptSlotDetailsActivity.class);
                i.putExtra("name", arrayList_cus_name.get(position));
                i.putExtra("mob", arrayList_cus_mob.get(position));
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);*/
                //((Activity)context).finish();
            }
        });


    }


    @Override
    public int getItemCount() {
        return arrayList_time.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textView_dt_time;
        LinearLayout linearLayout_bg, linearLayout_color;
        ImageView imageView_avail;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView_dt_time = (TextView) itemView.findViewById(R.id.tv_slot_dt_time);
            linearLayout_bg = (LinearLayout) itemView.findViewById(R.id.layout_appt_cust_slot);
            imageView_avail = (ImageView) itemView.findViewById(R.id.image_available);
        }
    }
}



