package ca.sheridancollege.beans;

import java.util.Date;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name="\"messages\"")
public class Message {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	private String message;
	
	@Column(name="\"senderId\"")
	private String senderId;
	
	@Column(name="\"receiverId\"")
	private String receiverId;
	
	private Boolean seen;
	private Date created_at; //changed to date
	
	public Message(String input, String senderId, String receiverId) {
		this.message = input;
		this.senderId = senderId;
		this.receiverId = receiverId;
	}
	
	public Message(String string) {
		this.message = string;
	}
}
