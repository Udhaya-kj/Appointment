package com.corals.appointment.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.corals.appointment.Client.ApiCallback;
import com.corals.appointment.Client.ApiException;
import com.corals.appointment.Client.OkHttpApiClient;
import com.corals.appointment.Client.api.MerchantApisApi;
import com.corals.appointment.Client.model.SecurityAPIBody;
import com.corals.appointment.Client.model.SecurityAPIResponse;
import com.corals.appointment.Dialogs.AlertDialogFailure;
import com.corals.appointment.Dialogs.IntermediateAlertDialog;
import com.corals.appointment.R;
import com.corals.appointment.receiver.ConnectivityReceiver;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    EditText editText_id, editText_password;
    TextView textView_fg_pass;
    TextView textView,textView_title;
    ImageView imageView_logo;
    MaterialButton button_login;
    private SharedPreferences sharedpreferences_services;
    private SharedPreferences sharedpreferences_staffs;
    private SharedPreferences sharedpreferences_ask_again;
    private ArrayList<String> service_name_list, service_dur_list;
    private ArrayList<String> staff_name_list, staff_mob_list;
    private SharedPreferences sharedpreferences_sessionToken;
    public static final String MyPREFERENCES_SESSIONTOKEN = "MyPREFERENCES_SESSIONTOKEN ";
    public static final String SESSIONTOKEN = "SESSIONTOKEN ";
    public static final String MERID = "MERID ";
    private IntermediateAlertDialog intermediateAlertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

            boolean isConn = ConnectivityReceiver.isConnected();
            if (!isConn) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new AlertDialogFailure(LoginActivity.this, getResources().getString(R.string.no_internet_sub_title), "OK", getResources().getString(R.string.no_internet_title),"Failed") {
                            @Override
                            public void onButtonClick() {
                                finish();
                            }
                        };
                    }
                });
            }
        editText_id = findViewById(R.id.edit_userid);
        editText_password = findViewById(R.id.edit_password);
        button_login = findViewById(R.id.button_login);
        textView = findViewById(R.id.tv_signup);
        textView_fg_pass = findViewById(R.id.text_forgot_pass);

        imageView_logo=findViewById(R.id.image_logo);
        textView_title=findViewById(R.id.text_app_title);

       /* Animation animation= AnimationUtils.loadAnimation(this,R.anim.fadein_anim);
        imageView_logo.startAnimation(animation);
        textView_title.startAnimation(animation);*/

        service_name_list = new ArrayList<>();
        service_dur_list = new ArrayList<>();
        staff_name_list = new ArrayList<>();
        staff_mob_list = new ArrayList<>();

        sharedpreferences_sessionToken = getSharedPreferences(MyPREFERENCES_SESSIONTOKEN, Context.MODE_PRIVATE);
        sharedpreferences_ask_again = getSharedPreferences(StatusServiceStaffActivity.MyPREFERENCES_ASK_AGAIN, Context.MODE_PRIVATE);
        sharedpreferences_services = getSharedPreferences(AddServiceAvailTimeActivity.MyPREFERENCES_SERVICES, Context.MODE_PRIVATE);
        sharedpreferences_staffs = getSharedPreferences(AddStaffActivity.MyPREFERENCES_STAFFS, Context.MODE_PRIVATE);

       /* SharedPreferences.Editor editor = sharedpreferences_ask_again.edit();
        editor.putString(StatusServiceStaffActivity.VALUE, "0");
        editor.commit();*/


        String nameList = sharedpreferences_services.getString(AddServiceAvailTimeActivity.SERVICE_NAME, "");
        String mobList = sharedpreferences_services.getString(AddServiceAvailTimeActivity.SERVICE_DURATION, "");
        if (!TextUtils.isEmpty(nameList) && !TextUtils.isEmpty(mobList)) {
            service_name_list = new Gson().fromJson(nameList, new TypeToken<ArrayList<String>>() {
            }.getType());
            service_dur_list = new Gson().fromJson(mobList, new TypeToken<ArrayList<String>>() {
            }.getType());
        }

        String nameList1 = sharedpreferences_staffs.getString(AddStaffActivity.NAME, "");
        String mobList1 = sharedpreferences_staffs.getString(AddStaffActivity.MOBILE, "");
        if (!TextUtils.isEmpty(nameList1) && !TextUtils.isEmpty(mobList1)) {
            staff_name_list = new Gson().fromJson(nameList1, new TypeToken<ArrayList<String>>() {
            }.getType());
            staff_mob_list = new Gson().fromJson(mobList1, new TypeToken<ArrayList<String>>() {
            }.getType());
        }


        Log.d("listsize---->", "onCreate: " + service_name_list.size() + "," + service_dur_list.size() + "," + staff_name_list.size() + "," + staff_mob_list.size());

        textView.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + "Sign up" + "</u>  </font>"));
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "Register...", Toast.LENGTH_SHORT).show();
            }
        });

        textView_fg_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
                finish();
                overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);
            }
        });
        button_login.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {

          /*      startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
                finish();
                overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);*/

                String email = editText_id.getText().toString().trim();
                String pass = editText_password.getText().toString().trim();

                if (!email.isEmpty()) {
                    if (!pass.isEmpty()) {
                        MessageDigest messageDigest = null;
                        try {
                            messageDigest = MessageDigest.getInstance("SHA-256");
                        } catch (NoSuchAlgorithmException e) {
                            e.printStackTrace();
                        }
                        byte hashBytes[] = messageDigest.digest(pass.getBytes(StandardCharsets.UTF_8));
                        BigInteger noHash = new BigInteger(1, hashBytes);
                        String hashStr = noHash.toString(16);
                        Log.d("PASSWORD--->", "onClick: " + hashStr);

                        SecurityAPIBody securityAPIBody = new SecurityAPIBody();
                        securityAPIBody.setReqType("S-L.M");
                        securityAPIBody.setDeviceId("c43cbfe00b37ae6133ca023484869d2c489a8974ba48fb3286aa058292d08f0e");
                        securityAPIBody.setUserEmail(editText_id.getText().toString());
                        securityAPIBody.setUserPass(hashStr);

                        try {
                            boolean isConn = ConnectivityReceiver.isConnected();
                            if (isConn) {
                                merchantApptLogin(securityAPIBody);
                            } else {

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        new AlertDialogFailure(LoginActivity.this, getResources().getString(R.string.no_internet_sub_title), "OK", getResources().getString(R.string.no_internet_title),"Failed") {
                                            @Override
                                            public void onButtonClick() {
                                                finish();
                                            }
                                        };
                                    }
                                });
                            }
                        } catch (ApiException e) {
                            e.printStackTrace();
                        }

                    } else {
                        editText_password.setError("Enter valid password");
                        editText_password.requestFocus();
                    }
                } else {
                    editText_id.setError("Enter valid email");
                    editText_id.requestFocus();
                }


                //First created
       /*         String id = editText_id.getText().toString().trim();
                String pswd = editText_password.getText().toString().trim();
                String value = sharedpreferences_ask_again.getString(StatusServiceStaffActivity.VALUE, "");
                // Toast.makeText(LoginActivity.this, "Value :"+value, Toast.LENGTH_SHORT).show();
                if (TextUtils.isEmpty(value)) {
                    if (service_name_list.isEmpty() || staff_name_list.isEmpty()) {
                        startActivity(new Intent(LoginActivity.this, StatusServiceStaffActivity.class));
                        finish();
                    } else {
                        startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
                        finish();
                    }
                } else {
                    startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
                    finish();
                }*/
            }
        });
    }

    private void merchantApptLogin(SecurityAPIBody requestBody) throws ApiException {


        intermediateAlertDialog = new IntermediateAlertDialog(LoginActivity.this);

        Log.d("login---", "login: " + requestBody);
        OkHttpApiClient okHttpApiClient = new OkHttpApiClient(LoginActivity.this);
        MerchantApisApi webMerchantApisApi = new MerchantApisApi();
        webMerchantApisApi.setApiClient(okHttpApiClient.getApiClient());

        webMerchantApisApi.merchantAppoinmentSecurityAsync(requestBody, new ApiCallback<SecurityAPIResponse>() {
            @Override
            public void onFailure(ApiException e, int statusCode, Map<String, List<String>> responseHeaders) {

                Log.d("login--->", "OnFailure :" + statusCode + " , " + e.getMessage().toString());
                if (intermediateAlertDialog != null) {
                    intermediateAlertDialog.dismissAlertDialog();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new AlertDialogFailure(LoginActivity.this, getResources().getString(R.string.try_again), "OK", getResources().getString(R.string.went_wrong),"Failed") {
                            @Override
                            public void onButtonClick() {
                               // finish();
                            }
                        };
                    }
                });
            }

            @Override
            public void onSuccess(SecurityAPIResponse result, int statusCode, Map<String, List<String>> responseHeaders) {
                if (intermediateAlertDialog != null) {
                    intermediateAlertDialog.dismissAlertDialog();
                }
                Log.d("login--->", "onSuccess-" + statusCode + " , " + result+ " , " + result.getUserId());

                if (Integer.parseInt(result.getStatusCode()) == 200) {
                    SharedPreferences.Editor editor = sharedpreferences_sessionToken.edit();
                    editor.putString(SESSIONTOKEN, result.getSessionToken());
                    editor.putString(MERID, result.getUserId());
                    editor.commit();

                    startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
                    finish();
                } else {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new AlertDialogFailure(LoginActivity.this, getResources().getString(R.string.try_again), "OK", getResources().getString(R.string.went_wrong),"Failed") {
                                @Override
                                public void onButtonClick() {
                                   // finish();
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
