package com.hoang.app.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springmodules.validation.commons.DefaultBeanValidator;

import com.hoang.app.boundary.RoleFC;
import com.hoang.app.domain.Role;
import com.hoang.app.dto.PagedResultDTO;

/**
 *
 * @author htruong
 */

@Controller
@SessionAttributes("role")
public class RoleController {

    private final Logger logger = Logger.getLogger(RoleController.class);

    // Requested URIs
    public static final String URI_INDEX    = "/role/index.htm";
    public static final String URI_CREATE   = "/role/create.htm";
    public static final String URI_UPDATE   = "/role/update.htm";
    public static final String URI_DELETE   = "/role/delete.htm";
    public static final String URI_SUCCESS  = "/role/success.htm";

    // Returned Views
    public static final String VIEW_INDEX   = "role/list";
    public static final String VIEW_CREATE  = "role/edit";
    public static final String VIEW_UPDATE  = "role/edit";
    public static final String VIEW_SUCCESS = "role/success";
    
    @Autowired
    private RoleFC roleFC;
    
    @Autowired
    private DefaultBeanValidator validator;
    

    @RequestMapping(value=URI_INDEX)
    public ModelAndView index(ModelMap model,
        @RequestParam(value="page", required=false) Integer page)
    {
        logger.info("index()");

        if (page == null) {
            page = new Integer(1);
        }
        PagedResultDTO<Role> pr = roleFC.page(page);
        model.put("pagedResult", pr);
        model.put("indexUri", URI_INDEX);
        model.put("createUri", URI_CREATE);
        model.put("updateUri", URI_UPDATE);
        model.put("deleteUri", URI_DELETE);

        return new ModelAndView(VIEW_INDEX, model);
    }

    @RequestMapping(value=URI_CREATE, method=RequestMethod.GET)
    public ModelAndView create(ModelMap model) {
        logger.info("create<get>()");
        
        Role role = new Role();
        model.addAttribute("role", role);

        return new ModelAndView(VIEW_CREATE,model);
    }

    @RequestMapping(value=URI_CREATE, method=RequestMethod.POST)
    public ModelAndView create(ModelMap model,
        @ModelAttribute("role") Role role,
        Errors errors)
    {
        logger.info("create<post>('" + role.getName() + "')");

        validator.validate(role, errors);
        if (errors.hasErrors()) {            
            return new ModelAndView (VIEW_CREATE, model);
        }        
        roleFC.create(role);

        return new ModelAndView(VIEW_SUCCESS);
    }

    @RequestMapping(value=URI_UPDATE, method=RequestMethod.GET)
    public ModelAndView update(ModelMap model, 
    	@RequestParam("id") Long id) 
    {
        logger.info("update<get>(" + id + ")");
        
        Role role = roleFC.read(id);
        if (role == null) {
            throw new IllegalArgumentException("Role " + id + " not found !");
        }
        model.addAttribute("role", role);

        return new ModelAndView(VIEW_UPDATE, model);
    }

    @RequestMapping(value=URI_UPDATE, method=RequestMethod.POST)
    public ModelAndView update(ModelMap model,
        @ModelAttribute("role") Role role,
        Errors errors)
    {
        logger.info("update<post>(" + role.getId() + ")");

        validator.validate(role, errors);
        if (errors.hasErrors()) {
            return new ModelAndView(VIEW_UPDATE, model);
        }        
        roleFC.update(role);

        return new ModelAndView(VIEW_SUCCESS);
    }

    @RequestMapping(value=URI_DELETE)
    public ModelAndView delete(ModelMap model,
        @RequestParam(value="id") Long id   ) throws Exception
    {
        logger.info("delete(" + id + ")");
        
        roleFC.delete(id);

        return new ModelAndView("redirect:" + URI_INDEX);
    }   
}

