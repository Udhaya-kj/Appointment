package com.corals.appointment.Dialogs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.corals.appointment.Client.model.Appointments;
import com.corals.appointment.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

public class ViewApptCustomersBottomSheetDialog {
    private Context mCtx;
    private BottomSheetDialog bottomSheetDialog;
    List<Appointments> appointmentsList;

    public ViewApptCustomersBottomSheetDialog(Context mCtx, List<Appointments> appointmentsList) {
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

        for(int k=0;k<appointmentsList.size();k++) {
            textView_service.setText(appointmentsList.get(k).getStartTime());
        }
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



