package com.corals.appointment.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;


import com.corals.appointment.R;

import java.util.ArrayList;

public class ServiceActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<String> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

        arrayList=new ArrayList<>();
        arrayList.add("Saloon");
        arrayList.add("Hospital");
        arrayList.add("Hotel");

        recyclerView = findViewById(R.id.recyclerview_service);
        LinearLayoutManager li = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(li);
    /*    ServiceAdapter recyclerAdapterservice = new ServiceAdapter(ServiceActivity.this, arrayList);
        recyclerView.setAdapter(recyclerAdapterservice);*/


    }
}
