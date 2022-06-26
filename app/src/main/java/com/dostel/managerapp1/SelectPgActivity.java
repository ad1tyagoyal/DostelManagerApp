package com.dostel.managerapp1;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class SelectPgActivity extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String namePgValue;
    private String addressPg;
    private String documentId;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<PgListItem> pgListItems;
    private int pgCount;
    private String managerPhNumber = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();
    private String nameOfPg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_pg);
        // initialising the recyclerview
        recyclerView = findViewById(R.id.recyclerViewSelectPg);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // initialising the list
        pgListItems = new ArrayList<>();
        namePgValue = getIntent().getStringExtra("namePg");
        addressPg = getIntent().getStringExtra("addressPg");
        documentId = managerPhNumber + "_" + namePgValue.replaceAll(" ", "_") + addressPg.replaceAll(" ", "_");


        db.collection("PgCollection")
                .document(documentId).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    public void onComplete(Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = (DocumentSnapshot) task.getResult();
                            if (document.exists()) {
                                Toast.makeText(getApplicationContext(), "fetching data", Toast.LENGTH_LONG).show();
                                pgCount = Integer.parseInt(document.getData().get("noOfPgs").toString());
                                for (int i = 0; i < pgCount; i++) {
                                    if (i == 0) {
                                        nameOfPg = document.getData().get("namePg").toString();
                                    } else {
                                        nameOfPg = document.getData().get("namePg" + String.valueOf(i + 1)).toString();

                                    }
                                    PgListItem listItem = new PgListItem(nameOfPg);
                                    pgListItems.add(listItem);
                                }
                                makeRecyclerElements();
                            }
                            ;
                        }

                    }
                });
    }

    public void makeRecyclerElements() {

        adapter = new PgListAdapter(pgListItems, this, nameOfPg);
        Toast.makeText(getApplicationContext(), "PG list size is" + pgListItems.size(), Toast.LENGTH_LONG).show();
        // now to link the adapter to the recyclerView
        recyclerView.setAdapter(adapter);
        Toast.makeText(getApplicationContext(), "recyclerview linked with adapter", Toast.LENGTH_LONG).show();

    }
}
