package com.rms.rms.controller.v1;

import com.rms.rms.common.SearchCriteria;
import com.rms.rms.common.dto.ResponseDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.exception.InvalidViewModelException;
import com.rms.rms.common.view_model.CommissionCalculateModel;
import com.rms.rms.common.view_model.CommissionCheckModel;
import com.rms.rms.common.view_model.CommissionExperienceModel;
import com.rms.rms.common.view_model.CommissionSearchCriteria;
import com.rms.rms.controller.CommissionController;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * homertruong
 */

@RestController
@RequestMapping(value = "/v1/commissions")
public class CommissionControllerV1 extends CommissionController {

    @PostMapping(value = "/calculate", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto calculate(@RequestBody CommissionCalculateModel calculateModel) throws BusinessException {
        return super.calculate(calculateModel);
    }

    @PostMapping(value = "/check_commissions", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto checkCommissions(@RequestBody CommissionCheckModel checkModel) throws BusinessException {
        return super.checkCommissions(checkModel);
    }

    @PostMapping(value = "/evaluate_coasv", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto evaluateCOASV() throws BusinessException {
        return super.evaluateCOASV();
    }

    @PostMapping(value = "/experience", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto experience(@RequestBody CommissionExperienceModel experienceModel) {
        return super.experience(experienceModel);
    }

    @GetMapping(value = "/export")
    public ResponseEntity<byte[]> export(@RequestParam("from") String from, @RequestParam("to") String to)
            throws BusinessException
    {
        Date fromDate, toDate;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        try {
            fromDate = simpleDateFormat.parse(from);
            toDate = simpleDateFormat.parse(to);
        }
        catch (ParseException e) {
            throw new InvalidViewModelException("Error parsing date strings: " + from + " or " + to);
        }
        return super.export(fromDate, toDate);
    }

    @PostMapping(value = "/search", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto search(@RequestBody SearchCriteria<CommissionSearchCriteria> searchCriteria) throws BusinessException {
        return super.search(searchCriteria);
    }
}
