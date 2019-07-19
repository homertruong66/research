package com.tech3s.jcoder.lib;

import com.tech3s.jcoder.model.Customer;
import com.tech3s.jcoder.model.Order;
import com.tech3s.jcoder.model.OrderLine;
import com.tech3s.jcoder.model.Product;

import java.util.Scanner;

public class EcommerceLibrary {

    public static Product createProduct(Scanner scanner) {	
		System.out.println("Create a Product...");
		
		Product product = new Product();    	
		System.out.print("Please enter id: ");
	    String id = scanner.next();
	    product.setId(id);
		
	    System.out.print("Please enter title: ");
	    String title = scanner.next();
	    product.setTitle(title);
	    
	    System.out.print("Please enter description: ");
	    String description = scanner.next();
	    product.setDescription(description);
	    
	    System.out.print("Please enter price: ");
	    double price = scanner.nextDouble();
	    product.setPrice(price);
	    
	    System.out.print("Please enter quantity: ");
	    int quantity = scanner.nextInt();
	    product.setQuantity(quantity);
	    
	    return product;
    }
    
    public static Customer createCustomer(Scanner scanner) {	
		System.out.println("Create a Customer...");
		
		Customer customer = new Customer();    	
		System.out.print("Please enter id: ");
	    String id = scanner.next();
	    customer.setId(id);
		
	    System.out.print("Please enter code: ");
	    String code = scanner.next();
	    customer.setCode(code);
	    
	    System.out.print("Please enter email: ");
	    String email = scanner.next();
	    customer.setEmail(email);
	    
	    System.out.print("Please enter name: ");
	    String name = scanner.next();
	    customer.setName(name);	    	   
	    	    
	    return customer;
    }
    
    public static OrderLine createOrderLine(Scanner scanner) {	
    	System.out.println("Create an OrderLine...");
    	
		 OrderLine orderLine = new OrderLine();
		 
		 System.out.print("Please enter id: ");
		 String id = scanner.next();
		 orderLine.setId(id);
		 
		 System.out.print("Please enter sales price: ");
		 double salesPrice = scanner.nextDouble();
		 orderLine.setPrice(salesPrice);

		 System.out.print("Please enter quantity: ");
		 int salesQuantity = scanner.nextInt();
		 orderLine.setQuantity(salesQuantity);		 	     
	     
	     return orderLine;
    }
    
    public static Order createOrder(Scanner scanner) {
    	System.out.println("Create an Order...");
    	
		Order order = new Order();
		
		System.out.print("Please enter id: ");
	    String id = scanner.next();
	    order.setId(id);
	    
		System.out.print("Please enter Order code: ");
		String code = scanner.next();
		order.setCode(code);
	     
	    return order;
    }
    
}
