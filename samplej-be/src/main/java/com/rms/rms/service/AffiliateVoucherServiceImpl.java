package com.rms.rms.service;

import com.rms.rms.common.constant.SystemRole;
import com.rms.rms.common.dto.AffiliateVoucherDto;
import com.rms.rms.common.dto.UserDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.exception.InvalidViewModelException;
import com.rms.rms.common.view_model.AffiliateVoucherCreateModel;
import com.rms.rms.persistence.GenericDao;
import com.rms.rms.service.model.Affiliate;
import com.rms.rms.service.model.AffiliateVoucher;
import com.rms.rms.service.model.Voucher;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(value = "transactionManager", propagation = Propagation.SUPPORTS)
public class AffiliateVoucherServiceImpl implements AffiliateVoucherService {

    private Logger logger = Logger.getLogger(AffiliateVoucherServiceImpl.class);

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

    @Override
    @Secured({SystemRole.ROLE_SUBS_ADMIN})
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<AffiliateVoucherDto> create(AffiliateVoucherCreateModel createModel) throws BusinessException {
        logger.info("create: " + createModel.toString());

        // process view model
        createModel.escapeHtml();
        String errors = createModel.validate();
        if (errors != null) {
            throw new InvalidViewModelException(errors);
        }
        List<String> affiliateIds = createModel.getAffiliateIds();
        List<String> voucherIds = createModel.getVoucherIds();

        UserDto loggedUserDto = authenService.getLoggedUser();
        List<AffiliateVoucherDto> dtos = new ArrayList<>();
        for (String voucherId : voucherIds) {
            Voucher voucher = validationService.getVoucher(voucherId, true);
            // do authorization
            authorService.checkSubsAdminAndSubscriber(loggedUserDto.getId(), voucher.getSubscriberId());

            for (String affiliateId : affiliateIds) {
                Affiliate affiliate = validationService.getAffiliate(affiliateId, false);

                // validate biz rules
                Map<String, Object> params = new HashMap<>();
                params.put("affiliateId", affiliateId);
                params.put("voucherId", voucherId);
                AffiliateVoucher existingDO = genericDao.readUnique(AffiliateVoucher.class, params, false);
                if (existingDO != null) {
                    throw new BusinessException(BusinessException.AFFILIATE_VOUCHER_EXISTS,
                            String.format(BusinessException.AFFILIATE_VOUCHER_EXISTS_MESSAGE, affiliateId, voucherId));
                }

                // do authorization
                authorService.checkSubsAdminAndAffiliate(loggedUserDto.getId(), affiliateId);

                // do biz action
                AffiliateVoucher newDO = new AffiliateVoucher();
                newDO.setAffiliate(affiliate);
                newDO.setVoucher(voucher);
                AffiliateVoucher pdo = genericDao.create(newDO);
                dtos.add(beanMapper.map(pdo, AffiliateVoucherDto.class));
            }
            voucher.setStatus(Voucher.STATUS_SENT);
            genericDao.update(voucher);
        }

        return dtos;
    }

}
