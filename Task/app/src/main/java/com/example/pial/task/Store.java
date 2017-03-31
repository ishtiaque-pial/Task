package com.example.pial.task;


public class Store {

    private int storeID;

    public int getStoreID() {
        return storeID;
    }

    public String getStoreName() {
        return storeName;
    }

    public String getStorePhoneNo() {
        return storePhoneNo;
    }

    private String storeName;
    private String storePhoneNo;

    public Store(String storeName, String storePhoneNo) {
        this.storeName = storeName;
        this.storePhoneNo = storePhoneNo;
    }

    public Store(int storeID, String storeName, String storePhoneNo) {
        this.storeID = storeID;
        this.storeName = storeName;
        this.storePhoneNo = storePhoneNo;
    }
}
