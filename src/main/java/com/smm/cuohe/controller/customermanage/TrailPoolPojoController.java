package com.smm.cuohe.controller.customermanage;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.smm.cuohe.bo.IAreasBO;
import com.smm.cuohe.bo.ICategoryBO;
import com.smm.cuohe.bo.ICompanyApiBo;
import com.smm.cuohe.bo.ICompanyBO;
import com.smm.cuohe.bo.IContacterBo;
import com.smm.cuohe.bo.ICustomerBO;
import com.smm.cuohe.bo.IUserBO;
import com.smm.cuohe.bo.PubQueryBO;
import com.smm.cuohe.bo.customermanage.ITrailPoolPojoBO;
import com.smm.cuohe.bo.impl.users.SellPoolPBoImpl;
import com.smm.cuohe.bo.users.UserInfoBO;
import com.smm.cuohe.domain.Areas;
import com.smm.cuohe.domain.Category;
import com.smm.cuohe.domain.Company;
import com.smm.cuohe.domain.Customer;
import com.smm.cuohe.domain.PageBean;
import com.smm.cuohe.domain.TrailContract;
import com.smm.cuohe.domain.TrailPoolPOJO;
import com.smm.cuohe.domain.User;
import com.smm.cuohe.tools.ResultMessage;
import com.smm.cuohe.util.ExportUtil;
import com.smm.cuohe.util.StringUtil;

import net.sf.json.JSONArray;


@Controller
@RequestMapping("/TrailPoolPojo")
public class TrailPoolPojoController {
    private Logger logger = Logger.getLogger(SellPoolPBoImpl.class);

    @Resource
    private ITrailPoolPojoBO iTrailPool;
    @Resource
    private UserInfoBO iUserBo;
    @Resource
    private IAreasBO iAreasBo;
    @Resource
    private UserInfoBO iUserInfoBoImpl;
    @Resource
    private IContacterBo iContractBo;
    @Resource
    private ICompanyBO iCompanyBo;

    @Resource
    ICategoryBO iCategoryBO;

    @Resource
    private ICompanyApiBo icompanyApiBo;
   

    @Resource
    private IUserBO iuserBO;
    @Resource
    private ICustomerBO iCustomerBO;
    @Resource  PubQueryBO pubQueryBO;
    
    /**
     * 获取线索池
     * @param type
     * @param tpoolOne
     * @param tpoolTwo
     * @param tpoolThree
     * @param pno
     * @param request
     * @return
     */
    @RequestMapping("getAllList")
    public ModelAndView all(String type, String tpoolOne, String tpoolTwo, String tpoolThree, Integer pno, HttpServletRequest request) {
        if (pno == null) {
            pno = 1;
        }
        int totalRecords = 0;
        ModelAndView view = new ModelAndView("home/custom");
        Map<String, Object> map = new HashMap<String, Object>();
        //需要 type userID(登录用户的ID) itemId(登录用户的品目ID)
        User user = (User) request.getSession().getAttribute("userInfo");
       int userID = -1;
        int itemId = -1;
        if (user != null) {
           userID = user.getId();
         itemId = Integer.parseInt(user.getItemId());
       }
        map.put("userID", userID);
        map.put("itemId", itemId);
        String tpoolThree2 = "";

        if (tpoolThree!=null&&!"".equals(tpoolThree)) {

            tpoolThree2 = StringUtil.doDecoder(tpoolThree);
        }
        List<TrailPoolPOJO> countlist = iTrailPool.getTrailPoolList(type, tpoolOne, tpoolTwo, tpoolThree2, map);

        if (countlist != null && !countlist.isEmpty()) {
            totalRecords = countlist.size();
        }
        //分页
        PageBean page = new PageBean(10, pno, totalRecords);
        int startNum = page.getStartNum();
        int endNum = page.getEndNum();
        map.put("startNum", startNum);
        map.put("endNum", endNum);

        List<TrailPoolPOJO> tlist = iTrailPool.getTrailPoolList(type, tpoolOne, tpoolTwo, tpoolThree2, map);
        view.addObject("tlist", tlist);
        view.addObject("totalRecords", totalRecords);//总条数
        view.addObject("totalPage", page.getTotalPages());//总页数
        if (StringUtils.isBlank(type)) {
            type = "0";
        }
        view.addObject("typeVal", type);
        view.addObject("tpoolOne", tpoolOne);
        view.addObject("tpoolTwo", tpoolTwo);
        view.addObject("tpoolThree", tpoolThree2);
        return view;
    }

