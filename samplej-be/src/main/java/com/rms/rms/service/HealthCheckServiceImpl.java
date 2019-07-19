package com.rms.rms.service;

import com.rms.rms.persistence.SpecificDao;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(value = "transactionManager", propagation = Propagation.SUPPORTS)
public class HealthCheckServiceImpl implements HealthCheckService {

    private Logger logger = Logger.getLogger(HealthCheckServiceImpl.class);

    @Autowired
    private SpecificDao specificDao;

    @Override
    public void check() {
        logger.info("healthCheck");
        specificDao.healthCheck();
    }
}
