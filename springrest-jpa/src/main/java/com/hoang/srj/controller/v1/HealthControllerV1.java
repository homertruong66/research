package com.hoang.srj.controller.v1;

import com.hoang.srj.controller.HealthController;
import com.hoang.srj.dto.HealthDto;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthControllerV1 extends HealthController {

    @RequestMapping("/v1/health")
    public HealthDto checkHealth () {
        return super.checkHealth();
    }
}
