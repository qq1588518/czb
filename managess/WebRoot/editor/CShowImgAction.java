/*
 * CShowImgAction.java
 *
 * Created on 
 */

package action.editor;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.webeditor.*;
import action.common.CActionError;
import basic.IObject;
/**
 *
 * @author  zq
 */
public class CShowImgAction extends action.common.CAction {
    
    /** Creates a new instance of CShowImgAction */
    public CShowImgAction() throws Exception {
         super();
    }
    
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String result = null;
        String _No="";
        _No=request.getParameter("no")==null?"":request.getParameter("no");
        try{
            CBLOBInEditor BlobInEditor=new CBLOBInEditor();
            BlobInEditor.open(_No);
            request.setAttribute("BLOBInEditor",BlobInEditor); 
            result="showImgjsp";
        }catch(Exception e){
            e.getMessage();
        }
        return result;
    }
}
