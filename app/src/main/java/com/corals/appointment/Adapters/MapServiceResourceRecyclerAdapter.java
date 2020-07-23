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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.corals.appointment.Client.model.AppointmentService;
import com.corals.appointment.Client.model.MapServiceResourceBody;
import com.corals.appointment.Interface.MappedServicesCallback;
import com.corals.appointment.R;

import java.util.ArrayList;
import java.util.List;

public class MapServiceResourceRecyclerAdapter extends RecyclerView.Adapter<MapServiceResourceRecyclerAdapter.MyViewHolder> {
    Activity context;
    List<AppointmentService> appointmentServiceArrayList;
    List<MapServiceResourceBody> mapServiceResourceBodies;
    String flag;
    ArrayList<String> ser_list;
    ArrayList<String> load_list;
    ArrayList<Integer> pos_list;

    ArrayList<Boolean> update_pos_list;
    boolean loadData = false;
    boolean mng_loadData = false, pos_loadData = false, pos_loadDisable = false, enableWatcher = false;
    MappedServicesCallback mappedServicesCallback;
    String chk_value = "";
    ArrayList<String> list_mng_load, list_serId;
    ArrayList<String> update_serName, update_serLoad;
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
        pos_list = new ArrayList<Integer>();

        list_mng_load = new ArrayList<String>();
        list_serId = new ArrayList<String>();
        update_pos_list = new ArrayList<Boolean>();

        update_serName = new ArrayList<String>();
        update_serLoad = new ArrayList<String>();
        update_serName.clear();
        update_serLoad.clear();
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

        holder.editText_load.setTag(position);
        holder.checkBox.setTag(position);

        if (flag.equals("0")) {
            //Resource create
            if (!loadData) {
                if (!appointmentServiceArrayList.isEmpty() && appointmentServiceArrayList != null) {
                    for (int i = 0; i < appointmentServiceArrayList.size(); i++) {
                        //Service
                        ser_list.add(appointmentServiceArrayList.get(i).getSerId());
                    }
                    loadData = true;
                }
            }
            holder.textView_s_no.setText(String.valueOf(position + 1));
            holder.textView_serv_name.setText(appointmentServiceArrayList.get(position).getSerName());

            holder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String edit_value = holder.editText_load.getText().toString();
                    if (holder.checkBox.isChecked()) {

                        chk_value = "1";
                        Log.d("Check---", "onTextChanged: " + edit_value);
                     /*   ser_list.add(appointmentServiceArrayList.get(position));
                        list_load.add(edit_value);*/
                        if (!TextUtils.isEmpty(edit_value)) {
                            list_serId.add(appointmentServiceArrayList.get(position).getSerId());
                            list_mng_load.add(edit_value);
                            update_serName.add(appointmentServiceArrayList.get(position).getSerName());
                            Log.d("CheckCreate---", "checkbox:" + list_serId + "," + list_mng_load+ "," + update_serName);
                        } else {
                            holder.checkBox.setChecked(false);
                            holder.editText_load.setBackgroundResource(R.drawable.layout_bg_mng_load_red);
                            holder.editText_load.requestFocus();
                        }


                    } else {
                        chk_value = "";
                      /*  ser_list.remove(appointmentServiceArrayList.get(position));
                        list_load.remove(edit_value);*/
                        list_serId.remove(appointmentServiceArrayList.get(position).getSerId());
                        list_mng_load.remove(edit_value);
                        update_serName.remove(appointmentServiceArrayList.get(position).getSerName());

                        holder.editText_load.setText("");
                        Log.d("CheckCreate---", "checkbox:" + list_serId + "," + list_mng_load+ "," + update_serName);
                    }

                }
            });

