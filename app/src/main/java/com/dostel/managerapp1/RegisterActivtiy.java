package com.dostel.managerapp1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivtiy extends AppCompatActivity {
    private String contactPgFull;
    /* access modifiers changed from: private */
    public String addressPg;
    private Button buttonRegister;
    /* access modifiers changed from: private */
    public String contactPg;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    /* access modifiers changed from: private */
    public ProgressDialog dialog;
    /* access modifiers changed from: private */
    public String emailPg;
    /* access modifiers changed from: private */
    public EditText etAddress;
    /* access modifiers changed from: private */
    public EditText etContact;
    /* access modifiers changed from: private */
    public EditText etEmail;
    /* access modifiers changed from: private */
    public EditText etName;
    /* access modifiers changed from: private */
    public EditText etPassword;
    /* access modifiers changed from: private */
    public EditText etPinCode;
    /* access modifiers changed from: private */
    public String namePg;
    /* access modifiers changed from: private */
    public String passwordPg;
    /* access modifiers changed from: private */
    public String pinCodePg;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        RegisterActivtiy.super.onCreate(savedInstanceState);
        setContentView(R.layout.activtiy_register);
        this.etEmail = (EditText) findViewById(R.id.etEmail);
        this.etPassword = (EditText) findViewById(R.id.etPassword);
        this.etName = (EditText) findViewById(R.id.etName);
        this.etContact = (EditText) findViewById(R.id.etContact);
        this.etAddress = (EditText) findViewById(R.id.etAddress);
        this.etPinCode = (EditText) findViewById(R.id.etPinCode);
        this.buttonRegister = (Button) findViewById(R.id.button_Register);

        this.buttonRegister.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                RegisterActivtiy registerActivtiy = RegisterActivtiy.this;
                String unused = registerActivtiy.emailPg = registerActivtiy.etEmail.getText().toString();
                RegisterActivtiy registerActivtiy2 = RegisterActivtiy.this;
                String unused2 = registerActivtiy2.namePg = registerActivtiy2.etName.getText().toString();
                RegisterActivtiy registerActivtiy3 = RegisterActivtiy.this;
                String unused3 = registerActivtiy3.addressPg = registerActivtiy3.etAddress.getText().toString();
                RegisterActivtiy registerActivtiy4 = RegisterActivtiy.this;
                String unused4 = registerActivtiy4.contactPg = registerActivtiy4.etContact.getText().toString();
                RegisterActivtiy registerActivtiy5 = RegisterActivtiy.this;
                String unused5 = registerActivtiy5.pinCodePg = registerActivtiy5.etPinCode.getText().toString();
                RegisterActivtiy registerActivtiy6 = RegisterActivtiy.this;
                String unused6 = registerActivtiy6.passwordPg = registerActivtiy6.etPassword.getText().toString();
                contactPgFull = "+91" + contactPg;
                dialog = new ProgressDialog(RegisterActivtiy.this);
                dialog.setMessage("Submitting Details...");
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();

                RegisterActivtiy.this.registerUser();
            }
        });
    }

    /* access modifiers changed from: package-private */
    public void registerUser() {
        if (this.emailPg.length() == 0) {
            this.etEmail.setError("Please enter email");
            this.dialog.dismiss();
        } else if (this.contactPg.length() == 0) {
            this.etContact.setError("Please enter contact number");
            this.dialog.dismiss();
        } else if (this.namePg.length() == 0) {
            this.etName.setError("Please enter name of PG ");
            this.dialog.dismiss();
        } else if (this.addressPg.length() == 0) {
            this.etAddress.setError("Please enter address of PG ");
            this.dialog.dismiss();
        } else if (this.pinCodePg.length() == 0) {
            this.etPinCode.setError("Please enter PIN code ");
            this.dialog.dismiss();
        } else if (this.passwordPg.length() == 0) {
            this.etPassword.setError("Please set a password");
            this.dialog.dismiss();
        } else {
            Map<String, Object> dataToSave = new HashMap<>();
            dataToSave.put("noOfPgs", 1);
            dataToSave.put("addressPg", this.addressPg);
            dataToSave.put("namePg", this.namePg);
            dataToSave.put("emailPg", this.emailPg);
            dataToSave.put("pinCodePg", this.pinCodePg);
            dataToSave.put("isRegistered", true);
            dataToSave.put("password", this.passwordPg);
            dataToSave.put("contactPg", contactPgFull);
            //we have to store phone number with std code in database to makee our search easy
            CollectionReference collection = this.db.collection("Managers");
            collection.document(contactPgFull).set(dataToSave, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
                /* JADX WARNING: type inference failed for: r1v1, types: [android.content.Context, com.dostel.managerapp1.RegisterActivtiy] */
                public void onSuccess(Void aVoid) {
                    Toast.makeText(RegisterActivtiy.this.getApplicationContext(), "Registration Successful", Toast.LENGTH_LONG).show();

                    RegisterActivtiy.this.dialog.dismiss();
                    Intent intent = new Intent(RegisterActivtiy.this, HomeActivity.class);
                    // sending all the data required to HomeActivity

                    intent.putExtra("namePg", RegisterActivtiy.this.namePg);
                    intent.putExtra("contactPg", contactPgFull);
                    intent.putExtra("emailPg", RegisterActivtiy.this.emailPg);
                    intent.putExtra("pinCodePg", RegisterActivtiy.this.pinCodePg);
                    intent.putExtra("addressPg", addressPg);
                    RegisterActivtiy.this.startActivity(intent);


                }
            }).addOnFailureListener(new OnFailureListener() {
                public void onFailure(Exception e) {
                    Toast.makeText(RegisterActivtiy.this, "Something went Wrong! Try again", Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}





