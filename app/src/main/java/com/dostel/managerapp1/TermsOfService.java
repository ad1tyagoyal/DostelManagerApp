package com.dostel.managerapp1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class TermsOfService extends AppCompatActivity {


    private FirebaseFirestore db;

    //variables for UI components
    private RadioGroup mRadioGroupRentAddingDate;
    private RadioButton mRentDate;

    private RadioGroup mRadioGroupSeparateUtilityBills;
    private RadioButton mSeparateUtilityBill;

    private RadioGroup mRadioGroupOffersFood;
    private RadioButton mOffersFood;

    private EditText mDailyBedCharges;

    private Spinner mLockInPeriod;

    private Button mButtonDone;

    //variables declare by us to work with

    private String rentDate;
    private String separateUtilityBills;
    private String offersFood;
    private String dailyBedCharges;
    private String lockInPeriod;

    private String documentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_of_service);

        documentId = getIntent().getStringExtra("documentId");

        initComponents();

        ArrayAdapter<CharSequence> adapter1 =
                ArrayAdapter.createFromResource(this, R.array.locked_in_period, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mLockInPeriod.setAdapter(adapter1);
        mLockInPeriod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                lockInPeriod = parent.getItemAtPosition(position).toString().trim();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mButtonDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dailyBedCharges = mDailyBedCharges.getText().toString().trim();
                saveDetails();
            }
        });


    }
    /*private void setSpinnerError(Spinner spinner, String error){
        View selectedView = spinner.getSelectedView();
        if (selectedView != null && selectedView instanceof TextView) {
            spinner.requestFocus();
            TextView selectedTextView = (TextView) selectedView;
            selectedTextView.setError("error"); // any name of the error will do
            selectedTextView.setTextColor(Color.RED); //text color in which you want your error message to be displayed
            selectedTextView.setText(error); // actual error message
            spinner.performClick(); // to open the spinner list if error is found.

        }
    }*/

    public void getRentDate(View v) {
        int buttonId = mRadioGroupRentAddingDate.getCheckedRadioButtonId();
        mRentDate = findViewById(buttonId);
        rentDate = mRentDate.getText().toString().trim();
    }

    public void getUtilityCharge(View v) {
        int buttonId = mRadioGroupSeparateUtilityBills.getCheckedRadioButtonId();
        mSeparateUtilityBill = findViewById(buttonId);
        separateUtilityBills = mSeparateUtilityBill.getText().toString().trim();
    }

    public void getOffersFood(View v) {
        int buttonId = mRadioGroupOffersFood.getCheckedRadioButtonId();
        mOffersFood = findViewById(buttonId);
        offersFood = mOffersFood.getText().toString().trim();
    }

    //initialising UI components
    public void initComponents() {

        mRadioGroupRentAddingDate = findViewById(R.id.radio_group_rent_adding_date);
        mRadioGroupSeparateUtilityBills = findViewById(R.id.radio_group_utility_bill);
        mRadioGroupOffersFood = findViewById(R.id.radio_group_offers_food);

        mDailyBedCharges = findViewById(R.id.edit_text_daily_bed_charges);
        mLockInPeriod = findViewById(R.id.spinner_lock_in_period);
        mButtonDone = findViewById(R.id.button_save_terms_of_services);
        db = FirebaseFirestore.getInstance();
    }


    private void saveDetails() {

        if (rentDate.length() == 0) {
            mRentDate.setError("Please select date of paying rent");
        } else if (dailyBedCharges.length() == 0) {
            mDailyBedCharges.setError("Please add daily bed charges.");
        } else if (separateUtilityBills.length() == 0) {
            mSeparateUtilityBill.setError("select one choice");
        } else if (offersFood.length() == 0) {
            mOffersFood.setError("select one choice");
        }
        /*else if (lockInPeriod.length() == 0) {
            mLockInPeriod.setError("Address of PG required.");
        }*/

        else {
            Map<String, Object> dataToSave = new HashMap<>();
            dataToSave.put("rentDate", rentDate);
            dataToSave.put("separateUtilityBills", separateUtilityBills);
            dataToSave.put("dailyBedCharges", dailyBedCharges);
            dataToSave.put("offersFood", offersFood);
            dataToSave.put("lockInPeriod", lockInPeriod);


            db.collection("PgCollection").document(documentId).set(dataToSave, SetOptions.merge())
                    .addOnSuccessListener(
                            new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(TermsOfService.this, "Details added successfully", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(TermsOfService.this, PgDetails.class);
                                    startActivity(intent);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                public void onFailure(Exception e) {
                    Toast.makeText(TermsOfService.this, "Something went Wrong! Try again", Toast.LENGTH_LONG).show();
                }
            });
        }

    }
}
