package com.dostel.managerapp1;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.concurrent.TimeUnit;

public class PhoneAuthActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String KEY_VERIFY_IN_PROGRESS = "key_verify_in_progress";
    private static final int STATE_CODE_SENT = 2;
    private static final int STATE_INITIALIZED = 1;
    private static final int STATE_SIGNIN_FAILED = 5;
    private static final int STATE_SIGNIN_SUCCESS = 6;
    private static final int STATE_VERIFY_FAILED = 3;
    private static final int STATE_VERIFY_SUCCESS = 4;
    private static final String TAG = "PhoneAuthActivity";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    /* access modifiers changed from: private */
    public boolean isRegistered;
    private FirebaseAuth mAuth;

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private TextView mDetailText;
    /* access modifiers changed from: private */
    public EditText mPhoneNumberField;
    private TextView mPhoneNumberViews;
    private Button mResendButton;
    /* access modifiers changed from: private */
    public PhoneAuthProvider.ForceResendingToken mResendToken;
    private Button mStartButton;
    private TextView mStatusText;
    /* access modifiers changed from: private */
    public EditText mVerificationField;
    /* access modifiers changed from: private */
    public String mVerificationId;
    /* access modifiers changed from: private */
    public boolean mVerificationInProgress = false;
    private Button mVerifyButton;
    /* access modifiers changed from: private */
    public String namePg;
    private String contactPg;
    private String addressPg;
    private String pinCodePg;
    private String emailPg;
    private Process dialog;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        PhoneAuthActivity.super.onCreate(savedInstanceState);
        setContentView(R.layout.activtiy_phone_auth);
        if (savedInstanceState != null) {
            onRestoreInstanceState(savedInstanceState);
        }
        this.mPhoneNumberViews = (TextView) findViewById(R.id.fieldPhoneNumber);
