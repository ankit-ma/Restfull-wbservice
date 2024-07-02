package com.ankit.webservice.user;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

import org.springframework.stereotype.Controller;

@Controller
public class UserServiceDao {
	
	public static List<User> users = new ArrayList<>();
	public static int userCount=1;
	static {
//		users.add(new User(userCount++, "Ankit Kumar", LocalDate.now().minusYears(21)));
//		users.add(new User(userCount++, "Ankit Sharma", LocalDate.now().minusYears(20)));
//		users.add(new User(userCount++, "Ankit Singh", LocalDate.now().minusYears(22)));
//		users.add(new User(userCount++, "Ankit Gulatia", LocalDate.now().minusYears(24)));
	}
	
	public List<User> findAll(){
		return users;
	}

	public User findById(Integer id) {
		// TODO Auto-generated method stub
		Predicate<? super User> predicate = users -> users.getId().equals(id);
		return users.stream().filter(predicate).findFirst().orElse(null);
	}

	public User save(User user) {
		user.setId(userCount++);
		users.add(user);
		return user;
	}

	public void deleteById(Integer id) {
		Predicate<? super User> predicate = users -> users.getId().equals(id);
		users.removeIf(predicate);
		
	}

}
