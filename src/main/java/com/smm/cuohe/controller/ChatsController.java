package com.smm.cuohe.controller;
import com.smm.cuohe.bo.IChatsBO;
import com.smm.cuohe.bo.ICustomerBO;
import com.smm.cuohe.bo.IDealMakerConversationBO;
import com.smm.cuohe.domain.ChatsExport;
import com.smm.cuohe.domain.Customer;
import com.smm.cuohe.domain.PageBean;
import com.smm.cuohe.domain.User;
import com.smm.cuohe.util.ConvertUtil;
import com.smm.cuohe.util.ExportUtil;
import com.smm.cuohe.util.QueryConditions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 洽谈
 * @author hanfeihu
 *
 */
@Controller
@RequestMapping("/chats")
public class ChatsController {
	
	
	@Resource
    private IChatsBO iChatsBO;
	
	@Resource
	private IDealMakerConversationBO iDealMakerConversationBO;
	
	@Resource
	private ICustomerBO iCustomerBO; 

    @RequestMapping("chatsList")
    public ModelAndView getChatsList(Integer pno,HttpServletRequest req,QueryConditions qc){
    			Map<String, Object> parameters = new HashMap<String, Object>();
    			if(qc!=null){
    				
	    			parameters.put("content", qc.getContent());
	    			parameters.put("operator", qc.getOperator());
	    			parameters.put("attribute", qc.getAttribute());
    			}
    			
    			parameters.put("viewType", req.getParameter("viewType"));
    			User user = (User) req.getSession().getAttribute("userInfo");
    			parameters.put("userid",user.getId());
    			parameters.put("itemId",user.getItemId());
   
    			int count;
    			count = iChatsBO.getChatsCount(parameters);
    			//分页查询
    			if (pno == null) {
    				pno = 1;
    			}
    			PageBean page = new PageBean(10, pno, count);
    			parameters.put("_start", page.getStartNum());
    			parameters.put("_size", page.getEndNum());
    			List<Map<String, Object>> list = iChatsBO.getChatsList(parameters);
    			for(Map<String, Object> map:list){
    				if(map.get("customerName").toString().equals("")){
    					map.put("inPool", "否");
    				}else{
    					Customer cust=new Customer();
    					cust.setAccount(map.get("customerName").toString());
    					cust.setItemId(Integer.parseInt(map.get("itemId").toString()));
    					List<Customer> listCust=iCustomerBO.getCustomerByAccountAndItemId(cust);
    					if(listCust!=null){
    						if(listCust.size()>0){
    							map.put("inPool", "是");
    						}else{
    							map.put("inPool", "否");
    						}
    					}
    				}
    			}
    			iChatsBO.updateOrderStatus(Integer.parseInt(user.getItemId()));
    			
		        ModelAndView view=new ModelAndView("chats/chatsList");
		        view.addObject("list", list);
				view.addObject("totalRecords", count);// 总条数
				view.addObject("totalPage", page.getTotalPages());// 总页数
				view.addObject("qc", qc);// 总页数
				view.addObject("viewType", req.getParameter("viewType"));
		        return view;
    }
	
    /**
     * 回收站
     * @param ids 
     * @param req
     * @return 
     */
    @RequestMapping("removeChats")
	@ResponseBody
    public Map<String, Object> removeChats(String ids,HttpServletRequest req){
    	Map<String,Object> map = new HashMap<String, Object>();
    	try {
    		iChatsBO.removeChats(ids.split(","));
			map.put("code", "succ");	
			map.put("msg", "系统提示，操作成功");	
		} catch (Exception e) {
			map.put("code", "error");
			map.put("msg", "系统提示，操作失败！");	
		}
    	
    	return map;
    }
    
    
    
