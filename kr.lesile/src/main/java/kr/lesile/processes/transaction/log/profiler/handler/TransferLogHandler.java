package kr.lesile.processes.transaction.log.profiler.handler;

import com.google.gson.Gson;

import kr.lesile.processes.transaction.log.common.customer.Customer;
import kr.lesile.processes.transaction.log.common.telegram.TransferLog;
import kr.lesile.processes.transaction.log.common.thread.KBThread;
import kr.lesile.processes.transaction.log.profiler.manager.ProfilerManager;


/**
 * 이체 로그 처리 쓰레드
 */
public class TransferLogHandler extends KBThread
{
	int handlerId = -1;
	
	
	
	
	
	public TransferLogHandler( int handlerId )
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
		byte[] transactionLog = ProfilerManager.MQ_TRANSFER_LOG.poll();
		
		if( transactionLog == null )
		{
			return; 
		}
		
		String receiveTransactionLog = new String( transactionLog );
		this.transferLogHandle( receiveTransactionLog );
	}
	
	
	
	private void transferLogHandle( String transactionLog )
	{
		TransferLog transferLog = new Gson().fromJson(transactionLog, TransferLog.class);
		
		Customer transferCustomer = ProfilerManager.customerMap.get( transferLog.getCustomerNo() );
		Customer receivingCustomer = ProfilerManager.customerMap.get( transferLog.getReceivingBankAccountHolder() );

		
		if( transferCustomer == null || receivingCustomer == null )
		{
			System.out.println( "[TransferLogHandler] Customer has not joined yet - REQUEING" );
			ProfilerManager.MQ_TRANSFER_LOG.put( transactionLog.getBytes() );

			this.doThreadWait( 100L );
			return;
		}
		
		
		/**
		 * 고객의 출금액이 현재 잔고보다 작은 경우, 진행 종료.
		 */
		if( !transferCustomer.withdraw( transferLog.getTransferAccountNo(), transferLog.getTransferAmount() ) )
		{
			System.out.println( "[TransferLogHandler] Customer has not enough balance to transfer - END" );
			return;
		}
		
		/**
		 * 송금 계좌가 아직 생성되기 전이면, 환불 후 종료
		 */
		if( !receivingCustomer.deposit( transferLog.getReceivingAccountNo(), transferLog.getTransferAmount() ))
		{
			System.out.println( "[TransferLogHandler] Receiving Customer has not joined yet - END" );
			transferCustomer.deposit( transferLog.getTransferAccountNo(), transferLog.getTransferAmount() );
			return;
		}
	}

	
}
