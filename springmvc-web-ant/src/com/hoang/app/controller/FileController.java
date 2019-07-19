package com.hoang.app.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

/**
*
* @author htruong
*/

@Controller
public class FileController {

    private final Logger logger = Logger.getLogger(FileController.class);
    
    // Requested URIs
    public static final String URI_UPLOAD   	 = "/file/upload.htm";    
    public static final String URI_DOWNLOAD		 = "/file/download.htm";
    public static final String URI_DOWNLOAD_FILE = "/file/download-file.htm";  

    // Returned Views
    public static final String VIEW_UPLOAD   = "file/upload";
    public static final String VIEW_DOWNLOAD = "file/download";
    
    @RequestMapping(value=URI_UPLOAD, method=RequestMethod.GET)
    public ModelAndView upload() throws Exception
    {
        logger.info("upload<get>()");       
        
        return new ModelAndView(VIEW_UPLOAD);
    }
    
    @RequestMapping(value=URI_UPLOAD, method=RequestMethod.POST)
    public ModelAndView upload(ModelMap model, HttpServletRequest request, HttpSession session,
    	@RequestParam("name") String name,
    	@RequestParam("file") MultipartFile file) throws Exception
    {
        logger.info("upload('" + name + "')");
        
        try {
        	// Create upload folder if not exist
        	String realPath = session.getServletContext().getRealPath("/");
        	if (realPath != null) {
	        	File uploadFolder = new File(realPath + "WEB-INF/upload");
	        	if (!uploadFolder.exists()) {
	        		uploadFolder.mkdir();
	        	}
	        	File f = new File(uploadFolder.getPath() + "/" + name);
	        	f.createNewFile();
				FileOutputStream fout = new FileOutputStream(f);
				fout.write(file.getBytes());
				fout.flush();
				fout.close();
        	}
        	else {
        		throw new Exception("Can not get real path");
        	}
		} 
        catch (Exception e) {			
        	logger.error("Error saving uploaded file: " + e.getMessage());
        	throw e;
		}
        model.put("done", "File '" + name + "' has been uploaded !");
        
        return new ModelAndView(VIEW_UPLOAD, model);
    }
    
    @RequestMapping(value=URI_DOWNLOAD, method=RequestMethod.GET)
    public ModelAndView download(ModelMap model, HttpSession session) throws Exception
    {
        logger.info("download<get>()");
             	
    	String realPath = session.getServletContext().getRealPath("/");
    	if (realPath != null) {
    		String uploadPath = realPath + "WEB-INF/upload";
        	File uploadFolder = new File(uploadPath);
        	String [] files = uploadFolder.list();
        	model.put("files", files);
    	}
    	else {
    		throw new Exception("Can not get real path");
    	}        
        
        return new ModelAndView(VIEW_DOWNLOAD, model);
    }
    
    @RequestMapping(value=URI_DOWNLOAD_FILE, method=RequestMethod.GET)
    public void downloadFile(HttpSession session, HttpServletResponse response,
    	@RequestParam("name") String name) throws Exception
    {
        logger.info("downloadFile('" + name + "')");
        
        String realPath = session.getServletContext().getRealPath("/");
        if (realPath != null) {
        	 File file = new File(realPath + "/WEB-INF/upload/" + name);      
//             response.setContentType();
//             response.setContentLength();
             response.setHeader("Content-Disposition","attachment; filename=\"" + name +"\"");
             FileInputStream fis = new FileInputStream(file); 
             FileCopyUtils.copy(fis, response.getOutputStream());
    	}
    	else {
    		throw new Exception("Can not get real path");
    	}   
    }
}
