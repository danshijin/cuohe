package com.smm.cuohe.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.smm.cuohe.bo.IAreasBO;
import com.smm.cuohe.bo.IOrdersBO;
import com.smm.cuohe.bo.dealmake.BuyPoolBo;
import com.smm.cuohe.bo.dealmake.IBuyOrderBO;
import com.smm.cuohe.bo.users.UserInfoBO;
import com.smm.cuohe.domain.Company;
import com.smm.cuohe.domain.Contract;
import com.smm.cuohe.domain.Customer;
import com.smm.cuohe.domain.ItemAttr;
import com.smm.cuohe.domain.Items;
import com.smm.cuohe.domain.Order;
import com.smm.cuohe.domain.PageBean;
import com.smm.cuohe.domain.SubOrder;
import com.smm.cuohe.domain.User;
import com.smm.cuohe.tools.ResultMessage;
import com.smm.cuohe.util.QueryConditions;
import com.smm.cuohe.util.StringUtil;


/**
 * 订单管理
 * @author hanfeihu
 *
 */
@Controller
@RequestMapping("/order")
public class OrderController {
	
	
	@Resource
    private IOrdersBO iOrdersBO;
	@Resource
	private UserInfoBO iUserInfoBoImpl;
	
	@Resource
	BuyPoolBo buyPoolBo;
	@Resource
	IAreasBO iAreasBO;
	@Resource
	IBuyOrderBO ibuyOrderBo;

	
	/**
	 * 订单列表加搜索功能
	 * @param page
	 * @param req
	 * @param qc
	 * @return
	 */
	
    @RequestMapping("ordersList")
    public ModelAndView ordersList(Integer pno,HttpServletRequest req,QueryConditions qc){
		Map<String, Object> parameters = new HashMap<String, Object>();
		if(qc!=null){
			if(qc.getContent()!=null){
				qc.setContent(StringUtil.doDecoder(qc.getContent()));
			}
			if(qc.getAttribute()!=null&&!qc.getAttribute().equals("-1")){
				parameters.put("content", qc.getContent());
    			parameters.put("operator", qc.getOperator());
    			parameters.put("attribute", qc.getAttribute());
			}
		}
		User user = (User) req.getSession().getAttribute("userInfo");
		
		if(user!=null){
			parameters.put("itemid", user.getItemId());
			parameters.put("createdById", user.getId());
		}else{
			return new ModelAndView("/login");
		}
		int count;
		count = iOrdersBO.getOrderCount(parameters);
		
		//分页查询
		if (pno == null) {
			pno = 1;
		}
		PageBean page = new PageBean(10, pno, count);
		parameters.put("_start", page.getStartNum());
		parameters.put("_size", page.getEndNum());
		parameters.put("_orderby", "b.updatedAt");
		parameters.put("_order", "asc");
		
		List<Order> list= iOrdersBO.getOrderList(parameters);
		
		List<ItemAttr> attrs = ibuyOrderBo.getMainItemAttr(Integer.parseInt(user.getItemId()));
        ModelAndView view=new ModelAndView("orders/ordersList");
        view.addObject("list", list);
		view.addObject("totalRecords", count);// 总条数
		view.addObject("attrs", attrs);
		view.addObject("totalPage", page.getTotalPages());// 总页数
		view.addObject("qc",qc);
        return view;
    }
    
    
    

    /**
     * 订单的编辑
     */
    @RequestMapping(value="orderSave",method=RequestMethod.POST)
    @ResponseBody
    public  ResultMessage orderSave(Order order,HttpServletRequest req){
    	try {	
    		User user = (User) req.getSession().getAttribute("userInfo");
    		String[] subIds = req.getParameterValues("subId");
    		String[] units = req.getParameterValues("unit");
    		String[] quantitys = req.getParameterValues("quantity");
    		String[] prices = req.getParameterValues("price");
    		String  orderId = req.getParameter("orderId");
    		SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");
    		String deliveryDateStr= req.getParameter("deliveryDateStr");
    		order.setDeliveryDate(sdf.parse(deliveryDateStr));
    		order.setUpdatedBy(user.getId());
    		order.setId(Integer.parseInt(orderId));
    		for(int i=0;i<subIds.length;i++){
    			SubOrder subOrder = new SubOrder();
    			subOrder.setUnit(Integer.parseInt(units[i]));
    			subOrder.setQuantity(Double.parseDouble(quantitys[i]));
    			subOrder.setPrice(Double.parseDouble(prices[i]));
    			subOrder.setUpdatedBy(user.getId());
    			subOrder.setId(Integer.parseInt(subIds[i]));    			
    			iOrdersBO.updateSubOrders(subOrder);
    		}
    		iOrdersBO.orderEditor(order);
		} catch (Exception e) {
			return ResultMessage.UPDATE_FAIL_RESULT;
		}
		return ResultMessage.UPDATE_SUCCESS_RESULT;
    }
    
    @RequestMapping("getOrderById")
    public  ModelAndView getOrderById(Integer id){
    	
      ModelAndView view=new ModelAndView("orders/ordersEditor");
    	try {
    		Order order=iOrdersBO.getOrderById(id);
    		view.addObject(order);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("getOrderById获取订单ID错误！");
			return view;
		}
		return view;
    }
    
