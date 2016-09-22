package com.smm.cuohe.test;

import com.smm.cuohe.util.URLRequest;
import net.sf.json.JSONObject;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 商城接口测试
 * Created by zhenghao on 2015/9/24.
 */
public class TestMallImpl {

    private static  String remote_host="http://testmall.smm.cn/Interface/";

    
    public void testAddUser() throws Exception {

        String url="addUser";

        Map<String,String> params=new HashMap<>();


        //企业联系人
        params.put("business_truename", "212121");
        //企业地区ID
        params.put("business_area","393");
        params.put("email","");
        params.put("qq","");
        // 企业名称
        params.put("business_company","反倒是发生的发生大幅放松放松的21212");
        params.put("address","");
        //联系电话
        params.put("tel","13312345688");
        params.put("ip","127.0.0.1");
        //行业
        params.put("industry","");
        //性质
        params.put("business_mode","合资");

        String result= URLRequest.post(TestMallImpl.remote_host + url, params);

        JSONObject data=JSONObject.fromObject(result);

        String status=data.getString("status");

        String msg=data.getString("msg");

        System.out.println("----status:"+status+"  msg:"+msg);


    }
}
