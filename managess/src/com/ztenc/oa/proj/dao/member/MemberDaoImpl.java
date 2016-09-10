package com.ztenc.oa.proj.dao.member;

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
import com.ztenc.oa.proj.bean.ProAnswer;




public class MemberDaoImpl extends HibernateDaoSupport implements MemberDao {
	
	
	public List getGroup(){
		
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
		
	}
	
public ResultSet memberSearchByExpert(String useracc,String telno,String vpdn){
		
		
		ResultSet rs = null;
		final String _useracc = useracc;
		final String _telno = telno;
		final String _vpdn = vpdn;
		rs = (ResultSet)this.getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				ResultSet rs = null;
				Connection conn = null;
				String hql = "";
				String tmp_name = "";
				String tmp_tel = "";
				try{
					Criteria criteria = session.createCriteria(Member.class);
					//hql ="select * from (select * from member cross join (select count(*) from member) b) c limit "+(maxnum)+","+(maxnum+_length);
					/*if(!_useracc.equals("null")){
						criteria.add(Expression.like("userAcc","%"+_useracc+"%"));
						}
					if(!_telno.equals("null")){
						criteria.add(Expression.eq("telno", _telno));
						}
					if(!_vpdn.equals("null")){
						criteria.add(Expression.eq("VPDN",_vpdn));
					List l =criteria.list();
					return l;*/
					
					
					if(!_useracc.equals("")){
						
						tmp_name +=" and m.userAcc like '%"+_useracc+"%'";
						
					}
					if(!_telno.equals("")){
						
						tmp_name +=" and m.telno='"+_telno+"'";
					}
					
					if(!_vpdn.equals("")){
						tmp_name +=" and m.vpdn='"+_vpdn+"'";
					}
					String key = tmp_name;	
					hql = "select m.userAcc,m.telno,m.VPDN,m.enableDate,m.disableDate from member m,member2group g,groups gs where m.memberno=g.memberno and g.groupno=gs.groupid "+key+" order by loginDate desc";
					conn = session.connection();
					Statement stmt =  conn.createStatement();
					rs = stmt.executeQuery(hql);
				//	}
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
	
	public ResultSet memberSearchByKey(int index,int length,String membername,String telno,String vpdn){
		
		
		ResultSet rs = null;
		final int maxnum = index;
		final int _length = length;
		final String _membername = membername;
		final String _telno = telno;
		final String _vpdn = vpdn;
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
						
						tmp_name +=" and m.userAcc like '%"+_membername+"%'";
						
					}
					if(!_telno.equals("")){
						
						tmp_name +=" and m.telno='"+_telno+"'";
					}
					if(!_vpdn.equals("")){
						tmp_name += " and m.vpdn='"+_vpdn+"'";
					}
					String key = tmp_name + tmp_tel;	
					hql = "select *  from member m cross join (select count(*) from member m,member2group g,groups gs where m.memberno=g.memberno and g.groupno=gs.groupid "+key+") c,member2group g,groups gs where m.memberno=g.memberno and g.groupno=gs.groupid "+key+" order by loginDate desc limit "+(maxnum)+","+_length;
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
	public ResultSet memberSearch(int index, int length) {
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
					hql = "select *  from member m cross join (select count(*) from member m,member2group g,groups gs where m.memberno=g.memberno and g.groupno=gs.groupid ) c,member2group g,groups gs where m.memberno=g.memberno and g.groupno=gs.groupid order by loginDate desc limit "+(maxnum)+","+_length;
					System.out.println("membersql=="+hql);
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
	
	public Member getVPDN(String memberno) {
		Member member = null;
		final String _memberno = memberno;
		member = (Member)this.getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
					Member mem =(Member) session
					.createQuery("from Member  where memberno='"
							+ _memberno+"'").uniqueResult();
					session.clear();
					return mem;
					
			}
		});
		return member;
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
	public String deleteMember(String id)throws DataAccessException{//留言板
		String rtn = "0";
		final String fbid = id;
		rtn = (String)this.getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException,SQLException{
				String rtn = "";
				Connection conn = null;
				try{
					//String hql = "DELETE FROM WL_reply WHERE (resId in (SELECT resId FROM WL_ReplyMessage WHERE (fbId = (SELECT fbId FROM wl_messageboard WHERE fbId = "+fbid+"))))";
					//hql = hql + "delete from WL_ReplyMessage where fbId = (SELECT fbId FROM wl_messageboard WHERE fbId = "+fbid+")";
					//hql = hql + "delete from wl_messageboard WHERE fbId = "+fbid;
					String hql = "delete from member where memberno='"+fbid+"'";
					String hql2 = "delete from member2group where memberno='"+fbid+"'";
					conn = session.connection();
					java.sql.PreparedStatement ps = conn.prepareStatement(hql);
					java.sql.PreparedStatement ps2 = conn.prepareStatement(hql2);
					ps.executeUpdate();
					ps2.executeUpdate();
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
	
	public Member memberSearchByAcc(String userAcc) {
		final String _userAcc = userAcc;
		Member tLoc = (Member) this.getHibernateTemplate().execute(
				new HibernateCallback()
				{
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException
					{
						Member temLoc =(Member) session
								.createQuery("from Member  where userAcc='"
										+ _userAcc+"'").uniqueResult();
						//List l = q.list().;
						return temLoc;
					}
				});
		return tLoc;
	}
	
	public Member getMemberId(String id) {
		Member member = (Member) this.getHibernateTemplate().get(
				Member.class, id);
		return member;
	}
	
}
