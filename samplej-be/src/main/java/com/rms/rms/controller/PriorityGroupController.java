package com.rms.rms.controller;

import com.rms.rms.common.SearchCriteria;
import com.rms.rms.common.SearchResult;
import com.rms.rms.common.dto.PriorityGroupDto;
import com.rms.rms.common.dto.ResponseDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.view_model.*;
import com.rms.rms.service.PriorityGroupService;
import org.springframework.beans.factory.annotation.Autowired;

public class PriorityGroupController {

    @Autowired
    private PriorityGroupService priorityGroupService;

    public ResponseDto addAffiliates(String id, PriorityGroupAffiliatesAssignModel assignModel) throws BusinessException {
        PriorityGroupDto priorityGroupDto = priorityGroupService.addAffiliates(id, assignModel);
        if ( !priorityGroupDto.getErrors().equals("") ) {
            throw new BusinessException(BusinessException.PRIORITY_GROUP_AFFILIATE_ALREADY_ASSIGNED,
                                        priorityGroupDto.getErrors());
        }

        return new ResponseDto(priorityGroupDto);
    }

    public ResponseDto create (String subsCommissionConfigId, PriorityGroupCreateModel createModel) throws BusinessException {
        PriorityGroupDto priorityGroupDto = priorityGroupService.create(subsCommissionConfigId, createModel);
        return new ResponseDto(priorityGroupDto);
    }

    public ResponseDto get (String id) throws BusinessException {
        PriorityGroupDto priorityGroupDto = priorityGroupService.get(id);
        return new ResponseDto(priorityGroupDto);
    }

    public ResponseDto removeAffiliates(String id, PriorityGroupAffiliatesUnassignModel unassignModel) throws BusinessException {
        PriorityGroupDto priorityGroupDto = priorityGroupService.removeAffiliates(id, unassignModel);
        if ( !priorityGroupDto.getErrors().equals("") ) {
            throw new BusinessException(BusinessException.PRIORITY_GROUP_AFFILIATE_NOT_IN,
                                        priorityGroupDto.getErrors());
        }
        return new ResponseDto(priorityGroupDto);
    }

    public ResponseDto search (SearchCriteria<PriorityGroupSearchCriteria> searchCriteria) throws BusinessException {
        SearchResult<PriorityGroupDto> dtoSearchResult = priorityGroupService.search(searchCriteria);
        return new ResponseDto(dtoSearchResult);
    }

    public ResponseDto update (String id, PriorityGroupUpdateModel updateModel) throws BusinessException {
        PriorityGroupDto priorityGroupDto = priorityGroupService.update(id, updateModel);
        return new ResponseDto(priorityGroupDto);
    }
}
