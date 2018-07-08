package com.fis.service;

import java.util.Date;
import java.util.List;

public class CreditCardProcessor {
	
	private final class CreditCardWorker implements Runnable {
		private final String cardType;
		private final List<CreditCardDTO> cardsList;
		
		public CreditCardWorker(String cardType, List<CreditCardDTO> cards) {
			this.cardType = cardType;
			this.cardsList = cards;
		}
			@Override
			public void run() {
				try {
					CreditCardEngine engine = new CreditCardEngine();
					String[] cards = engine.generateCardNumbers(cardType, 1);
					CreditCardDTO dto = new CreditCardDTO();
					dto.setCardNumber(cards[0]);
					dto.setExpiryDate(new Date());
					cardsList.add(dto);
				} catch (Exception e) {
					System.out.println("Exception while processing data: " + e);
				}
			}
			
	}
	
	public Runnable worker(String cardType, List<CreditCardDTO> cards) {
		return new CreditCardWorker(cardType, cards);
	}

}
