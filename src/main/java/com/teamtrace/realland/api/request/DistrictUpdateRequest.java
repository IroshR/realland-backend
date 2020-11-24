package com.teamtrace.realland.api.request;

public class DistrictUpdateRequest extends Request {
    private int districtId;
    private String name;

    public int getDistrictId() {
        return districtId;
    }

    public void setDistrictId(int districtId) {
        this.districtId = districtId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
