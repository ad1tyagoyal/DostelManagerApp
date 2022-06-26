package com.dostel.managerapp1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class PassBookActivity extends AppCompatActivity {

    private TextView mButtonIncome;
    private TextView mButtonDues;
    private String pgId;
    private CardView duesBottomBar;
    private CardView incomeBottomBar;

    private LinearLayout linearLayoutPassBookTop;

    private CardView passBookTop;
    private LinearLayout passBookTopContainer;

    private TextView tvPassBook;
    private TextView tvDues;
    private TextView tvIncome;
    private TextView tvDueIncomeTitle;
    private TextView tvDueIncomeAmount;

    private int intDueAmount;
    private int intIncomeAmount;

    //variables for dues recycler view

    private FirebaseFirestore db;

    private RecyclerView recyclerViewDues;
    private List<DueListItem> duesList;
    private RecyclerView.Adapter dueListAdapter;


    private ProgressDialog dialog;

    private String dueAmount;
    private String dueLateDays;
    private String dueType;
    private String dueIssueDate;
    private String dueTenantName;
    private String dueTenantRoomNo;
    private String dueDocID;


    //variables for income recycler view
    private RecyclerView recyclerViewIncome;
    private List<IncomeListItem> incomeList;
    private RecyclerView.Adapter incomeListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_book);

        Intent in = getIntent();
        pgId = in.getStringExtra("pgId");


        db = FirebaseFirestore.getInstance();

        mButtonIncome = findViewById(R.id.text_view_income_button);
        mButtonDues = findViewById(R.id.text_view_dues_button);
        passBookTop = findViewById(R.id.card_view_pass_book_top);

        linearLayoutPassBookTop = findViewById(R.id.linear_layout_pass_book_top);
        recyclerViewDues = findViewById(R.id.recycler_view_dues);
        recyclerViewIncome = findViewById(R.id.recycler_view_incomes);


        linearLayoutPassBookTop = findViewById(R.id.linear_layout_pass_book_top);
        tvPassBook = findViewById(R.id.text_view_pass_book);
        tvDues = findViewById(R.id.text_view_dues);
        tvIncome = findViewById(R.id.text_view_income);
        tvDueIncomeTitle = findViewById(R.id.text_view_total_dues_income_title);
        tvDueIncomeAmount = findViewById(R.id.text_view_amount_dues_and_income);

        duesBottomBar = findViewById(R.id.card_view_dues_bottom);
        incomeBottomBar = findViewById(R.id.card_view_income_bottom);


        intDueAmount = 0;
        intIncomeAmount = 0;


        dialog = new ProgressDialog(PassBookActivity.this);
        dialog.setMessage("getting your data...");
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();


        //by default we show due recycler view


        recyclerViewDues.setHasFixedSize(true);
        recyclerViewDues.setLayoutManager(new LinearLayoutManager(PassBookActivity.this));

        duesList = new ArrayList<>();

        recyclerViewIncome.setHasFixedSize(true);
        recyclerViewIncome.setLayoutManager(new LinearLayoutManager(PassBookActivity.this));

        incomeList = new ArrayList<>();

        //fetching data as soon as we open activity
        db.collection("Dues").whereEqualTo("pgId", pgId)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot q : queryDocumentSnapshots) {

                            dueAmount = q.getData().get("duesAmount").toString().trim();
                            dueLateDays = "5 days";
                            dueType = q.getData().get("dueType").toString().trim();
                            dueIssueDate = q.getData().get("dueDate").toString().trim();
                            dueTenantName = q.getData().get("nameTenant").toString().trim();
                            dueTenantRoomNo = q.getData().get("roomNo").toString().trim();
                            dueDocID = q.getData().get("dueDocId").toString().trim();


                            //isPaid == true ==> income
                            //isPaid == false ==> due
                            if (q.getData().get("isPaid").toString().equals("true")) {
                                IncomeListItem incomeListItem = new IncomeListItem(dueAmount, dueTenantName, dueType, dueIssueDate);
                                incomeList.add(incomeListItem);
                                intIncomeAmount += Integer.parseInt(dueAmount);
                            } else if (q.getData().get("isPaid").toString().equals("false")) {
                                DueListItem dueListItem = new DueListItem(dueAmount, dueLateDays, dueType, dueIssueDate, dueTenantName, dueTenantRoomNo, dueDocID, pgId);
                                duesList.add(dueListItem);
                                intDueAmount += Integer.parseInt(dueAmount);
                            }


                        }

                        tvDueIncomeAmount.setText(String.valueOf(intDueAmount));
                        tvDueIncomeTitle.setText("TOTAL DUES");

                        dueListAdapter = new DueListAdapter(duesList, PassBookActivity.this);
                        recyclerViewDues.setAdapter(dueListAdapter);

                        incomeListAdapter = new IncomeListAdapter(incomeList, PassBookActivity.this);
                        recyclerViewIncome.setAdapter(incomeListAdapter);


                        dialog.dismiss();

                    }
                });


        mButtonIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mButtonIncome.setTypeface(null, Typeface.BOLD);
                mButtonDues.setTypeface(null, Typeface.NORMAL);

                incomeBottomBar.setVisibility(View.VISIBLE);
                duesBottomBar.setVisibility(View.INVISIBLE);
                linearLayoutPassBookTop.setBackground(getDrawable(R.drawable.green_bg));
                tvDues.setVisibility(View.GONE);
                tvIncome.setVisibility(View.VISIBLE);
                recyclerViewDues.setVisibility(View.GONE);
                recyclerViewIncome.setVisibility(View.VISIBLE);
                tvDueIncomeAmount.setText(String.valueOf(intIncomeAmount));
                tvDueIncomeTitle.setText("TOTAL INCOME");



            }
        });

        mButtonDues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mButtonIncome.setTypeface(null, Typeface.NORMAL);
                mButtonDues.setTypeface(null, Typeface.BOLD);

                incomeBottomBar.setVisibility(View.INVISIBLE);
                duesBottomBar.setVisibility(View.VISIBLE);
                linearLayoutPassBookTop.setBackground(getDrawable(R.drawable.red_bg));
                tvIncome.setVisibility(View.GONE);
                tvDues.setVisibility(View.VISIBLE);
                recyclerViewIncome.setVisibility(View.GONE);
                recyclerViewDues.setVisibility(View.VISIBLE);
                tvDueIncomeAmount.setText(String.valueOf(intDueAmount));
                tvDueIncomeTitle.setText("TOTAL DUES");
            }
        });
    }
}
