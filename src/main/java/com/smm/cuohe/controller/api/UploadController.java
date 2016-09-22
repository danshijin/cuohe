package com.smm.cuohe.controller.api;


import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Pattern;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.smm.cuohe.dao.IDiscussChatRecordDAO;
import com.smm.cuohe.domain.DiscussChatRecord;

/**
 * 图片上传Controller
 * @author wanghui
 *
 */

@Controller
@RequestMapping(value="/upload")
public class UploadController {
	
	private Logger logger = Logger.getLogger(UploadController.class);
	
	 @Resource
	 IDiscussChatRecordDAO iDiscussChatRecordDao;
	
	
	@Value("#{ch['fileUpload.URL']}")
	private String fileUpload;
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param chatID 
	 * 				ch_chats中的id
	 * @param employeeId
	 * 				撮合人员id
	 * @param chatFromID
	 * 				对应发送消息id
	 * @param chatFromType
	 * 				该记录是谁发送过来的，U代表撮合人员，C代表商城用户
	 * @return
	 */
    @RequestMapping(value="addupload")
 	@ResponseBody
 	public Map<String, Object> addupload(HttpServletRequest request,HttpServletResponse response,Integer chatID,Integer employeeId,Integer chatFromID,String chatFromType){
 		Map<String, Object> map = new HashMap<String, Object>();
 		String result="发送图片成功";
 		String success="true";
 		String errorcode="1001";
 		logger.info("入参:"+chatID+"--"+employeeId+"--"+chatFromID+"--"+chatFromType);
 		try {
 				Map<String, Object> maper=upload(request, response);//图片上传地址
 				if(maper.get("code").toString().equals("000000")){
 					DiscussChatRecord record=new DiscussChatRecord();
 					record.setChatId(chatID);
 					record.setType(5);//类型
 					record.setContent(maper.get("data").toString());//内容
 					if(chatFromType.equals("U")){
 						record.setChatFromId(employeeId);
 					}else{
 						record.setChatFromId(chatFromID);
 					}
 					record.setChatFromType(chatFromType);
 					record.setStatus(4);//对应type的状态
 					record.setEmployeeId(employeeId);
 					record.setSysTime(new Date());
 					iDiscussChatRecordDao.addRecord(record);
 				}else{
 					result="图片格式错误";
 				}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			success="false";
			errorcode="1002";
			result=e.getMessage();
		}
        map.put("success",success);
        map.put("errorcode", errorcode);
        map.put("result", result);
        return map;
 	}
    
