/*
 * enumBooleanType.java
 *
 * Created on 
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.ztenc.oa.proj.util;

public enum enumBooleanType{
    /**
     * 成功
     */
    TRUE(1),
    /**
     * 失败
     */
    FALSE(0);

    private final int _num;

    enumBooleanType(int num){
        _num=num;
    }

    public static int getLength(){
        return enumBooleanType.values().length;
    }

    public int getValue(){
        return _num;
    }

    public static enumBooleanType getEnum(int num){
        switch(num){
            case 1:
                return TRUE;
            case 0:
                return FALSE;
            default:
                return null;
        }

    }

    public String getDescription(){
        String rtn=null;
        switch(this){
            case TRUE:
                rtn= "成功";
                break;
            case FALSE:
                rtn= "失败";
                break;

        }

        return rtn;
    }
}
    
