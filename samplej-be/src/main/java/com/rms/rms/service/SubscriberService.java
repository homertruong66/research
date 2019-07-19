package com.rms.rms.service;

import com.rms.rms.common.SearchCriteria;
import com.rms.rms.common.SearchResult;
import com.rms.rms.common.dto.SubscriberDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.view_model.SubscriberCreateModel;
import com.rms.rms.common.view_model.SubscriberSearchCriteria;
import com.rms.rms.common.view_model.SubscriberUpdateModel;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * homertruong
 */

public interface SubscriberService {

    SubscriberDto create(SubscriberCreateModel createModel) throws BusinessException, IOException;

    SubscriberDto get(String id) throws BusinessException;

    SubscriberDto getByDomainName(String domainName) throws BusinessException;

    SubscriberDto renew(String id) throws BusinessException;

    SubscriberDto resetData(String id) throws BusinessException;

    SearchResult<SubscriberDto> search(SearchCriteria<SubscriberSearchCriteria> vmSearchCriteria) throws BusinessException;

    SubscriberDto update(String id, SubscriberUpdateModel updateModel) throws BusinessException;

    SubscriberDto upgrade(String id, String packageType) throws BusinessException;

    void upload(MultipartFile file) throws BusinessException;

}