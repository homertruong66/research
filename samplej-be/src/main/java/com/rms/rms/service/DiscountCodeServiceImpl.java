package com.rms.rms.service;

import com.rms.rms.common.CustomCriteria;
import com.rms.rms.common.SearchCriteria;
import com.rms.rms.common.SearchResult;
import com.rms.rms.common.constant.SystemRole;
import com.rms.rms.common.dto.DiscountCodeDto;
import com.rms.rms.common.dto.UserDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.exception.InvalidViewModelException;
import com.rms.rms.common.util.MyBeanUtil;
import com.rms.rms.common.util.MyDateUtil;
import com.rms.rms.common.util.MyNumberUtil;
import com.rms.rms.common.view_model.DiscountCodeCreateModel;
import com.rms.rms.common.view_model.DiscountCodeSearchCriteria;
import com.rms.rms.common.view_model.DiscountCodeUpdateModel;
import com.rms.rms.persistence.GenericDao;
import com.rms.rms.persistence.SpecificDao;
import com.rms.rms.service.model.DiscountCode;
import com.rms.rms.service.model.PackageConfigApplied;
import com.rms.rms.service.model.Subscriber;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * homertruong
 */

@Service
@Transactional(value = "transactionManager", propagation = Propagation.SUPPORTS)
public class DiscountCodeServiceImpl implements DiscountCodeService {

    private Logger logger = Logger.getLogger(DiscountCodeServiceImpl.class);

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
    @Secured({SystemRole.ROLE_AFFILIATE})
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public DiscountCodeDto create(DiscountCodeCreateModel createModel) throws BusinessException {
        logger.info("create: " + createModel.toString());

        // process view model
        createModel.escapeHtml();
        String errors = createModel.validate();
        if (errors != null) {
            throw new InvalidViewModelException(errors);
        }
        DiscountCode newDO = beanMapper.map(createModel, DiscountCode.class);
        newDO.setDiscount(MyNumberUtil.roundDouble(createModel.getDiscount(), 2));
        newDO.setStartDate(MyDateUtil.convertToUTCDate(newDO.getStartDate()));
        if (newDO.getEndDate() != null) {
            newDO.setEndDate(MyDateUtil.convertToUTCDate(newDO.getEndDate()));
        }

        // validate biz rules
        Subscriber subscriber = validationService.getSubscriber(newDO.getSubscriberId(), false);

          // code must be unique in a Subscriber
        String subscriberId = newDO.getSubscriberId();
        Map<String, Object> params = new HashMap<>();
        params.put("code", newDO.getCode());
        params.put("subscriberId", subscriberId);
        DiscountCode discountCode = genericDao.readUnique(DiscountCode.class, params,false);
        if (discountCode != null) {
            throw new BusinessException(BusinessException.DISCOUNT_CODE_CODE_EXISTS_IN_SUBSCRIBER,
                    String.format(BusinessException.DISCOUNT_CODE_CODE_EXISTS_IN_SUBSCRIBER_MESSAGE, newDO.getCode(), subscriberId));
        }

        // check Subscriber package
        PackageConfigApplied pca = validationService.getPackageConfigApplied(subscriberId, false);
        if (!pca.getHasDiscountCode()) {
            throw new BusinessException(BusinessException.DISCOUNT_CODE_SUBSCRIBER_PACKAGE_NOT_ALLOWED,
                    String.format(BusinessException.DISCOUNT_CODE_SUBSCRIBER_PACKAGE_NOT_ALLOWED_MESSAGE, subscriberId));
        }

        Date today = MyDateUtil.convertToUTCDate(new Date());
        if (pca.getUsageExpiredAt().before(today)) {
            throw new BusinessException(BusinessException.SUBSCRIBER_USAGE_EXPIRED,
                    String.format(BusinessException.SUBSCRIBER_USAGE_EXPIRED_MESSAGE, pca.getUsageExpiredAt()));
        }

        // do authorization
            // Affiliate can only create DiscountCode on its Subscriber
        UserDto loggedUserDto = authenService.getLoggedUser();
        authorService.checkAffiliateAndSubscriber(loggedUserDto.getId(), subscriberId);

        // do biz action
        newDO.setAffiliateId(loggedUserDto.getId());
        newDO.setStartDate(MyDateUtil.convertToUTCDate(newDO.getStartDate()));
        newDO.setSubscriber(subscriber);
        DiscountCode pdo = genericDao.create(newDO);

        return beanMapper.map(pdo, DiscountCodeDto.class);
    }

