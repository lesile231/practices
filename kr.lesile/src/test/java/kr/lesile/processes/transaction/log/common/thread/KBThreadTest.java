package kr.lesile.processes.transaction.log.common.thread;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import kr.lesile.processes.transaction.log.common.thread.KBThread;

public class KBThreadTest 
{
	private KBThread kbThread;
	
	
	
	
	
	@Before
	public void setup()
	{
		this.kbThread = new KBThread();
	}
	
	
	
	
	
	@Test(timeout = 1000)
	public void testDoThreadWait() 
	{
		long expectedElapsedTime = 10L;
		
		this.kbThread.doThreadWait( expectedElapsedTime );
	}

	
	
	@Test
	public void testElapsedTime() 
	{
		long time = 100L;
		long returnTime = this.kbThread.elapsedTime( time );
		
		assertNotNull( returnTime );
	}

	
	
}
