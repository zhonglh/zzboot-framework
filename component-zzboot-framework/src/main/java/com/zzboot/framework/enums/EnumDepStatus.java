
package com.zzboot.framework.enums;
import com.zzboot.framework.core.enums.EnumBase;

/**
 * 字典类型 状态
 * @author Administrator
 */
public enum EnumDepStatus implements EnumBase<String> {

    normal("1","正常" ),
    disband("0","解散" ),
  ;

  EnumDepStatus(String theValue, String theName) {
        this.theValue = theValue;
        this.theName = theName;
    }

  String theValue ;
  String theName ;



    @Override
    public EnumDepStatus getEnum(String v){
        return EnumDepStatus.getEnumByValue(v);
    }

   public static EnumDepStatus getEnumByValue(String v){
       for(EnumDepStatus enum1 : EnumDepStatus.values()){
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