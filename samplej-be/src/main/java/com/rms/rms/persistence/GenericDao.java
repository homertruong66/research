package com.rms.rms.persistence;

import com.rms.rms.common.SearchCriteria;
import com.rms.rms.common.SearchResult;

import java.util.List;
import java.util.Map;

/**
 * homertruong
 */

public interface GenericDao {

    <T> List<T> bulkCreate(List<T> pdos);

    <T> long count(Class<T> clazz);

    <T> T create(T pdo);

    <T> void delete(T pdo);

    <T> SearchResult<T> generateEmptySearchResult(SearchCriteria<T> searchCriteria);

    <T> List<T> list(Class<T> clazz, Map<String, Object> params);

    <T> T read(Class<T> clazz, String id, boolean forUpdate);

    <T> List<T> readAll(Class<T> clazz);

    <T> T readFirst(Class<T> clazz, Map<String, Object> params);

    <T> T readUnique(Class<T> clazz, Map<String, Object> params, boolean forUpdate);

    <T> SearchResult<T> search(SearchCriteria<T> searchCriteria);

    <T> T update(T pdo);

    <T> List<T> update(List<T> pdos);

}

