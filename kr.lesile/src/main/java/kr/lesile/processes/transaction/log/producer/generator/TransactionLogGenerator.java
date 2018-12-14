package kr.lesile.processes.transaction.log.producer.generator;

import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import kr.lesile.processes.transaction.log.common.config.ConfigLoader;
import kr.lesile.processes.transaction.log.common.telegram.AccountOpenLog;
import kr.lesile.processes.transaction.log.common.telegram.DepositLog;
import kr.lesile.processes.transaction.log.common.telegram.JoinLog;
import kr.lesile.processes.transaction.log.common.telegram.LogTelegram;
import kr.lesile.processes.transaction.log.common.telegram.TransferLog;
import kr.lesile.processes.transaction.log.common.telegram.WithdrawLog;
import kr.lesile.processes.transaction.log.common.thread.KBThread;
import kr.lesile.processes.transaction.log.producer.manager.ProducerManager;



/**
 * 거래 로그 생성기
 */
public class TransactionLogGenerator extends KBThread
{
	private long threadDelay = 1;
	private int generatorId = 0;
	private int generatorCnt = 0;
	
	
	
	public TransactionLogGenerator( int generatorId )
	{
		this.generatorId = generatorId+1;
	}
	
	
	
	
	
	public void run()
	{
		try
		{
			if( !this.loadProperties() )
			{
				return;
			}
			
			this.generateTransactionLogs();
		}
		catch( Exception e )
		{
			return;
		}
	}

	
	
	/**
	 * 거래 로그를 순차적으로 생성하는 메소드
	 * 
	 * 가입 → 계좌개설 → 입금 → 출금 → 이체 순으로 로그 생성
	 */
	private void generateTransactionLogs()
	{
		try
		{
			while( this.generatorId <= 50000 )
			{
				this.generateJoinLog();
				
				this.doThreadWait( this.threadDelay );
				
				this.generateAccountOpenLog();

				this.doThreadWait( this.threadDelay );
				
				this.generateDepositLog();

				this.doThreadWait( this.threadDelay );
				
				this.generateWithdrawLog();

				this.doThreadWait( this.threadDelay );
				
				this.generateTransferLog();
				
				
				/**
				 * 1 ~ Generator 수 까지 아이디를 채번하고 있으므로,
				 * Generator 수만큼 더해주면서 진행하면, 5만번까지 거래로그를 차례대로 생성하게 된다.
				 */
				this.generatorId += this.generatorCnt;
			}
			
			this.doThreadWait( this.threadDelay );
		}
		catch( Exception e )
		{
			return;
		}
		
	}
	
	
	
	private void generateJoinLog()
	{
		String strCurDate = this.getCurrentDate();

		JoinLog joinLog = new JoinLog( "CUSTOMER."+ this.generatorId, "Lee."+this.generatorId, strCurDate );
		
		this.generateMessage( joinLog );
	}
	
	
	private void generateAccountOpenLog()
	{
		String strCurDate = this.getCurrentDate();
		
		AccountOpenLog accountOpenLog = new AccountOpenLog( "CUSTOMER."+ this.generatorId, "ACCOUNT."+this.generatorId, strCurDate );
		
		this.generateMessage( accountOpenLog );
	}
	
	
	
	private void generateDepositLog()
	{
		String strCurDate = this.getCurrentDate();
		
		DepositLog depositLog = new DepositLog( "CUSTOMER."+ this.generatorId, "ACCOUNT."+ this.generatorId, Integer.parseInt(this.generatorId+"0000"), strCurDate );

		this.generateMessage( depositLog );
	}
	
	
	
	private void generateWithdrawLog()
	{
		String strCurDate = this.getCurrentDate();
		
		WithdrawLog withdrawLog = new WithdrawLog( "CUSTOMER."+ this.generatorId, "ACCOUNT."+ this.generatorId, 10000, strCurDate );

		this.generateMessage( withdrawLog );
	}
	
	
	
	private void generateTransferLog()
	{
		String strCurDate = this.getCurrentDate();
		
		TransferLog transferLog = new TransferLog( "CUSTOMER."+ this.generatorId, "ACCOUNT."+this.generatorId, 
												  "BANK."+(this.generatorId+1), "ACCOUNT."+(this.generatorId+1), 
												  "CUSTOMER."+ this.generatorId, 5000, strCurDate);

		this.generateMessage( transferLog );
	}
	
	
	
	private boolean loadProperties()
	{
		boolean result = false;
		ConfigLoader config = null;
		int tmpInt = -1;
		
		try
		{
			config = new ConfigLoader( ProducerManager.DEFAULT_CONFIG_FILENAME );
			
			tmpInt = config.getIntTag( "transaction.log.generator.thread.delay" );
			if( tmpInt < 0 )
			{
				return result;
			}
			this.threadDelay = tmpInt;
			
			tmpInt = config.getIntTag( "transaction.log.generator.cnt" );
			if( tmpInt < 0 )
			{
				return result;
			}
			this.generatorCnt = tmpInt;
			
			result = true;
			return result;
		}
		catch( FileNotFoundException fne )
		{
			System.out.println( "[TransactionLogGenerator] Load properties file is not found - SHUTDOWN" );
			return false;
		}
		catch( Exception e )
		{
			System.out.println( "[TransactionLogGenerator] Load properties's value is not available or not found - SHUTDOWN" );
			return false;
		}
		finally
		{
			if( result )
			{
				System.out.println( "[TransactionLogGenerator] Load properties has done - END" );
			}
		}
	}
	
	
	
	private String getCurrentDate()
	{
		SimpleDateFormat formatter = new SimpleDateFormat ( "yyyyMMddHHmmss", Locale.KOREA );
		Date curDate = new Date();
		return formatter.format ( curDate );
	}
	
	
	
	private void generateMessage( LogTelegram logTelegram )
	{
		byte[] transactionLog = logTelegram.getJsonData().getBytes();
		ProducerManager.MQ_SENDER.put( transactionLog );
	}
	
	
}
