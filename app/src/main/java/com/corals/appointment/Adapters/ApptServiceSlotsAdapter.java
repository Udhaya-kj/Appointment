package com.corals.appointment.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.corals.appointment.Activity.CustomersMakeApptActivity;
import com.corals.appointment.Client.model.Appointments;
import com.corals.appointment.Dialogs.ViewSlotCustomersBottomDialog;
import com.corals.appointment.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

public class ApptServiceSlotsAdapter extends RecyclerView.Adapter<ApptServiceSlotsAdapter.MyViewHolder> {
    Activity context;
    List<Appointments> appointmentAvailableSlots;
    public ApptServiceSlotsAdapter(Activity context,  List<Appointments> appointmentAvailableSlots) {
        this.context = context;
        this.appointmentAvailableSlots = appointmentAvailableSlots;

    }

    @Override
    public ApptServiceSlotsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_appt_service_slot, parent, false);
        ApptServiceSlotsAdapter.MyViewHolder myViewHolder = new ApptServiceSlotsAdapter.MyViewHolder(v);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final ApptServiceSlotsAdapter.MyViewHolder holder, final int position) {

        holder.textView_ser_time.setText(appointmentAvailableSlots.get(position).getStartTime()+" - "+appointmentAvailableSlots.get(position).getEndTime());

       /* if (arrayList_available.get(position).equals("0")) {
            holder.linearLayout_color.setBackgroundResource(R.drawable.left_round_corners_blue);
            holder.imageView_avail.setBackgroundResource(R.drawable.tick);
            holder.imageView_avail.setEnabled(false);
        } else {
            holder.linearLayout_color.setBackgroundResource(R.drawable.left_round_corners_green);
            holder.imageView_avail.setBackgroundResource(R.drawable.add_green);
            holder.imageView_avail.setEnabled(true);
        }*/

        holder.linearLayout_bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialog bd = new BottomSheetDialog(context);
              /*  ViewSlotCustomersBottomDialog viewSlotCustomersBottomDialog = new ViewSlotCustomersBottomDialog(context,arrayList_time.get(position), arrayList_cus_name, arrayList_cus_mob, bd);
                viewSlotCustomersBottomDialog.showBottomSheetDialog();*/
            }
        });

        holder.imageView_avail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(context, CustomersMakeApptActivity.class);
                in.putExtra("page_id","2");
                context.startActivity(in);
                ((Activity) context).finish();
            }
        });

    }


    @Override
    public int getItemCount() {
        return appointmentAvailableSlots.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textView_cus_name, textView_ser_time, textView_cus_mob;
        LinearLayout linearLayout_bg, linearLayout_color;
        ImageView imageView_avail;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView_cus_name = (TextView) itemView.findViewById(R.id.tv_slot_cus_name);
            textView_ser_time = (TextView) itemView.findViewById(R.id.tv_slot_time);
            textView_cus_mob = (TextView) itemView.findViewById(R.id.tv_slot_cus_mob);
            linearLayout_bg = (LinearLayout) itemView.findViewById(R.id.layout_appt_slot);
            linearLayout_color = (LinearLayout) itemView.findViewById(R.id.layout_color_status);
            imageView_avail = (ImageView) itemView.findViewById(R.id.image_available);
        }
    }
}


