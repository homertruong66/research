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

import com.hoang.app.command.RecipientSearchCommand;
import com.hoang.app.dto.PagedResultDTO;
import com.hoang.module.mail.boundary.MailerFC;
import com.hoang.module.mail.boundary.RecipientFC;
import com.hoang.module.mail.domain.Mailer;
import com.hoang.module.mail.domain.Recipient;
import com.hoang.module.mail.dto.RecipientDTO;

/**
 * 
 * @author Hoang Truong
 * 
 */

@Controller
@SessionAttributes(value="recipientDTO")
public class RecipientController {

    private final Logger logger = Logger.getLogger(RecipientController.class);

    // Requested URIs
    public static final String URI_INDEX    = "/recipient/index.htm";
    public static final String URI_CREATE   = "/recipient/create.htm";
    public static final String URI_UPDATE   = "/recipient/update.htm";
    public static final String URI_DELETE   = "/recipient/delete.htm";
    public static final String URI_SUCCESS  = "/recipient/success.htm";    

    // Returned Views
    public static final String VIEW_INDEX   = "recipient/search";
    public static final String VIEW_CREATE  = "recipient/edit";
    public static final String VIEW_UPDATE  = "recipient/edit";
    public static final String VIEW_SUCCESS = "recipient/success";    

    @Autowired
    private DefaultBeanValidator defaultValidator;

    @Autowired
    private MailerFC mailerFC;

    @Autowired
    private RecipientFC recipientFC;

    @RequestMapping(value=URI_INDEX, method=RequestMethod.GET)
    public ModelAndView search(ModelMap model,
        @RequestParam(value="mId", required=true) Long mailerId)
    {
        logger.info("search<get>(" + mailerId + ")");

        RecipientSearchCommand rsc = new RecipientSearchCommand();
        rsc.setMailerId(mailerId);
        model.put("recipientSearchCommand", rsc);

        return new ModelAndView(VIEW_INDEX, model);
    }

    @RequestMapping(value=URI_INDEX, method=RequestMethod.POST)
    public ModelAndView search(ModelMap model,
        @ModelAttribute(value="recipientSearchCommand") RecipientSearchCommand rsc)
    {
        logger.info("search<post>(" + rsc.getMailerId() + ", '" + rsc.getName() + "')");

        PagedResultDTO<Recipient> pr = recipientFC.searchByMailer(rsc.getPage(), rsc.getMailerId(), rsc.getName());
        model.put("pagedResult", pr);
        model.put("createUri", URI_CREATE);
        model.put("updateUri", URI_UPDATE);
        model.put("deleteUri", URI_DELETE);
        model.put("mailerId", rsc.getMailerId());
        model.put("recipientSearchCommand", rsc);

        Mailer mailer = mailerFC.read(rsc.getMailerId());
        model.put("mailerName", mailer.getName());

        return new ModelAndView(VIEW_INDEX, model);
    }

    @RequestMapping(value=URI_CREATE, method=RequestMethod.GET)
    public ModelAndView create(ModelMap model, 
        @RequestParam("mId") Long mailerId)
    {
        logger.info("create<get>(" + mailerId + ")");
        
        RecipientDTO recipientDTO = new RecipientDTO();
        recipientDTO.setMailerId(mailerId);
        model.put("recipientDTO", recipientDTO);

        return new ModelAndView(VIEW_CREATE, model);
    }

    @RequestMapping(value=URI_CREATE, method=RequestMethod.POST)
    public ModelAndView create(ModelMap model,
        @ModelAttribute("recipientDTO") RecipientDTO recipientDTO,
        Errors errors)
    {
        logger.info("create<post>(" + recipientDTO.getMailerId() + ", '" + recipientDTO.getName() + "')");

        defaultValidator.validate(recipientDTO, errors);
        if (errors.hasErrors()) {            
            return new ModelAndView (VIEW_CREATE, model);
        }        
        mailerFC.addRecipient(recipientDTO);                

        return new ModelAndView(VIEW_SUCCESS, model);
    }

    @RequestMapping(value=URI_UPDATE, method=RequestMethod.GET)
    public ModelAndView update(ModelMap model, 
        @RequestParam("mId") Long mailerId,
        @RequestParam("id") Long id)
    {
        logger.info("update<get>(" + mailerId + ", " + id + ")");
        
        Recipient recipient = recipientFC.read(id);
        if (recipient == null) {
            throw new IllegalArgumentException("Recipient " + id + " not found !");
        }
        RecipientDTO recipientDTO = new RecipientDTO();
        recipientDTO.setMailerId(mailerId);
        recipientDTO.copyFromRecipient(recipient);        
        model.put("recipientDTO", recipientDTO);

        return new ModelAndView(VIEW_UPDATE, model);
    }

    @RequestMapping(value=URI_UPDATE, method=RequestMethod.POST)
    public ModelAndView update(ModelMap model,
        @ModelAttribute("recipientDTO") RecipientDTO recipientDTO,
        Errors errors)
    {
        logger.info("update<post>(" + recipientDTO.getMailerId() + ", " + recipientDTO.getId() + ")");

        defaultValidator.validate(recipientDTO, errors);
        if (errors.hasErrors()) {
            return new ModelAndView(VIEW_UPDATE, model);
        }        
        recipientFC.update(recipientDTO);                

        return new ModelAndView(VIEW_SUCCESS, model);
    }

    @RequestMapping(value=URI_DELETE)
    public ModelAndView delete(ModelMap model,
        @RequestParam("mId") Long mailerId,
        @RequestParam(value="id") Long id   ) 
    {
        logger.info("delete(" + mailerId + ", " + id + ")");
                        
        recipientFC.deleteByMailer(mailerId, id);

        String redirectUrl = "redirect:" + URI_INDEX + "?mId=" + mailerId;
        return new ModelAndView(redirectUrl);
    }
}

