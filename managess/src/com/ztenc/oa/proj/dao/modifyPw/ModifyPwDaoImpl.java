package com.ztenc.oa.proj.dao.modifyPw;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transaction;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.sql.Connection;

public class ModifyPwDaoImpl extends HibernateDaoSupport implements ModifyPwDao {
	
	/*
	 *
	 * */
	public String checkPassword(String username, String password){
		final String password1 = password;
		final String username1 = username;
		String rtn = "";
		rtn = (String)this.getHibernateTemplate().execute(
			new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				ResultSet rs =null;
				String rtn ="";
				String sql ="select username from user where password='"+password1+"'AND username='"+username1+"'";
				Connection conn = session.connection();
				Statement stmt =  conn.createStatement();
				rs = stmt.executeQuery(sql);
				if(rs.next()){
					rtn = rs.getString(1);
				}
				return rtn;
			}
		}
		);
		return rtn;
	}
	
	/*
	 *
	 * */
	public boolean updatePw(String password, String userName) {
		final String password_p = password;
		final String username = userName;
		try {
			this.getHibernateTemplate().execute(new HibernateCallback() {
				public Object doInHibernate(Session session) {
					String hql_a = "update  User set password ='"+ password_p + "' where  username= '" + username+"'";
					Query query_a = session.createQuery(hql_a);
					query_a.executeUpdate();
					session.flush();
					return null;
				}
			});
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
