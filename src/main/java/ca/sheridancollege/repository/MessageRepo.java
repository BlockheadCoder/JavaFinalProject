package ca.sheridancollege.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import ca.sheridancollege.beans.Message;

public interface MessageRepo extends CrudRepository<Message, Integer>{

	@Query(value = "SELECT * FROM public.messages WHERE (\"senderId\"=?1 AND \"receiverId\"=?2) OR (\"senderId\"=?2 AND \"receiverId\"=?1)", nativeQuery = true)
    public List<Message> getAllMessagesForSenderAndReciever(Integer userId, Integer recId);
}
