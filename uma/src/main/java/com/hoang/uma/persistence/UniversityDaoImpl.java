package com.hoang.uma.persistence;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * homertruong
 */

@Repository
public class UniversityDaoImpl implements UniversityDao {

    private Logger logger = Logger.getLogger(UniversityDaoImpl.class);

    @PersistenceContext(unitName = "uma")
    private EntityManager entityManager;

    @Override
    public boolean isCourseNameExistent (String courseName, String departmentId) {
        logger.debug("check if course name = '" + courseName + "' exists in department id = " + departmentId);

        Query query = entityManager.createQuery(
            "SELECT id " +
            "FROM Course c " +
            "WHERE c.name = :name " +
            "AND c.department.id = :departmentId"
        );
        query.setParameter("name", courseName);
        query.setParameter("departmentId", departmentId);

        try {
            return query.getSingleResult() != null;
        }
        catch (NoResultException ex) {
            return false;
        }
    }

    @Override
    public boolean isDepartmentNameExistent (String departmentName, String id) {
        logger.debug("check if department name = '" + departmentName + "' exists in university id = " + id);

        Query query = entityManager.createQuery(
            "SELECT id " +
            "FROM Department d " +
            "WHERE d.name = :name " +
            "AND d.university.id = :id"
        );
        query.setParameter("name", departmentName);
        query.setParameter("id", id);

        try {
            return query.getSingleResult() != null;
        }
        catch (NoResultException ex) {
            return false;
        }
    }

}
