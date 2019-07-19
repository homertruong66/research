package com.hoang.linkshortener.service;

import java.util.List;

import com.hoang.linkshortener.dto.UserDto;
import com.hoang.linkshortener.exception.BusinessException;
import com.hoang.linkshortener.view_model.SubscriptionCreateModel;
import com.hoang.linkshortener.view_model.UserSearchCriteria;
import com.hoang.linkshortener.dto.SubscriptionDto;
import com.hoang.linkshortener.view_model.UserCreateModel;

public interface UserService {

    int create (UserCreateModel userCreateModel) throws BusinessException;

    SubscriptionDto createSubscription (int id, SubscriptionCreateModel subscriptionCreateModel) throws BusinessException;

    UserDto get (int id) throws BusinessException;

    SubscriptionDto getSubscription (int subscriptionId) throws BusinessException;

    List<UserDto> search (UserSearchCriteria userSearchCriteria) throws BusinessException;
}
