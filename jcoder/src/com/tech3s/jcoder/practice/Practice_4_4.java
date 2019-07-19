package com.tech3s.jcoder.practice;

import com.tech3s.jcoder.model.Customer;
import com.tech3s.jcoder.model.Node;

public class Practice_4_4 {

	public static void main(String[] args) {
        // create a binary tree with 3 Customers: Dad -> Daughter & Son		
        Node rootNode = new Node();
        
        // create Dad
        Customer dad = new Customer();
    	dad.setId("dad123");    		
	    dad.setCode("dad");    	    
	    dad.setEmail("dad@gmail.com");
	    dad.setName("Daddy");	    
	    rootNode.setCustomer(dad);
	    
	    // create Daughter
	    Customer daughter = new Customer();
	    daughter.setId("daughter123");    		
	    daughter.setCode("daughter");    	    
	    daughter.setEmail("daughter@gmail.com");
	    daughter.setName("Daughter");
	    Node daughterNode = new Node();
	    daughterNode.setCustomer(daughter);
	    rootNode.setLeft(daughterNode);
	    
	    // create Son
        Customer son = new Customer();
    	son.setId("son123");    		
	    son.setCode("son");    	    
	    son.setEmail("son@gmail.com");
	    son.setName("Son");
	    Node sonNode = new Node();
	    sonNode.setCustomer(son);
	    rootNode.setRight(sonNode);
	            
        System.out.println("Binary family tree: " + rootNode);       
	}
}
