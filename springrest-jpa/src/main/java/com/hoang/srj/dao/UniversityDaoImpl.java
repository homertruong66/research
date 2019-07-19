package com.hoang.srj.dao;

import com.hoang.srj.model.University;
import com.hoang.srj.view_model.UniversitySearchCriteria;
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
public class UniversityDaoImpl implements UniversityDao {

    private Logger logger = Logger.getLogger(UniversityDaoImpl.class);

    @PersistenceContext(unitName = "srjPU")
    private EntityManager entityManager;

    @Override
    public List<University> search(UniversitySearchCriteria searchCriteria) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery criteriaQuery = criteriaBuilder.createQuery();

        // build a list of predicates to search
        Root<University> root = criteriaQuery.from(University.class);
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
        List<University> universities = query.getResultList();

        return universities;
    }

    private List<Predicate> buildPredicates(CriteriaBuilder criteriaBuilder, Root<University> root, UniversitySearchCriteria searchCriteria) {
        List<Predicate> predicates = new ArrayList();

        String name = searchCriteria.getName();
        if (!StringUtils.isEmpty(name)) {
            predicates.add(criteriaBuilder.like(root.get("name").as(String.class),
                                                "%" + name + "%"));
        }

        return predicates;
    }
}
