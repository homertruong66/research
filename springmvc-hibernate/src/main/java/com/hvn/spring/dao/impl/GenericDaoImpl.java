package com.hvn.spring.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hvn.spring.dao.GenericDao;
import com.hvn.spring.dto.DomainDTO;
import com.hvn.spring.dto.UserDTO;
import com.hvn.spring.model.AbstractEntity;
import com.hvn.spring.model.ReturnValue;
import com.hvn.spring.model.User;
import com.hvn.spring.utils.HVNSpringUtils;
import com.hvn.spring.utils.criteria.SearchCriteria;

@Service
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class GenericDaoImpl implements GenericDao {

	@Autowired
	protected HibernateTemplate hibernateTemplate;

	@Autowired
	protected JdbcTemplate jdbcTemplate;

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public <T> T delete(Class<?> entityClass, long id) {
		ReturnValue<?> returnValue = new ReturnValue<>();
		try {
			Object entity = hibernateTemplate.get(entityClass, id);
			hibernateTemplate.delete(entity);
		} catch (Exception e) {
			returnValue.setError(e.getMessage());
		}
		
		return (T) returnValue;
	}
	
	@Override
	public <T> List<T> find(SearchCriteria searchCriteria, Class<T> clazz) {
		List<?> results = null;
		
		DetachedCriteria detachedCriteria = HVNSpringUtils.createDetachedCriteria(searchCriteria, clazz);

		if (searchCriteria.getPageSize() != null && searchCriteria.getPageIndex() != null) {
			int startIndex = searchCriteria.getPageSize() * (searchCriteria.getPageIndex() - 1);
			results = hibernateTemplate.findByCriteria(detachedCriteria, startIndex,
					searchCriteria.getPageSize());
		} else {
			results = hibernateTemplate.findByCriteria(detachedCriteria);
		}

		return (List<T>) results;
	}
	
	@Override
	public UserDTO findByUserName(String username) {
		List<?> userResults  = hibernateTemplate.findByNamedQuery("User.findByName", username);
		UserDTO userDTO = null;
		if (userResults != null && !userResults.isEmpty()) {
			userDTO = new UserDTO((User) userResults.get(0));
		}
		
		return userDTO;
	}
	
	@Override
	public <T> T get(Class<T> entityClass, long id) {
		return hibernateTemplate.get(entityClass, id);
	}
	
	@Override
	public <T> List<T> getAll(Class<T> entityClass) {
		return hibernateTemplate.loadAll(entityClass);
	}
	
	@Override
	public List<DomainDTO> findCountries() {
		String query = "select ID, SUBSTRING(CODE, 2) as CODE, NAME from domain where code like '.%'";
		return getDomains(query);
	}
	
	// //// Utilities
	private List<DomainDTO> getDomains(String query) {
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(query);
		List<DomainDTO> domains = new ArrayList<DomainDTO>();

		if (rows != null) {
			rows.stream().forEach(row -> {
				DomainDTO domain = new DomainDTO();
				domain.setId((long) row.get("ID"));
				domain.setCode((String) row.get("CODE"));
				domain.setName((String) row.get("NAME"));

				domains.add(domain);
			});
		}

		return domains;
	}
	
	@Override
	public List<DomainDTO> findProvinces(String countryCode) {
		StringBuilder queryBuilder = new StringBuilder(
				"select ID, CODE, NAME from domain where code like '");
		queryBuilder.append(countryCode);
		queryBuilder.append(".%'");

		return getDomains(queryBuilder.toString());
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public Long save(Object entity) {
		Long ret = 1l;
		
		AbstractEntity abEntity = (AbstractEntity) entity;
		// create
		if (abEntity.isNewEntity()) {
			ret = (Long) hibernateTemplate.save(entity);
		
		// update
		} else {
			hibernateTemplate.update(entity);
			ret = abEntity.getId();
		}
		
		return ret;
	}
}