    /**
     * 批量删除线索
     * @param ids
     * @return
     */
    @RequestMapping("updateList")
    @ResponseBody
    public Map<String, Object> updateList(String ids) {
        Map<String, Object> map = new HashMap<String, Object>();
        map = iTrailPool.updateList(ids);
        return map;
    }

    @RequestMapping("selectTrailPool")
    public ModelAndView selectTrailPool(HttpServletRequest request) {
    	 ModelAndView view=updateTrailPool(request);
    	 view.setViewName("trailPool/trailPoolquery");
        try {
            
        	view.addObject("OnlySelect", "0");
            return view;
        } catch (Exception exec) {
            return view;
        }
    }
		/**
		 * 准备添加线索
		 * @param request
		 * @return
		 */
	   @RequestMapping("addTrailPool")
	    public ModelAndView addTrailPool(HttpServletRequest request) {
	        ModelAndView view = new ModelAndView("trailPool/trailPoolAdd");
	        User user = (User) request.getSession().getAttribute("userInfo"); //获取当前登录用户
	        if (user != null) {
	            List<User> uList = iUserBo.getAccount();
	            List<User> userList = new ArrayList<User>();
	            for (int i = 0; i < uList.size(); i++) {
	                if (uList.get(i).getItemId().equals(user.getItemId()))
	                    userList.add(uList.get(i));
	            }
	            view.addObject("OnlyAdd", "0");
	            view.addObject("userList", userList);
	            Map<String, List<Category>> listCategory = iCategoryBO.getCategoryByModuleName();
	            view.addObject("laiyuanList", listCategory.get("线索来源"));
	            //view.addObject("itemList",iUserInfoBoImpl.getItems());
	            view.addObject("empleeyCList", listCategory.get("员工人数"));
	            view.addObject("yearSalesList", listCategory.get("年销售额"));
	            view.addObject("companyPropertyList", listCategory.get("企业性质"));
	            view.addObject("entTypesList", listCategory.get("企业类型"));
	            List<Areas> areaList = iAreasBo.getParentAreas();
	            view.addObject("parentArea", areaList);
	            if (areaList.size() > 0) {
	                List<Areas> childAreas = iAreasBo.getChildArea(areaList.get(0).getId().toString());
	                if (childAreas.size() > 0)
	                    view.addObject("childArea", iAreasBo.getChildArea(areaList.get(0).getId().toString()));
	            }
	        }
	        return view;
	    }

	   
	   /**
	     * 添加线索
	     * @param trailPool
	     * @param request
	     * @return
	     * @throws Exception
	     */
	    @RequestMapping(value = "trailPoolSave",method = RequestMethod.POST)
	    @ResponseBody	    
	    public ResultMessage trailPoolSave(TrailPoolPOJO trailPool, HttpServletRequest request)  {
	        try {
	        	
	        
	        	
	            User loginUser = (User) request.getSession().getAttribute("userInfo");
	            if (trailPool != null && loginUser != null) {
	              String[] entTypess=	request.getParameterValues("entTypes");
	              String entTypes=null;
			              if(entTypess!=null && entTypess.length>0){
			            	  for (int i = 0; i < entTypess.length; i++) {
			            		  entTypes=entTypes+entTypess[i]+",";
							}
			            	if(entTypes!=null){
			            	  entTypes=entTypes.substring(0, entTypes.length()-1);
			            	}
			              }
	            			trailPool.setItemId(loginUser.getItems().getId());
	            			trailPool.setCreatedAt(new Date());
	            			trailPool.setCreatedBy(loginUser.getId());
	            			trailPool.setUpdatedAt(new Date());
	            			trailPool.setUpdatedBy(loginUser.getId());
	            			iTrailPool.addTrailPoolAdd(trailPool);
	            			Integer id=trailPool.getId();
	            			System.out.println(id);

	                return ResultMessage.ADD_SUCCESS_RESULT;
	            } 
	        } catch (Exception ex) {
	        	ex.printStackTrace();
	           
	        }
	        return ResultMessage.ADD_FAIL_RESULT;
	    }

	   
	   
	   
	 /**
	  * 准备修改线索  
	  * @param request
	  * @return
	  */
    @RequestMapping("updateTrailPool")
    public ModelAndView updateTrailPool(HttpServletRequest request) {
        ModelAndView view = new ModelAndView("trailPool/trailPoolAdd");
        User user = (User) request.getSession().getAttribute("userInfo"); //获取当前登录用户
        if (user != null) {
         
            
            //Integer cityId = 0;
            if (request.getParameter("id") != null && request.getParameter("id") != "") {
                //加载线索池
            	TrailPoolPOJO comObj = iTrailPool.getTrailPoolById(request.getParameter("id"));
            	comObj.setEntTypesName(comObj.getEntTypes());
                if (comObj != null) {             
                    String temp = "";
                    if (comObj.getEntTypes() != null && comObj.getEntTypes() != "") {
                        if (!comObj.getEntTypes().startsWith(",")) temp += "," + comObj.getEntTypes();
                        if (!temp.endsWith(",")) temp += comObj.getEntTypes() + ",";
                        comObj.setEntTypes(temp);
                    }
                    if (comObj != null) view.addObject("comObj", comObj);
                    List<TrailContract> contList = iTrailPool.gettrailcontractBytraiId(comObj.getId());
	                   if(contList!=null&&contList.size()>0){
	                	   for (int i = 0; i < contList.size(); i++) {
	                		   TrailContract con=contList.get(i);
	                		   if(con.getFirst()==0){
	                			   comObj.setConId(con.getId());
	                			   comObj.setContacterName(con.getContactName());
	                			   comObj.setEmail(con.getEmail());
	                			   comObj.setSex(con.getSex());
	                			   comObj.setQq(con.getQqNum());
	                			   comObj.setMobilePhone(con.getMobilePhone());
	                			   comObj.setPosition(con.getPositions());
	                		   }else {
	                			   comObj.setConId2(con.getId());
	                			   comObj.setContacterName2(con.getContactName());
	                			   comObj.setEmail2(con.getEmail());
	                			   comObj.setSex2(con.getSex());
	                			   comObj.setQq2(con.getQqNum());
	                			   comObj.setMobilePhone2(con.getMobilePhone());
	                			   comObj.setPosition2(con.getPositions());
							}
							
						}
	                	   
	                	   
	                   }
                }
                view.addObject("comObj", comObj);
                List<User> uList = iUserBo.getAccount();
                List<User> userList = new ArrayList<User>();
                for (int i = 0; i < uList.size(); i++) {
                    if (uList.get(i).getItemId().equals(user.getItemId()))
                        userList.add(uList.get(i));
                }
                view.addObject("userList", userList);
                Map<String, List<Category>> listCategory = iCategoryBO.getCategoryByModuleName();
                view.addObject("laiyuanList", listCategory.get("线索来源"));
                //view.addObject("itemList",iUserInfoBoImpl.getItems());
                view.addObject("empleeyCList", listCategory.get("员工人数"));
                view.addObject("yearSalesList", listCategory.get("年销售额"));
                view.addObject("companyPropertyList", listCategory.get("企业性质"));
                view.addObject("entTypesList", listCategory.get("企业类型"));
                List<Areas> areaList = iAreasBo.getParentAreas();
                view.addObject("parentArea", areaList);
                if (areaList.size() > 0) {
                    List<Areas> childAreas = iAreasBo.getChildArea(String.valueOf(comObj.getAreaId()));
                    if (childAreas.size() > 0)
                        view.addObject("childArea", childAreas);
                }
            } else{
            	 return view;
            }
           
        }
        
        return view;
    }
    
