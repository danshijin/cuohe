package com.smm.cuohe.controller;

import com.smm.cuohe.bo.ICompanyBO;
import com.smm.cuohe.bo.IContacterApiBo;
import com.smm.cuohe.bo.IContacterBo;
import com.smm.cuohe.bo.dealmake.BuyPoolBo;
import com.smm.cuohe.domain.Contacter;
import com.smm.cuohe.domain.Customer;
import com.smm.cuohe.domain.PageBean;
import com.smm.cuohe.domain.User;
import com.smm.cuohe.tools.ResultMessage;
import com.smm.cuohe.tools.mail.UtilMail;
import com.smm.cuohe.util.QueryConditions;
import com.smm.cuohe.util.Texting;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 联系人
 * @author hanfeihu
 *
 */
@Controller
@RequestMapping("/contacter")
public class ContacterController {
	
	private static Logger logger = Logger.getLogger(ContacterController.class.getName());
	
	@Resource
    private IContacterBo iContacterBo;
	
	
	@Resource
    private ICompanyBO iCompanyBO;
	
	@Resource
	public BuyPoolBo buyPoolBo; 
	
	@Resource
	public IContacterApiBo iContacterApiBo; 
	
	@Resource
	public Texting texting;
	
	 @Value("#{ch['sendSMS.URL']}")
	 private String sendSMS;
	
    @RequestMapping("contacterList")
    public ModelAndView getContacterList(Integer pno,HttpServletRequest req,QueryConditions qc){
		if (pno == null) {pno = 1;}
		User user = (User) req.getSession().getAttribute("userInfo");
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("content", qc.getContent());
		parameters.put("operator", qc.getOperator());
		parameters.put("attribute", qc.getAttribute());
		parameters.put("but", req.getParameter("but"));
		parameters.put("itemId",user.getItemId());
		parameters.put("createdById", user.getId());
		int count = iContacterBo.getContacterCount(parameters);
		//分页查询
		PageBean page = new PageBean(10, pno, count);
		parameters.put("_start",page.getStartNum());
		parameters.put("_size", page.getEndNum());
		parameters.put("_orderby", "a.updatedAt");
		parameters.put("_order", "desc");
		 //${_orderby} ${_order}
		
		List<Map> list = iContacterBo.getContacterList(parameters);
        ModelAndView view=new ModelAndView("contacter/contacterList");
		view.addObject("list", list);
		view.addObject("qc", qc);
		view.addObject("totalRecords", count);// 总条数
		view.addObject("totalPage", page.getTotalPages());// 总页数
        return view;
    }
    
    @RequestMapping(value="showAllCustomerDialog")
    public ModelAndView showAllCustomerDialog(){
    	ModelAndView view=new ModelAndView("contacter/contacterCompanyList");
    	return view;
    }
    
    @RequestMapping(value="getAllCompany")
    @ResponseBody
    public Map<String, Object> getAllCompany(Integer pno,HttpServletRequest req,QueryConditions qc, Integer start, Integer len){
		if (pno == null) {pno = 1;}
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("content", qc.getContent());
		parameters.put("operator", qc.getOperator());
		parameters.put("attribute", qc.getAttribute());
		User user = (User) req.getSession().getAttribute("userInfo");
		parameters.put("userid", user.getId());
		parameters.put("itemid", user.getItemId());
		Integer count = iContacterBo.getAllCompanyCount(parameters);
		parameters.put("_start", start);
		parameters.put("_size", len);
		List<Map> list = iContacterBo.getAllCompany(parameters);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", list);
		map.put("total", count);
        return map;
	
    }
	
    
    /**
     * 添加联系人
     * @param contacter
     * @return
     */
	@RequestMapping(value="/contacterAdd",method=RequestMethod.POST)
	@ResponseBody
	public ResultMessage addContacter(Contacter contacter,HttpServletRequest request,String isUpdateMall) {

		if(contacter!=null){
			try {
				if(contacter.getName()!=null&&!contacter.getName().equals("")){
//				if(iContacterBo.selectCountByName(contacter.getName())>=1){	
//					return new ResultMessage("error","这个联系人已经存在了换个名字试试！");
//				}

					User user = (User) request.getSession().getAttribute("userInfo");
					contacter.setCreatedBy(user.getId());
			        if(isUpdateMall!=null&&isUpdateMall.equals("1")){
			        }
				
				iContacterBo.addContacter(contacter);
				}
			} catch (Exception e) {
		
				return new ResultMessage("error","未知异常");
			}
		}
		return ResultMessage.ADD_SUCCESS_RESULT;
	}
	
	
	
	
	
	
	
