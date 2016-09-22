package com.smm.cuohe.controller;

import com.smm.cuohe.bo.IPurchaseBO;
import com.smm.cuohe.domain.PageBean;
import com.smm.cuohe.domain.Purchase;
import com.smm.cuohe.domain.User;
import com.smm.cuohe.util.ExcelUtil;
import com.smm.cuohe.util.ExportUtil;
import com.smm.cuohe.util.StringUtil;
import com.smm.cuohe.util.Texting;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 采购商客户管理
 */
@Controller
@RequestMapping(value = "/purchase")
public class PurchaseController {

    @Resource
    private IPurchaseBO iPurchaseBO;
	@Resource
	public Texting texting;
	
	 @Value("#{ch['sendSMS.URL']}")
	 private String sendSMS;
	

    @RequestMapping(value = "/getAll")
    public ModelAndView getAll(HttpServletRequest req, String type, String condition, String column,
                               String context, Integer pno) {
        User user = (User) req.getSession().getAttribute("userInfo");

        Map<String, String> map = new HashMap<>();

        map.put("type", type);
        map.put("condition", condition);
        map.put("column", column);

        if (context != null) {
            map.put("context", StringUtil.doDecoder(context));
        }

        map.put("userId", "" + user.getId());
        map.put("itemId", user.getItemId());

        int count = this.iPurchaseBO.getAllCount(map);
        if (pno == null) {
            pno = 1;
        }
        PageBean page = new PageBean(10, pno, count);
        int startNum = page.getStartNum();
        int endNum = page.getEndNum();

        map.put("startNum", "" + startNum);
        map.put("endNum", "" + endNum);

        List<Purchase> list = this.iPurchaseBO.getAll(map);
        ModelAndView view = new ModelAndView("customer/purchase");
        view.addObject("purlist", list);
        view.addObject("typeId", type);
        view.addObject("column", column);
        view.addObject("condition", condition);

        if (context != null) {
            view.addObject("context", StringUtil.doDecoder(context));
        }
        view.addObject("totalRecords", count);// 总条数
        view.addObject("totalPage", page.getTotalPages());// 总页数

        return view;
    }

    @RequestMapping(value = "/deleteByIds")
    @ResponseBody
    public Map<String, Object> deleteByIds(String ids) {
        Map<String, Object> map = new HashMap<String, Object>();
        int count = this.iPurchaseBO.deleteByIds(ids);
        if (count > 0) {
            map.put("code", "succ");
            map.put("msg", "系统提示，操作成功");
        } else {
            map.put("code", "error");
            map.put("msg", "系统提示，操作失败");

        }
        return map;
    }

    @RequestMapping(value = "/tosendPurchaseSMS")
    public ModelAndView tosendPurchaseSMS(String ids) {
        List<String> list = this.iPurchaseBO.selPhoneBycusId(ids);
        StringBuffer sb = new StringBuffer("");
        for (String t : list) {
            sb.append(t + ",");
        }
        String phones = sb.substring(0, sb.toString().length() - 1).toString();
        ModelAndView view = new ModelAndView("customer/purchaseSMS");
        view.addObject("phones", phones);
        return view;
    }

