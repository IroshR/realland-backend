package com.teamtrace.realland.model;

import javax.persistence.*;

@Entity
@Table(name = "district")
@Cacheable
public class District {
    public static final String PK_TYPE = "TINYINT(3) UNSIGNED";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "district_id", updatable = false, nullable = false, columnDefinition = PK_TYPE + " AUTO_INCREMENT")
    private Integer districtId;
    @Column(name = "name", nullable = false, columnDefinition = "VARCHAR(100)")
    private String name;
    @Column(name = "province_id", nullable = false, columnDefinition = Province.PK_TYPE)
    private int provinceId;
    @Column(name = "status", nullable = false, columnDefinition = "TINYINT(2) UNSIGNED DEFAULT '0'")
    private short status;

    public Integer getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Integer districtId) {
        this.districtId = districtId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }

    public short getStatus() {
        return status;
    }

    public void setStatus(short status) {
        this.status = status;
    }
}
