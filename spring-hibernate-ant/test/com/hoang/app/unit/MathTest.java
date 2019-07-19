package com.hoang.app.unit;

import junit.framework.TestCase;

/**
 * 
 * @author Hoang Truong
 */

public class MathTest extends TestCase {

    public void testAddition() {
        System.out.println("Testing addition...");
        assertEquals(2, 1+1);        
    }

    public void testSubtraction() {
        System.out.println("Testing substraction...");
        assertEquals(0, 1-1);
    }

    public void testMultiplication() {
        System.out.println("Testing multiplication...");
        assertEquals(2, 1*2);
    }

    public void testDivision() {
        System.out.println("Testing division...");
        assertEquals(1, 2/2);
    }
}
