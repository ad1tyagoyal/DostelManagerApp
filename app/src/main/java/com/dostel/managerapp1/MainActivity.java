package com.dostel.managerapp1;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.firebase.client.Firebase;

public class MainActivity extends AppCompatActivity {

    private ProgressDialog dialog;


    private EditText etEmail;
    private EditText etPassword;
    private TextView tvForgetPassword;
    private Button button_LogIn;
    private Button buttonRegisterIfNew;
    private ImageView ivGoogleSignIn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initComponents();
        Firebase.setAndroidContext(MainActivity.this);


        buttonRegisterIfNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivtiy.class);
                startActivity(intent);
            }
        });

        button_LogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();

                dialog = new ProgressDialog(MainActivity.this);
                dialog.setMessage("Submitting Details...");
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();

            }
        });
    }

    public void initComponents() {

        etEmail = findViewById(R.id.edit_text_email);
        etPassword = findViewById(R.id.edit_text_password);
        tvForgetPassword = findViewById(R.id.text_view_forget_password);
        button_LogIn = findViewById(R.id.button_login);
        buttonRegisterIfNew = findViewById(R.id.button_Register_If_New);
        ivGoogleSignIn = findViewById(R.id.button_google_signin);

    }
}
