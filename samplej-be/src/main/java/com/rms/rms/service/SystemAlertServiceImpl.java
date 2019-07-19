package com.rms.rms.service;

import com.rms.rms.common.constant.SystemRole;
import com.rms.rms.common.dto.SystemAlertDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.exception.InvalidViewModelException;
import com.rms.rms.common.util.MyBeanUtil;
import com.rms.rms.common.view_model.SystemAlertUpdateModel;
import com.rms.rms.persistence.GenericDao;
import com.rms.rms.service.model.SystemAlert;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(value = "transactionManager", propagation = Propagation.SUPPORTS)
public class SystemAlertServiceImpl implements SystemAlertService {

    private Logger logger = Logger.getLogger(SystemAlertServiceImpl.class);

    @Autowired
    private ModelMapper beanMapper;

    @Autowired
    private ValidationService validationService;

    @Autowired
    private GenericDao genericDao;

    @Override
    @Secured({SystemRole.ROLE_ADMIN})
    public SystemAlertDto get(String id) throws BusinessException {
        logger.info("get: " + id);

        // validate biz rules
        SystemAlert pdo = validationService.getSystemAlert(id, false);

        return beanMapper.map(pdo, SystemAlertDto.class);
    }

    @Override
    @Secured({SystemRole.ROLE_ADMIN})
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public SystemAlertDto update(String id, SystemAlertUpdateModel updateModel) throws BusinessException {
        logger.info("update: " + id + ", " + updateModel.toString());

        // process view model
        updateModel.escapeHtml();
        String errors = updateModel.validate();
        if (errors != null) {
            throw new InvalidViewModelException(errors);
        }
        SystemAlert detachedDO = beanMapper.map(updateModel, SystemAlert.class);

        // validate biz rules
        SystemAlert existingDO = validationService.getSystemAlert(id, true);

        // map input to existingDO
        MyBeanUtil.mapIgnoreNullProps(existingDO, detachedDO);

        // do biz action
        genericDao.update(existingDO);

        return beanMapper.map(existingDO, SystemAlertDto.class);
    }
    
}
