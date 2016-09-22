package com.smm.cuohe.bo.impl.users;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smm.cuohe.bo.users.UserInfoBO;
import com.smm.cuohe.dao.base.ChOnlineHistoryMapper;
import com.smm.cuohe.dao.users.UserInfoDAO;
import com.smm.cuohe.domain.Chats;
import com.smm.cuohe.domain.Items;
import com.smm.cuohe.domain.User;
import com.smm.cuohe.domain.base.ChOnlineHistory;
import com.smm.cuohe.enumDef.LoginResult;

/**
 * 
 * @author zengshihua
 * 
 * 用户登录业务处理
 *
 */
@Service
public class UserInfoBoImpl implements UserInfoBO {

	private static Logger logger= Logger.getLogger(UserInfoBoImpl.class.getName());
	
	@Resource
	private UserInfoDAO userInfoDAO;
	@Autowired
	ChOnlineHistoryMapper chOnlineDao;
	

	@Override
	public LoginResult login(User user,HttpServletRequest request) {
		
		
		LoginResult loginResult=LoginResult.FAILD;
		try {
			Map<String, String> paramMap=new HashMap<String, String>();
			paramMap.put("account", user.getAccount());
			paramMap.put("pwd", user.getPwd());
			List<User> listUser = this.userInfoDAO.userLogin(paramMap);
			if (listUser != null && listUser.size() > 0) {
				if(listUser.size()>1){
					loginResult=LoginResult.EXE;
					logger.info("用户登录失败，数据库存在相同用户.");
				}else{
					
					User userInfo = listUser.get(0);
					//修改用户在线状态
//					this.userInfoDAO.updateOnline(1,userInfo.getId());
					//加载对应品目
					List<Items> items=this.userInfoDAO.getItemsById(userInfo.getItemId());
					userInfo.setItems(items.get(0));
					//将用户信息放进Session
					request.getSession().setAttribute("userInfo", userInfo);
					loginResult=LoginResult.SUCC;
					ChOnlineHistory record = new ChOnlineHistory();
					record.setCreatetime(new Date());
					record.setEndTime(new Date());
					record.setEmployeeid(userInfo.getId());
					record.setLogintype(1);
					record.setOnlinetype(0);
					chOnlineDao.insert(record);//插入在线历史记录表
					
					
					logger.info("用户登录成功，用户信息："+listUser.get(0).toString());
				}
			} else {
				loginResult=LoginResult.FAILD;
				logger.info("用户登录失败，请查看具体原因.");
			}
		} catch (Exception e) {
			loginResult=LoginResult.EXE;
			logger.info("用户登录失败，程序发生异常."+e.getMessage());
		}
		return loginResult;
	}

	
	@Override
	public Integer updateItem(String itemId, String userId) {
		
		return this.userInfoDAO.updateItem(itemId,userId);
	}


	@Override
	public List<Items> getItems() {
		
		return this.userInfoDAO.getItems();
	}

	/**
	 * 获取所有用户ID及姓名 insert by dongmiaonan
	 */ 
	@Override
	public List<User> getAccount() { 
		return this.userInfoDAO.getAccount();
	}


	@Override
	public List<Chats> queryChatsRemind(Map<String, Object> paramMap) {
		
		return this.userInfoDAO.queryChatsRemind(paramMap);
	}


	@Override
	public List<Items> getItemsById(String itemId) {
		
		//加载对应品目
		List<Items> items=this.userInfoDAO.getItemsById(itemId);
		
		return items;
	}


	@Override
	public Integer updateOnline(Integer online,Integer userId){
		
		return this.userInfoDAO.updateOnline(online,userId);
		
	}

	@Override
	public User getEditableInfo(String account) {
		return userInfoDAO.getEditableInfo(account);
	}


	@Override
	public int updateUserInfo(int id, String name, String email, String image, String oldPass, String newPass) {
		return userInfoDAO.updateUserInfo(id, name, email, image, oldPass, newPass);
	}


	@Override
	public int insertDefaultUser(User user) {
		return userInfoDAO.insertDefaultUser(user);
	}


	@Override
	public int countUserByAccountAndName(User user) {
		return userInfoDAO.countUserByAccountAndName(user);
	}
	
	

}
