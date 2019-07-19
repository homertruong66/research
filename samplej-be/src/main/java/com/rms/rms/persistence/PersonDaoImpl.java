package com.rms.rms.persistence;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * homertruong
 */

@Component("personDao")
@Transactional(value = "transactionManager", propagation = Propagation.SUPPORTS)
public class PersonDaoImpl extends GenericDaoImpl implements PersonDao {

    @Override
    protected <T> Object autoBoxValue(Class<T> clazz, String property, Object value) {
        if (property.equals("discriminator")) {    // inheritance case
            return value;
        }
        else {
            return super.autoBoxValue(clazz, property, value);
        }
    }
}
