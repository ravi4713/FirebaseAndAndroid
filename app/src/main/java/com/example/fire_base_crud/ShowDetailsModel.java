package com.example.fire_base_crud;


public class ShowDetailsModel {
    private String mTilte;
    private String mDescription;
    private String mid;

    public ShowDetailsModel(String id, String title, String desc) {
        mid = id;
        mTilte = title;
        mDescription = desc;
    }

    public String getmTilte() {
        return mTilte;
    }
    public String getmDescription() {
        return mDescription;
    }
    public String getMid() { return mid; }
}