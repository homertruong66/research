package com.hvn.spring.service.impl;

import com.hvn.spring.dao.GenericDao;
import com.hvn.spring.dto.UserDTO;
import com.hvn.spring.model.ReturnValue;
import com.hvn.spring.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class UserServiceImpl implements UserService {

    @Autowired
    private GenericDao crudService;

    @Autowired
    protected HibernateTemplate hibernateTemplate;

    @Override
    public ReturnValue<UserDTO> findByUserName(String username) {
    	ReturnValue<UserDTO> returnValue = new ReturnValue<UserDTO>();
    	try {
    		UserDTO userResult = crudService.findByUserName(username);
    		returnValue.setData(userResult);
		} catch (Exception e) {
			returnValue.setError(e.getMessage());
		}
        
        return returnValue;
    }
}
