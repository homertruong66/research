package com.rms.rms.controller.v1;

import com.rms.rms.common.SearchCriteria;
import com.rms.rms.common.dto.ResponseDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.view_model.*;
import com.rms.rms.controller.PriorityGroupController;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/v1")
public class PriorityGroupControllerV1 extends PriorityGroupController {

    @Override
    @PostMapping(value = "/priority_groups/{id}/priority_groups_affiliates", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto addAffiliates(@PathVariable @NotNull String id,
                                     @RequestBody PriorityGroupAffiliatesAssignModel assignModel) throws BusinessException {
        return super.addAffiliates(id, assignModel);
    }

    @Override
    @PostMapping(value = "/subs_commission_configs/{subsCommissionConfigId}/priority_groups", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto create (@PathVariable @NotNull String subsCommissionConfigId,
                               @RequestBody PriorityGroupCreateModel createModel) throws BusinessException {
        return super.create(subsCommissionConfigId, createModel);
    }

    @Override
    @GetMapping(value = "/priority_groups/{id}")
    public ResponseDto get (@PathVariable @NotNull String id) throws BusinessException {
        return super.get(id);
    }

    @Override
    @DeleteMapping(value = "/priority_groups/{id}/priority_groups_affiliates",
                   consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto removeAffiliates(@PathVariable String id, @RequestBody PriorityGroupAffiliatesUnassignModel unassignModel) throws BusinessException {
        return super.removeAffiliates(id, unassignModel);
    }

    @Override
    @PostMapping(value = "/priority_groups/search", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto search (@RequestBody SearchCriteria<PriorityGroupSearchCriteria> searchCriteria) throws BusinessException {
        return super.search(searchCriteria);
    }

    @Override
    @PutMapping(value = "/priority_groups/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto update (@PathVariable String id, @RequestBody PriorityGroupUpdateModel updateModel) throws BusinessException {
        return super.update(id, updateModel);
    }
}