/*
        this.mStatusText = (TextView) findViewById(R.id.status);
*/
/*
        this.mDetailText = (TextView) findViewById(R.id.detail);
*/
        this.mPhoneNumberField = (EditText) findViewById(R.id.fieldPhoneNumber);
        this.mVerificationField = (EditText) findViewById(R.id.fieldVerificationCode);
        this.mStartButton = (Button) findViewById(R.id.buttonStartVerification);
        this.mVerifyButton = (Button) findViewById(R.id.buttonVerifyPhone);
        this.mResendButton = (Button) findViewById(R.id.buttonResend);
        this.mStartButton.setOnClickListener(this);
        this.mVerifyButton.setOnClickListener(this);
        this.mResendButton.setOnClickListener(this);
        this.mAuth = FirebaseAuth.getInstance();
        this.mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                Log.d(PhoneAuthActivity.TAG, "onVerificationCompleted:" + credential);
                boolean unused = PhoneAuthActivity.this.mVerificationInProgress = false;
                PhoneAuthActivity.this.updateUI(4, credential);
                PhoneAuthActivity.this.signInWithPhoneAuthCredential(credential);
            }

            public void onVerificationFailed(FirebaseException e) {
                Log.w(PhoneAuthActivity.TAG, "onVerificationFailed", e);
                boolean unused = PhoneAuthActivity.this.mVerificationInProgress = false;
                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    PhoneAuthActivity.this.mPhoneNumberField.setError("Invalid phone number.");
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    Toast.makeText(PhoneAuthActivity.this.getApplicationContext(), "sms quota exceeded, try on a different phone number", Toast.LENGTH_LONG).show();
                }
                PhoneAuthActivity.this.updateUI(3);
            }

            public void onCodeSent(String verificationId, PhoneAuthProvider.ForceResendingToken token) {
                Log.d(PhoneAuthActivity.TAG, "onCodeSent:" + verificationId);
                String unused = PhoneAuthActivity.this.mVerificationId = verificationId;
                PhoneAuthProvider.ForceResendingToken unused2 = PhoneAuthActivity.this.mResendToken = token;
                PhoneAuthActivity.this.updateUI(2);
            }
        };
    }

    public void onStart() {
        PhoneAuthActivity.super.onStart();
        FirebaseUser currentUser = this.mAuth.getCurrentUser();
        if (this.mAuth.getCurrentUser() != null) {
            contactPg = mAuth.getCurrentUser().getPhoneNumber();
            // check whether this contains 10 digit or 13 digit number
            checkRegistration(contactPg);
        } else {
            updateUI(currentUser);
        }
        if (this.mVerificationInProgress && validatePhoneNumber()) {
            startPhoneNumberVerification(this.mPhoneNumberField.getText().toString());
        }
    }

    /* access modifiers changed from: protected */
    public void onSaveInstanceState(Bundle outState) {
        PhoneAuthActivity.super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_VERIFY_IN_PROGRESS, this.mVerificationInProgress);
    }

    /* access modifiers changed from: protected */
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        PhoneAuthActivity.super.onRestoreInstanceState(savedInstanceState);
        this.mVerificationInProgress = savedInstanceState.getBoolean(KEY_VERIFY_IN_PROGRESS);
    }

    private void startPhoneNumberVerification(java.lang.String r8) {
        // [START start_phone_auth]
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                mPhoneNumberField.getText().toString(),        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks

        mVerificationInProgress = true;
    }

    private void verifyPhoneNumberWithCode(String verificationId, String code) {
        signInWithPhoneAuthCredential(PhoneAuthProvider.getCredential(verificationId, code));
    }

    private void resendVerificationCode(java.lang.String r9, com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken r10) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                mPhoneNumberField.getText().toString(),        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks,         // OnVerificationStateChangedCallbacks
                mResendToken);
        Toast.makeText(getApplicationContext(), "Code resent", Toast.LENGTH_LONG).show();
    }

    public void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {

        this.mAuth.signInWithCredential(credential).addOnCompleteListener(this,
                new OnCompleteListener<AuthResult>() {
                    public void onComplete(Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(PhoneAuthActivity.TAG, "signInWithCredential:success");
                            FirebaseUser user = ((AuthResult) task.getResult()).getUser();
                            PhoneAuthActivity.this.checkRegistration(((AuthResult) task.getResult()).getUser().getPhoneNumber());
                            PhoneAuthActivity.this.updateUI(6, user);

                            return;
                        }
                        Log.w(PhoneAuthActivity.TAG, "signInWithCredential:failure", task.getException());
                        if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                            PhoneAuthActivity.this.mVerificationField.setError("Invalid code.");
                        } else {
                            Toast.makeText(PhoneAuthActivity.this.getApplicationContext(), "something went wrong, please try again", Toast.LENGTH_LONG).show();
                        }
                        PhoneAuthActivity.this.updateUI(5);
                    }
                });
    }

    /* access modifiers changed from: private */
    public void updateUI(int uiState) {
        updateUI(uiState, this.mAuth.getCurrentUser(), (PhoneAuthCredential) null);
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            updateUI(6, user);
        } else {
            updateUI(1);
        }
    }

    /* access modifiers changed from: private */
    public void updateUI(int uiState, FirebaseUser user) {
        updateUI(uiState, user, (PhoneAuthCredential) null);
    }

    /* access modifiers changed from: private */
    public void updateUI(int uiState, PhoneAuthCredential cred) {
        updateUI(uiState, (FirebaseUser) null, cred);
    }

    private void updateUI(int uiState, FirebaseUser user, PhoneAuthCredential cred) {
        if (uiState == 1) {
            enableViews(this.mPhoneNumberField, this.mVerificationField, this.mStartButton);
            disableViews(this.mVerifyButton, this.mResendButton);
            /*this.mDetailText.setText((CharSequence) null);*/
        } else if (uiState == 2) {
            enableViews(this.mVerifyButton, this.mResendButton, this.mPhoneNumberField, this.mVerificationField);
            disableViews(this.mStartButton);
            /*this.mDetailText.setText(R.string.status_code_sent);*/
        } else if (uiState == 3) {
            enableViews(this.mStartButton, this.mVerifyButton, this.mResendButton, this.mPhoneNumberField, this.mVerificationField);
            Toast.makeText(getApplicationContext(), "Verification failed", Toast.LENGTH_LONG).show();
        } else if (uiState != 4 && uiState == 5) {
            /*this.mDetailText.setText(R.string.status_sign_in_failed);*/
        }
        /*if (user == null) {
            this.mPhoneNumberViews.setVisibility(View.VISIBLE);
            this.mVerificationField.setVisibility(View.VISIBLE);
        }*/
    }

    private boolean validatePhoneNumber() {
        if (!TextUtils.isEmpty(this.mPhoneNumberField.getText().toString())) {
            return true;
        }
        this.mPhoneNumberField.setError("Invalid phone number.");
        return false;
    }

    private void enableViews(View... views) {
        for (View v : views) {
            v.setEnabled(true);
        }
    }

    private void disableViews(View... views) {
        for (View v : views) {
            v.setEnabled(false);
        }
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonResend:
                resendVerificationCode(this.mPhoneNumberField.getText().toString(), this.mResendToken);
                return;
            case R.id.buttonStartVerification:
                if (validatePhoneNumber()) {
                    startPhoneNumberVerification(this.mPhoneNumberField.getText().toString());
                    Toast.makeText(getApplicationContext(), "Code sent", Toast.LENGTH_LONG).show();
                    return;
                }
                Toast.makeText(getApplicationContext(), "please enter valid phone number", Toast.LENGTH_LONG).show();
                return;
            case R.id.buttonVerifyPhone:
                String code = this.mVerificationField.getText().toString();
                if (TextUtils.isEmpty(code)) {
                    this.mVerificationField.setError("Cannot be empty.");
                    return;
                } else {
                    verifyPhoneNumberWithCode(this.mVerificationId, code);
                    Toast.makeText(getApplicationContext(), "Verifying Code...", Toast.LENGTH_LONG).show();
                    return;
                }
            default:
                return;
        }
    }

    /* access modifiers changed from: package-private */
    public void checkRegistration(String phoneNumber) {
        this.db.collection("Managers").document(phoneNumber).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            public void onComplete(Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = (DocumentSnapshot) task.getResult();
                    if (document.exists()) {
                        isRegistered = Boolean.parseBoolean(document.getData().get("isRegistered").toString());
                        if (PhoneAuthActivity.this.isRegistered) {
                            namePg = document.getData().get("namePg").toString();
                            addressPg = document.getData().get("addressPg").toString();
                            pinCodePg = document.getData().get("pinCodePg").toString();
                            emailPg = document.getData().get("emailPg").toString();
                            Intent intent = new Intent(PhoneAuthActivity.this, HomeActivity.class);
                            intent.putExtra("namePg", PhoneAuthActivity.this.namePg);
                            intent.putExtra("contactPg", PhoneAuthActivity.this.contactPg);
                            intent.putExtra("addressPg", PhoneAuthActivity.this.addressPg);
                            intent.putExtra("pinCodePg", PhoneAuthActivity.this.pinCodePg);
                            intent.putExtra("emailPg", PhoneAuthActivity.this.emailPg);
                            PhoneAuthActivity.this.startActivity(intent);
                            return;
                        }
                        Intent intent = new Intent(PhoneAuthActivity.this, RegisterActivtiy.class);
                        startActivity(intent);


                        return;
                    }
                    Intent intent = new Intent(PhoneAuthActivity.this, RegisterActivtiy.class);
                    startActivity(intent);
                }
            }
        });
    }
}
