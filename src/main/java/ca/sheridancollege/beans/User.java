package ca.sheridancollege.beans;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class User {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	public enum UserType {
		CUSTOMER,
		ARTIST
	}
	
	private String name;
	
	private UserType type;
		
}
