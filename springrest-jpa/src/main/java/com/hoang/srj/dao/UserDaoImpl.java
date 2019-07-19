package com.hoang.srj.dao;

import com.hoang.srj.model.User;
import com.hoang.srj.view_model.UserSearchCriteria;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    private Logger logger = Logger.getLogger(UserDaoImpl.class);

    @PersistenceContext(unitName = "srjPU")
    private EntityManager entityManager;

    @Override
    public boolean hasEmail (String email) {
        logger.debug("check if email '" + email + "' exists");

        if (StringUtils.isEmpty(email)) {
            return false;
        }

        String result = null;
        Query query = entityManager.createQuery(
            "SELECT u.id " +
            "FROM User u " +
            "WHERE u.email = :email "
        );
        query.setParameter("email", email);

        try {
            result = (String) query.getSingleResult();
        }
        catch (Exception ex) {
            return false;
        }

        return result != null;
    }

    @Override
    public List<User> search(UserSearchCriteria searchCriteria) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery criteriaQuery = criteriaBuilder.createQuery();

        // build a list of predicates to search
        Root<User> root = criteriaQuery.from(User.class);
        List<Predicate> predicates = buildPredicates(criteriaBuilder, root, searchCriteria);
        criteriaQuery.select(root)
                     .where(predicates.toArray(new Predicate[]{}));

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
        List<User> users = query.getResultList();

        return users;
    }

    private List<Predicate> buildPredicates(CriteriaBuilder criteriaBuilder, Root<User> root, UserSearchCriteria searchCriteria) {
        List<Predicate> predicates = new ArrayList();

        String name = searchCriteria.getName();
        if (!StringUtils.isEmpty(name)) {
            predicates.add(criteriaBuilder.like(root.get("name").as(String.class),
                                                "%" + name + "%"));
        }

        return predicates;
    }

}
