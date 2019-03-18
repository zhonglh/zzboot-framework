package com.zzboot.framework.core.enums;

/**
 * 树的节点状态
 * @author zhonglh
 */

public enum EnumTreeState implements EnumBase<String> {

    OPEN("open"),
    CLOSE("closed");


    EnumTreeState(String theValue) {
        this.theValue = theValue;
    }

    String theValue ;


    public String getTheValue() {
        return theValue;
    }

    @Override
    public String getVal() {
        return theValue;
    }

    @Override
    public String getLabel() {
        return theValue;
    }

    @Override
    public EnumBase<String> getEnum(String val) {
        for(EnumTreeState state : EnumTreeState.values()){
            if(state.theValue.equals(val)){
                return state;
            }
        }
        return null;
    }

    @Override
    public String getValue() {
        return theValue;
    }



}