/*            holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    String edit_value = holder.editText_load.getText().toString();
                    if (isChecked) {
                        Toast.makeText(context, "check", Toast.LENGTH_SHORT).show();
                        chk_value = "1";
                        Log.d("Check---", "onTextChanged: " + edit_value);
                     *//*   ser_list.add(appointmentServiceArrayList.get(position));
                        list_load.add(edit_value);*//*
                        if (!TextUtils.isEmpty(edit_value)) {
                            list_serId.add(appointmentServiceArrayList.get(position).getSerId());
                            list_mng_load.add(edit_value);
                            update_serName.add(appointmentServiceArrayList.get(position).getSerName());
                        } else {
                            holder.checkBox.setChecked(false);
                            holder.editText_load.setBackgroundResource(R.drawable.layout_bg_mng_load_red);
                        }
                        Log.d("Check---", "onTextChanged: list :" + list_serId + "," + list_mng_load);

                    } else {
                        Toast.makeText(context, "uncheck", Toast.LENGTH_SHORT).show();
                        chk_value = "";
                      *//*  ser_list.remove(appointmentServiceArrayList.get(position));
                        list_load.remove(edit_value);*//*

                        list_serId.remove(appointmentServiceArrayList.get(position).getSerId());
                        list_mng_load.remove(edit_value);
                        update_serName.remove(appointmentServiceArrayList.get(position).getSerName());

                        holder.editText_load.setText("");
                        Log.d("Check---", "onTextChanged: list :" + list_serId + "," + list_mng_load);


                    }

                *//*    if (!isChecked) {
                        //Uncheck
                        // holder.editText_load.setEnabled(false);
                        if (!TextUtils.isEmpty(holder.editText_load.getText().toString())) {
                            mappedServicesCallback.mappedServicesList("0", appointmentServiceArrayList.get(position).getSerId(), holder.editText_load.getText().toString(), appointmentServiceArrayList.get(position).getSerName());
                        }

                        // mappedServicesCallback.mappedServicesList("0",appointmentServiceArrayList.get(position).getSerId());
                        // AddStaffActivity.arrayList_map_service.remove(appointmentServiceArrayList.get(position).getSerId());
                    } else {
                        //Check
                        if (!TextUtils.isEmpty(holder.editText_load.getText().toString()) && !holder.editText_load.getText().toString().startsWith("0")) {
                            mappedServicesCallback.mappedServicesList("1", appointmentServiceArrayList.get(position).getSerId(), holder.editText_load.getText().toString(), appointmentServiceArrayList.get(position).getSerName());
                        } else {
                            holder.checkBox.setChecked(false);
                            holder.editText_load.setError("*");
                            holder.editText_load.requestFocus();
                        }
                        //holder.editText_load.setEnabled(true);
                        // mappedServicesCallback.mappedServicesList("1",appointmentServiceArrayList.get(position).getSerId());
                        //AddStaffActivity.arrayList_map_service.add(appointmentServiceArrayList.get(position).getSerId());
                    }*//*
                }
            });*/

        } else if (flag.equals("1")) {
            //Resource update
            if (!loadData) {
                if (!appointmentServiceArrayList.isEmpty() && appointmentServiceArrayList != null) {
                    for (int i = 0; i < appointmentServiceArrayList.size(); i++) {
                        //Service
                        update_pos_list.add(false);
                        //AddStaffActivity.positionArray.add(false);
                        ser_list.add(appointmentServiceArrayList.get(i).getSerId());
                    }
                    for (int i = 0; i < mapServiceResourceBodies.size(); i++) {
                        list_mng_load.add("0");
                        //AddStaffActivity.loadArray.add("0");
                        load_list.add(mapServiceResourceBodies.get(i).getManageableLoad());
                    }
                    Log.d("load_list--->", "Pos :" + load_list);
                    int add_count = ser_list.size() - mapServiceResourceBodies.size();
                    for (int i = 0; i < add_count; i++) {
                        MapServiceResourceBody mapServiceResourceBody = new MapServiceResourceBody();
                        mapServiceResourceBody.setSerId("0");
                        mapServiceResourceBody.setManageableLoad("0");
                        mapServiceResourceBodies.add(mapServiceResourceBody);
                        load_list.add("0");
                        list_mng_load.add("0");
                        //AddStaffActivity.loadArray.add("0");
                    }
                    Log.d("apptSerArrayList--->", "" + appointmentServiceArrayList.size() + "," + ser_list + "," + mapServiceResourceBodies.size());
                    for (int i = 0; i < ser_list.size(); i++) {
                        if (ser_list.contains(mapServiceResourceBodies.get(i).getSerId())) {
                            int pos = ser_list.indexOf(mapServiceResourceBodies.get(i).getSerId());
                            int load_pos = load_list.indexOf(mapServiceResourceBodies.get(i).getManageableLoad());
                            update_pos_list.set(pos, true);
                            //AddStaffActivity.positionArray.set(pos, true);
                            list_mng_load.set(pos, mapServiceResourceBodies.get(i).getManageableLoad());
                            //AddStaffActivity.loadArray.set(pos, mapServiceResourceBodies.get(i).getManageableLoad());
                            pos_list.add(pos);
                            //alert
                            update_serLoad.add(mapServiceResourceBodies.get(i).getManageableLoad());
                        }
                    }

                    for (int g = 0; g < pos_list.size(); g++) {
                        update_serName.add(appointmentServiceArrayList.get(pos_list.get(g)).getSerName());
                        Log.d("pos_loadData--->", "" + appointmentServiceArrayList.get(pos_list.get(g)).getSerName());
                    }

                    loadData = true;
                    Log.d("positionArrayServer--->", "" + update_pos_list + "," + list_mng_load);
                    Log.d("positionArrayLocal--->", "IN :" + update_serName + "," + update_serLoad);

                } else {
                    Log.d("positionArray--->", "Data empty");
                }
            }
            Log.d("positionArrayLocal--->", "OUT :" + update_serName + "," + update_serLoad);
            if (!ser_list.isEmpty()) {
               /* holder.checkBox.setChecked(AddStaffActivity.positionArray.get(position));
                holder.editText_load.setText(AddStaffActivity.loadArray.get(position));*/

                holder.checkBox.setChecked(update_pos_list.get(position));
                holder.editText_load.setText(list_mng_load.get(position));
            }

     /*       Log.d("positionArrayLocal--->", ""+position+","+appointmentServiceArrayList.size());
            if(position==appointmentServiceArrayList.size()-1){
                    enableWatcher=true;
                    Log.d("positionArrayLocal--->", "pos_loadDisable true");

            }*/
   /*         if (!pos_loadDisable) {
                Log.d("positionArrayLocal--->", "pos_loadDisable false :"+update_pos_list.size());
                for (int g = 0; g <update_pos_list.size(); g++) {
                    holder.checkBox.setChecked(update_pos_list.get(g));
                     holder.editText_load.setText(list_mng_load.get(g));

                     if(g==update_pos_list.size()-1){
                         enableWatcher=true;
                         Log.d("positionArrayLocal--->", "pos_loadDisable true");
                     }
                }
                pos_loadDisable=true;
            }*/

     /*       if (!pos_loadData) {
                for (int g = 0; g < pos_list.size(); g++) {
                    AddStaffActivity.update_serName.add(appointmentServiceArrayList.get(pos_list.get(g)).getSerName());
                    Log.d("pos_loadData--->", "" + appointmentServiceArrayList.get(pos_list.get(g)).getSerName());
                }
                pos_loadData = true;
            }*/

        /*    if (!mng_loadData) {
                for (int g = 0; g < load_list.size(); g++) {
                    if (!load_list.get(g).equals("0")) {
                        mappedServicesCallback.mappedServicesList("1", appointmentServiceArrayList.get(position).getSerId(), holder.editText_load.getText().toString().trim(), appointmentServiceArrayList.get(position).getSerName());
                        Log.d("mng_load--->", "uncheck :" + appointmentServiceArrayList.get(position).getSerId() + "," + holder.editText_load.getText().toString().trim());

                    }
                }
                mng_loadData = true;
            }*/

            holder.textView_serv_name.setText(appointmentServiceArrayList.get(position).getSerName());

            holder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.editText_load.requestFocus();
                    String edit_value = holder.editText_load.getText().toString();
                    if (holder.checkBox.isChecked()) {
                        if (!TextUtils.isEmpty(edit_value)) {
                            if (ser_list.contains(appointmentServiceArrayList.get(position).getSerId())) {
                                int pos = ser_list.indexOf(appointmentServiceArrayList.get(position).getSerId());
                                if (pos != -1) {
                                    update_pos_list.set(pos, true);
                                    list_mng_load.set(pos, edit_value);

                                    update_serName.add(appointmentServiceArrayList.get(pos).getSerName());
                                    update_serLoad.add(edit_value);
                                    Log.d("CheckUpdateLocal---", "checkbox check:" + update_serName + "," + update_serLoad);
                                    //Toast.makeText(context, "" + appointmentServiceArrayList.get(pos).getSerName() + "," + edit_value, Toast.LENGTH_SHORT).show();
                                }
                            }
                        } else {
                            holder.checkBox.setChecked(false);
                            holder.editText_load.setBackgroundResource(R.drawable.layout_bg_mng_load_red);
                            holder.editText_load.requestFocus();
                        }
                    } else {
                        int pos = ser_list.indexOf(appointmentServiceArrayList.get(position).getSerId());
                        Log.d("positionArray--->", "uncheck :" + pos);

                        update_pos_list.set(pos, false);
                        list_mng_load.set(pos, "0");

                        update_serName.remove(appointmentServiceArrayList.get(pos).getSerName());
                        update_serLoad.remove(edit_value);
                         holder.editText_load.setText("");
                        // Toast.makeText(context, ""+appointmentServiceArrayList.get(pos).getSerName()+","+edit_value, Toast.LENGTH_SHORT).show();
                        Log.d("CheckUpdateLocal---", "checkbox uncheck :" + update_serName + "," + update_serLoad);
                    }
                }
            });
