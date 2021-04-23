package ca.sheridancollege.repository;

import org.springframework.data.repository.CrudRepository;

import ca.sheridancollege.beans.Role;

public interface RoleRepo extends CrudRepository<Role, Integer>{

	public Role findByName(String name);
	
}
