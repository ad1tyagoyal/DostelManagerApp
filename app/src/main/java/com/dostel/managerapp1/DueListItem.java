package com.dostel.managerapp1;

public class DueListItem {

    private String dueAmount;
    private String dueLateDays;
    private String dueType;
    private String dueIssueDate;
    private String dueTenantName;
    private String dueTenantRoomNo;
    private String dueDocId;
    private String pgId;

    public DueListItem(String dueAmount, String dueLateDays, String dueType, String dueIssueDate, String dueTenantName, String dueTenantRoomNo, String dueDocId, String pgId) {
        this.dueAmount = dueAmount;
        this.dueLateDays = dueLateDays;
        this.dueType = dueType;
        this.dueIssueDate = dueIssueDate;
        this.dueTenantName = dueTenantName;
        this.dueTenantRoomNo = dueTenantRoomNo;
        this.dueDocId = dueDocId;
        this.pgId = pgId;
    }

    public String getPgId() {
        return pgId;
    }

    public void setPgId(String pgId) {
        this.pgId = pgId;
    }

    public String getDueDocId() {
        return dueDocId;
    }

    public void setDueDocId(String dueDocId) {
        this.dueDocId = dueDocId;
    }

    public String getDueAmount() {
        return dueAmount;
    }

    public void setDueAmount(String dueAmount) {
        this.dueAmount = dueAmount;
    }

    public String getDueLateDays() {
        return dueLateDays;
    }

    public void setDueLateDays(String dueLateDays) {
        this.dueLateDays = dueLateDays;
    }

    public String getDueType() {
        return dueType;
    }

    public void setDueType(String dueType) {
        this.dueType = dueType;
    }

    public String getDueIssueDate() {
        return dueIssueDate;
    }

    public void setDueIssueDate(String dueIssueDate) {
        this.dueIssueDate = dueIssueDate;
    }

    public String getDueTenantName() {
        return dueTenantName;
    }

    public void setDueTenantName(String dueTenantName) {
        this.dueTenantName = dueTenantName;
    }

    public String getDueTenantRoomNo() {
        return dueTenantRoomNo;
    }

    public void setDueTenantRoomNo(String dueTenantRoomNo) {
        this.dueTenantRoomNo = dueTenantRoomNo;
    }
}
