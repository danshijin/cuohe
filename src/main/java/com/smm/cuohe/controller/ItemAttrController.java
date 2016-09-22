package com.smm.cuohe.controller;

import com.smm.cuohe.bo.IItemAttrBO;
import com.smm.cuohe.domain.ItemAttr;
import com.smm.cuohe.domain.User;
import com.smm.cuohe.tools.ResultMessage;
import com.smm.cuohe.util.QueryConditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * Created by zhenghao on 2015/8/3.
 *
 */
@Controller
@RequestMapping("/itemattr")
public class ItemAttrController {

	@Autowired
	IItemAttrBO iItemAttrBO;

	/**
	 * 获取属性列表
	 * @param qc
	 * @param req
	 * @return
	 */
	@RequestMapping("getAll")
    public ModelAndView getAll(QueryConditions qc,HttpServletRequest req){
		Map<String, Object> parameters = new HashMap<String, Object>();
		User user = (User) req.getSession().getAttribute("userInfo");
		if(qc!=null){
				parameters.put("content", qc.getContent());
				parameters.put("operator", qc.getOperator());
				parameters.put("attribute", qc.getAttribute());
		}

		parameters.put("itemId",user.getItemId());
		parameters.put("createdById", user.getId());
        ModelAndView view=new ModelAndView("itemattr/itemattrList");
        List<Map<String,String>> list=iItemAttrBO.getAll(parameters);
        view.addObject("itemAttrList",list);
        view.addObject("parameters",parameters);
        return view;
    }
    
    /**
     * 添加属性
     * @param itemattr
     * @return
     */
    @RequestMapping(value="itemattrAdd",method=RequestMethod.POST)
    @ResponseBody
    public ResultMessage itemattrAdd(ItemAttr itemattr,HttpServletRequest req){
    	try {
    		
	    	User user = (User) req.getSession().getAttribute("userInfo");
			Map<String,String> map=new HashMap<String, String>();
			map.put("name", itemattr.getName());
			map.put("itemid", user.getItemId());
	
			if (iItemAttrBO.selectItemAttrByname(map)>0) {
				return new ResultMessage("error", "属性名字每品类只能有一个！");
			}
			
	    	iItemAttrBO.ItemAttrAdd(itemattr,user);
		} catch (Exception e) {
			return new ResultMessage("error", "未知异常请联系管理员！");
		}
        return ResultMessage.ADD_SUCCESS_RESULT;
        
    }
    
    
    @RequestMapping("jumpAddPage")
    public ModelAndView jumpAddPage(){
        ModelAndView view=new ModelAndView("itemattr/itemattrAdd");
        return view;
    }
    
    
    
    /**
     * 上下移动 交换数据排序值
     * @param sortingType   1上移动  否则下移动
     * @param id			操作数据id
     * @param sorting
     * @return
     */
    @RequestMapping("updateSorting")
    public String updateSorting(Integer sortingType,Integer id,Integer sorting){
    	

    	if(sortingType==1){
    		iItemAttrBO.updateSortingOn(id, sorting);
    	}else{
    		iItemAttrBO.updateSortingUnder(id, sorting);
    	}

    	return "redirect:/itemattr/getAll.do"; 
    }
    
    
    
    
    /**
     * 	批量禁用
     * @param sortingType
     * @return
     */
    @RequestMapping("batchDisable")
	@ResponseBody
    public Map<String,String> batchDisable(String ids){
    	
    		Map<String,String> map=new HashMap<String, String>();
		try {
			iItemAttrBO.disable(ids.split(","));
			map.put("code", "succ");	
			map.put("msg", "系统提示，操作成功");	
		} catch (Exception e) {
			// TODO: handle exception
			map.put("code", "error");
			map.put("msg", "系统提示，操作失败！");	
		}

        return map; 
    }
    
    
    
}
