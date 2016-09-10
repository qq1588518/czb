package com.ztenc.oa.proj.dao.column;

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
import com.ztenc.oa.proj.bean.CatagoryCon;
import com.ztenc.oa.proj.bean.WlMessageboard;

public class ColumnConDaoImpl extends HibernateDaoSupport implements ColumnConDao {
	
	/**
	 * 查询所有的栏目信息
	 * 
	 * @param
	 * @return
	 */
	public List getpro(String proid) {
		final String id = proid;
		return (List) this.getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				// TODO Auto-generated method stub
				String hql = "FROM Catagory where topproId = '" + id + "'";
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
	
	public List listPro(String id) {
		final String _id = id;
		return (List) this.getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				// TODO Auto-generated method stub
				String hql = "FROM Catagory where topproId = '" + _id + "'";
				Query query = session.createQuery(hql);
				return query.list();
			}
		});
	}
	
	public List getlistCatagorycon(String id) {
		final String _id = id;
		return (List) this.getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				// TODO Auto-generated method stub
				String hql = "from CatagoryCon where id = '" + _id + "'";
				Query query = session.createQuery(hql);
				return query.list();
			}
		});
	}
	
	public List getCatgroupcon(String id) {
		final String _id = id;
		return (List) this.getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				// TODO Auto-generated method stub
				String hql = "from CatatoryConGroup where id = '" + _id + "'";
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
	public ResultSet getprotype(String title, String sub_protype, int index, int length) {
		ResultSet rs = null;
		final String _title = title;
		final String _sub_protype = sub_protype;
		final int _index = index;
		final int _length = length;
		System.out.println("title=" + _title);
		System.out.println("index=" + _index);
		System.out.println("length=" + _length);
		rs = (ResultSet) this.getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = null;
				ResultSet rs = null;
				Connection conn = null;
				String tmp_name = "";
				String _tmp_name = "";
				try {
					conn = session.connection();//
					String sql = "";
					
					if (!_title.equals("")) {
						tmp_name = "  and cc.title like '%" + _title + "%'";
						_tmp_name = "  and cc2.title like '%" + _title + "%'";
					}
					if (!_sub_protype.equals("")) {
						
						tmp_name += " and ccg.sub_catagory_id='" + _sub_protype + "'";
						_tmp_name += " and ccg2.sub_catagory_id='" + _sub_protype + "'";
					}
					// sql =
					// "select * from product cc ,product2group ccg,groups gs,producttype c cross join(select count(*) from product where 1=1 "+_tmp_name+" ) a where cc.productno = ccg.productno and ccg.groupno = gs.groupid and cc.typeno = c.typeno "+tmp_name+" order by cc.level,cc.createdate desc limit "+_index+","+_length;
					sql = "select * from catagory_con cc ,catatory_con_group ccg,groups gs,catagory c cross join(select count(*) from catagory_con cc2 ,catatory_con_group ccg2,groups gs2,catagory c2 where cc2.id = ccg2.id and ccg2.groupid = gs2.groupid and ccg2.sub_catagory_id = c2.proId "
							+ _tmp_name
							+ " ) a where cc.id = ccg.id and ccg.groupid = gs.groupid and ccg.sub_catagory_id = c.proId "
							+ tmp_name
							+ " order by cc.dates desc limit "
							+ _index
							+ ","
							+ _length;
					
					System.out.println("hql:===" + sql);
					/*
					 * if(_title.equals("")){ sql = "select * from catagory_con cc ,catatory_con_group ccg,groups gs,catagory c cross join(select count(*) from catagory_con where 1=1"+_tmp_name+
					 * " ) a where cc.id = ccg.id and ccg.groupid = gs.groupid and ccg.sub_catagory_id = c.proId "+tmp_name+" order by cc.dates desc limit "+_index+","+_length; }else{ sql =
					 * "select * from catagory_con cc ,catatory_con_group ccg,groups gs,catagory c cross join(select count(*) from catagory_con ) a where cc.id = ccg.id and ccg.groupid = gs.groupid and ccg.sub_catagory_id = c.proId and cc.title like '%"
					 * +_title+"%' order by cc.dates desc limit "+_index+","+_length; }
					 */
					// String sql =
					// "select * from catagory_con cc ,catatory_con_group ccg,groups gs,catagory c cross join(select count(*) from catagory_con ) a where cc.id = ccg.id and ccg.groupid = gs.groupid and ccg.sub_catagory_id = c.proId";
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
	public String deleteproType(String id) throws DataAccessException {
		String rtn = "0";
		final String _id = id;
		rtn = (String) this.getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				String rtn = "";
				String hql = "";
				String hql2 = "";
				try {
					hql = "delete from catagory_con WHERE id = '" + _id + "'";
					hql2 = "delete from catatory_con_group WHERE id ='" + _id + "'";
					Query query = session.createSQLQuery(hql);
					query.executeUpdate();
					Query query2 = session.createSQLQuery(hql2);
					query2.executeUpdate();
					rtn = "1";
				} catch (Exception e) {
					System.out.println(e);
					// conn.close();
				} finally {
					// conn.close();
				}
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
	public CatagoryCon getCatagoryConId(String id) {
		CatagoryCon catagory = (CatagoryCon) this.getHibernateTemplate().get(CatagoryCon.class, new String(id));
		return catagory;
	}
	
	public String updateInfo(Object obj) throws DataAccessException {
		
		String rtn = "0";
		this.getHibernateTemplate().update(obj);
		rtn = "1";
		
		return rtn;
	}
	
}
