package kr.lesile.processes.transaction.log.profiler.manager;

import java.io.FileNotFoundException;

import kr.lesile.processes.transaction.log.common.config.ConfigLoader;
import kr.lesile.processes.transaction.log.common.customer.Customer;
import kr.lesile.processes.transaction.log.common.datamap.DataMap;
import kr.lesile.processes.transaction.log.common.messagequeue.MessageQueue;
import kr.lesile.processes.transaction.log.common.thread.KBThread;
import kr.lesile.processes.transaction.log.profiler.handler.AccountOpenLogHandler;
import kr.lesile.processes.transaction.log.profiler.handler.DepositLogHandler;
import kr.lesile.processes.transaction.log.profiler.handler.JoinLogHandler;
import kr.lesile.processes.transaction.log.profiler.handler.TransferLogHandler;
import kr.lesile.processes.transaction.log.profiler.handler.WithdrawLogHandler;
import kr.lesile.processes.transaction.log.profiler.receiver.TransactionLogReceiver;
import kr.lesile.processes.transaction.log.profiler.restful.SearchService;

public class ProfilerManager extends KBThread
{
	public static final String DEFAULT_CONFIG_FILENAME = "./transaction.log.profiler.properties";
	
	public static DataMap<String, Customer> customerMap = null;
	
	public static MessageQueue<byte[]> MQ_JOIN_LOG = null;
	public static MessageQueue<byte[]> MQ_ACCOUNT_OPEN_LOG = null;
	public static MessageQueue<byte[]> MQ_DEPOSIT_LOG = null;
	public static MessageQueue<byte[]> MQ_WITHDRAW_LOG = null;
	public static MessageQueue<byte[]> MQ_TRANSFER_LOG = null;
	
	private int TransactionLogReceiverCnt = -1;
	private TransactionLogReceiver[] transactionLogReceiver = null;

	private int joinLogHandlerCnt = -1;
	private JoinLogHandler[] joinLogHandler = null;

	private int accountOpenLogHandlerCnt = -1;
	private AccountOpenLogHandler[] accountOpenLogHandler = null;

	private int depositLogHandlerCnt = -1;
	private DepositLogHandler[] depositLogHandler = null;

	private int withdrawLogHandlerCnt = -1;
	private WithdrawLogHandler[] withdrawLogHandler = null;

	private int transferLogHandlerCnt = -1;
	private TransferLogHandler[] transferLogHandler = null;
	
	
	
	
	
	public ProfilerManager()
	{
		ProfilerManager.customerMap = new DataMap<String, Customer>();
	}
	
	
	
	
	
	public static void main( String args[] )
	{
		ProfilerManager manager = new ProfilerManager();
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
			
			if( !this.initMessageQueue() )
			{
				return;
			}
			
			if( !initRestfulService() )
			{
				return;
			}
			
			while( true )
			{
				this.runJoinLogHandler();
				
				this.runAccountOpenLogHandler();
				
				this.runDepositLogHandler();
				
				this.runWithdrawLogHandler();
				
				this.runTransferLogHandler();
	
				this.doThreadWait( 2000L );
				
				this.runTransactionLogReceiver();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
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
			config = new ConfigLoader( ProfilerManager.DEFAULT_CONFIG_FILENAME );
			
			tmpInt = config.getIntTag( "transaction.log.receiver.cnt" );
			this.TransactionLogReceiverCnt = tmpInt;
			
			tmpInt = config.getIntTag( "join.log.handler.cnt" );
			this.joinLogHandlerCnt = tmpInt;

			tmpInt = config.getIntTag( "account.open.log.handler.cnt" );
			this.accountOpenLogHandlerCnt = tmpInt;

			tmpInt = config.getIntTag( "deposit.log.handler.cnt" );
			this.depositLogHandlerCnt = tmpInt;

			tmpInt = config.getIntTag( "withdraw.log.handler.cnt" );
			this.withdrawLogHandlerCnt = tmpInt;

			tmpInt = config.getIntTag( "transfer.log.handler.cnt" );
			this.transferLogHandlerCnt = tmpInt;
			
			
			result = true;
			return result;
		}
		catch( FileNotFoundException fne )
		{
			System.out.println( "[ProfilerManager] Load properties file is not found - SHUTDOWN" );
			return false;
		}
		catch( Exception e )
		{
			System.out.println( "[ProfilerManager] Load properties's value is not available or not found - SHUTDOWN" );
			return false;
		}
		finally
		{
			if( result )
			{
				System.out.println( "[ProfilerManager] Load properties has done - END" );
			}
		}
	}
	
	
	
