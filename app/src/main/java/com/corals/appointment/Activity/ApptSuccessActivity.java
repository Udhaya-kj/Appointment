package com.corals.appointment.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.corals.appointment.R;

public class ApptSuccessActivity extends AppCompatActivity {
    Button button_close;
    String cus_email;
    TextView textView_confirm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_complete);

        textView_confirm=findViewById(R.id.text_confirm_email);
        if(getIntent().getExtras()!=null){
            cus_email=getIntent().getStringExtra("cus_email");
            if(!TextUtils.isEmpty(cus_email) && !cus_email.equals("null")) {
                textView_confirm.setText("We have sent confirmation mail to " + cus_email);
            }else {
                textView_confirm.setText("We have sent confirmation mail to --");
            }
        }
        button_close=findViewById(R.id.button_setup_close);
        button_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(ApptSuccessActivity.this, DashboardActivity.class);
                startActivity(in);
                finish();
                overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent in = new Intent(ApptSuccessActivity.this, DashboardActivity.class);
        startActivity(in);
        finish();
        overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);
    }
}
