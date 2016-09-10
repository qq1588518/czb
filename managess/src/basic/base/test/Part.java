// Decompiled by DJ v3.7.7.81 Copyright 2004 Atanas Neshkov  Date: 2007-8-26 15:54:56
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   Part.java

package basic.base.test;


public abstract class Part
{

    Part(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public boolean isFile()
    {
        return false;
    }

    public boolean isParam()
    {
        return false;
    }

    private String name;
}