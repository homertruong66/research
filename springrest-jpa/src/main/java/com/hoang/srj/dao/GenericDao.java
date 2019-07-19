package com.hoang.srj.dao;

import java.io.Serializable;
import java.util.List;

public interface GenericDao {

    <T> Long countAll (Class<T> clazz);

    <T> T create (T pdo);

    <T> void delete (T pdo);

    <T> List<T> findAll (Class<T> clazz);

    <T> boolean has (Class<T> clazz, Serializable id);

    <T> T read (Class<T> clazz, Serializable id);

    <T> T update (T pdo);

}
