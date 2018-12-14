package kr.lesile.processes.transaction.log.profiler.receiver;

import java.io.FileNotFoundException;
import java.time.Duration;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import kr.lesile.processes.transaction.log.common.config.ConfigLoader;
import kr.lesile.processes.transaction.log.common.enums.LogType;
import kr.lesile.processes.transaction.log.common.thread.KBThread;
import kr.lesile.processes.transaction.log.profiler.manager.ProfilerManager;



/**
 * 거래로그 수신
 */
public class TransactionLogReceiver extends KBThread
{
	private long threadDelay = 1;
	private boolean isThreadRunning = false;
	
	private Properties brokerConfigs = null;
	private KafkaConsumer<String, String> consumer = null;
	private TransactionLogReceiverPolicy policy = null;
	private String topic = null;
	private String brokerAddress = null;
	private String brokerPort = null;
	private String receiverGroupId = null;
	private int partitionNo = 0;
	private int partitionCnt = 1;
	
	
	
	
	
	public TransactionLogReceiver( int i )
	{
		this.partitionNo = i;
	}
	
	
	
	
	
	public void run()
	{
		try
		{
			if( !this.loadProperties() )
			{
				return;
			}
			
			if( !this.initReceiverProperties() )
			{
				return;
			}
			
			this.isThreadRunning = true;
			
			while( this.getIsThreadRunning() )
			{
				try
				{
					this.processing();
					this.doThreadWait( this.threadDelay );
				}
				catch( Exception e )
				{
					continue;
				}
			}
		}
		catch( Exception e )
		{
			return;
		}
		finally
		{
			this.isThreadRunning = false;
			this.release();
		}
	}
	
	
	
	private boolean loadProperties()
	{
		boolean result = false;
		
		ConfigLoader config = null;
		String tmpStr = null;
		int tmpInt = -1;
		
		try
		{
			config = new ConfigLoader( ProfilerManager.DEFAULT_CONFIG_FILENAME );
			
			tmpStr = config.getStringTag( "broker.ip.address" );
			if( tmpStr == null || tmpStr.trim().equals("") )
			{
				return result;
			}
			this.brokerAddress = tmpStr;
			
			tmpStr = config.getStringTag( "broker.port" );
			if( tmpStr == null || tmpStr.trim().equals("") )
			{
				return result;
			}
			this.brokerPort = tmpStr;
			
			tmpStr = config.getStringTag( "broker.topic" );
			if( tmpStr == null || tmpStr.trim().equals("") )
			{
				return result;
			}
			this.topic = tmpStr;
			
			tmpInt = config.getIntTag("broker.partition.cnt");
			if (tmpInt <= 0)
			{
				return result;
			}
			this.partitionCnt = tmpInt;
			
			tmpStr = config.getStringTag( "broker.group.id" );
			if( tmpStr == null || tmpStr.trim().equals("") )
			{
				return result;
			}
			this.receiverGroupId = tmpStr;
			
			tmpInt = config.getIntTag("transaction.log.receiver.thread.delay");
			if (tmpInt <= 0)
			{
				return result;
			}
			this.threadDelay = tmpInt;
			
			result = true;
			return result;
		}
		catch( FileNotFoundException fne )
		{
			System.out.println( "[TransactionLogReceiver] Load properties file is not found - SHUTDOWN" );
			return false;
		}
		catch( Exception e )
		{
			System.out.println( "[TransactionLogReceiver] Load properties's value is not available or not found - SHUTDOWN" );
			return false;
		}
		finally
		{
			if( result )
			{
				System.out.println( "[TransactionLogReceiver] Load properties has done. - END" );
			}
		}
	}
	
	
	
