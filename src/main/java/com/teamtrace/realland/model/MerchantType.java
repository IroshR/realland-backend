package com.teamtrace.realland.model;

import javax.persistence.*;

@Entity
@Table(name = "merchant_type")
@Cacheable
public class MerchantType {
    public static final String PK_TYPE = "TINYINT(3) UNSIGNED";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "type_id", updatable = false, nullable = false, columnDefinition = PK_TYPE + " AUTO_INCREMENT")
    private Integer typeId;
    @Column(name = "name", nullable = false, columnDefinition = "VARCHAR(100)")
    private String name;
    @Column(name = "status", nullable = false, columnDefinition = "TINYINT(2) UNSIGNED DEFAULT '0'")
    private short status;

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public short getStatus() {
        return status;
    }

    public void setStatus(short status) {
        this.status = status;
    }
}
