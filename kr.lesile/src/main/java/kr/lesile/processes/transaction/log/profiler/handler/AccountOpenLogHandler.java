package kr.lesile.processes.transaction.log.profiler.handler;

import com.google.gson.Gson;

import kr.lesile.processes.transaction.log.common.customer.Customer;
import kr.lesile.processes.transaction.log.common.telegram.AccountOpenLog;
import kr.lesile.processes.transaction.log.common.thread.KBThread;
import kr.lesile.processes.transaction.log.profiler.manager.ProfilerManager;


/**
 * 계좌개설 로그 처리 쓰레드
 */
public class AccountOpenLogHandler extends KBThread
{
	int handlerId = -1;
	
	
	
	
	
	public AccountOpenLogHandler( int handlerId )
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
		byte[] transactionLog = ProfilerManager.MQ_ACCOUNT_OPEN_LOG.poll();
		
		if( transactionLog == null )
		{
			return;
		}

		String receiveTransactionLog = new String( transactionLog );
		
		this.accountOpenLogHandle( receiveTransactionLog );
	}
	
	
	
	private void accountOpenLogHandle( String transactionLog )
	{
		AccountOpenLog accountOpenLog = new Gson().fromJson(transactionLog, AccountOpenLog.class);
		Customer accountOpenCustomer = ProfilerManager.customerMap.get( accountOpenLog.getCustomerNo() );

		if( accountOpenCustomer == null )
		{
			System.out.println( "[AccountOpenLogHandler] Customer has not joined yet - REQUEING" );
			ProfilerManager.MQ_ACCOUNT_OPEN_LOG.put( transactionLog.getBytes() );
			
			this.doThreadWait( 100L );
			return;
		}
		
		accountOpenCustomer.openAccount( accountOpenLog.getAccountNo(), accountOpenLog.getDateOfAccountOpen() );
	}
	
	
}
