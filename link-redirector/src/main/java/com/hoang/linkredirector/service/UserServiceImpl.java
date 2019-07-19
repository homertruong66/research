package com.hoang.linkredirector.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hoang.linkredirector.dao.UserDao;
import com.hoang.linkredirector.dto.SubscriptionDto;
import com.hoang.linkredirector.dto.UserDto;
import com.hoang.linkredirector.exception.BusinessException;
import com.hoang.linkredirector.model.Subscription;
import com.hoang.linkredirector.model.User;
import com.hoang.linkredirector.view_model.SubscriptionCreateModel;
import com.hoang.linkredirector.view_model.UserCreateModel;
import com.hoang.linkredirector.view_model.UserSearchCriteria;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Transactional(transactionManager = "modelMasterTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public int create (UserCreateModel userCreateModel) throws BusinessException {
        boolean isUserEmailExistent = userDao.isUserEmailExistent(userCreateModel.getEmail());
        if ( isUserEmailExistent ) {
            throw new BusinessException(BusinessException.EMAIL_EXISTS, "Email already exists!");
        }

        User user = new User();
        userCreateModel.copyTo(user);

        int userId = userDao.create(user);

        return userId;
    }

    @Override
    public SubscriptionDto createSubscription (int id, SubscriptionCreateModel subscriptionCreateModel) throws BusinessException {
        User user = userDao.read(id);
        if ( user == null ) {
            throw new BusinessException(BusinessException.USER_NOT_FOUND, "User doesn't exist!");
        }

        boolean isSubscriptionNameExistent = userDao.isSubscriptionNameExistent(subscriptionCreateModel.getName());
        if ( isSubscriptionNameExistent ) {
            throw new BusinessException(BusinessException.NAME_EXISTS, "Subscription name already exists!");
        }

        Subscription subscription = new Subscription();
        subscriptionCreateModel.copyTo(subscription);
        int subscriptionId = userDao.createSubscription(user.getId(), subscription);
        // get Subscription back to get createdAt, updatedAt field
        subscription = userDao.readSubscription(subscriptionId);

        SubscriptionDto subscriptionDto = new SubscriptionDto();
        subscriptionDto.copyFrom(subscription);

        return subscriptionDto;
    }

    @Override
    public UserDto get (int id) throws BusinessException {
        User user = userDao.read(id);
        if ( user == null ) {
            throw new BusinessException(BusinessException.USER_NOT_FOUND, "User doesn't exist!");
        }

        UserDto userDto = new UserDto();
        userDto.copyFrom(user);

        return userDto;
    }

    @Override
    public SubscriptionDto getSubscription (int subscriptionId) throws BusinessException {
        Subscription subscription = userDao.readSubscription(subscriptionId);
        SubscriptionDto subscriptionDto = new SubscriptionDto();
        subscriptionDto.copyFrom(subscription);

        return subscriptionDto;
    }

    @Override
    public List<UserDto> search (UserSearchCriteria userSearchCriteria) throws BusinessException {
        List<User> users = userDao.search(userSearchCriteria);

        List<UserDto> result = new ArrayList<>();
        for (User user : users) {
            UserDto userDto = new UserDto();
            userDto.copyFrom(user);
            result.add(userDto);
        }

        return result;
    }
}
