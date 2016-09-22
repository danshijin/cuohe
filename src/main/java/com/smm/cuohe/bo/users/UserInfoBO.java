package com.smm.cuohe.bo.users;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.annotations.Param;

import com.smm.cuohe.domain.Chats;
import com.smm.cuohe.domain.Items;
import com.smm.cuohe.domain.User;
import com.smm.cuohe.enumDef.LoginResult;

public interface UserInfoBO {

	/**
	 * 匹配登录
	 */
	public LoginResult login(User user,HttpServletRequest request);
	
	/**
	 * 获取所有用户ID及姓名 insert by dongmiaonan
	 */
	public List<User> getAccount();
	
	/**
	 * 品目列表
	 */
	public List<Items> getItems();
	
	
	/**
	 * 修改品目
	 * 
	 */
	
	public Integer updateItem(String itemId,String userId);
	
	/**
	 * 洽谈请求
	 * @param paramMap
	 * @return
	 */
	public List<Chats> queryChatsRemind(Map<String, Object> paramMap);
	
	
	/**
	 * 根据ID品目列表
	 */
	public List<Items> getItemsById(String itemId);
	
	/**
	 * 修改用户登录状态
	 * @param paramMap
	 * @return
	 */
	public Integer updateOnline(Integer online,Integer userId);
	
	
	/** 获取可编辑用户信息
	 * @param account
	 * @return
	 */
	public User getEditableInfo(String account);
	
	
	/** 更新用户信息
	 * @param name
	 * @param email
	 * @param image
	 * @param id
	 * @return
	 */
	public int updateUserInfo(int id, String name, String email, String image, String oldPass, String newPass);
	
	public int insertDefaultUser(User user);
	
	public int countUserByAccountAndName(User user);
	
}
