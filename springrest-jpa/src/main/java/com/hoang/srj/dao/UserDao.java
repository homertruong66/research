package com.hoang.srj.dao;

import com.hoang.srj.model.User;
import com.hoang.srj.view_model.UserSearchCriteria;

import java.util.List;

public interface UserDao {

    boolean hasEmail (String email);

    List<User> search (UserSearchCriteria searchCriteria);

}
