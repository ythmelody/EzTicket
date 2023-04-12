package com.ezticket.web.activity.repository;

import java.util.Set;

public interface SeatsRedisDAO<String> {
    public int setAddKV(String key, String value);

    public int setDelKV(String key, String value);

    public Set<String> setFindAllValues(String key);

}
