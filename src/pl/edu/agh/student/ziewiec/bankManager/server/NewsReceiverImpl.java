package pl.edu.agh.student.ziewiec.bankManager.server;

import java.util.Map;

import FinancialNews.Currency;
import FinancialNews._NewsReceiverDisp;
import Ice.Current;

@SuppressWarnings("serial")
public class NewsReceiverImpl extends _NewsReceiverDisp {
	Map<String,Float> exchangeRate;
	Map<String,Float> interestRate;
	
	private NewsReceiverImpl(){
		
	}

	@Override
	public void exchangeRate(Currency curr1, Currency curr2, float rate,
			Current __current) {
		// TODO Auto-generated method stub

	}

	@Override
	public void interestRate(Currency curr, float rate, Current __current) {
		// TODO Auto-generated method stub

	}

}
