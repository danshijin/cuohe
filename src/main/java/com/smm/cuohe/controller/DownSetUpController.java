package com.smm.cuohe.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.smm.cuohe.bo.DownSetUpbo;
import com.smm.cuohe.domain.DownSetUpEntity;
import com.smm.cuohe.domain.User;
import com.smm.cuohe.tools.ResultMessage;

/**
 * 
 * 下游行业设置
 * @author tantaigen
 *
 */
@Controller
@RequestMapping("/downSetUp")
public class DownSetUpController {
	@Resource DownSetUpbo setbo;
	@Resource
	private RestTemplate restTemplate;
	@Value("#{ch['getCategory.URL']}")
	private String getCategoryUrl;

	@RequestMapping("/setup")
	public ModelAndView setup( HttpServletRequest req ){
		ModelAndView view =new ModelAndView("setup/downsetup");
		User user =(User)req.getSession().getAttribute("userInfo");
		List<DownSetUpEntity> itemprodlist=setbo.queryItemsProd(Integer.valueOf(user.getItemId()));
		view.addObject("itemprodlist", itemprodlist);
		return view;
	}
	
	/**
	 * 添加下游行业设置
	 * @param req
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	
	@RequestMapping("/addsetup")
	@ResponseBody
	public Object addsetup( HttpServletRequest req,HttpServletResponse response ) throws ServletException, IOException{
			User user=(User)req.getSession().getAttribute("userInfo");
		
		DownSetUpEntity downSetUpEntity=new DownSetUpEntity();
		downSetUpEntity.setProductName(req.getParameter("productName"));
		downSetUpEntity.setItemId(user.getItems().getId());
		DownSetUpEntity setentity=	setbo.queryItemsProdname(downSetUpEntity);
		if(setentity!=null){
			
			return "cunzai";
		}

		DownSetUpEntity setup= new DownSetUpEntity();
		setup.setCreatedAt(new Date());
		setup.setStatus(0);
		setup.setCreatedBy(user.getId());
		setup.setItemId(user.getItems().getId());
		setup.setProductName(req.getParameter("productName"));
		setbo.addTtemsProd(setup);
		 return ResultMessage.ADD_SUCCESS_RESULT;
	
	}

	@RequestMapping("/delsetup")
	public void delsetup( HttpServletRequest req ,HttpServletResponse response) throws ServletException, IOException{
		
		String id=req.getParameter("ids");
		if(id!=null&& !"".equals(id)){
			
			setbo.delItemsProd(Integer.valueOf(id));
			
		}		
		req.getRequestDispatcher("/downSetUp/setup.do").forward(req, response);

	}

}
