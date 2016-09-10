// Decompiled by DJ v3.7.7.81 Copyright 2004 Atanas Neshkov  Date: 2007-8-26 18:59:53
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   FilePart.java

package basic.base.test;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.ServletInputStream;

import com.ztenc.oa.proj.util.CUtil;

// Referenced classes of package com.oreilly.servlet.multipart:
//            Part, PartInputStream, MacBinaryDecoderOutputStream, FileRenamePolicy

public class FilePart extends Part
{

    FilePart(String name, ServletInputStream in, String boundary, String contentType, String fileName, String filePath)
        throws IOException
    {
        super(name);
        this.fileName = fileName;
        this.filePath = filePath;
        this.contentType = contentType;
        partInput = new PartInputStream(in, boundary);
    }

    public void setRenamePolicy(FileRenamePolicy policy)
    {
        this.policy = policy;
    }

    public String getFileName()
    {
        return fileName;
    }
    public void setFileName()
    {
    	this.fileName = fileName;
    }

    public String getFilePath()
    {
        return filePath;
    }

    public String getContentType()
    {
        return contentType;
    }

    public InputStream getInputStream()
    {
        return partInput;
    }

    public long writeTo(File fileOrDirectory)
        throws IOException
    {
        long written = 0L;
        OutputStream fileOut = null;
        try
        {
            if(fileName != null)
            {
                File file;
                if(fileOrDirectory.isDirectory())
                    file = new File(fileOrDirectory, fileName);
                else
                    file = fileOrDirectory;
                if(policy != null)
                {
                    file = policy.rename(file);
                    fileName = file.getName();
                }
                fileOut = new BufferedOutputStream(new FileOutputStream(file));
                written = write(fileOut);
            }
        }
        finally
        {
            if(fileOut != null)
                fileOut.close();
        }
        return written;
    }
    
    
    public String writeTo(File fileOrDirectory,String fileName,boolean flag)
    throws IOException
{
    long written = 0L;
    String name=fileName;
    OutputStream fileOut = null;
    SimpleDateFormat tempDate = new SimpleDateFormat("yyyyMMddHHmmss");
	Calendar p_cal = Calendar.getInstance();
    java.util.Date p_date = p_cal.getTime();
	String datetime = tempDate.format(p_date);
	System.out.println("datetime="+datetime);
    
	if(flag){
		
	}else{
		int pos = name.lastIndexOf(".");
		String lastname = name.substring(pos,name.length());
		String firstName = name.substring(0,pos);
		name= datetime;
		name = datetime+lastname;
	}
    try
    {
        if(name != null)
        {
            File file;
            if(fileOrDirectory.isDirectory())
                file = new File(fileOrDirectory, name);
            else
                file = fileOrDirectory;
            if(policy != null)
            {
                file = policy.rename(file);
                name = file.getName();
            }
            fileOut = new BufferedOutputStream(new FileOutputStream(file));
            written = write(fileOut);
        }
    }
    finally
    {
        if(fileOut != null)
            fileOut.close();
    }
    return name;
}
    
    
    
    public String writeTo(File fileOrDirectory,String fileName)
    throws IOException
{
    long written = 0L;
    String name=fileName;
    OutputStream fileOut = null;
    SimpleDateFormat tempDate = new SimpleDateFormat("yyyyMMddHHmmss");
	Calendar p_cal = Calendar.getInstance();
    java.util.Date p_date = p_cal.getTime();
	String datetime = tempDate.format(p_date);
	System.out.println("datetime="+datetime);
    int pos = name.lastIndexOf(".");
	String lastname = name.substring(pos,name.length());
	String firstName = name.substring(0,pos);
	name= datetime+CUtil.createBillNo(6);
	name = name+lastname;
    try
    { 
        if(name != null)
        {
            File file;
            if(fileOrDirectory.isDirectory())
                file = new File(fileOrDirectory, name);
            else
                file = fileOrDirectory;
            if(policy != null)
            {
                file = policy.rename(file);
                name = file.getName();
            }
            fileOut = new BufferedOutputStream(new FileOutputStream(file));
            written = write(fileOut);
        }
    }
    finally
    {
        if(fileOut != null)
            fileOut.close();
    }
    return name;
}
    
    
    

    public long writeTo(OutputStream out)
        throws IOException
    {
        long size = 0L;
        if(fileName != null)
            size = write(out);
        return size;
    }

    long write(OutputStream out)
        throws IOException
    {
        if(contentType.equals("application/x-macbinary"))
            out = new MacBinaryDecoderOutputStream(out);
        long size = 0L;
        byte buf[] = new byte[8192];
        int i;
        while((i = partInput.read(buf)) != -1) 
        {
            out.write(buf, 0, i);
            size += i;
        }
        return size;
    }

    public boolean isFile()
    {
        return true;
    }

    private String fileName;
    private String filePath;
    private String contentType;
    private PartInputStream partInput;
    private FileRenamePolicy policy;
}