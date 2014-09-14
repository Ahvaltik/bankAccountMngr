import Ice
import sys
Ice.loadSlice("../../../../../../../../slice/Bank.ice")
import Bank

class Client(Ice.Application):
	def run(self, args):
		connection_line = ":tcp -h 127.0.0.1 -p 11000:ssl -h 172.7.7.1 -p 10001"
		manager = Bank.BankManagerPrx.checkedCast(self.communicator().stringToProxy("bankManager/bankManager"+connection_line))
		answer_line = ""
		print("""quit
		new
		delete
		balance
		account number
		transfer""")
		while answer_line.lower() != "quit" or answer_line != "q":
			if answer_line.lower() == "new" :
				account_data = Bank.PersonalData()
				account_data.firstName = raw_input("First name : ")
				account_data.firstName = raw_input("First name : ")
				dummy = raw_input("Account type [SILVER/PREMIUM]")
				if dummy.upper() == "PREMIUM":
					account_type = Bank.accountType.PREMIUM
				else:
					account_type = Bank.accountType.SILVER
				print("Creating account..")
				account_id = manager.createAccount(account_data, account_type, None)
				print(account_id + " created.")
			elif answer_line.lower() == "delete" :
				account_id = raw_input("Account ID : ")
				manager.removeAccount(accountID)
			elif answer_line.lower() == "balance" :
				account_id = raw_input("Account ID : ")
				account = Bank.AccountPrx.checkedCast(self.communicator().stringToProxy("account/" + str(accountID) + connection_line))
				print(account.getBalance())
			elif answer_line.lower() == "account number" :
				account_id = raw_input("Account ID : ")
				account = Bank.AccountPrx.checkedCast(self.communicator().stringToProxy("account/" + str(accountID) + connection_line))
				print(account.getAccountNumber())
			elif answer_line.lower() == "transfer" :
				account_id_1 = raw_input("Account ID : ")
				account_1 = Bank.AccountPrx.checkedCast(self.communicator().stringToProxy("account/" + str(accountID) + connection_line))
				account_id_2 = raw_input("Recipient Account ID : ")
				amount = raw_input("Amount : ")
				account_1.transfer(account_id_2, int(amount))
			elif answer_line.lower() == "calculate loan" :
				account_id = raw_input("Account ID : ")
				account = Bank.AccountPrx.checkedCast(self.communicator().stringToProxy("account/" + str(accountID) + connection_line))
				amount = raw_input("Amount : ")
				currency = raw_input("Currency : ")
				if currency == "PLN":
					curr_id = Bank.currency.PLN
				elif currency == "USD":
					curr_id = Bank.currency.USD
				time = raw_input("Time : ")
				(cost, interest_rate) = account.calculateLoan(int(amount), curr_id, int(period), None)
				print("Cost: " + str(cost) + " interest_rate: " + str(interest_rate))
		return 0

app = Client()
sys.exit(app.main(sys.argv, ""))