    @Override
    @Secured({SystemRole.ROLE_AFFILIATE})
    public DiscountCodeDto get(String id) throws BusinessException {
        logger.info("get: " + id);

        // validate biz rules
        DiscountCode pdo = validationService.getDiscountCode(id, false);

        // do authorization
            // Affiliate can only get its DiscountCode
        UserDto loggedUserDto = authenService.getLoggedUser();
        if (!loggedUserDto.getId().equals(pdo.getAffiliateId())) {
            throw new BusinessException(BusinessException.DISCOUNT_CODE_LOGGED_AFFILIATE_NOT_CREATOR,
                String.format(BusinessException.DISCOUNT_CODE_LOGGED_AFFILIATE_NOT_CREATOR_MESSAGE,
                              loggedUserDto.getId(), id));
        }

        return beanMapper.map(pdo, DiscountCodeDto.class);
    }

    @Override
    @Secured({SystemRole.ROLE_AFFILIATE})
    public SearchResult<DiscountCodeDto> search(SearchCriteria<DiscountCodeSearchCriteria> vmSearchCriteria) throws BusinessException {
        logger.info("search: " + vmSearchCriteria.toString());

        // setup search criteria
        SearchCriteria<DiscountCode> searchCriteria = new SearchCriteria<>();
        beanMapper.map(vmSearchCriteria, searchCriteria);       // map sort, page info
        if (vmSearchCriteria.getCriteria() != null) {
            // map search info
            DiscountCode criteria = beanMapper.map(vmSearchCriteria.getCriteria(), DiscountCode.class);
            searchCriteria.setCriteria(criteria);
        }
        else {
            // no search info found, use default
            searchCriteria.setCriteria(new DiscountCode());
        }

        // do authorization
          // Affiliate can only search its DiscountCodes
        UserDto loggedUserDto = authenService.getLoggedUser();
        CustomCriteria customCriteria = searchCriteria.getCustomCriteria();
        customCriteria.setValue("affiliateId", loggedUserDto.getId());

        // do biz action
        SearchResult<DiscountCode> searchResult;
        SearchResult<DiscountCodeDto> dtoSearchResult;
        searchResult = genericDao.search(searchCriteria);
        dtoSearchResult = createDtoSearchResult(searchResult);

        return dtoSearchResult;
    }

