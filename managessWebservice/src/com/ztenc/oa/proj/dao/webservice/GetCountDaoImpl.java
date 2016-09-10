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
import com.ztenc.oa.proj.bean.Member;
import com.ztenc.oa.proj.bean.Member2group;
import com.ztenc.oa.proj.bean.Servicecount;



public class GetCountDaoImpl  extends HibernateDaoSupport implements GetCountDao{

	public List getCount() {
		return (List) this.getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session arg0)
							throws HibernateException, SQLException {
						// TODO Auto-generated method stub
						Criteria crit = null;
							crit = arg0.createCriteria(Servicecount.class);
							return crit.list();
						
					}

				});
	}

	public List getCount(String value) {
		final String val = value;
		return (List) this.getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						// TODO Auto-generated method stub
						Criteria crit = null;
							crit = session.createCriteria(Servicecount.class);
							crit.add(Restrictions.eq("cataName", new String(val)));
//							String hql = "select * from servicecount where cataName = '"+val+"'";
//							Query query = session.createSQLQuery(hql);
							return crit.list();
						
					}

				});
	}
	
	
}
