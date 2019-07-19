package com.hoang.linkshortener.view_model;

public class CustomResponseEntity {

    private Object request;
    private Object response;

    public Object getRequest () {
        return request;
    }

    public void setRequest (Object request) {
        this.request = request;
    }

    public Object getResponse () {
        return response;
    }

    public void setResponse (Object response) {
        this.response = response;
    }
}
