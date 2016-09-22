package com.smm.cuohe.util;

import com.smm.cuohe.tools.CommonException;

public class ExceptionUtil {
    public static CommonException newException(String retCode, String retMsg){
        return new CommonException(retCode, retMsg);
    }

}