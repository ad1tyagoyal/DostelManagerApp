package com.dostel.managerapp1;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class PersonalDetails extends AppCompatActivity {

    private FirebaseFirestore db;
    private EditText mOwnerName;
    private EditText mContact;
    private EditText mEmail;
    private EditText mAccountHolderName;
    private EditText mAccountNumber;
    private EditText mIfscCode;
    private EditText mBankNameAndBranch;
    private EditText mUpiId;
    private Button buttonSavePersonalDetails;

    private String pgOwnerName;
    private String personalContact;
    private String personalEmail;
    private String accountHolderName;
    private String accountNumber;
    private String ifscCode;
    private String bankNameAndBranch;
    private String upiId;

    private String documentId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_details);

        documentId = getIntent().getStringExtra("documentId");

        initComponents();

        buttonSavePersonalDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pgOwnerName = mOwnerName.getText().toString().trim();
                personalContact = "+91" + mContact.getText().toString().trim();
                //Limit of this textbox has been set to 10 so the user will not be able to enter with std code
                personalEmail = mEmail.getText().toString().trim();
                accountHolderName = mAccountHolderName.getText().toString().trim();
                accountNumber = mAccountNumber.getText().toString().trim();
                ifscCode = mIfscCode.getText().toString().trim();
                bankNameAndBranch = mBankNameAndBranch.getText().toString().trim();
                upiId = mUpiId.getText().toString().trim();

                saveDetails();
            }
        });
    }


    public void initComponents() {
        mOwnerName = findViewById(R.id.edit_text_owner_name);
        mContact = findViewById(R.id.edit_text_personal_contact);
        mEmail = findViewById(R.id.edit_text_personal_email);
        mAccountHolderName = findViewById(R.id.edit_text_account_holder_name);
        mAccountNumber = findViewById(R.id.edit_text_account_number);
        mIfscCode = findViewById(R.id.edit_text_account_ifsc_code);
        mBankNameAndBranch = findViewById(R.id.edit_text_bank_name_and_branch);
        mUpiId = findViewById(R.id.edit_text_upi_id);
        buttonSavePersonalDetails = findViewById(R.id.button_save_personal_details);
        db = FirebaseFirestore.getInstance();
    }

    private void saveDetails() {

        if (pgOwnerName.equals(""))
            mOwnerName.setError("Please enter owner name");
        else if (personalContact.equals("+91"))
            mContact.setError("Please enter contact number");
        else if (personalEmail.equals("+91"))
            mEmail.setError("Please enter personal email Id");
        else {

            Map<String, Object> dataToSave = new HashMap<>();
            dataToSave.put("ownerNamePg", pgOwnerName);
            dataToSave.put("personalEmail", personalEmail);
            dataToSave.put("personalContact", personalContact);
            dataToSave.put("accountHolderName", accountHolderName);
            dataToSave.put("accountNumber", accountNumber);
            dataToSave.put("IFSCcode", ifscCode);
            dataToSave.put("bankNameAndBranch", bankNameAndBranch);
            dataToSave.put("upiID", upiId);


            db.collection("PgCollection").document(documentId).set(dataToSave, SetOptions.merge())
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(PersonalDetails.this, "Personal details added successfully", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                public void onFailure(Exception e) {
                    Toast.makeText(PersonalDetails.this, "Something went Wrong! Try again", Toast.LENGTH_LONG).show();
                }
            });


        }
    }


}