	  /**
	   * 修改线索
	   * @param trailPool
	   * @param request
	   * @return
	   * @throws Exception
	   */
    @RequestMapping(value = "trailPoolupdate", method = RequestMethod.POST)
    @ResponseBody
    @Transactional(rollbackFor = Exception.class)
    public ResultMessage trailPoolupdate(TrailPoolPOJO trailPool, HttpServletRequest request) throws Exception {
        try {
        	
        	
        	
            User loginUser = (User) request.getSession().getAttribute("userInfo");
            if (trailPool != null && loginUser != null) {
              String[] entTypess=	request.getParameterValues("entTypes");
              String entTypes=null;
		              if(entTypess!=null && entTypess.length>0){
		            	  for (int i = 0; i < entTypess.length; i++) {
		            		  entTypes=entTypes+entTypess[i]+",";
						}
		            	if(entTypes!=null){
		            	  entTypes=entTypes.substring(0, entTypes.length()-1);
		            	}
		              }
            			trailPool.setItemId(loginUser.getItems().getId());            	
            			trailPool.setUpdatedAt(new Date());
            			trailPool.setUpdatedBy(loginUser.getId());
            			iTrailPool.updateTrail(trailPool);
            			Integer id=trailPool.getId();
            			System.out.println(id);

                return ResultMessage.ADD_SUCCESS_RESULT;
            } else {
            	return ResultMessage.ADD_FAIL_RESULT;
            	}
        } catch (Exception ex) {
            throw ex;
        }
    }
    
    
    //转换页面
    @RequestMapping(value = "trailTocustomer")
    public ModelAndView mvNew(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("userInfo");
        ModelAndView mv = null;
        mv = new ModelAndView("trailPool/traulTocustom");
        if (user != null) {
            List<Category> list = null;
            mv.addObject("userId",  user.getId());
            mv.addObject("parentAreas", iAreasBo.getParentAreas());
            mv.addObject("pics", iuserBO.getUsersByItemId(user.getItemId()));
            mv.addObject("categorySource", iCategoryBO.getByModId(7));
            list = iCategoryBO.getByModId(5);
            mv.addObject("level", list);
            mv.addObject("levelString", JSONArray.fromObject(list).toString());
            mv.addObject("entTypes", iCategoryBO.getByModId(1));
            mv.addObject("salesvolume", iCategoryBO.getByModId(2));
            mv.addObject("categoryemployee", iCategoryBO.getByModId(3));
            mv.addObject("categoryfreq", iCategoryBO.getByModId(8));
            mv.addObject("categorybusiness", iCategoryBO.getByModId(4));
            mv.addObject("companyproperty", iCategoryBO.getByModId(6));
            mv.addObject("categoryCredit", iCategoryBO.getByModId(9));
        }
        
        
        if (request.getParameter("id") != null && request.getParameter("id") != "") {
            //加载线索池
        	TrailPoolPOJO comObj = iTrailPool.getTrailPoolById(request.getParameter("id"));
        	comObj.setEntTypesName(comObj.getEntTypes());
            if (comObj != null) {             
                String temp = "";
                if (comObj.getEntTypes() != null && comObj.getEntTypes() != "") {
                    if (!comObj.getEntTypes().startsWith(",")) temp += "," + comObj.getEntTypes();
                    if (!temp.endsWith(",")) temp += comObj.getEntTypes() + ",";
                    comObj.setEntTypes(temp+"-100,");
                }             
                List<TrailContract> contList = iTrailPool.gettrailcontractBytraiId(comObj.getId());
                   if(contList!=null&&contList.size()>0){
                	   for (int i = 0; i < contList.size(); i++) {
                		   TrailContract con=contList.get(i);
                		   if(con.getFirst()==0){
                			   comObj.setConId(con.getId());
                			   comObj.setContacterName(con.getContactName());
                			   comObj.setEmail(con.getEmail());
                			   comObj.setSex(con.getSex());
                			   comObj.setQq(con.getQqNum());
                			   comObj.setMobilePhone(con.getMobilePhone());
                			   comObj.setPosition(con.getPositions());
                		   }else {
                			   comObj.setConId2(con.getId());
                			   comObj.setContacterName2(con.getContactName());
                			   comObj.setEmail2(con.getEmail());
                			   comObj.setSex2(con.getSex());
                			   comObj.setQq2(con.getQqNum());
                			   comObj.setMobilePhone2(con.getMobilePhone());
                			   comObj.setPosition2(con.getPositions());
						}
						
					}
                	   
                	   
                   }
            }
            if(comObj!=null&&comObj.getUserId()==null){
            	comObj.setUserId(user.getId());
            }
            mv.addObject("comObj", comObj);
            List<Areas> childAreas = iAreasBo.getChildArea(String.valueOf(comObj.getAreaId()));
            mv.addObject("childArea", childAreas);
        }
        
       
            
        
            	
      
        
        return mv;
    }
    
