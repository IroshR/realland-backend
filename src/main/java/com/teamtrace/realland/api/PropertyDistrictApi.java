package com.teamtrace.realland.api;

public class PropertyDistrictApi {
    private int districtId;
    private String name;

    public String getName() {
        return name;
    }

    public int getDistrictId() {
        return districtId;
    }

    public void setDistrictId(int districtId) {
        this.districtId = districtId;
    }

    public void setName(String name) {
        this.name = name;
    }
}
