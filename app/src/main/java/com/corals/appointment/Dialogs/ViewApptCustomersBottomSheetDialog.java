package com.corals.appointment.Dialogs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.corals.appointment.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

public class ViewApptCustomersBottomSheetDialog {
    private Context mCtx;
    private BottomSheetDialog bottomSheetDialog;
    ArrayList<String> arrayList_name, arrayList_mob;

    public ViewApptCustomersBottomSheetDialog(Context mCtx) {
        this.mCtx = mCtx;
        setUpDialog();
    }

    private void setUpDialog() {
        LayoutInflater layoutInflater = LayoutInflater.from(mCtx);
        bottomSheetDialog = new BottomSheetDialog(mCtx);

        View sheetView = layoutInflater.inflate(R.layout.bottom_sheet_customers_appt_details, null, false);
        bottomSheetDialog.setContentView(sheetView);
        ((View) sheetView.getParent()).setBackgroundColor(mCtx.getResources().getColor(android.R.color.transparent));

        TextView textView_service = sheetView.findViewById(R.id.tv_cus_service);
        TextView textView_staff = sheetView.findViewById(R.id.tv_cus_staff);
        TextView textView_dur = sheetView.findViewById(R.id.tv_cus_duration);
        TextView textView_appt_dt = sheetView.findViewById(R.id.tv_appt_dt);

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


}



