package com.corals.appointment.Adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.corals.appointment.Activity.CustomersMakeApptActivity;
import com.corals.appointment.R;

import java.util.ArrayList;

/**
 * Created by udhaya on 30-Aug-18.
 */

public class RecyclerAdapter_TimeSlots extends RecyclerView.Adapter<RecyclerAdapter_TimeSlots.MyViewHolder> {
    ArrayList<String> arrayList1, arrayList2;
    Context context;
    int i = 0;


    public RecyclerAdapter_TimeSlots(Context context, ArrayList<String> arrayList1, ArrayList<String> arrayList2) {

        this.context = context;
        this.arrayList1 = arrayList1;
        this.arrayList2 = arrayList2;

      /*  arrayList_val = new ArrayList<>();

        for (int k = 0; k <= 11; k++) {
            arrayList_val.add("0");
        }
*/
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.timeslot_row_item, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(v);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {


        // holder.textView_cus_name.setText(arrayList_cus_name.get(position));
        holder.textView_ser_time.setText(arrayList1.get(position));
        // holder.textView_cus_mob.setText(arrayList_cus_mob.get(position));

        if (arrayList2.get(position).equals("0")) {
            holder.linearLayout_color.setBackgroundResource(R.drawable.left_round_corners_blue);
            holder.imageView_avail.setBackgroundResource(R.drawable.tick);
            holder.imageView_avail.setEnabled(false);
        } else if (arrayList2.get(position).equals("1")) {
            holder.linearLayout_color.setBackgroundResource(R.drawable.left_round_corners_green);
            holder.imageView_avail.setBackgroundResource(R.drawable.add_green);
            holder.imageView_avail.setEnabled(true);
        } else if (arrayList2.get(position).equals("2")) {
            holder.linearLayout_color.setBackgroundResource(R.drawable.left_round_corners_grey);
            holder.imageView_avail.setBackgroundResource(R.drawable.block);
            holder.imageView_avail.setEnabled(false);
        }

        holder.linearLayout_bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (String.valueOf(arrayList2.get(position)).equals("1")) {

                    Intent in = new Intent(context, CustomersMakeApptActivity.class);
                    in.putExtra("page_id","1");
                    context.startActivity(in);
                    ((Activity) context).finish();
                    ((Activity) context).overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);

   /*                 AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                    alertDialogBuilder.setMessage("Are you sure, You want to book this appointment?");
                    alertDialogBuilder.setPositiveButton("Yes",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {
                                    arg0.dismiss();
                               *//*     holder.imageView.setVisibility(View.VISIBLE);
                                    holder.linearLayout_bg.setBackgroundColor(Color.parseColor("#EEEEEE"));
                                    holder.textView_s_time.setTextColor(Color.parseColor("#000000"));
                                    arrayList_val.set(position,"1");*//*

                                    Intent in = new Intent(context, CustomersMakeApptActivity.class);
                                    context.startActivity(in);
                                    ((Activity)context).finish();

                                }
                            });

                    alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();*/
                } else if (String.valueOf(arrayList2.get(position)).equals("2")) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                    alertDialogBuilder.setMessage("Appointment not available for this slot!");
                    alertDialogBuilder.setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {
                                    arg0.dismiss();
                                }
                            });


                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();

                }

            }
        });


    }


    @Override
    public int getItemCount() {
        return arrayList1.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textView_ser_time;
        LinearLayout linearLayout_bg, linearLayout_color;
        ImageView imageView_avail;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView_ser_time = (TextView) itemView.findViewById(R.id.tv_slot_time);
            linearLayout_bg = (LinearLayout) itemView.findViewById(R.id.layout_appt_slot);
            linearLayout_color = (LinearLayout) itemView.findViewById(R.id.layout_color_status);
            imageView_avail = (ImageView) itemView.findViewById(R.id.image_available);
        }
    }
}
