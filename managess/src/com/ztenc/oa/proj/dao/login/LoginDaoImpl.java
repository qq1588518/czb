package com.ztenc.oa.proj.dao.login;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transaction;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class LoginDaoImpl extends HibernateDaoSupport implements LoginDao {

	public ResultSet getUser(String name,String password) {
		// TODO Auto-generated method stub
		final String userName=name;
		final String Password=password;
		
		ResultSet rs = (ResultSet)this.getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				// TODO Auto-generated method stub
				ResultSet rs = null;
				String hql ="select username from user where password='"+Password+"'AND username='"+userName+"'";
				Connection conn = session.connection();
				Statement stmt =  conn.createStatement();
				rs = stmt.executeQuery(hql);
				return rs;
			}
		});
		
		return rs;
	}
	
	
}
