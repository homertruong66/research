package com.hoang.linkredirector.service;

import java.util.List;

import com.hoang.linkredirector.dto.UserDto;
import com.hoang.linkredirector.exception.BusinessException;
import com.hoang.linkredirector.view_model.SubscriptionCreateModel;
import com.hoang.linkredirector.view_model.UserSearchCriteria;
import com.hoang.linkredirector.dto.SubscriptionDto;
import com.hoang.linkredirector.view_model.UserCreateModel;

public interface UserService {

    int create (UserCreateModel userCreateModel) throws BusinessException;

    SubscriptionDto createSubscription (int id, SubscriptionCreateModel subscriptionCreateModel) throws BusinessException;

    UserDto get (int id) throws BusinessException;

    SubscriptionDto getSubscription (int subscriptionId) throws BusinessException;

    List<UserDto> search (UserSearchCriteria userSearchCriteria) throws BusinessException;
}
