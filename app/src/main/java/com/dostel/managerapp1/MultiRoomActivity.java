package com.dostel.managerapp1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;

import android.os.Bundle;

import android.view.View;

import android.widget.CheckBox;
import android.widget.EditText;

import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MultiRoomActivity extends AppCompatActivity {

    FirebaseFirestore db;
    private EditText roomNumbers;
    private EditText beds;
    private EditText rent;
    private CardView addRooms;
    private String pgId;
    private String phNumber;
    private String pgAddress;
    private String strRoomNo;
    private String strFloorNo;
    private String strBeds;
    private String strRent;
    private String roomDocId;

    private Boolean acPresent;
    private Boolean ventilationPresent;
    private Boolean washroomPresent;
    private Boolean isLargeRoom;
    private Boolean balconyPresent;
    private Boolean isCornerRoom;
    private Boolean isVacant;
    private Boolean isSemiVacant;

    private CheckBox ac;
    private CheckBox ventilation;
    private CheckBox washroom;
    private CheckBox largeRoom;
    private CheckBox balcony;
    private CheckBox cornerRoom;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_room);

        initComponents();

        Intent in = getIntent();
        pgId = in.getStringExtra("namePg");
        phNumber = in.getStringExtra("contactPg");
        pgAddress = in.getStringExtra("addressPg");


        addRooms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //making an array of room numbers
                String[] str = roomNumbers.getText().toString().trim().split(",");
                int[] n = new int[str.length];

                for (int j = 0; j < str.length; j++)
                    n[j] = Integer.parseInt(str[j]);


                acPresent = ac.isChecked();
                ventilationPresent = ventilation.isChecked();
                washroomPresent = washroom.isChecked();
                isLargeRoom = largeRoom.isChecked();
                balconyPresent = balcony.isChecked();
                isCornerRoom = cornerRoom.isChecked();
                isVacant = true;
                isSemiVacant = false;


                strBeds = beds.getText().toString().trim();
                strRent = rent.getText().toString().trim();

                //adding rooms to database
                for (int i = 0; i < n.length; i++) {

                    strRoomNo = String.valueOf(n[i]);
                    strFloorNo = String.valueOf(String.valueOf(n[i]).charAt(0));

                    roomDocId = phNumber + "_" + pgId + "" + pgAddress.replace(" ", "_") + "_" + strRoomNo;

                    Map<String, Object> dataToSave = new HashMap<>();
                    dataToSave.put("contactPg", phNumber);
                    dataToSave.put("roomNumber", strRoomNo);
                    dataToSave.put("floor", strFloorNo);
                    dataToSave.put("beds", strBeds);
                    dataToSave.put("rent", strRent);
                    dataToSave.put("roomRemark", null);
                    dataToSave.put("acPresent", Boolean.valueOf(acPresent));
                    dataToSave.put("washroomPresent", Boolean.valueOf(washroomPresent));
                    dataToSave.put("balconyPresent", Boolean.valueOf(balconyPresent));
                    dataToSave.put("ventilationPresent", Boolean.valueOf(ventilationPresent));
                    dataToSave.put("largeRoom", Boolean.valueOf(isLargeRoom));
                    dataToSave.put("cornerRoom", Boolean.valueOf(isCornerRoom));
                    dataToSave.put("isVacant", Boolean.valueOf(isVacant));
                    dataToSave.put("isSemiVacant", Boolean.valueOf(isSemiVacant));
                    dataToSave.put("pgId", phNumber + "_" + pgId + "" + pgAddress.replace(" ", "_"));

                    CollectionReference collection = db.collection("Rooms");


                    collection.document(roomDocId).set(dataToSave).addOnSuccessListener(
                            new OnSuccessListener<Void>() {
                                public void onSuccess(Void aVoid) {

                                    Toast.makeText(MultiRoomActivity.this, "Rooms added successfully!", Toast.LENGTH_LONG).show();


                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        public void onFailure(Exception e) {
                            Toast.makeText(MultiRoomActivity.this, "Something went Wrong", Toast.LENGTH_LONG).show();
                        }
                    });


                }


            }


        });


    }

    public void initComponents() {
        roomNumbers = findViewById(R.id.edit_text_room_num_multi);
        beds = findViewById(R.id.edit_text_beds_multi);
        addRooms = findViewById(R.id.btn_add_rooms);
        db = FirebaseFirestore.getInstance();

        ac = findViewById(R.id.check_box_ac_multi);
        balcony = findViewById(R.id.check_box__balcony_multi);
        washroom = findViewById(R.id.check_box_washroom_multi);
        cornerRoom = findViewById(R.id.check_box_corner_room_multi);
        largeRoom = findViewById(R.id.check_box_large_room_multi);
        ventilation = findViewById(R.id.check_box_ventilation_multi);
        rent = findViewById(R.id.edit_text_rent_multi);
    }

    /* access modifiers changed from: package-private */
    public void addRoomDetails() {


    }

    public void addNewRoom() {

    }


}
