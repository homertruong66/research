package com.rms.rms.controller.v1;

import com.rms.rms.common.SearchCriteria;
import com.rms.rms.common.dto.ResponseDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.exception.InvalidViewModelException;
import com.rms.rms.common.view_model.OrderCreateModel;
import com.rms.rms.common.view_model.OrderRejectModel;
import com.rms.rms.common.view_model.OrderSearchCriteria;
import com.rms.rms.common.view_model.OrderUpdateModel;
import com.rms.rms.controller.OrderController;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * homertruong
 */

@RestController
@RequestMapping(value = "/v1/orders")
public class OrderControllerV1 extends OrderController {

    @PostMapping(value = "/{id}/approve", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto approve(@PathVariable String id) throws BusinessException {
        return super.approve(id);
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto create(@RequestBody OrderCreateModel createModel) throws BusinessException {
        return super.create(createModel);
    }

    @GetMapping(value = "/export")
    public ResponseEntity<byte[]> export(@RequestParam("from") String from, @RequestParam("to") String to, HttpServletResponse response)
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
        return super.export(fromDate, toDate, response);
    }

    @GetMapping(value = "/{id}")
    public ResponseDto get(@PathVariable String id) throws BusinessException {
        return super.get(id);
    }
    
    @PostMapping(value = "/{id}/reject", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto reject(@PathVariable String id, @RequestBody OrderRejectModel rejectModel) throws BusinessException {
        return super.reject(id, rejectModel.getReason());
    }

    @GetMapping(value = "/{id}/resend_getfly")
    public ResponseDto resendGetfly(@PathVariable String id) throws BusinessException, IOException {
        return super.resendGetfly(id);
    }

    @GetMapping(value = "/{id}/resend_infusion")
    public ResponseDto resendInfusion(@PathVariable String id) throws BusinessException, IOException {
        return super.resendInfusion(id);
    }

    @PostMapping(value = "/search", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto search(@RequestBody SearchCriteria<OrderSearchCriteria> vmSearchCriteria) throws BusinessException {
        return super.search(vmSearchCriteria);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto update(@PathVariable String id,
                              @RequestBody @Valid OrderUpdateModel updateModel) throws BusinessException {
        return super.update(id, updateModel);
    }

    @Override
    @PostMapping(value = "/{id}/view")
    public ResponseDto view(@PathVariable String id) throws BusinessException {
        return super.view(id);
    }
}
