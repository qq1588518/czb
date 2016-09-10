package com.ztenc.oa.proj.dao.group;

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




public class GroupDaoImpl extends HibernateDaoSupport implements GroupDao {
	
	public ResultSet groupSearch(int index, int length) {
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
					hql = "select *  from groups m cross join (select count(*) from groups) c limit "+(maxnum)+","+(maxnum+_length);
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
	
	public String updateInfo(Object obj)throws DataAccessException{
		
		String rtn = "0";
		this.getHibernateTemplate().update(obj);
		rtn = "1";
		
		return rtn;
	}
	
	//删除留言包括回复信息
	public String deleteGroup(String id)throws DataAccessException{
		String rtn = "0";
		final String fbid = id;
		rtn = (String)this.getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException,SQLException{
				String rtn = "";
				Connection conn = null;
				ResultSet rs = null;
				try{
					//String hql = "DELETE FROM WL_reply WHERE (resId in (SELECT resId FROM WL_ReplyMessage WHERE (fbId = (SELECT fbId FROM wl_messageboard WHERE fbId = "+fbid+"))))";
					//hql = hql + "delete from WL_ReplyMessage where fbId = (SELECT fbId FROM wl_messageboard WHERE fbId = "+fbid+")";
					//hql = hql + "delete from wl_messageboard WHERE fbId = "+fbid;
					conn = session.connection();
					String hql2 = "select * from member2group where groupno='"+fbid+"'";
					java.sql.PreparedStatement ps2 = conn.prepareStatement(hql2);
					rs = ps2.executeQuery();
					if(!rs.next()){
						String hql = "delete from groups where groupid='"+fbid+"'";
						java.sql.PreparedStatement ps = conn.prepareStatement(hql);
						ps.executeUpdate();
						rtn = "1";
					}else{
						
						rtn = "-1";
					}
				}catch(Exception e){
					rs.close();
					System.out.println(e);
					conn.close();
				}finally{
					rs.close();
					conn.close();
				}
				return rtn;
			}
		});
		return rtn ;
	}
	
	
	
}
