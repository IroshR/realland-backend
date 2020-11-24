package com.teamtrace.realland.search.mapper;

import com.teamtrace.realland.api.MerchantGridApi;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class MerchantGridMapper extends AbstractMapper<MerchantGridApi> {
    public MerchantGridMapper() {
        columnMap = new HashMap<>(14);
        columnMap.put("merchantId", "A.merchant_id");
        columnMap.put("typeId", "A.type_id");
        columnMap.put("typeName", "B.name");
        columnMap.put("categoryId", "A.category_id");
        columnMap.put("categoryName", "C.name");
        columnMap.put("name", "A.name");
        columnMap.put("registrationNo", "A.registration_no");
        columnMap.put("email", "A.email");
        columnMap.put("address", "A.address");
        columnMap.put("telephone", "A.telephone");
        columnMap.put("telephone2", "A.telephone2");
        columnMap.put("logoUrl", "A.logo_url");
        columnMap.put("status", "A.status");
        columnMap.put("createdDate", "A.created_date");
    }

    @Override
    public MerchantGridApi mapRow(ResultSet row, int rowNum) throws SQLException {
        MerchantGridApi api = new MerchantGridApi();
        api.setMerchantId(row.getInt("A.merchant_id"));
        api.setTypeId(row.getShort("A.type_id"));
        api.setTypeName(row.getString("B.name"));
        api.setCategoryId(row.getShort("A.category_id"));
        api.setCategoryName(row.getString("C.name"));
        api.setName(row.getString("A.name"));
        api.setRegistrationNo(row.getString("A.registration_no"));
        api.setEmail(row.getString("A.email"));
        api.setAddress(row.getString("A.address"));
        api.setTelephone(row.getString("A.telephone"));
        api.setTelephone2(row.getString("A.telephone2"));
        api.setLogoUrl(row.getString("A.logo_url"));
        api.setStatus(row.getShort("A.status"));
        api.setCreatedDate(row.getTimestamp("A.created_date"));
        return api;
    }
}
