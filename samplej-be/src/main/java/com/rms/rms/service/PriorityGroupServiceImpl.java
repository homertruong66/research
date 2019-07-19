package com.rms.rms.service;

import com.rms.rms.common.CustomCriteria;
import com.rms.rms.common.SearchCriteria;
import com.rms.rms.common.SearchResult;
import com.rms.rms.common.constant.SystemRole;
import com.rms.rms.common.dto.AffiliateDto;
import com.rms.rms.common.dto.PriorityGroupDto;
import com.rms.rms.common.dto.UserDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.exception.InvalidViewModelException;
import com.rms.rms.common.util.MyBeanUtil;
import com.rms.rms.common.util.MyDateUtil;
import com.rms.rms.common.util.MyNumberUtil;
import com.rms.rms.common.view_model.*;
import com.rms.rms.persistence.GenericDao;
import com.rms.rms.persistence.SpecificDao;
import com.rms.rms.service.model.*;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional(value = "transactionManager", propagation = Propagation.SUPPORTS)
public class PriorityGroupServiceImpl implements PriorityGroupService {
    private static Logger logger = Logger.getLogger(PriorityGroupServiceImpl.class);

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
    @Secured({ SystemRole.ROLE_SUBS_ADMIN })
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public PriorityGroupDto addAffiliates (String id, PriorityGroupAffiliatesAssignModel assignModel) throws BusinessException {
        logger.info("addAffiliates :" + id + ", assignModel : " + assignModel);

        // process view model
        String errors = assignModel.validate();
        if ( errors != null ) {
            throw new InvalidViewModelException(errors);
        }
        List<String> affiliateIds = assignModel.getAffiliateIds();

        // validate biz rules
        PriorityGroup pdo = validationService.getPriorityGroup(id, false);
        String assignErrors = "";
        for (String affiliateId : affiliateIds) {
            if ( isAffiliateInPriorityGroup(affiliateId, pdo.getAffiliates()) ) {
                assignErrors += String.format(BusinessException.PRIORITY_GROUP_AFFILIATE_ALREADY_ASSIGNED_MESSAGE,
                                              affiliateId, id) + " ! && ";
                continue;
            }

            Affiliate affiliate = genericDao.read(Affiliate.class, affiliateId, false);
            if ( affiliate == null ) {
                assignErrors += String.format(BusinessException.AFFILIATE_NOT_FOUND_MESSAGE, affiliateId) + " ! && ";
                continue;
            }

            // check Affiliate and Priority Group belong to the same Subscriber
            SubsCommissionConfig scg = genericDao.read(SubsCommissionConfig.class, pdo.getSubsCommissionConfigId(), false);
            String subscriberId = scg.getSubscriberId();
            List<String> affIds = specificDao.getAffiliateIdsBySubscriberId(subscriberId);
            if (!affIds.contains(affiliateId)) {
                assignErrors += String.format(BusinessException.PRIORITY_GROUP_AFFILIATE_NOT_SAME_SUBSCRIBER_MESSAGE, affiliateId, pdo.getId()) + " ! && ";
                continue;
            }

            // do biz action
            PriorityGroupAffiliate newPriorityGroupAffiliate = new PriorityGroupAffiliate();
            newPriorityGroupAffiliate.setPriorityGroup(pdo);
            newPriorityGroupAffiliate.setAffiliate(affiliate);

            genericDao.create(newPriorityGroupAffiliate);
            pdo.getAffiliates().add(newPriorityGroupAffiliate);
        }

        // do authorization
          // SubsAdmin can only add Affiliates to the PriorityGroup whose Subscriber is its Subscriber
        UserDto loggedUserDto = authenService.getLoggedUser();
        String subscriberId = pdo.getSubsCommissionConfig().getSubscriberId();
        authorService.checkSubsAdminAndSubscriber(loggedUserDto.getId(), subscriberId);

        // do biz action
        PriorityGroupDto priorityGroupDto = beanMapper.map(pdo, PriorityGroupDto.class);
        Set<AffiliateDto> affiliates = pdo
            .getAffiliates()
            .stream()
            .map(priorityGroupAffiliate -> beanMapper.map(priorityGroupAffiliate.getAffiliate(), AffiliateDto.class))
            .collect(Collectors.toSet());

        priorityGroupDto.setAffiliates(affiliates);
        priorityGroupDto.setErrors(assignErrors);

        return priorityGroupDto;
    }

