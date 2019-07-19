package com.rms.rms.common.util;

import com.rms.rms.common.exception.BusinessException;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.hibernate.Hibernate;

import java.lang.reflect.InvocationTargetException;

/**
 * homertruong
 */

public class MyBeanUtil {

	public static void mapIgnoreNullProps(Object dest, Object source) throws BusinessException {
		try {
			BeanUtilsBean nullAwareBeanUtilsBean = new MyNullAwareBeanUtilsBean();
			nullAwareBeanUtilsBean.copyProperties(dest, source);
		}
		catch (IllegalAccessException | InvocationTargetException e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	public static void triggerLazyLoad(Object object) {
		Hibernate.initialize(object);
	}

}
