package com.guohui.o2o.utils;

import javax.servlet.http.HttpServletRequest;


/**
 * 验证码校验的工具
 */
public class CodeUtil {

    public static boolean checkVerifyCode(HttpServletRequest request){
        String verifyCodeExpected =(String) request.getSession().getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);

        String verifyCodeActual = HttpServletRequestUtil.getString(request,"verifyCodeActual");

        if (verifyCodeActual == null || !verifyCodeActual.equals(verifyCodeExpected)){
            return false;
        }
        return true;
    }
}
