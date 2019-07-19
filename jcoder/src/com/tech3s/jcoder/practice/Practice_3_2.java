package com.tech3s.jcoder.practice;

import java.util.Scanner;

public class Practice_3_2 {

	public static void main(String[] args) {
		System.out.println("Print star structure...");

        Scanner scanner = new Scanner(System.in);
        
        int n = 0;
        do {
        	System.out.print("Please enter n (n>0): ");
        	n = scanner.nextInt();
        }
        while (n <=0);
        
        System.out.println("1. n*n star matrix:");
        for (int i = 1; i <= n; i ++) {
            for (int j = 1; j <= n; j ++) {
                System.out.print("* ");
            }
            System.out.println();
        }
        
        System.out.println("\n2. Left star triangle:");
        for (int i = 1; i <= n; i ++) {
            for (int j = 1; j <= i; j ++) {
                System.out.print("* ");
            }
            System.out.println();
        }
        
        System.out.println("\n3. Inversed Left star triangle:");
        for (int i = 1; i <= n; i ++) {
            for (int j = 1; j <= n-i+1; j ++) {
                System.out.print("* ");
            }
            System.out.println();
        }       
        
        System.out.println("\n4. Right star triangle:");
        for (int i = 1; i <= n; i ++) {
            for (int j = 1; j <= n; j ++) {
            	if (j == n - i + 1) {
            		// print stars
            		for (int k = j; k <= n; k++) {
            			System.out.print("* ");
            		}
            	}
            	else {
            		System.out.print("  ");
            	}
            }
            System.out.println();
        }        
        
        System.out.println("\n5. Inversed Right star triangle:");
        for (int i = 1; i <= n; i ++) {
            for (int j = 1; j <= n; j ++) {
            	if (j == i) {
            		// print stars
            		for (int k = j; k <= n; k++) {
            			System.out.print("* ");
            		}
            	}
            	else {
            		System.out.print("  ");
            	}
            }
            System.out.println();
        }   
        
        System.out.println("\n6. Star pyramid:");
        for(int i = 1; i <= n; i ++) {
        	// print spaces
            for(int j = 1; j <= n-i; j ++){
                System.out.print("  ");
            }
            
            // print stars
            for(int k = 1; k <= 2*i-1; k ++){
                System.out.print("* ");
            }

            System.out.println();
        }
        
		scanner.close();
	}

}
