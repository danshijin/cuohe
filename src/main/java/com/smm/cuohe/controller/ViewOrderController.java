package com.smm.cuohe.controller;

import com.smm.cuohe.bo.IAttrValuesBO;
import com.smm.cuohe.bo.IItemAttrBO;
import com.smm.cuohe.bo.IOrdersBO;
import com.smm.cuohe.bo.dealmake.ISellPoolBO;
import com.smm.cuohe.domain.*;
import com.smm.cuohe.domain.dealmake.SellPool;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**根据卖盘池信息查看订单
 * @author zhangnan
 *
 */
@Controller
@RequestMapping("/viewOrderController")
public class ViewOrderController {
	private static Logger logger = Logger.getLogger(ViewOrderController.class
			.getName());
	
	@Resource
	private IOrdersBO ordersBO;
	@Resource
	private IItemAttrBO itemAttrBO;
	@Resource
	private IAttrValuesBO attrValuesBO;
	@Resource
	private ISellPoolBO sellPollBo;
	  
	    /**     查看卖盘池订单信息
	     * @discription 
	     * @author Nancy/张楠       
	     * @created 2015年8月27日 上午11:30:56          
	     */
	@RequestMapping("/viewOrder")
	public ModelAndView viewOrder(Integer companyId,HttpServletRequest req,Integer pno,Integer sellPoolId) {
		User user = (User) req.getSession().getAttribute("userInfo");
		if (pno == null) {
			pno = 1;
		}

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("companyId", companyId);
		// 分页
		int totalRecords = this.ordersBO.countOrdersBySellId(parameters);
		
		PageBean page = new PageBean(10, pno, totalRecords);
		int startNum = page.getStartNum();
		int endNum = page.getEndNum();
		parameters.put("startNum", startNum);
		parameters.put("endNum", endNum);
		List<SubOrder> subOrderList = this.ordersBO.querySubOrderBycompanyId(parameters);
		//商品主要属性
		Items item = user.getItems();
		List<ItemAttr> itemAttrList = this.itemAttrBO.getItemAttrByItemsId(item.getId());
		if (itemAttrList != null && itemAttrList.size() > 0) {
			for (int i = 0; i < itemAttrList.size(); i++) {
				if (itemAttrList.get(i).getMainproperty() == 0) {
					itemAttrList.remove(i);
				}
			}
		}
		
		
		if (subOrderList != null && subOrderList.size() > 0) {
			for (int j = 0; j < subOrderList.size(); j++) {
				SellPool sellPool = this.sellPollBo.getSellPoolById(sellPoolId);
				List<AttrValue> attrValuesList = attrValuesBO.getAttrValuesByExtId(sellPool.getCommodityId());
				subOrderList.get(j).setAttrValues(attrValuesList);
			}
		}
		
		ModelAndView view = new ModelAndView("dealMake/orderList");
		view.addObject("subOrderList", subOrderList);
		view.addObject("totalRecords", totalRecords);// 总条数
		view.addObject("totalPage", page.getTotalPages());// 总页数
		//商品主要属性
		view.addObject("itemAttrList", itemAttrList);
		view.addObject("companyId", companyId);
		view.addObject("sellPoolId", sellPoolId);
		return view;
	}
}
