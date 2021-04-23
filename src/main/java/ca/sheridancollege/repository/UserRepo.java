package ca.sheridancollege.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import ca.sheridancollege.beans.User;

public interface UserRepo extends CrudRepository<User, Integer>{

	public User findByEmail(String email);
	
	@Query(value = "select distinct u.user_id, u.email, u.password, u.name " 
			+ "from public.user u "
			+ "inner join public.messages m "
			+ "on u.user_id = m.\"receiverId\" "
			+ "where m.\"senderId\" = ?1 "
			+ "union "
			+ "select distinct u.user_id, u.email, u.password, u.name " 
			+ "from public.user u "
			+ "inner join public.messages m "
			+ "on u.user_id = m.\"senderId\" "
			+ "where m.\"receiverId\" = ?1", nativeQuery = true)
	public List<User> getUsersInConversationWithLoggedInUser(Integer userId);
}
