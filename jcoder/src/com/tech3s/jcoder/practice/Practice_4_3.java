package com.tech3s.jcoder.practice;

import com.tech3s.jcoder.model.Customer;
import com.tech3s.jcoder.model.Order;
import com.tech3s.jcoder.model.OrderLine;
import com.tech3s.jcoder.model.Product;

import java.util.*;

public class Practice_4_3 {

	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);
		Random random = new Random();

        // create 3 Customers: code C3, C6, C9 -> save them in a List, print them
		List<Customer> customers = new ArrayList<>();
        for (int i = 3; i <= 9; i+=3) {
        	Customer customer = new Customer();
        	customer.setId("" + i);    		
    	    customer.setCode("C" + i);    	    
    	    customer.setEmail("customer" + i + "@gmail.com");
    	    customer.setName("name_" + random.nextInt(1000));
    	    customers.add(customer);
        }
        System.out.println("Customers: " + customers);
        
		// create 1 Order for C3, 2 Orders for C6, 3 Orders for C9 -> save to a Set
		Product product = new Product();
		product.setId("1");
		product.setTitle("title" + random.nextInt());
		product.setDescription("description" + random.nextInt());
		product.setPrice(100);
		product.setQuantity(10);

		OrderLine orderLine = new OrderLine();
		orderLine.setId("1");
		orderLine.setPrice(90);
		orderLine.setQuantity(6);
		orderLine.setProduct(product);

        Set<Order> orders = new HashSet<>();
        for (int i = 1; i <= 6; i++) {
    		Order order = new Order();
    		order.setId("" + i);    		
    	    order.setCode("O" + random.nextInt(1000));
    	    order.getOrderLineList().add(orderLine);
    	    if (i == 1) {
				order.setCustomer(getCustomer(customers, "C3"));
			}
    	    else if (i <= 3) {
				order.setCustomer(getCustomer(customers, "C6"));
			}
    	    else {
				order.setCustomer(getCustomer(customers, "C9"));
			}
    	    orders.add(order);
        }
        System.out.println("\nSet of Orders: " + orders);

        // sort Customers by name 
        Collections.sort(customers, Comparator.comparing(Customer::getName));
        System.out.println("\nSorted Customers by name: " + customers);
        
        // save Orders in Lists, Customers and its Orders in a Map -> print the Map
        Map<String, List<Order>> customerCode2Orders = new HashMap<>();
        customerCode2Orders.put("C3", getOrdersByCustomerCode(orders,"C3"));
		customerCode2Orders.put("C6", getOrdersByCustomerCode(orders,"C6"));
		customerCode2Orders.put("C9", getOrdersByCustomerCode(orders,"C9"));
        System.out.println("\nCustomerCodeToOrders: " + customerCode2Orders);
      
        // Ask a Customer enter a code -> if code = C3, C6 or C9 print its corresponding Orders
        // otherwise show error and ask again.
        String customerCode;
        do {
        	System.out.print("\nPlease enter Customer code: ");
        	customerCode = scanner.next();
        	System.out.print("Orders of " + customerCode + ": ");
        	switch (customerCode) {
	            case "C3":
	                System.out.println(customerCode2Orders.get(customerCode));
	                break;
	
	            case "C6":
	                System.out.println(customerCode2Orders.get(customerCode));
	                break;
	
	            case "C9":
	                System.out.println(customerCode2Orders.get(customerCode));
	                break;
	
	            default:
	                System.out.println("No Orders, there are only Orders of Customer Code = C3/C6/C9!");
	        }       
        }
        while (!customerCode.equals("C3") && 
        		!customerCode.equals("C6") && !customerCode.equals("C9"));
        		        
		scanner.close();
	}

	private static Customer getCustomer(List<Customer> customers, String code) {
		if (code == null || code.equals("")) {
			return null;
		}

		Customer result = null;
		for (Customer customer: customers) {
			if (customer.getCode().equalsIgnoreCase(code)) {
				result = customer;
				break;
			}
		}

		return result;
	}

	private static List<Order> getOrdersByCustomerCode(Set<Order> orders, String customerCode) {
		if (customerCode == null || customerCode.equals("")) {
			return null;
		}

		List<Order> result = new ArrayList<>();
		for (Order order: orders) {
			if (order.getCustomer().getCode().equalsIgnoreCase(customerCode)) {
				result.add(order);
			}
		}

		return result;
	}

}
