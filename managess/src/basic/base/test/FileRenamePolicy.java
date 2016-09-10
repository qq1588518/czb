// Decompiled by DJ v3.7.7.81 Copyright 2004 Atanas Neshkov  Date: 2007-8-26 17:02:23
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   FileRenamePolicy.java

package basic.base.test;

import java.io.File;

public interface FileRenamePolicy
{

    public abstract File rename(File file);
}