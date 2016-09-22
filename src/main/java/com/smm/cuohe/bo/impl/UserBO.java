package com.smm.cuohe.bo.impl;

import com.smm.cuohe.bo.IUserBO;
import com.smm.cuohe.dao.IUserDAO;
import com.smm.cuohe.domain.User;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.List;

/**
 * Created by zhenghao on 2015/7/27.
 *
 */
@Service
public class UserBO implements IUserBO {

    @Resource
    private IUserDAO iUserDAO;

    @Override
    public List<User> getAll() {

        List<User> users=this.iUserDAO.getAll();

        return users;
    }

    @Override
    public User getOne(int id) {

        return this.iUserDAO.getOne(id);
        
    }

	@Override
	public List<User> getUsersByItemId(String itemId) {
		return iUserDAO.getUsersByItemId(itemId);
	}

	@Override
	public Integer jobTask() {
		// TODO Auto-generated method stub
		return iUserDAO.jobTask();
	}


}
