package com.smm.cuohe.controller.users;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.smm.cuohe.bo.ICustomerBO;
import com.smm.cuohe.bo.users.IBuyPoolPBO;
import com.smm.cuohe.bo.users.ICustomsPojoBO;
import com.smm.cuohe.bo.users.ISellPoolPBO;
import com.smm.cuohe.bo.users.IWebPartsPBO;
import com.smm.cuohe.bo.users.NotesBO;
import com.smm.cuohe.domain.BuyPoolPOJO;
import com.smm.cuohe.domain.CommodityAttr;
import com.smm.cuohe.domain.CustomsPOJO;
import com.smm.cuohe.domain.Notes;
import com.smm.cuohe.domain.RemindCustomerEntity;
import com.smm.cuohe.domain.SellPoolPOJO;
import com.smm.cuohe.domain.User;
import com.smm.cuohe.util.DateUtil;

/**
 * @author zengshihua
 *         <p/>
 *         用户登录控制器 先简单匹配，后续采用Spring Security
 */
@Controller
@RequestMapping("/notes")
public class NotesController {

    @Resource
    private NotesBO notesBO;
    @Resource
    private ICustomsPojoBO iCustomsPojoBO;
    @Resource
    private IBuyPoolPBO iPoolPBO;
    @Resource
    private IWebPartsPBO iwebpaPBO;
    @Resource
    private ISellPoolPBO iSellPoolPBO;
    @Resource
    private ICustomerBO icustomerBO;

    /**
     * 组织记录
     */
    @RequestMapping("notesByOrgan")
    @ResponseBody
    public Map<String, Object> notesByOrgan(HttpServletRequest request, Notes notes, Model mode) {

        Map<String, Object> paramMap = new HashMap<>();

        User user = (User) request.getSession().getAttribute("userInfo");

        paramMap.put("itemid", user.getItemId());

        paramMap.put("userid",user.getId());

        //获取当前日期
        String nowDate = DateUtil.doFormatDate(new Date(), "yyyy/MM/dd");
        String queryDate = null;

        if (notes.getNoteDate() != null) {

            queryDate = DateUtil.doFormatDate(notes.getNoteDate(), "yyyy/MM/dd");
            paramMap.put("notedate", queryDate);

        } else {
            paramMap.put("notedate", nowDate);
        }

        Notes reNotes = this.notesBO.notesByOrgan(paramMap);

        Map<String, Object> resultMap = new HashMap<>();

        if (reNotes != null)
            resultMap.put("notes", reNotes);
        else
            resultMap.put("notes", new Notes());

        //判断文本域是否可进行编辑
        //如果查询日期等于当前日期，可编辑、否则不可编辑
        //初始化时日期
        if (nowDate.equals(queryDate)) {
            resultMap.put("editFlag", "yes");
        } else {
            resultMap.put("editFlag", "no");
        }

        if (StringUtils.isBlank(queryDate)) {
            resultMap.put("editFlag", "yes");
            resultMap.put("nowDate", nowDate);
        }
        return resultMap;
    }


    /**
     * 组织记录-新增
     */
    @RequestMapping("addnotesByOrgan")
    @ResponseBody
    public Map<String, Object> addnotesByOrgan(HttpServletRequest request, Notes notes, Model mode) {

        User user = (User) request.getSession().getAttribute("userInfo");

        Integer count = this.notesBO.addnotesByOrgan(user, notes);

        Map<String, Object> resultMap = new HashMap<>();


        if (count > 0) {
            resultMap.put("msg", "保存成功");
        } else if (count == -1){
        	resultMap.put("msg", "");//未进行修改
        } else {
            resultMap.put("msg", "保存失败");
        }
        return resultMap;
    }

    /**
     * 个人便签
     */
    @RequestMapping("notesByMe")
    @ResponseBody
    public Map<String, Object> notesByMe(HttpServletRequest request, Notes notes, Model mode) {

        User user = (User) request.getSession().getAttribute("userInfo");

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("userId", user.getId());

        Notes reNotes = this.notesBO.notesByMe(paramMap);

        Map<String, Object> resultMap = new HashMap<>();
        if (reNotes != null)
            resultMap.put("notes", reNotes);
        else
            resultMap.put("notes", new Notes());

        return resultMap;
    }


