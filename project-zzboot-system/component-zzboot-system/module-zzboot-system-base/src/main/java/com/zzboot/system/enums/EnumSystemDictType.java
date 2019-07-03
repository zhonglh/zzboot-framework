package com.zzboot.system.enums;

import com.zzboot.framework.core.enums.EnumBase;

/**
 * 字典类型枚举
 * @author Administrator
 */
public enum EnumSystemDictType implements EnumBase<String> {


    USER_STATUS("user_status"),
    OPERATION_TYPE("operation_type"),
    ROLE_TYPE("role_type"),
    ORGAN_TYPE("organ_type"),
    YES_NO("yes_no"),
    LEAF("leaf"),
    DEP_STATUS("dep_status"),
    SHORTCUT("shortcut"),
    MSG_LANGUAGE("msg_language"),
    ORGAN_STATUS("organ_status"),
    FILE_ENGINE("file_engine"),
    ROLE_STATUS("role_status"),
    DICT_VAL("dict_val"),
    MSG_TEMPLET_TYPE("msg_templet_type"),

    FUND_TYPE("fund_type"),
    FUND_DIRECTION("fund_direction"),
    INVESTOR_TYPE("investor_type"),
    FUND_ACCOUNT_TYPE("fund_account_type"),
    MANAG_TYPE("manag_type"),
    CARD_TYPE("card_type"),

    ;


    private String code;


    EnumSystemDictType(String code) {
        this.code = code;
    }

    @Override
    public String getVal() {
        return code;
    }

    @Override
    public String getLabel() {
        return code;
    }

    @Override
    public String getValue() {
        return code;
    }




    @Override
    public EnumSystemDictType getEnum(String v){
        return EnumSystemDictType.getEnumByValue(v);
    }

    public static EnumSystemDictType getEnumByValue(String v){
        for(EnumSystemDictType enum1 : EnumSystemDictType.values()){
            if(enum1.code.equals(v)){
                return enum1;
            }
        }
        return  null;
    }
}
