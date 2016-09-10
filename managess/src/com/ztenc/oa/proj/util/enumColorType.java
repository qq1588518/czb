package com.ztenc.oa.proj.util;

public enum enumColorType {
	
	
	RED((short)10),
	BLUE((short)12),
	YELLOW((short)13),
	GREEN((short)17),
	BLACK((short)8);
    public final short Index;

    enumColorType(short num){
    	Index=num;
    }

    public static int getLength(){
        return enumColorType.values().length;
    }

    public static enumColorType getEnum(int num){
        switch(num){
            case 10:
                return RED;
            case 12:
                return BLUE;
            case 13:
            	return YELLOW;
            case 17:
            	return GREEN;
            case 8:
            	return BLACK;
            default:
                return null;
        }

    }

    public String getDescription(){
        String rtn=null;
        switch(this){
            case RED:
                rtn= "红色";
                break;
            case BLUE:
                rtn= "蓝色";
            case YELLOW:
                rtn= "黄色";
                break;
            case GREEN:
                rtn= "绿色";
                break;
            case BLACK:
                rtn= "黑色";
                break;	
        }
        return rtn;
    }
}
