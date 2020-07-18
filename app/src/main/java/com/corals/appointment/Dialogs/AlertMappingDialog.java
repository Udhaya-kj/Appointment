package com.corals.appointment.Dialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.corals.appointment.R;
import com.google.android.material.button.MaterialButton;

public abstract class AlertMappingDialog {

    public AlertMappingDialog(Context mcntx) {
        failureDiolog(mcntx);
    }


    private void failureDiolog(Context mcntx) {
        AlertDialog.Builder builder;
        AlertDialog alertDialog = null;
        View view = LayoutInflater.from(mcntx).inflate(R.layout.alert_mapping, null, false);
        MaterialButton btn = view.findViewById(R.id.buttonOk);

        builder = new AlertDialog.Builder(mcntx);
        builder.setView(view);
        alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();
        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(alertDialog.getWindow().getAttributes());
        lp.width = getDisplayWidth(alertDialog);
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        lp.x = 0;
        lp.y = 0;
        alertDialog.getWindow().setAttributes(lp);

        final AlertDialog finalAlertDialog1 = alertDialog;
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finalAlertDialog1.dismiss();
                onButtonClick();
            }

        });
    }

    private int getDisplayWidth(AlertDialog alertDialog) {
        int width = 500;
        if (alertDialog.getWindow() != null) {
            Display display = alertDialog.getWindow().getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);

            if (size.x > 720) {
                width = size.x;
                //width = size.x - 200;
            }
        }
        return width;
    }


    public abstract void onButtonClick();

}
