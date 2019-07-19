package com.hoang.app.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springmodules.validation.commons.DefaultBeanValidator;

import com.hoang.module.mail.boundary.MailFC;
import com.hoang.module.mail.domain.Mail;

/**
 * 
 * @author Hoang Truong
 *
 */

@Controller
@SessionAttributes("mail")
public class MailController {

    private final Logger logger = Logger.getLogger(MailController.class);

    // Requested URIs
    public static final String URI_UPDATE          = "/mail/update.htm";

    // Returned Views
    public static final String VIEW_EDIT           = "mail/edit";
    public static final String VIEW_SUCCESS        = "mail/success";

    @Autowired
    private DefaultBeanValidator defaultValidator;

    @Autowired
    private MailFC mailFC;

    @RequestMapping(value=URI_UPDATE, method=RequestMethod.GET)
    public ModelAndView update(ModelMap model) {

        logger.info("update<get>()");
                
        Mail mail = mailFC.read(1l);
        if (mail != null) {
            mail.setHost(mail.getHost());
            mail.setUsername(mail.getUsername());
            mail.setPassword(mail.getPassword());
            mail.setSenderHost(mail.getSenderHost());
            mail.setSenderAddress(mail.getSenderAddress());
            mail.setSenderName(mail.getSenderName());
            mail.setSubject(mail.getSubject());
            mail.setTimeInterval(mail.getTimeInterval());
        }
        model.addAttribute("mail", mail);

        return new ModelAndView(VIEW_EDIT, model);
    }

    @RequestMapping(value=URI_UPDATE, method=RequestMethod.POST)
    public ModelAndView update(ModelMap model,
        @ModelAttribute("mail") Mail mail,
        Errors errors)
    {
        logger.info("update<post>()");

        defaultValidator.validate(mail, errors);
        if (errors.hasErrors()) {
            return new ModelAndView (VIEW_EDIT, model);
        }        
        mailFC.update(mail);

        return new ModelAndView(VIEW_SUCCESS);
    }
}


