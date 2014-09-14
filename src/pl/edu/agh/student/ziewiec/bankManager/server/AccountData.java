package pl.edu.agh.student.ziewiec.bankManager.server;

import java.io.Serializable;

import Bank.PersonalData;
import Bank.accountType;

public class AccountData implements Serializable {

	private float rateDecrease;
	private int balance;
	private String accountNumber;
	private accountType type;
	private PersonalData data;

	public AccountData(PersonalData data, accountType type, String value) {
		this.data = data;
		this.type = type;
		this.accountNumber = value;
		balance = 1000;
		setRateDecrease(0);
	}

	public void add(int amount) {
		//System.out.println(accountNumber+" +"+amount);
		balance += amount;
	}

	public void substract(int amount) {
		//System.out.println(accountNumber+" -"+Integer.toString(amount));
		balance -= amount;
	}
	
	public Bank.accountType getType() {
		return type;
	}
	
	public PersonalData getData() {
		return data;
	}

	public int getBalance() {
		return balance;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public float getRateDecrease() {
		return rateDecrease;
	}

	public void setRateDecrease(float rateDecrease) {
		this.rateDecrease = rateDecrease;
	}

}
