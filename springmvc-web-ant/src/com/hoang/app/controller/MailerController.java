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

import com.hoang.app.command.MailerSearchCommand;
import com.hoang.app.dto.PagedResultDTO;
import com.hoang.module.mail.boundary.MailerFC;
import com.hoang.module.mail.domain.Mailer;

/**
 * 
 * @author Hoang Truong
 *
 */

@Controller
@SessionAttributes(value="mailer")
public class MailerController {

    private final Logger logger = Logger.getLogger(MailerController.class);

    // Requested URIs
    public static final String URI_INDEX    = "/mailer/index.htm";
    public static final String URI_CREATE   = "/mailer/create.htm";
    public static final String URI_UPDATE   = "/mailer/update.htm";
    public static final String URI_DELETE   = "/mailer/delete.htm";
    public static final String URI_SUCCESS  = "/mailer/success.htm";    

    // Returned Views
    public static final String VIEW_INDEX   = "mailer/search";
    public static final String VIEW_CREATE  = "mailer/edit";
    public static final String VIEW_UPDATE  = "mailer/edit";
    public static final String VIEW_SUCCESS = "mailer/success";    

    @Autowired
    private DefaultBeanValidator defaultValidator;

    @Autowired
    private MailerFC mailerFC;

    @RequestMapping(value=URI_INDEX, method=RequestMethod.GET)
    public ModelAndView search(ModelMap model)    {
        logger.info("search<get>()");

        MailerSearchCommand msc = new MailerSearchCommand();
        model.put("mailerSearchCommand", msc);

        return new ModelAndView(VIEW_INDEX, model);
    }

    @RequestMapping(value=URI_INDEX, method=RequestMethod.POST)
    public ModelAndView search(ModelMap model,
        @ModelAttribute("mailerSearchCommand") MailerSearchCommand msc)
    {
        logger.info("search<post>('" + msc.getName() + "')");

        PagedResultDTO<Mailer> pr = mailerFC.search(msc.getPage(), msc.getName());
        model.put("pagedResult", pr);
        model.put("createUri", URI_CREATE);
        model.put("updateUri", URI_UPDATE);
        model.put("deleteUri", URI_DELETE);
        model.put("mailerSearchCommand", msc);

        return new ModelAndView(VIEW_INDEX, model);
    }

    @RequestMapping(value=URI_CREATE, method=RequestMethod.GET)
    public ModelAndView create(ModelMap model) {
        logger.info("create<get>()");

        Mailer mailer = new Mailer();
        model.addAttribute("mailer", mailer);

        return new ModelAndView(VIEW_CREATE,model);
    }

    @RequestMapping(value=URI_CREATE, method=RequestMethod.POST)
    public ModelAndView create(ModelMap model,
        @ModelAttribute("mailer") Mailer mailer,
        Errors errors)
    {
        logger.info("create<post>('" + mailer.getName() + "')");

        defaultValidator.validate(mailer, errors);
        if (errors.hasErrors()) {            
            return new ModelAndView (VIEW_CREATE, model);
        }        
        mailerFC.create(mailer);

        return new ModelAndView(VIEW_SUCCESS);
    }

    @RequestMapping(value=URI_UPDATE, method=RequestMethod.GET)
    public ModelAndView update(ModelMap model, 
        @RequestParam("id") Long id)
    {
        logger.info("update<get>(" + id + ")");

        Mailer mailer = mailerFC.read(id);
        if (mailer == null) {
            throw new IllegalArgumentException("Mailer " + id + " not found.");
        }
        model.addAttribute("mailer", mailer);        

        return new ModelAndView(VIEW_UPDATE, model);
    }

    @RequestMapping(value=URI_UPDATE, method=RequestMethod.POST)
    public ModelAndView update(ModelMap model,
        @ModelAttribute("mailer") Mailer mailer,
        Errors errors)
    {
        logger.info("update<post>(" + mailer.getId() + ")");

        defaultValidator.validate(mailer, errors);
        if (errors.hasErrors()) {
            return new ModelAndView(VIEW_UPDATE, model);
        }
        mailerFC.update(mailer);

        return new ModelAndView(VIEW_SUCCESS);
    }

    @RequestMapping(value=URI_DELETE)
    public ModelAndView delete(ModelMap model,
        @RequestParam(value="id") Long id   ) 
    {
        logger.info("delete(" + id + ")");
        
        mailerFC.delete(id);

        return new ModelAndView("redirect:" + URI_INDEX);
    }
}
