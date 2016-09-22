package com.smm.cuohe.dao;


import com.smm.cuohe.domain.User;

import java.util.List;

/**
 * Created by zhenghao on 2015/7/31.
 * demo
 */

public interface IUserDAO {

    public List<User> getAll();


    public User getOne(int id);


	public List<User> getUsersByItemId(String itemId);


	public Integer jobTask();
	
}
