package com.corals.appointment.Adapters;

import android.app.Activity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;


import com.corals.appointment.Activity.AddStaffActivity;
import com.corals.appointment.Client.model.AppointmentService;
import com.corals.appointment.Client.model.MapServiceResourceBody;
import com.corals.appointment.Interface.MappedServicesCallback;
import com.corals.appointment.Interface.MappedServicesPositionCallback;
import com.corals.appointment.R;

import java.util.ArrayList;
import java.util.List;

public class MapServiceResourceRecyclerAdapter extends RecyclerView.Adapter<MapServiceResourceRecyclerAdapter.MyViewHolder> {
    Activity context;
    List<AppointmentService> appointmentServiceArrayList;
    List<MapServiceResourceBody> mapServiceResourceBodies;
    String flag;
    ArrayList<String> ser_list, load_list;
    boolean loadData = false;
    MappedServicesCallback mappedServicesCallback;
    //MappedServicesPositionCallback mappedServicesPositionCallback;

    public MapServiceResourceRecyclerAdapter(Activity context, String flag, List<AppointmentService> appointmentServiceArrayList, List<MapServiceResourceBody> mapServiceResourceBodies) {
        this.context = context;
        this.flag = flag;
        this.appointmentServiceArrayList = appointmentServiceArrayList;
        this.mapServiceResourceBodies = mapServiceResourceBodies;
        mappedServicesCallback = (MappedServicesCallback) context;
//        mappedServicesPositionCallback = (MappedServicesPositionCallback) context;
        ser_list = new ArrayList<String>();
        load_list = new ArrayList<String>();
    }

