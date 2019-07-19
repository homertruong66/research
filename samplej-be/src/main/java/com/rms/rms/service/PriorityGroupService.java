package com.rms.rms.service;

import com.rms.rms.common.SearchCriteria;
import com.rms.rms.common.SearchResult;
import com.rms.rms.common.dto.PriorityGroupDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.view_model.*;

public interface PriorityGroupService {

    PriorityGroupDto addAffiliates (String id, PriorityGroupAffiliatesAssignModel assignModel) throws BusinessException;

    PriorityGroupDto create (String subsCommissionConfigId, PriorityGroupCreateModel createModel) throws BusinessException;

    PriorityGroupDto get (String id) throws BusinessException;

    PriorityGroupDto removeAffiliates(String id, PriorityGroupAffiliatesUnassignModel unassignModel) throws BusinessException;

    SearchResult<PriorityGroupDto> search (SearchCriteria<PriorityGroupSearchCriteria> searchCriteria) throws BusinessException;

    PriorityGroupDto update (String id, PriorityGroupUpdateModel updateModel) throws BusinessException;
}
