package com.dostel.managerapp1;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DueListAdapter extends RecyclerView.Adapter<DueListAdapter.ViewHolder> {

    private List<DueListItem> dueList;
    private Context context;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();



    public DueListAdapter(List<DueListItem> dueList, Context context) {
        this.dueList = dueList;
        this.context = context;
    }

    @NonNull
    @Override
    public DueListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dues_list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final DueListAdapter.ViewHolder holder, int position) {

        DueListItem dueListItem = dueList.get(position);
        holder.dueAmount.setText(dueListItem.getDueAmount());
        holder.dueLateDays.setText(dueListItem.getDueLateDays());
        holder.dueType.setText(dueListItem.getDueType());
        holder.dueIssueDate.setText(dueListItem.getDueIssueDate());
        holder.dueTenantName.setText(dueListItem.getDueTenantName());
        holder.dueTenantRoomNo.setText(dueListItem.getDueTenantRoomNo());
        holder.dueDocId = dueListItem.getDueDocId();
        holder.pgId = dueListItem.getPgId();
        holder.buttonRecieveDueNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> map = new HashMap<>();
                map.put("isPaid", true);
                db.collection("Dues").document(holder.dueDocId+"")
                        .set(map, SetOptions.merge())
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Intent intent = new Intent(context, PassBookActivity.class);
                                intent.putExtra("pgId", holder.pgId);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                context.startActivity(intent);
                            }
                        });

            }
        });
    }

    @Override
    public int getItemCount() {
        return dueList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView dueAmount;
        public TextView dueLateDays;
        public TextView dueType;
        public TextView dueIssueDate;
        public TextView dueTenantName;
        public TextView dueTenantRoomNo;
        public CardView dueListItem;
        public CardView buttonRecieveDueNow;
        public String dueDocId;
        public String pgId;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dueAmount = (TextView) itemView.findViewById(R.id.text_view_due_amount);
            dueLateDays = (TextView) itemView.findViewById(R.id.text_view_due_late_days);
            dueType = (TextView) itemView.findViewById(R.id.text_view_due_type);
            dueIssueDate = (TextView) itemView.findViewById(R.id.text_view_due_issue_date);
            dueTenantName = (TextView) itemView.findViewById(R.id.text_view_due_tenant_name);
            dueTenantRoomNo = (TextView) itemView.findViewById(R.id.text_view_due_tenant_room_number);
            dueListItem = itemView.findViewById(R.id.card_view_due_list_item);
            buttonRecieveDueNow = dueListItem.findViewById(R.id.button_recieve_due_now);


        }
    }
}
