/*
 * CDataSource.java
 *
 * Created on 
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.ztenc.oa.proj.database;

import java.sql.*;
import javax.naming.*;
public class CDataSource {
    
    public final static String CONNECTION_POOL = "java:comp/env";
    
    public final static String RESOURCE_NAME = "jdbc/OracleDB";
    private javax.sql.DataSource _data_source = null;
    
    public CDataSource() throws Exception {
        super();
    }
    
    public void setDataSource(javax.sql.DataSource ds){
        _data_source=ds;
    }
    
    protected javax.sql.DataSource getDataSource() throws Exception {
        return getDataSource(CONNECTION_POOL,RESOURCE_NAME);
    }
    
    protected javax.sql.DataSource getDataSource(String res_name) throws Exception {
        return getDataSource(CONNECTION_POOL,res_name);
    }
    
    protected javax.sql.DataSource getDataSource(String connc_pool,String res_name) throws Exception {
        Context initContext = new InitialContext();
        Context context = (Context) initContext.lookup(connc_pool);
        Object object = (Object) context.lookup(res_name);
        _data_source = (javax.sql.DataSource)object;
        
        return _data_source;
    }
    
    public java.sql.Connection getConnection() throws Exception {
        Connection conn = null;
        if(_data_source==null){
            _data_source=getDataSource();
        }
        conn = _data_source.getConnection();
        
        return conn;
    }
    
    public void finalize() {
        //super.finalize();
        _data_source=null;
        
    }
    
}
