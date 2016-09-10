package com.ztenc.oa.proj.dao.webservice;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.ztenc.oa.proj.bean.Advertisment;
import com.ztenc.oa.proj.bean.Member;
import com.ztenc.oa.proj.bean.Member2group;



public class LoginDaoImpl  extends HibernateDaoSupport implements LoginDao{

	/**
	 * 根据手机号码到数据库中查找本地数据是否存在该用户
	 * @param userNo 用户帐号
	 * @return
	 */
	public List getUser(String userNo){
		final String userNo_b = userNo;
		return (List) this.getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session arg0)
							throws HibernateException, SQLException {
						// TODO Auto-generated method stub
						Criteria crit = null;
							crit = arg0.createCriteria(Member.class);
							crit.add(Restrictions.eq("membername", new String(
										userNo_b)));
						return crit.list();
						
					}

				});
	}
	
	public List getUserByTime(String userNo){
		final String userNo_b = userNo;
		return (List) this.getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session arg0)
							throws HibernateException, SQLException {
						// TODO Auto-generated method stub
						Criteria crit = arg0.createCriteria(Member.class)
								.add(Restrictions.eq("membername", new String(
										userNo_b))).addOrder(Order.desc("loginDate")).setMaxResults(1);
						return crit.list();
						
					}

				});
	}
	
	public Member getMember(String id){
		Member mem = (Member) this.getHibernateTemplate().get(
				Member.class, new String(id));
		return mem;
	}
	
	public void saveUser(Member mem){
		this.getHibernateTemplate().save(mem);
	}
	
	public void saveGroup(Member2group group){
		this.getHibernateTemplate().save(group);
	}
	
	public void updateUser(Member mem) throws DataAccessException
	{
		// TODO Auto-generated method stub

		this.getHibernateTemplate().merge(mem);//.update(user);
	}
	
	public Member getUserByNO(String userNo)
	{
		final String mobileNum_j = userNo;
		Member tLoc = (Member) this.getHibernateTemplate().execute(
				new HibernateCallback()
				{
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException
					{
						Member temLoc =(Member) session
								.createQuery("from Member  where membername='"
										+ mobileNum_j+"'").uniqueResult();
						//List l = q.list().;
						return temLoc;
					}
				});
		return tLoc;
	}
	
	public Member getUserByNoAndVPDN(String userNo)
	{
		final String mobileNum_j = userNo;
		Member tLoc = (Member) this.getHibernateTemplate().execute(
				new HibernateCallback()
				{
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException
					{
						Member temLoc =(Member) session
								.createQuery("from Member  where userAcc='"
										+ mobileNum_j+"'").uniqueResult();
						//List l = q.list().;
						return temLoc;
					}
				});
		return tLoc;
	}
	
	public List getUserInCode(String userNo,String code){
		final String userNo_b = userNo;
		final String code_b = code;
		return (List) this.getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session arg0)
							throws HibernateException, SQLException {
						// TODO Auto-generated method stub
						Criteria crit = null;
							crit = arg0.createCriteria(Member.class);
							crit.add(Restrictions.eq("membername", new String(
										userNo_b)));
							crit.add(Restrictions.eq("code", new String(
									code_b)));
						
						return crit.list();
						
					}

				});
	}

	public List getAdvertisment() {

		return (List)this.getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				String hql ="from Advertisment order by id desc limit 0,1";
				Query query = session.createQuery(hql);
				query.setMaxResults(1);
			    query.setFirstResult(0);
				return query.list();
			}
		});
	}
	
	
}
