package com.hoang.app.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.hoang.app.boundary.UserFC;
import com.hoang.app.command.VoteSearchCommand;
import com.hoang.app.dto.PagedResultDTO;
import com.hoang.module.es.boundary.EventFC;
import com.hoang.module.es.boundary.PlaceFC;
import com.hoang.module.es.boundary.VoteFC;
import com.hoang.module.es.domain.Event;
import com.hoang.module.es.domain.Place;
import com.hoang.module.es.domain.Vote;
import com.hoang.module.es.dto.VoteDTO;

/**
 * 
 * @author Hoang Truong
 *
 */

@Controller
@SessionAttributes(value="voteDTO")
public class VoteController {

    private final Logger logger = Logger.getLogger(VoteController.class);

    // Requested URIs
    public static final String URI_INDEX    = "/vote/index.htm";
    public static final String URI_CREATE   = "/vote/create.htm";
    public static final String URI_ACCEPT   = "/vote/accept.htm";
    public static final String URI_UPDATE   = "/vote/update.htm";
    public static final String URI_DELETE   = "/vote/delete.htm";
    public static final String URI_SUCCESS  = "/vote/success.htm";

    // Returned Views
    public static final String VIEW_INDEX   = "vote/search";
    public static final String VIEW_CREATE  = "vote/edit";
    public static final String VIEW_UPDATE  = "vote/edit";
    public static final String VIEW_SUCCESS = "vote/success";

    @Autowired
    private EventFC eventFC;

    @Autowired
    private VoteFC voteFC;

    @Autowired
    private UserFC userFC;

    @Autowired
    private PlaceFC placeFC;

    @RequestMapping(value=URI_INDEX, method=RequestMethod.GET)
    public ModelAndView search(ModelMap model,
        @RequestParam(value="eId", required=true) Long eventId)
    {
        logger.info("search<get>()");

        VoteSearchCommand vsc = new VoteSearchCommand();
        vsc.setEventId(eventId);
        model.put("voteSearchCommand", vsc);

        return new ModelAndView(VIEW_INDEX, model);
    }

    @RequestMapping(value=URI_INDEX, method=RequestMethod.POST)
    public ModelAndView search(ModelMap model,
        @ModelAttribute(value="voteSearchCommand") VoteSearchCommand vsc)
    {
        logger.info("search<post>(" + vsc.getEventId() + ")");

        PagedResultDTO<Vote> pr = voteFC.searchByEvent(vsc.getPage(), vsc.getEventId());
        model.put("pagedResult", pr);
        model.put("createUri", URI_CREATE);
        model.put("updateUri", URI_UPDATE);
        model.put("deleteUri", URI_DELETE);
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.put("userName", userDetails.getUsername());

        return new ModelAndView(VIEW_INDEX, model);
    }

    @RequestMapping(value=URI_CREATE, method=RequestMethod.GET)
    public ModelAndView create(ModelMap model,
        @RequestParam(value="eId") Long eId)
    {
        logger.info("create<get>(" + eId + ")");

        VoteDTO voteDTO = new VoteDTO();
        voteDTO.setDate(new Date().toString());
        voteDTO.setEvent(eventFC.read(eId));
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        voteDTO.setUser(userFC.get(userDetails.getUsername()));
        model.put("voteDTO", voteDTO);

        prepareEditData(model);

        return new ModelAndView(VIEW_CREATE,model);
    }

    @RequestMapping(value=URI_CREATE, method=RequestMethod.POST)
    public ModelAndView create(ModelMap model,
        @ModelAttribute("voteDTO") VoteDTO voteDTO,
        Errors errors)
    {
        logger.info("create<post>('" + voteDTO.getDate() + "')");
    
        voteFC.create(voteDTO);

        return new ModelAndView(VIEW_SUCCESS);
    }

    @RequestMapping(value=URI_ACCEPT)
    public ModelAndView accept(ModelMap model,
        @RequestParam(value="eId") Long eId)
    {
        logger.info("accept(" + eId + ")");

        Vote vote = new Vote();
        vote.setDate(new Date().toString());
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        vote.setUser(userFC.get(userDetails.getUsername()));
        Event event = eventFC.read(eId);
        vote.setEvent(event);
        int bracketIndex = event.getPlace().indexOf("(");
        String placeName = event.getPlace().substring(0, bracketIndex-1);
        vote.getPlaces().add(placeFC.get(placeName));
        voteFC.create(vote);
                
        model.put("eId", eId);

        return new ModelAndView(VIEW_SUCCESS, model);
    }

    @RequestMapping(value=URI_UPDATE, method=RequestMethod.GET)
    public ModelAndView update(ModelMap model, 
        @RequestParam(value="eId") Long eId,
        @RequestParam("id") Long id)
    {
        logger.info("update<get>(" + eId + ", " + id + ")");

        Vote vote = voteFC.read(id);
        if (vote == null) {
            throw new IllegalArgumentException("Vote " + id + " not found.");
        }
        VoteDTO voteDTO = new VoteDTO();
        voteDTO.copyFromVote(vote);
        model.put("voteDTO", voteDTO);

        prepareEditData(model);

        return new ModelAndView(VIEW_UPDATE, model);
    }

    @RequestMapping(value=URI_UPDATE, method=RequestMethod.POST)
    public ModelAndView update(ModelMap model,
        @ModelAttribute("voteDTO") VoteDTO voteDTO,
        Errors errors)
    {
        logger.info("update<post>(" + voteDTO.getId() + ")");
     
        voteFC.update(voteDTO);

        return new ModelAndView(VIEW_SUCCESS);
    }

    @RequestMapping(value=URI_DELETE)
    public ModelAndView delete(ModelMap model,
        @RequestParam(value="eId") Long eId,
        @RequestParam(value="id") Long id   ) throws Exception
    {
        logger.info("delete(" + eId + ", " + id + ")");
        
        voteFC.delete(id);

        return new ModelAndView("redirect:" + URI_INDEX + "?eId=" + eId );
    }


    // Utility methods
    private void prepareEditData(ModelMap model)    {
        List<Place> places = placeFC.getAll();
        List<String> strPlaces = new ArrayList<String>();
        for (Place place : places) {
            strPlaces.add(place.getName());
        }
        model.put("allPlaces", strPlaces);
    }
}
