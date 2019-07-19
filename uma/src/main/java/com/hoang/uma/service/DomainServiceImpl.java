package com.hoang.uma.service;

import com.hoang.uma.common.dto.DomainDto;
import com.hoang.uma.common.exception.BusinessException;
import com.hoang.uma.persistence.GenericDao;
import com.hoang.uma.service.model.Domain;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

/**
 * homertruong
 */

@Service
public class DomainServiceImpl implements DomainService {

    private Logger logger = Logger.getLogger(DomainServiceImpl.class);

    @Autowired
    private ModelMapper beanMapper;
    //private DozerBeanMapper beanMapper;

    @PersistenceContext(unitName = "uma")
    private EntityManager entityManager;

    @Autowired
    private GenericDao genericDao;

    @Override
    public List<DomainDto> getCountries () throws BusinessException {
        logger.info("get Countries...");

        List<DomainDto> result = new ArrayList<>();
        List<Domain> domains = genericDao.findAll(Domain.class);
        domains.stream().forEach(domain ->  {
            DomainDto domainDto = new DomainDto();
            beanMapper.map(domain, domainDto);
            result.add(domainDto);
        });

        return result;
    }

    ////// Utilities //////

}
