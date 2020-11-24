package com.teamtrace.realland.api.request;

public class PropertyAmenityCreateRequest extends Request {
    private short propertyTypeId;
    private String name;

    public short getPropertyTypeId() {
        return propertyTypeId;
    }

    public void setPropertyTypeId(short propertyTypeId) {
        this.propertyTypeId = propertyTypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
