package com.dostel.managerapp1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {
    private TextView clickToAddDetails;
    private FirebaseAuth mAuth;
    private ProgressDialog dialog;
    private CardView mComplaintsCardView;
    private CardView mFoodCardView;
    private CardView mRentAndBillsCardView;
    private CardView mAccountsCardView;


    /* access modifiers changed from: private */
    public TextView mNamePg;

    private CardView mRoomsCardView;
    private CardView mTenantCardView;
    private CardView mPassbookCardView;
    private Button nMenuButton;
    private String namePgValue;
    private String addressPg;
    private String contactPgFull;
    private String emailPgValue;
    private String pinCodePgValue;
    private String numberOfPgs;

    private Button signout;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        HomeActivity.super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        this.namePgValue = getIntent().getStringExtra("namePg");
        this.addressPg = getIntent().getStringExtra("addressPg");
        this.emailPgValue = getIntent().getStringExtra("emailPg");
        this.contactPgFull = getIntent().getStringExtra("contactPg");
        this.pinCodePgValue = getIntent().getStringExtra("pinCodePg");


        this.mAuth = FirebaseAuth.getInstance();
        this.clickToAddDetails = (TextView) findViewById(R.id.homeTvClickToAddDetails);
        this.mNamePg = (TextView) findViewById(R.id.pgName);
        this.signout = (Button) findViewById(R.id.signOut);
        this.mRoomsCardView = findViewById(R.id.cardViewHome01);
        this.mPassbookCardView = findViewById(R.id.cardViewHome02);
        this.mNamePg.setText(this.namePgValue);
        this.clickToAddDetails.setOnClickListener(new View.OnClickListener() {
            /* JADX WARNING: type inference failed for: r1v0, types: [android.content.Context, com.dostel.managerapp1.HomeActivity] */
            public void onClick(View v) {

                Intent intent = new Intent(HomeActivity.this, PgDetails.class);
                // we are sending the users details to pgDetails Activity

                intent.putExtra("namePg", HomeActivity.this.mNamePg.getText().toString());
                intent.putExtra("contactPg", contactPgFull);
                intent.putExtra("addressPg", addressPg);
                intent.putExtra("emailPg", HomeActivity.this.emailPgValue);
                intent.putExtra("pinCodePg", HomeActivity.this.pinCodePgValue);

                HomeActivity.this.startActivity(intent);
            }
        });
        this.mRoomsCardView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, RoomActivity.class);
                intent.putExtra("namePg", HomeActivity.this.mNamePg.getText().toString());
                intent.putExtra("contactPg", contactPgFull);
                intent.putExtra("addressPg", addressPg);
                HomeActivity.this.startActivity(intent);
            }
        });
        this.mPassbookCardView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
//                dialog = new ProgressDialog(HomeActivity.this);
//                dialog.setMessage("getting your data...");
//                dialog.setCanceledOnTouchOutside(false);
//                dialog.show();
//                Intent intent = new Intent(HomeActivity.this, SelectPgActivity.class);
//                intent.putExtra("namePg", HomeActivity.this.mNamePg.getText().toString());
//                intent.putExtra("contactPg", contactPgFull);
//                intent.putExtra("addressPg", addressPg);
//                HomeActivity.this.startActivity(intent);
//                dialog.dismiss();


                Intent intent = new Intent(HomeActivity.this, PassBookActivity.class);
                intent.putExtra("pgId", contactPgFull + "_" + HomeActivity.this.mNamePg.getText().toString() + "" + addressPg.replace(" ", "_"));
                startActivity(intent);



            }
        });

        this.signout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                HomeActivity.this.signout();
            }
        });
    }

    public void signout() {
//        this.mAuth.signOut();
        startActivity(new Intent(HomeActivity.this, tenantLogIn.class));
    }
}
