package kr.lesile.processes.transaction.log.profiler.handler;

import com.google.gson.Gson;

import kr.lesile.processes.transaction.log.common.customer.Customer;
import kr.lesile.processes.transaction.log.common.telegram.JoinLog;
import kr.lesile.processes.transaction.log.common.thread.KBThread;
import kr.lesile.processes.transaction.log.profiler.manager.ProfilerManager;


/**
 * 가입 로그 처리 쓰레드
 */
public class JoinLogHandler extends KBThread
{
	int handlerId = -1;
	
	
	
	
	
	public JoinLogHandler( int handlerId )
	{
		this.handlerId = handlerId;
	}
	
	
	
	
	
	public void run()
	{
		try
		{
			while( true )
			{
				try
				{
					this.processing();
					this.doThreadWait( 100 );
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
	}
	
	
	
	private void processing()
	{
		byte[] transactionLog = ProfilerManager.MQ_JOIN_LOG.poll();
		
		if( transactionLog == null )
		{
			return;
		}
		
		String receiveTransactionLog = new String( transactionLog );
		this.joinLogHandle( receiveTransactionLog );
	}
	
	
	
	private void joinLogHandle( String transactionLog )
	{
		try
		{
			JoinLog joinLog = new Gson().fromJson( transactionLog, JoinLog.class );
			Customer joinCustomer = new Customer( joinLog.getCustomerNo(), joinLog.getCustomerName(), joinLog.getDateOfJoin() );
			
			ProfilerManager.customerMap.put( joinLog.getCustomerNo(), joinCustomer);
		}
		catch( Exception e )
		{
			System.out.println( "Can't Parsing Transaction Log to JoinLog - DISCARD" );
			return;
		}
		
	}
	
	
}
