package com.fis.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;

/**
 *
 * Credit Card Engine for generating specific credit card numbers
 * 
 * @author tjain
 *
 */
public class CreditCardEngine {

	public static final String[] VISA_PREFIX_LIST = new String[] { "4539", "4556", "4916", "4532", "4929", "40240071",
			"4485", "4716", "4" };

	public static final String[] MASTERCARD_PREFIX_LIST = new String[] { "51", "52", "53", "54", "55", "2221", "2222",
			"2223", "2224", "2225", "2226", "2227", "2228", "2229", "223", "224", "225", "226", "227", "228", "229",
			"23", "24", "25", "26", "270", "271", "2720" };

	public static final String[] AMEX_PREFIX_LIST = new String[] { "34", "37" };

	public static final String[] DISCOVER_PREFIX_LIST = new String[] { "6011" };

	static String strrev(String str) {
		if (str == null)
			return "";
		String revstr = "";
		for (int i = str.length() - 1; i >= 0; i--) {
			revstr += str.charAt(i);
		}

		return revstr;
	}

	/*
	 * 'prefix' is the start of the CC number as a string, any number of digits.
	 * 'length' is the length of the CC number to generate. Typically 13 or 16
	 */
	static String completed_number(String prefix, int length) {

		String ccnumber = prefix;

		// generate digits

		while (ccnumber.length() < (length - 1)) {
			ccnumber += new Double(Math.floor(Math.random() * 10)).intValue();
		}

		// reverse number and convert to int

		String reversedCCnumberString = strrev(ccnumber);

		List<Integer> reversedCCnumberList = new Vector<Integer>();
		for (int i = 0; i < reversedCCnumberString.length(); i++) {
			reversedCCnumberList.add(new Integer(String.valueOf(reversedCCnumberString.charAt(i))));
		}

		// calculate sum

		int sum = 0;
		int pos = 0;

		Integer[] reversedCCnumber = reversedCCnumberList.toArray(new Integer[reversedCCnumberList.size()]);
		while (pos < length - 1) {

			int odd = reversedCCnumber[pos] * 2;
			if (odd > 9) {
				odd -= 9;
			}

			sum += odd;

			if (pos != (length - 2)) {
				sum += reversedCCnumber[pos + 1];
			}
			pos += 2;
		}

		// calculate check digit

		int checkdigit = new Double(((Math.floor(sum / 10) + 1) * 10 - sum) % 10).intValue();
		ccnumber += checkdigit;

		return ccnumber;

	}

	public static String[] credit_card_number(String[] prefixList, int length, int howMany) {

		Stack<String> result = new Stack<String>();
		for (int i = 0; i < howMany; i++) {
			int randomArrayIndex = (int) Math.floor(Math.random() * prefixList.length);
			String ccnumber = prefixList[randomArrayIndex];
			result.push(completed_number(ccnumber, length));
		}

		return result.toArray(new String[result.size()]);
	}

	public String[] generateCardNumbers(String cardType, int howMany) {

		switch (cardType) {
		case "VISA":
			return generateVISANumbers(howMany);
		case "MASTERCARD":
			return generateMasterCardNumbers(howMany);
		case "AMEX":
			return generateAmexNumbers(howMany);
		case "DISCOVER":
			return generateDiscoverNumbers(howMany);
		}
		return null;
	}

	public static String[] generateVISANumbers(int howMany) {
		return credit_card_number(VISA_PREFIX_LIST, 16, howMany);
	}

	public static String[] generateMasterCardNumbers(int howMany) {
		return credit_card_number(MASTERCARD_PREFIX_LIST, 16, howMany);
	}

	public static String[] generateAmexNumbers(int howMany) {
		return credit_card_number(AMEX_PREFIX_LIST, 16, howMany);
	}

	public static String[] generateDiscoverNumbers(int howMany) {
		return credit_card_number(DISCOVER_PREFIX_LIST, 16, howMany);
	}

	public static String generateMasterCardNumber() {
		return credit_card_number(MASTERCARD_PREFIX_LIST, 16, 1)[0];
	}

	public static boolean isValidCreditCardNumber(String creditCardNumber) {
		boolean isValid = false;

		try {
			String reversedNumber = new StringBuffer(creditCardNumber).reverse().toString();
			int mod10Count = 0;
			for (int i = 0; i < reversedNumber.length(); i++) {
				int augend = Integer.parseInt(String.valueOf(reversedNumber.charAt(i)));
				if (((i + 1) % 2) == 0) {
					String productString = String.valueOf(augend * 2);
					augend = 0;
					for (int j = 0; j < productString.length(); j++) {
						augend += Integer.parseInt(String.valueOf(productString.charAt(j)));
					}
				}

				mod10Count += augend;
			}

			if ((mod10Count % 10) == 0) {
				isValid = true;
			}
		} catch (NumberFormatException e) {
		}

		return isValid;
	}

	/*
	 * public static void main(String[] args) { int howMany = 0; try { howMany =
	 * Integer.parseInt(args[0]); } catch (Exception e) { System.err .println(
	 * "Usage error. You need to supply a numeric argument (ex: 500000)"); }
	 * String[] creditcardnumbers = generateMasterCardNumbers(howMany); for (int
	 * i = 0; i < creditcardnumbers.length; i++) {
	 * System.out.println(creditcardnumbers[i] + ":" +
	 * (isValidCreditCardNumber(creditcardnumbers[i]) ? "valid" : "invalid")); }
	 * }
	 */

	public static void main(String[] args) {
		String cardType = "";
		int cardCount = 0;
		try {
			cardType = args[0];
			cardCount = Integer.parseInt(args[1]);
		} catch (Exception e) {
			System.err.println("Usage error. You need to supply a numeric argument (ex: 500000)");
		}

		CreditCardEngine engine = new CreditCardEngine();
		engine.startCreditCardEngine(cardType, cardCount);

	}

	public boolean startCreditCardEngine(String cardType, Integer cardCount) {
		boolean success = true;
		if (cardCount == null || StringUtils.isNotEmpty(cardType)) {
			ExecutorService executorService = Executors.newFixedThreadPool(100, Executors.defaultThreadFactory());
			List<CreditCardDTO> list = new ArrayList<CreditCardDTO>();
			for (int i = 0; i < cardCount; i++) {
				Runnable worker = new CreditCardProcessor().worker(cardType, list);
				executorService.execute(worker);
			}
			executorService.shutdown();
			try {
				executorService.awaitTermination(1800000, TimeUnit.MILLISECONDS);
			} catch (InterruptedException ie) {
				System.out.println("Service Interrupted");
				executorService.shutdownNow();
			}

			//System.out.println(list.toString());
			Iterator<CreditCardDTO> iter  = list.iterator();
			while (iter.hasNext()) {
				CreditCardDTO dto = iter.next();
				System.out.println("Card Number: " + dto.getCardNumber() + " Card Expriry Date: " + dto.getExpiryDate());
			}
			System.out.println("Total card generated: " + list.size());
		} else {
			System.out.println("Bad Data suuplied");
			success = false;
		}
		return success;
	}
}