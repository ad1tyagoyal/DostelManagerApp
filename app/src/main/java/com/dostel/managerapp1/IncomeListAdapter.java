package com.dostel.managerapp1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class IncomeListAdapter extends RecyclerView.Adapter<IncomeListAdapter.ViewHolder> {

    private List<IncomeListItem> incomeList;
    private Context context;

    public IncomeListAdapter(List<IncomeListItem> incomeList, Context context) {
        this.incomeList = incomeList;
        this.context = context;
    }

    @NonNull
    @Override
    public IncomeListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.income_list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull IncomeListAdapter.ViewHolder holder, int position) {

        IncomeListItem incomeListItem = incomeList.get(position);
        holder.incomeAmount.setText(incomeListItem.getIncomeAmount());
        holder.incomeType.setText(incomeListItem.getIncomeType());
        holder.incomeDate.setText(incomeListItem.getIncomeDate());
        holder.incomeTenantName.setText(incomeListItem.getIncomeTenantName());

    }

    @Override
    public int getItemCount() {
        return incomeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView incomeAmount;
        public TextView incomeType;
        public TextView incomeDate;
        public TextView incomeTenantName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            incomeAmount = (TextView) itemView.findViewById(R.id.text_view_income_amount);
            incomeType = (TextView) itemView.findViewById(R.id.text_view_income_type);
            incomeDate = (TextView) itemView.findViewById(R.id.text_view_income_date);
            incomeTenantName = (TextView) itemView.findViewById(R.id.text_view_income_tenant_name);
        }
    }
}
