package com.zzboot.framework.core.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 验证码类型
 * @author zhonglh
 */

public enum EnumCaptchaType {


    MATH("1" , "计算方式") ,

    CHAR("2" , "字符方式") ;



    private String code ;
    private String name ;

    EnumCaptchaType(String code, String name) {
        this.code = code;
        this.name = name;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
