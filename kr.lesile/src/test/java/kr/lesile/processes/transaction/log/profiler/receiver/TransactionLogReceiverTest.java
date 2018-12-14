package kr.lesile.processes.transaction.log.profiler.receiver;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import kr.lesile.processes.transaction.log.producer.sender.TransactionLogSenderPolicy;
import kr.lesile.processes.transaction.log.profiler.receiver.TransactionLogReceiver;

public class TransactionLogReceiverTest 
{
	private TransactionLogReceiver transactionLogReceiver;
	
	@Mock
	private KafkaProducer<String, String> kafkaProducer;
	
	
	
	
	@Before
	public void setup()
	{
		Properties brokerConfigs = new Properties();
		
		brokerConfigs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, String.format( "%1$s:%2$s",  "localhost", "9091" ) );
		brokerConfigs.put(ProducerConfig.ACKS_CONFIG, "1");
		brokerConfigs.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, TransactionLogSenderPolicy.class.getName() );
		brokerConfigs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		brokerConfigs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		
		this.kafkaProducer = new KafkaProducer<>( brokerConfigs );
		
		String topic = "TRANSACTION_LOG";
		String key = "1";
		String data = "{\"logType\":\"J\",\"customerNo\":\"CUSTOMER.1\",\"customerName\":\"Lee.1\",\"dateOfJoin\":\"20181202202720\"}";
		
		ProducerRecord<String,String> sndData = new ProducerRecord<String, String>( topic, key, data );
		this.kafkaProducer.send( sndData );
		
		
		int receiverId = 6000;
		this.transactionLogReceiver = new TransactionLogReceiver( receiverId );
	}
	
	
	
	
	
	@Test
	public void testRun() throws InterruptedException
	{
		this.transactionLogReceiver.start();
		
		/*
		this.kafkaConsumer = this.transactionLogReceiver.getKafkaConsumer();
		this.kafkaConsumer = mock(KafkaConsumer.class);
		
		ConsumerRecord<String, String> record = new ConsumerRecord<>("TRANSACTION_LOG", 0, 0L, "1", "testLog");
		
		Map<TopicPartition, List<ConsumerRecord<String, String>>> map = new HashMap<TopicPartition, List<ConsumerRecord<String, String>>>();
		map.put( new TopicPartition("TRANSACTION_LOG", 0),  Arrays.asList(record));
		ConsumerRecords<String, String> records = new ConsumerRecords<String, String>( map );
		when( this.kafkaConsumer.poll( Duration.ofHours(1) ) ).thenReturn( records );
		*/
	}
}