    @Override
    @Secured({ SystemRole.ROLE_SUBS_ADMIN })
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public PriorityGroupDto create (String subsCommissionConfigId, PriorityGroupCreateModel createModel) throws BusinessException {
        logger.info("create: " + createModel);

        // process view model
        String errors = createModel.validate();
        if ( errors != null ) {
            throw new InvalidViewModelException(errors);
        }
        PriorityGroup newDO = beanMapper.map(createModel, PriorityGroup.class);
        newDO.setCommission(MyNumberUtil.roundDouble(createModel.getCommission(), 2));
        newDO.setStartDate(MyDateUtil.convertToUTCDate(newDO.getStartDate()));
        newDO.setEndDate(MyDateUtil.convertToUTCDate(newDO.getEndDate()));

        // validate biz rules
        SubsCommissionConfig subsCommissionConfig = validationService.getSubsCommissionConfig(subsCommissionConfigId, false);
        if ( !subsCommissionConfig.getIsEnabled() ) {
            throw new BusinessException(BusinessException.SUBS_COMMISSION_CONFIG_IS_DISABLED,
                String.format(BusinessException.SUBS_COMMISSION_CONFIG_IS_DISABLED_MESSAGE, subsCommissionConfigId));
        }

        // do authorization
          // SubsAdmin can only create a PriorityGroup whose Subscriber is its Subscriber
        UserDto loggedUserDto = authenService.getLoggedUser();
        String subscriberId = subsCommissionConfig.getSubscriberId();
        authorService.checkSubsAdminAndSubscriber(loggedUserDto.getId(), subscriberId);

        // do biz action
        newDO.setSubsCommissionConfig(subsCommissionConfig);
        PriorityGroup pdo = genericDao.create(newDO);

        return beanMapper.map(pdo, PriorityGroupDto.class);
    }

    @Override
    @Secured({SystemRole.ROLE_SUBS_ADMIN})
    public PriorityGroupDto get (String id) throws BusinessException {
        logger.info("get : " + id);

        // validate biz rules
        PriorityGroup pdo = validationService.getPriorityGroup(id, false);

        // do authorization
          // SubsAdmin can only get the PriorityGroup whose Subscriber is its Subscriber
        UserDto loggedUserDto = authenService.getLoggedUser();
        String subscriberId = pdo.getSubsCommissionConfig().getSubscriberId();
        authorService.checkSubsAdminAndSubscriber(loggedUserDto.getId(), subscriberId);

        // do biz action
        PriorityGroupDto priorityGroupDto = beanMapper.map(pdo, PriorityGroupDto.class);
        Set<AffiliateDto> affiliates = pdo
            .getAffiliates()
            .stream()
            .map(priorityGroupAffiliate -> beanMapper.map(priorityGroupAffiliate.getAffiliate(), AffiliateDto.class))
            .collect(Collectors.toSet());
        priorityGroupDto.setAffiliates(affiliates);

        return priorityGroupDto;
    }

