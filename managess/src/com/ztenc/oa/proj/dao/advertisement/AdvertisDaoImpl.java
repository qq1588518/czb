package com.ztenc.oa.proj.dao.advertisement;

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
import com.ztenc.oa.proj.bean.WlMessageboard;

public class AdvertisDaoImpl extends HibernateDaoSupport implements AdvertisDao {
	
	/**
	 * 查询所有的栏目信息
	 * 
	 * @param
	 * @return
	 */
	public List getpro() {
		return (List) this.getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				// TODO Auto-generated method stub
				String hql = "FROM Catagory where topproId = '0'";
				Query query = session.createQuery(hql);
				return query.list();
			}
		});
	}
	
	public List getGroups() {
		return (List) this.getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				// TODO Auto-generated method stub
				String hql = "FROM Groups";
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
	public ResultSet getprotype(String id) {
		ResultSet rs = null;
		final String protopId = id;
		rs = (ResultSet) this.getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = null;
				ResultSet rs = null;
				Connection conn = null;
				try {
					conn = session.connection();//
					String sql = "SELECT * FROM Advertisment order by id desc";
					rs = conn.createStatement().executeQuery(sql);
				} catch (Exception e) {
					conn.close();
					System.out.println(e);
				} finally {
					conn.close();
				}
				return rs;
			}
		});
		return rs;
	}
	
	/**
	 * 根据id删除栏目信息
	 * 
	 * @param
	 * @return
	 */
	public String deleteproType(String id, String delete) throws DataAccessException {
		String rtn = "0";
		final String proid = id;
		final String type = delete;
		rtn = (String) this.getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				String rtn = "";
				// Connection conn = null;
				// String sql = "";
				// String sql1 = "";
				// String sql2 = "";
				String hql = "";
				// String hql2 = "";
				System.out.println("type=" + type);
				// sql = "select id from catatory_con_group where sub_catagory_id = '"+proid+"'";
				hql = "delete from Advertisment WHERE id = '" + proid + "'";
				// hql2 ="delete from cata_group WHERE proId ='"+proid+"'";
				// Query queryCon = session.createSQLQuery(sql);
				// List list = queryCon.list();
				// for(int i=0;i<list.size();i++){
				// String id = (String) list.get(i);
				// sql1 = "delete from catagory_con WHERE id = '"+id+"'";
				// sql2 = "delete from catatory_con_group WHERE id = '"+id+"'";
				// Query query_sql2 = session.createSQLQuery(sql2);
				// query_sql2.executeUpdate();
				// Query query_sql1 = session.createSQLQuery(sql1);
				// query_sql1.executeUpdate();
				// }
				Query query = session.createSQLQuery(hql);
				query.executeUpdate();
				// Query query2 = session.createSQLQuery(hql2);
				// query2.executeUpdate();
				rtn = "1";
				return rtn;
			}
		});
		return rtn;
	}
	
	public String saveInfo(Object obj) throws DataAccessException {
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
	public Catagory getCatagoryId(String id) {
		Catagory catagory = (Catagory) this.getHibernateTemplate().get(Catagory.class, new String(id));
		return catagory;
	}
	
	/**
	 * 根据id获取业务信息
	 * 
	 * @param id
	 * @return
	 */
	public CataGroup getCataGroupId(String id) {
		CataGroup cataGroup = (CataGroup) this.getHibernateTemplate().get(Catagory.class, new String(id));
		return cataGroup;
	}
	
	public String updateInfo(Object obj) throws DataAccessException {
		
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
				String hql = "from Advertisment where id = " + _id;
				Query query = session.createQuery(hql);
				return query.list();
			}
		});
	}
}