    /**
     * 上传合同
     * @param request
     * @return
     */
    @RequestMapping("contacterUpload")
    @ResponseBody
    public String contacterUpload(HttpServletRequest request,Order order){
    	 MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;     
    	 String rltMsg = "";
         /**构建图片保存的目录**/    
         //+ dateformat.format(new Date())
         String logoPathDir = "/files/contractFiles";     
         /**得到图片保存目录的真实路径**/    
         String rootPath = System.getProperty("smm.cuohe");
         /**根据真实路径创建目录**/    
         File logoSaveFile = new File(rootPath + logoPathDir);
         if(!logoSaveFile.exists()){
        	 logoSaveFile.mkdir();  
         }     
         
         /**页面控件的文件流**/    
         MultipartFile multipartFile = multipartRequest.getFile("fileData");      
         /**获取文件的后缀**/   

         String suffix= new Date().getTime() + "___" +multipartFile.getOriginalFilename();

         /**拼成完整的文件保存路径加文件**/    
         String fileName = logoPathDir + "/"   + suffix;
         File file = new File(rootPath + fileName);    
         
         if(iOrdersBO.ifExistContract(order.getId()) > 0){
        	 
        	 iOrdersBO.updateContractFilePath(order.getId(), fileName); //数据库中存入的是合同文件的相对路径，方便项目的移植
        	 
         } else {
        	 
        	 iOrdersBO.addContractByFile(order.getId(), fileName);
        	 
         }
         try {     
             multipartFile.transferTo(file);
             rltMsg = "合同上传成功";
         } catch (Exception ex) {
        	 rltMsg = "合同上传失败，失败原因：" + ex.getMessage();
		}   
         return rltMsg;
      }

    /**
     * 更新
     * @param request
     * @return
     */
    @RequestMapping("updateOrderStatus")
    public  ModelAndView updateOrderStatus(String id,HttpServletRequest req,String  status){
    	Map<String, Object> map=new HashMap<String, Object>();
    	map.put("id",id);
    	map.put("status",status);
    	
    	try {
    		iOrdersBO.updateOrderStatus(map);
		} catch (Exception e) {
			System.out.println("更新状态失败");
		}
    	
    	return  new ModelAndView("redirect:/order/ordersList.do");
    }
    

    /**
     * 订单查看
     * @param request
     * @return
     */
    @RequestMapping("ordersLook")
    public  ModelAndView ordersLook(Integer id){
    	 ModelAndView view=new ModelAndView("orders/ordersLook");
    	try {
    
    		List<SubOrder> subOrders=iOrdersBO.getSubOrdersByOrderId(id);
    		List<Map<String, Object>> map=iOrdersBO.getOrderDetailByOrderId(id);
    		view.addObject("map",map);
    		
       		view.addObject("subOrders",subOrders);
		} catch (Exception e) {
			System.out.println("订单查看获取数据失败");
		}
    	return view;
    }
    
   /**
    * 准备合同下载
    * @param request
    * @param response
    * @param orderId
    * @return
    */
    @RequestMapping("tocontacterDownload")
    public ModelAndView tocontacterDownload(HttpServletRequest request,HttpServletResponse response,String orderId){
    	ModelAndView view=new ModelAndView("contract/viewContract");
    	Order order=iOrdersBO.getOrderById(Integer.parseInt(orderId));
    	String   contract=order.getContract().getFilePath();
    	String[] arry=null;
    	List<Contract> list=new ArrayList<>();
   	 	if(contract!=null&&contract.length()>0){ 
   		  arry=contract.split(";"); 
   		 
   	 	}
   	 	if(arry!=null&&arry.length>0){
   	 	
   	 		for (int i = 0; i < arry.length; i++) {
   	 			Contract con=new Contract();
				String filePath=arry[i];
				con.setFileName(filePath.split("___")[1]);
				con.setFilePath(filePath);
				list.add(con);
			}
   	 	}
   	 	view.addObject("list", list);
    	return view;
    }
    
    
    /**
     * 下载
     * @param request
     * @return
     * @throws IOException 
     */
    @RequestMapping("contacterDownload")
    public  void  contacterDownload(HttpServletRequest request,HttpServletResponse response,String filePath) throws IOException{
        //1.根据传入id获取对应数据
    	 //Order order=iOrdersBO.getOrderById(Integer.parseInt(orderId));
    	 String rootPath = System.getProperty("smm.cuohe");
    	 if(filePath!=null){
    	 File file = new File(rootPath + StringUtil.doDecoder(filePath));
    	 FileInputStream is = new FileInputStream(file);
          // 设置response参数，可以打开下载页面
    	 String fileaNama=file.getName().split("___")[1];
          response.reset();
          response.setContentType("application/vnd.ms-excel;charset=utf-8");
          response.setHeader("Content-Disposition", "attachment;filename="+ new String((fileaNama).getBytes("GBK"), "iso-8859-1"));
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
    
}
    
    
   

   

}
