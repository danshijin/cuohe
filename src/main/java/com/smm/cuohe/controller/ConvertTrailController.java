package com.smm.cuohe.controller;

import com.smm.cuohe.bo.*;
import com.smm.cuohe.bo.counts.TrialcountBo;
import com.smm.cuohe.bo.dealmake.ISellPoolBO;
import com.smm.cuohe.domain.*;
import com.smm.cuohe.domain.counts.TrailEntity;
import com.smm.cuohe.tools.ResultMessage;
import com.smm.cuohe.util.DateUtil;
import com.smm.cuohe.util.URLRequest;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



/*
 * 
 * auth 转换线索
 * 
 */
@Controller
@RequestMapping(value="/convertrail")
public class ConvertTrailController {
	private Logger logger=Logger.getLogger(ConvertTrailController.class);
	
	@Resource TrialcountBo trialcountBo;
	@Resource  PubQueryBO pubQueryBO;
	@Resource ICompanyBO iCompanyBO;
	@Resource IContacterBo iContacterBo;
	@Resource ICustomerBO customerBO;
	@Resource IAreasBO iAreasBO;
	@Resource
	private RestTemplate restTemplate;
	@Value("#{ch['getCategory.URL']}")
	private String getCategoryUrl;
	
	@Value("#{ch['addUser.URL']}")
	private String addUser;
	@Resource
	private ISellPoolBO sellPollBo;
	@Value("#{ch['updateCompanyInfo.URL']}")
	private String updateCompanyInfo;
	@Value("#{ch['addMember.URL']}")
	private String addMember;
	
