package com.corals.appointment.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.corals.appointment.Activity.AppointmentActivity;
import com.corals.appointment.Activity.CustomersMakeApptActivity;
import com.corals.appointment.Activity.DashboardActivity;
import com.corals.appointment.Activity.LoginActivity;
import com.corals.appointment.Activity.SetupStaffActivity_Bottom;
import com.corals.appointment.Client.ApiCallback;
import com.corals.appointment.Client.ApiException;
import com.corals.appointment.Client.OkHttpApiClient;
import com.corals.appointment.Client.api.MerchantApisApi;
import com.corals.appointment.Client.model.AppointmentEnquiryBody;
import com.corals.appointment.Client.model.AppointmentEnquiryResponse;
import com.corals.appointment.Client.model.AppointmentResources;
import com.corals.appointment.Client.model.Appointments;
import com.corals.appointment.Client.model.InlineResponse20013Customersrec;
import com.corals.appointment.Client.model.MapServiceResourceBody;
import com.corals.appointment.Constants.Constants;
import com.corals.appointment.Dialogs.AlertDialogFailure;
import com.corals.appointment.Dialogs.IntermediateAlertDialog;
import com.corals.appointment.Dialogs.ViewSlotCustomersBottomDialog;
import com.corals.appointment.R;
import com.corals.appointment.Utils.CAllLoginAPI;
import com.corals.appointment.receiver.ConnectivityReceiver;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ApptServiceSlotsAdapter extends RecyclerView.Adapter<ApptServiceSlotsAdapter.MyViewHolder> {
    Activity context;
    List<Appointments> appointmentAvailableSlots;
    private SharedPreferences sharedpreferences_sessionToken;
    private IntermediateAlertDialog intermediateAlertDialog;
    String date, service_id, service,service_dur;

    public ApptServiceSlotsAdapter(Activity context, String service_id, String service, String date, String service_dur, List<Appointments> appointmentAvailableSlots) {
        this.context = context;
        this.date = date;
        this.service_id = service_id;
        this.service = service;
        this.service_dur = service_dur;
        this.appointmentAvailableSlots = appointmentAvailableSlots;
    }

    @Override
    public ApptServiceSlotsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_appt_service_slot, parent, false);
        ApptServiceSlotsAdapter.MyViewHolder myViewHolder = new ApptServiceSlotsAdapter.MyViewHolder(v);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final ApptServiceSlotsAdapter.MyViewHolder holder, final int position) {

        holder.textView_ser_time.setText(appointmentAvailableSlots.get(position).getStartTime() + " - " + appointmentAvailableSlots.get(position).getEndTime());

        final int total_appt = Integer.parseInt(appointmentAvailableSlots.get(position).getAllowed());
        final int booked_appt = Integer.parseInt(appointmentAvailableSlots.get(position).getBooked());

        if (total_appt == booked_appt) {
            holder.linearLayout_color.setBackgroundResource(R.drawable.left_round_corners_blue);
            holder.imageView_avail.setBackgroundResource(R.drawable.tick);
            holder.imageView_avail.setEnabled(false);
        } else if (total_appt > booked_appt) {
            holder.linearLayout_color.setBackgroundResource(R.drawable.left_round_corners_green);
            holder.imageView_avail.setBackgroundResource(R.drawable.add_green);
            holder.imageView_avail.setEnabled(true);
        }

        holder.linearLayout_bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callAPI(position);
            }
        });

        holder.imageView_avail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(context, CustomersMakeApptActivity.class);
                in.putExtra("page_id", "2");
                in.putExtra("service_id", service_id);
                in.putExtra("service", service);
                in.putExtra("date", date);
                in.putExtra("service_dur",service_dur);
                in.putExtra("slot_no",  appointmentAvailableSlots.get(position).getSlotNo());
                in.putExtra("start_time", appointmentAvailableSlots.get(position).getStartTime());
                in.putExtra("end_time", appointmentAvailableSlots.get(position).getEndTime());
                context.startActivity(in);
                //((Activity) context).finish();
            }
        });

    }


    @Override
    public int getItemCount() {
        return appointmentAvailableSlots.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textView_cus_name, textView_ser_time, textView_cus_mob;
        LinearLayout linearLayout_bg, linearLayout_color;
        ImageView imageView_avail;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView_cus_name = (TextView) itemView.findViewById(R.id.tv_slot_cus_name);
            textView_ser_time = (TextView) itemView.findViewById(R.id.tv_slot_time);
            textView_cus_mob = (TextView) itemView.findViewById(R.id.tv_slot_cus_mob);
            linearLayout_bg = (LinearLayout) itemView.findViewById(R.id.layout_appt_slot);
            linearLayout_color = (LinearLayout) itemView.findViewById(R.id.layout_color_status);
            imageView_avail = (ImageView) itemView.findViewById(R.id.image_available);
        }
    }

    private void callAPI(final int position) {
        boolean isConn = ConnectivityReceiver.isConnected();
        if (isConn) {
            try {
                sharedpreferences_sessionToken = context.getSharedPreferences(LoginActivity.MyPREFERENCES_SESSIONTOKEN, Context.MODE_PRIVATE);
                AppointmentEnquiryBody enquiryBody = new AppointmentEnquiryBody();
                enquiryBody.setReqType(Constants.FETCH_CUSTOMER_LIST_SLOT);
                enquiryBody.setMerId(sharedpreferences_sessionToken.getString(LoginActivity.MERID, ""));
                enquiryBody.setOutletId(sharedpreferences_sessionToken.getString(LoginActivity.OUTLETID, ""));
                enquiryBody.callerType("m");
                enquiryBody.setDate(date);
                enquiryBody.setSerId(appointmentAvailableSlots.get(position).getSerId());
                enquiryBody.setStartTime(appointmentAvailableSlots.get(position).getStartTime());
                enquiryBody.setEndTime(appointmentAvailableSlots.get(position).getEndTime());
                enquiryBody.setDeviceId(sharedpreferences_sessionToken.getString(LoginActivity.DEVICEID, ""));
                enquiryBody.setSessionToken(sharedpreferences_sessionToken.getString(LoginActivity.SESSIONTOKEN, ""));
                intermediateAlertDialog = new IntermediateAlertDialog(context);
                fetchCustomer(enquiryBody, appointmentAvailableSlots.get(position).getApptId(), appointmentAvailableSlots.get(position).getStartTime(), appointmentAvailableSlots.get(position).getEndTime(), date, service_id, service,position);
            } catch (ApiException e) {
                e.printStackTrace();
            }
        } else {
            context.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    new AlertDialogFailure(context, context.getResources().getString(R.string.no_internet_sub_title), "OK", context.getResources().getString(R.string.no_internet_title), context.getResources().getString(R.string.no_internet_Heading)) {
                        @Override
                        public void onButtonClick() {
                            callAPI(position);
                        }
                    };
                }
            });
        }
    }

    private void fetchCustomer(AppointmentEnquiryBody requestBody, final String appt_id, final String start_time, final String end_time, final String date, final String service_id, final String service,final int position) throws ApiException {

        Log.d("fetchCustomer--->", "fetchService: " + requestBody + "," + service);
        OkHttpApiClient okHttpApiClient = new OkHttpApiClient(context);
        MerchantApisApi webMerchantApisApi = new MerchantApisApi();
        webMerchantApisApi.setApiClient(okHttpApiClient.getApiClient());

        webMerchantApisApi.merchantAppointmentEnquiryAsync(requestBody, new ApiCallback<AppointmentEnquiryResponse>() {
            @Override
            public void onFailure(ApiException e, int statusCode, Map<String, List<String>> responseHeaders) {
                Log.d("fetchCustomer--->", "onFailure-" + e.getMessage());
                if (intermediateAlertDialog != null) {
                    intermediateAlertDialog.dismissAlertDialog();
                }

                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new AlertDialogFailure(context, context.getResources().getString(R.string.try_again), "OK", context.getResources().getString(R.string.server_error), "Failed") {
                            @Override
                            public void onButtonClick() {
                                context.startActivity(new Intent(context, AppointmentActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                                ((Activity) context).finish();
                                context.overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);
                            }
                        };
                    }
                });
            }

            @Override
            public void onSuccess(final AppointmentEnquiryResponse result, int statusCode, Map<String, List<String>> responseHeaders) {

                Log.d("fetchCustomer--->", "onSuccess-" + statusCode + "," + result + "," + result.getResources());
                if (Integer.parseInt(result.getStatusCode()) == 200) {
                    if (intermediateAlertDialog != null) {
                        intermediateAlertDialog.dismissAlertDialog();
                    }
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (!result.getCustomers().isEmpty() && result.getCustomers() != null) {
                                BottomSheetDialog bd = new BottomSheetDialog(context);
                                ViewSlotCustomersBottomDialog viewSlotCustomersBottomDialog = new ViewSlotCustomersBottomDialog(context, appt_id, start_time + "-" + end_time, date, service_id, service, bd, result.getCustomers());
                                viewSlotCustomersBottomDialog.showBottomSheetDialog();
                            } else {

                            }
                        }
                    });
                } else if (Integer.parseInt(result.getStatusCode()) == 404){
                    if (intermediateAlertDialog != null) {
                        intermediateAlertDialog.dismissAlertDialog();
                    }
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new AlertDialogFailure(context, "Customers not found for this appointment", "OK", "", "Failed") {
                                @Override
                                public void onButtonClick() {
                                /*    context.startActivity(new Intent(context, DashboardActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                                    ((Activity) context).finish();
                                    context.overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);*/
                                }
                            };
                        }
                    });
                }
                else if (Integer.parseInt(result.getStatusCode()) == 400){
                    if (intermediateAlertDialog != null) {
                        intermediateAlertDialog.dismissAlertDialog();
                    }
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new AlertDialogFailure(context, context.getResources().getString(R.string.try_again), "OK", "Invalid data", "Failed") {
                                @Override
                                public void onButtonClick() {
                                    context.startActivity(new Intent(context, AppointmentActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                                    ((Activity) context).finish();
                                    context.overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);
                                }
                            };
                        }
                    });
                }
                else if (Integer.parseInt(result.getStatusCode()) == 401) {
                    if (intermediateAlertDialog != null) {
                        intermediateAlertDialog.dismissAlertDialog();
                    }
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new CAllLoginAPI() {
                                @Override
                                public void onButtonClick() {

                                    callAPI(position);
                                }
                            }.callLoginAPI(context);
                        }
                    });

                }
                else {
                    if (intermediateAlertDialog != null) {
                        intermediateAlertDialog.dismissAlertDialog();
                    }
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new AlertDialogFailure(context, context.getResources().getString(R.string.try_again), "OK", context.getResources().getString(R.string.went_wrong), "Failed") {
                                @Override
                                public void onButtonClick() {
                                    context.startActivity(new Intent(context, AppointmentActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                                    ((Activity) context).finish();
                                    context.overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);
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


