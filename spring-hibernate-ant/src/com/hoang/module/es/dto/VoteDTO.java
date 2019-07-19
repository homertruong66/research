package com.hoang.module.es.dto;

import java.util.HashSet;
import java.util.Set;

import com.hoang.app.domain.User;
import com.hoang.module.es.domain.Event;
import com.hoang.module.es.domain.Place;
import com.hoang.module.es.domain.Vote;

/**
 * 
 * @author Hoang Truong
 *
 */

public class VoteDTO {
    private Long id;
    private String date;
    private Event event;
    private User user;
    private Set<String> places = new HashSet<String>(0);
    private Integer version;
   
    public void copyFromVote(Vote vote) {
        this.setId(vote.getId());
        this.setDate(vote.getDate());
        this.setEvent(vote.getEvent());
        this.setUser(vote.getUser());
        for (Place place : vote.getPlaces()) {
            places.add(place.getName());
        }
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<String> getPlaces() {
        return places;
    }

    public void setPlaces(Set<String> places) {
        this.places = places;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    public Integer getVersion() {
		return version;
    }
    
    public void setVersion(Integer version) {
		this.version = version;
	}
}