/*
            holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    //New
                    holder.editText_load.requestFocus();
                    String edit_value = holder.editText_load.getText().toString();
                    if (isChecked) {
                        chk_value = "1";
                        Log.d("CheckUpdate---", "onTextChanged: " + edit_value);
                     *//*   ser_list.add(appointmentServiceArrayList.get(position));
                        list_load.add(edit_value);*//*
                        if (!TextUtils.isEmpty(edit_value)) {
                            if (ser_list.contains(appointmentServiceArrayList.get(position).getSerId())) {
                                int pos = ser_list.indexOf(appointmentServiceArrayList.get(position).getSerId());
                                if (pos != -1) {
                                    update_pos_list.set(pos, true);
                                    list_mng_load.set(pos, edit_value);

                                    update_serName.add(appointmentServiceArrayList.get(pos).getSerName());
                                    update_serLoad.add(edit_value);
                                    Toast.makeText(context, ""+appointmentServiceArrayList.get(pos).getSerName()+","+edit_value, Toast.LENGTH_SHORT).show();
                                    Log.d("CheckUpdateLocal---", "check: list2 :" + update_serName + "," + update_serLoad);
                                    //Toast.makeText(context, "" + appointmentServiceArrayList.get(pos).getSerName() + "," + edit_value, Toast.LENGTH_SHORT).show();
                                }
                            }
                        } else {
                            holder.checkBox.setChecked(false);
                            holder.editText_load.setBackgroundResource(R.drawable.layout_bg_mng_load_red);
                        }
                        //Log.d("CheckUpdate---", "check: list1 :" + list_mng_load + "," + update_pos_list);


                    } else {
                        chk_value = "";
                        int pos = ser_list.indexOf(appointmentServiceArrayList.get(position).getSerId());
                        Log.d("positionArray--->", "uncheck :" + pos);

                        update_pos_list.set(pos, false);
                        list_mng_load.set(pos, "0");

                        update_serName.remove(appointmentServiceArrayList.get(pos).getSerName());
                        update_serLoad.remove(edit_value);
                        Toast.makeText(context, ""+appointmentServiceArrayList.get(pos).getSerName()+","+edit_value, Toast.LENGTH_SHORT).show();

                        // holder.editText_load.setText("");
                       // Toast.makeText(context, "" + appointmentServiceArrayList.get(pos).getSerName() + "," + edit_value, Toast.LENGTH_SHORT).show();

                        //Log.d("CheckUpdate---", "Uncheck: list1 :" + list_mng_load + "," + update_pos_list);
                        Log.d("CheckUpdate---", "Uncheck: list2 :" + update_serName + "," + update_serLoad);
                    }

                    //Old
                   *//* if (!isChecked) {
                        if (ser_list.contains(appointmentServiceArrayList.get(position).getSerId())) {
                            // holder.editText_load.setEnabled(true);
                            //alert
                            AddStaffActivity.update_serName.remove(appointmentServiceArrayList.get(position).getSerName());
                            AddStaffActivity.update_serLoad.remove(holder.editText_load.getText().toString());

                            int pos = ser_list.indexOf(appointmentServiceArrayList.get(position).getSerId());
                            Log.d("positionArray--->", "uncheck :" + pos);
                            update_pos_list.set(pos, false);
                            AddStaffActivity.positionArray.set(pos, false);
                            holder.editText_load.setText("0");
                            list_mng_load.set(pos, "0");
                            AddStaffActivity.loadArray.set(pos, "0");
                            mappedServicesCallback.mappedServicesList("0", appointmentServiceArrayList.get(position).getSerId(), holder.editText_load.getText().toString().trim(), appointmentServiceArrayList.get(position).getSerName());
                            Log.d("updateData---", "onCheckedChanged: " + AddStaffActivity.update_serName + "," + appointmentServiceArrayList.get(position).getSerName() + "," + AddStaffActivity.update_serLoad + "," + holder.editText_load.getText().toString());
                        }
                    }
                    else {
                        if (!TextUtils.isEmpty(holder.editText_load.getText().toString().trim()) && !holder.editText_load.getText().toString().trim().startsWith("0")) {
                            mappedServicesCallback.mappedServicesList("1", appointmentServiceArrayList.get(position).getSerId(), holder.editText_load.getText().toString().trim(), appointmentServiceArrayList.get(position).getSerName());
                            if (ser_list.contains(appointmentServiceArrayList.get(position).getSerId())) {
                                int pos = ser_list.indexOf(appointmentServiceArrayList.get(position).getSerId());
                                Log.d("positionArray--->", "check :" + pos + "," + holder.editText_load.getText().toString().trim());
                                update_pos_list.set(pos, true);
                                AddStaffActivity.positionArray.set(pos, true);
                                list_mng_load.set(pos, holder.editText_load.getText().toString().trim());
                                AddStaffActivity.loadArray.set(pos, holder.editText_load.getText().toString().trim());

                                //alert
                                AddStaffActivity.update_serName.add(appointmentServiceArrayList.get(position).getSerName());
                                AddStaffActivity.update_serLoad.add(holder.editText_load.getText().toString());
                                Log.d("updateData---", "onCheckedChanged: " + AddStaffActivity.update_serName + "," + appointmentServiceArrayList.get(position).getSerName() + "," + AddStaffActivity.update_serLoad + "," + holder.editText_load.getText().toString());
                            }
                        } else {
                            holder.checkBox.setChecked(false);
                            holder.editText_load.setError("*");
                            holder.editText_load.setText("");
                            holder.editText_load.requestFocus();
                        }
                    }*//*
                }
            });*/
        }

        holder.editText_load.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View arg0, boolean hasfocus) {
                if (hasfocus) {
                    enableWatcher = true;
                    // Toast.makeText(context, "Focused "+enableWatcher, Toast.LENGTH_SHORT).show();

                } else {

                    // Toast.makeText(context, "Not Focused", Toast.LENGTH_SHORT).show();
                }
            }
        });

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
            MyTextWatcher textWatcher = new MyTextWatcher(editText_load, checkBox);
            editText_load.addTextChangedListener(textWatcher);
        }
    }

    public class MyTextWatcher implements TextWatcher {
        private EditText editText;
        private CheckBox checkBox;
        private String edit_before_value;
        //Boolean enableWatcher;

        public MyTextWatcher(EditText editText, CheckBox checkBox) {
            this.editText = editText;
            this.checkBox = checkBox;
            // this.enableWatcher = enableWatcher;

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // Toast.makeText(context, "b4 :"+s.toString(), Toast.LENGTH_SHORT).show();
            edit_before_value = s.toString();
            Log.d("CheckB4---", "edit_before_value: " + edit_before_value + "," + enableWatcher);
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            // if(enableWatcher) {
            //Toast.makeText(context, "changed :" + s.toString(), Toast.LENGTH_SHORT).show();
            int position = (int) editText.getTag();
            String edit_value = s.toString();
            Log.d("Check---", "onTextChanged: " + chk_value + "," + edit_value + "," + position + "," + ser_list);
            if (edit_value.equals("0")) {
                // Toast.makeText(context, "IF...", Toast.LENGTH_SHORT).show();
                editText.setText("");

            } else if (!TextUtils.isEmpty(edit_value) && !edit_value.equals("0")) {
                Log.d("Check---", "onTextChanged: else :" + edit_value);
                // if (!TextUtils.isEmpty(chk_value)) {
                if (flag.equals("0")) {
                    if (enableWatcher) {
                    if (edit_value != null && edit_value.length() > 0) {
                        editText.setBackgroundResource(R.drawable.layout_bg_change_appt_blue);
                        checkBox.setChecked(true);

                        if (!TextUtils.isEmpty(edit_before_value) && list_mng_load.contains(edit_before_value)) {
                            Log.d("CheckCreate---", "onTextChanged: 2 ," + ser_list);
                            String serId = ser_list.get(position);
                            int pos = list_serId.indexOf(serId);
                            Log.d("CheckCreate---", "watcher 1:," + serId + "," + pos);
                            list_serId.set(pos, appointmentServiceArrayList.get(position).getSerId());
                            list_mng_load.set(pos, edit_value);
                            update_serName.set(pos,appointmentServiceArrayList.get(position).getSerName());
                            Log.d("CheckCreate---", "create  C1 watcher: " + edit_value + "," + list_serId + "," + list_mng_load+ "," + update_serName);

                         /*   int pos = update_serName.indexOf(appointmentServiceArrayList.get(position).getSerName());
                            Log.d("CheckUpdateLocal---", "watcher1 pos:" + pos+","+appointmentServiceArrayList.get(position).getSerName());
                            if (pos != -1) {
                                //update_serName.set(pos, appointmentServiceArrayList.get(pos).getSerName());
                                update_serLoad.set(pos, edit_value);

                            }*/
                        } else {
                            Log.d("Check---", "onTextChanged: 3");
                            list_serId.add(appointmentServiceArrayList.get(position).getSerId());
                            list_mng_load.add(edit_value);
                            update_serName.add(appointmentServiceArrayList.get(position).getSerName());
                            Log.d("CheckCreate---", "create C2 watcher:" + edit_value + "," + list_serId + "," + list_mng_load+ "," + update_serName);
                        }
                    }
                }
                } else if (flag.equals("1")) {
                    if (enableWatcher) {
                        Log.d("CheckUpdate---", "Flag:1 :" + edit_value);
                        if (!TextUtils.isEmpty(edit_value)) {
                            editText.setBackgroundResource(R.drawable.layout_bg_change_appt_blue);
                            checkBox.setChecked(true);
                            Log.d("CheckUpdate---", "Flag:1 - IF");

                            if (list_mng_load.contains(edit_before_value)) {
                                // list_serId.set(position, appointmentServiceArrayList.get(position).getSerId());
                                list_mng_load.set(position, edit_value);
                                update_pos_list.set(position, true);

                               /* int pos = AddStaffActivity.update_serLoad.indexOf(edit_before_value);
                                AddStaffActivity.update_serLoad.set(pos, edit_value);*/

                                int pos = update_serName.indexOf(appointmentServiceArrayList.get(position).getSerName());
                                Log.d("CheckUpdateLocal---", "watcher1 pos:" + pos+","+appointmentServiceArrayList.get(position).getSerName());
                                if (pos != -1) {
                                    //update_serName.set(pos, appointmentServiceArrayList.get(pos).getSerName());
                                    update_serLoad.set(pos, edit_value);

                                }
                                Log.d("CheckUpdateLocal---", "watcher1:" + update_serName + "," + update_serLoad);
                                Log.d("CheckUpdate---", "update list1:" + pos + "," + edit_value + "," + update_pos_list + "," + list_mng_load);
                            } else {
                                // list_serId.add(appointmentServiceArrayList.get(position).getSerId());
                                list_mng_load.set(position, edit_value);
                                update_pos_list.set(position, true);
                                Log.d("CheckUpdateLocal---", "watcher2:" + update_serName + "," + update_serLoad);
                                update_serLoad.add(edit_value);
                                update_serName.add(appointmentServiceArrayList.get(position).getSerName());
                                Log.d("CheckUpdateLocal---", "watcher2:" + update_serName + "," + update_serLoad);

                                // Log.d("CheckUpdate---", "update Add:" + edit_value + "," + update_pos_list + "," + list_mng_load + "," + appointmentServiceArrayList.get(position).getSerName());
                            }
                        }

                    }
                }

            } else {

                Log.d("Check---", "onTextChanged: empty B4:" + edit_before_value);

                if (flag.equals("0")) {
                    if (!edit_before_value.equals("0")) {
                        checkBox.setChecked(false);
                        String serId = ser_list.get(position);
                        int pos = list_serId.indexOf(serId);
                        Log.d("Check---", "onTextChanged: empty");
                        list_serId.remove(serId);
                        list_mng_load.remove(edit_before_value);
                        if (pos != -1) {
                            update_serName.remove(pos);
                        }
                        Log.d("CheckCreate---", "create C3 watcher:" + edit_value + "," + list_serId + "," + list_mng_load+ "," + update_serName);

                    }
                } else {
                    Log.d("CheckUpdate---", "Flag:1 - MAIN ELSE");
                    if (!edit_before_value.equals("0")) {
                /*        String serName = appointmentServiceArrayList.get(position).getSerName();
                        Log.d("CheckUpdate---", "onTextChanged Main: empty :" + appointmentServiceArrayList + "," + serName);

                        if (!TextUtils.isEmpty(serName)) {
                            int pos = AddStaffActivity.update_serName.indexOf(serName);
                            Log.d("CheckUpdate---", "onTextChanged Main: empty :" + pos);
                            if (!TextUtils.isEmpty(String.valueOf(pos)) && pos != -1) {
                                AddStaffActivity.update_serName.remove(appointmentServiceArrayList.get(position).getSerName());
                                AddStaffActivity.update_serLoad.remove(pos);

                            }
                            Log.d("CheckUpdate---", "onTextChanged Main: empty " + AddStaffActivity.update_serName + "," + AddStaffActivity.update_serLoad);
                        }
*/

                        checkBox.setChecked(false);
                        update_pos_list.set(position, false);
                        list_mng_load.set(position, "0");
                        Log.d("CheckUpdate---", "update list1 MainElse:" + update_pos_list + "," + list_mng_load);

                        int pos = ser_list.indexOf(appointmentServiceArrayList.get(position).getSerId());
                        Log.d("positionArray--->", "uncheck :" + pos);
                        if (pos != -1) {
                            update_serName.remove(appointmentServiceArrayList.get(pos).getSerName());
                            update_serLoad.remove(edit_before_value);
                            Log.d("CheckUpdateLocal---", "watcher3:" + update_serName + "," + update_serLoad);
                        }

                        // Log.d("CheckUpdate---", "Uncheck: list1 :" + list_mng_load + "," + update_pos_list);
                        //Log.d("CheckUpdate---", "Uncheck: list2 :" + AddStaffActivity.update_serName + "," + AddStaffActivity.update_serLoad);


                    }
                }
            }

        }

        @Override
        public void afterTextChanged(Editable s) {

        }

    }

    public ArrayList<String> getLoad() {
        return list_mng_load;
    }

    public ArrayList<String> getService() {
        return list_serId;
    }

    public ArrayList<Boolean> getCheckPosition() {
        return update_pos_list;
    }

    public ArrayList<String> getUpdateServiceName() {
        return update_serName;
    }

    public ArrayList<String> getUpdateServiceLoad() {
        return update_serLoad;
    }

}