    public Map<String, Object> upload(HttpServletRequest request,HttpServletResponse response) throws Exception {  
    	Map<String, Object> map=new HashMap<String, Object>();
    	String code="";
    	String data="";
    	try {
    		 //创建一个通用的多部分解析器  
            CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());  
            //判断 request 是否有文件上传,即多部分请求  
            if(multipartResolver.isMultipart(request)){  
                //转换成多部分request    
                MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;  
                //取得request中的所有文件名  
                Iterator<String> iter = multiRequest.getFileNames();  
                while(iter.hasNext()){
                    //取得上传文件  
                    MultipartFile file = multiRequest.getFile(iter.next());  
                    if(file != null){
                        //取得当前上传文件的文件名称  
                        String myFileName = file.getOriginalFilename();  
                        //如果名称不为“”,说明该文件存在，否则说明该文件不存在  
                        if(myFileName.trim() !=""){
                        	String  path=getNewUserImage(file,"/Public/images/tmp/","tmp");
                        	if(!path.equals("图片格式有误")){
	                        	 File fl=new File(path);
	                           	 String  result_str = postRequestEmulator(fl);
	                           	 JSONObject jsonresult = JSONObject.fromObject(result_str);
	                           	 code=jsonresult.get("code").toString();
	                           	 if(code.equals("000000")){
	                           		logger.info("图片上传FTP地址："+fileUpload+jsonresult.get("data").toString());
	                           		data =fileUpload+jsonresult.get("data").toString();;
	                           	 }
                        	}
                        }
                    }
                }  
            }
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw e;
		}
    	map.put("code", code);
    	map.put("data", data);
    	return map;
    }
    
    /**
     * http请求
     * @return
     * @throws Exception 
     */
    public String postRequestEmulator(File file) throws Exception{
    	 DataInputStream in = null;
    	 DataOutputStream out = null;
    	 HttpURLConnection con = null;
    	try{
    		 /**
             * 第一部分
             */
            URL urlObj = new URL("http://172.16.9.205:8080/imgServer/mongo/upload.do?clientid=10002");
            con = (HttpURLConnection) urlObj.openConnection();
            /**
             * 设置关键值
             */
            con.setRequestMethod("POST"); // 以Post方式提交表单，默认get方式
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setUseCaches(false); // post方式不能使用缓存
            // 设置请求头信息
            con.setRequestProperty("Connection", "Keep-Alive");
            con.setRequestProperty("Charset", "UTF-8");
            // 设置边界
            String BOUNDARY = "----------" + System.currentTimeMillis();
            con.setRequestProperty("Content-Type", "multipart/form-data; boundary="  + BOUNDARY);
            // 请求正文信息
            // 第一部分：
            StringBuilder sb = new StringBuilder();
            sb.append("--"); // ////////必须多两道线
            sb.append(BOUNDARY);
            sb.append("\r\n");
            sb.append("Content-Disposition: form-data;name=\"file\";filename=\""+ file.getName() + "\"\r\n");
            sb.append("Content-Type:application/octet-stream\r\n\r\n");
            byte[] head = sb.toString().getBytes("utf-8");
            // 获得输出流
            out = new DataOutputStream(con.getOutputStream());
            out.write(head);
            // 文件正文部分
            in = new DataInputStream(new FileInputStream(file));
            int bytes = 0;
            byte[] bufferOut = new byte[1024];
            while ((bytes = in.read(bufferOut)) != -1) {
                out.write(bufferOut, 0, bytes);
            }
            // 结尾部分
            byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");// 定义最后数据分隔线
            out.write(foot);
            out.flush();
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}finally{
			 try {
				in.close();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
    	 InputStream is = con.getInputStream();
    	 return getStreamAsString(is,"UTF-8");
    }
    
    /**
     * 
     * 将InputStream 转化为String
     * 
     * @param stream  inputstream
     * @param charset  字符集
     * @return
     * @throws IOException
     */
    private static String getStreamAsString(InputStream stream, String charset) throws IOException {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream, charset), 8192);
            StringWriter writer = new StringWriter();
            char[] chars = new char[8192];
            int count = 0;
            while ((count = reader.read(chars)) > 0) {
                writer.write(chars, 0, count);
            }
            return writer.toString();
        } finally {
            if (stream != null) {
                stream.close();
            }
        }
    }
    
    /** 获取上传图片路径
	 * @param imageFile
	 * @param prePath
	 * @param imageName
	 * @return
	 */
	private String getNewUserImage(MultipartFile imageFile, String prePath, String imageName)throws Exception{
		String path="";
		try {
			//构造服务器接收路径
			String relativeFilePath = imageFile.getOriginalFilename();
			String suffix = relativeFilePath.substring(relativeFilePath.lastIndexOf(".") + 1);
			Pattern p = Pattern.compile("jepg|jpg|png");
			if(!p.matcher(suffix).matches()){
				path="图片格式有误";
			}
			//文件夹路径
			relativeFilePath = prePath + imageName +"."+ suffix;
			//相对路径
			String rootPath = System.getProperty("smm.cuohe");
			//文件存储全部路径
			File destFile = new File(rootPath, relativeFilePath);
			if(!destFile.exists()){
				destFile.mkdirs();
			}
			try {
				imageFile.transferTo(destFile); //转移文件
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			path=rootPath+relativeFilePath;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return path;
	}
	
}
