package com.ztenc.oa.proj.dao.webservice;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;


import com.ztenc.oa.proj.bean.Member;

public class SysOtpDaoImpl extends HibernateDaoSupport implements SysOtpDao{

	/**
	 * 通过手机号找到原用户对象
	 * @param mobNum 手机号码
	 * @return
	 */
	public Member getMemberInfo(String mobNum) {		
		final String mobNum_m = mobNum;
		return (Member) this.getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session) {
						Member upUser = null;
						Query query = session
								.createQuery("from Member where  telno= '"
										+ mobNum_m + "'");
						upUser = (Member) query.uniqueResult();
						return upUser;
					}
				});
	}
	
	/**
	 * 修改用户信息
	 * @param mem 用户对象
	 * @return
	 */
	public boolean updateMemberInfo(Member mem) {
		try {
			this.getHibernateTemplate().merge(mem);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * 删除用户
	 * @param memName 用户名
	 * @return
	 */
	public boolean deleteMember(String memName) {
		final String memName_b = memName;
		try {
			this.getHibernateTemplate().execute(new HibernateCallback() {

				public Object doInHibernate(Session session) {
					Query query = session
							.createQuery("delete from  Member where membername=:memName_b");					
					query.setString("memName_b", memName_b);
					query.executeUpdate();					
					System.out.println(query.executeUpdate());
					session.flush();
					return null;
				}
			});

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
