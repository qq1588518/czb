/*
 * CEditUploadAction.java
 *
 * Created on 
 */

package action.editor;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import action.common.CActionError;
import com.jspsmart.upload.*;
import java.sql.*;
import com.system.*;
import basic.*;
import java.util.*;
import com.webeditor.*;


/**
 *
 * @author  zq
 */
public class CEditUploadAction extends action.common.CAction {
    
    /** Creates a new instance of CEditUploadAction */
    public CEditUploadAction() throws Exception {
         super();
    }
    
    public String execute(HttpServletRequest request,HttpServletResponse response) throws Exception {
        String result = null;
        byte[] in_byte=null;
        try{
            String _billno=request.getParameter("billNo")==null?"":request.getParameter("billNo");
            String _tableName=request.getParameter("tableName")==null?"":request.getParameter("tableName");
            String _no=request.getParameter("no")==null?"":request.getParameter("no");
            CBLOBInEditor BLOBInEditor=new CBLOBInEditor();
            String contenttype="";
            ByteArrayInputStream in=null;
            SmartUpload mySmartUpload=new SmartUpload();
            mySmartUpload.initialize(this.getActionServlet().getServletConfig(),request,response);
            mySmartUpload.upload();
            for(int i=0;i<mySmartUpload.getFiles().getCount();i++){
                com.jspsmart.upload.File myFile=mySmartUpload.getFiles().getFile(i);
                boolean rtn=myFile.isMissing();
                if(!rtn){
                   byte[] fileByte=new byte[myFile.getSize()];
                   for(int j=0;j<myFile.getSize();j++){
                       fileByte[j]=myFile.getBinaryData(j);
                       contenttype=myFile.getContentType();
                   }
                   in=new ByteArrayInputStream(fileByte);
                   in_byte=new byte[in.available()];
                   in.read(in_byte);
                   BLOBInEditor.setArrContent(in_byte);
                }     
            }
            BLOBInEditor.setNo(_no);
            BLOBInEditor.setBillNo(_billno);
            BLOBInEditor.setTableName(_tableName);
            BLOBInEditor.setContentType(contenttype);
            BLOBInEditor.update();
            result="editUploadjsp";
        }catch(Exception e){
            e.getMessage();
        }
        return result;
    }
}
