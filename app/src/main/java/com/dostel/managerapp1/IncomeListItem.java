package com.dostel.managerapp1;

public class IncomeListItem {

    private String incomeAmount;
    private String incomeTenantName;
    private String incomeType;
    private String incomeDate;

    public IncomeListItem(String incomeAmount, String incomeTenantName, String incomeType, String incomeDate) {
        this.incomeAmount = incomeAmount;
        this.incomeTenantName = incomeTenantName;
        this.incomeType = incomeType;
        this.incomeDate = incomeDate;
    }

    public String getIncomeAmount() {
        return incomeAmount;
    }

    public void setIncomeAmount(String incomeAmount) {
        this.incomeAmount = incomeAmount;
    }

    public String getIncomeTenantName() {
        return incomeTenantName;
    }

    public void setIncomeTenantName(String incomeTenantName) {
        this.incomeTenantName = incomeTenantName;
    }

    public String getIncomeType() {
        return incomeType;
    }

    public void setIncomeType(String incomeType) {
        this.incomeType = incomeType;
    }

    public String getIncomeDate() {
        return incomeDate;
    }

    public void setIncomeDate(String incomeDate) {
        this.incomeDate = incomeDate;
    }
}
