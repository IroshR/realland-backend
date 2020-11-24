package com.teamtrace.realland.search;

import com.teamtrace.realland.api.request.SearchCriteria;
import com.teamtrace.realland.api.response.SearchResponse;
import com.teamtrace.realland.search.mapper.AbstractMapper;
import com.teamtrace.realland.util.constant.Statuses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;

public abstract class DynamicSearchTemplate {
    protected boolean merchantLevel = true;
    @Autowired
    protected JdbcTemplate jdbcTemplate;
    private String selectSQL;
    private String countSQL;
    private AbstractMapper rowMapper;
    private RowMapper countMapper;

    protected DynamicSearchTemplate(String selectSQL, String countSQL, AbstractMapper rowMapper, RowMapper countMapper) {
        this.selectSQL = selectSQL;
        this.countSQL = countSQL;
        this.rowMapper = rowMapper;
        this.countMapper = countMapper;
    }

    public SearchResponse findByCriteria(SearchCriteria criteria) {
        SearchResponse response = new SearchResponse();
        response.setOffset(criteria.getOffset());
        response.setLimit(criteria.getLimit());
        StringBuilder where = generateWhere(criteria, new StringBuilder(100), rowMapper.getColumnMap());
        filter(criteria, where);
        String select = selectSQL + where.toString() + generateOrderBy(criteria, rowMapper.getColumnMap()) + generateLimit(criteria);
        String count = countSQL + where.toString();
        //System.out.println(select);
        //System.out.println(count);
        try {
            response.setData(this.jdbcTemplate.query(select, rowMapper));
            response.setRecordCount((Integer) this.jdbcTemplate.query(count, countMapper).get(0));
            response.setStatus(Statuses.RESPONSE_STATUS_SUCCESS);

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
        }

        return response;
    }

    protected void filter(SearchCriteria criteria, StringBuilder where) {
        //any hard coded filters
    }

    private StringBuilder generateWhere(SearchCriteria criteria, StringBuilder where, HashMap<String, String> columnMap) {
        if (merchantLevel) {
            where.append(" WHERE A.merchant_id = ").append(criteria.getMerchantId());
        } else {
            where.append(" WHERE 1 > 0 ");
        }
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if (criteria.getFromDate() != null && criteria.getToDate() != null) {
            where.append(" AND A.created_date BETWEEN '").append(dateFormat.format(criteria.getFromDate())).
                    append("' AND '").append(dateFormat.format(criteria.getToDate())).append("' ");
        }
        if (criteria.getStatuses() != null && !criteria.getStatuses().isEmpty()) {
            String statuses = criteria.getStatuses().toString();
            where.append(" AND A.status IN (").append(statuses.substring(1, statuses.length() - 1)).append(")");
        }
        if (criteria.getSearchKeys() != null && !criteria.getSearchKeys().isEmpty()) {
            for (int i = 0; i < criteria.getSearchKeys().size(); i++) {
                String key = criteria.getSearchKeys().get(i);
                key = columnMap.get(key);
                Object value = criteria.getValues().get(i);

                switch (criteria.getOperators().get(i)) {
                    case "=":
                    case "eq": {
                        if (value instanceof Integer || value instanceof Double)
                            where.append(" AND ").append(key).append(" = ").append(value);
                        else if ("true".equalsIgnoreCase(value.toString()) || "false"
                                .equalsIgnoreCase(value.toString()))
                            where.append(" AND ").append(key).append(" IS ").append(value);
                        else
                            where.append(" AND ").append(key).append(" = '").append(escapeControlCharacters(value)).append("'");
                        break;
                    }
                    case "!=":
                    case "<>": {
                        if (value instanceof Integer || value instanceof Double)
                            where.append(" AND ").append(key).append(" <> ").append(value);
                        else
                            where.append(" AND ").append(key).append(" <> '").append(escapeControlCharacters(value)).append("'");
                        break;
                    }
                    case "like": {
                        where.append(" AND ").append(key).append(" LIKE '%").append(escapeControlCharacters(value)).append("%'");
                        break;
                    }
                    case "%like": {
                        where.append(" AND ").append(key).append(" LIKE '%").append(escapeControlCharacters(value)).append("'");
                        break;
                    }
                    case "like%": {
                        where.append(" AND ").append(key).append(" LIKE '").append(escapeControlCharacters(value)).append("%'");
                        break;
                    }
                    case ">":
                    case "gt": {
                        if (value instanceof Integer || value instanceof Double)
                            where.append(" AND ").append(key).append(" > ").append(value).append(" ");
                        else
                            where.append(" AND ").append(key).append(" > '").append(escapeControlCharacters(value)).append("'");
                        break;
                    }
                    case "<":
                    case "lt": {
                        if (value instanceof Integer || value instanceof Double)
                            where.append(" AND ").append(key).append(" < ").append(value).append(" ");
                        else
                            where.append(" AND ").append(key).append(" < '").append(escapeControlCharacters(value)).append("'");
                        break;
                    }
                    case ">=":
                    case "gte": {
                        if (value instanceof Integer || value instanceof Double)
                            where.append(" AND ").append(key).append(" >= ").append(value).append(" ");
                        else
                            where.append(" AND ").append(key).append(" >= '").append(escapeControlCharacters(value)).append("'");
                        break;
                    }
                    case "<=":
                    case "lte": {
                        if (value instanceof Integer || value instanceof Double)
                            where.append(" AND ").append(key).append(" <= ").append(value).append(" ");
                        else
                            where.append(" AND ").append(key).append(" <= '").append(escapeControlCharacters(value)).append("'");
                        break;
                    }
                    case "in": {
                        where.append(" AND ").append(key).append(" IN (").append(escapeControlCharacters(value)).append(")");
                        break;
                    }
                }
            }

        }

        return where;
    }

    private String generateOrderBy(SearchCriteria criteria, HashMap<String, String> columnMap) {
        String orderBy = " ASC ";
        if (criteria.getOrderByKey() != null) {
            criteria.setOrderByKey(columnMap.get(criteria.getOrderByKey()));
            if (criteria.getOrderByKey() != null) {
                if ("DESC".equalsIgnoreCase(criteria.getOrderByValue())) {
                    orderBy = " DESC ";
                }

                return " ORDER BY " + criteria.getOrderByKey() + orderBy;
            }
        }

        return "";
    }

    private String generateLimit(SearchCriteria criteria) {
        if (criteria.getLimit() > 0) {
            return " LIMIT " + criteria.getOffset() + "," + criteria.getLimit();
        } else {
            return "";
        }
    }

    private String escapeControlCharacters(Object text) {
        if (text != null) {
            return String.valueOf(text).replaceAll("'", "\'");
        }
        return null;
    }
}
