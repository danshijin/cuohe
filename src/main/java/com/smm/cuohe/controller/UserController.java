package com.smm.cuohe.controller;

import com.smm.cuohe.bo.IUserBO;
import com.smm.cuohe.domain.User;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


/**
 * Created by zhenghao on 2015/7/27.
 * this is demo
 */
@Controller
@RequestMapping("/user")
public class UserController {

    private static Logger logger= Logger.getLogger(UserController.class.getName());

    //@Resource
    private IUserBO iUserBO;


    @RequestMapping("/all")
    public ModelAndView all(){

        List<User> users=this.iUserBO.getAll();

        ModelAndView view=new ModelAndView("user/all");

        view.addObject("userList", users);

        logger.info("-----user name:"+users.get(0).getName());

        return view;
    }


    @RequestMapping("/info")
    public ModelAndView info(@RequestParam("id") int id){

        ModelAndView view=new ModelAndView("user/info");

        User user=this.iUserBO.getOne(id);

        if(null==user) user=new User();

        view.addObject("user",user);

        return view;
    }

}
