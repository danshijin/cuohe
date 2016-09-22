package com.smm.cuohe.bo.impl.customermanage;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.smm.cuohe.bo.IAreasBO;
import com.smm.cuohe.bo.ICategoryBO;
import com.smm.cuohe.bo.IPurchaseBO;
import com.smm.cuohe.bo.customermanage.ITrailPoolPojoBO;
import com.smm.cuohe.controller.api.TrailPoolAPIController;
import com.smm.cuohe.dao.customermanage.ITrailPoolPojoDAO;
import com.smm.cuohe.domain.Areas;
import com.smm.cuohe.domain.Category;
import com.smm.cuohe.domain.Items;
import com.smm.cuohe.domain.TrailContract;
import com.smm.cuohe.domain.TrailPoolAPI;
import com.smm.cuohe.domain.TrailPoolPOJO;

@Service
public class TrailPoolPojoBO implements ITrailPoolPojoBO {
	private static Logger logger= Logger.getLogger(TrailPoolPojoBO.class.getName());
    @Resource
    private ITrailPoolPojoDAO iTrailPoolPojoDao;
    

    /**
     * 获取线索池列表 
     */
    @Override
    public List<TrailPoolPOJO> getTrailPoolList(String type,String tpoolOne,String tpoolTwo,String tpoolThree,Map map) {
    	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		Calendar   ca   =   Calendar.getInstance(Locale.CHINA);
		
		List<String> productsList = iTrailPoolPojoDao.getAllProductsByItemId((int) map.get("itemId"));
		map.put("productsList", productsList);
		map.put("type", type);
		if(type !=null && type.length()>0 && type.equals("4")){
			ca.setTime(new Date()); //  setTime里面的时间为你要获取的那个月的时间
			ca.set(Calendar.DAY_OF_MONTH,   1);
			
			String firstDate=sdf.format(ca.getTime());//当前月的第一天
			ca.add(Calendar.MONTH,   1);
			ca.add(Calendar.DAY_OF_MONTH,   -1);
			String lastDate=sdf.format(ca.getTime());//当前月的最后一天
			map.put("firstDate", firstDate);
			map.put("lastDate", lastDate);
		}
		if(tpoolOne !=null && tpoolOne.length()>0 && tpoolTwo !=null && tpoolTwo.length()>0 && ! "0".equals(tpoolTwo) ){
			map.put("tpoolOne", tpoolOne);
			String tooplVal=tpoolTwo;//接收 tpoolTwo 搜索条件2的值
			map.put("tooplVal", tooplVal);
			if(tpoolThree==null){
				tpoolThree="";
			}
			if(tpoolTwo.equals("1")){//包含
				tpoolTwo="like "+" '%"+tpoolThree+"%'";
			}else if(tpoolTwo.equals("2")){
				tpoolTwo="not like "+" '%"+tpoolThree+"%'";
			}
			else if(tpoolTwo.equals("3")){
				tpoolTwo=" ='"+tpoolThree+"'";
			}
			else if(tpoolTwo.equals("4")){
				tpoolTwo=" !='"+tpoolThree+"'";
			}
			else if(tpoolTwo.equals("5")){
				tpoolTwo="like "+" '"+tpoolThree+"%'";
			}
			else if(tpoolTwo.equals("6")){
				tpoolTwo="like "+" '%"+tpoolThree+"'";
			}
			else if(tpoolTwo.equals("7")){
				tpoolTwo="IS NULL";
			}
			else if(tpoolTwo.equals("8")){
				tpoolTwo="IS NOT NULL";
			}
			map.put("tpoolTwo", tpoolTwo);
		}
        List<TrailPoolPOJO> trailPoolList=this.iTrailPoolPojoDao.getTrailPoolList(map);

         return trailPoolList;
    }
    /**
     * 批量删除线索池
     */
	@Override
	public Map<String, Object> updateList(String ids) {
		Map<String, Object> map=new HashMap<String, Object>();
		try{
		Integer count=this.iTrailPoolPojoDao.updateList(ids);
		if(count>0){
			map.put("code", "succ");	
			map.put("msg", "系统提示，操作成功");	
		}else{
			map.put("code", "error");
			map.put("msg", "系统提示，操作失败");	
			
		}
		}catch(Exception e){
			map.put("code", "error");
			map.put("msg", "系统提示，操作失败");	
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * 添加线索池 
	 * insert by tantaigen
	 */	
	public void addTrailPoolAdd(TrailPoolPOJO trailpooladd)
			throws RuntimeException { 
		  iTrailPoolPojoDao.addTrailPool(trailpooladd);
		  TrailContract conkey=new  TrailContract();
		  conkey.setContactName(trailpooladd.getContacterName());
		  conkey.setEmail(trailpooladd.getEmail());
		  conkey.setFirst(0);
		  conkey.setMobilePhone(trailpooladd.getMobilePhone());
		  conkey.setQqNum(trailpooladd.getQq());
		  conkey.setStatus(0);
		  conkey.setSex(trailpooladd.getSex());
		  conkey.setPositions(trailpooladd.getPosition());
		  conkey.setTrailId(trailpooladd.getId());
		  iTrailPoolPojoDao.addTrailContacer(conkey);
		  if(trailpooladd.getContacterName2()!=null&&!"".equals(trailpooladd.getContacterName2())){
			  TrailContract con=new  TrailContract();
			  con.setContactName(trailpooladd.getContacterName2());
			  con.setEmail(trailpooladd.getEmail2());
			  con.setFirst(1);
			  con.setMobilePhone(trailpooladd.getMobilePhone2());
			  con.setQqNum(trailpooladd.getQq2());
			  con.setStatus(0);
			  con.setSex(trailpooladd.getSex2());
			  con.setPositions(trailpooladd.getPosition2());
			  con.setTrailId(trailpooladd.getId());
			  iTrailPoolPojoDao.addTrailContacer(con);
		  }
	} 
	


	public  void updateTrail(TrailPoolPOJO trailpooladd){
		 iTrailPoolPojoDao.updateTrail(trailpooladd);
		  TrailContract conkey=new  TrailContract();
		  conkey.setContactName(trailpooladd.getContacterName());
		  conkey.setEmail(trailpooladd.getEmail());
		  conkey.setFirst(0);
		  conkey.setMobilePhone(trailpooladd.getMobilePhone());
		  conkey.setQqNum(trailpooladd.getQq());
		  conkey.setStatus(0);
		  conkey.setSex(trailpooladd.getSex());
		  conkey.setPositions(trailpooladd.getPosition());
		  conkey.setTrailId(trailpooladd.getId());
		  conkey.setId(trailpooladd.getConId());
		  iTrailPoolPojoDao.updateTrailCon(conkey);
		  if(trailpooladd.getContacterName2()!=null&&!"".equals(trailpooladd.getContacterName2())){
			  TrailContract con=new  TrailContract();
			  con.setId(trailpooladd.getConId2());
			  con.setContactName(trailpooladd.getContacterName2());
			  con.setEmail(trailpooladd.getEmail2());
			  con.setFirst(1);
			  con.setMobilePhone(trailpooladd.getMobilePhone2());
			  con.setQqNum(trailpooladd.getQq2());
			  con.setStatus(0);
			  con.setSex(trailpooladd.getSex2());
			  con.setPositions(trailpooladd.getPosition2());
			  con.setTrailId(trailpooladd.getId());
			  iTrailPoolPojoDao.updateTrailCon(con);
		  }
	};
	
	
	@Override
	public TrailPoolPOJO getTrailPoolById(String id) {
	  return iTrailPoolPojoDao.getTrailPoolById(id);
	} 

	
	@Override
	public List<TrailPoolPOJO> getDataByID(String trailIds) {
		
		return iTrailPoolPojoDao.getTrailpoolById(trailIds);
	}
	
	
	@Override
	public List<TrailContract> gettrailcontractBytraiId(Integer trailId) {
		// TODO Auto-generated method stub
		return iTrailPoolPojoDao.gettrailcontractBytraiId(trailId);
	}
	
	
	@Resource
	IPurchaseBO iPurchaseBO;
	@Resource
	ICategoryBO iCategoryBO; 
	@Resource
	IAreasBO iAreasBO;  
    
	@Override
	public Map<String, String> addTrailPool(TrailPoolAPI tpApi) {
		Map<String, String> result=new HashMap<>();
		String msg="";
		String code="faild";
		try {

			String[] coulum={tpApi.getCompanyName(),tpApi.getSalesProducts(),tpApi.getPosition(),
					tpApi.getContacterName(),tpApi.getMobilePhone(),tpApi.getSex()==null?"":tpApi.getSex().toString()};
			String[] message ={"[企业名称]不能为空","[主要销售]不能为空","[联系人职位]不能为空","[联系人姓名]不能为空",
					"[联系人手机]不能为空","[联系人性别]不能为空"};
			for(int i=0;i<coulum.length;i++)
			{
				if(!StringUtils.isNotBlank(coulum[i]))
				{ 
					result.put("code",code);
					result.put("msg", message[i]); 
					return result;
				}
			}
			Map<String, List<Category>> listCategory = iCategoryBO.getCategoryByModuleName();
			
			//categorySourceId		   
			if(StringUtils.isNotBlank(tpApi.getCategorySource())) tpApi.setCategorySource("接口注入");
			List<Category> li =  listCategory.get("线索来源");
			for(Category c : li)  
				if(c.getName().equals(tpApi.getCategorySource())){ tpApi.setCategorySourceId(c.getId()); break;} 
			
			//categoryCooId
			if(StringUtils.isNotBlank(tpApi.getCategoryCoo())){
				List<Category> list =  listCategory.get("客户级别");
				for(Category c : list)  
					if(c.getName().equals(tpApi.getCategoryCoo())){ tpApi.setCategoryCooId(c.getId()); break;} 
			}	
			
			//categoryEmployeeId
			if(StringUtils.isNotBlank(tpApi.getCategoryEmployee())){
				List<Category> list =  listCategory.get("员工人数");
				for(Category c : list)  
					if(c.getName().equals(tpApi.getCategoryEmployee())){ tpApi.setCategoryEmployeeId(c.getId()); break;} 
			}
			
			//salesVolumeId 
			if(StringUtils.isNotBlank(tpApi.getSalesVolume())){
				List<Category> list =  listCategory.get("年销售额");
				for(Category c : list)  
					if(c.getName().equals(tpApi.getSalesVolume())){ tpApi.setSalesVolumeId(c.getId()); break;} 
			}
			
			//companyPropertyId 
			if(StringUtils.isNotBlank(tpApi.getCompanyProperty())){
				List<Category> list =  listCategory.get("企业性质");
				for(Category c : list)  
					if(c.getName().equals(tpApi.getCompanyProperty())){ tpApi.setCompanyPropertyId(c.getId()); break;} 
			}
			
			//areaId  cityId 
			if(StringUtils.isNotBlank(tpApi.getArea()) && StringUtils.isNotBlank(tpApi.getCity())){
				
				tpApi.setAreaId(Integer.parseInt(tpApi.getArea()));
				tpApi.setCityId(Integer.parseInt(tpApi.getCity()));
			} 
			
			TrailPoolPOJO tPojo=new TrailPoolPOJO();
			tPojo.setCategorySource(tpApi.getCategorySourceId());
			tPojo.setNextTime(tpApi.getNextTime());
			tPojo.setNextContext(tpApi.getNextContext());
			tPojo.setCreatedAt(new Date());
			tPojo.setUpdatedAt(new Date());
			tPojo.setCategoryCoo(tpApi.getCategoryCooId());
			tPojo.setCompanyName(tpApi.getCompanyName());
			tPojo.setSalesProducts(tpApi.getSalesProducts());
			tPojo.setAddress(tpApi.getAddress());
			tPojo.setCorporate(tpApi.getCorporate());
			tPojo.setCompanyPhone(tpApi.getCompanyPhone());
			tPojo.setCompaySite(tpApi.getCompaySite());
			tPojo.setEntTypes(tpApi.getEntTypes());
			tPojo.setCategoryEmployee(tpApi.getCategoryEmployeeId());
			tPojo.setSalesVolume(tpApi.getSalesVolumeId());
			tPojo.setStrRegisterDate(tpApi.getStrRegisterDate());
			tPojo.setCompanyProperty(tpApi.getCompanyPropertyId());
			tPojo.setAreaId(tpApi.getAreaId());
			tPojo.setCity(tpApi.getCityId());
			iTrailPoolPojoDao.addTrailPool(tPojo);
			Integer id=tPojo.getId();
			TrailContract contract =new TrailContract();
			contract.setTrailId(id);
			contract.setPositions(tpApi.getPosition());
			contract.setContactName(tpApi.getContacterName());
			contract.setMobilePhone(tpApi.getMobilePhone());
			contract.setQqNum(tpApi.getQqNum());
			contract.setEmail(tpApi.getEmail());
			contract.setStatus(0);
			contract.setFirst(0);
			contract.setSex(tpApi.getSex());
			iTrailPoolPojoDao.addTrailContacer(contract);
			msg="OK";
			code="succ";
			result.put("msg", "OK");
			result.put("code", "succ");
		} catch (Exception e) {
			logger.info("添加线索池发生异常."+e.getMessage());
			msg=msg+"添加线索池发生异常.具体原因为："+e.getMessage();
		}
		
		result.put("msg", msg);
		result.put("code",code);
		
		
		return result;
	}
	
}
