package com.formahei.entity;

import java.sql.ResultSet;

public interface EntityMapper<T> {
    T mapRow(ResultSet resultSet);
}
