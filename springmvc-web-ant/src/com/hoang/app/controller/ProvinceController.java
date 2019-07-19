package com.hoang.app.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hoang.app.boundary.ProvinceFC;
import com.hoang.app.domain.Province;

/**
*
* @author htruong
*/

@Controller
public class ProvinceController {

    private final Logger logger = Logger.getLogger(ProvinceController.class);
    
    @Autowired
    private ProvinceFC provinceFC;

    @RequestMapping(value="**/province.htm")
    public void getProvinces(ModelMap model, HttpServletResponse response, 
    	@RequestParam("countryId") Long countryId) throws Exception
    {
        logger.info("getProvinces(" + countryId + ")");
        try {
        List<Province> provinces = provinceFC.getByCountry(countryId);
        for (Province province : provinces) {
        	String htmlOption = "<option value=\"" + province.getId() + "\">" + province.getName() + "</option>";
			response.getWriter().write(htmlOption);
		}
        }
        catch (Exception ex) {
        	throw ex;
        }
    } 
}
