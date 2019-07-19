package com.rms.rms.service;

import com.rms.rms.common.constant.SystemRole;
import com.rms.rms.common.dto.SubsAlertConfigDto;
import com.rms.rms.common.dto.UserDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.exception.InvalidViewModelException;
import com.rms.rms.common.util.MyBeanUtil;
import com.rms.rms.common.view_model.SubsAlertConfigUpdateModel;
import com.rms.rms.persistence.GenericDao;
import com.rms.rms.persistence.SpecificDao;
import com.rms.rms.service.model.SubsAlertConfig;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(value = "transactionManager", propagation = Propagation.SUPPORTS)
public class SubsAlertConfigServiceImpl implements SubsAlertConfigService {

    private Logger logger = Logger.getLogger(SubsAlertConfigServiceImpl.class);

    @Autowired
    private ModelMapper beanMapper;

    @Autowired
    private ValidationService validationService;

    @Autowired
    private AuthenService authenService;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private GenericDao genericDao;

    @Autowired
    private SpecificDao specificDao;

    @Override
    @Secured({SystemRole.ROLE_SUBS_ADMIN})
    public SubsAlertConfigDto get(String id) throws BusinessException {
        logger.info("get: " + id);

        // validate biz rules
        SubsAlertConfig pdo = validationService.getSubsAlertConfig(id, false);

        // do authorization
            // SubsAdmin can only get the SubsAlertConfig of its Subscriber
        UserDto loggedUserDto = authenService.getLoggedUser();
        String subscriberId = pdo.getId();
        authorService.checkSubsAdminAndSubscriber(loggedUserDto.getId(), subscriberId);

        return beanMapper.map(pdo, SubsAlertConfigDto.class);
    }

    @Override
    @Secured({SystemRole.ROLE_AFFILIATE})
    public List<SubsAlertConfigDto> list() throws BusinessException {
        logger.info("list");

        UserDto loggedUserDto = authenService.getLoggedUser();

        List<SubsAlertConfig> subsAlertConfigs = specificDao.getSubsAlertConfigsByAffiliateId(loggedUserDto.getId());
        List<SubsAlertConfigDto> result = new ArrayList<>();
        for (SubsAlertConfig pdo: subsAlertConfigs) {
            SubsAlertConfigDto subsAlertConfigDto = beanMapper.map(pdo, SubsAlertConfigDto.class);
            result.add(subsAlertConfigDto);
        }

        return result;
    }

    @Override
    @Secured({SystemRole.ROLE_SUBS_ADMIN})
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public SubsAlertConfigDto update(String id, SubsAlertConfigUpdateModel updateModel) throws BusinessException {
        logger.info("update: " + id + ", " + updateModel.toString());

        // process view model
        updateModel.escapeHtml();
        String errors = updateModel.validate();
        if (errors != null) {
            throw new InvalidViewModelException(errors);
        }
        SubsAlertConfig detachedDO = beanMapper.map(updateModel, SubsAlertConfig.class);

        // validate biz rules
        SubsAlertConfig existingDO = validationService.getSubsAlertConfig(id, true);

        // do authorization
            // SubsAdmin can only update the SubsAlertConfig of its Subscriber
        UserDto loggedUserDto = authenService.getLoggedUser();
        String subscriberId = existingDO.getId();
        authorService.checkSubsAdminAndSubscriber(loggedUserDto.getId(), subscriberId);


        // map input to existingDO
        MyBeanUtil.mapIgnoreNullProps(existingDO, detachedDO);

        // do biz action
        genericDao.update(existingDO);

        return beanMapper.map(existingDO, SubsAlertConfigDto.class);
    }
    
}
