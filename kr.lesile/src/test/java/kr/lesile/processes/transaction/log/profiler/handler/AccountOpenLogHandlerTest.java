package kr.lesile.kakaobank.codingtest.transaction.log.profiler.handler;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import kr.lesile.processes.transaction.log.profiler.handler.AccountOpenLogHandler;
import kr.lesile.processes.transaction.log.profiler.manager.ProfilerManager;

public class AccountOpenLogHandlerTest 
{
	private AccountOpenLogHandler accountOpenLogHandler;
	
	@Mock
	private ProfilerManager profilerManager;
	
	
	
	
	
	@Before
	public void setup() throws InterruptedException
	{
		this.profilerManager = new ProfilerManager();
		this.profilerManager.start();
		
		Thread.sleep( 2000 );
		
		int hanlderId = 0;
		this.accountOpenLogHandler = new AccountOpenLogHandler( hanlderId );
	}
	
	
	
	
	
	@Test
	public void testRun()
	{
		byte[] transactionLog = "{\"logType\":\"A\",\"customerNo\":\"CUSTOMER.1\",\"accountNo\":\"ACCOUNT.1\",\"dateOfAccountOpen\":\"20181202202726\"}".getBytes();
		
		assertNotNull( ProfilerManager.MQ_ACCOUNT_OPEN_LOG );
		
		ProfilerManager.MQ_ACCOUNT_OPEN_LOG.put( transactionLog );
		
		this.accountOpenLogHandler.start();
	}
}