    @RequestMapping(value = "convertrail", method = RequestMethod.POST)
    @ResponseBody
    public  Map<String, String> convertrail(@RequestBody Customer customer, HttpServletRequest request){
    	

        Map<String, String> map = new HashMap<>();
     

        User user = (User) request.getSession().getAttribute("userInfo");

        try{

            customer.setItemId(Integer.parseInt(user.getItemId()));
            customer.setCreatedBy(user.getId());

            this.iCustomerBO.saveCustomer(customer);

            map.put("success", "1");
            map.put("msg","转换线索成功");
            map.put("categorybusiness", String.valueOf(customer.getCategoryBusiness()));//上下家
            Map<String, Integer> map1=new HashMap<>();
            map1.put("trailId", Integer.valueOf(customer.getTrailId()));
            map1.put("userId", user.getId());
            pubQueryBO.deltrail(map1);

        }catch (Exception e){

            e.printStackTrace();
            map.put("success", "0");
            map.put("msg","转换客户失败！"+e.getMessage());


        }
      
        return map;
    	
    	
    	
    }
    
    
    
    
    
    

    @RequestMapping(value = "checkCompany")
    public ModelAndView company(HttpServletRequest request, Integer pno) {
        ModelAndView mv = new ModelAndView("trailPool/loadCompany");
        List<Company> list = icompanyApiBo.GetCompanySimpleInfo(request.getParameter("companyName"), pno);
        if (list != null && list.size() > 0) {
            mv.addObject("companys", list);
        }
        return mv;
    }

