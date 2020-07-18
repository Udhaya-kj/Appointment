package com.corals.appointment.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.corals.appointment.R;

public class TermsConditionsActivity extends AppCompatActivity {
TextView textView_terms_conditions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_conditions);

        Toolbar toolbar = findViewById(R.id.toolbar_terms_conditions);
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

        textView_terms_conditions=findViewById(R.id.tv_terms_conditions);

        textView_terms_conditions.setText(Html.fromHtml(getResources().getString(R.string.termsconditonslongvalue)));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}