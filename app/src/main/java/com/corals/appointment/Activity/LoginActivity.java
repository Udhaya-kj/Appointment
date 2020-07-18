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
import com.corals.appointment.Constants.Constants;
import com.corals.appointment.Dialogs.AlertDialogFailure;
import com.corals.appointment.Dialogs.IntermediateAlertDialog;
import com.corals.appointment.Model.ParamProperties;
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
import java.util.UUID;

import static com.corals.appointment.Model.ParamProperties.MOBILE_CODE;

public class LoginActivity extends AppCompatActivity {

    EditText editText_id, editText_password;
    TextView textView_fg_pass;
    TextView textView, textView_title;
    ImageView imageView_logo;
    MaterialButton button_login;
    private SharedPreferences sharedpreferences_ask_again;
    private SharedPreferences sharedpreferences_sessionToken;
    public static final String MyPREFERENCES_SESSIONTOKEN = "MyPREFERENCES_SESSIONTOKEN ";
    public static final String SESSIONTOKEN = "SESSIONTOKEN ";
    public static final String MERID = "MERID ";
    public static final String DEVICEID = "DEVICEID ";
    public static final String CURRENCY_SYMBOL = "CURRENCY_SYMBOL ";
    public static final String COUNTRY_CODE = "COUNTRY_CODE ";
    public static final String BIZ_NAME = "BIZ_NAME ";
    public static final String USER_MAIL = "USER_MAIL ";
    public static final String MAX_DAYS = "MAX_DAYS ";
    private IntermediateAlertDialog intermediateAlertDialog;
    String ask_again_value = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        boolean isConn = ConnectivityReceiver.isConnected();
        if (!isConn) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    new AlertDialogFailure(LoginActivity.this, getResources().getString(R.string.no_internet_sub_title), "OK", getResources().getString(R.string.no_internet_title), "Failed") {
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


        imageView_logo = findViewById(R.id.image_logo);
        textView_title = findViewById(R.id.text_app_title);


        sharedpreferences_sessionToken = getSharedPreferences(MyPREFERENCES_SESSIONTOKEN, Context.MODE_PRIVATE);
        sharedpreferences_ask_again = getSharedPreferences(StatusServiceStaffActivity.MyPREFERENCES_ASK_AGAIN, Context.MODE_PRIVATE);

        if (!TextUtils.isEmpty(sharedpreferences_sessionToken.getString(LoginActivity.USER_MAIL, ""))) {
            editText_id.setText(sharedpreferences_sessionToken.getString(LoginActivity.USER_MAIL, ""));
            editText_id.setSelection(sharedpreferences_sessionToken.getString(LoginActivity.USER_MAIL, "").length());
            editText_password.requestFocus();
        }
       /* SharedPreferences.Editor editor = sharedpreferences_ask_again.edit();
        editor.putString(StatusServiceStaffActivity.VALUE, "0");
        editor.commit();*/
        ask_again_value = sharedpreferences_ask_again.getString(StatusServiceStaffActivity.VALUE, "");

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
                startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
                overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);
            }
        });
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callAPI();
            }
        });
    }

    private void callAPI() {
        String email = editText_id.getText().toString().trim();
        String pass = editText_password.getText().toString().trim();

        if (!email.isEmpty()) {
            if (!pass.isEmpty()) {
                try {
                    boolean isConn = ConnectivityReceiver.isConnected();
                    if (isConn) {
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

                        String id = UUID.randomUUID().toString();
                        Log.d("UUID--->", "onCreate: " + id);
                        SecurityAPIBody securityAPIBody = new SecurityAPIBody();
                        securityAPIBody.setReqType(Constants.LOGIN_API);
                        securityAPIBody.setDeviceId(id);
                        securityAPIBody.setUserEmail(editText_id.getText().toString());
                        securityAPIBody.setUserPass(hashStr);
                        intermediateAlertDialog = new IntermediateAlertDialog(LoginActivity.this);
                        merchantApptLogin(securityAPIBody);
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                new AlertDialogFailure(LoginActivity.this, getResources().getString(R.string.no_internet_sub_title), "OK", getResources().getString(R.string.no_internet_title), getResources().getString(R.string.no_internet_Heading)) {
                                    @Override
                                    public void onButtonClick() {
                                        callAPI();
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

    }

    private void merchantApptLogin(final SecurityAPIBody requestBody) throws ApiException {
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
                        new AlertDialogFailure(LoginActivity.this, getResources().getString(R.string.try_again), "OK", getResources().getString(R.string.went_wrong), "Failed") {
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
                Log.d("login--->", "onSuccess-" + statusCode + " , " + result + " , " + result.getUserId() + "," + result.getBizDisplayName() + " , " + result.getCountryCode() + " , " + result.getMerCurSymbol() + " , " + result.getMaxLenLoadingDays() + "," + result.getTotalSerCount() + "," + result.getTotalResCount());

                if (Integer.parseInt(result.getStatusCode()) == 200) {
                    SharedPreferences.Editor editor = sharedpreferences_sessionToken.edit();
                    editor.putString(SESSIONTOKEN, result.getSessionToken());
                    editor.putString(MERID, result.getUserId());
                    editor.putString(CURRENCY_SYMBOL, result.getMerCurSymbol());
                    editor.putString(COUNTRY_CODE, result.getCountryCode());
                    editor.putString(BIZ_NAME, result.getBizDisplayName());
                    editor.putString(DEVICEID, requestBody.getDeviceId());
                    editor.putString(MAX_DAYS, result.getMaxLenLoadingDays());
                    editor.putString(USER_MAIL, editText_id.getText().toString().trim());
                    editor.commit();

                    if (Integer.parseInt(result.getTotalSerCount()) > 0 && Integer.parseInt(result.getTotalResCount()) > 0) {
                        startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
                        } else {
                        if (!TextUtils.isEmpty(ask_again_value) && ask_again_value.equals("1")) {
                            startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
                        } else {
                            startActivity(new Intent(LoginActivity.this, StatusServiceStaffActivity.class).putExtra("total_service", result.getTotalSerCount()).putExtra("total_resource", result.getTotalResCount()));
                        }
                        }
                    finish();
                    overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);

                } else if (Integer.parseInt(result.getStatusCode()) == 205) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new AlertDialogFailure(LoginActivity.this, "Email or password is incorrect", "OK", "", "Login Failed") {
                                @Override
                                public void onButtonClick() {
                                    // finish();
                                }
                            };
                        }
                    });
                } else if (Integer.parseInt(result.getStatusCode()) == 412) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new AlertDialogFailure(LoginActivity.this, "Email or password is incorrect", "OK", "", "Login Failed") {
                                @Override
                                public void onButtonClick() {
                                    // finish();
                                }
                            };
                        }
                    });
                } else {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new AlertDialogFailure(LoginActivity.this, getResources().getString(R.string.try_again), "OK", getResources().getString(R.string.went_wrong), "Failed") {
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (intermediateAlertDialog != null) {
            intermediateAlertDialog.dismissAlertDialog();
            intermediateAlertDialog = null;
        }
    }
}
