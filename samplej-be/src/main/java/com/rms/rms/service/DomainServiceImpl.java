package com.rms.rms.service;

import com.rms.rms.common.SearchCriteria;
import com.rms.rms.common.SearchResult;
import com.rms.rms.common.dto.DomainDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.view_model.DomainSearchCriteria;
import com.rms.rms.persistence.GenericDao;
import com.rms.rms.service.model.Domain;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * homertruong
 */

@Service
@Transactional(value = "transactionManager", propagation = Propagation.SUPPORTS)
public class DomainServiceImpl implements DomainService {

    private Logger logger = Logger.getLogger(DomainServiceImpl.class);

    @Autowired
    private ModelMapper beanMapper;

    @Autowired
    private GenericDao genericDao;

    @Override
    public SearchResult<DomainDto> search(SearchCriteria<DomainSearchCriteria> vmSearchCriteria) throws BusinessException {
        logger.info("search: " + vmSearchCriteria.toString());

        // setup search criteria
        SearchCriteria<Domain> searchCriteria = new SearchCriteria<>();
        beanMapper.map(vmSearchCriteria, searchCriteria);       // map sort, page info
        if (vmSearchCriteria.getCriteria() != null) {
            // map search info
            Domain criteria = beanMapper.map(vmSearchCriteria.getCriteria(), Domain.class);
            searchCriteria.setCriteria(criteria);
        }
        else {
            // no search info found, use default
            searchCriteria.setCriteria(new Domain());
        }

        // do biz action
        SearchResult<Domain> searchResult;
        SearchResult<DomainDto> dtoSearchResult;
        searchResult = genericDao.search(searchCriteria);
        dtoSearchResult = createDtoSearchResult(searchResult);

        return dtoSearchResult;
    }


    // Utilities
    private SearchResult<DomainDto> createDtoSearchResult(SearchResult<Domain> searchResult) {
        SearchResult<DomainDto> result = new SearchResult<>();
        beanMapper.map(searchResult, result);
        List<DomainDto> dtos = new ArrayList<>();
        for (Domain pdo : searchResult.getList()) {
            dtos.add(beanMapper.map(pdo, DomainDto.class));
        }
        result.setList(dtos);

        return result;
    }

}
