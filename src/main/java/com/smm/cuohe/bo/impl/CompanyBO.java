package com.smm.cuohe.bo.impl;

import com.smm.cuohe.bo.IAreasBO;
import com.smm.cuohe.bo.ICategoryBO;
import com.smm.cuohe.bo.ICompanyBO;
import com.smm.cuohe.dao.ICompanyDAO;
import com.smm.cuohe.dao.IContacterDAO;
import com.smm.cuohe.domain.*;
import com.smm.cuohe.util.JSONUtil;
import com.smm.cuohe.util.StringUtil;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * @author zhaoyutao
 */
@Service
public class CompanyBO implements ICompanyBO {

    private Logger logger = Logger.getLogger(CompanyBO.class);

    @Resource
    ICompanyDAO iCompanyDao;

    @Resource
    IContacterDAO iContacterDao;

    @Resource
    private RestTemplate restTemplate;

    @Value("#{ch['getCompanySimpleInfo.URL']}")
    private String companySimpleInfoURL;

    @Resource
    ICategoryBO iCategoryBO;

    @Resource
    IAreasBO iAreasBO;

    @Override
    public CompanyView getCompanyViewByCustomId(Integer customId) {

        CompanyView info = iCompanyDao.getCompanyViewByCustomId(customId);

        //设置企业联系人信息
        info.setContacters(iContacterDao.getContactersByCustomerId(info.getId()));
        //设置企业首要采购联系人
        info.setPrimaryPurchaseContacts(iContacterDao.getPrimaryPurchaseContacter(customId));

        //拆分企业类型数据，生成说明
        String enttypes = info.getEntTypes();
        if (enttypes != null && !enttypes.equals("")) {
            String[] types = enttypes.split(",");
            String cTypes = "";
            Map<Integer, Category> categoryMap = iCategoryBO.getCategoryMap();
            for (String t : types) {
                cTypes += categoryMap.get(Integer.parseInt(t)).getName() + ",";
            }
            cTypes = cTypes.substring(0, cTypes.length() - 1);
            info.setEntTypesText(cTypes);
        }

        return info;
    }

    @Override
    public String updateCompanyView(CompanyView info, HttpServletRequest request) {

        //将用户信息从session中获取放入updatedby中
        User user = (User) request.getSession(Boolean.FALSE).getAttribute("userInfo");
        info.setUpdatedBy(user.getId() + "");

        iCompanyDao.updateCompanyView(info);

        return "success";
    }

    @Override
    public String deleteCompany(Integer id, int userId) {

        iCompanyDao.deleteCompany(id, userId);
        return "success";
    }

    @Override
    public List<Company> getCompanyByParam(String companyName) {
        List<Company> companyList = new ArrayList<Company>();
        try {
            String url = companySimpleInfoURL;
            url = url.replace("{0}", StringUtil.doEncoder(companyName));
            logger.info("开始调用：获取企业简略信息接口");
            String companySimpleInfo = restTemplate.postForObject(url, null,
                    String.class);

            if (StringUtils.isNotBlank(companySimpleInfo)) {
                Map<String, Object> spMap = JSONUtil
                        .doConvertJson2Map(companySimpleInfo);

                if (spMap.containsKey("code")
                        && "success".equals(spMap.get("code"))) {

                    @SuppressWarnings("unchecked")
                    List<Map<String, Object>> listMap = (List<Map<String, Object>>) spMap.get("resultData");
                    for (int i = 0; i < listMap.size(); i++) {
                        Company company = new Company();
                        company.setUserid((Integer) listMap.get(i).get("userid"));
                        company.setTruename((String) listMap.get(i).get("truename"));
                        company.setCompany((String) listMap.get(i).get("company"));
                        company.setAccount((String) listMap.get(i).get("username"));
                        company.setContractor((String) listMap.get(i).get("contractor"));
                        company.setMobile((String) listMap.get(i).get("mobile"));
                        companyList.add(company);
                    }

                    //TODO listMap转换成 list<实体类>

                } else {
                    //result.put("avg", "获取失败");
                }

            } else {
                //result.put("avg", "获取失败");
            }
        } catch (Exception e) {
            logger.info("SHFE实时价格接口调用发生异常");
//			result.put("avg", "获取失败");
        }
        return companyList;
    }

    /**
     * insert by dongmiaonan
     *
     * @param 添加企业 返回主键
     * @return
     */
    @Override
    public Integer addCompanyToId(Company company) {
        return iCompanyDao.addCompanyToId(company);
    }

    @Override
    public Company getCompanyById(Integer id) {
        return iCompanyDao.getCompanyById(id);
    }

    @Override
    public CompanyView getCompanyViewById(Integer companyId) {
        return iCompanyDao.getCompanyViewById(companyId);
    }

    @Override
    public void modifyCompany(Company company) {
        iCompanyDao.modifyCompany(company);
    }

    /**
     * insert by dongmiaonan
     *
     * @param 根据公司名查询
     * @return id name
     */
    @Override
    public List<Company> getCompanyByName(Map<String, Object> map) {
        return iCompanyDao.getCompanyByName(map);
    }

    @Override
    public List<Company> queryCompanyByNm(Map<String, Object> map) {
        return iCompanyDao.getCompanyByParam(map);
    }

    @Override
    public String getCompanyNameByCustomerId(int customerId) {

        return iCompanyDao.getCompanyNameByCustomerId(customerId);
    }

    public int getCustomerIdByItemIdAndCompanyId(Map<String, Integer> map) {

        logger.info("---map:" + map.toString());

        logger.info("---iCompanyDao:" + iCompanyDao.toString());

        Integer id = iCompanyDao.getCustomerIdByItemIdAndCompanyId(map);

        if (id == null) return 0;

        return id;
    }

    @Override
    public Company getCompanyByCompanyName(String name) {

//        return iCompanyDao.getCompanyByCompanyName(name);


        return null;
    }

    @Override
    public List<Areas> getParentArea() {

        return iAreasBO.getParentAreas();
    }

    @Override
    public int countCompanyByName(String companyName) {
        return iCompanyDao.countCompanyByName(companyName);
    }

	@Override
	public List<Company> getCompanyByNameAndItem(String companyName, int itemId) {
		
		return iCompanyDao.getCompanyByNameAndItem(companyName, itemId);
	}

}
