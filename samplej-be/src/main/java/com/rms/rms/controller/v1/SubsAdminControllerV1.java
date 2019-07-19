package com.rms.rms.controller.v1;

import com.rms.rms.common.SearchCriteria;
import com.rms.rms.common.dto.ResponseDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.exception.InvalidViewModelException;
import com.rms.rms.common.view_model.SubsAdminCreateModel;
import com.rms.rms.common.view_model.SubsAdminSearchCriteria;
import com.rms.rms.common.view_model.SubsAdminUpdateModel;
import com.rms.rms.controller.SubsAdminController;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

/**
 * homertruong
 */

@RestController
public class SubsAdminControllerV1 extends SubsAdminController {

    @PostMapping(value = "/v1/subs_admins/{id}/roles/{roleName}")
    public ResponseDto addRole(@PathVariable String id, @PathVariable String roleName) throws BusinessException {
        if (StringUtils.isBlank(roleName)) {
            String errors = "'role_name' can't be empty! &&";
            throw new InvalidViewModelException(errors);
        }
        return super.addRole(id, roleName);
    }

    @PostMapping(value = "/v1/subscribers/{subscriberId}/subs_admins", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto create(@PathVariable String subscriberId,
                              @RequestBody SubsAdminCreateModel createModel) throws BusinessException, IOException {
        return super.create(subscriberId, createModel);
    }

    @DeleteMapping(value = "/v1/subs_admins/{id}")
    public void delete(@PathVariable String id) throws BusinessException {
        super.delete(id);
    }

    @GetMapping(value = "/v1/subs_admins/{id}")
    public ResponseDto get(@PathVariable String id) throws BusinessException {
        return super.get(id);
    }

    @PostMapping(value = "/v1/subs_admins/{id}/grant_root")
    public ResponseDto grantRoot(@PathVariable String id) throws BusinessException {
        return super.grantRoot(id);
    }

    @DeleteMapping(value = "/v1/subs_admins/{id}/roles/{roleName}")
    public ResponseDto removeRole(@PathVariable String id, @PathVariable String roleName) throws BusinessException {
        if (StringUtils.isBlank(roleName)) {
            String errors = "'role_name' can't be empty! &&";
            throw new InvalidViewModelException(errors);
        }
        return super.removeRole(id, roleName);
    }

    @PostMapping(value = "/v1/subs_admins/{id}/revoke_root")
    public ResponseDto revokeRoot(@PathVariable String id) throws BusinessException {
        return super.revokeRoot(id);
    }

    @PostMapping(value = "/v1/subs_admins/search", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto search(@RequestBody SearchCriteria<SubsAdminSearchCriteria> vmSearchCriteria) throws BusinessException {
        return super.search(vmSearchCriteria);
    }

    @PutMapping(value = "/v1/subs_admins/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto update(@PathVariable String id,
                              @RequestBody @Valid SubsAdminUpdateModel updateModel) throws BusinessException {
        return super.update(id, updateModel);
    }

}
