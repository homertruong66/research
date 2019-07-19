package com.hoang.app.domain;

import java.util.Date;
import java.util.UUID;

/**
*
* @author Hoang Truong
*/

public class Comment extends AbstractEntity { 

    private static final long serialVersionUID = 1L;
     
    private String key;
    private UUID sc;
    private String commenter;
    private String text;
    private Date date;
    
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public UUID getSc() {
		return sc;
	}
	public void setSc(UUID sc) {
		this.sc = sc;
	}
	public String getCommenter() {
		return commenter;
	}
	public void setCommenter(String commenter) {
		this.commenter = commenter;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}           
}
