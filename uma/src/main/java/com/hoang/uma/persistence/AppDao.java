package com.hoang.uma.persistence;

/**
 * homertruong
 */

public interface AppDao {

    <T> T findByEmail(Class<T> clazz, String email);

    <T> T findByName(Class<T> clazz, String name);

    String findIdByEmail(String email);

    <T> boolean isEmailExistent(Class<T> clazz, String email);

    <T> boolean isNameExistent(Class<T> clazz, String name);

}
