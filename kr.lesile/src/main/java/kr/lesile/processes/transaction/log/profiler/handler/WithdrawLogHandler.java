package kr.lesile.processes.transaction.log.profiler.handler;

import com.google.gson.Gson;

import kr.lesile.processes.transaction.log.common.customer.Customer;
import kr.lesile.processes.transaction.log.common.telegram.WithdrawLog;
import kr.lesile.processes.transaction.log.common.thread.KBThread;
import kr.lesile.processes.transaction.log.profiler.manager.ProfilerManager;


/**
 * 출금 로그 처리 쓰레드
 */
public class WithdrawLogHandler extends KBThread
{
	int handlerId = -1;
	
	
	
	
	
	public WithdrawLogHandler( int handlerId )
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
		byte[] transactionLog = ProfilerManager.MQ_WITHDRAW_LOG.poll();
		
		if( transactionLog == null )
		{
			return; 
		}
		
		String receiveTransactionLog = new String( transactionLog );
		
		this.withdrawLogHandle( receiveTransactionLog );
	}
	
	
	
	private void withdrawLogHandle( String transactionLog )
	{
		WithdrawLog withdrawLog = new Gson().fromJson(transactionLog, WithdrawLog.class);
		Customer withDrawCustomer = ProfilerManager.customerMap.get( withdrawLog.getCustomerNo() );

		if( withDrawCustomer == null )
		{
			System.out.println( "[WithdrawLogHandler] Customer has not joined yet - REQUEING" );
			ProfilerManager.MQ_WITHDRAW_LOG.put( transactionLog.getBytes() );

			this.doThreadWait( 100L );
			return;
		}

		withDrawCustomer.withdraw( withdrawLog.getWithdrawAccountNo(), withdrawLog.getWithdrawAmount() );
	}
	
	
}
