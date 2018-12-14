package kr.lesile.processes.transaction.log.profiler.handler;

import org.junit.Before;
import org.junit.Test;

import kr.lesile.processes.transaction.log.profiler.handler.DepositLogHandler;
import kr.lesile.processes.transaction.log.profiler.manager.ProfilerManager;

public class DepositLogHandlerTest 
{
	private DepositLogHandler depositLogHandler;
	
	
	
	@Before
	public void setup()
	{
		int handlerId = 0;
		this.depositLogHandler = new DepositLogHandler( handlerId );
	}
	
	
	
	@Test
	public void testRun() 
	{
		byte[] transactionLog = "{“logType\":\"D\",\"customerNo\":\"CUSTOMER.1\",\"depositAccountNo\":\"ACCOUNT.1\",\"depositAmount\":10000,\"dateOfDeposit\":\"20181202202732\"}".getBytes();
		ProfilerManager.MQ_DEPOSIT_LOG.put( transactionLog );
		
		this.depositLogHandler.start();
	}

}
