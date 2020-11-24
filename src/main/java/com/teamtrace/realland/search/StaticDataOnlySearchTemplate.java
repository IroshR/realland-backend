package com.teamtrace.realland.search;

import com.teamtrace.realland.api.request.SearchCriteria;
import com.teamtrace.realland.api.response.SearchResponse;
import com.teamtrace.realland.search.mapper.AbstractMapper;
import com.teamtrace.realland.util.constant.Statuses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;

public abstract class StaticDataOnlySearchTemplate {
    @Autowired
    protected JdbcTemplate jdbcTemplate;
    private String selectSQL;
    private AbstractMapper rowMapper;

    protected StaticDataOnlySearchTemplate(String selectSQL, AbstractMapper rowMapper) {
        this.selectSQL = selectSQL;
        this.rowMapper = rowMapper;
    }

    public SearchResponse findByCriteria(SearchCriteria criteria) {
        SearchResponse response = new SearchResponse();
        response.setOffset(criteria.getOffset());
        response.setLimit(criteria.getLimit());

        try {
            response.setData(this.jdbcTemplate.query(selectSQL, getDataStatement(criteria), rowMapper));

            response.setStatus(Statuses.RESPONSE_STATUS_SUCCESS);

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
        }

        return response;
    }

    protected abstract PreparedStatementSetter getDataStatement(SearchCriteria criteria);
}