    /**
     * 获取当前管理员日记创建日期列表
     */
    @RequestMapping("createDatesByUser")
    @ResponseBody
    public Map<String, Object> createDateByUser(HttpServletRequest request) {

        Map<String, Object> resultMap = new HashMap<>();

        User user = (User) request.getSession().getAttribute("userInfo");

        int user_id = user.getId();

        int item_id = Integer.parseInt(user.getItemId());

        List<String> dateList = this.notesBO.getNotesCreateDateByUser(user_id, item_id);

        resultMap.put("dates", dateList);

        return resultMap;

    }


    /**
     * 个人便签-新增
     */
    @RequestMapping("addnotesByMe")
    @ResponseBody
    public Map<String, Object> addnotesByMe(HttpServletRequest request, Notes notes, Model mode) {

        User user = (User) request.getSession().getAttribute("userInfo");
        Integer count = this.notesBO.addnotesByMe(user, notes);

        Map<String, Object> resultMap = new HashMap<>();
        if (count > 0) {
            resultMap.put("msg", "保存成功");
        } else {
            resultMap.put("msg", "保存失败");
        }
        return resultMap;
    }

    /**
     * 首页-最新加入客户
     */
    @RequestMapping("getCustomsList")
    @ResponseBody
    public Map<String, Object> getCustomsList(HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<>();
        User user = (User) request.getSession().getAttribute("userInfo");
        List<CustomsPOJO> plist = new ArrayList<>();
        if (user != null) {
            if (StringUtils.isNoneBlank(user.getItemId())) {//品目id
                plist = this.iCustomsPojoBO.getCustomsList(user.getItemId());
            }
        }
        resultMap.put("plist", plist);
        return resultMap;

    }

    /**
     * 首页-最新买盘
     */
    @RequestMapping("getBuyPoolList")
    @ResponseBody
    public Map<String, Object> getBuyPoolList(HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<>();
        List<BuyPoolPOJO> blist = new ArrayList<>();
        User user = (User) request.getSession().getAttribute("userInfo");
        if (user != null) {
            if (StringUtils.isNoneBlank(user.getItemId())) {//品目id
                blist = this.iPoolPBO.getBuyPoolList(user.getItemId());
            }
        }
        resultMap.put("blist", blist);
        return resultMap;

    }

    /**
     * 首页-最新卖盘
     */
    @RequestMapping("getSellPoolList")
    @ResponseBody
    public Map<String, Object> getSellPoolList(HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<>();
        User user = (User) request.getSession().getAttribute("userInfo");
        List<SellPoolPOJO> slist = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();

        if (user != null) {

            //获取用户品目对应卖盘列表
            slist = this.iSellPoolPBO.getSellPoolList(user.getItemId());

            if (slist != null && slist.size() > 0) {
                for (SellPoolPOJO s : slist) {
                    if (StringUtils.isNoneBlank(user.getItemId())) {//品目id
                        map.put("itemsID", user.getItems().getId());
                        map.put("sid", s.getId());

                        map.put("commodityId", s.getCommodityId());
                        CommodityAttr commodityAttr = this.iSellPoolPBO.getAttrVal(map);//查询品牌是否存在
                        if (commodityAttr != null && StringUtils.isNoneBlank(commodityAttr.getAttrName())) {
                            s.setItemAttrName(commodityAttr.getAttrName());
                            s.setItemAttrOptions(commodityAttr.getAttrValue());
                        }
                    }
                }
            }
        }
        resultMap.put("slist", slist);
        return resultMap;

    }

    /**
     * 最新买盘的查看
     */
    @RequestMapping("getBuyPool")
    public ModelAndView getBuyPool(String bid, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("/home/buyPoolDetail");
        BuyPoolPOJO bPojo = new BuyPoolPOJO();
        //首先查询 牌号 品牌是否存在
        Map<String, Object> map = new HashMap<>();
        User user = (User) request.getSession().getAttribute("userInfo");

        if (StringUtils.isNotBlank(bid)) {
            bPojo = this.iPoolPBO.getBuyPool(bid);
        }

        if (user != null) {
            if (StringUtils.isNoneBlank(user.getItemId())) {
                map.put("itemsID", user.getItemId());
                map.put("bid", bid);
                map.put("commodityId", bPojo.getCommodityId());

                CommodityAttr commodityAttr = this.iPoolPBO.getAttrVal(map);

                if (commodityAttr != null && StringUtils.isNoneBlank(commodityAttr.getAttrName())) {
                    bPojo.setItemAttrName(commodityAttr.getAttrName());
                    bPojo.setItemAttrOptions(commodityAttr.getAttrValue());
                }
            }
        }
        mav.addObject("bPojo", bPojo);
        return mav;
    }

