package com.corals.appointment.Dialogs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.corals.appointment.Adapters.Slot_Cus_BottomSheet_RecyclerAdapter;
import com.corals.appointment.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

public class ViewSlotCustomersBottomDialog {
    private Context mCtx;
    private BottomSheetDialog bottomSheetDialog;
    ArrayList<String> arrayList_name, arrayList_mob;
    String time;
    public ViewSlotCustomersBottomDialog(Context mCtx,String time, ArrayList<String> arrayList_name,ArrayList<String> arrayList_mob,BottomSheetDialog bottomSheetDialog) {
        this.mCtx = mCtx;
        this.time = time;
        this.arrayList_name = arrayList_name;
        this.arrayList_mob = arrayList_mob;
        this.bottomSheetDialog = bottomSheetDialog  ;
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

        Slot_Cus_BottomSheet_RecyclerAdapter adapter=new Slot_Cus_BottomSheet_RecyclerAdapter(mCtx,arrayList_name,arrayList_mob,bottomSheetDialog);
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


