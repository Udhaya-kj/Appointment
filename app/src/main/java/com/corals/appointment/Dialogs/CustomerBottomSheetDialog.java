package com.corals.appointment.Dialogs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.corals.appointment.Activity.CalendarViewActivity;
import com.corals.appointment.Activity.ViewCustomerApptActivity;
import com.corals.appointment.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class CustomerBottomSheetDialog implements View.OnClickListener {
    private Context mCtx;
    private BottomSheetDialog bottomSheetDialog;
    String name, mob;

    public CustomerBottomSheetDialog(Context mCtx, String name, String mob) {
        this.mCtx = mCtx;
        this.name = name;
        this.mob = mob;
        setUpDialog();
    }

    private void setUpDialog() {
        LayoutInflater layoutInflater = LayoutInflater.from(mCtx);
        bottomSheetDialog = new BottomSheetDialog(mCtx);

        View sheetView = layoutInflater.inflate(R.layout.customer_bottom_alertdiolag_view, null, false);
        bottomSheetDialog.setContentView(sheetView);
        ((View) sheetView.getParent()).setBackgroundColor(mCtx.getResources().getColor(android.R.color.transparent));

        LinearLayout layout_call = sheetView.findViewById(R.id.layout_call);
        LinearLayout layout_view_appt = sheetView.findViewById(R.id.layout_view_appt);
        LinearLayout layout_make_appt = sheetView.findViewById(R.id.layout_make_appt);
        TextView textView_name = sheetView.findViewById(R.id.tv_cus_alert_name);
        TextView textView_mob = sheetView.findViewById(R.id.tv_cus_alert_mob);
        TextView textView_email = sheetView.findViewById(R.id.tv_cus_alert_email);

        textView_name.setText(name);
        textView_mob.setText(mob);

        layout_call.setOnClickListener(this);
        layout_view_appt.setOnClickListener(this);
        layout_make_appt.setOnClickListener(this);
    }

    public void showBottomSheetDialog() {
        if (bottomSheetDialog != null && !bottomSheetDialog.isShowing())
            bottomSheetDialog.show();
    }

    public void dismissBottomSheetDialog() {
        if (bottomSheetDialog.isShowing()) {
            bottomSheetDialog.dismiss();
        }
    }

    @Override
    public void onClick(View v) {
        dismissBottomSheetDialog();
        if (v.getId() == R.id.layout_call) {
            Toast.makeText(mCtx, "Calling...", Toast.LENGTH_SHORT).show();
        } else if (v.getId() == R.id.layout_view_appt) {
            // Toast.makeText(mCtx, "View Appointment", Toast.LENGTH_SHORT).show();
            Intent in = new Intent(((Activity) mCtx), ViewCustomerApptActivity.class);
            in.putExtra("cus_name",name);
            mCtx.startActivity(in);
            ((Activity) mCtx).finish();
            ((Activity) mCtx).overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);
        } else if (v.getId() == R.id.layout_make_appt) {
            //Toast.makeText(mCtx, "Make Appointment", Toast.LENGTH_SHORT).show();
            Intent in = new Intent(((Activity) mCtx), CalendarViewActivity.class);
            in.putExtra("cus_name",name);
            mCtx.startActivity(in);
            ((Activity) mCtx).finish();
            ((Activity) mCtx).overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);
        }
    }

}

