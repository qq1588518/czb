package com.ztenc.oa.proj.dao.CorporateCulture;

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
import com.ztenc.oa.proj.bean.Corporateculture;
import com.ztenc.oa.proj.bean.WlMessageboard;




public class CorporateCultureDaoImpl extends HibernateDaoSupport implements CorporateCultureDao {
	
	/**
	 * 获取所有的列表信息，
	 * 
	 * @param id
	 * @return
	 */
	public List getCorporate(String id){
		final String _id = id;
		return (List)this.getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				// TODO Auto-generated method stub
				String hql ="from Corporateculture where id = '"+_id+"'";
				Query query = session.createQuery(hql);
				return query.list();
			}
		});
	}
	

	/**
	 * 进入管理界面读取所有的信息列表
	 * 
	 * @param title,index,length
	 * @return
	 */
	public ResultSet getprotype(String title,int index,int length){
		ResultSet rs = null;
		final String _title = title;
		final int _index = index;
		final int _length = length;
		rs = (ResultSet)this.getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = null;
				ResultSet rs = null;
				Connection conn = null;
				try{
					conn = session.connection();//
					String sql ="";
					if(_title.equals("")){
						sql = "select * from CorporateCulture cross join (select count(*) from CorporateCulture) a order by id desc limit "+_index+","+_length;
					}else{
						sql = "select * from CorporateCulture cross join (select count(*) from CorporateCulture where title like '%"+_title+"%') a where title like '%"+_title+"%' order by id desc limit "+_index+","+_length;
					}
					rs = conn.createStatement().executeQuery(sql);
				}catch(Exception e){
					conn.close();
					System.out.println(e);
				}finally{
					conn.close();
				}
				return rs;
			}
		});
		return rs;
	}
	
	/**
	 * 删除信息
	 * 
	 * @param id
	 * @return
	 */
	public String deleteproType(String id)throws DataAccessException{
		String rtn = "0";
		final String _id = id;
		rtn = (String)this.getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException,SQLException{
				String rtn = "";
				String hql = "";
				String hql2 = "";
					hql = "delete from CorporateCulture WHERE id = '"+_id+"'" ;
					Query query = session.createSQLQuery(hql);
					query.executeUpdate();
					rtn = "1";
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
	public Corporateculture getCorporatecultureId(int id) {
		Corporateculture corporateculture = (Corporateculture) this.getHibernateTemplate().get(
				Corporateculture.class, id);
		return corporateculture;
	}
	
	
	public String updateInfo(Object obj)throws DataAccessException{
		String rtn = "0";
		this.getHibernateTemplate().update(obj);
		rtn = "1";
		return rtn;
	}

	
	
}
