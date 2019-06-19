package com.zzboot.framework.core.enums;

/**
 * 用户在线状态
 */
public enum EnumOnlineStatus {

    /**
     * 用户状态
     */
    ON_LINE("1" , "在线"), OFF_LINE("0" , "离线");



    private String code ;
    private String name ;

    EnumOnlineStatus(String code, String name) {
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
