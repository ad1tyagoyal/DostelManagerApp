package com.dostel.managerapp1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class PgListAdapter extends RecyclerView.Adapter<PgListAdapter.ViewHolder> {


    private List<PgListItem> pgListItems;
    private Context context;
    private String namePg;

    //Constructor
    public PgListAdapter(List<PgListItem> pgListItems, Context context, String namePg) {

        this.pgListItems = pgListItems;
        this.context = context;
        this.namePg = namePg;
        //we should get 3 names
    }


    @Override
    public PgListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.pg_list_item, parent, false);
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull PgListAdapter.ViewHolder holder, int position) {
        PgListItem listItem = pgListItems.get(position);
        holder.mPgNameValue.setText(listItem.getLiPgNameValue());


    }

    @Override
    public int getItemCount() {
        return pgListItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mPgName;
        private TextView mTenantCount;
        private TextView mRoomsCount;
        // those values which will get changed from database
        private TextView mPgNameValue;
        private TextView mTenantCountValue;
        private TextView mRoomsCountValue;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Assigning Views to the static fields
            mPgName = itemView.findViewById(R.id.textView_pg_name);
            mTenantCount = itemView.findViewById(R.id.textView_tenants);
            mRoomsCount = itemView.findViewById(R.id.textView_rooms);
            //Assigning Views to the dynamic fields
            mPgNameValue = itemView.findViewById(R.id.textView_pg_name_value);
            mTenantCountValue = itemView.findViewById(R.id.textView_tenants_value);
            mRoomsCountValue = itemView.findViewById(R.id.textView_rooms_value);

        }
    }

}
