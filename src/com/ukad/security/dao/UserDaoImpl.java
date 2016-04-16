package com.ukad.security.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.ukad.dao.BaseDaoImpl;
import com.ukad.model.Transaction;
import com.ukad.security.model.RolesUser;
import com.ukad.security.model.Menu;
import com.ukad.security.model.User;

@Repository("userDao")
@Scope("singleton")
public class UserDaoImpl extends BaseDaoImpl {
	@Autowired
	public UserDaoImpl(SessionFactory sessionFactory) {
		setSessionFactory(sessionFactory);
	}

	public void deleteRolesUser(Long userId, Long groupId) {
		RolesUser gu = getRolesUserByUserAndRolesIds(userId, groupId);
		getHibernateTemplate().delete(gu);
	}

	public RolesUser getRolesUserByUserAndRolesIds(Long userId, Long groupId) {
		RolesUser gu = null;
		List list = getHibernateTemplate().find("from RolesUser gu where gu.user.numUser = ? and gu.group.numRoles = ?",
				new Object[] { userId, groupId });
		if (list.size() > 0)
			gu = (RolesUser) list.get(0);
		return gu;
	}

	public List<Long> getRolesUserListByUser(Long userId) {
		return null;
		/*
		 * return (List<Long>) getHibernateTemplate() .find(
		 * "select gu.group.numRoles from RolesUser gu where gu.user.numUser = ?"
		 * , new Object[] { userId });
		 */
	}

	public List<Menu> getSubMenus(Long parentId) {
		// TODO Auto-generated method stub
		/**
		 * final String[] paramNames = { "parentId" }; final Object[]
		 * paramValues = new Object[] { parentId };
		 * 
		 * return (List<Menu>)
		 * getHibernateTemplate().findByNamedQueryAndNamedParam( "getKidsMenu",
		 * paramNames, paramValues);
		 * 
		 */
		return null;
	}

	public User getUser(String nom, String password) {

		User user = null;
		List list = getHibernateTemplate()
				.find("from User where userName = '" + nom + "' and password='" + password + "'");
		if (list.size() > 0) {

			user = (User) list.get(0);
			/*
			 * for (RolesUser gu : user.getRolesUser()) { ;
			 * Hibernate.initialize(gu.getRoles().getRolesMenus()); }
			 */
		}

		return user;
	}

	public List<User> loadAllMembers() {

		DetachedCriteria crit = DetachedCriteria.forClass(User.class);
		crit.add(Restrictions.eq("status", (short) 1));
		List l = getHibernateTemplate().findByCriteria(crit);

		return l;
	}
	
	public List<User> loadAllUsersWithOnlineStatus() {
		final String sql = "SELECT U.USER_ID, U.USER_NAME, U.PASSWORD, U.FIRST_NAME, U.LAST_NAME, SH2.CREATE_DATE, SH2.END_DATE "
				+	"FROM USERS U "
				+ 	"JOIN (SELECT USER_ID, MAX(CREATE_DATE) AS CREATE_DATE FROM SESSION_HISTORY GROUP BY USER_ID) SH "
				+ 	"	ON U.USER_ID = SH.USER_ID "
				+	"JOIN SESSION_HISTORY SH2 ON SH.USER_ID = SH2.USER_ID AND SH.CREATE_DATE = SH2.CREATE_DATE "
				+ 	"WHERE SH2.END_DATE IS NULL "
				//+ "U.STATUS = 1 "
				;


		List<Object[]> list = (List)getHibernateTemplate().execute(
				new HibernateCallback() {
				public Object doInHibernate(Session session) throws HibernateException {
				SQLQuery sq =session.createSQLQuery(sql);
				//sq.addScalar("TEST_TABLE_ID", Hibernate.INTEGER);
				//sq.addScalar("NAME", Hibernate.STRING);
				//sq.addScalar("TEST_DATE", Hibernate.DATE);
				return sq.list();
				}});

		List<User> users = new ArrayList<User>();
		
		if(list.size() > 0){
			for(Object[] row : list){
				User user = new User();
				user.setId(((BigInteger) row[0]).longValue());
				user.setUserName((String) row[1]);
				user.setPassword((String) row[2]);
				user.setFirstName((String) row[3]);
				user.setLastName(((String) row[4]).substring(0, 1));
				user.setOnline(row[6] != null);
				
				users.add(user);
			}
		}
				
		return users;
	}

	public List<User> loadAllMembersPending() {
		DetachedCriteria crit = DetachedCriteria.forClass(User.class);
		crit.add(Restrictions.eq("status", (short) 0));
		List l = getHibernateTemplate().findByCriteria(crit);
		return l;
	}

	public List<User> findMembers(String searchText) {

		if (searchText == null) {
			return null;
		} else {
			DetachedCriteria crit = DetachedCriteria.forClass(User.class);
			String ab[] = searchText.split(" ");
			if (ab.length == 1) {
				Criterion c1 = Restrictions.like("firstName", "%" + searchText.toLowerCase() + "%").ignoreCase();
				Criterion c2 = Restrictions.like("lastName", "%" + searchText.toLowerCase() + "%").ignoreCase();
				Criterion c3 = Restrictions.eq("status", (short) 1);
				crit.add(Restrictions.or(c1, c2));
				crit.add(Restrictions.and(c3));
				return (List<User>) getHibernateTemplate().findByCriteria(crit);
			} else {// take first 2
				Criterion c1 = Restrictions.like("firstName", "%" + ab[0].toLowerCase() + "%").ignoreCase();
				Criterion c2 = Restrictions.like("lastName", "%" + ab[1].toLowerCase() + "%").ignoreCase();
				Criterion c3 = Restrictions.like("firstName", "%" + ab[1].toLowerCase() + "%").ignoreCase();
				Criterion c4 = Restrictions.like("lastName", "%" + ab[0].toLowerCase() + "%").ignoreCase();
				Criterion c5 = Restrictions.eq("status", (short) 1);
				crit.add(Restrictions.or(c1, c2, c3, c4));
				crit.add(Restrictions.and(c5));
				return (List<User>) getHibernateTemplate().findByCriteria(crit);
			}

		}
	}

	public List<User> getLeaders() {
		DetachedCriteria crit = DetachedCriteria.forClass(User.class);
		crit.createCriteria("position").add(Restrictions.gt("id", 1L));
		crit.addOrder(Order.asc("position.id"));
		List l = getHibernateTemplate().findByCriteria(crit);
		return l;
	}

	public List<Transaction> getAllExpenses() {
		DetachedCriteria crit = DetachedCriteria.forClass(Transaction.class);
		crit.add(Restrictions.eq("io", (short) 0));
		crit.addOrder(Order.desc("createDate"));
		List l = getHibernateTemplate().findByCriteria(crit);
		return l;
	}
}