	private boolean initReceiverProperties()
	{
		boolean result = false;
		
		try
		{
			this.brokerConfigs = new Properties();
			
			/**
			 * BOOTSTRAP_SERVERS_CONFIG	: 브로커의 IP:PORT 설정값
			 * SESSION_TIMEOUT_MS_CONFIG	: 세션 타임 아웃 설정값
			 * GROUP_ID_CONFIG			: 토픽의 그룹 아이디 설정값
			 * MAX_POLL_RECORDS_CONFIG	: 한번에 poll 되는 max 레코드 수 설정값
			 */
			this.brokerConfigs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, String.format("%1$s:%2$s", this.brokerAddress, this.brokerPort));
			this.brokerConfigs.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "10000");
			this.brokerConfigs.put(ConsumerConfig.GROUP_ID_CONFIG, this.receiverGroupId);
			this.brokerConfigs.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 1000);
			
			this.brokerConfigs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
			this.brokerConfigs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
			this.consumer = new KafkaConsumer<>(this.brokerConfigs);
			LinkedList<String> topics = new LinkedList<String>();
			
			topics.add(this.topic);

			this.policy = new TransactionLogReceiverPolicy();
			
			result = this.policy.init(this.consumer, this.topic, this.partitionNo, this.partitionCnt);
			return result;
		}
		catch( Exception e )
		{
			return false;
		}
		finally
		{
			if( result )
			{
				System.out.println( "[TransactionLogReceiver] Initialize receiver properties has done. - END" );
			}
			else
			{
				System.out.println( "[TransactionLogReceiver] Initialize receiver properties has not done completely. - SHUTDOWN" );
			}
		}
	}
	
	
	
	private void processing()
	{
		byte[] receiveData = null;
		
		try
		{
			receiveData = this.receiveTransactionLog();
			
			if( receiveData == null || receiveData.length == 0 )
			{
				return;
			}
			
			String receiveTransactionLog = new String(receiveData);
			String logType = this.getLogType( receiveTransactionLog );
			
			if( logType == null )
			{
				return;
			}
				
			switch( logType )
			{
				case LogType.JOIN_LOG :
					ProfilerManager.MQ_JOIN_LOG.put(receiveData);
					break;
					
				case LogType.ACCOUNT_OPEN_LOG :
					ProfilerManager.MQ_ACCOUNT_OPEN_LOG.put(receiveData);
					break;
					
				case LogType.DEPOSIT_LOG :
					ProfilerManager.MQ_DEPOSIT_LOG.put(receiveData);
					break;
					
				case LogType.WITHDRAW_LOG :
					ProfilerManager.MQ_WITHDRAW_LOG.put(receiveData);
					break;
					
				case LogType.TRANSFER_LOG :
					ProfilerManager.MQ_TRANSFER_LOG.put(receiveData);
					break;
					
				default :
					System.out.println( "Received transactionLog has unsupported logType. - DISCARD" );
					break;
			}
			
		}
		catch( Exception e )
		{
			return;
		}
	}
	
	
	
	private byte[] receiveTransactionLog()
	{
		byte[] result = null;
		
		try
		{
			ConsumerRecords<String, String> records = this.consumer.poll( Duration.ofHours(1) );
			
			if( records == null )
			{
				return result;
			}
			
			
			/**
			 * Consumer가 한번에 1개의 레코드만 읽어오는 것이 아니라,
			 * 한번에 배치로 여러개의 레코드를 읽어올 수도 있기 때문에 for문 사용
			 */
			for( ConsumerRecord<String, String> record : records )
			{
				String readTopic = record.topic();
				
				if( readTopic == null || readTopic.trim().equals("") )
				{
					continue;
				}
				
				if( readTopic.equals( this.topic ) )
				{
					result = record.value().getBytes();
				}
				else
				{
					System.out.println( "Has received unsupported topic. - DISCARD" );
				}
				
				Thread.sleep( this.threadDelay );
			}
			
			return result;
		}
		catch( Exception e )
		{
			return null;
		}
	}
	
	
	
	private String getLogType( String transactionLog )
	{
		try
		{
			String logType = "";
			
			JsonObject object = new JsonParser().parse( transactionLog ).getAsJsonObject();
			Iterator<Map.Entry<String, JsonElement>> iterator = object.entrySet().iterator();
	        Map.Entry<String, JsonElement> entry;
			
	        if( iterator.hasNext() )
	        {
	        		entry = iterator.next();
	            JsonElement value = entry.getValue();
	            logType = value.getAsString();
	        }
	        
			return logType;
		}
		catch( Exception e )
		{
			return  "";
		}
	}
	
	
	
	private void release()
	{
		try
		{
			if (this.brokerConfigs != null)
			{
				this.brokerConfigs.clear();
			}
		}
		catch (Exception e)
		{
			return;
		}
		finally
		{
			this.brokerConfigs = null;
		}
	}
	
	
	
	public boolean getIsThreadRunning()
	{
		return this.isThreadRunning;
	}
	
	
	
	public KafkaConsumer<String,String> getKafkaConsumer()
	{
		return this.consumer;
	}
	
	
}
