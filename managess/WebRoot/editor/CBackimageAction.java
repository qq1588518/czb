/*
 * CBackimageAction.java
 *
 * Created on 
 */

package action.editor;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import action.common.CActionError;
import java.sql.*;
/**
 *
 * @author  zq
 */
public class CBackimageAction extends action.common.CAction {
    
    /** Creates a new instance of CBackimageAction */
    public CBackimageAction() throws Exception {
         super();
    }
    
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String result = null;
        String _tableName="",_billNo="";
        try{   
            _tableName=request.getParameter("tableName")==null?"":request.getParameter("tableName");
            _billNo=request.getParameter("billNo")==null?"":request.getParameter("billNo");
            request.setAttribute("TableName", _tableName);
            request.setAttribute("BillNo", _billNo);
            result="backimagejsp";   
        }catch(Exception e){
            request.setAttribute("error", new CActionError(e));
        }
        return result;
    }
}
