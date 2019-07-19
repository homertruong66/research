package com.hoang.uma.service;

import com.hoang.uma.persistence.AppDao;
import com.hoang.uma.persistence.GenericDao;
import com.hoang.uma.service.model.UserUpdate;
import com.hoang.uma.common.dto.UserDto;
import com.hoang.uma.service.model.User;
import org.apache.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;

/**
 * homertruong
 */

@Service
public class SecurityServiceImpl implements SecurityService {

    private Logger logger = Logger.getLogger(SecurityServiceImpl.class);

    @Autowired
    private ModelMapper beanMapper;
    //private DozerBeanMapper beanMapper;

    @PersistenceContext(unitName = "uma")
    private EntityManager entityManager;

    @Autowired
    @Qualifier("namedParameterJdbcTemplate")
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private GenericDao genericDao;

    @Autowired
    private AppDao appDao;

    @Override
    public UserDto authenticate(String email, String password) {
        logger.debug("authenticating " + email + "/" + password + "...");

        User user = appDao.findByEmail(User.class, email);
        if (user == null) {
            return null;
        }

        try {
            if (!BCrypt.checkpw(password, user.getPassword())) {
                return null;
            }
        }
        catch (IllegalArgumentException ex) {
            return null;
        }


        UserDto userDto = new UserDto();
        beanMapper.map(user, userDto);

        return userDto;
    }

    @Override
    @Transactional(value = "jdbcTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public String deleteToken(String userId) {
        logger.debug("delete token of user '" + userId + "'");
        String sql = "UPDATE users " +
                     "SET token = NULL " +
                     "WHERE id = :id";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("id", userId);
        namedParameterJdbcTemplate.update(sql, namedParameters, keyHolder);

        // keyHolder.getKey().intValue(); -> get PK in case of insertion
        return userId;
    }

    @Override
    public UserDto getUserByEmail(String email) {
        logger.debug("get a User by email: " + email);

        // get entity
        User user = appDao.findByEmail(User.class, email);
        if ( user == null ) {
            return null;
        }

        // return dto
        UserDto userDto = new UserDto();
        beanMapper.map(user, userDto);

        return userDto;
    }

    @Override
    @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public String insertToken(String userId, String token) {
        logger.debug("insert token for user '" + userId + "'");
        UserUpdate userUpdate = genericDao.read(UserUpdate.class, userId);
        if (userUpdate == null) {
            logger.error("User '" + userId + "' not found!!!!!!");
            return userId;
        }

        // set data
        userUpdate.setToken(token);
        userUpdate.setUpdatedAt(new Date());

        // update entity
        entityManager.detach(userUpdate);
        entityManager.joinTransaction();
        genericDao.update(userUpdate);

        // use native update sql
//        String updateSQL =  "UPDATE users " +
//                            "SET token = :token " +
//                            "WHERE id = :id ";
//        Map<String, Object> params = new HashMap<>();
//        params.put("id", userId);
//        params.put("token", token);
////      An application-managed entity manager participates in a JTA transaction in one of two ways.
////        If the persistence context is created inside the transaction,
////          the persistence provider will automatically synchronize the persistence context with the transaction.
////        If the persistence context was created earlier (outside of a transaction or in a transaction that has since ended),
////          the persistence context can be manually synchronized with the transaction by calling joinTransaction()
//            on the EntityManager interface.
////      Once synchronized, the persistence context will automatically be flushed when the transaction commits.
//        entityManager.joinTransaction();
//        genericDao.executeNativeUpdateSQL(updateSQL, params);

        return userId;
    }
}
