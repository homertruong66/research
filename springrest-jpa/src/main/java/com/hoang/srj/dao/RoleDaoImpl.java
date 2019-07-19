package com.hoang.srj.dao;

import com.hoang.srj.model.Role;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Repository
public class RoleDaoImpl implements RoleDao {

    private Logger logger = Logger.getLogger(RoleDaoImpl.class);

    @PersistenceContext(unitName = "srjPU")
    private EntityManager entityManager;

    @Override
    public Role findByName(String name) {
        logger.debug("find Role by name = " + name);

        Role result = null;
        Query query = entityManager.createQuery(
            "SELECT r " +
            "FROM Role r " +
            "WHERE r.name = :name "
        );
        query.setParameter("name", name);

        try {
            result = (Role) query.getSingleResult();
        }
        catch (Exception ex) {}

        return result;
    }

}
