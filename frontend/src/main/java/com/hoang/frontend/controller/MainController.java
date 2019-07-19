package com.hoang.frontend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    @RequestMapping("/index")
    public String index () {
        return "index";
    }

    @RequestMapping("/jui_jsb")
    public String juiJSb () {
        return "jui_jsb";
    }

    @RequestMapping("/reactjs_fluxxor")
    public String reactjsFluxxor () {
        return "reactjs_fluxxor";
    }

    @RequestMapping("/reactjs_redux")
    public String reactjsRedux () {
        return "reactjs_redux";
    }
}
