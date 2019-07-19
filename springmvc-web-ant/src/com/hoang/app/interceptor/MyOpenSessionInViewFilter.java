package com.hoang.app.interceptor;

import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.orm.hibernate3.support.OpenSessionInViewFilter;

/**
 * 
 * @author htruong
 */
public class MyOpenSessionInViewFilter extends OpenSessionInViewFilter {
    /*
     * The default mode is FlushMode.NEVER          
     */
    @Override
    protected Session getSession(SessionFactory sessionFactory)
                        throws DataAccessResourceFailureException {
        Session session = super.getSession(sessionFactory);
        session.setFlushMode(FlushMode.COMMIT);        
        return session;
    }

    /**
    * we do an explicit flush here just in case we do not have an automated flush
    */
    @Override
    protected void closeSession(Session session, SessionFactory factory) {
        session.flush();
        logger.debug(session.toString());
        super.closeSession(session, factory);
    }
}