package com.rms.rms.controller.v1;

import com.rms.rms.common.SearchCriteria;
import com.rms.rms.common.dto.ResponseDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.view_model.NotificationSearchCriteria;
import com.rms.rms.controller.NotificationController;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/notifications")
public class NotificationControllerV1 extends NotificationController {

    @Override
    @DeleteMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void delete (@PathVariable String id) throws BusinessException {
        super.delete(id);
    }

    @Override
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto markAsRead (@PathVariable String id) throws BusinessException {
        return super.markAsRead(id);
    }

    @Override
    @PostMapping(value = "/search", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto search (@RequestBody SearchCriteria<NotificationSearchCriteria> searchCriteria) throws BusinessException {
        return super.search(searchCriteria);
    }
}
