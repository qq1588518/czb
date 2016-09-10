package com.ztenc.oa.proj.dao.subject;

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
import com.ztenc.oa.proj.bean.Subject;




public class SubjectDaoImpl extends HibernateDaoSupport implements SubjectDao {
	
	
	/*public List getGroup(){
		
		List rs = null;
		rs = (List)this.getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				ResultSet rs = null;
//				Connection conn = null;
				String hql = "";
				try{
					//hql ="select * from (select * from member cross join (select count(*) from member) b) c limit "+(maxnum)+","+(maxnum+_length);
					//hql = "select groupid,groupname  from groups";
					//conn = session.connection();
					//Statement stmt =  conn.createStatement();
					//rs = stmt.executeQuery(hql);
					
					hql ="FROM Groups";
					Query query = session.createQuery(hql);
					return query.list();
				}catch(Exception e){
//					conn.close();
					System.out.println(e);
				}finally{
					//conn.close();
				}
				return rs;
			}
		});
		return rs;
		
	}*/
	
	public ResultSet subjectSearchByKey(int index,int length,String membername,String telno){
		
		
		ResultSet rs = null;
		final int maxnum = index;
		final int _length = length;
		final String _membername = membername;
		final String _telno = telno;
		rs = (ResultSet)this.getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				ResultSet rs = null;
				Connection conn = null;
				String hql = "";
				String tmp_name = "";
				String tmp_tel = "";
				try{
					//hql ="select * from (select * from member cross join (select count(*) from member) b) c limit "+(maxnum)+","+(maxnum+_length);
					if(!_membername.equals("")){
						
						tmp_name =" and m.membername="+_membername;
						
					}
					if(!_telno.equals("")){
						
						tmp_name =" and m.telno="+_telno;
					}
					String key = tmp_name + tmp_tel;	
					hql = "select *  from member m cross join (select count(*) from member m where 1=1 "+key+") c,member2group g,groups gs where m.memberno=g.memberno and g.groupno=gs.groupid "+key+" order by createDate limit "+(maxnum)+","+_length;
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
	

	//留言板信息读取，index为搜索开始位置，length为每页显示的长度
	public ResultSet subjectSearch(int index, int length) {
		ResultSet rs = null;
		final int maxnum = index;
		final int _length = length;
		rs = (ResultSet)this.getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				ResultSet rs = null;
				Connection conn = null;
				String hql = "";
				try{
					//hql ="select * from (select * from member cross join (select count(*) from member) b) c limit "+(maxnum)+","+(maxnum+_length);
					hql = "select *  from subject m cross join (select count(*) from subject) c  order by level desc limit "+(maxnum)+","+_length;
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
	
	
	public Subject getProdctId(int id) {
		Subject subject = (Subject) this.getHibernateTemplate().get(
				Subject.class, id);
		return subject;
	}
	
	public String updateInfo(Object obj)throws DataAccessException{
		
		String rtn = "0";
		this.getHibernateTemplate().update(obj);
		rtn = "1";
		
		return rtn;
	}
	
	//删除留言包括回复信息
	public String deleteSubject(String id)throws DataAccessException{
		String rtn = "0";
		final String fbid = id;
		rtn = (String)this.getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException,SQLException{
				String rtn = "";
				String sql1 = "";
				String sql2 = "";
				String sql = "select coursewareno from courseware2subject where subjectno="+fbid;
				String hql = "delete from subject where subjectno='"+fbid+"'";
				Query queryCon = session.createSQLQuery(sql);
				List list = queryCon.list();
				for(int i=0;i<list.size();i++){
					String id = (String) list.get(i);
					sql1 = "delete from courseware2subject WHERE coursewareno = '"+id+"'";
					sql2 = "delete from courseware WHERE coursewareno = '"+id+"'";
					Query query_sql2 = session.createSQLQuery(sql2);
					query_sql2.executeUpdate();
					Query query_sql1 = session.createSQLQuery(sql1);
					query_sql1.executeUpdate();
				}
				Query query = session.createSQLQuery(hql);
				query.executeUpdate();
				rtn = "1";
				return rtn;
			}
		});
		return rtn ;
	}
	
	
	
}