    @Override
    @Secured({SystemRole.ROLE_AFFILIATE})
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public DiscountCodeDto update(String id, DiscountCodeUpdateModel updateModel) throws BusinessException {
        logger.info("update: " + id + " - " + updateModel);

        // process view model
        updateModel.escapeHtml();
        String errors = updateModel.validate();
        if (errors != null) {
            throw new InvalidViewModelException(errors);
        }
        DiscountCode detachedDO = beanMapper.map(updateModel, DiscountCode.class);
        if (updateModel.getDiscount() != null) {
            detachedDO.setDiscount(MyNumberUtil.roundDouble(updateModel.getDiscount(), 2));
        }

        // validate biz rules
        DiscountCode existingDO = validationService.getDiscountCode(id, true);
          // update code?
        if (detachedDO.getCode() != null && !detachedDO.getCode().equals(existingDO.getCode())) {
            Map<String, Object> params = new HashMap<>();
            params.put("code", detachedDO.getCode());
            params.put("subscriberId", existingDO.getSubscriberId());
            DiscountCode discountCode = genericDao.readUnique(DiscountCode.class, params,false);
            if (discountCode != null) {
                throw new BusinessException(BusinessException.DISCOUNT_CODE_CODE_EXISTS_IN_SUBSCRIBER,
                        String.format(BusinessException.DISCOUNT_CODE_CODE_EXISTS_IN_SUBSCRIBER_MESSAGE,
                                      detachedDO.getCode(), existingDO.getSubscriberId()));
            }
        }
          // update both startDate & endDate are validated in view model
          // startDate & not endDate?
        if (detachedDO.getStartDate() != null && detachedDO.getEndDate() == null) {
            if (existingDO.getEndDate() != null) {  // compare startDate with existing endDate
                if (detachedDO.getStartDate().after(existingDO.getEndDate())) {
                    throw new BusinessException(BusinessException.DISCOUNT_CODE_START_DATE_UPDATE_INVALID,
                            BusinessException.DISCOUNT_CODE_START_DATE_UPDATE_INVALID_MESSAGE);
                }
            }
            detachedDO.setStartDate(MyDateUtil.convertToUTCDate(detachedDO.getStartDate()));
        }
          // endDate & not startDate?
        if (detachedDO.getEndDate() != null && detachedDO.getStartDate() == null) { // update endDate, not startDate
            if (existingDO.getStartDate().after(detachedDO.getEndDate())) { // compare existing startDate with endDate
                throw new BusinessException(BusinessException.DISCOUNT_CODE_END_DATE_UPDATE_INVALID,
                        BusinessException.DISCOUNT_CODE_END_DATE_UPDATE_INVALID_MESSAGE);
            }
            detachedDO.setEndDate(MyDateUtil.convertToUTCDate(detachedDO.getEndDate()));
        }

        // check Subscriber package
        String subscriberId = existingDO.getSubscriberId();
        PackageConfigApplied pca = validationService.getPackageConfigApplied(subscriberId, false);
        if (!pca.getHasDiscountCode()) {
            throw new BusinessException(BusinessException.DISCOUNT_CODE_SUBSCRIBER_PACKAGE_NOT_ALLOWED,
                    String.format(BusinessException.DISCOUNT_CODE_SUBSCRIBER_PACKAGE_NOT_ALLOWED_MESSAGE, subscriberId));
        }

        Date today = MyDateUtil.convertToUTCDate(new Date());
        if (pca.getUsageExpiredAt().before(today)) {
            throw new BusinessException(BusinessException.SUBSCRIBER_USAGE_EXPIRED,
                    String.format(BusinessException.SUBSCRIBER_USAGE_EXPIRED_MESSAGE, pca.getUsageExpiredAt()));
        }

        // do authorization
            // Affiliate can only update its DiscountCode
        UserDto loggedUserDto = authenService.getLoggedUser();
        if (!loggedUserDto.getId().equals(existingDO.getAffiliateId())) {
            throw new BusinessException(BusinessException.DISCOUNT_CODE_LOGGED_AFFILIATE_NOT_CREATOR,
                    String.format(BusinessException.DISCOUNT_CODE_LOGGED_AFFILIATE_NOT_CREATOR_MESSAGE,
                                  loggedUserDto.getId(), id));
        }

        // map input to existingDO
        MyBeanUtil.mapIgnoreNullProps(existingDO, detachedDO);

        // do biz action
        genericDao.update(existingDO);

        return beanMapper.map(existingDO, DiscountCodeDto.class);
    }


    // Utilities
    private SearchResult<DiscountCodeDto> createDtoSearchResult(SearchResult<DiscountCode> searchResult) {
        SearchResult<DiscountCodeDto> result = new SearchResult<>();
        beanMapper.map(searchResult, result);
        List<DiscountCodeDto> dtos = new ArrayList<>();
        for (DiscountCode pdo : searchResult.getList()) {
            dtos.add(beanMapper.map(pdo, DiscountCodeDto.class));
        }
        result.setList(dtos);

        return result;
    }
}
