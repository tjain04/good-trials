package com.fis.service;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

public class CreditCardTest {
	CreditCardEngine calculator;
		@Before
		public void setUp() {
			CreditCardEngine calculator = new CreditCardEngine();	
		}
		
		@Test
		public void testCreditCardArgs() {			
			Boolean success = calculator.startCreditCardEngine(null, null);
			assertFalse(success);
		}

}
