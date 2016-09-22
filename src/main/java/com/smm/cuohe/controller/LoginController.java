package com.smm.cuohe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by zhenghao on 2015/8/3.
 *
 */
@Controller
public class LoginController {


    @RequestMapping("/loginDemo")
    public ModelAndView login(){

        ModelAndView view=new ModelAndView("login");
   
        return view;
    }


    @RequestMapping("/submit_login")
    public ModelAndView submitLogin(){
    
        ModelAndView view=new ModelAndView("home/index");

        return view;
    }
}
