package com.corals.appointment.Dialogs;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.corals.appointment.Activity.CalendarServicesActivity;
import com.corals.appointment.Activity.CalendarViewActivity;
import com.corals.appointment.Activity.ViewCustomerApptActivity;
import com.corals.appointment.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class CustomerBottomSheetDialog implements View.OnClickListener {
    private Context mCtx;
    private BottomSheetDialog bottomSheetDialog;
    String name, mob, cus_id, email;

    public CustomerBottomSheetDialog(Context mCtx, String cus_id, String name, String mob, String email) {
        this.mCtx = mCtx;
        this.name = name;
        this.mob = mob;
        this.cus_id = cus_id;
        this.email = email;
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

        if (!TextUtils.isEmpty(name)) {
            textView_name.setText(name);
        } else {
            textView_name.setText("--");
        }
        if (!TextUtils.isEmpty(mob)) {
            textView_mob.setText(mob);
        } else {
            textView_mob.setText("--");
        }
        if (!TextUtils.isEmpty(email)) {
            textView_email.setText(email);
        } else {
            textView_email.setText("--");
        }


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
            Uri number = Uri.parse("tel:" + mob);
            Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
            mCtx.startActivity(callIntent);
        } else if (v.getId() == R.id.layout_view_appt) {
            // Toast.makeText(mCtx, "View Appointment", Toast.LENGTH_SHORT).show();
            Intent in = new Intent(((Activity) mCtx), ViewCustomerApptActivity.class);
            in.putExtra("cus_id", cus_id);
            in.putExtra("cus_name", name);
            in.putExtra("cus_mob", mob);
            mCtx.startActivity(in);
            ((Activity) mCtx).finish();
            ((Activity) mCtx).overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);
        } else if (v.getId() == R.id.layout_make_appt) {
            //Toast.makeText(mCtx, "Make Appointment", Toast.LENGTH_SHORT).show();
            Intent in = new Intent(((Activity) mCtx), CalendarServicesActivity.class);
            in.putExtra("page_id", "2");
            in.putExtra("cus", name);
            in.putExtra("cus_id", cus_id);
            in.putExtra("cus_email", email);
            in.putExtra("cus_mob", mob);
            mCtx.startActivity(in);
            ((Activity) mCtx).finish();
            ((Activity) mCtx).overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);
        }
    }

}

