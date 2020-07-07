package com.corals.appointment.Adapters;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ServicesAdapter extends BaseAdapter {

    private final Activity context;
    List<AppointmentService> appointmentServiceArrayList;
    private IntermediateAlertDialog intermediateAlertDialog;
    private SharedPreferences sharedpreferences_sessionToken;
    public ServicesAdapter(Activity context,List<AppointmentService> appointmentServiceArrayList) {
        // TODO Auto-generated constructor stub

        this.context = context;
        this.appointmentServiceArrayList = appointmentServiceArrayList;

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
                i.putExtra("position", String.valueOf(position));
                i.putExtra("ser_id", appointmentServiceArrayList.get(position).getSerId());
                i.putExtra("name", appointmentServiceArrayList.get(position).getSerName());
                i.putExtra("duration", appointmentServiceArrayList.get(position).getSerDuration());
                i.putExtra("amount", appointmentServiceArrayList.get(position).getSerPrice());
                i.putExtra("description", appointmentServiceArrayList.get(position).getSerDescription());
                i.putExtra("show_cust", String.valueOf(appointmentServiceArrayList.get(position).isIsShowCust()));
                context.startActivity(i);
                ((Activity) context).finish();
                ((Activity) context).overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);

            }
        });

        imageView_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new AlertDialogYesNo(context, "Delete Service?", "Are you sure, You want to delete "+appointmentServiceArrayList.get(position).getSerName()+"?", "Yes", "No") {
                            @Override
                            public void onOKButtonClick() {

                                try {
                                    boolean isConn = ConnectivityReceiver.isConnected();
                                    if (isConn) {
                                        sharedpreferences_sessionToken = context.getSharedPreferences(LoginActivity.MyPREFERENCES_SESSIONTOKEN, Context.MODE_PRIVATE);
                                        AppointmentService appointmentService = new AppointmentService();
                                        appointmentService.setSerId(appointmentServiceArrayList.get(position).getSerId());
                                        appointmentService.setIsShowCust(appointmentServiceArrayList.get(position).isIsShowCust());
                                        appointmentService.setIsActive(false);
                                        appointmentService.setMerId(sharedpreferences_sessionToken.getString(LoginActivity.MERID, ""));

                                        ApptTransactionBody transactionBody = new ApptTransactionBody();
                                        transactionBody.setReqType(Constants.SERVICES_UPDATE);
                                        transactionBody.setSerId(appointmentServiceArrayList.get(position).getSerId());
                                        transactionBody.setMerId(sharedpreferences_sessionToken.getString(LoginActivity.MERID, ""));
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
                                        });                                    }
                                } catch (ApiException e) {
                                    e.printStackTrace();
                                }
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
                        new AlertDialogFailure(context, context.getResources().getString(R.string.try_again), "OK", context.getResources().getString(R.string.went_wrong),"Failed") {
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
                            new AlertDialogFailure(context, "Service deactivated successfully!", "OK", "","Success") {
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
                            new AlertDialogFailure(context, "Service deactivation failed. Please try again later!", "OK", "","Failed") {
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

}

