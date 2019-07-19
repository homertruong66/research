package com.tech3s.jcoder.practice;

import com.tech3s.jcoder.model.Product;

import java.util.*;

public class Practice_3_4 {

	public static void main(String[] args) {

		// find first number 6
		int[] integers = new int[] {1,61,4,16,2,4,7,8,5,2,1,22,44,6,66};

		System.out.println("Find first number 6 in the list: " + Arrays.toString(integers));

		boolean found = false;
		for (int i = 0; i < integers.length ; i++) {
			if (integers[i] == 6) {
				System.out.println("The first number 6 is at index = " + (i+1));
				found = true;
				break;
			}
		}

		if (!found) {
			System.out.println("There's no number 6 in the list " + Arrays.toString(integers));
		}


		// create Product List and Set
		Random random = new Random();
		List<Product> productList = new ArrayList<>();
		Set<Product> productSet = new HashSet<>();
		for (int i = 0; i < 6; i++) {
			Product product = new Product();
			product.setTitle("Tittle " + (i+1));
			product.setDescription("Description " + (i+1));
			product.setPrice(random.nextInt(200));
			product.setQuantity(i*2 + 1);
			productList.add(product);
			productSet.add(product);
		}

		System.out.println("\nProducts from list: ");
		for (Product product: productList) {
			if (product.getPrice() >= 100) {
				System.out.println(product);
			}
		}

		System.out.println("\nProducts from set: ");
		for (Product product: productSet) {
			if (product.getPrice() >= 100) {
				System.out.println(product);
			}
		}
	}

}
