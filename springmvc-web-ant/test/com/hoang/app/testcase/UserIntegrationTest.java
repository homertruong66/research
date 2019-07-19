package com.hoang.app.testcase;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hoang.app.boundary.UserFC;
import com.hoang.app.domain.User;
import com.hoang.app.dto.UserDTO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/test-context.xml"})
public class UserIntegrationTest {

    @Autowired
    private UserFC userFC;      

	@Test
	public void testCRUD() {
		UserDTO userDTO = null;
		User user = null;
		
		// create & read	
		try {
			userDTO = new UserDTO();
			userDTO.setUsername("test-username");
			userDTO.setPassword("test-password");
			userDTO.setFullname("test-fullname");
			user = userFC.create(userDTO);
			
			User user1 = userFC.read(user.getId());
			Assert.assertEquals(user.getUsername(), user1.getUsername());
			Assert.assertEquals(user.getPassword(), user1.getPassword());
			Assert.assertEquals(user.getFullname(), user1.getFullname());
		} 
		catch (Exception e) {
			e.printStackTrace();
		}			
		
		// update & read			
		try {
			userDTO.setId(user.getId());
			userDTO.setUsername("test-username-updated");
			userDTO.setPassword("test-password-updated");
			userDTO.setFullname("test-fullname-updated");
			user = userFC.update(userDTO);
			
			User user1 = userFC.read(user.getId());
			Assert.assertEquals(user.getUsername(), user1.getUsername());
			Assert.assertEquals(user.getPassword(), user1.getPassword());
			Assert.assertEquals(user.getFullname(), user1.getFullname());
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		
		// delete
		try {
			userFC.delete(user.getId());
			Assert.assertNull(userFC.read(user.getId()));
		} 
		catch (Exception e) {	
			e.printStackTrace();
		}
	}	
}
