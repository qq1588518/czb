// Decompiled by DJ v3.7.7.81 Copyright 2004 Atanas Neshkov  Date: 2007-8-26 19:04:22
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   MacBinaryDecoderOutputStream.java

package basic.base.test;

import java.io.*;

public class MacBinaryDecoderOutputStream extends FilterOutputStream
{

    public MacBinaryDecoderOutputStream(OutputStream out)
    {
        super(out);
        bytesFiltered = 0;
        dataForkLength = 0;
    }

    public void write(int b)
        throws IOException
    {
        if(bytesFiltered <= 86 && bytesFiltered >= 83)
        {
            int leftShift = (86 - bytesFiltered) * 8;
            dataForkLength = dataForkLength | (b & 0xff) << leftShift;
        } else
        if(bytesFiltered < 128 + dataForkLength && bytesFiltered >= 128)
            super.out.write(b);
        bytesFiltered++;
    }

    public void write(byte b[])
        throws IOException
    {
        write(b, 0, b.length);
    }

    public void write(byte b[], int off, int len)
        throws IOException
    {
        if(bytesFiltered >= 128 + dataForkLength)
            bytesFiltered += len;
        else
        if(bytesFiltered >= 128 && bytesFiltered + len <= 128 + dataForkLength)
        {
            super.out.write(b, off, len);
            bytesFiltered += len;
        } else
        {
            for(int i = 0; i < len; i++)
                write(b[off + i]);

        }
    }

    private int bytesFiltered;
    private int dataForkLength;
}