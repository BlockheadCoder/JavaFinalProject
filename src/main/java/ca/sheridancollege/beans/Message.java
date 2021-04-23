package ca.sheridancollege.beans;

import java.util.Date;

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
@Table(name="\"messages\"")
public class Message {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	private String message;
	
	@Column(name="\"senderId\"")
	private int senderId;
	
	@Column(name="\"receiverId\"")
	private int receiverId;
	
	private Boolean seen;
	private Date created_at;
	
	public Message(String input, int senderId, int receiverId) {
		this.message = input;
		this.senderId = senderId;
		this.receiverId = receiverId;
	}
	
	public Message(String string) {
		this.message = string;
	}
}
