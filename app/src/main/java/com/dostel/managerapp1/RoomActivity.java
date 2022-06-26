package com.dostel.managerapp1;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoomActivity extends AppCompatActivity {

    private FirebaseFirestore db;


    private FloatingActionButton buttonAddRoom;

    private TextView mTotalRooms;
    private TextView mVacantRooms;
    private TextView mSemiVacantRooms;


    private String totalRooms = "";
    private String vacantRooms = "";
    private String semiVacantRooms = "";
    private CollectionReference roomsCol;

    private int total = 0;
    private int vacant = 0;
    private int semiVacant = 0;
    private TextView tv1;
    private TextView tv2;
    private ImageView iv;


    private Dialog roomTypeDialog;

    private Dialog addSingleRoomDialog;

    private ProgressDialog dialog;

    // variables for single room popup
    private String strRoomNo;
    private String strFloorNo;
    private String strNoOfBeds;
    private String strRoomRent;
    private String strRoomRemark;

    private Boolean acPresent;
    private Boolean ventilationPresent;
    private Boolean washroomPresent;
    private Boolean isLargeRoom;
    private Boolean balconyPresent;
    private Boolean isCornerRoom;
    private Boolean isVacant;
    private Boolean isSemiVacant;


    private EditText roomNo;
    private EditText floorNo;
    private EditText noOfBeds;
    private EditText roomRent;
    private EditText roomRemark;

    private CheckBox ac;
    private CheckBox ventilation;
    private CheckBox washroom;
    private CheckBox largeRoom;
    private CheckBox balcony;
    private CheckBox cornerRoom;


    private String pgId;
    private String phNumber;
    private String pgAddress;
    private String roomDocID;

    private int totalNoOfRooms;
    private String roomNumber;
    private RecyclerView recyclerViewRooms;
    private RecyclerView.Adapter roomAdapter;
    private List<RoomListItem> roomList;


    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        RoomActivity.super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);

        buttonAddRoom = findViewById(R.id.buttonAddRoom);
        mTotalRooms = findViewById(R.id.text_view_total_rooms);
        mVacantRooms = findViewById(R.id.text_view_vacant);
        mSemiVacantRooms = findViewById(R.id.text_view_semi_vacant);
        db = FirebaseFirestore.getInstance();
        roomsCol = db.collection("Rooms");
        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
        iv = findViewById(R.id.iv);
        recyclerViewRooms = findViewById(R.id.recycler_view_rooms);

        dialog = new ProgressDialog(RoomActivity.this);
        dialog.setMessage("getting your data...");
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        totalNoOfRooms = 0;
        roomNumber = "";
        roomTypeDialog = new Dialog(RoomActivity.this);
        addSingleRoomDialog = new Dialog(RoomActivity.this);

        //getting pgId from HomeActivity
        Intent in = getIntent();
        pgId = in.getStringExtra("namePg");
        phNumber = in.getStringExtra("contactPg");
        pgAddress = in.getStringExtra("addressPg");

        roomDocID = this.phNumber + "_" + this.pgId + this.pgAddress + "_" + this.strRoomNo;
        //checking the current status of rooms
        //ie
        //total rooms
        //vacant rooms
        //semi vacant rooms

        //code for recycler view
        recyclerViewRooms.setHasFixedSize(true);
        recyclerViewRooms.setLayoutManager(new LinearLayoutManager(RoomActivity.this));

        roomList = new ArrayList<>();

        roomsCol.whereEqualTo("pgId", phNumber + "_" + pgId + "" + pgAddress.replace(" ", "_"))
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot q : queryDocumentSnapshots) {
                            total += 1;

                            if (q.getData().get("isVacant").toString().equals("true")) {
                                vacant += 1;
                            } else if (q.getData().get("isVacant").toString().equals("true")) {
                                semiVacant += 1;
                            }

                            roomNumber = q.getData().get("roomNumber").toString().trim();
                            RoomListItem room = new RoomListItem(roomNumber);
                            roomList.add(room);

                        }



                        totalRooms = String.valueOf(total);
                        mTotalRooms.setText(totalRooms);

                        vacantRooms = String.valueOf(vacant);
                        mVacantRooms.setText(vacantRooms);

                        semiVacantRooms = String.valueOf(semiVacant);
                        mSemiVacantRooms.setText(semiVacantRooms);

                        if (total > 0) {
                            tv1.setVisibility(View.GONE);
                            tv2.setVisibility(View.GONE);
                            iv.setVisibility(View.GONE);
                        }
                        dialog.dismiss();

                        roomAdapter = new RoomListAdapter(roomList, RoomActivity.this);
                        recyclerViewRooms.setAdapter(roomAdapter);
                    }
                });





    }




    public void showDialog(View v) {

        roomTypeDialog.setContentView(R.layout.room_type_popup);

        ImageView singleRoom;
        ImageView multiRoom;
        ImageView close;

        singleRoom = roomTypeDialog.findViewById(R.id.image_view_single_room);
        multiRoom = roomTypeDialog.findViewById(R.id.image_view_multi_room);
        close = roomTypeDialog.findViewById(R.id.image_view_close);


        multiRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RoomActivity.this, MultiRoomActivity.class);
                intent.putExtra("namePg", pgId);
                intent.putExtra("contactPg", phNumber);
                intent.putExtra("addressPg", pgAddress);
                startActivity(intent);
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                roomTypeDialog.dismiss();
            }
        });
        roomTypeDialog.show();

    }

    public void showSingleRoomPopup(View v) {
        addSingleRoomDialog.setContentView(R.layout.add_single_room_popup);


        addSingleRoomDialog.show();

        roomTypeDialog.dismiss();
        Button cancel;
        TextView add;

        //initialising components
        roomNo = addSingleRoomDialog.findViewById(R.id.edit_text_room_number);
        floorNo = addSingleRoomDialog.findViewById(R.id.edit_text_floor);
        noOfBeds = addSingleRoomDialog.findViewById(R.id.edit_text_beds);
        roomRent = addSingleRoomDialog.findViewById(R.id.edit_text_rent);
        roomRemark = addSingleRoomDialog.findViewById(R.id.edit_text_room_remark);

        ac = addSingleRoomDialog.findViewById(R.id.check_box_ac);
        ventilation = addSingleRoomDialog.findViewById(R.id.check_box_ventilation);
        washroom = addSingleRoomDialog.findViewById(R.id.check_box_washroom);
        largeRoom = addSingleRoomDialog.findViewById(R.id.check_box_large_room);
        balcony = addSingleRoomDialog.findViewById(R.id.check_box__balcony);
        cornerRoom = addSingleRoomDialog.findViewById(R.id.check_box_corner_room);

        cancel = addSingleRoomDialog.findViewById(R.id.button_cancel);
        add = addSingleRoomDialog.findViewById(R.id.text_view_add);


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addSingleRoomDialog.dismiss();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strRoomNo = roomNo.getText().toString().trim();
                strFloorNo = floorNo.getText().toString().trim();
                strNoOfBeds = noOfBeds.getText().toString().trim();
                strRoomRent = roomRent.getText().toString().trim();
                strRoomRemark = roomRemark.getText().toString().trim();

                acPresent = ac.isChecked();
                ventilationPresent = ventilation.isChecked();
                washroomPresent = washroom.isChecked();
                isLargeRoom = largeRoom.isChecked();
                balconyPresent = balcony.isChecked();
                isCornerRoom = cornerRoom.isChecked();
                isVacant = true;
                isSemiVacant = false;


                addRoomDetails();



            }
        });


    }


    /* access modifiers changed from: package-private */
    public void addRoomDetails() {
        // check whether all the fields entered by the user are correct
        if (this.roomNo.length() == 0) {
            roomNo.setError("room number is necessary");
            Toast.makeText(getApplicationContext(), "Please enter room number", Toast.LENGTH_LONG).show();
        } else if (this.floorNo.length() == 0) {
            floorNo.setError("floor is necessary");
            Toast.makeText(getApplicationContext(), "Please enter floor number", Toast.LENGTH_LONG).show();
        } else if (this.noOfBeds.length() == 0) {
            noOfBeds.setError("no. of beds is necessary");
            Toast.makeText(getApplicationContext(), "Please enter number of beds ", Toast.LENGTH_LONG).show();
        } else {


            //first of all check whether the room already exists or not
            db.collection("Rooms").document(roomDocID)
                    .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                public void onComplete(Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = (DocumentSnapshot) task.getResult();
                        if (document.exists()) {
                            Toast.makeText(RoomActivity.this, "Room already exists", Toast.LENGTH_LONG).show();


                        } else {
                            addNewRoom();
                        }
                    }
                    return;
                }
            });
        }
    }

    public void addNewRoom() {
        Map<String, Object> dataToSave = new HashMap<>();
        dataToSave.put("contactPg", this.phNumber);
        dataToSave.put("roomNumber", this.strRoomNo);
        dataToSave.put("floor", this.strFloorNo);
        dataToSave.put("beds", this.strNoOfBeds);
        dataToSave.put("rent", this.strRoomRent);
        dataToSave.put("roomRemark", this.strRoomRemark);
        dataToSave.put("acPresent", Boolean.valueOf(this.acPresent));
        dataToSave.put("washroomPresent", Boolean.valueOf(this.washroomPresent));
        dataToSave.put("balconyPresent", Boolean.valueOf(this.balconyPresent));
        dataToSave.put("ventilationPresent", Boolean.valueOf(this.ventilationPresent));
        dataToSave.put("largeRoom", Boolean.valueOf(this.isLargeRoom));
        dataToSave.put("cornerRoom", Boolean.valueOf(this.isCornerRoom));
        dataToSave.put("isVacant", Boolean.valueOf(this.isVacant));
        dataToSave.put("isSemiVacant", Boolean.valueOf(this.isSemiVacant));
        dataToSave.put("pgId", phNumber + "_" + pgId + "" + pgAddress.replace(" ", "_"));

        CollectionReference collection = this.db.collection("Rooms");


        collection.document(phNumber + "_" + pgId + "" + pgAddress.replace(" ", "_") + "_" + this.strRoomNo).set(dataToSave, SetOptions.merge()).addOnSuccessListener(
                new OnSuccessListener<Void>() {
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(RoomActivity.this, "Room added successfully!", Toast.LENGTH_LONG).show();

                        addSingleRoomDialog.dismiss();
                        Intent intent = new Intent(RoomActivity.this, RoomActivity.class);
                        intent.putExtra("namePg", pgId);
                        intent.putExtra("contactPg", phNumber);
                        intent.putExtra("addressPg", pgAddress);
                        startActivity(intent);

                    }
                }).addOnFailureListener(new OnFailureListener() {
            public void onFailure(Exception e) {
                Toast.makeText(RoomActivity.this, "Something went Wrong", Toast.LENGTH_LONG).show();
            }
        });
    }

    //    @Override
//    public void onBackPressed() {
//        if(!isRoomAdded) {
//            Toast.makeText(getApplicationContext(), "Please Add Room", Toast.LENGTH_SHORT).show();
//        }else {
//            super.onBackPressed();
//        }
//    }


}