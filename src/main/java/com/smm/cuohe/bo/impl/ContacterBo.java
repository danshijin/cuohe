package com.smm.cuohe.bo.impl;

import com.smm.cuohe.bo.IContacterBo;
import com.smm.cuohe.dao.IContacterDAO;
import com.smm.cuohe.domain.Contacter;
import com.smm.cuohe.domain.User;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.List;
import java.util.Map;

/**
 * Created by zhenghao on 2015/7/27.
 *
 */
@Service
public class ContacterBo implements IContacterBo{
	
    @Resource
    private IContacterDAO iContacterDAO;
    public List<Contacter> getAll() {

        List<Contacter> users=this.iContacterDAO.getAll();

        return users;
    }
	@Override
	public void addContacter(Contacter contacter) {
		iContacterDAO.addContacter(contacter);
		
	}
	public Integer selectCountByName(String name) {
		
		return iContacterDAO.selectCountByName(name);
	}
	@Override
	public Integer addContacterToId(Contacter contacter)
	{
	     return	iContacterDAO.addContacterToId(contacter);
	} 
	@Override
	public void  modifyContacter(Contacter contacter)
	{
		iContacterDAO.modifyContacter(contacter);
	}
	@Override
	public List<Contacter> getContacterByMobile(Map map)
	{
	     return	iContacterDAO.getContacterByMobile(map);
	}
	@Override
	public int getContacterCount(Map<String, Object> parameters) {
		return iContacterDAO.getContacterCount(parameters);
	}
	@Override
	public List<Map> getContacterList(Map<String, Object> parameters) {
		return iContacterDAO.getContacterList(parameters);
	}
	@Override
	public Contacter getContacterById(Integer id) {
		
		return iContacterDAO.getContacterById(id);
	}
	
	@Override
	public void removeContacter(String[] idarr) {
		iContacterDAO.removeContacter(idarr);
	
	}

	@Override
	public List<Contacter> getContactersByCustomerId(Integer customerId) {
		
		return iContacterDAO.getContactersByCustomerId(customerId);
	}

	@Override
	public void updateContacter(Contacter contacter) {
		
		iContacterDAO.updateContacter(contacter);
	}

	@Override
	public String updateContacter(Contacter contacter, User user) {
		
		iContacterDAO.updateContacter(contacter);
		
		return "success";
	}
	@Override
	public String addContacterForCompany(Contacter contacter, User user) {
		iContacterDAO.addContacterForCompany(contacter);
		return "success";
	}
	@Override
	public String deleteContacterById(int id) {
		iContacterDAO.deleteContacterById(id);
		return "success";
	}


	@Override
	public List<Map> getAllCompany(Map map){
		
		
		return iContacterDAO.getAllCompany(map);
	}
	@Override
	public Integer getAllCompanyCount(Map map) {
		return iContacterDAO.getAllCompanyCount(map);
	}
	@Override
	public void addContacterBatch(List<Contacter> contacters) {
		iContacterDAO.addContacterBatch(contacters);
	}
	@Override
	public Boolean batchDleContacterById(String... ids) {
		try {
			for (int i = 0; i < ids.length; i++) {
				deleteContacterById(Integer.parseInt(ids[i]));
			}
			
		} catch (Exception e) {
			return false;
		}

		return true;
	}
	@Override
	public Integer getCountByCK(Integer customerId) {
		return iContacterDAO.getCountByCK(customerId);
	}
	@Override
	public List<Contacter> getContacterBycustomerId(Map<String, Object> map) {
		
		return iContacterDAO.getContacterBycustomerId(map);
	}


}