    @Override
    public MapServiceResourceRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_map_service_to_resource, parent, false);
        MapServiceResourceRecyclerAdapter.MyViewHolder myViewHolder = new MapServiceResourceRecyclerAdapter.MyViewHolder(v);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MapServiceResourceRecyclerAdapter.MyViewHolder holder, final int position) {
        Log.d("positionArray--->", "Flag :" + flag);


        if (flag.equals("0")) {
            //Resource create
            holder.textView_s_no.setText(String.valueOf(position + 1));
            holder.textView_serv_name.setText(appointmentServiceArrayList.get(position).getSerName());
            holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (!isChecked) {
                        //Uncheck
                        // holder.editText_load.setEnabled(false);
                        if (!TextUtils.isEmpty(holder.editText_load.getText().toString())) {
                            mappedServicesCallback.mappedServicesList("0", appointmentServiceArrayList.get(position).getSerId(), holder.editText_load.getText().toString());
                        }

                        // mappedServicesCallback.mappedServicesList("0",appointmentServiceArrayList.get(position).getSerId());
                        // AddStaffActivity.arrayList_map_service.remove(appointmentServiceArrayList.get(position).getSerId());
                    } else {
                        //Check
                        if (!TextUtils.isEmpty(holder.editText_load.getText().toString()) && !holder.editText_load.getText().toString().startsWith("0")) {
                            mappedServicesCallback.mappedServicesList("1", appointmentServiceArrayList.get(position).getSerId(), holder.editText_load.getText().toString());
                        } else {
                            holder.checkBox.setChecked(false);
                            holder.editText_load.setError("*");
                            holder.editText_load.requestFocus();
                        }
                        //holder.editText_load.setEnabled(true);
                        // mappedServicesCallback.mappedServicesList("1",appointmentServiceArrayList.get(position).getSerId());
                        //AddStaffActivity.arrayList_map_service.add(appointmentServiceArrayList.get(position).getSerId());
                    }
                }
            });

        } else if (flag.equals("1")) {
            //Resource update
            if (!loadData) {
                if (!appointmentServiceArrayList.isEmpty() && appointmentServiceArrayList != null) {
                    for (int i = 0; i < appointmentServiceArrayList.size(); i++) {
                        AddStaffActivity.positionArray.add(false);
                        ser_list.add(appointmentServiceArrayList.get(i).getSerId());
                    }

                    for (int i = 0; i < mapServiceResourceBodies.size(); i++) {
                        AddStaffActivity.loadArray.add("0");
                        load_list.add(mapServiceResourceBodies.get(i).getManageableLoad());
                    }
                    int add_count = ser_list.size() - mapServiceResourceBodies.size();
                    for (int i = 0; i < add_count; i++) {
                        MapServiceResourceBody mapServiceResourceBody = new MapServiceResourceBody();
                        mapServiceResourceBody.setSerId("0");
                        mapServiceResourceBodies.add(mapServiceResourceBody);
                        load_list.add("0");
                    }
                    Log.d("apptSerArrayList--->", "" + appointmentServiceArrayList.size() + "," + ser_list + "," + mapServiceResourceBodies);
                    for (int i = 0; i < ser_list.size(); i++) {
                        if (ser_list.contains(mapServiceResourceBodies.get(i).getSerId())) {
                            int pos = ser_list.indexOf(mapServiceResourceBodies.get(i).getSerId());
                            int load_pos = load_list.indexOf(mapServiceResourceBodies.get(i).getManageableLoad());
                            Log.d("positionArray--->", "Pos :" + pos);
                            AddStaffActivity.positionArray.set(pos, true);
                            AddStaffActivity.loadArray.set(load_pos, mapServiceResourceBodies.get(i).getManageableLoad());
                        }
                    }
                    loadData = true;
                    Log.d("positionArray--->", "" + AddStaffActivity.positionArray);
                } else {
                    Log.d("positionArray--->", "Data empty");
                }
            }

            if (!ser_list.isEmpty()) {
                holder.checkBox.setChecked(AddStaffActivity.positionArray.get(position));
                holder.editText_load.setText(AddStaffActivity.loadArray.get(position));
            }
            holder.textView_serv_name.setText(appointmentServiceArrayList.get(position).getSerName());
            holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (!isChecked) {
                        if (ser_list.contains(appointmentServiceArrayList.get(position).getSerId())) {
                            int pos = ser_list.indexOf(appointmentServiceArrayList.get(position).getSerId());
                            Log.d("positionArray--->", "uncheck :" + pos);
                            AddStaffActivity.positionArray.set(pos, false);
                            mappedServicesCallback.mappedServicesList("0", appointmentServiceArrayList.get(position).getSerId(), holder.editText_load.getText().toString());

                        }
                    } else {
                        if (!TextUtils.isEmpty(holder.editText_load.getText().toString()) && !holder.editText_load.getText().toString().startsWith("0")) {
                            mappedServicesCallback.mappedServicesList("1", appointmentServiceArrayList.get(position).getSerId(), holder.editText_load.getText().toString());
                            if (ser_list.contains(appointmentServiceArrayList.get(position).getSerId())) {
                                int pos = ser_list.indexOf(appointmentServiceArrayList.get(position).getSerId());
                                Log.d("positionArray--->", "check :" + pos);
                                AddStaffActivity.positionArray.set(pos, true);
                            }
                        } else {
                            holder.checkBox.setChecked(false);
                            holder.editText_load.setError("*");
                            holder.editText_load.requestFocus();
                        }

                    }
                }
            });


        }
    }

    @Override
    public int getItemCount() {
        return appointmentServiceArrayList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textView_serv_name, textView_s_no;
        LinearLayout linearLayout_bg;
        CheckBox checkBox;
        EditText editText_load;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView_serv_name = (TextView) itemView.findViewById(R.id.text_service_name);
            textView_s_no = (TextView) itemView.findViewById(R.id.text_ser_no);
            linearLayout_bg = (LinearLayout) itemView.findViewById(R.id.layout_map_ser_res);
            checkBox = (CheckBox) itemView.findViewById(R.id.checkbox);
            editText_load = (EditText) itemView.findViewById(R.id.et_max_load);
        }
    }
}



