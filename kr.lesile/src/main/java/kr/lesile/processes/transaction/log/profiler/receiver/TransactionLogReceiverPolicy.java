package kr.lesile.processes.transaction.log.profiler.receiver;

import java.util.LinkedList;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.common.TopicPartition;

public class TransactionLogReceiverPolicy 
{
	private LinkedList<TopicPartition> partitionList = null;

	
	
	
	
	public boolean init(Consumer<?, ?> consumer, String topic, int threadId, int partitionCnt)
	{
		boolean result = false;
		int partitionNo = -1;
		
		try
		{
			partitionNo = threadId % partitionCnt;
			
			TopicPartition partition = new TopicPartition(topic, partitionNo);
			
			this.partitionList = new LinkedList<TopicPartition>();
			this.partitionList.add(partition);
			
			consumer.assign( this.partitionList );
			
			result = true;
			return result;
		}
		catch( Exception e )
		{
			return false;
		}
	}
	
	
}
