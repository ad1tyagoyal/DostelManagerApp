package com.dostel.managerapp1;

public class PgListItem {
    // declaring the variables which you are gonna use in the list item
    // those values which will get changed from database
    private String liPgNameValue;
    private String liTenantCountValue;
    private String liRoomsCountValue;
    // also you need to get them and set data to them

    public PgListItem(String namePgValue) {
        this.liPgNameValue = namePgValue;
    }

    public String getLiPgNameValue() {
        return liPgNameValue;
    }
}
