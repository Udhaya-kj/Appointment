package com.corals.appointment.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.corals.appointment.Activity.ApptSlotDetailsActivity;
import com.corals.appointment.Client.model.InlineResponse20013Customersrec;
import com.corals.appointment.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

public class Slot_Cus_BottomSheet_RecyclerAdapter extends RecyclerView.Adapter<Slot_Cus_BottomSheet_RecyclerAdapter.MyViewHolder> {
    Context context;
    String date,service_id,service,time,appt_id;
    private BottomSheetDialog bottomSheetDialog;
    List<InlineResponse20013Customersrec> inlineResponse20013Customersrecs;
    public Slot_Cus_BottomSheet_RecyclerAdapter(Context context,String appt_id,String service_id,String service,String time,String date,BottomSheetDialog bottomSheetDialog,List<InlineResponse20013Customersrec> inlineResponse20013Customersrecs) {
        this.context = context;
        this.date = date;
        this.time = time;
        this.service_id = service_id;
        this.service = service;
        this.appt_id = appt_id;
        this.bottomSheetDialog = bottomSheetDialog;
        this.inlineResponse20013Customersrecs = inlineResponse20013Customersrecs;

    }

    @Override
    public Slot_Cus_BottomSheet_RecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_appt_slot_customers, parent, false);
        Slot_Cus_BottomSheet_RecyclerAdapter.MyViewHolder myViewHolder = new Slot_Cus_BottomSheet_RecyclerAdapter.MyViewHolder(v);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final Slot_Cus_BottomSheet_RecyclerAdapter.MyViewHolder holder, final int position) {

        holder.textView_cus_name.setText(inlineResponse20013Customersrecs.get(position).getCustName());
        holder.textView_cus_mob.setText(inlineResponse20013Customersrecs.get(position).getCustMobile());

        holder.textView_cus_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bottomSheetDialog != null &&bottomSheetDialog.isShowing()) {
                    bottomSheetDialog.dismiss();
                }
                Intent i = new Intent(context, ApptSlotDetailsActivity.class);
                i.putExtra("cus_id",inlineResponse20013Customersrecs.get(position).getCustId());
                i.putExtra("name",inlineResponse20013Customersrecs.get(position).getCustName());
                i.putExtra("mob", inlineResponse20013Customersrecs.get(position).getCustMobile());
                i.putExtra("res_name", inlineResponse20013Customersrecs.get(position).getResName());
                i.putExtra("time", time);
                i.putExtra("date", date);
                i.putExtra("appt_id", appt_id);
                i.putExtra("service_id", service_id);
                i.putExtra("service", service);
                context.startActivity(i);
                //((Activity) context).finish();
                ((Activity) context).overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);

                Log.d("SerName---", "onClick: "+service);
            }
        });


    }


    @Override
    public int getItemCount() {
        return inlineResponse20013Customersrecs.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textView_cus_name, textView_cus_mob,textView_cus_view;
        ImageView imageView_edit, imageView_delete;
        LinearLayout linearLayout_bg;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView_cus_name = (TextView) itemView.findViewById(R.id.tv_slot_cus_name);
            textView_cus_mob = (TextView) itemView.findViewById(R.id.tv_slot_cus_mob);
            textView_cus_view = (TextView) itemView.findViewById(R.id.tv_slot_cus_view);
            imageView_edit = (ImageView) itemView.findViewById(R.id.image_edit_slot_appt);
            imageView_delete = (ImageView) itemView.findViewById(R.id.image_delete_slot_appt);
            linearLayout_bg = (LinearLayout) itemView.findViewById(R.id.layout_ser_calender);
        }
    }
}