    /**
     * 组件添加或者删除
     */
    @RequestMapping("addOrDelWpById")
    @ResponseBody
    public Map<String, Object> addOrDelWpById(String status, String wpartsId, HttpServletRequest request) {
        //ModelAndView mav=new ModelAndView("/home/index");
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (StringUtils.isNoneBlank(wpartsId) && StringUtils.isNoneBlank(status)) {
            User user = (User) request.getSession().getAttribute("userInfo");
            if (user != null) {
                if (StringUtils.isNoneBlank(user.getItemId())) {
                    Integer userID = user.getId();
                    String itemid = user.getItemId();
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("userID", userID);
                    map.put("itemid", itemid);
                    map.put("wpartsId", wpartsId);
                    map.put("updatedAt", new Date());
                    map.put("status", status);
                    resultMap = iwebpaPBO.addOrDelWpById(map);
                }
            }
        }
        return resultMap;
    }

    /**
     * 最近走势
     */
    @RequestMapping("recentlyTrend")
    @ResponseBody
    public Map<String, Object> recentlyTrend(HttpServletRequest request, Model mode) {

        User user = (User) request.getSession().getAttribute("userInfo");
        Map<String, Object> resultMap = null;
        if (user != null) {
            resultMap = this.notesBO.recentlyTrend(user);
        } else {

        }
        return resultMap;
    }
    /**
     * 推荐采购商
     * @param request
     * @return
     */
    @RequestMapping("geTreminCustom")
    @ResponseBody
    public Map<String, Object> geTreminCustom(HttpServletRequest request){
    	  Map<String, Object> resultMap = new HashMap<String, Object>();
    	  User user = (User) request.getSession().getAttribute("userInfo");
    	  RemindCustomerEntity remindCustomerEntity=new RemindCustomerEntity();
    	  remindCustomerEntity.setItemId(user.getItems().getId());
    	  SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
    	  remindCustomerEntity.setRemindTime(sdf.format(new Date()));
    	  List<RemindCustomerEntity>	culist=  icustomerBO.queryRemindCustomer(remindCustomerEntity);
    	  
    	  resultMap.put("culist", culist);
    	  
    	  return resultMap;
    }
    /**
     * 跳过
     * @param request
     * @param customerId
     * @return
     */
    @RequestMapping(value="toTaoguo")
    public ModelAndView toTaoguo(HttpServletRequest request){
    	
    	ModelAndView view=new ModelAndView("customer/updateTime");
    	view.addObject("id", request.getParameter("customerId" ));
    	 Calendar calendar=Calendar.getInstance();  
		 SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		 calendar.add(Calendar.DAY_OF_YEAR, 7);//让日期加7 
		 String	remindTime=sdf.format(calendar.getTime());
		 calendar.setTime(new Date()); 
		 view.addObject("remindTime", remindTime);
    	return view;
    }
    
    
    
    @RequestMapping(value="tiaoguo")
    @ResponseBody
    public Map<String, Object> tiaoguo(HttpServletRequest request )  {
    	 Map<String, Object> resultMap = new HashMap<String, Object>();
    	 try {
			Integer id=Integer.valueOf(request.getParameter("id"));
			String remindTime=request.getParameter("remindTime");
    	 Calendar calendar=Calendar.getInstance();  
		 SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		 calendar.setTime(new Date()); 
    	 if(remindTime==null||"".equals(remindTime)){
    			calendar.add(Calendar.DAY_OF_YEAR, 7);//让日期加7 
    			remindTime=sdf.format(calendar.getTime());
    	 }else {
    		
    		 
    		Date date=new Date();
    		Date remindDate=sdf.parse(remindTime);
    		if(!date.before(remindDate)){
    			resultMap.put("code", "remindTimeErron");
    	    	 return resultMap;	
    		}
    	 }
    	 RemindCustomerEntity remindCustomerEntity=new RemindCustomerEntity();
    	 remindCustomerEntity.setCustomerId(id);
    	 remindCustomerEntity.setRemindTime(remindTime);
    	 icustomerBO.tiaoguo(remindCustomerEntity);
    	 
    } catch (Exception e) {
    			// TODO: handle exception
    	e.printStackTrace();
			resultMap.put("code", "erron");
    		}
    	 resultMap.put("code", "ok");
    	 return resultMap;
    }
    
}
