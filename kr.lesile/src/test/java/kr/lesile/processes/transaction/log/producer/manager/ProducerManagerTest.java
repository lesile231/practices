package kr.lesile.processes.transaction.log.producer.manager;

import org.junit.Before;
import org.junit.Test;

import kr.lesile.processes.transaction.log.producer.manager.ProducerManager;

public class ProducerManagerTest 
{
	private ProducerManager producerManager;
	
	
	
	@Before
	public void setup()
	{
		this.producerManager = new ProducerManager();
	}
	
	
	
	@Test
	public void testRun()
	{
		this.producerManager.start();
	}

}
