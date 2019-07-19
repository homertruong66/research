package com.hoang.srj.controller.v1;

import com.hoang.srj.controller.UniversityController;
import com.hoang.srj.dto.UniversityDto;
import com.hoang.srj.exception.BusinessException;
import com.hoang.srj.view_model.UniversityCreateModel;
import com.hoang.srj.view_model.UniversitySearchCriteria;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
public class UniversityControllerV1 extends UniversityController {

    @RequestMapping(value = "/v1/universities", method = { RequestMethod.POST })
    public UniversityDto create (@Valid UniversityCreateModel createModel) throws BusinessException {
        return super.create(createModel);
    }

    @RequestMapping(value = "/v1/universities/{id}", method = { RequestMethod.GET })
    public UniversityDto get (@PathVariable String id) throws BusinessException {
        return super.get(id);
    }

    @RequestMapping(value = "/v1/universities/search", method = { RequestMethod.GET })
    public List<UniversityDto> search (@Valid UniversitySearchCriteria searchCriteria) throws BusinessException {
        return super.search(searchCriteria);
    }

}
