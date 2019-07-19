package com.rms.rms.persistence;

import org.hibernate.criterion.Criterion;

import java.util.Collection;

/**
 * homertruong
 */

public interface CustomFieldFilter {

	Collection<? extends Criterion> filter(String fieldName, Object value);

}
