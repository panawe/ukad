package com.ukad.security.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ukad.model.BaseEntity;
import com.ukad.model.Position;
import com.ukad.model.Transaction;
import com.ukad.security.dao.UserDaoImpl;
import com.ukad.security.model.Roles;
import com.ukad.security.model.RolesUser;
import com.ukad.security.model.Contribution;
import com.ukad.security.model.Menu;
import com.ukad.security.model.User;
import com.ukad.service.*;

@Service("userService")
// @Scope("session")
public class UserServiceImpl extends BaseServiceImpl implements UserService {

	@Autowired
	@Qualifier("userDao")
	private UserDaoImpl userDao;

	@Transactional(readOnly = true)
	public List<Long> getRolesIdsByUser(Long userId) {
		return userDao.getRolesUserListByUser(userId);
	}

	@Transactional(readOnly = false)
	public User getUser(String nom, String password) {
		return userDao.getUser(nom, password);
	}

	@Transactional(readOnly = false)
	public void savePickedList(Long loginId, Long userId, List<Long> availableItemKeys, List<Long> selectedItemKeys) {
		Set<RolesUser> groupUsersToAdd = new HashSet<RolesUser>();
		// userDao.setUserId(loginId);
		User user = (User) userDao.getById(User.class, userId);
		// load the current associations.
		List<Long> groupIds = userDao.getRolesUserListByUser(userId);

		// Check to see if any group get associated to the user after we
		// retrieved our pickList.
		for (Long groupId : groupIds) {
			if (!(availableItemKeys.contains(groupId) || selectedItemKeys.contains(groupId)))
				selectedItemKeys.add(groupId);

			if (availableItemKeys.contains(groupId))
				userDao.deleteRolesUser(userId, groupId);

			if (selectedItemKeys.contains(groupId))
				selectedItemKeys.remove(groupId);

		}

		for (Long groupId : selectedItemKeys) {
			Roles group = (Roles) userDao.getById(Roles.class, groupId);
			RolesUser gu = new RolesUser();
			gu.setRoles(group);
			gu.setUser(user);

			userDao.update(gu, user);
		}
	}

	public List<Menu> getSubMenus(Long parentId) {
		// TODO Auto-generated method stub
		return userDao.getSubMenus(parentId);
	}

	public List<Long> getGroupeIdsByUser(Long utilisateurId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional(readOnly = false)
	public void add(User user) {
		// TODO Auto-generated method stub
		if (user.getPosition() == null) {
			user.setPosition((Position) getById(Position.class, 1L));
		}
		userDao.save(user);
	}

	public List<Roles> getAllRoles() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Long> getRolesIdsForUser(Long idParameter, Class<User> class1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> loadAllMembers() {
		// TODO Auto-generated method stub
		return userDao.loadAllMembers();
	}

	@Override
	public List<User> loadAllMembersPending() {
		// TODO Auto-generated method stub
		return userDao.loadAllMembersPending();
	}

	@Override
	public List<User> findMembers(String searchText) {
		// TODO Auto-generated method stub
		return userDao.findMembers(searchText);
	}

	@Override
	public List<User> getLeaders() {
		// TODO Auto-generated method stub
		return userDao.getLeaders();
	}

	@Override
	public List<Transaction> getAllExpenses() {
		// TODO Auto-generated method stub
		return userDao.getAllExpenses();
	}

	@Override
	public List<Contribution> getContributions() {
		// TODO Auto-generated method stub
		return userDao.getContributions();
	}

}
