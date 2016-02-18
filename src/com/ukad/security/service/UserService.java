package com.ukad.security.service;

import java.util.List;

import com.ukad.model.BaseEntity;
import com.ukad.security.model.Menu;
import com.ukad.security.model.Roles;
import com.ukad.security.model.User;
import com.ukad.service.BaseService;

public interface UserService extends BaseService {
	public List<Long> getGroupeIdsByUser(Long utilisateurId);

	public User getUser(String nom, String password);

	public void savePickedList(Long loginId, Long userId,
			List<Long> availableItemKeys, List<Long> selectedItemKeys);
	
	public  List<Menu> getSubMenus(Long parentId);
	
	public List<Long> getRolesIdsForUser(Long idParameter, Class<User> class1);

	public List<Roles> getAllRoles();

	public void add(User user);

	public List<User> loadAllMembers();
	public List<User> loadAllMembersPending();

	public List<User> findMembers(String searchText);

	public List<User> getLeaders();
}
