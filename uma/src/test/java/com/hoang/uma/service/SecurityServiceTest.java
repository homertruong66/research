package com.hoang.uma.service;

import com.hoang.uma.common.dto.UserDto;
import com.hoang.uma.common.exception.BusinessException;
import com.hoang.uma.persistence.AppDao;
import com.hoang.uma.persistence.GenericDao;
import com.hoang.uma.service.model.Role;
import com.hoang.uma.service.model.User;
import com.hoang.uma.service.model.UserRole;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mindrot.jbcrypt.BCrypt;
import org.mockito.BDDMockito;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.Date;

/**
 * homertruong
 */

@RunWith(SpringRunner.class)
public class SecurityServiceTest {

    private SecurityService service = new SecurityServiceImpl();

    @MockBean
    private EntityManager entityManager;

    @MockBean
    private GenericDao genericDao;

    @MockBean
    private AppDao appDao;

    @MockBean
    private CriteriaBuilder criteriaBuilder;

    @MockBean
    private CriteriaQuery criteriaQuery;

    @MockBean
    private CriteriaQuery countCriteriaQuery;

    @MockBean
    private TypedQuery query;

    private User user1;
    private User user2;
    private User user3;
    Role roleUser;
    Role roleAdmin;
    Role roleTeacher;
    Role roleStudent;
    private Date now = new Date();

    @Before
    public void setUp() {
        ReflectionTestUtils.setField(service, "beanMapper", ModelMapperFactory.create(), ModelMapper.class);
        //ReflectionTestUtils.setField(service, "beanMapper", DozerFactory.create(), DozerBeanMapper.class);
        ReflectionTestUtils.setField(service, "entityManager", entityManager, EntityManager.class);
        ReflectionTestUtils.setField(service, "genericDao", genericDao, GenericDao.class);
        ReflectionTestUtils.setField(service, "appDao", appDao, AppDao.class);

        roleUser = new Role();
        roleUser.setName("ROLE_USER");
        UserRole userRoleUser1 = new UserRole();
        userRoleUser1.setRole(roleUser);
        UserRole userRoleUser3 = new UserRole();
        userRoleUser3.setRole(roleUser);

        roleAdmin = new Role();
        roleAdmin.setName("ROLE_ADMIN");
        UserRole userRoleAdmin2 = new UserRole();
        userRoleAdmin2.setRole(roleAdmin);
        UserRole userRoleAdmin3 = new UserRole();
        userRoleAdmin3.setRole(roleAdmin);

        user1 = new User();
        user2 = new User();
        user3 = new User();
        this.setData(user1, user2, user3);
        user1.getUserRoles().add(userRoleUser1);
        user2.getUserRoles().add(userRoleAdmin2);
        user3.getUserRoles().add(userRoleUser3);
        user3.getUserRoles().add(userRoleAdmin3);
    }

    @Test
    public void testAuthenticate () throws BusinessException {
        String email = user1.getEmail();
        String password = "pass1";

        BDDMockito.given(appDao.findByEmail(User.class, email)).willReturn(user1);

        UserDto userDto = service.authenticate(email, password);
        Assert.assertEquals(user1.getId(), userDto.getId());
        Assert.assertEquals(user1.getEmail(), userDto.getEmail());
        Assert.assertEquals(user1.getName(), userDto.getName());
        Assert.assertEquals(user1.getPassword(), userDto.getPassword());
        Assert.assertEquals(user1.getCreatedAt().toString(), userDto.getCreatedAt());
        Assert.assertEquals(user1.getUpdatedAt().toString(), userDto.getUpdatedAt());
        Assert.assertEquals(user1.getUserRoles().size(), userDto.getRoles().size());
    }

    @Test
    public void testGetUserByEmail () throws BusinessException {
        String email = user1.getEmail();

        BDDMockito.given(appDao.findByEmail(User.class, email)).willReturn(user1);

        UserDto userDto = service.getUserByEmail(user1.getEmail());
        Assert.assertEquals(user1.getId(), userDto.getId());
        Assert.assertEquals(user1.getEmail(), userDto.getEmail());
        Assert.assertEquals(user1.getName(), userDto.getName());
        Assert.assertEquals(user1.getPassword(), userDto.getPassword());
        Assert.assertEquals(user1.getCreatedAt().toString(), userDto.getCreatedAt());
        Assert.assertEquals(user1.getUpdatedAt().toString(), userDto.getUpdatedAt());
        Assert.assertEquals(user1.getUserRoles().size(), userDto.getRoles().size());
    }

    // Utilities
    private void setData (User user1, User user2, User user3) {
        user1.setId("1");
        user1.setEmail("email1");
        user1.setName("name1");
        user1.setPassword(BCrypt.hashpw("pass1", BCrypt.gensalt(12)));
        user1.setAge(11);
        user1.setCreatedAt(now);
        user1.setUpdatedAt(now);
        user1.setVersion(1);
        user1.setUserRoles(new ArrayList<>());

        user2.setId("2");
        user2.setEmail("email2");
        user2.setName("name2");
        user2.setPassword("pass2");
        user2.setAge(22);
        user2.setCreatedAt(now);
        user2.setUpdatedAt(now);
        user2.setVersion(1);
        user2.setUserRoles(new ArrayList<>());

        user3.setId("3");
        user3.setEmail("email3");
        user3.setName("name3");
        user3.setPassword("pass3");
        user3.setAge(33);
        user3.setCreatedAt(now);
        user3.setUpdatedAt(now);
        user3.setVersion(1);
        user3.setUserRoles(new ArrayList<>());
    }

}
