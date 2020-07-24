package com.corals.appointment.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.corals.appointment.Activity.LoginActivity;
import com.corals.appointment.Client.ApiCallback;
import com.corals.appointment.Client.ApiException;
import com.corals.appointment.Client.OkHttpApiClient;
import com.corals.appointment.Client.api.MerchantApisApi;
import com.corals.appointment.Client.model.SecurityAPIBody;
import com.corals.appointment.Client.model.SecurityAPIResponse;
import com.corals.appointment.Constants.Constants;
import com.corals.appointment.Dialogs.AlertDialogFailure;
import com.corals.appointment.Dialogs.IntermediateAlertDialog;
import com.corals.appointment.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.gson.internal.$Gson$Preconditions;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;
import java.util.UUID;


public abstract class CAllLoginAPI {
    private SharedPreferences sharedpreferences_sessionToken;
    private IntermediateAlertDialog intermediateAlertDialog;


    public void callLoginAPI(final Context context) {

        sharedpreferences_sessionToken = context.getSharedPreferences(LoginActivity.MyPREFERENCES_SESSIONTOKEN, Context.MODE_PRIVATE);
        final String username = sharedpreferences_sessionToken.getString(LoginActivity.USER_MAIL, "");
        final String password = sharedpreferences_sessionToken.getString(LoginActivity.USER_PASSWORD, "");

        String fb_token = FirebaseInstanceId.getInstance().getToken();
        if (TextUtils.isEmpty(fb_token) || fb_token==null) {
            fb_token = FirebaseInstanceId.getInstance().getToken();
        }
        Log.d("callLoginAPI--->", "callLoginAPI: " + fb_token);
        try {
            MessageDigest messageDigest = null;
            try {
                messageDigest = MessageDigest.getInstance("SHA-256");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            byte hashBytes[] = messageDigest.digest(password.getBytes(StandardCharsets.UTF_8));
            BigInteger noHash = new BigInteger(1, hashBytes);
            String hashStr = noHash.toString(16);
            Log.d("PASSWORD--->", "onClick: " + hashStr);

            String id = UUID.randomUUID().toString();
            Log.d("UUID--->", "onCreate: " + id);
            Log.d("callLoginAPI--->", "callLoginAPI: " + username + "," + password + ", Token :" + fb_token);

            SecurityAPIBody securityAPIBody = new SecurityAPIBody();
            securityAPIBody.setReqType(Constants.LOGIN_API);
            securityAPIBody.setDeviceId(id);
            securityAPIBody.setFirebaseInstId(fb_token);
            securityAPIBody.setUserEmail(username);
            securityAPIBody.setUserPass(hashStr);

            merchantApptLoginAPI(securityAPIBody, username, password, context);

        } catch (
                ApiException e) {
            e.printStackTrace();
        }

    }


    private void merchantApptLoginAPI(final SecurityAPIBody requestBody, final String username, final String password, final Context context) throws ApiException {
        Log.d("login---", "login: " + requestBody);
        intermediateAlertDialog = new IntermediateAlertDialog(context);
        OkHttpApiClient okHttpApiClient = new OkHttpApiClient(context);
        MerchantApisApi webMerchantApisApi = new MerchantApisApi();
        webMerchantApisApi.setApiClient(okHttpApiClient.getApiClient());

        webMerchantApisApi.merchantAppoinmentSecurityAsync(requestBody, new ApiCallback<SecurityAPIResponse>() {
            @Override
            public void onFailure(ApiException e, int statusCode, Map<String, List<String>> responseHeaders) {

                Log.d("login--->", "OnFailure :" + statusCode + " , " + e.getMessage().toString());
                if (intermediateAlertDialog != null) {
                    intermediateAlertDialog.dismissAlertDialog();
                }
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new AlertDialogFailure(context, context.getResources().getString(R.string.try_again), "OK", context.getResources().getString(R.string.server_error), "Failed") {
                            @Override
                            public void onButtonClick() {
                                ((Activity) context).finish();
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
                    editor.putString(LoginActivity.SESSIONTOKEN, result.getSessionToken());
                    editor.putString(LoginActivity.MERID, result.getUserId());
                    editor.putString(LoginActivity.CURRENCY_SYMBOL, result.getMerCurSymbol());
                    editor.putString(LoginActivity.COUNTRY_CODE, result.getCountryCode());
                    editor.putString(LoginActivity.BIZ_NAME, result.getBizDisplayName());
                    editor.putString(LoginActivity.DEVICEID, requestBody.getDeviceId());
                    editor.putString(LoginActivity.MAX_DAYS, result.getMaxLenLoadingDays());
                    editor.putString(LoginActivity.USER_MAIL, username);
                    editor.putString(LoginActivity.USER_PASSWORD, password);
                    editor.commit();

                    ((Activity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            onButtonClick();
                        }
                    });

                } else {

                    ((Activity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new AlertDialogFailure(context, context.getResources().getString(R.string.try_again), "OK", context.getResources().getString(R.string.went_wrong), "Failed") {
                                @Override
                                public void onButtonClick() {
                                    ((Activity) context).finish();
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

    public abstract void onButtonClick();

}
