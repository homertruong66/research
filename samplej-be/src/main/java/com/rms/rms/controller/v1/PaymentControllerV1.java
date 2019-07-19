package com.rms.rms.controller.v1;

import com.rms.rms.common.SearchCriteria;
import com.rms.rms.common.dto.ResponseDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.exception.InvalidViewModelException;
import com.rms.rms.common.view_model.PaymentCreateModel;
import com.rms.rms.common.view_model.PaymentRejectModel;
import com.rms.rms.common.view_model.PaymentSearchCriteria;
import com.rms.rms.common.view_model.PaymentUpdateModel;
import com.rms.rms.controller.PaymentController;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * homertruong
 */

@RestController
@RequestMapping(value = "/v1/payments")
public class PaymentControllerV1 extends PaymentController {

    @PostMapping(value = "/{id}/approve", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto approve (@PathVariable String id) throws BusinessException {
        return super.approve(id);
    }

    @Override
    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto create (@RequestBody PaymentCreateModel createModel) throws BusinessException {
        return super.create(createModel);
    }

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

    @Override
    @GetMapping(value = "/{id}")
    public ResponseDto get (@PathVariable @NotNull String id) throws BusinessException {
        return super.get(id);
    }

    @PostMapping(value = "/{id}/reject", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto reject (@PathVariable @NotNull String id, @RequestBody PaymentRejectModel rejectModel) throws BusinessException {
        return super.reject(id, rejectModel.getReason());
    }

    @PostMapping(value = "/search", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto search (@RequestBody SearchCriteria<PaymentSearchCriteria> searchCriteria) throws BusinessException {
        return super.search(searchCriteria);
    }

    @Override
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto update (@PathVariable String id, @RequestBody PaymentUpdateModel updateModel) throws BusinessException {
        return super.update(id, updateModel);
    }

}
