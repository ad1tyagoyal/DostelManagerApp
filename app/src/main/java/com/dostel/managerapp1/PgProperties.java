package com.dostel.managerapp1;

import android.app.ProgressDialog;
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

public class PgProperties extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ProgressDialog dialog;
    private String contactPgFull;


    private EditText etPgName;
    private EditText etPgEmail;
    private EditText etPgAddressLine1;
    private EditText etPgAddressLine2;
    private EditText etCity;
    private EditText etState;
    private EditText etPoliceStation;
    private EditText etPinCode;
    private EditText etNearestLandmark;

    private String namePg;
    private String emailPg;

    private String addressPgline1;
    private String addressPgline2;
    private String addressPgfinal;
    private String cityPg;
    private String statePg;
    private String policeStationPg;
    private String pinCodePg;
    private String nearestLandmarkPg;
    private Button buttonSubmitDetails;

    private String documentId;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pg_properties);

        //getting values from intent
        namePg = getIntent().getStringExtra("namePg");
        contactPgFull = getIntent().getStringExtra("contactPg");
        documentId = getIntent().getStringExtra("documentId");
        emailPg = getIntent().getStringExtra("emailPg");
        pinCodePg = getIntent().getStringExtra("pinCodePg");

        //contains 10 digit phone number. Store in database after adding +91


        //Assigning
        etPgName = findViewById(R.id.edit_text_pg_name);
        etPgEmail = findViewById(R.id.edit_text_pg_email);
        etPgAddressLine1 = findViewById(R.id.edit_text_address_line1);
        etPgAddressLine2 = findViewById(R.id.edit_text_address_line2);
        etCity = findViewById(R.id.edit_text_city);
        etState = findViewById(R.id.edit_text_state);
        etPoliceStation = findViewById(R.id.edit_text_police_station);
        etPinCode = findViewById(R.id.edit_text_pin_code);
        etNearestLandmark = findViewById(R.id.edit_text_nearest_landmark);
        buttonSubmitDetails = findViewById(R.id.buttonSubmitPgDetails);

//Setting the values that have to be already displayed when the user enters the activity
        etPgName.setText(namePg);
        etPgEmail.setText(emailPg);
        etPinCode.setText(pinCodePg);
        // also the user can change these values by clicking on them


        buttonSubmitDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                namePg = etPgName.getText().toString();
                emailPg = etPgEmail.getText().toString();
                pinCodePg = etPinCode.getText().toString();
                addressPgline1 = etPgAddressLine1.getText().toString();
                addressPgline2 = etPgAddressLine2.getText().toString();
                addressPgfinal = addressPgline1 + " " + addressPgline2;
                cityPg = etCity.getText().toString();
                statePg = etState.getText().toString();
                policeStationPg = etPoliceStation.getText().toString();
                nearestLandmarkPg = etNearestLandmark.getText().toString();
                submitPgDetails();
            }
        });


    }

    public void submitPgDetails() {
        if (namePg.length() == 0) {
            etPgName.setError("Name of PG required.");
        } else if (emailPg.length() == 0) {
            etPgEmail.setError("email of PG required.");
        } else if (addressPgline1.length() == 0) {
            etPgAddressLine1.setError("Address of PG required.");
        } else if (cityPg.length() == 0) {
            etCity.setError("City of PG required.");
        } else if (this.statePg.length() == 0) {
            this.etState.setError("State required.");
        } else if (this.policeStationPg.length() == 0) {
            this.etPoliceStation.setError("Police Station required.");
        } else if (this.nearestLandmarkPg.length() == 0) {
            this.etNearestLandmark.setError("Nearest landmark required.");

        } else if (this.pinCodePg.length() == 0) {
            this.etPinCode.setError("PIN Code required.");
        } else {
            Map<String, Object> dataToSave = new HashMap<>();
            dataToSave.put("addressPg", this.addressPgfinal);
            dataToSave.put("namePg", this.namePg);
            dataToSave.put("managerId", contactPgFull);
            dataToSave.put("emailPg", this.emailPg);
            dataToSave.put("pinCodePg", this.pinCodePg);
            dataToSave.put("cityPg", this.cityPg);
            dataToSave.put("statePg", this.statePg);
            dataToSave.put("policeStationPg", this.policeStationPg);
            dataToSave.put("nearestLandmarkPg", this.nearestLandmarkPg);
            CollectionReference collection = this.db.collection("PgCollection");
            collection.document(documentId).set(dataToSave, SetOptions.merge()).addOnSuccessListener(
                    new OnSuccessListener<Void>() {

                        public void onSuccess(Void aVoid) {
                            Toast.makeText(PgProperties.this.getApplicationContext(), "Details Added Successfully", Toast.LENGTH_LONG).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                public void onFailure(Exception e) {
                    Toast.makeText(PgProperties.this, "Something went Wrong! Try again", Toast.LENGTH_LONG).show();
                }
            });
        }
    }
   /* public static String getAlphaNumericString(int n)
    {

        // chose a Character random from this String
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
