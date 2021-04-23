package ca.sheridancollege.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import ca.sheridancollege.beans.User;

public interface UserRepo extends CrudRepository<User, Integer>{

	public User findByName(String name);
	
	@Query(value = "select distinct u.id, u.email, u.password, u.role " 
			+ "from public.user u "
			+ "inner join public.messages m "
			+ "on u.id = m.\"receiverId\" "
			+ "where m.\"senderId\" = ?1 "
			+ "union "
			+ "select distinct u.id, u.email, u.password, u.role " 
			+ "from public.user u "
			+ "inner join public.messages m "
			+ "on u.id = m.\"senderId\" "
			+ "where m.\"receiverId\" = ?1", nativeQuery = true)
	public List<User> getUsersInConversationWithLoggedInUser(Integer userId);
}
