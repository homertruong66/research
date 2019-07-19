package com.tech3s.jcoder.practice;

import com.tech3s.jcoder.model.Product;

import java.util.List;
import java.util.UUID;

public class Practice_5_4 {

	public static void main(String[] args) {
        System.out.println("CRUD a Product...");

        // create a Product
        Product product = new Product();
        String uuid = UUID.randomUUID().toString();
        product.setId(uuid);
        product.setTitle("title1");
		product.setDescription("description1");
		product.setPrice(66);
		product.setQuantity(6);

		ProductDao productDao = new ProductDao();
		productDao.create(product);
		System.out.println("Inserted Product: " + product);

		// read a Product
		Product readProduct = productDao.read(product.getId());
		System.out.println("Read Product: " + readProduct);

		// list Products
		List<Product> products = productDao.list();
		System.out.println("Product List: " + products);

		// update a Product
		product.setTitle("title1u");
		product.setDescription("description1u");
		product.setPrice(660);
		product.setQuantity(60);
		productDao.update(uuid, product);
		Product updatedProduct = productDao.read(product.getId());
		System.out.println("Updated Product: " + updatedProduct);
		
		// delete a Product
		productDao.delete(product.getId());
	}

}
