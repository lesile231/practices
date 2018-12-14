package kr.lesile.kakaobank.codingtest.transaction.log.producer.generator;

import org.junit.Before;
import org.junit.Test;

import kr.lesile.processes.transaction.log.producer.generator.TransactionLogGenerator;

public class TransactionLogGeneratorTest
{
	private TransactionLogGenerator transactionLogGenerator;
	
	
	
	@Before
	public void setup()
	{
		this.transactionLogGenerator = new TransactionLogGenerator(0);
	}
	
	
	
	@Test
	public void testRun() throws InterruptedException
	{
		this.transactionLogGenerator.start();
	}
}
