package pl.edu.agh.student.ziewiec.bankManager.server;

import Bank.IncorrectAccountNumber;
import Bank.IncorrectAmount;
import Bank.IncorrectData;
import Bank._PremiumAccountDisp;
import Bank.currency;
import Ice.Current;
import Ice.FloatHolder;
import Ice.IntHolder;

@SuppressWarnings("serial")
public class PremiumAccountImpl extends _PremiumAccountDisp {

	private AccountData account;
	@SuppressWarnings("unused")
	private String accountNumber;

	public PremiumAccountImpl(String value) {
		this.account = SerializationService.deserialize(value);
		this.accountNumber = value;
	}

	@Override
	public void calculateLoan(int amount, currency curr, int period,
			IntHolder totalCost, FloatHolder interestRate, Current __current)
			throws IncorrectData {

		interestRate.value = CurrencyMap.interestRate.get(curr.toString());
		totalCost.value = (int)((amount * CurrencyMap.exchangeRate.get(curr.toString())) *(1 + interestRate.value));
		totalCost.value -= totalCost.value *  this.account.getRateDecrease();
	}

	@Override
	public int getBalance(Current __current) {
		return account.getBalance();
	}

	@Override
	public String getAccountNumber(Current __current) {
		return account.getAccountNumber();
	}

	@Override
	public void transfer(String accountNumber, int amount, Current __current)
			throws IncorrectAccountNumber, IncorrectAmount {
		int senderBalance = account.getBalance();
		if (amount > senderBalance || amount < 0)
			throw new IncorrectAmount();
		if (!BankManagerImpl.getInstance().contains(accountNumber))
			throw new IncorrectAccountNumber();
		AccountData receiverAccount = SerializationService.deserialize(accountNumber);
		receiverAccount.add(amount);
		account.substract(amount);
		SerializationService.serialize(account);
		SerializationService.serialize(receiverAccount);
	}

}
