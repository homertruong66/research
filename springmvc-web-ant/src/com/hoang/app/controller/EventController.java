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

import com.hoang.app.command.EventSearchCommand;
import com.hoang.app.dto.PagedResultDTO;
import com.hoang.module.es.boundary.EventFC;
import com.hoang.module.es.domain.Event;

/**
 * 
 * @author Hoang Truong
 *
 */

@Controller
@SessionAttributes(value="event")
public class EventController {

    private final Logger logger = Logger.getLogger(EventController.class);

    // Requested URIs
    public static final String URI_INDEX    = "/event/index.htm";
    public static final String URI_CREATE   = "/event/create.htm";
    public static final String URI_ACCEPT   = "/vote/accept.htm";
    public static final String URI_VOTE     = "/vote/index.htm";
    public static final String URI_REMIND   = "/event/remind.htm";
    public static final String URI_MAIL     = "/event/mail.htm";
    public static final String URI_DELETE   = "/event/delete.htm";
    public static final String URI_SUCCESS  = "/event/success.htm";

    // Returned Views
    public static final String VIEW_INDEX   = "event/search";
    public static final String VIEW_CREATE  = "event/edit";
    public static final String VIEW_UPDATE  = "event/edit";
    public static final String VIEW_SUCCESS = "event/success";
    public static final String VIEW_INFO    = "event/info";

    @Autowired
    private DefaultBeanValidator defaultValidator;

    @Autowired
    private EventFC eventFC;

    @RequestMapping(value=URI_INDEX, method=RequestMethod.GET)
    public ModelAndView search(ModelMap model) {
        logger.info("search<get>()");

        EventSearchCommand esc = new EventSearchCommand();
        model.put("eventSearchCommand", esc);

        return new ModelAndView(VIEW_INDEX, model);
    }

    @RequestMapping(value=URI_INDEX, method=RequestMethod.POST)
    public ModelAndView search(ModelMap model,
        @ModelAttribute("eventSearchCommand") EventSearchCommand esc )
    {
        logger.info("search<post>('" + esc.getName() + "')");

        PagedResultDTO<Event> pr = eventFC.search(esc.getPage(), esc.getName());
        model.put("pagedResult", pr);
        model.put("createUri", URI_CREATE);
        model.put("acceptUri", URI_ACCEPT);
        model.put("voteUri", URI_VOTE);
        model.put("remindUri", URI_REMIND);
        model.put("mailUri", URI_MAIL);
        model.put("deleteUri", URI_DELETE);
        model.put("eventSearchCommand", esc);

        return new ModelAndView(VIEW_INDEX, model);
    }

    @RequestMapping(value=URI_CREATE, method=RequestMethod.GET)
    public ModelAndView create(ModelMap model) {
        logger.info("create<get>()");

        Event event = eventFC.generateEvent();
        model.addAttribute("event", event);

        return new ModelAndView(VIEW_CREATE,model);
    }

    @RequestMapping(value=URI_CREATE, method=RequestMethod.POST)
    public ModelAndView create(ModelMap model,
        @ModelAttribute("event") Event event,
        Errors errors)
    {
        logger.info("create<post>('" + event.getName() + "')");

        defaultValidator.validate(event, errors);
        if (errors.hasErrors()) {
            return new ModelAndView (VIEW_CREATE, model);
        }
        eventFC.create(event);

        return new ModelAndView(VIEW_SUCCESS);       
    }

    @RequestMapping(value=URI_REMIND)
    public ModelAndView remind(ModelMap model,
        @RequestParam(value="id") Long id   ) 
    {
        logger.info("remind(" + id + ")");
        
        try {
        	eventFC.remind(id);            
            model.put("remindSent", "successful");
        }
        catch (Exception ex) {
            model.put("remindSent", "failed");
            model.put("error", ex.getMessage());
        }

        return new ModelAndView(VIEW_INFO, model);
    }

    @RequestMapping(value=URI_MAIL)
    public ModelAndView mail(ModelMap model,
        @RequestParam(value="id") Long id   ) 
    {
        logger.info("mail(" + id + ")");
                
        try {
        	eventFC.mail(id);            
            model.put("resultSent", "successful");
        }
        catch (Exception ex) {
            model.put("resultSent", "failed");
            model.put("error", ex.getMessage());
        }

        return new ModelAndView(VIEW_INFO, model);
    }

    @RequestMapping(value=URI_DELETE)
    public ModelAndView delete(ModelMap model,
        @RequestParam(value="id") Long id   ) 
    {
        logger.info("delete(" + id + ")");
        
        eventFC.delete(id);

        return new ModelAndView("redirect:" + URI_INDEX);
    }    
}
