package ca.sheridancollege.beans;

import javax.persistence.*;

@Entity
public class User {
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	private String name;
		
}
