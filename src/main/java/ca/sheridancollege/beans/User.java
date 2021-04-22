package ca.sheridancollege.beans;

import javax.persistence.*;

@Entity
@Table(name="\"user\"")
public class User {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	
	@Column(name="email")
	private String name;
}
