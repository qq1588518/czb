package com.ztenc.oa.proj.dao.search;

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

import com.ztenc.oa.proj.bean.CataGroup;
import com.ztenc.oa.proj.bean.Catagory;
import com.ztenc.oa.proj.bean.SearchInfo;
import com.ztenc.oa.proj.bean.WlMessageboard;




public class SearchDaoImpl extends HibernateDaoSupport implements SearchDao {
	
	
	/**
	 * 查询所有的栏目信息
	 * 
	 * @param 
	 * @return
	 */
	public List getpro(){
		return (List)this.getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				String hql ="FROM Catagory where topproId = '0'";
				Query query = session.createQuery(hql);
				return query.list();
			}
		});
	}
	
	/**
	 * 进入管理界面读取所有的栏目信息
	 * 
	 * @param id
	 * @return
	 */
	
	public List getprotype(){
		return (List)this.getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				String hql ="FROM SearchInfo";
				Query query = session.createQuery(hql);
				return query.list();
			}
		});
	}
	
	
	/**
	 * 根据id删除栏目信息
	 * 
	 * @param 
	 * @return
	 */
	public String deleteproType(String id)throws DataAccessException{
		String rtn = "0";
		final String proid = id;
		rtn = (String)this.getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException,SQLException{
				String rtn = "";
				String hql = "";
				try{
						hql = "delete from Search_info WHERE id = '"+proid+"'" ;
						Query query = session.createSQLQuery(hql);
						query.executeUpdate();
					rtn = "1";
				}catch(Exception e){
					System.out.println(e);
				}finally{
				}
				return rtn;
			}
		});
		return rtn ;
	}

	public String saveInfo(Object obj)throws DataAccessException{
		String rtn = "0";
		Serializable serializable = this.getHibernateTemplate().save(obj);
		rtn = "1";
		
		return rtn;
	}
	
	/**
	 * 根据id获取业务信息
	 * 
	 * @param id
	 * @return
	 */
	public SearchInfo getSearchId(int id) {
		SearchInfo searchInfo = (SearchInfo) this.getHibernateTemplate().get(
				SearchInfo.class, new Integer(id));
		return searchInfo;
	}
	
	
	public String updateInfo(Object obj)throws DataAccessException{
		
		String rtn = "0";
		this.getHibernateTemplate().update(obj);
		rtn = "1";
		
		return rtn;
	}
	
	// modify by Tim 20110913
	public List getProductInfo(String id) {
		final String _id = id;
		return (List) this.getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				// TODO Auto-generated method stub
				String hql = "from SearchInfo where id = " + _id;
				Query query = session.createQuery(hql);
				return query.list();
			}
		});
	}
}
