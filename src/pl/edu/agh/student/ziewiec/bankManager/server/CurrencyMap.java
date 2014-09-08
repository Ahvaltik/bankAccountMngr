package pl.edu.agh.student.ziewiec.bankManager.server;

import java.util.HashMap;

public class CurrencyMap {
	public static HashMap<String, Float> interestRate = new HashMap<String, Float>();
	public static HashMap<String, Float> exchangeRate = new HashMap<String, Float>();
	
	static {
		interestRate.put("PLN", (float) 1);
		interestRate.put("USD", (float) 1);
		
		exchangeRate.put("PLN", (float) 1);
		exchangeRate.put("USD", (float) 1);
	}
}
