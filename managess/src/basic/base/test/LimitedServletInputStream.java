// Decompiled by DJ v3.7.7.81 Copyright 2004 Atanas Neshkov  Date: 2007-8-26 19:06:19
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   LimitedServletInputStream.java

package basic.base.test;

import java.io.IOException;
import java.io.InputStream;
import javax.servlet.ServletInputStream;

public class LimitedServletInputStream extends ServletInputStream
{

    public LimitedServletInputStream(ServletInputStream in, int totalExpected)
    {
        totalRead = 0;
        this.in = in;
        this.totalExpected = totalExpected;
    }

    public int readLine(byte b[], int off, int len)
        throws IOException
    {
        int left = totalExpected - totalRead;
        if(left <= 0)
            return -1;
        int result = in.readLine(b, off, Math.min(left, len));
        if(result > 0)
            totalRead += result;
        return result;
    }

    public int read()
        throws IOException
    {
        if(totalRead >= totalExpected)
            return -1;
        int result = in.read();
        if(result != -1)
            totalRead++;
        return result;
    }

    public int read(byte b[], int off, int len)
        throws IOException
    {
        int left = totalExpected - totalRead;
        if(left <= 0)
            return -1;
        int result = in.read(b, off, Math.min(left, len));
        if(result > 0)
            totalRead += result;
        return result;
    }

    private ServletInputStream in;
    private int totalExpected;
    private int totalRead;
}