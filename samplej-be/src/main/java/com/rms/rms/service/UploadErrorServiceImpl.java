package com.rms.rms.service;

import com.rms.rms.common.SearchCriteria;
import com.rms.rms.common.SearchResult;
import com.rms.rms.common.constant.SystemRole;
import com.rms.rms.common.dto.UploadErrorDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.view_model.UploadErrorSearchCriteria;
import com.rms.rms.persistence.GenericDao;
import com.rms.rms.persistence.SpecificDao;
import com.rms.rms.service.model.UploadError;
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
public class UploadErrorServiceImpl implements UploadErrorService {

    private Logger logger = Logger.getLogger(UploadErrorServiceImpl.class);

    @Autowired
    private ModelMapper beanMapper;

    @Autowired
    private AuthenService authenService;

    @Autowired
    private GenericDao genericDao;

    @Autowired
    private SpecificDao specificDao;

    @Override
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public UploadError create (UploadError uploadError) throws BusinessException {
        logger.info("create: " + uploadError.toString());

        return genericDao.create(uploadError);
    }

    @Override
    @Secured({SystemRole.ROLE_ADMIN})
    public SearchResult<UploadErrorDto> search (SearchCriteria<UploadErrorSearchCriteria> vmSearchCriteria) throws BusinessException {
        logger.info("create: " + vmSearchCriteria.toString());

        // setup search criteria
        SearchCriteria<UploadError> searchCriteria = new SearchCriteria<>();
        beanMapper.map(vmSearchCriteria, searchCriteria);       // map sort, page info
        if (vmSearchCriteria.getCriteria() != null) {
            // map search info
            UploadError criteria = beanMapper.map(vmSearchCriteria.getCriteria(), UploadError.class);
            searchCriteria.setCriteria(criteria);
        }
        else {
            // no search info found, use default
            searchCriteria.setCriteria(new UploadError());
        }

        // do biz action
        SearchResult<UploadError> searchResult;
        SearchResult<UploadErrorDto> dtoSearchResult;
        searchResult = genericDao.search(searchCriteria);
        dtoSearchResult = createDtoSearchResult(searchResult);

        return dtoSearchResult;
    }


    // Utilities
    private SearchResult<UploadErrorDto> createDtoSearchResult(SearchResult<UploadError> searchResult) {
        SearchResult<UploadErrorDto> result = new SearchResult<>();
        beanMapper.map(searchResult, result);
        List<UploadErrorDto> dtos = new ArrayList<>();
        for (UploadError pdo : searchResult.getList()) {
            dtos.add(beanMapper.map(pdo, UploadErrorDto.class));
        }
        result.setList(dtos);

        return result;
    }

}