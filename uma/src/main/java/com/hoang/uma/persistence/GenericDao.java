package com.hoang.uma.persistence;

import com.hoang.uma.common.view_model.SearchCriteria;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * homertruong
 */

public interface GenericDao {

    <T> void bulkCreate (List<T> entities);

    <T> Long countAll (Class<T> clazz);

    <T> T create (T pdo);

    <T> void delete (T pdo);

    int executeNativeUpdateSQL (String updateSQL, Map<String, Object> params);

    <T> List<T> findAll (Class<T> clazz);

    <T> boolean isExistent (Class<T> clazz, Serializable id);

    <T> T read (Class<T> clazz, Serializable id);

    <T> List<T> search (CriteriaBuilder criteriaBuilder, CriteriaQuery criteriaQuery, Root<T> root,
                        SearchCriteria searchCriteria);

    <T> T update (T pdo);

}
