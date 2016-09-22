package com.smm.cuohe.controller.users;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.google.code.kaptcha.Constants;
import com.smm.cuohe.bo.users.IWebPartsPBO;
import com.smm.cuohe.bo.users.UserInfoBO;
import com.smm.cuohe.dao.base.ChOnlineHistoryMapper;
import com.smm.cuohe.domain.Chats;
import com.smm.cuohe.domain.Items;
import com.smm.cuohe.domain.User;
import com.smm.cuohe.domain.WebPartsPOJO;
import com.smm.cuohe.domain.base.ChOnlineHistory;
import com.smm.cuohe.enumDef.LoginResult;

/**
 * 
 * @author zengshihua
 * 
 *         用户登录控制器 先简单匹配，后续采用Spring Security
 * 
 */
@Controller
public class UserInfoController {

	@Resource
	private UserInfoBO userInfoBO;
	@Resource
	private IWebPartsPBO iwebpaPBO;
	@Autowired
	ChOnlineHistoryMapper chOnlineDao;

	/**
	 * 登录页
	 * 
	 * @return
	 */
	@RequestMapping("/login")
	public ModelAndView login() {

		ModelAndView view = new ModelAndView("login");

		return view;
	}

	/**
	 * 实现用户登录
	 * 
	 * @param request
	 * @param user
	 * @param mode
	 * @return
	 */
	@RequestMapping("/user/userLogin")
	@ResponseBody
	public Map<String, Object> userLogin(HttpServletRequest request, User user,
			String vCode, Model mode) {

		String code = (String) request.getSession().getAttribute(
				Constants.KAPTCHA_SESSION_KEY);

		Map<String, Object> resultMap = new HashMap<>();
		LoginResult loginResult = LoginResult.FAILD;

		if (StringUtils.isNotBlank(vCode)) {
			if (code.equals(vCode)) {
				loginResult = userInfoBO.login(user, request);
			} else {
				loginResult = LoginResult.VCODE_ERROR;
			}

		} else {
			loginResult = LoginResult.VCODE_NULL;
		}

		resultMap.put("code", loginResult.getCode());
		resultMap.put("msg", loginResult.getMessage());

		return resultMap;
	}

	@RequestMapping("/user/noPageLogin")
	public String noPageLogin(HttpServletRequest request,
			HttpServletResponse response, String uid, String pwd, String destUrl) {
		User u = new User();
		u.setAccount(uid);
		u.setPwd(pwd);
		LoginResult loginResult = LoginResult.FAILD;
		loginResult = userInfoBO.login(u, request);

		if (loginResult.equals(LoginResult.SUCC)) {

			return "redirect:" + destUrl;
		}

		return "redirect:/login.do";
	}

	/**
	 * 登录成功
	 * 
	 */
	@RequestMapping("/user/loginSucc")
	public ModelAndView loginSucc(HttpServletRequest request, Model mode) {

		ModelAndView mav = new ModelAndView();
		// 查询当前品目要显示的界面组件
		// 获取当前品目id
		User user = (User) request.getSession().getAttribute("userInfo");
		
	
		List<WebPartsPOJO> wlist = iwebpaPBO.getWebPartsPList(user.getId());
		String str = "";
		for (WebPartsPOJO webPartsPOJO : wlist) {
			str += webPartsPOJO.getId() + ",";
		}
		if (StringUtils.isNotBlank(str)) {
			str = str.substring(0, str.length() - 1);
		}
		mav.addObject("webParts", str);

		// 返回页面处理
		mav.setViewName("/home/index");

		return mav;
	}

	/**
	 * 获取用户登录信息
	 * 
	 * @param request
	 * @param mode
	 * @return
	 */
	@RequestMapping("/user/getUserLoginInfo")
	@ResponseBody
	public Map<String, Object> getUserLoginInfo(HttpServletRequest request,
			Model mode) {

		User user = (User) request.getSession().getAttribute("userInfo");

		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("userName", user.getName());

		return resultMap;
	}

