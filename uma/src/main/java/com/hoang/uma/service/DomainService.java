package com.hoang.uma.service;

import com.hoang.uma.common.exception.BusinessException;
import com.hoang.uma.common.dto.DomainDto;

import java.util.List;

/**
 * homertruong
 */

public interface DomainService {

    List<DomainDto> getCountries () throws BusinessException;

}
