package com.smm.cuohe.util;

/**
 * @author  zhaoyutao
 * @version 2015年9月23日 下午2:52:33
 * 类说明
 */
import java.io.BufferedWriter;  
import java.io.File;  
import java.io.FileNotFoundException;  
import java.io.FileOutputStream;  
import java.io.IOException;  
import java.io.OutputStreamWriter;  
import java.io.Writer;  
import java.util.ArrayList;  
import java.util.HashMap;  
import java.util.List;  
import java.util.Map;  
  
import freemarker.template.Configuration;  
import freemarker.template.Template;  
import freemarker.template.TemplateException;  
  
public class TestFtlExl {  
      
    private Configuration configuration = null;  
      
    public TestFtlExl(){  
        configuration = new Configuration();  
        configuration.setDefaultEncoding("UTF-8");  
    }  
      
    public static void main(String[] args) {  
    	TestFtlExl test = new TestFtlExl();  
        test.createWord();  
    }  
      
    public void createWord(){  
        Map<String,Object> dataMap=new HashMap<String,Object>();  
        getData(dataMap);  
        Template t=null;
        Writer out = null;
        //configuration.setClassForTemplateLoading(this.getClass(), "C:\\Users\\zhaoyutao\\Desktop");  //FTL文件所存在的位置  
        try {
			configuration.setDirectoryForTemplateLoading(new File("C:\\Users\\zhaoyutao\\Desktop"));  
            t = configuration.getTemplate("ftl.ftl"); //文件名  
            File outFile = new File("E:/outFilessa"+Math.random()*10000+".xls");  //生成文件的路径
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile)));  
            t.process(dataMap, out); 
        } catch (IOException e) {  
            e.printStackTrace();  
        } catch (TemplateException e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
    }  
    //这里赋值的时候需要注意,xml中需要的数据你必须提供给它,不然会报找不到某元素错的.
    private void getData(Map<String, Object> dataMap) {  
        dataMap.put("title", "标题");  
        dataMap.put("year", "2012");  
        dataMap.put("month", "2");  
        dataMap.put("day", "13");  
        dataMap.put("auditor", "鑫");  
        dataMap.put("phone", "xxxxxxxxxxxxx");  
        dataMap.put("weave", "文涛");
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();  
        for (int i = 0; i < 2; i++) {  
            Map<String,Object> map = new HashMap<String,Object>();  
    		map.put("title", "标题");  
            map.put("year", "2012");  
            map.put("month", "2");  
            map.put("day", "13");  
            map.put("auditor", "鑫");  
            map.put("phone", "xxxxxxxxxxxxx");  
            map.put("weave", "文涛");
            list.add(map);  
        }
        dataMap.put("list", list);  
    }  
}  