    @Override
    @Secured({ SystemRole.ROLE_SUBS_ADMIN })
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public PriorityGroupDto removeAffiliates(String id, PriorityGroupAffiliatesUnassignModel unassignModel) throws BusinessException {
        logger.info("removeAffiliates :" + id + ", unassignModel : " + unassignModel);

        // process view model
        String errors = unassignModel.validate();
        if ( errors != null ) {
            throw new InvalidViewModelException(errors);
        }
        List<String> affiliateIds = unassignModel.getAffiliateIds();

        // validate biz rules
        PriorityGroup pdo = validationService.getPriorityGroup(id, false);

        // do authorization
          // SubsAdmin can only remove Affiliates of the PriorityGroup whose Subscriber is its Subscriber
        UserDto loggedUserDto = authenService.getLoggedUser();
        String subscriberId = pdo.getSubsCommissionConfig().getSubscriberId();
        authorService.checkSubsAdminAndSubscriber(loggedUserDto.getId(), subscriberId);

        String unassignErrors = "";
        for (String affiliateId : affiliateIds) {
            PriorityGroupAffiliate existingPriorityGroupAffiliate = pdo
                .getAffiliates()
                .stream()
                .filter(priorityGroupAffiliate -> priorityGroupAffiliate.getAffiliateId().equals(affiliateId))
                .findFirst().orElse(null);

            if ( existingPriorityGroupAffiliate == null ) {
                unassignErrors += String.format(BusinessException.PRIORITY_GROUP_AFFILIATE_NOT_IN_MESSAGE,
                                                id, affiliateId) + " ! && ";
                continue;
            }

            // do biz action
            genericDao.delete(existingPriorityGroupAffiliate);
            pdo.getAffiliates().removeIf(
                priorityGroupAffiliate -> priorityGroupAffiliate.getAffiliateId().equals(affiliateId)
            );
        }

        PriorityGroupDto priorityGroupDto = beanMapper.map(pdo, PriorityGroupDto.class);
        Set<AffiliateDto> affiliates = pdo
            .getAffiliates()
            .stream()
            .map(priorityGroupAffiliate -> beanMapper.map(priorityGroupAffiliate.getAffiliate(), AffiliateDto.class))
            .collect(Collectors.toSet());

        priorityGroupDto.setAffiliates(affiliates);
        priorityGroupDto.setErrors(unassignErrors);

        return priorityGroupDto;
    }

    @Override
    @Secured({ SystemRole.ROLE_SUBS_ADMIN })
    public SearchResult<PriorityGroupDto> search (SearchCriteria<PriorityGroupSearchCriteria> searchCriteria) throws BusinessException {
        logger.info("search : " + searchCriteria);

        // setup search criteria
        SearchCriteria<PriorityGroup> priorityGroupCriteria = new SearchCriteria<>();
        beanMapper.map(searchCriteria, priorityGroupCriteria);

        if ( searchCriteria.getCriteria() != null ) {
            PriorityGroup priorityGroup = beanMapper.map(searchCriteria.getCriteria(), PriorityGroup.class);
            priorityGroupCriteria.setCriteria(priorityGroup);
        }
        else {
            priorityGroupCriteria.setCriteria(new PriorityGroup());
        }

        // do authorization
          // SubsAdmin can only search PriorityGroups whose Subscriber is its Subscriber
        UserDto loggedUserDto = authenService.getLoggedUser();
        boolean isSubsAdmin = SystemRole.hasSubsAdminRole(loggedUserDto.getRoles());
        if (isSubsAdmin) {
            SubsAdmin loggedSubsAdmin = genericDao.read(SubsAdmin.class, loggedUserDto.getId(), false);
            List<String> subsCommissionConfigIds = specificDao.getSubsCommissionConfigIdsBySubscriberId(loggedSubsAdmin.getSubscriberId());
            CustomCriteria customCriteria = searchCriteria.getCustomCriteria();
            customCriteria.setValue("subsCommissionConfigId", subsCommissionConfigIds);
        }

        // do biz
        SearchResult<PriorityGroup> searchResult;
        SearchResult<PriorityGroupDto> dtoSearchResult;
        searchResult = genericDao.search(priorityGroupCriteria);
        dtoSearchResult = createDtoSearchResult(searchResult);

        return dtoSearchResult;
    }

