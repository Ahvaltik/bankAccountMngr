package pl.edu.agh.student.ziewiec.bankManager.server;

import java.util.List;

import Bank.IncorrectData;
import Bank.NoSuchAccount;
import Bank.PersonalData;
import Bank.RequestRejected;
import Bank._BankManagerDisp;
import Bank.accountType;
import Ice.Communicator;
import Ice.Current;
import Ice.Identity;
import Ice.StringHolder;

@SuppressWarnings("serial")
public class BankManagerImpl extends _BankManagerDisp {
	Communicator communicator;
	List<String> accountIdList;
	int maxAccountNumber;
	static BankManagerImpl _instance;
	
	private BankManagerImpl(){

		this.accountIdList = SerializationService.getAccountList();
		this.maxAccountNumber = SerializationService.getMaxAccountNumber();
	}
	
	public static BankManagerImpl getInstance(){
		if(_instance == null){
			_instance = new BankManagerImpl();
		}
		return _instance;
	}
	
	public void setCommunicator(Communicator communicator){
		this.communicator = communicator;
	}

	@Override
	public void createAccount(PersonalData data, accountType type,
			StringHolder accountID, Current __current) throws IncorrectData,
			RequestRejected {
		accountID.value = String.valueOf(maxAccountNumber);
		maxAccountNumber++;
		AccountData accountData = new AccountData(data,type,accountID.value);
		SerializationService.serialize(accountData);
		accountIdList.add(accountID.value);
		if(type == accountType.PREMIUM){
			PremiumAccountImpl account = new PremiumAccountImpl(accountID.value);
			__current.adapter.add(account, new Identity(accountID.value, "account"));
		}
	}

	@Override
	public void removeAccount(String accountID, Current __current)
			throws IncorrectData, NoSuchAccount {
		accountIdList.remove(accountID);
		SerializationService.remove(accountID);
	}

	public boolean contains(String accountNumber) {
		return accountIdList.contains(accountNumber);
	}

}
