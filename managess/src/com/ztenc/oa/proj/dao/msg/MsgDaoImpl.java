package com.ztenc.oa.proj.dao.msg;

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

import com.ztenc.oa.proj.bean.WlMessageboard;




public class MsgDaoImpl extends HibernateDaoSupport implements MsgDao {

	//留言板信息读取，index为搜索开始位置，length为每页显示的长度
	public ResultSet msgSearch(int index, int length,String title) {
		ResultSet rs = null;
		final int maxnum = index;
		final int _length = length;
		final String _title = title;
		rs = (ResultSet)this.getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				ResultSet rs = null;
				Connection conn = null;
				String hql = "";
				try{
					if(_title.equals("")){
						hql ="select * from (select * from `WL_Messageboard` cross join (select count(*) from `WL_Messageboard`) b) c order by faDate desc limit "+(maxnum)+","+(_length);
					}else{
						hql ="select * from (select * from `WL_Messageboard` cross join (select count(*) from `WL_Messageboard` where userName = '"+_title+"') b where userName = '"+_title+"') c order by faDate desc limit "+(maxnum)+","+(_length);
					}
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
	
	/**
	 * 根据id获取业务信息
	 * 
	 * @param id
	 * @return
	 */
	public WlMessageboard getMsgId(int id) {
		WlMessageboard wlMessageboard = (WlMessageboard) this.getHibernateTemplate().get(
				WlMessageboard.class, new Integer(id));
		return wlMessageboard;
	}
	
	
	public String updateInfo(Object obj)throws DataAccessException{
		
		String rtn = "0";
		this.getHibernateTemplate().update(obj);
		rtn = "1";
		
		return rtn;
	}
	
	//删除留言包括回复信息
	public String deleteMsg(String id)throws DataAccessException{//留言板
		String rtn = "0";
		final String fbid = id;
		rtn = (String)this.getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException,SQLException{
				String rtn = "";
				Connection conn = null;
				try{
					String hql = "DELETE FROM WL_reply WHERE (resId in (SELECT resId FROM WL_ReplyMessage WHERE (fbId = (SELECT fbId FROM wl_messageboard WHERE fbId = "+fbid+"))))";
					String hql2 ="delete from WL_ReplyMessage where fbId = (SELECT fbId FROM wl_messageboard WHERE fbId = "+fbid+")";
					String hql3 ="delete from wl_messageboard WHERE fbId = "+fbid;
					conn = session.connection();
					java.sql.PreparedStatement ps = conn.prepareStatement(hql);
					ps.executeUpdate();
					java.sql.PreparedStatement ps2 = conn.prepareStatement(hql2);
					ps2.executeUpdate();
					java.sql.PreparedStatement ps3 = conn.prepareStatement(hql3);
					ps3.executeUpdate();
					rtn = "1";
				}catch(Exception e){
					System.out.println(e);
					conn.close();
				}finally{
					conn.close();
				}
				return rtn;
			}
		});
		return rtn ;
	}
	
	//读取回复信息
	public ResultSet replyedMsg(String ID){//留言板
		final String fbid = ID;
		ResultSet rs = (ResultSet)this.getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				// TODO Auto-generated method stub
				ResultSet rs = null;
				String hql ="SELECT m.corporation, m.FaBMsg, m.faDate, m.tel, m.type, y.resadmin, y.ReMsg, y.resdate, r.fbId, r.resId FROM WL_Messageboard m INNER JOIN WL_ReplyMessage r ON m.fbId = r.fbId INNER JOIN WL_reply y ON r.resId = y.resId WHERE (m.fbId ="+fbid+")";
				Connection conn = session.connection();
				Statement stmt =  conn.createStatement();
				rs = stmt.executeQuery(hql);
				return rs;
			}
			
		});
		
		return rs;
	}
	
	
	//删除回复信息
	public String deleteReplay(String id,String resid)throws DataAccessException{//留言板
		String rtn = "0";
		final String fbid = id;
		final String resId = resid;
		rtn = (String)this.getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException,SQLException{
				String rtn = "";
				Connection conn = null;
				try{
					String hql = "delete from WL_ReplyMessage where resId = (SELECT resId FROM WL_reply WHERE resId = '"+resId+"')";
					String hql2 ="DELETE FROM WL_reply WHERE resId = '"+resId+"'";
					String hql3 ="UPDATE WL_Messageboard SET sfhf = 0 WHERE (fbId = '"+fbid+"')";
					conn = session.connection();
					java.sql.PreparedStatement ps = conn.prepareStatement(hql);
					ps.executeUpdate();
					java.sql.PreparedStatement ps2 = conn.prepareStatement(hql2);
					ps2.executeUpdate();
					java.sql.PreparedStatement ps3 = conn.prepareStatement(hql3);
					ps3.executeUpdate();
					rtn = "1";
				}catch(Exception e){
					System.out.println(e);
					conn.close();
				}finally{
					conn.close();
				}
				return rtn;
			}
		});
		return rtn ;
	}
	
	
	
	//修改回复信息
	public String modifyreplay(String id,String Replycon,String ReplyName,String datetime)throws DataAccessException{//留言板
		String rtn = "0";
		final String Id = id;
		final String replycon = Replycon;
		final String replyName = ReplyName;
		final String dateTime = datetime;
		rtn = (String)this.getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException,SQLException{
				String rtn = "";
				Connection conn = null;
				try{
					String hql ="UPDATE WL_reply SET remsg = '"+replycon+"',resadmin = '"+replyName+"',resdate='"+dateTime+"' WHERE (resid = '"+Id+"')";
					conn = session.connection();
					java.sql.PreparedStatement ps = conn.prepareStatement(hql);
					ps.executeUpdate();
					rtn = "1";
				}catch(Exception e){
					System.out.println(e);
					conn.close();
				}finally{
					conn.close();
				}
				return rtn;
			}
		});
		return rtn ;
	}
	
	
}
