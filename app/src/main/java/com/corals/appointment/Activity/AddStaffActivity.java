package com.corals.appointment.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.corals.appointment.Adapters.MapServiceResourceRecyclerAdapter;
import com.corals.appointment.Adapters.MappingServicesAdapter;
import com.corals.appointment.Adapters.StaffListAdapter;
import com.corals.appointment.Client.ApiCallback;
import com.corals.appointment.Client.ApiException;
import com.corals.appointment.Client.OkHttpApiClient;
import com.corals.appointment.Client.api.MerchantApisApi;
import com.corals.appointment.Client.model.AppointmentEnquiryBody;
import com.corals.appointment.Client.model.AppointmentEnquiryResponse;
import com.corals.appointment.Client.model.AppointmentResources;
import com.corals.appointment.Client.model.AppointmentService;
import com.corals.appointment.Client.model.ApptTransactionBody;
import com.corals.appointment.Client.model.ApptTransactionResponse;
import com.corals.appointment.Client.model.AvailDay;
import com.corals.appointment.Client.model.MapServiceResourceBody;
import com.corals.appointment.Client.model.TimeData;
import com.corals.appointment.Constants.Constants;
import com.corals.appointment.Dialogs.AlertDialogFailure;
import com.corals.appointment.Dialogs.AlertDialogYesNo;
import com.corals.appointment.Dialogs.IntermediateAlertDialog;
import com.corals.appointment.Interface.MappedServicesCallback;
import com.corals.appointment.R;
import com.corals.appointment.Utils.CAllLoginAPI;
import com.corals.appointment.receiver.ConnectivityReceiver;
import com.google.android.material.button.MaterialButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class AddStaffActivity extends AppCompatActivity implements MappedServicesCallback {
    EditText et_staff_name, et_staff_mob;
    MaterialButton button_continue, button_add_time;

    public static final String MyPREFERENCES_STAFFS = "MyPrefs_Staffs";
    public static final String NAME = "name";
    public static final String MOBILE = "mobile";
    private ArrayList<String> mng_service_name_list, mng_load_update_list, mng_service_load_list, mng_service_id_list;
    private ArrayList<Boolean> mng_check_pos_list;
    public String pageId = "", position = "", res_id = null;


    CardView cardView_mapped_services;
    RecyclerView recyclerView_services;
    public ArrayList<String> arrayList_map_service, arrayList_map_load, arrayList_map_service_name;
    //public static ArrayList<Boolean> positionArray;
    //public  ArrayList<String>  update_serId;
    public static String map_services = "";

    TextView btn_yes_sday_p, btn_yes_mnday_p, btn_yes_tsday_p, btn_yes_wedday_p, btn_yes_trsday_p, btn_yes_fdday_p, btn_yes_strday_p;
    boolean isActiveSunday_p = true, isActiveMonday_p = true, isActiveTuesday_p = true, isActiveWednesday_p = true, isActiveThursday_p = true, isActiveFriday_p = true, isActiveSaturday_p = true;
    private String staff_in_time, staff_out_time;
    private IntermediateAlertDialog intermediateAlertDialog;
    private SharedPreferences sharedpreferences_sessionToken;

    NestedScrollView scrollView;
    Switch aSwitch_all_days;
    String all_days_time1 = "00:00 - 00:00", all_days_time2 = "00:00 - 00:00", all_days_time3 = "00:00 - 00:00";
    boolean isSameBizHrs = false;
    RadioButton radioButton_biz_hrs, radioButton_custom_time;
    Spinner spinner_weekdays;
    LinearLayout layout_custom_time, layout_add_time, layout_weekday;
    TextView text_start_time, text_end_time, text_weekday;
    ArrayList<String> list_sun, list_mon, list_tue, list_wed, list_thu, list_fri, list_sat;
    StringBuilder weekdays = new StringBuilder("yyyyyyy");
    int hour = 0, minute = 0;
    TextView textView_sun_time1, textView_sun_time2, textView_sun_time3, textView_mon_time1, textView_mon_time2, textView_mon_time3, textView_tue_time1, textView_tue_time2, textView_tue_time3, textView_wed_time1, textView_wed_time2, textView_wed_time3,
            textView_thu_time1, textView_thu_time2, textView_thu_time3, textView_fri_time1, textView_fri_time2, textView_fri_time3, textView_sat_time1, textView_sat_time2, textView_sat_time3;
    LinearLayout linearLayout__sun, linearLayout__mon, linearLayout__tue, linearLayout__wed, linearLayout__thur, linearLayout__fri, linearLayout__sat,
            linearLayout__sun2, linearLayout__mon2, linearLayout__tue2, linearLayout__wed2, linearLayout__thur2, linearLayout__fri2, linearLayout__sat2,
            linearLayout__sun3, linearLayout__mon3, linearLayout__tue3, linearLayout__wed3, linearLayout__thur3, linearLayout__fri3, linearLayout__sat3;
    ImageView imageView_edit_sun, imageView_edit_mon, imageView_edit_tue, imageView_edit_wed, imageView_edit_thu, imageView_edit_fri, imageView_edit_sat;
    ImageView imageView_warning_sun2, imageView_warning_mon2, imageView_warning_tue2, imageView_warning_wed2, imageView_warning_thu2, imageView_warning_fri2, imageView_warning_sat2,
            imageView_warning_sun3, imageView_warning_mon3, imageView_warning_tue3, imageView_warning_wed3, imageView_warning_thu3, imageView_warning_fri3, imageView_warning_sat3;
    LinearLayout layout_services, layout_wrk_hrs, layout_mapping;
    CardView cardView_custom_time;
    List<MapServiceResourceBody> mapServiceResourceBodyList_update;
    List<AppointmentService> appointmentServices = new ArrayList<>();
    boolean isMapSelectedUpdate = false;
    boolean isBizTimeSelected = false, isCustomTimeSelected = false;
    boolean isTimeSelected = true, isTimeAdded = false;
    ImageView imageView_add_update_icon;
    MapServiceResourceRecyclerAdapter mapServiceResourceRecyclerAdapter;
    private String serviceFlag = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_staff);

        map_services = "";
        Toolbar toolbar = findViewById(R.id.toolbar_add_staff);
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
        sharedpreferences_sessionToken = getSharedPreferences(LoginActivity.MyPREFERENCES_SESSIONTOKEN, Context.MODE_PRIVATE);

        et_staff_name = findViewById(R.id.et_staff_name);
        et_staff_mob = findViewById(R.id.et_staff_mob);
        button_continue = findViewById(R.id.button_add_staff_continue);
        cardView_custom_time = findViewById(R.id.cardview_custom_time);

        imageView_add_update_icon = findViewById(R.id.image_map_icon);
        cardView_mapped_services = findViewById(R.id.cardview_mapped_services);
        layout_mapping = findViewById(R.id.layout_map_service);
        layout_services = findViewById(R.id.layout_select_services);
        layout_wrk_hrs = findViewById(R.id.layout_staff_wrk_hrs);
        layout_weekday = findViewById(R.id.layout_weekdays);
        btn_yes_sday_p = findViewById(R.id.btn_yes_sunday_P);
        btn_yes_mnday_p = findViewById(R.id.btn_yes_monday_P);
        btn_yes_tsday_p = findViewById(R.id.btn_yes_tuesday_P);
        btn_yes_wedday_p = findViewById(R.id.btn_yes_wedsnesday_P);
        btn_yes_trsday_p = findViewById(R.id.btn_yes_thursday_P);
        btn_yes_fdday_p = findViewById(R.id.btn_yes_friday_P);
        btn_yes_strday_p = findViewById(R.id.btn_yes_saturday_P);

        layout_add_time = findViewById(R.id.layout_add_time);
        radioButton_biz_hrs = findViewById(R.id.rb_biz_hours);
        radioButton_custom_time = findViewById(R.id.rb_custom_time);
        layout_custom_time = findViewById(R.id.layout_custom_time);
        text_start_time = findViewById(R.id.text_start_time);
        text_end_time = findViewById(R.id.text_end_time);
        text_weekday = findViewById(R.id.text_weekday);
        button_add_time = findViewById(R.id.button_add_time);
        scrollView = findViewById(R.id.scrollview_add_staff);
        aSwitch_all_days = findViewById(R.id.switch_all_days);
        spinner_weekdays = findViewById(R.id.spinner_weekdays);
        textView_sun_time1 = (TextView) findViewById(R.id.text_sunday_time1);
        textView_sun_time2 = (TextView) findViewById(R.id.text_sunday_time2);
        textView_sun_time3 = (TextView) findViewById(R.id.text_sunday_time3);
        textView_mon_time1 = (TextView) findViewById(R.id.text_monday_time1);
        textView_mon_time2 = (TextView) findViewById(R.id.text_monday_time2);
        textView_mon_time3 = (TextView) findViewById(R.id.text_monday_time3);
        textView_tue_time1 = (TextView) findViewById(R.id.text_tuesday_time1);
        textView_tue_time2 = (TextView) findViewById(R.id.text_tuesday_time2);
        textView_tue_time3 = (TextView) findViewById(R.id.text_tuesday_time3);
        textView_wed_time1 = (TextView) findViewById(R.id.text_wednesday_time1);
        textView_wed_time2 = (TextView) findViewById(R.id.text_wednesday_time2);
        textView_wed_time3 = (TextView) findViewById(R.id.text_wednesday_time3);
        textView_thu_time1 = (TextView) findViewById(R.id.text_thursday_time1);
        textView_thu_time2 = (TextView) findViewById(R.id.text_thursday_time2);
        textView_thu_time3 = (TextView) findViewById(R.id.text_thursday_time3);
        textView_fri_time1 = (TextView) findViewById(R.id.text_friday_time1);
        textView_fri_time2 = (TextView) findViewById(R.id.text_friday_time2);
        textView_fri_time3 = (TextView) findViewById(R.id.text_friday_time3);
        textView_sat_time1 = (TextView) findViewById(R.id.text_saturday_time1);
        textView_sat_time2 = (TextView) findViewById(R.id.text_saturday_time2);
        textView_sat_time3 = (TextView) findViewById(R.id.text_saturday_time3);

        imageView_edit_sun = (ImageView) findViewById(R.id.image_edit_sun);
        imageView_edit_mon = (ImageView) findViewById(R.id.image_edit_mon);
        imageView_edit_tue = (ImageView) findViewById(R.id.image_edit_tue);
        imageView_edit_wed = (ImageView) findViewById(R.id.image_edit_wed);
        imageView_edit_thu = (ImageView) findViewById(R.id.image_edit_thu);
        imageView_edit_fri = (ImageView) findViewById(R.id.image_edit_fri);
        imageView_edit_sat = (ImageView) findViewById(R.id.image_edit_sat);


        linearLayout__sun = (LinearLayout) findViewById(R.id.layout_sunday);
        linearLayout__mon = (LinearLayout) findViewById(R.id.layout_monday);
        linearLayout__tue = (LinearLayout) findViewById(R.id.layout_tuesday);
        linearLayout__wed = (LinearLayout) findViewById(R.id.layout_wednesday);
        linearLayout__thur = (LinearLayout) findViewById(R.id.layout_thursday);
        linearLayout__fri = (LinearLayout) findViewById(R.id.layout_friday);
        linearLayout__sat = (LinearLayout) findViewById(R.id.layout_saturday);

        linearLayout__sun2 = (LinearLayout) findViewById(R.id.layout_sunday_time2);
        linearLayout__mon2 = (LinearLayout) findViewById(R.id.layout_monday_time2);
        linearLayout__tue2 = (LinearLayout) findViewById(R.id.layout_tuesday_time2);
        linearLayout__wed2 = (LinearLayout) findViewById(R.id.layout_wednesday_time2);
        linearLayout__thur2 = (LinearLayout) findViewById(R.id.layout_thursday_time2);
        linearLayout__fri2 = (LinearLayout) findViewById(R.id.layout_friday_time2);
        linearLayout__sat2 = (LinearLayout) findViewById(R.id.layout_saturday_time2);

        linearLayout__sun3 = (LinearLayout) findViewById(R.id.layout_sunday_time3);
        linearLayout__mon3 = (LinearLayout) findViewById(R.id.layout_monday_time3);
        linearLayout__tue3 = (LinearLayout) findViewById(R.id.layout_tuesday_time3);
        linearLayout__wed3 = (LinearLayout) findViewById(R.id.layout_wednesday_time3);
        linearLayout__thur3 = (LinearLayout) findViewById(R.id.layout_thursday_time3);
        linearLayout__fri3 = (LinearLayout) findViewById(R.id.layout_friday_time3);
        linearLayout__sat3 = (LinearLayout) findViewById(R.id.layout_saturday_time3);

        imageView_warning_sun2 = (ImageView) findViewById(R.id.image_warning_sun2);
        imageView_warning_mon2 = (ImageView) findViewById(R.id.image_warning_mon2);
        imageView_warning_tue2 = (ImageView) findViewById(R.id.image_warning_tue2);
        imageView_warning_wed2 = (ImageView) findViewById(R.id.image_warning_wed2);
        imageView_warning_thu2 = (ImageView) findViewById(R.id.image_warning_thu2);
        imageView_warning_fri2 = (ImageView) findViewById(R.id.image_warning_fri2);
        imageView_warning_sat2 = (ImageView) findViewById(R.id.image_warning_sat2);

        imageView_warning_sun3 = (ImageView) findViewById(R.id.image_warning_sun3);
        imageView_warning_mon3 = (ImageView) findViewById(R.id.image_warning_mon3);
        imageView_warning_tue3 = (ImageView) findViewById(R.id.image_warning_tue3);
        imageView_warning_wed3 = (ImageView) findViewById(R.id.image_warning_wed3);
        imageView_warning_thu3 = (ImageView) findViewById(R.id.image_warning_thu3);
        imageView_warning_fri3 = (ImageView) findViewById(R.id.image_warning_fri3);
        imageView_warning_sat3 = (ImageView) findViewById(R.id.image_warning_sat3);

        list_sun = new ArrayList<>();
        list_mon = new ArrayList<>();
        list_tue = new ArrayList<>();
        list_wed = new ArrayList<>();
        list_thu = new ArrayList<>();
        list_fri = new ArrayList<>();
        list_sat = new ArrayList<>();


        //update_serId = new ArrayList<>();

        mng_load_update_list = new ArrayList<>();
        mng_service_name_list = new ArrayList<>();
        mng_service_load_list = new ArrayList<>();
        mng_service_id_list = new ArrayList<>();
        mng_check_pos_list = new ArrayList<>();
        arrayList_map_service = new ArrayList<>();
        arrayList_map_load = new ArrayList<>();
        arrayList_map_service_name = new ArrayList<>();

        aSwitch_all_days.setEnabled(false);
        recyclerView_services = findViewById(R.id.recyclerview_select_services);
        LinearLayoutManager lm = new LinearLayoutManager(this);
        recyclerView_services.setLayoutManager(lm);
        getWeekDays("yyyyyyy");

        if (getIntent().getExtras() != null) {

            pageId = getIntent().getStringExtra("page_id");
           // position = getIntent().getStringExtra("position");
            res_id = getIntent().getStringExtra("res_id");
      /*      isSameBizHrs = Boolean.parseBoolean(getIntent().getStringExtra("sameBizTime"));
            String name = getIntent().getStringExtra("name");
            String mobile = getIntent().getStringExtra("mobile");

            mapServiceResourceBodyList_update = (List<MapServiceResourceBody>) getIntent().getSerializableExtra("mapSerRes");
            Log.d("AddStaff--->", "onCreate: " + pageId + "," + res_id + "," + isSameBizHrs + "," + name + "," + mobile + "," + mapServiceResourceBodyList_update);
*/

            if (pageId.equals("03")) {
                serviceFlag = "1";

                toolbar.setTitle("Update Staff");
                button_continue.setText("UPDATE STAFF");
                layout_wrk_hrs.setVisibility(View.GONE);

                callAPI_Staff();

            } else {
                serviceFlag = "0";
                //imageView_add_update_icon.setImageResource(0);
                imageView_add_update_icon.setImageResource(R.drawable.add_map);
                //fetchMerServices("0");
            }
        }

        layout_mapping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeKeyboard();
                        if (pageId.equals("03")) {
                            fetchMerServices(serviceFlag);
                        } else {
                            fetchMerServices(serviceFlag);
                        }

                    }
                });
            }
        });

        aSwitch_all_days.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (list_sun.isEmpty() && list_mon.isEmpty() && list_tue.isEmpty() && list_wed.isEmpty() && list_thu.isEmpty() && list_fri.isEmpty() && list_sat.isEmpty()) {
                        getAllDaysTime("00:00 - 00:00", "00:00 - 00:00", "00:00 - 00:00");
                    } else {
                        if (!list_sun.isEmpty()) {
                            if (list_sun.size() == 1) {
                                all_days_time1 = list_sun.get(0);
                            } else if (list_sun.size() == 2) {
                                all_days_time1 = list_sun.get(0);
                                all_days_time2 = list_sun.get(1);
                            } else if (list_sun.size() == 3) {
                                all_days_time1 = list_sun.get(0);
                                all_days_time2 = list_sun.get(1);
                                all_days_time3 = list_sun.get(2);
                            }
                        } else if (!list_mon.isEmpty()) {
                            if (list_mon.size() == 1) {
                                all_days_time1 = list_mon.get(0);
                            } else if (list_mon.size() == 2) {
                                all_days_time1 = list_mon.get(0);
                                all_days_time2 = list_mon.get(1);
                            } else if (list_mon.size() == 3) {
                                all_days_time1 = list_mon.get(0);
                                all_days_time2 = list_mon.get(1);
                                all_days_time3 = list_mon.get(2);
                            }
                        } else if (!list_tue.isEmpty()) {
                            if (list_tue.size() == 1) {
                                all_days_time1 = list_tue.get(0);
                            } else if (list_tue.size() == 2) {
                                all_days_time1 = list_tue.get(0);
                                all_days_time2 = list_tue.get(1);
                            } else if (list_tue.size() == 3) {
                                all_days_time1 = list_tue.get(0);
                                all_days_time2 = list_tue.get(1);
                                all_days_time3 = list_tue.get(2);
                            }
                        } else if (!list_wed.isEmpty()) {
                            if (list_wed.size() == 1) {
                                all_days_time1 = list_wed.get(0);
                            } else if (list_wed.size() == 2) {
                                all_days_time1 = list_wed.get(0);
                                all_days_time2 = list_wed.get(1);
                            } else if (list_wed.size() == 3) {
                                all_days_time1 = list_wed.get(0);
                                all_days_time2 = list_wed.get(1);
                                all_days_time3 = list_wed.get(2);
                            }
                        } else if (!list_thu.isEmpty()) {
                            if (list_thu.size() == 1) {
                                all_days_time1 = list_thu.get(0);
                            } else if (list_thu.size() == 2) {
                                all_days_time1 = list_thu.get(0);
                                all_days_time2 = list_thu.get(1);
                            } else if (list_thu.size() == 3) {
                                all_days_time1 = list_thu.get(0);
                                all_days_time2 = list_thu.get(1);
                                all_days_time3 = list_thu.get(2);
                            }
                        } else if (!list_fri.isEmpty()) {
                            if (list_fri.size() == 1) {
                                all_days_time1 = list_fri.get(0);
                            } else if (list_fri.size() == 2) {
                                all_days_time1 = list_fri.get(0);
                                all_days_time2 = list_fri.get(1);
                            } else if (list_fri.size() == 3) {
                                all_days_time1 = list_fri.get(0);
                                all_days_time2 = list_fri.get(1);
                                all_days_time3 = list_fri.get(2);
                            }
                        } else if (!list_sat.isEmpty()) {
                            if (list_sat.size() == 1) {
                                all_days_time1 = list_sat.get(0);
                            } else if (list_sat.size() == 2) {
                                all_days_time1 = list_sat.get(0);
                                all_days_time2 = list_sat.get(1);
                            } else if (list_sat.size() == 3) {
                                all_days_time1 = list_sat.get(0);
                                all_days_time2 = list_sat.get(1);
                                all_days_time3 = list_sat.get(2);
                            }
                        }
                   /*     if (all_days_time2.equals("00:00 - 00:00")) {
                            all_days_time2 = "--";
                        }
                        if (all_days_time3.equals("00:00 - 00:00")) {
                            all_days_time3 = "--";
                        }*/

                        getAllDaysTime(all_days_time1, all_days_time2, all_days_time3);
                        getClearList();
                        getCopyTimeAllDays(String.valueOf(weekdays), all_days_time1, all_days_time2, all_days_time3);
                        Log.d("Copy_all_day---->", "onCheckedChanged: " + all_days_time1 + "," + all_days_time2 + "," + all_days_time3);
                    }

                } else {
                    getAllDaysTime("00:00 - 00:00", "00:00 - 00:00", "00:00 - 00:00");
                    getClearList();
                    all_days_time1 = "00:00 - 00:00";
                    all_days_time2 = "00:00 - 00:00";
                    all_days_time3 = "00:00 - 00:00";
                    isTimeAdded = false;
                    aSwitch_all_days.setEnabled(false);
                }
            }
        });

        spinner_weekdays.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                layout_add_time.setVisibility(View.VISIBLE);
                String day = parent.getSelectedItem().toString();
                // String day = arrayList_weekdays.get(position);
                text_weekday.setText("" + day);
                text_start_time.setText("00:00");
                text_end_time.setText("00:00");
                button_add_time.setText("ADD");
                // Toast.makeText(AddServiceAvailTimeActivity.this, ""+day, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        text_start_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timepicker_dialog("1");
            }
        });

        text_end_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timepicker_dialog("0");
            }
        });


        radioButton_biz_hrs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (radioButton_biz_hrs.isChecked()) {
                    radioButton_biz_hrs.setChecked(true);
                    radioButton_custom_time.setChecked(false);
                    layout_custom_time.setVisibility(View.GONE);
                    isSameBizHrs = true;
                }
                isBizTimeSelected = true;
                isCustomTimeSelected = false;
                closeKeyboard();
            }
        });

        radioButton_custom_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (radioButton_custom_time.isChecked()) {
                    radioButton_biz_hrs.setChecked(false);
                    radioButton_custom_time.setChecked(true);
                    layout_custom_time.setVisibility(View.VISIBLE);
                    isSameBizHrs = false;
                }
                isCustomTimeSelected = true;
                isBizTimeSelected = false;
                closeKeyboard();
            }
        });


        btn_yes_sday_p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isActiveSunday_p) {
                    btn_yes_sday_p.setBackgroundColor(getResources().getColor(R.color.dark_grey));
                    btn_yes_sday_p.setTextColor(getResources().getColor(R.color.black));
                    isActiveSunday_p = false;

                    weekdays.setCharAt(0, 'n');
                    getWeekDays(String.valueOf(weekdays));
                    getWeekDaysLayout(String.valueOf(weekdays));
                    textView_sun_time1.setText("00:00 - 00:00");
                    textView_sun_time2.setText("00:00 - 00:00");
                    textView_sun_time3.setText("00:00 - 00:00");
                    Log.d("Weekdayslist--->", "onClick: " + weekdays);
                } else {
                    btn_yes_sday_p.setBackgroundColor(getResources().getColor(R.color.green_hase));
                    btn_yes_sday_p.setTextColor(getResources().getColor(R.color.white));
                    isActiveSunday_p = true;

                    weekdays.setCharAt(0, 'y');
                    getWeekDays(String.valueOf(weekdays));
                    getWeekDaysLayout(String.valueOf(weekdays));
                    Log.d("Weekdayslist--->", "onClick: " + weekdays);
                }
            }
        });

        btn_yes_mnday_p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isActiveMonday_p) {
                    btn_yes_mnday_p.setBackgroundColor(getResources().getColor(R.color.dark_grey));
                    btn_yes_mnday_p.setTextColor(getResources().getColor(R.color.black));
                    isActiveMonday_p = false;

                    weekdays.setCharAt(1, 'n');
                    getWeekDays(String.valueOf(weekdays));
                    getWeekDaysLayout(String.valueOf(weekdays));
                    textView_mon_time1.setText("00:00 - 00:00");
                    textView_mon_time2.setText("00:00 - 00:00");
                    textView_mon_time3.setText("00:00 - 00:00");
                    Log.d("Weekdayslist--->", "onClick: " + weekdays);
                } else {
                    btn_yes_mnday_p.setBackgroundColor(getResources().getColor(R.color.green_hase));
                    btn_yes_mnday_p.setTextColor(getResources().getColor(R.color.white));
                    isActiveMonday_p = true;
                    weekdays.setCharAt(1, 'y');
                    getWeekDays(String.valueOf(weekdays));
                    getWeekDaysLayout(String.valueOf(weekdays));
                    Log.d("Weekdayslist--->", "onClick: " + weekdays);
                }
            }
        });


        btn_yes_tsday_p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isActiveTuesday_p) {
                    btn_yes_tsday_p.setBackgroundColor(getResources().getColor(R.color.dark_grey));
                    btn_yes_tsday_p.setTextColor(getResources().getColor(R.color.black));
                    isActiveTuesday_p = false;

                    weekdays.setCharAt(2, 'n');
                    getWeekDays(String.valueOf(weekdays));
                    getWeekDaysLayout(String.valueOf(weekdays));
                    textView_tue_time1.setText("00:00 - 00:00");
                    textView_tue_time2.setText("00:00 - 00:00");
                    textView_tue_time3.setText("00:00 - 00:00");
                    Log.d("Weekdayslist--->", "onClick: " + weekdays);
                } else {
                    btn_yes_tsday_p.setBackgroundColor(getResources().getColor(R.color.green_hase));
                    btn_yes_tsday_p.setTextColor(getResources().getColor(R.color.white));
                    isActiveTuesday_p = true;

                    weekdays.setCharAt(2, 'y');
                    getWeekDays(String.valueOf(weekdays));
                    getWeekDaysLayout(String.valueOf(weekdays));
                    Log.d("Weekdayslist--->", "onClick: " + weekdays);
                }
            }
        });


        btn_yes_wedday_p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isActiveWednesday_p) {
                    btn_yes_wedday_p.setBackgroundColor(getResources().getColor(R.color.dark_grey));
                    btn_yes_wedday_p.setTextColor(getResources().getColor(R.color.black));
                    isActiveWednesday_p = false;

                    weekdays.setCharAt(3, 'n');
                    getWeekDays(String.valueOf(weekdays));
                    getWeekDaysLayout(String.valueOf(weekdays));
                    textView_wed_time1.setText("00:00 - 00:00");
                    textView_wed_time2.setText("00:00 - 00:00");
                    textView_wed_time3.setText("00:00 - 00:00");
                    Log.d("Weekdayslist--->", "onClick: " + weekdays);
                } else {
                    btn_yes_wedday_p.setBackgroundColor(getResources().getColor(R.color.green_hase));
                    btn_yes_wedday_p.setTextColor(getResources().getColor(R.color.white));
                    isActiveWednesday_p = true;

                    weekdays.setCharAt(3, 'y');
                    getWeekDays(String.valueOf(weekdays));
                    getWeekDaysLayout(String.valueOf(weekdays));
                    Log.d("Weekdayslist--->", "onClick: " + weekdays);
                }

            }
        });


        btn_yes_trsday_p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isActiveThursday_p) {
                    btn_yes_trsday_p.setBackgroundColor(getResources().getColor(R.color.dark_grey));
                    btn_yes_trsday_p.setTextColor(getResources().getColor(R.color.black));
                    isActiveThursday_p = false;

                    weekdays.setCharAt(4, 'n');
                    getWeekDays(String.valueOf(weekdays));
                    getWeekDaysLayout(String.valueOf(weekdays));
                    textView_thu_time1.setText("00:00 - 00:00");
                    textView_thu_time2.setText("00:00 - 00:00");
                    textView_thu_time3.setText("00:00 - 00:00");
                    Log.d("Weekdayslist--->", "onClick: " + weekdays);
                } else {
                    btn_yes_trsday_p.setBackgroundColor(getResources().getColor(R.color.green_hase));
                    btn_yes_trsday_p.setTextColor(getResources().getColor(R.color.white));
                    isActiveThursday_p = true;

                    weekdays.setCharAt(4, 'y');
                    getWeekDays(String.valueOf(weekdays));
                    getWeekDaysLayout(String.valueOf(weekdays));
                    Log.d("Weekdayslist--->", "onClick: " + weekdays);
                }

            }
        });


        btn_yes_fdday_p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isActiveFriday_p) {
                    btn_yes_fdday_p.setBackgroundColor(getResources().getColor(R.color.dark_grey));
                    btn_yes_fdday_p.setTextColor(getResources().getColor(R.color.black));
                    isActiveFriday_p = false;

                    weekdays.setCharAt(5, 'n');
                    getWeekDays(String.valueOf(weekdays));
                    getWeekDaysLayout(String.valueOf(weekdays));
                    textView_fri_time1.setText("00:00 - 00:00");
                    textView_fri_time2.setText("00:00 - 00:00");
                    textView_fri_time3.setText("00:00 - 00:00");
                    Log.d("Weekdayslist--->", "onClick: " + weekdays);
                } else {
                    btn_yes_fdday_p.setBackgroundColor(getResources().getColor(R.color.green_hase));
                    btn_yes_fdday_p.setTextColor(getResources().getColor(R.color.white));
                    isActiveFriday_p = true;

                    weekdays.setCharAt(5, 'y');
                    getWeekDays(String.valueOf(weekdays));
                    getWeekDaysLayout(String.valueOf(weekdays));
                    Log.d("Weekdayslist--->", "onClick: " + weekdays);
                }

            }
        });


        btn_yes_strday_p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isActiveSaturday_p) {
                    btn_yes_strday_p.setBackgroundColor(getResources().getColor(R.color.dark_grey));
                    btn_yes_strday_p.setTextColor(getResources().getColor(R.color.black));
                    isActiveSaturday_p = false;

                    weekdays.setCharAt(6, 'n');
                    getWeekDays(String.valueOf(weekdays));
                    getWeekDaysLayout(String.valueOf(weekdays));
                    textView_sat_time1.setText("00:00 - 00:00");
                    textView_sat_time2.setText("00:00 - 00:00");
                    textView_sat_time3.setText("00:00 - 00:00");
                    Log.d("Weekdayslist--->", "onClick: " + weekdays);
                } else {
                    btn_yes_strday_p.setBackgroundColor(getResources().getColor(R.color.green_hase));
                    btn_yes_strday_p.setTextColor(getResources().getColor(R.color.white));
                    isActiveSaturday_p = true;

                    weekdays.setCharAt(6, 'y');
                    getWeekDays(String.valueOf(weekdays));
                    getWeekDaysLayout(String.valueOf(weekdays));
                    Log.d("Weekdayslist--->", "onClick: " + weekdays);
                }
            }
        });


        //Edit sunday
        imageView_edit_sun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                text_weekday.setText("Sunday");
                text_start_time.setText("00:00");
                text_end_time.setText("00:00");
                list_sun.clear();
                textView_sun_time1.setText("00:00 - 00:00");
                textView_sun_time2.setText("00:00 - 00:00");
                textView_sun_time3.setText("00:00 - 00:00");
                getScrollTop();
            }
        });

        //Edit monday
        imageView_edit_mon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text_weekday.setText("Monday");
                text_start_time.setText("00:00");
                text_end_time.setText("00:00");
                list_mon.clear();
                textView_mon_time1.setText("00:00 - 00:00");
                textView_mon_time2.setText("00:00 - 00:00");
                textView_mon_time3.setText("00:00 - 00:00");
                getScrollTop();
            }
        });

        //Edit tuesday
        imageView_edit_tue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text_weekday.setText("Tuesday");
                text_start_time.setText("00:00");
                text_end_time.setText("00:00");
                list_tue.clear();
                textView_tue_time1.setText("00:00 - 00:00");
                textView_tue_time2.setText("00:00 - 00:00");
                textView_tue_time3.setText("00:00 - 00:00");
                getScrollTop();
            }
        });

        //Edit wednesday
        imageView_edit_wed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text_weekday.setText("Wednesday");
                text_start_time.setText("00:00");
                text_end_time.setText("00:00");
                list_wed.clear();
                textView_wed_time1.setText("00:00 - 00:00");
                textView_wed_time2.setText("00:00 - 00:00");
                textView_wed_time3.setText("00:00 - 00:00");
                getScrollTop();
            }
        });

        //Edit thursday
        imageView_edit_thu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text_weekday.setText("Thursday");
                text_start_time.setText("00:00");
                text_end_time.setText("00:00");
                list_thu.clear();
                textView_thu_time1.setText("00:00 - 00:00");
                textView_thu_time2.setText("00:00 - 00:00");
                textView_thu_time3.setText("00:00 - 00:00");
                getScrollTop();
            }
        });


        //Edit friday
        imageView_edit_fri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text_weekday.setText("Friday");
                text_start_time.setText("00:00");
                text_end_time.setText("00:00");
                list_fri.clear();
                textView_fri_time1.setText("00:00 - 00:00");
                textView_fri_time2.setText("00:00 - 00:00");
                textView_fri_time3.setText("00:00 - 00:00");
                getScrollTop();
            }
        });


        //Edit saturday
        imageView_edit_sat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text_weekday.setText("Saturday");
                text_start_time.setText("00:00");
                text_end_time.setText("00:00");
                list_sat.clear();
                textView_sat_time1.setText("00:00 - 00:00");
                textView_sat_time2.setText("00:00 - 00:00");
                textView_sat_time3.setText("00:00 - 00:00");
                getScrollTop();
            }
        });


        button_add_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("Weekdays_ListSize---", "onClick: " + list_sun.size());
                String weekday = text_weekday.getText().toString();
                String s_time = text_start_time.getText().toString();
                String e_time = text_end_time.getText().toString();

                boolean chktime = checktimings(s_time, e_time);
                if (chktime) {
                    isTimeAdded = true;
                    aSwitch_all_days.setEnabled(true);
                    if (weekday.equals("Sunday")) {

                        if (list_sun.size() <= 2) {
                            if (list_sun.size() == 1) {
                                String data_sun = list_sun.get(0);
                                String[] strs = data_sun.split(" - ");
                                String s_tm = strs[0];
                                String e_tm = strs[1];

                                boolean chktime_slot2 = checktimings2(e_tm, s_time);
                                if (chktime_slot2) {
                                    list_sun.add(s_time + " - " + e_time);
                                    textView_sun_time2.setText(list_sun.get(1));
                                    runAnimation(textView_sun_time2);
                                    getAddTimeScrollBottom(linearLayout__sun);

                                    Log.d("sunday_list--->", "add_time: " + list_sun);
                                } else {
                                    getDialog("Availability time cannot overlap");
                                }

                            } else if (list_sun.size() == 2) {
                                String data_sun = list_sun.get(1);
                                String[] strs = data_sun.split(" - ");
                                String s_tm = strs[0];
                                String e_tm = strs[1];

                                boolean chktime_slot2 = checktimings2(e_tm, s_time);
                                if (chktime_slot2) {
                                    list_sun.add(s_time + " - " + e_time);
                                    textView_sun_time3.setText(list_sun.get(2));
                                    runAnimation(textView_sun_time3);
                                    getAddTimeScrollBottom(linearLayout__sun);
                                    Log.d("sunday_list--->", "add_time: " + list_sun);
                                } else {
                                    getDialog("Availability time cannot overlap");
                                }

                            } else {

                                list_sun.add(s_time + " - " + e_time);
                                textView_sun_time1.setText(list_sun.get(0));
                                linearLayout__sun.setVisibility(View.VISIBLE);
                                runAnimation(textView_sun_time1);
                                getAddTimeScrollBottom(linearLayout__sun);
                            }
                        } else {
                            getDialog("You have reached the limit for sunday");
                        }


                    } else if (weekday.equals("Monday")) {

                        if (list_mon.size() <= 2) {
                            if (list_mon.size() == 1) {
                                String data_sun = list_mon.get(0);
                                String[] strs = data_sun.split(" - ");
                                String s_tm = strs[0];
                                String e_tm = strs[1];

                                boolean chktime_slot2 = checktimings2(e_tm, s_time);
                                if (chktime_slot2) {
                                    list_mon.add(s_time + " - " + e_time);
                                    textView_mon_time2.setText(list_mon.get(1));
                                    getAddTimeScrollBottom(linearLayout__mon);
                                    runAnimation(textView_mon_time2);
                                } else {
                                    getDialog("Availability time cannot overlap");
                                }

                            } else if (list_mon.size() == 2) {
                                String data_sun = list_mon.get(1);
                                String[] strs = data_sun.split(" - ");
                                String s_tm = strs[0];
                                String e_tm = strs[1];

                                boolean chktime_slot2 = checktimings2(e_tm, s_time);
                                if (chktime_slot2) {
                                    list_mon.add(s_time + " - " + e_time);
                                    textView_mon_time3.setText(list_mon.get(2));
                                    getAddTimeScrollBottom(linearLayout__mon);
                                    runAnimation(textView_mon_time3);
                                } else {
                                    getDialog("Availability time cannot overlap");
                                }

                            } else {

                                list_mon.add(s_time + " - " + e_time);
                                textView_mon_time1.setText(list_mon.get(0));
                                linearLayout__mon.setVisibility(View.VISIBLE);
                                getAddTimeScrollBottom(linearLayout__mon);
                                runAnimation(textView_mon_time1);

                            }
                        } else {
                            getDialog("You have reached the limit for monday");
                        }


                    } else if (weekday.equals("Tuesday")) {
                        if (list_tue.size() <= 2) {
                            if (list_tue.size() == 1) {
                                String data_sun = list_tue.get(0);
                                String[] strs = data_sun.split(" - ");
                                String s_tm = strs[0];
                                String e_tm = strs[1];

                                boolean chktime_slot2 = checktimings2(e_tm, s_time);
                                if (chktime_slot2) {
                                    list_tue.add(s_time + " - " + e_time);
                                    textView_tue_time2.setText(list_tue.get(1));
                                    getAddTimeScrollBottom(linearLayout__tue);
                                    runAnimation(textView_tue_time2);
                                } else {
                                    getDialog("Availability time cannot overlap");
                                }

                            } else if (list_tue.size() == 2) {
                                String data_sun = list_tue.get(1);
                                String[] strs = data_sun.split(" - ");
                                String s_tm = strs[0];
                                String e_tm = strs[1];

                                boolean chktime_slot2 = checktimings2(e_tm, s_time);
                                if (chktime_slot2) {
                                    list_tue.add(s_time + " - " + e_time);
                                    textView_tue_time3.setText(list_tue.get(2));
                                    getAddTimeScrollBottom(linearLayout__tue);
                                    runAnimation(textView_tue_time3);
                                } else {
                                    getDialog("Availability time cannot overlap");
                                }

                            } else {

                                list_tue.add(s_time + " - " + e_time);
                                textView_tue_time1.setText(list_tue.get(0));
                                linearLayout__tue.setVisibility(View.VISIBLE);
                                getAddTimeScrollBottom(linearLayout__tue);
                                runAnimation(textView_tue_time1);

                            }
                        } else {
                            getDialog("You have reached the limit for tuesday");
                        }

                    } else if (weekday.equals("Wednesday")) {
                        if (list_wed.size() <= 2) {
                            if (list_wed.size() == 1) {
                                String data_sun = list_wed.get(0);
                                String[] strs = data_sun.split(" - ");
                                String s_tm = strs[0];
                                String e_tm = strs[1];

                                boolean chktime_slot2 = checktimings2(e_tm, s_time);
                                if (chktime_slot2) {
                                    list_wed.add(s_time + " - " + e_time);
                                    textView_wed_time2.setText(list_wed.get(1));
                                    getAddTimeScrollBottom(linearLayout__wed);
                                    runAnimation(textView_wed_time2);
                                } else {
                                    getDialog("Availability time cannot overlap");
                                }

                            } else if (list_wed.size() == 2) {
                                String data_sun = list_wed.get(1);
                                String[] strs = data_sun.split(" - ");
                                String s_tm = strs[0];
                                String e_tm = strs[1];

                                boolean chktime_slot2 = checktimings2(e_tm, s_time);
                                if (chktime_slot2) {
                                    list_wed.add(s_time + " - " + e_time);
                                    textView_wed_time3.setText(list_wed.get(2));
                                    getAddTimeScrollBottom(linearLayout__wed);
                                    runAnimation(textView_wed_time3);
                                } else {
                                    getDialog("Availability time cannot overlap");
                                }

                            } else {

                                list_wed.add(s_time + " - " + e_time);
                                textView_wed_time1.setText(list_wed.get(0));
                                linearLayout__wed.setVisibility(View.VISIBLE);
                                getAddTimeScrollBottom(linearLayout__wed);
                                runAnimation(textView_wed_time1);

                            }
                        } else {
                            getDialog("You have reached the limit for wednesday");
                        }

                    } else if (weekday.equals("Thursday")) {
                        if (list_thu.size() <= 2) {
                            if (list_thu.size() == 1) {
                                String data_sun = list_thu.get(0);
                                String[] strs = data_sun.split(" - ");
                                String s_tm = strs[0];
                                String e_tm = strs[1];

                                boolean chktime_slot2 = checktimings2(e_tm, s_time);
                                if (chktime_slot2) {
                                    list_thu.add(s_time + " - " + e_time);
                                    textView_thu_time2.setText(list_thu.get(1));
                                    getAddTimeScrollBottom(linearLayout__thur);
                                    runAnimation(textView_thu_time2);
                                } else {
                                    getDialog("Availability time cannot overlap");
                                }

                            } else if (list_thu.size() == 2) {
                                String data_sun = list_thu.get(1);
                                String[] strs = data_sun.split(" - ");
                                String s_tm = strs[0];
                                String e_tm = strs[1];

                                boolean chktime_slot2 = checktimings2(e_tm, s_time);
                                if (chktime_slot2) {
                                    list_thu.add(s_time + " - " + e_time);
                                    textView_thu_time3.setText(list_thu.get(2));
                                    getAddTimeScrollBottom(linearLayout__thur);
                                    runAnimation(textView_thu_time3);
                                } else {
                                    getDialog("Availability time cannot overlap");
                                }

                            } else {

                                list_thu.add(s_time + " - " + e_time);
                                textView_thu_time1.setText(list_thu.get(0));
                                linearLayout__thur.setVisibility(View.VISIBLE);
                                getAddTimeScrollBottom(linearLayout__thur);
                                runAnimation(textView_thu_time1);

                            }
                        } else {
                            getDialog("You have reached the limit for thursday");
                        }

                    } else if (weekday.equals("Friday")) {
                        if (list_fri.size() <= 2) {
                            if (list_fri.size() == 1) {
                                String data_sun = list_fri.get(0);
                                String[] strs = data_sun.split(" - ");
                                String s_tm = strs[0];
                                String e_tm = strs[1];

                                boolean chktime_slot2 = checktimings2(e_tm, s_time);
                                if (chktime_slot2) {
                                    list_fri.add(s_time + " - " + e_time);
                                    textView_fri_time2.setText(list_fri.get(1));
                                    getAddTimeScrollBottom(linearLayout__fri);
                                    runAnimation(textView_fri_time2);
                                } else {
                                    getDialog("Availability time cannot overlap");
                                }

                            } else if (list_fri.size() == 2) {
                                String data_sun = list_fri.get(1);
                                String[] strs = data_sun.split(" - ");
                                String s_tm = strs[0];
                                String e_tm = strs[1];

                                boolean chktime_slot2 = checktimings2(e_tm, s_time);
                                if (chktime_slot2) {
                                    list_fri.add(s_time + " - " + e_time);
                                    textView_fri_time3.setText(list_fri.get(2));
                                    getAddTimeScrollBottom(linearLayout__fri);
                                    runAnimation(textView_fri_time3);
                                } else {
                                    getDialog("Availability time cannot overlap");
                                }

                            } else {

                                list_fri.add(s_time + " - " + e_time);
                                textView_fri_time1.setText(list_fri.get(0));
                                linearLayout__fri.setVisibility(View.VISIBLE);
                                getAddTimeScrollBottom(linearLayout__fri);
                                runAnimation(textView_fri_time1);

                            }
                        } else {
                            getDialog("You have reached the limit for friday");
                        }

                    } else if (weekday.equals("Saturday")) {
                        if (list_sat.size() <= 2) {
                            if (list_sat.size() == 1) {
                                String data_sun = list_sat.get(0);
                                String[] strs = data_sun.split(" - ");
                                String s_tm = strs[0];
                                String e_tm = strs[1];

                                boolean chktime_slot2 = checktimings2(e_tm, s_time);
                                if (chktime_slot2) {
                                    list_sat.add(s_time + " - " + e_time);
                                    textView_sat_time2.setText(list_sat.get(1));
                                    getAddTimeScrollBottom(linearLayout__sat);
                                    runAnimation(textView_sat_time2);
                                } else {
                                    getDialog("Availability time cannot overlap");
                                }

                            } else if (list_sat.size() == 2) {
                                String data_sun = list_sat.get(1);
                                String[] strs = data_sun.split(" - ");
                                String s_tm = strs[0];
                                String e_tm = strs[1];

                                boolean chktime_slot2 = checktimings2(e_tm, s_time);
                                if (chktime_slot2) {
                                    list_sat.add(s_time + " - " + e_time);
                                    textView_sat_time3.setText(list_sat.get(2));
                                    getAddTimeScrollBottom(linearLayout__sat);
                                    runAnimation(textView_sat_time3);
                                } else {
                                    getDialog("Availability time cannot overlap");
                                }

                            } else {

                                list_sat.add(s_time + " - " + e_time);
                                textView_sat_time1.setText(list_sat.get(0));
                                linearLayout__sat.setVisibility(View.VISIBLE);
                                getAddTimeScrollBottom(linearLayout__sat);
                                runAnimation(textView_sat_time1);

                            }
                        } else {
                            getDialog("You have reached the limit for saturday");
                        }

                    }

                    //layout_add_time.setVisibility(View.GONE);


                } else {
                    getDialog("Invalid Time");
                }


            }
        });


        button_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callAPI();

            }
        });


    }

    private void callAPI_Staff() {
        AppointmentEnquiryBody enquiryBody = new AppointmentEnquiryBody();
        enquiryBody.setReqType(Constants.UPDATE_RESOURCE_INFO);
        enquiryBody.setMerId(sharedpreferences_sessionToken.getString(LoginActivity.MERID, ""));
        enquiryBody.setOutletId(sharedpreferences_sessionToken.getString(LoginActivity.OUTLETID, ""));
        enquiryBody.callerType("m");
        enquiryBody.setResId(res_id);
        enquiryBody.setDeviceId(sharedpreferences_sessionToken.getString(LoginActivity.DEVICEID, ""));
        enquiryBody.setSessionToken(sharedpreferences_sessionToken.getString(LoginActivity.SESSIONTOKEN, ""));
        boolean isConn = ConnectivityReceiver.isConnected();
        if (isConn) {
            try {
                intermediateAlertDialog = new IntermediateAlertDialog(AddStaffActivity.this);
                fetchStaff(enquiryBody);
            } catch (ApiException e) {
                e.printStackTrace();
            }
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    new AlertDialogFailure(AddStaffActivity.this, getResources().getString(R.string.no_internet_sub_title), "OK", getResources().getString(R.string.no_internet_title), getResources().getString(R.string.no_internet_Heading)) {
                        @Override
                        public void onButtonClick() {
                            callAPI_Staff();
                        }
                    };
                }
            });
        }
    }

    private void fetchStaff(AppointmentEnquiryBody requestBody) throws ApiException {
        Log.d("fetchService--->", "fetchService: " + requestBody);
        OkHttpApiClient okHttpApiClient = new OkHttpApiClient(AddStaffActivity.this);
        MerchantApisApi webMerchantApisApi = new MerchantApisApi();
        webMerchantApisApi.setApiClient(okHttpApiClient.getApiClient());

        webMerchantApisApi.merchantAppointmentEnquiryAsync(requestBody, new ApiCallback<AppointmentEnquiryResponse>() {
            @Override
            public void onFailure(ApiException e, int statusCode, Map<String, List<String>> responseHeaders) {
                Log.d("fetchStaff--->", "onFailure-" + e.getMessage());
                if (intermediateAlertDialog != null) {
                    intermediateAlertDialog.dismissAlertDialog();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new AlertDialogFailure(AddStaffActivity.this, getResources().getString(R.string.try_again), "OK", getResources().getString(R.string.server_error), "Failed") {
                            @Override
                            public void onButtonClick() {

                                onBackPressed();
                            }
                        };
                    }
                });
            }

            @Override
            public void onSuccess(final AppointmentEnquiryResponse result, int statusCode, Map<String, List<String>> responseHeaders) {
                Log.d("fetchStaff--->", "onSuccess-" + statusCode + "," + result + "," + result.getResource());
                if (Integer.parseInt(result.getStatusCode()) == 200) {
                  /*  if (intermediateAlertDialog != null) {
                        intermediateAlertDialog.dismissAlertDialog();
                    }*/
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (result.getResource() != null) {

                                et_staff_name.setText(result.getResource().getResName());
                                et_staff_mob.setText(result.getResource().getMobile());
                                mapServiceResourceBodyList_update=result.getResource().getSerResMaps();
                                if (result.getResource().getResName().length() > 0) {
                                    et_staff_name.setSelection(result.getResource().getResName().length());
                                    imageView_add_update_icon.setImageResource(R.drawable.edit_map);
                                }

                                fetchMerServices("2");
                            }
                        }
                    });
                } else if (Integer.parseInt(result.getStatusCode()) == 400) {
                    if (intermediateAlertDialog != null) {
                        intermediateAlertDialog.dismissAlertDialog();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new AlertDialogFailure(AddStaffActivity.this, getResources().getString(R.string.try_again), "OK", "Invalid data", "Failed") {
                                @Override
                                public void onButtonClick() {

                                    onBackPressed();
                                }
                            };
                        }
                    });
                } else if (Integer.parseInt(result.getStatusCode()) == 401) {
                    if (intermediateAlertDialog != null) {
                        intermediateAlertDialog.dismissAlertDialog();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new CAllLoginAPI() {
                                @Override
                                public void onButtonClick() {
                                    callAPI_Staff();
                                }
                            }.callLoginAPI(AddStaffActivity.this);
                        }
                    });

                } else {
                    if (intermediateAlertDialog != null) {
                        intermediateAlertDialog.dismissAlertDialog();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new AlertDialogFailure(AddStaffActivity.this, getResources().getString(R.string.try_again), "OK", getResources().getString(R.string.went_wrong), "Failed") {
                                @Override
                                public void onButtonClick() {

                                    onBackPressed();
                                }
                            };
                        }
                    });
                }
            }

            @Override
            public void onUploadProgress(long bytesWritten, long contentLength, boolean done) {

            }

            @Override
            public void onDownloadProgress(long bytesRead, long contentLength, boolean done) {

            }
        });

    }

    
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        closeKeyboard();
     /*   Intent in = new Intent(AddStaffActivity.this, AddStaffActivity.class);
        startActivity(in);
        finish();
        overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);*/

    }

    private void getDialog(final String msg) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new AlertDialogFailure(AddStaffActivity.this, msg, "OK", "", "Warning") {
                    @Override
                    public void onButtonClick() {

                    }
                };
            }
        });
    }


    private void apptCreateStaff(ApptTransactionBody requestBody) throws ApiException {
        Log.d("createStaff---", "createStaff: " + requestBody);

        OkHttpApiClient okHttpApiClient = new OkHttpApiClient(AddStaffActivity.this);
        MerchantApisApi webMerchantApisApi = new MerchantApisApi();
        webMerchantApisApi.setApiClient(okHttpApiClient.getApiClient());

        webMerchantApisApi.merchantAppointmentTransactionAsync(requestBody, new ApiCallback<ApptTransactionResponse>() {
            @Override
            public void onFailure(ApiException e, int statusCode, Map<String, List<String>> responseHeaders) {
                Log.d("createStaff--->", "onFailure-" + e.getMessage());
                if (intermediateAlertDialog != null) {
                    intermediateAlertDialog.dismissAlertDialog();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new AlertDialogFailure(AddStaffActivity.this, getResources().getString(R.string.try_again), "OK", getResources().getString(R.string.server_error), "Failed") {
                            @Override
                            public void onButtonClick() {
                        /*        startActivity(new Intent(AddStaffActivity.this, DashboardActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                                finish();
                                overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);*/

                                onBackPressed();
                            }
                        };
                    }
                });
            }

            @Override
            public void onSuccess(ApptTransactionResponse result, int statusCode, Map<String, List<String>> responseHeaders) {
                Log.d("createStaff--->", "onSuccess-" + statusCode + "," + result.getStatusMessage());
                if (Integer.parseInt(result.getStatusCode()) == 200) {
                    if (intermediateAlertDialog != null) {
                        intermediateAlertDialog.dismissAlertDialog();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new AlertDialogFailure(AddStaffActivity.this, "Staff created successfully!", "OK", "", "Success") {
                                @Override
                                public void onButtonClick() {
                                    Intent in = new Intent(AddStaffActivity.this, DashboardActivity.class);
                                    in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(in);
                                    finish();
                                    overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);

                                    //onBackPressed();
                                }
                            };
                        }
                    });
                } else if (Integer.parseInt(result.getStatusCode()) == 204) {
                    if (intermediateAlertDialog != null) {
                        intermediateAlertDialog.dismissAlertDialog();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new AlertDialogFailure(AddStaffActivity.this, "Staff available time creation failed!", "OK", "Staff created successfully", "Warning") {
                                @Override
                                public void onButtonClick() {
                               /*     startActivity(new Intent(AddStaffActivity.this, DashboardActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                                    finish();
                                    overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);*/

                                    onBackPressed();
                                }
                            };
                        }
                    });
                } else if (Integer.parseInt(result.getStatusCode()) == 409) {
                    if (intermediateAlertDialog != null) {
                        intermediateAlertDialog.dismissAlertDialog();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new AlertDialogFailure(AddStaffActivity.this, getResources().getString(R.string.try_again), "OK", "Staff registration failed", "Failed") {
                                @Override
                                public void onButtonClick() {
                               /*     startActivity(new Intent(AddStaffActivity.this, DashboardActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                                    finish();
                                    overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);*/

                                    onBackPressed();
                                }
                            };
                        }
                    });
                } else if (Integer.parseInt(result.getStatusCode()) == 412) {
                    if (intermediateAlertDialog != null) {
                        intermediateAlertDialog.dismissAlertDialog();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new AlertDialogFailure(AddStaffActivity.this, getResources().getString(R.string.try_again), "OK", "Service mapping failed", "Failed") {
                                @Override
                                public void onButtonClick() {
                               /*     startActivity(new Intent(AddStaffActivity.this, DashboardActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                                    finish();
                                    overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);*/

                                    onBackPressed();
                                }
                            };
                        }
                    });
                } else if (Integer.parseInt(result.getStatusCode()) == 401) {
                    if (intermediateAlertDialog != null) {
                        intermediateAlertDialog.dismissAlertDialog();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new CAllLoginAPI() {
                                @Override
                                public void onButtonClick() {
                                    createMerStaff();
                                }
                            }.callLoginAPI(AddStaffActivity.this);
                        }
                    });

                } else {
                    if (intermediateAlertDialog != null) {
                        intermediateAlertDialog.dismissAlertDialog();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new AlertDialogFailure(AddStaffActivity.this, getResources().getString(R.string.try_again), "OK", getResources().getString(R.string.went_wrong), "Failed") {
                                @Override
                                public void onButtonClick() {
                               /*     startActivity(new Intent(AddStaffActivity.this, DashboardActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                                    finish();
                                    overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);*/

                                    onBackPressed();
                                }
                            };
                        }
                    });
                }
            }

            @Override
            public void onUploadProgress(long bytesWritten, long contentLength, boolean done) {

            }

            @Override
            public void onDownloadProgress(long bytesRead, long contentLength, boolean done) {

            }
        });
    }


    private void getScrollTop() {
        scrollView.postDelayed(new Runnable() {
            @Override
            public void run() {
                //scrollView.fullScroll(ScrollView.FOCUS_UP);
                //scrollView.scrollTo(0, layout_add_time.getBottom());
                layout_add_time.getParent().requestChildFocus(layout_add_time, layout_add_time);
            }
        }, 500);
    }

    private void getAddTimeScrollBottom(final View view) {
    /*    scrollView.postDelayed(new Runnable() {
            @Override
            public void run() {
                // scrollView.smoothScrollTo(0, view.getBottom());
                int scrollTo = ((View) view.getParent().getParent()).getTop() + view.getTop();
                scrollView.smoothScrollTo(0, scrollTo);
            }
        }, 500);*/
        view.getParent().requestChildFocus(view, view);
    }

    private void runAnimation(final View view) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Animation a = AnimationUtils.loadAnimation(AddStaffActivity.this, R.anim.blinking_animation1);
                a.reset();
                view.clearAnimation();
                view.startAnimation(a);
            }
        }, 800);

    }

    private void getAllDaysTime(String time1, String time2, String time3) {
        textView_sun_time1.setText(time1);
        textView_sun_time2.setText(time2);
        textView_sun_time3.setText(time3);
        textView_mon_time1.setText(time1);
        textView_mon_time2.setText(time2);
        textView_mon_time3.setText(time3);
        textView_tue_time1.setText(time1);
        textView_tue_time2.setText(time2);
        textView_tue_time3.setText(time3);
        textView_wed_time1.setText(time1);
        textView_wed_time2.setText(time2);
        textView_wed_time3.setText(time3);
        textView_thu_time1.setText(time1);
        textView_thu_time2.setText(time2);
        textView_thu_time3.setText(time3);
        textView_fri_time1.setText(time1);
        textView_fri_time2.setText(time2);
        textView_fri_time3.setText(time3);
        textView_sat_time1.setText(time1);
        textView_sat_time2.setText(time2);
        textView_sat_time3.setText(time3);
    }

    private void getClearList() {
        list_sun.clear();
        list_mon.clear();
        list_tue.clear();
        list_wed.clear();
        list_thu.clear();
        list_fri.clear();
        list_sat.clear();
    }

    public void getWeekDays(String weekday) {
        ArrayList<String> arrayList_weekdays = new ArrayList<>();
        arrayList_weekdays.add("Sunday");
        arrayList_weekdays.add("Monday");
        arrayList_weekdays.add("Tuesday");
        arrayList_weekdays.add("Wednesday");
        arrayList_weekdays.add("Thursday");
        arrayList_weekdays.add("Friday");
        arrayList_weekdays.add("Saturday");

        if (String.valueOf(weekday.charAt(0)).equals("n")) {
            arrayList_weekdays.remove("Sunday");
        }
        if (String.valueOf(weekday.charAt(1)).equals("n")) {
            arrayList_weekdays.remove("Monday");
        }

        if (String.valueOf(weekday.charAt(2)).equals("n")) {
            arrayList_weekdays.remove("Tuesday");
        }

        if (String.valueOf(weekday.charAt(3)).equals("n")) {
            arrayList_weekdays.remove("Wednesday");
        }

        if (String.valueOf(weekday.charAt(4)).equals("n")) {
            arrayList_weekdays.remove("Thursday");
        }

        if (String.valueOf(weekday.charAt(5)).equals("n")) {
            arrayList_weekdays.remove("Friday");
        }

        if (String.valueOf(weekday.charAt(6)).equals("n")) {
            arrayList_weekdays.remove("Saturday");
        }

        ArrayAdapter arrayAdapter_weekdays = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, arrayList_weekdays);
        spinner_weekdays.setAdapter(arrayAdapter_weekdays);

    }

    public void getWeekDaysLayout(String weekday) {
        if (weekday.equals("nnnnnnn")) {
            layout_weekday.setVisibility(View.GONE);
            aSwitch_all_days.setChecked(false);
            isTimeSelected = false;
            list_sun.clear();
            list_mon.clear();
            list_tue.clear();
            list_wed.clear();
            list_thu.clear();
            list_fri.clear();
            list_sat.clear();
        } else {
            isTimeSelected = true;
            layout_weekday.setVisibility(View.VISIBLE);
            if (String.valueOf(weekday.charAt(0)).equals("n")) {
                linearLayout__sun.setVisibility(View.GONE);
                list_sun.clear();
            } else {
                linearLayout__sun.setVisibility(View.VISIBLE);
            }
            if (String.valueOf(weekday.charAt(1)).equals("n")) {
                linearLayout__mon.setVisibility(View.GONE);
                list_mon.clear();
            } else {
                linearLayout__mon.setVisibility(View.VISIBLE);
            }

            if (String.valueOf(weekday.charAt(2)).equals("n")) {
                linearLayout__tue.setVisibility(View.GONE);
                list_tue.clear();
            } else {
                linearLayout__tue.setVisibility(View.VISIBLE);
            }

            if (String.valueOf(weekday.charAt(3)).equals("n")) {
                linearLayout__wed.setVisibility(View.GONE);
                list_wed.clear();
            } else {
                linearLayout__wed.setVisibility(View.VISIBLE);
            }
            if (String.valueOf(weekday.charAt(4)).equals("n")) {
                linearLayout__thur.setVisibility(View.GONE);
                list_thu.clear();
            } else {
                linearLayout__thur.setVisibility(View.VISIBLE);
            }
            if (String.valueOf(weekday.charAt(5)).equals("n")) {
                linearLayout__fri.setVisibility(View.GONE);
                list_fri.clear();
            } else {
                linearLayout__fri.setVisibility(View.VISIBLE);
            }

            if (String.valueOf(weekday.charAt(6)).equals("n")) {
                linearLayout__sat.setVisibility(View.GONE);
                list_sat.clear();
            } else {
                linearLayout__sat.setVisibility(View.VISIBLE);
            }
        }
    }

    public void getCopyTimeAllDays(String weekday, String time1, String time2, String time3) {
        Log.d("getCopyTimeAllDays", "getCopyTimeAllDays: " + weekday + "," + time1 + "," + time2 + "," + time3);
        if (String.valueOf(weekday.charAt(0)).equals("y")) {
            list_sun.add(time1);
            if (!time2.equals("00:00 - 00:00")) {
                list_sun.add(time2);
            }
            if (!time3.equals("00:00 - 00:00")) {
                list_sun.add(time3);
            }
        }

        if (String.valueOf(weekday.charAt(1)).equals("y")) {
            list_mon.add(time1);
            if (!time2.equals("00:00 - 00:00")) {
                list_mon.add(time2);
            }
            if (!time3.equals("00:00 - 00:00")) {
                list_mon.add(time3);
            }
        }

        if (String.valueOf(weekday.charAt(2)).equals("y")) {
            list_tue.add(time1);
            if (!time2.equals("00:00 - 00:00")) {
                list_tue.add(time2);
            }
            if (!time3.equals("00:00 - 00:00")) {
                list_tue.add(time3);
            }
        }

        if (String.valueOf(weekday.charAt(3)).equals("y")) {
            list_wed.add(time1);
            if (!time2.equals("00:00 - 00:00")) {
                list_wed.add(time2);
            }
            if (!time3.equals("00:00 - 00:00")) {
                list_wed.add(time3);
            }
        }
        if (String.valueOf(weekday.charAt(4)).equals("y")) {
            list_thu.add(time1);
            if (!time2.equals("00:00 - 00:00")) {
                list_thu.add(time2);
            }
            if (!time3.equals("00:00 - 00:00")) {
                list_thu.add(time3);
            }
        }
        if (String.valueOf(weekday.charAt(5)).equals("y")) {
            list_fri.add(time1);
            if (!time2.equals("00:00 - 00:00")) {
                list_fri.add(time2);
            }
            if (!time3.equals("00:00 - 00:00")) {
                list_fri.add(time3);
            }
        }

        if (String.valueOf(weekday.charAt(6)).equals("y")) {
            list_sat.add(time1);
            if (!time2.equals("00:00 - 00:00")) {
                list_sat.add(time2);
            }
            if (!time3.equals("00:00 - 00:00")) {
                list_sat.add(time3);
            }
        }

    }

    void timepicker_dialog(final String val) {
        LayoutInflater factory = LayoutInflater.from(this);
        final View deleteDialogView = factory.inflate(R.layout.timepicker_layout, null);
        final AlertDialog pickerDialog = new AlertDialog.Builder(this).create();
        pickerDialog.setView(deleteDialogView);
        final TimePicker pickStartTime = (TimePicker) deleteDialogView.findViewById(R.id.StartTime);
        Button button = (Button) deleteDialogView.findViewById(R.id.button_picker_ok);
        TextView textView = (TextView) deleteDialogView.findViewById(R.id.text_alert_title);
        pickStartTime.setIs24HourView(true);
        pickStartTime.setCurrentHour(00);
        pickStartTime.setCurrentMinute(00);
        pickStartTime.setOnTimeChangedListener(mStartTimeChangedListener);

        if (val.equals("1")) {
            textView.setText("Select start time");
        } else if (val.equals("0")) {
            textView.setText("Select end time");
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hr = "", min = "";
                hour = pickStartTime.getCurrentHour();
                minute = pickStartTime.getCurrentMinute();

                if (String.valueOf(hour).length() == 1) {
                    hr = "0" + hour;
                } else {
                    hr = String.valueOf(hour);
                }
                if (String.valueOf(minute).length() == 1) {
                    min = "0" + minute;
                } else {
                    min = String.valueOf(minute);
                }

                if (val.equals("1")) {
                    text_start_time.setText(hr + ":" + min);
                } else if (val.equals("0")) {
                    text_end_time.setText(hr + ":" + min);
                }
                //  Toast.makeText(AddServiceActivity.this, hour+" hrs "+minute+" mins", Toast.LENGTH_SHORT).show();
                pickerDialog.dismiss();
            }
        });
        pickerDialog.show();

    }

    private TimePicker.OnTimeChangedListener mStartTimeChangedListener =
            new TimePicker.OnTimeChangedListener() {

                public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                    updateDisplay(view, hourOfDay, minute);
                }
            };

    private TimePicker.OnTimeChangedListener mNullTimeChangedListener =
            new TimePicker.OnTimeChangedListener() {

                public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {

                }
            };


    private void updateDisplay(TimePicker timePicker, int hourOfDay, int minute) {
        int nextMinute = 0;
        //int nextHour = 0;

        timePicker.setOnTimeChangedListener(mNullTimeChangedListener);

        if (minute >= 45 && minute <= 59)
            nextMinute = 45;
        else if (minute >= 30)
            nextMinute = 30;
        else if (minute >= 15)
            nextMinute = 15;
        else if (minute > 0)
            nextMinute = 0;
        else {
            nextMinute = 45;
        }

        if (minute - nextMinute == 1) {
            if (minute >= 45 && minute <= 59) {
                nextMinute = 00;
                hourOfDay = hourOfDay + 1;
            } else if (minute >= 30)
                nextMinute = 45;
            else if (minute >= 15)
                nextMinute = 30;
            else if (minute > 0)
                nextMinute = 15;
            else {
                nextMinute = 15;
            }
        }

        timePicker.setCurrentHour(hourOfDay);
        timePicker.setCurrentMinute(nextMinute);

        timePicker.setOnTimeChangedListener(mStartTimeChangedListener);

    }

    private boolean checktimings(String time, String endtime) {
        String pattern = "HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);

        try {
            Date date1 = sdf.parse(time);
            Date date2 = sdf.parse(endtime);

            if (date1.before(date2)) {
                return true;
            } else {

                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean checktimings2(String time, String endtime) {
        String pattern = "HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);

        try {
            Date date1 = sdf.parse(time);
            Date date2 = sdf.parse(endtime);

            if ((date1.before(date2)) || (date1.equals(date2))) {
                return true;
            } else {

                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void fetchServices(AppointmentEnquiryBody requestBody, final String flag) throws ApiException {
        Log.d("fetchServiceAddStaff--->", "fetchService: " + requestBody);
        OkHttpApiClient okHttpApiClient = new OkHttpApiClient(AddStaffActivity.this);
        MerchantApisApi webMerchantApisApi = new MerchantApisApi();
        webMerchantApisApi.setApiClient(okHttpApiClient.getApiClient());

        webMerchantApisApi.merchantAppointmentEnquiryAsync(requestBody, new ApiCallback<AppointmentEnquiryResponse>() {
            @Override
            public void onFailure(ApiException e, int statusCode, Map<String, List<String>> responseHeaders) {
                Log.d("fetchServiceAddStaff--->", "onFailure-" + e.getMessage());
                if (intermediateAlertDialog != null) {
                    intermediateAlertDialog.dismissAlertDialog();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new AlertDialogFailure(AddStaffActivity.this, getResources().getString(R.string.try_again), "OK", getResources().getString(R.string.server_error), "Failed") {
                            @Override
                            public void onButtonClick() {
                                /*startActivity(new Intent(AddStaffActivity.this, DashboardActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                                finish();
                                overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);*/
                                onBackPressed();
                            }
                        };
                    }
                });
            }

            @Override
            public void onSuccess(final AppointmentEnquiryResponse result, int statusCode, Map<String, List<String>> responseHeaders) {

                Log.d("fetchServiceAddStaff--->", "onSuccess-" + statusCode + "," + result);
                if (Integer.parseInt(result.getStatusCode()) == 200) {
                    if (intermediateAlertDialog != null) {
                        intermediateAlertDialog.dismissAlertDialog();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (!result.getServices().isEmpty() && result.getServices() != null) {
                                //layout_services.setVisibility(View.VISIBLE);
                                appointmentServices = result.getServices();

                                if (flag.equals("2")) {
                                    getServiMngLoadceUpdate(result.getServices(), mapServiceResourceBodyList_update);
                                } else {
                                    if (flag.equals("0")) {
                                        arrayList_map_service.clear();
                                        arrayList_map_service_name.clear();
                                        arrayList_map_load.clear();
                                    }

                                    LayoutInflater factory = LayoutInflater.from(AddStaffActivity.this);
                                    final View mapDialogView = factory.inflate(R.layout.alert_mapping, null);
                                    final AlertDialog mappingDialog = new AlertDialog.Builder(AddStaffActivity.this).create();
                                    //mappingDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
                                    //mappingDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                                    mappingDialog.setView(mapDialogView);
                                    mappingDialog.setCancelable(false);
                                    RecyclerView recyclerView = mapDialogView.findViewById(R.id.recyclerview_select_services);
                                    LinearLayoutManager li = new LinearLayoutManager(AddStaffActivity.this);
                                    recyclerView.setLayoutManager(li);
                                    mapServiceResourceRecyclerAdapter = new MapServiceResourceRecyclerAdapter(AddStaffActivity.this, flag, result.getServices(), mapServiceResourceBodyList_update);
                                    recyclerView.setAdapter(mapServiceResourceRecyclerAdapter);
                                    mapDialogView.findViewById(R.id.buttonOk).setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            //your business logic
                                            closeKeyboard();
                                            mappingDialog.dismiss();

                                            Log.d("MappedServices---", "adapter: " + mapServiceResourceRecyclerAdapter.getUpdateServiceName() + "," + mapServiceResourceRecyclerAdapter.getLoad());

                                            if (flag.equals("0")) {
                                                mng_service_name_list = mapServiceResourceRecyclerAdapter.getUpdateServiceName();
                                                mng_service_load_list = mapServiceResourceRecyclerAdapter.getLoad();
                                                mng_service_id_list = mapServiceResourceRecyclerAdapter.getService();
                                                Log.d("MappedServices---", "onClick: " + mng_service_name_list + "," + mng_service_load_list + "," + mng_service_id_list + "," + mng_check_pos_list);

                                                if (!mng_service_name_list.isEmpty() && !mng_service_load_list.isEmpty()) {
                                                    runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            layout_services.setVisibility(View.VISIBLE);
                                                            MappingServicesAdapter mappingServicesAdapter = new MappingServicesAdapter(AddStaffActivity.this, mng_service_name_list, mng_service_load_list);
                                                            recyclerView_services.setAdapter(mappingServicesAdapter);
                                                            mappingServicesAdapter.notifyDataSetChanged();
                                                        }
                                                    });

                                                } else {
                                                    layout_services.setVisibility(View.GONE);
                                                }
                                            } else if (flag.equals("1")) {
                                                mng_service_name_list = mapServiceResourceRecyclerAdapter.getUpdateServiceName();
                                                mng_service_load_list = mapServiceResourceRecyclerAdapter.getLoad();
                                                mng_service_id_list = mapServiceResourceRecyclerAdapter.getService();
                                                mng_check_pos_list = mapServiceResourceRecyclerAdapter.getCheckPosition();
                                                mng_load_update_list = mapServiceResourceRecyclerAdapter.getUpdateServiceLoad();
                                                Log.d("MappedServices---", "onClick: " + mng_service_name_list + "," + mng_load_update_list + "," + mng_service_load_list + "," + mng_service_id_list + "," + mng_check_pos_list);
                                                if (!mng_service_name_list.isEmpty() && !mng_service_load_list.isEmpty()) {
                                                    runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            Log.d("update---", "run: update :" + mng_service_name_list + "," + mng_service_load_list);
                                                            layout_services.setVisibility(View.VISIBLE);
                                                            MappingServicesAdapter mappingServicesAdapter = new MappingServicesAdapter(AddStaffActivity.this, mng_service_name_list, mng_load_update_list);
                                                            recyclerView_services.setAdapter(mappingServicesAdapter);
                                                            mappingServicesAdapter.notifyDataSetChanged();
                                                        }
                                                    });

                                                } else {
                                                    layout_services.setVisibility(View.GONE);
                                                }
                                            }
                                        }

                                    });
                                    mapDialogView.findViewById(R.id.button_cancel).setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            //your business logic
                                            closeKeyboard();
                                            mappingDialog.dismiss();

                                        }
                                    });
                                    mappingDialog.show();
                                }
                               /* MapServiceResourceRecyclerAdapter mapServiceResourceRecyclerAdapter2 = new MapServiceResourceRecyclerAdapter(AddStaffActivity.this, flag, result.getServices(), mapServiceResourceBodyList_update);
                                recyclerView_services.setAdapter(mapServiceResourceRecyclerAdapter2);*/
                            } else {
                                // layout_services.setVisibility(View.GONE);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        new AlertDialogFailure(AddStaffActivity.this, "Set up service before adding staff", "OK", "", "Warning") {
                                            @Override
                                            public void onButtonClick() {
                                                Intent in = new Intent(AddStaffActivity.this, DashboardActivity.class);
                                                in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                startActivity(in);
                                                finish();
                                                overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);
                                            }
                                        };
                                    }
                                });

                            }


                        }
                    });


                } else if (Integer.parseInt(result.getStatusCode()) == 404) {
                    if (intermediateAlertDialog != null) {
                        intermediateAlertDialog.dismissAlertDialog();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new AlertDialogFailure(AddStaffActivity.this, "Set up service before adding staff", "OK", "", "Warning") {
                                @Override
                                public void onButtonClick() {
                                    Intent in = new Intent(AddStaffActivity.this, DashboardActivity.class);
                                    in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(in);
                                    finish();
                                    overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);
                                }
                            };
                        }
                    });

                } else if (Integer.parseInt(result.getStatusCode()) == 401) {
                    if (intermediateAlertDialog != null) {
                        intermediateAlertDialog.dismissAlertDialog();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new CAllLoginAPI() {
                                @Override
                                public void onButtonClick() {

                                    if (!TextUtils.isEmpty(serviceFlag) && serviceFlag.equals("1")) {
                                        fetchMerServices("1");
                                    } else if (!TextUtils.isEmpty(serviceFlag) && serviceFlag.equals("0")) {
                                        fetchMerServices("0");
                                    } else {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                new AlertDialogFailure(AddStaffActivity.this, getResources().getString(R.string.try_again), "OK", getResources().getString(R.string.went_wrong), "Failed") {
                                                    @Override
                                                    public void onButtonClick() {
                                                        onBackPressed();
                                                    }
                                                };
                                            }
                                        });
                                    }
                                }
                            }.callLoginAPI(AddStaffActivity.this);
                        }
                    });

                } else if (Integer.parseInt(result.getStatusCode()) == 400) {
                    if (intermediateAlertDialog != null) {
                        intermediateAlertDialog.dismissAlertDialog();
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new AlertDialogFailure(AddStaffActivity.this, getResources().getString(R.string.try_again), "OK", "Invalid data", "Failed") {
                                @Override
                                public void onButtonClick() {
                                    onBackPressed();
                                }
                            };
                        }
                    });

                } else {
                    if (intermediateAlertDialog != null) {
                        intermediateAlertDialog.dismissAlertDialog();
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new AlertDialogFailure(AddStaffActivity.this, getResources().getString(R.string.try_again), "OK", getResources().getString(R.string.went_wrong), "Failed") {
                                @Override
                                public void onButtonClick() {
                                    onBackPressed();
                                }
                            };
                        }
                    });
                }
            }

            @Override
            public void onUploadProgress(long bytesWritten, long contentLength, boolean done) {

            }

            @Override
            public void onDownloadProgress(long bytesRead, long contentLength, boolean done) {

            }
        });

    }

    private void resourceUpdate() {
        closeKeyboard();

        String name = et_staff_name.getText().toString();
        String mob = et_staff_mob.getText().toString();

        List<MapServiceResourceBody> mapServiceResourceBodyList = new ArrayList<>();
        MapServiceResourceBody mapServiceResourceBody = null;
        for (int j = 0; j < appointmentServices.size(); j++) {
      /*      mapServiceResourceBody = new MapServiceResourceBody();
            mapServiceResourceBody.setSerId(appointmentServices.get(j).getSerId());
            mapServiceResourceBody.setDelete(!positionArray.get(j));
            mapServiceResourceBody.setManageableLoad(loadArray.get(j));
            mapServiceResourceBodyList.add(mapServiceResourceBody);*/

            mapServiceResourceBody = new MapServiceResourceBody();
            mapServiceResourceBody.setSerId(appointmentServices.get(j).getSerId());
            mapServiceResourceBody.setDelete(!mng_check_pos_list.get(j));
            mapServiceResourceBody.setManageableLoad(mng_service_load_list.get(j));
            mapServiceResourceBodyList.add(mapServiceResourceBody);

            Log.d("MappingUpdate---", "resourceUpdate: " + appointmentServices.get(j).getSerId() + "," + !mng_check_pos_list.get(j) + "," + mng_service_load_list.get(j));
        }
        AppointmentResources appointmentResources = new AppointmentResources();
        appointmentResources.setResId(res_id);
        appointmentResources.setResName(name);
        appointmentResources.setMobile(mob);
        appointmentResources.setMerId(sharedpreferences_sessionToken.getString(LoginActivity.MERID, ""));
        appointmentResources.setIsActive("");
        appointmentResources.setManageableLoad("");
        appointmentResources.setSameBussTime(isSameBizHrs);
        appointmentResources.serResMaps(mapServiceResourceBodyList);

        ApptTransactionBody transactionBody = new ApptTransactionBody();
        transactionBody.setReqType(Constants.RESOURCE_UPDATE);
        transactionBody.setResId(res_id);
        transactionBody.setMerId(sharedpreferences_sessionToken.getString(LoginActivity.MERID, ""));
        transactionBody.setOutletId(sharedpreferences_sessionToken.getString(LoginActivity.OUTLETID, ""));
        transactionBody.setDeviceId(sharedpreferences_sessionToken.getString(LoginActivity.DEVICEID, ""));
        transactionBody.setSessionToken(sharedpreferences_sessionToken.getString(LoginActivity.SESSIONTOKEN, ""));

        transactionBody.setResource(appointmentResources);

        Log.d("mapServiceRes--->", "onClick: " + mapServiceResourceBodyList + "," + arrayList_map_service);
        try {
            boolean isConn = ConnectivityReceiver.isConnected();
            if (isConn) {
                intermediateAlertDialog = new IntermediateAlertDialog(AddStaffActivity.this);
                apptUpdateStaff(transactionBody);
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new AlertDialogFailure(AddStaffActivity.this, getResources().getString(R.string.no_internet_sub_title), "OK", getResources().getString(R.string.no_internet_title), getResources().getString(R.string.no_internet_heading)) {
                            @Override
                            public void onButtonClick() {

                            }
                        };
                    }
                });
            }
        } catch (ApiException e) {
            e.printStackTrace();
        }

    }

    private void apptUpdateStaff(ApptTransactionBody requestBody) throws ApiException {
        Log.d("apptUpdateStaff---", "createStaff: " + requestBody);

        OkHttpApiClient okHttpApiClient = new OkHttpApiClient(AddStaffActivity.this);
        MerchantApisApi webMerchantApisApi = new MerchantApisApi();
        webMerchantApisApi.setApiClient(okHttpApiClient.getApiClient());

        webMerchantApisApi.merchantAppointmentTransactionAsync(requestBody, new ApiCallback<ApptTransactionResponse>() {
            @Override
            public void onFailure(ApiException e, int statusCode, Map<String, List<String>> responseHeaders) {
                Log.d("apptUpdateStaff--->", "onFailure-" + e.getMessage());
                if (intermediateAlertDialog != null) {
                    intermediateAlertDialog.dismissAlertDialog();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new AlertDialogFailure(AddStaffActivity.this, getResources().getString(R.string.try_again), "OK", getResources().getString(R.string.server_error), "Failed") {
                            @Override
                            public void onButtonClick() {
                           /*     startActivity(new Intent(AddStaffActivity.this, DashboardActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                                finish();
                                overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);*/
                                onBackPressed();

                            }
                        };
                    }
                });
            }

            @Override
            public void onSuccess(ApptTransactionResponse result, int statusCode, Map<String, List<String>> responseHeaders) {
                Log.d("apptUpdateStaff--->", "onSuccess-" + result.getStatusCode() + "," + result.getStatusMessage());
                if (Integer.parseInt(result.getStatusCode()) == 200) {
                    if (intermediateAlertDialog != null) {
                        intermediateAlertDialog.dismissAlertDialog();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new AlertDialogFailure(AddStaffActivity.this, "Staff updated successfully!", "OK", "", getResources().getString(R.string.success)) {
                                @Override
                                public void onButtonClick() {
                                    startActivity(new Intent(AddStaffActivity.this, DashboardActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                                    finish();
                                    overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);
                                }
                            };
                        }
                    });

                } else if (Integer.parseInt(result.getStatusCode()) == 404) {
                    if (intermediateAlertDialog != null) {
                        intermediateAlertDialog.dismissAlertDialog();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new AlertDialogFailure(AddStaffActivity.this, "Staff does not exist!", "OK", "", "Failed") {
                                @Override
                                public void onButtonClick() {
                                /*    startActivity(new Intent(AddStaffActivity.this, DashboardActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                                    finish();
                                    overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);*/

                                    onBackPressed();
                                }
                            };
                        }
                    });

                } else if (Integer.parseInt(result.getStatusCode()) == 409) {
                    if (intermediateAlertDialog != null) {
                        intermediateAlertDialog.dismissAlertDialog();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new AlertDialogFailure(AddStaffActivity.this, "Staff updation failed!", "OK", "", "Failed") {
                                @Override
                                public void onButtonClick() {
                                /*    startActivity(new Intent(AddStaffActivity.this, DashboardActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                                    finish();
                                    overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);*/

                                    onBackPressed();
                                }
                            };
                        }
                    });

                } else if (Integer.parseInt(result.getStatusCode()) == 412) {
                    if (intermediateAlertDialog != null) {
                        intermediateAlertDialog.dismissAlertDialog();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new AlertDialogFailure(AddStaffActivity.this, "Service mapping failed!", "OK", "", "Failed") {
                                @Override
                                public void onButtonClick() {
                                /*    startActivity(new Intent(AddStaffActivity.this, DashboardActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                                    finish();
                                    overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);*/

                                    onBackPressed();
                                }
                            };
                        }
                    });

                } else if (Integer.parseInt(result.getStatusCode()) == 503) {
                    if (intermediateAlertDialog != null) {
                        intermediateAlertDialog.dismissAlertDialog();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new AlertDialogFailure(AddStaffActivity.this, "Staff has appointment in future dates!", "OK", "Staff updation failed", "Failed") {
                                @Override
                                public void onButtonClick() {
                                /*    startActivity(new Intent(AddStaffActivity.this, DashboardActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                                    finish();
                                    overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);*/

                                    onBackPressed();
                                }
                            };
                        }
                    });

                } else if (Integer.parseInt(result.getStatusCode()) == 401) {
                    if (intermediateAlertDialog != null) {
                        intermediateAlertDialog.dismissAlertDialog();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new CAllLoginAPI() {
                                @Override
                                public void onButtonClick() {
                                    resourceUpdate();
                                }
                            }.callLoginAPI(AddStaffActivity.this);
                        }
                    });

                } else {
                    if (intermediateAlertDialog != null) {
                        intermediateAlertDialog.dismissAlertDialog();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new AlertDialogFailure(AddStaffActivity.this, getResources().getString(R.string.try_again), "OK", getResources().getString(R.string.went_wrong), "Failed") {
                                @Override
                                public void onButtonClick() {
                                  /*  startActivity(new Intent(AddStaffActivity.this, DashboardActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                                    finish();
                                    overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);*/

                                    onBackPressed();
                                }
                            };
                        }
                    });
                }
            }

            @Override
            public void onUploadProgress(long bytesWritten, long contentLength, boolean done) {

            }

            @Override
            public void onDownloadProgress(long bytesRead, long contentLength, boolean done) {

            }
        });
    }

    private void fetchMerServices(String flag) {
        AppointmentEnquiryBody enquiryBody = new AppointmentEnquiryBody();
        enquiryBody.setReqType(Constants.SERVICE_MERCHANT);
        enquiryBody.setMerId(sharedpreferences_sessionToken.getString(LoginActivity.MERID, ""));
        enquiryBody.setOutletId(sharedpreferences_sessionToken.getString(LoginActivity.OUTLETID, ""));
        enquiryBody.callerType("m");
        enquiryBody.setDeviceId(sharedpreferences_sessionToken.getString(LoginActivity.DEVICEID, ""));
        enquiryBody.setSessionToken(sharedpreferences_sessionToken.getString(LoginActivity.SESSIONTOKEN, ""));
        boolean isConn = ConnectivityReceiver.isConnected();
        if (isConn) {
            try {
               // intermediateAlertDialog = new IntermediateAlertDialog(AddStaffActivity.this);
                fetchServices(enquiryBody, flag);
            } catch (ApiException e) {
                e.printStackTrace();
            }
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    new AlertDialogFailure(AddStaffActivity.this, getResources().getString(R.string.no_internet_sub_title), "OK", getResources().getString(R.string.no_internet_title), getResources().getString(R.string.no_internet_Heading)) {
                        @Override
                        public void onButtonClick() {
                            startActivity(new Intent(AddStaffActivity.this, AddStaffActivity.class));
                            finish();
                            overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);
                        }
                    };
                }
            });
        }
    }

    private void createMerStaff() {

        String name = et_staff_name.getText().toString();
        String mob = et_staff_mob.getText().toString();

        closeKeyboard();
        AvailDay availDay_sun = new AvailDay();
        AvailDay availDay_mon = new AvailDay();
        AvailDay availDay_tue = new AvailDay();
        AvailDay availDay_wed = new AvailDay();
        AvailDay availDay_thu = new AvailDay();
        AvailDay availDay_fri = new AvailDay();
        AvailDay availDay_sat = new AvailDay();

        List<TimeData> timeDataList_sun = new ArrayList<>();
        List<TimeData> timeDataList_mon = new ArrayList<>();
        List<TimeData> timeDataList_tue = new ArrayList<>();
        List<TimeData> timeDataList_wed = new ArrayList<>();
        List<TimeData> timeDataList_thu = new ArrayList<>();
        List<TimeData> timeDataList_fri = new ArrayList<>();
        List<TimeData> timeDataList_sat = new ArrayList<>();


        TimeData timeData_sun1 = new TimeData();
        TimeData timeData_sun2 = new TimeData();
        TimeData timeData_sun3 = new TimeData();

        TimeData timeData_mon1 = new TimeData();
        TimeData timeData_mon2 = new TimeData();
        TimeData timeData_mon3 = new TimeData();

        TimeData timeData_tue1 = new TimeData();
        TimeData timeData_tue2 = new TimeData();
        TimeData timeData_tue3 = new TimeData();

        TimeData timeData_wed1 = new TimeData();
        TimeData timeData_wed2 = new TimeData();
        TimeData timeData_wed3 = new TimeData();

        TimeData timeData_thu1 = new TimeData();
        TimeData timeData_thu2 = new TimeData();
        TimeData timeData_thu3 = new TimeData();

        TimeData timeData_fri1 = new TimeData();
        TimeData timeData_fri2 = new TimeData();
        TimeData timeData_fri3 = new TimeData();

        TimeData timeData_sat1 = new TimeData();
        TimeData timeData_sat2 = new TimeData();
        TimeData timeData_sat3 = new TimeData();

        //Sunday
        if (!list_sun.isEmpty()) {
            Log.d("sunday_list--->", "submit: " + list_sun);
            if (list_sun.size() > 0) {
                String[] strs = list_sun.get(0).split(" - ");
                String s_tm = strs[0];
                String e_tm = strs[1];

                timeData_sun1.setAvailId("");
                timeData_sun1.setStartTime(s_tm + ":00");
                timeData_sun1.setEndTime(e_tm + ":00");
                timeDataList_sun.add(timeData_sun1);
                Log.d("Sun--->", "onClick: >0" + " 1");
            }

            if (list_sun.size() > 1) {
                String[] strs = list_sun.get(1).split(" - ");
                String s_tm = strs[0];
                String e_tm = strs[1];

                timeData_sun2.setAvailId("");
                timeData_sun2.setStartTime(s_tm + ":00");
                timeData_sun2.setEndTime(e_tm + ":00");
                timeDataList_sun.add(timeData_sun2);
                Log.d("Sun--->", "onClick: > 1" + " 2");
            }

            if (list_sun.size() > 2) {
                String[] strs = list_sun.get(2).split(" - ");
                String s_tm = strs[0];
                String e_tm = strs[1];

                timeData_sun3.setAvailId("");
                timeData_sun3.setStartTime(s_tm + ":00");
                timeData_sun3.setEndTime(e_tm + ":00");
                timeDataList_sun.add(timeData_sun3);
                Log.d("Sun--->", "onClick: ==3" + " 3");
            }

            availDay_sun.setDay("1");
            availDay_sun.setTiming(timeDataList_sun);
        } else {
            timeData_sun1.setAvailId("");
            timeData_sun1.setStartTime("00:00:00");
            timeData_sun1.setEndTime("00:00:00");

            timeDataList_sun.add(timeData_sun1);

            availDay_sun.setDay("1");
            availDay_sun.setTiming(timeDataList_sun);
        }

        //Monday
        if (!list_mon.isEmpty()) {
            if (list_mon.size() > 0) {
                String[] strs = list_mon.get(0).split(" - ");
                String s_tm = strs[0];
                String e_tm = strs[1];

                timeData_mon1.setAvailId("");
                timeData_mon1.setStartTime(s_tm + ":00");
                timeData_mon1.setEndTime(e_tm + ":00");
                timeDataList_mon.add(timeData_mon1);
                Log.d("Mon--->", "onClick: >0" + " 1");
            }

            if (list_mon.size() > 1) {
                String[] strs = list_mon.get(1).split(" - ");
                String s_tm = strs[0];
                String e_tm = strs[1];

                timeData_mon2.setAvailId("");
                timeData_mon2.setStartTime(s_tm + ":00");
                timeData_mon2.setEndTime(e_tm + ":00");
                timeDataList_mon.add(timeData_mon2);
                Log.d("Mon--->", "onClick: > 1" + " 2");
            }

            if (list_mon.size() > 2) {
                String[] strs = list_mon.get(2).split(" - ");
                String s_tm = strs[0];
                String e_tm = strs[1];

                timeData_mon3.setAvailId("");
                timeData_mon3.setStartTime(s_tm + ":00");
                timeData_mon3.setEndTime(e_tm + ":00");
                timeDataList_mon.add(timeData_mon3);
                Log.d("Mon--->", "onClick: ==3" + " 3");
            }

            availDay_mon.setDay("2");
            availDay_mon.setTiming(timeDataList_mon);
        } else {
            timeData_mon1.setAvailId("");
            timeData_mon1.setStartTime("00:00:00");
            timeData_mon1.setEndTime("00:00:00");

            timeDataList_mon.add(timeData_mon1);
            availDay_mon.setDay("2");
            availDay_mon.setTiming(timeDataList_mon);
        }


        //Tuesday
        if (!list_tue.isEmpty()) {
            if (list_tue.size() > 0) {
                String[] strs = list_tue.get(0).split(" - ");
                String s_tm = strs[0];
                String e_tm = strs[1];

                timeData_tue1.setAvailId("");
                timeData_tue1.setStartTime(s_tm + ":00");
                timeData_tue1.setEndTime(e_tm + ":00");
                timeDataList_tue.add(timeData_tue1);
                Log.d("Tue--->", "onClick: >0" + " 1");
            }

            if (list_tue.size() > 1) {
                String[] strs = list_tue.get(1).split(" - ");
                String s_tm = strs[0];
                String e_tm = strs[1];

                timeData_tue2.setAvailId("");
                timeData_tue2.setStartTime(s_tm + ":00");
                timeData_tue2.setEndTime(e_tm + ":00");
                timeDataList_tue.add(timeData_tue2);
                Log.d("Tue--->", "onClick: > 1" + " 2");
            }

            if (list_tue.size() > 2) {
                String[] strs = list_tue.get(2).split(" - ");
                String s_tm = strs[0];
                String e_tm = strs[1];

                timeData_tue3.setAvailId("");
                timeData_tue3.setStartTime(s_tm + ":00");
                timeData_tue3.setEndTime(e_tm + ":00");
                timeDataList_tue.add(timeData_tue3);
                Log.d("Tue--->", "onClick: ==3" + " 3");
            }

            availDay_tue.setDay("3");
            availDay_tue.setTiming(timeDataList_tue);
        } else {
            timeData_tue1.setAvailId("");
            timeData_tue1.setStartTime("00:00:00");
            timeData_tue1.setEndTime("00:00:00");

            timeDataList_tue.add(timeData_tue1);

            availDay_tue.setDay("3");
            availDay_tue.setTiming(timeDataList_tue);
        }


        //Wednesday
        if (!list_wed.isEmpty()) {
            if (list_wed.size() > 0) {
                String[] strs = list_wed.get(0).split(" - ");
                String s_tm = strs[0];
                String e_tm = strs[1];

                timeData_wed1.setAvailId("");
                timeData_wed1.setStartTime(s_tm + ":00");
                timeData_wed1.setEndTime(e_tm + ":00");
                timeDataList_wed.add(timeData_wed1);
                Log.d("Wed--->", "onClick: >0" + " 1");
            }

            if (list_wed.size() > 1) {
                String[] strs = list_wed.get(1).split(" - ");
                String s_tm = strs[0];
                String e_tm = strs[1];

                timeData_wed2.setAvailId("");
                timeData_wed2.setStartTime(s_tm + ":00");
                timeData_wed2.setEndTime(e_tm + ":00");
                timeDataList_wed.add(timeData_wed2);
                Log.d("Wed--->", "onClick: > 1" + " 2");
            }

            if (list_wed.size() > 2) {
                String[] strs = list_wed.get(2).split(" - ");
                String s_tm = strs[0];
                String e_tm = strs[1];

                timeData_wed3.setAvailId("");
                timeData_wed3.setStartTime(s_tm + ":00");
                timeData_wed3.setEndTime(e_tm + ":00");
                timeDataList_wed.add(timeData_wed3);
                Log.d("Wed--->", "onClick: ==3" + " 3");
            }

            availDay_wed.setDay("4");
            availDay_wed.setTiming(timeDataList_wed);
        } else {
            timeData_wed1.setAvailId("");
            timeData_wed1.setStartTime("00:00:00");
            timeData_wed1.setEndTime("00:00:00");

            timeDataList_wed.add(timeData_wed1);

            availDay_wed.setDay("4");
            availDay_wed.setTiming(timeDataList_wed);
        }


        //Thursday
        if (!list_thu.isEmpty()) {
            if (list_thu.size() > 0) {
                String[] strs = list_thu.get(0).split(" - ");
                String s_tm = strs[0];
                String e_tm = strs[1];

                timeData_thu1.setAvailId("");
                timeData_thu1.setStartTime(s_tm + ":00");
                timeData_thu1.setEndTime(e_tm + ":00");
                timeDataList_thu.add(timeData_thu1);
                Log.d("Thu--->", "onClick: >0" + " 1");
            }

            if (list_thu.size() > 1) {
                String[] strs = list_thu.get(1).split(" - ");
                String s_tm = strs[0];
                String e_tm = strs[1];

                timeData_thu2.setAvailId("");
                timeData_thu2.setStartTime(s_tm + ":00");
                timeData_thu2.setEndTime(e_tm + ":00");
                timeDataList_thu.add(timeData_thu2);
                Log.d("Thu--->", "onClick: > 1" + " 2");
            }

            if (list_thu.size() > 2) {
                String[] strs = list_thu.get(2).split(" - ");
                String s_tm = strs[0];
                String e_tm = strs[1];

                timeData_thu3.setAvailId("");
                timeData_thu3.setStartTime(s_tm + ":00");
                timeData_thu3.setEndTime(e_tm + ":00");
                timeDataList_thu.add(timeData_thu3);
                Log.d("Thu--->", "onClick: ==3" + " 3");
            }

            availDay_thu.setDay("5");
            availDay_thu.setTiming(timeDataList_thu);
        } else {
            timeData_thu1.setAvailId("");
            timeData_thu1.setStartTime("00:00:00");
            timeData_thu1.setEndTime("00:00:00");

            timeDataList_thu.add(timeData_thu1);

            availDay_thu.setDay("5");
            availDay_thu.setTiming(timeDataList_thu);
        }


        //Friday
        if (!list_fri.isEmpty()) {
            if (list_fri.size() > 0) {
                String[] strs = list_fri.get(0).split(" - ");
                String s_tm = strs[0];
                String e_tm = strs[1];

                timeData_fri1.setAvailId("");
                timeData_fri1.setStartTime(s_tm + ":00");
                timeData_fri1.setEndTime(e_tm + ":00");
                timeDataList_fri.add(timeData_fri1);
                Log.d("Fri--->", "onClick: >0" + " 1");
            }

            if (list_fri.size() > 1) {
                String[] strs = list_fri.get(1).split(" - ");
                String s_tm = strs[0];
                String e_tm = strs[1];

                timeData_fri2.setAvailId("");
                timeData_fri2.setStartTime(s_tm + ":00");
                timeData_fri2.setEndTime(e_tm + ":00");
                timeDataList_fri.add(timeData_fri2);
                Log.d("Fri--->", "onClick: > 1" + " 2");
            }

            if (list_fri.size() > 2) {
                String[] strs = list_fri.get(2).split(" - ");
                String s_tm = strs[0];
                String e_tm = strs[1];

                timeData_fri3.setAvailId("");
                timeData_fri3.setStartTime(s_tm + ":00");
                timeData_fri3.setEndTime(e_tm + ":00");
                timeDataList_fri.add(timeData_fri3);
                Log.d("Fri--->", "onClick: ==3" + " 3");
            }


            availDay_fri.setDay("6");
            availDay_fri.setTiming(timeDataList_fri);
        } else {
            timeData_fri1.setAvailId("");
            timeData_fri1.setStartTime("00:00:00");
            timeData_fri1.setEndTime("00:00:00");

            timeDataList_fri.add(timeData_fri1);

            availDay_fri.setDay("6");
            availDay_fri.setTiming(timeDataList_fri);
        }


        //Saturday
        if (!list_sat.isEmpty()) {
            if (list_sat.size() > 0) {
                String[] strs = list_sat.get(0).split(" - ");
                String s_tm = strs[0];
                String e_tm = strs[1];

                timeData_sat1.setAvailId("");
                timeData_sat1.setStartTime(s_tm + ":00");
                timeData_sat1.setEndTime(e_tm + ":00");
                timeDataList_sat.add(timeData_sat1);
                Log.d("Sat--->", "onClick: >0" + " 1");
            }

            if (list_sat.size() > 1) {
                String[] strs = list_sat.get(1).split(" - ");
                String s_tm = strs[0];
                String e_tm = strs[1];

                timeData_sat2.setAvailId("");
                timeData_sat2.setStartTime(s_tm + ":00");
                timeData_sat2.setEndTime(e_tm + ":00");
                timeDataList_sat.add(timeData_sat2);
                Log.d("Sat--->", "onClick: > 1" + " 2");
            }

            if (list_sat.size() > 2) {
                String[] strs = list_sat.get(2).split(" - ");
                String s_tm = strs[0];
                String e_tm = strs[1];

                timeData_sat3.setAvailId("");
                timeData_sat3.setStartTime(s_tm + ":00");
                timeData_sat3.setEndTime(e_tm + ":00");
                timeDataList_sat.add(timeData_sat3);
                Log.d("Sat--->", "onClick: ==3" + " 3");
            }

            availDay_sat.setDay("7");
            availDay_sat.setTiming(timeDataList_sat);
        } else {
            timeData_sat1.setAvailId("");
            timeData_sat1.setStartTime("00:00:00");
            timeData_sat1.setEndTime("00:00:00");

            timeDataList_sat.add(timeData_sat1);

            availDay_sat.setDay("7");
            availDay_sat.setTiming(timeDataList_sat);
        }


        //Final avail list
        List<AvailDay> availDayList = new ArrayList<>();
        availDayList.add(availDay_sun);
        availDayList.add(availDay_mon);
        availDayList.add(availDay_tue);
        availDayList.add(availDay_wed);
        availDayList.add(availDay_thu);
        availDayList.add(availDay_fri);
        availDayList.add(availDay_sat);

        List<MapServiceResourceBody> mapServiceResourceBodyList = new ArrayList<>();
        MapServiceResourceBody mapServiceResourceBody = null;
        for (int j = 0; j < mng_service_id_list.size(); j++) {
 /*           mapServiceResourceBody = new MapServiceResourceBody();
            mapServiceResourceBody.setSerId(arrayList_map_service.get(j));
            mapServiceResourceBody.setDelete(false);
            mapServiceResourceBody.setManageableLoad(arrayList_map_load.get(j));
            mapServiceResourceBodyList.add(mapServiceResourceBody);*/

            mapServiceResourceBody = new MapServiceResourceBody();
            mapServiceResourceBody.setSerId(mng_service_id_list.get(j));
            mapServiceResourceBody.setDelete(false);
            mapServiceResourceBody.setManageableLoad(mng_service_load_list.get(j));
            mapServiceResourceBodyList.add(mapServiceResourceBody);

            Log.d("MapCreate---", "createMerStaff: " + mng_service_id_list.get(j) + "," + mng_service_load_list.get(j));
        }

        AppointmentResources appointmentResources = new AppointmentResources();
        appointmentResources.setResName(name);
        appointmentResources.setMobile(mob);
        appointmentResources.setMerId(sharedpreferences_sessionToken.getString(LoginActivity.MERID, ""));
        appointmentResources.setIsActive("");
        appointmentResources.setResId("");
        appointmentResources.setManageableLoad("");
        appointmentResources.setSameBussTime(isSameBizHrs);
        appointmentResources.serResMaps(mapServiceResourceBodyList);
        appointmentResources.availDays(availDayList);

        ApptTransactionBody transactionBody = new ApptTransactionBody();
        transactionBody.setReqType(Constants.RESOURCE_CREATE);
        transactionBody.setMerId(sharedpreferences_sessionToken.getString(LoginActivity.MERID, ""));
        transactionBody.setOutletId(sharedpreferences_sessionToken.getString(LoginActivity.OUTLETID, ""));
        transactionBody.setDeviceId(sharedpreferences_sessionToken.getString(LoginActivity.DEVICEID, ""));
        transactionBody.setSessionToken(sharedpreferences_sessionToken.getString(LoginActivity.SESSIONTOKEN, ""));

        transactionBody.setResource(appointmentResources);
        Log.d("mapServiceRes--->", "onClick: " + mapServiceResourceBodyList + "," + arrayList_map_service);
        try {
            boolean isConn = ConnectivityReceiver.isConnected();
            if (isConn) {
                intermediateAlertDialog = new IntermediateAlertDialog(AddStaffActivity.this);
                apptCreateStaff(transactionBody);
            } else {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new AlertDialogFailure(AddStaffActivity.this, getResources().getString(R.string.no_internet_sub_title), "OK", getResources().getString(R.string.no_internet_title), getResources().getString(R.string.no_internet_Heading)) {
                            @Override
                            public void onButtonClick() {
                                createMerStaff();
                            }
                        };
                    }
                });
            }
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (intermediateAlertDialog != null) {
            intermediateAlertDialog.dismissAlertDialog();
            intermediateAlertDialog = null;
        }
    }

    @Override
    public void mappedServicesList(String flag, String value, String load, String ser_name) {
        if (flag.equals("1")) {
            arrayList_map_service.add(value);
            arrayList_map_load.add(load);
            arrayList_map_service_name.add(ser_name);
            Log.d("List_map_ser--->", "arrayList_map_service: " + arrayList_map_service + "," + arrayList_map_load + "," + arrayList_map_service_name);
        } else if (flag.equals("0")) {
            arrayList_map_service.remove(value);
            arrayList_map_load.remove(load);
            arrayList_map_service_name.remove(ser_name);
            Log.d("List_map_ser--->", "arrayList_map_service: " + arrayList_map_service + "," + arrayList_map_load + "," + arrayList_map_service_name);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.finish_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_finish) {
            callAPI();
        }
        return super.onOptionsItemSelected(item);
    }

    private void closeKeyboard() {
        try {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    private void getServiMngLoadceUpdate(List<AppointmentService> appointmentServiceArrayList, List<MapServiceResourceBody> mapServiceResourceBodies) {

        ArrayList<String> ser_list = new ArrayList<>();
        ArrayList<Integer> pos_list = new ArrayList<>();
        if (!appointmentServiceArrayList.isEmpty() && appointmentServiceArrayList != null && !mapServiceResourceBodies.isEmpty() && mapServiceResourceBodies != null) {
            for (int i = 0; i < appointmentServiceArrayList.size(); i++) {
                //Service
                mng_check_pos_list.add(false);
                ser_list.add(appointmentServiceArrayList.get(i).getSerId());
            }

            for (int i = 0; i < mapServiceResourceBodies.size(); i++) {
                mng_service_load_list.add("0");
            }

            Log.d("load_list--->", "Pos :" + mng_service_load_list);
            int add_count = ser_list.size() - mapServiceResourceBodies.size();
            for (int i = 0; i < add_count; i++) {
                MapServiceResourceBody mapServiceResourceBody = new MapServiceResourceBody();
                mapServiceResourceBody.setSerId("0");
                mapServiceResourceBody.setManageableLoad("0");
                mapServiceResourceBodies.add(mapServiceResourceBody);
                mng_service_load_list.add("0");

            }
            Log.d("apptSerArrayList--->", "" + appointmentServiceArrayList.size() + "," + ser_list + "," + mapServiceResourceBodies.size());
            for (int i = 0; i < ser_list.size(); i++) {
                if (ser_list.contains(mapServiceResourceBodies.get(i).getSerId())) {
                    int pos = ser_list.indexOf(mapServiceResourceBodies.get(i).getSerId());
                    mng_check_pos_list.set(pos, true);
                    mng_service_load_list.set(pos, mapServiceResourceBodies.get(i).getManageableLoad());
                    pos_list.add(pos);
                    mng_load_update_list.add(mapServiceResourceBodies.get(i).getManageableLoad());
                    Log.d("pos_list--->", "" + pos);

                }
            }

            Log.d("pos_list--->", "" + pos_list);
            for (int g = 0; g < pos_list.size(); g++) {
                mng_service_name_list.add(appointmentServiceArrayList.get(pos_list.get(g)).getSerName());
                Log.d("pos_loadData--->", "" + mng_service_name_list + "," + mng_load_update_list);
            }

            Log.d("positionArrayServer--->", "" + mng_service_name_list + "," + mng_load_update_list + "," + mng_check_pos_list + "," + mng_service_load_list);
            if (!mng_service_name_list.isEmpty() && !mng_load_update_list.isEmpty()) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        layout_services.setVisibility(View.VISIBLE);
                        MappingServicesAdapter mappingServicesAdapter = new MappingServicesAdapter(AddStaffActivity.this, mng_service_name_list, mng_load_update_list);
                        recyclerView_services.setAdapter(mappingServicesAdapter);
                        mappingServicesAdapter.notifyDataSetChanged();
                    }
                });

            } else {
                layout_services.setVisibility(View.GONE);
            }

        } else {
            Log.d("positionArray--->", "Data empty");
        }
    }

    private void callAPI() {
        closeKeyboard();
        final String name = et_staff_name.getText().toString();
        final String mob = et_staff_mob.getText().toString();

        if (!TextUtils.isEmpty(pageId) && pageId.equals("3")) {
            if (name.length() > 0) {
                if (mob.length() > 0 && mob.length() >= 8) {
                    Log.d("sunday_list--->", "onClick: " + list_sun.size() + "," + list_mon.size() + "," + list_tue.size() + "," + list_wed.size() + "," + list_thu.size() + "," + list_fri.size() + "," + list_sat.size());
                    if (isBizTimeSelected) {
                        if (!mng_service_id_list.isEmpty()) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    new AlertDialogYesNo(AddStaffActivity.this, "Create Staff?", "Are you sure, You want to create " + name + "?", "Yes", "No") {
                                        @Override
                                        public void onOKButtonClick() {
                                            createMerStaff();
                                        }

                                        @Override
                                        public void onCancelButtonClick() {

                                        }

                                    };
                                }
                            });
                        } else {
                            getDialog("Must select one service for staff");
                        }

                    } else if (isCustomTimeSelected) {
                        if (isTimeAdded) {
                            if (isTimeSelected) {
                                if (!mng_service_id_list.isEmpty()) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            new AlertDialogYesNo(AddStaffActivity.this, "Create Staff?", "Are you sure, You want to create " + name + "?", "Yes", "No") {
                                                @Override
                                                public void onOKButtonClick() {
                                                    createMerStaff();
                                                }

                                                @Override
                                                public void onCancelButtonClick() {

                                                }

                                            };
                                        }
                                    });
                                } else {
                                    getDialog("Must select one service for staff");
                                }

                            } else {
                                getDialog("Select valid staff active days");

                            }
                        } else {
                            getDialog("Select valid staff active time");
                        }


                    } else {
                        getDialog("Select valid staff availability time");
                    }


                } else {
                    et_staff_mob.setError("Enter valid mobile");
                    et_staff_mob.requestFocus();
                }
            } else {
                et_staff_name.setError("Enter valid name");
                et_staff_name.requestFocus();
            }
        } else if (!TextUtils.isEmpty(pageId) && pageId.equals("03")) {

            for (int y = 0; y < mng_check_pos_list.size(); y++) {
                if (mng_check_pos_list.get(y) == true) {
                    isMapSelectedUpdate = true;
                }
            }
            if (name.length() > 0) {
                if (mob.length() > 0 && mob.length() >= 8) {
                    if (isMapSelectedUpdate) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                new AlertDialogYesNo(AddStaffActivity.this, "Update Staff?", "Are you sure, You want to update " + name + "?", "Yes", "No") {
                                    @Override
                                    public void onOKButtonClick() {
                                        resourceUpdate();
                                    }

                                    @Override
                                    public void onCancelButtonClick() {

                                    }

                                };
                            }
                        });


                    } else {
                        getDialog("Must select one service for staff");
                    }

                } else {
                    et_staff_mob.setError("Enter valid mobile");
                    et_staff_mob.requestFocus();
                }
            } else {
                et_staff_name.setError("Enter valid name");
                et_staff_name.requestFocus();
            }
        }

    }
}
