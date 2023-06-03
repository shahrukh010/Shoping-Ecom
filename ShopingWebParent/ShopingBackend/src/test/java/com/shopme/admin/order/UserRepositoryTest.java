package com.shopme.admin.order;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.shopme.admin.repository.UserRepository;
import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;

@DataJpaTest(showSql = true)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTest {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private TestEntityManager entityManager;

//	@Test
//	public void testCreateNewUserWithTwoRole() {
//
//		User user = new User("redhat", "Alex", "Smith", "alex@gmail.com");
//		Role roleEditor = new Role(4l);
//		Role roleAssistant1 = new Role(5l);
//		Role roleAssistant2 = new Role(3l);
//		user.addRole(roleEditor);
//		//here you can one user have same role two times so avoid duplicate object
//		//in role class i have implemented equals(),hasCode()
//		user.addRole(roleAssistant1);
//		user.addRole(roleAssistant2);
//		//or same object multiple times if we not implement equals() and hashCode() then in db
//		//two times will be same role for user 
////		user.addRole(roleAssistant1);
//		
//		
//
//		User saveUser = userRepo.save(user);
//	}

}
