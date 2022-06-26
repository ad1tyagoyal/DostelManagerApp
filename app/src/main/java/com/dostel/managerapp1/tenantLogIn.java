package com.dostel.managerapp1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class tenantLogIn extends AppCompatActivity {

    private LinearLayout mComplaintTenant;
    private LinearLayout hostAFriendTab;
    private Dialog hostAFriendDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenant_log_in);

        mComplaintTenant = findViewById(R.id.complaint_tab_tenant);
        hostAFriendTab = findViewById(R.id.host_a_friend_tab);

        hostAFriendDialog = new Dialog(tenantLogIn.this);



        mComplaintTenant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(tenantLogIn.this, TenantComplaint.class);
                startActivity(intent);
            }
        });

        hostAFriendTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




            }
        });





    }



}
