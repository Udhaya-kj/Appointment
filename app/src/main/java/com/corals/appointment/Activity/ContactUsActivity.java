package com.corals.appointment.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.corals.appointment.R;

public class ContactUsActivity extends AppCompatActivity {

    TextView textView_mob, textView_wp, textView_email;
    LinearLayout linearLayout_mob, linearLayout_wp,linearLayout_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        Toolbar toolbar = findViewById(R.id.toolbar_contact_us);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        textView_mob = findViewById(R.id.text_mob);
        textView_wp = findViewById(R.id.text_whatsapp);
        textView_email = findViewById(R.id.text_email);
        linearLayout_mob = findViewById(R.id.layout_mob);
        linearLayout_wp = findViewById(R.id.layout_wp);
        linearLayout_email = findViewById(R.id.layout_email);


        linearLayout_mob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri number = Uri.parse("tel:" + "6379685956");
                Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
                startActivity(callIntent);
            }
        });


        linearLayout_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto","dijkstralogistics@gmail.com", null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "");
                startActivity(Intent.createChooser(emailIntent, ""));
            }
        });

        linearLayout_wp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             /*   String url = "https://api.whatsapp.com/send?phone="+"6379685956";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);*/

                PackageManager pm = getPackageManager();
                try {
                    String toNumber = "916379685956"; // Replace with mobile phone number without +Sign or leading zeros, but with country code.
                    //Suppose your country is India and your phone number is “xxxxxxxxxx”, then you need to send “91xxxxxxxxxx”.
                    Intent sendIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + "" + toNumber));
                    sendIntent.setPackage("com.whatsapp");
                    startActivity(sendIntent);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(ContactUsActivity.this, "WhatsApp not Installed", Toast.LENGTH_LONG).show();

                }
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}