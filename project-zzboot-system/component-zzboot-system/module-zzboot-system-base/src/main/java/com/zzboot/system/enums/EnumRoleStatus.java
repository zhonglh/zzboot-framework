
package com.zzboot.system.enums;

import com.zzboot.framework.core.enums.EnumBase;

/**
 * 字典类型 角色状态
 * @author Administrator
 */
public enum EnumRoleStatus implements EnumBase<String> {

    normal("1","正常" ),
    invalid ("0","失效"),
  ;

  EnumRoleStatus(String theValue, String theName) {
        this.theValue = theValue;
        this.theName = theName;
    }

  String theValue ;
  String theName ;




    @Override
    public EnumRoleStatus getEnum(String v){
        return EnumRoleStatus.getEnumByValue(v);
    }

   public static EnumRoleStatus getEnumByValue(String v){
       for(EnumRoleStatus enum1 : EnumRoleStatus.values()){
           if(enum1.theValue.equals(v)){
               return enum1;
           }
       }
       return  null;
   }

    @Override
    public String getVal() {
        return theValue;
    }

    @Override
    public String getLabel() {
        return theName;
    }

    @Override
    public String getValue() {
        return theValue;
    }
}