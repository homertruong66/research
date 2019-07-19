package com.hoang.app.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springmodules.validation.commons.DefaultBeanValidator;

import com.hoang.app.boundary.OrganizationFC;
import com.hoang.app.boundary.PersonFC;
import com.hoang.app.boundary.RoleFC;
import com.hoang.app.boundary.UserFC;
import com.hoang.app.command.UserSearchCommand;
import com.hoang.app.domain.Organization;
import com.hoang.app.domain.Person;
import com.hoang.app.domain.Role;
import com.hoang.app.domain.User;
import com.hoang.app.dto.PagedResultDTO;
import com.hoang.app.dto.UserDTO;
import com.hoang.app.editor.OrganizationEditor;
import com.hoang.app.editor.PersonEditor;
import com.hoang.app.validator.UserValidator;

/**
 * 
 * @author Hoang Truong
 *
 */

@Controller
@SessionAttributes("userDTO")
public class UserController {

    private final Logger logger = Logger.getLogger(UserController.class);

    // Requested URIs
    public static final String URI_INDEX            = "/user/index.htm";    
    public static final String URI_CREATE           = "/user/create.htm";
    public static final String URI_UPDATE           = "/user/update.htm";
    public static final String URI_CHANGE_PASSWORD  = "/user/change-password.htm";
    public static final String URI_ENABLE           = "/user/enable.htm";
    public static final String URI_DISABLE          = "/user/disable.htm";
    public static final String URI_DELETE           = "/user/delete.htm";    

    // Returned Views
    public static final String VIEW_INDEX           = "user/search";
    public static final String VIEW_CREATE          = "user/edit";
    public static final String VIEW_UPDATE          = "user/edit";
    public static final String VIEW_CHANGE_PASSWORD = "user/change-password";
    public static final String VIEW_SUCCESS         = "user/success";    

    private DefaultBeanValidator defaultValidator;

    @Autowired
    private UserFC userFC;

    @Autowired
    private RoleFC roleFC;

    @Autowired
    private PersonFC personFC;