    @Override
    @Secured({ SystemRole.ROLE_SUBS_ADMIN })
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public PriorityGroupDto update (String id, PriorityGroupUpdateModel updateModel) throws BusinessException {
        logger.info("update, id :" + id + ", updateModel : " + updateModel);

        // process view model
        String errors = updateModel.validate();
        if ( errors != null ) {
            throw new InvalidViewModelException(errors);
        }
        PriorityGroup detachedDO = beanMapper.map(updateModel, PriorityGroup.class);
        if (updateModel.getCommission() != null) {
            detachedDO.setCommission(MyNumberUtil.roundDouble(updateModel.getCommission(), 2));
        }

        // validate biz rules
        PriorityGroup existingDO = validationService.getPriorityGroup(id, true);

        // check the updateModel's endDate with existing startDate if it presents
        if ( detachedDO.getEndDate() != null && existingDO.getStartDate().after(detachedDO.getEndDate()) ) {
            throw new BusinessException(
                BusinessException.PRIORITY_GROUP_START_DATE_VS_END_DATE,
                String.format(
                    BusinessException.PRIORITY_GROUP_START_DATE_VS_END_DATE_MESSAGE,
                    MyDateUtil.getYYMMDDString(existingDO.getStartDate()),
                    MyDateUtil.getYYMMDDString(detachedDO.getEndDate())
                )
            );
        }

        // check the updateModel's startDate with existing endDate if it presents
        if ( detachedDO.getStartDate() != null && detachedDO.getStartDate().after(existingDO.getEndDate()) ) {
            throw new BusinessException(
                BusinessException.PRIORITY_GROUP_START_DATE_VS_END_DATE,
                String.format(
                    BusinessException.PRIORITY_GROUP_START_DATE_VS_END_DATE_MESSAGE,
                    MyDateUtil.getYYMMDDString(detachedDO.getStartDate()),
                    MyDateUtil.getYYMMDDString(existingDO.getEndDate())
                )
            );
        }

        // do authorization
          // SubsAdmin can only update the PriorityGroup whose Subscriber is its Subscriber
        UserDto loggedUserDto = authenService.getLoggedUser();
        String subscriberId = existingDO.getSubsCommissionConfig().getSubscriberId();
        authorService.checkSubsAdminAndSubscriber(loggedUserDto.getId(), subscriberId);

        // map input to existingDO
        MyBeanUtil.mapIgnoreNullProps(existingDO, detachedDO);

        // do biz action
        genericDao.update(existingDO);

        return beanMapper.map(existingDO, PriorityGroupDto.class);
    }


    // Utilities
    private SearchResult<PriorityGroupDto> createDtoSearchResult (SearchResult<PriorityGroup> searchResult) {
        SearchResult<PriorityGroupDto> result = new SearchResult<>();
        beanMapper.map(searchResult, result);

        List<PriorityGroupDto> dtos = new ArrayList<>();
        for (PriorityGroup pdo : searchResult.getList()) {
            PriorityGroupDto priorityGroupDto = beanMapper.map(pdo, PriorityGroupDto.class);
            Set<AffiliateDto> affiliates = pdo
                    .getAffiliates()
                    .stream()
                    .map(priorityGroupAffiliate -> beanMapper.map(priorityGroupAffiliate.getAffiliate(), AffiliateDto.class))
                    .collect(Collectors.toSet());
            priorityGroupDto.setAffiliates(affiliates);
            dtos.add(priorityGroupDto);
        }
        result.setList(dtos);

        return result;
    }

    private boolean isAffiliateInPriorityGroup (String affiliateId, Set<PriorityGroupAffiliate> pgAffiliates) {
        if ( affiliateId == null || pgAffiliates == null || pgAffiliates.size() == 0 ) {
            return false;
        }

        for (PriorityGroupAffiliate pgAffiliate : pgAffiliates) {
            if ( affiliateId.equals(pgAffiliate.getAffiliateId()) ) {
                return true;
            }
        }

        return false;
    }
}
