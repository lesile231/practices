
//	@formatter:off





package kr.lesile.processes.transaction.log.producer.sender;











import java.util.Map;
import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;










public class TransactionLogSenderPolicy implements Partitioner
{
	@Override
	public void configure(Map<String, ?> arg0) {}


	@Override
	public void close() {}


	
	@Override
	public int partition( String topic, Object key, byte[] keyByte, Object value, byte[] valueByte, Cluster clusterInfo)
	{
		int partitionNo = -1;
		int partitionCnt = -1;

		try
		{
			partitionCnt = clusterInfo.partitionCountForTopic(topic);

			partitionNo = Integer.parseInt((String) key);
			partitionNo = partitionNo % partitionCnt;
			return partitionNo;
		}
		catch (Exception e)
		{
			return 0;
		}
	}
	
	
}
