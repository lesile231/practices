package kr.lesile.processes.transaction.log.profiler.receiver;

import static org.junit.Assert.assertNotNull;

import java.util.LinkedList;
import java.util.Properties;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.Before;
import org.junit.Test;

public class TransactionLogReceiverPolicyTest 
{
	private Consumer<?,?> consumer;
	private String topic;
	private int threadId;
	private int partitionCnt;
	
	
	@Before
	public void setup()
	{
		Properties brokerConfigs = new Properties();
		brokerConfigs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, String.format("%1$s:%2$s", "localhost", "9091"));
		brokerConfigs.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "10000");
		brokerConfigs.put(ConsumerConfig.GROUP_ID_CONFIG, "TRANSACTION_LOG");
		brokerConfigs.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 1000);
		brokerConfigs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		brokerConfigs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		
		this.consumer = new KafkaConsumer<>( brokerConfigs );
		
		this.topic = "TRANSACTION_LOG";
		this.threadId = 5;
		this.partitionCnt = 10;
	}

	
	
	@Test
	public void testInit() 
	{
		int partitionNo = -1;
		partitionNo = this.threadId % this.partitionCnt;
		
		TopicPartition partition = new TopicPartition(this.topic, partitionNo);
		assertNotNull( partition );
		
		LinkedList<TopicPartition> partitionList = new LinkedList<TopicPartition>();
		assertNotNull( partitionList );
		
		partitionList.add(partition);
		this.consumer.assign( partitionList );
		
	}

}
