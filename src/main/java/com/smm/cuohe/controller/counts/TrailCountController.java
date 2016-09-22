package com.smm.cuohe.controller.counts;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.smm.cuohe.bo.counts.TrialcountBo;
import com.smm.cuohe.domain.PageBean;
import com.smm.cuohe.domain.User;
import com.smm.cuohe.domain.counts.TrailEntity;
import com.smm.cuohe.util.DateUtil;

/**auth tantaigen
 * 
 * 线索统计
 * 
 * */
@Controller
@RequestMapping(value="/trailcount")
public class TrailCountController {
	@Resource TrialcountBo trialcountBo;

	@RequestMapping(value="trailQuery")
	public   ModelAndView  trailQuary(HttpServletRequest req,Integer pno){
		Map<String, Object> map=new HashMap<String, Object>();
		String userId=req.getParameter("userId");
		
		String startDate=req.getParameter("startDate");
		String endDate=req.getParameter("endDate");
	
		if(startDate!=null && !"".equals(startDate)){
			
			map.put("startDate", DateUtil.doSFormatDate(startDate, "yyyy-MM-dd"));
		}
		
		if(endDate!=null && !"".equals(endDate)){
			
			map.put("endDate", DateUtil.doSFormatDate(endDate, "yyyy-MM-dd"));
		}
		
		Integer id=null;
		if(userId!=null&&!"".equals(userId)){
			
			id=Integer.valueOf(userId);
			map.put("userId", id);
		}
		User user=(User)req.getSession().getAttribute("userInfo");
		map.put("itemsId", user.getItems().getId());
		List<TrailEntity> list1=trialcountBo.trialquery(map);
		int totalRecords=0;
		if(list1!=null){
			totalRecords=list1.size();
		}
		if(pno==null){
			pno=0;
		}
		PageBean page=new PageBean(10,pno, totalRecords);
		int startNum=page.getStartNum();
		int endNum=page.getEndNum();
		map.put("startNum", startNum);
		map.put("endNum", endNum);
		
		List<TrailEntity> list=trialcountBo.trialquery(map);
	
		ModelAndView view =new ModelAndView("counts/trailCount");
		List<TrailEntity> userlist=trialcountBo.userlist();
		List<TrailEntity> itemslist=trialcountBo.itemslist();
		view.addObject("totalRecords",totalRecords);//总条数
		view.addObject("totalPage",page.getTotalPages());//总页数
		
		view.addObject("traillist", list);
		view.addObject("itemslist", itemslist);
		view.addObject("userlist", userlist);
		view.addObject("userId", id);
		view.addObject("endDate", endDate);
		view.addObject("startDate", startDate);
		
		return view;
		
	}
	
	@RequestMapping(value="traillikeQuery")
	public   ModelAndView  traillikeQuary(HttpServletRequest req){
		
		String userid=req.getParameter("userID");
		Map<Object, Object> map=new HashMap<Object, Object>();
		
		if(userid!=null&&!"".equals(userid)){
			
			map.put("id", userid);
		}
		List<TrailEntity> list=trialcountBo.trailListlike(map);
	
		ModelAndView view =new ModelAndView("counts/trailCount");
		List<TrailEntity> userlist=trialcountBo.userlist();
		List<TrailEntity> itemslist=trialcountBo.itemslist();
		
		
		view.addObject("traillist", list);
		view.addObject("itemslist", itemslist);
		view.addObject("userlist", userlist);
		
		return view;
		
	}
	
	
}

