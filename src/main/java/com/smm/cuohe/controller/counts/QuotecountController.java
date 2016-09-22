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
import com.smm.cuohe.bo.dealmake.BuyPoolBo;
import com.smm.cuohe.domain.Customer;
import com.smm.cuohe.domain.PageBean;
import com.smm.cuohe.domain.User;
import com.smm.cuohe.domain.counts.TrailEntity;
import com.smm.cuohe.domain.dealmake.OrdercodeEntity;
import com.smm.cuohe.util.DateUtil;

/**auth tantaigen
 * 
 * 报价统计
 * 
 * */
@Controller
@RequestMapping(value="/quotecount")
public class QuotecountController {
	@Resource TrialcountBo trialcountBo;
	@Resource  BuyPoolBo BuyPoolBo;

	@RequestMapping(value="quoteQuery")
	public   ModelAndView  trailQuary(HttpServletRequest req,Integer  pno){
		
			User user=(User)req.getSession().getAttribute("userInfo");
			
		ModelAndView view =new ModelAndView("counts/quotecount");
		List<Customer> company=trialcountBo.selectcompanyName(user.getItemId());
		List<TrailEntity> itemslist=trialcountBo.itemslist();
		String companyMame=req.getParameter("companyMame");
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
		
		if(companyMame!=null&&!"".equals(companyMame)){
					
					
					map.put("companyMame", companyMame);
				}
		map.put("itemsId", user.getItems().getId());
		List<OrdercodeEntity> ordercountlist1=trialcountBo.querybaojiacount(map);
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
		view.addObject("totalRecords",totalRecords);//总条数
		view.addObject("totalPage",page.getTotalPages());//总页数
		
		List<OrdercodeEntity> baojialist=trialcountBo.querybaojiacount(map);

		view.addObject("baojialist", baojialist);
		view.addObject("companylist", company);

		view.addObject("itemslist", itemslist);
		view.addObject("companyMame", companyMame);
		
		return view;
		
	}
	
}

