
package com.zzboot.framework.enums;
import com.zzboot.framework.core.enums.EnumBase;

/**
 * 字典类型 状态
 * @author Administrator
 */
public enum EnumOrganStatus implements EnumBase<String> {

    normal("1","正常" ),
    disband("0","解散" ),
    ;
  ;

  EnumOrganStatus(String theValue, String theName) {
        this.theValue = theValue;
        this.theName = theName;
    }

  String theValue ;
  String theName ;





    @Override
    public EnumOrganStatus getEnum(String v){
        return EnumOrganStatus.getEnumByValue(v);
    }

   public static EnumOrganStatus getEnumByValue(String v){
       for(EnumOrganStatus enum1 : EnumOrganStatus.values()){
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