package com.ztenc.oa.proj.dao.ServiceCount;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import com.ztenc.oa.proj.bean.Groups;
import com.ztenc.oa.proj.bean.Member;
import com.ztenc.oa.proj.bean.ProAnswer;
import com.ztenc.oa.proj.bean.Servicecount;




public class ServiceCountDaoImpl extends HibernateDaoSupport implements ServiceCountDao {
	
	
	
	public String updateInfo(String obj)throws DataAccessException{
		String rtn = "0";
		final String cataName = obj;
		rtn = (String)this.getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException,SQLException{
				String rtn = "0";
				try{
					String hql = "from Servicecount where cataName ='"+cataName+"'";
					Query query1 = session.createQuery(hql);
					List list = query1.list();
					int count = 0;
					if(list.size() > 0){
						for(int i=0;i<list.size();i++){
							Servicecount s = (Servicecount)list.get(i);
							count = s.getCount();
						}
						String sql = "update servicecount set count="+(count+1) +" where cataName ='"+cataName+"'";
						Query query = session.createSQLQuery(sql);
						query.executeUpdate();
						rtn = "1";
					}
				}catch(Exception e){
					System.out.println(e);
					//conn.close();
				}finally{
					//conn.close();
				}
				return rtn;
			}
		});
		return rtn ;
	}
	
	
	public String deleteInfo(String obj)throws DataAccessException{
		String rtn = "0";
		final String cataName = obj;
		rtn = (String)this.getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException,SQLException{
				String rtn = "0";
				try{
					String hql = "from Servicecount where cataName ='"+cataName+"'";
					Query query1 = session.createQuery(hql);
					List list = query1.list();
					int count = 0;
					if(list.size() > 0){
						for(int i=0;i<list.size();i++){
							Servicecount s = (Servicecount)list.get(i);
							count = s.getCount();
						}
						String sql = "update servicecount set count="+(count-1)+" where cataName ='"+cataName+"'";
						Query query = session.createSQLQuery(sql);
						query.executeUpdate();
						rtn = "1";
					}
				}catch(Exception e){
					System.out.println(e);
					//conn.close();
				}finally{
					//conn.close();
				}
				return rtn;
			}
		});
		return rtn ;
	}
}
