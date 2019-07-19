package com.hoang.uma.controller.v1;

import com.hoang.uma.common.dto.ClazzDto;
import com.hoang.uma.common.dto.ResponseDto;
import com.hoang.uma.common.dto.SearchResultDto;
import com.hoang.uma.common.exception.BusinessException;
import com.hoang.uma.common.view_model.ClazzCreateModel;
import com.hoang.uma.common.view_model.ClazzSearchCriteria;
import com.hoang.uma.common.view_model.ClazzUpdateModel;
import com.hoang.uma.controller.ClazzController;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * homertruong
 */

@RestController
public class ClazzControllerV1 extends ClazzController {

    @RequestMapping(value = "/v1/clazzes/{id}/cancel", method = { RequestMethod.POST })
    public ResponseDto cancel (@PathVariable(name = "id") String id) throws BusinessException {
        return super.cancel(id);
    }

    @RequestMapping(value = "/v1/courses/{id}/clazzes", method = { RequestMethod.POST }, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto create (@PathVariable(name = "id") String courseId,
                          @RequestBody @Valid ClazzCreateModel createModel) throws BusinessException
    {
        return super.create(courseId, createModel);
    }

    @RequestMapping(value = "/v1/clazzes/{id}", method = { RequestMethod.DELETE })
    public void delete (@PathVariable String id) throws BusinessException {
        super.delete(id);
    }

    @RequestMapping(value = "/v1/clazzes/{id}/end", method = { RequestMethod.POST })
    public ResponseDto end (@PathVariable(name = "id") String id) throws BusinessException {
        return super.end(id);
    }

    @RequestMapping(value = "/v1/clazzes/{id}", method = { RequestMethod.GET })
    public ResponseDto get (@PathVariable String id) throws BusinessException {
        return super.get(id);
    }

    @RequestMapping(value = "/v1/clazzes/{id}/register", method = { RequestMethod.POST })
    public void register (@PathVariable String id) throws BusinessException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName(); //get logged in email

        super.register(id, email);
    }

    @RequestMapping(value = "/v1/clazzes/search", method = { RequestMethod.POST }, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto search (@RequestBody @Valid ClazzSearchCriteria searchCriteria) throws BusinessException {
        return super.search(searchCriteria);
    }

    @RequestMapping(value = "/v1/clazzes/assigned", method = { RequestMethod.POST }, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto searchAssigned (@RequestBody @Valid ClazzSearchCriteria searchCriteria) throws BusinessException {
        return super.searchAssigned(searchCriteria);
    }

    @RequestMapping(value = "/v1/clazzes/{id}/start", method = { RequestMethod.POST })
    public ResponseDto start (@PathVariable(name = "id") String id) throws BusinessException {
        return super.start(id);
    }

    @RequestMapping(value = "/v1/clazzes/{id}/unregister", method = { RequestMethod.POST })
    public void unregister (@PathVariable String id) throws BusinessException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName(); //get logged in email

        super.unregister(id, email);
    }

    @RequestMapping(value = "/v1/clazzes/{id}", method = { RequestMethod.PUT }, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto update (@PathVariable String id, @RequestBody @Valid ClazzUpdateModel updateModel) throws BusinessException {
        return super.update(id, updateModel);
    }

}
