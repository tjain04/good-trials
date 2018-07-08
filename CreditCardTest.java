package com.jwt.rest;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class CreditCardTest {
	CreditCardEngine engine;
	List<CreditCardDTO> list;
		@Before
		public void setUp() {
			engine = new CreditCardEngine();
			list = new ArrayList<CreditCardDTO>();
		}
		
		@Test
		public void testCreditCardInvalidArgs() {			
			list = engine.startCreditCardEngine(null, null);
			assertTrue(list.isEmpty());
		}
		
		public void testVISACreditCard() {			
			list = engine.startCreditCardEngine("VISA", 2);
			assertTrue(list.size() == 2);
		}
		
		public void testAmexCardCreditCard() {			
			list = engine.startCreditCardEngine("AMEX", 4);
			assertTrue(list.size() == 4);
			assertTrue(list.get(0).getCardNumber().startsWith("3"));
		}
		
		public void testDiscoverCreditCard() {			
			list = engine.startCreditCardEngine("DISCOVER", 2);
			assertTrue(list.size() == 2);
			assertTrue(list.get(1).getCardNumber().startsWith("6"));
		}
		
		public void testMasterCreditCard() {			
			list = engine.startCreditCardEngine("MASTERCARD", 2);
			assertTrue(list.size() == 2);
		}


}
