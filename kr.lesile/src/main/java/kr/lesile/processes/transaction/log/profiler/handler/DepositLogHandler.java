package kr.lesile.processes.transaction.log.profiler.handler;

import com.google.gson.Gson;

import kr.lesile.processes.transaction.log.common.customer.Customer;
import kr.lesile.processes.transaction.log.common.telegram.DepositLog;
import kr.lesile.processes.transaction.log.common.thread.KBThread;
import kr.lesile.processes.transaction.log.profiler.manager.ProfilerManager;


/**
 * 입금 로그 처리 쓰레드
 */
public class DepositLogHandler extends KBThread
{
	int handlerId = -1;
	
	
	
	
	
	public DepositLogHandler( int handlerId )
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
					this.doThreadWait( 100L );
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
		byte[] transactionLog = ProfilerManager.MQ_DEPOSIT_LOG.poll();
		
		if( transactionLog == null )
		{
			return; 
		}
		
		String receiveTransactionLog = new String( transactionLog );
		
		this.depositLogHandle( receiveTransactionLog );
	}
	
	
	
	private void depositLogHandle( String transactionLog )
	{
		DepositLog depositLog = new Gson().fromJson(transactionLog, DepositLog.class);
		Customer depositCustomer = ProfilerManager.customerMap.get( depositLog.getCustomerNo() );

		if( depositCustomer == null )
		{
			ProfilerManager.MQ_DEPOSIT_LOG.put( transactionLog.getBytes() );

			this.doThreadWait( 100L );
			return;
		}
		
		depositCustomer.deposit( depositLog.getDepositAccountNo() , depositLog.getDepositAccountAmount() );
	}
	
	
}
