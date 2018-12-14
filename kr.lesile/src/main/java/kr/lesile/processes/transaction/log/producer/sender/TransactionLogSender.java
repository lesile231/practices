package kr.lesile.processes.transaction.log.producer.sender;

import java.io.FileNotFoundException;
import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;

import kr.lesile.processes.transaction.log.common.config.ConfigLoader;
import kr.lesile.processes.transaction.log.common.thread.KBThread;
import kr.lesile.processes.transaction.log.producer.manager.ProducerManager;







public class TransactionLogSender extends KBThread
{
	private int senderId = -1;
	private Properties brokerConfigs = null;
	private KafkaProducer<String, String> producer = null;
	
	private String topic = null;
	private String brokerAddress = null;
	private String brokerPort = null;
	private String acks = null;
	private String transactionBatchSize = null;
	private String lingerMs = null;
	private String bufferMemoryConfig = null;
	
	private long threadDelay = 1;
	
	
	
	public TransactionLogSender( int senderId )
	{
		this.senderId = senderId;
	}
	
	
	
	
	
	public void run()
	{
		try
		{
			if( !this.loadProperties() )
			{
				return;
			}
			
			if( !this.initSenderProperties() )
			{
				return;
			}
			
			while( true )
			{
				this.processing();
				this.doThreadWait( this.threadDelay );
			}
		}
		catch (Exception e)
		{
			return;
		}
		finally
		{
			this.release();
		}
	}
	
	
	
	public void processing()
	{
		try
		{
			byte[] transactionLog = ProducerManager.MQ_SENDER.poll();
			
			if( transactionLog == null )
			{
				return;
			}
			
			
			/**
			 * 거래로그 카프카의 브로커로 송신
			 * 전송 실패시, 거래로그를 다시 Queue(ProducerManager.MQ_SENDER)에 넣어줌
			 * 
			 * sendDataToBroker 메소드 파라미터 정의
			 * 
			 * 순서	파라미터 값	파라미터 설명		파라미터 타입
			 * 1 	토픽			송신할 브로커의 토픽 	String
			 * 2 	키			송신할 거래로그의 키 	String
			 * 3 	로그 			송신할 거래로그		byte[]
			 */
			if ( !this.sendDataToBroker( this.topic, String.valueOf( this.senderId ), transactionLog) )
			{
				ProducerManager.MQ_SENDER.put( transactionLog );
			}
		}
		catch( Exception e )
		{
			return;
		}
	}
	
	
	
	private boolean sendDataToBroker( String topic, String key, byte[] data )
	{
		boolean result = false;
		ProducerRecord<String,String> sndData = null;
		
		try
		{
			sndData = new ProducerRecord<String, String>( topic, key, new String( data ) );
			
			RecordMetadata meta = this.producer.send( sndData ).get();

			if( meta == null )
			{
				System.out.println( "[TransactionLogSender] Sending transactionLog has failed - REQUEING " );
				return result;
			}
			
			result = true;
			return result;
		}
		catch( Exception e )
		{
			return false;
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
			config = new ConfigLoader( ProducerManager.DEFAULT_CONFIG_FILENAME );
			
			tmpStr = config.getStringTag( "broker.ip.address" );
			if( tmpStr == null || tmpStr.trim().equals( "" ) )
			{
				return result;
			}
			this.brokerAddress = tmpStr;
			
			tmpStr = config.getStringTag( "broker.port" );
			if( tmpStr == null || tmpStr.trim().equals( "" ) )
			{
				return result;
			}
			this.brokerPort = tmpStr;
			
			tmpStr = config.getStringTag( "broker.topic" );
			if( tmpStr == null || tmpStr.trim().equals( "" ) )
			{
				return result;
			}
			this.topic = tmpStr;
			
			tmpStr = config.getStringTag( "broker.acks" );
			if( tmpStr == null || tmpStr.trim().equals( "" ) )
			{
				return result;
			}
			this.acks = tmpStr;
			
			tmpInt = config.getIntTag( "transaction.log.sender.thread.delay" );
			if( tmpInt < 0 )
			{
				return result;
			}
			this.threadDelay = tmpInt;

			tmpStr = config.getStringTag( "transaction.log.linger.ms" );
			if( tmpStr == null || tmpStr.trim().equals( "" ) )
			{
				return result;
			}
			this.lingerMs = tmpStr;
			
			tmpStr = config.getStringTag( "transaction.log.buffer.memory.config" );
			if( tmpStr == null || tmpStr.trim().equals( "" ) )
			{
				return result;
			}
			this.bufferMemoryConfig = tmpStr;
			
			tmpStr = config.getStringTag( "transaction.log.batch.size" );
			if( tmpStr == null || tmpStr.trim().equals( "" ) )
			{
				return result;
			}
			this.transactionBatchSize = tmpStr;
			
			result = true;
			return result;
		}
		catch( FileNotFoundException fne )
		{
			System.out.println( "[TransactionLogSender] Load properties file is not found - SHUTDOWN" );
			return false;
		}
		catch( Exception e )
		{
			System.out.println( "[TransactionLogSender] Load properties's value is not available or not found - SHUTDOWN" );
			return false;
		}
		finally
		{
			if( result )
			{
				System.out.println( "[TransactionLogSender] Load properties has done - END" );
			}
		}
	}
	
	
	
