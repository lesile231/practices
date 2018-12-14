package kr.lesile.processes.transaction.log.producer.manager;

import java.io.FileNotFoundException;

import kr.lesile.processes.transaction.log.common.config.ConfigLoader;
import kr.lesile.processes.transaction.log.common.messagequeue.MessageQueue;
import kr.lesile.processes.transaction.log.common.thread.KBThread;
import kr.lesile.processes.transaction.log.producer.generator.TransactionLogGenerator;
import kr.lesile.processes.transaction.log.producer.sender.TransactionLogSender;

public class ProducerManager extends KBThread
{
	public static final String DEFAULT_CONFIG_FILENAME = "./transaction.log.producer.properties";
	public static MessageQueue<byte[]> MQ_SENDER = null;
	
	private int transactionLogSenderCnt = -1;
	private TransactionLogSender[] transactionLogSender = null;

	private int transactionLogGeneratorCnt = -1;
	private TransactionLogGenerator[] transactionLogGenerator = null;
	
	
	
	
	
	public static void main( String args[] )
	{
		ProducerManager manager = new ProducerManager();
		manager.start();
	}
	
	
	
	
	
	public void run()
	{
		try
		{
			if( !this.loadProperties() )
			{
				return;
			}
			
			this.initMessageQueue();
			
			this.runTransactionLogSender();
			
			this.doThreadWait( 1000 );
			
			this.runTransactionLogGenerator();

			while( true )
			{
				this.doThreadWait( 1000 );
			}
			
		}
		catch (Exception e)
		{
			return;
		}
	}
	

	
	private boolean loadProperties()
	{
		boolean result = false;
		ConfigLoader config = null;
		int tmpInt = -1;
		
		try
		{
			config = new ConfigLoader( ProducerManager.DEFAULT_CONFIG_FILENAME );
			
			tmpInt = config.getIntTag( "transaction.log.sender.cnt" );
			if( tmpInt < 0 )
			{
				return result;
			}
			this.transactionLogSenderCnt = tmpInt;
			
			
			/**
			 * 금융거래 로그를 만드는 생성기 수 (동시 접속 고객의 수)
			 */
			tmpInt = config.getIntTag( "transaction.log.generator.cnt" );
			if( tmpInt < 0 )
			{
				return result;
			}
			this.transactionLogGeneratorCnt = tmpInt;
			
			result = true;
			return result;
		}
		catch( FileNotFoundException fne )
		{
			System.out.println( "[ProducerManager] Load properties file is not found - SHUTDOWN" );
			return false;
		}
		catch( Exception e )
		{
			System.out.println( "[ProducerManager] Load properties's value is not available or not found - SHUTDOWN" );
			return false;
		}
		finally
		{
			if( result )
			{
				System.out.println( "[ProducerManager] Load properties has done - END" );
			}
		}
	}
	
	
	
	private void initMessageQueue()
	{
		ProducerManager.MQ_SENDER = new MessageQueue<byte[]>();
			
	}
	
	
	
	private void runTransactionLogSender()
	{
		if( this.transactionLogSender == null )
		{
			this.transactionLogSender = new TransactionLogSender[ this.transactionLogSenderCnt ];
		}
		
		
		for( int i = 0; i < this.transactionLogSenderCnt; i++ )
		{
			if (this.transactionLogSender[i] == null)
			{
				this.transactionLogSender[i] = new TransactionLogSender( i );
				this.transactionLogSender[i].start();
			}
		}
	}
	
	
	
	private void runTransactionLogGenerator()
	{
		if( this.transactionLogGenerator == null )
		{
			this.transactionLogGenerator = new TransactionLogGenerator[ this.transactionLogGeneratorCnt ];
		}
		
		
		for( int i = 0; i < this.transactionLogGeneratorCnt; i++ )
		{
			if (this.transactionLogGenerator[i] == null)
			{
				this.transactionLogGenerator[i] = new TransactionLogGenerator( i );
				this.transactionLogGenerator[i].start();
			}
		}
	}
	
	
}
