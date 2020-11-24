package com.teamtrace.realland.model;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class Model {
    public static final int SCALE = 20;
    public static final int PRECISION = 5;
    public static final String VALUE_TYPE = "DECIMAL(20, 5) DEFAULT '0'";
}
