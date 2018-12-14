package kr.lesile.kakaobank.codingtest.transaction.log.profiler.handler;

import org.junit.Before;
import org.junit.Test;

import kr.lesile.processes.transaction.log.profiler.handler.WithdrawLogHandler;
import kr.lesile.processes.transaction.log.profiler.manager.ProfilerManager;

public class WithdrawLogHandlerTest 
{
	private WithdrawLogHandler withdrawLogHandler;
	
	
	
	@Before
	public void setup() 
	{
		int handlerId = 0;
		this.withdrawLogHandler = new WithdrawLogHandler( handlerId );
		
		
	}
	
	
	
	@Test
	public void testRun() 
	{
		byte[] transactionLog = "{\"logType\":\"W\",\"customerNo\":\"CUSTOMER.1\",\"withdrawAccountNo\":\"ACCOUNT.1\",\"withdrawAmount\":10000,\"dateOfWithdraw\":\"20181202202738\"}".getBytes();
		ProfilerManager.MQ_WITHDRAW_LOG.put( transactionLog );
		
		this.withdrawLogHandler.start();
	}

}
