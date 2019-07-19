package com.hoang.uma.service;

import com.hoang.uma.common.exception.StorageException;
import org.springframework.web.multipart.MultipartFile;

/**
 * homertruong
 */

public interface StorageService {

    void store(MultipartFile file) throws StorageException ;

}