    @Autowired
    private OrganizationFC organizationFC;    

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Person.class, 		new PersonEditor(personFC));
        binder.registerCustomEditor(Organization.class, new OrganizationEditor(organizationFC));
    }

    @RequestMapping(value=URI_INDEX, method=RequestMethod.GET)
    public ModelAndView search(ModelMap model)    {
        logger.info("search<get>()");        

        UserSearchCommand usc = new UserSearchCommand();
        model.put("userSearchCommand", usc);

        return new ModelAndView(VIEW_INDEX, model);
    }

    @RequestMapping(value=URI_INDEX, method=RequestMethod.POST)
    public ModelAndView search(ModelMap model,
        @ModelAttribute("userSearchCommand") UserSearchCommand usc)
    {
        logger.info("search<post>('" + usc.getUsername() + "')");

        PagedResultDTO<User> pr = userFC.search(usc.getPage(), usc.getUsername());
        model.put("pagedResult", pr);
        model.put("createUri", URI_CREATE);
        model.put("enableUri", URI_ENABLE);
        model.put("disableUri", URI_DISABLE);
        model.put("updateUri", URI_UPDATE);
        model.put("deleteUri", URI_DELETE);
        model.put("userSearchCommand", usc);

        return new ModelAndView(VIEW_INDEX, model);
    }


    @RequestMapping(value=URI_CREATE, method=RequestMethod.GET)
    public ModelAndView create(ModelMap model) {
        logger.info("create<get>()");

        UserDTO userDTO = new UserDTO();
        userDTO.setPerson(new Person());
        userDTO.setOrganization(new Organization());
        model.put("userDTO", userDTO);
        model.put("formAction", URI_CREATE);

        prepareEditData(model);

        return new ModelAndView(VIEW_CREATE, model);
    }

    @RequestMapping(value=URI_CREATE, method=RequestMethod.POST)
    public ModelAndView create(ModelMap model,
        @ModelAttribute("userDTO") UserDTO userDTO,
        Errors errors)
    {
        logger.info("create<post>('" + userDTO.getUsername() + "')");
        
        defaultValidator.validate(userDTO, errors);
        new UserValidator(userFC).validate(userDTO, errors);
        if (errors.hasErrors()) {
            model.put("formAction", URI_CREATE);
            prepareEditData(model);
            return new ModelAndView (VIEW_CREATE, model);
        }        
        userFC.create(userDTO);

        return new ModelAndView(VIEW_SUCCESS);
    }

    @RequestMapping(value=URI_UPDATE, method=RequestMethod.GET)
    public ModelAndView update(ModelMap model,
        @RequestParam("username") String username)
    {
        logger.info("update<get>('" + username + "')");

        User user = userFC.get(username);
        if (user == null) {
            throw new IllegalArgumentException("User '" + username + "' not found !");
        }
        UserDTO userDTO = new UserDTO();
        userDTO.copyFromUser(user);
        model.put("userDTO", userDTO);
        model.put("formAction", URI_UPDATE);

        prepareEditData(model);

        return new ModelAndView(VIEW_UPDATE, model);
    }  

    @RequestMapping(value=URI_UPDATE, method=RequestMethod.POST)
    public ModelAndView update(ModelMap model,
        @ModelAttribute("userDTO") UserDTO userDTO,
        Errors errors)
    {
        logger.info("update<post>('" + userDTO.getUsername() + "')");

        defaultValidator.validate(userDTO, errors);
        if (errors.hasErrors()) {
            model.put("formAction", URI_UPDATE);
            prepareEditData(model);
            return new ModelAndView(VIEW_UPDATE, model);
        }       
        userFC.update(userDTO);

        return new ModelAndView(VIEW_SUCCESS);
    }

    @RequestMapping(value=URI_CHANGE_PASSWORD, method=RequestMethod.GET)
    public ModelAndView changePassword(ModelMap model,
        @RequestParam("username") String username)
    {
        logger.info("changePassword<get>('" + username + "')");

        User user = userFC.get(username);
        if (user == null) {
            throw new IllegalArgumentException("User '" + username + "' not found !");
        }
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(username);
        userDTO.setEmail(user.getEmail());
        model.put("userDTO", userDTO);

        return new ModelAndView(VIEW_CHANGE_PASSWORD, model);
    }

    @RequestMapping(value=URI_CHANGE_PASSWORD, method=RequestMethod.POST)
    public ModelAndView changePassword(ModelMap model,
        @ModelAttribute("userDTO") UserDTO userDTO,
        Errors errors)
    {
        logger.info("changePassword<post>('" + userDTO.getUsername() + "')");
        
        defaultValidator.validate(userDTO, errors);
        new UserValidator(userFC).validate(userDTO, errors);
        if (errors.hasErrors()) {            
            return new ModelAndView(VIEW_CHANGE_PASSWORD, model);
        }        
        userFC.changePassword(userDTO);

        return new ModelAndView(VIEW_SUCCESS);
    }

    @RequestMapping(value=URI_ENABLE)
    public ModelAndView enable(ModelMap model,
        @RequestParam(value="username") String username)
    {
        logger.info("enable('" + username + "')");

        User user = userFC.get(username);
        if (user == null) {
            throw new IllegalArgumentException("User '" + username + "' not found !");
        }        
        userFC.enable(username);

        return new ModelAndView("redirect:" + URI_INDEX);
    }

    @RequestMapping(value=URI_DISABLE)
    public ModelAndView disable(ModelMap model,
        @RequestParam(value="username") String username)
    {
        logger.info("disable('" + username + "')");

        User user = userFC.get(username);
        if (user == null) {
            throw new IllegalArgumentException("User '" + username + "' not found !");
        }           
        userFC.disable(username);

        return new ModelAndView("redirect:" + URI_INDEX);
    }

    @RequestMapping(value=URI_DELETE)
    public ModelAndView delete(ModelMap model,
        @RequestParam(value="id") Long id)
    {
        logger.info("delete(" + id + ")");
        
        userFC.delete(id);

        return new ModelAndView("redirect:" + URI_INDEX);
    }

    // Utility methods
    private void prepareEditData(ModelMap model) {
        List<Person> persons = personFC.getAll();
        model.put("persons", persons);

        List<Organization> organizations = organizationFC.getAll();
        model.put("organizations", organizations);        
        model.put("changePasswordUri", URI_CHANGE_PASSWORD);

        List<Role> roles = roleFC.getAll();
        List<String> strRoles = new ArrayList<String>();
        for (Role role : roles) {
            strRoles.add(role.getName());
        }
        model.put("allRoles", strRoles);
    }
}

