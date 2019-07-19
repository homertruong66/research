package com.rms.rms.service;

import com.rms.rms.common.CustomCriteria;
import com.rms.rms.common.SearchCriteria;
import com.rms.rms.common.SearchResult;
import com.rms.rms.common.config.properties.ApplicationProperties;
import com.rms.rms.common.constant.SystemRole;
import com.rms.rms.common.dto.PerformerDto;
import com.rms.rms.common.dto.UserDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.view_model.PerformerSearchCriteria;
import com.rms.rms.persistence.GenericDao;
import com.rms.rms.service.model.Performer;
import com.rms.rms.service.model.SubsAdmin;
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
public class PerformerServiceImpl implements PerformerService{

    private Logger logger = Logger.getLogger(PerformerServiceImpl.class);

    @Autowired
    private ApplicationProperties applicationProperties;

    @Autowired
    private ModelMapper beanMapper;

    @Autowired
    private ValidationService validationService;

    @Autowired
    private AuthenService authenService;

    @Autowired
    private GenericDao genericDao;

    @Override
    @Secured({SystemRole.ROLE_ADMIN, SystemRole.ROLE_SUBS_ADMIN, SystemRole.ROLE_AFFILIATE})
    public SearchResult<PerformerDto> search(SearchCriteria<PerformerSearchCriteria> vmSearchCriteria) throws BusinessException {
        logger.info("search: " + vmSearchCriteria.toString());

        // setup search criteria
        SearchCriteria<Performer> searchCriteria = new SearchCriteria<>();
        beanMapper.map(vmSearchCriteria, searchCriteria);       // map sort, page info
        if (vmSearchCriteria.getCriteria() != null) {
            // map search info
            Performer criteria = beanMapper.map(vmSearchCriteria.getCriteria(), Performer.class);
            searchCriteria.setCriteria(criteria);
        }
        else {
            // no search info found, use default
            searchCriteria.setCriteria(new Performer());
        }

        // do authorization
            // Admin can search all Performers
            // SubsAdmin can only search Performers of its Subscriber
            // Affiliate can only search himself
        CustomCriteria customCriteria = searchCriteria.getCustomCriteria();
        UserDto loggedUserDto = authenService.getLoggedUser();
        boolean isAdmin = SystemRole.hasAdminRole(loggedUserDto.getRoles());
        boolean isSubsAdmin = SystemRole.hasSubsAdminRole(loggedUserDto.getRoles());
        boolean isAffiliate = SystemRole.hasAffiliateRole(loggedUserDto.getRoles());
        if (!isAdmin) {
            if (isSubsAdmin) {
                SubsAdmin loggedSubsAdmin = validationService.getSubsAdmin(loggedUserDto.getId(), false);
                if (searchCriteria.getCriteria().getSubscriberId() != null) {
                    searchCriteria.getCriteria().setSubscriberId(null);
                }
                customCriteria.setValue("subscriberId", loggedSubsAdmin.getSubscriberId());
            } else if (isAffiliate) {
                if (searchCriteria.getCriteria().getSubscriberId() != null) {
                    searchCriteria.getCriteria().setSubscriberId(null);
                }
                customCriteria.setValue("affiliateId", loggedUserDto.getId());
            }
        }
        // do biz action
        SearchResult<Performer> searchResult;
        SearchResult<PerformerDto> dtoSearchResult;
        searchResult = genericDao.search(searchCriteria);
        dtoSearchResult = createDtoSearchResult(searchResult);

        return dtoSearchResult;
    }


    // Utilities
    private SearchResult<PerformerDto> createDtoSearchResult(SearchResult<Performer> searchResult) {
        SearchResult<PerformerDto> result = new SearchResult<>();
        beanMapper.map(searchResult, result);
        List<PerformerDto> dtos = new ArrayList<>();
        for (Performer pdo : searchResult.getList()) {
            PerformerDto dto = beanMapper.map(pdo, PerformerDto.class);
            dtos.add(dto);
        }
        result.setList(dtos);

        return result;
    }
}
