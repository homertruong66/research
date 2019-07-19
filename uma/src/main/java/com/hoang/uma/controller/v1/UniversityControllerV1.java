package com.hoang.uma.controller.v1;

import com.hoang.uma.common.dto.ResponseDto;
import com.hoang.uma.common.exception.BusinessException;
import com.hoang.uma.common.view_model.UniversityCreateModel;
import com.hoang.uma.common.view_model.UniversitySearchCriteria;
import com.hoang.uma.common.view_model.UniversityUpdateModel;
import com.hoang.uma.controller.UniversityController;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * homertruong
 */

@RestController
public class UniversityControllerV1 extends UniversityController {

    @RequestMapping(value = "/v1/universities", method = { RequestMethod.POST }, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto create (@RequestBody @Valid UniversityCreateModel createModel) throws BusinessException {
        return super.create(createModel);
    }

    @RequestMapping(value = "/v1/universities/{id}", method = { RequestMethod.DELETE })
    public void delete (@PathVariable String id) throws BusinessException {
        super.delete(id);
    }

    @RequestMapping(value = "/v1/universities/{id}", method = { RequestMethod.GET })
    public ResponseDto get (@PathVariable String id) throws BusinessException {
        return super.get(id);
    }

    @RequestMapping(value = "/v1/universities/search", method = { RequestMethod.POST }, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto search (@RequestBody @Valid UniversitySearchCriteria searchCriteria) throws BusinessException {
        return super.search(searchCriteria);
    }

    @RequestMapping(value = "/v1/universities/{id}", method = { RequestMethod.PUT }, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto update (@PathVariable String id,
                          @RequestBody @Valid UniversityUpdateModel updateModel) throws BusinessException {
        return super.update(id, updateModel);
    }

}
