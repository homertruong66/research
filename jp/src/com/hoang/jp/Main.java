package com.hoang.jp;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
	    System.out.println("Solve equation: a*x*x + b*x + c = 0");
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter an integer as a: ");
        int a = scanner.nextInt();

        System.out.print("Enter an integer as b: ");
        int b = scanner.nextInt();

        System.out.print("Enter an integer as c: ");
        int c = scanner.nextInt();

        float delta = b*b - 4*a*c;
        boolean isEquationNoRoot = delta < 0;
        if (isEquationNoRoot) {
            System.out.print("Equation has no root!");
            return;
        }

        double root1 = -(b - Math.sqrt(delta))/(2*a);
        double root2 = -(b + Math.sqrt(delta))/(2*a);
        System.out.println("First root x1 = " + root1);
        System.out.println("Second root x2 = " + root2);

    }
}