    @RequestMapping(value = "/sendPurchaseSMS")
    @ResponseBody
    public String sendPurchaseSMS(String phones, String context) {
        boolean b = true;
        String[] phone = phones.split(",");
        for (int i = 0; i < phone.length; i++) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            String time = sdf.format(new Date());
            String status = texting.sendsmm(time, context, phone[i]);
            if (!status.equals("command=SMS20_RESPONSE&status=ACCEPTD&code=000")) {
                b = false;
            }
        }
        if (b) {
            return "success";
        } else {
            return "success";
        }
    }

    // 导入excel
    @RequestMapping(value = "/importExcel")
    @ResponseBody
    public String importExcel(HttpServletRequest request,
                              HttpServletResponse response) throws IllegalStateException,
            IOException {
        InputStream stream = null;
        String fileName = "";
        // 解析器解析request的上下文
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        // 先判断request中是否包涵multipart类型的数据，
        if (multipartResolver.isMultipart(request)) {
            // 再将request中的数据转化成multipart类型的数据
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            Iterator iter = multiRequest.getFileNames();
            while (iter.hasNext()) {
                MultipartFile file = multiRequest.getFile((String) iter.next());
                if (file != null) {
                    fileName = file.getOriginalFilename().substring(
                            file.getOriginalFilename().lastIndexOf("."));
                    stream = file.getInputStream();
                    break;
                }
            }
        }
        Map<String, String> dataExcelMapping = new HashMap<>();
        dataExcelMapping.put("序号", "excelNum");
        dataExcelMapping.put("品目名称", "itemName");
        dataExcelMapping.put("企业名称", "companyName");
        dataExcelMapping.put("企业地址", "companyAddress");
        dataExcelMapping.put("企业类型", "companyEntTypes");
        dataExcelMapping.put("行业信息", "salesProducts");
        dataExcelMapping.put("年销售额", "companySalesVolume");
        dataExcelMapping.put("采购周期", "companyCategoryFreq");
        dataExcelMapping.put("采购品牌", "companyBuyBrand");
        dataExcelMapping.put("采购量", "companyBuyVolume");
        dataExcelMapping.put("上下家(类别)", "CategoryBusiness");
        dataExcelMapping.put("企业员工人数", "categoryEmployee");
        dataExcelMapping.put("企业联系人", "contacterName");
        dataExcelMapping.put("企业联系人性别", "contacterSex");
        dataExcelMapping.put("联系人电话", "contacterMobilePhone");
        dataExcelMapping.put("联系人职位", "contacterPosition");
        dataExcelMapping.put("企业地区名称", "areaName");
        //dataExcelMapping.put("最后成交时间", "customLastTransTime");
        //dataExcelMapping.put("负责人姓名", "userName");
        //dataExcelMapping.put("最近一次沟通时间", "comRecordUpdatedAt");
        //dataExcelMapping.put("沟通标题", "comRecordTitle");
        //dataExcelMapping.put("沟通内容", "comRecordContext");
        //dataExcelMapping.put("负责人Email", "userEmail");
        ExcelUtil excelUtil = new ExcelUtil();
        List<Map<String, String>> dataInfo = null;
        String result = "";
        if (fileName.equals(".xls")) {
            // 2003
            try {
                dataInfo = excelUtil.parseExcel2003(stream, dataExcelMapping);
            } catch (Exception e) {
                e.printStackTrace();
                result = "文件导入格式不正确，请确认。\r\n";
            }
        } else {
            // 2007
            try {
                dataInfo = excelUtil.parseExcel2007(stream, dataExcelMapping);
            } catch (Exception e) { 
                e.printStackTrace();
                result = "文件导入格式不正确，请确认。\r\n";
            }
        }

        User user = (User) request.getSession().getAttribute("userInfo");
        if (user == null) {
            result += "请重新登录系统。\r\n";
        }
        if (dataInfo != null)
            result += this.iPurchaseBO.importExcel(dataInfo, user);
        return result;
    }

    @RequestMapping("purchaseExport")
    public void purchaseExport(HttpServletRequest req, String type, String condition, String column,
            String context, String ids, HttpServletResponse response)
            throws UnsupportedEncodingException, IOException {
        // 1.根据传入id获取对应数据
        //String[] purchaseInfo = ids.split(",");

        //List<Purchase> listPurchase = this.iPurchaseBO.getExpotDataByID(purchaseInfo);
    	
    	 User user = (User) req.getSession().getAttribute("userInfo");

         Map<String, String> map = new HashMap<>();

         map.put("type", type);
         if (StringUtils.isNoneBlank(condition)) {

        	 condition = StringUtil.doDecoder(condition);
         }
         map.put("condition", condition);
         map.put("column", column);
         map.put("ids", ids);

         if (context != null) {
             map.put("context", StringUtil.doDecoder(context));
         }

         map.put("userId", "" + user.getId());
         map.put("itemId", user.getItemId());

         List<Purchase> listPurchase = this.iPurchaseBO.getAll(map);
    	
    	
    	//2.生成excel
        ExportUtil<Purchase> excel = new ExportUtil<Purchase>();
        String fileName = "客户池_采购";

        String[] headerNames = new String[]{"公司名称", "沟通时间", "企业类型", "年销售额",
                "联系人", "电话", "省份", "上次成交时间", "采购周期", "采购品牌", "负责人", "加入时间"};
        String[] header = new String[]{"companyName", "updatedAtByRecord",
                "entTypes", "salesVolume", "nameByContacter", "mobilePhone",
                "nameByArea", "updatedAtByOrder", "freqString", "buyBrand",
                "nameByUser", "createdAt"};
        String[] comments = new String[]{null, null, null, null, null, null,
                null, null, null, null, null, null};
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        excel.export("sheet1", headerNames, header, comments, listPurchase, os,
                "");

        byte[] content = os.toByteArray();
        InputStream is = new ByteArrayInputStream(content);
        // 设置response参数，可以打开下载页面
        response.reset();
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename="
                + new String((fileName + ".xls").getBytes("GBK"), "iso-8859-1"));//
        ServletOutputStream out = response.getOutputStream();
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            bis = new BufferedInputStream(is);
            bos = new BufferedOutputStream(out);
            byte[] buff = new byte[2048];
            int bytesRead;
            // Simple read/write loop.
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
