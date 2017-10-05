package com.baciu.DAO;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.baciu.entity.User;

@Transactional
@Repository
public class UserDAO {

	@PersistenceContext
	private EntityManager em;

	public User userExists(String userName, String password) throws NoResultException {
		String hql = "FROM User as usr WHERE usr.username = :username AND usr.password = :password";
		User user = em.createQuery(hql, User.class).setParameter("username", userName).setParameter("password", password)
				.getSingleResult();

		return user;
	}

	public void register(User user) {
		em.persist(user);
	}

	public void update(User user) {
		User usr = getById(user.getId());
		usr.setUsername(user.getUsername());
		usr.setAvatarPath(user.getAvatarPath());
		usr.setPassword(user.getPassword());
		usr.setEmail(user.getEmail());
		usr.setBanExpire(user.getBanExpire());
		em.flush();
	}

	public User getById(long userId) {
		return em.find(User.class, userId);
	}
	
	public boolean usernameExists(String username) {
		String hql = "FROM User as usr WHERE usr.username = :username";
		try {
			em.createQuery(hql, User.class).setParameter("username", username).getSingleResult();
		} catch (NoResultException noResultException) {
			return false;
		}
		return true;
	}
	
	public boolean emailExists(String email) {
		String hql = "FROM User as usr WHERE usr.email = :email";
		try {
			em.createQuery(hql, User.class).setParameter("email", email).getSingleResult();
		} catch (NoResultException noResultException) {
			return false;
		}
		return true;
	}
	
	public void banUser(User user, Date date) {
		user.setBanExpire(date);
		user.setBanCount(user.getBanCount() + 1);
		em.flush();
	}
	
	public List<User> getBestUsers() {
		String hql = "FROM User as u ORDER BY u.commentsCount DESC";
		return em.createQuery(hql, User.class).setMaxResults(5).getResultList();
	}

}
