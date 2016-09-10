// Decompiled by DJ v3.7.7.81 Copyright 2004 Atanas Neshkov  Date: 2007-8-26 15:40:42
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   MultipartParser.java

package basic.base.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Vector;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

// Referenced classes of package com.oreilly.servlet.multipart:
//            BufferedServletInputStream, LimitedServletInputStream, ParamPart, FilePart, 
//            Part

public class MultipartParser
{

    public MultipartParser(HttpServletRequest req, int maxSize)
        throws IOException
    {
        this(req, maxSize, true, true);
    }

    public MultipartParser(HttpServletRequest req, int maxSize, boolean buffer, boolean limitLength)
        throws IOException
    {
        this(req, maxSize, buffer, limitLength, null);
    }

    public MultipartParser(HttpServletRequest req, int maxSize, boolean buffer, boolean limitLength, String encoding)
        throws IOException
    {
        buf = new byte[8192];
        this.encoding = DEFAULT_ENCODING;
        if(encoding != null)
            setEncoding(encoding);
        String type = null;
        String type1 = req.getHeader("Content-Type");
        String type2 = req.getContentType();
        if(type1 == null && type2 != null)
            type = type2;
        else
        if(type2 == null && type1 != null)
            type = type1;
        else
        if(type1 != null && type2 != null)
            type = type1.length() <= type2.length() ? type2 : type1;
    if(type == null || !type.toLowerCase().startsWith("multipart/form-data"))
            throw new IOException("Posted content type isn't multipart/form-data");
        int length = req.getContentLength();
        if(length > maxSize)
            throw new IOException("您上传的文件大小为" + length/1024 + "k ，超过了 " + maxSize/1024+"k,最大不能超过"+ maxSize/1024+"k");
        String boundary = extractBoundary(type);
        if(boundary == null)
            throw new IOException("Separation boundary was not specified");
        ServletInputStream in = req.getInputStream();
        if(buffer)
            in = new BufferedServletInputStream(in);
        if(limitLength)
            in = new LimitedServletInputStream(in, length);
        this.in = in;
        this.boundary = boundary;
        String line;
        do
        {
            line = readLine();
            if(line == null)
                throw new IOException("Corrupt form data: premature ending");
        } while(!line.startsWith(boundary));
    }

    public void setEncoding(String encoding)
    {
        this.encoding = encoding;
    }

    public Part readNextPart()
        throws IOException
    {
        if(lastFilePart != null)
        {
            lastFilePart.getInputStream().close();
            lastFilePart = null;
        }
        Vector headers = new Vector();
        String line = readLine();
        if(line == null)
            return null;
        if(line.length() == 0)
            return null;
        String nextLine;
        for(; line != null && line.length() > 0; line = nextLine)
        {
            nextLine = null;
            boolean getNextLine = true;
            while(getNextLine) 
            {
                nextLine = readLine();
                if(nextLine != null && (nextLine.startsWith(" ") || nextLine.startsWith("\t")))
                    line = line + nextLine;
                else
                    getNextLine = false;
            }
            headers.addElement(line);
        }

        if(line == null)
            return null;
        String name = null;
        String filename = null;
        String origname = null;
        String contentType = "text/plain";
        for(Enumeration enumer = headers.elements(); enumer.hasMoreElements();)
        {
            String headerline = (String)enumer.nextElement();
            if(headerline.toLowerCase().startsWith("content-disposition:"))
            {
                String dispInfo[] = extractDispositionInfo(headerline);
                name = dispInfo[1];
                filename = dispInfo[2];
                origname = dispInfo[3];
            } else
            if(headerline.toLowerCase().startsWith("content-type:"))
            {
                String type = extractContentType(headerline);
                if(type != null)
                    contentType = type;
            }
        }

        if(filename == null)
            return new ParamPart(name, in, boundary, encoding);
        if(filename.equals(""))
            filename = null;
        lastFilePart = new FilePart(name, in, boundary, contentType, filename, origname);
        return lastFilePart;
    }

    private String extractBoundary(String line)
    {
        int index = line.lastIndexOf("boundary=");
        if(index == -1)
            return null;
        String boundary = line.substring(index + 9);
        if(boundary.charAt(0) == '"')
        {
            index = boundary.lastIndexOf('"');
            boundary = boundary.substring(1, index);
        }
        boundary = "--" + boundary;
        return boundary;
    }

    private String[] extractDispositionInfo(String line)
        throws IOException
    {
        String retval[] = new String[4];
        String origline = line;
        line = origline.toLowerCase();
        int start = line.indexOf("content-disposition: ");
        int end = line.indexOf(";");
        if(start == -1 || end == -1)
            throw new IOException("Content disposition corrupt: " + origline);
        String disposition = line.substring(start + 21, end);
        if(!disposition.equals("form-data"))
            throw new IOException("Invalid content disposition: " + disposition);
        start = line.indexOf("name=\"", end);
        end = line.indexOf("\"", start + 7);
        int startOffset = 6;
        if(start == -1 || end == -1)
        {
            start = line.indexOf("name=", end);
            end = line.indexOf(";", start + 6);
            if(start == -1)
                throw new IOException("Content disposition corrupt: " + origline);
            if(end == -1)
                end = line.length();
            startOffset = 5;
        }
        String name = origline.substring(start + startOffset, end);
        String filename = null;
        String origname = null;
        start = line.indexOf("filename=\"", end + 2);
        end = line.indexOf("\"", start + 10);
        if(start != -1 && end != -1)
        {
            filename = origline.substring(start + 10, end);
            origname = filename;
            int slash = Math.max(filename.lastIndexOf('/'), filename.lastIndexOf('\\'));
            if(slash > -1)
                filename = filename.substring(slash + 1);
        }
        retval[0] = disposition;
        retval[1] = name;
        retval[2] = filename;
        retval[3] = origname;
        return retval;
    }

    private static String extractContentType(String line)
        throws IOException
    {
        line = line.toLowerCase();
        int end = line.indexOf(";");
        if(end == -1)
            end = line.length();
        return line.substring(13, end).trim();
    }

    private String readLine()
        throws IOException
    {
        StringBuffer sbuf = new StringBuffer();
        int result;
        do
        {
            result = in.readLine(buf, 0, buf.length);
            if(result != -1)
                sbuf.append(new String(buf, 0, result, encoding));
        } while(result == buf.length);
        if(sbuf.length() == 0)
            return null;
        int len = sbuf.length();
        if(len >= 2 && sbuf.charAt(len - 2) == '\r')
            sbuf.setLength(len - 2);
        else
        if(len >= 1 && sbuf.charAt(len - 1) == '\n')
            sbuf.setLength(len - 1);
        return sbuf.toString();
    }

    private ServletInputStream in;
    private String boundary;
    private FilePart lastFilePart;
    private byte buf[];
    private static String DEFAULT_ENCODING = "ISO-8859-1";
    private String encoding;

}