	/**
	 * 准备转换线索
	 * @param req
	 * @return
	 */
	@RequestMapping(value="/toconvertrail")
		public ModelAndView toconvertrail(HttpServletRequest req,Integer id){
		
			try {
				req.setCharacterEncoding("utf-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ModelAndView view =new ModelAndView("home/convertTrail");
		
		List<TrailEntity> userlist=trialcountBo.userlist();
		List<TrailEntity> itemslist=trialcountBo.itemslist();
		List<PubQueryEntity> sousslist=pubQueryBO.queryModel("线索来源");
		List<PubQueryEntity> querycustom=pubQueryBO.queryModel("客户级别");
		List<PubQueryEntity> qiyetypelist=pubQueryBO.queryModel("企业类型");
		List<PubQueryEntity> yearmoneylist=pubQueryBO.queryModel("年销售额");
		List<PubQueryEntity> usercountlist=pubQueryBO.queryModel("员工人数");
		List<PubQueryEntity>  qiyexingzhi=pubQueryBO.queryModel("企业性质");
		List<PubQueryEntity>  caigouzhouqi=pubQueryBO.queryModel("采购周期");
		List<Areas>  parent= iAreasBO.getParentAreas();
		
		//String  itemsId=req.getParameter("itemsId");
		String source=req.getParameter("source");
		String pic=req.getParameter("pic");
		TrailEntity trail=pubQueryBO.querytrail(id);
		CompanyView	company=null;
		if(trail!=null){
				company=iCompanyBO.getCompanyViewById(trail.getCompanyID()); //获取企业信息
				
		}
		
		
		if(company!=null&&company.getEntTypes()!=null){
			
			String[] Str=company.getEntTypes().split(",");
			String name="";
			if(Str!=null && Str.length>0){
				for (String listid : Str) {
					PubQueryEntity pub=new PubQueryEntity();
					
					if(listid!=null&&!"".equals(listid)){
					pub.setListid(listid);
					pub=pubQueryBO.queryCategary(pub);
					};
					
					if(pub!=null&&pub.getName()!=null){
					name=name+pub.getName()+",";
							}
						}
						
					}
					if(name!=null&&!"".equals(name)){
					company.setEntTypes(name.substring(0, name.length()-1));
					}
				}
		
			List<Contacter> conlist=null;
			if(company!=null){//获取企业联系人信息
				
				conlist=pubQueryBO.contacterCompanyID(company.getId());
			}
			PubQueryEntity pub =new PubQueryEntity();
			pub.setListid(source);
			PubQueryEntity p=pubQueryBO.queryCategary(pub);
			
			
		
			if(company!=null&&pubQueryBO!=null){
				
					company.setCoo(p.getName());
				}
			
			User user =(User)req.getSession().getAttribute("userInfo");	
			company.setUserId(user.getId());
			company.setUserName(user.getName());
			view.addObject("userlist", userlist);
			view.addObject("sousslist", sousslist);
			view.addObject("querycustom", querycustom);
			view.addObject("yearmoneylist", yearmoneylist);
			view.addObject("usercountlist", usercountlist);
			view.addObject("qiyexingzhi", qiyexingzhi);
			view.addObject("company", company);	
			view.addObject("itemslist", itemslist);
			view.addObject("qiyetypelist", qiyetypelist);
			view.addObject("parent", parent);
			view.addObject("caigouzhouqi", caigouzhouqi);
			
			view.addObject("trailid", trail.getId());
			view.addObject("conlist", conlist);
			view.addObject("pic",req.getParameter(pic));
			if(trail.getItemsId()!=null){
				view.addObject("itemsId",trail.getItemsId());	
			}else {
				view.addObject("itemsId",req.getParameter("itemsId"));	
			}
			
			view.addObject("source",source);
		return   view;
	}
/**
 * 转换线索
 * @param req
 * @return
 */
@RequestMapping(value="/addconver",method= RequestMethod.POST)
@ResponseBody
public Object addconver(HttpServletRequest req) throws Exception {
		 User user=(User) req.getSession().getAttribute("userInfo");
		 
		 String trailId=req.getParameter("trailid");
		 String trailitemsID=req.getParameter("itemsId");
		 //企业信息
		 String  companyId=req.getParameter("companyId");
		  String comanyName=req.getParameter("comanyName");
		  String salesProducts=req.getParameter("salesProducts");
		  String level=req.getParameter("level");
		  String areaId=req.getParameter("areaId");
		  String[] entType=req.getParameterValues("entTypes");
		  String entTypes="";
		  if(entType!=null){
		  for (String str : entType) {
			  entTypes=entTypes+str+",";
		  }
		  }
		  if(!"".equals(entTypes)){
			  entTypes=entTypes.substring(0, entTypes.length()-1);
			  
		  }
		  String address=req.getParameter("address");
		  String salesVolume=req.getParameter("salesVolume");
		  String categoryEmployee=req.getParameter("categoryEmployee");
		  String buyProducts=req.getParameter("buyProducts");
		  String categoryFreq=req.getParameter("categoryFreq");
		  String buyVolume=req.getParameter("buyVolume");
		  String buyBrand=req.getParameter("buyBrand");
		  String categoryBusiness=req.getParameter("categoryBusiness");
		  if(categoryBusiness!=null&&!"1".equals(categoryBusiness)&&!"2".equals(categoryBusiness)){
			  
			  categoryBusiness="3";
		  }
	      String corporate=req.getParameter("corporate");
		  String registerDate=req.getParameter("registerDate");
		  String companyPhone=req.getParameter("companyPhone");
		  String companyProperty=req.getParameter("companyProperty");
		  String companySite=req.getParameter("companySite");
		  String taxNo=req.getParameter("taxNo");
		  String bank=req.getParameter("bank");
		  String bankAccount=req.getParameter("bankAccount");
		  String credit=req.getParameter("credit");
		  String businessLicense=req.getParameter("businessLicense");
		  String expiryTime=req.getParameter("expiryTime");
		  String commands=req.getParameter("commands");
		  String companyAss=req.getParameter("companyAss");
		 String  account=sellPollBo.getcompanyAccount(Integer.valueOf(companyId));
		 Company com=new Company();
		 com.setId(Integer.valueOf(companyId));

		 com.setName(comanyName);
		 com.setSalesProducts(salesProducts);
		 com.setLevel(Integer.valueOf(level));
		 com.setAreaId(Integer.valueOf(areaId));
		 com.setEntTypes(entTypes);
		 com.setAddress(address);
		 com.setSalesVolumes(Integer.valueOf(salesVolume));
		 com.setCategoryEmployee(Integer.valueOf(categoryEmployee));
		 com.setBuyProducts(buyProducts);
		 com.setCategoryFreq(Integer.valueOf(categoryFreq));
		 com.setBuyVolume(Integer.valueOf(buyVolume));
		 com.setBuyBrand(buyBrand);
		 com.setCategoryBusiness(Integer.valueOf(categoryBusiness));
		 com.setUpdatedAt(new Date());
		 com.setCorporate(corporate);
		 if(registerDate!=null&&!"".equals(registerDate)){
			 com.setRegisterDate(DateUtil.doSFormatDate(registerDate, "yyyy-MM-dd"));
		 }
		 
		 com.setCompanyPhone(companyPhone);
		 com.setCompanyProperty(Integer.valueOf(companyProperty));
		 com.setCompanySite(companySite);
		 com.setTaxNo(taxNo);
		 com.setBank(bank);
		 com.setBankAccount(bankAccount);
		 com.setCredit(Integer.valueOf(credit));
		 com.setBusinessLicense(businessLicense);
		 if(expiryTime!=null&&!"".equals(expiryTime)){
			 com.setExpiryTime(DateUtil.doSFormatDate(expiryTime, "yyyy-MM-dd"));
		 }
		 
		 com.setCompanyAss(companyAss);
		 com.setCommands(commands.trim());
		 //客户信息
		 Customer cus=new Customer();
		 String source=req.getParameter("source");
		
		String pic=req.getParameter("pic");
//		cus.setCompanyId(Integer.valueOf(companyId));
		cus.setPic(Integer.valueOf(pic));
		cus.setCreatedAt(new Date());
		cus.setStatus(0);
		cus.setCreatedBy(user.getId());
		if(trailitemsID!=null&&!"".equals("")){
			cus.setItemId(Integer.valueOf(trailitemsID));
		}else{
			cus.setItemId(user.getItems().getId());
			
		}
		
		cus.setCategorySource(Integer.valueOf(source));
		
		//获取修改联系人数据
		String[] conId=req.getParameterValues("conId") ;
		String[] concompanys=req.getParameterValues("concompany") ;
		String[] connames=req.getParameterValues("conname") ;
		String[] consex=req.getParameterValues("consex") ;
		String[] conposition=req.getParameterValues("conposition") ;
		String[] conmobilePhone=req.getParameterValues("conmobilePhone") ;
		String[] conqq=req.getParameterValues("conqq") ;
		String[] contelephone=req.getParameterValues("contelephone") ;
		String[] conemail=req.getParameterValues("conemail") ;
		String[] conkeyPerson=req.getParameterValues("conkeyPerson") ;
		String[]  contidentity=req.getParameterValues("tidentity");		
		String[] keyPerson=req.getParameterValues("keyPerson") ;
		String[] names=req.getParameterValues("name") ;
		String[] telephone=req.getParameterValues("telephone") ;
		String[] mobilePhone=req.getParameterValues("mobilePhone") ;
		String[] qq=req.getParameterValues("qq") ;
		String[] email=req.getParameterValues("email") ;
		
		String[] tidentity=req.getParameterValues("tidentity");
		Map<String, Object> map=new HashMap<>();
		map.put("companyId", companyId);
		map.put("itemId", trailId);
		int countcustom=pubQueryBO.queryzhcustoms(map);
		if(countcustom>0){
			return ResultMessage.ADD_CFKH_RESULT;
		}		
		 String accountName="";
		//修改联系人
	
				com.setAccount(accountName);
					
				Contacter cons=new Contacter();
			if(conId!=null&&conId.length>0){
			for (int i = 0; i < conId.length; i++) {
				
				
				
				Contacter con=new Contacter();
				con.setId(Integer.valueOf(conId[i]));
				
				Company	company1=new Company(); //获取企业信息
				company1.setId(Integer.valueOf(concompanys[i]));
//				con.setCompany(company1);
				if(connames!=null&&connames.length>=i){
					con.setName(connames[i]);
					
				}
				if(consex!=null&&consex.length>=i){
					con.setSex(Integer.valueOf(consex[i]));
					
				}
				if(conposition!=null&&conposition.length>=i){
					con.setPosition(conposition[i]);
					
				}
				if(conmobilePhone!=null&&conmobilePhone.length>=i){
						con.setMobilePhone(conmobilePhone[i]);	
								
							}
				if(conqq!=null&&conqq.length>=i){
					con.setQq(conqq[i]);
					
				}
				if(contelephone!=null&&contelephone.length>=i){
					
					con.setTelephone(contelephone[i]);
				}
				if(conemail!=null&conemail.length>=i){
					
					con.setEmail(conemail[i]);
				}
				if(conkeyPerson!=null&&conkeyPerson.length>=i){
					con.setKeyPerson(Integer.valueOf(conkeyPerson[i]));
					
				}if(contidentity!=null&&contidentity.length>=i){
					con.setTidentity(Integer.valueOf(contidentity[i]));
				}
				con.setStatus(0);
//				con.setUpdatedBy(user);
				con.setUpdatedAt(new Date());
				if(i==0){
					cons=con;
				}
				
				iContacterBo.updateContacter(con);;			
			}
			
			}
		
			
		 //获取新建联系人信息
		String[] companys=req.getParameterValues("company") ;
		
		String[] sex=req.getParameterValues("sex") ;
		String[] position=req.getParameterValues("position") ;
	
	
		//新建联系人
		if(companys!=null&&companys.length>0){
		
			
			

			for (int i = 0; i < companys.length; i++) {
				Contacter con=new Contacter();
				Company	company1=new Company(); //获取企业信息
				company1.setId(Integer.valueOf(companys[i]));
//				con.setCompany(company1);
				if(names!=null&&names.length>=i){
					con.setName(names[i]);
					
				}
				if(sex!=null&&sex.length>=i){
					con.setSex(Integer.valueOf(sex[i]));
					
				}
				if(position!=null&&position.length>=i){
					con.setPosition(position[i]);
					
				}
				if(mobilePhone!=null&&mobilePhone.length>=i){
						con.setMobilePhone(mobilePhone[i]);	
								
							}
				if(qq!=null&&qq.length>=i){
					con.setQq(qq[i]);
					
				}
				if(telephone!=null&&telephone.length>=i){
					
					con.setTelephone(telephone[i]);
				}
				if(email!=null&email.length>=i){
					
					con.setEmail(email[i]);
				}
				if(keyPerson!=null&&keyPerson.length>=i){
					con.setKeyPerson(Integer.valueOf(keyPerson[i]));
					
				}if(tidentity!=null&&tidentity.length>=i){
					con.setTidentity(Integer.valueOf(tidentity[i]));
				}
				con.setStatus(0);
//				con.setCreatedBy(user);

				iContacterBo.addContacter(con);			
			}
			
			}
		
		if(account==null || "".equals(account)){
			 
		

		 Integer userid= addMember(cons,com);//注册商城会员
		if(userid==-1){
			return "商城会员注册失败";
		}else {
			com.setAccount(cons.getMobilePhone());
		}
		
		Integer	code=addMallcompamy(com,cons);//添加企业信息 
		if(code==1){
			return "更新商城企业接口调用失败";
		}
		
		}else {
			com.setAccount(account);
		}
		
		 
		 pubQueryBO.updatecompany(com);
		
		customerBO.saveCustomer(cus);
		//pubQueryBO.deltrail(Integer.valueOf(req.getParameter("trailid")));
		
		return ResultMessage.ADD_SUCCESS_RESULT;
	}	

/*
 * 注册会员
 * 
 */

	public  Integer addMember(Contacter con,Company company){
		
		String url="http://172.16.9.16:8080/destoon/member/add";
		
        Map<String,String> map=new HashMap<>();

        map.put("mobile",con.getMobilePhone());
        map.put("company",company.getName() );
        map.put("email",con.getEmail());
        map.put("gender",String.valueOf(con.getSex()));
        map.put("truename", con.getName());
        map.put("qq", con.getQq());
        map.put("areaid", String.valueOf(company.getAreaId()));
        map.put("career", con.getPosition());

        try {
        	logger.info("开始调用：注册会员接口  参数："+map.toString());
        	
            String result=URLRequest.post(url, map);
            JSONObject jsonObject= JSONObject.fromObject(result);
            
            logger.info("册会员接口返回结果"+jsonObject.toString());
            
            String code=jsonObject.get("code").toString();
            if("1".equals(code)){            	
            	 String resultData=String.valueOf(jsonObject.get("resultData"));
            	JSONObject jsonresultData= JSONObject.fromObject(resultData);
            	String userid =String.valueOf(jsonresultData.get("userid"));
            	return Integer.valueOf(userid);
            }else {
				return -1;
			}
            
            

        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }

		
	}

	/**
	 * 同步商城企业用户接口
	 */
	public  Integer   addMallcompamy(Company company,Contacter con ){
		
		
		 

	        Map<String,String> map=new HashMap<>();

	        map.put("userid",String.valueOf(con.getId()));
	        map.put("username",con.getMobilePhone());
	        map.put("company",company.getName());
	        map.put("level",String.valueOf(company.getLevel()));
	        map.put("areaid",String.valueOf(company.getAreaId()));
	        map.put("size",String.valueOf(company.getCategoryEmployee()));

	        map.put("regyear",DateUtil.doFormatDate(company.getRegisterDate(),"yyyy"));
	        map.put("sell",company.getSalesProducts());
	        map.put("buy",company.getBuyProducts());
	        map.put("business",company.getSalesProducts());
	        map.put("telephone",company.getCompanyPhone());
	        map.put("address",company.getAddress());

	        map.put("homepage","http://www.smm.cn");

	        try {

	            String result=URLRequest.post(addMember, map);
	            JSONObject jsonObject= JSONObject.fromObject(result);
	            String code=(String) jsonObject.get("code");
	            if("1".equals(code)){
	            	return 0;
	            }
	            System.out.println(result);

	        } catch (Exception e) {
	            e.printStackTrace();
	            return 1;
	        }
		
		
	
			
		 
				
			
		 return 1;
	}


}
