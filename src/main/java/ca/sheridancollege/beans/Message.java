package ca.sheridancollege.beans;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
public class Message {
	
	
	public Message(String input, String senderId, String receiverId) {
		this.message = input;
		this.senderId = senderId;
		this.receiverId = receiverId;
	}
	
	public Message(String string) {
		this.message = string;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	private String message;
	private String senderId;
	private String receiverId;
	private Boolean seen;
	private String created_at;
	
	
}
