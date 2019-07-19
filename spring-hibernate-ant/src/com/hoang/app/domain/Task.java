package com.hoang.app.domain;

import java.util.Date;

/**
 * Hoang Truong
 */

public class Task extends AbstractEntity { 

     private static final long serialVersionUID = 1L;

    // Fields    

     private Integer version;
     private String taskId;
     private String type;
     private Date startDate;
     private Date dueDate;
     private String priorityLevel;
     private String description;
     private String reference;
     private String status;
     private User assignee;
     // Constructors

    /** default constructor */
    public Task() {
    }

    /** full constructor */
    public Task(String taskId, String type, Date startDate, Date dueDate, String priorityLevel, String description, String reference, String status, User assignee) {
       this.taskId = taskId;
       this.type = type;
       this.startDate = startDate;
       this.dueDate = dueDate;
       this.priorityLevel = priorityLevel;
       this.description = description;
       this.reference = reference;
       this.status = status;
       this.assignee = assignee;
    }

    // Property accessors
    public Integer getVersion() {
        return this.version;
    }
    public void setVersion(Integer version) {
        this.version = version;
    }
    public String getTaskId() {
        return this.taskId;
    }
    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
    public String getType() {
        return this.type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public Date getStartDate() {
        return this.startDate;
    }
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    public Date getDueDate() {
        return this.dueDate;
    }
    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }
    public String getPriorityLevel() {
        return this.priorityLevel;
    }
    public void setPriorityLevel(String priorityLevel) {
        this.priorityLevel = priorityLevel;
    }
    public String getDescription() {
        return this.description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getReference() {
        return this.reference;
    }
    public void setReference(String reference) {
        this.reference = reference;
    }
    public String getStatus() {
        return this.status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public User getAssignee() {
        return this.assignee;
    }
    public void setAssignee(User assignee) {
        this.assignee = assignee;
    }
}
