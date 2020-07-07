package com.corals.appointment.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.corals.appointment.Dialogs.AlertDialogFailure;
import com.corals.appointment.Model.ParamProperties;
import com.corals.appointment.R;
import com.corals.appointment.receiver.ConnectivityReceiver;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.corals.appointment.Model.ParamProperties.MOBILE_CODE;

public class OtpActivity extends AppCompatActivity {

    private MaterialButton button_next;
    private TextView textView_resend, textView_time;

    private String mVerificationId;
    //The edittext to input the code
    private EditText editTextCode;
    //firebase auth object
    private FirebaseAuth mAuth;
    private String mob;

    private ProgressBar progress;
    private FrameLayout linearLayout;
    private LinearLayout imageView_back;
    private SharedPreferences sharedpreferences_sessionToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        sharedpreferences_sessionToken = getSharedPreferences(LoginActivity.MyPREFERENCES_SESSIONTOKEN, Context.MODE_PRIVATE);

        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();

        imageView_back = (LinearLayout) findViewById(R.id.image_back);
        linearLayout = (FrameLayout) findViewById(R.id.layout_otp);
        progress = findViewById(R.id.verify_otp_progress_bar);
        editTextCode = (EditText) findViewById(R.id.editText_Code);
        button_next = (MaterialButton) findViewById(R.id.button_next_opt_fragment);
        textView_resend = (TextView) findViewById(R.id.text_resend_otp);
        textView_time = (TextView) findViewById(R.id.text_time);
        //textView_resend.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + "Resend OTP" + "</u>  </font>"));
        textView_resend.setEnabled(false);

        if (getIntent().getExtras() != null) {
            mob = getIntent().getStringExtra("mobile");
            Log.d("Mobile---->", "" + mob);
        }

        boolean isConn = ConnectivityReceiver.isConnected();

        if (isConn) {
            if (!TextUtils.isEmpty(mob) && mob != null) {
                sendVerificationCode(mob);
            } else {
                Toast.makeText(this, "Something went wrong.Please try again!", Toast.LENGTH_SHORT).show();
            }
        } else {

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    new AlertDialogFailure(OtpActivity.this, getResources().getString(R.string.no_internet_sub_title), "OK", getResources().getString(R.string.no_internet_title), getResources().getString(R.string.no_internet_Heading)) {
                        @Override
                        public void onButtonClick() {
                            Intent i = new Intent(OtpActivity.this, ForgotPasswordActivity.class);
                            startActivity(i);
                            finish();
                            overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);
                        }
                    };
                }
            });
        }


        Timer();

        imageView_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in = new Intent(OtpActivity.this, ForgotPasswordActivity.class);
                startActivity(in);
                finish();
                overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);


            }
        });
        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String code = editTextCode.getText().toString().trim();
              /*  if (code.isEmpty() || code.length() < 6) {
                    editTextCode.setError("Enter valid code");
                    editTextCode.requestFocus();
                    return;
                }*/

                //verifying the code entered manually
                verifyVerificationCode(code);

            }
        });

        textView_resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(mob) && mob != null) {
                    Timer();
                    sendVerificationCode(mob);
                } else {
                    Toast.makeText(OtpActivity.this, "Something went wrong.Please try again!", Toast.LENGTH_SHORT).show();
                }


              /*  String code = editTextCode.getText().toString().trim();
                if (code.isEmpty() || code.length() < 6) {
                    editTextCode.setError("Enter valid code");
                    editTextCode.requestFocus();
                    return;
                }
                //verifying the code entered manually
                verifyVerificationCode(code);*/
            }
        });

    }


    private void sendVerificationCode(String mobile) {
        String code = sharedpreferences_sessionToken.getString(LoginActivity.COUNTRY_CODE, "");
        ParamProperties paramProperties = new ParamProperties();
        String mob_code = paramProperties.getProperty(code, MOBILE_CODE);
        progress.setVisibility(View.VISIBLE);
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+" + mob_code + "" + mobile,
                120,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallbacks);

        Toast.makeText(this, "OTP has been sent to " + mob, Toast.LENGTH_LONG).show();
    }


    //the callback to detect the verification status
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            //Getting the code sent by SMS
            String code = phoneAuthCredential.getSmsCode();
            //sometime the code is not detected automatically
            //in this case the code will be null
            //so user has to manually enter the code
            if (code != null) {
                editTextCode.setText(code);
                //verifying the code
                verifyVerificationCode(code);
            }

        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(OtpActivity.this, "Verification Failed.Please try again!", Toast.LENGTH_LONG).show();

            Log.d("Verification Failed--->", "" + e.getMessage().toString());
        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            //storing the verification id that is sent to the user
            mVerificationId = s;
            //Toast.makeText(OTP_Activity.this, "Code sent!", Toast.LENGTH_LONG).show();

        }
    };


    private void verifyVerificationCode(String code) {

        try {
            //creating the credential
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);
            //signing the user
            signInWithPhoneAuthCredential(credential);
        } catch (Exception e) {
            Toast toast = Toast.makeText(this, "Verification code is wrong", Toast.LENGTH_SHORT);
            // toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
        }
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(OtpActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //verification successful we will start the profile activity
                            progress.setVisibility(View.INVISIBLE);

                            Intent in = new Intent(getApplicationContext(), ResetPasswordActivity.class);
                            in.putExtra("mobile", mob);
                            startActivity(in);
                            finish();
                            overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);
                        } else {

                            progress.setVisibility(View.INVISIBLE);
                            //verification unsuccessful.. display an error message

                            String message = "";
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                message = "Invalid code entered. Please enter valid code";
                            }
                            Snackbar snackbar = Snackbar.make(findViewById(R.id.layout_otp), message, Snackbar.LENGTH_LONG);
                            snackbar.show();


                        }
                    }
                });
    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {

        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }


    public void Timer() {
        new CountDownTimer(120000, 1000) { // adjust the milli seconds here

            public void onTick(long millisUntilFinished) {
                textView_time.setText("" + String.format("%02d : %02d ",
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
            }

            public void onFinish() {
                textView_time.setText("00 : 00");
                progress.setVisibility(View.INVISIBLE);
                textView_resend.setAlpha(1f);
                textView_resend.setEnabled(true);
            }
        }.start();
    }

}
