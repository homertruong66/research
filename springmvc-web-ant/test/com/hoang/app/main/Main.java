package com.hoang.app.main;

import junit.framework.Test;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

import com.hoang.app.testcase.UserSystemTest;

public class Main {

	public static Test suite() {
		TestSuite suite = new TestSuite();
		suite.addTestSuite(UserSystemTest.class);

		return suite;
	}

	public static void main(String[] args) {
		TestRunner.run(suite());
	}
}
