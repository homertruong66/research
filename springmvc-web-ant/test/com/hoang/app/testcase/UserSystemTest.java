package com.hoang.app.testcase;
import com.thoughtworks.selenium.SeleneseTestCase;

public class UserSystemTest extends SeleneseTestCase {
	
	public void setUp() throws Exception {
//		setUp("http://localhost:8080/", "*firefox");
		setUp("http://localhost:8080/", "*googlechrome");
//		setUp("http://localhost:8080/", "*iexplore");
	}
	
	public void testLogin() throws Exception {		
		selenium.open("/yjf/login.htm");
		selenium.waitForPageToLoad("3000");
		selenium.type("j_username", "hoang");
		selenium.type("j_password", "hoang");
		selenium.click("btnLogin");
		selenium.waitForPageToLoad("5000");
		assertTrue(selenium.isTextPresent("This is your homepage"));		
		selenium.close();
	}
	
//	public void testSearch() throws Exception {		
//		selenium.open("/PMS");
//		selenium.waitForPageToLoad("3000");
//		selenium.type("j_username", "hoang");
//		selenium.type("j_password", "hoang");
//		selenium.click("btnLogin");
//		selenium.waitForPageToLoad("5000");
////		selenium.type("searchProductId", "1");
//		selenium.click("btnSearch");		
//		Thread.sleep(1000);
//		assertTrue(selenium.isTextPresent("2 found products"));			
//		selenium.close();
//	}
	
//	public void testAddandDel() throws Exception {		
//		selenium.open("/yjf");
//		selenium.waitForPageToLoad("3000");
////		selenium.type("j_username", "hoang");
////		selenium.type("j_password", "hoang");
////		selenium.click("btnLogin");		
////		selenium.waitForPageToLoad("5000");
//		selenium.click("id=btnProductAdd");
//		selenium.type("productId", "1");
//		selenium.type("group", "1");
//		selenium.type("description", "1");
//		selenium.click("isSet");
//		selenium.click("btnProductSave");		
//		Thread.sleep(1000);
//		assertTrue(selenium.isTextPresent("1 found products"));
//				
//		selenium.chooseOkOnNextConfirmation();
//		selenium.click("css=div.r5");
//		selenium.click("id=btnProductDelete");
////		assertTrue(selenium.isConfirmationPresent());		
////		System.out.println("Close dialog: '" + selenium.getConfirmation() + "'");		
//		assertTrue(selenium.isTextPresent("0 found products"));		
//		selenium.close();
//	}
}
