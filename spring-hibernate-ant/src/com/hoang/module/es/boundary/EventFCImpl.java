package com.hoang.module.es.boundary;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hoang.app.boundary.ApplicationSettings;
import com.hoang.app.domain.User;
import com.hoang.app.dto.PagedResultDTO;
import com.hoang.app.exception.BusinessException;
import com.hoang.app.service.CrudService;
import com.hoang.module.es.domain.Event;
import com.hoang.module.es.domain.Place;
import com.hoang.module.es.domain.Vote;
import com.hoang.module.es.service.EventService;

/**
 * 
 * @author Hoang Truong
 */

@Service
@Transactional(propagation=Propagation.REQUIRES_NEW)
public class EventFCImpl implements EventFC {

	private Logger logger = Logger.getLogger(EventFCImpl.class);
			
	@Autowired
	private CrudService crudService;
		
	@Autowired
	private EventService eventService;
	
//	@Autowired
//	private HibernateTemplate hibernateTemplate;		

	
	// CRUD
	public Event create(Event event) {
		logger.info("create('" + event + "')");		
		return crudService.create(event);
	}
	
	public Event read(Serializable id) {
		logger.info("read(" + id + ")");		
		return crudService.read(Event.class, id);
	}		
	
	public Event update(Event event) {
		logger.info("update(" + event.getId() + ")");
		return crudService.update(event);
	}
	
	public void delete (Serializable id) {
		logger.info("delete(" + id + ")");		
		crudService.delete(crudService.read(Event.class, id));
	}		
		
	
	// Event-specific	
	public PagedResultDTO<Event> search(int pageIndex, String firstName) {
		logger.info("search('" + firstName + "')" ); 
		return eventService.search(ApplicationSettings.getPageSize(), pageIndex, firstName);
	}
	
	public PagedResultDTO<Event> search(int pageSize, int pageIndex, String firstName) {
		logger.info("search('" + firstName + "')" ); 
		return eventService.search(pageSize, pageIndex, firstName);
	}
		
	public Event generateEvent() {
	 	Place randomPlace = getRandomPlace();

        Event event = new Event();
        event.setDate(new Date().toString());
        event.setPlace(randomPlace.getName() + " (Phone: " + randomPlace.getPhone() + ")");
        event.setReserver(getRandomReserver().getUsername());

        return event;
	}
	
	public void remind(Serializable id) throws BusinessException {
        try {
        	logger.info("remind(" + id + ")" ); 
        	
        	Event event = crudService.read(Event.class, id);
        	String remindContent = getRemindContent(event);
        	logger.info(remindContent);
        	
//        	List<User> users = crudService.getAll(User.class);
//            String [] to = new String[users.size()];
//            int i = 0;
//            for (User user : users) {
//                to[i++] = user.getEmail();
//            }
//	        MailUtil.sendHtmlMail("localhost",
//					                "admin@localhost",
//					                "Event Tool",
//					                to,
//					                "Event Remind",
//					                remindContent,
//					                ""
//	        );
        }
        catch (Exception ex) {
        	throw new BusinessException(ex.getMessage());
        }                
	}
	
	public void mail(Serializable id) throws BusinessException {		        
        try {
        	logger.info("mail(" + id + ")" );
        	
        	Event event = crudService.read(Event.class, id);
        	String resultContent = getResultContent(event);
        	logger.info(resultContent);
        	
//        	List<User> users = crudService.getAll(User.class);
//            String [] to = new String[users.size()];
//            int i = 0;
//            for (User user : users) {
//                to[i++] = user.getEmail();
//            }
//	        MailUtil.sendHtmlMail("localhost",
//				                "admin@localhost",
//				                "Event Tool",
//				                to,
//				                "Event Voting Result",
//				                resultContent,
//				                ""
//	        ); 
        }
        catch (Exception ex) {
        	throw new BusinessException(ex.getMessage());
        } 
	}
	
	
	////// Utility Methods //////
	private Place getRandomPlace() {
        List<Place> places = crudService.getAll(Place.class);
        int randomIndex = (int) (Math.round(Math.random()*(places.size() - 1 )));
        
        return places.get(randomIndex);
    }

