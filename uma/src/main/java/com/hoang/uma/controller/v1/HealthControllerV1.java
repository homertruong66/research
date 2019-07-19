package com.hoang.uma.controller.v1;

import com.hoang.uma.common.dto.HealthDto;
import com.hoang.uma.controller.HealthController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * homertruong
 */

@RestController
public class HealthControllerV1 extends HealthController {

    @RequestMapping("/v1/health")
    public HealthDto checkHealth () {
        return super.checkHealth();
    }
}
