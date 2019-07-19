package com.hoang.jerseyspringjdbc.dao;

import java.util.List;

import com.hoang.jerseyspringjdbc.model.Subscription;
import com.hoang.jerseyspringjdbc.model.User;
import com.hoang.jerseyspringjdbc.view_model.UserSearchCriteria;

public interface UserDao {

    boolean checkMasterModelsDbConnection ();

    boolean checkSlaveModelsDbConnection ();

    int create (User user);

    int createSubscription (int id, Subscription subscription);

    boolean isUserEmailExistent (String email);

    boolean isSubscriptionNameExistent (String name);

    User read (int id);

    Subscription readSubscription (int subscriptionId);

    List<User> search (UserSearchCriteria userSearchCriteria);

}
