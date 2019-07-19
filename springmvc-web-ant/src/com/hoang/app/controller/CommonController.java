package com.hoang.app.controller;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
*
* @author htruong
*/

@Controller
public class CommonController {

    private final Logger logger = Logger.getLogger(CommonController.class);

    @RequestMapping(value="/welcome.htm")
    public ModelAndView welcome(ModelMap model, HttpSession session) {
        logger.info("welcome()");                 

        return new ModelAndView("welcome");
    }    

    @RequestMapping(value="/login.htm")
    public ModelAndView login(ModelMap model) {
        logger.info("login()");

        return new ModelAndView("login");
    }
    
    @RequestMapping(value="/home/homepage.htm")
    public ModelAndView homepage(ModelMap model, HttpSession session) {
        logger.info("homepage()");          
        
        return new ModelAndView("home/homepage");
    }
}
