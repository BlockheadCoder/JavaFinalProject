package ca.sheridancollege.repository;

import org.springframework.data.repository.CrudRepository;

import ca.sheridancollege.beans.User;

public interface UserRepo extends CrudRepository<User, Integer>{

	public User findByName(String name);
	
}
