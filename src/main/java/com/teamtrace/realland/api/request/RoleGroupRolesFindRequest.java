package com.teamtrace.realland.api.request;

public class RoleGroupRolesFindRequest extends Request {
    private int merchantTypeId;
    private int roleGroupId;

    public int getMerchantTypeId() {
        return merchantTypeId;
    }

    public void setMerchantTypeId(int merchantTypeId) {
        this.merchantTypeId = merchantTypeId;
    }

    public int getRoleGroupId() {
        return roleGroupId;
    }

    public void setRoleGroupId(int roleGroupId) {
        this.roleGroupId = roleGroupId;
    }
}
