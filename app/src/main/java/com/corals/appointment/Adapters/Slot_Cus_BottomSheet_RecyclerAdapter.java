package com.corals.appointment.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.corals.appointment.Activity.ApptSlotDetailsActivity;
import com.corals.appointment.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

public class Slot_Cus_BottomSheet_RecyclerAdapter extends RecyclerView.Adapter<Slot_Cus_BottomSheet_RecyclerAdapter.MyViewHolder> {
    ArrayList<String> arrayList1, arrayList2, arrayList_val;
    Context context;
    String time;
    private BottomSheetDialog bottomSheetDialog;

    public Slot_Cus_BottomSheet_RecyclerAdapter(Context context, ArrayList<String> arrayList1, ArrayList<String> arrayList2,String time,BottomSheetDialog bottomSheetDialog) {

        this.context = context;
        this.arrayList1 = arrayList1;
        this.arrayList2 = arrayList2;
        this.time = time;
        this.bottomSheetDialog = bottomSheetDialog;

    }

    @Override
    public Slot_Cus_BottomSheet_RecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_appt_slot_customers, parent, false);
        Slot_Cus_BottomSheet_RecyclerAdapter.MyViewHolder myViewHolder = new Slot_Cus_BottomSheet_RecyclerAdapter.MyViewHolder(v);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final Slot_Cus_BottomSheet_RecyclerAdapter.MyViewHolder holder, final int position) {

        holder.textView_cus_name.setText(arrayList1.get(position));
        holder.textView_cus_mob.setText(arrayList2.get(position));

       /* holder.linearLayout_bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, TimeSlotsActivity.class);
                i.putExtra("resource", res);
                i.putExtra("date", CalendarServicesActivity.cal_date);
                context.startActivity(i);
                ((Activity) context).finish();
            }
        });*/

        holder.textView_cus_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bottomSheetDialog != null &&bottomSheetDialog.isShowing()) {
                    bottomSheetDialog.dismiss();
                }
                else {
                    Toast.makeText(context, "Bottom Sheet is null", Toast.LENGTH_SHORT).show();
                }
                
                Intent i = new Intent(context, ApptSlotDetailsActivity.class);
                i.putExtra("name",arrayList1.get(position));
                i.putExtra("mob", arrayList2.get(position));
                i.putExtra("time", time);
                context.startActivity(i);
                ((Activity) context).finish();
            }
        });

/*        holder.imageView_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                res = arrayList1.get(position);
                Intent i = new Intent(context, TimeSlotsActivity.class);
                i.putExtra("resource", res);
                i.putExtra("date", CalendarServicesActivity.cal_date);
                context.startActivity(i);
                ((Activity) context).finish();
            }
        });*/


    }


    @Override
    public int getItemCount() {
        return arrayList1.size();
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


