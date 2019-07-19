package com.hoang.uma.service;

import com.hoang.uma.persistence.GenericDao;
import com.hoang.uma.common.dto.HealthDto;
import com.hoang.uma.service.model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * homertruong
 */

@RunWith(SpringRunner.class)
public class HealthCheckServiceTest {

    private HealthCheckService service = new HealthCheckServiceImpl();

    @MockBean
    private GenericDao genericDao;

    @Before
    public void setUp() {
        ReflectionTestUtils.setField(service, "genericDao", genericDao, GenericDao.class);
    }

    @Test
    public void testHealthCheck () {
        BDDMockito.given(genericDao.countAll(User.class)).willReturn(1l);
        HealthDto healthDto = service.checkHealth();
        Assert.assertEquals("App Server OK!", healthDto.getAppStatus());
        Assert.assertEquals("DB Master OK!", healthDto.getDbMasterStatus());
        Assert.assertEquals("DB Slave OK!", healthDto.getDbSlaveStatus());
    }

    @Test
    public void shouldHealthCheckFail () {
        BDDMockito.given(genericDao.countAll(User.class)).willThrow(new RuntimeException());

        HealthDto healthDto = service.checkHealth();
        Assert.assertEquals("App Server OK!", healthDto.getAppStatus());
        Assert.assertEquals("DB Master has problem!", healthDto.getDbMasterStatus());
        Assert.assertEquals("DB Slave has problem!", healthDto.getDbSlaveStatus());
    }
}
