package ca.sheridancollege.configurations;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.stereotype.Service;

import ca.sheridancollege.dao.DataAccessObject;
import ca.sheridancollege.repository.UserRepo;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepo userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		ca.sheridancollege.beans.User user = userRepo.findByName(username);
		
		if (user == null) {
			throw new UsernameNotFoundException("User " + username + " was not found in the database");
		}

		if (!DataAccessObject.checkUsernamePassword(username, user.getPassword())) {	
			throw new UsernameNotFoundException("Wrong Password!");
		}

		List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();

		//NEEDS UPDATING
		grantList.add(new SimpleGrantedAuthority("ROLE_ARTIST"));

		UserDetails userDetails = (UserDetails)new User(user.getName(), user.getPassword(), grantList);
		
		return userDetails;
	}

}
