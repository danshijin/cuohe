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
import com.smm.cuohe.domain.dealmake.OrdercodeEntity;
import com.smm.cuohe.util.DateUtil;

/**auth tantaigen
 * 
 * 订单统计
 * 
 * */
@Controller
@RequestMapping(value="/ordercount")
public class OrdercountController {
	@Resource TrialcountBo trialcountBo;

	@RequestMapping(value="orderQuery")
	public   ModelAndView  trailQuary(HttpServletRequest req,Integer pno){
		User user=(User)req.getSession().getAttribute("userInfo");
	
		ModelAndView view =new ModelAndView("counts/ordercount");
		List<TrailEntity> userlist=trialcountBo.userlist();
		List<TrailEntity> itemslist=trialcountBo.itemslist();
		String userId=req.getParameter("userId");
		Map<String, Object> map=new HashMap<String, Object>();
		String startDate=req.getParameter("startDate");
		String endDate=req.getParameter("endDate");
	
		if(startDate!=null && !"".equals(startDate)){
			
			map.put("startDate", DateUtil.doSFormatDate(startDate, "yyyy-MM-dd"));
		}
		
		if(endDate!=null && !"".equals(endDate)){
			
			map.put("endDate", DateUtil.doSFormatDate(endDate, "yyyy-MM-dd"));
		}
		view.addObject("endDate", endDate);
		view.addObject("startDate", startDate);
		int totalRecords=0;
		int id=0;
		if(userId!=null&&!"".equals(userId)){
					
					id=Integer.valueOf(userId);
					map.put("userId", id);
				}
		map.put("itemsId", user.getItems().getId());
		List<OrdercodeEntity> ordercountlist1=trialcountBo.queryordercount(map);
		if(ordercountlist1!=null&&ordercountlist1.size()>0){
			totalRecords=ordercountlist1.size();
			
		}
		if(pno==null){
			pno=1;
			
		}
		PageBean page=new PageBean(10,pno, totalRecords);
		int startNum=page.getStartNum();
		int endNum=page.getEndNum();
		map.put("startNum", startNum);
		map.put("endNum", endNum);
		List<OrdercodeEntity> ordercountlist=trialcountBo.queryordercount(map);
		view.addObject("totalRecords",totalRecords);//总条数
		view.addObject("totalPage",page.getTotalPages());//总页数
		
		view.addObject("ordercountlist", ordercountlist);
		view.addObject("itemslist", itemslist);
		view.addObject("userlist", userlist);
		view.addObject("userId", id);
		return view;
		
	}
	
}

