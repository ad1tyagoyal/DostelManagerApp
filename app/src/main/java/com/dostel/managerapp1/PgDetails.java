package com.dostel.managerapp1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class PgDetails extends AppCompatActivity {
    private String namePgValue;
    private TextView nameOfPg;
    private TextView clickToAdddetails;
    private LinearLayout linearLayoutTitle;
    private CardView cardViewPgProperties;
    private CardView cardViewTermsOfService;
    private CardView cardViewPersonaldetails;
    private CardView cardViewOtherdetails;
    private ImageView buttonBackArrow;
    private String addressPgValue;
    private String emailPgValue;
    private String pinCodePgValue;
    private String contactPgFull;

    //To store the id of PgCollectionDocument
    private String documentId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pg_details);
        //Assigning Views
        nameOfPg = findViewById(R.id.textViewPgName);
        clickToAdddetails = findViewById(R.id.textViewClickToAddDetails);
        linearLayoutTitle = findViewById(R.id.linearLayoutTitle);
        buttonBackArrow = findViewById(R.id.imageButtonBack);
        cardViewPgProperties = findViewById(R.id.cardViewPgProperty);
        cardViewTermsOfService = findViewById(R.id.cardViewTermsOfService);
        cardViewPersonaldetails = findViewById(R.id.cardViewPersonalDetails);
        cardViewOtherdetails = findViewById(R.id.cardViewOtherDetails);
        cardViewPersonaldetails = findViewById(R.id.cardViewPersonalDetails);


        namePgValue = getIntent().getStringExtra("namePg");
        addressPgValue = getIntent().getStringExtra("addressPg");
        this.contactPgFull = getIntent().getStringExtra("contactPg");
        this.emailPgValue = getIntent().getStringExtra("emailPg");
        this.pinCodePgValue = getIntent().getStringExtra("pinCodePg");
        documentId = contactPgFull + "_" + namePgValue.replaceAll(" ", "_") + addressPgValue.replaceAll(" ", "_");


        nameOfPg.setText(namePgValue);

        cardViewPgProperties.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(PgDetails.this, PgProperties.class);
                intent.putExtra("namePg", PgDetails.this.namePgValue);
                intent.putExtra("contactPg", contactPgFull);
                intent.putExtra("emailPg", PgDetails.this.emailPgValue);
                intent.putExtra("pinCodePg", PgDetails.this.pinCodePgValue);
                intent.putExtra("documentId", PgDetails.this.documentId);
                startActivity(intent);

            }
        });
        cardViewTermsOfService.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(PgDetails.this, TermsOfService.class);
                intent.putExtra("namePg", PgDetails.this.namePgValue);
                intent.putExtra("contactPg", contactPgFull);
                intent.putExtra("emailPg", PgDetails.this.emailPgValue);
                intent.putExtra("pinCodePg", PgDetails.this.pinCodePgValue);
                intent.putExtra("documentId", PgDetails.this.documentId);
                startActivity(intent);

            }
        });
        cardViewPersonaldetails.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(PgDetails.this, PersonalDetails.class);
                intent.putExtra("namePg", PgDetails.this.namePgValue);
                intent.putExtra("contactPg", contactPgFull);
                intent.putExtra("emailPg", PgDetails.this.emailPgValue);
                intent.putExtra("pinCodePg", PgDetails.this.pinCodePgValue);
                intent.putExtra("documentId", PgDetails.this.documentId);
                startActivity(intent);

            }
        });
        cardViewOtherdetails.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(PgDetails.this, OtherDetails.class);
                intent.putExtra("namePg", PgDetails.this.namePgValue);
                intent.putExtra("contactPg", contactPgFull);
                intent.putExtra("emailPg", PgDetails.this.emailPgValue);
                intent.putExtra("pinCodePg", PgDetails.this.pinCodePgValue);
                intent.putExtra("documentId", PgDetails.this.documentId);
                startActivity(intent);

            }
        });
        linearLayoutTitle.setOnClickListener(new View.OnClickListener() {
            /* JADX WARNING: type inference failed for: r1v0, types: [android.content.Context, com.dostel.managerapp1.HomeActivity] */
            public void onClick(View v) {

                Intent intent = new Intent(PgDetails.this, PgProperties.class);
                intent.putExtra("namePg", PgDetails.this.namePgValue);
                intent.putExtra("contactPg", contactPgFull);
                intent.putExtra("emailPg", PgDetails.this.emailPgValue);
                intent.putExtra("pinCodePg", PgDetails.this.pinCodePgValue);
                intent.putExtra("documentId", PgDetails.this.documentId);

                startActivity(intent);

            }
        });
        buttonBackArrow.setOnClickListener(new View.OnClickListener() {
            /* JADX WARNING: type inference failed for: r1v0, types: [android.content.Context, com.dostel.managerapp1.HomeActivity] */
            public void onClick(View v) {
                startActivity(new Intent(PgDetails.this, HomeActivity.class));
            }
        });
    }
//Function to generate alpha numeric String
    /*public static String getAlphaNumericString(int n)
    {

        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                    = (int)(AlphaNumericString.length()
                    * Math.random());

            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                    .charAt(index));
        }

        return sb.toString();
    }*/
}
