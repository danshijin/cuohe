package com.smm.cuohe.controller.counts;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.smm.cuohe.bo.counts.TrialcountBo;
import com.smm.cuohe.controller.BaseController;
import com.smm.cuohe.domain.PageParameter;
import com.smm.cuohe.domain.base.ChUsersEntity;
import com.smm.cuohe.domain.counts.ChatCount;
import com.smm.cuohe.util.ExportExcel;
@Controller
@RequestMapping(value="/chatCount/")
public class ChatCountController extends BaseController {
	@Autowired
	private TrialcountBo chatDao;
	
	@RequestMapping(value="index")
	public ModelAndView index(){
		ModelAndView mav = new ModelAndView("counts/chatCount");
		String currentPage = getRequestParam("pno");
		String startDate = getRequestParam("startDate");
		String endDate = getRequestParam("endDate");
		PageParameter page = new PageParameter(currentPage!=null?Integer.parseInt(currentPage):1, 10);
	    ChatCount chatCount = new ChatCount();
	    chatCount.setParameter(page);
	    if(startDate!=null){
	    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    	 try {
				chatCount.setStartDate(sdf.parse(startDate));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	    if(endDate!=null){
	    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    	 try {
				chatCount.setEndDate(sdf.parse(endDate));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
		List<ChatCount> lists =  chatDao.chatCountManage(chatCount);
		mav.addObject("lists", lists);
		mav.addObject("totalPage", chatCount.getParameter().getTotalPage());//总条数
		mav.addObject("totalRecords", chatCount.getParameter().getTotalCount());//总页数
		mav.addObject("startDate",startDate);
		mav.addObject("endDate", endDate);
		return mav;
	}
	
	@RequestMapping("download")
	public String exportExcel(){
		String startDate = getRequestParam("startDate");
		String endDate = getRequestParam("endDate");
		String fileName = "";
		ChatCount chatCount = new ChatCount();
		if(startDate!=null&&!"".equals(startDate)){
	    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    	 try {
				chatCount.setStartDate(sdf.parse(startDate));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	    if(endDate!=null&&!"".equals(endDate)){
	    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    	 try {
				chatCount.setEndDate(sdf.parse(endDate));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	    if(startDate!=null&&!"".equals(startDate)){
	    	fileName+="撮合报表从"+startDate;
	    }else{
	    	fileName+="撮合报表从最开始";
	    }
	    if(endDate!=null&&!"".equals(endDate)){
	    	fileName+="至"+endDate;
	    }else{
	    	fileName+="至今";
	    }
	   
	    String[] rowsName = new String[]{"序号","撮合员","客户端在线时长","收到会话请求(次)","有效沟通次数","生成用户数","生成订单数","订单总金额(元)"};
	    List<Object[]> datalist = chatDao.chatExportExcel(chatCount,rowsName);
	    ExportExcel excel = new ExportExcel(fileName, rowsName, datalist,getResponse());
	    try {
			excel.export();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	@RequestMapping("onlineCount")
	public ModelAndView onlineCount(){
		ModelAndView mav = new ModelAndView("counts/onlineCount");
		String currentPage = getRequestParam("pno");
		PageParameter page = new PageParameter(currentPage!=null?Integer.parseInt(currentPage):1, 10);
	    ChatCount chatCount = new ChatCount();
	    chatCount.setParameter(page);
		List<ChUsersEntity> lists = chatDao.onlineCount(chatCount);
		mav.addObject("lists", lists);
		mav.addObject("totalPage", chatCount.getParameter().getTotalPage());//总条数
		mav.addObject("totalRecords", chatCount.getParameter().getTotalCount());//总页数
		return mav;
	}
	
	

}
