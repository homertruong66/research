package com.hoang.srj.dao;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.Serializable;
import java.util.List;

@Repository
public class GenericDaoImpl implements GenericDao {

    private Logger logger = Logger.getLogger(GenericDaoImpl.class);

    @PersistenceContext(unitName = "srjPU")
    private EntityManager entityManager;

//    @PersistenceContext(unitName = "srjPU_Slave")
//    private EntityManager entityManagerSlave;

    @Override
    public <T> Long countAll(Class<T> clazz) {
        logger.info("countAll for " + clazz.toString());
        Query query = entityManager.createQuery(
            "SELECT count(id) " +
            "FROM " + clazz.getName()
        );

        return (Long) query.getSingleResult();
    }

    @Override
    public <T> T create(T pdo) {
        logger.info("create an Entity of type " + pdo.getClass());
        entityManager.persist(pdo);

        return pdo;
    }

    @Override
    public <T> void delete(T pdo) {
        logger.info("delete an Entity of type " + pdo.getClass());
        entityManager.remove(pdo);
    }

    @Override
    public <T> List<T> findAll(Class<T> clazz) {
        logger.info("findAll for " + clazz.toString());
        Query query = entityManager.createQuery(
            "SELECT e " +
            "FROM " + clazz.getName() + " e"
        );

        return query.getResultList();
    }

    @Override
    public <T> boolean has(Class<T> clazz, Serializable id) {
        logger.info("check if an Entity (" + clazz.toString() + ", id = " + id + ") exists");

        String result;
        Query query = entityManager.createQuery(
            "SELECT id " +
            "FROM " + clazz.getName() + " e " +
            "WHERE e.id = :id"
        );
        query.setParameter("id", id);

        try {
           result = (String) query.getSingleResult();
        }
        catch (Exception ex) {
            return false;
        }

        return result != null;
    }

    @Override
    public <T> T read(Class<T> clazz, Serializable id) {
        logger.info("read an Entity of type " + clazz.getTypeParameters().toString() + " with id = " + id);

        return entityManager.find(clazz, id);
    }

    @Override
    public <T> T update(T pdo) {
        logger.info("update an Entity of type " + pdo.getClass());
        entityManager.merge(pdo);

        return pdo;
    }

}
