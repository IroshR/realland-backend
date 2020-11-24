package com.teamtrace.realland.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "role")
@Cacheable
public class Role {
    public static final String PK_TYPE = "SMALLINT(5) UNSIGNED";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id", updatable = false, nullable = false, columnDefinition = PK_TYPE + " AUTO_INCREMENT")
    private int roleId;
    @Column(name = "type_id", nullable = false, columnDefinition = MerchantType.PK_TYPE)
    private int merchantTypeId;
    @Column(name = "name", nullable = false, columnDefinition = "VARCHAR(200)")
    private String name;
    @Column(name = "description", nullable = false, columnDefinition = "VARCHAR(300)")
    private String description;
    @Column(name = "status", nullable = false, columnDefinition = "TINYINT(2) UNSIGNED DEFAULT '0'")
    private short status;
    @Column(name = "created_date", updatable = false, nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public int getMerchantTypeId() {
        return merchantTypeId;
    }

    public void setMerchantTypeId(int merchantTypeId) {
        this.merchantTypeId = merchantTypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public short getStatus() {
        return status;
    }

    public void setStatus(short status) {
        this.status = status;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