    private User getRandomReserver() {
        List<User> users = crudService.getAll(User.class);
        int randomIndex = (int) (Math.round(Math.random()*(users.size() - 1 )));
        
        return users.get(randomIndex);
    }
    
	private String getRemindContent(Event event) {
        StringBuilder sb = new StringBuilder();
        sb.append("Hi all,"); sb.append("<br/>");
        sb.append("<br/>");
        sb.append("In order to reduce the stress of our team in making a decision " +
                  "of where to go for the coming event, a Event Scheduling System " +
                  "is used to schedule this event."); sb.append("<br/>");
        sb.append("<br/>");
        sb.append("Here is the random result generated by ESS: "); sb.append("<br/>");
        sb.append("* Place: <b>" + event.getPlace() + "</b>"); sb.append("<br/>");
        sb.append("<br/>");
        sb.append("It is obvious that not everyone may like to go to <b>" + event.getPlace() + "</b> this time. " +
                  "So you have a right to vote for your favorite places. <br/>");
        sb.append("<br/>");
        sb.append("After the voting, the winning place will be sent to you in another email. "); sb.append("<br/>");
        sb.append("<br/>");                
        sb.append("<a href=\"http://localhost:8080/Hoang_Spring/event/index.htm\">Vote</a> " +
                  "for your favorite places now. ");  sb.append("<br/>");
        sb.append("<br/>");
        sb.append("<br/>");
        sb.append("Have fun !!!");

        return sb.toString();
	}
	 
	private String getResultContent(Event event) {
        StringBuilder sb = new StringBuilder();
        sb.append("Hi all,"); sb.append("<br/>");
        sb.append("<br/>");
        sb.append("Here is the voting result: "); sb.append("<br/>");
        sb.append("* Place: <b>" + getWinningPlace(event) + "</b>"); sb.append("<br/>");
        sb.append("* Reserver: <b>" + event.getReserver() + "</b>"); sb.append("<br/>");
        sb.append("<br/>");
        sb.append("Please confirm your presence with <b>" + event.getReserver() +
                    "</b> before <b>...</b>. "); sb.append("<br/>");
        sb.append("<br/>");
        sb.append("<br/>");
        sb.append("Have fun !!!");

        return sb.toString();
	}
	 
	private String getWinningPlace(Event event) {
		String winningPlace = event.getPlace() + " : 0 vote";

        // initialize a list of HashMap: key(place), value(counter)
        List<Place> places = crudService.getAll(Place.class);
        List<HashMap<String, Integer>> restTables = new ArrayList<HashMap<String, Integer>>(places.size());
        for (Place place : places) {
            HashMap<String, Integer> ht = new HashMap<String, Integer>();
            ht.put(place.getName(), new Integer(0));
            restTables.add(ht);
        }
        
        // get winning place which has the most votes
        Integer max = new Integer(0);
        Set<Vote> votes = event.getVotes();        
        for (Vote vote : votes) {
            Set<Place> votedRes = vote.getPlaces();
            for (Place place : votedRes) {
                for (HashMap<String, Integer> hm : restTables) {
                    Integer counter = (Integer) hm.get(place.getName());
                    if (counter != null) {
                        counter++;
                        hm.put(place.getName(), counter);
                        if (counter.intValue() > max.intValue()) {
                            max = counter;                            
                            winningPlace = place.getName() + " (" + place.getPhone() + "): " + max + " vote(s).";
                            event.setReserver(vote.getUser().getUsername());
                        }
                    }
                }
            }
        }

        event.setPlace(winningPlace);
        crudService.update(event);

        return winningPlace;
   }
}
