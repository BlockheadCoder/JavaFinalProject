package ca.sheridancollege.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import ca.sheridancollege.beans.Message;

public interface MessageRepo extends CrudRepository<Message, Integer>{


	
}
