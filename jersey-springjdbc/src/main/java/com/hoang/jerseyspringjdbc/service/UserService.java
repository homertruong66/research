package com.hoang.jerseyspringjdbc.service;

import java.util.List;

import com.hoang.jerseyspringjdbc.dto.UserDto;
import com.hoang.jerseyspringjdbc.exception.BusinessException;
import com.hoang.jerseyspringjdbc.view_model.SubscriptionCreateModel;
import com.hoang.jerseyspringjdbc.view_model.UserSearchCriteria;
import com.hoang.jerseyspringjdbc.dto.SubscriptionDto;
import com.hoang.jerseyspringjdbc.view_model.UserCreateModel;

public interface UserService {

    int create (UserCreateModel userCreateModel) throws BusinessException;

    SubscriptionDto createSubscription (int id, SubscriptionCreateModel subscriptionCreateModel) throws BusinessException;

    UserDto get (int id) throws BusinessException;

    SubscriptionDto getSubscription (int subscriptionId) throws BusinessException;

    List<UserDto> search (UserSearchCriteria userSearchCriteria) throws BusinessException;
}
