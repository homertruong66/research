package com.hoang.uma.persistence;

import com.hoang.uma.common.config.properties.AppProperties;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * homertruong
 */

@Repository
public class AppDaoImpl implements AppDao {

    private Logger logger = Logger.getLogger(AppDaoImpl.class);

    @Autowired
    AppProperties appProperties;

    @PersistenceContext(unitName = "uma")
    private EntityManager entityManager;

//    @PersistenceContext(unitName = "uma-slave")
//    private EntityManager entityManagerSlave;

    @Override
    public <T> T findByEmail(Class<T> clazz, String email) {
        logger.debug("find by email = '" + email + "' entity of type " + clazz.toString());

        Query query = entityManager.createQuery(
            "SELECT e " +
            "FROM " + clazz.getName() + " e " +
            "WHERE e.email = :email"
        );
        query.setParameter("email", email);

        try {
            return (T) query.getSingleResult();
        }
        catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public <T> T findByName(Class<T> clazz, String name) {
        logger.debug("find by name = '" + name + "' entity of type " + clazz.toString());

        Query query = entityManager.createQuery(
            "SELECT e " +
            "FROM " + clazz.getName() + " e " +
            "WHERE e.name = :name"
        );
        query.setParameter("name", name);

        try {
            return (T) query.getSingleResult();
        }
        catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public String findIdByEmail(String email) {
        logger.debug("find id by email = '" + email + "'");

        Query query = entityManager.createQuery(
            "SELECT u.id " +
            "FROM User u " +
            "WHERE u.email = :email"
        );
        query.setParameter("email", email);

        try {
            return (String) query.getSingleResult();
        }
        catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public <T> boolean isEmailExistent(Class<T> clazz, String email) {
        logger.debug("check if email = '" + email + "' exists in " + clazz.toString());

        Query query = entityManager.createQuery(
            "SELECT id " +
            "FROM " + clazz.getName() + " e " +
            "WHERE e.email = :email"
        );
        query.setParameter("email", email);

        try {
            return query.getSingleResult() != null;
        }
        catch (NoResultException ex) {
            return false;
        }
    }

    @Override
    public <T> boolean isNameExistent(Class<T> clazz, String name) {
        logger.debug("check if name = '" + name + "' exists in " + clazz.toString());

        Query query = entityManager.createQuery(
            "SELECT id " +
            "FROM " + clazz.getName() + " e " +
            "WHERE e.name = :name"
        );
        query.setParameter("name", name);

        try {
            return query.getSingleResult() != null;
        }
        catch (NoResultException ex) {
            return false;
        }
    }

}
