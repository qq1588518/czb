package com.ztenc.oa.proj.dao.proanswertype;

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

import com.ztenc.oa.proj.bean.AnswerType;
import com.ztenc.oa.proj.bean.CataGroup;
import com.ztenc.oa.proj.bean.Catagory;
import com.ztenc.oa.proj.bean.Producttype;
import com.ztenc.oa.proj.bean.WlMessageboard;




public class AnswerTypeDaoImpl extends HibernateDaoSupport implements AnswerTypeDao {
	
	

	/**
	 * 进入管理界面读取所有的产品类别信息
	 * 
	 * @param id
	 * @return
	 */
	public ResultSet getType(int index, int length){
		ResultSet rs = null;
		final int _index = index;
		final int _length = length;
		rs = (ResultSet)this.getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = null;
				ResultSet rs = null;
				Connection conn = null;
				try{
					conn = session.connection();//
					String sql="SELECT * FROM Answer_type c cross join(select count(*) from Answer_type ) a order by c.level desc limit "+_index+","+_length;
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
	
	
	public AnswerType getTypeId(int id) {
		AnswerType answerType = (AnswerType) this.getHibernateTemplate().get(
				AnswerType.class, id);
		return answerType;
	}
	/**
	 * 根据id删除产品类别信息
	 * 
	 * @param 
	 * @return
	 */
	public String deleteType(String id)throws DataAccessException{
		String rtn = "0";
		final String typeno = id;
		rtn = (String)this.getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException,SQLException{
				String rtn = "";
				//Connection conn = null;
				String hql = "";
				String hql2 = "";
						hql = "delete from Answer_type WHERE typeno = '"+typeno+"'" ;
						hql2 = "select * from pro_answer where id='"+typeno+"'";
						Query type = session.createSQLQuery(hql2);
						if(type.list().size()>0){
							rtn = "-1";
						}else{
							Query query = session.createSQLQuery(hql);
							query.executeUpdate();
							rtn = "1";
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
	
	public String updateInfo(Object obj)throws DataAccessException{
		
		String rtn = "0";
		this.getHibernateTemplate().update(obj);
		rtn = "1";
		return rtn;
	}
}
