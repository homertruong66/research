package com.rms.rms.controller.v1;

import com.rms.rms.common.SearchCriteria;
import com.rms.rms.common.dto.ResponseDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.exception.InvalidViewModelException;
import com.rms.rms.common.view_model.CustomerSearchCriteria;
import com.rms.rms.controller.CustomerController;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * homertruong
 */

@RestController
@RequestMapping(value = "/v1/customers")
public class CustomerControllerV1 extends CustomerController {

    @GetMapping(value = "/export")
    public ResponseEntity<byte[]> export(@RequestParam("from") String from,
                                         @RequestParam("to") String to, HttpServletResponse response) throws BusinessException {
        Date fromDate, toDate;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        try {
            fromDate = simpleDateFormat.parse(from);
            toDate = simpleDateFormat.parse(to);
        }
        catch (ParseException e) {
            throw new InvalidViewModelException("Error parsing date strings: " + from + " or " + to);
        }

        return super.export(fromDate, toDate, response);
    }


    @PostMapping(value = "/search", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto search(@RequestBody SearchCriteria<CustomerSearchCriteria> searchCriteria) throws BusinessException {
        return super.search(searchCriteria);
    }

    @Override
    @PostMapping(value = "/upload")
    public ResponseDto upload(@RequestParam("file") MultipartFile uploadedFile) throws BusinessException {
        return super.upload(uploadedFile);
    }

}
