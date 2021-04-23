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
public class User {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	public enum UserType {
		ROLE_CUSTOMER,
		ROLE_ARTIST
	}
	
	@Column(unique = true)
	private String name;
	
	private String password;
	
	private UserType type;
		
}
