package pl.edu.agh.student.ziewiec.bankManager.server;


public class Server {

	public static void main(String[] args) {
		Ice.Communicator communicator = null;
		try{
		communicator = Ice.Util.initialize(args);
		
		Ice.ObjectAdapter objectAdapter = communicator.createObjectAdapter("Adapter");
		
		//NewsServerPrx financialNews = NewsServerPrxHelper.checkedCast(communicator.propertyToProxy("FinancialNews").ice_twoway().ice_timeout(1).ice_secure(false));
		
		Ice.ServantLocator servantLocator = new EvictorImpl();
		objectAdapter.addServantLocator(servantLocator, "account");
		
		BankManagerImpl bankManager = BankManagerImpl.getInstance();
		bankManager.setCommunicator(communicator);
		Ice.Identity bankManagerIdentity = new Ice.Identity();
		bankManagerIdentity.category = "bankManager";
		bankManagerIdentity.name = "bankManager";
		objectAdapter.add(bankManager, bankManagerIdentity);
		
		objectAdapter.activate();
		
		communicator.waitForShutdown();
		} catch(Exception e){
			
		} finally {
			if(communicator!=null){
				communicator.destroy();
			}
		}
	}

}
