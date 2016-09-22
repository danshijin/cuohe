package com.smm.cuohe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by zhenghao on 2015/8/3.
 *
 */
@Controller
@RequestMapping("/home")
public class HomeController {

    @RequestMapping("index")
    public ModelAndView index(){

        ModelAndView view=new ModelAndView("home/index");

        System.out.println("home test");

        return view;
    }
}
