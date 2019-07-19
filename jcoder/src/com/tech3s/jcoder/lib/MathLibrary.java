package com.tech3s.jcoder.lib;

import java.util.ArrayList;
import java.util.List;

public class MathLibrary {

    /**
     * Solve first degree equation a*x + b = 0
     */
    public static Double solveFirstEquation(double a, double b) {
        if (a == 0) {            
            return null;
        }

        double root = -b / a;

        return root;
    }
    
    /**
     * Solve second degree equation a*x*x + b*x + c = 0
     */
    public static List<Double> solveSecondEquation(double a, double b, double c) {
    	List<Double> roots = new ArrayList<>();
    	
        if (a == 0) {
        	if (b == 0) {	            
	            return roots;
        	}
        	else {	// b*x + c = 0 (b != 0)
        		roots.add(solveFirstEquation(b, c));
        	}
        }
        else {
        	double delta = b*b - 4*a*c;
        	if (delta < 0) {        		
	            return roots;
        	}
        	else {
        		double root1 = (-b + Math.sqrt(delta))/(2*a);
        		roots.add(root1);
        		
        		double root2 = (-b - Math.sqrt(delta))/(2*a);        		
        		roots.add(root2);
        	}
        }

        return roots;
    }
}