	/**
	 * 实现用户退出登录
	 * 
	 */
	@RequestMapping("/user/loginOut")
	public String loginOut(HttpServletRequest request, Model mode) {

		 User user = (User) request.getSession().getAttribute("userInfo");
		// 清空session
		request.getSession().invalidate();
		ChOnlineHistory history = chOnlineDao.queryLastHistoryWeb(user.getId());
		ChOnlineHistory record = new ChOnlineHistory();
		record.setId(history.getId());
		record.setEndTime(new Date());
		record.setOnlinetype(1);//离线
		chOnlineDao.updateByPrimaryKeySelective(record);
		return "redirect:/login.do";
	}

	/**
	 * 品目列表
	 */
	@RequestMapping("/user/getItems")
	public String getItems(HttpServletRequest request, Model mode) {

		User user = (User) request.getSession().getAttribute("userInfo");
		List<Items> listItems = this.userInfoBO.getItems();
		mode.addAttribute("listItems", listItems);
		mode.addAttribute("userItemId", Integer.parseInt(user.getItemId()));

		return "home/items";
	}

	/**
	 * 用户修改品目
	 */
	@RequestMapping("/user/updateItem")
	@ResponseBody
	public Map<String, Object> updateItem(HttpServletRequest request,
			Model mode, String itemId) {

		Map<String, Object> resultMap = new HashMap<>();
		User user = (User) request.getSession().getAttribute("userInfo");
		Integer count = this.userInfoBO.updateItem(itemId,
				String.valueOf(user.getId()));
		if (count > 0) {
			resultMap.put("code", "succ");
			// 更新session
			user.setItemId(itemId);
			// 加载对应品目
			List<Items> items = this.userInfoBO.getItemsById(itemId);
			user.setItems(items.get(0));
			request.getSession().setAttribute("userInfo", user);

		} else {
			resultMap.put("code", "faild");
		}
		return resultMap;
	}

	/**
	 * 
	 * 洽谈请求提醒
	 * 
	 */
	@RequestMapping("/user/chatsRemind")
	@ResponseBody
	public Map<String, Object> chatsRemind(HttpServletRequest request,
			Model mode) {

		Map<String, Object> paramMap = new HashMap<>();
		Map<String, Object> resultMap = new HashMap<>();

		User user = (User) request.getSession().getAttribute("userInfo");
		// 品目ID
		paramMap.put("itemId", user.getItemId());
		// 订单状态
		paramMap.put("orderStatus", 1);
		List<Chats> listChats = this.userInfoBO.queryChatsRemind(paramMap);

		resultMap.put("total", listChats.size());

		return resultMap;
	}

	@RequestMapping(value = "/user/editUserFrameView")
	public ModelAndView editUserFrameView(String account) {
		ModelAndView mv = new ModelAndView("/user/editUserFrame");
		return mv;
	}

	@RequestMapping(value = "/user/editUserView")
	public ModelAndView editUserView(String account) {
		ModelAndView mv = new ModelAndView("/user/editUser");
		return mv;
	}

