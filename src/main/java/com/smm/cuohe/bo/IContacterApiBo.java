package com.smm.cuohe.bo;

import com.smm.cuohe.domain.Company;
import com.smm.cuohe.domain.Contacter;
import com.smm.cuohe.domain.User;
import net.sf.json.JSONObject;
 
public interface IContacterApiBo {

JSONObject updateContacterInfo(Company company, Contacter contacter,User user);
}
