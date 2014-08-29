package pl.edu.agh.student.ziewiec.bankManager.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.List;

import Bank.NoSuchAccount;

public class SerializationService {

	public static AccountData deserialize(String id) {
		AccountData data = null;
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream("serializedAccounts/" + id));
			data = (AccountData) in.readObject();
	        in.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		return data;
	}

	public static void serialize(AccountData account) {
		try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("serializedAccounts/"
					+ account.getAccountID().toString()));
			out.writeObject(account);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static List<String> getAccountList() {
		List<String> accountNames = new LinkedList<String>();
		File folder = new File("serializedAccounts");
		File[] accountFileList = folder.listFiles();
		for (File accountFile : accountFileList) {
			if (accountFile.isFile()) {
				accountNames.add(accountFile.getName());
			}
		}
		return accountNames;
	}

	public static int getMaxAccountNumber() {
		int maxAccountNumber = 0;
		File folder = new File("serializedAccounts");
		File[] accountFileList = folder.listFiles();
		for (File accountFile : accountFileList) {
			if (accountFile.isFile()) {
				if (Integer.parseInt(accountFile.getName())>= maxAccountNumber){
					maxAccountNumber = Integer.parseInt(accountFile.getName());
					maxAccountNumber++;
				}
			}
		}
		return maxAccountNumber;
	}

	public static void remove(String accountID) throws NoSuchAccount {
		File file = new File("serializedAccounts/" + accountID);
		if (!file.delete()) {
			throw new Bank.NoSuchAccount();
		}
		
	}

}
