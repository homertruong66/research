package com.hoang.srj.service;

import com.hoang.srj.dao.RoleDao;
import com.hoang.srj.model.Role;
import com.hoang.srj.view_model.UserCreateModel;
import com.hoang.srj.view_model.UserUpdateModel;
import com.hoang.srj.dao.GenericDao;
import com.hoang.srj.dao.UserDao;
import com.hoang.srj.dto.UserDto;
import com.hoang.srj.exception.BusinessException;
import com.hoang.srj.model.User;
import com.hoang.srj.view_model.UserSearchCriteria;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    private Logger logger = Logger.getLogger(UserServiceImpl.class);

    @Autowired
    private GenericDao genericDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Override
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public UserDto create (UserCreateModel createModel) throws BusinessException {
        logger.info("create a User: " + createModel.toString());

        // validate business rules on createModel
        if (userDao.hasEmail(createModel.getEmail())) {
            throw new BusinessException(BusinessException.USER_EMAIL_EXISTS,
                                        BusinessException.USER_EMAIL_EXISTS_MESSAGE);
        }

        List<Role> roles = new ArrayList<>();
        Arrays.stream(createModel.getRoles().split(",")).forEach(roleName -> {
            Role role = roleDao.findByName(roleName);
            if (role != null) {
                roles.add(role);
            }
        });
        if (roles.size() == 0) {
            throw new BusinessException(BusinessException.USER_ROLES_EMPTY,
                                        BusinessException.USER_ROLES_EMPTY_MESSAGE);
        }

        // set data for User
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setEmail(createModel.getEmail());
        user.setName(createModel.getName());
        user.setPassword(createModel.getPassword());
        user.setAge(createModel.getAge());
        user.setCreatedAt(new Date());
        user.setUpdatedAt(new Date());

        // set Roles for User
        user.setRoles(roles);

        // create User
        genericDao.create(user);

        // create UserDto to return
        UserDto userDto = new UserDto();
        userDto.copyFrom(user);

        return userDto;
    }

    @Override
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void delete(String id) throws BusinessException {
        logger.info("delete a User: " + id);

        // get User
        User user = genericDao.read(User.class, id);
        if ( user == null ) {
            throw new BusinessException(BusinessException.USER_NOT_FOUND,
                                        BusinessException.USER_NOT_FOUND_MESSAGE);
        }

        // delete User
        genericDao.delete(user);
    }

    @Override
    public UserDto get (String id) throws BusinessException {
        logger.info("get a User: " + id);

        // get User
        User user = genericDao.read(User.class, id);
        if ( user == null ) {
            throw new BusinessException(BusinessException.USER_NOT_FOUND,
                                        BusinessException.USER_NOT_FOUND_MESSAGE);
        }

        // create UserDto to return
        UserDto userDto = new UserDto();
        userDto.copyFrom(user);

        return userDto;
    }

    @Override
    public List<UserDto> search (UserSearchCriteria searchCriteria) throws BusinessException {
        List<UserDto> result = new ArrayList<>();

        List<User> users = userDao.search(searchCriteria);
        users.forEach(user -> {
            UserDto userDto = new UserDto();
            userDto.copyFrom(user);
            result.add(userDto);
        });

        return result;
    }

    @Override
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public UserDto update(UserUpdateModel updateModel) throws BusinessException {
        logger.info("update a User: " + updateModel.toString());

        // set data for User
        User user = genericDao.read(User.class, updateModel.getId());
        if ( user == null ) {
            throw new BusinessException(BusinessException.USER_NOT_FOUND,
                                        BusinessException.USER_NOT_FOUND_MESSAGE);
        }

        if (!StringUtils.isEmpty(updateModel.getName())) {
            user.setName(updateModel.getName());
        }
        if (updateModel.getAge() > 0) {
            user.setAge(updateModel.getAge());
        }
        user.setUpdatedAt(new Date());

        // set Roles for User
        if (!StringUtils.isEmpty(updateModel.getRoles())) {
            List<Role> roles = new ArrayList<>();
            Arrays.stream(updateModel.getRoles().split(",")).forEach(roleName -> {
                Role role = roleDao.findByName(roleName);
                if (role != null) {
                    roles.add(role);
                }
            });
            if (roles.size() > 0) {
                user.setRoles(roles);
            }
        }

        // update User
        genericDao.update(user);

        // create UserDto to return
        UserDto userDto = new UserDto();
        userDto.copyFrom(user);

        return userDto;
    }
}