	private boolean initSenderProperties()
	{
		boolean result = false;
		
		try
		{
			this.brokerConfigs = new Properties();
			
			/**
			 *  - 설정 값 설명
			 * BOOTSTRAP_SERVERS_CONFIG	: 브로커의 IP:PORT 설정값
			 * ACKS_CONFIG				: 브로커 응답 ACKS 설정값
			 * BUFFER_MEMORY_CONFIG 		: 서버로 보낼 레코드를 버퍼링 할 때 사용할 수 있는 전체 메모리의 바이트 길이 설정값
			 * BATCH_SIZE_CONFIG    		: 레코드를 한번에 담을 수 있는 배치 바이트 길이 설정값. 레코드를 받는 버퍼가 설정값만큼 찼을 때 레코드를 배치로 보내게 된다.
			 * LINGER_MS_CONFIG     		: 레코드를 모아서 배치로 보내기 위해서 기다리는 시간 설정값
			 * COMPRESSION_TYPE_CONFIG	: 레코드 압축 형식 설정값
			 * SEND_BUFFER_CONFIG   		: 송신 버퍼 크기 설정값
			 * MAX_REQUEST_SIZE_CONFIG 	: 레코드 최대 크기 설정값 
			 * 
			 *  - 주의사항 
			 * BUFFER_MEMORY_CONFIG의 설정값은 BATCH_SIZE_CONFIG의 설정값보다 꼭 커야함
			 */
			this.brokerConfigs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, String.format( "%1$s:%2$s",  this.brokerAddress, this.brokerPort ) );
			this.brokerConfigs.put(ProducerConfig.ACKS_CONFIG, this.acks);
			this.brokerConfigs.put(ProducerConfig.BUFFER_MEMORY_CONFIG, this.bufferMemoryConfig);
			this.brokerConfigs.put(ProducerConfig.BATCH_SIZE_CONFIG, this.transactionBatchSize);
			this.brokerConfigs.put(ProducerConfig.LINGER_MS_CONFIG, this.lingerMs);
			this.brokerConfigs.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, "none");
			this.brokerConfigs.put(ProducerConfig.SEND_BUFFER_CONFIG, 10458760);
			this.brokerConfigs.put(ProducerConfig.MAX_REQUEST_SIZE_CONFIG, 100000);
			
			this.brokerConfigs.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, TransactionLogSenderPolicy.class.getName() );
			this.brokerConfigs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
			this.brokerConfigs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

			this.producer = new KafkaProducer<>( this.brokerConfigs );

			result = true;
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
				System.out.println( "[TransactionLogSender] Initialize sender properties has done - END" );
			}
			else
			{
				System.out.println( "[TransactionLogSender] Initialize sender properties has not done completely - SHUTDOWN" );
			}
		}
	}
	
	
	
	private void release()
	{
		if( this.brokerConfigs != null )
		{
			this.brokerConfigs.clear();
		}
	}
	
	
	public KafkaProducer<String, String> getKafkaProducer()
	{
		return this.producer;
	}
	
	
}
