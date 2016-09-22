package com.smm.cuohe.controller;

import com.smm.cuohe.bo.IAreasBO;
import com.smm.cuohe.domain.Areas;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 
 * @author lirubin
 *
 */
@Controller
@RequestMapping(value="/areas")
public class AreasController {
	@Resource
	private IAreasBO iAreasBO;
	
	@RequestMapping(value="getChildAreas")
	@ResponseBody
	List<Areas> getChildArea(HttpServletRequest request){
		List<Areas> list = null;
		String parentId = request.getParameter("parentId");


		if(StringUtils.isNotBlank(parentId)){
			list = iAreasBO.getChildArea(parentId);
		}
		return list;
	} 
}
