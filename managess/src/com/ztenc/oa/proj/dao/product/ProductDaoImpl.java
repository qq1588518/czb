package com.ztenc.oa.proj.dao.product;

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

import com.ztenc.oa.proj.bean.CatagoryCon;
import com.ztenc.oa.proj.bean.Product2group;
import com.ztenc.oa.proj.bean.Producttype;
import com.ztenc.oa.proj.bean.Product;

import com.ztenc.oa.proj.bean.Product;

public class ProductDaoImpl extends HibernateDaoSupport implements ProductDao {

	public String deleteProduct(String id) {
		String rtn = "0";
		final String _id = id;
		rtn = (String)this.getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException,SQLException{
				String rtn = "";
				String hql = "";
				String hql2 = "";
				try{
					hql = "delete from product WHERE productno = '"+_id+"'" ;
					hql2 ="delete from product2group WHERE productno ='"+_id+"'";
					Query query = session.createSQLQuery(hql);
					query.executeUpdate();
					Query query2 = session.createSQLQuery(hql2);
					query2.executeUpdate();
					rtn = "1";
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

	public List getGroups() {
		return (List)this.getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				// TODO Auto-generated method stub
				String hql ="FROM Groups";
				Query query = session.createQuery(hql);
				return query.list();
			}
		});
	}
	public List getTypes(){
		
		return (List)this.getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				// TODO Auto-generated method stub
				String hql ="FROM Producttype";
				Query query = session.createQuery(hql);
				return query.list();
			}
		});
	}
	public Product getProdctId(String id) {
		Product product = (Product) this.getHibernateTemplate().get(
				Product.class, new String(id));
		return product;
	}

	public List getProductInfo(String id) {
		final String _id = id;
		return (List)this.getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				// TODO Auto-generated method stub
				String hql ="from Product where productno = '"+_id+"'";
				Query query = session.createQuery(hql);
				return query.list();
			}
		});
	}

	public ResultSet getProductList(String name,String typeno,int index, int length) {
		ResultSet rs = null;
		final String _name = name;
		final String _typeno = typeno;
		final int _index = index;
		final int _length = length;
		System.out.println("name="+_name);
		System.out.println("index="+_index);
		System.out.println("length="+_length);
		rs = (ResultSet)this.getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = null;
				ResultSet rs = null;
				Connection conn = null;
				String tmp_name = "";
				String _tmp_name = "";
				String tmp_tel = "";
				try{
					conn = session.connection();//
					String sql ="";
					
					if(!_name.equals("")){
						
						tmp_name ="  and cc.productname like '%"+_name+"%'";
						_tmp_name = "  and productname like '%"+_name+"%'";
					}
					if(!_typeno.equals("")){
						
						tmp_name +=" and cc.typeno='"+_typeno+"'";
						_tmp_name +=" and typeno='"+_typeno+"'";
					}
					//String key = tmp_name + tmp_tel;
						//sql = "select * from product cc ,product2group ccg,groups gs,producttype c cross join(select count(*) from product ) a where cc.productno = ccg.productno and ccg.groupno = gs.groupid and cc.typeno = c.typeno order by cc.level,cc.createdate desc limit "+_index+","+_length;
						sql = "select * from product cc ,product2group ccg,groups gs,producttype c cross join(select count(*) from product where 1=1 "+_tmp_name+" ) a where cc.productno = ccg.productno and ccg.groupno = gs.groupid and cc.typeno = c.typeno "+tmp_name+" order by cc.level desc limit "+_index+","+_length;
						//sql = "select * from product cc ,product2group ccg,groups gs,producttype c cross join(select count(*) from product ) a where cc.productno = ccg.productno and ccg.groupno = gs.groupid and cc.typeno = c.typeno and cc.productname like '%"+_name+"%' order by cc.level,cc.createdate desc limit "+_index+","+_length;
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

	public List getProductPermission(String id) {
		final String _id = id;
		return (List)this.getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				// TODO Auto-generated method stub
				String hql ="from Product2group where productno = '"+_id+"'";
				Query query = session.createQuery(hql);
				return query.list();
			}
		});
	}

	public String saveInfo(Object obj) {
		String rtn = "0";
		Serializable serializable = this.getHibernateTemplate().save(obj);
		rtn = "1";
		return rtn;
	}

	public String updateInfo(Object obj) {
		String rtn = "0";
		this.getHibernateTemplate().update(obj);
		rtn = "1";
		return rtn;
	}

}
