package com.hoang.uma.service;

import com.hoang.uma.common.config.properties.StorageProperties;
import com.hoang.uma.controller.MainController;
import com.hoang.uma.common.exception.StorageException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * homertruong
 */

@Service
public class StorageServiceImpl implements StorageService {

    private Logger logger = Logger.getLogger(MainController.class);

    @Autowired
    private StorageProperties storageProperties;

    @Override
    public void store(MultipartFile uploadedFile) throws StorageException {
//        logger.info("storing uploaded file '" + uploadedFile.getOriginalFilename() + "' at location = '"
//                    + storageProperties.getLocation() + "' ...");

    }

}
