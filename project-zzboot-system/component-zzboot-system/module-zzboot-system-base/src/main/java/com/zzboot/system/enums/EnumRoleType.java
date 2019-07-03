
package com.zzboot.system.enums;
import com.zzboot.framework.core.enums.EnumBase;

/**
 * 字典类型 角色类型
 * @author Administrator
 */
public enum EnumRoleType implements EnumBase<String> {


    SYSTEM_ROLE("1","系统角色" ),
    USER_ROLE ("2","用户角色"),
  ;

  EnumRoleType(String theValue, String theName) {
        this.theValue = theValue;
        this.theName = theName;
    }

  String theValue ;
  String theName ;


    @Override
    public EnumRoleType getEnum(String v){
        return EnumRoleType.getEnumByValue(v);
    }

   public static EnumRoleType getEnumByValue(String v){
       for(EnumRoleType enum1 : EnumRoleType.values()){
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