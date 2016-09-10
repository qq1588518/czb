/*
 * CEditViewAction.java
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
public class CEditViewAction extends action.common.CAction {
    
    /** Creates a new instance of CEditViewAction */
    public CEditViewAction() throws Exception {
         super();
    }
    
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String result = null;
        try{   
            result="editViewjsp";   
        }catch(Exception e){
            request.setAttribute("error", new CActionError(e));
        }
        return result;
    }
}
