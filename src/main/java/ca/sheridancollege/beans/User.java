package ca.sheridancollege.beans;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Builder
@Table(name="\"user\"")
public class User {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;

	@Column(name="email", unique=true)
	private String name;

	public enum UserType {
		ROLE_CUSTOMER,
		ROLE_ARTIST
	}
	
	private String password;
	
	private UserType type;
		
}
