package com.teamtrace.realland.api.request;

public class PropertyImageRequestApi {
    private String imageUrl;
    private short sequence;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public short getSequence() {
        return sequence;
    }

    public void setSequence(short sequence) {
        this.sequence = sequence;
    }
}
