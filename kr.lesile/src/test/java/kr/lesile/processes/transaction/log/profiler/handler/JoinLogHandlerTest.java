package kr.lesile.kakaobank.codingtest.transaction.log.profiler.handler;

import org.junit.Before;
import org.junit.Test;

import kr.lesile.processes.transaction.log.profiler.handler.JoinLogHandler;
import kr.lesile.processes.transaction.log.profiler.manager.ProfilerManager;

public class JoinLogHandlerTest 
{
	private JoinLogHandler joinLogHandler;
	
	
	
	@Before
	public void setup()
	{
		int handlerId = 0;
		this.joinLogHandler = new JoinLogHandler( handlerId );
	}
	
	
	
	@Test
	public void testRun() 
	{
		byte[] transactionLog = "{\"logType\":\"J\",\"customerNo\":\"CUSTOMER.1\",\"customerName\":\"Lee.1\",\"dateOfJoin\":\"20181202202720\"}".getBytes();
		ProfilerManager.MQ_JOIN_LOG.put( transactionLog );
		
		this.joinLogHandler.start();
	}

}
