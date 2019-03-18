
package com.zzboot.framework.enums;
import com.zzboot.framework.core.enums.EnumBase;

/**
 * 字典类型 实体类型
 * @author Administrator
 */
public enum EnumEntityType implements EnumBase<String> {

  ;

  EnumEntityType(String theValue, String theName) {
        this.theValue = theValue;
        this.theName = theName;
    }

  String theValue ;
  String theName ;




    @Override
    public EnumEntityType getEnum(String v){
        return EnumEntityType.getEnumByValue(v);
    }

   public static EnumEntityType getEnumByValue(String v){
       for(EnumEntityType enum1 : EnumEntityType.values()){
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