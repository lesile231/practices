package kr.lesile.kakaobank.codingtest.transaction.log.producer.sender;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import kr.lesile.processes.transaction.log.producer.sender.TransactionLogSender;

public class TransactionLogSenderTest 
{
	private TransactionLogSender transactionLogSender;

	@Mock
	private KafkaProducer<String, String> kafkaProducer;
	
	
	
	
	@Before
	public void setup()
	{
		this.transactionLogSender = new TransactionLogSender(0);
	}
	
	
	
	
	@SuppressWarnings("unchecked")
	@Test
	public void testRun() throws InterruptedException, ExecutionException 
	{
		this.transactionLogSender.start();
		
		this.kafkaProducer = this.transactionLogSender.getKafkaProducer();
		this.kafkaProducer = mock(KafkaProducer.class);
		
		when( this.kafkaProducer.send(any(ProducerRecord.class)) ).thenReturn( null );
		
	}
	

}
