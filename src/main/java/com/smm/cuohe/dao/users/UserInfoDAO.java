package com.smm.cuohe.dao.users;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.smm.cuohe.domain.Chats;
import com.smm.cuohe.domain.Items;
import com.smm.cuohe.domain.User;


public interface UserInfoDAO {

	/**
	 * 匹配登录
	 */
	public List<User> userLogin(Map<String, String> paramMap);
	
	public List<Map<String, Object>> dealMakerLogin(String account);
	
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
	 * 查询用户首页组件配置
	 * 
	 */
	public List<Map<String, Object>> userComponent(String itemId,String userId);
	
	
	/**
	 * 根据ID品目列表
	 */
	public List<Items> getItemsById(String itemId);
	
	
	/**
	 * 洽谈请求
	 * @param paramMap
	 * @return
	 */
	public List<Chats> queryChatsRemind(Map<String, Object> paramMap);
	
	
	/**
	 * 修改用户登录状态
	 * @param online
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
	public int updateUserInfo(@Param(value="id") int id, @Param(value="name") String name, @Param(value="email") String email, @Param(value="image") String image, @Param(value="oldPass") String oldPass, @Param(value="newPass") String newPass);
	
	public int insertDefaultUser(User user);
	
	public int countUserByAccountAndName(User user);
	
	public int generateUsers(@Param(value="empId")int empId,@Param(value="startDate")Date startDate,@Param(value="endDate")Date endDate);
}
