package com.teamtrace.realland.model;

import javax.persistence.*;

@Entity
@Table(name = "property_image")
public class PropertyImage {
    public static final String PK_TYPE = "SMALLINT(11) UNSIGNED";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id", updatable = false, nullable = false, columnDefinition = PK_TYPE + " AUTO_INCREMENT")
    private Integer imageId;
    @Column(name = "property_id", updatable = false, nullable = false, columnDefinition = Property.PK_TYPE)
    private int propertyId;
    @Column(name = "sequence", nullable = false, columnDefinition = "TINYINT(2) UNSIGNED DEFAULT '0'")
    private short sequence;
    @Column(name = "image_url", columnDefinition = "VARCHAR(128)")
    private String imageUrl;

    public Integer getImageId() {
        return imageId;
    }

    public void setImageId(Integer imageId) {
        this.imageId = imageId;
    }

    public int getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(int propertyId) {
        this.propertyId = propertyId;
    }

    public short getSequence() {
        return sequence;
    }

    public void setSequence(short sequence) {
        this.sequence = sequence;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
