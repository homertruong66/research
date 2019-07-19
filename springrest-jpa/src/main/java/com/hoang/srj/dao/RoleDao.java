package com.hoang.srj.dao;

import com.hoang.srj.model.Role;

public interface RoleDao {

    Role findByName(String name);

}
