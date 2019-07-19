package com.tech3s.jcoder.practice;

import com.tech3s.jcoder.model.Customer;
import com.tech3s.jcoder.model.Order;
import com.tech3s.jcoder.model.OrderLine;
import com.tech3s.jcoder.model.Product;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Practice_4_2 {

	public static void main(String[] args) {

	    // read Products from a csv file
        System.out.println("Import Products...");

        List<Product> products = new ArrayList<>();
        File file = new File("E:\\Work\\Project\\jcoder\\src\\com\\tech3s\\jcoder\\products.csv");
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
            String line;
            while ( (line = br.readLine()) != null) {
                String[] tokens = line.split(",");
                Product product = new Product();
                product.setTitle(tokens[0]);
                product.setDescription(tokens[1]);
                product.setPrice(Double.parseDouble(tokens[2]));
                product.setQuantity(Integer.parseInt(tokens[3]));
                products.add(product);
            }
        }
        catch (IOException ioException) {
            ioException.printStackTrace();
        }

        for (Product product : products) {
            System.out.println(product);
        }

        // create a Sales Order
        OrderLine orderLine1 = new OrderLine();
        orderLine1.setId("1");
        orderLine1.setPrice(90);
        orderLine1.setQuantity(6);
        orderLine1.setProduct(products.get(1));

        OrderLine orderLine2 = new OrderLine();
        orderLine2.setId("2");
        orderLine2.setPrice(92);
        orderLine2.setQuantity(62);
        orderLine2.setProduct(products.get(2));

        Customer customer = new Customer();
        customer.setId("1");
        customer.setCode("C1");
        customer.setEmail("customer1@gmail.com");
        customer.setName("Customer 1");

        Order order = new Order();
        order.setId("1");
        order.setCode("O1");
        order.getOrderLineList().add(orderLine1);
        order.getOrderLineList().add(orderLine2);
        order.setCustomer(customer);

        System.out.println(order);
    }

}
