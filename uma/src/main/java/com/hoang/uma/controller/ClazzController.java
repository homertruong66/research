package com.hoang.uma.controller;

import com.hoang.uma.common.dto.ResponseDto;
import com.hoang.uma.common.exception.BusinessException;
import com.hoang.uma.common.view_model.ClazzCreateModel;
import com.hoang.uma.common.view_model.ClazzSearchCriteria;
import com.hoang.uma.common.view_model.ClazzUpdateModel;
import com.hoang.uma.service.ClazzService;
import com.hoang.uma.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.validation.Valid;

/**
 * homertruong
 */

public class ClazzController {

    @Autowired
    protected ClazzService clazzService;

    @Autowired
    protected UserService userService;

    public ResponseDto cancel (String id) throws BusinessException {
        return new ResponseDto(clazzService.cancel(id));
    }

    public ResponseDto create (String courseId,
                               @Valid ClazzCreateModel createModel) throws BusinessException
    {
        return new ResponseDto(clazzService.create(courseId, createModel));
    }

    public void delete (String id) throws BusinessException {
        clazzService.delete(id);
    }

    public ResponseDto end (String id) throws BusinessException {
        return new ResponseDto(clazzService.end(id));
    }

    public ResponseDto get (String id) throws BusinessException {
        return new ResponseDto(clazzService.get(id));
    }

    public void register (String id, String email) throws BusinessException {
        clazzService.register(id, email);
    }

    public ResponseDto search (ClazzSearchCriteria searchCriteria) throws BusinessException {
        return new ResponseDto(clazzService.search(searchCriteria));
    }

    public ResponseDto searchAssigned (ClazzSearchCriteria searchCriteria) throws BusinessException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        searchCriteria.setTeacherId(userService.getIdByEmail(email));

        return new ResponseDto(clazzService.search(searchCriteria));
    }

    public ResponseDto start (String id) throws BusinessException {
        return new ResponseDto(clazzService.start(id));
    }

    public void unregister (String id, String email) throws BusinessException {
        clazzService.unregister(id, email);
    }

    public ResponseDto update (String id, @Valid ClazzUpdateModel updateModel) throws BusinessException {
        return new ResponseDto(clazzService.update(id, updateModel));
    }

}
