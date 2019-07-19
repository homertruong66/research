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

import com.hoang.app.command.PlaceSearchCommand;
import com.hoang.app.dto.PagedResultDTO;
import com.hoang.module.es.boundary.PlaceFC;
import com.hoang.module.es.domain.Place;

/**
 * 
 * @author Hoang Truong
 *
 */

@Controller
@SessionAttributes(value="place")
public class PlaceController {

    private final Logger logger = Logger.getLogger(PlaceController.class);

    // Requested URIs
    public static final String URI_INDEX    = "/place/index.htm";
    public static final String URI_CREATE   = "/place/create.htm";
    public static final String URI_UPDATE   = "/place/update.htm";
    public static final String URI_DELETE   = "/place/delete.htm";
    public static final String URI_SUCCESS  = "/place/success.htm";

    // Returned Views
    public static final String VIEW_INDEX   = "place/search";
    public static final String VIEW_CREATE  = "place/edit";
    public static final String VIEW_UPDATE  = "place/edit";
    public static final String VIEW_SUCCESS = "place/success";

    @Autowired
    private DefaultBeanValidator defaultValidator;

    @Autowired
    private PlaceFC placeFC;

    @RequestMapping(value=URI_INDEX, method=RequestMethod.GET)
    public ModelAndView search(ModelMap model) {
        logger.info("search<get>()");

        PlaceSearchCommand psc = new PlaceSearchCommand();
        model.put("placeSearchCommand", psc);

        return new ModelAndView(VIEW_INDEX, model);
    }

    @RequestMapping(value=URI_INDEX, method=RequestMethod.POST)
    public ModelAndView search(ModelMap model,
        @ModelAttribute("placeSearchCommand") PlaceSearchCommand psc )
    {
        logger.info("search<post>('" + psc.getName() + "')");

        PagedResultDTO<Place> pr = placeFC.search(psc.getPage(), psc.getName());
        model.put("pagedResult", pr);
        model.put("createUri", URI_CREATE);
        model.put("updateUri", URI_UPDATE);
        model.put("deleteUri", URI_DELETE);
        model.put("placeSearchCommand", psc);

        return new ModelAndView(VIEW_INDEX, model);
    }

    @RequestMapping(value=URI_CREATE, method=RequestMethod.GET)
    public ModelAndView create(ModelMap model) {
        logger.info("create<get>()");

        Place place = new Place();
        model.put("place", place);

        return new ModelAndView(VIEW_CREATE,model);
    }

    @RequestMapping(value=URI_CREATE, method=RequestMethod.POST)
    public ModelAndView create(ModelMap model,
        @ModelAttribute("place") Place place,
        Errors errors)
    {
        logger.info("create<post>('" + place.getName() + "')");

        defaultValidator.validate(place, errors);
        if (errors.hasErrors()) {
            return new ModelAndView (VIEW_CREATE, model);
        }        
        placeFC.create(place);

        return new ModelAndView(VIEW_SUCCESS);
    }

    @RequestMapping(value=URI_UPDATE, method=RequestMethod.GET)
    public ModelAndView update(ModelMap model, 
        @RequestParam("id") Long id)
    {
        logger.info("update<get>(" + id + ")");

        Place place = placeFC.read(id);
        if (place == null) {
            throw new IllegalArgumentException("Place " + id + " not found.");
        }
        model.put("place", place);

        return new ModelAndView(VIEW_UPDATE, model);
    }

    @RequestMapping(value=URI_UPDATE, method=RequestMethod.POST)
    public ModelAndView update(ModelMap model,
        @ModelAttribute("place") Place place,
        Errors errors)
    {
        logger.info("update<post>(" + place.getId() + ")");

        defaultValidator.validate(place, errors);
        if (errors.hasErrors()) {
            return new ModelAndView(VIEW_UPDATE, model);
        }
        placeFC.update(place);

        return new ModelAndView(VIEW_SUCCESS);
    }

    @RequestMapping(value=URI_DELETE)
    public ModelAndView delete(ModelMap model,
        @RequestParam(value="id") Long id   )
    {
        logger.info("delete(" + id + ")");

        placeFC.delete(id);

        return new ModelAndView("redirect:" + URI_INDEX);
    }    
}

