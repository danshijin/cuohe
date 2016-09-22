package com.smm.cuohe.bo;

import com.smm.cuohe.domain.User;

import java.util.List;

/**
 * Created by zhenghao on 2015/7/27.
 *
 */
public interface IUserBO {

    /**
    *获取所有用户
    * */
    public List<User> getAll();


    /**
     * 获取指定用户
     * */
    public User getOne(int id);

    /**
     * @author lirubin
     * 获取品目相同的用户
     * @param itemId
     * @return
     */
    public List<User> getUsersByItemId(String itemId);
    
    public Integer jobTask();

}
