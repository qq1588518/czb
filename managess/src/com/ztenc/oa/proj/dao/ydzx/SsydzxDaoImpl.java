package com.ztenc.oa.proj.dao.ydzx;

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

public class SsydzxDaoImpl extends HibernateDaoSupport implements SsydzxDao {

	
	//根据查询条件查询出所有子栏目信息
	public List getAll(String id) {
		final String _id = id;
		return (List)this.getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "from Catagory where topproId = (select c.proId from Catagory c where c.proName ='移动资讯')";
				Query query = session.createQuery(hql);
				return query.list();
				//return null;
			}
		});
	}
	
	
	//查询子栏目信息的第一条数据
	public List getItem(String id,String index,String length) {
		final String _id = id;
		final String _index = index;
		final String _length = length;
		return (List)this.getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "select * from catagory_con cc,catatory_con_group cg cross join (select count(*) from catagory_con cc1,catatory_con_group cg1 where cc1.id=cg1.id and cg1.sub_catagory_id='"+_id+"') b where cc.id=cg.id and cg.sub_catagory_id='"+_id+"'order by cc.dates desc limit "+_index+","+_length;
				Query query = session.createSQLQuery(hql);
				return query.list();
				//return null;
			}
		});
	}
}