	/**
	 * @param user
	 * @param oldPass
	 * @param newPass
	 * @param callBackCloseElementName 成功后要调用的js函数
	 * @param imageFile
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/user/saveChangeInfo")
	public void saveChangeInfo(User user, String oldPass, String newPass,
			String callBackCloseElementName,
			@RequestParam(value="imageFile", required = false) MultipartFile imageFile,
			HttpServletRequest request, HttpServletResponse response) {
		String rltMsg = "";
		if(!imageFile.isEmpty()){
			Map<String, Object> map = getNewUserImage(imageFile, "/Public/images/user/", user.getAccount());
			String status = (String) map.get("status");
			String msg = (String) map.get("msg");
			if(status.equals("ok")){
				user.setImage(msg);  //获取用户头像路径
			} else {
				rltMsg = msg;
			}
		}
		//更新用户信息
		int num = userInfoBO.updateUserInfo(user.getId(), user.getName(),
				user.getEmail(), user.getImage(), oldPass, newPass);
		
		// 更新成功
		if (num == 1) {
			
			updateSessionUser(request, user); // 更新session中的用户信息
			if(rltMsg.equals("")){
				rltMsg = "编辑成功";
			}
			//返回处理结果
			rltMsg = "alert('"+rltMsg+"');parent.document.location.reload();parent.document.getElementById('"
					+ callBackCloseElementName + "').click()";
		} else {// 密码不对
			rltMsg="密码错误";
			rltMsg = "alert('"+rltMsg+"');window.history.back();";   //返回处理结果
		}
		outMessage(response, rltMsg); // 返回处理结果
	}
	@RequestMapping(value="/user/addUserView")
	public ModelAndView addUserView(){
		return new ModelAndView("user/addUser");
	}
	@RequestMapping(value="/user/addUser")
	@ResponseBody
	public Map<String, Object> addUser(User user){
		Map<String, Object> map = new HashMap<String, Object>();
		
		if(StringUtils.isBlank(user.getAccount())){
			map.put("status", "faild");
			map.put("msg", "参数异常，账号不能为空");
			map.put("data", "");
		} else if(StringUtils.isBlank(user.getName())){
			map.put("status", "faild");
			map.put("msg", "参数异常，名称不能为空");
			map.put("data", "");
		} else if(StringUtils.isBlank(user.getEmail())){
			map.put("status", "faild");
			map.put("msg", "参数异常，邮箱不能为空");
			map.put("data", "");
		} else {
			int count = userInfoBO.countUserByAccountAndName(user);
			if(count > 0){
				map.put("status", "faild");
				map.put("msg", "账号或用户名已存在");
				map.put("data", count);
			} else {
				userInfoBO.insertDefaultUser(user);
				map.put("status", "ok");
				map.put("msg", "添加成功");
				map.put("data", count);
			}
		}
		
		return map;
		
	}
	
	/** 获取上传的用户头像路径
	 * @param imageFile
	 * @param prePath
	 * @param imageName
	 * @return
	 */
	private Map<String, Object> getNewUserImage(MultipartFile imageFile, String prePath, String imageName){
		Map<String, Object> map = new HashMap<String, Object>();
		//构造服务器接收路径
		String relativeFilePath = imageFile.getOriginalFilename();
		String suffix = relativeFilePath.substring(relativeFilePath.lastIndexOf(".") + 1);
		Pattern p = Pattern.compile("jepg|jpg|png");
		if(!p.matcher(suffix).matches()){
			map.put("status", "faild");
			map.put("msg", "头像图片格式有误！");
			return map;
		}
		if(imageFile.getSize() > 1024 * 1024){
			map.put("status", "faild");
			map.put("msg", "头像图片太大，应小于1M！");
			return map;
		}
		relativeFilePath = prePath + imageName + suffix;
		String rootPath = System.getProperty("smm.cuohe");
		File destFile = new File(rootPath, relativeFilePath);
		if(!destFile.exists()){
			destFile.mkdirs();
		}
		try {
			imageFile.transferTo(destFile); //转移文件
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		map.put("status", "ok");
		map.put("msg", relativeFilePath);
		return map;
	}
	
	/** 更新session中的用户信息
	 * @param request
	 */
	private void updateSessionUser(HttpServletRequest request, User newUser){
		
		HttpSession session = request.getSession(Boolean.FALSE);
		User su = (User) session.getAttribute("userInfo");
		su.setEmail(newUser.getEmail());
		su.setName(newUser.getName());
		su.setImage(newUser.getImage());
		session.setAttribute("userInfo", su);
	}
	

	/** 返回js处理结果信息
	 * @param response
	 * @param msg 可执行的js代码
	 */
	private void outMessage(HttpServletResponse response, String msg){
		
		msg = "<script>" + msg + "</script>";
		PrintWriter pw = null;
		try {
			pw = response.getWriter();
			pw.write(msg);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			pw.close();
		}
	}
	
}
