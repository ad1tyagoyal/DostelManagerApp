package com.dostel.managerapp1;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class OtherDetails extends AppCompatActivity {

    private FirebaseFirestore db;
    private Spinner spinnerPGType;
    private Spinner spinnerTenantsType;
    private EditText mNoOfRooms;
    private EditText mNoOfBeds;
    private EditText mNoOfStaff;
    private EditText mNoOfFloors;
    private EditText mLastEntryTime;
    private TextView msetPgLocation;
    private Button mSaveOtherDetails;
    //variables define by us

    private String noOfRooms;
    private String noOfBeds;
    private String noOfStaff;
    private String noOfFloors;
    private String lastEntryTime;
    private String tenantsType;
    private String pgType;
    private String documentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_details);

        documentId = getIntent().getStringExtra("documentId");


        initComponents();

        // array adapter for pgType Spinner
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.pg_type, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPGType.setAdapter(adapter1);
        spinnerPGType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                pgType = parent.getItemAtPosition(position).toString().trim();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        // array adapter for tenantsType Spinner
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.tenants_type, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTenantsType.setAdapter(adapter2);
        spinnerTenantsType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tenantsType = parent.getItemAtPosition(position).toString().trim();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        msetPgLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mSaveOtherDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                noOfRooms = mNoOfRooms.getText().toString().trim();
                noOfBeds = mNoOfBeds.getText().toString().trim();
                noOfStaff = mNoOfStaff.getText().toString().trim();
                noOfFloors = mNoOfFloors.getText().toString().trim();
                lastEntryTime = mLastEntryTime.getText().toString().trim();

                saveDetails();

            }
        });
    }

    public void initComponents() {
        spinnerPGType = findViewById(R.id.spinner_pg_type);
        spinnerTenantsType = findViewById(R.id.spinner_tenants_preferred);
        mNoOfRooms = findViewById(R.id.edit_text__number_of_rooms);
        mNoOfBeds = findViewById(R.id.edit_text__number_of_beds);
        mNoOfStaff = findViewById(R.id.edit_text__number_of_staff);
        mNoOfFloors = findViewById(R.id.edit_text__number_of_floors);
        mLastEntryTime = findViewById(R.id.edit_text__last_entry_time);
        db = FirebaseFirestore.getInstance();
        msetPgLocation = findViewById(R.id.text_view_set_pg_location);
        mSaveOtherDetails = findViewById(R.id.button_save_other_details);
    }


    private void saveDetails() {
        if (noOfRooms.equals(""))
            mNoOfRooms.setError("Please enter number of rooms");
        else if (noOfBeds.equals(""))
            mNoOfBeds.setError("Please enter number of beds");
        else if (noOfFloors.equals(""))
            mNoOfFloors.setError("Please enter number of floors");
        else if (lastEntryTime.equals(""))
            mLastEntryTime.setError("Please enter last entry time");
        else {

            Map<String, Object> dataToSave = new HashMap<>();
            dataToSave.put("typePg", pgType);
            dataToSave.put("tenantType", tenantsType);
            dataToSave.put("totalBeds", noOfBeds);
            dataToSave.put("totalRooms", noOfRooms);
            dataToSave.put("totalStaff", noOfStaff);
            dataToSave.put("totalFloors", noOfFloors);
            dataToSave.put("lastEntryTime", lastEntryTime);


            db.collection("PgCollection").document(documentId).set(dataToSave, SetOptions.merge())
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(OtherDetails.this, "Others details saved successfully", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                public void onFailure(Exception e) {
                    Toast.makeText(OtherDetails.this, "Something went Wrong! Try again", Toast.LENGTH_LONG).show();
                }
            });

        }
    }
}