	/**
	 * 编辑短信
	 * @return
	 */
	@RequestMapping("/editSMM")
	public ModelAndView editSMM(String recipientMobilePhone) {
		ModelAndView view=new ModelAndView("contacter/sendSMM");

		view.addObject("recipientMobilePhone",recipientMobilePhone);
		return view;
	}
	
	
	/**
	 * 发送短信 
	 */
	@RequestMapping("sendSMS")
	@ResponseBody
	public ResultMessage sendSMS(HttpServletRequest req) {
		
		String resultMsg = texting.sendsmm(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()), req.getParameter("SMMcontent"), req.getParameter("recipientMobilePhone"));
		String smsSendResult = Texting.checkResultCode(resultMsg);
		if(smsSendResult.equals("成功")){
			return new ResultMessage(ResultMessage.SUCCESS,"短信发送成功！");
		} else {
			return new ResultMessage(ResultMessage.ERROR,smsSendResult);
		}
	}

	
	
	/**
	 * 跳转添加页面
	 * @return
	 */
	@RequestMapping("/contacterNew")
	public ModelAndView contacterNew() {
		ModelAndView view=new ModelAndView("contacter/contacterNew");
		List<Customer> companys=buyPoolBo.companylist();
		view.addObject("companylist",companys);
		return view;
	}


	
    /**
     * 保存联系人
     */
	@RequestMapping(value="/contacterSave",method=RequestMethod.POST)
	@ResponseBody
	public ResultMessage contacterSave(Contacter contacter,HttpServletRequest request,String isUpdateMall) {
		try {
				User user = (User) request.getSession().getAttribute("userInfo");
				contacter.setUpdatedBy(user.getId());
		        iContacterBo.updateContacter(contacter);
		} catch (Exception e) {
			return ResultMessage.ADD_FAIL_RESULT;
		}
		return ResultMessage.ADD_SUCCESS_RESULT;
	}
	
	/**
	 * 根据ID查看联系人详细信息
	 */
	@RequestMapping("/contacterLook")
	public ModelAndView contacterLook(Integer id) {
		ModelAndView view=new ModelAndView("contacter/contacterLook");
		Contacter contacter=iContacterBo.getContacterById(id);
		view.addObject("contacter",contacter);
		
		return view;
	}

	
	/**
	 * 跳转修改联系人页面
	 */
	@RequestMapping("/contacterModify")
	public ModelAndView contacterModify(Integer id, Integer customerId, String companyName) {
		if(id==null){
			ModelAndView view=new ModelAndView("contacter/contacterNew");
			List<Customer> companys=buyPoolBo.companylist();
			view.addObject("companylist",companys);
			view.addObject("customerId", customerId);
			view.addObject("companyName", companyName);
			return view;
		}
		ModelAndView view=new ModelAndView("contacter/contacterModify");
		Contacter contacter=iContacterBo.getContacterById(id);
		view.addObject("contacter",contacter);
		List<Customer> companys=buyPoolBo.companylist();
		view.addObject("companylist",companys);
		return view;
	}
	
	
	/**
	 * 移除联系人
	 */
	@RequestMapping(value="/contacterRemove")
	@ResponseBody
	public Map<String, Object> removeContacter(String ids) {
		Map<String,Object> map = new HashMap<String, Object>();
		
		try {
			iContacterBo.removeContacter(ids.split(","));	
			map.put("code", "succ");	
			map.put("msg", "系统提示，操作成功");	
		} catch (Exception e) {
			// TODO: handle exception
			map.put("code", "error");
			map.put("msg", "系统提示，操作失败！");	
		}
	
		return map;
	}
	

	/**
	 * 编辑邮件
	 * @return
	 */
	@RequestMapping("/editMail")
	public ModelAndView editMail() {
		ModelAndView view=new ModelAndView("contacter/editMail");

		return view;
	}
	
	/**
	 * 发送邮件
	 */
	@RequestMapping("tomail")
	@ResponseBody
	public ResultMessage toMail(HttpServletRequest req) {
		String[] ids= req.getParameter("ids").split(",");
		for (int i = 0; i < ids.length; i++) {
			Contacter c=iContacterBo.getContacterById(Integer.parseInt(ids[i]));
			UtilMail se = new UtilMail(false);
			se.doSendHtmlEmail(req.getParameter("mailTitle"), req.getParameter("mailContent"), c.getEmail());
		}
		return new ResultMessage("success","发送邮件成功！");
	}



}
