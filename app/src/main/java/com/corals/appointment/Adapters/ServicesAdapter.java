package com.corals.appointment.Adapters;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;


import com.corals.appointment.Activity.AddServiceActivity;
import com.corals.appointment.Activity.LoginActivity;
import com.corals.appointment.Activity.SerUnavailAskTimeActivity;
import com.corals.appointment.Activity.SettingsActivity;
import com.corals.appointment.Activity.SetupServiceActivity_Bottom;
import com.corals.appointment.Client.ApiCallback;
import com.corals.appointment.Client.ApiException;
import com.corals.appointment.Client.OkHttpApiClient;
import com.corals.appointment.Client.api.MerchantApisApi;
import com.corals.appointment.Client.model.AppointmentService;
import com.corals.appointment.Client.model.ApptTransactionBody;
import com.corals.appointment.Client.model.ApptTransactionResponse;
import com.corals.appointment.Constants.Constants;
import com.corals.appointment.Dialogs.AlertDialogFailure;
import com.corals.appointment.Dialogs.AlertDialogYesNo;
import com.corals.appointment.Dialogs.IntermediateAlertDialog;
import com.corals.appointment.R;
import com.corals.appointment.receiver.ConnectivityReceiver;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ServicesAdapter extends BaseAdapter  {
    DatePickerDialog datePickerDialog;
    int Year, Month, Day, Hour, Minute, pos;
    Calendar calendar;
    private final Activity context;
    List<AppointmentService> appointmentServiceArrayList;
    private IntermediateAlertDialog intermediateAlertDialog;
    private SharedPreferences sharedpreferences_sessionToken;
    String date_active = "";

    public ServicesAdapter(Activity context, List<AppointmentService> appointmentServiceArrayList) {
        // TODO Auto-generated constructor stub

        this.context = context;
        this.appointmentServiceArrayList = appointmentServiceArrayList;

        calendar = Calendar.getInstance();
        Year = calendar.get(Calendar.YEAR);
        Month = calendar.get(Calendar.MONTH);
        Day = calendar.get(Calendar.DAY_OF_MONTH);
        Hour = calendar.get(Calendar.HOUR_OF_DAY);
        Minute = calendar.get(Calendar.MINUTE);
    }

    @Override
    public int getCount() {
        return appointmentServiceArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.layout_services, null, true);

        TextView ser_name = (TextView) rowView.findViewById(R.id.text_service_name);
        TextView ser_dur = (TextView) rowView.findViewById(R.id.text_ser_dur);
        ImageView imageView_edit = (ImageView) rowView.findViewById(R.id.image_edit_service);
        ImageView imageView_delete = (ImageView) rowView.findViewById(R.id.image_delete_service);

     /*   ser_name.setText(arrayList.get(position));
        ser_dur.setText(arrayList1.get(position));*/

        ser_name.setText(appointmentServiceArrayList.get(position).getSerName());

        imageView_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent(context, AddServiceActivity.class);
                i.putExtra("page_id", "03");
                i.putExtra("ser_id", appointmentServiceArrayList.get(position).getSerId());

                context.startActivity(i);
                //((Activity) context).finish();
                ((Activity) context).overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);

            }
        });

        imageView_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new AlertDialogYesNo(context, "Delete Service?", "Are you sure, You want to delete " + appointmentServiceArrayList.get(position).getSerName() + "?", "Yes", "No") {
                            @Override
                            public void onOKButtonClick() {

                                pos = position;

                                getdate();

                    /*            try {
                                    boolean isConn = ConnectivityReceiver.isConnected();
                                    if (isConn) {
                                        sharedpreferences_sessionToken = context.getSharedPreferences(LoginActivity.MyPREFERENCES_SESSIONTOKEN, Context.MODE_PRIVATE);
                                        AppointmentService appointmentService = new AppointmentService();
                                        appointmentService.setSerId(appointmentServiceArrayList.get(pos).getSerId());
                                        appointmentService.setIsShowCust(appointmentServiceArrayList.get(pos).isIsShowCust());
                                        //appointmentService.setIsShowCust(false);
                                        appointmentService.setIsActive("false");
                                        appointmentService.setMerId(sharedpreferences_sessionToken.getString(LoginActivity.MERID, ""));

                                        ApptTransactionBody transactionBody = new ApptTransactionBody();
                                        transactionBody.setReqType(Constants.SERVICES_UPDATE);
                                        transactionBody.setDate("2020-08-02");
                                        transactionBody.setSerId(appointmentServiceArrayList.get(pos).getSerId());
                                        transactionBody.setMerId(sharedpreferences_sessionToken.getString(LoginActivity.MERID, ""));
                                        transactionBody.setOutletId(sharedpreferences_sessionToken.getString(LoginActivity.OUTLETID, ""));
                                        transactionBody.setDeviceId(sharedpreferences_sessionToken.getString(LoginActivity.DEVICEID, ""));
                                        transactionBody.setSessionToken(sharedpreferences_sessionToken.getString(LoginActivity.SESSIONTOKEN, ""));
                                        transactionBody.setService(appointmentService);
                                        intermediateAlertDialog = new IntermediateAlertDialog(context);
                                        apptUpdateService(transactionBody);
                                    } else {
                                        context.runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                new AlertDialogFailure(context, context.getResources().getString(R.string.no_internet_sub_title), "OK", context.getResources().getString(R.string.no_internet_title), context.getResources().getString(R.string.no_internet_Heading)) {
                                                    @Override
                                                    public void onButtonClick() {

                                                    }
                                                };
                                            }
                                        });
                                    }
                                } catch (ApiException e) {
                                    e.printStackTrace();
                                }*/


                 /*               String weekdays = "yyyyyyy";
                                if (weekdays.length() != 0 && weekdays.length() == 7) {
                                    datePickerDialog = DatePickerDialog.newInstance((DatePickerDialog.OnDateSetListener) context, Year, Month, Day);
                                    datePickerDialog.setThemeDark(false);
                                    datePickerDialog.showYearPickerFirst(false);
                                    datePickerDialog.setTitle("Service will be deactivated from the date you select");

                                    // Setting Min Date to today date
                                    Calendar min_date_c = Calendar.getInstance();
                                    datePickerDialog.setMinDate(min_date_c);

                                    // Setting Max Date to next 2 years
                                    Calendar max_date_c = Calendar.getInstance();
                                    max_date_c.set(Calendar.MONTH, Month + 6);
                                    datePickerDialog.setMaxDate(max_date_c);


                                    //Disable all SUNDAYS and SATURDAYS between Min and Max Dates
                                    for (Calendar loopdate = min_date_c; min_date_c.before(max_date_c); min_date_c.add(Calendar.DATE, 1), loopdate = min_date_c) {
                                        int dayOfWeek = loopdate.get(Calendar.DAY_OF_WEEK);
                                        String sun = String.valueOf(weekdays.charAt(0));
                                        String mon = String.valueOf(weekdays.charAt(1));
                                        String tue = String.valueOf(weekdays.charAt(2));
                                        String wed = String.valueOf(weekdays.charAt(3));
                                        String thu = String.valueOf(weekdays.charAt(4));
                                        String fri = String.valueOf(weekdays.charAt(5));
                                        String sat = String.valueOf(weekdays.charAt(6));
                                        Log.d("weekdays---", "onClick: " + sun + "," + mon + "," + tue + "," + wed + "," + thu + "," + fri + "," + sat);
                                        if ((sun.equals("n") ? dayOfWeek == Calendar.SUNDAY : dayOfWeek == 0) || (mon.equals("n") ? dayOfWeek == Calendar.MONDAY : dayOfWeek == 0) || (tue.equals("n") ? dayOfWeek == Calendar.TUESDAY : dayOfWeek == 0) || (wed.equals("n") ? dayOfWeek == Calendar.WEDNESDAY : dayOfWeek == 0) || (thu.equals("n") ? dayOfWeek == Calendar.THURSDAY : dayOfWeek == 0) || (fri.equals("n") ? dayOfWeek == Calendar.FRIDAY : dayOfWeek == 0) || (sat.equals("n") ? dayOfWeek == Calendar.SATURDAY : dayOfWeek == 0)) {
                                            Calendar[] disabledDays = new Calendar[1];
                                            disabledDays[0] = loopdate;
                                            datePickerDialog.setDisabledDays(disabledDays);
                                        }
                                    }

                                    datePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

                                        @Override
                                        public void onCancel(DialogInterface dialogInterface) {

                                            //Toast.makeText(AppointmentActivity.this, "Datepicker Canceled", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                    datePickerDialog.show(context.getFragmentManager(), "DatePickerDialog");

                                } else {
                                    //Toast.makeText(context, "Enter valid active weekdays", Toast.LENGTH_SHORT).show();
                                }*/

                            }

                            @Override
                            public void onCancelButtonClick() {

                            }

                        };
                    }
                });


            }
        });

        return rowView;

    }

    private void apptUpdateService(ApptTransactionBody requestBody) throws ApiException {
        Log.d("deactivateService---", "service: " + requestBody);
        OkHttpApiClient okHttpApiClient = new OkHttpApiClient(context);
        MerchantApisApi webMerchantApisApi = new MerchantApisApi();
        webMerchantApisApi.setApiClient(okHttpApiClient.getApiClient());

        webMerchantApisApi.merchantAppointmentTransactionAsync(requestBody, new ApiCallback<ApptTransactionResponse>() {
            @Override
            public void onFailure(final ApiException e, int statusCode, Map<String, List<String>> responseHeaders) {
                Log.d("deactivateService--->", "onFailure-" + e.getMessage());
                if (intermediateAlertDialog != null) {
                    intermediateAlertDialog.dismissAlertDialog();
                }
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new AlertDialogFailure(context, context.getResources().getString(R.string.try_again), "OK", context.getResources().getString(R.string.went_wrong), "Failed") {
                            @Override
                            public void onButtonClick() {
                                context.startActivity(new Intent(context, SetupServiceActivity_Bottom.class));
                                ((Activity) context).finish();
                                ((Activity) context).overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);
                            }
                        };
                    }
                });
            }

            @Override
            public void onSuccess(ApptTransactionResponse result, int statusCode, Map<String, List<String>> responseHeaders) {
                Log.d("deactivateService--->", "onSuccess-" + statusCode + "," + result.getStatusMessage());
                if (intermediateAlertDialog != null) {
                    intermediateAlertDialog.dismissAlertDialog();
                }
                if (Integer.parseInt(result.getStatusCode()) == 200) {

                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new AlertDialogFailure(context, "Service deactivated successfully!", "OK", "", "Success") {
                                @Override
                                public void onButtonClick() {
                                    context.startActivity(new Intent(context, SetupServiceActivity_Bottom.class));
                                    ((Activity) context).finish();
                                    ((Activity) context).overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);
                                }
                            };
                        }
                    });

                } else {
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new AlertDialogFailure(context, "Service deactivation failed. Please try again later!", "OK", "", "Failed") {
                                @Override
                                public void onButtonClick() {
                                    context.startActivity(new Intent(context, SetupServiceActivity_Bottom.class));
                                    ((Activity) context).finish();
                                    ((Activity) context).overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);
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

    private void getdate() {

        sharedpreferences_sessionToken = context.getSharedPreferences(LoginActivity.MyPREFERENCES_SESSIONTOKEN, Context.MODE_PRIVATE);
        String maxday = sharedpreferences_sessionToken.getString(LoginActivity.MAX_DAYS, "");
        if(TextUtils.isEmpty(maxday)){
            maxday="30";
        }
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        android.app.DatePickerDialog datePickerDialog = new android.app.DatePickerDialog(context, android.R.style.Theme_Holo_Dialog_MinWidth,
                new android.app.DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        //date_active = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                        if (String.valueOf(monthOfYear + 1).length() == 1 && String.valueOf(dayOfMonth).length() == 1) {
                            String mnth = "0" + (monthOfYear + 1);
                            String day = "0" + (dayOfMonth);
                            date_active = year + "-" + mnth + "-" + day;
                        } else if (String.valueOf(monthOfYear + 1).length() == 1 && String.valueOf(dayOfMonth).length() != 1) {
                            String mnth = "0" + (monthOfYear + 1);
                            date_active = year + "-" + mnth + "-" + dayOfMonth;
                        } else if (String.valueOf(monthOfYear + 1).length() != 1 && String.valueOf(dayOfMonth).length() == 1) {
                            String day = "0" + (dayOfMonth);
                            date_active = year + "-" + (monthOfYear + 1) + "-" + day;
                        } else {
                            date_active = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                        }

                        getDeactivate();
                        // editText_start_dt.setText(date_active);
                        // editText_start_dt.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + date_active + "</u>  </font>"));
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.getDatePicker().setSpinnersShown(true);
        datePickerDialog.getDatePicker().setCalendarViewShown(false);
        //datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        //c.add(Calendar.YEAR, +5);
        //c.add(Calendar.MONTH, +6);
        c.add(Calendar.DAY_OF_MONTH, Integer.parseInt(maxday)-1);
        Date dOneMonthAgo = c.getTime();
        long oneMonthAgoMillis = dOneMonthAgo.getTime();
        datePickerDialog.getDatePicker().setMaxDate(oneMonthAgoMillis);
        datePickerDialog.show();
    }

    private void getDeactivate() {
        try {
            boolean isConn = ConnectivityReceiver.isConnected();
            if (isConn) {
                sharedpreferences_sessionToken = context.getSharedPreferences(LoginActivity.MyPREFERENCES_SESSIONTOKEN, Context.MODE_PRIVATE);
                AppointmentService appointmentService = new AppointmentService();
                appointmentService.setSerId(appointmentServiceArrayList.get(pos).getSerId());
                appointmentService.setIsShowCust(appointmentServiceArrayList.get(pos).isIsShowCust());
                //appointmentService.setIsShowCust(false);
                appointmentService.setIsActive("false");
                appointmentService.setMerId(sharedpreferences_sessionToken.getString(LoginActivity.MERID, ""));

                ApptTransactionBody transactionBody = new ApptTransactionBody();
                transactionBody.setReqType(Constants.SERVICES_UPDATE);
                transactionBody.setDate(date_active); //"2020-08-02"
                transactionBody.setSerId(appointmentServiceArrayList.get(pos).getSerId());
                transactionBody.setMerId(sharedpreferences_sessionToken.getString(LoginActivity.MERID, ""));
                transactionBody.setOutletId(sharedpreferences_sessionToken.getString(LoginActivity.OUTLETID, ""));
                transactionBody.setDeviceId(sharedpreferences_sessionToken.getString(LoginActivity.DEVICEID, ""));
                transactionBody.setSessionToken(sharedpreferences_sessionToken.getString(LoginActivity.SESSIONTOKEN, ""));
                transactionBody.setService(appointmentService);
                intermediateAlertDialog = new IntermediateAlertDialog(context);
                apptUpdateService(transactionBody);
            } else {
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new AlertDialogFailure(context, context.getResources().getString(R.string.no_internet_sub_title), "OK", context.getResources().getString(R.string.no_internet_title), context.getResources().getString(R.string.no_internet_Heading)) {
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
}

