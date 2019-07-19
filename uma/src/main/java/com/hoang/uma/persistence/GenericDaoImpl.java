package com.hoang.uma.persistence;

import com.hoang.uma.common.config.properties.AppProperties;
import com.hoang.uma.common.view_model.SearchCriteria;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * homertruong
 */

@Repository
public class GenericDaoImpl implements GenericDao {

    private Logger logger = Logger.getLogger(GenericDaoImpl.class);

    @Autowired
    AppProperties appProperties;

    @PersistenceContext(unitName = "uma")
    private EntityManager entityManager;

//    @PersistenceContext(unitName = "common-java-app-slave")
//    private EntityManager entityManagerSlave;

    @Override
    public <T> void bulkCreate(List<T> entities) {
        int batchSize = appProperties.getDatasource().getModelsMaster().getBatchSize();
        int i = 0;
        for (T e : entities) {
            entityManager.persist(e);
            i++;
            if (i % batchSize == 0) {
                // flush a batch of inserts and release memory
                entityManager.flush();
                entityManager.clear();
            }
        }
    }

    @Override
    public <T> Long countAll(Class<T> clazz) {
        logger.debug("countAll for " + clazz.toString());
        Query query = entityManager.createQuery(
            "SELECT count(id) " +
            "FROM " + clazz.getName()
        );

        return (Long) query.getSingleResult();
    }

    @Override
    public <T> T create(T pdo) {
        logger.debug("create an Entity of type " + pdo.getClass());
        entityManager.persist(pdo);

        return pdo;
    }

    @Override
    public <T> void delete(T pdo) {
        logger.debug("delete an Entity of type " + pdo.getClass());
        entityManager.remove(pdo);
    }

    @Override
    public int executeNativeUpdateSQL(String updateSQL, Map<String, Object> params) {
        Query updateQuery = entityManager.createNativeQuery(updateSQL);
        params.keySet().stream().forEach(key -> {
            Object value = params.get(key);
            updateQuery.setParameter(key, value);
        });

        return updateQuery.executeUpdate();
    }

    @Override
    public <T> List<T> findAll(Class<T> clazz) {
        logger.debug("findAll for " + clazz.toString());
        Query query = entityManager.createQuery(
            "SELECT e " +
            "FROM " + clazz.getName() + " e"
        );

        return query.getResultList();
    }

    @Override
    public <T> boolean isExistent(Class<T> clazz, Serializable id) {
        logger.debug("check if an Entity (" + clazz.toString() + ", id = " + id + ") exists");

        Query query = entityManager.createQuery(
            "SELECT id " +
            "FROM " + clazz.getName() + " e " +
            "WHERE e.id = :id"
        );
        query.setParameter("id", id);

        try {
           return query.getSingleResult() != null;
        }
        catch (NoResultException ex) {
            return false;
        }
    }

    @Override
    public <T> T read(Class<T> clazz, Serializable id) {
        logger.debug("read an Entity of type " + clazz.getTypeParameters().toString() + " with id = " + id);

        return entityManager.find(clazz, id);
    }

    @Override
    public <T> List<T> search(CriteriaBuilder criteriaBuilder, CriteriaQuery criteriaQuery, Root<T> root,
                              SearchCriteria searchCriteria)
    {
        // sort
        String sortName = searchCriteria.getSortName();
        String sortDirection = searchCriteria.getSortDirection();
        if (!StringUtils.isEmpty(sortName)) {
            Order order;
            if (sortDirection.equalsIgnoreCase("asc")) {
                order = criteriaBuilder.asc(root.get(sortName));
            }
            else {
                order = criteriaBuilder.desc(root.get(sortName));
            }
            criteriaQuery.orderBy(order);
        }

        Query query = entityManager.createQuery(criteriaQuery);
        // page
        query.setFirstResult(searchCriteria.getOffset());
        query.setMaxResults(searchCriteria.getPageSize());

        return query.getResultList();
    }

    @Override
    public <T> T update(T pdo) {
        logger.debug("update an Entity of type " + pdo.getClass());

        return entityManager.merge(pdo);
    }

}
