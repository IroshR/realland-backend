package com.teamtrace.realland.api.request;

public class PropertyUpdateRequest extends Request {
    private int propertyId;

    public int getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(int propertyId) {
        this.propertyId = propertyId;
    }
}
