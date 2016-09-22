
package com.smm.cuohe.controller;

import com.smm.cuohe.bo.ICompanyBO;
import com.smm.cuohe.bo.IContractBO;
import com.smm.cuohe.domain.Contacter;
import com.smm.cuohe.domain.Contract;
import com.smm.cuohe.domain.ContractCommodity;
import com.smm.cuohe.domain.User;
import com.smm.cuohe.util.DateUtil;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping(value = "contract")
public class ContractController {


    @Resource
    ICompanyBO iCompanyBO;

    @Resource
    IContractBO iContractBO;

    @RequestMapping(value = "generateContractByTemplate")
    public ModelAndView generateContractByTemplate(Integer orderId,HttpServletRequest request) {
    	User user = (User) request.getSession().getAttribute("userInfo");
		//品目ID  
		String itemId = user.getItemId();
    	
        ModelAndView mv = new ModelAndView("contract/contractTemplate");
        mv.addObject("itemId", itemId);
        
        Map<String, Object> rltMap = iContractBO.getContractContentAndOrderCodeByOrderId(orderId);
        String content = (String) rltMap.get("content");
        if (content != null && !content.equals("")) {

            mv.addObject("content", content);
            mv.addObject("orderId", orderId);
            mv.addObject("orderCode", rltMap.get("orderCode"));
            mv.addObject("deliveryDate", rltMap.get("deliveryDate").toString().replace("-", ""));
            mv.addObject("ware_Province", rltMap.get("provinceName"));
            mv.addObject("delivery", rltMap.get("delivery"));
            mv.addObject("receipttype", rltMap.get("receipttype"));
            mv.addObject("payment", rltMap.get("payment"));
            mv.addObject("nowDate", DateUtil.doFormatDate(new Date(), "yyyy-MM-dd").replace("-", ""));
            //磅差
            mv.addObject("poundDiff",rltMap.get("poundDiff"));

        } else {
        	//总金额
        	Double allPrice = 0.0;
        	//总数量
        	Double allQuantity = 0.0;
            Contract contract = iContractBO.getContractByOrderId(orderId);
            mv.addObject("contract", contract);
            List<ContractCommodity> list = contract.getCommodity();
            if (list != null && list.size() > 0) {
            	for (int i = 0; i < list.size(); i++) {
            		ContractCommodity concom = list.get(i);
            		allPrice += concom.getTotalPrice();
            		allQuantity += concom.getQuantity();
				}
			}
            mv.addObject("orderId", contract.getOrderId());
            mv.addObject("orderCode", contract.getOrderCode());
            mv.addObject("allPrice", allPrice);
            mv.addObject("allQuantity", allQuantity);
            mv.addObject("deliveryDate", rltMap.get("deliveryDate").toString().replace("-", ""));
            mv.addObject("ware_Province", rltMap.get("provinceName"));
            mv.addObject("delivery", rltMap.get("delivery"));
            mv.addObject("receipttype", rltMap.get("receipttype"));
            mv.addObject("payment", rltMap.get("payment"));
            mv.addObject("nowDate", DateUtil.doFormatDate(new Date(), "yyyy-MM-dd").replace("-", ""));
            //磅差
            mv.addObject("poundDiff",rltMap.get("poundDiff"));
        }

        return mv;
    }

    /** 确认并提交合同
     * @Title: addContractWithHtml 
     * @Description: TODO
     * @param orderId
     * @param orderCode
     * @param contract
     * @return
     * @author zhangnan/张楠
     * @return: String
     * @createTime 2016年3月30日上午10:29:22
     */
    @RequestMapping(value = "addContractWithHtml")
    @ResponseBody
    public String addContractWithHtml(Integer orderId, String orderCode, String contract) {
        contract = removeEmptyCharacter(contract);

        return iContractBO.addContractWithHtml(orderId, orderCode, contract);
    }

    private String removeEmptyCharacter(String text) {

        String regEx = "[\n\t]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(text);
        return m.replaceAll("");
    }

    @RequestMapping(value = "sendContract")
    @ResponseBody
    public Map<String, Object> sendContract(Integer orderId, String sendType, HttpServletRequest request) {

        return iContractBO.sendContract(orderId, sendType, request);

    }

    @RequestMapping(value = "showBuyContacter")
    public ModelAndView showBuyContacter(Integer orderId, String sendType) {
        ModelAndView mv = new ModelAndView("contract/showBuyContacter");
        List<Contacter> contacterList = iContractBO.getBuyContactersByOrderId(orderId);
        mv.addObject("orderId", orderId);
        mv.addObject("sendType", sendType);
        mv.addObject("contacterList", contacterList);
        return mv;
    }

    @RequestMapping(value = "sendContractBySelectedContacters")
    @ResponseBody
    public String sendContractBySelectedContacters(String customerName, String phoneOrEmail, String sendType, Integer orderId, HttpServletRequest request) {

        return iContractBO.sendContractBySelectedContacters(customerName, phoneOrEmail, sendType, orderId, request);

    }

    @RequestMapping(value = "getContractUrlPage")
    public ModelAndView getContractUrlPage(String url) {
        ModelAndView mv = new ModelAndView("contract/getContractUrlPage");
        mv.addObject("url", url);
        return mv;
    }

}
