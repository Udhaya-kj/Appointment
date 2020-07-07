package com.corals.appointment.Dialogs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.corals.appointment.Adapters.Slot_Cus_BottomSheet_RecyclerAdapter;
import com.corals.appointment.Client.model.InlineResponse20013Customersrec;
import com.corals.appointment.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.List;

public class ViewSlotCustomersBottomDialog {
    private Context mCtx;
    private BottomSheetDialog bottomSheetDialog;
    String time;
    String date,service_id,service,appt_id;
    List<InlineResponse20013Customersrec> inlineResponse20013Customersrecs;
    public ViewSlotCustomersBottomDialog(Context mCtx, String appt_id, String time, String date, String service_id, String service,BottomSheetDialog bottomSheetDialog,List<InlineResponse20013Customersrec> inlineResponse20013Customersrecs) {
        this.mCtx = mCtx;
        this.time = time;
        this.date = date;
        this.appt_id = appt_id;
        this.service_id = service_id;
        this.service = service;
        this.bottomSheetDialog = bottomSheetDialog;
        this.inlineResponse20013Customersrecs = inlineResponse20013Customersrecs;
        setUpDialog();
    }

    private void setUpDialog() {
        LayoutInflater layoutInflater = LayoutInflater.from(mCtx);
        bottomSheetDialog = new BottomSheetDialog(mCtx);

        View sheetView = layoutInflater.inflate(R.layout.view_slot_customer_bottom_alertdiolag, null, false);
        bottomSheetDialog.setContentView(sheetView);
        ((View) sheetView.getParent()).setBackgroundColor(mCtx.getResources().getColor(android.R.color.transparent));

        TextView textView_time = sheetView.findViewById(R.id.text_bottom_appn_time);
        textView_time.setText(time);
        RecyclerView recyclerView = sheetView.findViewById(R.id.recyclerview_bottomsheet_dialog);
        LinearLayoutManager lm = new LinearLayoutManager(mCtx);
        recyclerView.setLayoutManager(lm);

        Slot_Cus_BottomSheet_RecyclerAdapter adapter=new Slot_Cus_BottomSheet_RecyclerAdapter(mCtx,appt_id,service_id,service,time,date,bottomSheetDialog,inlineResponse20013Customersrecs);
        recyclerView.setAdapter(adapter);
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