    /**
     *继续洽谈
     * @param ids
     * @param req
     * @return
     */
    @RequestMapping("continueNegotiate")
	@ResponseBody
    public Map<String, Object> continueNegotiate(Integer id,HttpServletRequest req){


 
    	Map<String, String> chats=	 iChatsBO.getChatsById(id);

    	Integer employeeId=null;
    	if(chats.get("employeeid")!=null){
    		  Object employeeIdObj=chats.get("employeeid");
    		  employeeId=Integer.parseInt(employeeIdObj.toString());
    	}
    	
    	
    	Map<String, Object> map= iDealMakerConversationBO.addWakeUpRecord(employeeId, id);
    	return map;
    }
    
    
    
     
    /**
     * 从表删除数据
     * @param ids
     * @param req
     * @return
     */
    @RequestMapping("delChats")
	@ResponseBody
    public Map<String, String> delChats(String ids,HttpServletRequest req){
    	Map<String,String> map = new HashMap<String, String>();
    	try {
    		iChatsBO.delChats(ids.split(","));
			map.put("code", "succ");	
			map.put("msg", "系统提示，操作成功");	
		} catch (Exception e) {
			// TODO: handle exception
			map.put("code", "error");
			map.put("msg", "系统提示，操作失败！");	
		}
    	return map;
    }
    
    
    
    @RequestMapping("chatsLook")
    public ModelAndView chatsLook(Integer id,HttpServletRequest req){


    	Map<String,String>  chats=	 iChatsBO.getChatsById(id);
    	if(chats.get("customerName") == null) {
    		chats.put("inPool", "否");
    	} else{
    		int count = iChatsBO.countByMallAccount((String)chats.get("customerName"));
    		if(count > 0){
    			chats.put("inPool", "是");
    		} else {
    			chats.put("inPool", "否");
    		}
    	}
    	
        ModelAndView view=new ModelAndView("chats/chatsLook");
    	view.addObject("chats",chats);
		return view;

    }
    
    /**
     * 洽谈导出
     * @param req
     * @param response
     * @throws UnsupportedEncodingException
     * @throws IOException
     */
    @RequestMapping("chatsExport")
	public void chatsExport (HttpServletRequest req,HttpServletResponse response) throws UnsupportedEncodingException, IOException
	{


		 
		 
		Map<String, Object> parameters = new HashMap<String, Object>();

		parameters.put("ids", req.getParameter("ids"));
		parameters.put("viewType", req.getParameter("viewType"));
		parameters.put("content", req.getParameter("content"));
		parameters.put("operator", req.getParameter("operator"));
		parameters.put("attribute", req.getParameter("attribute"));
		User user = (User) req.getSession().getAttribute("userInfo");
		parameters.put("userid",user.getId());
		parameters.put("itemId",user.getItemId());
		

		List<Map<String, Object>> list = iChatsBO.getChatsList(parameters);
		
		
		
		List<ChatsExport> exportList=new ArrayList<ChatsExport>();
		for (int i = 0; i < list.size(); i++) {
			ChatsExport ce=new ChatsExport();
			ce=(ChatsExport) ConvertUtil.mapToBean(list.get(i), ce);
			exportList.add(ce);
		}
		
		ExportUtil<ChatsExport> excel=new ExportUtil<ChatsExport>();
		String fileName="洽谈信息";
		String[] headerNames=new String[]{"洽谈编号","企业","用户名","客户池","IP/地区","沟通次数","最后沟通时间","最后沟通人","状态"};
		String[] header=new String[]{"id","companyName","customerName","inPool","ip","chatTimes","updatedAt","name","status"};
		String[] comments=new String[]{null,null,null,null,null,null,null,null,null,null,null};
		ByteArrayOutputStream os = new ByteArrayOutputStream();
        excel.export("洽谈信息", headerNames, header, comments, exportList, os, "");
        byte[] content = os.toByteArray();
        InputStream is = new ByteArrayInputStream(content);
        // 设置response参数，可以打开下载页面
        response.reset();
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename="+ new String((fileName + ".xls").getBytes("GBK"), "iso-8859-1"));
        ServletOutputStream out = response.getOutputStream();
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            bis = new BufferedInputStream(is);
            bos = new BufferedOutputStream(out);
            byte[] buff = new byte[2048];
            int bytesRead;
            // Simple read/write loop.
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
        } catch (final IOException e) {
            throw e;
        } finally {
            if (bis != null)
                bis.close();
            if (bos != null)
                bos.close();
        }
        
	}
    
    @RequestMapping(value="getAllRecordView")
    public ModelAndView getAllRecordView(Integer chatId){
    	
    	return new ModelAndView("chats/recordList").addObject("chatId", chatId);
    }
    
    @RequestMapping(value="getAllRecord")
    @ResponseBody
    public Map<String, Object> getAllRecord(Integer chatId, Integer start, Integer len){
    	
    	return iChatsBO.getAllRecord(chatId, start, len);
    }
    
}
