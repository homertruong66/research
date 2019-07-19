package com.hoang.uma.controller;

import com.hoang.uma.service.StorageService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * homertruong
 */

@Controller
public class MainController {

    private Logger logger = Logger.getLogger(MainController.class);

    @Autowired
    private StorageService storageService;

    @PostMapping("/files")
    public String upload(@RequestParam("file") MultipartFile uploadedFile, RedirectAttributes redirectAttributes) {
        logger.info("processing uploaded file '" + uploadedFile.getOriginalFilename() + "' ...");
        storageService.store(uploadedFile);
        redirectAttributes.addFlashAttribute("message",
                                             "You've successfully uploaded " + uploadedFile.getOriginalFilename() + "!");

        return "redirect:/files";
    }

}
