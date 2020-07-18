package com.corals.appointment.Adapters;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;


import com.corals.appointment.Activity.AddStaffActivity;
import com.corals.appointment.Activity.LoginActivity;
import com.corals.appointment.Activity.SerUnavailAskTimeActivity;
import com.corals.appointment.Activity.SettingsActivity;
import com.corals.appointment.Activity.SetupStaffActivity_Bottom;
import com.corals.appointment.Client.ApiCallback;
import com.corals.appointment.Client.ApiException;
import com.corals.appointment.Client.OkHttpApiClient;
import com.corals.appointment.Client.api.MerchantApisApi;
import com.corals.appointment.Client.model.AppointmentResources;
import com.corals.appointment.Client.model.AppointmentService;
import com.corals.appointment.Client.model.ApptTransactionBody;
import com.corals.appointment.Client.model.ApptTransactionResponse;
import com.corals.appointment.Client.model.MapServiceResourceBody;
import com.corals.appointment.Constants.Constants;
import com.corals.appointment.Dialogs.AlertDialogFailure;
import com.corals.appointment.Dialogs.AlertDialogYesNo;
import com.corals.appointment.Dialogs.IntermediateAlertDialog;
import com.corals.appointment.R;
import com.corals.appointment.receiver.ConnectivityReceiver;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StaffListAdapter extends BaseAdapter {

    private final Activity context;
    List<AppointmentResources> appointmentResources;
    private SharedPreferences sharedpreferences_sessionToken;
    private IntermediateAlertDialog intermediateAlertDialog;

    public StaffListAdapter(Activity context, List<AppointmentResources> appointmentResources) {
        // TODO Auto-generated constructor stub

        this.context = context;
        this.appointmentResources = appointmentResources;
    }

    @Override
    public int getCount() {
        return appointmentResources.size();
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
        View rowView = inflater.inflate(R.layout.layout_staff, null, true);

        TextView titleText = (TextView) rowView.findViewById(R.id.text_staff_name);
        ImageView imageView_edit = (ImageView) rowView.findViewById(R.id.image_edit);
        ImageView imageView_delete = (ImageView) rowView.findViewById(R.id.image_delete);
        LinearLayout linearLayout = (LinearLayout) rowView.findViewById(R.id.layout_staff);
        titleText.setText(appointmentResources.get(position).getResName());

        imageView_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Mapping---", "onClick: "+appointmentResources.get(position).getSerResMaps()+" , "+appointmentResources.get(position).getResName());

                Intent i = new Intent(context, AddStaffActivity.class);
                i.putExtra("page_id", "03");
                i.putExtra("position", String.valueOf(position));
                i.putExtra("res_id", appointmentResources.get(position).getResId());
                i.putExtra("name", appointmentResources.get(position).getResName());
                i.putExtra("mobile", appointmentResources.get(position).getMobile());
                i.putExtra("sameBizTime", appointmentResources.get(position).isSameBussTime());
                i.putExtra("mapSerRes", (Serializable) appointmentResources.get(position).getSerResMaps());
                context.startActivity(i);
               // ((Activity) context).finish();
                ((Activity) context).overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);
            }
        });

        imageView_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new AlertDialogYesNo(context, "Delete Staff?", "Are you sure, You want to delete "+appointmentResources.get(position).getResName()+"?", "Yes", "No") {
                            @Override
                            public void onOKButtonClick() {
                                sharedpreferences_sessionToken = context.getSharedPreferences(LoginActivity.MyPREFERENCES_SESSIONTOKEN, Context.MODE_PRIVATE);

                                List<MapServiceResourceBody> mapServiceResourceBodyList = appointmentResources.get(position).getSerResMaps();
                                AppointmentResources appointmentResources1 = new AppointmentResources();
                                appointmentResources1.setResId(appointmentResources.get(position).getResId());
                                appointmentResources1.setMerId(sharedpreferences_sessionToken.getString(LoginActivity.MERID, ""));
                                appointmentResources1.setIsActive("false");

                                ApptTransactionBody transactionBody = new ApptTransactionBody();
                                transactionBody.setReqType(Constants.RESOURCE_UPDATE);
                                transactionBody.setResId(appointmentResources.get(position).getResId());
                                transactionBody.setMerId(sharedpreferences_sessionToken.getString(LoginActivity.MERID, ""));
                                transactionBody.setDeviceId(sharedpreferences_sessionToken.getString(LoginActivity.DEVICEID, ""));
                                transactionBody.setSessionToken(sharedpreferences_sessionToken.getString(LoginActivity.SESSIONTOKEN, ""));
                                transactionBody.setResource(appointmentResources1);

                                try {
                                    boolean isConn = ConnectivityReceiver.isConnected();
                                    if (isConn) {
                                        intermediateAlertDialog = new IntermediateAlertDialog(context);
                                        deactivateStaff(transactionBody);
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

    private void deactivateStaff(ApptTransactionBody requestBody) throws ApiException {
        Log.d("deactivateStaff---", "createStaff: " + requestBody);

        OkHttpApiClient okHttpApiClient = new OkHttpApiClient(context);
        MerchantApisApi webMerchantApisApi = new MerchantApisApi();
        webMerchantApisApi.setApiClient(okHttpApiClient.getApiClient());

        webMerchantApisApi.merchantAppointmentTransactionAsync(requestBody, new ApiCallback<ApptTransactionResponse>() {
            @Override
            public void onFailure(ApiException e, int statusCode, Map<String, List<String>> responseHeaders) {
                Log.d("deactivateStaff--->", "onFailure-" + e.getMessage());
                if (intermediateAlertDialog != null) {
                    intermediateAlertDialog.dismissAlertDialog();
                }
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new AlertDialogFailure(context, context.getResources().getString(R.string.try_again), "OK", context.getResources().getString(R.string.went_wrong), "Failed") {
                            @Override
                            public void onButtonClick() {
                                    /*  context.startActivity(new Intent(context, SetupStaffActivity_Bottom.class));
                                    ((Activity)context).finish();
                                    context.overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);*/
                            }
                        };
                    }
                });
            }

            @Override
            public void onSuccess(ApptTransactionResponse result, int statusCode, Map<String, List<String>> responseHeaders) {
                Log.d("deactivateStaff--->", "onSuccess-" + statusCode + "," + result.getStatusMessage());
                if (intermediateAlertDialog != null) {
                    intermediateAlertDialog.dismissAlertDialog();
                }
                if (Integer.parseInt(result.getStatusCode()) == 200) {
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new AlertDialogFailure(context, "Staff deactivated successfully!", "OK", "", context.getResources().getString(R.string.success)) {
                                @Override
                                public void onButtonClick() {
                                    context.startActivity(new Intent(context, SetupStaffActivity_Bottom.class));
                                    ((Activity) context).finish();
                                    context.overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);
                                }
                            };
                        }
                    });

                } else {
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new AlertDialogFailure(context, "Please try again later!", "OK", "Staff deactivation failed", "Failed") {
                                @Override
                                public void onButtonClick() {
                                  /*  context.startActivity(new Intent(context, SetupStaffActivity_Bottom.class));
                                    ((Activity)context).finish();
                                    context.overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);*/
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
