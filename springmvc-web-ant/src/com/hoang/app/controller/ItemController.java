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

import com.hoang.app.command.ItemSearchCommand;
import com.hoang.app.dto.PagedResultDTO;
import com.hoang.module.mail.boundary.ItemFC;
import com.hoang.module.mail.boundary.MailerFC;
import com.hoang.module.mail.domain.Item;
import com.hoang.module.mail.domain.Mailer;
import com.hoang.module.mail.dto.ItemDTO;

/**
 * 
 * @author Hoang Truong
 *
 */

@Controller
@SessionAttributes(value="itemDTO")
public class ItemController {

    private final Logger logger = Logger.getLogger(ItemController.class);

    // Requested URIs
    public static final String URI_INDEX    = "/item/index.htm";    
    public static final String URI_CREATE   = "/item/create.htm";
    public static final String URI_UPDATE   = "/item/update.htm";
    public static final String URI_DELETE   = "/item/delete.htm";
    public static final String URI_SUCCESS  = "/item/success.htm";

    // Returned Views
    public static final String VIEW_INDEX   = "item/search";
    public static final String VIEW_CREATE  = "item/edit";
    public static final String VIEW_UPDATE  = "item/edit";
    public static final String VIEW_SUCCESS = "item/success";

    @Autowired
    private MailerFC mailerFC;

    @Autowired
    private ItemFC itemFC;
    
    @Autowired
    private DefaultBeanValidator defaultValidator;

    @RequestMapping(value=URI_INDEX, method=RequestMethod.GET)
    public ModelAndView search(ModelMap model,
        @RequestParam(value="mId", required=true) Long mailerId)
    {
        logger.info("search<get>(" + mailerId + ")");

        ItemSearchCommand isc = new ItemSearchCommand();
        isc.setMailerId(mailerId);
        model.put("itemSearchCommand", isc);

        return new ModelAndView(VIEW_INDEX, model);
    }

    @RequestMapping(value=URI_INDEX, method=RequestMethod.POST)
    public ModelAndView search(ModelMap model,
        @ModelAttribute(value="itemSearchCommand") ItemSearchCommand isc)
    {
        logger.info("search<post>(" + isc.getMailerId() + ", '" + isc.getName() + "')");

        PagedResultDTO<Item> pr = itemFC.searchByMailer(isc.getPage(), isc.getMailerId(), isc.getName());
        model.put("pagedResult", pr);
        model.put("createUri", URI_CREATE);
        model.put("updateUri", URI_UPDATE);
        model.put("deleteUri", URI_DELETE);
        model.put("mailerId", isc.getMailerId());
        model.put("itemSearchCommand", isc);

        // get extra data
        Mailer mailer = mailerFC.read(isc.getMailerId());
        model.put("mailerName", mailer.getName());        

        return new ModelAndView(VIEW_INDEX, model);
    }

    @RequestMapping(value=URI_UPDATE, method=RequestMethod.GET)
    public ModelAndView update(ModelMap model,
        @RequestParam("mId") Long mailerId,
        @RequestParam("id") Long id)
    {
        logger.info("update<get>(" + mailerId + ", " + id + ")");
        
        Item item = itemFC.read(id);
        if (item == null) {
            throw new IllegalArgumentException("Item " + id + " not found !");
        }
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setMailerId(mailerId);
        itemDTO.copyFromItem(item);        
        model.addAttribute("itemDTO", itemDTO);        

        return new ModelAndView(VIEW_UPDATE, model);
    }   
    
    @RequestMapping(value=URI_UPDATE, method=RequestMethod.POST)
    public ModelAndView update(ModelMap model,
        @ModelAttribute("itemDTO") ItemDTO itemDTO,
        Errors errors)
    {
        logger.info("update<post>(" + itemDTO.getMailerId() + ", " + itemDTO.getId() + ")");

        defaultValidator.validate(itemDTO, errors);
        if (errors.hasErrors()) {
            return new ModelAndView(VIEW_UPDATE, model);
        }        
        itemFC.update(itemDTO);                

        return new ModelAndView(VIEW_SUCCESS, model);
    }
    
    @RequestMapping(value=URI_DELETE)
    public ModelAndView delete(ModelMap model,
        @RequestParam("mId") Long mailerId,
        @RequestParam(value="id") Long id   ) 
    {
        logger.info("delete(" + mailerId + ", " + id + ")");
                        
        itemFC.deleteByMailer(mailerId, id);

        String redirectUrl = "redirect:" + URI_INDEX + "?mId=" + mailerId;
        return new ModelAndView(redirectUrl);
    }
}
