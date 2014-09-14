package pl.edu.agh.student.ziewiec.bankManager.server;

import Bank.IncorrectAccountNumber;
import Bank.IncorrectAmount;
import Bank._AccountDisp;
import Ice.Current;
import Ice.Identity;

@SuppressWarnings("serial")
public class AccountImpl extends _AccountDisp {
	
	String id;
	AccountData account;
	public AccountImpl(String id){
		this.id = id;
		this.account = SerializationService.deserialize(id);
	}

	@Override
	public int getBalance(Current __current) {
		this.account = SerializationService.deserialize(id);
		return account.getBalance();
	}

	@Override
	public String getAccountNumber(Current __current) {
		this.account = SerializationService.deserialize(id);
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
