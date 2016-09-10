// Decompiled by DJ v3.7.7.81 Copyright 2004 Atanas Neshkov  Date: 2007-8-26 15:55:33
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   ParamPart.java

package basic.base.test;

import java.io.*;
import javax.servlet.ServletInputStream;

// Referenced classes of package com.oreilly.servlet.multipart:
//            Part, PartInputStream

public class ParamPart extends Part
{

    ParamPart(String name, ServletInputStream in, String boundary, String encoding)
        throws IOException
    {
        super(name);
        this.encoding = encoding;
        PartInputStream pis = new PartInputStream(in, boundary);
        ByteArrayOutputStream baos = new ByteArrayOutputStream(512);
        byte buf[] = new byte[128];
        int i;
        while((i = pis.read(buf)) != -1) 
            baos.write(buf, 0, i);
        pis.close();
        baos.close();
        value = baos.toByteArray();
    }

    public byte[] getValue()
    {
        return value;
    }

    public String getStringValue()
        throws UnsupportedEncodingException
    {
        return getStringValue(encoding);
    }

    public String getStringValue(String encoding)
        throws UnsupportedEncodingException
    {
        return new String(value, encoding);
    }

    public boolean isParam()
    {
        return true;
    }

    private byte value[];
    private String encoding;
}