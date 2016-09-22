package com.smm.cuohe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value="/client")
public class ClientController {
	
	@RequestMapping(value="purchase")
	public ModelAndView all(){
		
		ModelAndView view=new ModelAndView("client/purchase");

//        System.out.println("client all");
        
        return view;
	}
}
