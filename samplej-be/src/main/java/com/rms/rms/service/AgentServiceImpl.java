package com.rms.rms.service;

import com.rms.rms.common.CustomCriteria;
import com.rms.rms.common.SearchCriteria;
import com.rms.rms.common.SearchResult;
import com.rms.rms.common.constant.SystemRole;
import com.rms.rms.common.dto.AgentDto;
import com.rms.rms.common.dto.UserDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.view_model.AgentSearchCriteria;
import com.rms.rms.persistence.GenericDao;
import com.rms.rms.persistence.SpecificDao;
import com.rms.rms.service.model.Agent;
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
public class AgentServiceImpl implements AgentService {

    private Logger logger = Logger.getLogger(AgentServiceImpl.class);

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
    @Secured({SystemRole.ROLE_ADMIN, SystemRole.ROLE_AFFILIATE})
    public SearchResult<AgentDto> search(SearchCriteria<AgentSearchCriteria> vmSearchCriteria) throws BusinessException {
        logger.info("search: " + vmSearchCriteria.toString());

        // setup search criteria
        SearchCriteria<Agent> searchCriteria = new SearchCriteria<>();
        beanMapper.map(vmSearchCriteria, searchCriteria);   // map sort, page info
        if (vmSearchCriteria.getCriteria() != null) {
            // map search info
            Agent criteria = beanMapper.map(vmSearchCriteria.getCriteria(), Agent.class);
            searchCriteria.setCriteria(criteria);
        }
        else {
            // no search info found, use default
            searchCriteria.setCriteria(new Agent());
        }

        // do authorization
            // Admin can search all Agents
            // Affiliate can only search Agents of it
        UserDto loggedUserDto = authenService.getLoggedUser();
        boolean isAffiliate = SystemRole.hasAffiliateRole(loggedUserDto.getRoles());
        if (isAffiliate) {
            if (searchCriteria.getCriteria().getAffiliateId() != null) {
                searchCriteria.getCriteria().setAffiliateId(null);
            }
            CustomCriteria customCriteria = searchCriteria.getCustomCriteria();
            customCriteria.setValue("affiliateId", loggedUserDto.getId());
        }

        // do biz action
        SearchResult<Agent> searchResult;
        SearchResult<AgentDto> dtoSearchResult;
        searchResult = genericDao.search(searchCriteria);
        dtoSearchResult = createDtoSearchResult(searchResult);

        return dtoSearchResult;
    }


    // Utilities
    private SearchResult<AgentDto> createDtoSearchResult(SearchResult<Agent> searchResult) {
        SearchResult<AgentDto> result = new SearchResult<>();
        beanMapper.map(searchResult, result);
        List<AgentDto> dtos = new ArrayList<>();
        for (Agent pdo : searchResult.getList()) {
            dtos.add(beanMapper.map(pdo, AgentDto.class));
        }
        result.setList(dtos);

        return result;
    }
}
