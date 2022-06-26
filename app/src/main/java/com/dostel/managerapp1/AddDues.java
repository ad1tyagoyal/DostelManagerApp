package com.dostel.managerapp1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.Date;

public class AddDues extends AppCompatActivity {

    private RadioGroup mDueTypeRadioGroup;
    private RadioButton mDueType;
    private EditText mDueMonth;
    private EditText mDueDate;
    private EditText mDueAmount;
    private EditText mDueDescription;

    private String dueType;
    private String dueMonth;
    private String dueDate;
    private String dueAmount;
    private String dueDescription;

    private Button buttonAddMore;
    private TextView addDues;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dues);

        initComponents();


        dueMonth = mDueMonth.getText().toString().trim();
        dueDate = mDueDate.getText().toString().trim();
        dueAmount = mDueAmount.getText().toString().trim();
        dueDescription = mDueDescription.getText().toString().trim();

        buttonAddMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        addDues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }

    public void getDueType(View v) {
        int buttonId = mDueTypeRadioGroup.getCheckedRadioButtonId();
        mDueType = findViewById(buttonId);
        dueType = mDueType.getText().toString().trim();
    }

    public void initComponents() {
        mDueTypeRadioGroup = findViewById(R.id.radio_group_type_of_due);
        mDueMonth = findViewById(R.id.edit_text_dues_month);
        mDueDate = findViewById(R.id.edit_text_due_date);
        mDueAmount = findViewById(R.id.dit_text_due_amount);
        mDueDescription = findViewById(R.id.edit_text_due_date);
        buttonAddMore = findViewById(R.id.button_add_more_dues);
        addDues = findViewById(R.id.text_view_add_due);
    }
}
