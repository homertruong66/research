package com.rms.rms.service;

import com.rms.rms.common.CustomCriteria;
import com.rms.rms.common.SearchCriteria;
import com.rms.rms.common.SearchResult;
import com.rms.rms.common.constant.SystemRole;
import com.rms.rms.common.dto.FeedbackDto;
import com.rms.rms.common.dto.UserDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.exception.InvalidViewModelException;
import com.rms.rms.common.view_model.FeedbackCreateModel;
import com.rms.rms.common.view_model.FeedbackSearchCriteria;
import com.rms.rms.persistence.GenericDao;
import com.rms.rms.service.model.Feedback;
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
public class FeedbackServiceImpl implements FeedbackService {

    private static Logger logger = Logger.getLogger(FeedbackServiceImpl.class);
    @Autowired
    private AuthenService authenService;
    @Autowired
    private ModelMapper beanMapper;
    @Autowired
    private GenericDao genericDao;
    @Autowired
    private ValidationService validationService;

    @Override
    @Secured({SystemRole.ROLE_SUBS_ADMIN, SystemRole.ROLE_AFFILIATE})
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public FeedbackDto create(FeedbackCreateModel createModel) throws BusinessException {
        logger.info("create:" + createModel.toString());

        // process view model
        createModel.escapeHtml();
        String errors = createModel.validate();
        if( errors != null ) {
            throw new InvalidViewModelException(errors);
        }

        Feedback newDO = beanMapper.map(createModel, Feedback.class);

        // validate biz rules
        newDO.setStatus(Feedback.STATUS_NEW);
        newDO.setCreatedBy(authenService.getLoggedUser().getId());

        // do biz action
        Feedback pdo = genericDao.create(newDO);

        return beanMapper.map(pdo, FeedbackDto.class);
    }

    @Override
    @Secured(SystemRole.ROLE_ADMIN)
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public FeedbackDto resolve(String id) throws BusinessException {
        logger.info("resolve: " + id);

        // validate biz rules
        Feedback pdo = validationService.getFeedback(id, true);

        // do authorization

        // do biz action
        pdo.setStatus(Feedback.STATUS_RESOLVED);
        genericDao.update(pdo);

        return beanMapper.map(pdo, FeedbackDto.class);
    }

    @Override
    @Secured({SystemRole.ROLE_ADMIN, SystemRole.ROLE_SUBS_ADMIN, SystemRole.ROLE_AFFILIATE})
    public SearchResult<FeedbackDto> search(SearchCriteria<FeedbackSearchCriteria> vmSearchCriteria) throws BusinessException {
        logger.info("search:" + vmSearchCriteria);
        // setup search criteria

        SearchCriteria<Feedback> searchCriteria = new SearchCriteria<>();
        beanMapper.map(vmSearchCriteria, searchCriteria);   // map sort, page info
        if( vmSearchCriteria.getCriteria() != null ) {
            // map search info
            Feedback criteria = beanMapper.map(vmSearchCriteria.getCriteria(), Feedback.class);
            searchCriteria.setCriteria(criteria);
        }
        else {
            // no search info found, use default
            searchCriteria.setCriteria(new Feedback());
        }

        // do authorization
          // Admin can search all Feedbacks
          // SubsAdmin can only search its Feedbacks
          // Affiliate can only search its Feedbacks
        UserDto loggedUserDto = authenService.getLoggedUser();
        boolean isAdmin = SystemRole.hasAdminRole(loggedUserDto.getRoles());
        if( !isAdmin ) {
            CustomCriteria customCriteria = searchCriteria.getCustomCriteria();
            customCriteria.setValue("createdBy", loggedUserDto.getId());
        }

        // do biz action
        SearchResult<Feedback> searchResult;
        SearchResult<FeedbackDto> dtoSearchResult;
        searchResult = genericDao.search(searchCriteria);
        dtoSearchResult = this.createDtoSearchResult(searchResult);

        return dtoSearchResult;
    }


    // Utilities
    private SearchResult<FeedbackDto> createDtoSearchResult(SearchResult<Feedback> searchResult) {
        SearchResult<FeedbackDto> result = new SearchResult<>();
        beanMapper.map(searchResult, result);

        List<FeedbackDto> dtos = new ArrayList<>();
        for( Feedback pdo : searchResult.getList() ) {
            dtos.add(beanMapper.map(pdo, FeedbackDto.class));
        }
        result.setList(dtos);

        return result;
    }
}
