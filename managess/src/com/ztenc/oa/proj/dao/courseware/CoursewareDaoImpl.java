package com.ztenc.oa.proj.dao.courseware;

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

import com.ztenc.oa.proj.bean.Courseware;
import com.ztenc.oa.proj.bean.Groups;
import com.ztenc.oa.proj.bean.Member;
import com.ztenc.oa.proj.bean.Product;




public class CoursewareDaoImpl extends HibernateDaoSupport implements CoursewareDao {
	
	
	public List getSubject(){
		
		List rs = null;
		rs = (List)this.getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				ResultSet rs = null;
				String hql = "";
				try{
					hql ="FROM Subject";
					Query query = session.createQuery(hql);
					return query.list();
				}catch(Exception e){
					System.out.println(e);
				}finally{
				}
				return rs;
			}
		});
		return rs;
		
	}
	
	
	public List getlistcoursegroup(String id){
		final String _id = id;
		return (List)this.getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				// TODO Auto-generated method stub
				String hql ="from Courseware2subject where id = '"+_id+"'";
				Query query = session.createQuery(hql);
				return query.list();
			}
		});
	}
	
	
	public ResultSet coursewareSearchByKey(int index,int length,String coursewarename,String sub_protype){
		ResultSet rs = null;
		final int maxnum = index;
		final int _length = length;
		final String _coursewarename = coursewarename;
		final String subProtype = sub_protype;
		System.out.println("subProtype==============="+subProtype);
		rs = (ResultSet)this.getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				ResultSet rs = null;
				Connection conn = null;
				String hql = "";
				String tmp_name = "";
				String tmp_tel = "";
				try{
					if(!_coursewarename.equals("")){
						
						tmp_name +=" and m.coursewarename like '%"+_coursewarename+"%' ";
						
					}
					if(!subProtype.equals("")){
						
						tmp_name +=" and g.subjectno = '"+subProtype+"'";
						
					}
					String key = tmp_name + tmp_tel;	
					hql = "select *  from courseware m cross join (select count(*) from courseware m,courseware2subject g,subject gs where m.coursewareno=g.coursewareno and g.subjectno=gs.subjectno "+key+") c,courseware2subject g,subject gs where m.coursewareno=g.coursewareno and g.subjectno=gs.subjectno "+key+" order by createDate desc limit "+(maxnum)+","+_length;
					conn = session.connection();
					Statement stmt =  conn.createStatement();
					rs = stmt.executeQuery(hql);
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
	
	public String saveInfo(Object obj)throws DataAccessException{
		String rtn = "0";
		Serializable serializable = this.getHibernateTemplate().save(obj);
		rtn = "1";
		return rtn;
	}
	
	
	public Courseware getProdctId(String id) {
		Courseware courseware = (Courseware) this.getHibernateTemplate().get(
				Courseware.class, new String(id));
		return courseware;
	}
	
	public String updateInfo(Object obj)throws DataAccessException{
		
		String rtn = "0";
		this.getHibernateTemplate().update(obj);
		rtn = "1";
		
		return rtn;
	}
	
	//删除留言包括回复信息
	public String deleteCourseware(String id)throws DataAccessException{
		String rtn = "0";
		final String fbid = id;
		rtn = (String)this.getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException,SQLException{
				String rtn = "";
				Connection conn = null;
				ResultSet rs = null;
				try{
					conn = session.connection();
					String hql2 = "delete from courseware2subject where coursewareno='"+fbid+"'";
					java.sql.PreparedStatement ps2 = conn.prepareStatement(hql2);
					ps2.executeUpdate();
					String hql = "delete from courseware where coursewareno='"+fbid+"'";
					java.sql.PreparedStatement ps = conn.prepareStatement(hql);
					ps.executeUpdate();
						rtn = "1";
				}catch(Exception e){
					//rs.close();
					System.out.println(e);
					//conn.close();
				}finally{
					//rs.close();
					//conn.close();
				}
				return rtn;
			}
		});
		return rtn ;
	}
	
	// modify by Tim 20110914
	public List getProductInfo(String id) {
		final String _id = id;
		return (List) this.getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				// TODO Auto-generated method stub
				String hql = "from Courseware where coursewareno = '" + _id + "'";
				Query query = session.createQuery(hql);
				return query.list();
			}
		});
	}
	
}
