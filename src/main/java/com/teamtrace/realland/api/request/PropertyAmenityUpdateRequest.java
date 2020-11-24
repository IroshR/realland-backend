package com.teamtrace.realland.api.request;

public class PropertyAmenityUpdateRequest extends Request {
    private int propertyAmenityId;
    private String name;

    public int getPropertyAmenityId() {
        return propertyAmenityId;
    }

    public void setPropertyAmenityId(int propertyAmenityId) {
        this.propertyAmenityId = propertyAmenityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