    @RequestMapping(value = "companyDetail", method = RequestMethod.POST)
    @ResponseBody
    public String LoadContacter(HttpServletRequest request) {
        try {
            Map<String, Object> map = icompanyApiBo.GetCompanyInfo(request.getParameter("userid"));
            if (map.size() > 0) {
                if (map.containsKey("companyDetail")) return map.get("companyDetail").toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "{}";
    }

    // 根据父级id取城市
    @RequestMapping(value = "getAreaByprovinceId/{id}")
    @ResponseBody
    public List<Areas> getAreaByprovinceId(@PathVariable("id") String id) {
        List<Areas> areaList = iAreasBo.getChildArea(id);
        if (areaList != null) {
            return areaList;
        } else {
            return null;
        }
    }

   
    


   


   

    @RequestMapping("trailExport")
    public void TrailExport(String ids, String type, String tpoolOne, String tpoolTwo, String tpoolThree, Integer pno, HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException, IOException {
        //1.根据传入id获取对应数据
        //String[] companyInfo = ids.split(",");
        //List<TrailPoolPOJO> listTrail = iTrailPool.getDataByID(ids);
    	
    	Map<String, Object> map = new HashMap<String, Object>();
        //需要 type userID(登录用户的ID) itemId(登录用户的品目ID)
        User user = (User) request.getSession().getAttribute("userInfo");
       int userID = -1;
        int itemId = -1;
        if (user != null) {
           userID = user.getId();
         itemId = Integer.parseInt(user.getItemId());
       }
        map.put("userID", userID);
        map.put("itemId", itemId);
        map.put("trailId", ids);
        String tpoolThree2 = "";

        if (StringUtils.isNoneBlank(tpoolThree)) {

            tpoolThree2 = StringUtil.doDecoder(tpoolThree);
        }
        List<TrailPoolPOJO> listTrail = iTrailPool.getTrailPoolList(type, tpoolOne, tpoolTwo, tpoolThree2, map);
        
        
        //2.输出excel文件
        ExportUtil<TrailPoolPOJO> excel = new ExportUtil<TrailPoolPOJO>();
        String fileName = "线索池";
        String[] headerNames = new String[]{
                "公司名", "来源", "行业", "职位", "联系人", "手机", "QQ", "下次联系时间",
                "下次联系内容", "负责人", "加入时间"
        };
        String[] header = new String[]{
                "companyName", "categorySourceName", "salesProducts", "positions", "contractName", "mobilePhone",
                "qqNum", "nextTime", "nextContext", "userName", "createdAt"
        };
        String[] comments = new String[]{
                null, null, null, null, null, null,
                null, null, null, null, null
        };
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        excel.export("线索池", headerNames, header, comments, listTrail, os, "");
        byte[] content = os.toByteArray();
        InputStream is = new ByteArrayInputStream(content);
        // 设置response参数，可以打开下载页面
        response.reset();
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + new String((fileName + ".xls").getBytes("GBK"), "iso-8859-1"));
        ServletOutputStream out = response.getOutputStream();
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            bis = new BufferedInputStream(is);
            bos = new BufferedOutputStream(out);
            byte[] buff = new byte[2048];
            int bytesRead;
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