	private boolean initMessageQueue()
	{
		boolean result = false;
		
		try
		{
			ProfilerManager.MQ_JOIN_LOG = new MessageQueue<byte[]>();
			ProfilerManager.MQ_ACCOUNT_OPEN_LOG = new MessageQueue<byte[]>();
			ProfilerManager.MQ_DEPOSIT_LOG = new MessageQueue<byte[]>();
			ProfilerManager.MQ_WITHDRAW_LOG = new MessageQueue<byte[]>();
			ProfilerManager.MQ_TRANSFER_LOG = new MessageQueue<byte[]>();
			
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
				System.out.println( "[ProfilerManager] Initialize message queue has done - END" );
			}
			else
			{
				System.out.println( "[ProfilerManager] Initialize message queue has not done completely - SHUTDOWN" );
			}
		}
	}
	
	
	
	private boolean initRestfulService()
	{
		boolean result = false;
		SearchService ss = null;
		
		try
		{
			ss = new SearchService();
			ss.init();
			
			result = true;
			return result;
		}
		catch( Exception e )
		{
			return false;
		}
	}
	
	
	
	private void runTransactionLogReceiver() throws Exception
	{
		try
		{
			if( this.transactionLogReceiver == null )
			{
				this.transactionLogReceiver = new TransactionLogReceiver[ this.TransactionLogReceiverCnt ];
			}
			
			
			for( int i = 0; i < this.TransactionLogReceiverCnt; i++ )
			{
				if (this.transactionLogReceiver[i] == null)
				{
					this.transactionLogReceiver[i] = new TransactionLogReceiver( i );
					this.transactionLogReceiver[i].start();
				}
			}
		}
		catch( Exception e )
		{
			return;
		}
	}
	
	
	
	private void runJoinLogHandler()
	{
		try
		{
			if( this.joinLogHandler == null )
			{
				this.joinLogHandler = new JoinLogHandler[ this.joinLogHandlerCnt ];
			}
			
			
			for( int i = 0; i < this.joinLogHandlerCnt; i++ )
			{
				if (this.joinLogHandler[i] == null)
				{
					this.joinLogHandler[i] = new JoinLogHandler( i );
					this.joinLogHandler[i].start();
				}
			}
			
			return;
		}
		catch( Exception e )
		{
			return;
		}
	}
	
	
	
	private void runAccountOpenLogHandler()
	{
		try
		{
			if( this.accountOpenLogHandler == null )
			{
				this.accountOpenLogHandler = new AccountOpenLogHandler[ this.accountOpenLogHandlerCnt ];
			}
			
			
			for( int i = 0; i < this.accountOpenLogHandlerCnt; i++ )
			{
				if (this.accountOpenLogHandler[i] == null)
				{
					this.accountOpenLogHandler[i] = new AccountOpenLogHandler( i );
					this.accountOpenLogHandler[i].start();
				}
			}
			
			return;
		}
		catch( Exception e )
		{
			return;
		}
	}
	
	
	
	private void runDepositLogHandler()
	{
		try
		{
			if( this.depositLogHandler == null )
			{
				this.depositLogHandler = new DepositLogHandler[ this.depositLogHandlerCnt ];
			}
			
			
			for( int i = 0; i < this.depositLogHandlerCnt; i++ )
			{
				if (this.depositLogHandler[i] == null)
				{
					this.depositLogHandler[i] = new DepositLogHandler( i );
					this.depositLogHandler[i].start();
				}
			}
			
			return;
		}
		catch( Exception e )
		{
			return;
		}
	}
	
	
	
	private void runWithdrawLogHandler()
	{
		try
		{
			if( this.withdrawLogHandler == null )
			{
				this.withdrawLogHandler = new WithdrawLogHandler[ this.withdrawLogHandlerCnt ];
			}
			
			
			for( int i = 0; i < this.withdrawLogHandlerCnt; i++ )
			{
				if (this.withdrawLogHandler[i] == null)
				{
					this.withdrawLogHandler[i] = new WithdrawLogHandler( i );
					this.withdrawLogHandler[i].start();
				}
			}
			
			return;
		}
		catch( Exception e )
		{
			return;
		}
	}
	
	
	
	private void runTransferLogHandler()
	{
		try
		{
			if( this.transferLogHandler == null )
			{
				this.transferLogHandler = new TransferLogHandler[ this.transferLogHandlerCnt ];
			}
			
			
			for( int i = 0; i < this.transferLogHandlerCnt; i++ )
			{
				if (this.transferLogHandler[i] == null)
				{
					this.transferLogHandler[i] = new TransferLogHandler( i );
					this.transferLogHandler[i].start();
				}
			}
		}
		catch( Exception e )
		{
			return;
		}
	}
	
	
}
