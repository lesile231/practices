package kr.lesile.kakaobank.codingtest.transaction.log.profiler.handler;

import org.junit.Before;
import org.junit.Test;

import kr.lesile.processes.transaction.log.common.customer.Account;
import kr.lesile.processes.transaction.log.common.customer.Customer;
import kr.lesile.processes.transaction.log.common.datamap.DataMap;
import kr.lesile.processes.transaction.log.profiler.handler.TransferLogHandler;
import kr.lesile.processes.transaction.log.profiler.manager.ProfilerManager;

public class TransferLogHandlerTest 
{
	private TransferLogHandler transferLogHandler;
	
	
	
	@Before
	public void setup() 
	{
		ProfilerManager profilerManager = new ProfilerManager();
		profilerManager.start();
		
		int handlerId = 0;
		this.transferLogHandler = new TransferLogHandler( handlerId ); 
		
		Customer transferCusomter = new Customer( "CUSTOMER.1", "LEE.1", "20181204" );
		Customer receivingCusomter = new Customer( "CUSTOMER.2", "LEE.2", "20181204" );
		
		Account transferAccount = new Account( "ACCOUNT.1", 1000, "20181204" );
		Account receivingAccount = new Account( "ACCOUNT.2", 2000, "20181204" );
		
		transferCusomter.getAccountMap().put( "ACCOUNT.1" , transferAccount);
		receivingCusomter.getAccountMap().put( "ACCOUNT.2" , receivingAccount);
		
		ProfilerManager.customerMap = new DataMap<String, Customer>();

		ProfilerManager.customerMap.put( "CUSTOMER.1" , transferCusomter);
		ProfilerManager.customerMap.put( "CUSTOMER.2" , receivingCusomter);
	}
	
	
	
	@Test
	public void testRun() 
	{
		byte[] transactionLog = "{\"logType\":\"T\",\"customerNo\":\"CUSTOMER.1\",\"transferAccountNo\":\"ACCOUNT.1\",\"receivingBank\":\"BANK.2\",\"receivingAccountNo\":\"ACCOUNT.2\",\"receivingBankAccountHolder\":\"CUSTOMER.2\",\"transferAmount\":5000,\"dateOfTransfer\":\"20181202202744\"}".getBytes();
		ProfilerManager.MQ_TRANSFER_LOG.put( transactionLog );
		
		this.transferLogHandler.start();
	}

}
