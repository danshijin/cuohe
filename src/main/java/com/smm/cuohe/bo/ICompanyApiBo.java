package com.smm.cuohe.bo;

import java.util.List;
import java.util.Map;

import com.smm.cuohe.domain.Company;
import com.smm.cuohe.domain.TrailPoolAdd;

import net.sf.json.JSONObject;
 
public interface ICompanyApiBo {
   public List<Company> GetCompanySimpleInfo(String companyName,Integer pno);
   
   public  Map<String, Object> GetCompanyInfo(String userId);
   
   public JSONObject updateContacterInfo(TrailPoolAdd trailAdd);
}
