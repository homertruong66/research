package com.hoang.uma.service;

import com.hoang.uma.common.exception.BusinessException;
import com.hoang.uma.persistence.GenericDao;
import com.hoang.uma.service.model.Domain;
import com.hoang.uma.common.dto.DomainDto;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

/**
 * homertruong
 */

@RunWith(SpringRunner.class)
public class DomainServiceTest {

    private DomainService service = new DomainServiceImpl();

    @MockBean
    private EntityManager entityManager;

    @MockBean
    private GenericDao genericDao;

    private List<Domain> countries = new ArrayList<>();

    @Before
    public void setUp() {
        ReflectionTestUtils.setField(service, "beanMapper", ModelMapperFactory.create(), ModelMapper.class);
        //ReflectionTestUtils.setField(service, "beanMapper", DozerFactory.create(), DozerBeanMapper.class);
        ReflectionTestUtils.setField(service, "entityManager", entityManager, EntityManager.class);
        ReflectionTestUtils.setField(service, "genericDao", genericDao, GenericDao.class);

        Domain country = new Domain();
        country.setCode("Code1");
        country.setName("Name1");
        country.setType("Country");
        countries.add(country);

        country = new Domain();
        country.setCode("Code2");
        country.setName("Name2");
        country.setType("Country");
        countries.add(country);

        country = new Domain();
        country.setCode("Code3");
        country.setName("Name3");
        country.setType("Country");
        countries.add(country);
    }

    @Test
    public void testGetCountries() throws BusinessException {
        BDDMockito.given(genericDao.findAll(Domain.class)).willReturn(countries);

        List<DomainDto> domainDtos = service.getCountries();
        for (int i = 1; i <= domainDtos.size(); i++) {
            DomainDto dto = domainDtos.get(i-1);
            Assert.assertEquals("Code" + i, dto.getCode());
            Assert.assertEquals("Name" + i, dto.getName());
            Assert.assertEquals("Country", dto.getType());
        }
    }

}
